package com.gargoylesoftware.htmlunit;

public interface WebClientOptionsUtil {

	static void setJavaScriptEnabled(final WebClientOptions instance, final boolean enabled) {
		if (instance != null) {
			instance.setJavaScriptEnabled(enabled);
		}
	}

}