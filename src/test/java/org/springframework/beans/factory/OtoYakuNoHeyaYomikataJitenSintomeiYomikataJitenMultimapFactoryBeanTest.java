package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_HAS_ATTR, METHOD_INT_VALUE, METHOD_VALUE_OF, METHOD_TEST_AND_APPLY = null;

	private static final int ZERO = 0;

	@BeforeAll
	static void beforeClass() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_HAS_ATTR = clz.getDeclaredMethod("hasAttr", Node.class, String.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private Boolean hasAttr;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "hasAttr")) {
					//
					return hasAttr;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Properties properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.txt");
			//
			FileUtils.writeLines(file, MultimapUtil.entries(getObject(instance)));
			//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testHasAttr() throws Throwable {
		//
		Assertions.assertFalse(hasAttr(null, null));
		//
		final MH mh = new MH();
		//
		final Node node = createProxy(Node.class, mh);
		//
		Assertions.assertEquals(mh.hasAttr = Boolean.FALSE, Boolean.valueOf(hasAttr(node, null)));
		//
		Assertions.assertEquals(mh.hasAttr = Boolean.TRUE, Boolean.valueOf(hasAttr(node, null)));
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
		return (T) Util.cast(clz, instance);
		//
	}

	private static boolean hasAttr(final Node instance, final String attributeKey) throws Throwable {
		try {
			final Object obj = METHOD_HAS_ATTR.invoke(null, instance, attributeKey);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		final int one = 1;
		//
		Assertions.assertEquals(one, intValue(null, one));
		//
		Assertions.assertEquals(ZERO, intValue(Integer.valueOf(ZERO), one));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf(""));
		//
		Assertions.assertNull(valueOf(" "));
		//
		Assertions.assertNull(valueOf("A"));
		//
		Assertions.assertEquals(Integer.valueOf(ZERO), valueOf(Integer.toString(ZERO)));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}