package org.springframework.beans.factory;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JlptLevelListFactoryBeanTest {

	private static final String EMPTY = "";

	private JlptLevelListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JlptLevelListFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(List.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testSetTimeout() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field timeout = JlptLevelListFactoryBean.class.getDeclaredField("timeout");
		//
		if (timeout != null) {
			//
			timeout.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, null));
		//
		Assertions.assertNull(get(timeout, instance));
		//
		// java.time.Duration
		//
		final Duration duration = Duration.ZERO;
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, duration));
		//
		Assertions.assertSame(duration, get(timeout, instance));
		//
		// java.lang.Number
		//
		final long l = 1;
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, Long.valueOf(l)));
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, Long.toString(l)));
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, EMPTY));
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, " "));
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> setTimeout(instance, String.format("PT%1$sS", l)));
		//
		Assertions.assertEquals(Duration.ofMillis(l * 1000), get(timeout, instance));
		//
	}

	private static void setTimeout(final JlptLevelListFactoryBean instance, final Object object) {
		if (instance != null) {
			instance.setTimeout(object);
		}
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testSetValues() throws Throwable {
		//
		final Field values = JlptLevelListFactoryBean.class.getDeclaredField("values");
		//
		if (values != null) {
			//
			values.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> setValues(instance, null));
		//
		Assertions.assertEquals("[[null]]", Util.toString(get(values, instance)));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setValues(instance, EMPTY));
		//
		Assertions.assertEquals("[[null]]", Util.toString(get(values, instance)));
		//
		// java.util.List
		//
		Assertions.assertDoesNotThrow(() -> setValues(instance, String.format("[%1$s]", EMPTY)));
		//
		Assertions.assertEquals("[null]", Util.toString(get(values, instance)));
		//
		// java.lang.Number
		//
		final Integer zero = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> setValues(instance, Util.toString(zero)));
		//
		Assertions.assertEquals(String.format("[[%1$s]]", zero), Util.toString(get(values, instance)));
		///
		Assertions.assertDoesNotThrow(() -> setValues(instance, String.format("[%1$s]", zero)));
		//
		Assertions.assertEquals(String.format("[[%1$s]]", zero), Util.toString(get(values, instance)));
		//
		// java.util.Map
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> setValues(instance, String.format("{\"%1$s\":%1$s}", zero)));
		//
		// Invalid Format
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> setValues(instance, String.format("{%1$s:%1$s}", zero)));
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(RuntimeException.class, String.join("\n",
					"{localizedMessage=org.opentest4j.AssertionFailedError: Unexpected character ('0' (code 48)): was expecting double-quote to start field name",
					" at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 2] ==> Unexpected exception thrown: com.fasterxml.jackson.core.JsonParseException: Unexpected character ('0' (code 48)): was expecting double-quote to start field name",
					" at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 2], message=org.opentest4j.AssertionFailedError: Unexpected character ('0' (code 48)): was expecting double-quote to start field name",
					" at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 2] ==> Unexpected exception thrown: com.fasterxml.jackson.core.JsonParseException: Unexpected character ('0' (code 48)): was expecting double-quote to start field name",
					" at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 2]}"),
					() -> setValues(instance, String.format("{%1$s:%1$s}", zero)));
			//
		} // if
			//
	}

	private static void setValues(final JlptLevelListFactoryBean instance, final String string) {
		if (instance != null) {
			instance.setValues(string);
		}
	}

}