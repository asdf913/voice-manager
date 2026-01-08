package com.microsoft.playwright;

public interface ElementHandleUtil {

	static byte[] screenshot(final ElementHandle instance) {
		return instance != null ? instance.screenshot() : null;
	}

}