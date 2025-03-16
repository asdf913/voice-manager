package org.javatuples.valueintf;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;

class IValue1UtilTest {

	@Test
	void testGetValue1() throws Throwable {
		//
		final Iterable<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			if (classInfo == null) {
				//
				continue;
				//
			} // if
				//
			try {
				//
				if (isAssignableFrom(IValue1.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final IValue1<?> iValue1 = cast(IValue1.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> IValue1Util.getValue1(iValue1), name);
					//
				} // if
					//
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}