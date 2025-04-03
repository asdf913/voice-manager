package org.meeuw.functional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class TriConsumerUtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(method != null ? method.getName() : null);
			//
		}

	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> TriConsumerUtil.accept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(
				() -> TriConsumerUtil.accept(Reflection.newProxy(TriConsumer.class, new IH()), null, null, null));
		//
	}

}