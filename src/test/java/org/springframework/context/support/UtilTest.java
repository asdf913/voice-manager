package org.springframework.context.support;

import java.awt.Component;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.FieldOrMethod;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.google.common.reflect.Reflection;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.ScanResult;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class UtilTest {

	private static Method METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD, METHOD_TEST, METHOD_IS_STATIC,
			METHOD_GET_FIELD_NMAE_IF_SINGLE_LINE_RETURN_METHOD, METHOD_GET_FIELD_NMAE_FOR_STREAM_OF_AND_ITERATOR,
			METHOD_GET_FIELD_NAME, METHOD_GET_CLASS_NAME1, METHOD_GET_CLASS_NAME2, METHOD_GET_METHOD_NAME,
			METHOD_GET_ARGUMENT_TYPES, METHOD_COLLECT, METHOD_GET_RESOURCE_AS_STREAM, METHOD_PUT_ALL = null;

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
		(METHOD_GET_FIELD_NAME = clz.getDeclaredMethod("getFieldName", FieldInstruction.class, ConstantPoolGen.class))
				.setAccessible(true);
		//
		(METHOD_GET_CLASS_NAME1 = clz.getDeclaredMethod("getClassName", Type.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS_NAME2 = clz.getDeclaredMethod("getClassName", FieldOrMethod.class, ConstantPoolGen.class))
				.setAccessible(true);
		//
		(METHOD_GET_METHOD_NAME = clz.getDeclaredMethod("getMethodName", InvokeInstruction.class,
				ConstantPoolGen.class)).setAccessible(true);
		//
		(METHOD_GET_ARGUMENT_TYPES = clz.getDeclaredMethod("getArgumentTypes", InvokeInstruction.class,
				ConstantPoolGen.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_PUT_ALL = clz.getDeclaredMethod("putAll", Map.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "map")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "filter")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "toList")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "collect")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

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
				if (Objects.equals(methodName, "getMethodName")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getArgumentTypes")) {
					//
					return null;
					//
				} // if
					//
			} else if (self instanceof FieldOrMethod) {
				//
				if (Objects.equals(methodName, "getClassName")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Stream<?> stream = null;

	private InvokeInstruction invokeInstruction = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach()
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		stream = Reflection.newProxy(Stream.class, new IH());
		//
		invokeInstruction = createProxyObject(InvokeInstruction.class, mh = new MH());
		//
	}

	private static <T> T createProxyObject(final Class<T> clz, final MethodHandler mh)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(clz);
		//
		final Class<?> c = proxyFactory.createClass();
		//
		final Constructor<?> constructor = c != null ? c.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return Util.cast(clz, instance);
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
		new FailableStream<>(Util.filter(Arrays.stream(Util.class.getDeclaredMethods()),
				m -> m != null && Objects.equals(Util.getName(m), "getName") && Modifier.isStatic(m.getModifiers())))
				.forEach(m -> Assertions.assertNull(m != null ? m.invoke(null, (Object) null) : null));
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
		new FailableStream<>(Arrays.stream(Util.class.getDeclaredMethods())
				.filter(m -> m != null && Objects.equals(Util.getName(m), "test") && Modifier.isStatic(m.getModifiers())
						&& m.getParameterCount() == 2))
				.forEach(m -> {
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
		new FailableStream<>(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(empty)))).forEach(f -> {
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
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (Util.iterator(classInfos) == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			try {
				//
				if (Util.isAssignableFrom(Stream.class, Class.forName(name = classInfo.getName()))
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
		final List<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (Util.iterator(classInfos) == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			try {
				//
				if (Util.isAssignableFrom(Iterable.class, Class.forName(name = classInfo.getName()))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Iterable<?> it = Util.cast(Iterable.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> Util.iterator(it), name);
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

	private static ClassInfoList getAllClasses(final ScanResult instance) {
		return instance != null ? instance.getAllClasses() : null;
	}

	private static ScanResult scan(final ClassGraph instance) {
		return instance != null ? instance.scan() : null;
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
	void testGetFieldNmae() throws Throwable {
		//
		Assertions.assertNull(getFieldName(null, null));
		//
	}

	private static String getFieldName(final FieldInstruction instance, final ConstantPoolGen cpg) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_NAME.invoke(null, instance, cpg);
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
	void testGetClassName() throws Throwable {
		//
		Assertions.assertNull(getClassName(null));
		//
		Assertions.assertNull(getClassName(null, null));
		//
		Assertions.assertNull(getClassName(createProxyObject(FieldOrMethod.class, mh), null));
		//
		final Class<?> clz = Method.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(new ClassParser(is, null)),
					clz != null ? clz.getDeclaredMethod("toString") : null);
			//
			Assertions
					.assertNull(getClassName(
							Util.map(
									Util.filter(Arrays.stream(InstructionListUtil
											.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(m, null,
													m != null
															? new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m))
															: null)))),
											x -> x instanceof InvokeInstruction),
									x -> Util.cast(InvokeInstruction.class, x)).findFirst().orElse(null),
							null));
			//
		} // try
			//
	}

	private static String getClassName(final Type instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS_NAME1.invoke(null, instance);
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

	private static String getClassName(final FieldOrMethod instance, final ConstantPoolGen cpg) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS_NAME2.invoke(null, instance, cpg);
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
	void testGetMethodName() throws Throwable {
		//
		Assertions.assertNull(getMethodName(null, null));
		//
		Assertions.assertNull(getMethodName(invokeInstruction, null));
		//
		final Class<?> clz = Method.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(new ClassParser(is, null)),
					clz != null ? clz.getDeclaredMethod("toString") : null);
			//
			Assertions
					.assertNull(getMethodName(
							Util.map(
									Util.filter(Arrays.stream(InstructionListUtil
											.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(m, null,
													m != null
															? new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m))
															: null)))),
											x -> x instanceof InvokeInstruction),
									x -> Util.cast(InvokeInstruction.class, x)).findFirst().orElse(null),
							null));
			//
		} // try
			//
	}

	private static String getMethodName(final InvokeInstruction instance, final ConstantPoolGen cpg) throws Throwable {
		try {
			final Object obj = METHOD_GET_METHOD_NAME.invoke(null, instance, cpg);
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
	void testGetArgumentTypes() throws Throwable {
		//
		Assertions.assertNull(getArgumentTypes(null, null));
		//
		Assertions.assertNull(getArgumentTypes(invokeInstruction, null));
		//
		final Class<?> clz = Method.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(new ClassParser(is, null)),
					clz != null ? clz.getDeclaredMethod("toString") : null);
			//
			Assertions
					.assertNull(getArgumentTypes(
							Util.map(
									Util.filter(Arrays.stream(InstructionListUtil
											.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(m, null,
													m != null
															? new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m))
															: null)))),
											x -> x instanceof InvokeInstruction),
									x -> Util.cast(InvokeInstruction.class, x)).findFirst().orElse(null),
							null));
			//
		} // try
			//
	}

	private static Type[] getArgumentTypes(final InvokeInstruction instance, final ConstantPoolGen cpg)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ARGUMENT_TYPES.invoke(null, instance, cpg);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type[]) {
				return (Type[]) obj;
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

}