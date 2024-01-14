package org.springframework.beans.factory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
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
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Iterable<Element> tbodies = ElementUtil.select(testAndApply(
				Objects::nonNull, testAndApply(Util::isAbsolute,
						testAndApply(StringUtils::isNotBlank, url, URI::new, null), x -> toURL(x), null),
				x -> Jsoup.parse(x, 0), null), "tbody");
		//
		if (Util.iterator(tbodies) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		List<Element> children = null;
		//
		Element element = null;
		//
		for (final Element tbody : tbodies) {
			//
			if (tbody == null || tbody.childrenSize() < 1
					|| (pattern = ObjectUtils.getIfNull(pattern,
							() -> Pattern.compile("\\p{InHiragana}{1,2}行"))) == null
					|| !Util.matches(
							pattern.matcher(ElementUtil.text(IterableUtils.get(children = tbody.children(), 0))))) {
				//
				continue;
				//
			} // if
				//
			for (int i = 0; i < IterableUtils.size(children); i++) {
				//
				if (Util.matches(pattern.matcher(ElementUtil.text(element = IterableUtils.get(children, i))))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						createMultimap(element.children()));
				//
			} // for
				//
		} // for
			//
		remove(multimap, "後藤艮山", "もあり");
		//
		remove(multimap, "菅原ミネ嗣", "はくさ");
		//
		remove(multimap, "菅原ミネ嗣", "に");
		//
		final Iterable<String> strings = Arrays.asList("は", "を", "つ", "ねた", "です");
		//
		if (Util.iterator(strings) != null) {
			//
			for (final String string : strings) {
				//
				remove(multimap, "本間ソウ軒", string);
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	private static void remove(@Nullable final Multimap<?, ?> instance, final Object key, final Object value) {
		if (instance != null) {
			instance.remove(key, value);
		}
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Iterable<Element> es) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(es) != null) {
			//
			Pattern pattern = null;
			//
			String key = null;
			//
			Matcher matcher = null;
			//
			for (final Element e : es) {
				//
				if (key == null) {
					//
					key = StringUtils.deleteWhitespace(ElementUtil.text(e));
					//
				} else {
					//
					matcher = Util.matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}+")),
							StringUtils.deleteWhitespace(ElementUtil.text(e)));
					//
					while (Util.find(matcher)) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key,
								matcher.group());
						//
					} // while
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		//
		if (instance == null || !Util.isAbsolute(instance)) {
			//
			return null;
			//
		} // if
			//
		return instance.toURL();
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}