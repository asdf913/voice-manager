package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
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
 * https://hiramatu-hifuka.com/onyak/kotoba-1/sugaku.html
 */
public class OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean
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
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(tbodies) != null) {
			//
			Pattern pattern = null;
			//
			List<Element> children = null;
			//
			Element element = null;
			//
			String s1 = null;
			//
			String[] ss1 = null;
			//
			List<String> ss2 = null;
			//
			int length, size = 0;
			//
			for (final Element tbody : tbodies) {
				//
				if (tbody == null || tbody.childrenSize() < 1 || !Util.matches(Util.matcher(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}{1,2}行")),
						ElementUtil.text(IterableUtils.get(children = tbody.children(), 0))))) {
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
					if ((length = length(
							ss1 = StringUtils.split(
									s1 = ElementUtil.text(testAndApply(x -> ElementUtil.childrenSize(x) > 0, element,
											x -> ElementUtil.child(x, 0), null)),
									"・"))) == (size = IterableUtils
											.size(ss2 = getStrings(
													ElementUtil.text(testAndApply(x -> ElementUtil.childrenSize(x) > 1,
															element, x -> ElementUtil.child(x, 1), null)),
													UnicodeBlock.HIRAGANA)))) {
						//
						for (int j = 0; j < length(ss1); j++) {
							//
							MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
									ss1[j], IterableUtils.get(ss2, j));
							//
						} // for
							//
					} else if (size == 1) {
						//
						for (int j = 0; j < length; j++) {
							//
							MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
									ss1[j], IterableUtils.get(ss2, 0));
							//
						} // for
							//
					} else {
						//
						MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
								ss2);
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

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static List<String> getStrings(final String string, final UnicodeBlock unicodeBlock) {
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
			if (!Objects.equals(UnicodeBlock.of(c = cs[i]), unicodeBlock)) {
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
		if (StringUtils.isNotEmpty(sb) && list == null) {
			//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
			//
		} // if
			//
		return list;
		//
	}

	private static void clear(@Nullable final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
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