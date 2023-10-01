package org.springframework.context.support;

import java.lang.reflect.Member;

public interface Util {

	static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

}