package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.reflect.Reflection;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class JouYouKanjiGuiTest {

	private static Class<?> CLASS_OBJECT_MAP, CLASS_IH = null;

	private static Method METHOD_GET_CLASS, METHOD_GET_DECLARED_FIELDS, METHOD_GET_TYPE, METHOD_NAME,
			METHOD_GET_ECSS_VERSION_BY_MAJOR, METHOD_ADD_JOU_YOU_KAN_JI_SHEET, METHOD_CAST,
			METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY, METHOD_SET_PREFERRED_WIDTH,
			METHOD_GET_PREFERRED_SIZE = null;

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
		(METHOD_ADD_JOU_YOU_KAN_JI_SHEET = clz.getDeclaredMethod("addJouYouKanJiSheet",
				CLASS_OBJECT_MAP = Class.forName("org.springframework.context.support.JouYouKanjiGui$ObjectMap"),
				String.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY = clz.getDeclaredMethod(
				"getCSSDeclarationByAttributeAndCssProperty", Element.class, String.class, ECSSVersion.class,
				String.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_PREFERRED_SIZE = clz.getDeclaredMethod("getPreferredSize", Component.class)).setAccessible(true);
		//
		CLASS_IH = Class.forName("org.springframework.context.support.JouYouKanjiGui$IH");
		//
	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Component) {
				//
				if (Objects.equals(methodName, "getPreferredSize")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

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

	@Test
	void testAddJouYouKanJiSheet() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> addJouYouKanJiSheet(null, null));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> addJouYouKanJiSheet(createObjectMap(), null));
		//
	}

	@Test
	void testObjectMap() throws Throwable {
		//
		final Object objectMap = createObjectMap();
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.setObject(org.springframework.context.support.JouYouKanjiGui$ObjectMap,java.lang.Class,java.lang.Object)
		//
		Method method = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", CLASS_OBJECT_MAP, Class.class, Object.class)
				: null;
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, null, null, null));
		//
		Assertions.assertNull(invoke(method, null, objectMap, null, null));
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.getObject(org.springframework.context.support.JouYouKanjiGui$ObjectMap,java.lang.Class)
		//
		if ((method = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", CLASS_OBJECT_MAP, Class.class)
				: null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, objectMap, null));
		//
		final Method m = method;
		//
		Assertions.assertThrows(IllegalStateException.class, () -> invoke(m, null, objectMap, String[].class));
		//
	}

	@Test
	void testIh() throws Throwable {
		//
		final InvocationHandler ih = createInvocationHandler();
		//
		Assertions.assertThrows(Throwable.class, () -> ih.invoke(null, null, null));
		//
		final Object objectMap = createObjectMap();
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.getObject(java.lang.Class)
		//
		final Method getObject = CLASS_OBJECT_MAP != null ? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
				: null;
		//
		Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, getObject, null));
		//
		Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, getObject, new Object[] {}));
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.setObject(java.lang.Class,java.lang.Object)
		//
		final Method setObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
				: null;
		//
		Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, setObject, null));
		//
		Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, setObject, new Object[] {}));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.containsKey(java.lang.Map,java.lang.Object)
		//
		Method method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("containsKey", Map.class, Object.class) : null;
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, invoke(method, null, null, null));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.isArray(java.lang.Class)
		//
		if ((method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("isArray", OfField.class) : null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, invoke(method, null, (Object) null));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.getSimpleName(java.lang.Class)
		//
		if ((method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("getSimpleName", Class.class) : null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, (Object) null));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.put(java.util.Map,java.lang.Object,java.lang.Object)
		//
		if ((method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("put", Map.class, Object.class, Object.class)
				: null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, null, null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return method != null ? method.invoke(instance, args) : null;
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static InvocationHandler createInvocationHandler() throws Throwable {
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		return cast(InvocationHandler.class, constructor != null ? constructor.newInstance() : null);
		//
	}

	private static Object createObjectMap() throws Throwable {
		//
		return Reflection.newProxy(CLASS_OBJECT_MAP, createInvocationHandler());
		//
	}

	private static void addJouYouKanJiSheet(final Object objectMap, final String sheetName) throws Throwable {
		try {
			METHOD_ADD_JOU_YOU_KAN_JI_SHEET.invoke(null, objectMap, sheetName);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetCSSDeclarationByAttributeAndCssProperty() throws Throwable {
		//
		Assertions.assertNull(getCSSDeclarationByAttributeAndCssProperty(null, null, null, null));
		//
	}

	private static CSSDeclaration getCSSDeclarationByAttributeAndCssProperty(final Element element,
			final String attribute, final ECSSVersion ecssVersion, final String cssProperty) throws Throwable {
		try {
			final Object obj = METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY.invoke(null, element, attribute,
					ecssVersion, cssProperty);
			if (obj == null) {
				return null;
			} else if (obj instanceof CSSDeclaration) {
				return (CSSDeclaration) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth()
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, null));
		//
		// java.awt.Component.getPreferredSize() return null
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(new MH());
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, Arrays.asList(null, cast(Component.class, instance))));
		//
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredSize() throws Throwable {
		//
		Assertions.assertNull(getPreferredSize(null));
		//
	}

	private static Dimension getPreferredSize(final Component instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_SIZE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Dimension) {
				return (Dimension) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}