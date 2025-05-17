package org.apache.pdfbox.pdmodel;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public interface PDPageUtil {

	static PDRectangle getMediaBox(final PDPage instance) {
		return instance != null ? instance.getMediaBox() : null;
	}

}