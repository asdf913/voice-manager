package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.google.common.reflect.Reflection;

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean implements FactoryBean<Object> {

	private static final Logger LOG = LoggerFactory.getLogger(OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.class);

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("title")
	private String title = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	static interface Link {

		String getCategory();

		String getUrl();

		String getText();

		String getDescription();

		Integer getNumber();

	}

	private static class IH implements InvocationHandler {

		@Note("description")
		private String description = null;

		@Note("url")
		private String url = null;

		private String text, category = null;

		private Integer number = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return text;
					//
				} else if (Objects.equals(methodName, "getDescription")) {
					//
					return description;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
					//
					return url;
					//
				} else if (Objects.equals(methodName, "getCategory")) {
					//
					return category;
					//
				} else if (Objects.equals(methodName, "getNumber")) {
					//
					return number;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Override
	public List<Link> getObject() throws Exception {
		//
		return getLinks(Util.toList(Util.filter(Util.stream(ElementUtil.select(
				getElement(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), title), "tr")),
				x -> x != null && x.childrenSize() >= 3)));
		//
	}

	@Nullable
	private static List<Link> getLinks(final List<Element> es) {
		//
		Element e = null;
		//
		String category = null;
		//
		int childrenSize = 0;
		//
		Element child = null;
		//
		List<Link> links = null;
		//
		Integer number = null;
		//
		int offset, index = 0;
		//
		boolean hasAttrRowSpan = false;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((childrenSize = childrenSize(e)) > 0 && (child = ElementUtil.child(e, 0)) != null
					&& (hasAttrRowSpan = child.hasAttr("rowspan"))) {
				//
				category = ElementUtil.text(child);
				//
			} // if
				//
				// number
				//
			if (childrenSize > (index = (0 + (offset = hasAttrRowSpan ? 1 : 0)))
					&& (child = ElementUtil.child(e, index)) != null) {
				//
				number = valueOf(ElementUtil.text(child));
				//
			} // if
				//
			addAll(links = ObjectUtils.getIfNull(links, ArrayList::new),
					createLinks(childrenSize, offset, e,
							childrenSize > (index = 2 + offset) && (child = ElementUtil.child(e, index)) != null
									? ElementUtil.select(child, "a")
									: null,
							category, number));
			//
		} // for
			//
		return links;
		//
	}

	private static <E> void addAll(@Nullable final Collection<E> instance, @Nullable final Collection<? extends E> c) {
		if (instance != null && c != null) {
			instance.addAll(c);
		}
	}

	@Nullable
	private static Collection<Link> createLinks(final int childrenSize, final int offset, final Element e,
			final Collection<Element> as2, final String category, @Nullable final Integer number) {
		//
		Collection<Element> as1 = null;
		//
		int index, size = 0;
		//
		Element child, a1, a2 = null;
		//
		IH ih = null;
		//
		Collection<Link> links = null;
		//
		for (int j = 0; j < IterableUtils
				.size(as1 = childrenSize > (index = 1 + offset) && (child = ElementUtil.child(e, index)) != null
						? ElementUtil.select(child, "a")
						: null); j++) {
			//
			if ((a1 = IterableUtils.get(as1, j)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((size = IterableUtils.size(as2)) > 0) {
				//
				for (int k = 0; k < size; k++) {
					//
					if ((a2 = IterableUtils.get(as2, k)) == null) {
						//
						continue;
						//
					} // if
						//
					(ih = new IH()).category = category;
					//
					ih.number = number;
					//
					if (isAbsolute(apply(URI::new, a1.attr("href"), null))) {
						//
						ih.description = ElementUtil.text(a1);
						//
						ih.url = a1.absUrl("href");
						//
						ih.text = ElementUtil.text(a2);
						//
					} else {
						//
						ih.description = StringUtils.joinWith(" ", ElementUtil.text(a1), ElementUtil.text(a2));
						//
						ih.url = a2.absUrl("href");
						//
						ih.text = StringUtils.joinWith(" ", ElementUtil.text(a1), ElementUtil.text(a2));
						//
					} // if
						//
					Util.add(links = ObjectUtils.getIfNull(links, ArrayList::new), Reflection.newProxy(Link.class, ih));
					//
				} // for
					//
			} else {
				//
				(ih = new IH()).category = category;
				//
				ih.number = number;
				//
				if (childrenSize > (index = 2 + offset) && (child = ElementUtil.child(e, index)) != null) {
					//
					ih.description = ElementUtil.text(child);
					//
				} // if
					//
				ih.url = a1.absUrl("href");
				//
				ih.text = ElementUtil.text(a1);
				//
				Util.add(links = ObjectUtils.getIfNull(links, ArrayList::new), Reflection.newProxy(Link.class, ih));
				//
			} // for
				//
		} // for
			//
		return links;
		//
	}

	private static boolean isAbsolute(final URI instance) {
		return instance != null && instance.isAbsolute();
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> function, final T value,
			final R defaultValue) {
		try {
			return function != null ? function.apply(value) : defaultValue;
		} catch (final Throwable e) {
			return defaultValue;
		}
	}

	private static int childrenSize(@Nullable final Element instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance);
		//
		try {
			//
			final Field field = clz != null ? clz.getDeclaredField("childNodes") : null;
			//
			if (field != null) {
				//
				field.setAccessible(true);
				//
				if (field.get(instance) == null) {
					//
					return 0;
					//
				} // if
					//
			} // if
				//
		} catch (final IllegalAccessException | NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.childrenSize();
		//
	}

	@Nullable
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static Element getParentByNodeName(final Element element, final String nodeName) {
		//
		return orElse(findFirst(
				Util.filter(Util.stream(parents(element)), x -> Objects.equals(nodeName, NodeUtil.nodeName(x)))), null);
		//
	}

	private static Element getElement(final URL url, final String title) throws IOException {
		//
		final List<Element> bs = ElementUtil.select(testAndApply(Objects::nonNull, url, x -> Jsoup.parse(x, 0), null),
				"b");
		//
		final List<Element> es = Util.collect(Util.filter(Util.stream(bs),
				x -> StringUtils.equals(StringUtils.defaultIfBlank(title, "音訳の部屋読み方辞典"), trim(ElementUtil.text(x)))),
				Collectors.toList());
		//
		final int size = IterableUtils.size(es);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getParentByNodeName(size == 1 ? IterableUtils.get(es, 0) : null, "table");
		//
	}

	private static <T> T orElse(@Nullable final Optional<T> instance, final T value) {
		return instance != null ? instance.orElse(value) : value;
	}

	@Nullable
	private static <T> Optional<T> findFirst(@Nullable final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	@Nullable
	private static List<Element> parents(@Nullable final Element instance) {
		return instance != null ? instance.parents() : null;
	}

	private static String trim(final String string) {
		//
		if (StringUtils.isEmpty(string)) {
			//
			return string;
			//
		} // if
			//
		final char[] cs = Util.toCharArray(string);
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if (Character.isWhitespace(c = cs[i])) {
				//
				continue;
				//
			} // if
				//
			append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
			//
		} // for
			//
		return StringUtils.defaultString(Util.toString(sb));
		//
	}

	private static void append(@Nullable final StringBuilder instance, final char c) {
		if (instance != null) {
			instance.append(c);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}