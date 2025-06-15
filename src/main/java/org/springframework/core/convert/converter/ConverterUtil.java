package org.springframework.core.convert.converter;

import javax.annotation.Nullable;

public interface ConverterUtil {

	@Nullable
	static <S, T> T convert(@Nullable final Converter<S, T> instance, final S source) {
		return instance != null ? instance.convert(source) : null;
	}

}