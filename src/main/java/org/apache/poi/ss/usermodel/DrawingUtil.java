package org.apache.poi.ss.usermodel;

public interface DrawingUtil {

	static Comment createCellComment(final Drawing<?> instance, final ClientAnchor anchor) {
		return instance != null ? instance.createCellComment(anchor) : null;
	}

}