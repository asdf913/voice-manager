package io.github.classgraph;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface ScanResultUtil {

	static ClassInfoList getAllClasses(final ScanResult instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readDeclaredField(instance, "closed", true) == null
					|| FieldUtils.readDeclaredField(instance, "scanSpec", true) == null) {
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
		return instance.getAllClasses();
		//
	}

}