package org.springframework.beans.factory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class TsukubaExpressKanjiMapFactoryBean implements FactoryBean<Map<String, String>> {

	private static enum RomajiOrHiragana {

		ROMAJI, HIRAGANA

	}

	private String url = null;

	private RomajiOrHiragana romajiOrHiragana = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setRomajiOrHiragana(@Nullable final Object instance) {
		//
		if (instance == null) {
			//
			this.romajiOrHiragana = null;
			//
		} else if (instance instanceof RomajiOrHiragana roh) {
			//
			this.romajiOrHiragana = roh;
			//
		} else if (instance instanceof String string) {
			//
			if (StringUtils.isBlank(string)) {
				//
				setRomajiOrHiragana(null);
				//
			} else {
				//
				final List<RomajiOrHiragana> rohs = Arrays.stream(RomajiOrHiragana.values())
						.filter(x -> StringUtils.startsWithIgnoreCase(x != null ? x.name() : null, string)).toList();
				//
				if (IterableUtils.size(rohs) > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				if (!IterableUtils.isEmpty(rohs)) {
					//
					this.romajiOrHiragana = IterableUtils.get(rohs, 0);
					//
				} // if
					//
			} // if
				//
		} else if (instance instanceof char[] cs) {
			//
			setRomajiOrHiragana(new String(cs));
			//
		} else {
			//
			throw new IllegalArgumentException(instance.toString());
			//
		} // if
			//
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), ".station_list a");
		//
		Map<String, String> map = null;
		//
		Entry<String, String> entry = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((entry = createEntry(NodeUtil.absUrl(es.get(i), "href"), romajiOrHiragana)) == null
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

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	private static Entry<String, String> createEntry(final String url, final RomajiOrHiragana roh) throws IOException {
		//
		final Element document = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, url, URL::new, null),
				x -> Jsoup.parse(x, 0), null);
		///
		final MutablePair<String, String> pair = new MutablePair<>(
				getString(ElementUtil.select(document, ".hero_title_label")), null);
		//
		if (Objects.equals(roh, RomajiOrHiragana.HIRAGANA)) {
			//
			pair.setValue(getString(ElementUtil.select(document, ".hero_ruby")));
			//
		} else if (Objects.equals(roh, RomajiOrHiragana.ROMAJI)) {
			//
			pair.setValue(getString(ElementUtil.select(document, ".hero_romanization")));
			//
		} else {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
		return pair;
		//
	}

	@Nullable
	private static String getString(final Iterable<Element> es) {
		//
		List<String> ss = null;
		//
		String s = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (contains(ss = ObjectUtils.getIfNull(ss, ArrayList::new),
					s = ElementUtil.text(IterableUtils.get(es, i)))) {
				//
				continue;
				//
			} // if
				//
			ss.add(s);
			//
		} // for
			//
		if (IterableUtils.size(ss) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return !IterableUtils.isEmpty(ss) ? IterableUtils.get(ss, 0) : null;
		//
	}

	private static boolean contains(@Nullable final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}