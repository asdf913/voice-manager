package org.jsoup.nodes;

import org.jsoup.select.Elements;

public interface ElementUtil {

	static String text(final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.text() : null;
	}

	static Elements getElementsByTag(final Element instance, final String tagName) {
		return instance != null ? instance.getElementsByTag(tagName) : null;
	}

}