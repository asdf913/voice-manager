package org.apache.commons.lang3.tuple;

public interface TripleUtil {

	static <L> L getLeft(final Triple<L, ?, ?> instance) {
		return instance != null ? instance.getLeft() : null;
	}

	static <M> M getMiddle(final Triple<?, M, ?> instance) {
		return instance != null ? instance.getMiddle() : null;
	}

	static <R> R getRight(final Triple<?, ?, R> instance) {
		return instance != null ? instance.getRight() : null;
	}

}