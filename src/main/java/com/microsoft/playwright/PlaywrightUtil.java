package com.microsoft.playwright;

public interface PlaywrightUtil {

	static BrowserType chromium(final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

}