package org.jsoup.nodes;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;

public final class ElementUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ElementUtil.class);

	private static final String CHILD_NODES = "childNodes";

	private ElementUtil() {
	}

	@Nullable
	public static String text(@Nullable final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.text()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Elements getElementsByTag(@Nullable final Element instance, final String tagName) {
		//
		try {
			//
			return instance != null && StringUtils.isNotEmpty(tagName)
					&& FieldUtils.readField(instance, "tag", true) != null ? instance.getElementsByTag(tagName) : null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Elements selectXpath(@Nullable final Element instance, @Nullable final String xpath) {
		return instance != null && xpath != null ? instance.selectXpath(xpath) : null;
	}

	@Nullable
	public static Elements select(@Nullable final Element instance, final String cssQuery) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null
					? instance.select(cssQuery)
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Elements children(@Nullable final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.children()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Element nextElementSibling(@Nullable final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	@Nullable
	public static String tagName(@Nullable final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, "tag", true) != null ? instance.tagName() : null;
			//
		} catch (IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Element child(@Nullable final Element instance, final int index) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.child(index)
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	public static int childrenSize(@Nullable final Element instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		try {
			//
			final Field field = clz != null ? clz.getDeclaredField(CHILD_NODES) : null;
			//
			if (Narcissus.getField(instance, field) == null) {
				//
				return 0;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.childrenSize();
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	public static List<Element> parents(@Nullable final Element instance) {
		return instance != null ? instance.parents() : null;
	}

	@Nullable
	public static final Element parent(@Nullable final Element instnace) {
		return instnace != null ? instnace.parent() : null;
	}

	@Nullable
	public static Elements nextElementSiblings(@Nullable final Element instnace) {
		return instnace != null ? instnace.nextElementSiblings() : null;
	}

	@Nullable
	public static String html(@Nullable final Element instance) {
		//
		final Collection<Field> fs = collect(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(getClass(instance)), Arrays::stream, null),
						f -> Objects.equals(getName(f), CHILD_NODES)),
				Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getObjectField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.html() : null;
		//
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static <T, R, A> R collect(@Nullable final Stream<T> instance,
			@Nullable final Collector<? super T, A, R> collector) {
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: instance;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static Field[] getDeclaredFields(@Nullable final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredFields() : null;
	}

}