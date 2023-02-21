package org.springframework.beans.config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class CustomBeanPostProcessorTest {

	private static Method METHOD_GET_NAME, METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_IS_STATIC, METHOD_GET,
			METHOD_CAST, METHOD_TEST, METHOD_IS_ANNOTATION_PRESENT, METHOD_GET_ANNOTATION, METHOD_SET_TITLE,
			METHOD_VALUE_OF = null;

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
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ANNOTATION_PRESENT = clz.getDeclaredMethod("isAnnotationPresent", AnnotatedElement.class,
				Class.class)).setAccessible(true);
		//
		(METHOD_GET_ANNOTATION = clz.getDeclaredMethod("getAnnotation", AnnotatedElement.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_SET_TITLE = clz.getDeclaredMethod("setTitle", Frame.class, Title.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> properties = null;

		private String value = null;

		private Map<Object, Object> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					return containsKey(getProperties(), args[0]);
					//
				} else if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProperties(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof Title) {
				//
				if (Objects.equals(methodName, "value")) {
					//
					return value;
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

	private static class MH implements MethodHandler {

		private Dimension preferredSize = null;

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
					return preferredSize;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private CustomBeanPostProcessor instance = null;

	private IH ih = null;

	private Environment environment = null;

	private JFrame jFrame = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new CustomBeanPostProcessor();
		//
		environment = Reflection.newProxy(Environment.class, ih = new IH());
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
	}

	@Test
	void testPostProcessBeforeInitialization() throws Throwable {
		//
		Assertions.assertNull(postProcessBeforeInitialization(instance, null, null));
		//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
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
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		instance.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
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
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
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
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		// java.lang.Boolean
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setDefaultCloseOperation(Boolean.TRUE));
		//
		// org.springframework.core.env.PropertyResolver
		//
		setEnvironment(instance, environment);
		//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		// title
		//
		final String empty = "";
		//
		if (ih != null) {
			//
			ih.getProperties().put(StringUtils.joinWith(".", JFrame.class.getName(), "title"), empty);
			//
		} // if
			//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		if (ih != null) {
			//
			ih.getProperties().put("java.awt.Frame.title", " ");
			//
		} // if
			//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		// defaultCloseOperation
		//
		if (ih != null) {
			//
			ih.getProperties().put(StringUtils.joinWith(".", JFrame.class.getName(), "defaultCloseOperation"),
					Integer.toString(WindowConstants.EXIT_ON_CLOSE));
			//
		} // if
			//
		Assertions.assertSame(jFrame, postProcessBeforeInitialization(instance, jFrame, null));
		//
		instance.setDefaultCloseOperation(null);
		//
		if (ih != null) {
			//
			ih.getProperties().put(StringUtils.joinWith(".", JFrame.class.getName(), "defaultCloseOperation"), "A");
			//
		} // if
			//
		Assertions.assertThrows(RuntimeException.class,
				() -> postProcessBeforeInitialization(instance, Narcissus.allocateInstance(JFrame.class), null));
		//
	}

	private static Object postProcessBeforeInitialization(final BeanPostProcessor instance, final Object bean,
			final String beanName) {
		return instance != null ? instance.postProcessBeforeInitialization(bean, beanName) : null;
	}

	private static void setEnvironment(final EnvironmentAware instance, final Environment environment) {
		if (instance != null) {
			instance.setEnvironment(environment);
		}
	}

	@Test
	void testPostProcessAfterInitialization() throws ReflectiveOperationException {
		//
		// org.springframework.core.env.PropertyResolver
		//
		setEnvironment(instance, environment);
		//
		// setPreferredWidth
		//
		Assertions.assertNull(postProcessAfterInitialization(instance, null, null));
		//
		if (ih != null) {
			//
			ih.getProperties().put(StringUtils.joinWith(".", JFrame.class.getName(), "preferredSize.width"),
					Integer.toString(1));
			//
		} // if
			//
		Assertions.assertSame(jFrame, postProcessAfterInitialization(instance, jFrame, null));
		//
		if (ih != null) {
			//
			ih.getProperties().remove(StringUtils.joinWith(".", JFrame.class.getName(), "defaultCloseOperation"));
			//
			final Map<Object, Object> properties = ih.properties;
			//
			if (properties != null) {
				//
				properties.put("java.awt.Component.preferredSize.width",
						properties.remove(StringUtils.joinWith(".", JFrame.class.getName(), "preferredSize.width")));
				//
			} // if
				//
		} // if
			//
		Assertions.assertSame(jFrame, postProcessAfterInitialization(instance, jFrame, null));
		//
		Assertions.assertNotNull(
				postProcessAfterInitialization(instance, Narcissus.allocateInstance(JFrame.class), null));
		//
		// Create a "java.awt.Component" instance which
		// "java.awt.Component.getPreferredSize()" method returns null
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object object = constructor != null ? constructor.newInstance() : null;
		//
		if (object instanceof ProxyObject) {
			//
			((ProxyObject) object).setHandler(new MH());
			//
		} // if
			//
		Assertions.assertSame(object, postProcessAfterInitialization(instance, object, null));
		//
	}

	private static Object postProcessAfterInitialization(final BeanPostProcessor instance, final Object bean,
			final String beanName) {
		return instance != null ? instance.postProcessAfterInitialization(bean, beanName) : null;
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

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAnnotationPresent() throws Throwable {
		//
		Assertions.assertFalse(isAnnotationPresent(null, null));
		//
		Assertions.assertFalse(isAnnotationPresent(Object.class, null));
		//
		Assertions.assertTrue(
				isAnnotationPresent(CustomBeanPostProcessorTest.class.getDeclaredMethod("beforeAll"), BeforeAll.class));
		//
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) throws Throwable {
		try {
			final Object obj = METHOD_IS_ANNOTATION_PRESENT.invoke(null, instance, annotationClass);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAnnotation() throws Throwable {
		//
		Assertions.assertNull(getAnnotation(null, null));
		//
		Assertions.assertNull(getAnnotation(Object.class, null));
		//
		Assertions.assertNotNull(
				getAnnotation(CustomBeanPostProcessorTest.class.getDeclaredMethod("beforeAll"), BeforeAll.class));
		//
	}

	private static <T extends Annotation> T getAnnotation(final AnnotatedElement instance,
			final Class<T> annotationClass) throws Throwable {
		try {
			final Object obj = METHOD_GET_ANNOTATION.invoke(null, instance, annotationClass);
			if (obj == null) {
				return null;
			} else if (annotationClass != null && annotationClass.isInstance(obj)) {
				return annotationClass.cast(obj);
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetTitle() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setTitle(null, null));
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		final Frame frame = headless ? cast(Frame.class, Narcissus.allocateInstance(Frame.class)) : new Frame();
		//
		Assertions.assertDoesNotThrow(() -> setTitle(frame, null));
		//
		if (headless) {
			//
			final Method method = CustomBeanPostProcessor.class.getDeclaredMethod("ensureObjectLockNotNull",
					Object.class);
			//
			if (method != null) {
				//
				method.setAccessible(true);
				//
				method.invoke(null, frame);
				//
			} // if
				//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setTitle(frame, Reflection.newProxy(Title.class, ih)));
		//
	}

	private static void setTitle(final Frame frame, final Title title) throws Throwable {
		try {
			METHOD_SET_TITLE.invoke(null, frame, title);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf("A"));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return ((Integer) obj);
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}