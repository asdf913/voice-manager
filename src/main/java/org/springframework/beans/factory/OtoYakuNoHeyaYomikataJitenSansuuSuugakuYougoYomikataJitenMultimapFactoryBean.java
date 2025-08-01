package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/sugaku.html
 */
public class OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	private static final Logger LOG = LoggerFactory
			.getLogger(OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.class);

	private static final String TBODY = "tbody";

	private static IValue0<Iterable<Class<?>>> CLASSES = null;

	@org.springframework.beans.factory.URL("https://hiramatu-hifuka.com/onyak/kotoba-1/sugaku.html")
	private String url = null;

	private Iterable<Link> links = null;

	@Note("Text")
	private IValue0<String> text = null;

	private IValue0<String> description = null;

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

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> multimap = getIvalue0();
		//
		if (multimap != null) {
			//
			return IValue0Util.getValue0(multimap);
			//
		} // if
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
			return toMultimap(ElementUtil.select(
					testAndApply(Objects::nonNull,
							testAndApply(Util::isAbsolute,
									testAndApply(StringUtils::isNotBlank, Link.getUrl(IterableUtils.get(ls, 0)),
											URI::new, null),
									x -> toURL(x), null),
							x -> Jsoup.parse(x, 0), null),
					TBODY));
			//
		} // if
			//
		if ((size = IterableUtils.size(ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> description != null && x != null
						&& Objects.equals(x.getDescription(), IValue0Util.getValue0(description)))))) > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return toMultimap(ElementUtil.select(
					testAndApply(Objects::nonNull,
							testAndApply(Util::isAbsolute,
									testAndApply(StringUtils::isNotBlank, Link.getUrl(IterableUtils.get(ls, 0)),
											URI::new, null),
									x -> toURL(x), null),
							x -> Jsoup.parse(x, 0), null),
					TBODY));
			//
		} // if
			//
		return toMultimap(ElementUtil.select(testAndApply(
				Objects::nonNull, testAndApply(Util::isAbsolute,
						testAndApply(StringUtils::isNotBlank, url, URI::new, null), x -> toURL(x), null),
				x -> Jsoup.parse(x, 0), null), TBODY));
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<Element> tbodies) {
		//
		if (Util.iterator(tbodies) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		for (final Element tbody : tbodies) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(tbody,
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}{1,3}行")),
							"数学者"));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Element tbody, final Pattern pattern,
			final String string) {
		//
		if (ElementUtil.childrenSize(tbody) < 1) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Element> children = ElementUtil.children(tbody);
		//
		final String text = ElementUtil.text(IterableUtils.get(children, 0));
		//
		if (!Util.matches(Util.matcher(pattern, text)) && !Objects.equals(string, text)) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Element element = null;
		//
		String s0 = null;
		//
		for (int i = 0; i < IterableUtils.size(children); i++) {
			//
			if (Util.matches(pattern.matcher(ElementUtil.text(element = IterableUtils.get(children, i))))) {
				//
				continue;
				//
			} // if
				//
			s0 = StringUtils.deleteWhitespace(ElementUtil.text(
					testAndApply(x -> ElementUtil.childrenSize(x) > 0, element, x -> ElementUtil.child(x, 0), null)));
			//
			MultimapUtil
					.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							toMultimap(s0,
									Util.toList(
											Util.filter(
													Util.stream(
															getStrings(
																	StringUtils.deleteWhitespace(
																			ElementUtil.text(testAndApply(
																					x -> ElementUtil
																							.childrenSize(x) > 1,
																					element,
																					x -> ElementUtil.child(x, 1),
																					null))),
																	UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA)),
													StringUtils::isNotEmpty))));
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(s0, ElementUtil.text(testAndApply(x -> ElementUtil.childrenSize(x) > 2, element,
							x -> ElementUtil.child(x, 2), null))));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final String s1, final String s3) {
		//
		if (CLASSES == null) {
			//
			CLASSES = Unit.with(Arrays.asList(StringToMultimapImpl.class, Prefix差StringToMultimap.class,
					PrefixRStringToMultimap.class, PrefixpHStringToMultimap.class, PrefixRyuubeiStringToMultimap.class,
					PrefixStringToMultimap.class, SuffixDomoStringToMultimap.class,
					SecondDerivativeStringToMultimap.class));
			//
		} // if
			//
		final List<Multimap<?, ?>> multimaps = Util
				.toList(Util.map(
						Util.filter(Util.stream(getObjects(IValue0Util.getValue0(CLASSES), s1, s3)),
								x -> Boolean.logicalOr(x == null, x instanceof Multimap)),
						x -> Util.cast(Multimap.class, x)));
		//
		final int size = IterableUtils.size(multimaps);
		//
		if (size == 1) {
			//
			return Util.collect(Util.stream(MultimapUtil.entries(IterableUtils.get(multimaps, 0))),
					LinkedHashMultimap::create,
					(k, v) -> MultimapUtil.put(k, Util.toString(Util.getKey(v)), Util.toString(Util.getValue(v))),
					Multimap::putAll);
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		final Supplier<StringBuilder> supplier = StringBuilder::new;
		//
		final Matcher matcher = Util.matcher(Pattern.compile(String.format("((%1$s)+)（(\\p{InHiragana}+)）",
				Util.collect(Stream.of("CJKUnifiedIdeographs", "Hiragana"), supplier, (a, b) -> {
					//
					if (StringUtils.isNotBlank(a)) {
						//
						Util.append(a, '|');
						//
					} // if
						//
					if (a != null) {
						//
						a.append(String.format("\\p{In%1$s}", b));
						//
					} // if
						//
				}, StringBuilder::append))), s3);
		//
		String g1, s11, g3 = null;
		//
		int i1 = 0;
		//
		while (Util.find(matcher) && Util.groupCount(matcher) > 2) {
			//
			g3 = Util.group(matcher, 3);
			//
			if ((i1 = getLastIndexWithUnicodeBlock(g1 = StringUtils.trim(Util.group(matcher, 1)),
					UnicodeBlock.HIRAGANA)) >= 0
					&& StringUtils.isNotEmpty(
							s11 = StringUtils.trim(StringUtils.substring(g1, i1 + 1, StringUtils.length(g1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s11, g3);
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g1, g3);
			//
		} // while
			//
		return multimap;
		//
	}

	private static int getLastIndexWithUnicodeBlock(final String s, final UnicodeBlock unicodeBlock) {
		//
		final char[] cs = Util.toCharArray(s);
		//
		int index = -1;
		//
		for (int i = 0; i < length(cs); i++) {
			//
			if (Objects.equals(UnicodeBlock.of(cs[i]), unicodeBlock)) {
				//
				index = i;
				//
			} // if
				//
		} // for
			//
		return index;
		//
	}

	@Nullable
	private static Collection<Object> getObjects(final Iterable<Class<?>> iterable, final Object a, final Object b) {
		//
		Collection<Object> objects = null;
		//
		if (Util.iterator(iterable) != null) {
			//
			Object instance = null;
			//
			for (final Class<?> c : iterable) {
				//
				if (!((instance = testAndApply(Objects::nonNull, c, Narcissus::allocateInstance,
						null)) instanceof BiPredicate) || !Util.test((BiPredicate) instance, a, b)) {
					//
					continue;
					//
				} // if
					//
				if (instance instanceof BiFunction function) {
					//
					Util.add(objects = ObjectUtils.getIfNull(objects, ArrayList::new), Util.apply(function, a, b));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return objects;
		//
	}

	private static interface StringToMultimap
			extends BiPredicate<String, String>, BiFunction<String, String, Multimap<String, String>> {
	}

	private static class StringToMultimapImpl implements StringToMultimap {

		private static final Pattern PATTERN = Pattern
				.compile("^(関連語：)?(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$");

		@Override
		public boolean test(final String a, final String b) {
			//
			return Boolean.logicalOr(Util.matches(Util.matcher(PATTERN, b)),
					StringsUtil.startsWith(Strings.CS, b, "関連語："));
			//
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final Matcher matcher = Util.matcher(PATTERN, b);
			//
			if (Boolean.logicalAnd(Util.matches(matcher), Util.groupCount(matcher) > 2)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 2), Util.group(matcher, 3));
				//
			} else if (StringsUtil.startsWith(Strings.CS, b, "関連語：")) {
				//
				final char[] cs = Util.toCharArray(StringUtils.substringAfter(b, "関連語："));
				//
				UnicodeBlock unicodeBlock = null;
				//
				char c = ' ';
				//
				StringBuilder sb = null;
				//
				TextStringBuilder tsb = null;
				//
				boolean leftParenthesisFound = false;
				//
				for (int j = 0; j < length(cs); j++) {
					//
					if (Objects.equals(unicodeBlock = UnicodeBlock.of(c = cs[j]),
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
						//
						Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
						//
					} else if (Objects.equals(unicodeBlock, UnicodeBlock.HIRAGANA)) {
						//
						if (leftParenthesisFound) {
							//
							append(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new), c);
							//
						} else {
							//
							Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
							//
						} // if
							//
					} else if (c == '（') {
						//
						leftParenthesisFound = true;
						//
					} else if (c == '）') {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.toString(sb), Util.toString(tsb));
						//
						clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
						//
						TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
						//
						leftParenthesisFound = false;
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

	}

	private static void append(@Nullable final TextStringBuilder instance, final char c) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readDeclaredField(instance, "buffer", true) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.append(c);
		//
	}

	private static class Prefix差StringToMultimap implements StringToMultimap {

		@Override
		public boolean test(final String a, final String b) {
			return StringsUtil.startsWith(Strings.CS, b, "差");
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final char[] cs = Util.toCharArray(b);
			//
			UnicodeBlock unicodeBlock = null;
			//
			char c = ' ';
			//
			StringBuilder sb = null;
			//
			TextStringBuilder tsb = null;
			//
			boolean leftParenthesisFound = false;
			//
			for (int j = 0; j < length(cs); j++) {
				//
				if (Objects.equals(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, unicodeBlock = UnicodeBlock.of(c = cs[j]))) {
					//
					Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
					//
				} else if (Util.contains(Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA), unicodeBlock)) {
					//
					if (!leftParenthesisFound) {
						//
						Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
						//
					} else {
						//
						append(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new), c);
						//
					} // if
						//
				} else if (c == '（') {
					//
					leftParenthesisFound = true;
					//
				} else if (c == '）') {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							toMultimap(MultimapUtil.isEmpty(multimap), Util.toString(sb), Util.toString(tsb)));
					//
					clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
					//
					TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
					//
					leftParenthesisFound = false;
					//
				} // if
					//
			} // for
				//
			return multimap;
			//
		}

		@Nullable
		private static Multimap<String, String> toMultimap(final boolean first, final String s1, final String s2) {
			//
			Multimap<String, String> multimap = null;
			//
			if (first) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} else {
				//
				final int index = getLastIndexWithUnicodeBlock(s1, UnicodeBlock.HIRAGANA);
				//
				if (index >= 0) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substring(s1, index + 1, StringUtils.length(s1)),
							testAndApply(Objects::nonNull, StringUtils.split(s2, '・'), Arrays::asList, null));
					//
				} // if
					//
			} // if
				//
			return multimap;
			//
		}

	}

	private static class PrefixRStringToMultimap implements StringToMultimap {

		@Override
		public boolean test(final String a, final String b) {
			return StringsUtil.startsWith(Strings.CS, b, "R-");
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final char[] cs = Util.toCharArray(b);
			//
			UnicodeBlock unicodeBlock = null;
			//
			char c = ' ';
			//
			StringBuilder sb = null;
			//
			TextStringBuilder tsb = null;
			//
			for (int j = 0; j < length(cs); j++) {
				//
				if (Util.contains(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						unicodeBlock = UnicodeBlock.of(c = cs[j]))) {
					//
					Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
					//
				} else if (Util.contains(Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA), unicodeBlock)) {
					//
					append(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new), c);
					//
				} else if (c == '）') {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.toString(sb), Util.toString(tsb));
					//
					clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
					//
					TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
					//
				} // if
					//
			} // for
				//
			return multimap;
			//
		}

	}

	private static class PrefixpHStringToMultimap implements StringToMultimap {

		@Override
		public boolean test(final String a, final String b) {
			return Objects.equals(a, "pH");
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final char[] cs = Util.toCharArray(b);
			//
			char c = ' ';
			//
			StringBuilder sb = null;
			//
			boolean leftParenthesisFound = false;
			//
			for (int i = 0; i < length(cs); i++) {
				//
				if ((c = cs[i]) == '（') {
					//
					leftParenthesisFound = true;
					//
				} else if (c == '）') {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), a,
							Util.toString(sb));
					//
					clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
					//
					leftParenthesisFound = false;
					//
				} else if (leftParenthesisFound) {
					//
					Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
					//
				} // if
					//
			} // for
				//
			return multimap;
			//
		}

	}

	private static class PrefixRyuubeiStringToMultimap implements StringToMultimap {

		private static final Pattern PATTERN = Pattern.compile("（(\\p{InCJKUnifiedIdeographs}+)・(\\p{InHiragana}+)）");

		@Override
		public boolean test(final String a, final String b) {
			//
			return Util.find(Util.matcher(PATTERN, b));
			//
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final Matcher matcher = Util.matcher(PATTERN, b);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} // while
				//
			return multimap;
			//
		}

	}

	private static class SecondDerivativeStringToMultimap implements StringToMultimap {

		@Override
		public boolean test(final String a, final String b) {
			//
			return Objects.equals(a, "第2次導関数");
			//
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final char[] cs = Util.toCharArray(b);
			//
			UnicodeBlock unicodeBlock = null;
			//
			char c = ' ';
			//
			StringBuilder sb = null;
			//
			TextStringBuilder tsb = null;
			//
			for (int j = 0; j < length(cs); j++) {
				//
				if (Util.contains(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						unicodeBlock = UnicodeBlock.of(c = cs[j]))) {
					//
					Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
					//
				} else if (Objects.equals(UnicodeBlock.HIRAGANA, unicodeBlock)) {
					//
					append(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new), c);
					//
				} else if (c == '）') {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.toString(sb), Util.toString(tsb));
					//
					break;
					//
				} // if
					//
			} // for
				//
			return multimap;
			//
		}

	}

	private static class PrefixStringToMultimap implements StringToMultimap {

		@Override
		public boolean test(final String a, final String b) {
			//
			return Util.contains(Arrays.asList("ISO", "十進法", "零行列", "六十進法", "平均余命"), a);
			//
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			final Matcher matcher = Util.matcher(Pattern.compile("（(\\p{InHiragana}+)）"), b);
			//
			Multimap<String, String> multimap = null;
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), a,
						Util.group(matcher, 1));
				//
			} // while
				//
			return multimap;
			//
		}

	}

	private static class SuffixDomoStringToMultimap implements StringToMultimap {

		private static final Pattern PATTERN = Pattern.compile("^（(\\p{InHiragana}+)）とも$");

		@Override
		public boolean test(final String a, final String b) {
			//
			return Util.find(Util.matcher(PATTERN, b));
			//
		}

		@Override
		@Nullable
		public Multimap<String, String> apply(final String a, final String b) {
			//
			Multimap<String, String> multimap = null;
			//
			final Matcher matcher = Util.matcher(PATTERN, b);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), a,
						Util.group(matcher, 1));
				//
			} // while
				//
			return multimap;
			//
		}

	}

	@Nullable
	private static Multimap<String, String> toMultimap(final String s1, final Iterable<String> ss2) {
		//
		final String[] ss1 = StringUtils.split(s1, "・");
		//
		final int length = Util.length(ss1);
		//
		final int size = IterableUtils.size(ss2);
		//
		Multimap<String, String> multimap = null;
		//
		if (length == size) {
			//
			for (int j = 0; j < length; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[j],
						IterableUtils.get(ss2, j));
				//
			} // for
				//
		} else if (size == 1) {
			//
			for (int j = 0; j < length; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[j],
						IterableUtils.get(ss2, 0));
				//
			} // for
				//
		} else {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, ss2);
			//
		} // if
			//
		return multimap;
		//
	}

	private static int length(@Nullable final char[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static List<String> getStrings(final String string, final UnicodeBlock ub, final UnicodeBlock... ubs) {
		//
		final char[] cs = Util.toCharArray(string);
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		List<String> list = null;
		//
		for (int i = 0; i < length(cs); i++) {
			//
			if (!Objects.equals(UnicodeBlock.of(c = cs[i]), ub)
					&& !ArrayUtils.contains(ubs, UnicodeBlock.of(c = cs[i]))) {
				//
				if (i > 0) {
					//
					Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
					//
				} // if
					//
				clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
				//
			} else {
				//
				Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
				//
			} // if
				//
		} // for
			//
		if (StringUtils.isNotEmpty(sb) && list == null) {
			//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
			//
		} // if
			//
		return list;
		//
	}

	private static void clear(@Nullable final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
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

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}