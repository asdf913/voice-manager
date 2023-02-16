package org.apache.commons.lang3.function;

import java.io.IOException;
import java.util.List;

import domain.Pronounication;

public interface OnlineNHKJapanesePronunciationsAccentFailableFunction
		extends FailableFunction<String, List<Pronounication>, IOException> {
}