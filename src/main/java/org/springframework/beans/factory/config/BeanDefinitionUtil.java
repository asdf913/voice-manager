package org.springframework.beans.factory.config;

import javax.annotation.Nullable;

import org.springframework.beans.MutablePropertyValues;

public interface BeanDefinitionUtil {

	@Nullable
	static String getBeanClassName(@Nullable final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

	static MutablePropertyValues getPropertyValues(@Nullable final BeanDefinition instance) {
		return instance != null ? instance.getPropertyValues() : null;
	}

}