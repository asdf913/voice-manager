package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class TokyuKanjiRomajiOrHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

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
				final List<RomajiOrHiragana> rohs = Util.toList(Util.filter(Arrays.stream(RomajiOrHiragana.values()),
						x -> StringUtils.startsWithIgnoreCase(Util.name(x), string)));
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
			throw new IllegalArgumentException(Util.toString(instance));
			//
		} // if
			//
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
				".mod-change-link"), romajiOrHiragana);
		//
	}

	@Nullable
	private static Map<String, String> getObject(@Nullable final Iterable<Element> es, final Object romajiOrHiragana)
			throws Exception {
		//
		Map<String, String> map = null;
		//
		if (es != null) {
			//
			String text = null;
			//
			Map<RomajiOrHiragana, String> temp = null;
			//
			for (final Element e : es) {
				//
				if (e == null
						|| !isAllCharacterInSameUnicodeBlock(text = ElementUtil.text(e),
								UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
						|| (temp = getRomajiOrHiraganaMap(NodeUtil.absUrl(e.selectFirst("a"), "href"))) == null
						|| temp.isEmpty()) {
					//
					continue;
					//
				} // if
					//
				if (!Util.containsKey(temp, romajiOrHiragana)) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), text, Util.get(temp, romajiOrHiragana));
				//
			} // for
				//
		} // if
			//
		return map;
		//
	}

	@Nullable
	private static Map<RomajiOrHiragana, String> getRomajiOrHiraganaMap(final String url) throws Exception {
		//
		return getRomajiOrHiraganaMap(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotEmpty, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "p[class^=\"name-sub\"]"));
		//
	}

	@Nullable
	private static Map<RomajiOrHiragana, String> getRomajiOrHiraganaMap(@Nullable final Iterable<Element> es) {
		//
		if (es == null) {
			//
			return null;
			//
		} // if
			//
		Map<RomajiOrHiragana, String> map = null;
		//
		String classString = null;
		//
		RomajiOrHiragana romajiOrHiragana = null;
		//
		for (final Element e : es) {
			//
			if (e == null) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.equalsIgnoreCase(classString = NodeUtil.attr(e, "class"), "name-sub01")) {
				//
				if (Util.containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
						romajiOrHiragana = RomajiOrHiragana.ROMAJI)) {
					//
					throw new IllegalStateException();
					//
				} else {
					//
					Util.put(map, romajiOrHiragana, ElementUtil.text(e));
					//
				} // if
					//
			} else if (StringUtils.equalsIgnoreCase(classString, "name-sub02")) {
				//
				if (Util.containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
						romajiOrHiragana = RomajiOrHiragana.HIRAGANA)) {
					//
					throw new IllegalStateException();
					//
				} else {
					//
					Util.put(map, romajiOrHiragana, ElementUtil.text(e));
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	private static boolean isAllCharacterInSameUnicodeBlock(@Nullable final String string,
			final UnicodeBlock unicodeBlock) {
		//
		final char[] cs = Util.toCharArray(string);
		//
		if (cs != null) {
			//
			List<UnicodeBlock> unicodeBlocks = null;
			//
			for (final char c : cs) {
				//
				testAndAccept((a, b) -> b != null && !Util.contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						Util::add);
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}