package org.apache.pdfbox.pdmodel.font;

public interface PDFontDescriptorUtil {

	static float getAscent(final PDFontDescriptor instance, final float defaultValue) {
		return instance != null && instance.getCOSObject() != null ? instance.getAscent() : defaultValue;
	}

	static float getDescent(final PDFontDescriptor instance, final float defaultValue) {
		return instance != null && instance.getCOSObject() != null ? instance.getDescent() : defaultValue;
	}

}