package org.apache.commons.lang3.function;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.htmlunit.Page;
import org.htmlunit.ScriptResult;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;
import org.springframework.context.annotation.Description;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.github.toolfactory.narcissus.Narcissus;

@Description("T(java.lang.String).format('Online (%1$s)',url)")
public class FuriganaMueckeFailableFunction implements FailableFunction<String, String, Exception> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String apply(final String input) throws Exception {
		//
		try (final WebClient webClient = new WebClient()) {
			//
			final HtmlPage htmlPage = cast(HtmlPage.class, testAndApply(
					x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x), url, webClient::getPage, null));
			//
			setTextContent(testAndApply(x -> IterableUtils.size(x) > 0, getElementsByTagName(htmlPage, "textarea"),
					x -> IterableUtils.get(x, 0), null), input);
			//
			return toString(
					getJavaScriptResult(executeJavaScript(
							cast(HtmlPage.class,
									click(testAndApply(x -> IterableUtils.size(x) > 0,
											toList(filter(stream(getElementsByTagName(htmlPage, "input")),
													x -> Objects.equals(getAttribute(x, "name"), "submit"))),
											x -> IterableUtils.get(x, 0), null))),
							"document.querySelector(\"body ruby\").outerHTML;")));
			//
		}
		//
	}

	private static String getAttribute(final Element instance, final String name) {
		return instance != null ? instance.getAttribute(name) : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static void setTextContent(final Node instance, final String textContent) throws DOMException {
		if (instance != null) {
			instance.setTextContent(textContent);
		}
	}

	private static Object getJavaScriptResult(final ScriptResult instance) {
		return instance != null ? instance.getJavaScriptResult() : null;
	}

	private static ScriptResult executeJavaScript(final HtmlPage instance, final String sourceCode) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final List<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(getClass(instance))),
				f -> Objects.equals(f != null ? f.getName() : null, "webClient_")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) > 0, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.executeJavaScript(sourceCode);
		//
	}

	private static <P extends Page> P click(final DomElement instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final List<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(getClass(instance))),
				f -> Objects.equals(f != null ? f.getName() : null, "page_")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) > 0, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.click();
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: instance;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static DomNodeList<DomElement> getElementsByTagName(final HtmlPage instance, final String tagname) {
		return instance != null ? instance.getElementsByTagName(tagname) : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}