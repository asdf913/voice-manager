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

import javax.annotation.Nullable;

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

	@Nullable
	private static Map<String, String> createMap(@Nullable final List<Element> es) {
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
					filter(stream(childNodes(parentNode(e = es.get(i)))), TextNode.class::isInstance))) == null
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
					return testAndApply(Objects::nonNull, getWholeText(textNode), StringUtils::trim, null);
					//
				} // if
					//
				return StringUtils.trim(Util.toString(x));
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

	@Nullable
	private static String getWholeText(@Nullable final TextNode instance) {
		return instance != null ? instance.getWholeText() : null;
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static Matcher matcher(@Nullable final Pattern instance, @Nullable final CharSequence input) {
		return instance != null && input != null ? instance.matcher(input) : null;
	}

	private static boolean find(@Nullable final Matcher instance) {
		return instance != null && instance.find();
	}

	@Nullable
	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	@Nullable
	private static Node parentNode(@Nullable final Node instance) {
		return instance != null ? instance.parentNode() : null;
	}

	@Nullable
	private static List<Node> childNodes(@Nullable final Node instance) {
		return instance != null ? instance.childNodes() : null;
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
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

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}