package org.springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactoryUtil {

	static <T> Map<String, T> getBeansOfType(final ListableBeanFactory instance, final Class<T> type) {
		return instance != null ? instance.getBeansOfType(type) : null;
	}

}