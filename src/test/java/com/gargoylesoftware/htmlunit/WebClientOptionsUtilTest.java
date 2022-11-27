package com.gargoylesoftware.htmlunit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WebClientOptionsUtilTest {

	@Test
	void testSetJavaScriptEnabled() {
		//
		Assertions.assertDoesNotThrow(() -> WebClientOptionsUtil.setJavaScriptEnabled(null, false));
		//
	}

}