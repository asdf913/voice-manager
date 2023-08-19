package freemarker.cache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

public class StringTemplateLoaderUtil {

	private static final Logger LOG = LoggerFactory.getLogger(StringTemplateLoaderUtil.class);

	private StringTemplateLoaderUtil() {
	}

	public static void putTemplate(final StringTemplateLoader instance, final String name,
			final String templateContent) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			final Field field = StringTemplateLoader.class.getDeclaredField("templates");
			//
			setAccessible(field, true);
			//
			if (get(field, instance) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		if (name != null && templateContent != null) {
			//
			instance.putTemplate(name, templateContent);
			//
		} // if
			//
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

}