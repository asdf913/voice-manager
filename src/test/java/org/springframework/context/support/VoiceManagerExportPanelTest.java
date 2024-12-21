package org.springframework.context.support;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void testNull() {
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
		String toString = null;
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
				} else {
					//
					list.add(null);
					//
				} // if
					//
			} // for
				//
			os = toArray(list);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				if (Boolean.logicalAnd(Objects.equals("getTitle", m.getName()), m.getParameterCount() == 0)
						|| Boolean.logicalAnd(Objects.equals("getWorkbookClassFailableSupplierMap", m.getName()),
								m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(Narcissus.invokeMethod(instance, m, os), toString);
					//
				} else if (Boolean.logicalAnd(Objects.equals("actionPerformed", m.getName()),
						Arrays.equals(parameterTypes, new Class<?>[] { ActionEvent.class }))) {
					//
					final Method method = m;
					//
					final Object[] objects = os;
					//
					Assertions.assertThrows(Throwable.class, () -> Narcissus.invokeMethod(instance, method, objects));
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