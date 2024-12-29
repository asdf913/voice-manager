package org.apache.poi.ss.usermodel;

public interface SheetUtil {

	static Row createRow(final Sheet instance, final int rownum) {
		return instance != null ? instance.createRow(rownum) : null;
	}

	static Drawing<?> createDrawingPatriarch(final Sheet instance) {
		return instance != null ? instance.createDrawingPatriarch() : null;
	}

}