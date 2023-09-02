package j2html.tags.specialized;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;

import j2html.tags.ContainerTagUtil;
import j2html.tags.TagUtil;

public final class ATagUtil {

	private ATagUtil() {
	}

	public static ATag createByUrl(final String url) throws IOException {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final Elements elements = ElementUtil.getElementsByTag(testAndApply(Objects::nonNull,
					(is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) != null
							? IOUtils.toString(is, StandardCharsets.UTF_8)
							: null,
					Jsoup::parse, null), "title");
			//
			TagUtil.attr(
					ContainerTagUtil.withText(aTag = new ATag(),
							ElementUtil
									.text(IterableUtils.size(elements) == 1 ? IterableUtils.get(elements, 0) : null)),
					"href", url);
			//
		} finally {
			//
			IOUtils.closeQuietly(is);
			//
		} // try
			//
		return aTag;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static InputStream openStream(final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

}