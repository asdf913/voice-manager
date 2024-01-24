package org.apache.poi.ss.usermodel;

public interface FormulaErrorUtil {

	static String getString(final FormulaError instance) {
		return instance != null ? instance.getString() : null;
	}

}