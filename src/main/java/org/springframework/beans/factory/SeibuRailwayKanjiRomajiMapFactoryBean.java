package org.springframework.beans.factory;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

public class SeibuRailwayKanjiRomajiMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), ".station__name");
		//
		Map<String, String> map = null;
		//
		Iterable<Element> children = null;
		//
		String key, value = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if (IterableUtils.size(children = ElementUtil.children(es.get(i))) < 2
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			value = ElementUtil.text(IterableUtils.get(children, 1));
			//
			if (map.containsKey(key = ElementUtil.text(IterableUtils.get(children, 0)))
					&& !Objects.equals(map.get(key), value)) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			map.put(key, value);
			//
		} // for
			//
		return map;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}