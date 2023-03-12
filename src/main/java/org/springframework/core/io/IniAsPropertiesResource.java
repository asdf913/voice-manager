package org.springframework.core.io;

import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.github.vincentrussell.ini.Ini;

import io.github.toolfactory.narcissus.Narcissus;

public class IniAsPropertiesResource implements Resource, ApplicationEventPublisherAware, EnvironmentAware {

	private static final String PROFILE = "profile";

	private ApplicationEventPublisher applicationEventPublisher = null;

	private Resource resource = null;

	private PropertyResolver propertyResolver = null;

	public IniAsPropertiesResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		//
		final Ini ini = new Ini();
		//
		// org.springframework.core.io.UrlResource.url
		//
		final List<Field> fs = toList(filter(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, getClass(resource), FieldUtils::getAllFields, null), Arrays::stream,
				null), x -> Objects.equals(URL.class, getType(x))));
		//
		Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		setAccessible(f, true);
		//
		URLConnection urlConnection = null;
		//
		try {
			//
			urlConnection = openConnection(
					cast(URL.class, Boolean.logicalOr(isStatic(f), resource != null) ? get(f, resource) : null));
			//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		if (urlConnection != null) {
			//
			// sun.net.www.protocol.file.FileURLConnection.file
			//
			final List<Field> fs2 = toList(filter(testAndApply(Objects::nonNull,
					testAndApply(Objects::nonNull, getClass(urlConnection), FieldUtils::getAllFields, null),
					Arrays::stream, null), x -> Objects.equals(File.class, getType(x))));
			//
			if (exists(cast(File.class,
					Boolean.logicalAnd(urlConnection != null, (f = testAndApply(x -> IterableUtils.size(x) == 1, fs2,
							x -> IterableUtils.get(x, 0), null)) != null) ? Narcissus.getObjectField(urlConnection, f)
									: null))) {
				//
				testAndAccept(Objects::nonNull, InputStreamSourceUtil.getInputStream(resource), ini::load);
				//
			} // if
				//
		} else {
			//
			testAndAccept(Objects::nonNull, InputStreamSourceUtil.getInputStream(resource), ini::load);
			//
		} // if
			//
		final Collection<String> sections = ini.getSections();
		//
		int size = IterableUtils.size(sections);
		//
		if (size == 0) {
			//
			publishEvent(applicationEventPublisher, new PayloadApplicationEvent<>("ini", Unit.with(null)));
			//
			return toInputStream(new Properties());
			//
		} else if (size == 1) {
			//
			final String sectionString = IterableUtils.get(sections, 0);
			//
			final Map<String, Object> map = ini.getSectionSortedByKey(sectionString);
			//
			final Properties properties = new Properties();
			//
			properties.putAll(map.entrySet().stream()
					.collect(Collectors.toMap(IniAsPropertiesResource::getKey, v -> toString(getValue(v)))));
			//
			publishEvent(applicationEventPublisher, new PayloadApplicationEvent<>("ini", Unit.with(sectionString)));
			//
			return toInputStream(properties);
			//
		} // if
			//
		if (size > 1) {
			//
			final String section = IValue0Util.getValue0(getSection(GraphicsEnvironment.isHeadless(),
					System.getProperties(), propertyResolver, System.console(), sections));
			//
			if (contains(sections, section)) {
				//
				final Map<String, Object> map = ini.getSectionSortedByKey(section);
				//
				final Properties properties = new Properties();
				//
				properties.putAll(map.entrySet().stream()
						.collect(Collectors.toMap(IniAsPropertiesResource::getKey, v -> toString(getValue(v)))));
				//
				publishEvent(applicationEventPublisher, new PayloadApplicationEvent<>("ini", Unit.with(section)));
				//
				return toInputStream(properties);
				//
			} else {
				//
				publishEvent(applicationEventPublisher, new PayloadApplicationEvent<>("ini", Unit.with(null)));
				//
				return toInputStream(new Properties());
				//
			} // if
				//
		} // if
			//
		throw new UnsupportedOperationException();
		//
	}

	@Nullable
	private static URLConnection openConnection(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openConnection() : null;
	}

	private static void publishEvent(final ApplicationEventPublisher instance, final ApplicationEvent event) {
		if (instance != null) {
			instance.publishEvent(event);
		}
	}

	private static boolean contains(@Nullable final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	@Nullable
	private static Unit<String> getSection(final boolean headless, @Nullable final Map<?, ?> map,
			final PropertyResolver propertyResolver, @Nullable final Console console, final Collection<?> collection) {
		//
		if (map != null && map.containsKey(PROFILE)) {
			//
			return Unit.with(toString(map.get(PROFILE)));
			//
		} // if
			//
		if (PropertyResolverUtil.containsProperty(propertyResolver, PROFILE)) {
			//
			return Unit.with(PropertyResolverUtil.getProperty(propertyResolver, PROFILE));
			//
		} // if
			//
		if (!headless) {
			//
			final JComboBox<Object> jcb = testAndApply(Objects::nonNull,
					//
					testAndApply(Objects::nonNull, toArray(collection), x -> ArrayUtils.addFirst(x, null), null)
					//
					, JComboBox::new, null);
			//
			JOptionPane.showMessageDialog(null, jcb, "Profile", JOptionPane.QUESTION_MESSAGE);
			//
			return Unit.with(toString(getSelectedItem(jcb)));
			//
		} else if (console != null) {
			//
			final List<Field> fs = toList(
					testAndApply(Objects::nonNull, FieldUtils.getAllFields(getClass(console)), Arrays::stream, null));
			//
			boolean ready = false;
			//
			// java.io.Console.reader
			//
			try {
				//
				ready = ready(cast(Reader.class,
						Narcissus.getObjectField(console,
								testAndApply(x -> IterableUtils.size(x) == 1,
										toList(filter(fs.stream(), x -> Objects.equals(getName(x), "reader"))),
										x -> IterableUtils.get(x, 0), null))));
				//
			} catch (final IOException e) {
				//
				ready = false;
				//
			} // try
				//
			if (//
				// java.io.Console.readLock
				//
			Narcissus.getObjectField(console,
					testAndApply(x -> IterableUtils.size(x) == 1,
							toList(filter(fs.stream(), x -> Objects.equals(getName(x), "writeLock"))),
							x -> IterableUtils.get(x, 0), null)) != null
					//
					// java.io.Console.readLock
					//
					&& Narcissus.getObjectField(console,
							testAndApply(x -> IterableUtils.size(x) == 1,
									toList(filter(fs.stream(), x -> Objects.equals(getName(x), "readLock"))),
									x -> IterableUtils.get(x, 0), null)) != null
					//
					// java.io.Console.pw
					//
					&& Narcissus.getObjectField(console,
							testAndApply(x -> IterableUtils.size(x) == 1,
									toList(filter(fs.stream(), x -> Objects.equals(getName(x), "pw"))),
									x -> IterableUtils.get(x, 0), null)) != null

					//
					// java.io.Console.rcb
					//
					&& Narcissus.getObjectField(console,
							testAndApply(x -> IterableUtils.size(x) == 1,
									toList(filter(fs.stream(), x -> Objects.equals(getName(x), "rcb"))),
									x -> IterableUtils.get(x, 0), null)) != null
					//
					&& ready) {
				//
				return Unit.with(console.readLine("Profile"));
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	private static <K> K getKey(@Nullable final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	@Nullable
	private static <V> V getValue(@Nullable final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static boolean exists(@Nullable final File instance) {
		return instance != null && instance.exists();
	}

	@Nullable
	private static Class<?> getType(@Nullable final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalArgumentException, IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			@Nullable final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static Object[] toArray(@Nullable final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static boolean ready(@Nullable final Reader instance) throws IOException {
		return instance != null && instance.ready();
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final JComboBox<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static InputStream toInputStream(@Nullable final Properties properties) throws IOException {
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			if (properties != null) {
				//
				properties.store(baos, null);
				//
			} // if
				//
			return new ByteArrayInputStream(baos.toByteArray());
			//
		} // try
			//
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
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
		return resource != null ? resource.getFilename() : null;
	}

	@Override
	public String getDescription() {
		throw new UnsupportedOperationException();
	}

}