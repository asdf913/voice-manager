package org.junit.jupiter.api;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.junit.jupiter.api.function.Executable;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.toolfactory.narcissus.Narcissus;

public class AssertionsUtil {

	private static ObjectMapper objectMapper = null;

	private AssertionsUtil() {
	}

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper().setDefaultPropertyInclusion(Include.NON_NULL);
		}
		return objectMapper;
	}

	public static <T extends Throwable> void assertThrowsAndEquals(final Class<T> expectedType, final String json,
			final Executable executable) throws IOException {
		//
		assertThrowsAndEquals(expectedType, json, executable, getObjectMapper(), "stackTrace");
		//
	}

	private static <T extends Throwable> void assertThrowsAndEquals(final Class<T> expectedType, final String json,
			final Executable executable, final ObjectMapper objectMapper, final String... excludedFields)
			throws IOException {
		//
		final Throwable throwable = Assertions.assertThrows(expectedType, executable);
		//
		final Throwable th = SerializationUtils.clone(throwable);
		//
		if (th != null) {
			//
			new FailableStream<>(
					FieldUtils
							.getAllFieldsList(th.getClass()).stream().filter(
									f -> f != null && !Modifier.isStatic(f.getModifiers())
											&& (Objects.equals(f.getType(), Throwable.class)
													|| Objects.equals(f.getType(), StackTraceElement[].class))))
					.forEach(f -> {
						//
						if (f != null) {
							//
							if (Objects.equals("java.base", f.getDeclaringClass().getModule().getName())) {
								//
								Narcissus.setObjectField(th, f, null);
								//
								return;
								//
							} // if
								//
							f.setAccessible(true);
							//
							f.set(th, null);
							//
						} // if
							//
					});
			//
			final Map<?, ?> map = objectMapper.readValue(objectMapper.writeValueAsBytes(th), Map.class);
			//
			Assertions.assertEquals(json,
					toString(map.entrySet().stream()
							.filter(x -> x == null || excludedFields == null || excludedFields.length == 0
									|| !Arrays.asList(excludedFields).contains(x.getKey()))
							.collect(Collectors.toMap(Entry::getKey, Entry::getValue))));
			//

		} else {
			//
			final Map<?, ?> map = objectMapper.readValue(objectMapper.writeValueAsBytes(throwable), Map.class);
			//
			Assertions.assertEquals(json,
					toString(map.entrySet().stream()
							.filter(x -> x == null || excludedFields == null || excludedFields.length == 0
									|| !Arrays.asList(excludedFields).contains(x.getKey()))
							.collect(Collectors.toMap(Entry::getKey, Entry::getValue))));
			//
		} // if
			//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

}