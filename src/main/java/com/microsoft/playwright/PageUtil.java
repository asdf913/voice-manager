package com.microsoft.playwright;

public interface PageUtil {

	static Response navigate(final Page instance, final String url) {
		return instance != null ? instance.navigate(url) : null;
	}

}