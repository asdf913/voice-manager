package com.google.common.collect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Table.Cell;
import com.google.common.reflect.Reflection;

public class CellUtilTest {

	private static class IH implements InvocationHandler {

		private Object rowKey, columnKey, value = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getRowKey")) {
					//
					return rowKey;
					//
				} else if (Objects.equals(methodName, "getColumnKey")) {
					//
					return columnKey;
					//
				} else if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Cell<?, ?, ?> cell = null;

	@BeforeEach
	void beforeEach() {
		//
		cell = Reflection.newProxy(Cell.class, new IH());
		//
	}

	@Test
	void testGetRowKey() {
		//
		Assertions.assertNull(CellUtil.getRowKey(null));
		//
		Assertions.assertNull(CellUtil.getRowKey(cell));
		//
	}

	@Test
	void testGetColumnKey() {
		//
		Assertions.assertNull(CellUtil.getColumnKey(null));
		//
		Assertions.assertNull(CellUtil.getColumnKey(cell));
		//
	}

	@Test
	void testGetValue() {
		//
		Assertions.assertNull(CellUtil.getValue(null));
		//
		Assertions.assertNull(CellUtil.getValue(cell));
		//
	}

}