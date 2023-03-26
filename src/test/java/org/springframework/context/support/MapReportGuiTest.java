package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.AttributeAccessor;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class MapReportGuiTest {

	private static Method METHOD_CAST, METHOD_IS_ALL_ATTRIBUTES_MATCHED, METHOD_GET_CLASS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = MapReportGui.class;
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ALL_ATTRIBUTES_MATCHED = clz.getDeclaredMethod("isAllAttributesMatched", Map.class,
				AttributeAccessor.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private String[] beanDefinitionNames = null;

		private BeanDefinition beanDefinition;

		private String beanClassName = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private Boolean hasAttribute = null;

		private Object attribute = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinitionNames")) {
					//
					return beanDefinitionNames;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof ConfigurableListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinition")) {
					//
					return beanDefinition;
					//
				} // if
					//
			} else if (proxy instanceof BeanDefinition) {
				//
				if (Objects.equals(methodName, "getBeanClassName")) {
					//
					return beanClassName;
					//
				} // if
					//
			} else if (proxy instanceof AttributeAccessor) {
				//
				if (Objects.equals(methodName, "hasAttribute")) {
					//
					return hasAttribute;
					//
				} else if (Objects.equals(methodName, "getAttribute")) {
					//
					return attribute;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private MapReportGui instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<MapReportGui> constructor = MapReportGui.class.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = cast(MapReportGui.class, Narcissus.allocateInstance(MapReportGui.class));
			//
		} // if
			//
		ih = new IH();
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.afterPropertiesSet();
				//
			} //
				//
		});
		//
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent("", 0, null)));
		//
		final JTextComponent tfAttributeJson = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfAttributeJson", tfAttributeJson, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		tfAttributeJson.setText("{}");
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		final DefaultTableModel dtm = new DefaultTableModel();
		//
		dtm.addRow(new Object[] {});
		//
		FieldUtils.writeDeclaredField(instance, "dtm", dtm, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (instance != null) {
			//
			instance.postProcessBeanFactory(Reflection.newProxy(ConfigurableListableBeanFactory.class, ih));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (ih != null) {
			//
			ih.beanDefinitionNames = new String[] { null };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (ih != null) {
			//
			ih.beanDefinition = Reflection.newProxy(BeanDefinition.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllAttributesMatched() throws Throwable {
		//
		Assertions.assertTrue(isAllAttributesMatched(null, null));
		//
		Assertions.assertTrue(isAllAttributesMatched(Collections.singletonMap(null, null), null));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		final AttributeAccessor aa = Reflection.newProxy(AttributeAccessor.class, ih);
		//
		if (ih != null) {
			//
			ih.hasAttribute = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(isAllAttributesMatched(Collections.singletonMap(null, null), aa));
		//
		if (ih != null) {
			//
			ih.hasAttribute = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(Collections.singletonMap(null, null), aa));
		//
		Assertions.assertFalse(isAllAttributesMatched(Collections.singletonMap(null, ""), aa));
		//
	}

	private static boolean isAllAttributesMatched(final Map<?, ?> attributes, final AttributeAccessor aa)
			throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_ATTRIBUTES_MATCHED.invoke(null, attributes, aa);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}