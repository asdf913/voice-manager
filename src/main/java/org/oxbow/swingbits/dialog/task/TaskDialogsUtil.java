package org.oxbow.swingbits.dialog.task;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

public class TaskDialogsUtil {

	private static Logger LOG = LoggerFactory.getLogger(TaskDialogsUtil.class);

	private TaskDialogsUtil() {
	}

	public static void errorOrPrintStackTraceOrAssertOrShowException(final Throwable throwable) {
		//
		errorOrPrintStackTraceOrAssertOrShowException(GraphicsEnvironment.isHeadless(), LOG, throwable);
		//
	}

	private static void errorOrPrintStackTraceOrAssertOrShowException(final boolean headless, final Logger logger,
			final Throwable throwable) {
		//
		if (headless) {
			//
			if (Boolean.logicalAnd(logger != null, !LoggerUtil.isNOPLogger(logger))) {
				//
				LoggerUtil.error(logger, getMessage(throwable), throwable);
				//
			} else if (throwable != null) {
				//
				printStackTrace(throwable);
				//
			} // if
				//
			return;
			//
		} // if
			//
		final List<Method> ms = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(forName("org.junit.jupiter.api.AssertDoesNotThrow")),
						Arrays::stream, null),
				x -> Boolean.logicalAnd(StringUtils.equals(getName(x), "createAssertionFailedError"),
						Arrays.equals(new Class<?>[] { Object.class, Throwable.class }, getParameterTypes(x)))));
		//
		final Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
		//
		if (method == null) {
			//
			TaskDialogs.showException(throwable);
			//
		} else {
			//
			setAccessible(method, true);
			//
			try {
				//
				final RuntimeException runtimeException = toRuntimeException(cast(Throwable.class,
						getClass(throwable) != null ? invoke(method, null, getMessage(throwable), throwable) : null));
				//
				if (runtimeException != null) {
					//
					throw runtimeException;
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				//
				errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
				//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				errorOrPrintStackTraceOrAssertOrShowException(headless, LOG,
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
								ExceptionUtils.getRootCause(e), e));
				//
			} // try
				//
		} // if
			//
	}

	private static String getMessage(final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static void printStackTrace(final Throwable throwable) {
		//
		final List<Method> ms = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(Throwable.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(getName(m), "printStackTrace") && m.getParameterCount() == 0));
		//
		final Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
		//
		setAccessible(method, true);
		//
		try {
			//
			testAndAccept(m -> throwable != null || isStatic(m), method, m -> invoke(m, throwable));
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			printStackTrace(ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
					ExceptionUtils.getRootCause(e), e));
			//
		} catch (final ReflectiveOperationException e) {
			//
			printStackTrace(throwable);
			//
		} // try
			//
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?>[] getParameterTypes(final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static RuntimeException toRuntimeException(final Throwable instance) {
		//
		if (instance instanceof RuntimeException) {
			//
			return (RuntimeException) instance;
			//
		} else if (instance instanceof Throwable) {
			//
			return new RuntimeException(instance);
			//
		} // if
			//
		return null;
		//
	}

}