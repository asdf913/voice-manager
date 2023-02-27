package org.springframework.beans.factory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

public class JapaneseNameMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final String[] allowProtocols = ProtocolUtil.getAllowProtocols();
		//
		final Elements tds = ElementUtil.select(
				testAndApply(
						x -> x != null && (allowProtocols == null || allowProtocols.length == 0
								|| StringUtils.equalsAnyIgnoreCase(getProtocol(x), allowProtocols)),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"table td");
		//
		Element td = null;
		//
		Elements divs = null;
		//
		Table<String, String, String> table = null;
		//
		Multimap<String, String> multimap = null;
		//
		String text, hiragana = null;
		//
		Element nextElementSibling = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		List<String> strings = null;
		//
		for (int i = 0; i < IterableUtils.size(tds); i++) {
			//
			if ((divs = ElementUtil.select(td = IterableUtils.get(tds, i), "div")) == null || divs.isEmpty()
					|| divs.iterator() == null || (table = ObjectUtils.getIfNull(table, HashBasedTable::create)) == null
					|| (multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create)) == null) {
				//
				continue;
				//
			} // if
				//
			for (final Element div : divs) {
				//
				if (div == null) {
					//
					continue;
					//
				} // if
					//
				nextElementSibling = div;
				//
				while ((nextElementSibling = nextElementSibling.nextElementSibling()) != null
						&& !Objects.equals(nextElementSibling.tagName(), "div")) {
					//
					if ((pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("^\\((.+)\\)$"))) != null
							&& (matcher = pattern
									.matcher(StringUtils.substringAfter(text = nextElementSibling.text(), ' '))) != null
							&& matcher.matches() && matcher.groupCount() == 1
							&& (strings = Arrays.stream(StringUtils.split(matcher.group(1), '/')).map(StringUtils::trim)
									.toList()) != null
							&& strings.iterator() != null) {
						//
						for (final String s : strings) {
							//
							table.put(div.text(), s, hiragana = StringUtils.substringBefore(text, ' '));
							//
							multimap.put(s, hiragana);
							//
						} // for
							//
					} // if
						//
				} // while
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
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