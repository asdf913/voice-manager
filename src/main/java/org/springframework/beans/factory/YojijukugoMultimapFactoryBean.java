package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class YojijukugoMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final byte[] bs = getContentAsByteArray(resource);
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument",
					getMimeType(testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null)))) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
					//
					final AtomicBoolean first = new AtomicBoolean(true);
					//
					if (sheet != null && sheet.iterator() != null) {
						//
						Multimap<String, String> multimap = null;
						//
						for (final Row row : sheet) {
							//
							if (row == null || row.getPhysicalNumberOfCells() < 2 || first == null
									|| first.getAndSet(false)) {
								//
								continue;
								//
							} // if
								//
							MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create),
									toString(row.getCell(0)), toString(row.getCell(1)));
							//
						} // if
							//
						return multimap;
						//
					} // if
						//
					return null;
					//
				} // try
					//
			} // if
				//
		} // if
			//
		final String[] allowProtocols = ProtocolUtil.getAllowProtocols();
		//
		final Elements tables = ElementUtil.getElementsByTag(
				testAndApply(
						x -> allowProtocols == null || allowProtocols.length == 0
								|| StringUtils.equalsAnyIgnoreCase(getProtocol(x), allowProtocols),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"table");
		//
		Element tr, a = null;
		//
		Elements trs, as = null;
		//
		Node nextSibling = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(tables); i++) {
			//
			if ((trs = ElementUtil.getElementsByTag(IterableUtils.get(tables, i), "tr")) == null) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < IterableUtils.size(trs); j++) {
				//
				if ((tr = IterableUtils.get(trs, j)) == null || tr.childNodeSize() < 2
						|| (as = ElementUtil.getElementsByTag(tr, "a")) == null || as == null || as.isEmpty()
						|| (a = IterableUtils.get(as, 0)) == null) {
					//
					continue;
					//
				} // if
					//
					// hiragana
					//
				if ((nextSibling = a.nextSibling()) != null
						&& (pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("[ぁ-ん]+"))) != null
						&& (matcher = pattern.matcher(nextSibling.outerHtml())) != null && matcher.find()) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), a.text(),
							matcher.group());
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static byte[] getContentAsByteArray(final Resource instance) throws IOException {
		return instance != null ? instance.getContentAsByteArray() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}