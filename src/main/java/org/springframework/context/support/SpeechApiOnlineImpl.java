package org.springframework.context.support;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomElementUtil;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlOption;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSelect;
import org.htmlunit.html.HtmlTextArea;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

public class SpeechApiOnlineImpl implements SpeechApi {

	private static Pattern PATTERN = null;

	private String url = null;

	private IValue0<Map<String, String>> voices = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public boolean isInstalled() {
		return true;
	}

	private static class IH implements InvocationHandler {

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(Util.getName(method));
			//
		}

	}

	@Target(ElementType.PARAMETER)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Override
	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate,
			@Note("Not usued") final int volume) {
		//
		final URL u = execute(url, text, getVoices(), voiceId, rate);
		//
		try (final InputStream is = testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, Util.openStream(u), IOUtils::toByteArray, null),
				ByteArrayInputStream::new, null); final AudioInputStream ais = getAudioInputStream(is)) {
			//
			final AudioFormat af = getFormat(ais);
			//
			final Info info = testAndApply(x -> !isTestMode(), af, x -> new DataLine.Info(SourceDataLine.class, x),
					x -> Util.cast(Info.class, Narcissus.allocateInstance(Info.class)));
			//
			final Collection<Field> fs = Util
					.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(info))),
							f -> Objects.equals(Util.getName(f), "lineClass")));
			//
			testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final DataLine dl = Util
					.cast(DataLine.class,
							testAndApply(
									x -> and(
											testAndApply(y -> IterableUtils.size(y) == 1, fs,
													y -> IterableUtils.get(y, 0), null),
											Objects::nonNull, y -> Narcissus.getField(info, y) != null),
									info, AudioSystem::getLine,
									x -> Reflection.newProxy(SourceDataLine.class, new IH())));
			//
			final byte[] buf = new byte[1024];
			//
			final SourceDataLine sdl = Util.cast(SourceDataLine.class, dl);
			//
			open(sdl, af, buf.length);
			//
			start(dl);
			//
			int len;
			//
			while (ais != null && (len = ais.read(buf)) != -1) {
				//
				write(sdl, buf, 0, len);
				//
			} // while
				//
			drain(dl);
			//
			stop(dl);
			//
			close(dl);
			//
		} catch (final IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	private static void close(@Nullable final Line instance) {
		if (instance != null) {
			instance.close();
		}
	}

	private static void stop(@Nullable final DataLine instance) {
		if (instance != null) {
			instance.stop();
		}
	}

	private static void drain(@Nullable final DataLine instance) {
		if (instance != null) {
			instance.drain();
		}
	}

	private static int write(@Nullable final SourceDataLine instance, final byte[] b, final int off, final int len) {
		return instance != null ? instance.write(b, off, len) : 0;
	}

	private static void start(@Nullable final DataLine instance) {
		if (instance != null) {
			instance.start();
		}
	}

	private static void open(@Nullable final SourceDataLine instance, @Nullable final AudioFormat format,
			final int bufferSize) throws LineUnavailableException {
		if (instance != null) {
			instance.open(format, bufferSize);
		}
	}

	private static <T> boolean and(final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Nullable
	private static AudioFormat getFormat(@Nullable final AudioInputStream instance) {
		return instance != null ? instance.getFormat() : null;
	}

	@Nullable
	private static AudioInputStream getAudioInputStream(@Nullable final InputStream instance)
			throws UnsupportedAudioFileException, IOException {
		return instance != null ? AudioSystem.getAudioInputStream(instance) : null;
	}

	@Override
	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			@Note("Not usued") final int volume, @Nullable final File file) {
		//
		final URL u = execute(url, text, getVoices(), voiceId, rate);
		//
		try (final InputStream is = Util.openStream(u)) {
			//
			testAndAccept((a, b) -> b != null, file, is,
					(a, b) -> FileUtils.writeByteArrayToFile(a, IOUtils.toByteArray(b)));
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> predicate,
			@Nullable final T t, final U u, @Nullable final FailableBiConsumer<T, U, E> consumer) throws E {
		if (Util.test(predicate, t, u) && consumer != null) {
			consumer.accept(t, u);
		} // if
	}

	@Nullable
	private static URL execute(final String url, @Nullable final String text, final Map<String, String> voices,
			@Nullable final String voiceId, final int rate) {
		//
		try (final WebClient webClient = new WebClient()) {
			//
			final HtmlPage htmlPage = testAndApply(Objects::nonNull, url, webClient::getPage, null);
			//
			if (getElementByName(htmlPage, "SPKR") instanceof HtmlSelect htmlSelect) {
				//
				final List<String> keys = Util.toList(Util.map(
						Util.filter(Util.stream(Util.entrySet(voices)), x -> Objects.equals(Util.getValue(x), voiceId)),
						Util::getKey));
				//
				final int size = IterableUtils.size(keys);
				//
				testAndRunThrows(size > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				final Iterable<HtmlOption> options = Util.toList(Util.filter(Util.stream(getOptions(htmlSelect)),
						x -> StringUtils.equals(getValueAttribute(x), testAndApply(y -> IterableUtils.size(y) == 1,
								keys, y -> IterableUtils.get(y, 0), null))));
				//
				testAndRunThrows(IterableUtils.size(options) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				setSelectedIndex(htmlSelect, testAndApply(x -> IterableUtils.size(x) == 1, options,
						x -> testAndApply(y -> IterableUtils.size(y) == 1, x, y -> IterableUtils.get(y, 0), null),
						null));
				//
			} // if
				//
			if (getElementByName(htmlPage, "SYNTEXT") instanceof HtmlTextArea htmlTextArea) {
				//
				Util.setTextContent(htmlTextArea, text);
				//
			} // if
				//
			if (getElementByName(htmlPage, "DURATION") instanceof HtmlInput htmlInput) {
				//
				htmlInput.setValue(Integer.toString(rate));
				//
			} // if
				//
			final HtmlPage hm = Util.cast(HtmlPage.class, DomElementUtil
					.click(Util.cast(DomElement.class, querySelector(htmlPage, "input[type=\"submit\"]"))));
			//
			final IValue0<String> attribute = getAttribute(getElementsByTagName(hm, "source"), "src",
					x -> StringUtils.startsWith(x, "./temp/"));
			//
			if (attribute != null) {
				//
				return new URL(String.join("/", StringUtils.substringBeforeLast(url, "/"),
						StringUtils.substringAfter(IValue0Util.getValue0(attribute), '/')));
				//
			} else if (hm != null) {
				//
				final Iterable<DomNode> domNodes = Util.toList(Util.filter(Util.stream(hm.querySelectorAll("b")),
						x -> Objects.equals(Util.getTextContent(x), "合成結果")));
				//
				testAndRunThrows(IterableUtils.size(domNodes) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				throw new RuntimeException(StringUtils.trim(Util.toString(testAndApply(x -> Util.getLength(x) == 1,
						Util.getChildNodes(getNextElementSibling(testAndApply(x -> IterableUtils.size(x) == 1, domNodes,
								x -> IterableUtils.get(x, 0), null))),
						x -> Util.item(x, 0), null))));
				//
			} // if
				//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static <E extends DomElement> E getElementByName(@Nullable final HtmlPage instance, final String name) {
		return instance != null ? instance.getElementByName(name) : null;
	}

	@Nullable
	private static final String getValueAttribute(@Nullable final HtmlOption instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, FieldUtils.getAllFields(Util.getClass(instance)), Arrays::stream, null),
				f -> Objects.equals(Util.getName(f), "attributes_")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getValueAttribute();
		//
	}

	@Nullable
	private static List<HtmlOption> getOptions(@Nullable final HtmlSelect instance) {
		return instance != null ? instance.getOptions() : null;
	}

	private static void setSelectedIndex(@Nullable final HtmlSelect htmlSelect, @Nullable final HtmlOption htmlOption) {
		//
		if (htmlSelect == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, FieldUtils.getAllFields(Util.getClass(htmlSelect)),
						Arrays::stream, null), f -> Objects.equals(Util.getName(f), "attributes_")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(htmlSelect, f) == null) {
			//
			return;
			//
		} // if
			//
		if (htmlOption != null) {
			//
			htmlSelect.setSelectedIndex(htmlOption.getIndex());
			//
		} // if
			//
	}

	@Nullable
	private static DomElement getNextElementSibling(@Nullable final DomNode instance) {
		return instance != null ? instance.getNextElementSibling() : null;
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	@Nullable
	private static IValue0<String> getAttribute(@Nullable final NodeList nodeList, final String attrbiuteName,
			final Predicate<String> predicate) {
		//
		Node namedItem = null;
		//
		String nodeValue = null;
		//
		IValue0<String> result = null;
		//
		for (int i = 0; i < Util.getLength(nodeList); i++) {
			//
			if ((namedItem = Util.getNamedItem(Util.getAttributes(Util.item(nodeList, i)), attrbiuteName)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Util.test(predicate, nodeValue = namedItem.getNodeValue())) {
				//
				if (result == null) {
					//
					result = Unit.with(nodeValue);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static NodeList getElementsByTagName(@Nullable final org.w3c.dom.Document instance, final String tagname) {
		return instance != null ? instance.getElementsByTagName(tagname) : null;
	}

	@Nullable
	private static <N extends DomNode> N querySelector(@Nullable final DomNode instance, final String selectors) {
		return instance != null ? instance.querySelector(selectors) : null;
	}

	@Override
	public String[] getVoiceIds() {
		//
		return Util.toArray(Util.values(getVoices()), new String[] {});
		//
	}

	private Map<String, String> getVoices() {
		//
		if (voices == null) {
			//
			org.jsoup.nodes.Document document = null;
			//
			try {
				//
				document = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, url, URL::new, null),
						x -> Jsoup.parse(x, 0), null);
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			voices = Unit.with(Util.collect(
					Util.stream(ElementUtil.children(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.select(document, "select"), x -> IterableUtils.get(x, 0), null))),
					Collectors.toMap(x -> NodeUtil.attr(x, "value"), ElementUtil::text)));
			//
		} // if
			//
		return IValue0Util.getValue0(voices);
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public String getVoiceAttribute(@Nullable final String voiceId, final String attribute) {
		//
		final Map<?, String> map = getVoices();
		//
		if (Util.containsKey(map, voiceId)) {
			//
			if (PATTERN == null) {
				//
				final Iterable<Field> fs = Util
						.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(SpeechApiOnlineImpl.class)),
								f -> Objects.equals(Util.getName(f), "PATTERN")));
				//
				testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				testAndAccept(Objects::nonNull,
						testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
						f -> Narcissus.setStaticField(f,
								Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)：(\\w+)(\\s\\((\\w+)\\))?")));
				//
			} // if
				//
			final Matcher matcher = Util.matcher(PATTERN, Util.get(map, voiceId));
			//
			if (Util.matches(matcher)) {
				//
				final int groupCount = Util.groupCount(matcher);
				//
				if (Objects.equals(attribute, "gender") && groupCount > 0) {
					//
					return Util.group(matcher, 1);
					//
				} else if (Objects.equals(attribute, "name") && groupCount > 1) {
					//
					return Util.group(matcher, 2);
					//
				} else if (Objects.equals(attribute, "mood") && groupCount > 3) {
					//
					return Util.group(matcher, 4);
					//
				} // if
					//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
		}
	}

}