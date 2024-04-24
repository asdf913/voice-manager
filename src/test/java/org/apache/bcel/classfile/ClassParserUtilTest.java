package org.apache.bcel.classfile;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ClassParserUtilTest {

	@Test
	void testParse() throws IOException, IllegalAccessException {
		//
		Assertions.assertNull(
				ClassParserUtil.parse(cast(ClassParser.class, Narcissus.allocateInstance(ClassParser.class))));
		//
		try (final InputStream is = ClassParserUtilTest.class.getResourceAsStream(
				String.format("/%1$s.class", StringUtils.replace(ClassParserUtilTest.class.getName(), ".", "/")))) {
			//
			final ClassParser classParser = new ClassParser(is, null);
			//
			FieldUtils.writeDeclaredField(classParser, "fileOwned", true, true);
			//
			Assertions.assertNull(ClassParserUtil.parse(classParser));
			//
			FieldUtils.writeDeclaredField(classParser, "isZip", true, true);
			//
			Assertions.assertNull(ClassParserUtil.parse(classParser));
			//
		} // try
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}