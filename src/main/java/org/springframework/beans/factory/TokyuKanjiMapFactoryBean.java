package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;

public class TokyuKanjiMapFactoryBean implements FactoryBean<Map<String, String>> {

	private static enum RomajiOrHiragana {

		ROMAJI, HIRAGANA

	}

	private String url = null;

	private RomajiOrHiragana romajiOrHiragana = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setRomajiOrHiragana(final Object instance) {
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
						.filter(x -> StringUtils.startsWithIgnoreCase(name(x), string)).toList();
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
		} else if (instance instanceof byte[] bs) {
			//
			setRomajiOrHiragana(new String(bs));
			//
		} else {
			//
			throw new IllegalArgumentException(instance.toString());
			//
		} // if
			//
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		return getObject(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), ".mod-change-link"),
				romajiOrHiragana);
		//
	}

	private static Map<String, String> getObject(final Iterable<Element> es, final Object romajiOrHiragana)
			throws IOException {
		//
		Map<String, String> map = null;
		//
		if (es != null) {
			//
			Element a = null;
			//
			String text = null;
			//
			Map<RomajiOrHiragana, String> temp = null;
			//
			for (final Element e : es) {
				//
				if (e == null || !isAllCharacterInSameUnicodeBlock(text = e.text(), UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
						|| (a = e.selectFirst("a")) == null || (temp = getRomajiOrHiraganaMap(a.absUrl("href"))) == null
						|| temp.isEmpty()) {
					//
					continue;
					//
				} // if
					//
				if (!containsKey(temp, romajiOrHiragana)) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), text, temp.get(romajiOrHiragana));
				//
			} // for
				//
		} // if
			//
		return map;
		//
	}

	private static Map<RomajiOrHiragana, String> getRomajiOrHiraganaMap(final String url)
			throws MalformedURLException, IOException {
		//
		return getRomajiOrHiraganaMap(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotEmpty, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"p[class^=\"name-sub\"]"));
		//
	}

	private static Map<RomajiOrHiragana, String> getRomajiOrHiraganaMap(final Iterable<Element> es)
			throws MalformedURLException, IOException {
		//
		Map<RomajiOrHiragana, String> map = null;
		//
		if (es != null) {
			//
			String classString = null;
			//
			for (final Element e : es) {
				//
				if (e == null) {
					//
					continue;
					//
				} // if
					//
				if (StringUtils.equalsIgnoreCase(classString = e.attr("class"), "name-sub01")) {
					//
					testAndAccept((a, b, c) -> containsKey(a, b), map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
							RomajiOrHiragana.ROMAJI, e.text(), (a, b, c) -> {
								throw new IllegalStateException();
							}, (a, b, c) -> put(a, b, c));
					//
				} else if (StringUtils.equalsIgnoreCase(classString, "name-sub02")) {
					//
					testAndAccept((a, b, c) -> containsKey(a, b), map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
							RomajiOrHiragana.HIRAGANA, e.text(), (a, b, c) -> {
								throw new IllegalStateException();
							}, (a, b, c) -> put(a, b, c));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return map;
		//
	}

	private static <T, U, V> void testAndAccept(final TriPredicate<T, U, V> pridicate, final T t, final U u, final V v,
			final TriConsumer<T, U, V> consumerTrue, final TriConsumer<T, U, V> consumerFalse) {
		if (pridicate != null && pridicate.test(t, u, v)) {
			accept(consumerTrue, t, u, v);
		} else {
			accept(consumerFalse, t, u, v);
		}
	}

	private static <T, U, V> void accept(final TriConsumer<T, U, V> instance, final T t, final U u, final V v) {
		if (instance != null) {
			instance.accept(t, u, v);
		}
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static boolean isAllCharacterInSameUnicodeBlock(final String string, final UnicodeBlock unicodeBlock) {
		//
		final char[] cs = string != null ? string.toCharArray() : null;
		//
		if (cs != null) {
			//
			List<UnicodeBlock> unicodeBlocks = null;
			//
			for (final char c : cs) {
				//
				testAndAccept((a, b) -> b != null && !contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						TokyuKanjiMapFactoryBean::add);
				//
			} // for
				//
			return Objects.equals(Collections.singletonList(unicodeBlock), unicodeBlocks);
			//
		} // if
			//
		return true;
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}