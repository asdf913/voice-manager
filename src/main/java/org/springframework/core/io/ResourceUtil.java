package org.springframework.core.io;

import java.io.IOException;

import javax.annotation.Nullable;

public interface ResourceUtil {

	static boolean exists(@Nullable final Resource instance) {
		return instance != null && instance.exists();
	}

	static boolean isFile(final Resource instance) {
		return instance != null && instance.isFile();
	}

	@Nullable
	static byte[] getContentAsByteArray(@Nullable final Resource instance) throws IOException {
		return instance != null ? instance.getContentAsByteArray() : null;
	}

}