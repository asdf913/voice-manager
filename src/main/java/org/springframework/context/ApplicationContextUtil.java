package org.springframework.context;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public interface ApplicationContextUtil {

	static AutowireCapableBeanFactory getAutowireCapableBeanFactory(final ApplicationContext instance)
			throws IllegalStateException {
		return instance != null ? instance.getAutowireCapableBeanFactory() : null;
	}

}