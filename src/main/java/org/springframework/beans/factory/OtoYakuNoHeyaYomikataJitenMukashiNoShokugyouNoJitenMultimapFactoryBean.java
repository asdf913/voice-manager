package org.springframework.beans.factory;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
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
		final Element document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final Iterable<Node> bs = Util.toList(Util.filter(NodeUtil.nodeStream(document),
				x -> StringUtils.equals("b", ElementUtil.tagName(Util.cast(Element.class, x)))));
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(bs) != null) {
			//
			PatternMap patternMap = null;
			//
			String[] ss;
			//
			Node nextSibling = null;
			//
			Matcher m = null;
			//
			for (final Node b : bs) {
				//
				if (b == null
						|| !Util.matches(Util.matcher(PatternMap.getPattern(
								patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
								"^（\\p{InHiragana}）$"), ElementUtil.text(Util.cast(Element.class, b))))
						|| (nextSibling = b.nextSibling()) instanceof Element) {
					//
					continue;
					//
				} // if
					//
				ss = StringUtils.split(Util.toString(nextSibling), '\u3000');
				//
				for (int i = 0; ss != null && i < ss.length; i++) {
					//
					if (Util.matches(m = Util.matcher(
							PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
							ss[i])) && Util.groupCount(m) > 1) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.group(m, 1), Util.group(m, 2));
						//
					} // if
						//
				} // for
					//
			} // for
				//
		} // if
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

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}