package org.apache.commons.lang3.function;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;

import com.github.hal4j.uritemplate.URIBuilder;

import domain.Pronunciation;

public class OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl
		implements OnlineNHKJapanesePronunciationsAccentFailableFunction {

	private String url = null;

	private Integer imageType = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * The value should be one of the below.
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_CUSTOM
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_ARGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_ARGB_PRE
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_BGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_3BYTE_BGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_4BYTE_ABGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_4BYTE_ABGR_PRE
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_565_RGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_555_RGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_GRAY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_GRAY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_BINARY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_INDEXED
	 */
	public void setImageType(final Integer imageType) {
		this.imageType = imageType;
	}

	@Override
	public List<Pronunciation> apply(final String input) throws IOException {
		//
		List<Pronunciation> list = null;
		//
		try {
			//
			final URL u = toURL(toURI(relative(testAndApply(Objects::nonNull, url, URIBuilder::basedOn, null), input)));
			//
			list = createPronunciations(u, ElementUtil.select(
					testAndApply(Objects::nonNull, u, x -> Jsoup.parse(x, 0), null), "audio[title='発音図：']"), imageType);
			//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return list;
		//
	}

	private static List<Pronunciation> createPronunciations(final URL url, final List<Element> elements,
			final Number imageType) {
		//
		List<Pronunciation> list = null;
		//
		Element element = null;
		//
		Pronunciation pronunciation = null;
		//
		Map<String, String> audioUrls = null;
		//
		final String protocolAndHost = testAndApply(Objects::nonNull, url,
				x -> String.join("://", getProtocol(x), getHost(x)), null);
		//
		for (int i = 0; i < IterableUtils.size(elements); i++) {
			//
			if ((element = IterableUtils.get(elements, i)) == null) {
				//
				continue;
				//
			} // if
				//
			(pronunciation = new Pronunciation()).setPageUrl(toString(url));
			//
			pronunciation.setAudioUrls(audioUrls = getSrcMap(element));
			//
			forEach(entrySet(audioUrls), x -> setValue(x, String.join("", protocolAndHost, getValue(x))));
			//
			pronunciation.setPitchAccentImage(createMergedBufferedImage(protocolAndHost, getImageSrcs(element),
					intValue(imageType, BufferedImage.TYPE_INT_RGB)));
			//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), pronunciation);
			//
		} // for
			//
		return list;
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Map<String, String> getSrcMap(final Element input) {
		//
		Map<String, String> map = null;
		//
		final Elements children = ElementUtil.children(input);
		//
		Element element = null;
		//
		for (int i = 0; i < IterableUtils.size(children); i++) {
			//
			if ((element = IterableUtils.get(children, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) != null) {
				//
				map.put(NodeUtil.attr(element, "type"), NodeUtil.attr(element, "src"));
				//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	private static List<String> getImageSrcs(final Element element) {
		//
		List<String> srcs = null;
		//
		Element temp = element, nextElement = null;
		//
		while ((nextElement = ElementUtil.nextElementSibling(temp)) != null
				&& !Objects.equals("audio", ElementUtil.tagName(nextElement))) {
			//
			if ((srcs = ObjectUtils.getIfNull(srcs, ArrayList::new)) != null) {
				//
				srcs.add(NodeUtil.attr(nextElement, "src"));
				//
			} // if
				//
			temp = nextElement;
			//
		} // while
			//
		return srcs;
		//
	}

	private static BufferedImage createMergedBufferedImage(final String urlString, final List<String> srcs,
			final int imageType) {
		//
		final FailableStream<String> fs1 = new FailableStream<>(stream(srcs));
		//
		final FailableStream<BufferedImage> fs2 = fs1 != null
				? FailableStreamUtil.map(fs1, x -> ImageIO.read(new URI(String.join("/", urlString, x)).toURL()))
				: null;
		//
		final List<BufferedImage> bis = fs2 != null ? fs2.collect(Collectors.toList()) : null;
		//
		BufferedImage result = null;
		//
		Graphics graphics = null;
		//
		final AtomicInteger position = new AtomicInteger(0);
		//
		BufferedImage bi = null;
		//
		for (int i = 0; i < IterableUtils.size(bis); i++) {
			//
			if ((bi = IterableUtils.get(bis, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (result == null) {
				//
				result = new BufferedImage(
						//
						// total width
						//
						orElse(reduce(mapToInt(stream(bis), x -> intValue(getWidth(x), 0)), Integer::sum), 0)
						//
						// max height
						//
						, orElse(reduce(mapToInt(stream(bis), x -> intValue(getHeight(x), 0)), Integer::max), 0)
						//
						, imageType);
				//
			} // if
				//
			if (graphics == null) {
				//
				graphics = getGraphics(result);
				//
			} // if
				//
			drawImage(graphics, bi, position != null ? position.getAndAdd(intValue(getWidth(bi), 0)) : 0, 0, null);
			//
		} // for
			//
		return result;
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	private static OptionalInt reduce(final IntStream instance, final IntBinaryOperator op) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || op != null) ? instance.reduce(op) : null;
		//
	}

	private static int orElse(final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static Graphics getGraphics(final Image instance) {
		return instance != null ? instance.getGraphics() : null;
	}

	private static boolean drawImage(final Graphics instance, final Image image, final int x, final int y,
			final ImageObserver observer) {
		return instance != null && instance.drawImage(image, x, y, observer);
	}

	private static Integer getWidth(final RenderedImage instance) {
		return instance != null ? Integer.valueOf(instance.getWidth()) : null;
	}

	private static Integer getHeight(final RenderedImage instance) {
		return instance != null ? Integer.valueOf(instance.getHeight()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static <T> void forEach(final Iterable<T> items, final Consumer<? super T> action) {
		//
		if (iterator(items) != null && (Proxy.isProxyClass(getClass(items)) || action != null)) {
			//
			for (final T item : items) {
				//
				action.accept(item);
				//
			} // for
				//
		} // if
			//
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <V> void setValue(final Entry<?, V> instance, final V value) {
		if (instance != null) {
			instance.setValue(value);
		}
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private static String getHost(final URL instance) {
		return instance != null ? instance.getHost() : null;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null && instance.isAbsolute() ? instance.toURL() : null;
	}

	private static URI toURI(final URIBuilder instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static URIBuilder relative(final URIBuilder instance, final Object... pathSegments) {
		return instance != null ? instance.relative(pathSegments) : null;
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