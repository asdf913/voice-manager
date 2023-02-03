package org.apache.poi.ss.usermodel;

public interface RowUtil {

	static Cell createCell(final Row instance, final int column) {
		return instance != null ? instance.createCell(column) : null;
	}

}