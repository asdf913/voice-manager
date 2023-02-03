package org.apache.poi.ooxml;

import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;

public interface POIXMLPropertiesUtil {

	static CustomProperties getCustomProperties(final POIXMLProperties instance) {
		return instance != null ? instance.getCustomProperties() : null;
	}

}