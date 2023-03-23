package org.jsoup.nodes;

public interface NodeUtil {

	static String absUrl(final Node instance, final String attributeKey) {
		return instance != null ? instance.absUrl(attributeKey) : null;
	}

}