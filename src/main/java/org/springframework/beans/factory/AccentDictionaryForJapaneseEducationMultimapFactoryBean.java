package org.springframework.beans.factory;

import java.io.IOException;
import java.net.URL;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class AccentDictionaryForJapaneseEducationMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final String[] allowProtocols = ProtocolUtil.getAllowProtocols();
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
			if ((pattern = ObjectUtils.getIfNull(pattern,
					() -> Pattern.compile("(\\p{InHiragana}+)\\s+\\((.+)\\)"))) != null
					&& (matcher = pattern.matcher(td.text())) != null && matcher.matches() && matcher.groupCount() > 1
					&& (ss = StringUtils.split(matcher.group(2), '/')) != null) {
				//
				for (final String s : ss) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.trim(s), matcher.group(1));
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

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}