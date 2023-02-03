package org.apache.poi.ss.usermodel;

public interface WorkbookUtil {

	static Sheet createSheet(final Workbook instance) {
		return instance != null ? instance.createSheet() : null;
	}

}