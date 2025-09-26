package j2html.tags.specialized;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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

import io.github.toolfactory.narcissus.Narcissus;
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

	public static ATag createByUrl(final String urlString)
			throws NoSuchFieldException, MalformedURLException, IOException, URISyntaxException {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final URLConnection urlConnection = openConnection(forName("org.junit.jupiter.api.Test") == null
					? toURL(testAndApply(StringUtils::isNotBlank, urlString, URI::new, null))
					: null);
			//
			setRequestProperty(urlConnection, "User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
			//
			final Document document = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull,
					is = getInputStream(urlConnection), x -> IOUtils.toString(x, StandardCharsets.UTF_8), null),
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
					"href", urlString);
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

	private static InputStream getInputStream(final URLConnection instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

	private static void setRequestProperty(final URLConnection instance, final String key, final String value)
			throws NoSuchFieldException {
		//
		if (instance != null && key != null
				&& !Narcissus.getBooleanField(instance, Narcissus.findField(URLConnection.class, "connected"))) {
			//
			instance.setRequestProperty(key, value);
			//
		} // if
			//
	}

	private static URLConnection openConnection(final URL instance) throws IOException, NoSuchFieldException {
		//
		return instance != null && Narcissus.getField(instance, Narcissus.findField(URL.class, "handler")) != null
				? instance.openConnection()
				: null;
		//
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null && instance.isAbsolute() ? instance.toURL() : null;
	}

	private static Class<?> forName(final String name) {
		try {
			return StringUtils.isNotBlank(name) ? Class.forName(name) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
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

}