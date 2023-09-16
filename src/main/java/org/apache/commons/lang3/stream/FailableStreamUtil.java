package org.apache.commons.lang3.stream;

import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;

public interface FailableStreamUtil {

	static <O> Stream<O> stream(final FailableStream<O> instance) {
		return instance != null ? instance.stream() : null;
	}

	static <T, R> FailableStream<R> map(final FailableStream<T> instance, final FailableFunction<T, R, ?> mapper) {
		return instance != null
				&& stream(instance) != null
				? instance.map(mapper) 
						: null;
	}

}