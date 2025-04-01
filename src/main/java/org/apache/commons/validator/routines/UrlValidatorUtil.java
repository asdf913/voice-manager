package org.apache.commons.validator.routines;

public interface UrlValidatorUtil {

	static boolean isValid(final UrlValidator instance, final String value) {
		return instance != null && instance.isValid(value);
	}

}