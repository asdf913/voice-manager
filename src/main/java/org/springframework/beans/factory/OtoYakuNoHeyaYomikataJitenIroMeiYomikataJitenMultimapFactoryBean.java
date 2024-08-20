package org.springframework.beans.factory;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/sikime01.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Iterable<Node> nodes = Util.toList(NodeUtil.nodeStream(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null)));
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(nodes) != null) {
			//
			String[] ss = null;
			//
			Pattern pattern = null;
			//
			Matcher matcher = null;
			//
			for (final Node node : nodes) {
				//
				if (!(node instanceof TextNode) || (ss = StringUtils.split(Util.toString(node), "\u3000")) == null) {
					//
					continue;
					//
				} // if
					//
				for (final String s : ss) {
					//
					if (Util.matches(matcher = Util.matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile(
									"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$")),
							StringUtils.trim(s))) && Util.groupCount(matcher) > 1) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.group(matcher, 1), Util.group(matcher, 2));
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