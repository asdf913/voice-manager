package com.microsoft.playwright;

public interface BrowserTypeUtil {

	static Browser launch(final BrowserType instance) {
		return instance != null ? instance.launch() : null;
	}

}