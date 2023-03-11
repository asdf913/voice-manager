package org.springframework.beans.factory.config;

import javax.annotation.Nullable;

public interface ConfigurableListableBeanFactoryUtil {

	static BeanDefinition getBeanDefinition(@Nullable final ConfigurableListableBeanFactory instance,
			final String beanName) {
		return instance != null ? instance.getBeanDefinition(beanName) : null;
	}

}