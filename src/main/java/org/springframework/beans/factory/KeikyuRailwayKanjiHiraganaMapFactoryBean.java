package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class KeikyuRailwayKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private static final Pattern PATTERN = Pattern.compile("^([^駅]+)駅$");

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		return createMap(url);
	}

	private static Map<String, String> createMap(final String url) throws IOException {
		//
		return createMap(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				".maplinks a .station-title span"));
		//
	}

	private static Map<String, String> createMap(final List<Element> es) {
		//
		Element e = null;
		//
		List<Node> nodes = null;
		//
		Matcher matcher = null;
		//
		String string = null;
		//
		Map<String, String> map = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((nodes = toList(
					filter(stream(childNodes(parentNode(e = es.get(i)))), x -> x instanceof TextNode))) == null
					|| nodes.isEmpty()) {
				//
				continue;
				//
			} // if
				//
			if (find(matcher = matcher(PATTERN, string = stream(nodes).map(x -> {
				//
				if (x instanceof TextNode textNode) {
					//
					return testAndApply(Objects::nonNull, getWholeText(textNode), y -> StringUtils.trim(y), null);
					//
				} // if
					//
				return StringUtils.trim(toString(x));
				//
			}).collect(Collectors.joining("")))) && matcher != null && matcher.groupCount() > 0) {
				//
				string = matcher.group(1);
				//
			} // if
				//
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), string, ElementUtil.text(e));
			//
		} // for
			//
		return map;
		//
	}

	private static String getWholeText(final TextNode instance) {
		return instance != null ? instance.getWholeText() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Matcher matcher(final Pattern instance, final CharSequence input) {
		return instance != null ? instance.matcher(input) : null;
	}

	private static boolean find(final Matcher instance) {
		return instance != null && instance.find();
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static Node parentNode(final Node instance) {
		return instance != null ? instance.parentNode() : null;
	}

	private static List<Node> childNodes(final Node instance) {
		return instance != null ? instance.childNodes() : null;
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}