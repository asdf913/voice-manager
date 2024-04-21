package org.springframework.beans.factory;

import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;

class FactoryBeanUtilTest {

	@Test
	void testGetObjectType() throws Throwable {
		//
		Assertions.assertNull(FactoryBeanUtil.getObjectType(null));
		//
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			try {
				//
				if (Util.isAssignableFrom(FactoryBean.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final FactoryBean<?> factoryBean = Util.cast(FactoryBean.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> FactoryBeanUtil.getObjectType(factoryBean), name);
					//
				} // if
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

}