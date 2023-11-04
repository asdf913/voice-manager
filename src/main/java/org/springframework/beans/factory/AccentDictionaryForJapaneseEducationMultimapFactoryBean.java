package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfoUtil;

public class AccentDictionaryForJapaneseEducationMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private Resource resource = null;

	private String url = null;

	private String unicodeBlock = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUnicodeBlock(final String unicodeBlock) {
		this.unicodeBlock = unicodeBlock;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument",
					Util.getMimeType(testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null)))
					|| XlsxUtil.isXlsx(resource)) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					final IValue0<Multimap<String, String>> value = createMultimap(wb);
					//
					if (value != null) {
						//
						return IValue0Util.getValue0(value);
						//
					} // if
						//
						//
				} // try
					//
			} // if
				//
		} // if
			//
		return createMultimapByUrl(url, ProtocolUtil.getAllowProtocols(), unicodeBlock);
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(@Nullable final Workbook wb) {
		//
		IValue0<Multimap<String, String>> value = null;
		//
		if (wb != null && wb.iterator() != null) {
			//
			Multimap<String, String> multimap = null;
			//
			for (final Sheet sheet : wb) {
				//
				if (sheet == null || sheet.iterator() == null || (multimap = createMultimap(sheet)) == null) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(
						IValue0Util.getValue0(
								value = ObjectUtils.getIfNull(value, () -> Unit.with(LinkedHashMultimap.create()))),
						multimap);
				//
			} // for
				//
		} // if
			//
		return value;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(@Nullable final Sheet sheet) {
		//
		Multimap<String, String> value = null;
		//
		if (sheet != null && sheet.iterator() != null) {
			//
			AtomicBoolean firstRow = null;
			//
			for (final Row row : sheet) {
				//
				if (row == null || BooleanUtils.toBooleanDefaultIfNull(
						getAndSet((firstRow = ObjectUtils.getIfNull(firstRow, () -> new AtomicBoolean(true))), false),
						false)) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(value = ObjectUtils.getIfNull(value, LinkedHashMultimap::create),
						CellUtil.getStringCellValue(RowUtil.getCell(row, 0)),
						CellUtil.getStringCellValue(RowUtil.getCell(row, 1)));
				//
			} // for
				//
		} // if
			//
		return value;
		//
	}

	@Nullable
	private static Boolean getAndSet(@Nullable final AtomicBoolean instance, final boolean newValue) {
		return instance != null ? Boolean.valueOf(instance.getAndSet(newValue)) : null;
	}

	@Nullable
	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols,
			final String unicodeBlock) throws Exception {
		//
		final Elements as = ElementUtil.select(testAndApply(
				x -> x != null && (allowProtocols == null || allowProtocols.length == 0
						|| StringUtils.equalsAnyIgnoreCase(x.getProtocol(), allowProtocols)),
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), ".menu a");
		//
		Multimap<String, String> result = null, temp = null;
		//
		for (int i = 0; as != null && i < as.size(); i++) {
			//
			if ((result = ObjectUtils.getIfNull(result, LinkedHashMultimap::create)) != null
					&& (temp = createMultimap(NodeUtil.absUrl(as.get(i), "href"), allowProtocols,
							unicodeBlock)) != null) {
				//
				result.putAll(temp);
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final String url, final String[] allowProtocols,
			final String unicodeBlock) throws Exception {
		//
		final Elements tds = ElementUtil.getElementsByTag(testAndApply(
				x -> x != null && (allowProtocols == null || allowProtocols.length == 0
						|| StringUtils.equalsAnyIgnoreCase(x.getProtocol(), allowProtocols)),
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "td");
		//
		Element td = null;
		//
		Element firstChild = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		String[] ss = null;
		//
		final int size = IterableUtils.size(tds);
		//
		Pair<String[], String> pair = null;
		//
		AtomicReference<Pattern> arPattern = null;
		//
		for (int i = 0; i < size; i++) {
			//
			if ((td = IterableUtils.get(tds, i)) == null
					|| (td.childrenSize() > 0 && (firstChild = ElementUtil.child(td, 0)) != null
							&& StringUtils.equalsIgnoreCase("script", ElementUtil.tagName(firstChild)))
					|| (pair = getPair(ElementUtil.text(td), unicodeBlock,
							arPattern = ObjectUtils.getIfNull(arPattern, AtomicReference::new))) == null
					|| (ss = IValue0Util.getValue0(pair)) == null) {
				//
				continue;
				//
			} // if
				//
			for (final String s : ss) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.trim(s), Util.group(matcher, 1));
				//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Pair<String[], String> getPair(final String text, final String unicodeBlock,
			final AtomicReference<Pattern> arPattern) {
		//
		Pair<String[], String> pair = null;
		//
		if (StringUtils.isBlank(unicodeBlock)) {
			//
			final int index = StringUtils.indexOf(text, '(');
			//
			IValue0<String> key = null;
			//
			if (index > 0) {
				//
				key = Unit.with(StringUtils.trim(StringUtils.substring(text, 0, index)));
				//
			} // if
				//
			if (key != null) {
				//
				final StringBuilder sb = new StringBuilder(
						StringUtils.defaultString(StringUtils.substringAfter(text, '(')));
				//
				if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ')') {
					//
					sb.deleteCharAt(sb.length() - 1);
					//
				} // if
					//
				pair = Pair.with(StringUtils.split(Util.toString(sb), '/'), IValue0Util.getValue0(key));
				//
			} // if
				//
		} else {
			//
			final Pattern pattern = ObjectUtils.getIfNull(Util.get(arPattern),
					() -> Pattern.compile(String.format("(\\p{In%1$s}+)\\s+\\((.+)\\)", unicodeBlock)));
			//
			Util.set(arPattern, pattern);
			//
			pair = getPair(pattern, text);
			//
		} // if
			//
		return pair;
		//
	}

	@Nullable
	private static Pair<String[], String> getPair(final Pattern pattern, final String text) {
		//
		final Matcher matcher = Util.matcher(pattern, text);
		//
		return Util.matches(matcher) && Util.groupCount(matcher) > 1
				? Pair.with(StringUtils.split(Util.group(matcher, 2), '/'), Util.group(matcher, 1))
				: null;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}