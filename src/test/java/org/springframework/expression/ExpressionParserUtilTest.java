package org.springframework.expression;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class ExpressionParserUtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ExpressionParser) {
				//
				if (Objects.equals(methodName, "parseExpression") && Arrays
						.equals(method != null ? method.getParameterTypes() : null, new Class<?>[] { String.class })) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testParseExpression() {
		//
		Assertions.assertNull(ExpressionParserUtil.parseExpression(null, null));
		//
		Assertions.assertNull(
				ExpressionParserUtil.parseExpression(Reflection.newProxy(ExpressionParser.class, new IH()), null));
		//
	}

}
