package j2html.tags;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

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
		Assertions.assertNull(TagUtil.attr(createProxy(Tag.class, new MH(), x -> {
			//
			final Constructor<?> constructor = x != null ? x.getDeclaredConstructor(String.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		}), null, null));
		//
	}

	private static <T, E extends Throwable> T createProxy(final Class<T> superClass, final MethodHandler mh,
			final FailableFunction<Class<?>, ?, E> function) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		Object instance = function != null ? function.apply(clz) : null;
		//
		if (instance == null) {
			//
			final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
			//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} // if
			//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return (T) cast(clz, instance);
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}