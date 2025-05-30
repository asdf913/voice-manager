package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class IpaSymbolGuiTest {

	private static final String EMPTY = "";

	private static Method METHOD_TEST_AND_APPLY, METHOD_IIF, METHOD_SET_PREFERRED_WIDTH, METHOD_TEST_AND_ACCEPT3,
			METHOD_TEST_AND_ACCEPT4 = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = IpaSymbolGui.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Component[].class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private Dimension preferredSize = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			if (Objects.equals(thisMethod != null ? thisMethod.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Component) {
				//
				if (Objects.equals(methodName, "getPreferredSize")) {
					//
					return preferredSize;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IpaSymbolGui instance = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<IpaSymbolGui> constructor = IpaSymbolGui.class.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = cast(IpaSymbolGui.class, Narcissus.allocateInstance(IpaSymbolGui.class));
			//
		} // if
			//
		mh = new MH();
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		final AbstractButton btnCheckIpaSymbolJson = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCheckIpaSymbolJson", btnCheckIpaSymbolJson, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCheckIpaSymbolJson, 0, null)));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jlIpaJsonFile", new JLabel(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCheckIpaSymbolJson, 0, null)));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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

	@Test
	void testIif() throws Throwable {
		//
		Assertions.assertNull(iif(false, null, null));
		//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, trueValue, falseValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, (Component[]) null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, (Component) null));
		//
		final Component component = ProxyUtil.createProxy(Component.class, mh);
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, component));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			if (mh != null) {
				//
				mh.preferredSize = cast(Dimension.class, Narcissus.allocateInstance(Dimension.class));
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, component));
			//
		} // if
			//
	}

	private static void setPreferredWidth(final int width, final Component... cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, a -> {
			}));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, (a, b) -> {
			}));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
			//
		} // if
			//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer)
			throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}