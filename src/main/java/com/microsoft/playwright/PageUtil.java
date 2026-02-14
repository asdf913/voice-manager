package com.microsoft.playwright;

import java.util.List;

public interface PageUtil {

	static Response navigate(final Page instance, final String url) {
		return instance != null ? instance.navigate(url) : null;
	}

	static List<ElementHandle> querySelectorAll(final Page instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	static Object evaluate(final Page instance, final String expression) {
		return instance != null ? instance.evaluate(expression) : null;
	}

}