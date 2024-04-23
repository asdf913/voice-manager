package org.springframework.beans.factory.config;

public interface BeanDefinitionUtil {

	static String getBeanClassName(final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

}