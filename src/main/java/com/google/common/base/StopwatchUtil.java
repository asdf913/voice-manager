package com.google.common.base;

import java.time.Duration;

public interface StopwatchUtil {

	static Duration elapsed(final Stopwatch instance) {
		return instance != null ? instance.elapsed() : null;
	}

}