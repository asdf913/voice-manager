package org.springframework.context.support;

import java.awt.Component;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldOrMethod;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.ReferenceType;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Consumers;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class UtilTest {

	private static Method METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD, METHOD_TEST, METHOD_IS_STATIC,
			METHOD_GET_FIELD_NMAE_IF_SINGLE_LINE_RETURN_METHOD, METHOD_GET_FIELD_NMAE_FOR_STREAM_OF_AND_ITERATOR,
			METHOD_COLLECT, METHOD_GET_RESOURCE_AS_STREAM, METHOD_PUT_ALL, METHOD_GET_REFERENCE_TYPE, METHOD_ITERATOR2,
			METHOD_ITERATOR3, METHOD_HANDLE_ITERATOR_THROWABLE = null;

	private static List<ClassInfo> CLASS_INFOS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Util.class;
		//
		(METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD = clz.getDeclaredMethod("getJavaIoFileSystemField", Object.class))
				.setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", FailablePredicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_GET_FIELD_NMAE_IF_SINGLE_LINE_RETURN_METHOD = clz
				.getDeclaredMethod("getFieldNmaeIfSingleLineReturnMethod", Method.class)).setAccessible(true);
		//
		(METHOD_GET_FIELD_NMAE_FOR_STREAM_OF_AND_ITERATOR = clz.getDeclaredMethod("getFieldNmaeForStreamOfAndIterator",
				Method.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_PUT_ALL = clz.getDeclaredMethod("putAll", Map.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_REFERENCE_TYPE = clz.getDeclaredMethod("getReferenceType", FieldOrMethod.class,
				ConstantPoolGen.class)).setAccessible(true);
		//
		(METHOD_ITERATOR2 = clz.getDeclaredMethod("iterator", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_ITERATOR3 = clz.getDeclaredMethod("iterator", Class.class, Object.class, Map.class))
				.setAccessible(true);
		//
		(METHOD_HANDLE_ITERATOR_THROWABLE = clz.getDeclaredMethod("handleIteratorThrowable", Object.class, Class.class))
				.setAccessible(true);
		//
		CLASS_INFOS = ClassInfoUtil.getClassInfos();
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean isAnnotationPresent, anyMatch = null;

		private Annotation annotation = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Util.contains(Arrays.asList("map", "filter", "toList", "collect"), methodName)) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "anyMatch")) {
					//
					return anyMatch;
					//
				} // if
					//
			} else if (proxy instanceof AnnotatedElement) {
				//
				if (Objects.equals(methodName, "isAnnotationPresent")) {
					//
					return isAnnotationPresent;
					//
				} else if (Objects.equals(methodName, "getAnnotation")) {
					//
					return annotation;
					//
				} // if
					//
			} else if (proxy instanceof ComboBoxModel && Objects.equals(methodName, "getSelectedItem")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		private byte[] digest = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			if (ReflectionUtils.isToStringMethod(thisMethod)) {
				//
				return toString();
				//
			} // if
				//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof InvokeInstruction) {
				//
				if (Util.contains(Arrays.asList("getMethodName"), methodName)) {
					//
					return null;
					//
				} // if
					//
			} else if (self instanceof FieldOrMethod) {
				//
				if (Objects.equals(methodName, "getReferenceType")) {
					//
					return null;
					//
				} // if
					//
			} else if (self instanceof MessageDigest && Objects.equals(methodName, "digest")) {
				//
				return digest;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Stream<?> stream = null;

	private MH mh = null;

	private IH ih = null;

	private Matcher matcher = null;

	private AnnotatedElement annotatedElement = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
		annotatedElement = Reflection.newProxy(AnnotatedElement.class, ih);
		//
		mh = new MH();
		//
		matcher = Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class));
		//
	}

	@Test
	void testMap() {
		//
		Assertions.assertNull(Util.map(stream, null));
		//
		Assertions.assertNull(Util.map(Stream.empty(), null));
		//
	}

	@Test
	void testFilter() {
		//
		Assertions.assertNull(Util.filter(stream, null));
		//
		Assertions.assertNull(Util.filter(Stream.empty(), null));
		//
	}

	@Test
	void testGetText() throws NoSuchFieldException {
		//
		final JTextComponent jtc = Narcissus.getStaticField(Narcissus.findField(Component.class, "LOCK")) != null
				? new JTextField()
				: null;
		//
		Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
		//
		Assertions.assertNull(Util.getText(jtc));
		//
	}

	@Test
	void testSetText() throws NoSuchFieldException {
		//
		final JTextComponent jtc = Narcissus.getStaticField(Narcissus.findField(Component.class, "LOCK")) != null
				? new JTextField()
				: null;
		//
		if (jtc != null) {
			//
			Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> Util.setText(jtc, null));
		//
		final JLabel jl = new JLabel();
		//
		Narcissus.setField(jl, Narcissus.findField(Util.getClass(jtc), "objectLock"), null);
		//
		Assertions.assertDoesNotThrow(() -> Util.setText(jl, null));
		//
	}

	@Test
	void testSetForeground() throws NoSuchFieldException {
		//
		final Component component = Narcissus.getStaticField(Narcissus.findField(Component.class, "LOCK")) != null
				? new JLabel()
				: null;
		//
		Narcissus.setField(component, Narcissus.findField(Util.getClass(component), "objectLock"), null);
		//
		Assertions.assertDoesNotThrow(() -> Util.setForeground(component, null));
		//
	}

	@Test
	void testGetName() {
		//
		FailableStreamUtil.forEach(
				new FailableStream<>(Util.filter(Arrays.stream(Util.class.getDeclaredMethods()),
						m -> m != null && Objects.equals(Util.getName(m), "getName")
								&& Modifier.isStatic(m.getModifiers()))),
				m -> Assertions.assertNull(m != null ? m.invoke(null, (Object) null) : null));
		//
	}

	@Test
	void testGetDeclaringClass() {
		//
		Assertions.assertNull(Util.getDeclaringClass(null));
		//
	}

	@Test
	void testIsAssignableFrom() {
		//
		Assertions.assertFalse(Util.isAssignableFrom(null, null));
		//
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> Util.put(null, null, null));
		//
	}

	@Test
	void testGetAbsolutePath() throws Throwable {
		//
		final File file = Util.cast(File.class, Narcissus.allocateInstance(File.class));
		//
		Assertions.assertNull(Util.getAbsolutePath(file));
		//
		final Field f = getJavaIoFileSystemField(file);
		//
		final Object fs = Narcissus.getStaticField(f);
		//
		try {
			//
			Narcissus.setStaticField(f, null);
			//
			Assertions.assertNull(Util.getAbsolutePath(file));
			//
		} finally {
			//
			Narcissus.setStaticField(f, fs);
			//
		} // try
			//
	}

	@Test
	void testGetJavaIoFileSystemField() throws Throwable {
		//
		Assertions.assertNull(getJavaIoFileSystemField(null));
		//
	}

	private static Field getJavaIoFileSystemField(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		FailableStreamUtil
				.forEach(
						new FailableStream<>(
								Arrays.stream(Util.class.getDeclaredMethods())
										.filter(m -> m != null && Objects.equals(Util.getName(m), "test")
												&& Modifier.isStatic(m.getModifiers()) && m.getParameterCount() == 2)),
						m -> {
							//
							if (m == null) {
								//
								return;
								//
							} // if
								//
							m.setAccessible(true);
							//
							Assertions.assertEquals(Boolean.FALSE, m != null ? m.invoke(null, null, null) : null);
							//
						});
		//
		final FailablePredicate<?, RuntimeException> failablePredicate = x -> false;
		//
		Assertions.assertEquals(Boolean.FALSE, test(failablePredicate, null));
		//
	}

	private static <T, E extends Throwable> boolean test(final FailablePredicate<T, E> instance, final T value)
			throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(Util.contains(null, null));
		//
		Assertions.assertFalse(Util.contains(Collections.emptyList(), null));
		//
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(Util.toList(stream));
		//
		final Stream<?> empty = Stream.empty();
		//
		FailableStreamUtil.forEach(new FailableStream<>(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(empty)))),
				f -> {
					//
					if (f == null || Util.contains(Arrays.asList(Integer.TYPE, Boolean.TYPE), f.getType())
							|| !Util.contains(Arrays.asList("sourceStage"), Util.getName(f))) {
						//
						return;
						//
					} // if
						//
					if (Modifier.isStatic(f.getModifiers())) {
						//
						Narcissus.setStaticField(f, null);
						//
					} else {
						//
						Narcissus.setField(empty, f, null);
						//
					} // if
						//
				});
		//
		Assertions.assertNull(Util.toList(empty));
		//
		if (Util.iterator(CLASS_INFOS) == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : CLASS_INFOS) {
			//
			try {
				//
				if (Util.isAssignableFrom(Stream.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Stream<?> stream = Util.cast(Stream.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> Util.toList(stream), name);
					//
				} // if
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertTrue(isStatic(Boolean.class.getDeclaredField("TRUE")));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIterator() throws Throwable {
		//
		Assertions.assertNull(Util.iterator(null));
		//
		if (Util.iterator(CLASS_INFOS) == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : CLASS_INFOS) {
			//
			try {
				//
				if ((name = HasNameUtil.getName(classInfo)) == null || (clz = Util.forName(name)) == null
						|| Util.contains(Arrays.asList("org.eclipse.jetty.http.MultiPartByteRanges$Parts",
								"org.eclipse.jetty.http.MultiPartFormData$Parts"), Util.getName(clz))) {
					//
					continue;
					//
				} // if
					//
				if (Util.isAssignableFrom(Iterable.class, clz) && !clz.isInterface()
						&& !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Iterable<?> it = Util.cast(Iterable.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					if (Util.contains(Arrays.asList(
							"net.bytebuddy.description.type.TypeDescription$Generic$Visitor$AnnotationStripper$NonAnnotatedTypeVariable",
							"net.bytebuddy.description.type.TypeDescription$Generic$Visitor$Substitutor$ForTypeVariableBinding$RetainedMethodTypeVariable",
							"net.bytebuddy.description.type.TypeList$Generic$ForDetachedTypes$OfTypeVariables$AttachedTypeVariable",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForLowerBoundWildcard$LazyLowerBoundWildcard",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$AnnotatedTypeVariable",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForUpperBoundWildcard$LazyUpperBoundWildcard",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$LazyMethodDescription$LazyParameterizedReceiverType$TypeArgumentList$AnnotatedTypeVariable",
							"org.d2ab.collection.BiMappedCollection", "org.d2ab.collection.BiMappedList$SequentialList",
							"org.d2ab.collection.ChainedCollection", "org.d2ab.collection.ChainedList",
							"org.d2ab.collection.ChainingIterable", "org.d2ab.collection.FilteredCollection",
							"org.d2ab.collection.FilteredList", "org.d2ab.collection.MappedCollection",
							"org.d2ab.collection.MappedList$SequentialList", "org.d2ab.collection.ReverseList",
							"org.d2ab.collection.chars.BitCharSet", "org.d2ab.collection.chars.ChainingCharIterable",
							"org.d2ab.collection.chars.CharList$SubList",
							"org.d2ab.collection.doubles.ChainingDoubleIterable",
							"org.d2ab.collection.doubles.DoubleList$SubList",
							"org.d2ab.collection.doubles.RawDoubleSet", "org.d2ab.collection.ints.BitIntSet",
							"org.d2ab.collection.ints.ChainingIntIterable", "org.d2ab.collection.ints.IntList$SubList",
							"org.d2ab.collection.longs.ChainingLongIterable",
							"org.d2ab.collection.longs.LongList$SubList", "org.d2ab.sequence.EquivalentSizeSequence"),
							name)) {
						//
						Assertions.assertThrows(NullPointerException.class, () -> Util.iterator(it), name);
						//
					} else if (Objects.equals(
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$TokenizedGenericType$Malformed",
							name)) {
						//
						Assertions.assertThrows(GenericSignatureFormatError.class, () -> Util.iterator(it), name);
						//
					} else if (Util.contains(Arrays.asList(
							"net.bytebuddy.description.type.TypeDescription$Generic$OfTypeVariable$Symbolic",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$Formal$LazyTypeVariable",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForTypeVariable$UnresolvedTypeVariable",
							"net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$GenericTypeToken$ForUnboundWildcard$LazyUnboundWildcard"),
							name)) {
						//
						Assertions.assertThrows(IllegalStateException.class, () -> Util.iterator(it), name);
						//
					} else if (Util.contains(Arrays.asList("org.htmlunit.cyberneko.util.SimpleArrayList"), name)) {
						//
						Assertions.assertThrows(UnsupportedOperationException.class, () -> Util.iterator(it), name);
						//
					} else {
						//
						Assertions.assertDoesNotThrow(() -> Util.iterator(it), name);
						//
					} // if
						//
				} // if
					//
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
		Assertions.assertNull(iterator(null, null));
		//
		Assertions.assertNull(iterator(null, null, null));
		//
		Assertions.assertEquals(Unit.with(null), iterator(null, null, Collections.singletonMap(null, null)));
		//
	}

	private static <T> IValue0<Iterator<T>> iterator(final Class<?> clz, final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_ITERATOR2.invoke(null, clz, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> IValue0<Iterator<T>> iterator(final Class<?> clz, final Object instance,
			final Map<String, String> map) throws Throwable {
		try {
			final Object obj = METHOD_ITERATOR3.invoke(null, clz, instance, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldNmaeIfSingleLineReturnMethod() throws Throwable {
		//
		Assertions.assertNull(getFieldNmaeIfSingleLineReturnMethod(null));
		//
	}

	private static String getFieldNmaeIfSingleLineReturnMethod(final Method method) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_NMAE_IF_SINGLE_LINE_RETURN_METHOD.invoke(null, method);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldNmaeForStreamOfAndIterator() throws Throwable {
		//
		Assertions.assertNull(getFieldNmaeForStreamOfAndIterator(null));
		//
	}

	private static String getFieldNmaeForStreamOfAndIterator(final Method method) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_NMAE_FOR_STREAM_OF_AND_ITERATOR.invoke(null, method);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCollect() throws Throwable {
		//
		Assertions.assertNull(collect(null, null));
		//
		Assertions.assertNull(collect(stream, null));
		//
		Assertions.assertNull(collect(Stream.empty(), null));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector)
			throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, collector);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetResourceAsStream() throws Throwable {
		//
		Assertions.assertNull(getResourceAsStream(Class.class, null));
		//
	}

	private static InputStream getResourceAsStream(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_RESOURCE_AS_STREAM.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutAll() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> putAll(null, null, null, (Object[]) null));
		//
	}

	private static <K, V> void putAll(final Map<K, V> instance, final V v, final K k, final K... ks) throws Throwable {
		try {
			METHOD_PUT_ALL.invoke(null, instance, v, k, ks);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredSize() throws NoSuchFieldException {
		//
		final Component component = new JLabel();
		//
		final Field field = Narcissus.findField(Component.class, "LOCK");
		//
		final Object lock = Narcissus.getStaticField(field);
		//
		try {
			//
			Narcissus.setStaticField(field, null);
			//
			Assertions.assertNull(Util.getPreferredSize(component));
			//
		} finally {
			//
			Narcissus.setStaticField(field, lock);
			//
		} // try
			//
	}

	@Test
	void testGetReferenceType() throws Throwable {
		//
		Assertions.assertNull(getReferenceType(null, null));
		//
		Assertions.assertNull(getReferenceType(ProxyUtil.createProxy(FieldOrMethod.class, mh), null));
		//
		final Class<?> clz = Method.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(new ClassParser(is, null)),
					clz != null ? clz.getDeclaredMethod("toString") : null);
			//
			final FieldOrMethod fom = Util.map(Util.filter(
					Arrays.stream(InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(m,
							null, m != null ? new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m)) : null)))),
					x -> x instanceof InvokeInstruction), x -> Util.cast(InvokeInstruction.class, x)).findFirst()
					.orElse(null);
			//
			Assertions.assertNull(getReferenceType(fom, null));
			//
			Assertions.assertNull(getReferenceType(fom,
					Util.cast(ConstantPoolGen.class, Narcissus.allocateInstance(ConstantPoolGen.class))));
			//
		} // try
			//
	}

	private static ReferenceType getReferenceType(final FieldOrMethod instance, final ConstantPoolGen cpg)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_REFERENCE_TYPE.invoke(null, instance, cpg);
			if (obj == null) {
				return null;
			} else if (obj instanceof ReferenceType) {
				return (ReferenceType) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testHandleIteratorThrowable() throws Throwable {
		//
		Assertions.assertNull(handleIteratorThrowable(null, null));
		//
	}

	private static <T> IValue0<Iterator<T>> handleIteratorThrowable(final Object instance, final Class<?> clz)
			throws Throwable {
		try {
			final Object obj = METHOD_HANDLE_ITERATOR_THROWABLE.invoke(null, instance, clz);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDigest() throws Throwable {
		//
		Assertions.assertNull(Util.digest(null, null));
		//
		final MessageDigest messageDigest = ProxyUtil.createProxy(MessageDigest.class, mh, clz -> {
			//
			final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor(String.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		});
		//
		Assertions.assertNull(Util.digest(messageDigest, null));
		//
		Assertions.assertNull(Util.digest(messageDigest, new byte[] {}));
		//
	}

	@Test
	void testMatches() {
		//
		Assertions.assertFalse(Util.matches(null));
		//
		Assertions.assertFalse(Util.matches(matcher));
		//
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertTrue(Util.matches(pattern != null ? pattern.matcher("1") : null));
		//
	}

	@Test
	void testMatcher() {
		//
		Assertions.assertNull(Util.matcher(null, null));
		//
		Assertions.assertNull(Util.matcher(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), null));
		//
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertNull(Util.matcher(pattern, null));
		//
		Assertions.assertNotNull(Util.matcher(pattern, "1"));
		//
	}

	@Test
	void testGroupCount() throws Throwable {
		//
		Assertions.assertEquals(0, Util.groupCount(matcher));
		//
		final String string = "1";
		//
		final Matcher m = Util.matcher(Pattern.compile("\\d+"), string);
		//
		Assertions.assertTrue(Util.matches(m));
		//
		Assertions.assertEquals(0, Util.groupCount(m));
		//
	}

	@Test
	void testGroup() throws Throwable {
		//
		Assertions.assertNull(Util.group(matcher, 0));
		//
		final String string = "1";
		//
		final Matcher m = Util.matcher(Pattern.compile("\\d+"), string);
		//
		Assertions.assertTrue(Util.matches(m));
		//
		Assertions.assertSame(string, Util.group(m, 0));
		//
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach((Map) null, null));
		//
		final Map<?, ?> emptyMap = Collections.emptyMap();
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach(emptyMap, null));
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach(emptyMap, Consumers.biNop()));
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach(Reflection.newProxy(Map.class, ih), null));
		//
	}

	@Test
	void testIsAnnotationPresent() {
		//
		Assertions.assertFalse(Util.isAnnotationPresent(null, null));
		//
		Assertions.assertFalse(Util.isAnnotationPresent(METHOD_COLLECT, null));
		//
		Assertions.assertFalse(Util.isAnnotationPresent(METHOD_COLLECT, Target.class));
		//
		if (ih != null) {
			//
			ih.isAnnotationPresent = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(Util.isAnnotationPresent(annotatedElement, null));
		//
		if (ih != null) {
			//
			ih.isAnnotationPresent = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(Util.isAnnotationPresent(annotatedElement, null));
		//
	}

	@Test
	void testGetAnnotation() {
		//
		Assertions.assertNull(Util.getAnnotation(null, null));
		//
		Assertions.assertNull(Util.getAnnotation(METHOD_COLLECT, null));
		//
		Assertions.assertNull(Util.getAnnotation(METHOD_COLLECT, Target.class));
		//
		Assertions.assertNull(Util.getAnnotation(annotatedElement, null));
		//
	}

	@Test
	void testGetRowCount() throws Throwable {
		//
		Assertions.assertEquals(0, Util.getRowCount(new DefaultTableModel()));
		//
		FailableStreamUtil.forEach(new FailableStream<>(Stream.of(DefaultTableModel.class,
				Util.forName("sun.tools.jconsole.inspector.XMBeanInfo$ReadOnlyDefaultTableModel"),
				Util.forName("sun.tools.jconsole.inspector.TableSorter"))), x -> {
					//
					Assertions.assertEquals(0,
							Util.getRowCount(Util.cast(DefaultTableModel.class, Narcissus.allocateInstance(x))),
							Util.getName(x));
					//
				});
		//
	}

	@Test
	void testRemoveRow() {
		//
		final DefaultTableModel defaultTableModel = new DefaultTableModel();
		//
		Assertions.assertDoesNotThrow(() -> Util.removeRow(defaultTableModel, 0));
		//
		defaultTableModel.addRow(new Object[] {});
		//
		Assertions.assertDoesNotThrow(() -> Util.removeRow(defaultTableModel, 0));
		//
		FailableStreamUtil.forEach(new FailableStream<>(Stream.of(DefaultTableModel.class,
				Util.forName("sun.tools.jconsole.inspector.XMBeanInfo$ReadOnlyDefaultTableModel"),
				Util.forName("sun.tools.jconsole.inspector.TableSorter"))), x -> {
					//
					Assertions.assertDoesNotThrow(
							() -> Util.removeRow(Util.cast(DefaultTableModel.class, Narcissus.allocateInstance(x)), -1),
							Util.getName(x));
					//
				});
		//
	}

	@Test
	void testAddRow() {
		//
		Assertions.assertDoesNotThrow(() -> Util.addRow(new DefaultTableModel(), null));
		//
		FailableStreamUtil.forEach(new FailableStream<>(Stream.of(DefaultTableModel.class,
				Util.forName("sun.tools.jconsole.inspector.XMBeanInfo$ReadOnlyDefaultTableModel"),
				Util.forName("sun.tools.jconsole.inspector.TableSorter"))), x -> {
					//
					Assertions.assertDoesNotThrow(
							() -> Util.addRow(Util.cast(DefaultTableModel.class, Narcissus.allocateInstance(x)), null),
							Util.getName(x));
					//
				});
		//
	}

	@Test
	void testAddElement() {
		//
		final Class<?>[] classes = new Class<?>[] { DefaultComboBoxModel.class,
				Util.forName("javax.swing.text.html.OptionComboBoxModel") };
		//
		for (int i = 0; classes != null && i < classes.length; i++) {
			//
			final Class<?> clz = ArrayUtils.get(classes, i);
			//
			Assertions.assertDoesNotThrow(
					() -> Util.addElement(Util.cast(MutableComboBoxModel.class, Narcissus.allocateInstance(clz)), null),
					Util.getName(clz));
			//
		} // for
			//
	}

	@Test
	void testGetSelectedItem() {
		//
		Assertions.assertNull(Util.getSelectedItem((JComboBox<?>) null));
		//
		final JComboBox<?> jcb = new JComboBox<>();
		//
		Assertions.assertNull(Util.getSelectedItem(jcb));
		//
		final List<Field> fs = Util
				.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(Util.getClass(JComboBox.class))),
						f -> Objects.equals(Util.getName(f), "dataModel")));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = size == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null) {
			//
			Narcissus.setField(jcb, f, Reflection.newProxy(ComboBoxModel.class, ih));
			//
		} // if
			//
		Assertions.assertNull(Util.getSelectedItem(jcb));
		//
	}

	@Test
	void testSetSelectedIndex() {
		//
		Assertions.assertDoesNotThrow(() -> Util.setSelectedIndex(null, 0));
		//
		Assertions.assertDoesNotThrow(() -> Util
				.setSelectedIndex(Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class)), 0));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> Util.setSelectedIndex(new JComboBox<>(), 0));
		//
		Assertions.assertDoesNotThrow(
				() -> Util.setSelectedIndex(new JComboBox<>(new DefaultComboBoxModel<>(new Object[] { null })), 0));
		//
	}

	@Test
	void testAnyMatch() throws NoSuchMethodException {
		//
		Assertions.assertFalse(Util.anyMatch(null, null));
		//
		Assertions.assertFalse(Util.anyMatch(Stream.empty(), null));
		//
		Assertions.assertTrue(Util.anyMatch(Stream.of(""), Predicates.alwaysTrue()));
		//
		Assertions.assertEquals(ih != null ? ih.anyMatch = Boolean.FALSE : null,
				Narcissus.invokeStaticMethod(
						Narcissus.findMethod(Util.class, "anyMatch", Stream.class, Predicate.class),
						Reflection.newProxy(Stream.class, ih), null));
		//
	}

	@Test
	void testGetDeclaredConstructor() throws NoSuchMethodException {
		//
		Assertions.assertNull(Util.getDeclaredConstructor(null));
		//
		Assertions.assertNotNull(Util.getDeclaredConstructor(String.class));
		//
	}

}