package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.Character.UnicodeBlock;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

import io.github.toolfactory.narcissus.Narcissus;

public class HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private static final Logger LOG = LoggerFactory.getLogger(HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean.class);

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("URL")
	private String url = null;

	private String encoding = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) {
			//
			return createMap(is, forName(encoding));
			//
		} // try
			//
	}

	@Nullable
	private static Charset forName(@Nullable final String instance) {
		//
		try {
			//
			return StringUtils.isNotBlank(instance) ? Charset.forName(instance) : null;
			//
		} catch (final Exception e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	private static Map<String, String> createMap(@Nullable final InputStream is, @Nullable final Charset encoding)
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
			String key, value = null;
			//
			while ((ss = CSVReaderUtil.readNext(csvReader)) != null) {
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
					if (Objects.equals(key = Util.getKey(pair), value = pair.getValue())
							|| !isAllCharacterInSameUnicodeBlock(key, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
						//
						continue;
						//
					} // if
						//
					Util.put(map, key, value);
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

	private static boolean isAllCharacterInSameUnicodeBlock(@Nullable final String string,
			final UnicodeBlock unicodeBlock) {
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
			return Objects.equals(Collections.singletonList(unicodeBlock), unicodeBlocks);
			//
		} // if
			//
		return true;
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
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
			if (i == 1) {
				//
				if (pair == null) {
					//
					pair = MutablePair.of(s, null);
					//
				} // if
					//
				MutablePairUtil.setLeft(pair, s);
				//
			} else if (i == 2) {
				//
				MutablePairUtil.setRight(pair, s);
				//
			} // if
				//
		} // for
			//
		return pair;
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
			if (Narcissus.getField(instance, getDeclaredField(Util.getClass(instance), "handler")) == null) {
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
	private static Field getDeclaredField(@Nullable final Class<?> instance, @Nullable final String name)
			throws NoSuchFieldException {
		return instance != null && name != null ? instance.getDeclaredField(name) : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate,
			@Nullable final T t, @Nullable final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}