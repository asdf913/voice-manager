package org.springframework.core.convert.converter;

public interface ConverterUtil {

	static <S, T> T convert(final Converter<S, T> instance, final S source) {
		return instance != null ? instance.convert(source) : null;
	}

}