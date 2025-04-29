package org.apache.bcel.classfile;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class FieldOrMethodUtilTest {

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			throw new Throwable(thisMethod != null ? thisMethod.getName() : null);
			//
		}

	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(FieldOrMethodUtil.getName(null));
		//
		Assertions.assertNull(FieldOrMethodUtil.getName(ProxyUtil.createProxy(FieldOrMethod.class, new MH())));
		//
	}

}