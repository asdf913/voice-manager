package org.springframework.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasName;
import io.github.toolfactory.narcissus.Narcissus;

public class UrlAnnotationResource implements Resource {

	private static final Logger LOG = LoggerFactory.getLogger(UrlAnnotationResource.class);

	@Override
	public InputStream getInputStream() throws IOException {
		//
		final Properties properties = new Properties();
		//
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		Field[] fs = null;
		//
		Field f1, f2;
		//
		Annotation[] as;
		//
		Annotation a;
		//
		List<Field> fields = null;
		//
		int size;
		//
		Class<?> clz = null;
		//
		List<Method> ms = null;
		//
		Method m = null;
		//
		for (int i = 0; i < IterableUtils.size(classInfos); i++) {
			//
			try {
				//
				if ((fs = FieldUtils.getAllFields(forName(getName(IterableUtils.get(classInfos, i))))) == null) {
					//
					continue;
					//
				} // if
					//
			} catch (final Throwable e) {
				//
			} // try
				//
			for (int j = 0; j < fs.length; j++) {
				//
				if ((as = getAnnotations(f1 = fs[j])) == null) {
					//
					continue;
					//
				} // if
					//
				for (int k = 0; k < as.length; k++) {
					//
					if ((a = as[k]) == null) {
						//
						continue;
						//
					} // if
						//
					if (Proxy.isProxyClass(clz = getClass(a))) {
						//
						if ((size = IterableUtils.size(fields = toList(
								filter(Arrays.stream(FieldUtils.getAllFields(getClass(Proxy.getInvocationHandler(a)))),
										x -> Objects.equals(getName(x), "type"))))) > 1) {
							//
							throw new RuntimeException();
							//
						} // if
							//
						if ((f2 = size == 1 ? IterableUtils.get(fields, 0) : null) == null
								|| !Objects.equals(
										getName(cast(Class.class,
												Narcissus.getField(Proxy.getInvocationHandler(a), f2))),
										"org.springframework.beans.factory.URL")
								|| (ms = toList(filter(Arrays.stream(getDeclaredMethods(clz)),
										x -> Objects.equals(getName(x), "value")))) == null) {
							//
							continue;
							//
						} // if
							//
						if ((size = ms.size()) > 1) {
							//
							throw new IllegalStateException();
							//
						} // if
							//
						if ((m = size == 1 ? ms.get(0) : null) == null) {
							//
							continue;
							//
						} // if
							//
						try {
							//
							properties.put(StringUtils.joinWith(".", getName(getDeclaringClass(f1)), getName(f1)),
									Narcissus.invokeMethod(a, m));
							//
						} catch (final IllegalArgumentException e) {
							//
							LoggerUtil.error(LOG, e.getMessage(), e);
							//
						} // try
							//
					} // if
						//
				} // for
					//
			} // for
				//
		} // for
			//
		return toInputStream(properties);
		//
	}

	@Nullable
	private static Class<?> getDeclaringClass(@Nullable final Member instance) {
		return instance != null ? instance.getDeclaringClass() : null;
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		return instance != null && predicate != null ? instance.filter(predicate) : instance;
	}

	@Nullable
	private static Method[] getDeclaredMethods(@Nullable final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static Annotation[] getAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getAnnotations() : null;
	}

	@Nullable
	private static String getName(@Nullable final HasName instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static Class<?> forName(@Nullable final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Nullable
	private static InputStream toInputStream(@Nullable final Properties properties) throws IOException {
		//
		if (properties == null) {
			//
			return null;
			//
		} // if
			//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			properties.store(baos, null);
			//
			return new ByteArrayInputStream(baos.toByteArray());
			//
		} // try
			//
	}

	@Override
	public boolean exists() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URI getURI() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public File getFile() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long contentLength() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long lastModified() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Resource createRelative(final String relativePath) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFilename() {
		return null;
	}

	@Override
	public String getDescription() {
		throw new UnsupportedOperationException();
	}

}