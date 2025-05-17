package org.apache.pdfbox.pdmodel;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

public interface PDPageUtil {

	static PDRectangle getMediaBox(final PDPage instance) {
		return instance != null ? instance.getMediaBox() : null;
	}

	static List<PDAnnotation> getAnnotations(final PDPage instance) throws IOException {
		return instance != null ? instance.getAnnotations() : null;
	}

}