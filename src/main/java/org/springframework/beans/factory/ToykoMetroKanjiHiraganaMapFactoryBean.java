package org.springframework.beans.factory;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class ToykoMetroKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

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
			map.put(text(cast(TextNode.class, childNodes.get(0))),
					ElementUtil.text(cast(Element.class, childNodes.get(1))));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static String text(@Nullable final TextNode instance) throws IllegalAccessException {
		return instance != null && FieldUtils.readField(instance, "value", true) != null ? instance.text() : null;
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}