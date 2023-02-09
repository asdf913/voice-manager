package org.apache.poi.ss.usermodel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class WorkbookUtilTest {

	private static Method METHOD_GET_CLASS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_GET_CLASS = WorkbookUtil.class.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(method != null ? method.getName() : null);
			//
		}

	}

	@Test
	void testCreateCellStyle() {
		//
		Assertions.assertDoesNotThrow(() -> WorkbookUtil.createCellStyle(null));
		//
	}

	@Test
	void testGetSheetAt() {
		//
		Assertions.assertDoesNotThrow(() -> WorkbookUtil.getSheetAt(null, 0));
		//
	}

	@Test
	void testGetCreationHelper() {
		//
		Assertions.assertNull(WorkbookUtil.getCreationHelper(null));
		//
	}

	@Test
	void testWrite() {
		//
		Assertions.assertDoesNotThrow(() -> WorkbookUtil.write(null, null));
		//
		Assertions.assertDoesNotThrow(() -> WorkbookUtil.write(Reflection.newProxy(Workbook.class, new IH()), null));
		//
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

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

}