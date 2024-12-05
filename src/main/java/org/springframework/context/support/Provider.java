package org.springframework.context.support;

import javax.annotation.Nullable;

public interface Provider {

	@Nullable
	String getProviderName();

	static String getProviderName(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderName() : null;
	}

	@Nullable
	String getProviderVersion();

	static String getProviderVersion(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderVersion() : null;
	}

	@Nullable
	String getProviderPlatform();

	static String getProviderPlatform(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderPlatform() : null;
	}

}