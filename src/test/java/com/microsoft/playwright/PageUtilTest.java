package com.microsoft.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class PageUtilTest {

	@Test
	void testNavigate() {
		//
		Assertions.assertNull(PageUtil.navigate(null, null));
		//
		Assertions.assertNull(PageUtil.navigate(Reflection.newProxy(Page.class, (a, b, c) -> {
			return null;
		}), null));
		//
	}

}