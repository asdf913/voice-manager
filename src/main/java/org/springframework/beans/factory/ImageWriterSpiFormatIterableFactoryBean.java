package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
												Util.getDeclaredField(ServiceRegistry.class, "categoryMap"))),
								ImageWriterSpi.class),
						x -> Narcissus.getField(x, Util.getDeclaredField(Util.getClass(x), "map")), null));
		//
		final List<String> classNames = testAndApply(Objects::nonNull, Util.toList(
				Util.map(Util.stream(Util.keySet(imageWriterSpis)), x -> Util.getName(Util.cast(Class.class, x)))),
				ArrayList::new, null);
		//
		final String commonPrefix = StringUtils.getCommonPrefix(toArray(classNames, new String[] {}));
		//
		for (int i = 0; i < IterableUtils.size(classNames); i++) {
			//
			set(classNames, i, StringUtils
					.substringBefore(StringUtils.replace(IterableUtils.get(classNames, i), commonPrefix, ""), '.'));
			//
		} // if
			//
		return classNames;
		//
	}

	private static <E> void set(@Nullable final List<E> instance, final int index, final E element) {
		if (instance != null) {
			instance.set(index, element);
		}
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
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