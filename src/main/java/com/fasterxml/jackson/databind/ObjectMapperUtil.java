package com.fasterxml.jackson.databind;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ObjectMapperUtil {

	static <T> T readValue(final ObjectMapper instance, final String content, final Class<T> valueType)
			throws JsonProcessingException {
		//
		return instance != null && StringUtils.isNotBlank(content) && valueType != null
				? instance.readValue(content, valueType)
				: null;
		//
	}

	static <T> T readValue(final ObjectMapper instance, final InputStream src, final Class<T> valueType)
			throws IOException {
		//
		return instance != null && src != null && valueType != null ? instance.readValue(src, valueType) : null;
		//
	}

	static String writeValueAsString(final ObjectMapper instance, final Object value) throws JsonProcessingException {
		return instance != null ? instance.writeValueAsString(value) : null;
	}

	static byte[] writeValueAsBytes(final ObjectMapper instance, final Object value) throws JsonProcessingException {
		return instance != null ? instance.writeValueAsBytes(value) : null;
	}

}