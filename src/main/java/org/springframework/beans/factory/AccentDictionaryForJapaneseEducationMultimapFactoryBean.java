package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class AccentDictionaryForJapaneseEducationMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

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
			if (Objects.equals("application/vnd.openxmlformats-officedocument",
					getMimeType(testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null)))
					|| XlsxUtil.isXlsx(resource)) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					if (wb != null) {
						//
						IValue0<Multimap<String, String>> value = null;
						//
						boolean firstRow = true;
						//
						for (final Sheet sheet : wb) {
							//
							if (sheet == null) {
								//
								continue;
								//
							} // if
								//
							firstRow = true;
							//
							for (final Row row : sheet) {
								//
								if (row == null) {
									//
									continue;
									//
								} // if
									//
								if (firstRow) {
									//
									firstRow = false;
									//
									continue;
									//
								} // if
									//
								MultimapUtil.put(
										IValue0Util.getValue0(value = ObjectUtils.getIfNull(value,
												() -> Unit.with(LinkedHashMultimap.create()))),
										row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
								//
							} // for
								//
						} // for
							//
						if (value != null) {
							//
							return IValue0Util.getValue0(value);
							//
						} // if
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
		final Elements as = ElementUtil.select(
				testAndApply(
						x -> x != null && (allowProtocols == null || allowProtocols.length == 0
								|| StringUtils.equalsAnyIgnoreCase(x.getProtocol(), allowProtocols)),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				".menu a");
		//
		Element a = null;
		//
		Multimap<String, String> result = null, temp = null;
		//
		for (int i = 0; as != null && i < as.size(); i++) {
			//
			if ((a = as.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((result = ObjectUtils.getIfNull(result, LinkedHashMultimap::create)) != null
					&& (temp = createMultimap(a.absUrl("href"), allowProtocols)) != null) {
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

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static Multimap<String, String> createMultimap(final String url, final String[] allowProtocols)
			throws IOException {
		//
		final Elements tds = ElementUtil.getElementsByTag(
				testAndApply(
						x -> x != null && (allowProtocols == null || allowProtocols.length == 0
								|| StringUtils.equalsAnyIgnoreCase(x.getProtocol(), allowProtocols)),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"td");
		//
		Element td = null;
		//
		Element firstChild = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		String[] ss = null;
		//
		for (int i = 0; tds != null && i < tds.size(); i++) {
			//
			if ((td = tds.get(i)) == null || (td.childrenSize() > 0 && (firstChild = td.child(0)) != null
					&& StringUtils.equalsIgnoreCase("script", ElementUtil.tagName(firstChild)))) {
				//
				continue;
				//
			} // if
				//
			if (matches(matcher = matcher(
					pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("(\\p{InHiragana}+)\\s+\\((.+)\\)")),
					td.text())) && groupCount(matcher) > 1
					&& (ss = StringUtils.split(group(matcher, 2), '/')) != null) {
				//
				for (final String s : ss) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.trim(s), group(matcher, 1));
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static Matcher matcher(final Pattern instance, final String input) {
		return instance != null && input != null ? instance.matcher(input) : null;
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static int groupCount(final MatchResult instance) {
		return instance != null ? instance.groupCount() : 0;
	}

	private static String group(final MatchResult instance, final int group) {
		return instance != null ? instance.group(group) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}