package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class ImageWriterSpiFormatIterableFactoryBean implements FactoryBean<Iterable<String>> {

	@Override
	public Iterable<String> getObject() throws Exception {
		//
		final Map<?, ?> imageWriterSpis = Util.cast(Map.class,
				testAndApply(Objects::nonNull,
						Util.get(
								Util.cast(Map.class,
										Narcissus.getObjectField(IIORegistry.getDefaultInstance(),
												getDeclaredField(ServiceRegistry.class, "categoryMap"))),
								ImageWriterSpi.class),
						x -> Narcissus.getField(x, getDeclaredField(Util.getClass(x), "map")), null));
		//
		final List<String> classNames = testAndApply(Objects::nonNull,
				Util.toList(
						Util.map(Util.stream(keySet(imageWriterSpis)), x -> Util.getName(Util.cast(Class.class, x)))),
				ArrayList::new, null);
		//
		final String commonPrefix = StringUtils.getCommonPrefix(toArray(classNames, new String[] {}));
		//
		for (int i = 0; classNames != null && i < classNames.size(); i++) {
			//
			classNames.set(i, StringUtils
					.substringBefore(StringUtils.replace(IterableUtils.get(classNames, i), commonPrefix, ""), '.'));
			//
		} // if
			//
		return classNames;
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	private static <K> Set<K> keySet(@Nullable final Map<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Iterable.class;
	}

}