package org.springframework.beans;

public interface PropertyValuesUtil {

	static boolean contains(final PropertyValues instance, final String propertyName) {
		return instance != null && instance.contains(propertyName);
	}

	static PropertyValue getPropertyValue(final PropertyValues instance, final String propertyName) {
		return instance != null ? instance.getPropertyValue(propertyName) : null;
	}

}