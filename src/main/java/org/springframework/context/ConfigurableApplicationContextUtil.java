package org.springframework.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContextUtil {

	static ConfigurableListableBeanFactory getBeanFactory(final ConfigurableApplicationContext instance) {
		return instance != null ? instance.getBeanFactory() : null;
	}

}