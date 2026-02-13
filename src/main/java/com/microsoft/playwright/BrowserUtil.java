package com.microsoft.playwright;

public interface BrowserUtil {

	static Page newPage(final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

}