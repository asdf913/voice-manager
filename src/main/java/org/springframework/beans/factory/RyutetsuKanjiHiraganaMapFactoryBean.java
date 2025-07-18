package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.net.URI;
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
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
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
import org.jsoup.nodes.TextNodeUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class RyutetsuKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	@Note("Key Unicode Block")
	private UnicodeBlock keyUnicodeBlock = null;

	private UnicodeBlock valueUnicodeBlock = null;

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
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return createMap(createKanjiHiraganaRomajiList(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null),
				".station_t")));
		//
	}

	@Nullable
	private Map<String, String> createMap(@Nullable final List<KanjiHiraganaRomaji> khrs) {
		//
		Field[] fs = null;
		//
		IValue0<String> key = null, value = null;
		//
		Map<String, String> map = null;
		//
		Entry<IValue0<String>, IValue0<String>> entry = null;
		//
		String string = null;
		//
		for (int i = 0; khrs != null && i < khrs.size(); i++) {
			//
			if (Boolean
					.logicalOr((key = Util.getKey(entry = createEntry(
							fs = ObjectUtils.getIfNull(fs, () -> Util.getDeclaredFields(KanjiHiraganaRomaji.class)),
							khrs.get(i)))) == null, (value = Util.getValue(entry)) == null)
					|| StringUtils.isEmpty(string = IValue0Util.getValue0(key))) {
				//
				continue;
				//
			} // if
				//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), string, IValue0Util.getValue0(value));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private Entry<IValue0<String>, IValue0<String>> createEntry(@Nullable final Field[] fs, final Object v) {
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
	private static List<KanjiHiraganaRomaji> createKanjiHiraganaRomajiList(@Nullable final List<Element> es) {
		//
		KanjiHiraganaRomaji khr = null;
		//
		List<KanjiHiraganaRomaji> khrs = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			setHiraganaKanjiRomaji(khr = new KanjiHiraganaRomaji(), NodeUtil.childNodes(es.get(i)));
			//
			Util.add(khrs = ObjectUtils.getIfNull(khrs, ArrayList::new), khr);
			//
		} // for
			//
		return khrs;
		//
	}

	private static void setHiraganaKanjiRomaji(final KanjiHiraganaRomaji khr, final Iterable<Node> childNodes) {
		//
		if (Util.iterator(childNodes) != null) {
			//
			TextNode textNode = null;
			//
			Element e = null;
			//
			final Strings strings = Strings.CI;
			//
			for (final Node node : childNodes) {
				//
				if (node == null) {
					//
					continue;
					//
				} // if
					//
				if (StringsUtil.equals(strings, NodeUtil.nodeName(node), "a") && khr != null && khr.romaji == null) {
					//
					khr.romaji = NodeUtil.attr(node, "name");
					//
				} else if ((textNode = Util.cast(TextNode.class, node)) != null && khr != null && khr.kanji == null) {
					//
					khr.kanji = TextNodeUtil.text(textNode);
					//
				} else if (StringsUtil.equals(strings, NodeUtil.nodeName(node), "span")
						&& (e = Util.cast(Element.class, node)) != null && khr != null && khr.hiragana == null) {
					//
					khr.hiragana = StringUtils.replaceChars(ElementUtil.text(e), "()", "");
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class KanjiHiraganaRomaji {

		@Note("Kanji")
		private String kanji = null;

		@Note("Hiragana")
		private String hiragana = null;

		@Note("Romaji")
		private String romaji = null;

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
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}