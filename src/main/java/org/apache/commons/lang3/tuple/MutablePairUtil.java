package org.apache.commons.lang3.tuple;

public interface MutablePairUtil {

	static <L> void setLeft(final MutablePair<L, ?> instance, final L left) {
		//
		if (instance != null) {
			//
			instance.setLeft(left);
			//
		} // if
			//
	}

	static <R> void setRight(final MutablePair<?, R> instance, final R right) {
		//
		if (instance != null) {
			//
			instance.setRight(right);
			//
		} // if
			//
	}

}