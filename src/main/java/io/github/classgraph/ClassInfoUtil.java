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
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.lang3.ArrayUtils;
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
		ContinueOrFalse cof = null;
		//
		for (int i = 0; i < length(fs); i++) {
			//
			if ((cof = checkType(getType(ArrayUtils.get(fs, i)))) == null) {
				//
				throw new IllegalStateException();
				//
			} else if (Objects.equals(cof, ContinueOrFalse.CONTINUE)) {
				//
				continue;
				//
			} else if (Objects.equals(cof, ContinueOrFalse.FALSE)) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return true;
		//
	}

	private static enum ContinueOrFalse {
		CONTINUE, FALSE;
	}

	private static ContinueOrFalse checkType(final Type type) {
		//
		if (type instanceof BasicType) {
			//
			return ContinueOrFalse.CONTINUE;
			//
		} // if
			//
		final ArrayType arrayType = cast(ArrayType.class, type);
		//
		if (arrayType != null) {
			//
			final Type basicType = arrayType.getBasicType();
			//
			if (basicType instanceof BasicType) {
				//
				return ContinueOrFalse.CONTINUE;
				//
			} // if
				//
			if (forName(TypeUtil.getClassName(basicType)) == null) {
				//
				return ContinueOrFalse.FALSE;
				//
			} // if
				//
		} else {
			//
			if (forName(TypeUtil.getClassName(type)) == null) {
				//
				return ContinueOrFalse.FALSE;
				//
			} // if
				//
		} // if
			//
		return ContinueOrFalse.CONTINUE;
		//
	}

	private static Type getType(final Field instance) {
		return instance != null && instance.getConstantPool() != null ? instance.getType() : null;
	}

	private static int length(final Object[] instance) {
		return instance != null ? instance.length : 0;
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