package org.springframework.context.support;

public interface Provider {

	String getProviderName();

	String getProviderVersion();

	String getProviderPlatform();

}