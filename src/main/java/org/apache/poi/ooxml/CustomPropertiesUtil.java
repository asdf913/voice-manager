package org.apache.poi.ooxml;

import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;

public interface CustomPropertiesUtil {

	static void addProperty(final CustomProperties instance, final String name, final String value) {
		if (instance != null) {
			instance.addProperty(name, value);
		}
	}

}