package org.springframework.beans.factory.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigurableListableBeanFactoryUtilTest {

	@Test
	void testGetBeanDefinition() {
		//
		Assertions.assertNull(ConfigurableListableBeanFactoryUtil.getBeanDefinition(null, null));
		//
	}

}