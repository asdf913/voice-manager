package org.springframework.core.env;

public interface PropertyResolverUtil {

	static boolean containsProperty(final PropertyResolver instance, final String key) {
		return instance != null && instance.containsProperty(key);
	}

	static String getProperty(final PropertyResolver instance, final String key) {
		return instance != null ? instance.getProperty(key) : null;
	}

}