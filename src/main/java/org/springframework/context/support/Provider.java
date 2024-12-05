package org.springframework.context.support;

import javax.annotation.Nullable;

public interface Provider {

	@Nullable
	String getProviderName();

	@Nullable
	String getProviderVersion();

	@Nullable
	String getProviderPlatform();

	static String getProviderPlatform(final Provider instance) {
		return instance != null ? instance.getProviderPlatform() : null;
	}

}