package org.springframework.core.io;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XlsxUtilTest {

	private static Method METHOD_TEST, METHOD_IS_STATIC, METHOD_INVOKE, METHOD_NEW_DOCUMENT_BUILDER, METHOD_PARSE,
			METHOD_GET_DOCUMENT_ELEMENT, METHOD_GET_CHILD_NODES, METHOD_GET_LENGTH, METHOD_ITEM, METHOD_GET_ATTRIBUTES,
			METHOD_GET_NAMED_ITEM, METHOD_GET_TEXT_CONTENT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = XlsxUtil.class;
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_NEW_DOCUMENT_BUILDER = clz.getDeclaredMethod("newDocumentBuilder", DocumentBuilderFactory.class))
				.setAccessible(true);
		//
		(METHOD_PARSE = clz.getDeclaredMethod("parse", DocumentBuilder.class, InputStream.class)).setAccessible(true);
		//
		(METHOD_GET_DOCUMENT_ELEMENT = clz.getDeclaredMethod("getDocumentElement", Document.class)).setAccessible(true);
		//
		(METHOD_GET_CHILD_NODES = clz.getDeclaredMethod("getChildNodes", Node.class)).setAccessible(true);
		//
		(METHOD_GET_LENGTH = clz.getDeclaredMethod("getLength", NodeList.class)).setAccessible(true);
		//
		(METHOD_ITEM = clz.getDeclaredMethod("item", NodeList.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_ATTRIBUTES = clz.getDeclaredMethod("getAttributes", Node.class)).setAccessible(true);
		//
		(METHOD_GET_NAMED_ITEM = clz.getDeclaredMethod("getNamedItem", NamedNodeMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_TEXT_CONTENT = clz.getDeclaredMethod("getTextContent", Node.class)).setAccessible(true);
		//
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

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNewDocumentBuilder() throws Throwable {
		//
		Assertions.assertNull(newDocumentBuilder(null));
		//
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance) throws Throwable {
		try {
			final Object obj = METHOD_NEW_DOCUMENT_BUILDER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DocumentBuilder) {
				return (DocumentBuilder) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParse() throws Throwable {
		//
		Assertions.assertNull(parse(null, null));
		//
	}

	private static Document parse(final DocumentBuilder instance, final InputStream is) throws Throwable {
		try {
			final Object obj = METHOD_PARSE.invoke(null, instance, is);
			if (obj == null) {
				return null;
			} else if (obj instanceof Document) {
				return (Document) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDocumentElement() throws Throwable {
		//
		Assertions.assertNull(getDocumentElement(null));
		//
	}

	private static Element getDocumentElement(final Document instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DOCUMENT_ELEMENT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Element) {
				return (Element) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetChildNodes() throws Throwable {
		//
		Assertions.assertNull(getChildNodes(null));
		//
	}

	private static NodeList getChildNodes(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CHILD_NODES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof NodeList) {
				return (NodeList) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLength() throws Throwable {
		//
		Assertions.assertEquals(0, getLength(null));
		//
	}

	private static int getLength(final NodeList instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LENGTH.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testItem() throws Throwable {
		//
		Assertions.assertNull(item(null, 0));
		//
	}

	private static Node item(final NodeList instance, final int index) throws Throwable {
		try {
			final Object obj = METHOD_ITEM.invoke(null, instance, index);
			if (obj == null) {
				return null;
			} else if (obj instanceof Node) {
				return (Node) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAttributes() throws Throwable {
		//
		Assertions.assertNull(getAttributes(null));
		//
	}

	private static NamedNodeMap getAttributes(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ATTRIBUTES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof NamedNodeMap) {
				return (NamedNodeMap) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNamedItem() throws Throwable {
		//
		Assertions.assertNull(getNamedItem(null, null));
		//
	}

	private static Node getNamedItem(final NamedNodeMap instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAMED_ITEM.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Node) {
				return (Node) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTextContent() throws Throwable {
		//
		Assertions.assertNull(getTextContent(null));
		//
	}

	private static String getTextContent(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT_CONTENT.invoke(null, instance);
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

}