package org.slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;

public class LoggerUtil {

	private static final Pattern PATTERN = Pattern.compile("^<(cl)?init>$");

	private LoggerUtil() {
	}

	public static void error(final Logger instance, final String msg, final Throwable t) {
		//
		if (instance != null) {
			//
			instance.error(msg, t);
			//
		} // if
			//
	}

	public static boolean isNOPLogger(final Logger instance) {
		//
		final Class<?> clz = getClass(instance);
		//
		JavaClass javaClass = null;
		//
		Method[] ms = null;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			ms = (javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null))) != null
							? javaClass.getMethods()
							: null;
			//
		} catch (final IOException e) {
			//
			printStackTrace(e);
			//
		} // try
			//
		return CollectionUtils.isNotEmpty(toList(filter(testAndApply(Objects::nonNull, ms, Arrays::stream, null), m -> {
			//
			if (m == null || !Objects.equals(Type.VOID, m.getReturnType()) || matches(matcher(PATTERN, m.getName()))) {
				//
				return false;
				//
			} // if
				//
			return isEmptyMethod(InstructionListUtil
					.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(m, null, null))));
			//
		})));
		//
	}

	private static boolean isEmptyMethod(final Instruction[] is) {
		return is == null || is.length == 0 || (is.length == 1 && (is[0] == null || is[0] instanceof RETURN));
	}

	private static void printStackTrace(final Throwable throwable) {
		//
		final List<java.lang.reflect.Method> ms = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(Throwable.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(getName(m), "printStackTrace") && m.getParameterCount() == 0));
		//
		final java.lang.reflect.Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms,
				x -> IterableUtils.get(x, 0), null);
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

	private static java.lang.reflect.Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static Object invoke(final java.lang.reflect.Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		return pattern != null && input != null ? pattern.matcher(input) : null;
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
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

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

}