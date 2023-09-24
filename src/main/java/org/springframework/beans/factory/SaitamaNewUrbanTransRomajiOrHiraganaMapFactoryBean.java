package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
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
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;

public class SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

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
														"li.m-list-train__information-item-station a"))),
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

	private void accumulate(final Map<String, String> m, final KanjiHiraganaRomaji v, @Nullable final Field[] fs) {
		//
		Field f = null;
		//
		String s = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
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
			if ((unicodeBlocks = getUnicodeBlocks(Util.toCharArray(s))) != null) {
				//
				if (key == null) {
					//
					key = getKey(unicodeBlocks, s);
					//
				} // if
					//
				if (value == null) {
					//
					value = getValue(unicodeBlocks, s);
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

	@Nullable
	private IValue0<String> getKey(@Nullable final List<UnicodeBlock> unicodeBlocks, final String s) {
		//
		return unicodeBlocks != null && unicodeBlocks.size() == 1
				&& Objects.equals(unicodeBlocks.get(0), keyUnicodeBlock) ? Unit.with(s) : null;
		//
	}

	@Nullable
	private IValue0<String> getValue(@Nullable final List<UnicodeBlock> unicodeBlocks, final String s) {
		//
		if (unicodeBlocks != null && ((unicodeBlocks.size() == 1
				&& Objects.equals(unicodeBlocks.get(0), valueUnicodeBlock))
				|| (unicodeBlocks.size() > 1 && Objects.equals(valueUnicodeBlock, UnicodeBlock.BASIC_LATIN)
						&& Objects.equals(unicodeBlocks,
								Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.COMBINING_DIACRITICAL_MARKS))))) {
			//
			return Unit.with(s);
			//
		} // if
			//
		return null;
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

		@Note("Kanji")
		private String kanji = null;

		@Note("Hiragana")
		private String hiragana = null;

		private String romaji = null;

	}

	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final String urlString) throws IOException {
		//
		final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, urlString, URL::new, null), x -> Jsoup.parse(x, 0), null),
				".keyvisual__content-box");
		//
		if (elements != null && elements.size() > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getKanjiHiraganaRomaji(Util.toList(Util.map(
				Util.stream(ElementUtil.children(elements != null && elements.size() == 1 ? elements.get(0) : null)),
				ElementUtil::text)));
		//
	}

	@Nullable
	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final List<String> ss) {
		//
		KanjiHiraganaRomaji kanjiHiraganaRomaji = null;
		//
		String s = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
		//
		Pattern pattern = null;
		//
		for (int i = 0; ss != null && i < ss.size(); i++) {
			//
			if ((kanjiHiraganaRomaji = ObjectUtils.getIfNull(kanjiHiraganaRomaji, KanjiHiraganaRomaji::new)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((Objects
					.equals(unicodeBlocks = getUnicodeBlocks(Util.toCharArray(s = ss.get(i))), Collections
							.singletonList(UnicodeBlock.BASIC_LATIN))
					|| Objects.equals(unicodeBlocks,
							Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.COMBINING_DIACRITICAL_MARKS)))
					&& !Util.matches(Util.matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("^\\w+\\s+\\d+$")), s))) {
				//
				kanjiHiraganaRomaji.romaji = s;
				//
			} else if (Objects.equals(unicodeBlocks, Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))) {
				//
				kanjiHiraganaRomaji.kanji = s;
				//
			} else if (Objects.equals(unicodeBlocks, Collections.singletonList(UnicodeBlock.HIRAGANA))) {
				//
				kanjiHiraganaRomaji.hiragana = s;
				//
			} // if
				//
		} // for
			//
		return kanjiHiraganaRomaji;
		//
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
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}