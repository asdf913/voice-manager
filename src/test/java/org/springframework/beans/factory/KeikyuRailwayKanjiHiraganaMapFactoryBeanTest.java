package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class KeikyuRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_GET_WHOLE_TEXT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = KeikyuRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", List.class)).setAccessible(true);
		//
		(METHOD_GET_WHOLE_TEXT = clz.getDeclaredMethod("getWholeText", TextNode.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private List<Node> childNodes = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "childNodes")) {
					//
					return childNodes;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private KeikyuRailwayKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new KeikyuRailwayKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(Collections.singletonList(null)));
		//
		final Element element = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		final MH mh = new MH();
		//
		final Node parentNode = createProxy(Node.class, mh);
		//
		if (element != null) {
			//
			FieldUtils.writeField(element, "parentNode", parentNode, true);
			//
		} // if
			//
		final List<Element> elements = Collections.singletonList(element);
		//
		Assertions.assertNull(createMap(elements));
		//
		mh.childNodes = Collections.singletonList(null);
		//
		Assertions.assertNull(createMap(elements));
		//
		final TextNode textNode = cast(TextNode.class, Narcissus.allocateInstance(TextNode.class));
		//
		mh.childNodes = Collections.singletonList(textNode);
		//
		Assertions.assertEquals("{null=null}", Util.toString(createMap(elements)));
		//
		if (textNode != null) {
			//
			textNode.text("1駅");
			//
		} // if
			//
		Assertions.assertEquals("{1=null}", Util.toString(createMap(elements)));
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

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static Map<String, String> createMap(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWholeText() throws Throwable {
		//
		Assertions.assertNull(getWholeText(null));
		//
	}

	private static String getWholeText(final TextNode instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WHOLE_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}