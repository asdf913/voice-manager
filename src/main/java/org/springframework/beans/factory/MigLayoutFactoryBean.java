package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Functions;

import net.miginfocom.swing.MigLayout;

public class MigLayoutFactoryBean implements FactoryBean<MigLayout> {

	@Nullable
	private String[] arguments = null;

	public void setArguments(@Nullable final Object value) throws JsonProcessingException {
		//
		if (value == null) {
			//
			this.arguments = null;
			//
			return;
			//
		} // if
			//
		if (value instanceof String[] ss) {
			//
			this.arguments = ss;
			//
			return;
			//
		} else if (value instanceof Iterable<?>) {
			//
			setArguments(Util.toList(Util.map(Util.stream(IterableUtils.toList((Iterable<?>) value)), Util::toString))
					.toArray(new String[] {}));
			//
			return;
			//
		} // for
			//
		final IValue0<Object> iValue0 = getIValue0(value);
		//
		Object object = IValue0Util.getValue0(iValue0);
		//
		if (object instanceof String[] || object instanceof int[]) {
			//
			setArguments(object);
			//
			return;
			//
		} // if
			//
		try {
			//
			if ((object = testAndApply(StringUtils::isNotEmpty, Util.toString(value),
					x -> ObjectMapperUtil.readValue(new ObjectMapper(), x, Object.class), null)) == null
					|| object instanceof Iterable<?>) {
				//
				setArguments(object);
				//
			} else if (object instanceof Number || object instanceof Boolean || object instanceof String) {
				//
				setArguments(Collections.singleton(object));
				//
			} else {
				//
				throw new IllegalArgumentException(Util.toString(Util.getClass(object)));
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

	@Nullable
	private static IValue0<Object> getIValue0(final Object value) {
		//
		final Class<?> clz = Util.getClass(value);
		//
		IValue0<Object> result = null;
		//
		if (clz != null && clz.isArray()) {
			//
			final Class<?> componentType = clz.getComponentType();
			//
			if (Objects.equals(componentType, Integer.TYPE)) {
				//
				result = Unit.with(
						Util.toList(Arrays.stream((int[]) value).mapToObj(Integer::toString)).toArray(new String[] {}));
				//
			} else if (Objects.equals(componentType, Long.TYPE)) {
				//
				result = Unit.with(
						Util.toList(Arrays.stream((long[]) value).mapToObj(Long::toString)).toArray(new String[] {}));
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
				result = Unit.with(ints);
				//
			} else if (Objects.equals(componentType, Double.TYPE)) {
				//
				result = getIValue0((double[]) value);
				//
			} else if (Objects.equals(componentType, Float.TYPE)) {
				//
				result = getIValue0((float[]) value);
				//
			} else if (Objects.equals(componentType, Byte.TYPE)) {
				//
				result = getIValue0((byte[]) value);
				//
			} else if (Objects.equals(componentType, Boolean.TYPE)) {
				//
				result = getIValue0((boolean[]) value);
				//
			} else {
				//
				result = Unit.with(Util.toList(Util.map(Arrays.stream((Object[]) value), Util::toString))
						.toArray(new String[] {}));
				//
			} // if
				//
		} // if
			//
		return result;
		//
	}

	private static IValue0<Object> getIValue0(@Nullable final double[] ds) {
		//
		final String[] strings = ds != null ? new String[ds.length] : null;
		//
		for (int i = 0; ds != null && i < ds.length; i++) {
			//
			strings[i] = Double.toString(ds[i]);
			//
		} // for
			//
		return Unit.with(strings);
		//
	}

	private static IValue0<Object> getIValue0(@Nullable final float[] fs) {
		//
		final String[] strings = fs != null ? new String[fs.length] : null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			strings[i] = Float.toString(fs[i]);
			//
		} // for
			//
		return Unit.with(strings);
		//
	}

	private static IValue0<Object> getIValue0(@Nullable final byte[] bs) {
		//
		final String[] strings = bs != null ? new String[bs.length] : null;
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			strings[i] = Byte.toString(bs[i]);
			//
		} // for
			//
		return Unit.with(strings);
		//
	}

	private static IValue0<Object> getIValue0(@Nullable final boolean[] bs) {
		//
		final String[] strings = bs != null ? new String[bs.length] : null;
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			strings[i] = Boolean.toString(bs[i]);
			//
		} // for
			//
		return Unit.with(strings);
		//
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public MigLayout getObject() throws Exception {
		//
		final Map<Integer, Constructor<?>> constructorMap = Util.collect(Util.filter(
				testAndApply(Objects::nonNull, MigLayout.class.getDeclaredConstructors(), Arrays::stream, null), c -> {
					//
					if (c == null) {
						//
						return false;
						//
					} // if
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
				}), Collectors.toMap(c -> c != null ? c.getParameterCount() : null, Functions.identity()));
		//
		final Constructor<?> constructor = MapUtils.getObject(constructorMap, Integer.valueOf(length(arguments)));
		//
		if (constructor == null) {
			//
			throw new IllegalStateException("java.lang.reflect.Constructor is null");
			//
		} // if
			//
		return Util.cast(MigLayout.class, constructor.newInstance((Object[]) arguments));
		//
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Override
	public Class<?> getObjectType() {
		return String[].class;
	}

}