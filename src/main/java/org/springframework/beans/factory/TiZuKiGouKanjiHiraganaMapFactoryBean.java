package org.springframework.beans.factory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

/**
 * https://web.archive.org/web/20211126172558/http://www.gsi.go.jp/KIDS/map-sign-tizukigou-h14kigou-itiran.htm
 */
public class TiZuKiGouKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		return Util.collect(
				Util.map(
						Util.stream(ElementUtil.select(testAndApply(Objects::nonNull,
								testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null),
								x -> Jsoup.parse(x, 0), null), "ruby")),
						x -> Pair.of(text(ElementUtil.select(x, "rb")), text(ElementUtil.select(x, "rt")))),
				LinkedHashMap::new, (a, b) -> Util.put(a, Util.getKey(b), Util.getValue(b)), Util::putAll);
		//
	}

	@Nullable
	private static String text(@Nullable final Elements instance) {
		return instance != null ? instance.text() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}