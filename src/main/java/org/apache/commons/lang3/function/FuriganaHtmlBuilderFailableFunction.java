package org.apache.commons.lang3.function;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringsUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.springframework.context.annotation.Description;

import com.atilika.kuromoji.TokenBase;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.google.common.base.Strings;
import com.mariten.kanatools.KanaConverter;

import io.github.toolfactory.narcissus.Narcissus;
import j2html.rendering.FlatHtml;
import j2html.rendering.HtmlBuilder;
import j2html.rendering.TagBuilder;

@Description("Offline")
public class FuriganaHtmlBuilderFailableFunction implements FailableFunction<String, String, IOException> {

	private static final Pattern PATTERN_HIRAGANA = Pattern.compile("^\\p{InHiragana}+$");

	public String apply(final String string) throws IOException {
		//
		final Collection<Token> tokens = testAndApply(x -> Boolean.logicalAnd(Objects.nonNull(x), isPlainText(x)),
				string, new Tokenizer()::tokenize, null);
		//
		if (iterator(tokens) == null) {
			//
			return null;
			//
		} // if
			//
		HtmlBuilder<StringBuilder> htmlBuilder = null;
		//
		for (final Token token : tokens) {
			//
			toHtml(htmlBuilder = ObjectUtils.getIfNull(htmlBuilder, FlatHtml::inMemory), token);
			//
		} // for
			//
		return toString(output(htmlBuilder));
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T extends Appendable> T output(final HtmlBuilder<T> instance) {
		return instance != null ? instance.output() : null;
	}

	private static void toHtml(final HtmlBuilder<StringBuilder> htmlBuilder, final TokenBase token) throws IOException {
		//
		final String[] allFeatures = getAllFeaturesArray(token);
		//
		if (length(allFeatures) < 9) {
			//
			return;
			//
		} // if
			//
		final String surface = getSurface(token);
		//
		final String reading = ArrayUtils.get(allFeatures, 7);
		//
		final String convertKana = KanaConverter.convertKana(reading, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA);
		//
		if (Boolean.logicalOr(StringsUtil.equals(org.apache.commons.lang3.Strings.CS, surface, convertKana),
				Objects.equals(surface, reading))
				|| (surface != null && surface.matches("^\\d+$") && Objects.equals(reading, "*"))) {
			//
			appendUnescapedText(htmlBuilder, surface);
			//
			return;
			//
		} // if
			//
		final String lcs = longestCommonSubstring(surface, convertKana);
		//
		final String[] ss1 = StringUtils.split(surface, lcs);
		//
		final String[] ss2 = StringUtils.split(convertKana, lcs);
		//
		final int length1 = length(ss1);
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(lcs), length1 == length(ss2))) {
			//
			String commonPrefix, commonSuffix, s1, s2 = null;
			//
			for (int i = 0; i < length1; i++) {
				//
				testAndRun(
						Boolean.logicalOr(Boolean.logicalAnd(i == 1, length1 == 2),
								Objects.equals(commonPrefix = Strings.commonPrefix(surface, convertKana), lcs)),
						() -> appendUnescapedText(htmlBuilder, lcs), null);
				//
				completeTag(appendStartTag(completeTag(appendStartTag(htmlBuilder, "ruby")), "rb"));
				//
				if (StringUtils.isNotBlank(commonSuffix = Strings.commonSuffix(s1 = ArrayUtils.get(ss1, i),
						s2 = ArrayUtils.get(ss2, i)))) {
					//
					appendUnescapedText(htmlBuilder,
							StringUtils.substring(s1, 0, StringUtils.length(s1) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					appendUnescapedText(htmlBuilder, s1);
					//
				} // if
					//
				completeTag(
						appendStartTag(
								appendEndTag(appendUnescapedText(
										completeTag(appendStartTag(appendEndTag(htmlBuilder, "rb"), "rp")), "("), "rp"),
								"rt"));
				//
				if (StringUtils.isNotBlank(commonSuffix = Strings.commonSuffix(s1, s2))) {
					//
					appendUnescapedText(htmlBuilder,
							StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					appendUnescapedText(htmlBuilder, s2);
					//
				} // if
					//
				appendEndTag(
						appendEndTag(appendUnescapedText(
								completeTag(appendStartTag(appendEndTag(htmlBuilder, "rt"), "rp")), ")"), "rp"),
						"ruby");
				//
				testAndAccept((a, b) -> StringUtils.isNotBlank(b), htmlBuilder, commonSuffix,
						FuriganaHtmlBuilderFailableFunction::appendUnescapedText);
				//
				testAndRun(and(i == 0, length1 == 1, !Objects.equals(commonPrefix, lcs)),
						() -> appendUnescapedText(htmlBuilder, lcs), null);
				//
			} // for
				//
			return;
			//
		} // if
			//
		toHtml(htmlBuilder, surface, convertKana);
		//
	}

	private static void toHtml(final HtmlBuilder<StringBuilder> htmlBuilder, final String text, final String ruby)
			throws IOException {
		//
		if (matches(matcher(PATTERN_HIRAGANA, text)) && Objects.equals(ruby, "*")) {
			//
			appendUnescapedText(htmlBuilder, text);
			//
			return;
			//
		} // if
			//
		final String commonPrefix = testAndApply((a, b) -> a != null && b != null, text, ruby, Strings::commonPrefix,
				null);
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, commonPrefix);
			//
		} // if
			//
		completeTag(appendStartTag(completeTag(appendStartTag(htmlBuilder, "ruby")), "rb"));
		//
		final String commonSuffix = testAndApply((a, b) -> a != null && b != null, text, ruby, Strings::commonSuffix,
				null);
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, StringUtils.substringAfter(text, commonPrefix));
			//
		} else if (StringUtils.isNotBlank(commonSuffix)) {
			//
			appendUnescapedText(htmlBuilder,
					StringUtils.substring(text, 0, StringUtils.length(text) - StringUtils.length(commonSuffix)));
			//
		} else {
			//
			appendUnescapedText(htmlBuilder, text);
			//
		} // if
			//
		completeTag(appendStartTag(appendEndTag(
				appendUnescapedText(completeTag(appendStartTag(appendEndTag(htmlBuilder, "rb"), "rp")), "("), "rp"),
				"rt"));
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, StringUtils.substringAfter(ruby, commonPrefix));
			//
		} else if (StringUtils.isNotBlank(commonSuffix)) {
			//
			appendUnescapedText(htmlBuilder,
					StringUtils.substring(ruby, 0, StringUtils.length(ruby) - StringUtils.length(commonSuffix)));
			//
		} else {
			//
			appendUnescapedText(htmlBuilder, ruby);
			//
		} // if
			//
		appendEndTag(appendEndTag(
				appendUnescapedText(completeTag(appendStartTag(appendEndTag(htmlBuilder, "rt"), "rp")), ")"), "rp"),
				"ruby");
		//
		testAndAccept((a, b) -> StringUtils.isNotBlank(b), htmlBuilder, commonSuffix,
				FuriganaHtmlBuilderFailableFunction::appendUnescapedText);
		//
	}

	private static boolean matches(final Matcher instance) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, Matcher.class.getDeclaredFields(), Arrays::stream, null),
						x -> Objects.equals(getName(x), "groups")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (Narcissus.getField(instance,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return false;
			//
		} // if
			//
		return instance.matches();
		//
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		//
		if (pattern == null) {
			//
			return null;
			//
		} // if
			//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, Pattern.class.getDeclaredFields(), Arrays::stream, null),
						x -> Objects.equals(getName(x), "pattern")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (Narcissus.getField(pattern,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return null;
			//
		} // if
			//
		return input != null ? pattern.matcher(input) : null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, U, E extends Exception> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			FailableBiConsumerUtil.accept(consumer, t, u);
		} // if
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static <T extends Appendable> HtmlBuilder<T> appendEndTag(final HtmlBuilder<T> instance, final String name)
			throws IOException {
		return instance != null ? instance.appendEndTag(name) : null;
	}

	private static HtmlBuilder<? extends Appendable> completeTag(final TagBuilder instance) throws IOException {
		return instance != null ? instance.completeTag() : null;
	}

	private static TagBuilder appendStartTag(final HtmlBuilder<?> instance, final String name) throws IOException {
		return instance != null ? instance.appendStartTag(name) : null;
	}

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> ra,
			final FailableRunnable<E> rb) throws E {
		if (b) {
			FailableRunnableUtil.run(ra);
		} else {
			FailableRunnableUtil.run(rb);
		}
	}

	private static String longestCommonSubstring(final String a, final String b) {
		//
		int start = 0, max = 0;
		//
		for (int i = 0; i < StringUtils.length(a); i++) {
			//
			for (int j = 0; j < StringUtils.length(b); j++) {
				//
				int x = 0;
				//
				while (a.charAt(i + x) == b.charAt(j + x)) {
					//
					x++;
					//
					if (((i + x) >= a.length()) || ((j + x) >= b.length())) {
						//
						break;
						//
					} // if
						//
				} // while
					//
				if (x > max) {
					//
					max = x;
					//
					start = i;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return StringUtils.substring(a, start, start + max);
		//
	}

	private static <T extends Appendable> HtmlBuilder<T> appendUnescapedText(final HtmlBuilder<T> instance,
			final String txt) throws IOException {
		return instance != null ? instance.appendUnescapedText(txt) : null;
	}

	private static String getSurface(final TokenBase instance) {
		return instance != null ? instance.getSurface() : null;
	}

	private static int length(final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static String[] getAllFeaturesArray(final TokenBase instance) {
		return instance != null ? instance.getAllFeaturesArray() : null;
	}

	private static boolean isPlainText(final String string) {
		//
		Element element = testAndApply(x -> IterableUtils.size(x) == 1,
				ElementUtil.children(testAndApply(Objects::nonNull, string, Jsoup::parse, null)),
				x -> IterableUtils.get(x, 0), null);
		//
		final List<Element> es = ElementUtil.children(element);
		//
		boolean plainText = true;
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(string), IterableUtils.size(es) == 2)) {
			//
			plainText &= and(ElementUtil.childrenSize(element = IterableUtils.get(es, 0)) == 0,
					NodeUtil.attributesSize(element) == 0,
					//
					ArrayUtils.contains(new int[] { 0, 1 }, NodeUtil.childNodeSize(element = IterableUtils.get(es, 1))),
					NodeUtil.attributesSize(element) == 0
					//
					,
					StringsUtil.equals(org.apache.commons.lang3.Strings.CS,
							TextNodeUtil.text(cast(TextNode.class, testAndApply(x -> NodeUtil.childNodeSize(x) == 1,
									element, x -> NodeUtil.childNode(x, 0), null))),
							string));
			//
		} // if
			//
		return plainText;
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) {
		//
		boolean result = Boolean.logicalAnd(a, b);
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			result &= bs[i];
			//
		} // for
			//
		return result;
		//
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}