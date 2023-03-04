package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class JapaneseNameMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private Resource resource = null;

	private String url = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
			//
			IValue0<Multimap<String, String>> value = null;
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument", getMimeType(ci))
					|| Objects.equals("OLE 2 Compound Document", ci != null ? ci.getMessage() : null)
					|| XlsxUtil.isXlsx(resource)) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					if ((value = createMultimap(wb)) != null) {
						//
						return IValue0Util.getValue0(value);
						//
					} // if
						//
				} // try
					//
			} // if
				//
		} // if
			//
		return createMultimapByUrl(url, ProtocolUtil.getAllowProtocols());
		//
	}

	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols)
			throws IOException {
		//
		final Elements tds = ElementUtil.select(
				testAndApply(
						x -> x != null && (allowProtocols == null || allowProtocols.length == 0
								|| StringUtils.equalsAnyIgnoreCase(getProtocol(x), allowProtocols)),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"table td");
		//
		Elements divs = null;
		//
		Table<String, String, String> table = null;
		//
		Multimap<String, String> multimap = null, temp = null;
		//
		Pattern pattern = null;
		//
		for (int i = 0; i < IterableUtils.size(tds); i++) {
			//
			if ((divs = ElementUtil.select(IterableUtils.get(tds, i), "div")) == null || divs.isEmpty()
					|| divs.iterator() == null || (table = ObjectUtils.getIfNull(table, HashBasedTable::create)) == null
					|| (multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create)) == null) {
				//
				continue;
				//
			} // if
				//
			for (final Element div : divs) {
				//
				if ((temp = createMultimap(div,
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("^\\((.+)\\)$")))) == null) {
					//
					continue;
					//
				} // if
					//
				multimap.putAll(temp);
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

	private static IValue0<Multimap<String, String>> createMultimap(final Workbook wb) {
		//
		final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
		//
		final AtomicBoolean first = new AtomicBoolean(true);
		//
		if (sheet != null && sheet.iterator() != null) {
			//
			IValue0<Multimap<String, String>> multimap = null;
			//
			for (final Row row : sheet) {
				//
				if (row == null || row.getPhysicalNumberOfCells() < 2 || first == null || first.getAndSet(false)) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedListMultimap.create()))),
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
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Multimap<String, String> createMultimap(final Element input, final Pattern pattern) {
		//
		Element nextElementSibling = input;
		//
		Matcher matcher = null;
		//
		String text = null;
		//
		List<String> strings = null;
		//
		Multimap<String, String> multimap = null;
		//
		while ((nextElementSibling = nextElementSibling != null ? nextElementSibling.nextElementSibling()
				: null) != null && !Objects.equals(nextElementSibling.tagName(), "div")) {
			//
			if (pattern != null
					&& (matcher = pattern
							.matcher(StringUtils.substringAfter(text = nextElementSibling.text(), ' '))) != null
					&& matcher.matches() && matcher.groupCount() == 1 && (strings = Arrays
							.stream(StringUtils.split(matcher.group(1), '/')).map(StringUtils::trim).toList()) != null
					&& strings.iterator() != null) {
				//
				for (final String s : strings) {
					//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), s,
							StringUtils.substringBefore(text, ' '));
					//
				} // for
					//
			} // if
				//
		} // while
			//
		return multimap;
		//
	}

	private static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}