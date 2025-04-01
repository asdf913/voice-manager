package org.springframework.beans;

import javax.annotation.Nullable;

public interface PropertyValuesUtil {

	static boolean contains(@Nullable final PropertyValues instance, final String propertyName) {
		return instance != null && instance.contains(propertyName);
	}

	static PropertyValue getPropertyValue(final PropertyValues instance, final String propertyName) {
		return instance != null ? instance.getPropertyValue(propertyName) : null;
	}

}