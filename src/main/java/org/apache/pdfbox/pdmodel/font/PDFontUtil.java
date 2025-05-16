package org.apache.pdfbox.pdmodel.font;

public interface PDFontUtil {

	static PDFontDescriptor getFontDescriptor(final PDFont instance) {
		return instance != null ? instance.getFontDescriptor() : null;
	}

}