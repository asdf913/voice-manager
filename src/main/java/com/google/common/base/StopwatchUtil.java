package com.google.common.base;

import java.time.Duration;

import javax.annotation.Nullable;

public interface StopwatchUtil {

	static Duration elapsed(@Nullable final Stopwatch instance) {
		return instance != null ? instance.elapsed() : null;
	}

}