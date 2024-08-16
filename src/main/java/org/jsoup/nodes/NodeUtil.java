package org.jsoup.nodes;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.NodeVisitor;

public class NodeUtil {

	private NodeUtil() {
	}

	public static String absUrl(final Node instance, final String attributeKey) {
		return instance != null ? instance.absUrl(attributeKey) : null;
	}

	public static List<Node> childNodes(final Node instance) {
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(getClass(instance)), Arrays::stream, null),
						f -> Objects.equals(getName(f), "childNodes")));
		//
		final Field f = fs != null && fs.size() == 1 ? fs.get(0) : null;
		//
		try {
			//
			if (f != null && f.get(instance) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance != null ? instance.childNodes() : null;
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	public static boolean hasAttr(final Node instance, final String attributeKey) {
		return instance != null && instance.hasAttr(attributeKey);
	}

	public static String attr(final Node instance, final String attributeKey) {
		return instance != null && attributeKey != null ? instance.attr(attributeKey) : null;
	}

	public static String nodeName(final Node instance) {
		return instance != null ? instance.nodeName() : null;
	}

	public static Node traverse(final Node instance, final NodeVisitor nodeVisitor) {
		return instance != null && nodeVisitor != null ? instance.traverse(nodeVisitor) : instance;
	}

	public static int childNodeSize(final Node instance) {
		//
		final Element element = cast(Element.class, instance);
		//
		if (element != null) {
			//
			try {
				//
				if (FieldUtils.readField(element, "childNodes", true) == null) {
					//
					return 0;
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // if
			//
		return instance != null ? instance.childNodeSize() : 0;
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	public static Stream<Node> nodeStream(final Node instance) {
		return instance != null ? instance.nodeStream() : null;
	}

	public static Node nextSibling(final Node instance) {
		return instance != null ? instance.nextSibling() : null;
	}

}