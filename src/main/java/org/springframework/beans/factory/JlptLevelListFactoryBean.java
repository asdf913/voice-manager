package org.springframework.beans.factory;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

public class JlptLevelListFactoryBean implements FactoryBean<List<String>> {

	private String url = null;

	private Duration timeout = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTimeout(final Duration timeout) {
		this.timeout = timeout;
	}

	@Override
	public List<String> getObject() throws Exception {
		//
		return toList(map(
				stream(select(testAndApply(x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x), "http", "https"),
						testAndApply(StringUtils::isNotBlank, url, URL::new, null),
						x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null), ".thLeft[scope='col']")),
				ElementUtil::text));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		//
		return instance != null ? instance.toList() : null;
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static Elements select(final Element instance, final String cssQuery) {
		return instance != null ? instance.select(cssQuery) : null;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static Long toMillis(final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}