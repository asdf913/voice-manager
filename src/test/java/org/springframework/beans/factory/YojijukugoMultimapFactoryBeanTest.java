package org.springframework.beans.factory;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Multimap;

class YojijukugoMultimapFactoryBeanTest {

	private YojijukugoMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new YojijukugoMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		instance.setUrl(new File("pom.xml").toURI().toURL().toString());
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

}