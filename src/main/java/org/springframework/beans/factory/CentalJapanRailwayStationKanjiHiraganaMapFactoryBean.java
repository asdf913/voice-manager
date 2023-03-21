package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

public class CentalJapanRailwayStationKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		Object object = ObjectMapperUtil.readValue(new ObjectMapper(),
				openStream(testAndApply(StringUtils::isNotBlank, url, URL::new, null)), Object.class);
		//
		final List<?> list = (object = get(object instanceof Map<?, ?> m ? m : null, "station")) instanceof List<?> l
				? l
				: null;
		//
		Map<String, String> map = null;
		//
		Map<?, ?> m = null;
		//
		for (int i = 0; list != null && i < list.size(); i++) {
			//
			if ((m = list.get(i) instanceof Map<?, ?> t ? t : null) == null
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			map.put(toString(m.get("name")), toString(m.get("kana")));
			//
		} // for
			//
		return map;
		//
	}

	private static <V> V get(@Nullable final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}