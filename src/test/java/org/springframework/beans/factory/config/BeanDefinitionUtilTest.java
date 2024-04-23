package org.springframework.beans.factory.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanDefinitionUtilTest {

	@Test
	void testGetBeanClassName() {
		//
		Assertions.assertNull(BeanDefinitionUtil.getBeanClassName(null));
		//
	}

}