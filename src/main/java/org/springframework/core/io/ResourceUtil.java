package org.springframework.core.io;

import java.io.IOException;

public interface ResourceUtil {

	static boolean exists(final Resource instance) {
		return instance != null && instance.exists();
	}

	static byte[] getContentAsByteArray(final Resource instance) throws IOException {
		return instance != null ? instance.getContentAsByteArray() : null;
	}

}