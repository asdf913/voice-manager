package org.springframework.beans.factory;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class IpaMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private static Logger LOG = LoggerFactory.getLogger(IpaMultimapFactoryBean.class);

	private Unit<Multimap<String, String>> multimap = null;

	private ObjectMapper objectMapper = null;

	private String url = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		Multimap<String, String> multimap = IValue0Util.getValue0(this.multimap);
		//
		final ObjectMapper objectMapper = getObjectMapper();
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (multimap == null) {
			//
			try {
				//
				multimap = IValue0Util.getValue0(this.multimap = getMultimapUnitFromJson(objectMapper, testAndApply(
						IpaMultimapFactoryBean::exists, resource, InputStreamSourceUtil::getInputStream, null)));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
		if (multimap == null) {
			//
			try (final InputStream is = openStream(testAndApply(StringUtils::isNotBlank, url, URL::new, null))) {
				//
				multimap = IValue0Util.getValue0(this.multimap = getMultimapUnitFromJson(objectMapper,
						openStream(testAndApply(StringUtils::isNotBlank, url, URL::new, null))));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
		return multimap;
		//
	}

	private static Unit<Multimap<String, String>> getMultimapUnitFromJson(final ObjectMapper objectMapper,
			final InputStream is) throws IOException {
		//
		final Object obj = objectMapper != null && is != null ? objectMapper.readValue(is, Object.class) : null;
		//
		if (is == null) {
			//
			return null;
			//
		} else if (obj == null) {
			//
			return Unit.with(null);
			//
		} else if (obj instanceof Map) {
			//
			final Set<Entry<String, String>> entrySet = entrySet((Map) obj);
			//
			Multimap<String, String> multimap = null;
			//
			if (iterator(entrySet) != null) {
				//
				List<String> list = null;
				//
				for (final Entry<?, ?> en : entrySet) {
					//
					list = testAndApply(Objects::nonNull, StringUtils.split(toString(getValue(en)), ","),
							Arrays::asList, null);
					//
					for (int i = 0; list != null && i < list.size(); i++) {
						//
						list.set(i, StringUtils.trim(list.get(i)));
						//
					} // for
						//
					putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), toString(getKey(en)),
							list);
					//
				} // for
					//
			} // if
				//
			return Unit.with(multimap);
			//
		} // if
			//
		throw new IllegalArgumentException(toString(getClass(obj)));
		//
	}

	private static boolean exists(final Resource instance) {
		return instance != null && instance.exists();
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static <K, V> void putAll(final Multimap<K, V> instance, final K key, final Iterable<? extends V> values) {
		if (instance != null) {
			instance.putAll(key, values);
		}
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	private static InputStream openStream(final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}