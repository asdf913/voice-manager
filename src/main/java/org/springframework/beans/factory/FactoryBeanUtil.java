package org.springframework.beans.factory;

import javax.annotation.Nullable;

public interface FactoryBeanUtil {

	@Nullable
	static Class<?> getObjectType(@Nullable final FactoryBean<?> instance) {
		return instance != null ? instance.getObjectType() : null;
	}

}