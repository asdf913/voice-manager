package org.springframework.context;

import javax.annotation.Nullable;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public interface ApplicationContextUtil {

	@Nullable
	static AutowireCapableBeanFactory getAutowireCapableBeanFactory(@Nullable final ApplicationContext instance)
			throws IllegalStateException {
		return instance != null ? instance.getAutowireCapableBeanFactory() : null;
	}

}