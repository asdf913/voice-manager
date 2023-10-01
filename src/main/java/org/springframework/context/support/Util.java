package org.springframework.context.support;

import java.lang.reflect.Member;

public interface Util {

	static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

}