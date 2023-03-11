package org.springframework.core.io;

import java.io.IOException;

import javax.annotation.Nullable;

public interface ResourceUtil {

	static boolean exists(final Resource instance) {
		return instance != null && instance.exists();
	}

	static byte[] getContentAsByteArray(@Nullable final Resource instance) throws IOException {
		return instance != null ? instance.getContentAsByteArray() : null;
	}

}