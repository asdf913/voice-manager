package org.springframework.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.nutch.util.DeflateUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class XlsUtilTest {

	private static Method METHOD_HAS_ENTRY_CASE_INSENSITIVE, METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_ADD,
			METHOD_GET_CONSTANT_POOL, METHOD_GET_DECLARED_METHOD, METHOD_GET_NAME, METHOD_TEST,
			METHOD_GET_RESOURCE_AS_STREAM = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = XlsUtil.class;
		//
		(METHOD_HAS_ENTRY_CASE_INSENSITIVE = clz.getDeclaredMethod("hasEntryCaseInsensitive", DirectoryEntry.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_CONSTANT_POOL = clz.getDeclaredMethod("getConstantPool", JavaClass.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHOD = clz.getDeclaredMethod("getDeclaredMethod", Class.class, String.class,
				Class[].class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
	}

	@Test
	void testIsXls() throws IOException {
		//
		Assertions.assertFalse(XlsUtil.isXls(null));
		//
		final Decoder decoder = Base64.getDecoder();
		//
		Assertions.assertFalse(XlsUtil.isXls(new ByteArrayResource(DeflateUtils.inflate(decoder != null
				? decoder.decode(
						"eJztmVtMXEUYx785e3bZxRaWBWmlKFu6AlLu2BZslWsBty5QQGmt1iy3sgq7yCXSxAejMfHBJhgfTBoT0wTjg6ZBffPF+iBvXmLSh/pUn9TEh2p84aGs/2/OHFiW21lsSqvnT37MObNn5vtm5pvZ2Tk//pB188rneb9Qko6Tg5bjHnIl5AmQad54iTSVtxyPx83suK37SrdVymOoY/ycgMc8DbiBB6SDB8AesBdkqDH3qtTW/aseiuFvmvx0kqJIJ+li8lKwpXIRMYn1WSmzbPE5q7Lt79y+uX6nOv/5e4DnfxbwgWyQAx4kjgmifWA/eAjkgQMgHzys7BUg9avrQqSHQAA8CopAMSgBj4FScBiUgXJQASpBFagGNaAWPA6OgKPgGKgD9eAJ4u8zohPgSfAUaACNoAk0gxbQCk6CNtAOOsDTIAhOgWdACHSCLtANToMe0Av6wLPgOdAPzoCzqo3n6N5cKwW8cqQbMeTyaDImrhmh0cbjF4oMTsamYiPT/v7Y5FB5a+yVmfHh6LSMiVAv57XGBmUk8HUFbuTnFXX0d/0Xr24agisSxjZix/Ih6tJRy7nVnQmdR2j+Gdc4RSx3Yn2bpHEK0xgZcVwdEKUB0RTgqGkupa6gg06DluB+Gu/w6FPgVIdG3UGdoh1Z+jQ4H3RSGJ+/1FGvb+lPGy0XCNIEx1AaImgYdocogrX1AvHsyaTs+VuUMz9LroDADOkKumDcBeP5MJhGhqF8xHKzl+vhmPYgQmOo5SJmTB9qnMU6zbPPS76hHCFQI+be/LvEM6rSK4RP+HGtI14jNCWf1TBPddTtRswXot5C0Sj9a4F3E3gigvqj0j8fapuV/u2Bfz7ZKUfdgoty+190iyLZwhrBs9SJ+RGFZ+wT32XLUoYlNbq4kwuGjDBeUjS3scqY1+YOU6hU8yZkdBurkFyG2nPnzGe2VB+aMw6XptAdnUhfQ8pfcxwC3MgjqMdKzPWiw8dpACU5cGqLrFlvQrBFVLBF2OOs7cvwQA3A00mUMwfDD/vDsq4RC2067rLm3VmaQcmQDMhBGkUN6A+LZUPS1gXVtl7uD++2hTZtG9c1RWv7K7VWtMuNyyhKD3Ir9DlhpfDaVjQjPIX7Ela6bxESo7KC78u++cR8em2Nxp1Ob82tzzVV7FAXW68TtraRr+rO17l+1lsapJQ9ecP6o8txDqD1jnFQ3Xz7o7+Wuka9n77npsPFX95gP14nY8Xkz3l/wwtjIxl7NN6f8Pcb7zt4rzZExn5tgoym/36b6BF1XaXKmUq+9v92TLvW8LNIzN9If3wsRMjvQT/e6vnayDK6S63uGdQSjo2Fo3UbdGKWnkuVGWvbvJXi8Uz5zSHUtSvhOlnvyP+XVZWXLawM/EzO9o+t6KowevkrlZr6Ffdn/tWextCipaVwvRowFgMJ9n8Sxq7c1n9Ti0nxd7ek0Y1dsGrLlq17R3t32wFbuyu1z7P1v5Qwx39ptz2xdZeEX12OAIUDxAeoopxOVFFjN1Fzt0YFC29W+BcWmw4uRPVCcGguqgdALT4vqaGMDX4Dbvb7l3O0699d/7DigPf9D/D7t2zpKp/PO5PyXiDjPYNQeGn1t+5m+bZWdSff/3E/J79D2KiMPCHbZ1wLapHHmBPURQP0csr++zCqbJEPcuQJs0WZlgTsjtFwynZNpcM623WkYJ/9NU8aqqmPwmj5Tn3IUPZNWSnDvl5Rx+5O6qUZ9D8fJPPY83n+yMobk9U3A5upBPbNd4ZW7fMZ1Gfqul/aGqJWpIPSk2EZh1aVt4P2HwTmuZNzneXU+qNuB/afB9ZbuL1StZ/4/vcfnO3KVg==")
				: null))));
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.write(WorkbookFactory.create(false), baos);
			//
			Assertions.assertTrue(XlsUtil.isXls(new ByteArrayResource(baos.toByteArray())));
			//
		} // try
			//
	}

	@Test
	void testHasEntryCaseInsensitive() throws Throwable {
		//
		Assertions.assertFalse(hasEntryCaseInsensitive(null, null));
		//
		Assertions.assertFalse(hasEntryCaseInsensitive(
				cast(DirectoryEntry.class,
						Narcissus.allocateInstance(Class.forName("org.apache.poi.poifs.filesystem.DirectoryNode"))),
				""));
		//
		Assertions
				.assertFalse(
						hasEntryCaseInsensitive(
								cast(DirectoryEntry.class,
										Narcissus.allocateInstance(Class
												.forName("org.apache.poi.poifs.filesystem.FilteringDirectoryNode"))),
								""));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static boolean hasEntryCaseInsensitive(final DirectoryEntry instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_HAS_ENTRY_CASE_INSENSITIVE.invoke(null, instance, name);
			if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethod() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethod(null, null));
		//
	}

	private static Method getDeclaredMethod(final Class<?> clz, final String name, final Class<?>... parameterTypes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHOD.invoke(null, clz, name, parameterTypes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method) {
				return (Method) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() {
		//
		new FailableStream<>(Arrays.stream(XlsUtil.class.getDeclaredMethods()))
				.filter(m -> m != null && Objects.equals(getName(m), "getName") && Modifier.isStatic(m.getModifiers())
						&& m.getParameterCount() == 1)
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
					Assertions.assertNull(m.invoke(null, (Object) null));
					//
				});
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetConstantPool() throws Throwable {
		//
		Assertions.assertNull(getConstantPool(null));
		//
	}

	private static ConstantPool getConstantPool(final JavaClass instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CONSTANT_POOL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ConstantPool) {
				return (ConstantPool) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetResourceAsStream() throws Throwable {
		//
		Assertions.assertNull(getResourceAsStream(null, null));
		//
		Assertions.assertNull(getResourceAsStream(Class.class, null));
		//
	}

	private static InputStream getResourceAsStream(final Class<?> clz, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_RESOURCE_AS_STREAM.invoke(null, clz, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}