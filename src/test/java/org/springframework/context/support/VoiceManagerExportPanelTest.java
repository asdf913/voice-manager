package org.springframework.context.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Table;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerExportPanelTest {

	private VoiceManagerExportPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerExportPanel();
		//
	}

	@Test
	void testNull() throws ClassNotFoundException {
		//
		final Method[] ms = VoiceManagerExportPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Object[] os = null;
		//
		String name, toString = null;
		//
		int parameterCount = 0;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			for (int j = 0; (parameterTypes = m.getParameterTypes()) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Boolean.TYPE)) {
					//
					list.add(Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					list.add(Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					list.add(Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					list.add(Character.valueOf(' '));
					//
				} else {
					//
					list.add(null);
					//
				} // if
					//
			} // for
				//
			name = Util.getName(m);
			//
			parameterCount = m.getParameterCount();
			//
			os = toArray(list);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE, Double.TYPE),
						m.getReturnType())) {
					//
					Assertions.assertNotNull(Narcissus.invokeStaticMethod(m, os), toString);
					//
				} else if (Boolean.logicalAnd(Objects.equals("getFieldOrder", name), parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("getVisibileVoiceFields", name), parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("getTempFileMinimumPrefixLength", name),
								parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("getAccessibleObjectIsAccessibleMethod", name),
								parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("toDurationIvalue0", name),
								Arrays.equals(parameterTypes, new Class<?>[] { Object.class }))
						|| Boolean.logicalAnd(Objects.equals("createExportTask", name),
								Arrays.equals(parameterTypes, new Class<?>[] { Class.forName(
										"org.springframework.context.support.VoiceManagerExportPanel$ObjectMap"),
										Integer.class, Integer.class, Integer.class, Map.class, Table.class }))) {
					//
					Assertions.assertNotNull(Narcissus.invokeStaticMethod(m, os));
					//
				} else {
					//
					Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
					//
				} // if
					//
			} else {
				//
				if (Boolean.logicalAnd(Objects.equals("getTitle", name), parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("getWorkbookClassFailableSupplierMap", name),
								parameterCount == 0)
						|| Boolean.logicalAnd(Objects.equals("getObjectMapper", name), parameterCount == 0)) {
					//
					Assertions.assertNotNull(Narcissus.invokeMethod(instance, m, os), toString);
					//
				} else {
					//
					Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
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