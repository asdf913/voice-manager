package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.github.toolfactory.narcissus.Narcissus;

public class HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private static final Logger LOG = LoggerFactory.getLogger(HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean.class);

	private String url, encoding = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, x -> new URL(x), null))) {
			//
			return createMap(is, encoding);
			//
		} // try
			//
	}

	private static Map<String, String> createMap(final InputStream is, final String encoding)
			throws UnsupportedEncodingException, MalformedURLException, IOException, CsvValidationException {
		//
		Map<String, String> map = null;
		//
		try (final Reader reader = testAndApply((a, b) -> a != null && b != null, is, encoding,
				(a, b) -> new InputStreamReader(a, b),
				(a, b) -> testAndApply(Objects::nonNull, a, x -> new InputStreamReader(x), null));
				final CSVReader csvReader = testAndApply(Objects::nonNull, reader, CSVReader::new, null)) {
			//
			String[] ss = null;
			//
			String s = null;
			//
			MutablePair<String, String> pair = null;
			//
			while ((ss = csvReader != null ? csvReader.readNext() : null) != null) {
				//
				if (map == null) {
					//
					map = new LinkedHashMap<>();
					//
					continue;
					//
				} // if
					//
				pair = null;
				//
				for (int i = 0; i < ss.length; i++) {
					//
					s = ss[i];
					//
					if (i == 0) {
						//
						if (pair == null) {
							//
							pair = MutablePair.of(s, null);
							//
						} // if
							//
						pair.setLeft(s);
						//
					} else if (i == 1) {
						//
						if (pair == null) {
							//
							pair = MutablePair.of(null, s);
							//
						} // if
							//
						pair.setRight(s);
						//
					} // if
						//
				} // for
					//
				if (pair != null) {
					//
					map.put(pair.getKey(), pair.getValue());
					//
				} // for
					//
			} // while
				//
		} // try
			//
		return map;
		//
	}

	@Nullable
	private static final InputStream openStream(final URL instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(getClass(instance), "handler")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.openStream();
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Field getDeclaredField(@Nullable final Class<?> instance, @Nullable final String name)
			throws NoSuchFieldException {
		return instance != null && name != null ? instance.getDeclaredField(name) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	private static <T, R, U, E extends Throwable> R apply(final FailableBiFunction<T, U, R, E> instance, final T t,
			final U u) throws E {
		return instance != null ? instance.apply(t, u) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}
