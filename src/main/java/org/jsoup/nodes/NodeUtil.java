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

import javax.annotation.Nullable;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.NodeVisitor;

public class NodeUtil {

	private NodeUtil() {
	}

	@Nullable
	public static String absUrl(@Nullable final Node instance, final String attributeKey) {
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

	@Nullable
	private static Field[] getDeclaredFields(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final Function<T, R> functionTrue, @Nullable final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static <T, R> R apply(@Nullable final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	public static boolean hasAttr(@Nullable final Node instance, final String attributeKey) {
		return instance != null && instance.hasAttr(attributeKey);
	}

	@Nullable
	public static String attr(@Nullable final Node instance, @Nullable final String attributeKey) {
		return instance != null && attributeKey != null ? instance.attr(attributeKey) : null;
	}

	@Nullable
	public static String nodeName(@Nullable final Node instance) {
		return instance != null ? instance.nodeName() : null;
	}

	@Nullable
	public static Node traverse(@Nullable final Node instance, @Nullable final NodeVisitor nodeVisitor) {
		return instance != null && nodeVisitor != null ? instance.traverse(nodeVisitor) : instance;
	}

	public static int childNodeSize(@Nullable final Node instance) {
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

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Nullable
	public static Stream<Node> nodeStream(@Nullable final Node instance) {
		return instance != null ? instance.nodeStream() : null;
	}

	@Nullable
	public static Node nextSibling(@Nullable final Node instance) {
		return instance != null ? instance.nextSibling() : null;
	}

	@Nullable
	public static Node parentNode(@Nullable final Node instance) {
		return instance != null ? instance.parentNode() : null;
	}

}