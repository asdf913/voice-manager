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