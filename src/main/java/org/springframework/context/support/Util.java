package org.springframework.context.support;

import java.lang.reflect.Member;

import javax.annotation.Nullable;

public interface Util {

	@Nullable
	static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

}