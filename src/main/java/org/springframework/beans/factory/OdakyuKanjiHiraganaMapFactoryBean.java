package org.springframework.beans.factory;

import java.net.URI;
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
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

public class OdakyuKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	@URL("https://www.odakyu.jp/station/by_station")
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
		return getObject(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null),
				".stationTimetableAiueo-name"));
		//
	}

	@Nullable
	private static Map<String, String> getObject(@Nullable final List<Element> es) {

		Map<String, String> map = null;
		//
		Iterable<Element> children = null;
		//
		String key, value = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if (IterableUtils.size(children = ElementUtil.children(IterableUtils.get(es, i))) < 2) {
				//
				continue;
				//
			} // if
				//
			value = ElementUtil.text(IterableUtils.get(children, 0));
			//
			if (Util.containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
					key = ElementUtil.text(IterableUtils.get(children, 1)))
					&& !Objects.equals(Util.get(map, key), value)) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			Util.put(map, key, value);
			//
		} // for
			//
		return map;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}