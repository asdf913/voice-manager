package org.jsoup.nodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.jsoup.select.NodeVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class NodeUtilTest {

	private static Method METHOD_GET_NAME, METHOD_GET_CLASS, METHOD_FILTER, METHOD_TEST, METHOD_CAST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = NodeUtil.class;
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String absUrl = null;

		private Integer childNodeSize = null;

		private Boolean hasAttr;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "absUrl")) {
					//
					return absUrl;
					//
				} else if (Objects.equals(methodName, "childNodeSize")) {
					//
					return childNodeSize;
					//
				} else if (Objects.equals(methodName, "hasAttr")) {
					//
					return hasAttr;
					//
				} else if (IterableUtils.contains(Arrays.asList("attr", "traverse", "nextSibling"), methodName)) {
					//
					return proceed != null ? proceed.invoke(self, args) : null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} else if (proxy instanceof NodeVisitor) {
				//
				if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
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

	private Node node = null;

	private IH ih = null;

	private MH mh = null;

	@BeforeEach
	private void beforeEach() throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Node.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		mh = new MH();
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		node = cast(Node.class, instance);
		//
		ih = new IH();
		//
	}

	@Test
	void testAbsUrl()
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(NodeUtil.absUrl(null, null));
		//
		Assertions.assertNull(NodeUtil.absUrl(node, null));
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
	void testAttr() {
		//
		Assertions.assertNull(NodeUtil.attr(null, null));
		//
		Assertions.assertNull(NodeUtil.attr(node, null));
		//
	}

	@Test
	void testNodeName() {
		//
		Assertions.assertNull(NodeUtil.nodeName(null));
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

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNotNull(getClass(""));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		final Stream<?> stream = Reflection.newProxy(Stream.class, ih);
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
	void testTraverse() {
		//
		Assertions.assertNull(NodeUtil.traverse(null, null));
		//
		Assertions.assertSame(node, NodeUtil.traverse(node, null));
		//
		if (mh != null) {
			//
			mh.childNodeSize = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertSame(node, NodeUtil.traverse(node, Reflection.newProxy(NodeVisitor.class, ih)));
		//
	}

	@Test
	void testChildNodeSize() throws Throwable {
		//
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			if (classInfo == null) {
				//
				continue;
				//
			} // if
				//
			try {
				//
				if (isAssignableFrom(Node.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Node node = cast(Node.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> NodeUtil.childNodeSize(node), name);
					//
				} // if
					//
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	@Test
	void testHasAttr() throws Throwable {
		//
		Assertions.assertFalse(NodeUtil.hasAttr(null, null));
		//
		if (mh != null) {
			//
			final Node node = createProxy(Node.class, mh);
			//
			Assertions.assertEquals(mh.hasAttr = Boolean.FALSE, Boolean.valueOf(NodeUtil.hasAttr(node, null)));
			//
			Assertions.assertEquals(mh.hasAttr = Boolean.TRUE, Boolean.valueOf(NodeUtil.hasAttr(node, null)));
			//
		} // if
			//
	}

	private static <T> T createProxy(final Class<T> superClass, final MethodHandler mh) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return (T) cast(clz, instance);
		//
	}

	@Test
	void testNodeStream() throws Throwable {
		//
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			if (classInfo == null) {
				//
				continue;
				//
			} // if
				//
			try {
				//
				if (isAssignableFrom(Node.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Node node = cast(Node.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> NodeUtil.nodeStream(node), name);
					//
				} // if
					//
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = NodeUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		int parameterCount = 0;
		//
		boolean isStatic = false;
		//
		Object result = null;
		//
		String toString = null;
		//
		Class<?> returnType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null) {
				//
				continue;
				//
			} // if
				//
			isStatic = Modifier.isStatic(m.getModifiers());
			//
			toString = Objects.toString(m);
			//
			returnType = m.getReturnType();
			//
			if ((parameterCount = m.getParameterCount()) == 1 && isStatic) {
				//
				result = Narcissus.invokeStaticMethod(m, (Object) null);
				//
				if (Objects.equals(Integer.TYPE, returnType)) {
					//
					Assertions.assertEquals(Integer.valueOf(0), result, toString);
					//
				} else if (Objects.equals(Boolean.TYPE, returnType)) {
					//
					Assertions.assertEquals(Boolean.FALSE, result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else if (parameterCount == 2 && isStatic) {
				//
				result = Narcissus.invokeStaticMethod(m, null, null);
				//
				if (Objects.equals(Boolean.TYPE, returnType)) {
					//
					Assertions.assertEquals(Boolean.FALSE, result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

}