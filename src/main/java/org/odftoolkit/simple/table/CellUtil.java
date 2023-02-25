package org.odftoolkit.simple.table;

public interface CellUtil {

	static String getStringValue(final Cell instance) {
		return instance != null ? instance.getStringValue() : null;
	}

}