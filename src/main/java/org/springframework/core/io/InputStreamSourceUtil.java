package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSourceUtil {

	static InputStream getInputStream(final InputStreamSource instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

}