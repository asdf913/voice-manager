package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/tenno.html
 */
public class OtoYakuNoHeyaYomikataJitenTennoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<Element> trs = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "table[border=\"1\"] tr");
		//
		List<Element> tds = null;
		//
		Pattern p = null;
		//
		Matcher m;
		//
		Multimap<String, String> multimap = null;
		//
		String s;
		//
		for (int i = 0; trs != null && i < trs.size(); i++) {
			//
			if ((tds = ElementUtil.children(trs.get(i))) == null || tds.size() < 5
					|| (m = Util.matcher(p = ObjectUtils.getIfNull(p, () -> Pattern.compile("^（(\\p{InHiragana}+)）$")),
							ElementUtil.text(tds.get(2)))) == null
					|| !m.matches() || Util.groupCount(m) < 1) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					getUnicodeBlocks(s = ElementUtil.text(tds.get(1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s,
						Util.group(m, 1));
				//
			} // if
				//
			if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					getUnicodeBlocks(s = ElementUtil.text(tds.get(3))))
					&& (m = Util.matcher(p = ObjectUtils.getIfNull(p, () -> Pattern.compile("^（(\\p{InHiragana}+)）$")),
							ElementUtil.text(tds.get(4)))) != null
					&& m.matches() && Util.groupCount(m) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s,
						Util.group(m, 1));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

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