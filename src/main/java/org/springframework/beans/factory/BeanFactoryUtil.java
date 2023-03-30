package org.springframework.beans.factory;

import javax.annotation.Nullable;

public interface BeanFactoryUtil {

	@Nullable
	static Object getBean(@Nullable final BeanFactory instance, final String name) {
		return instance != null ? instance.getBean(name) : null;
	}

}