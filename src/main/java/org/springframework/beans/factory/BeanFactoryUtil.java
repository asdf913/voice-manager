package org.springframework.beans.factory;

public interface BeanFactoryUtil {

	static Object getBean(final BeanFactory instance, final String name) {
		return instance != null ? instance.getBean(name) : null;
	}

}