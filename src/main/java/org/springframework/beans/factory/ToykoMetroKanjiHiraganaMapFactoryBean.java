package org.springframework.beans.factory;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class ToykoMetroKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, url, URL::new, null),
				x -> Jsoup.parse(x, 0), null);
		//
		final List<Element> es = document != null ? document.select(".v2_linkStationListLink") : null;
		//
		Element e = null;
		//
		List<Node> childNodes = null;
		//
		Map<String, String> map = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null || (childNodes = e.childNodes()) == null || childNodes.size() < 2
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			map.put(text(cast(TextNode.class, childNodes.get(0))), text(cast(Element.class, childNodes.get(1))));
			//
		} // for
			//
		return map;
		//
	}

	private static String text(final TextNode instance) throws IllegalAccessException {
		return instance != null && FieldUtils.readField(instance, "value", true) != null ? instance.text() : null;
	}

	private static String text(final Element instance) throws IllegalAccessException {
		return instance != null && FieldUtils.readField(instance, "childNodes", true) != null ? instance.text() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}