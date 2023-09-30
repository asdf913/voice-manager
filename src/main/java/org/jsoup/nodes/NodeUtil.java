package org.jsoup.nodes;

import java.util.List;

public interface NodeUtil {

	static String absUrl(final Node instance, final String attributeKey) {
		return instance != null ? instance.absUrl(attributeKey) : null;
	}

	static List<Node> childNodes(final Node instance) {
		return instance != null ? instance.childNodes() : null;
	}

}