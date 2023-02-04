package com.fasterxml.jackson.databind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

class ObjectMapperUtilTest {

	@Test
	void testReadValue() throws JsonProcessingException {
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(null, null, null));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, null, null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, "", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, " ", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, "1", null));
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