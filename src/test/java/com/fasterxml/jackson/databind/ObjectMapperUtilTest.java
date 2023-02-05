package com.fasterxml.jackson.databind;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

class ObjectMapperUtilTest {

	@Test
	void testReadValue() throws IOException {
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(null, (String) null, null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(null, (InputStream) null, null));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, (String) null, null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, "", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, " ", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, "1", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, (InputStream) null, null));
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, is, null));
			//
		} // try
			//
	}

	@Test
	void testWriteValueAsString() throws JsonProcessingException {
		//
		Assertions.assertNull(ObjectMapperUtil.writeValueAsString(null, null));
		//
	}

	@Test
	void testWriteValueAsBytes() throws JsonProcessingException {
		//
		Assertions.assertNull(ObjectMapperUtil.writeValueAsBytes(null, null));
		//
	}

}