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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class KyushuJapanRailwayKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		IValue0<Map<String, String>> iValue0 = getIvalue0();
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

	@Nullable
	private static Map<String, String> createMap(final String url) throws IOException {
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"ol.stationList li a");
		//
		Map<String, String> map = null;
		//
		Entry<String, String> entry = null;
		//
		String key, value = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((entry = createEntry(NodeUtil.absUrl(es.get(i), "href"))) == null
					|| Objects.equals(key = entry.getKey(), value = entry.getValue())) {
				//
				continue;
				//
			} // if
				//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), key, value);
			//
		} // for
			//
		return map;
		//
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

	@Nullable
	private static Entry<String, String> createEntry(final String url) throws IOException {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null);
		//
		return createEntry(document);
		//
	}

	private static Entry<String, String> createEntry(final Document document) {
		//
		MutablePair<String, String> pair = null;
		//
		// kanji
		//
		List<Element> es = ElementUtil.select(document, "div.box-station-name p.title");
		//
		if (es != null) {
			//
			if (es.size() > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (es.size() == 1) {
				//
				MutablePairUtil.setLeft(pair = ObjectUtils.getIfNull(pair, MutablePair::new),
						ElementUtil.text(IterableUtils.get(es, 0)));
				//
			} // if
				//
		} // if
			//
			// hiragana
			//
		if ((es = ElementUtil.select(document, "p.subtitle")) != null) {
			//
			if (es.size() > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (es.size() == 1) {
				//
				MutablePairUtil.setRight(pair = ObjectUtils.getIfNull(pair, MutablePair::new),
						ElementUtil.text(IterableUtils.get(es, 0)));
				//
			} // if
				//
		} // if
			//
		return pair;
		//
	}

	@Override
	public Class<?> getObjectType() {
		//
		return Map.class;
		//
	}

}