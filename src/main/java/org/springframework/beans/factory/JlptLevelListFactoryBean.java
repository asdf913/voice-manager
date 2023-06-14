package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.ElementUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

public class JlptLevelListFactoryBean implements FactoryBean<List<String>> {

	private String url = null;

	private Duration timeout = null;

	private Unit<List<String>> values = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTimeout(@Nullable final Object timeout) {
		//
		Unit<Duration> value = null;
		//
		if (timeout == null) {
			//
			value = Unit.with(null);
			//
		} else if (timeout instanceof Duration duration) {
			//
			value = Unit.with(duration);
			//
		} else if (timeout instanceof Number number) {
			//
			value = Unit.with(Duration.ofMillis(longValue(number, 0)));
			//
		} // if
			//
		if (value != null) {
			//
			this.timeout = IValue0Util.getValue0(value);
			//
			return;
			//
		} // if
			//
		final String string = Util.toString(timeout);
		//
		final Long l = valueOf(string);
		//
		if (l != null) {
			//
			setTimeout(l);
			//
		} else if (StringUtils.isNotBlank(string)) {
			//
			setTimeout(Duration.parse(string));
			//
		} // if
			//
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	public void setValues(final String string) {
		//
		try {
			//
			final Object object = ObjectMapperUtil.readValue(new ObjectMapper(), string, Object.class);
			//
			if (object instanceof Map) {
				//
				throw new IllegalArgumentException();
				//
			} else if (object instanceof Iterable) {
				//
				final Iterable<?> iterable = (Iterable<?>) object;
				//
				if (iterable != null) {
					//
					List<String> list = null;
					//
					for (final Object obj : iterable) {
						//
						add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(obj));
						//
					} // for
						//
					this.values = Unit.with(list);
					//
				} // if
					//
			} else {
				//
				this.values = Unit.with(Collections.singletonList(Util.toString(object)));
				//
			} // if
				//
		} catch (final JsonProcessingException e) {
			//
			this.values = Unit.with(Collections.singletonList(string));
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	private static <E> void add(@Nullable final Collection<E> items, @Nullable final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Nullable
	private static Long valueOf(@Nullable final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Long.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Override
	public List<String> getObject() throws Exception {
		//
		if (values != null) {
			//
			return IValue0Util.getValue0(values);
			//
		} // if
			//
		return getObjectByUrl(url, timeout);
		//
	}

	@Nullable
	private static List<String> getObjectByUrl(final String url, final Duration timeout) throws IOException {
		//
		return toList(
				map(stream(
						ElementUtil.select(
								testAndApply(
										x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x),
												ProtocolUtil.getAllowProtocols()),
										testAndApply(StringUtils::isNotBlank, url, URL::new, null),
										x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null),
								".thLeft[scope='col']")),
						ElementUtil::text));
		//
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static <T, R> Stream<R> map(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	@Nullable
	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}