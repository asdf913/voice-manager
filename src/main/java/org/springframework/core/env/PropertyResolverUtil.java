package org.springframework.core.env;

import javax.annotation.Nullable;

public interface PropertyResolverUtil {

	static boolean containsProperty(@Nullable final PropertyResolver instance, final String key) {
		return instance != null && instance.containsProperty(key);
	}

	@Nullable
	static String getProperty(@Nullable final PropertyResolver instance, final String key) {
		return instance != null ? instance.getProperty(key) : null;
	}

}