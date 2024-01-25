package org.springframework.context.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class UtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
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
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Stream<?> stream = null;

	@BeforeEach
	public void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, new IH());
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
		final JTextComponent jtc = new JTextField();
		//
		Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
		//
		Assertions.assertNull(Util.getText(jtc));
		//
	}

	@Test
	void testSetText() throws NoSuchFieldException {
		//
		final JTextComponent jtc = new JTextField();
		//
		Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
		//
		Assertions.assertDoesNotThrow(() -> Util.setText(jtc, null));
		//
	}

}