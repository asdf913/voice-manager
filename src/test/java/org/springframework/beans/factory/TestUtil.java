package org.springframework.beans.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.ReflectionUtils;

import io.github.toolfactory.narcissus.Narcissus;

public interface TestUtil {

	static Entry<Field, Object> getFieldWithUrlAnnotationAndValue(final Class<?> c) throws ClassNotFoundException {
		//
		final Class<?> clz = Class.forName("org.springframework.beans.factory.URL");
		//
		final List<Field> fs = c != null ? FieldUtils.getAllFieldsList(c) : null;
		//
		Field f = null;
		//
		Annotation[] as = null;
		//
		InvocationHandler ih = null;
		//
		List<Field> fs2 = null;
		//
		int size = 0;
		//
		List<Method> ms = null;
		//
		Entry<Field, Object> entry = null;
		//
		for (int i = 0; fs != null && i < fs.size(); i++) {
			//
			if ((f = fs.get(i)) == null || (as = f != null ? f.getDeclaredAnnotations() : null) == null) {
				//
				continue;
				//
			} // if
				//
			for (final Annotation a : as) {
				//
				if (a == null) {
					//
					continue;
					//
				} // if
					//
				if ((ih = Proxy.isProxyClass(Util.getClass(a))
						? Util.cast(InvocationHandler.class, Proxy.getInvocationHandler(a))
						: null) != null) {
					//
					if ((size = IterableUtils.size(
							fs2 = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(ih))),
									x -> Objects.equals(Util.getType(x), Class.class))))) == 1) {
						//
						if (Objects.equals(clz, Narcissus.getField(ih, IterableUtils.get(fs2, 0)))) {
							//
							if ((size = IterableUtils.size(
									ms = Util.toList(Util.filter(Arrays.stream(Util.getClass(a).getDeclaredMethods()),
											x -> Objects.equals(String.class, x != null ? x.getReturnType() : null)
													&& !ReflectionUtils.isToStringMethod(x))))) == 1) {
								//
								if (entry == null) {
									//
									entry = Pair.of(f, Narcissus.invokeObjectMethod(a, IterableUtils.get(ms, 0)));
									//
								} else {
									//
									throw new IllegalStateException();
									//
								} // if
									//
							} else if (size > 1) {
								//
								throw new IllegalStateException();
								//
							} // if
								//
								//
						} // if
							//
					} else if (size > 1) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return entry;
		//
	}

}