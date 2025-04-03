package org.apache.commons.lang3.function;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;

@Description("Online (${url!''})")
public class FuriganaOnlineFailableFunction implements FailableFunction<String, String, IOException> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public String apply(final String string) throws IOException {
		//
		final HttpURLConnection httpURLConnection = cast(HttpURLConnection.class,
				openConnection(testAndApply(Objects::nonNull, url, URL::new, null)));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		if (httpURLConnection != null) {
			//
			httpURLConnection.setRequestMethod("POST");
			//
			httpURLConnection.setDoOutput(true);
			//
		} // if
			//
		try (final OutputStream os = getOutputStream(httpURLConnection)) {
			//
			write(os, ObjectMapperUtil.writeValueAsBytes(objectMapper, Collections.singletonMap("input", string)));
			//
		} // try
			//
		try (final InputStream is = getInputStream(httpURLConnection)) {
			//
			return toString(
					get(cast(Map.class, ObjectMapperUtil.readValue(objectMapper, is, Object.class)), "furigana"));
			//
		} // try
			//
	}

	private static InputStream getInputStream(final URLConnection instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

	private static OutputStream getOutputStream(final URLConnection instance) throws IOException {
		return instance != null ? instance.getOutputStream() : null;
	}

	private static void write(final OutputStream instance, final byte[] bs) throws IOException {
		if (instance != null) {
			instance.write(bs);
		}
	}

	private static URLConnection openConnection(final URL instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Field[] fs = getDeclaredFields(instance.getClass());
		//
		Field f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = ArrayUtils.get(fs, i)) == null || !Objects.equals(f.getName(), "handler")) {
				//
				continue;
				//
			} // if
				//
			if (Narcissus.getField(instance, f) == null) {
				//
				return null;
				//
			} // if
				//
		} // for
			//
		return instance.openConnection();
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

}