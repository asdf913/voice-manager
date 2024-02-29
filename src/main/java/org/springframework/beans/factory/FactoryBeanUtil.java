package org.springframework.beans.factory;

public interface FactoryBeanUtil {

	static Class<?> getObjectType(final FactoryBean<?> instance) {
		return instance != null ? instance.getObjectType() : null;
	}

}