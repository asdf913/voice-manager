package org.springframework.beans.factory;

import java.util.Map;

import javax.annotation.Nullable;

public interface ListableBeanFactoryUtil {

	@Nullable
	static <T> Map<String, T> getBeansOfType(@Nullable final ListableBeanFactory instance, final Class<T> type) {
		return instance != null ? instance.getBeansOfType(type) : null;
	}

}