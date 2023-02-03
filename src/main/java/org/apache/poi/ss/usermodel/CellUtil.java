package org.apache.poi.ss.usermodel;

public interface CellUtil {

	static void setCellValue(final Cell instance, final String value) {
		if (instance != null) {
			instance.setCellValue(value);
		}
	}

}