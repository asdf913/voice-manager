package org.springframework.core.env;

import javax.annotation.Nullable;

public interface EnvironmentCapableUtil {

	@Nullable
	static Environment getEnvironment(@Nullable final EnvironmentCapable instance) {
		return instance != null ? instance.getEnvironment() : null;
	}

}