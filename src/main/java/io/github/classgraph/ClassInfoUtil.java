package io.github.classgraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.Type;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;

public final class ClassInfoUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ClassInfoUtil.class);

	private static IValue0<List<ClassInfo>> CLASS_INFOS = null;

	private ClassInfoUtil() {
	}

	public static List<ClassInfo> getClassInfos() {
		//
		if (CLASS_INFOS != null) {
			//
			return IValue0Util.getValue0(CLASS_INFOS);
			//
		} // if
			//
		final List<ClassInfo> classInfos = ScanResultUtil
				.getAllClasses(ClassGraphUtil.scan(new ClassGraph().enableClassInfo()));
		//
		removeIf(classInfos, x -> {
			//
			if (x == null) {
				//
				return false;
				//
			} // if
				//
			try {
				//
				if (!checkJavaClass(toJavaClass(
						(ClassLoader) Narcissus.getObjectField(x, Narcissus.findField(getClass(x), "classLoader")),
						HasNameUtil.getName(x)))) {
					//
					return true;
					//
				} // if
					//
			} catch (final NoSuchFieldException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			return false;
			//
		});
		//
		return IValue0Util.getValue0(CLASS_INFOS = Unit.with(classInfos));
		//
	}

	private static boolean checkJavaClass(final JavaClass javaClass) {
		//
		if (forName(getClassName(javaClass)) == null) {
			//
			return false;
			//
		} // if
			//
		final Field[] fs = getFields(javaClass);
		//
		Field f = null;
		//
		Type type, basicType = null;
		//
		ArrayType arrayType = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null || (type = f.getType()) instanceof BasicType) {
				//
				continue;
				//
			} // if
				//
			if ((arrayType = cast(ArrayType.class, type)) != null) {
				//
				if ((basicType = arrayType.getBasicType()) instanceof BasicType) {
					//
					continue;
					//
				} // if
					//
				if (forName(getClassName(basicType)) == null) {
					//
					return false;
					//
				} // if
					//
			} else {
				//
				if (forName(getClassName(f.getType())) == null) {
					//
					return false;
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return true;
		//
	}

	private static Field[] getFields(final JavaClass instance) {
		return instance != null ? instance.getFields() : null;
	}

	private static String getClassName(final JavaClass instance) {
		return instance != null ? instance.getClassName() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static JavaClass toJavaClass(final ClassLoader cl, final String className) {
		//
		try (final InputStream is = cl != null
				? cl.getResourceAsStream(StringUtils.replace(className, ".", "/") + ".class")
				: null) {
			//
			return ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
		} catch (final IOException e) {
			//
			return null;
			//
		} // try
			//
	}

	private static String getClassName(final Type instance) {
		return instance != null ? instance.getClassName() : null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final Throwable e) {
			return null;
		}
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static <T> void removeIf(final Collection<T> instance, final Predicate<T> predicate) {
		if (instance != null && predicate != null) {
			instance.removeIf(predicate);
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

}