package org.apache.bcel.classfile;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface ClassParserUtil {

	static JavaClass parse(final ClassParser instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readField(instance, "dataInputStream", true) == null) {
				//
				return null;
				//
			} else if (Objects.equals(Boolean.TRUE, FieldUtils.readField(instance, "fileOwned", true))) {
				//
				final Object isZip = FieldUtils.readField(instance, "isZip", true);
				//
				if (Objects.equals(Boolean.FALSE, isZip) && FieldUtils.readField(instance, "fileName", true) == null) {
					//
					return null;
					//
				} else if (Objects.equals(Boolean.TRUE, isZip)
						&& FieldUtils.readField(instance, "zipFile", true) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.parse();
		//
	}

}