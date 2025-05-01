package j2html.tags.specialized;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import j2html.tags.ContainerTagUtil;
import j2html.tags.TagUtil;

public final class ATagUtil {

	private ATagUtil() {
	}

	private interface WinInet extends Library {

		WinInet INSTANCE = Native.load("Wininet", WinInet.class);

		boolean InternetGetConnectedState(final IntByReference lpdwFlags, final int dwReserved);

	}

	public static boolean InternetGetConnectedState(final boolean defaultValue) {
		//
		if (Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
			//
			final WinInet winInet = WinInet.INSTANCE;
			//
			if (winInet != null) {
				//
				return winInet.InternetGetConnectedState(new IntByReference(), 0);
				//
			} // if
				//
		} // if
			//
		return defaultValue;
		//
	}

	public static ATag createByUrl(final String url) throws Exception {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final Document document = testAndApply(Objects::nonNull,
					(is = openStream(testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null))) != null
							? IOUtils.toString(is, StandardCharsets.UTF_8)
							: null,
					Jsoup::parse, null);
			//
			Elements elements = ElementUtil.getElementsByTag(document, "title");
			//
			if (CollectionUtils.size(elements) > 1) {
				//
				elements = ElementUtil.getElementsByTag(testAndApply(x -> IterableUtils.size(x) == 1,
						ElementUtil.getElementsByTag(document, "head"), x -> IterableUtils.get(x, 0), null), "title");
				//
			} // if
				//
			TagUtil.attr(ContainerTagUtil.withText(aTag = new ATag(), ElementUtil
					.text(testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null))),
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

	public static ATag createByUrl(final String url, final String text) throws Exception {
		//
		if (!InternetGetConnectedState(true)) {
			//
			return TagUtil.attr(ContainerTagUtil.withText(new ATag(), text), "href", url);
			//
		} // if
			//
		return createByUrl(url);
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