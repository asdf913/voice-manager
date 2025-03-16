package org.javatuples.valueintf;

public interface IValue2Util {

	static <X> X getValue2(final IValue2<X> instance) {
		return instance != null ? instance.getValue2() : null;
	}

}