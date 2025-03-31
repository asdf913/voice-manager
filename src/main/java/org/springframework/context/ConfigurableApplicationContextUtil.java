package org.springframework.context;

import javax.annotation.Nullable;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContextUtil {

	static ConfigurableListableBeanFactory getBeanFactory(@Nullable final ConfigurableApplicationContext instance) {
		return instance != null ? instance.getBeanFactory() : null;
	}

}