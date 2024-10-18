package org.apache.commons.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class TextStringBuilderUtilTest {

	@Test
	void testClear() {
		//
		Assertions.assertNull(TextStringBuilderUtil.clear(null));
		//
		final TextStringBuilder tsb = cast(TextStringBuilder.class,
				Narcissus.allocateInstance(TextStringBuilder.class));
		//
		Assertions.assertSame(tsb, TextStringBuilderUtil.clear(tsb));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}