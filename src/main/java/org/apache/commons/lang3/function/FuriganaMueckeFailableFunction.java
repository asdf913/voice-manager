package org.apache.commons.lang3.function;

import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.springframework.context.annotation.Description;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;

@Description("Online (${url!''})")
public class FuriganaMueckeFailableFunction implements FailableFunction<String, String, RuntimeException> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String apply(final String input) throws RuntimeException {
		//
		try (final Playwright playwright = testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x),
				url, x -> Playwright.create(), null)) {
			//
			final Page page = newPage(BrowserTypeUtil.launch(chromium(playwright)));
			//
			PageUtil.navigate(page, url);
			//
			fill(locator(page, "textarea"), input);
			//
			click(locator(page, "[name=\"submit\"]"));
			//
			return toString(evaluate(locator(page, "body ruby"), "x=>x.outerHTML"));
			//
		} // try
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static Page newPage(final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static BrowserType chromium(final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	private static Locator locator(final Page instance, final String selector) {
		return instance != null ? instance.locator(selector) : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Object evaluate(final Locator instance, final String expression) {
		return instance != null ? instance.evaluate(expression) : null;
	}

	private static void click(final Locator instance) {
		if (instance != null) {
			instance.click();
		}
	}

	private static void fill(final Locator instance, final String value) {
		if (instance != null) {
			instance.fill(value);
		}
	}

}