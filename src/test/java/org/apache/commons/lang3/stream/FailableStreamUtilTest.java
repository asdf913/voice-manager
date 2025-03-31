package org.apache.commons.lang3.stream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class FailableStreamUtilTest {

	@Test
	void testStream() {
		//
		Assertions.assertNotNull(FailableStreamUtil.stream(new FailableStream<>(Stream.empty())));
		//
	}

	@Test
	void testMap() {
		//
		Assertions.assertNotNull(FailableStreamUtil.map(new FailableStream<>(Stream.ofNullable(null)), null));
		//
	}

	@Test
	void testForEach() {
		//
		final FailableStream<?> fs = new FailableStream<>(Stream.ofNullable(null));
		//
		Assertions.assertDoesNotThrow(() -> FailableStreamUtil.forEach(fs, null));
		//
		Assertions.assertDoesNotThrow(() -> FailableStreamUtil.forEach(fs, FailableConsumer.nop()));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = FailableStreamUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}