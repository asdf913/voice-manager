package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.jsoup.select.NodeVisitor;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/robot01.html
 */
public class OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/robot01.html")
	private String url = null;

	private Iterable<Link> links = null;

	@Note("Text")
	private IValue0<String> text = null;

	@Note("Description")
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
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
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
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
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

		private IValue0<String> kanji = null;

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
			Matcher matcher = Util
					.matcher(Pattern.compile("(\\p{InCJKUnifiedIdeographs}+)（(((\\u3000)?\\p{InHiragana}+)+)）"), text);
			//
			final int size = MultimapUtil.size(getMultimap());
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
			if (size == MultimapUtil.size(getMultimap())) {
				//
				if (Util.matches(text, "^\\p{InCJKUnifiedIdeographs}+$")) {
					//
					kanji = Unit.with(text);
					//
				} else {
					//
					matcher = Util.matcher(Pattern.compile("（(\\p{InHiragana}+)）"), text);
					//
					while (Util.find(matcher)) {
						//
						if (Util.groupCount(matcher) > 0 && kanji != null) {
							//
							MultimapUtil.put(getMultimap(), IValue0Util.getValue0(kanji), Util.group(matcher, 1));
							//
						} // if
							//
					} // while
						//
				} // if
					//
			} // if
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