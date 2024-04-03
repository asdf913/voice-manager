package org.springframework.beans.factory;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.jsoup.select.NodeVisitor;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/tetu-jr.html
 */
public class OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		return createMultimap(url);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final String url) throws Exception {
		//
		final NodeVisitorImpl nodeVisitorImpl = new NodeVisitorImpl();
		//
		NodeUtil.traverse(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), nodeVisitorImpl);
		//
		return nodeVisitorImpl.multimap;
		//
	}

	private static class NodeVisitorImpl implements NodeVisitor {

		private Multimap<String, String> multimap = null;

		private Multimap<String, String> getMultimap() {
			if (multimap == null) {
				multimap = LinkedHashMultimap.create();
			}
			return multimap;
		}

		@Override
		public void head(final Node node, final int depth) {
			//
			final String text = StringUtils.trim(TextNodeUtil.text(Util.cast(TextNode.class, node)));
			//
			final Matcher matcher = Util
					.matcher(Pattern.compile("(\\p{InCJKUnifiedIdeographs}+)（(((\\u3000)?\\p{InHiragana}+)+)）"), text);
			//
			while (Util.find(matcher)) {
				//
				if (Util.groupCount(matcher) > 1) {
					//
					MultimapUtil.put(getMultimap(), Util.group(matcher, 1),
							StringUtils.deleteWhitespace(Util.group(matcher, 2)));
					//
				} // if
					//
			} // while
				//
		}

	}

	@Nullable
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