package io.github.classgraph;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface ClassGraphUtil {

	static ScanResult scan(final ClassGraph instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readDeclaredField(instance, "scanSpec", true) == null
					|| FieldUtils.readDeclaredField(instance, "reflectionUtils", true) == null) {
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
		return instance.scan();
		//
	}

}