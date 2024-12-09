package org.springframework.context.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerImportBatchPanelTest {

	private VoiceManagerImportBatchPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImportBatchPanel();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerImportBatchPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Object[] os = null;
		//
		Class<?>[] parameterTypes = null;
		//
		String name;
		//
		Object invoke;
		//
		String toString;
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
				if (Objects.equals(parameterTypes[j], Boolean.TYPE)) {
					//
					Util.add(list, Boolean.FALSE);
					//
				} else {
					//
					Util.add(list, null);
					//
				} // if
					//
			} // for
				//
			name = Util.getName(m);
			//
			toString = Objects.toString(m);
			//
			os = toArray(list);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Void.TYPE }, m.getReturnType())) {
					//
					Assertions.assertNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNotNull(invoke, toString);
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