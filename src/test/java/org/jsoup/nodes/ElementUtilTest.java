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

}