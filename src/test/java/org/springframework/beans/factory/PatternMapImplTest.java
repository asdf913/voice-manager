package org.springframework.beans.factory;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatternMapImplTest {

	@Test
	void testGetPattern() {
		//
		final PatternMap instance = new PatternMapImpl();
		//
		Assertions.assertNull(instance.getPattern(null));
		//
		Assertions.assertNull(instance.getPattern(null));
		//
		final String string = "";
		//
		final Pattern pattern = instance.getPattern(string);
		//
		Assertions.assertSame(pattern, instance.getPattern(string));
		//
	}

}