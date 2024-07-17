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
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;
import org.springframework.util.function.SingletonSupplier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/rekisi/tenno.html
 */
public class OtoYakuNoHeyaYomikataJitenTennoYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@URL("https://hiramatu-hifuka.com/onyak/rekisi/tenno.html")
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
			return getObject(Link.getUrl(IterableUtils.get(ls, 0)));
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
			return getObject(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
			//
		return getObject(url);
		//
	}

	@Nullable
	private static Multimap<String, String> getObject(final String url) throws Exception {
		//
		final List<Element> trs = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "table[border=\"1\"] tr");
		//
		Multimap<String, String> multimap = null, mm;
		//
		Supplier<Pattern> supplier = null;
		//
		for (int i = 0; trs != null && i < trs.size(); i++) {
			//
			if ((mm = createMultimap(
					supplier = ObjectUtils.getIfNull(supplier,
							() -> new SingletonSupplier<>(null, () -> Pattern.compile("^（(\\p{InHiragana}+)）$"))),
					ElementUtil.children(trs.get(i)))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Supplier<Pattern> supplier,
			@Nullable final Iterable<Element> tds) {
		//
		if (tds == null || IterableUtils.size(tds) < 5) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null, mm;
		//
		if ((mm = createMultimap(supplier, ElementUtil.text(IterableUtils.get(tds, 1)),
				ElementUtil.text(IterableUtils.get(tds, 2)))) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if ((mm = createMultimap(supplier, ElementUtil.text(IterableUtils.get(tds, 3)),
				ElementUtil.text(IterableUtils.get(tds, 4)))) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Supplier<Pattern> supplier, final String s1,
			final String s2) {
		//
		final Matcher m = Util
				.matcher(ObjectUtils.getIfNull(get(supplier), () -> Pattern.compile("^（(\\p{InHiragana}+)）$")), s2);
		//
		if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), getUnicodeBlocks(s1))
				&& Util.matches(m) && Util.groupCount(m) > 0) {
			//
			return ImmutableMultimap.of(s1, Util.group(m, 1));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static <T> T get(@Nullable final Supplier<T> instance) {
		return instance != null ? instance.get() : null;
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