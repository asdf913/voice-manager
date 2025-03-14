package org.springframework.beans.factory.config;

import javax.annotation.Nullable;

public interface BeanDefinitionUtil {

	@Nullable
	static String getBeanClassName(@Nullable final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

}