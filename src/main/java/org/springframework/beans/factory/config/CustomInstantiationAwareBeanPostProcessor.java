package org.springframework.beans.factory.config;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;

public class CustomInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	@Override
	public PropertyValues postProcessProperties(final PropertyValues pvs, final Object bean, final String beanName)
			throws BeansException {
		//
		final Class<?> clz = bean != null ? bean.getClass() : null;
		//
		System.out.println(clz);
		//
		if (Objects.equals(clz,
				org.springframework.beans.factory.AccentDictionaryForJapaneseEducationMultimapFactoryBean.class)) {
			System.out.println();
		}
		//
		return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
		//
	}

}