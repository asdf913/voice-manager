package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.helger.css.ECSSVersion;

class JouYouKanjiGuiTest {

	private static Method METHOD_GET_CLASS, METHOD_GET_DECLARED_FIELDS, METHOD_GET_TYPE, METHOD_NAME,
			METHOD_GET_ECSS_VERSION_BY_MAJOR = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JouYouKanjiGui.class;
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELDS = clz.getDeclaredMethod("getDeclaredFields", Class.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_GET_ECSS_VERSION_BY_MAJOR = clz.getDeclaredMethod("getECSSVersionByMajor", ECSSVersion[].class,
				Number.class)).setAccessible(true);
		//
	}

	private JouYouKanjiGui instance = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<JouYouKanjiGui> constructor = JouYouKanjiGui.class.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} // if
			//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testActionPerformed() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.actionPerformed(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyTyped() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyTyped(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyPressed() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyPressed(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyReleased() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, new KeyEvent(new JLabel(), 0, 0, 0, 0, (char) 0));
			//
		});
		//
		// tfText
		//
		final JTextComponent tfText = new JTextField(" ");
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfText", tfText, true);
			//
		} // if
			//
		final KeyEvent keyEvent = new KeyEvent(tfText, 0, 0, 0, 0, (char) 0);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, keyEvent);
			//
		});
		//
		// jouYouKanJiList
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jouYouKanJiList", Unit.with(Collections.singletonList(" ")), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, keyEvent);
			//
		});
		//
	}

	private static void keyReleased(final KeyListener instance, final KeyEvent e) {
		if (instance != null) {
			instance.keyReleased(e);
		}
	}

	@Test
	void testSetEcssVersion() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field ecssVersion = JouYouKanjiGui.class.getDeclaredField("ecssVersion");
		//
		if (ecssVersion != null) {
			//
			ecssVersion.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, null));
		//
		// com.helger.css.ECSSVersion
		//
		final ECSSVersion ev = ECSSVersion.CSS10;
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, ev));
		//
		Assertions.assertSame(ev, get(ecssVersion, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, "2"));
		//
		Assertions.assertSame(ECSSVersion.CSS21, get(ecssVersion, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> setEcssVersion(instance, "CSS"));
		//
		// java.lang.Integer
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, 3));
		//
		Assertions.assertSame(ECSSVersion.CSS30, get(ecssVersion, instance));
		//
	}

	private static void setEcssVersion(final JouYouKanjiGui instance, final Object object)
			throws NoSuchFieldException, IllegalAccessException {
		if (instance != null) {
			instance.setEcssVersion(object);
		}
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testGetDeclaredFields() throws Throwable {
		//
		Assertions.assertNull(getDeclaredFields(null));
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELDS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field[]) {
				return (Field[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(null));
		//
	}

	private static Class<?> getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
	}

	private static String name(final Enum<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetECSSVersionByMajor() throws Throwable {
		//
		Assertions.assertNull(getECSSVersionByMajor(null, null));
		//
		Assertions.assertNull(getECSSVersionByMajor(new ECSSVersion[] { null }, null));
		//
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> getECSSVersionByMajor(new ECSSVersion[] { ECSSVersion.CSS30, ECSSVersion.CSS30 }, 3));
		//
	}

	private static IValue0<ECSSVersion> getECSSVersionByMajor(final ECSSVersion[] evs, final Number number)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ECSS_VERSION_BY_MAJOR.invoke(null, evs, number);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}