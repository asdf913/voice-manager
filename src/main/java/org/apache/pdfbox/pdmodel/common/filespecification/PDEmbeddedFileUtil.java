package org.apache.pdfbox.pdmodel.common.filespecification;

public interface PDEmbeddedFileUtil {

	static void setSubtype(final PDEmbeddedFile instance, final String mimeType) {
		if (instance != null && instance.getCOSObject() != null) {
			instance.setSubtype(mimeType);
		}
	}

	static void setSize(final PDEmbeddedFile instance, final int size) {
		if (instance != null && instance.getCOSObject() != null) {
			instance.setSize(size);
		}
	}

}