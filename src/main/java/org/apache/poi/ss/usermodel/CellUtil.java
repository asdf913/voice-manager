package org.apache.poi.ss.usermodel;

public interface CellUtil {

	static void setCellValue(final Cell instance, final String value) {
		if (instance != null) {
			instance.setCellValue(value);
		}
	}

	static void setCellStyle(final Cell instance, final CellStyle cellStyle) {
		if (instance != null) {
			instance.setCellStyle(cellStyle);
		}
	}

	static String getStringCellValue(final Cell instance) {
		return instance != null ? instance.getStringCellValue() : null;
	}

}