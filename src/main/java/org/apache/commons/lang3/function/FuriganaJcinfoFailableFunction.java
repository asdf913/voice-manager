package org.apache.commons.lang3.function;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

import io.github.toolfactory.narcissus.Narcissus;

public class FuriganaJcinfoFailableFunction implements FailableFunction<String, String, Exception> {

	// TODO https://www.jcinfo.net/en/tools/kana?text=%E8%B4%85%E6%B2%A2
	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String apply(final String input) throws Exception {
		//
		return collect(map(stream(ElementUtil.children(testAndApply(x -> IterableUtils.size(x) == 1,
				ElementUtil.children(testAndApply(
						x -> IterableUtils
								.size(x) == 1,
						getElementsByClass(
								testAndApply(Objects::nonNull,
										toURL(build(addParameter(
												testAndApply(StringUtils::isNotBlank, url, URIBuilder::new, null),
												"text", input))),
										x -> Jsoup.parse(x, 0), null),
								"_result-ruby"),
						x -> IterableUtils.get(x, 0), null)),
				x -> IterableUtils.get(x, 0), null))), x -> ElementUtil.html(x)), Collectors.joining());
		//
	}

	private static URIBuilder addParameter(final URIBuilder instance, final String param, final String value) {
		return instance != null && param != null ? instance.addParameter(param, value) : instance;
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector) {
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
	}

	private static Field[] getDeclaredFields(final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: instance;
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static URI build(final URIBuilder instance) throws URISyntaxException {
		return instance != null ? instance.build() : null;
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null && instance.isAbsolute() ? instance.toURL() : null;
	}

	private static Elements getElementsByClass(final Element instance, final String className) {
		//
		final Collection<Field> fs = collect(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(getClass(instance)), Arrays::stream, null),
						f -> Objects.equals(getName(f), "childNodes")),
				Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getObjectField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null && className != null ? instance.getElementsByClass(className) : null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}