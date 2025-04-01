package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstructionUtil;
import org.apache.bcel.generic.FieldOrMethod;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.ProxyFactory;

public abstract class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	private static final String UNDER_SCORE_ROWS = "_rows";

	private static final String UNDER_SCORE_RUNS = "_runs";

	private static final String DELEGATE = "delegate";

	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[] {};

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

	static void forEach(@Nullable final IntStream intStream, @Nullable final IntConsumer intConsumer) {
		if (intStream != null && intConsumer != null) {
			intStream.forEach(intConsumer);
		}
	}

	static <T> void forEach(@Nullable final Iterable<T> instance, @Nullable final Consumer<? super T> action) {
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		}
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

	static void setText(@Nullable final JLabel instance, @Nullable final String text) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		try {
			//
			if (isAssignableFrom(Class.forName("java.awt.Component"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(getClass(instance), "objectLock")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException | ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.setText(text);
		//
	}

	static void setForeground(@Nullable final Component instance, final Color color) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		try {
			//
			if (isAssignableFrom(Class.forName("java.awt.Component"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(getClass(instance), "objectLock")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException | ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.setForeground(color);
		//
	}

	static boolean isSelected(@Nullable final AbstractButton instance) {
		return instance != null && instance.isSelected();
	}

	static <E> void add(@Nullable final Collection<E> items, @Nullable final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	static <E> void addAll(@Nullable final Collection<E> a, @Nullable final Collection<? extends E> b) {
		if (a != null && (b != null || Proxy.isProxyClass(Util.getClass(a)))) {
			a.addAll(b);
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
	static String getName(@Nullable final Package instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static String getSimpleName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getSimpleName() : null;
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
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	static <T, R> R apply(@Nullable final Function<T, R> instance, @Nullable final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	@Nullable
	static Field getDeclaredField(@Nullable final Class<?> instance, final String name) throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	@Nullable
	static Field[] getDeclaredFields(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	@Nullable
	static Method getDeclaredMethod(@Nullable final Class<?> instance, final String name,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return instance != null ? instance.getDeclaredMethod(name, parameterTypes) : null;
	}

	@Nullable
	static Method[] getDeclaredMethods(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
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
				//
		} catch (final ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.toList();
		//
	}

	static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
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
		if (contains(Arrays.asList("net.bytebuddy.description.type.TypeDescription$Generic$OfTypeVariable$Symbolic",
				"net.bytebuddy.description.type.TypeDescription$Generic$Visitor$AnnotationStripper$NonAnnotatedTypeVariable",
				"net.bytebuddy.description.type.TypeDescription$Generic$Visitor$Substitutor$ForTypeVariableBinding$RetainedMethodTypeVariable",
				"net.bytebuddy.description.type.TypeList$Generic$ForDetachedTypes$OfTypeVariables$AttachedTypeVariable",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForLowerBoundWildcard$LazyLowerBoundWildcard",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$AnnotatedTypeVariable",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$Formal$LazyTypeVariable",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$UnresolvedTypeVariable",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForUnboundWildcard$LazyUnboundWildcard",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForUpperBoundWildcard$LazyUpperBoundWildcard",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$LazyMethodDescription$LazyParameterizedReceiverType$TypeArgumentList$AnnotatedTypeVariable",
				"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$TokenizedGenericType$Malformed"), name)) {
			//
			return instance.iterator();
			//
		} // if
			//
			//
		IValue0<Iterator<T>> iValue0 = null;
		//
		try {
			//
			if ((iValue0 = iterator(clz, instance)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
			if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name) && Narcissus
					.invokeMethod(instance, Narcissus.findMethod(clz, "createRowState", EMPTY_CLASS_ARRAY)) == null) {
				//
				return null;
				//
			} // if
				//
			final Map<String, FailablePredicate<Class<?>, ReflectiveOperationException>> predicates = new LinkedHashMap<>();
			//
			putAll(predicates, x -> Narcissus.getField(instance, Narcissus.findField(x, "collection")) == null,
					"org.apache.commons.collections.collection.PredicatedCollection",
					"org.apache.commons.collections.collection.SynchronizedCollection",
					"org.apache.commons.collections.collection.AbstractCollectionDecorator",
					"it.unimi.dsi.fastutil.booleans.BooleanSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.booleans.BooleanSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.bytes.ByteSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.bytes.ByteSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.chars.CharLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.chars.CharSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.chars.CharSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.doubles.DoubleSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.doubles.DoubleSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.floats.FloatSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.floats.FloatSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.ints.IntSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.ints.IntSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.longs.LongSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.longs.LongSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.objects.ObjectSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.objects.ObjectSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.objects.ReferenceSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.objects.ReferenceSets$UnmodifiableSet",
					"it.unimi.dsi.fastutil.shorts.ShortSets$SynchronizedSet",
					"it.unimi.dsi.fastutil.shorts.ShortSets$UnmodifiableSet",
					"org.d2ab.collection.chars.CollectionCharList", "org.d2ab.collection.doubles.CollectionDoubleList",
					"org.d2ab.collection.ints.CollectionIntList", "org.d2ab.collection.longs.CollectionLongList");
			//
			putAll(predicates,
					x -> Narcissus.invokeMethod(instance,
							Narcissus.findMethod(x, "decorated", EMPTY_CLASS_ARRAY)) == null,
					"org.apache.commons.collections4.collection.AbstractCollectionDecorator",
					"org.apache.commons.collections4.collection.SynchronizedCollection");
			//
			Util.put(predicates, "org.apache.commons.math3.genetics.ListPopulation",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "chromosomes")) == null);
			//
			Util.put(predicates, "org.apache.commons.math3.geometry.partitioning.AbstractRegion",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "tree")) == null);
			//
			Util.put(predicates, "org.apache.poi.poifs.property.DirectoryProperty",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "_children")) == null);
			//
			Util.put(predicates, "org.apache.poi.xslf.usermodel.XSLFTextShape",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "_paragraphs")) == null);
			//
			Util.put(predicates, "org.apache.poi.xslf.usermodel.XSLFSheet", x -> Narcissus.invokeMethod(instance,
					Narcissus.findMethod(x, "getXmlObject", EMPTY_CLASS_ARRAY)) == null);
			//
			Util.put(predicates, "org.apache.poi.xslf.usermodel.XSLFTextParagraph",
					x -> Narcissus.getField(instance, Narcissus.findField(x, UNDER_SCORE_RUNS)) == null);
			//
			putAll(predicates, x -> Narcissus.getField(instance, Narcissus.findField(x, UNDER_SCORE_ROWS)) == null,
					"org.apache.poi.xssf.streaming.DeferredSXSSFSheet", "org.apache.poi.xssf.streaming.SXSSFSheet",
					"org.apache.poi.xssf.usermodel.XSSFSheet");
			//
			Util.put(predicates, "org.apache.poi.xssf.streaming.DeferredSXSSFSheet",
					x -> Narcissus.getField(instance, Narcissus.findField(x, UNDER_SCORE_ROWS)) == null);
			//
			Util.put(predicates, "org.apache.poi.xssf.streaming.SXSSFWorkbook",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "_wb")) == null);
			//
			Util.put(predicates, "org.apache.poi.xssf.usermodel.XSSFShape",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "drawing")) == null);
			//
			Util.put(predicates, "org.openjdk.nashorn.internal.runtime.PropertyMap",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "properties")) == null);
			//
			Util.put(predicates, "com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "_hashArea")) == null);
			//
			putAll(predicates,
					x -> Narcissus.invokeMethod(instance, Narcissus.findMethod(x, DELEGATE, EMPTY_CLASS_ARRAY)) == null,
					"com.google.common.collect.ForwardingQueue",
					"org.apache.jena.ext.com.google.common.collect.ForwardingQueue");
			//
			putAll(predicates,
					x -> Narcissus.invokeMethod(instance, Narcissus.findMethod(x, "map", EMPTY_CLASS_ARRAY)) == null,
					"com.google.common.collect.Maps$KeySet", "com.google.common.collect.Maps$Values",
					"org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardKeySet",
					"org.apache.jena.ext.com.google.common.collect.Maps$Values",
					"org.apache.jena.ext.com.google.common.collect.Maps$KeySet");
			//
			putAll(predicates,
					x -> Narcissus.invokeMethod(instance,
							Narcissus.findMethod(x, "multiset", EMPTY_CLASS_ARRAY)) == null,
					"com.google.common.collect.ForwardingMultiset$StandardElementSet",
					"com.google.common.collect.SortedMultisets$ElementSet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingMultiset$StandardElementSet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMultiset$StandardElementSet");
			//
			Util.put(predicates, "java.util.TreeSet",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "m")) == null);
			//
			Util.put(predicates, "java.util.HashSet",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "map")) == null);
			//
			Util.put(predicates, "com.opencsv.CSVReader",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "peekedLines")) == null);
			//
			putAll(predicates, x -> Narcissus.getField(instance, Narcissus.findField(x, "map")) == null,
					"org.apache.commons.collections.bag.AbstractMapBag",
					"org.apache.commons.collections4.bag.AbstractMapBag",
					"org.apache.commons.collections4.multiset.AbstractMapMultiSet");
			//
			Util.put(predicates, "org.apache.commons.collections.DefaultMapBag",
					x -> Narcissus.getField(instance, Narcissus.findField(x, "_map")) == null);
			//
			for (final Entry<String, FailablePredicate<Class<?>, ReflectiveOperationException>> entry : predicates
					.entrySet()) {
				//
				if (isAssignableFrom(Class.forName(Util.getKey(entry)), clz) && test(Util.getValue(entry), clz)) {
					//
					return null;
					//
				} // if
					//
			} // for
				//
			final Map<String, String> map = new LinkedHashMap<>();
			//
			putAll(map, "backingMap", "com.google.common.collect.HashMultiset",
					"com.google.common.collect.LinkedHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.HashMultiset",
					"org.apache.jena.ext.com.google.common.collect.LinkedHashMultiset");
			//
			Util.put(map, "org.openjdk.nashorn.internal.runtime.SharedPropertyMap", "properties");
			//
			putAll(map, "_hashArea", "com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap",
					"com.google.common.collect.ForwardingQueue",
					"org.apache.jena.ext.com.google.common.collect.ForwardingQueue");
			//
			putAll(map, "_children", "com.fasterxml.jackson.databind.node.ArrayNode",
					"com.fasterxml.jackson.databind.node.ObjectNode");
			//
			putAll(map, "compactHashMap", "com.github.andrewoma.dexx.collection.DerivedKeyHashMap",
					"com.github.andrewoma.dexx.collection.HashMap", "com.github.andrewoma.dexx.collection.HashSet");
			//
			putAll(map, "redBlackTree", "com.github.andrewoma.dexx.collection.TreeMap",
					"com.github.andrewoma.dexx.collection.TreeSet");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.Vector", "pointer");
			//
			putAll(map, "list", "com.github.andrewoma.dexx.collection.internal.adapter.ListAdapater",
					"org.apache.commons.collections.FastArrayList",
					"org.apache.commons.math3.geometry.partitioning.NodesSet",
					"org.apache.logging.log4j.spi.MutableThreadContextStack",
					"it.unimi.dsi.fastutil.booleans.BooleanBigLists$ListBigList",
					"it.unimi.dsi.fastutil.booleans.BooleanBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.booleans.BooleanBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.booleans.BooleanLists$SynchronizedList",
					"it.unimi.dsi.fastutil.booleans.BooleanLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.booleans.BooleanLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.booleans.BooleanLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.bytes.ByteBigLists$ListBigList",
					"it.unimi.dsi.fastutil.bytes.ByteBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.bytes.ByteBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.bytes.ByteLists$SynchronizedList",
					"it.unimi.dsi.fastutil.bytes.ByteLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.bytes.ByteLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.bytes.ByteLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.chars.CharBigLists$ListBigList",
					"it.unimi.dsi.fastutil.chars.CharBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.chars.CharLists$SynchronizedList",
					"it.unimi.dsi.fastutil.chars.CharLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.chars.CharLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.chars.CharLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.chars.CharBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.doubles.DoubleBigLists$ListBigList",
					"it.unimi.dsi.fastutil.doubles.DoubleBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.doubles.DoubleBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.doubles.DoubleLists$SynchronizedList",
					"it.unimi.dsi.fastutil.doubles.DoubleLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.doubles.DoubleLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.doubles.DoubleLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.floats.FloatBigLists$ListBigList",
					"it.unimi.dsi.fastutil.floats.FloatBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.floats.FloatBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.floats.FloatLists$SynchronizedList",
					"it.unimi.dsi.fastutil.floats.FloatLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.floats.FloatLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.floats.FloatLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.ints.IntBigLists$ListBigList",
					"it.unimi.dsi.fastutil.ints.IntBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.ints.IntBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.ints.IntLists$SynchronizedList",
					"it.unimi.dsi.fastutil.ints.IntLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.ints.IntLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.ints.IntLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.longs.LongBigLists$ListBigList",
					"it.unimi.dsi.fastutil.longs.LongBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.longs.LongBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.longs.LongLists$SynchronizedList",
					"it.unimi.dsi.fastutil.longs.LongLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.longs.LongLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.longs.LongLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.objects.ObjectBigLists$ListBigList",
					"it.unimi.dsi.fastutil.objects.ObjectBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.objects.ObjectBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.objects.ObjectLists$SynchronizedList",
					"it.unimi.dsi.fastutil.objects.ObjectLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.objects.ObjectLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.objects.ObjectLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.objects.ReferenceBigLists$ListBigList",
					"it.unimi.dsi.fastutil.objects.ReferenceBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.objects.ReferenceBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.objects.ReferenceLists$SynchronizedList",
					"it.unimi.dsi.fastutil.objects.ReferenceLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.objects.ReferenceLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.objects.ReferenceLists$UnmodifiableRandomAccessList",
					"it.unimi.dsi.fastutil.shorts.ShortBigLists$ListBigList",
					"it.unimi.dsi.fastutil.shorts.ShortBigLists$SynchronizedBigList",
					"it.unimi.dsi.fastutil.shorts.ShortBigLists$UnmodifiableBigList",
					"it.unimi.dsi.fastutil.shorts.ShortLists$SynchronizedList",
					"it.unimi.dsi.fastutil.shorts.ShortLists$SynchronizedRandomAccessList",
					"it.unimi.dsi.fastutil.shorts.ShortLists$UnmodifiableList",
					"it.unimi.dsi.fastutil.shorts.ShortLists$UnmodifiableRandomAccessList");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.internal.base.MappedIterable", "from");
			//
			putAll(map, "countMap", "com.google.common.collect.ConcurrentHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.ConcurrentHashMultiset");
			//
			putAll(map, "rootReference", "com.google.common.collect.TreeMultiset",
					"org.apache.jena.ext.com.google.common.collect.TreeMultiset");
			//
			putAll(map, "types", "com.google.common.reflect.TypeToken$TypeSet",
					"org.apache.jena.ext.com.google.common.reflect.TypeToken$TypeSet");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.DatabaseImpl", "_tableFinder");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.IndexCursorImpl", "_entryCursor");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.PropertyMapImpl", "_props");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.PropertyMaps", "_maps");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.TableImpl", "_columns");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.TableScanCursor", "_ownedPagesCursor");
			//
			Util.put(map, "com.healthmarketscience.jackcess.impl.complex.MultiValueColumnPropertyMap", "_primary");
			//
			Util.put(map, "com.healthmarketscience.jackcess.util.EntryIterableBuilder", "_cursor");
			//
			Util.put(map, "com.healthmarketscience.jackcess.util.IterableBuilder", "_cursor");
			//
			Util.put(map, "com.healthmarketscience.jackcess.util.TableIterableBuilder", "_db");
			//
			Util.put(map, "com.helger.commons.callback.CallbackList", "m_aRWLock");
			//
			Util.put(map, "com.helger.commons.collection.impl.CommonsCopyOnWriteArraySet", "al");
			//
			Util.put(map, "com.helger.commons.collection.map.LRUSet", "m_aMap");
			//
			Util.put(map, "com.helger.commons.http.HttpHeaderMap", "m_aHeaders");
			//
			Util.put(map, "com.opencsv.bean.CsvToBean", "mappingStrategy");
			//
			Util.put(map, "com.opencsv.bean.PositionToBeanField", "ranges");
			//
			Util.put(map, "org.apache.bcel.classfile.ConstantPool", "constantPool");
			//
			Util.put(map, "org.apache.commons.collections.CursorableLinkedList", "_head");
			//
			putAll(map, "all", "org.apache.commons.collections.collection.CompositeCollection",
					"org.apache.commons.collections.set.CompositeSet",
					"org.apache.commons.collections4.collection.CompositeCollection",
					"org.apache.commons.collections4.set.CompositeSet");
			//
			putAll(map, "parent", "org.apache.commons.collections.list.AbstractLinkedList$LinkedSubList",
					"org.apache.commons.collections.map.AbstractHashedMap$EntrySet",
					"org.apache.commons.collections.map.AbstractHashedMap$KeySet",
					"org.apache.commons.collections.map.AbstractHashedMap$Values",
					"org.apache.commons.collections4.list.AbstractLinkedList$LinkedSubList",
					"org.apache.commons.collections4.map.AbstractHashedMap$EntrySet",
					"org.apache.commons.collections4.map.AbstractHashedMap$KeySet",
					"org.apache.commons.collections4.map.AbstractHashedMap$Values",
					"org.apache.commons.collections4.multiset.AbstractMultiSet$EntrySet",
					"org.apache.commons.collections4.multiset.AbstractMultiSet$UniqueSet");
			//
			putAll(map, "map", "org.apache.commons.collections.set.MapBackedSet",
					"org.apache.commons.collections4.set.MapBackedSet");
			//
			putAll(map, "values", "org.apache.commons.csv.CSVRecord",
					"org.d2ab.collection.doubles.SortedListDoubleSet");
			//
			Util.put(map, "org.apache.commons.io.IOExceptionList", "causeList");
			//
			Util.put(map, "org.apache.commons.math3.ml.neuralnet.Network", "neuronMap");
			//
			Util.put(map, "org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D", "network");
			//
			Util.put(map, "org.apache.jena.atlas.lib.Map2", "map1");
			//
			Util.put(map, "org.apache.poi.ddf.EscherContainerRecord", "_childRecords");
			//
			Util.put(map, "org.apache.poi.hssf.record.aggregates.ValueRecordsAggregate", "records");
			//
			Util.put(map, "org.apache.poi.hssf.usermodel.HSSFRow", "cells");
			//
			putAll(map, UNDER_SCORE_ROWS, "org.apache.poi.hssf.usermodel.HSSFSheet",
					"org.apache.poi.xslf.usermodel.XSLFTable");
			//
			Util.put(map, "org.apache.poi.hssf.usermodel.HSSFWorkbook", "_sheets");
			//
			Util.put(map, "org.apache.poi.openxml4j.opc.PackageRelationshipCollection", "relationshipsByID");
			//
			Util.put(map, "org.apache.poi.poifs.filesystem.DirectoryNode", "_entries");
			//
			Util.put(map, "org.apache.poi.poifs.filesystem.FilteringDirectoryNode", "directory");
			//
			Util.put(map, "org.apache.poi.poifs.filesystem.POIFSDocument", "_property");
			//
			Util.put(map, "org.apache.poi.poifs.filesystem.POIFSStream", "blockStore");
			//
			putAll(map, UNDER_SCORE_RUNS, "org.apache.poi.xddf.usermodel.text.XDDFTextParagraph",
					"org.apache.poi.xssf.usermodel.XSSFTextParagraph");
			//
			putAll(map, "_cells", "org.apache.poi.xslf.usermodel.XSLFTableRow",
					"org.apache.poi.xssf.streaming.SXSSFRow", "org.apache.poi.xssf.usermodel.XSSFRow");
			//
			Util.put(map, "org.apache.poi.xssf.streaming.SXSSFDrawing", "_drawing");
			//
			Util.put(map, "org.apache.poi.xssf.usermodel.XSSFDrawing", "drawing");
			//
			Util.put(map, "org.apache.poi.xssf.usermodel.XSSFWorkbook", "sheets");
			//
			Util.put(map, "org.apache.xmlbeans.XmlSimpleList", "underlying");
			//
			Util.put(map, "org.springframework.beans.MutablePropertyValues", "propertyValueList");
			//
			putAll(map, "iterable",
					"net.bytebuddy.agent.builder.AgentBuilder$RedefinitionStrategy$BatchAllocator$Slicing$SlicingIterable",
					"it.unimi.dsi.fastutil.booleans.BooleanCollections$IterableCollection",
					"it.unimi.dsi.fastutil.bytes.ByteCollections$IterableCollection",
					"it.unimi.dsi.fastutil.chars.CharCollections$IterableCollection",
					"it.unimi.dsi.fastutil.doubles.DoubleCollections$IterableCollection",
					"it.unimi.dsi.fastutil.floats.FloatCollections$IterableCollection",
					"it.unimi.dsi.fastutil.ints.IntCollections$IterableCollection",
					"it.unimi.dsi.fastutil.longs.LongCollections$IterableCollection",
					"it.unimi.dsi.fastutil.objects.ObjectCollections$IterableCollection",
					"it.unimi.dsi.fastutil.objects.ReferenceCollections$IterableCollection",
					"it.unimi.dsi.fastutil.shorts.ShortCollections$IterableCollection");
			//
			Util.put(map,
					"net.bytebuddy.agent.builder.AgentBuilder$RedefinitionStrategy$Listener$Compound$CompoundIterable",
					"iterables");
			//
			Util.put(map,
					"net.bytebuddy.agent.builder.AgentBuilder$RedefinitionStrategy$ResubmissionStrategy$Enabled$Resubmitter$ConcurrentHashSet",
					DELEGATE);
			//
			Util.put(map, "net.bytebuddy.build.Plugin$Engine$Source$Compound$Origin", "origins");
			//
			Util.put(map, "net.bytebuddy.build.Plugin$Engine$Source$ForFolder", "folder");
			//
			Util.put(map, "net.bytebuddy.build.Plugin$Engine$Source$InMemory", "storage");
			//
			Util.put(map, "net.bytebuddy.build.Plugin$Engine$Source$Origin$Filtering", DELEGATE);
			//
			Util.put(map, "net.bytebuddy.build.Plugin$Engine$Source$Origin$ForJarFile", "file");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$ForLoadedFieldType",
					"field");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$ForLoadedReturnType",
					"method");
			//
			Util.put(map,
					"net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$OfConstructorParameter",
					"constructor");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$OfMethodParameter",
					"method");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$OfRecordComponent",
					"recordComponent");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$LazyProjection$WithResolvedErasure",
					DELEGATE);
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$OfTypeVariable$ForLoadedType",
					"typeVariable");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$OfTypeVariable$WithAnnotationOverlay",
					"typeVariable");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$OfWildcardType$ForLoadedType",
					"wildcardType");
			//
			Util.put(map, "net.bytebuddy.description.type.TypeDescription$Generic$OfWildcardType$Latent",
					"lowerBounds");
			//
			Util.put(map, "net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$TokenizedGenericType",
					"genericTypeToken");
			//
			putAll(map, "l", "it.unimi.dsi.fastutil.booleans.AbstractBooleanBigList$BooleanRandomAccessSubList",
					"it.unimi.dsi.fastutil.booleans.AbstractBooleanBigList$BooleanSubList",
					"it.unimi.dsi.fastutil.booleans.AbstractBooleanList$BooleanRandomAccessSubList",
					"it.unimi.dsi.fastutil.booleans.AbstractBooleanList$BooleanSubList",
					"it.unimi.dsi.fastutil.bytes.AbstractByteBigList$ByteRandomAccessSubList",
					"it.unimi.dsi.fastutil.bytes.AbstractByteBigList$ByteSubList",
					"it.unimi.dsi.fastutil.bytes.AbstractByteList$ByteRandomAccessSubList",
					"it.unimi.dsi.fastutil.bytes.AbstractByteList$ByteSubList",
					"it.unimi.dsi.fastutil.chars.AbstractCharBigList$CharRandomAccessSubList",
					"it.unimi.dsi.fastutil.chars.AbstractCharBigList$CharSubList",
					"it.unimi.dsi.fastutil.chars.AbstractCharList$CharRandomAccessSubList",
					"it.unimi.dsi.fastutil.chars.AbstractCharList$CharSubList",
					"it.unimi.dsi.fastutil.doubles.AbstractDoubleBigList$DoubleRandomAccessSubList",
					"it.unimi.dsi.fastutil.doubles.AbstractDoubleBigList$DoubleSubList",
					"it.unimi.dsi.fastutil.doubles.AbstractDoubleList$DoubleRandomAccessSubList",
					"it.unimi.dsi.fastutil.doubles.AbstractDoubleList$DoubleSubList",
					"it.unimi.dsi.fastutil.floats.AbstractFloatBigList$FloatRandomAccessSubList",
					"it.unimi.dsi.fastutil.floats.AbstractFloatBigList$FloatSubList",
					"it.unimi.dsi.fastutil.floats.AbstractFloatList$FloatRandomAccessSubList",
					"it.unimi.dsi.fastutil.floats.AbstractFloatList$FloatSubList",
					"it.unimi.dsi.fastutil.ints.AbstractIntBigList$IntRandomAccessSubList",
					"it.unimi.dsi.fastutil.ints.AbstractIntBigList$IntSubList",
					"it.unimi.dsi.fastutil.ints.AbstractIntList$IntRandomAccessSubList",
					"it.unimi.dsi.fastutil.ints.AbstractIntList$IntSubList",
					"it.unimi.dsi.fastutil.longs.AbstractLongBigList$LongRandomAccessSubList",
					"it.unimi.dsi.fastutil.longs.AbstractLongBigList$LongSubList",
					"it.unimi.dsi.fastutil.longs.AbstractLongList$LongRandomAccessSubList",
					"it.unimi.dsi.fastutil.longs.AbstractLongList$LongSubList",
					"it.unimi.dsi.fastutil.objects.AbstractObjectBigList$ObjectRandomAccessSubList",
					"it.unimi.dsi.fastutil.objects.AbstractObjectBigList$ObjectSubList",
					"it.unimi.dsi.fastutil.objects.AbstractObjectList$ObjectRandomAccessSubList",
					"it.unimi.dsi.fastutil.objects.AbstractObjectList$ObjectSubList",
					"it.unimi.dsi.fastutil.objects.AbstractReferenceBigList$ReferenceRandomAccessSubList",
					"it.unimi.dsi.fastutil.objects.AbstractReferenceBigList$ReferenceSubList",
					"it.unimi.dsi.fastutil.objects.AbstractReferenceList$ReferenceRandomAccessSubList",
					"it.unimi.dsi.fastutil.objects.AbstractReferenceList$ReferenceSubList",
					"it.unimi.dsi.fastutil.shorts.AbstractShortBigList$ShortRandomAccessSubList",
					"it.unimi.dsi.fastutil.shorts.AbstractShortBigList$ShortSubList",
					"it.unimi.dsi.fastutil.shorts.AbstractShortList$ShortRandomAccessSubList",
					"it.unimi.dsi.fastutil.shorts.AbstractShortList$ShortSubList");
			//
			putAll(map, "a", "it.unimi.dsi.fastutil.booleans.BooleanImmutableList",
					"it.unimi.dsi.fastutil.bytes.ByteImmutableList", "it.unimi.dsi.fastutil.chars.CharImmutableList",
					"it.unimi.dsi.fastutil.doubles.DoubleImmutableList",
					"it.unimi.dsi.fastutil.floats.FloatImmutableList", "it.unimi.dsi.fastutil.ints.IntImmutableList",
					"it.unimi.dsi.fastutil.longs.LongImmutableList",
					"it.unimi.dsi.fastutil.objects.ObjectImmutableList",
					"it.unimi.dsi.fastutil.objects.ReferenceImmutableList",
					"it.unimi.dsi.fastutil.shorts.ShortImmutableList");
			//
			putAll(map, "this$0", "it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.bytes.AbstractByte2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.chars.AbstractChar2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.floats.AbstractFloat2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.ints.AbstractInt2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.longs.AbstractLong2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractObject2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.objects.AbstractReference2ShortSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ByteSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ByteSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2CharSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2CharSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2DoubleSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2DoubleSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2FloatSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2FloatSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2IntSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2IntSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2LongSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2LongSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceSortedMap$ValuesCollection",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ShortSortedMap$KeySet",
					"it.unimi.dsi.fastutil.shorts.AbstractShort2ShortSortedMap$ValuesCollection");
			//
			putAll(map, "key", "it.unimi.dsi.fastutil.doubles.DoubleOpenHashBigSet",
					"it.unimi.dsi.fastutil.floats.FloatOpenHashBigSet", "it.unimi.dsi.fastutil.ints.IntOpenHashBigSet",
					"it.unimi.dsi.fastutil.longs.LongOpenHashBigSet",
					"it.unimi.dsi.fastutil.objects.ObjectOpenHashBigSet",
					"it.unimi.dsi.fastutil.objects.ReferenceOpenHashBigSet");
			//
			putAll(map, "objects", "org.apache.pdfbox.cos.COSArray", "org.apache.pdfbox.cos.COSIncrement");
			//
			put(map, "org.apache.pdfbox.pdmodel.PDPageTree", "root");
			//
			put(map, "org.apache.pdfbox.pdmodel.common.COSArrayList", "actual");
			//
			put(map, "org.apache.pdfbox.pdmodel.interactive.form.PDFieldTree", "acroForm");
			//
			put(map, "com.google.gson.JsonArray", "elements");
			//
			put(map, "com.google.gson.internal.NonNullElementWrapperList", DELEGATE);
			//
			if ((iValue0 = iterator(clz, instance, map)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
		} catch (final ReflectiveOperationException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} catch (final NullPointerException npe) {
			//
			if ((iValue0 = handleIteratorThrowable(instance, clz)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
			throw npe;
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.iterator();
		//
	}

	@Nullable
	static <E> E next(@Nullable final Iterator<E> instance) {
		return instance != null ? instance.next() : null;
	}

	static boolean hasNext(@Nullable final Iterator<?> instance) {
		return instance != null && instance.hasNext();
	}

	@Nullable
	private static <T> IValue0<Iterator<T>> iterator(@Nullable final Class<?> clz, final Object instance,
			final Map<String, String> map) throws NoSuchFieldException {
		//
		final String name = getName(clz);
		//
		if (containsKey(map, name) && testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, MapUtils.getObject(map, name), x -> Narcissus.findField(clz, x), null),
				x -> Narcissus.getField(instance, x), null) == null) {
			//
			return Unit.with(null);
			//
		} // if
			//
		return null;
		//
	}

	static boolean containsKey(@Nullable final Map<?, ?> instance, @Nullable final Object key) {
		return instance != null && instance.containsKey(key);
	}

	@Nullable
	static <K> Set<K> keySet(@Nullable final Map<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	@Nullable
	static <V> Collection<V> values(@Nullable final Map<?, V> instance) {
		return instance != null ? instance.values() : null;
	}

	@Nullable
	private static <T> IValue0<Iterator<T>> iterator(@Nullable final Class<?> clz, final Object instance)
			throws ReflectiveOperationException, IOException {
		//
		final Method method = testAndApply(Objects::nonNull, clz, x -> Narcissus.findMethod(x, "iterator"), null);
		//
		String fieldName = getFieldNmaeIfSingleLineReturnMethod(method);
		//
		if (StringUtils.isNotBlank(fieldName)
				&& Narcissus.getField(instance, Narcissus.findField(clz, fieldName)) == null) {
			//
			return Unit.with(null);
			//
		} // if
			//
		if (StringUtils.isNotBlank(fieldName = getFieldNmaeForStreamOfAndIterator(method))
				&& Narcissus.getField(instance, Narcissus.findField(clz, fieldName)) == null) {
			//
			return Unit.with(null);
			//
		} // if
			//
		final String name = getName(clz);
		//
		if (Boolean.logicalOr(
				Objects.equals("org.apache.commons.math3.util.IntegerSequence$Range", name)
						&& Narcissus.getIntField(instance, Narcissus.findField(clz, "step")) == 0,
				Objects.equals("org.apache.commons.math3.util.MultidimensionalCounter", name)
						&& (Narcissus.getIntField(instance, Narcissus.findField(clz, "dimension"))) == 0)) {
			//
			return Unit.with(null);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static <T> IValue0<Iterator<T>> handleIteratorThrowable(final Object instance,
			@Nullable final Class<?> clz) {
		//
		try {
			//
			if (Objects.equals("com.healthmarketscience.jackcess.impl.TableDefinitionImpl", getName(clz))
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_database")) == null) {
				//
				return Unit.with(null);
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return null;
		//
	}

	static <T, E extends Throwable> boolean test(@Nullable final FailablePredicate<T, E> instance,
			@Nullable final T value) throws E {
		return instance != null && instance.test(value);
	}

	static <K, V> void putAll(@Nullable final Map<K, V> a, @Nullable final Map<? extends K, ? extends V> b) {
		if (a != null && (b != null || Proxy.isProxyClass(getClass(a)))) {
			a.putAll(b);
		}
	}

	private static <K, V> void putAll(final Map<K, V> instance, final V v, final K k, @Nullable final K... ks) {
		//
		Util.put(instance, k, v);
		//
		for (int i = 0; ks != null && i < ks.length; i++) {
			//
			Util.put(instance, ks[i], v);
			//
		} // for
			//
	}

	@Nullable
	private static String getFieldNmaeForStreamOfAndIterator(@Nullable final Method method) throws IOException {
		//
		final Class<?> clz = getDeclaringClass(method);
		//
		try (final InputStream is = getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					method);
			//
			final Instruction[] instructions = InstructionListUtil
					.getInstructions(MethodGenUtil
							.getInstructionList(testAndApply(x -> FieldOrMethodUtil.getConstantPool(x) != null, m,
									x -> new MethodGen(x, null, testAndApply(Objects::nonNull, x,
											y -> new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(y)), null)),
									null)));
			//
			Instruction instruction = null;
			//
			SortedSet<Boolean> booleans = null;
			//
			GETFIELD getField = null;
			//
			INVOKESTATIC invokeStatic = null;
			//
			INVOKEINTERFACE invokeInterface = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				instruction = instructions[i];
				//
				if (i == 0) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), instruction instanceof ALOAD);
					//
				} else if (i == 1) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new),
							(getField = cast(GETFIELD.class, instruction)) != null);
					//
				} else if (i == 2) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new),
							(invokeStatic = cast(INVOKESTATIC.class, instruction)) != null);
					//
				} else if (i == 3) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new),
							(invokeInterface = cast(INVOKEINTERFACE.class, instruction)) != null);
					//
				} else if (i == 4) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), instruction instanceof ARETURN);
					//
				} else {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), Boolean.FALSE);
					//
					break;
					//
				} // if
					//
			} // for
				//
			if (CollectionUtils.isNotEmpty(booleans) && booleans.first() != null && booleans.first().booleanValue()) {
				//
				final ConstantPoolGen cpg = new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m));
				//
				if (Objects.equals(TypeUtil.getClassName(getReferenceType(invokeStatic, cpg)),
						"java.util.stream.Stream")
						&& Objects.equals(InvokeInstructionUtil.getMethodName(invokeStatic, cpg), "of")
						&& Objects.equals(collect(
								Util.map(Arrays.stream(InvokeInstructionUtil.getArgumentTypes(invokeStatic, cpg)),
										Util::toString),
								Collectors.joining(",")), "java.lang.Object[]")
						&& Objects.equals(InvokeInstructionUtil.getMethodName(invokeInterface, cpg), "iterator")
						&& Objects.equals(Util.collect(
								Util.map(Arrays.stream(InvokeInstructionUtil.getArgumentTypes(invokeInterface, cpg)),
										Util::toString),
								Collectors.joining(",")), "")) {
					//
					return FieldInstructionUtil.getFieldName(getField, cpg);
					//
				} // if
					//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	static InputStream getResourceAsStream(@Nullable final Class<?> instance, @Nullable final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

	@Nullable
	static <T, R, A> R collect(@Nullable final Stream<T> instance,
			@Nullable final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	@Nullable
	static <T> Stream<T> distinct(@Nullable final Stream<T> instance) {
		return instance != null ? instance.distinct() : null;
	}

	@Nullable
	private static ReferenceType getReferenceType(@Nullable final FieldOrMethod instance,
			@Nullable final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getReferenceType(cpg);
			//
		} // if
			//
		Object constantPool = null;
		//
		try {
			//
			if (cpg != null
					&& (constantPool = FieldUtils.readField(cpg.getConstantPool(), "constantPool", true)) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		final Object[] os = cast(Object[].class, constantPool);
		//
		if (os != null && os.length < instance.getIndex()) {
			//
			return null;
			//
		} // if
			//
		return cpg != null ? instance.getReferenceType(cpg) : null;
		//
	}

	@Nullable
	private static String getFieldNmaeIfSingleLineReturnMethod(final Method method) throws IOException {
		//
		final Class<?> clz = getDeclaringClass(method);
		//
		try (final InputStream is = getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					method);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(
					MethodGenUtil.getInstructionList(testAndApply(x -> FieldOrMethodUtil.getConstantPool(x) != null, m,
							x -> new MethodGen(x, null,
									x != null ? new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x)) : null),
							null)));
			//
			Instruction instruction = null;
			//
			SortedSet<Boolean> booleans = null;
			//
			GETFIELD getField = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = instructions[i]) == null) {
					//
					continue;
					//
				} // if
					//
				if (i == 0) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), instruction instanceof ALOAD);
					//
				} else if (i == 1) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new),
							(getField = cast(GETFIELD.class, instruction)) != null);
					//
				} else if (i == 2) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new),
							instruction instanceof INVOKEINTERFACE);
					//
				} else if (i == 3) {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), instruction instanceof ARETURN);
					//
				} else {
					//
					add(booleans = ObjectUtils.getIfNull(booleans, TreeSet::new), Boolean.FALSE);
					//
					break;
					//
				} // if
					//
			} // for
				//
			if (CollectionUtils.isNotEmpty(booleans) && booleans.first() != null && booleans.first().booleanValue()) {
				//
				return FieldInstructionUtil.getFieldName(getField,
						new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m)));
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	static Dimension getPreferredSize(@Nullable final Component instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getStaticField(Narcissus.findField(getClass(instance), "LOCK")) == null) {
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
		return instance.getPreferredSize();
		//
	}

	@Nullable
	static Method[] getMethods(@Nullable final Class<?> instance) {
		return instance != null ? instance.getMethods() : null;
	}

	@Nullable
	static byte[] digest(@Nullable final MessageDigest instance, @Nullable final byte[] input) {
		return instance != null && input != null ? instance.digest(input) : null;
	}

	static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	static double doubleValue(@Nullable final Number instance, final double defaultValue) {
		return instance != null ? instance.doubleValue() : defaultValue;
	}

	@Nullable
	static Path toPath(@Nullable final File instance) {
		return instance != null ? instance.toPath() : null;
	}

	static boolean isFile(@Nullable final File instance) {
		return instance != null && instance.isFile();
	}

	static boolean exists(@Nullable final File instance) {
		return instance != null && instance.exists();
	}

	@Nullable
	static String getName(@Nullable final File instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	@Nullable
	static File toFile(@Nullable final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	static void run(@Nullable final Runnable instance) {
		if (instance != null) {
			instance.run();
		}
	}

	@Nullable
	static String name(@Nullable final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	@Nullable
	static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	static void clear(final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

}