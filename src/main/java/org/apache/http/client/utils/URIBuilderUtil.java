package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;

public interface URIBuilderUtil {

	static URI build(final URIBuilder instance) throws URISyntaxException {
		return instance != null ? instance.build() : null;
	}

}