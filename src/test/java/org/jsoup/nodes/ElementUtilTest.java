package org.jsoup.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ElementUtilTest {

	private Element element = null;

	@BeforeEach
	void beforeEach() {
		//
		element = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testText() {
		//
		Assertions.assertNull(ElementUtil.text(element));
		//
	}

	@Test
	void testGetElementsByTag() {
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, null));
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, ""));
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, " "));
		//
	}

	@Test
	void testSelectXpath() {
		//
		Assertions.assertNull(ElementUtil.selectXpath(element, null));
		//
	}

	@Test
	void testSelect() {
		//
		Assertions.assertNull(ElementUtil.select(element, ".1"));
		//
		Assertions.assertNotNull(ElementUtil.select(new Element("a"), ".1"));
		//
	}

	@Test
	void testChildren() {
		//
		Assertions.assertNull(ElementUtil.children(null));
		//
		Assertions.assertNull(ElementUtil.children(element));
		//
		Assertions.assertNotNull(ElementUtil.children(new Element("a")));
		//
	}

	@Test
	void testAttr() {
		//
		Assertions.assertNull(ElementUtil.attr(null, null));
		//
		Assertions.assertNull(ElementUtil.attr(element, null));
		//
		Assertions.assertEquals("", ElementUtil.attr(element, ""));
		//
		Assertions.assertNotNull(ElementUtil.attr(new Element("a"), ""));
		//
	}

	@Test
	void testNextElementSibling() {
		//
		Assertions.assertNull(ElementUtil.nextElementSibling(element));
		//
		Assertions.assertNull(ElementUtil.nextElementSibling(new Element("a")));
		//
	}

	@Test
	void testTagName() {
		//
		Assertions.assertNull(ElementUtil.tagName(element));
		//
	}

}