package org.apache.ibatis.session;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

public abstract class ConfigurationUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationUtil.class);

	private ConfigurationUtil() {
	}

	public static <T> T getMapper(final Configuration instance, final Class<T> type, final SqlSession sqlSession) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			// org.apache.ibatis.session.Configuration.mapperRegistry
			//
			Field field = getDeclaredField(Configuration.class, "mapperRegistry");
			//
			setAccessible(field, true);
			//
			final Object mapperRegistry = get(field, instance);
			//
			if (mapperRegistry == null) {
				//
				return null;
				//
			} // if
				//
				// org.apache.ibatis.binding.MapperRegistry.knownMappers
				//
			setAccessible(field = getDeclaredField(getClass(mapperRegistry), "knownMappers"), true);
			//
			final Object obj = get(field, mapperRegistry);
			//
			if (Objects.equals("java.util.concurrent.ConcurrentHashMap", getName(getClass(obj))) && type == null) {
				//
				return null;
				//
			} // if
				//
			final Map<?, ?> map = cast(Map.class, obj);
			//
			if (!containsKey(map, type) || get(map, type) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.getMapper(type, sqlSession);
		//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

}