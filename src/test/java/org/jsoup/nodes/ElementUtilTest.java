package org.jsoup.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ElementUtilTest {

	@Test
	void testSelect() {
		//
		Assertions.assertNotNull(ElementUtil.select(new Element("a"), ".1"));
		//
	}

	@Test
	void testChildren() {
		//
		Assertions.assertNull(ElementUtil.children(null));
		//
		Assertions.assertNotNull(ElementUtil.children(new Element("a")));
		//
	}

	@Test
	void testAttr() {
		//
		Assertions.assertNull(ElementUtil.attr(null, null));
		//
		Assertions.assertNotNull(ElementUtil.attr(new Element("a"), ""));
		//
	}

	@Test
	void testNextElementSibling() {
		//
		Assertions.assertNull(ElementUtil.nextElementSibling(new Element("a")));
		//
	}

}