package org.springframework.beans.config;

import javax.swing.JFrame;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {

	private Integer defaultCloseOperation = null;

	public void setDefaultCloseOperation(final Integer defaultCloseOperation) {
		//
		// TODO
		//
		this.defaultCloseOperation = defaultCloseOperation;
		//
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		//
		final JFrame jFrame = bean instanceof JFrame ? (JFrame) bean : null;
		//
		if (jFrame != null && defaultCloseOperation != null) {
			//
			jFrame.setDefaultCloseOperation(defaultCloseOperation.intValue());
			//
		} // if
			//
		return bean;
		//
	}
}