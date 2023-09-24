package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;

import io.github.toolfactory.narcissus.Narcissus;

public class KominatoKanjiRomajiOrHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

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
		return getObject(
				Util.filter(
						FailableStreamUtil
								.stream(FailableStreamUtil.map(
										new FailableStream<>(
												Util.stream(ElementUtil.select(
														testAndApply(Objects::nonNull,
																testAndApply(StringUtils::isNotBlank, url, URL::new,
																		null),
																x -> Jsoup.parse(x, 0), null),
														"div.box-img a"))),
										x -> getKanjiHiraganaRomaji(NodeUtil.absUrl(x, "href")))),
						Objects::nonNull));
		//
	}

	private Map<String, String> getObject(final Stream<KanjiHiraganaRomaji> stream) {
		//
		return Util.collect(stream, LinkedHashMap::new, this::accumulate, Map::putAll);
		//
	}

	private void accumulate(final Map<String, String> m, final KanjiHiraganaRomaji v) {
		//
		accumulate(m, v, Util.getDeclaredFields(Util.getClass(v)));
		//
	}

	private void accumulate(final Map<String, String> m, final KanjiHiraganaRomaji v, final Field[] fs) {
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
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if (!Util.isAssignableFrom(Util.getDeclaringClass(f = fs[i]), Util.getClass(v))) {
				//
				continue;
				//
			} // if
				//
			try {
				//
				s = Util.toString(
						testAndApply((a, b) -> b != null, f, v, (a, b) -> FieldUtils.readField(a, b, true), null));
				//
			} catch (final IllegalAccessException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			if ((unicodeBlocks = getUnicodeBlocks(s)) != null && unicodeBlocks.size() == 1) {
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
		if (Boolean.logicalAnd(key != null, value != null)) {
			//
			Util.put(m, IValue0Util.getValue0(key), IValue0Util.getValue0(value));
			//
		} // if
			//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class KanjiHiraganaRomaji {

		private String kanji, hiragana, romaji = null;

	}

	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final String urlString) throws IOException {
		//
		final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, urlString, URL::new, null), x -> Jsoup.parse(x, 0), null),
				".tx--title");
		//
		if (elements != null && elements.size() > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getKanjiHiraganaRomaji(ElementUtil.children(elements != null ? elements.get(0) : null));
		//
	}

	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final List<Element> elements) {
		//
		Element element = null;
		//
		KanjiHiraganaRomaji kanjiHiraganaRomaji = null;
		//
		Field[] fs = null;
		//
		String string = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
		//
		for (int i = 0; elements != null && i < elements.size(); i++) {
			//
			if ((element = elements.get(i)) == null
					|| isAllFieldNonBlank(
							fs = ObjectUtils.getIfNull(fs, () -> Util.getDeclaredFields(KanjiHiraganaRomaji.class)),
							kanjiHiraganaRomaji = ObjectUtils.getIfNull(kanjiHiraganaRomaji, KanjiHiraganaRomaji::new))
					|| (unicodeBlocks = getUnicodeBlocks(string = ElementUtil.text(element))) == null
					|| unicodeBlocks.size() != 1) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(unicodeBlocks, Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))
					&& kanjiHiraganaRomaji != null) {
				//
				kanjiHiraganaRomaji.kanji = string;
				//
			} else if (Objects.equals(unicodeBlocks, Collections.singletonList(UnicodeBlock.HIRAGANA))
					&& kanjiHiraganaRomaji != null) {
				//
				kanjiHiraganaRomaji.hiragana = string;
				//
			} else if (Objects.equals(unicodeBlocks, Collections.singletonList(UnicodeBlock.BASIC_LATIN))
					&& kanjiHiraganaRomaji != null) {
				//
				kanjiHiraganaRomaji.romaji = string;
				//
			} // if
				//
		} // for
			//
		return kanjiHiraganaRomaji;
		//
	}

	private static boolean isAllFieldNonBlank(final Field[] fs, final KanjiHiraganaRomaji kanjiHiraganaRomaji) {
		//
		Boolean nonBlank = null;
		//
		Field f = null;
		//
		for (int j = 0; fs != null && j < fs.length; j++) {
			//
			if ((f = fs[j]) == null) {
				//
				continue;
				//
			} // if
				//
			nonBlank = BooleanUtils.toBooleanDefaultIfNull(nonBlank, true)
					&& StringUtils.isNotBlank(Util.toString(Util.isStatic(f) ? Narcissus.getStaticField(f)
							: testAndApply(
									(a, b) -> a != null && Objects.equals(Util.getDeclaringClass(b), Util.getClass(a)),
									kanjiHiraganaRomaji, f, Narcissus::getField, null)));
			//
		} // for
			//
		return BooleanUtils.toBooleanDefaultIfNull(nonBlank, false);
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final String string) {
		//
		final char[] cs = string != null ? string.toCharArray() : null;
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (instance != null && instance.test(t, u) && consumer != null) {
			consumer.accept(t, u);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}