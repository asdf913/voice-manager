package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

class IpaMultimapFactoryBeanTest {

	private static class IH implements InvocationHandler {

		private Boolean exists = null;

		private InputStream inputStream = null;

		private IOException ioException = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					if (ioException != null) {
						//
						throw ioException;
						//
					} // if
						//
					return inputStream;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "exists")) {
					//
					return exists;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IpaMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new IpaMultimapFactoryBean();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		if (ih != null) {
			//
			ih.exists = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final String key = "KEY";
		//
		final String value = "VALUE";
		//
		try (final InputStream is = new ByteArrayInputStream(
				String.format("{\"%1$s\":\"%2$s\"}", key, value).getBytes())) {
			//

			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertEquals(Util.toString(ImmutableMultimap.of(key, value)),
					Util.toString(getObject(instance)));
			//
		} // try
			//
		if (instance != null) {
			//
			instance.setUrl("A");
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

}