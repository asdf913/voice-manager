package org.springframework.beans.factory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class RinkaiSenKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), ".stationnav a");
		//
		Map<String, String> map = null;
		//
		Entry<String, String> entry = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((entry = createEntry(NodeUtil.absUrl(es.get(i), "href"))) == null
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			map.put(entry.getKey(), entry.getValue());
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Entry<String, String> createEntry(final String url) throws IOException {
		//
		MutablePair<String, String> pair = null;
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), ".stationnav a");
		//
		int size = IterableUtils.size(es);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Element e = size == 1 ? IterableUtils.get(es, 0) : null;
		//
		if ((size = e != null ? e.childrenSize() : 0) > 0
				&& (pair = ObjectUtils.getIfNull(pair, MutablePair::new)) != null) {
			//
			pair.setLeft(ElementUtil.text(e.child(0)));
			//
		} // if
			//
		if (size > 1 && (pair = ObjectUtils.getIfNull(pair, MutablePair::new)) != null) {
			//
			pair.setRight(ElementUtil.text(e.child(1)));
			//
		} // if
			//
		return pair;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}