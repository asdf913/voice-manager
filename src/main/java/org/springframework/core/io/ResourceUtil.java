package org.springframework.core.io;

public interface ResourceUtil {

	static boolean exists(final Resource instance) {
		return instance != null && instance.exists();
	}

}