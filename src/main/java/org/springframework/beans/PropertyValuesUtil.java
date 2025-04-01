package org.springframework.beans;

public interface PropertyValuesUtil {

	static boolean contains(final PropertyValues instance, final String propertyName) {
		return instance != null && instance.contains(propertyName);
	}

}