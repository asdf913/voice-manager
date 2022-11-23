package org.javatuples.valueintf;

public interface IValue0Util {

	static <X> X getValue0(final IValue0<X> instance) {
		return instance != null ? instance.getValue0() : null;
	}

}