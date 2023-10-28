package org.springframework.beans.factory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

public class KeikyuRailwayKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private static final Pattern PATTERN = Pattern.compile("^([^駅]+)駅$");

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return createMap(url);
		//
	}

	private static Map<String, String> createMap(final String url) throws Exception {
		//
		return createMap(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), ".maplinks a .station-title span"));
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
			if ((nodes = Util.toList(Util.filter(Util.stream(NodeUtil.childNodes(parentNode(e = es.get(i)))),
					TextNode.class::isInstance))) == null || nodes.isEmpty()) {
				//
				continue;
				//
			} // if
				//
			if (Util.find(matcher = Util.matcher(PATTERN, string = Util.collect(Util.map(Util.stream(nodes), x -> {
				//
				if (x instanceof TextNode textNode) {
					//
					return testAndApply(Objects::nonNull, getWholeText(textNode), StringUtils::trim, null);
					//
				} // if
					//
				return StringUtils.trim(Util.toString(x));
				//
			}), Collectors.joining("")))) && Util.groupCount(matcher) > 0) {
				//
				string = Util.group(matcher, 1);
				//
			} // if
				//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), string, ElementUtil.text(e));
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
	private static Node parentNode(@Nullable final Node instance) {
		return instance != null ? instance.parentNode() : null;
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