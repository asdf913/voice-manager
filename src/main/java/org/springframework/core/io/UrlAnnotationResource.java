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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasName;
import io.github.classgraph.ScanResult;
import io.github.toolfactory.narcissus.Narcissus;

public class UrlAnnotationResource implements Resource {

	private static final Logger LOG = LoggerFactory.getLogger(UrlAnnotationResource.class);

	@Override
	@Nullable
	public InputStream getInputStream() throws IOException {
		//
		Properties properties = null;
		//
		final Class<?> clz = forName("org.springframework.beans.factory.URL");
		//
		List<ClassInfo> classInfos = null;
		//
		if (clz.getModifiers() == 9728) {
			//
			classInfos = getAllClasses(scan(new ClassGraph().acceptPackages(getName(getPackage(clz)))));
			//
		} else {
			//
			classInfos = ClassInfoUtil.getClassInfos();
			//
		} // if
			//
		Field[] fs = null;
		//
		Field f;
		//
		Annotation[] as;
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
				if ((as = getAnnotations(f = fs[j])) == null) {
					//
					continue;
					//
				} // if
					//
				for (int k = 0; k < as.length; k++) {
					//
					putAll(properties = ObjectUtils.getIfNull(properties, Properties::new),
							getUrlValue(f, as[k], Pair.of(getName(clz), "value")));
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

	private static ClassInfoList getAllClasses(@Nullable final ScanResult instance) {
		return instance != null ? instance.getAllClasses() : null;
	}

	private static ScanResult scan(@Nullable final ClassGraph instance) {
		return instance != null ? instance.scan() : null;
	}

	private static Package getPackage(@Nullable final Class<?> instance) {
		return instance != null ? instance.getPackage() : null;
	}

	private static void putAll(@Nullable final Properties instance, @Nullable final Map<?, ?> b) {
		if (instance != null && b != null) {
			instance.putAll(b);
		}
	}

	@Nullable
	private static Map<String, Object> getUrlValue(final Field f, final Object a, final Entry<String, String> entry) {
		//
		final Class<?> clz = getClass(a);
		//
		if (clz != null && Proxy.isProxyClass(clz)) {
			//
			final List<Field> fields = toList(
					filter(Arrays.stream(FieldUtils.getAllFields(getClass(Proxy.getInvocationHandler(a)))),
							x -> Objects.equals(getName(x), "type")));
			//
			if (IterableUtils.size(fields) > 1) {
				//
				throw new RuntimeException();
				//
			} // if
				//
			final Field f2 = testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0), null);
			//
			List<Method> ms = null;
			//
			if (f2 == null
					|| !Objects.equals(
							getName(cast(Class.class, Narcissus.getField(Proxy.getInvocationHandler(a), f2))),
							entry.getKey())
					|| (ms = toList(filter(Arrays.stream(getDeclaredMethods(clz)),
							x -> Objects.equals(getName(x), entry.getValue())))) == null) {
				//
				return null;
				//
			} // if
				//
			if (IterableUtils.size(ms) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			Method m = null;
			//
			if ((m = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return null;
				//
			} // if
				//
			try {
				//
				return Collections.singletonMap(StringUtils.joinWith(".", getName(getDeclaringClass(f)), getName(f)),
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
		return null;
		//
	}

	@Nullable
	private static <T, R> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final Function<T, R> functionTrue, @Nullable final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static <T, R> R apply(@Nullable final Function<T, R> instance, @Nullable final T value) {
		return instance != null ? instance.apply(value) : null;
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

	private static String getName(@Nullable final Package instance) {
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