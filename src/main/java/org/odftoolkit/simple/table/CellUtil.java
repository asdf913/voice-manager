package org.odftoolkit.simple.table;

public interface CellUtil {

	static String getStringValue(final Cell instance) {
		//
		if (instance == null || instance.getOdfElement() == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getStringValue();
		//
	}

}