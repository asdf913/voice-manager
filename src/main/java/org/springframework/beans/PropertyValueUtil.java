package org.springframework.beans;

public interface PropertyValueUtil {

	static Object getValue(final PropertyValue instance) {
		return instance != null ? instance.getValue() : null;
	}

}