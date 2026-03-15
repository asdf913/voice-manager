package com.fasterxml.jackson.databind;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.toolfactory.narcissus.Narcissus;

public interface ObjectMapperUtil {

	static <T> T readValue(final ObjectMapper instance, final String content, final Class<T> valueType)
			throws JsonProcessingException {
		//
		if (instance == null || content == null
				|| Narcissus.getField(content, getFieldByName(getClass(content), "value")) == null
				|| valueType == null) {
			//
			return null;
			//
		} // if
			//
		return instance.readValue(content, valueType);
		//
	}

	static <T> T readValue(final ObjectMapper instance, final InputStream src, final Class<T> valueType)
			throws IOException {
		//
		if (instance == null || src == null || valueType == null
				|| Narcissus.getField(instance, getFieldByName(getClass(instance), "_jsonFactory")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.readValue(src, valueType);
		//
	}

	static <T> T readValue(final ObjectMapper instance, final byte[] src, final Class<T> valueType) throws IOException {
		//
		if (instance == null || src == null || valueType == null
				|| Narcissus.getField(instance, getFieldByName(getClass(instance), "_jsonFactory")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.readValue(src, valueType);
		//
	}

	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final Iterable<Field> fs = collect(
				filter(stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						x -> Objects.equals(getName(x), name)),
				Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static String writeValueAsString(final ObjectMapper instance, final Object value) throws JsonProcessingException {
		//
		if (instance == null
				|| Narcissus.getField(instance, getFieldByName(getClass(instance), "_jsonFactory")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.writeValueAsString(value);
		//
	}

	static byte[] writeValueAsBytes(final ObjectMapper instance, final Object value) throws JsonProcessingException {
		//
		if (instance == null
				|| Narcissus.getField(instance, getFieldByName(getClass(instance), "_jsonFactory")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.writeValueAsBytes(value);
		// s
	}

}