package freemarker.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringTemplateLoaderUtilTest {

	@Test
	void testPutTemplate() {
		//
		Assertions.assertDoesNotThrow(() -> StringTemplateLoaderUtil.putTemplate(new StringTemplateLoader(), null, ""));
		//
	}

}