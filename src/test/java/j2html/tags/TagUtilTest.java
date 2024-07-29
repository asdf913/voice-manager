package j2html.tags;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class TagUtilTest {

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Tag) {
				//
				if (Objects.equals(methodName, "attr")) {
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
	void testAttr() throws Throwable {
		//
		Assertions.assertNull(TagUtil.attr(null, null, null));
		//
		Assertions.assertNull(TagUtil.attr(ProxyUtil.createProxy(Tag.class, new MH(), x -> {
			//
			final Constructor<?> constructor = x != null ? x.getDeclaredConstructor(String.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		}), null, null));
		//
	}

}