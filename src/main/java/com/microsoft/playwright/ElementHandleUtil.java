package com.microsoft.playwright;

import java.util.List;

public interface ElementHandleUtil {

	static byte[] screenshot(final ElementHandle instance) {
		return instance != null ? instance.screenshot() : null;
	}

	static List<ElementHandle> querySelectorAll(final ElementHandle instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

}