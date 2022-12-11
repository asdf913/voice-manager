package org.jsoup.nodes;

public interface ElementUtil {

	static String text(final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.text() : null;
	}

}