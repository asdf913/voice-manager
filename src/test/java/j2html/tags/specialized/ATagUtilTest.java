package j2html.tags.specialized;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

class ATagUtilTest {

	private static Method METHOD_GET_TITLE, METHOD_TEST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = ATagUtil.class;
		//
		(METHOD_GET_TITLE = clz.getDeclaredMethod("getTitleText", HtmlPage.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
	}

	@Test
	void testCreateByUrl() throws IOException {
		//
		Assertions.assertNotNull(ATagUtil.createByUrl(null));
		//
	}

	@Test
	void testGetTitleText() throws Throwable {
		//
		Assertions.assertNull(getTitleText(null));
		//
	}

	private static String getTitleText(final HtmlPage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TITLE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}