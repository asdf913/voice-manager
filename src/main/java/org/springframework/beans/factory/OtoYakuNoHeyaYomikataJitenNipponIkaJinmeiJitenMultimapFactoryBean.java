package org.springframework.beans.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/mw3.html
 */
public class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private static final Logger LOG = LoggerFactory
			.getLogger(OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.class);

	private String url = null;

	private Iterable<Link> links = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private IValue0<String> text = null;

	private IValue0<String> description = null;

	private Multimap<String, String> toBeRemoved = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	public void setDescription(final String description) {
		this.description = Unit.with(description);
	}

	public void setToBeRemoved(final String string) {
		//
		Object object = null;
		//
		try {
			//
			object = testAndApply(StringUtils::isNotBlank, string, x -> new ObjectMapper().readValue(x, Object.class),
					null);
			//
		} catch (final JsonProcessingException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		if (object instanceof Map m) {
			//
			final Iterable<Entry<?, ?>> entries = Util.entrySet(m);
			//
			if (Util.iterator(entries) != null) {
				//
				Object obj = null;
				//
				for (final Entry<?, ?> entry : entries) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					if ((obj = Util.getValue(entry)) instanceof Iterable iterable) {
						//
						for (final Object o : iterable) {
							//
							if (o instanceof Iterable || o instanceof Map) {
								//
								throw new IllegalStateException(Util.toString(Util.getClass(o)));
								//
							} // if
								//
							MultimapUtil.put(
									toBeRemoved = ObjectUtils.getIfNull(toBeRemoved, LinkedHashMultimap::create),
									Util.toString(Util.getKey(entry)), Util.toString(o));
							//
						} // if
							//
					} else if (obj instanceof Map) {
						//
						throw new IllegalStateException(Util.toString(Util.getClass(obj)));
						//
					} else {
						//
						MultimapUtil.put(toBeRemoved = ObjectUtils.getIfNull(toBeRemoved, LinkedHashMultimap::create),
								Util.toString(Util.getKey(entry)), Util.toString(Util.getValue(entry)));
						//
					} // if
						//
				} // for
					//
			} // if
				//
		} else if (object instanceof Iterable iterable && Util.iterator(iterable) != null) {
			//
			for (final Object obj : iterable) {
				//
				if (obj instanceof Map m) {
					//
					final Iterable<Entry<?, ?>> entries = Util.entrySet(m);
					//
					if (Util.iterator(entries) != null) {
						//
						for (final Entry<?, ?> entry : entries) {
							//
							if (entry == null) {
								//
								continue;
								//
							} // if
								//
							if (Util.getValue(entry) instanceof Iterable it) {
								//
								for (final Object o : it) {
									//
									if (o instanceof Iterable || o instanceof Map) {
										//
										throw new IllegalStateException(Util.toString(Util.getClass(o)));
										//
									} // if
										//
									MultimapUtil.put(
											toBeRemoved = ObjectUtils.getIfNull(toBeRemoved,
													LinkedHashMultimap::create),
											Util.toString(Util.getKey(entry)), Util.toString(o));
									//
								} // if
									//
							} else if (Util.getValue(entry) instanceof Map) {
								//
								throw new IllegalStateException(Util.toString(Util.getClass(obj)));
								//
							} else {
								//
								MultimapUtil.put(
										toBeRemoved = ObjectUtils.getIfNull(toBeRemoved, LinkedHashMultimap::create),
										Util.toString(Util.getKey(entry)), Util.toString(Util.getValue(entry)));
								//
							} // if
								//
						} // for
							//
					} // if
						//
				} else if (obj != null) {
					//
					throw new IllegalStateException(Util.toString(Util.getClass(obj)));
					//
				} // if
					//
			} // for
				//
		} else if (object != null) {
			//
			throw new IllegalStateException(Util.toString(Util.getClass(object)));
			//
		} // if
			//
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)), toBeRemoved);
			//
		} // if
			//
		if ((size = IterableUtils.size(ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, links != null ? links.spliterator() : null,
						x -> StreamSupport.stream(x, false), null),
				x -> description != null && x != null
						&& Objects.equals(x.getDescription(), IValue0Util.getValue0(description)))))) > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)), toBeRemoved);
			//
		} // if
			//
		return createMultimap(url, toBeRemoved);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(@Nullable final String url,
			final Multimap<String, String> toBeRemoved) throws Exception {
		//
		return createMultimap(ElementUtil.select(testAndApply(
				Objects::nonNull, testAndApply(Util::isAbsolute,
						testAndApply(StringUtils::isNotBlank, url, URI::new, null), x -> toURL(x), null),
				x -> Jsoup.parse(x, 0), null), "tbody"), toBeRemoved);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Iterable<Element> es,
			final Multimap<String, String> toBeRemoved) {
		//
		if (Util.iterator(es) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		List<Element> children = null;
		//
		Element element = null;
		//
		for (final Element e : es) {
			//
			if (ElementUtil.childrenSize(e) < 1 || !Util.matches(Util.matcher(
					pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}{1,2}行")),
					ElementUtil.text(IterableUtils.get(children = ElementUtil.children(e), 0))))) {
				//
				continue;
				//
			} // if
				//
			for (int i = 0; i < IterableUtils.size(children); i++) {
				//
				if (Util.matches(pattern.matcher(ElementUtil.text(element = IterableUtils.get(children, i))))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						createMultimap2(element.children()));
				//
			} // for
				//
		} // for
			//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(toBeRemoved);
		//
		if (Util.iterator(entries) != null) {
			//
			for (final Entry<String, String> entry : entries) {
				//
				remove(multimap, Util.getKey(entry), Util.getValue(entry));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	private static void remove(@Nullable final Multimap<?, ?> instance, final Object key, final Object value) {
		if (instance != null) {
			instance.remove(key, value);
		}
	}

	@Nullable
	private static Multimap<String, String> createMultimap2(final Iterable<Element> es) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(es) != null) {
			//
			Pattern pattern = null;
			//
			String key = null;
			//
			Matcher matcher = null;
			//
			for (final Element e : es) {
				//
				if (key == null) {
					//
					key = StringUtils.deleteWhitespace(ElementUtil.text(e));
					//
				} else {
					//
					matcher = Util.matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}+")),
							StringUtils.deleteWhitespace(ElementUtil.text(e)));
					//
					while (Util.find(matcher)) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key,
								matcher.group());
						//
					} // while
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		//
		if (instance == null || !Util.isAbsolute(instance)) {
			//
			return null;
			//
		} // if
			//
		return instance.toURL();
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}