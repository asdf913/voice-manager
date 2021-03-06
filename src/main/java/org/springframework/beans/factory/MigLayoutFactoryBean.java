package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Functions;

import net.miginfocom.swing.MigLayout;

public class MigLayoutFactoryBean implements FactoryBean<MigLayout> {

	private String[] arguments = null;

	public void setArguments(final Object value) throws JsonProcessingException {
		//
		if (value == null) {
			//
			this.arguments = null;
			//
			return;
			//
		} // if
			//
		if (value instanceof String[]) {
			//
			this.arguments = (String[]) value;
			//
			return;
			//
		} else if (value instanceof Iterable<?>) {
			//
			setArguments(IterableUtils.toList((Iterable<?>) value).stream().map(MigLayoutFactoryBean::toString).toList()
					.toArray(new String[] {}));
			//
			return;
			//
		} // for
			//
		final Class<?> clz = value != null ? value.getClass() : null;
		//
		if (clz != null && clz.isArray()) {
			//
			final Class<?> componentType = clz.getComponentType();
			//
			if (Objects.equals(componentType, Integer.TYPE)) {
				//
				this.arguments = Arrays.stream((int[]) value).mapToObj(Integer::toString).toList()
						.toArray(new String[] {});
				//
			} else if (Objects.equals(componentType, Long.TYPE)) {
				//
				this.arguments = Arrays.stream((long[]) value).mapToObj(Long::toString).toList()
						.toArray(new String[] {});
				//
			} else if (Objects.equals(componentType, Short.TYPE)) {
				//
				final short[] ss = (short[]) value;
				//
				final int[] ints = new int[ss.length];
				//
				for (int i = 0; i < ss.length; i++) {
					//
					ints[i] = ss[i];
					//
				} // for
					//
				setArguments(ints);
				//
			} else if (Objects.equals(componentType, Double.TYPE)) {
				//
				final double[] ds = (double[]) value;
				//
				final String[] strings = new String[ds.length];
				//
				for (int i = 0; i < ds.length; i++) {
					//
					strings[i] = Double.toString(ds[i]);
					//
				} // for
					//
				setArguments(strings);
				//
			} else if (Objects.equals(componentType, Float.TYPE)) {
				//
				final float[] fs = (float[]) value;
				//
				final String[] strings = new String[fs.length];
				//
				for (int i = 0; i < fs.length; i++) {
					//
					strings[i] = Float.toString(fs[i]);
					//
				} // for
					//
				setArguments(strings);
				//
			} else if (Objects.equals(componentType, Byte.TYPE)) {
				//
				final byte[] ds = (byte[]) value;
				//
				final String[] strings = new String[ds.length];
				//
				for (int i = 0; i < ds.length; i++) {
					//
					strings[i] = Byte.toString(ds[i]);
					//
				} // for
					//
				setArguments(strings);
				//
			} else if (Objects.equals(componentType, Boolean.TYPE)) {
				//
				final boolean[] ds = (boolean[]) value;
				//
				final String[] strings = new String[ds.length];
				//
				for (int i = 0; i < ds.length; i++) {
					//
					strings[i] = Boolean.toString(ds[i]);
					//
				} // for
					//
				setArguments(strings);
				//
			} else {
				//
				this.arguments = Arrays.stream((Object[]) value).map(MigLayoutFactoryBean::toString).toList()
						.toArray(new String[] {});
				//
			} // if
				//
			return;
			//
		} // if
			//
		try {
			//
			final Object object = testAndApply(StringUtils::isNotEmpty, toString(value),
					x -> new ObjectMapper().readValue(x, Object.class), null);
			//
			if (object == null || object instanceof Iterable<?>) {
				//
				setArguments(object);
				//
			} else if (object instanceof Number || object instanceof Boolean || object instanceof String) {
				//
				setArguments(Collections.singleton(object));
				//
			} else {
				//
				throw new IllegalArgumentException(object != null ? toString(object.getClass()) : null);
				//
			} // if
				//
		} catch (final JsonProcessingException e) {
			//
			setArguments(new Object[] { value });
			//
		} // try
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Override
	public MigLayout getObject() throws Exception {
		//
		final Map<Integer, Constructor<?>> constructorMap = testAndApply(Objects::nonNull,
				MigLayout.class.getDeclaredConstructors(), Arrays::stream, null).filter(c -> {
					//
					if (c != null) {
						//
						final Class<?>[] pts = c.getParameterTypes();
						//
						if (pts != null) {
							//
							for (final Class<?> pt : pts) {
								//
								if (!Objects.equals(pt, String.class)) {
									//
									return false;
								} // if
									//
							} // for
								//
						} // if
							//
						return true;
						//
					} // if
						//
					return false;
					//
				}).collect(Collectors.toMap(c -> c != null ? c.getParameterCount() : null, Functions.identity()));
		//
		final Constructor<?> constructor = MapUtils.getObject(constructorMap,
				Integer.valueOf(arguments != null ? arguments.length : 0));
		//
		if (constructor == null) {
			//
			throw new IllegalStateException("java.lang.reflect.Constructor is null");
			//
		} // if
			//
		return cast(MigLayout.class, constructor.newInstance((Object[]) arguments));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return String[].class;
	}

}