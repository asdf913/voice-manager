package com.microsoft.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class BrowserTypeUtilTest {

	@Test
	void testLaunch() {
		//
		Assertions.assertNull(BrowserTypeUtil.launch(null));
		//
		Assertions.assertNull(BrowserTypeUtil.launch(Reflection.newProxy(BrowserType.class, (a, b, c) -> {
			return null;
		})));
		//
	}

}