package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;

public abstract class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	private Util() {
	}

	@Nullable
	static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	@Nullable
	static <T, R> Stream<R> map(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	@Nullable
	static <T> Stream<T> filter(@Nullable final Stream<T> instance, @Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	static <T> T orElse(@Nullable final Optional<T> instance, @Nullable final T other) {
		return instance != null ? instance.orElse(other) : null;
	}

	@Nullable
	static String getText(@Nullable final JTextComponent instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, Narcissus.findField(getClass(instance), "model")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.getText();
		//
	}

	static void setText(@Nullable final JTextComponent instance, @Nullable final String text) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, Narcissus.findField(getClass(instance), "model")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.setText(text);
		//
	}

	static <E> void add(@Nullable final Collection<E> items, @Nullable final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Nullable
	static <K> K getKey(@Nullable final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	@Nullable
	static <V> V getValue(@Nullable final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Nullable
	static Object getSource(@Nullable final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	@Nullable
	static Class<?> forName(@Nullable final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	@Nullable
	static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static Class<?> getType(@Nullable final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	@Nullable
	static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Nullable
	static Class<?> getDeclaringClass(@Nullable final Member instance) {
		return instance != null ? instance.getDeclaringClass() : null;
	}

	static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	@Nullable
	static <K, V> Set<Map.Entry<K, V>> entrySet(@Nullable final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	@Nullable
	static <V> V get(@Nullable final Map<?, V> instance, @Nullable final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	@Nullable
	static String getAbsolutePath(@Nullable final File instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Object fs = testAndApply(Objects::nonNull, getJavaIoFileSystemField(instance), Narcissus::getStaticField,
				null);
		//
		if (fs == null) {
			//
			return null;
			//
		} // if
			//
		if (contains(Arrays.asList("java.io.WinNTFileSystem", "java.io.UnixFileSystem"), getName(getClass(fs)))
				&& instance.getPath() == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getAbsolutePath();
		//
	}

	static boolean contains(@Nullable final Collection<?> items, @Nullable final Object item) {
		return items != null && items.contains(item);
	}

	@Nullable
	private static Field getJavaIoFileSystemField(final Object instance) {
		//
		final List<Field> fields = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(getClass(instance)), Arrays::stream, null),
						x -> x != null && Objects.equals(getName(x.getType()), "java.io.FileSystem")));
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0), null);
		//
	}

	@Nullable
	private static <T, R> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final Function<T, R> functionTrue, @Nullable final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	static <T, R> R apply(@Nullable final Function<T, R> instance, @Nullable final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	@Nullable
	static Field[] getDeclaredFields(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	@Nullable
	static <T> List<T> toList(@Nullable final Stream<T> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			// java.util.stream.AbstractPipeline.sourceStage
			//
			if (isAssignableFrom(Class.forName("java.util.stream.AbstractPipeline"), getClass(instance))) {
				//
				final Stream<Field> s = filter(stream(FieldUtils.getAllFieldsList(getClass(instance))),
						f -> Objects.equals(getName(f), "sourceStage"));
				//
				final List<Field> fs = s != null ? s.toList() : null;
				//
				final int size = IterableUtils.size(fs);
				//
				if (size > 1) {
					//
					throw new IllegalStateException();
					//
				} else if (testAndApply(x -> !isStatic(x),
						testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
						x -> Narcissus.getField(instance, x), null) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
		} catch (final ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.toList();
		//
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	static <T> Iterator<T> iterator(final Iterable<T> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		final String name = getName(clz);
		//
		try {
			//
			if (contains(Arrays.asList("com.google.common.collect.HashMultiset",
					"com.google.common.collect.LinkedHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.HashMultiset",
					"org.apache.jena.ext.com.google.common.collect.LinkedHashMultiset"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "backingMap")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name)) {
				//
				if (Narcissus.invokeMethod(instance,
						Narcissus.findMethod(clz, "createRowState", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.openjdk.nashorn.internal.runtime.SharedPropertyMap"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "properties")) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			if (isAssignableFrom(Class.forName("com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_hashArea")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(
					Class.forName("com.github.andrewoma.dexx.collection.internal.adapter.SetAdapater"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "set")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("com.google.common.collect.ForwardingQueue"), clz)
					|| isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.ForwardingQueue"),
							clz)) {
				//
				if (Narcissus.invokeMethod(instance,
						Narcissus.findMethod(clz, "delegate", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("com.google.common.collect.Maps$KeySet"), clz)
					|| isAssignableFrom(Class.forName("com.google.common.collect.Maps$Values"), clz)
					|| isAssignableFrom(
							Class.forName("org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardKeySet"),
							clz)
					|| isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.Maps$Values"), clz)
					|| isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.Maps$KeySet"),
							clz)) {
				//
				if (Narcissus.invokeMethod(instance, Narcissus.findMethod(clz, "map", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(
					Class.forName("com.google.common.collect.ForwardingMultiset$StandardElementSet"), clz)
					|| isAssignableFrom(Class.forName("com.google.common.collect.SortedMultisets$ElementSet"), clz)
					|| isAssignableFrom(Class.forName(
							"org.apache.jena.ext.com.google.common.collect.ForwardingMultiset$StandardElementSet"), clz)
					|| isAssignableFrom(Class.forName(
							"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMultiset$StandardElementSet"),
							clz)) {
				//
				if (Narcissus.invokeMethod(instance,
						Narcissus.findMethod(clz, "multiset", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("com.google.common.collect.Sets$DescendingSet"), clz)
					|| isAssignableFrom(Class.forName(
							"org.apache.jena.ext.com.google.common.collect.ForwardingNavigableSet$StandardDescendingSet"),
							clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "forward")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("java.util.TreeSet"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "m")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("java.util.HashSet"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("com.opencsv.CSVReader"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "peekedLines")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.bcel.classfile.Annotations"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "annotationTable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.bcel.classfile.ParameterAnnotations"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "parameterAnnotationTable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.collections.bag.AbstractMapBag"), clz)
					|| isAssignableFrom(Class.forName("org.apache.commons.collections4.bag.AbstractMapBag"), clz)
					|| isAssignableFrom(Class.forName("org.apache.commons.collections4.multiset.AbstractMapMultiSet"),
							clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.collections.DefaultMapBag"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_map")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.collections.collection.PredicatedCollection"),
					clz)
					|| isAssignableFrom(
							Class.forName("org.apache.commons.collections.collection.SynchronizedCollection"), clz)
					|| isAssignableFrom(
							Class.forName("org.apache.commons.collections.collection.AbstractCollectionDecorator"),
							clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "collection")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(
					Class.forName("org.apache.commons.collections4.collection.AbstractCollectionDecorator"), clz)
					|| isAssignableFrom(
							Class.forName("org.apache.commons.collections4.collection.SynchronizedCollection"), clz)) {
				//
				if (Narcissus.invokeMethod(instance,
						Narcissus.findMethod(clz, "decorated", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.math3.genetics.ListPopulation"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "chromosomes")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.math3.geometry.partitioning.AbstractRegion"),
					clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "tree")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.poifs.property.DirectoryProperty"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_children")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFTextShape"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_paragraphs")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(
					Class.forName("org.apache.poi.xslf.usermodel.XSLFDiagram$XSLFDiagramGroupShape"), clz)
					|| isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFGroupShape"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_shapes")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFSheet"), clz)) {
				//
				if (Narcissus.invokeMethod(instance,
						Narcissus.findMethod(clz, "getXmlObject", new Class<?>[] {})) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFTextParagraph"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_runs")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.DeferredSXSSFSheet"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_rows")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.SXSSFWorkbook"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_wb")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.SXSSFSheet"), clz)
					|| isAssignableFrom(Class.forName("org.apache.poi.xssf.usermodel.XSSFSheet"), clz)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_rows")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.usermodel.XSSFSimpleShape"), clz)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_paragraphs")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.usermodel.XSSFShape"), clz)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "drawing")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xwpf.usermodel.XWPFAbstractFootnoteEndnote"),
					clz)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "paragraphs")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.eclipse.jetty.http.MetaData"), clz)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_httpFields")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.javatuples.Tuple"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "valueList")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (isAssignableFrom(Class.forName("org.openjdk.nashorn.internal.runtime.PropertyMap"), clz)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "properties")) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			if (contains(Arrays.asList("com.fasterxml.jackson.databind.node.ArrayNode",
					"com.fasterxml.jackson.databind.node.ObjectNode"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_children")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.DerivedKeyHashMap",
					"com.github.andrewoma.dexx.collection.HashMap", "com.github.andrewoma.dexx.collection.HashSet"),
					name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "compactHashMap")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.TreeMap",
					"com.github.andrewoma.dexx.collection.TreeSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "redBlackTree")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.Vector"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "pointer")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.internal.adapter.ListAdapater",
					"org.apache.commons.collections.FastArrayList",
					"org.apache.commons.math3.geometry.partitioning.NodesSet",
					"org.apache.logging.log4j.spi.MutableThreadContextStack"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "list")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.internal.base.MappedIterable"),
					name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "from")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.google.common.collect.ConcurrentHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.ConcurrentHashMultiset"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "countMap")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.google.common.collect.TreeMultiset",
					"org.apache.jena.ext.com.google.common.collect.TreeMultiset"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "rootReference")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.google.common.reflect.TypeToken$TypeSet",
					"org.apache.jena.ext.com.google.common.reflect.TypeToken$TypeSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "types")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.DatabaseImpl"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_tableFinder")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.IndexCursorImpl"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_entryCursor")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.PropertyMapImpl"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_props")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.PropertyMaps"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_maps")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableImpl"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_columns")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableScanCursor"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_ownedPagesCursor")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(
					Arrays.asList("com.healthmarketscience.jackcess.impl.complex.MultiValueColumnPropertyMap"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_primary")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.util.EntryIterableBuilder",
					"com.healthmarketscience.jackcess.util.IterableBuilder"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_cursor")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.util.TableIterableBuilder"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_db")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.helger.commons.callback.CallbackList"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "m_aRWLock")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.helger.commons.collection.impl.CommonsCopyOnWriteArraySet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "al")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.helger.commons.collection.map.LRUSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "m_aMap")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.helger.commons.http.HttpHeaderMap"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "m_aHeaders")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.helger.commons.log.InMemoryLogger"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "m_aMessages")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.opencsv.bean.CsvToBean"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "mappingStrategy")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.opencsv.bean.PositionToBeanField"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "ranges")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.BootstrapMethods"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "bootstrapMethods")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.ConstantPool"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "constantPool")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.InnerClasses"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "innerClasses")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.LineNumberTable"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "lineNumberTable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.LocalVariableTable"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "localVariableTable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.LocalVariableTypeTable"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "localVariableTypeTable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.MethodParameters"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "parameters")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.collections.CursorableLinkedList"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_head")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.collections.collection.CompositeCollection",
					"org.apache.commons.collections.set.CompositeSet",
					"org.apache.commons.collections4.collection.CompositeCollection",
					"org.apache.commons.collections4.set.CompositeSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "all")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.collections.list.AbstractLinkedList$LinkedSubList",
					"org.apache.commons.collections.map.AbstractHashedMap$EntrySet",
					"org.apache.commons.collections.map.AbstractHashedMap$KeySet",
					"org.apache.commons.collections.map.AbstractHashedMap$Values",
					"org.apache.commons.collections4.list.AbstractLinkedList$LinkedSubList",
					"org.apache.commons.collections4.map.AbstractHashedMap$EntrySet",
					"org.apache.commons.collections4.map.AbstractHashedMap$KeySet",
					"org.apache.commons.collections4.map.AbstractHashedMap$Values",
					"org.apache.commons.collections4.multiset.AbstractMultiSet$EntrySet",
					"org.apache.commons.collections4.multiset.AbstractMultiSet$UniqueSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "parent")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.collections.set.MapBackedSet",
					"org.apache.commons.collections4.set.MapBackedSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.collections4.FluentIterable"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "iterable")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.csv.CSVRecord"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "values")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.io.IOExceptionList"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "causeList")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.lang3.builder.DiffResult"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "diffList")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.math3.ml.neuralnet.Network"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "neuronMap")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "network")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.math3.util.IntegerSequence$Range"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "step")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.commons.math3.util.MultidimensionalCounter"), name)) {
				//
				if ((Narcissus.getIntField(instance, Narcissus.findField(clz, "dimension"))) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.jena.atlas.lib.Map2"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "map1")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.logging.log4j.message.StructuredDataCollectionMessage"),
					name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "structuredDataMessageList")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.ddf.EscherContainerRecord"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_childRecords")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.record.aggregates.ValueRecordsAggregate"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "records")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFPatriarch"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_shapes")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFRow"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "cells")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFShapeGroup"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "shapes")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(
					Arrays.asList("org.apache.poi.hssf.usermodel.HSSFSheet", "org.apache.poi.xslf.usermodel.XSLFTable"),
					name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_rows")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFWorkbook"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "_sheets")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.openxml4j.opc.PackageRelationshipCollection"), name)) {
				//
				if (Narcissus.getIntField(instance, Narcissus.findField(clz, "relationshipsByID")) == 0) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.crypt.temp.SXSSFWorkbookWithCustomZipEntrySource"),
					name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_wb")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.filesystem.DirectoryNode"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_entries")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.filesystem.FilteringDirectoryNode"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "directory")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.filesystem.POIFSDocument"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_property")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.filesystem.POIFSStream"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "blockStore")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.sl.draw.geom.CustomGeometry"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "paths")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.ss.util.SSCellRange"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_flattenedArray")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.util.IntMapper"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "elements")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xddf.usermodel.text.XDDFTextParagraph",
					"org.apache.poi.xssf.usermodel.XSSFTextParagraph"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_runs")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xslf.usermodel.XSLFTableRow",
					"org.apache.poi.xssf.streaming.SXSSFRow", "org.apache.poi.xssf.usermodel.XSSFRow"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_cells")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xslf.usermodel.XSLFTableStyles"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_styles")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xssf.streaming.SXSSFDrawing"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "_drawing")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xssf.usermodel.XSSFDrawing"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "drawing")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.poi.xssf.usermodel.XSSFWorkbook"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "sheets")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.apache.xmlbeans.XmlSimpleList"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "underlying")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.eclipse.jetty.http.MultiPartByteRanges$Parts",
					"org.eclipse.jetty.http.MultiPartFormData$Parts"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "parts")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.eclipse.jetty.http.pathmap.PathSpecSet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "specs")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.logevents.observers.batch.LogEventBatch"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "batch")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.openjdk.nashorn.internal.codegen.Compiler$CompilationPhases"),
					name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "phases")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.oxbow.swingbits.action.ActionGroup"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "actions")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.springframework.beans.MutablePropertyValues"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "propertyValueList")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.springframework.beans.factory.aot.AotServices"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "services")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.springframework.cglib.beans.FixedKeySet"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "set")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.springframework.core.env.MutablePropertySources"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "propertySourceList")) == null) {
					//
					return null;
					//
				} // if
					//
			} else if (contains(Arrays.asList("org.springframework.util.AutoPopulatingList"), name)) {
				//
				if (Narcissus.getField(instance, Narcissus.findField(clz, "backingList")) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
		} catch (final ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} catch (final NullPointerException npe) {
			//
			try {
				//
				if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name)) {
					//
					if (Narcissus.getField(instance, Narcissus.findField(clz, "_database")) == null) {
						//
						return null;
						//
					} // if
						//
				} // if
					//
			} catch (final NoSuchFieldException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			throw npe;
			//
		} // try
			//
		return instance.iterator();
		//
	}

}