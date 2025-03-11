package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntFunction;

import javax.swing.JScrollPane;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerHelpPanelIntFunctionFactoryBeanTest {

	private VoiceManagerHelpPanelIntFunctionFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerHelpPanelIntFunctionFactoryBean();
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists, isFile, isReadable = null;

		private byte[] contentAsByteArray = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(name, "exists")) {
					//
					return exists;
					//
				} else if (Objects.equals(name, "isFile")) {
					//
					return isFile;
					//
				} else if (Objects.equals(name, "isReadable")) {
					//
					return isReadable;
					//
				} else if (Objects.equals(name, "getContentAsByteArray")) {
					//
					return contentAsByteArray;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	@Test
	void testGetObject() throws Exception {
		//
		final IntFunction<JScrollPane> intFunction = FactoryBeanUtil.getObject(instance);
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		final String href = "href";
		//
		if (instance != null) {
			//
			instance.setMediaFormatPageHref(href);
			//
		} // if
			//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		final String text = "text";
		//
		if (instance != null) {
			//
			instance.setMediaFormatPageText(text);
			//
		} // if
			//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		final IH ih = new IH();
		//
		if (instance != null) {
			//
			instance.setEncryptionTableHtmlResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		ih.exists = Boolean.FALSE;
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		ih.exists = Boolean.TRUE;
		//
		ih.isFile = Boolean.FALSE;
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		ih.isFile = Boolean.TRUE;
		//
		ih.isReadable = Boolean.FALSE;
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		ih.isReadable = Boolean.TRUE;
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
		ih.contentAsByteArray = new byte[] {};
		//
		Assertions.assertNotNull(intFunction != null ? intFunction.apply(0) : null);
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerHelpPanelIntFunctionFactoryBean.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String name, toString;
		//
		Object invoke = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			name = Util.getName(m);
			//
			os = toArray(collection);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Long.TYPE, Integer.TYPE, Boolean.TYPE), m.getReturnType())) {
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
				if ((instance = ObjectUtils.getIfNull(instance,
						VoiceManagerHelpPanelIntFunctionFactoryBean::new)) != null) {
					//
					Util.filter(
							Arrays.stream(Util.getDeclaredFields(VoiceManagerHelpPanelIntFunctionFactoryBean.class)),
							f -> f != null && !Modifier.isStatic(f.getModifiers()))
							.forEach(f -> Narcissus.setField(instance, f, null));
					//
				} // if
					//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (Boolean.logicalAnd(Util.contains(Arrays.asList("getObject", "getObjectType"), name),
						m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
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