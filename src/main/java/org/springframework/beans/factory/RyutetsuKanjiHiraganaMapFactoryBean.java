package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import io.github.toolfactory.narcissus.Narcissus;

public class RyutetsuKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	private UnicodeBlock keyUnicodeBlock, valueUnicodeBlock = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setKeyUnicodeBlock(final UnicodeBlock keyUnicodeBlock) {
		this.keyUnicodeBlock = keyUnicodeBlock;
	}

	public void setValueUnicodeBlock(final UnicodeBlock valueUnicodeBlock) {
		this.valueUnicodeBlock = valueUnicodeBlock;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final List<KanjiHiraganaRomaji> khrs = createKanjiHiraganaRomajiList(
				ElementUtil.select(testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, url, URL::new, null),
						x -> Jsoup.parse(x, 0), null), ".station_t"));
		//
		final Field[] fs = Util.getDeclaredFields(KanjiHiraganaRomaji.class);
		//
		//
		IValue0<String> key = null, value = null;
		//
		Map<String, String> map = null;
		//
		Entry<IValue0<String>, IValue0<String>> entry = null;
		//
		for (int i = 0; khrs != null && i < khrs.size(); i++) {
			//
			if ((entry = createEntry(fs, khrs.get(i))) == null) {
				//
				continue;
				//
			} // if
				//
			if (Boolean.logicalOr((key = Util.getKey(entry)) == null, (value = Util.getValue(entry)) == null)) {
				//
				continue;
				//
			} // if
				//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), IValue0Util.getValue0(key),
					IValue0Util.getValue0(value));
			//
		} // for
			//
		return map;
		//
	}

	private Entry<IValue0<String>, IValue0<String>> createEntry(final Field[] fs, final Object v) {
		//
		Field f = null;
		//
		String s = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
		//
		UnicodeBlock unicodeBlock = null;
		//
		IValue0<String> key = null, value = null;
		//
		Class<?> clz = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if (!Util.isAssignableFrom(Util.getDeclaringClass(f = fs[i]), clz = Util.getClass(v))
					|| Util.contains(Arrays.asList(Boolean.class), clz)) {
				//
				continue;
				//
			} // if
				//
			if ((unicodeBlocks = getUnicodeBlocks(Util.toCharArray(s = Util.toString(
					testAndApply((a, b) -> b != null, f, v, (a, b) -> Narcissus.getObjectField(b, a), null))))) != null
					&& unicodeBlocks.size() == 1) {
				//
				if (keyUnicodeBlock == (unicodeBlock = unicodeBlocks.get(0))) {
					//
					key = Unit.with(s);
					//
				} else if (valueUnicodeBlock == unicodeBlock) {
					//
					value = Unit.with(s);
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return Pair.of(key, value);
		//
	}

	@Nullable
	private static List<KanjiHiraganaRomaji> createKanjiHiraganaRomajiList(final List<Element> es) {
		//
		List<Node> childNodes = null;
		//
		KanjiHiraganaRomaji khr = null;
		//
		TextNode textNode = null;
		//
		Element e = null;
		//
		List<KanjiHiraganaRomaji> khrs = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((childNodes = NodeUtil.childNodes(es.get(i))) == null) {
				//
				continue;
				//
			} // if
				//
			khr = new KanjiHiraganaRomaji();
			//
			for (final Node node : childNodes) {
				//
				if (node == null) {
					//
					continue;
					//
				} // if
					//
				if (StringUtils.equalsIgnoreCase(node.nodeName(), "a") && khr.romaji == null) {
					//
					khr.romaji = NodeUtil.attr(node, "name");
					//
				} else if ((textNode = Util.cast(TextNode.class, node)) != null && khr.kanji == null) {
					//
					khr.kanji = textNode.text();
					//
				} else if (StringUtils.equalsIgnoreCase(node.nodeName(), "span")
						&& (e = Util.cast(Element.class, node)) != null && khr.hiragana == null) {
					//
					khr.hiragana = StringUtils.replaceChars(ElementUtil.text(e), "()", "");
					//
				} // if
					//
			} // for
				//
			Util.add(khrs = ObjectUtils.getIfNull(khrs, ArrayList::new), khr);
			//
		} // for
			//
		return khrs;
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class KanjiHiraganaRomaji {

		private String kanji, hiragana, romaji = null;

	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(@Nullable final char[] cs) {
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
			return unicodeBlocks;
			//
		} // if
			//
		return null;
		//
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> instance, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (instance != null && instance.test(t, u) && consumer != null) {
			consumer.accept(t, u);
		} // if
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}