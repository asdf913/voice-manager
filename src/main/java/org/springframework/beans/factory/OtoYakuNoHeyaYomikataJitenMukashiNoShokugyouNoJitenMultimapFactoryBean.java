package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@URL("https://hiramatu-hifuka.com/onyak/syokmuka.html")
	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return toMultimap(Util.toList(Util.filter(
				NodeUtil.nodeStream(testAndApply(Objects::nonNull,
						testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null),
						x -> Jsoup.parse(x, 0), null)),
				x -> StringsUtil.equals(Strings.CS, "b", ElementUtil.tagName(Util.cast(Element.class, x))))));
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
		Node nextSibling;
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
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, StringUtils.split(Util.toString(nextSibling), '\u3000')));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String[] ss) {
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < Util.length(ss); i++) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, ArrayUtils.get(ss, i)));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) {
		//
		final List<BiFunction<PatternMap, String, IValue0<Multimap<String, String>>>> functions = Arrays.asList(
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean::toMultimap1,
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean::toMultimap2,
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean::toMultimap3,
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean::toMultimap4,
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean::toMultimap5);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = Util.apply(IterableUtils.get(functions, i), patternMap, s)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	private static IValue0<Multimap<String, String>> toMultimap1(final PatternMap patternMap, final String s) {
		//
		Matcher m = Util
				.matcher(PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"), s);
		//
		if (Util.matches(m) && Util.groupCount(m) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s))) {
			//
			return Unit.with(toMultimap1(m));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)）$"), s))) {
			//
			return Unit.with(toMultimap2(m));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)）$"),
				s))) {
			//
			String g;
			//
			List<UnicodeBlock> ubs = null;
			//
			List<String> kanjiList = null, hiraganaList = null;
			//
			for (int i = 1; i <= Util.groupCount(m); i++) {
				//
				if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						ubs = getUnicodeBlocks(g = Util.group(m, i)))) {
					//
					Util.add(kanjiList = ObjectUtils.getIfNull(kanjiList, ArrayList::new), g);
					//
				} else if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.HIRAGANA), ubs)) {
					//
					Util.add(hiraganaList = ObjectUtils.getIfNull(hiraganaList, ArrayList::new), g);
					//
				} // if
					//
			} // for
				//
			Multimap<String, String> multimap = null;
			//
			for (int i = 0; i < Math.min(IterableUtils.size(kanjiList), IterableUtils.size(hiraganaList)); i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IterableUtils.get(kanjiList, i), IterableUtils.get(hiraganaList, i));
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(m) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap2(final PatternMap patternMap, final String s) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})（(\\p{InHiragana}+)）$"), s);
		//
		String g2, g3;
		//
		final Strings strings = Strings.CS;
		//
		if (Util.matches(m) && Util.groupCount(m) > 2
				&& StringsUtil.endsWith(strings, g3 = Util.group(m, 3), g2 = Util.group(m, 2))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1),
					StringUtils.substring(g3, 0, StringUtils.length(g3) - StringUtils.length(g2))));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s))) {
			//
			return Unit.with(toMultimap1(m));
			//
		} // if
			//
		String g1;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}＊[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$"),
				s)) && Util.groupCount(m) > 2
				&& StringsUtil.startsWith(strings, g3 = Util.group(m, 3), g1 = Util.group(m, 1))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substring(g3, StringUtils.length(g1))));
			//
		} // if
			//
		String g4;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create();
			//
			MultimapUtil.put(multimap, Util.group(m, 1), StringUtils.substringBefore(g4, g2));
			//
			MultimapUtil.put(multimap, Util.group(m, 3), StringUtils.substringAfter(g4, g2));
			//
			return Unit.with(multimap);
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s))) {
			//
			return Unit.with(toMultimap1(m));
			//
		} else if (Util
				.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"), s))
				&& Util.groupCount(m) > 2
				&& StringsUtil.startsWith(strings, g3 = Util.group(m, 3), g1 = Util.group(m, 1))
				&& CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getUnicodeBlocks(g2 = Util.group(m, 2)))) {
			//
			return Unit.with(ImmutableMultimap.of(g2, StringUtils.substring(g3, StringUtils.length(g1))));
			//
		} else if (Util.matches(m = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)売り（(\\p{InHiragana}+)うり）$"), s))
				&& Util.groupCount(m) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(m) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap3(final PatternMap patternMap, final String s) {
		//
		List<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）＊（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\p{InCJKSymbolsAndPunctuation}?$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$");
		//
		Matcher m = null;
		//
		for (int i = 0; i < IterableUtils.size(patterns); i++) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, IterableUtils.get(patterns, i)), s))) {
				//
				return Unit.with(toMultimap2(m));
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList("^(\\p{InCJKUnifiedIdeographs}+)作り（(\\p{InHiragana}+)づくり）$",
				"^(\\p{InCJKUnifiedIdeographs}+)取り（(\\p{InHiragana}+)とり）$",
				"^(\\p{InCJKUnifiedIdeographs}+)直し（(\\p{InHiragana}+)なおし）$",
				"^(\\p{InCJKUnifiedIdeographs}+)磨き（(\\p{InHiragana}+)みがき）$",
				"^(\\p{InCJKUnifiedIdeographs}+)踊り（(\\p{InHiragana}+)おどり）$",
				"^(\\p{InCJKUnifiedIdeographs}+)張り（(\\p{InHiragana}+)はり）$",
				"^(\\p{InCJKUnifiedIdeographs}+)買い（(\\p{InHiragana}+)かい）$",
				"^(\\p{InCJKUnifiedIdeographs}+)売り（（(\\p{InHiragana}+)うり）$",
				"^(\\p{InCJKUnifiedIdeographs}+)替へ（(\\p{InHiragana}+)しかえ）$",
				"^(\\p{InCJKUnifiedIdeographs}+)舞い（(\\p{InHiragana}+)まい）$",
				"^(\\p{InCJKUnifiedIdeographs}+)入れ（(\\p{InHiragana}+)いれ）$",
				"^(\\p{InCJKUnifiedIdeographs}+)揚[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)あ\\p{InHiragana}+）$",
				"^(\\p{InCJKUnifiedIdeographs}+)磨き売り\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)みがきうり\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)拾ひ\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)ひろい\\p{InHalfwidthAndFullwidthForms}$");
		//
		for (int i = 0; i < IterableUtils.size(patterns); i++) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, IterableUtils.get(patterns, i)), s))
					&& Util.groupCount(m) > 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)売り（(\\p{InHiragana}+)うり）(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(m) > 3) {
			//
			return Unit
					.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2), Util.group(m, 3), Util.group(m, 4)));
			//
		} // if
			//
		String g3, g2;
		//
		patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）$",
				"^(\\p{InCJKUnifiedIdeographs}+)(の)[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）$");
		//
		for (int i = 0; i < IterableUtils.size(patterns); i++) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, IterableUtils.get(patterns, i)), s))
					&& Util.groupCount(m) > 2
					&& StringUtils.countMatches(g3 = Util.group(m, 3), g2 = Util.group(m, 2)) == 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g3, g2)));
				//
			} // if
				//
		} // for
			//
		String g4;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 2) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBeforeLast(g4, g2),
					Util.group(m, 3), StringUtils.substringAfterLast(g4, g2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap4(final PatternMap patternMap, final String s) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)入れ(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)いれ(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+$"),
				s);
		//
		if (Util.matches(m) && Util.groupCount(m) > 3) {
			//
			return Unit
					.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 3), Util.group(m, 2), Util.group(m, 4)));
			//
		} // if
			//
		String g3, g1;
		//
		if ((Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)売り\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)うり\\p{InHalfwidthAndFullwidthForms}$"),
				s)) && Util.groupCount(m) > 2
				&& StringUtils.countMatches(g3 = Util.group(m, 3), g1 = Util.group(m, 1)) == 1)
				|| (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\p{InCJKSymbolsAndPunctuation}]+$"),
						s)) && Util.groupCount(m) > 2
						&& StringsUtil.startsWith(Strings.CS, g3 = Util.group(m, 3), g1 = Util.group(m, 1)))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap5(final PatternMap patternMap, final String s) {
		//
		final Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+々)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s);
		//
		String g1;
		//
		final Iterable<String> repeatedStrings = getRepatedStrings(s);
		//
		if (Util.matches(m) && IterableUtils.size(repeatedStrings) == 1 && Util.groupCount(m) > 1
				&& StringsUtil.endsWith(Strings.CS, g1 = Util.group(m, 1), "々")) {
			//
			final StringBuilder sb = new StringBuilder(g1);
			//
			Multimap<String, String> multimap = null;
			//
			final int length = StringUtils.length(sb);
			//
			final String repeatedString = IterableUtils.get(repeatedStrings, 0);
			//
			for (int i = 0; i < length; i++) {
				//
				if (sb.charAt(i) == '々' && i > 0) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							new String(new char[] { sb.charAt(i - 1) }), repeatedString);
					//
					for (int j = i; j > 0; j--) {
						//
						sb.deleteCharAt(j);
						//
					} // for
						//
				} // for
					//
			} // for
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.toString(sb),
					remove(Strings.CS, Util.group(m, 2), repeatedString));
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static String remove(@Nullable final Strings instance, final String str, final String remove) {
		return instance != null ? instance.remove(str, remove) : null;
	}

	// https://gist.github.com/EasyG0ing1/f6e1d2c18d41850f80fa42bad469c9eb
	@Nullable
	private static Iterable<String> getRepatedStrings(final String userString) {
		//
		final int size = StringUtils.length(userString);
		//
		final String[] coreString = new String[size];
		//
		for (int x = 0; x < size; x++) {
			//
			coreString[x] = userString.substring(x, x + 1);
			//
		} // for
			//
		List<String> patterns = null;
		//
		String buildString;
		//
		for (int index = 0; index < size - 1; index++) {
			//
			buildString = coreString[index];
			//
			for (int x = index + 1; x < size; x++) {
				//
				Util.add(patterns = ObjectUtils.getIfNull(patterns, ArrayList::new),
						StringUtils.join(buildString, coreString[x]));
				//
			} // for
				//
		} // for
			//
		Map<String, Integer> hitCountMap = null;
		//
		if (Util.iterator(patterns) != null) {
			//
			for (final String pattern : patterns) {
				//
				if (StringsUtil.contains(Strings.CS, userString.replaceFirst(pattern, ""), pattern)) {
					//
					Util.put(hitCountMap = ObjectUtils.getIfNull(hitCountMap, LinkedHashMap::new), pattern,
							(size - userString.replaceAll(pattern, "").length()) / testAndApplyAsInt(x -> x == 0,
									StringUtils.length(pattern), x -> 1, IntUnaryOperator.identity(), 1));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return Util.keySet(hitCountMap);
		//
	}

	private static int testAndApplyAsInt(@Nullable final IntPredicate predicate, final int value,
			final IntUnaryOperator t, final IntUnaryOperator f, final int defaultValue) {
		return predicate != null && predicate.test(value) ? Util.applyAsInt(t, value, defaultValue)
				: Util.applyAsInt(f, value, defaultValue);
	}

	@Nullable
	private static Multimap<String, String> toMultimap1(final Matcher matcher) {
		//
		String group;
		//
		List<UnicodeBlock> ubs;
		//
		List<String> ss2 = null;
		//
		IValue0<String> hiragana = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int j = 1; Util.matches(matcher) && j <= Util.groupCount(matcher); j++) {
			//
			if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					ubs = getUnicodeBlocks(group = Util.group(matcher, j)))) {
				//
				Util.add(ss2 = ObjectUtils.getIfNull(ss2, ArrayList::new), group);
				//
			} else if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.HIRAGANA), ubs)) {
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
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap2(final Matcher matcher) {
		//
		Multimap<String, String> multimap = null;
		//
		List<UnicodeBlock> ubs;
		//
		IValue0<String> kanji = null;
		//
		List<String> ss2 = null;
		//
		String g;
		//
		for (int j = 1; j <= Util.groupCount(matcher); j++) {
			//
			if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					ubs = getUnicodeBlocks(g = Util.group(matcher, j)))) {
				//
				if (kanji != null) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				kanji = Unit.with(g);
				//
			} else if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.HIRAGANA), ubs)) {
				//
				Util.add(ss2 = ObjectUtils.getIfNull(ss2, ArrayList::new), g);
				//
			} // if
				//
		} // for
			//
		for (int j = 0; Boolean.logicalAnd(kanji != null, j < IterableUtils.size(ss2)); j++) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					IValue0Util.getValue0(kanji), IterableUtils.get(ss2, j));
			//
		} // for
			//
		return multimap;
		//
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