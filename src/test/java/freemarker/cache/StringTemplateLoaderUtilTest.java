package freemarker.cache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class StringTemplateLoaderUtilTest {

	private static Method METHOD_SET_ACCESSIBLE, METHOD_GET = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = StringTemplateLoaderUtil.class;
		//
		(METHOD_SET_ACCESSIBLE = clz.getDeclaredMethod("setAccessible", AccessibleObject.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
	}

	@Test
	void testPutTemplate() {
		//
		Assertions.assertDoesNotThrow(() -> StringTemplateLoaderUtil.putTemplate(new StringTemplateLoader(), null, ""));
		//
		Assertions.assertDoesNotThrow(() -> StringTemplateLoaderUtil.putTemplate(
				cast(StringTemplateLoader.class, Narcissus.allocateInstance(StringTemplateLoader.class)), "", ""));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testSetAccessible() {
		//
		Assertions.assertDoesNotThrow(() -> setAccessible(null, true));
		//
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) throws Throwable {
		try {
			METHOD_SET_ACCESSIBLE.invoke(null, instance, flag);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null, null));
		//
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}