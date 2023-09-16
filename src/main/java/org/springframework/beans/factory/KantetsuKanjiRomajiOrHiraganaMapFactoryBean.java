package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

public class KantetsuKanjiRomajiOrHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

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
						stream(map(
								new FailableStream<>(
										Util.stream(
												ElementUtil.select(
														testAndApply(Objects::nonNull,
																testAndApply(StringUtils::isNotBlank, url, URL::new,
																		null),
																x -> Jsoup.parse(x, 0), null),
														".list-stationguide-vt li a"))),
								KantetsuKanjiRomajiOrHiraganaMapFactoryBean::getKanjiHiraganaRomaji)),
						Objects::nonNull));
		//
	}

	private Map<String, String> getObject(final Stream<KanjiHiraganaRomaji> stream) {
		//
		return collect(stream, LinkedHashMap::new, (m, v) -> {
			//
			final Field[] fs = getDeclaredFields(Util.getClass(v));
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
				if ((f = fs[i]) == null) {
					//
					continue;
					//
				} // if
					//
				try {
					//
					s = Util.toString(FieldUtils.readField(f, v, true));
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
		}, Map::putAll);
		//
	}

	private static Field[] getDeclaredFields(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static <T, R> R collect(@Nullable final Stream<T> instance, final Supplier<R> supplier,
			final BiConsumer<R, ? super T> accumulator, final BiConsumer<R, R> combiner) {
		return instance != null ? instance.collect(supplier, accumulator, combiner) : null;
	}

	private static <T, R> FailableStream<R> map(@Nullable final FailableStream<T> instance,
			final FailableFunction<T, R, ?> mapper) {
		return instance != null && stream(instance) != null ? instance.map(mapper) : null;
	}

	private static <T> Stream<T> stream(@Nullable final FailableStream<T> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class KanjiHiraganaRomaji {

		private String kanji = null, hiragana = null, romaji = null;

	}

	@Nullable
	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final Element instance) throws IOException {
		//
		final String attr = ElementUtil.attr(instance, "href");
		//
		if (StringUtils.endsWith(attr, ".pdf")) {
			//
			return null;
			//
		} // if
			//
		return getKanjiHiraganaRomaji(attr);
		//
	}

	private static KanjiHiraganaRomaji getKanjiHiraganaRomaji(final String urlString) throws IOException {
		//
		final URL url = testAndApply(StringUtils::isNotBlank, urlString, URL::new, null);
		//
		final KanjiHiraganaRomaji kanjiHiraganaRomaji = new KanjiHiraganaRomaji();
		//
		kanjiHiraganaRomaji.romaji = StringUtils.substringAfterLast(getPath(url), '/');
		//
		final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull, url, x -> Jsoup.parse(x, 0), null),
				"h2.current");
		//
		if (elements != null && elements.size() > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Element element = elements != null ? elements.get(0) : null;
		//
		kanjiHiraganaRomaji.kanji = ElementUtil.text(element);
		//
		kanjiHiraganaRomaji.hiragana = ElementUtil.text(ElementUtil.nextElementSibling(element));
		//
		return kanjiHiraganaRomaji;
		//
	}

	private static String getPath(@Nullable final URL instance) {
		return instance != null ? instance.getPath() : null;
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(@Nullable final String string) {
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