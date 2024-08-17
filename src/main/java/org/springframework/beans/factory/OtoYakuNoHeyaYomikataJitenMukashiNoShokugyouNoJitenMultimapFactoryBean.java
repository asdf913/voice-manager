package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/syokmuka.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		return toMultimap(
				Util.toList(
						Util.filter(
								NodeUtil.nodeStream(
										testAndApply(Objects::nonNull,
												testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(),
														null),
												x -> Jsoup.parse(x, 0), null)),
								x -> StringUtils.equals("b", ElementUtil.tagName(Util.cast(Element.class, x))))));
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<Node> nodes) {
		//
		if (Util.iterator(nodes) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		PatternMap patternMap = null;
		//
		String[] ss1;
		//
		Node nextSibling;
		//
		Matcher m;
		//
		int groupCount;
		//
		List<UnicodeBlock> ubs;
		//
		List<String> ss2 = null;
		//
		String group, s = null;
		//
		IValue0<String> hiragana;
		//
		for (final Node node : nodes) {
			//
			if (Boolean.logicalOr(
					!Util.matches(Util.matcher(
							PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									"^（\\p{InHiragana}）$"),
							ElementUtil.text(Util.cast(Element.class, node)))),
					(nextSibling = NodeUtil.nextSibling(node)) instanceof Element)) {
				//
				continue;
				//
			} // if
				//
			ss1 = StringUtils.split(Util.toString(nextSibling), '\u3000');
			//
			for (int i = 0; i < Util.length(ss1); i++) {
				//
				if (Util.matches(m = Util.matcher(
						PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
								"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
						s = ArrayUtils.get(ss1, i))) && Util.groupCount(m) > 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m, 1), Util.group(m, 2));
					//
				} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(
						patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
						s)) && (groupCount = Util.groupCount(m)) > 2) {
					//
					clear(ss2 = ObjectUtils.getIfNull(ss2, ArrayList::new));
					//
					hiragana = null;
					//
					for (int j = 1; j <= groupCount; j++) {
						//
						if (CollectionUtils.isEqualCollection(
								Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
								ubs = getUnicodeBlocks(group = Util.group(m, j)))) {
							//
							Util.add(ss2, group);
							//
						} else if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.HIRAGANA),
								ubs)) {
							//
							if (hiragana != null) {
								//
								throw new IllegalStateException();
								//
							} // if
								//
							hiragana = Unit.with(group);
							//
						} // if
							//
					} // for
						//
					for (int j = 0; Boolean.logicalAnd(hiragana != null, j < IterableUtils.size(ss2)); j++) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								IterableUtils.get(ss2, j), IValue0Util.getValue0(hiragana));
						//
					} // for
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	private static void clear(@Nullable final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
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