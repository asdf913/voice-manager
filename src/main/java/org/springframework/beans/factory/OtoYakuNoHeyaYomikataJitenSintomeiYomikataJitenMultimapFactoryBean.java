package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.jsoup.select.NodeVisitor;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/sintomei.html
 */
public class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	private Iterable<Link> links = null;

	private IValue0<String> text = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		final int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return toMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
			//
		return toMultimap(url);
		//
	}

	private static interface PatternMap {

		Pattern getPattern(final String pattern);

		@Nullable
		private static Pattern getPattern(@Nullable final PatternMap instance, final String pattern) {
			return instance != null ? instance.getPattern(pattern) : null;
		}

	}

	private static class IH implements InvocationHandler {

		private Map<Object, Pattern> patternMap = null;

		private Map<Object, Pattern> getPatternMap() {
			if (patternMap == null) {
				patternMap = new LinkedHashMap<>();
			}
			return patternMap;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof PatternMap && Objects.equals(methodName, "getPattern") && args != null
					&& args.length > 0) {
				//
				final Object arg0 = args[0];
				//
				if (!Util.containsKey(getPatternMap(), arg0)) {
					//
					Util.put(getPatternMap(), arg0,
							testAndApply(x -> x != null, Util.toString(arg0), Pattern::compile, null));
					//
				} // if
					//
				return Util.get(getPatternMap(), arg0);
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Nullable
	private static Multimap<String, String> toMultimap(final String url) throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es = ElementUtil.select(document, "table[border=\"1\"] tr");
		//
		boolean hasAttrRowspan = false;
		//
		List<Element> children = null;
		//
		Integer rowspan = null;
		//
		int offset = 0;
		//
		Multimap<String, String> multimap = null, temp;
		//
		PatternMap patternMap = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (hasAttrRowspan = NodeUtil.hasAttr(
					IterableUtils.get(children = ElementUtil.children(IterableUtils.get(es, i)), 0), "rowspan")) {
				//
				rowspan = valueOf(NodeUtil.attr(IterableUtils.get(children, 0), "rowspan"));
				//
			} // if
				//
			if ((temp = toMultimap(
					patternMap = ObjectUtils.getIfNull(patternMap,
							() -> Reflection.newProxy(PatternMap.class, new IH())),
					children,
					offset = iif(Util.or(rowspan == null, hasAttrRowspan, intValue(rowspan, 0) <= 0), 1, 0))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), temp);
				//
			} // if
				//
			if ((temp = toMultimap2(patternMap, children, offset)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), temp);
				//
			} // if
				//
			rowspan = decrease(rowspan, 1);
			//
		} // for
			//
		final NodeVisitorImpl nodeVisitorImpl = new NodeVisitorImpl();
		//
		nodeVisitorImpl.patternMap = ObjectUtils.getIfNull(patternMap,
				() -> Reflection.newProxy(PatternMap.class, new IH()));
		//
		NodeUtil.traverse(document, nodeVisitorImpl);
		//
		if (nodeVisitorImpl.multimap != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					nodeVisitorImpl.multimap);
			//
		} // if
			//
		return multimap;
		//
	}

	private static class NodeVisitorImpl implements NodeVisitor {

		private Multimap<String, String> multimap = null;

		private PatternMap patternMap = null;

		@Override
		public void head(final Node node, final int depth) {
			//
			final Matcher m = Util
					.matcher(
							PatternMap.getPattern(
									ObjectUtils.getIfNull(patternMap,
											() -> Reflection.newProxy(PatternMap.class, new IH())),
									"([\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}]+)（(\\p{InHiragana}+)）"),
							TextNodeUtil.text(Util.cast(TextNode.class, node)));
			//
			String s1 = null;
			//
			while (Util.find(m) && Util.groupCount(m) > 1) {
				//
				if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getUnicodeBlocks(s1 = Util.group(m, 1)))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							Util.group(m, 2));
					//
				} // if
					//
			} // while
				//
		}

	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final List<Element> children,
			final int offset) {
		//
		Multimap<String, String> multimap = null;
		//
		final Matcher m1 = Util
				.matcher(
						PatternMap.getPattern(
								ObjectUtils.getIfNull(patternMap,
										() -> Reflection.newProxy(PatternMap.class, new IH())),
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)?$"),
						ElementUtil.text(testAndApply(x -> IterableUtils.size(x) > offset, children,
								x -> IterableUtils.get(children, offset), null)));
		//
		final Matcher m2 = Util
				.matcher(
						PatternMap.getPattern(ObjectUtils.getIfNull(patternMap,
								() -> Reflection.newProxy(PatternMap.class, new IH())), "^\\p{InHiragana}+$"),
						ElementUtil.text(testAndApply(x -> IterableUtils.size(x) > 2 + offset, children,
								x -> IterableUtils.get(children, 2 + offset), null)));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 0 && Util.matches(m2)) {
			//
			final String s1 = Util.group(m1, 1);
			//
			final String s2 = Util.group(m2, 0);
			//
			final String s = Util.group(m1, 2);
			//
			if (StringUtils.endsWith(s2, s)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(s)));
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap2(final PatternMap patternMap, final List<Element> children,
			final int offset) {
		//
		Multimap<String, String> multimap = null, mm;
		//
		final Element e3 = testAndApply(x -> IterableUtils.size(x) > 3 + offset, children,
				x -> IterableUtils.get(x, 3 + offset), null);
		//
		final String s3 = ElementUtil.text(e3);
		//
		final Matcher m3 = Util.matcher(PatternMap.getPattern(
				ObjectUtils.getIfNull(patternMap, () -> Reflection.newProxy(PatternMap.class, new IH())),
				"(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InHiragana}+)[\\)）]"), s3);
		//
		Matcher m4 = null;
		//
		if (Util.matches(m3) && Util.groupCount(m3) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m3, 1),
					Util.group(m3, 2));
			//
		} else if (NodeUtil.childNodeSize(e3) == 1 && IterableUtils.get(NodeUtil.childNodes(e3), 0) instanceof TextNode
				&& (m4 = Util.matcher(
						PatternMap.getPattern(
								ObjectUtils.getIfNull(patternMap,
										() -> Reflection.newProxy(PatternMap.class, new IH())),
								"([\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}]+)（(\\p{InHiragana}+)[\\)）]"),
						s3)) != null) {
			//
			String s41 = null;
			//
			while (Util.find(m4) && Util.groupCount(m4) > 1) {
				//
				if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getUnicodeBlocks(s41 = Util.group(m4, 1)))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s41,
							Util.group(m4, 2));
					//
				} // if
					//
			} // while
				//
		} else if ((mm = toMultimap21(
				ObjectUtils.getIfNull(patternMap, () -> Reflection.newProxy(PatternMap.class, new IH())),
				s3)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap21(final PatternMap patternMap, final String s3) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m5 = null;
		//
		if ((m5 = Util.matcher(PatternMap.getPattern(
				ObjectUtils.getIfNull(patternMap, () -> Reflection.newProxy(PatternMap.class, new IH())),
				"[（|(][\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+[)|）]([\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}]+)（(\\p{InHiragana}+)[)）]"),
				s3)) != null) {
			//
			String s51 = null;
			//
			while (Util.find(m5) && Util.groupCount(m5) > 1) {
				//
				if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getUnicodeBlocks(s51 = Util.group(m5, 1)))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s51,
							Util.group(m5, 2));
					//
				} // if
					//
			} // while
				//
		} // if
			//
		return multimap;
		//
	}

	private static Integer decrease(@Nullable final Integer instance, final int i) {
		return Integer.valueOf(intValue(instance, 0) - i);
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(final String string) {
		//
		final char[] cs = Util.toCharArray(string);
		//
		if (cs != null) {
			//
			List<UnicodeBlock> unicodeBlocks = null;
			//
			for (final char c : cs) {
				//
				testAndAccept((a, b) -> b != null && !Util.contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						Util::add);
				//
			} // for
				//
			return unicodeBlocks;
			//
		} // if
			//
		return null;
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	private static int iif(final boolean condition, final int trueValue, final int falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Nullable
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
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