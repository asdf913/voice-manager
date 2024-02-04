package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
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
 * https://hiramatu-hifuka.com/onyak/kotoba-1/kisyo.html
 */
public class OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		Multimap<String, String> multimap = null;
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null), "tr");
		//
		Element e = null;
		//
		List<String> ss1 = null;
		//
		List<String> ss2 = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null || e.childrenSize() < 3) {
				//
				continue;
				//
			} // if
				//
			if (IterableUtils
					.size(ss1 = getStrings(ElementUtil.text(IterableUtils.get(e.children(), 0)),
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) != 1
					|| IterableUtils.size(ss2 = getStrings(ElementUtil.text(IterableUtils.get(e.children(), 1)),
							UnicodeBlock.HIRAGANA)) != 1) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					IterableUtils.get(ss1, 0), IterableUtils.get(ss2, 0));
			//
		} // for
			//
		return multimap;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
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
		for (int i = 0; cs != null && i < cs.length; i++) {
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
		if (StringUtils.isNotEmpty(sb)) {
			//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
			//
		} // if
			//
		return list;
		//
	}

	private static void clear(final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}