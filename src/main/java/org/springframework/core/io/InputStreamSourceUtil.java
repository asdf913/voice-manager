package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

public interface InputStreamSourceUtil {

	@Nullable
	static InputStream getInputStream(@Nullable final InputStreamSource instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

}