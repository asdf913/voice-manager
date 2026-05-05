package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class IpaDictGuiTest {

	private static Method METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_TEST_AND_APPLY = Util.getDeclaredMethod(IpaDictGui.class, "testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test = null;

		private int[] selectedIndices = null;

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Predicate && Objects.equals(name, "test")) {
				//
				return test;
				//
			} else if (proxy instanceof FailableFunction && Objects.equals(name, "apply")) {
				//
				return null;
				//
			} else if (proxy instanceof ListSelectionModel && Objects.equals(name, "getSelectedIndices")) {
				//
				return selectedIndices;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private IpaDictGui instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = Util.cast(IpaDictGui.class, Narcissus.allocateInstance(IpaDictGui.class));
		//
		ih = new IH();
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(IpaDictGui.class);
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| (parameterTypes = m.getParameterTypes()) == null) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = Util.toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(Util.getReturnType(m), Boolean.TYPE)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testNonNull() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(IpaDictGui.class);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		if (ih != null) {
			//
			ih.test = Boolean.FALSE;
			//
		} // if
			//
		Object[] os = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| (parameterTypes = m.getParameterTypes()) == null) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (isArray(parameterType) && parameterType != null) {
					//
					Util.add(collection, Array.newInstance(parameterType.getComponentType(), 0));
					//
				} else if (Objects.equals(parameterType, Toolkit.class)) {
					//
					Util.add(collection, Toolkit.getDefaultToolkit());
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			os = Util.toArray(collection);
			//
			toString = Objects.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(Util.getReturnType(m), Boolean.TYPE)
						|| Util.and(Objects.equals(Util.getName(m), "getSystemClipboard"),
								Arrays.equals(parameterTypes, new Class<?>[] { Toolkit.class }),
								!GraphicsEnvironment.isHeadless())) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	@Test
	void testActionPerformed() throws Throwable {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Multimap<?, ?> multimap = ImmutableMultimap.of("", "");
		//
		FieldUtils.writeDeclaredField(instance, "multimap", multimap, true);
		//
		final JTextComponent tfText = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfText", tfText, true);
		//
		Util.setText(tfText, Util.toString(testAndApply(x -> IterableUtils.size(x) == 1, MultimapUtil.keySet(multimap),
				x -> IterableUtils.get(x, 0), null)));
		//
		// btnExecute
		//
		final AbstractButton btnExecute = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnExecute", btnExecute, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnExecute, 0, null)));
		//
		// btnCopy
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] {};
			//
		} // if
			//
		final ActionEvent actionEvent = new ActionEvent(btnCopy, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { 0 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { 0, 1 };
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.actionPerformed(actionEvent));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}