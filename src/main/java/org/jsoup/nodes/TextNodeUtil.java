package org.jsoup.nodes;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface TextNodeUtil {

	static String text(final TextNode instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readField(instance, "value", true) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.text();
		//
	}

}