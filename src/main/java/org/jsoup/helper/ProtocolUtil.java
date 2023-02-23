package org.jsoup.helper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public interface ProtocolUtil {

	/**
	 * @throws IOException
	 * @see <a href=
	 *      "https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/helper/HttpConnection.java#L840">org.jsoup.helper.HttpConnection$Response.execute(org.jsoup.helper.HttpConnection$Request,org.jsoup.helper.HttpConnection$Response)&nbsp;Line&nbsp;840&nbsp;jsoup/HttpConnection.java&nbsp;at&nbsp;master&nbsp;·&nbsp;jhy/jsoup</a>
	 */
	static String[] getAllowProtocols() {
		//
		try {
			//
			return toArray(getAllowProtocols(HttpConnection.Response.class), new String[] {});
			//
		} catch (final NoSuchMethodException | IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static List<String> getAllowProtocols(final Class<?> clz) throws IOException, NoSuchMethodException {
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			// org.jsoup.helper.HttpConnection$Response.execute(org.jsoup.helper.HttpConnection$Request,org.jsoup.helper.HttpConnection$Response)
			//
			final java.lang.reflect.Method method = clz != null
					? clz.getDeclaredMethod("execute", HttpConnection.Request.class, HttpConnection.Response.class)
					: null;
			//
			final JavaClass javaClass = ClassParserUtil.parse(is != null ? new ClassParser(is, null) : null);
			//
			final org.apache.bcel.classfile.Method m = javaClass != null ? javaClass.getMethod(method) : null;
			//
			return getAllowProtocols(
					InstructionListUtil.getInstructions(
							MethodGenUtil.getInstructionList(m != null ? new MethodGen(m, null, null) : null)),
					m != null ? m.getConstantPool() : null);
			//
		} // try
			//
	}

	private static List<String> getAllowProtocols(final Instruction[] is, final ConstantPool cp) {
		//
		List<Object> list = null;
		//
		ConstantPoolGen cpg = null;
		//
		final int length = is != null ? is.length : 0;
		//
		Object value = null;
		//
		for (int i = 0; i < length; i++) {
			//
			if (is[i] instanceof LDC ldc) {
				//
				if (cpg == null) {
					//
					cpg = ObjectUtils.getIfNull(cpg, () -> cp != null ? new ConstantPoolGen(cp) : null);
					//
				} // if
					//
				if (i < length - 2 && is[i + 2] instanceof IFNE && (
				//
				(i < length - 6 && is[i + 6] instanceof IFNE)
						//
						|| (i < length - 7 && is[i + 7] instanceof ATHROW)
				//
				) && !contains(list = ObjectUtils.getIfNull(list, ArrayList::new), value = ldc.getValue(cpg))) {
					//
					add(list, value);
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return list != null ? list.stream().map(ProtocolUtil::toString).toList() : null;
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <E> void add(final Collection<E> instance, final E item) {
		if (instance != null) {
			instance.add(item);
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

}