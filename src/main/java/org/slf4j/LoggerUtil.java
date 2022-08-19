package org.slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.Type;
import org.apache.commons.lang3.StringUtils;

public class LoggerUtil {

	private static final Pattern PATTERN = Pattern.compile("^<(cl)?init>$");

	private LoggerUtil() {
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
			e.printStackTrace();
			//
		} // try
			//
		return isEmpty(toList(filter(testAndApply(Objects::nonNull, ms, Arrays::stream, null), m -> {
			//
			if (m == null || !Objects.equals(Type.VOID, m.getReturnType()) || matches(matcher(PATTERN, m.getName()))) {
				//
				return false;
				//
			} // if
				//
			final byte[] bs = getCode(m.getCode());
			//
			return !Objects.equals(
					StringUtils.trim(Utility.codeToString(bs, m.getConstantPool(), 0, bs != null ? bs.length : 0)),
					"0:    return");
			//
		})));
		//
	}

	private static byte[] getCode(final Code instance) {
		return instance != null ? instance.getCode() : null;
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		return pattern != null && input != null ? pattern.matcher(input) : null;
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static boolean isEmpty(final Collection<?> instance) {
		return instance != null && instance.isEmpty();
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