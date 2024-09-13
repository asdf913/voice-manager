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
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
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
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.javatuples.valueintf.IValue1;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.j256.simplemagic.ContentInfo;

import io.github.toolfactory.narcissus.Narcissus;

abstract class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	private static final String VALUE = "value";

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

	static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
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
	private static Field getDeclaredField(@Nullable final Class<?> instance, @Nullable final String name)
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
	static <X> X getValue1(@Nullable final IValue1<X> instance) {
		return instance != null ? instance.getValue1() : null;
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
		try (final InputStream is = clz != null
				? clz.getResourceAsStream("/" + StringUtils.replace(name, ".", "/") + ".class")
				: null) {
			//
			if (function != null && function.apply(instance) == null) {
				//
				return;
				//
			} // if
				//
			final JavaClass javaClass = ClassParserUtil.parse(is != null ? new ClassParser(is, null) : null);
			//
			Method method = getForEachMethod(javaClass);
			//
			ConstantPoolGen cpg = null;
			//
			Instruction[] instructions = null;
			//
			if (method != null) {
				//
				if ((instructions = InstructionListUtil.getInstructions(
						new MethodGen(method, null, cpg = new ConstantPoolGen(method.getConstantPool()))
								.getInstructionList())) != null
						&& instructions.length == 5 && instructions[0] instanceof ALOAD
						&& instructions[1] instanceof GETFIELD gf && instructions[2] instanceof ALOAD
						&& instructions[3] instanceof INVOKEINTERFACE && instructions[4] instanceof RETURN) {
					//
					final String fieldName = gf.getFieldName(cpg);
					//
					put(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP,
							LinkedHashMap::new), name,
							function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
					//
					if (function.apply(instance) == null) {
						//
						return;
						//
					} // if
						//
				} // if
					//
			} else if (Objects.equals(getSuperclassName(javaClass), "java.lang.Object")) {
				//
				final JavaClass[] interfaces = javaClass.getInterfaces();
				//
				if (interfaces != null) {
					//
					String methodName = null;
					//
					final Method[] ms = JavaClassUtil.getMethods(javaClass);
					//
					Method m = null;
					//
					for (final JavaClass interfaceJc : interfaces) {
						//
						if ((method = getForEachMethod(interfaceJc)) == null) {
							//
							continue;
							//
						} // if
							//
						instructions = InstructionListUtil.getInstructions(
								new MethodGen(method, null, cpg = new ConstantPoolGen(method.getConstantPool()))
										.getInstructionList());
						//
						for (int i = 0; i < length(instructions) - 1; i++) {
							//
							if (instructions[i] instanceof ALOAD al && al.getIndex() == 0
									&& instructions[i + 1] instanceof INVOKEINTERFACE ii) {
								//
								methodName = ii.getMethodName(cpg);
								//
								for (int j = 0; j < length(ms); j++) {
									//
									if (!Objects.equals(FieldOrMethodUtil.getName(ms[j]), methodName)) {
										//
										continue;
										//
									} // if
										//
									if (m == null) {
										//
										m = ms[j];
										//
									} else {
										//
										throw new IllegalStateException();
										//
									} // if
										//
								} // for
									//
							} // if
								//
						} // for
							//
						if (m != null && (instructions = InstructionListUtil
								.getInstructions(new MethodGen(m, null, cpg = new ConstantPoolGen(m.getConstantPool()))
										.getInstructionList())) != null) {
							//
							if (length(instructions) == 4 && instructions[0] instanceof ALOAD
									&& instructions[1] instanceof GETFIELD gf
									&& instructions[2] instanceof INVOKEINTERFACE ii
									&& Objects.equals(ii.getMethodName(cpg), methodName)
									&& instructions[3] instanceof ARETURN) {
								//
								final String fieldName = gf.getFieldName(cpg);
								//
								put(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
										.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new), name,
										function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
								//
								if (function.apply(instance) == null) {
									//
									return;
									//
								} // if
									//
							} else if (length(instructions) == 5 && instructions[0] instanceof ALOAD
									&& instructions[1] instanceof GETFIELD gf
									&& instructions[2] instanceof INVOKEINTERFACE
									&& instructions[3] instanceof INVOKEINTERFACE ii
									&& Objects.equals(ii.getMethodName(cpg), methodName)
									&& instructions[4] instanceof ARETURN) {
								//
								final String fieldName = gf.getFieldName(cpg);
								//
								put(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
										.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new), name,
										function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
								//
								if (function.apply(instance) == null) {
									//
									return;
									//
								} // if
									//
							} else if (length(instructions) == 3 && instructions[0] instanceof ALOAD
									&& instructions[1] instanceof GETFIELD gf && instructions[2] instanceof ARETURN) {
								//
								final String fieldName = gf.getFieldName(cpg);
								//
								put(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
										.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new), name,
										function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
								//
								if (function.apply(instance) == null) {
									//
									return;
									//
								} // if
									//
							} else if (length(instructions) == 6 && instructions[0] instanceof ALOAD
									&& instructions[1] instanceof GETFIELD gf && instructions[2] instanceof CHECKCAST
									&& instructions[3] instanceof ALOAD && instructions[4] instanceof INVOKEVIRTUAL
									&& instructions[5] instanceof ARETURN) {
								//
								final String fieldName = gf.getFieldName(cpg);
								//
								put(STRING_FAILABLE_BI_FUNCTION_MAP = ObjectUtils
										.getIfNull(STRING_FAILABLE_BI_FUNCTION_MAP, LinkedHashMap::new), name,
										function = a -> FieldUtils.readDeclaredField(a, fieldName, true));
								//
								if (function.apply(instance) == null) {
									//
									return;
									//
								} // if
									//
							} // if
								//
						} // if
							//
					} // for
						//
				} // if
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
			if (Objects.equals(name, "com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap")
					&& FieldUtils.readDeclaredField(instance, "_hashArea", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.fasterxml.jackson.databind.node.ArrayNode",
					"com.fasterxml.jackson.databind.node.ObjectNode",
					"org.apache.poi.poifs.property.DirectoryProperty"), name)
					&& FieldUtils.readDeclaredField(instance, "_children", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.fasterxml.jackson.databind.util.ArrayIterator")
					&& FieldUtils.readDeclaredField(instance, "_a", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.github.andrewoma.dexx.collection.ArrayList")
					&& FieldUtils.readDeclaredField(instance, "elements", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.DerivedKeyHashMap",
					"com.github.andrewoma.dexx.collection.HashMap", "com.github.andrewoma.dexx.collection.HashSet"),
					name) && FieldUtils.readDeclaredField(instance, "compactHashMap", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.github.andrewoma.dexx.collection.TreeMap")
					&& FieldUtils.readDeclaredField(instance, "tree", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.github.andrewoma.dexx.collection.TreeSet")
					&& FieldUtils.readDeclaredField(instance, "redBlackTree", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.github.andrewoma.dexx.collection.Vector")
					&& FieldUtils.readDeclaredField(instance, "pointer", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.internal.adapter.ListAdapater",
					"org.apache.logging.log4j.spi.MutableThreadContextStack",
					"org.d2ab.collection.BiMappedList$RandomAccessList",
					"org.d2ab.collection.BiMappedList$SequentialList", "org.d2ab.collection.FilteredList",
					"org.d2ab.collection.FilteredList", "org.d2ab.collection.MappedList$RandomAccessList",
					"org.d2ab.collection.MappedList$SequentialList", "org.d2ab.collection.chars.CharList$SubList",
					"org.d2ab.collection.doubles.DoubleList$SubList", "org.d2ab.collection.ints.IntList$SubList",
					"org.d2ab.collection.longs.LongList$SubList"), name)
					&& FieldUtils.readDeclaredField(instance, "list", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.github.andrewoma.dexx.collection.internal.adapter.SetAdapater",
					"com.github.andrewoma.dexx.collection.internal.adapter.SortedSetAdapter",
					"org.springframework.cglib.beans.FixedKeySet"), name)
					&& FieldUtils.readDeclaredField(instance, "set", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.github.andrewoma.dexx.collection.internal.base.MappedIterable")
					&& FieldUtils.readDeclaredField(instance, "from", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.google.common.collect.ConcurrentHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.ConcurrentHashMultiset"), name)
					&& FieldUtils.readDeclaredField(instance, "countMap", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.google.common.collect.EnumMultiset")
					&& FieldUtils.readDeclaredField(instance, "enumConstants", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.google.common.collect.EvictingQueue")
					&& MethodUtils.invokeMethod(instance, true, "delegate") == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.google.common.collect.ForwardingMap$StandardKeySet",
					"com.google.common.collect.ForwardingMap$StandardValues",
					"com.google.common.collect.ForwardingNavigableMap$StandardNavigableKeySet",
					"com.google.common.collect.ForwardingSortedMap$StandardKeySet",
					"org.apache.commons.collections.bag.HashBag", "org.apache.commons.collections.bag.TreeBag",
					"org.apache.commons.collections4.bag.HashBag", "org.apache.commons.collections4.bag.TreeBag",
					"org.apache.commons.collections4.multiset.HashMultiSet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardKeySet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingMap$StandardValues",
					"org.apache.jena.ext.com.google.common.collect.ForwardingNavigableMap$StandardNavigableKeySet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMap$StandardKeySet"), name)
					&& FieldUtils.readField(instance, "map", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.helger.commons.collection.impl.CommonsHashSet",
					"com.helger.commons.collection.impl.CommonsLinkedHashSet"), name)
					&& Narcissus.getField(instance, Narcissus.findField(clz, "map")) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList(
					"org.apache.jena.ext.com.google.common.collect.ForwardingSortedMultiset$StandardElementSet"), name)
					&& FieldUtils.readField(instance, "multiset", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("com.google.common.collect.ForwardingMultiset$StandardElementSet",
							"com.google.common.collect.ForwardingSortedMultiset$StandardElementSet",
							"org.apache.jena.ext.com.google.common.collect.ForwardingMultiset$StandardElementSet"),
					name)) {
				//
				if (MethodUtils.invokeMethod(instance, true, "multiset") == null) {
					//
					return;
					//
				} // if
					//
			} else if (contains(Arrays.asList("com.google.common.collect.ForwardingNavigableSet$StandardDescendingSet",
					"org.apache.jena.ext.com.google.common.collect.ForwardingNavigableSet$StandardDescendingSet"), name)
					&& FieldUtils.readField(instance, "forward", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.google.common.collect.HashMultiset",
					"com.google.common.collect.LinkedHashMultiset",
					"org.apache.jena.ext.com.google.common.collect.HashMultiset",
					"org.apache.jena.ext.com.google.common.collect.LinkedHashMultiset"), name)
					&& FieldUtils.readField(instance, "backingMap", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.google.common.collect.TreeMultiset",
					"org.apache.jena.ext.com.google.common.collect.TreeMultiset"), name)
					&& FieldUtils.readDeclaredField(instance, "rootReference", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.google.common.reflect.TypeToken$TypeSet",
					"org.apache.jena.ext.com.google.common.reflect.TypeToken$TypeSet"), name)
					&& FieldUtils.readDeclaredField(instance, "types", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.DatabaseImpl")
					&& FieldUtils.readDeclaredField(instance, "_tableFinder", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.IndexCursorImpl")
					&& FieldUtils.readDeclaredField(instance, "_entryCursor", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.PropertyMapImpl")
					&& FieldUtils.readDeclaredField(instance, "_props", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.healthmarketscience.jackcess.impl.TableDefinitionImpl"), name)
					&& Narcissus.invokeMethod(instance,
							Narcissus.findMethod(clz, "createRowState", new Class<?>[] {})) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.TableImpl")
					&& FieldUtils.readDeclaredField(instance, "_columns", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.TableScanCursor")
					&& FieldUtils.readDeclaredField(instance, "_ownedPagesCursor", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.healthmarketscience.jackcess.impl.complex.MultiValueColumnPropertyMap")
					&& FieldUtils.readDeclaredField(instance, "_primary", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.callback.CallbackList")
					&& FieldUtils.readDeclaredField(instance, "m_aRWLock", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.impl.CommonsCopyOnWriteArrayList")
					&& Narcissus.getField(instance, Narcissus.findField(clz, "array")) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.impl.CommonsCopyOnWriteArraySet")
					&& Narcissus.getField(instance, Narcissus.findField(clz, "al")) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.impl.CommonsTreeSet")
					&& Narcissus.getField(instance, Narcissus.findField(clz, "m")) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.iterate.ArrayIterator")
					&& FieldUtils.readDeclaredField(instance, "m_aArray", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.iterate.IterableIterator")
					&& FieldUtils.readDeclaredField(instance, "m_aIter", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.iterate.MapperIterator")
					&& FieldUtils.readDeclaredField(instance, "m_aBaseIter", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.collection.map.LRUSet")
					&& FieldUtils.readDeclaredField(instance, "m_aMap", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.http.HttpHeaderMap")
					&& FieldUtils.readDeclaredField(instance, "m_aHeaders", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.io.file.FileSystemIterator")
					&& FieldUtils.readField(instance, "m_aIter", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.io.file.FileSystemRecursiveIterator")
					&& FieldUtils.readDeclaredField(instance, "m_aFilesLeft", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.log.InMemoryLogger")
					&& FieldUtils.readDeclaredField(instance, "m_aMessages", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.helger.commons.math.CombinationGenerator")
					&& FieldUtils.readDeclaredField(instance, "m_aCombinationsLeft", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("com.opencsv.CSVReader", "com.opencsv.CSVReaderHeaderAware"), name)
					&& FieldUtils.readField(instance, "peekedLines", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.opencsv.bean.CsvToBean")
					&& FieldUtils.readDeclaredField(instance, "mappingStrategy", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.opencsv.bean.FieldMapByPosition")) {
				//
				if (FieldUtils.readField(instance, "complexMapList", true) == null) {
					//
					return;
					//
				} // if
					//
			} else if (Objects.equals(name, "com.opencsv.bean.PositionToBeanField")
					&& FieldUtils.readDeclaredField(instance, "ranges", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "com.sun.jna.platform.win32.Advapi32Util$EventLogIterator")
					&& FieldUtils.readDeclaredField(instance, "_buffer", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "freemarker.core._SortedArraySet")
					&& FieldUtils.readDeclaredField(instance, "array", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "freemarker.core._UnmodifiableCompositeSet")
					&& FieldUtils.readDeclaredField(instance, "set1", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.BootstrapMethods")
					&& FieldUtils.readDeclaredField(instance, "bootstrapMethods", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.ConstantPool")
					&& FieldUtils.readDeclaredField(instance, "constantPool", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.InnerClasses")
					&& FieldUtils.readDeclaredField(instance, "innerClasses", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.LineNumberTable")
					&& FieldUtils.readDeclaredField(instance, "lineNumberTable", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.LocalVariableTable")
					&& FieldUtils.readDeclaredField(instance, "localVariableTable", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.LocalVariableTypeTable")
					&& FieldUtils.readDeclaredField(instance, "localVariableTypeTable", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.bcel.classfile.MethodParameters")
					&& FieldUtils.readDeclaredField(instance, "parameters", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.RuntimeInvisibleAnnotations",
					"org.apache.bcel.classfile.RuntimeVisibleAnnotations"), name)
					&& FieldUtils.readField(instance, "annotationTable", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.bcel.classfile.RuntimeInvisibleParameterAnnotations",
					"org.apache.bcel.classfile.RuntimeVisibleParameterAnnotations"), name)
					&& FieldUtils.readField(instance, "parameterAnnotationTable", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.collections.CursorableLinkedList")
					&& FieldUtils.readDeclaredField(instance, "_head", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("org.apache.commons.collections.HashBag", "org.apache.commons.collections.TreeBag"),
					name) && FieldUtils.readField(instance, "_map", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.commons.collections.bag.PredicatedBag",
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
					"org.apache.commons.collections.list.FixedSizeList",
					"org.apache.commons.collections.list.GrowthList", "org.apache.commons.collections.list.LazyList",
					"org.apache.commons.collections.list.PredicatedList",
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
					"org.apache.commons.collections4.list.FixedSizeList",
					"org.apache.commons.collections4.list.GrowthList", "org.apache.commons.collections4.list.LazyList",
					"org.apache.commons.collections4.list.PredicatedList",
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
					"org.apache.commons.collections4.set.UnmodifiableSortedSet",
					"org.d2ab.collection.BiMappedCollection"), name)
					&& FieldUtils.readField(instance, "collection", true) == null) {
				//
				return;
				//
			} else if (Util
					.contains(Arrays.asList("org.apache.commons.collections.collection.CompositeCollection",
							"org.apache.commons.collections4.collection.CompositeCollection",
							"org.apache.commons.collections4.set.CompositeSet"), name)
					&& FieldUtils.readDeclaredField(instance, "all", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.commons.collections.collection.SynchronizedCollection",
					"org.apache.commons.collections4.collection.SynchronizedCollection",
					"org.d2ab.collection.CollectionList", "org.d2ab.collection.FilteredCollection",
					"org.d2ab.collection.MappedCollection", "org.d2ab.collection.chars.CollectionCharList",
					"org.d2ab.collection.doubles.CollectionDoubleList", "org.d2ab.collection.ints.CollectionIntList",
					"org.d2ab.collection.longs.CollectionLongList", "org.d2ab.sequence.CollectionSequence"), name)
					&& FieldUtils.readDeclaredField(instance, "collection", true) == null) {
				//
				return;
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
					"org.apache.commons.collections4.multiset.AbstractMultiSet$UniqueSet"), name)
					&& FieldUtils.readDeclaredField(instance, "parent", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.collections.set.CompositeSet")
					&& FieldUtils.readField(instance, "all", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.commons.collections.set.ListOrderedSet",
					"org.apache.commons.collections4.set.ListOrderedSet"), name)
					&& FieldUtils.readDeclaredField(instance, "setOrder", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.commons.collections.set.MapBackedSet",
					"org.apache.commons.collections4.set.MapBackedSet"), name)
					&& FieldUtils.readDeclaredField(instance, "map", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.collections4.iterators.IteratorIterable")
					&& FieldUtils.readDeclaredField(instance, "typeSafeIterator", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("org.apache.commons.csv.CSVRecord", "org.d2ab.collection.chars.BitCharSet",
							"org.d2ab.collection.doubles.RawDoubleSet",
							"org.d2ab.collection.doubles.SortedListDoubleSet", "org.d2ab.collection.ints.BitIntSet"),
					name) && FieldUtils.readDeclaredField(instance, "values", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.io.IOExceptionList")
					&& FieldUtils.readDeclaredField(instance, "causeList", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.math3.genetics.ElitisticListPopulation")
					&& FieldUtils.readField(instance, "chromosomes", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet",
					"org.apache.commons.math3.geometry.spherical.oned.ArcsSet"), name)
					&& FieldUtils.readField(instance, "tree", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.math3.ml.neuralnet.Network")
					&& FieldUtils.readDeclaredField(instance, "neuronMap", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D")
					&& FieldUtils.readDeclaredField(instance, "network", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.math3.util.IntegerSequence$Range")
					&& Objects.equals(FieldUtils.readDeclaredField(instance, "step", true), Integer.valueOf(0))) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.commons.math3.util.MultidimensionalCounter")
					&& Objects.equals(FieldUtils.readDeclaredField(instance, "last", true),
							FieldUtils.readDeclaredField(instance, "dimension", true))) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.ibatis.cursor.defaults.DefaultCursor")
					&& FieldUtils.readDeclaredField(instance, "cursorIterator", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.jena.atlas.lib.Map2")
					&& FieldUtils.readDeclaredField(instance, "map2", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.jena.atlas.lib.tuple.TupleN")
					&& FieldUtils.readDeclaredField(instance, "tuple", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.jena.ext.com.google.common.collect.EnumMultiset")
					&& FieldUtils.readDeclaredField(instance, "enumConstants", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.jena.ext.com.google.common.collect.EvictingQueue")
					&& FieldUtils.readDeclaredField(instance, "delegate", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.jena.ext.xerces.impl.dv.util.ByteListImpl",
					"org.apache.xerces.impl.dv.util.ByteListImpl"), name)
					&& FieldUtils.readDeclaredField(instance, "data", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.logging.log4j.message.StructuredDataCollectionMessage")
					&& FieldUtils.readDeclaredField(instance, "structuredDataMessageList", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.ddf.EscherArrayProperty")
					&& Objects.equals(FieldUtils.readDeclaredField(instance, "emptyComplexPart", true), Boolean.FALSE)
					&& FieldUtils.readField(instance, "complexData", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.ddf.EscherContainerRecord")
					&& FieldUtils.readDeclaredField(instance, "_childRecords", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.hssf.record.aggregates.ValueRecordsAggregate")
					&& FieldUtils.readDeclaredField(instance, "records", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFPatriarch",
					"org.apache.poi.xslf.usermodel.XSLFGroupShape"), name)
					&& FieldUtils.readDeclaredField(instance, "_shapes", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.hssf.usermodel.HSSFRow")
					&& FieldUtils.readDeclaredField(instance, "cells", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.hssf.usermodel.HSSFShapeGroup")
					&& FieldUtils.readDeclaredField(instance, "shapes", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.hssf.usermodel.HSSFSheet",
					"org.apache.poi.xslf.usermodel.XSLFTable", "org.apache.poi.xssf.usermodel.XSSFSheet"), name)
					&& FieldUtils.readDeclaredField(instance, "_rows", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.hssf.usermodel.HSSFWorkbook")
					&& FieldUtils.readDeclaredField(instance, "_sheets", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.openxml4j.opc.PackageRelationshipCollection")
					&& FieldUtils.readDeclaredField(instance, "relationshipsByID", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.poifs.crypt.temp.SXSSFWorkbookWithCustomZipEntrySource",
					"org.apache.poi.xssf.streaming.DeferredSXSSFWorkbook"), name)
					&& FieldUtils.readField(instance, "_wb", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.poifs.filesystem.DirectoryNode")
					&& FieldUtils.readDeclaredField(instance, "_entries", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.poifs.filesystem.FilteringDirectoryNode")
					&& FieldUtils.readDeclaredField(instance, "directory", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.poifs.filesystem.POIFSDocument")
					&& FieldUtils.readDeclaredField(instance, "_property", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.poifs.filesystem.POIFSStream")
					&& FieldUtils.readDeclaredField(instance, "blockStore", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.poifs.property.RootProperty")
					&& FieldUtils.readField(instance, "_children", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.ss.util.SSCellRange")
					&& FieldUtils.readDeclaredField(instance, "_flattenedArray", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.xddf.usermodel.text.XDDFTextParagraph",
					"org.apache.poi.xslf.usermodel.XSLFTextParagraph"), name)
					&& FieldUtils.readDeclaredField(instance, "_runs", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.xslf.usermodel.XSLFAutoShape",
					"org.apache.poi.xslf.usermodel.XSLFFreeformShape", "org.apache.poi.xslf.usermodel.XSLFTableCell",
					"org.apache.poi.xslf.usermodel.XSLFTextBox", "org.apache.poi.xssf.usermodel.XSSFObjectData",
					"org.apache.poi.xssf.usermodel.XSSFTextBox"), name)
					&& FieldUtils.readField(instance, "_paragraphs", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xslf.usermodel.XSLFDiagram$XSLFDiagramGroupShape")
					&& FieldUtils.readField(instance, "_shapes", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xslf.usermodel.XSLFNotes")
					&& FieldUtils.readDeclaredField(instance, "_notes", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("org.apache.poi.xslf.usermodel.XSLFNotesMaster",
							"org.apache.poi.xslf.usermodel.XSLFSlide", "org.apache.poi.xslf.usermodel.XSLFSlideMaster"),
					name)) {
				//
				if (FieldUtils.readDeclaredField(instance, "_slide", true) == null) {
					//
					return;
					//
				} // if
					//
			} else if (Objects.equals(name, "org.apache.poi.xslf.usermodel.XSLFSlideLayout")
					&& FieldUtils.readDeclaredField(instance, "_layout", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.streaming.SXSSFRow")
					&& FieldUtils.readDeclaredField(instance, "_cells", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xslf.usermodel.XSLFTableStyles")
					&& FieldUtils.readDeclaredField(instance, "_styles", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.xssf.streaming.DeferredSXSSFSheet",
					"org.apache.poi.xssf.streaming.SXSSFSheet", "org.apache.poi.xssf.usermodel.XSSFChartSheet",
					"org.apache.poi.xssf.usermodel.XSSFDialogsheet"), name)
					&& FieldUtils.readField(instance, "_rows", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.streaming.SXSSFDrawing")
					&& FieldUtils.readDeclaredField(instance, "_drawing", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.streaming.SXSSFWorkbook")
					&& FieldUtils.readDeclaredField(instance, "_wb", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.xssf.usermodel.XSSFDrawing",
					"org.apache.poi.xssf.usermodel.XSSFShapeGroup"), name)
					&& FieldUtils.readField(instance, "drawing", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.usermodel.XSSFRow")
					&& FieldUtils.readField(instance, "_cells", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.usermodel.XSSFSimpleShape")
					&& FieldUtils.readDeclaredField(instance, "_paragraphs", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.poi.xssf.usermodel.XSSFWorkbook")
					&& FieldUtils.readDeclaredField(instance, "sheets", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.poi.xwpf.usermodel.XWPFEndnote",
					"org.apache.poi.xwpf.usermodel.XWPFFootnote"), name)
					&& FieldUtils.readField(instance, "paragraphs", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.apache.xmlbeans.XmlSimpleList")
					&& FieldUtils.readDeclaredField(instance, "underlying", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.apache.xmlbeans.impl.values.JavaListObject",
					"org.apache.xmlbeans.impl.values.JavaListXmlObject"), name)
					&& FieldUtils.readDeclaredField(instance, "sizer", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.d2ab.collection.ChainedCollection")
					&& FieldUtils.readDeclaredField(instance, "collections", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.d2ab.collection.ChainedList")
					&& FieldUtils.readDeclaredField(instance, "lists", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.d2ab.collection.ChainingIterable",
					"org.d2ab.collection.chars.ChainingCharIterable",
					"org.d2ab.collection.doubles.ChainingDoubleIterable",
					"org.d2ab.collection.ints.ChainingIntIterable", "org.d2ab.collection.longs.ChainingLongIterable"),
					name) && FieldUtils.readDeclaredField(instance, "iterables", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("org.d2ab.collection.ReverseList", "org.d2ab.sequence.EquivalentSizeSequence"), name)
					&& FieldUtils.readDeclaredField(instance, "original", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.d2ab.collection.longs.BitLongSet")
					&& FieldUtils.readDeclaredField(instance, "negatives", true) == null) {
				//
				return;
				//
			} else if (contains(
					Arrays.asList("org.eclipse.jetty.http.MetaData$ConnectRequest",
							"org.eclipse.jetty.http.MetaData$Request", "org.eclipse.jetty.http.MetaData$Response"),
					name) && FieldUtils.readField(instance, "_httpFields", true) == null) {
				//
				return;
				//
			} else if (contains(Arrays.asList("org.javatuples.Decade", "org.javatuples.Ennead",
					"org.javatuples.KeyValue", "org.javatuples.LabelValue", "org.javatuples.Octet",
					"org.javatuples.Pair", "org.javatuples.Quartet", "org.javatuples.Quintet", "org.javatuples.Septet",
					"org.javatuples.Sextet", "org.javatuples.Triplet", "org.javatuples.Triplet", "org.javatuples.Unit"),
					name) && FieldUtils.readField(instance, "valueList", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.openjdk.nashorn.internal.runtime.JSONListAdapter")
					&& FieldUtils.readField(instance, "obj", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.openjdk.nashorn.internal.runtime.ListAdapter")
					&& FieldUtils.readDeclaredField(instance, "obj", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.openjdk.nashorn.internal.runtime.PropertyMap")
					&& FieldUtils.readDeclaredField(instance, "properties", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.openjdk.nashorn.internal.runtime.SharedPropertyMap")
					&& FieldUtils.readField(instance, "properties", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.oxbow.swingbits.action.ActionGroup")
					&& FieldUtils.readDeclaredField(instance, "actions", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.springframework.beans.MutablePropertyValues")
					&& FieldUtils.readDeclaredField(instance, "propertyValueList", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.springframework.beans.factory.support.ManagedSet")
					&& Narcissus.getObjectField(instance, Narcissus.findField(clz, "map")) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.springframework.core.env.MutablePropertySources")
					&& FieldUtils.readDeclaredField(instance, "propertySourceList", true) == null) {
				//
				return;
				//
			} else if (Objects.equals(name, "org.springframework.util.AutoPopulatingList")
					&& FieldUtils.readDeclaredField(instance, "backingList", true) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final IllegalAccessException | NoSuchMethodException | InvocationTargetException |

				NoSuchFieldException e) {
			//
			throw new RuntimeException(e);
			//
		} catch (final NullPointerException e) {
			//
			try {
				//
				if (Objects.equals("com.healthmarketscience.jackcess.impl.TableDefinitionImpl", getName(clz))
						&& Narcissus.getField(instance, Narcissus.findField(clz, "_database")) == null) {
					//
					return;
					//
				} // if
					//
			} catch (final NoSuchFieldException nsfe) {
				//
				throw new RuntimeException(nsfe);
				//
			} // try
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

	private static String getSuperclassName(final JavaClass instance) {
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

}