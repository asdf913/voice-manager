package javassist.util.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;

public final class ProxyUtil {

	private ProxyUtil() {
	}

	public static <T, E extends Throwable> T createProxy(final Class<T> superClass, final MethodHandler mh)
			throws Throwable {
		return createProxy(superClass, mh, null);
	}

	public static <T, E extends Throwable> T createProxy(final Class<T> superClass, final MethodHandler mh,
			final FailableFunction<Class<?>, ?, E> function) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		Object instance = FailableFunctionUtil.apply(function, clz);
		//
		if (instance == null) {
			//
			instance = newInstance(clz != null ? clz.getDeclaredConstructor() : null);
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

	private static <T> T newInstance(final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}