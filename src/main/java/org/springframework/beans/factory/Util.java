package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.CHECKCAST;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstructionUtil;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiPredicate;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.javatuples.valueintf.IValue2;
import org.javatuples.valueintf.IValue3;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.j256.simplemagic.ContentInfo;

import io.github.toolfactory.narcissus.Narcissus;

abstract class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	private static final String VALUE = "value";

	private static final String DELEGATE = "delegate";

	private Util() {
	}

	@Nullable
	static IValue0<UnicodeBlock> getUnicodeBlock(final String string) throws IllegalAccessException {
		//
		if (StringUtils.isBlank(string)) {
			//
			return Unit.with(null);
			//
		} else {
			//
			final List<Field> fs = toList(filter(Arrays.stream(getDeclaredFields(UnicodeBlock.class)),
					f -> StringUtils.startsWithIgnoreCase(getName(f), string)));
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (size == 0) {
				//
				return null;
				//
			} // if
				//
			final Field f = IterableUtils.get(fs, 0);
			//
			if (f == null || !isStatic(f)) {
				//
				return null;
				//
			} else if (!isAssignableFrom(f.getType(), UnicodeBlock.class)) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			return Unit.with(cast(UnicodeBlock.class, f.get(0)));
			//
		} // if
			//
	}

	@Nullable
	static Field[] getDeclaredFields(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	@Nullable
	static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	@Nullable
	static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	static void setUnicodeBlock(@Nullable final Object instance, final Consumer<UnicodeBlock> consumer)
			throws IllegalAccessException {
		//
		if (instance == null) {
			//
			accept(consumer, null);
			//
		} else if (instance instanceof UnicodeBlock ub) {
			//
			accept(consumer, ub);
			//
		} else if (instance instanceof String string) {
			//
			final IValue0<UnicodeBlock> iValue0 = getUnicodeBlock(string);
			//
			if (iValue0 != null) {
				//
				setUnicodeBlock(IValue0Util.getValue0(iValue0), consumer);
				//
			} // if
				//
		} else if (instance instanceof char[] cs) {
			//
			setUnicodeBlock(new String(cs), consumer);
			//
		} else if (instance instanceof byte[] bs) {
			//
			setUnicodeBlock(new String(bs), consumer);
			//
		} else {
			//
			throw new IllegalArgumentException(toString(instance));
			//
		} // if
			//
	}

	private static <T> void accept(@Nullable final Consumer<T> instance, @Nullable final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

	static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, @Nullable final T t, @Nullable final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	static <T> void accept(@Nullable final ObjIntConsumer<T> instance, @Nullable final T t, final int value) {
		if (instance != null) {
			instance.accept(t, value);
		}
	}

	@Nullable
	static String toString(@Nullable final Object instance) {
		//
		if (instance instanceof TextNode) {
			//
			try {
				//
				if (Narcissus.getField(instance, Narcissus.findField(getClass(instance), VALUE)) == null) {
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
		} // if
			//
		return instance != null ? instance.toString() : null;
		//
	}

	@Nullable
	static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	static <K, V> void putAll(@Nullable final Map<K, V> a, @Nullable final Map<? extends K, ? extends V> b) {
		if (a != null && (b != null || Proxy.isProxyClass(getClass(a)))) {
			a.putAll(b);
		}
	}

	@Nullable
	static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
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
	static <T, R> Stream<R> map(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	@Nullable
	static <T, R> Stream<R> flatMap(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends Stream<? extends R>> mapper) {
		return instance != null && mapper != null ? instance.flatMap(mapper) : null;
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
	static <T, R> R collect(@Nullable final Stream<T> instance, @Nullable final Supplier<R> supplier,
			@Nullable final BiConsumer<R, ? super T> accumulator, @Nullable final BiConsumer<R, R> combiner) {
		return instance != null && (Proxy.isProxyClass(getClass(instance))
				|| (supplier != null && accumulator != null && combiner != null))
						? instance.collect(supplier, accumulator, combiner)
						: null;
	}

	@Nullable
	static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	static <V> V get(@Nullable final Map<?, V> instance, @Nullable final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	static boolean containsKey(@Nullable final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	@Nullable
	static <K, V> Set<Entry<K, V>> entrySet(@Nullable final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	static boolean contains(@Nullable final Collection<?> items, @Nullable final Object item) {
		return items != null && items.contains(item);
	}

	static <E> void add(@Nullable final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	static <E> void addAll(@Nullable final Collection<E> a, @Nullable final Collection<? extends E> b) {
		if (a != null && (b != null || Proxy.isProxyClass(getClass(a)))) {
			a.addAll(b);
		}
	}

	static <E> void set(@Nullable final List<E> instance, final int index, final E element) {
		if (instance != null) {
			instance.set(index, element);
		}
	}

	@Nullable
	static <V> V get(@Nullable final AtomicReference<V> instance) {
		return instance != null ? instance.get() : null;
	}

	static <V> void set(@Nullable final AtomicReference<V> instance, final V value) {
		if (instance != null) {
			instance.set(value);
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
	static Class<?> getType(@Nullable final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	@Nullable
	static Matcher matcher(@Nullable final Pattern pattern, @Nullable final CharSequence input) {
		//
		if (pattern == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(pattern, getDeclaredField(Pattern.class, "pattern")) == null) {
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
		return input != null ? pattern.matcher(input) : null;
		//
	}

	static boolean matches(@Nullable final Matcher instance) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(instance, getDeclaredField(Matcher.class, "groups")) == null) {
				//
				return false;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.matches();
		//
	}

	static boolean matches(@Nullable final String instance, @Nullable final String regex) {
		//
		final List<Field> fs = toList(
				filter(stream(FieldUtils.getAllFieldsList(String.class)), f -> Objects.equals(getName(f), VALUE)));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field value = size == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (instance == null || Narcissus.getField(instance, value) == null
				|| (regex != null && Narcissus.getField(regex, value) == null)) {
			//
			return false;
			//
		} // if
			//
		return regex != null && instance.matches(regex);
		//
	}

	@Nullable
	static Class<?> getDeclaringClass(@Nullable final Member instance) {
		return instance != null ? instance.getDeclaringClass() : null;
	}

	@Nullable
	static String name(@Nullable final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	static int groupCount(@Nullable final MatchResult instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} else if (instance instanceof Matcher) {
			//
			try {
				//
				if (Narcissus.getField(instance, Narcissus.findField(getClass(instance), "parentPattern")) == null) {
					//
					return 0;
					//
				} // if
					//
			} catch (final NoSuchFieldException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // if
			//
		return instance.groupCount();
		//
	}

	@Nullable
	static String group(@Nullable final MatchResult instance, final int group) {
		return instance != null ? instance.group(group) : null;
	}

	@Nullable
	static String group(@Nullable final MatchResult instance) {
		return instance != null ? instance.group() : null;
	}

	static boolean find(@Nullable final Matcher instance) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(Matcher.class, "groups")) == null) {
				//
				return false;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.find();
		//
	}

	@Nullable
	static final InputStream openStream(@Nullable final URL instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(getClass(instance), "handler")) == null) {
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
		return instance.openStream();
		//
	}

	@Nullable
	static Field getDeclaredField(@Nullable final Class<?> instance, @Nullable final String name)
			throws NoSuchFieldException {
		return instance != null && name != null ? instance.getDeclaredField(name) : null;
	}

	static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	static char[] toCharArray(@Nullable final String instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(String.class, VALUE)) == null) {
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
		return instance.toCharArray();
		//
	}

	@Nullable
	static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	@Nullable
	static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Nullable
	static <T> T orElse(@Nullable final Optional<T> instance, @Nullable final T value) {
		return instance != null ? instance.orElse(value) : value;
	}

	@Nullable
	static <T> Optional<T> findFirst(@Nullable final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	@Nullable
	static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	@Nullable
	static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	static void append(@Nullable final StringBuilder instance, final char c) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final List<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(getClass(instance))),
				f -> Objects.equals(getName(f), VALUE)));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (Narcissus.getObjectField(instance, size == 1 ? IterableUtils.get(fs, 0) : null) == null) {
			//
			return;
			//
		} // if
			//
		instance.append(c);
		//
	}

	static boolean isAbsolute(@Nullable final URI instance) {
		return instance != null && instance.isAbsolute();
	}

	@Nullable
	static <T, U, R> R apply(@Nullable final BiFunction<T, U, R> instance, @Nullable final T t, @Nullable final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	static <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, @Nullable final T t, @Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a || b;
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	static int applyAsInt(@Nullable final IntUnaryOperator instance, final int operand, final int defaultValue) {
		return instance != null ? instance.applyAsInt(operand) : defaultValue;
	}

	@Nullable
	static OptionalInt max(@Nullable final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	@Nullable
	static IntStream chars(@Nullable final CharSequence instance) {
		return instance != null ? instance.chars() : null;
	}

	@Nullable
	static <U> Stream<U> mapToObj(@Nullable final IntStream instance, @Nullable final IntFunction<? extends U> mapper) {
		return instance != null && mapper != null ? instance.mapToObj(mapper) : null;
	}

	@Nullable
	static <X> X getValue2(@Nullable final IValue2<X> instance) {
		return instance != null ? instance.getValue2() : null;
	}

	@Nullable
	static <X> X getValue3(@Nullable final IValue3<X> instance) {
		return instance != null ? instance.getValue3() : null;
	}

	private static Map<String, FailableFunction<Object, Object, Exception>> STRING_FAILABLE_BI_FUNCTION_MAP = null;

	static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		final String name = getName(clz);
		//
		FailableFunction<Object, Object, Exception> function = get(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
				.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new), name);
		//
		try (final InputStream is = getResourceAsStream(clz, "/" + StringUtils.replace(name, ".", "/") + ".class")) {
			//
			if (function != null && function.apply(instance) == null) {
				//
				return;
				//
			} // if
				//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Method method = getForEachMethod(javaClass);
			//
			ConstantPoolGen cpg = null;
			//
			if (method != null && !executeForEachMethod(instance, name, STRING_FAILABLE_BI_FUNCTION_MAP,
					InstructionListUtil.getInstructions(
							new MethodGen(method, null, cpg = new ConstantPoolGen(method.getConstantPool()))
									.getInstructionList()),
					cpg)) {
				//
				return;
				//
			} // if
				//
			if (Objects.equals(getSuperclassName(javaClass), "java.lang.Object")
					&& !executeForEachMethod(JavaClassUtil.getMethods(javaClass), javaClass.getInterfaces(), instance,
							name, STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
									.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new))) {
				//
				return;
				//
			} // if
				//
		} catch (final Exception e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		try {
			//
			if (!executeForEachMethod2(instance, name)) {
				//
				return;
				//
			} // if
				//
		} catch (final ReflectiveOperationException e) {
			//
			throw new RuntimeException(e);
			//
		} catch (final NullPointerException e) {
			//
			if (!executeForEachMethodForNullPointerException(instance)) {
				//
				return;
				//
			} // if
				//
		} // try
			//
		if (action != null) {
			//
			instance.forEach(action);
			//
		} // if
			//
	}

	static void forEach(@Nullable final IntStream intStream, @Nullable final IntConsumer intConsumer) {
		if (intStream != null && intConsumer != null) {
			intStream.forEach(intConsumer);
		}
	}

	private static boolean executeForEachMethodForNullPointerException(final Object instance) {
		//
		try {
			//
			final Class<?> clz = getClass(instance);
			//
			if (Objects.equals("com.healthmarketscience.jackcess.impl.TableDefinitionImpl", getName(clz))
					&& Narcissus.getField(instance, Narcissus.findField(clz, "_database")) == null) {
				//
				return false;
				//
			} // if
				//
		} catch (final NoSuchFieldException nsfe) {
			//
			throw new RuntimeException(nsfe);
			//
		} // try
			//
		return true;
		//

	}

	private static boolean executeForEachMethod2(final Object instance, @Nullable final String name)
			throws ReflectiveOperationException {
		//
		// org.apache.commons.lang3.reflect.FieldUtils.readDeclaredField(java.lang.Object,java.lang.String,boolean)
		//
		final Map<String, String> map = new LinkedHashMap<>(Map.of(
				"com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap", "_hashArea",
				"com.fasterxml.jackson.databind.util.ArrayIterator", "_a",
				"com.github.andrewoma.dexx.collection.ArrayList", "elements",
				"com.github.andrewoma.dexx.collection.TreeMap", "tree", "com.github.andrewoma.dexx.collection.TreeSet",
				"redBlackTree", "com.github.andrewoma.dexx.collection.Vector", "pointer",
				"com.github.andrewoma.dexx.collection.internal.base.MappedIterable", "from",
				"com.google.common.collect.EnumMultiset", "enumConstants", "com.google.common.collect.EvictingQueue",
				DELEGATE, "com.healthmarketscience.jackcess.impl.DatabaseImpl", "_tableFinder"));
		//
		putAll(map,
				Map.of("com.healthmarketscience.jackcess.impl.IndexCursorImpl", "_entryCursor",
						"com.healthmarketscience.jackcess.impl.PropertyMapImpl", "_props",
						"com.healthmarketscience.jackcess.impl.TableImpl", "_columns",
						"com.healthmarketscience.jackcess.impl.TableScanCursor", "_ownedPagesCursor",
						"com.healthmarketscience.jackcess.impl.complex.MultiValueColumnPropertyMap", "_primary",
						"com.helger.commons.callback.CallbackList", "m_aRWLock",
						"com.helger.commons.collection.iterate.ArrayIterator", "m_aArray",
						"com.helger.commons.collection.iterate.IterableIterator", "m_aIter",
						"com.helger.commons.collection.iterate.MapperIterator", "m_aBaseIter",
						"com.helger.commons.collection.map.LRUSet", "m_aMap"));
		//
		putAll(map,
				Map.of("com.helger.commons.http.HttpHeaderMap", "m_aHeaders",
						"com.helger.commons.io.file.FileSystemRecursiveIterator", "m_aFilesLeft",
						"com.helger.commons.log.InMemoryLogger", "m_aMessages",
						"com.helger.commons.math.CombinationGenerator", "m_aCombinationsLeft",
						"com.opencsv.bean.CsvToBean", "mappingStrategy", "com.opencsv.bean.PositionToBeanField",
						"ranges", "com.sun.jna.platform.win32.Advapi32Util$EventLogIterator", "_buffer",
						"freemarker.core._SortedArraySet", "array", "freemarker.core._UnmodifiableCompositeSet", "set1",
						"org.apache.bcel.classfile.BootstrapMethods", "bootstrapMethods"));
		//
		putAll(map, Map.of("org.apache.bcel.classfile.ConstantPool", "constantPool",
				"org.apache.bcel.classfile.InnerClasses", "innerClasses", "org.apache.bcel.classfile.LineNumberTable",
				"lineNumberTable", "org.apache.bcel.classfile.LocalVariableTable", "localVariableTable",
				"org.apache.bcel.classfile.LocalVariableTypeTable", "localVariableTypeTable",
				"org.apache.bcel.classfile.MethodParameters", "parameters",
				"org.apache.commons.collections.CursorableLinkedList", "_head",
				"org.apache.commons.collections4.iterators.IteratorIterable", "typeSafeIterator",
				"org.apache.commons.io.IOExceptionList", "causeList", "org.apache.commons.math3.ml.neuralnet.Network",
				"neuronMap"));
		//
		putAll(map,
				Map.of("org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D", "network",
						"org.apache.ibatis.cursor.defaults.DefaultCursor", "cursorIterator",
						"org.apache.jena.atlas.lib.Map2", "map2", "org.apache.jena.atlas.lib.tuple.TupleN", "tuple",
						"org.apache.jena.ext.com.google.common.collect.EnumMultiset", "enumConstants",
						"org.apache.jena.ext.com.google.common.collect.EvictingQueue", DELEGATE,
						"org.apache.logging.log4j.message.StructuredDataCollectionMessage", "structuredDataMessageList",
						"org.apache.poi.ddf.EscherContainerRecord", "_childRecords",
						"org.apache.poi.hssf.record.aggregates.ValueRecordsAggregate", "records",
						"org.apache.poi.hssf.usermodel.HSSFRow", "cells"));
		//
		putAll(map, Map.of("org.apache.poi.hssf.usermodel.HSSFShapeGroup", "shapes",
				"org.apache.poi.hssf.usermodel.HSSFWorkbook", "_sheets",
				"org.apache.poi.openxml4j.opc.PackageRelationshipCollection", "relationshipsByID",
				"org.apache.poi.poifs.filesystem.DirectoryNode", "_entries",
				"org.apache.poi.poifs.filesystem.FilteringDirectoryNode", "directory",
				"org.apache.poi.poifs.filesystem.POIFSDocument", "_property",
				"org.apache.poi.poifs.filesystem.POIFSStream", "blockStore", "org.apache.poi.ss.util.SSCellRange",
				"_flattenedArray", "org.apache.poi.xslf.usermodel.XSLFNotes", "_notes",
				"org.apache.poi.xslf.usermodel.XSLFSlideLayout", "_layout"));
		//
		putAll(map, Map.of("org.apache.poi.xssf.streaming.SXSSFRow", "_cells",
				"org.apache.poi.xslf.usermodel.XSLFTableStyles", "_styles",
				"org.apache.poi.xssf.streaming.SXSSFDrawing", "_drawing", "org.apache.poi.xssf.streaming.SXSSFWorkbook",
				"_wb", "org.apache.poi.xssf.usermodel.XSSFSimpleShape", "_paragraphs",
				"org.apache.poi.xssf.usermodel.XSSFWorkbook", "sheets", "org.apache.xmlbeans.XmlSimpleList",
				"underlying", "org.d2ab.collection.ChainedCollection", "collections", "org.d2ab.collection.ChainedList",
				"lists", "org.d2ab.collection.longs.BitLongSet", "negatives"));
		//
		putAll(map, Map.of("org.openjdk.nashorn.internal.runtime.ListAdapter", "obj",
				"org.openjdk.nashorn.internal.runtime.PropertyMap", "properties",
				"org.oxbow.swingbits.action.ActionGroup", "actions", "org.springframework.beans.MutablePropertyValues",
				"propertyValueList", "org.springframework.core.env.MutablePropertySources", "propertySourceList",
				"org.springframework.util.AutoPopulatingList", "backingList"));
		//
		putAll(map, collect(Stream.of("com.fasterxml.jackson.databind.node.ArrayNode",
				"com.fasterxml.jackson.databind.node.ObjectNode", "org.apache.poi.poifs.property.DirectoryProperty"),
				Collectors.toMap(Function.identity(), x -> "_children")));
		//
		putAll(map,
				collect(Stream.of("com.github.andrewoma.dexx.collection.DerivedKeyHashMap",
						"com.github.andrewoma.dexx.collection.HashMap", "com.github.andrewoma.dexx.collection.HashSet"),
						Collectors.toMap(Function.identity(), x -> "compactHashMap")));
		//
		putAll(map, collect(Stream.of("com.github.andrewoma.dexx.collection.internal.adapter.ListAdapater",
				"org.apache.logging.log4j.spi.MutableThreadContextStack",
				"org.d2ab.collection.BiMappedList$RandomAccessList", "org.d2ab.collection.BiMappedList$SequentialList",
				"org.d2ab.collection.FilteredList", "org.d2ab.collection.MappedList$RandomAccessList",
				"org.d2ab.collection.MappedList$SequentialList", "org.d2ab.collection.chars.CharList$SubList",
				"org.d2ab.collection.doubles.DoubleList$SubList", "org.d2ab.collection.ints.IntList$SubList",
				"org.d2ab.collection.longs.LongList$SubList",
				"it.unimi.dsi.fastutil.booleans.BooleanBigLists$ListBigList",
				"it.unimi.dsi.fastutil.bytes.ByteBigLists$ListBigList",
				"it.unimi.dsi.fastutil.chars.CharBigLists$ListBigList",
				"it.unimi.dsi.fastutil.doubles.DoubleBigLists$ListBigList",
				"it.unimi.dsi.fastutil.floats.FloatBigLists$ListBigList",
				"it.unimi.dsi.fastutil.ints.IntBigLists$ListBigList",
				"it.unimi.dsi.fastutil.longs.LongBigLists$ListBigList",
				"it.unimi.dsi.fastutil.objects.ObjectBigLists$ListBigList",
				"it.unimi.dsi.fastutil.objects.ReferenceBigLists$ListBigList",
				"it.unimi.dsi.fastutil.objects.ReferenceBigLists$SynchronizedBigList",
				"it.unimi.dsi.fastutil.shorts.ShortBigLists$ListBigList"),
				Collectors.toMap(Function.identity(), x -> "list")));
		//
		putAll(map,
				collect(Stream.of("com.github.andrewoma.dexx.collection.internal.adapter.SetAdapater",
						"com.github.andrewoma.dexx.collection.internal.adapter.SortedSetAdapter",
						"org.springframework.cglib.beans.FixedKeySet"),
						Collectors.toMap(Function.identity(), x -> "set")));
		//
		putAll(map,
				collect(Stream.of("com.google.common.collect.ConcurrentHashMultiset",
						"org.apache.jena.ext.com.google.common.collect.ConcurrentHashMultiset"),
						Collectors.toMap(Function.identity(), x -> "countMap")));
		//
		putAll(map,
				collect(Stream.of("com.google.common.collect.TreeMultiset",
						"org.apache.jena.ext.com.google.common.collect.TreeMultiset"),
						Collectors.toMap(Function.identity(), x -> "rootReference")));
		//
		putAll(map,
				collect(Stream.of("com.google.common.reflect.TypeToken$TypeSet",
						"org.apache.jena.ext.com.google.common.reflect.TypeToken$TypeSet"),
						Collectors.toMap(Function.identity(), x -> "types")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.collections.collection.CompositeCollection",
						"org.apache.commons.collections4.collection.CompositeCollection",
						"org.apache.commons.collections4.set.CompositeSet"),
						Collectors.toMap(Function.identity(), x -> "all")));
		//
		putAll(map, collect(Stream.of("org.apache.commons.collections.collection.SynchronizedCollection",
				"org.apache.commons.collections4.collection.SynchronizedCollection",
				"org.d2ab.collection.CollectionList", "org.d2ab.collection.FilteredCollection",
				"org.d2ab.collection.MappedCollection", "org.d2ab.collection.chars.CollectionCharList",
				"org.d2ab.collection.doubles.CollectionDoubleList", "org.d2ab.collection.ints.CollectionIntList",
				"org.d2ab.collection.longs.CollectionLongList", "org.d2ab.sequence.CollectionSequence"),
				Collectors.toMap(Function.identity(), x -> "collection")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.collections.list.AbstractLinkedList$LinkedSubList",
						"org.apache.commons.collections.map.AbstractHashedMap$EntrySet",
						"org.apache.commons.collections.map.AbstractHashedMap$KeySet",
						"org.apache.commons.collections.map.AbstractHashedMap$Values",
						"org.apache.commons.collections4.list.AbstractLinkedList$LinkedSubList",
						"org.apache.commons.collections4.map.AbstractHashedMap$EntrySet",
						"org.apache.commons.collections4.map.AbstractHashedMap$KeySet",
						"org.apache.commons.collections4.map.AbstractHashedMap$Values",
						"org.apache.commons.collections4.multiset.AbstractMultiSet$EntrySet",
						"org.apache.commons.collections4.multiset.AbstractMultiSet$UniqueSet"),
						Collectors.toMap(Function.identity(), x -> "parent")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.collections.set.ListOrderedSet",
						"org.apache.commons.collections4.set.ListOrderedSet"),
						Collectors.toMap(Function.identity(), x -> "setOrder")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.collections.set.MapBackedSet",
						"org.apache.commons.collections4.set.MapBackedSet"),
						Collectors.toMap(Function.identity(), x -> "map")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.csv.CSVRecord", "org.d2ab.collection.chars.BitCharSet",
						"org.d2ab.collection.doubles.RawDoubleSet", "org.d2ab.collection.doubles.SortedListDoubleSet",
						"org.d2ab.collection.ints.BitIntSet"), Collectors.toMap(Function.identity(), x -> "values")));
		//
		putAll(map,
				collect(Stream.of("org.apache.jena.ext.xerces.impl.dv.util.ByteListImpl",
						"org.apache.xerces.impl.dv.util.ByteListImpl"),
						Collectors.toMap(Function.identity(), x -> "data")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.hssf.usermodel.HSSFPatriarch",
						"org.apache.poi.xslf.usermodel.XSLFGroupShape"),
						Collectors.toMap(Function.identity(), x -> "_shapes")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.hssf.usermodel.HSSFSheet", "org.apache.poi.xslf.usermodel.XSLFTable",
						"org.apache.poi.xssf.usermodel.XSSFSheet"),
						Collectors.toMap(Function.identity(), x -> "_rows")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xddf.usermodel.text.XDDFTextParagraph",
						"org.apache.poi.xslf.usermodel.XSLFTextParagraph"),
						Collectors.toMap(Function.identity(), x -> "_runs")));
		//
		putAll(map,
				collect(Stream.of("org.apache.xmlbeans.impl.values.JavaListObject",
						"org.apache.xmlbeans.impl.values.JavaListXmlObject"),
						Collectors.toMap(Function.identity(), x -> "sizer")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xslf.usermodel.XSLFNotesMaster",
						"org.apache.poi.xslf.usermodel.XSLFSlide", "org.apache.poi.xslf.usermodel.XSLFSlideMaster"),
						Collectors.toMap(Function.identity(), x -> "_slide")));
		//
		putAll(map, collect(Stream.of("org.d2ab.collection.ChainingIterable",
				"org.d2ab.collection.chars.ChainingCharIterable", "org.d2ab.collection.doubles.ChainingDoubleIterable",
				"org.d2ab.collection.ints.ChainingIntIterable", "org.d2ab.collection.longs.ChainingLongIterable"),
				Collectors.toMap(Function.identity(), x -> "iterables")));
		//
		putAll(map, collect(Stream.of("org.d2ab.collection.ReverseList", "org.d2ab.sequence.EquivalentSizeSequence"),
				Collectors.toMap(Function.identity(), x -> "original")));
		//
		putAll(map,
				collect(Stream.of("it.unimi.dsi.fastutil.booleans.AbstractBooleanBigList$BooleanSubList",
						"it.unimi.dsi.fastutil.booleans.AbstractBooleanList$BooleanSubList",
						"it.unimi.dsi.fastutil.bytes.AbstractByteBigList$ByteSubList",
						"it.unimi.dsi.fastutil.bytes.AbstractByteList$ByteSubList",
						"it.unimi.dsi.fastutil.chars.AbstractCharBigList$CharSubList",
						"it.unimi.dsi.fastutil.chars.AbstractCharList$CharSubList",
						"it.unimi.dsi.fastutil.doubles.AbstractDoubleBigList$DoubleSubList",
						"it.unimi.dsi.fastutil.doubles.AbstractDoubleList$DoubleSubList",
						"it.unimi.dsi.fastutil.floats.AbstractFloatBigList$FloatSubList",
						"it.unimi.dsi.fastutil.floats.AbstractFloatList$FloatSubList",
						"it.unimi.dsi.fastutil.ints.AbstractIntBigList$IntSubList",
						"it.unimi.dsi.fastutil.ints.AbstractIntList$IntSubList",
						"it.unimi.dsi.fastutil.longs.AbstractLongBigList$LongSubList",
						"it.unimi.dsi.fastutil.longs.AbstractLongList$LongSubList",
						"it.unimi.dsi.fastutil.objects.AbstractObjectBigList$ObjectSubList",
						"it.unimi.dsi.fastutil.objects.AbstractObjectList$ObjectSubList",
						"it.unimi.dsi.fastutil.objects.AbstractReferenceBigList$ReferenceSubList",
						"it.unimi.dsi.fastutil.objects.AbstractReferenceList$ReferenceSubList",
						"it.unimi.dsi.fastutil.shorts.AbstractShortBigList$ShortSubList",
						"it.unimi.dsi.fastutil.shorts.AbstractShortList$ShortSubList"),
						Collectors.toMap(Function.identity(), x -> "l")));
		//
		putAll(map,
				collect(Stream.of("it.unimi.dsi.fastutil.booleans.BooleanCollections$IterableCollection",
						"it.unimi.dsi.fastutil.bytes.ByteCollections$IterableCollection",
						"it.unimi.dsi.fastutil.chars.CharCollections$IterableCollection",
						"it.unimi.dsi.fastutil.doubles.DoubleCollections$IterableCollection",
						"it.unimi.dsi.fastutil.floats.FloatCollections$IterableCollection",
						"it.unimi.dsi.fastutil.ints.IntCollections$IterableCollection",
						"it.unimi.dsi.fastutil.longs.LongCollections$IterableCollection",
						"it.unimi.dsi.fastutil.objects.ObjectCollections$IterableCollection",
						"it.unimi.dsi.fastutil.objects.ReferenceCollections$IterableCollection",
						"it.unimi.dsi.fastutil.shorts.ShortCollections$IterableCollection"),
						Collectors.toMap(Function.identity(), x -> "iterable")));
		//
		putAll(map, collect(Stream.of("it.unimi.dsi.fastutil.booleans.BooleanImmutableList",
				"it.unimi.dsi.fastutil.bytes.ByteImmutableList", "it.unimi.dsi.fastutil.chars.CharImmutableList",
				"it.unimi.dsi.fastutil.doubles.DoubleImmutableList", "it.unimi.dsi.fastutil.floats.FloatImmutableList",
				"it.unimi.dsi.fastutil.ints.IntImmutableList", "it.unimi.dsi.fastutil.longs.LongImmutableList",
				"it.unimi.dsi.fastutil.objects.ObjectImmutableList",
				"it.unimi.dsi.fastutil.objects.ReferenceImmutableList",
				"it.unimi.dsi.fastutil.shorts.ShortImmutableList"), Collectors.toMap(Function.identity(), x -> "a")));
		//
		putAll(map,
				collect(Stream.of("it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanSortedMap$KeySet",
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
						"it.unimi.dsi.fastutil.shorts.AbstractShort2ShortSortedMap$ValuesCollection"

				), Collectors.toMap(Function.identity(), x -> "this$0")));
		//
		putAll(map,
				collect(Stream.of("it.unimi.dsi.fastutil.bytes.ByteLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.bytes.ByteLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.chars.CharLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.chars.CharLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.doubles.DoubleLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.doubles.DoubleLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.floats.FloatLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.floats.FloatLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.ints.IntLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.longs.LongLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.objects.ObjectLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet",
						"it.unimi.dsi.fastutil.shorts.ShortLinkedOpenCustomHashSet",
						"it.unimi.dsi.fastutil.shorts.ShortLinkedOpenHashSet"),
						Collectors.toMap(Function.identity(), x -> "link")));
		//
		putAll(map, collect(Stream.of("org.apache.pdfbox.cos.COSArray", "org.apache.pdfbox.cos.COSIncrement"),
				Collectors.toMap(Function.identity(), x -> "objects")));
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
		if (!executeForEachMethod(map, name, instance, (a, b) -> FieldUtils.readDeclaredField(a, b, true) == null)) {
			//
			return false;
			//
		} // if
			//
		map.clear();
		//
		// org.apache.commons.lang3.reflect.FieldUtils.readField(java.lang.Object,java.lang.String,boolean)
		//
		putAll(map, Map.of("org.apache.poi.poifs.property.RootProperty", "_children",
				"org.apache.poi.xslf.usermodel.XSLFDiagram$XSLFDiagramGroupShape", "_shapes",
				"com.helger.commons.io.file.FileSystemIterator", "m_aIter", "com.opencsv.bean.FieldMapByPosition",
				"complexMapList", "org.apache.commons.collections.set.CompositeSet", "all",
				"org.apache.commons.math3.genetics.ElitisticListPopulation", "chromosomes",
				"org.apache.poi.xssf.usermodel.XSSFRow", "_cells",
				"org.openjdk.nashorn.internal.runtime.JSONListAdapter", "obj",
				"org.openjdk.nashorn.internal.runtime.SharedPropertyMap", "properties",
				"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMultiset$StandardElementSet",
				"multiset"));
		//
		putAll(map,
				collect(Stream.of("com.google.common.collect.ForwardingMap$StandardKeySet",
						"com.google.common.collect.ForwardingMap$StandardValues",
						"com.google.common.collect.ForwardingNavigableMap$StandardNavigableKeySet",
						"com.google.common.collect.ForwardingSortedMap$StandardKeySet",
						"org.apache.commons.collections.bag.HashBag", "org.apache.commons.collections.bag.TreeBag",
						"org.apache.commons.collections4.bag.HashBag", "org.apache.commons.collections4.bag.TreeBag",
						"org.apache.commons.collections4.multiset.HashMultiSet",
						"org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardKeySet",
						"org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardValues",
						"org.apache.jena.ext.com.google.common.collect.ForwardingNavigableMap$StandardNavigableKeySet",
						"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMap$StandardKeySet"),
						Collectors.toMap(Function.identity(), x -> "map")));
		//
		putAll(map,
				collect(Stream.of("com.google.common.collect.ForwardingNavigableSet$StandardDescendingSet",
						"org.apache.jena.ext.com.google.common.collect.ForwardingNavigableSet$StandardDescendingSet"),
						Collectors.toMap(Function.identity(), x -> "forward")));
		//
		putAll(map, collect(
				Stream.of("com.google.common.collect.HashMultiset", "com.google.common.collect.LinkedHashMultiset",
						"org.apache.jena.ext.com.google.common.collect.HashMultiset",
						"org.apache.jena.ext.com.google.common.collect.LinkedHashMultiset"),
				Collectors.toMap(Function.identity(), x -> "backingMap")));
		//
		putAll(map, collect(Stream.of("com.opencsv.CSVReader", "com.opencsv.CSVReaderHeaderAware"),
				Collectors.toMap(Function.identity(), x -> "peekedLines")));
		//
		putAll(map,
				collect(Stream.of("org.apache.bcel.classfile.RuntimeInvisibleAnnotations",
						"org.apache.bcel.classfile.RuntimeVisibleAnnotations"),
						Collectors.toMap(Function.identity(), x -> "annotationTable")));
		//
		putAll(map,
				collect(Stream.of("org.apache.bcel.classfile.RuntimeInvisibleParameterAnnotations",
						"org.apache.bcel.classfile.RuntimeVisibleParameterAnnotations"),
						Collectors.toMap(Function.identity(), x -> "parameterAnnotationTable")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.collections.HashBag", "org.apache.commons.collections.TreeBag"),
						Collectors.toMap(Function.identity(), x -> "_map")));
		//
		putAll(map, collect(Stream.of("org.apache.commons.collections.bag.PredicatedBag",
				"org.apache.commons.collections.bag.PredicatedSortedBag",
				"org.apache.commons.collections.bag.SynchronizedBag",
				"org.apache.commons.collections.bag.SynchronizedSortedBag",
				"org.apache.commons.collections.bag.TransformedBag",
				"org.apache.commons.collections.bag.TransformedSortedBag",
				"org.apache.commons.collections.bag.UnmodifiableBag",
				"org.apache.commons.collections.bag.UnmodifiableSortedBag",
				"org.apache.commons.collections.bidimap.AbstractDualBidiMap$EntrySet",
				"org.apache.commons.collections.bidimap.AbstractDualBidiMap$KeySet",
				"org.apache.commons.collections.bidimap.AbstractDualBidiMap$Values",
				"org.apache.commons.collections.buffer.BlockingBuffer",
				"org.apache.commons.collections.buffer.BoundedBuffer",
				"org.apache.commons.collections.buffer.PredicatedBuffer",
				"org.apache.commons.collections.buffer.SynchronizedBuffer",
				"org.apache.commons.collections.buffer.TransformedBuffer",
				"org.apache.commons.collections.buffer.UnmodifiableBuffer",
				"org.apache.commons.collections.collection.PredicatedCollection",
				"org.apache.commons.collections.collection.TransformedCollection",
				"org.apache.commons.collections.collection.UnmodifiableBoundedCollection",
				"org.apache.commons.collections.collection.UnmodifiableCollection",
				"org.apache.commons.collections.list.FixedSizeList", "org.apache.commons.collections.list.GrowthList",
				"org.apache.commons.collections.list.LazyList", "org.apache.commons.collections.list.PredicatedList",
				"org.apache.commons.collections.list.SetUniqueList",
				"org.apache.commons.collections.list.SynchronizedList",
				"org.apache.commons.collections.list.TransformedList",
				"org.apache.commons.collections.list.UnmodifiableList",
				"org.apache.commons.collections.map.UnmodifiableEntrySet",
				"org.apache.commons.collections.set.PredicatedSet",
				"org.apache.commons.collections.set.PredicatedSortedSet",
				"org.apache.commons.collections.set.SynchronizedSet",
				"org.apache.commons.collections.set.SynchronizedSortedSet",
				"org.apache.commons.collections.set.TransformedSet",
				"org.apache.commons.collections.set.TransformedSortedSet",
				"org.apache.commons.collections.set.UnmodifiableSet",
				"org.apache.commons.collections.set.UnmodifiableSortedSet",
				"org.apache.commons.collections4.bag.CollectionBag",
				"org.apache.commons.collections4.bag.CollectionSortedBag",
				"org.apache.commons.collections4.bag.PredicatedBag",
				"org.apache.commons.collections4.bag.PredicatedSortedBag",
				"org.apache.commons.collections4.bag.SynchronizedBag",
				"org.apache.commons.collections4.bag.SynchronizedSortedBag",
				"org.apache.commons.collections4.bag.TransformedBag",
				"org.apache.commons.collections4.bag.TransformedSortedBag",
				"org.apache.commons.collections4.bag.UnmodifiableBag",
				"org.apache.commons.collections4.bag.UnmodifiableSortedBag",
				"org.apache.commons.collections4.bidimap.AbstractDualBidiMap$EntrySet",
				"org.apache.commons.collections4.bidimap.AbstractDualBidiMap$KeySet",
				"org.apache.commons.collections4.bidimap.AbstractDualBidiMap$Values",
				"org.apache.commons.collections4.collection.IndexedCollection",
				"org.apache.commons.collections4.collection.PredicatedCollection",
				"org.apache.commons.collections4.collection.TransformedCollection",
				"org.apache.commons.collections4.collection.UnmodifiableBoundedCollection",
				"org.apache.commons.collections4.collection.UnmodifiableCollection",
				"org.apache.commons.collections4.list.FixedSizeList", "org.apache.commons.collections4.list.GrowthList",
				"org.apache.commons.collections4.list.LazyList", "org.apache.commons.collections4.list.PredicatedList",
				"org.apache.commons.collections4.list.SetUniqueList",
				"org.apache.commons.collections4.list.TransformedList",
				"org.apache.commons.collections4.list.UnmodifiableList",
				"org.apache.commons.collections4.map.UnmodifiableEntrySet",
				"org.apache.commons.collections4.multiset.PredicatedMultiSet",
				"org.apache.commons.collections4.multiset.SynchronizedMultiSet",
				"org.apache.commons.collections4.multiset.UnmodifiableMultiSet",
				"org.apache.commons.collections4.queue.PredicatedQueue",
				"org.apache.commons.collections4.queue.SynchronizedQueue",
				"org.apache.commons.collections4.queue.TransformedQueue",
				"org.apache.commons.collections4.queue.UnmodifiableQueue",
				"org.apache.commons.collections4.set.PredicatedNavigableSet",
				"org.apache.commons.collections4.set.PredicatedSet",
				"org.apache.commons.collections4.set.PredicatedSortedSet",
				"org.apache.commons.collections4.set.TransformedNavigableSet",
				"org.apache.commons.collections4.set.TransformedSet",
				"org.apache.commons.collections4.set.TransformedSortedSet",
				"org.apache.commons.collections4.set.UnmodifiableNavigableSet",
				"org.apache.commons.collections4.set.UnmodifiableSet",
				"org.apache.commons.collections4.set.UnmodifiableSortedSet", "org.d2ab.collection.BiMappedCollection",
				"it.unimi.dsi.fastutil.booleans.BooleanBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.booleans.BooleanLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.booleans.BooleanLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.booleans.BooleanSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.bytes.ByteBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.bytes.ByteLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.bytes.ByteLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.bytes.ByteSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.bytes.ByteSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.chars.CharBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.chars.CharLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.chars.CharLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.chars.CharSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.chars.CharSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.doubles.DoubleBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.doubles.DoubleLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.doubles.DoubleLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.doubles.DoubleSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.doubles.DoubleSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.floats.FloatBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.floats.FloatLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.floats.FloatLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.floats.FloatSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.floats.FloatSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.ints.IntBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.ints.IntLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.ints.IntLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.ints.IntSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.ints.IntSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.longs.LongBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.longs.LongLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.longs.LongLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.longs.LongSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.longs.LongSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.objects.ObjectBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.objects.ObjectLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.objects.ObjectLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.objects.ObjectSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.objects.ObjectSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.objects.ReferenceBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.objects.ReferenceLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.objects.ReferenceLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.objects.ReferenceSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.objects.ReferenceSortedSets$UnmodifiableSortedSet",
				"it.unimi.dsi.fastutil.shorts.ShortBigLists$UnmodifiableBigList",
				"it.unimi.dsi.fastutil.shorts.ShortLists$UnmodifiableList",
				"it.unimi.dsi.fastutil.shorts.ShortLists$UnmodifiableRandomAccessList",
				"it.unimi.dsi.fastutil.shorts.ShortSets$UnmodifiableSet",
				"it.unimi.dsi.fastutil.shorts.ShortSortedSets$UnmodifiableSortedSet"),
				Collectors.toMap(Function.identity(), x -> "collection")));
		//
		putAll(map,
				collect(Stream.of("org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet",
						"org.apache.commons.math3.geometry.spherical.oned.ArcsSet"),
						Collectors.toMap(Function.identity(), x -> "tree")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.poifs.crypt.temp.SXSSFWorkbookWithCustomZipEntrySource",
						"org.apache.poi.xssf.streaming.DeferredSXSSFWorkbook"),
						Collectors.toMap(Function.identity(), x -> "_wb")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xslf.usermodel.XSLFAutoShape",
						"org.apache.poi.xslf.usermodel.XSLFFreeformShape",
						"org.apache.poi.xslf.usermodel.XSLFTableCell", "org.apache.poi.xslf.usermodel.XSLFTextBox",
						"org.apache.poi.xssf.usermodel.XSSFObjectData", "org.apache.poi.xssf.usermodel.XSSFTextBox"),
						Collectors.toMap(Function.identity(), x -> "_paragraphs")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xssf.streaming.DeferredSXSSFSheet",
						"org.apache.poi.xssf.streaming.SXSSFSheet", "org.apache.poi.xssf.usermodel.XSSFChartSheet",
						"org.apache.poi.xssf.usermodel.XSSFDialogsheet"),
						Collectors.toMap(Function.identity(), x -> "_rows")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xssf.usermodel.XSSFDrawing",
						"org.apache.poi.xssf.usermodel.XSSFShapeGroup"),
						Collectors.toMap(Function.identity(), x -> "drawing")));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xwpf.usermodel.XWPFEndnote",
						"org.apache.poi.xwpf.usermodel.XWPFFootnote"),
						Collectors.toMap(Function.identity(), x -> "paragraphs")));
		//
		putAll(map,
				collect(Stream.of("org.eclipse.jetty.http.MetaData$ConnectRequest",
						"org.eclipse.jetty.http.MetaData$Request", "org.eclipse.jetty.http.MetaData$Response"),
						Collectors.toMap(Function.identity(), x -> "_httpFields")));
		//
		putAll(map,
				collect(Stream.of("org.javatuples.Decade", "org.javatuples.Ennead", "org.javatuples.KeyValue",
						"org.javatuples.LabelValue", "org.javatuples.Octet", "org.javatuples.Pair",
						"org.javatuples.Quartet", "org.javatuples.Quintet", "org.javatuples.Septet",
						"org.javatuples.Sextet", "org.javatuples.Triplet", "org.javatuples.Unit"),
						Collectors.toMap(Function.identity(), x -> "valueList")));
		//
		putAll(map,
				collect(Stream.of("it.unimi.dsi.fastutil.booleans.BooleanBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.booleans.BooleanLists$SynchronizedList",
						"it.unimi.dsi.fastutil.booleans.BooleanLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.booleans.BooleanSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.bytes.ByteBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.bytes.ByteLists$SynchronizedList",
						"it.unimi.dsi.fastutil.bytes.ByteLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.bytes.ByteSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.bytes.ByteSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.chars.CharBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.chars.CharLists$SynchronizedList",
						"it.unimi.dsi.fastutil.chars.CharLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.chars.CharSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.chars.CharSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.doubles.DoubleBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.doubles.DoubleLists$SynchronizedList",
						"it.unimi.dsi.fastutil.doubles.DoubleLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.doubles.DoubleSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.doubles.DoubleSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.floats.FloatBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.floats.FloatLists$SynchronizedList",
						"it.unimi.dsi.fastutil.floats.FloatLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.floats.FloatSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.floats.FloatSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.ints.IntBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.ints.IntLists$SynchronizedList",
						"it.unimi.dsi.fastutil.ints.IntLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.ints.IntSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.ints.IntSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.longs.LongBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.longs.LongLists$SynchronizedList",
						"it.unimi.dsi.fastutil.longs.LongLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.longs.LongSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.longs.LongSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.objects.ObjectBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.objects.ObjectLists$SynchronizedList",
						"it.unimi.dsi.fastutil.objects.ObjectLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.objects.ObjectSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.objects.ObjectSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.objects.ReferenceLists$SynchronizedList",
						"it.unimi.dsi.fastutil.objects.ReferenceLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.objects.ReferenceSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.objects.ReferenceSortedSets$SynchronizedSortedSet",
						"it.unimi.dsi.fastutil.shorts.ShortBigLists$SynchronizedBigList",
						"it.unimi.dsi.fastutil.shorts.ShortLists$SynchronizedList",
						"it.unimi.dsi.fastutil.shorts.ShortLists$SynchronizedRandomAccessList",
						"it.unimi.dsi.fastutil.shorts.ShortSets$SynchronizedSet",
						"it.unimi.dsi.fastutil.shorts.ShortSortedSets$SynchronizedSortedSet"),
						Collectors.toMap(Function.identity(), x -> "sync")));
		//
		put(map, "org.jsoup.nodes.Element", "childNodes");
		//
		if (!executeForEachMethod(map, name, instance, (a, b) -> FieldUtils.readField(a, b, true) == null)) {
			//
			return false;
			//
		} // if
			//
		map.clear();
		//
		// io.github.toolfactory.narcissus.Narcissus.getField(java.lang.Object,java.lang.reflect.Field)
		//
		putAll(map,
				Map.of("com.helger.commons.collection.impl.CommonsCopyOnWriteArrayList", "array",
						"com.helger.commons.collection.impl.CommonsCopyOnWriteArraySet", "al",
						"com.helger.commons.collection.impl.CommonsTreeSet", "m",
						"org.springframework.beans.factory.support.ManagedSet", "map"));
		//
		putAll(map,
				collect(Stream.of("com.helger.commons.collection.impl.CommonsHashSet",
						"com.helger.commons.collection.impl.CommonsLinkedHashSet"),
						Collectors.toMap(Function.identity(), x -> "map")));
		//
		putAll(map,
				collect(Stream.of("org.jsoup.nodes.Document", "org.jsoup.nodes.FormElement",
						"org.jsoup.nodes.PseudoTextElement"),
						Collectors.toMap(Function.identity(), x -> "childNodes")));
		//
		if (!executeForEachMethod(map, name, instance,
				(a, b) -> Narcissus.getField(a, Narcissus.findField(getClass(instance), b)) == null)) {
			//
			return false;
			//
		} // if
			//
		return executeForEachMethod(instance, name);
		//
	}

	private static boolean executeForEachMethod(final Map<String, String> map, @Nullable final String name,
			final Object instance,
			@Nullable final FailableBiPredicate<Object, String, ReflectiveOperationException> predicate)
			throws ReflectiveOperationException {
		//
		final Iterable<Entry<String, String>> entrySet = entrySet(map);
		//
		if (entrySet != null) {
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (Objects.equals(name, getKey(entry))
						&& (predicate != null && predicate.test(instance, getValue(entry)))) {
					//
					return false;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return true;
		//
	}

	private static boolean executeForEachMethod(final Object instance, @Nullable final String name)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		//
		return !((Objects.equals(name, "org.apache.poi.ddf.EscherArrayProperty")
				&& Objects.equals(FieldUtils.readDeclaredField(instance, "emptyComplexPart", true), Boolean.FALSE)
				&& FieldUtils.readField(instance, "complexData", true) == null)
				|| (Objects.equals(name, "org.apache.commons.math3.util.MultidimensionalCounter")
						&& Objects.equals(FieldUtils.readDeclaredField(instance, "last", true),
								FieldUtils.readDeclaredField(instance, "dimension", true)))
				|| (Objects.equals(name, "org.apache.commons.math3.util.IntegerSequence$Range")
						&& Objects.equals(FieldUtils.readDeclaredField(instance, "step", true), Integer.valueOf(0)))
				|| (contains(
						Arrays.asList("com.google.common.collect.ForwardingMultiset$StandardElementSet",
								"com.google.common.collect.ForwardingSortedMultiset$StandardElementSet",
								"org.apache.jena.ext.com.google.common.collect.ForwardingMultiset$StandardElementSet"),
						name) && MethodUtils.invokeMethod(instance, true, "multiset") == null)
				|| (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name)
						&& Narcissus.invokeMethod(instance, Narcissus.findMethod(getClass(instance), "createRowState",
								new Class<?>[] {})) == null));
		//
	}

	@Nullable
	private static <T, R> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final Function<T, R> functionTrue, @Nullable final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	@Nullable
	private static <T, R> R apply(@Nullable final Function<T, R> instance, @Nullable final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static boolean executeForEachMethod(final Method[] ms, final JavaClass[] interfaces, final Object instance,
			@Nullable final String name, final Map<String, FailableFunction<Object, Object, Exception>> map)
			throws Exception {
		//
		String methodName = null;
		//
		Method method, m = null;
		//
		Instruction[] instructions;
		//
		ConstantPoolGen cpg;
		//
		FailableFunction<Object, Object, Exception> function;
		//
		Entry<String, Object> entry;
		//
		for (int i = 0; i < length(interfaces); i++) {
			//
			if ((method = getForEachMethod(interfaces[i])) == null) {
				//
				continue;
				//
			} // if
				//
			if ((m = getFirstInvokeInterfaceMethod(ms,
					InstructionListUtil.getInstructions(
							new MethodGen(method, null, cpg = new ConstantPoolGen(method.getConstantPool()))
									.getInstructionList()),
					cpg)) == null
					|| (instructions = InstructionListUtil
							.getInstructions(new MethodGen(m, null, cpg = new ConstantPoolGen(m.getConstantPool()))
									.getInstructionList())) == null) {
				//
				continue;
				//
			} // if
				//
			if (!executeForEachMethod3a(instructions, cpg, entry = Pair.of(name, instance),
					methodName = FieldOrMethodUtil.getName(m), map)) {
				//
				return false;
				//
			} else if (!executeForEachMethod3b(instructions, cpg, entry, methodName, map)) {
				//
				return false;
				//
			} else if (!executeForEachMethod3c(instructions, cpg, entry, map)) {
				//
				return false;
				//
			} else if (length(instructions) == 6 && instructions[0] instanceof ALOAD
					&& instructions[1] instanceof GETFIELD gf && instructions[2] instanceof CHECKCAST
					&& instructions[3] instanceof ALOAD && instructions[4] instanceof INVOKEVIRTUAL
					&& instructions[5] instanceof ARETURN) {
				//
				final String fieldName = FieldInstructionUtil.getFieldName(gf, cpg);
				//
				put(map, name, function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
				//
				if (function.apply(instance) == null) {
					//
					return false;
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return true;
		//
	}

	private static boolean executeForEachMethod3a(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final String methodName,
			final Map<String, FailableFunction<Object, Object, Exception>> map) throws Exception {
		//
		if (length(instructions) == 4 && instructions[0] instanceof ALOAD && instructions[1] instanceof GETFIELD gf
				&& instructions[2] instanceof INVOKEINTERFACE ii
				&& Objects.equals(InvokeInstructionUtil.getMethodName(ii, cpg), methodName)
				&& instructions[3] instanceof ARETURN) {
			//
			final FailableFunction<Object, Object, Exception> function = a -> a != null
					? FieldUtils.readDeclaredField(a, FieldInstructionUtil.getFieldName(gf, cpg), true)
					: null;
			//
			put(map, getKey(entry), function);
			//
			if (function.apply(getValue(entry)) == null) {
				//
				return false;
				//
			} // if
				//
		} // if
			//
		return true;
		//
	}

	private static boolean executeForEachMethod3b(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final String methodName,
			final Map<String, FailableFunction<Object, Object, Exception>> map) throws Exception {
		//
		if (length(instructions) == 5 && instructions[0] instanceof ALOAD && instructions[1] instanceof GETFIELD gf
				&& instructions[2] instanceof INVOKEINTERFACE && instructions[3] instanceof INVOKEINTERFACE ii
				&& Objects.equals(InvokeInstructionUtil.getMethodName(ii, cpg), methodName)
				&& instructions[4] instanceof ARETURN) {
			//
			final FailableFunction<Object, Object, Exception> function = a -> a != null
					? FieldUtils.readDeclaredField(a, FieldInstructionUtil.getFieldName(gf, cpg), true)
					: null;
			//
			put(map, getKey(entry), function);
			//
			if (function.apply(getValue(entry)) == null) {
				//
				return false;
				//
			} // if
				//
		} // if
			//
		return true;
		//
	}

	private static boolean executeForEachMethod3c(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final Map<String, FailableFunction<Object, Object, Exception>> map)
			throws Exception {
		//
		if (length(instructions) == 3 && instructions[0] instanceof ALOAD && instructions[1] instanceof GETFIELD gf
				&& instructions[2] instanceof ARETURN) {
			//
			final FailableFunction<Object, Object, Exception> function = a -> a != null
					? FieldUtils.readDeclaredField(a, FieldInstructionUtil.getFieldName(gf, cpg), true)
					: null;
			//
			put(map, getKey(entry), function);
			//
			if (function.apply(getValue(entry)) == null) {
				//
				return false;
				//
			} // if
				//
		} // if
			//
		return true;
		//
	}

	@Nullable
	private static Method getFirstInvokeInterfaceMethod(final Method[] ms, final Instruction[] instructions,
			final ConstantPoolGen cpg) {
		//
		String methodName;
		//
		Method method = null;
		//
		for (int i = 0; i < length(instructions) - 1; i++) {
			//
			if (Boolean.logicalOr(!(ArrayUtils.get(instructions, i) instanceof ALOAD al && al.getIndex() == 0),
					!(ArrayUtils.get(instructions, i + 1) instanceof INVOKEINTERFACE))) {
				//
				continue;
				//
			} // if
				//
			methodName = InvokeInstructionUtil.getMethodName((INVOKEINTERFACE) ArrayUtils.get(instructions, i + 1),
					cpg);
			//
			for (int j = 0; j < length(ms); j++) {
				//
				if (!Objects.equals(FieldOrMethodUtil.getName(ms[j]), methodName)) {
					//
					continue;
					//
				} // if
					//
				if (method == null) {
					//
					method = ms[j];
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return method;
		//
	}

	@Nullable
	private static InputStream getResourceAsStream(@Nullable final Class<?> instance, @Nullable final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

	private static boolean executeForEachMethod(final Object instance, @Nullable final String name,
			final Map<String, FailableFunction<Object, Object, Exception>> map,
			@Nullable final Instruction[] instructions, final ConstantPoolGen cpg) throws Exception {
		//
		if (instructions != null && instructions.length == 5 && instructions[0] instanceof ALOAD
				&& instructions[1] instanceof GETFIELD gf && instructions[2] instanceof ALOAD
				&& instructions[3] instanceof INVOKEINTERFACE && instructions[4] instanceof RETURN) {
			//
			final FailableFunction<Object, Object, Exception> function = a -> FieldUtils.readDeclaredField(a,
					FieldInstructionUtil.getFieldName(gf, cpg), true);
			//
			put(map, name, function);
			//
			if (function.apply(instance) == null) {
				//
				return false;
				//
			} // if
				//
		} // if
			//
		return true;
		//

	}

	@Nullable
	private static String getSuperclassName(@Nullable final JavaClass instance) {
		return instance != null ? instance.getSuperclassName() : null;
	}

	@Nullable
	private static Method getForEachMethod(final JavaClass javaClass) {
		//
		final Method[] ms = JavaClassUtil.getMethods(javaClass);
		//
		final List<Method> list = toList(filter(ms != null ? Arrays.stream(ms) : null,
				m -> m != null && Objects.equals(m.getName(), "forEach") && Arrays.equals(m.getArgumentTypes(),
						new Type[] { ObjectType.getInstance("java.util.function.Consumer") })));
		//
		return IterableUtils.size(list) == 1 ? IterableUtils.get(list, 0) : null;
		//
	}

	@Nullable
	static <K> Set<K> keySet(@Nullable final Map<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

}