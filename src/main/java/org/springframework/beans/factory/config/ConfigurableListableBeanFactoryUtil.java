package org.springframework.beans.factory.config;

public interface ConfigurableListableBeanFactoryUtil {

	static BeanDefinition getBeanDefinition(final ConfigurableListableBeanFactory instance, final String beanName) {
		return instance != null ? instance.getBeanDefinition(beanName) : null;
	}

}