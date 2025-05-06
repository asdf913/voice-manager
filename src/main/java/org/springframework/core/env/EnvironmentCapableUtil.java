package org.springframework.core.env;

public interface EnvironmentCapableUtil {

	static Environment getEnvironment(final EnvironmentCapable instance) {
		return instance != null ? instance.getEnvironment() : null;
	}

}