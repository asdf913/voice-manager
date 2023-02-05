package org.apache.poi.ss.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Proxy;

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