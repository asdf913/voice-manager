package it.unimi.dsi.fastutil;

public interface PairUtil {

	static <R> R right(final Pair<?, R> instance) {
		return instance != null ? instance.right() : null;
	}

}