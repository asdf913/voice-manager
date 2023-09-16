package org.apache.commons.lang3.stream;

import java.util.stream.Stream;

import org.apache.commons.lang3.stream.Streams.FailableStream;

public interface FailableStreamUtil {

	static <O> Stream<O> stream(final FailableStream<O> instance) {
		return instance != null ? instance.stream() : null;
	}

}