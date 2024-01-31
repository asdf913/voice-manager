package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.FieldOrMethod;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.ProxyFactory;

public abstract class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	private static final String UNDER_SCORE_ROWS = "_rows";

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
		try {
			//
			final Method method = Narcissus.findMethod(clz, "iterator");
			//
			String fieldName = getFieldNmaeIfSingleLineReturnMethod(method);
			//
			if (StringUtils.isNotBlank(fieldName)
					&& Narcissus.getField(instance, Narcissus.findField(clz, fieldName)) == null) {
				//
				return null;
				//
			} // if
				//
			if (StringUtils.isNotBlank(fieldName = getFieldNmaeForStreamOfAndIterator(method))
					&& Narcissus.getField(instance, Narcissus.findField(clz, fieldName)) == null) {
				//
				return null;
				//
			} // if
				//
//			final Iterable<FailableFunction<Object, IValue0<Iterator<?>>, ReflectiveOperationException>> functions = Arrays
//					.asList(Util::iterator2);
//			//
//			if (functions != null && functions.iterator() != null) {
//				//
//				IValue0<Iterator<?>> iValue0 = null;
//				//
//				for (final FailableFunction<Object, IValue0<Iterator<?>>, ReflectiveOperationException> function : functions) {
//					//
//					if ((iValue0 = FailableFunctionUtil.apply(function, instance)) != null) {
//						//
//						return (Iterator<T>) IValue0Util.getValue0(iValue0);
//						//
//					} // if
//						//
//						//
//				} // for
//					//
//			} // if
			//
			if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name) && Narcissus
					.invokeMethod(instance, Narcissus.findMethod(clz, "createRowState", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (or(
					isAssignableFrom(Class.forName("org.apache.commons.collections.collection.PredicatedCollection"),
							clz),
					isAssignableFrom(Class.forName("org.apache.commons.collections.collection.SynchronizedCollection"),
							clz),
					isAssignableFrom(
							Class.forName("org.apache.commons.collections.collection.AbstractCollectionDecorator"),
							clz))
					&& Narcissus.getField(instance, Narcissus.findField(clz, "collection")) == null) {
				//
				return null;
				//
			} else if (or(isAssignableFrom(
					Class.forName("org.apache.commons.collections4.collection.AbstractCollectionDecorator"), clz),
					isAssignableFrom(Class.forName("org.apache.commons.collections4.collection.SynchronizedCollection"),
							clz))
					&& Narcissus.invokeMethod(instance,
							Narcissus.findMethod(clz, "decorated", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.math3.genetics.ListPopulation"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "chromosomes")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.math3.geometry.partitioning.AbstractRegion"),
					clz) && Narcissus.getField(instance, Narcissus.findField(clz, "tree")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.poifs.property.DirectoryProperty"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_children")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFTextShape"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_paragraphs")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFSheet"), clz) && Narcissus
					.invokeMethod(instance, Narcissus.findMethod(clz, "getXmlObject", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xslf.usermodel.XSLFTextParagraph"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_runs")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.DeferredSXSSFSheet"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, UNDER_SCORE_ROWS)) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.SXSSFWorkbook"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_wb")) == null) {
				//
				return null;
				//
			} else if (or(isAssignableFrom(Class.forName("org.apache.poi.xssf.streaming.SXSSFSheet"), clz),
					isAssignableFrom(Class.forName("org.apache.poi.xssf.usermodel.XSSFSheet"), clz))
					&& Narcissus.getField(instance, Narcissus.findField(clz, UNDER_SCORE_ROWS)) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.poi.xssf.usermodel.XSSFShape"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "drawing")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.openjdk.nashorn.internal.runtime.PropertyMap"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "properties")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_hashArea")) == null) {
				//
				return null;
				//
			} else if (or(isAssignableFrom(Class.forName("com.google.common.collect.ForwardingQueue"), clz),
					isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.ForwardingQueue"),
							clz))
					&& Narcissus.invokeMethod(instance,
							Narcissus.findMethod(clz, "delegate", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (or(isAssignableFrom(Class.forName("com.google.common.collect.Maps$KeySet"), clz),
					isAssignableFrom(Class.forName("com.google.common.collect.Maps$Values"), clz),
					isAssignableFrom(
							Class.forName("org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardKeySet"),
							clz),
					isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.Maps$Values"), clz),
					isAssignableFrom(Class.forName("org.apache.jena.ext.com.google.common.collect.Maps$KeySet"), clz))
					&& Narcissus.invokeMethod(instance, Narcissus.findMethod(clz, "map", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (or(
					isAssignableFrom(Class.forName("com.google.common.collect.ForwardingMultiset$StandardElementSet"),
							clz),
					isAssignableFrom(Class.forName("com.google.common.collect.SortedMultisets$ElementSet"), clz),
					isAssignableFrom(Class.forName(
							"org.apache.jena.ext.com.google.common.collect.ForwardingMultiset$StandardElementSet"),
							clz),
					isAssignableFrom(Class.forName(
							"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMultiset$StandardElementSet"),
							clz))
					&& Narcissus.invokeMethod(instance,
							Narcissus.findMethod(clz, "multiset", new Class<?>[] {})) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("java.util.TreeSet"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "m")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("java.util.HashSet"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("com.opencsv.CSVReader"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "peekedLines")) == null) {
				//
				return null;
				//
			} else if (or(isAssignableFrom(Class.forName("org.apache.commons.collections.bag.AbstractMapBag"), clz),
					isAssignableFrom(Class.forName("org.apache.commons.collections4.bag.AbstractMapBag"), clz),
					isAssignableFrom(Class.forName("org.apache.commons.collections4.multiset.AbstractMapMultiSet"),
							clz))
					&& Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
				//
				return null;
				//
			} else if (isAssignableFrom(Class.forName("org.apache.commons.collections.DefaultMapBag"), clz)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_map")) == null) {
				//
				return null;
				//
			} // if
				//
			if (contains(Arrays.asList("org.apache.commons.math3.util.IntegerSequence$Range"), name)
					&& Narcissus.getIntField(instance, Narcissus.findField(clz, "step")) == 0) {
				//
				return null;
				//
			} else if (contains(Arrays.asList("org.apache.commons.math3.util.MultidimensionalCounter"), name)
					&& (Narcissus.getIntField(instance, Narcissus.findField(clz, "dimension"))) == 0) {
				//
				return null;
				//
			} // if
				//
			final Map<String, String> map = new LinkedHashMap<>();
			//
			Util.put(map, "com.google.common.collect.HashMultiset", "backingMap");
			//
			Util.put(map, "com.google.common.collect.LinkedHashMultiset", "backingMap");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.collect.HashMultiset", "backingMap");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.collect.LinkedHashMultiset", "backingMap");
			//
			Util.put(map, "org.openjdk.nashorn.internal.runtime.SharedPropertyMap", "properties");
			//
			Util.put(map, "com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap", "_hashArea");
			//
			Util.put(map, "com.google.common.collect.ForwardingQueue", "_hashArea");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.collect.ForwardingQueue", "_hashArea");
			//
			Util.put(map, "com.fasterxml.jackson.databind.node.ArrayNode", "_children");
			//
			Util.put(map, "com.fasterxml.jackson.databind.node.ObjectNode", "_children");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.DerivedKeyHashMap", "compactHashMap");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.HashMap", "compactHashMap");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.HashSet", "compactHashMap");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.TreeMap", "redBlackTree");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.TreeSet", "redBlackTree");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.Vector", "pointer");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.internal.adapter.ListAdapater", "list");
			//
			Util.put(map, "org.apache.commons.collections.FastArrayList", "list");
			//
			Util.put(map, "org.apache.commons.math3.geometry.partitioning.NodesSet", "list");
			//
			Util.put(map, "org.apache.logging.log4j.spi.MutableThreadContextStack", "list");
			//
			Util.put(map, "com.github.andrewoma.dexx.collection.internal.base.MappedIterable", "from");
			//
			Util.put(map, "com.google.common.collect.ConcurrentHashMultiset", "countMap");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.collect.ConcurrentHashMultiset", "countMap");
			//
			Util.put(map, "com.google.common.collect.TreeMultiset", "rootReference");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.collect.TreeMultiset", "rootReference");
			//
			Util.put(map, "com.google.common.reflect.TypeToken$TypeSet", "types");
			//
			Util.put(map, "org.apache.jena.ext.com.google.common.reflect.TypeToken$TypeSet", "types");
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
			Util.put(map, "org.apache.commons.collections.collection.CompositeCollection", "all");
			//
			Util.put(map, "org.apache.commons.collections.set.CompositeSet", "all");
			//
			Util.put(map, "org.apache.commons.collections4.collection.CompositeCollection", "all");
			//
			Util.put(map, "org.apache.commons.collections4.set.CompositeSet", "all");
			//
			Util.put(map, "org.apache.commons.collections.list.AbstractLinkedList$LinkedSubList", "parent");
			//
			Util.put(map, "org.apache.commons.collections.map.AbstractHashedMap$EntrySet", "parent");
			//
			Util.put(map, "org.apache.commons.collections.map.AbstractHashedMap$KeySet", "parent");
			//
			Util.put(map, "org.apache.commons.collections.map.AbstractHashedMap$Values", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.list.AbstractLinkedList$LinkedSubList", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.map.AbstractHashedMap$EntrySet", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.map.AbstractHashedMap$KeySet", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.map.AbstractHashedMap$Values", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.multiset.AbstractMultiSet$EntrySet", "parent");
			//
			Util.put(map, "org.apache.commons.collections4.multiset.AbstractMultiSet$UniqueSet", "parent");
			//
			Util.put(map, "org.apache.commons.collections.set.MapBackedSet", "map");
			//
			Util.put(map, "org.apache.commons.collections4.set.MapBackedSet", "map");
			//
			Util.put(map, "org.apache.commons.csv.CSVRecord", "values");
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
			Util.put(map, "org.apache.poi.hssf.usermodel.HSSFSheet", UNDER_SCORE_ROWS);
			//
			Util.put(map, "org.apache.poi.xslf.usermodel.XSLFTable", UNDER_SCORE_ROWS);
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
			Util.put(map, "org.apache.poi.xddf.usermodel.text.XDDFTextParagraph", "_runs");
			//
			Util.put(map, "org.apache.poi.xssf.usermodel.XSSFTextParagraph", "_runs");
			//
			Util.put(map, "org.apache.poi.xslf.usermodel.XSLFTableRow", "_cells");
			//
			Util.put(map, "org.apache.poi.xssf.streaming.SXSSFRow", "_cells");
			//
			Util.put(map, "org.apache.poi.xssf.usermodel.XSSFRow", "_cells");
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
			if (map.containsKey(name)
					&& Narcissus.getField(instance, Narcissus.findField(clz, map.get(name))) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final ReflectiveOperationException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} catch (final NullPointerException npe) {
			//
			try {
				//
				if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name)
						&& Narcissus.getField(instance, Narcissus.findField(clz, "_database")) == null) {
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

	private static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		if (a || b) {
			//
			return true;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (bs[i]) {
				//
				return true;
				//
			} // if
				//
		} // for
			//
		return false;
		//
	}

	@Nullable
	private static String getFieldNmaeForStreamOfAndIterator(final Method method) throws IOException {
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
				if (Objects.equals(getClassName(invokeStatic, cpg), "java.util.stream.Stream")
						&& Objects.equals(getMethodName(invokeStatic, cpg), "of")
						&& Objects.equals(
								collect(Util.map(Arrays.stream(getArgumentTypes(invokeStatic, cpg)), Util::toString),
										Collectors.joining(",")),
								"java.lang.Object[]")
						&& Objects.equals(getMethodName(invokeInterface, cpg), "iterator")
						&& Objects
								.equals(Util.map(Arrays.stream(getArgumentTypes(invokeInterface, cpg)), Util::toString)
										.collect(Collectors.joining(",")), "")) {
					//
					return getFieldName(getField, cpg);
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
	private static InputStream getResourceAsStream(@Nullable final Class<?> instance, @Nullable final String name) {
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
	private static String getClassName(@Nullable final FieldOrMethod instance, @Nullable final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getClassName(cpg);
			//
		} // if
			//
		return cpg != null ? instance.getClassName(cpg) : null;
		//
	}

	@Nullable
	private static String getMethodName(@Nullable final InvokeInstruction instance,
			@Nullable final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getMethodName(cpg);
			//
		} // if
			//
		return cpg != null ? instance.getMethodName(cpg) : null;
		//
	}

	@Nullable
	private static Type[] getArgumentTypes(@Nullable final InvokeInstruction instance,
			@Nullable final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getArgumentTypes(cpg);
			//
		} // if
			//
		return cpg != null ? instance.getArgumentTypes(cpg) : null;
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
				return getFieldName(getField, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m)));
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static String getFieldName(@Nullable final FieldInstruction instance, final ConstantPoolGen cpg) {
		return instance != null ? instance.getFieldName(cpg) : null;
	}

}