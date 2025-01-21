package org.springframework.beans.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ImageWriterSpiFormatIterableFactoryBeanTest {

	@Test
	void testNull() {
		//
		final Method[] ms = ImageWriterSpiFormatIterableFactoryBean.class.getDeclaredMethods();
		//
		Method m = null;
		//
		ImageWriterSpiFormatIterableFactoryBean instance = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		String toString, name = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((list = ObjectUtils.getIfNull(list, ArrayList::new)) != null) {
				//
				list.clear();
				//
			} // if
				//
			for (int j = 0; j < Util.length(parameterTypes = m.getParameterTypes()); j++) {
				//
				if (Objects.equals(parameterTypes[j], Integer.TYPE)) {
					//
					Util.add(list, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(list, null);
					//
				} // if
					//
			} // for
				//
			os = toArray(list);
			//
			toString = Util.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(Boolean.TYPE, m.getReturnType())) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				if (Boolean.logicalOr(
						Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "getObject"),
								m.getParameterCount() == 0),
						Boolean.logicalAnd(Objects.equals(name, "getObjectType"), m.getParameterCount() == 0))) {
					//
					Assertions.assertNotNull(Narcissus.invokeMethod(
							instance = ObjectUtils.getIfNull(instance, ImageWriterSpiFormatIterableFactoryBean::new), m,
							os), toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}