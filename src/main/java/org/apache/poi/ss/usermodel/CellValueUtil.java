package org.apache.poi.ss.usermodel;

public interface CellValueUtil {

	static CellType getCellType(final CellValue instance) {
		return instance != null ? instance.getCellType() : null;
	}

	static String getStringValue(final CellValue instance) {
		return instance != null ? instance.getStringValue() : null;
	}

}