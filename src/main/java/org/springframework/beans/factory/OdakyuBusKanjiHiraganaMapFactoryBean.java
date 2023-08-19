package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.StringTemplateLoaderUtil;
import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import io.github.toolfactory.narcissus.Narcissus;

public class OdakyuBusKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private static final Logger LOG = LoggerFactory.getLogger(OdakyuBusKanjiHiraganaMapFactoryBean.class);

	private String url = null;

	private Configuration configuration = null;

	private String urlTemplate = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	public void setUrlTemplate(final String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	private Configuration getConfiguration() {
		//
		if (configuration == null) {
			//
			final StringTemplateLoader stl = new StringTemplateLoader();
			//
			StringTemplateLoaderUtil.putTemplate(stl, "", urlTemplate);
			//
			(configuration = new Configuration(Configuration.getVersion())).setTemplateLoader(stl);
			//
		} // if
			//
		return configuration;
		//
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		List<?> items = null;
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) {
			//
			items = cast(List.class,
					testAndApply(OdakyuBusKanjiHiraganaMapFactoryBean::containsKey,
							cast(Map.class,
									testAndApply(Objects::nonNull, is,
											x -> ObjectMapperUtil.readValue(objectMapper, x, Object.class), null)),
							"items", MapUtils::getObject, null));
			//
		} // try
			//
		return getObject(getConfiguration(), items, objectMapper);
		//
	}

	@Nullable
	private static Map<String, String> getObject(final Configuration configuration, @Nullable final List<?> items,
			final ObjectMapper objectMapper) throws IOException, TemplateException {
		//
		Map<?, ?> map = null;
		//
		Number count = 0;
		//
		AtomicReference<Map<String, String>> result = null;
		//
		for (int i = 0; items != null && i < items.size(); i++) {
			//
			if (!containsKey(map = cast(Map.class, items.get(i)), "code")
					|| (count = cast(Number.class,
							testAndApply(OdakyuBusKanjiHiraganaMapFactoryBean::containsKey, map, "count",
									OdakyuBusKanjiHiraganaMapFactoryBean::get, null))) == null
					|| count.intValue() < 1) {
				//
				continue;
				//
			} // if
				//
			try (final Writer writer = new StringWriter()) {
				//
				TemplateUtil.process(ConfigurationUtil.getTemplate(configuration, ""), map, writer);
				//
				perform(result = ObjectUtils.getIfNull(result, AtomicReference::new),
						createMap(Util.toString(writer), objectMapper));
				//
			} // try
				//
		} // for
			//
		return get(result);
		//
	}

	@Nullable
	private static <V> V get(@Nullable final AtomicReference<V> instance) {
		return instance != null ? instance.get() : null;
	}

	private static void perform(final AtomicReference<Map<String, String>> ar, @Nullable final Map<?, ?> map) {
		//
		final Map<String, String> result = ObjectUtils.getIfNull(get(ar), LinkedHashMap::new);
		//
		if (map != null && map.entrySet() != null && map.entrySet().iterator() != null) {
			//
			for (final Entry<?, ?> entry : map.entrySet()) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				checkIfKeyExistsAndDifferenceValue(result, entry);
				//
				put(result, Util.toString(getKey(entry)), Util.toString(getValue(entry)));
				//
			} // for
				//
		} // if
			//
		set(ar, result);
		//
	}

	private static <V> void set(@Nullable final AtomicReference<V> instance, final V value) {
		if (instance != null) {
			instance.set(value);
		}
	}

	private static void checkIfKeyExistsAndDifferenceValue(final Map<?, ?> map, final Entry<?, ?> entry) {
		//
		final Object key = getKey(entry);
		//
		if (containsKey(map, key) && !Objects.equals(get(map, key), getValue(entry))) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
	}

	@Nullable
	private static <K> K getKey(@Nullable final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	@Nullable
	private static <V> V getValue(@Nullable final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	@Nullable
	private static <T, R, E extends Throwable> R apply(@Nullable final FailableFunction<T, R, E> instance,
			@Nullable final T value) throws E {
		return instance != null ? instance.apply(value) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	@Nullable
	private static Map<String, String> createMap(final String url, final ObjectMapper objectMapper) throws IOException {
		//
		List<?> items = null;
		//
		try (final InputStream is = openStream(testAndApply(StringUtils::isNotEmpty, url, URL::new, null))) {
			//
			items = cast(List.class,
					testAndApply(OdakyuBusKanjiHiraganaMapFactoryBean::containsKey,
							cast(Map.class, testAndApply(Objects::nonNull, is,
									x -> ObjectMapperUtil.readValue(
											objectMapper != null ? objectMapper : new ObjectMapper(), x, Object.class),
									null)),
							"items", MapUtils::getObject, null));
			//
		} // try
			//
		return createMap(items);
		//
	}

	@Nullable
	private static Map<String, String> createMap(@Nullable final List<?> items) {
		//
		Map<?, ?> map = null;
		//
		Map<String, String> result = null;
		//
		String name, ruby = null;
		//
		for (int i = 0; items != null && i < items.size(); i++) {
			//
			if ((map = cast(Map.class, items.get(i))) == null || !and(map::containsKey, "name", "ruby")
					|| Objects.equals(name = Util.toString(get(map, "name")), ruby = Util.toString(get(map, "ruby")))
					|| !isAllCharacterInSameUnicodeBlock(name, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
				//
				continue;
				//
			} // if
				//
			put(result = ObjectUtils.getIfNull(result, LinkedHashMap::new), name, ruby);
			//
		} // for
			//
		return result;
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
				testAndAccept((a, b) -> b != null && !contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						OdakyuBusKanjiHiraganaMapFactoryBean::add);
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

	private static boolean contains(@Nullable final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <E> void add(@Nullable final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, @Nullable final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <T> boolean and(final Predicate<T> predicate, final T a, final T b) {
		return test(predicate, a) && test(predicate, b);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
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
	private static <V> V get(@Nullable final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Nullable
	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			final U u, final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse)
			throws E {
		return test(predicate, t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <T, U, R> R apply(@Nullable final BiFunction<T, U, R> instance, @Nullable final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static boolean containsKey(@Nullable final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

}
