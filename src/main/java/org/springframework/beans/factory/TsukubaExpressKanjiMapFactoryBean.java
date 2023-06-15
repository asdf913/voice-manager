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
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class TsukubaExpressKanjiMapFactoryBean extends StringMapFromResourceFactoryBean {

	private static enum RomajiOrHiragana {

		ROMAJI, HIRAGANA

	}

	private String url = null;

	@Nullable
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
				final List<RomajiOrHiragana> rohs = Util.toList(Arrays.stream(RomajiOrHiragana.values())
						.filter(x -> StringUtils.startsWithIgnoreCase(name(x), string)));
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
			throw new IllegalArgumentException(Util.toString(instance));
			//
		} // if
			//
	}

	@Nullable
	private static String name(@Nullable final Enum<?> instance) {
		return instance != null ? instance.name() : null;
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
			Util.put(map, entry.getKey(), entry.getValue());
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
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
			add(ss, s);
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

	private static <T> void add(@Nullable final Collection<T> items, final T item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}