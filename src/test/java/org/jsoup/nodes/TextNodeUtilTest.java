package org.jsoup.nodes;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class TextNodeUtilTest {

	@Test
	void testText() throws IllegalAccessException {
		//
		Assertions.assertNull(TextNodeUtil.text(null));
		//
		final TextNode textNode = cast(TextNode.class, Narcissus.allocateInstance(TextNode.class));
		//
		Assertions.assertNull(TextNodeUtil.text(textNode));
		//
		final String string = "";
		//
		FieldUtils.writeField(textNode, "value", string, true);
		//
		Assertions.assertSame(string, TextNodeUtil.text(textNode));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}