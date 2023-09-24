package org.apache.poi.ss.usermodel;

public interface FormulaEvaluatorUtil {

	static CellValue evaluate(final FormulaEvaluator instance, final Cell cell) {
		return instance != null ? instance.evaluate(cell) : null;
	}

}