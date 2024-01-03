package org.apache.poi.ss.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.util.Objects;

public interface WorkbookUtil {

	static Sheet createSheet(final Workbook instance) {
		return instance != null ? instance.createSheet() : null;
	}

	static Sheet createSheet(final Workbook instance, final String sheetname) {
		return instance != null ? instance.createSheet(sheetname) : null;
	}

	static CellStyle createCellStyle(final Workbook instance) {
		return instance != null ? instance.createCellStyle() : null;
	}

	static Sheet getSheetAt(final Workbook instance, final int index) {
		return instance != null ? instance.getSheetAt(index) : null;
	}

	static Sheet getSheet(final Workbook instance, final String name) {
		//
		if (instance == null
				|| (Objects.equals("org.apache.poi.xssf.usermodel.XSSFWorkbook", getName(getClass(instance)))
						&& name == null)) {
			//
			return null;
			//
		} // if
			//
		return instance.getSheet(name);
		//
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	static CreationHelper getCreationHelper(final Workbook instance) {
		return instance != null ? instance.getCreationHelper() : null;
	}

	static void write(final Workbook instance, final OutputStream stream) throws IOException {
		//
		if (instance != null && (stream != null || Proxy.isProxyClass(getClass(instance)))) {
			//
			instance.write(stream);
			//
		} // if
			//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

}