package org.jsoup.nodes;

import org.jsoup.select.Elements;

public interface ElementUtil {

	static String text(final Element instance) {
		return instance != null ? instance.text() : null;
	}

	static Elements getElementsByTag(final Element instance, final String tagName) {
		return instance != null ? instance.getElementsByTag(tagName) : null;
	}

	static Elements selectXpath(final Element instance, final String xpath) {
		return instance != null ? instance.selectXpath(xpath) : null;
	}

	static Elements select(final Element instance, final String cssQuery) {
		return instance != null ? instance.select(cssQuery) : null;
	}

	static Elements children(final Element instance) {
		return instance != null ? instance.children() : null;
	}

	static String attr(final Element instance, final String attributeKey) {
		return instance != null ? instance.attr(attributeKey) : null;
	}

	static Element nextElementSibling(final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

}