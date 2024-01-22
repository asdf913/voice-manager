package org.odftoolkit.simple;

import org.odftoolkit.simple.table.Table;

public interface SpreadsheetDocumentUtil {

	static Table getSheetByIndex(final SpreadsheetDocument instance, final int index) {
		return instance != null ? instance.getSheetByIndex(index) : null;
	}

}