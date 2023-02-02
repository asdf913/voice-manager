package org.apache.poi.ooxml;

public interface POIXMLDocumentUtil {

	static POIXMLProperties getProperties(final POIXMLDocument instance) {
		return instance != null ? instance.getProperties() : null;
	}

}