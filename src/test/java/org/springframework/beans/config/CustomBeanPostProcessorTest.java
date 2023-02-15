package org.springframework.beans.config;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import com.google.common.base.Objects;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class CustomBeanPostProcessorTest {

	private static Method METHOD_GET_NAME, METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_IS_STATIC, METHOD_GET = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = CustomBeanPostProcessor.class;
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> properties = null;

		private Map<Object, Object> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equal(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					return containsKey(getProperties(), args[0]);
					//
				} else if (Objects.equal(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProperties(), args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean containsKey(final Map<?, ?> instance, final Object key) {
			return instance != null && instance.containsKey(key);
		}

	}

	@Test
	void testPostProcessBeforeInitialization() throws Throwable {
		//
		final CustomBeanPostProcessor instance = new CustomBeanPostProcessor();
		//
		Assertions.assertNull(instance.postProcessBeforeInitialization(null, null));
		//
		JFrame jFrame = null;
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			jFrame = new JFrame();
			//
		} else {
			//
			final Object object = Narcissus.allocateInstance(JFrame.class);
			//
			if (object instanceof JFrame) {
				//
				jFrame = (JFrame) object;
				//
			} // if
				//
		} // if
			//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		// javax.swing.WindowConstants.EXIT_ON_CLOSE
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		final Field defaultCloseOperation = CustomBeanPostProcessor.class.getDeclaredField("defaultCloseOperation");
		//
		if (defaultCloseOperation != null) {
			//
			defaultCloseOperation.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(WindowConstants.EXIT_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		instance.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		// null
		//
		instance.setDefaultCloseOperation(null);
		//
		Assertions.assertNull(get(defaultCloseOperation, instance));
		//
		// java.lang.String
		//
		instance.setDefaultCloseOperation(Integer.toString(0));
		//
		Assertions.assertEquals(WindowConstants.DO_NOTHING_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> instance.setDefaultCloseOperation(StringUtils.wrap(Integer.toString(0), "\"")));
		//
		// EXIT_ON_CLOSE
		//
		instance.setDefaultCloseOperation("EXIT_ON_CLOSE");
		//
		Assertions.assertEquals(WindowConstants.EXIT_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		// java.lang.Boolean
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setDefaultCloseOperation(Boolean.TRUE));
		//
		// org.springframework.core.env.PropertyResolver
		//
		final IH ih = new IH();
		//
		instance.setEnvironment(Reflection.newProxy(Environment.class, ih));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		final String empty = "";
		//
		ih.getProperties().put(StringUtils.joinWith(".", JFrame.class.getName(), "title"), empty);
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
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

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
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
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(Object.class.getDeclaredMethod("toString")));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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