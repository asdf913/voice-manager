package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
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
import org.apache.commons.lang3.tuple.Pair;
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
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) {
			//
			return createMap(is, encoding);
			//
		} // try
			//
	}

	@Nullable
	private static Map<String, String> createMap(@Nullable final InputStream is, final String encoding)
			throws IOException, CsvValidationException {
		//
		Map<String, String> map = null;
		//
		try (final Reader reader = testAndApply((a, b) -> a != null && b != null, is, encoding, InputStreamReader::new,
				(a, b) -> testAndApply(Objects::nonNull, a, InputStreamReader::new, null));
				final CSVReader csvReader = testAndApply(Objects::nonNull, reader, CSVReader::new, null)) {
			//
			String[] ss = null;
			//
			Pair<String, String> pair = null;
			//
			while ((ss = readNext(csvReader)) != null) {
				//
				if (map == null) {
					//
					map = new LinkedHashMap<>();
					//
					continue;
					//
				} // if
					//
				if ((pair = createPair(ss)) != null) {
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
	private static Pair<String, String> createPair(@Nullable final String[] ss) {
		//
		MutablePair<String, String> pair = null;
		//
		String s = null;
		//
		for (int i = 0; ss != null && i < ss.length; i++) {
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
				setLeft(pair, s);
				//
			} else if (i == 1) {
				//
				setRight(pair, s);
				//
			} // if
				//
		} // for
			//
		return pair;
		//
	}

	private static <L> void setLeft(@Nullable final MutablePair<L, ?> instance, final L left) {
		if (instance != null) {
			instance.setLeft(left);
		}
	}

	private static <R> void setRight(@Nullable final MutablePair<?, R> instance, final R right) {
		if (instance != null) {
			instance.setRight(right);
		}
	}

	@Nullable
	private static String[] readNext(@Nullable final CSVReader instance) throws IOException, CsvValidationException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(getClass(instance), "peekedLines")) == null) {
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
		return instance.readNext();
		//
	}

	@Nullable
	private static final InputStream openStream(@Nullable final URL instance) throws IOException {
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

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static Field getDeclaredField(@Nullable final Class<?> instance, @Nullable final String name)
			throws NoSuchFieldException {
		return instance != null && name != null ? instance.getDeclaredField(name) : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	@Nullable
	private static <T, R, E extends Throwable> R apply(@Nullable final FailableFunction<T, R, E> instance,
			final T value) throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate,
			@Nullable final T t, final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <T, R, U, E extends Throwable> R apply(@Nullable final FailableBiFunction<T, U, R, E> instance,
			@Nullable final T t, final U u) throws E {
		return instance != null ? instance.apply(t, u) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}