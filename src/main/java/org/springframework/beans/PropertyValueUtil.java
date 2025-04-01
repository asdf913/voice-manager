package org.springframework.beans;

import javax.annotation.Nullable;

public interface PropertyValueUtil {

	@Nullable
	static Object getValue(@Nullable final PropertyValue instance) {
		return instance != null ? instance.getValue() : null;
	}

}