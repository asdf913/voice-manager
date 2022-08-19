package org.apache.bcel.classfile;

import java.io.IOException;

public interface ClassParserUtil {

	static JavaClass parse(final ClassParser instance) throws IOException {
		return instance != null ? instance.parse() : null;
	}

}