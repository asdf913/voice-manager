package org.springframework.beans.factory;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.jsoup.select.NodeVisitor;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/robot01.html
 */
public class OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		if (document != null) {
			//
			final NodeVisitorImpl nodeVisitorImpl = new NodeVisitorImpl();
			//
			document.traverse(nodeVisitorImpl);
			//
			return nodeVisitorImpl.multimap;
			//
		} // if
			//
		return null;
		//
	}

	private static class NodeVisitorImpl implements NodeVisitor {

		private Multimap<String, String> multimap = null;

		@Override
		public void head(final Node node, final int depth) {
			//
			final Matcher matcher = Util.matcher(
					Pattern.compile("(\\p{InCJKUnifiedIdeographs}+)（(((\\u3000)?\\p{InHiragana}+)+)）"),
					StringUtils.trim(TextNodeUtil.text(Util.cast(TextNode.class, node))));
			//
			while (Util.find(matcher)) {
				//
				if (Util.groupCount(matcher) > 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(matcher, 1), StringUtils.deleteWhitespace(Util.group(matcher, 2)));
					//
				} // if
					//
			} // while
				//
		}

	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}