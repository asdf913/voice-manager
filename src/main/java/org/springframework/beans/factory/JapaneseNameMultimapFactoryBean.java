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

import javax.annotation.Nullable;

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

	@Nullable
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
				MultimapUtil.putAll(multimap, temp);
				//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(@Nullable final Workbook wb) {
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

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	private static Multimap<String, String> createMultimap(@Nullable final Element input,
			@Nullable final Pattern pattern) {
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
				: null) != null && !Objects.equals(ElementUtil.tagName(nextElementSibling), "div")) {
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
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), s,
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

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}