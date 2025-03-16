package org.javatuples.valueintf;

public interface IValue1Util {

	static <X> X getValue1(final IValue1<X> instance) {
		return instance != null ? instance.getValue1() : null;
	}

}