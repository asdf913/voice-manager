package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilderUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.JlptLevelListFactoryBean;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.BoundingBox;

import io.github.toolfactory.narcissus.Narcissus;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import net.miginfocom.swing.MigLayout;

public class JapanDictGui extends JPanel implements ActionListener, InitializingBean {

	private static final long serialVersionUID = -4598144203806679104L;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	@Note("Response Code")
	private JTextComponent tfResponseCode = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Romaji")
	private JTextComponent tfRomaji = null;

	@Note("Audio URL")
	private JTextComponent tfAudioUrl = null;

	private JTextComponent tfPitchAccent = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Hiragana")
	private AbstractButton btnCopyHiragana = null;

	@Note("Copy Romaji")
	private AbstractButton btnCopyRomaji = null;

	@Note("Copy Audio URL")
	private AbstractButton btnCopyAudioUrl = null;

	@Note("Download Audio")
	private AbstractButton btnDownloadAudio = null;

	@Note("Play Audio")
	private AbstractButton btnPlayAudio = null;

	@Note("Copy Pitch Accent Image")
	private AbstractButton btnCopyPitchAccentImage = null;

	private AbstractButton btnSavePitchAccentImage = null;

	private JComboBox<String> jcbJlptLevel = null;

	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private Map<String, String> userAgentMap = null;

	private transient ComboBoxModel<String> cbmBrowserType = null;

	@Note("Pitch Accent Image")
	private JLabel pitchAccentImage = null;

	private JLabel strokeImage = null;

	private transient BufferedImage pitchAccentBufferedImage = null;

	private Window window = null;

	private Duration storkeImageDuration, storkeImageSleepDuration = null;

	private JapanDictGui() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		add(this, new JLabel("Text"));
		//
		final String wrap = "wrap";
		//
		final String growx = "growx";
		//
		add(this, tfText = new JTextField(), String.format("%1$s,%2$s,span %3$s", growx, wrap, 3));
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Iterable<BrowserType> browserTypes = Util.toList(Util.map(
					Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(playwright)),
									Arrays::stream, null),
							x -> Objects.equals(Util.getReturnType(x), BrowserType.class)),
					x -> Util.cast(BrowserType.class, Narcissus.invokeMethod(playwright, x))));
			//
			BrowserType browserType = null;
			//
			final boolean runningInGitHubActions = equals(Strings.CI, "true", System.getenv("GITHUB_ACTIONS"));
			//
			for (int i = 0; i < IterableUtils.size(browserTypes); i++) {
				//
				if ((browserType = IterableUtils.get(browserTypes, i)) == null) {
					//
					continue;
					//
				} // if
					//
				try (final Browser browser = !runningInGitHubActions ? BrowserTypeUtil.launch(browserType) : null;
						final Page page = newPage(browser)) {
					//
					Util.put(userAgentMap = ObjectUtils.getIfNull(userAgentMap, LinkedHashMap::new), browserType.name(),
							Util.toString(evaluate(page, "window.navigator.userAgent")));
					//
				} // try
					//
			} // for
				//
		} // if
			//
		add(this, new JLabel("Browser"));
		//
		add(this,
				new JComboBox<>(cbmBrowserType = new DefaultComboBoxModel<>(
						toArray(Stream.concat(Stream.of((String) null), Util.stream(Util.keySet(userAgentMap))),
								String[]::new))),
				String.format("%1$s,span %2$s", wrap, 2));
		//
		add(this, new JLabel());
		//
		add(this, btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2));
		//
		add(this, new JLabel("Response Code"));
		//
		add(this, tfResponseCode = new JTextField(), String.format("%1$s,%2$s,span %3$s", growx, wrap, 3));
		//
		add(this, new JLabel("JLPT Level"));
		//
		final JlptLevelListFactoryBean jlptLevelListFactoryBean = new JlptLevelListFactoryBean();
		//
		jlptLevelListFactoryBean.setUrl("https://www.jlpt.jp/about/levelsummary.html");
		//
		add(this, jcbJlptLevel = new JComboBox<>(cbmJlptLevel = new DefaultComboBoxModel<>(toArray(
				Stream.concat(Stream.of((String) null), Util.stream(ObjectUtils
						.getIfNull(FactoryBeanUtil.getObject(jlptLevelListFactoryBean), Collections::emptySet))),
				String[]::new))), wrap);
		//
		add(this, new JLabel("Hiragana"));
		//
		add(this, tfHiragana = new JTextField(), String.format("%1$s,span %2$s", growx, 3));
		//
		add(this, btnCopyHiragana = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Romaji"));
		//
		add(this, tfRomaji = new JTextField(), String.format("%1$s,span %2$s", growx, 3));
		//
		add(this, btnCopyRomaji = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Audio URL"));
		//
		add(this, tfAudioUrl = new JTextField(), String.format("%1$s,span %2$s,wmax %3$spx", growx, 3, 159));
		//
		add(this, btnCopyAudioUrl = new JButton("Copy"));
		//
		add(this, btnDownloadAudio = new JButton("Download"));
		//
		add(this, btnPlayAudio = new JButton("Play"), wrap);
		//
		add(this, new JLabel("Pitch Accent"));
		//
		add(this, tfPitchAccent = new JTextField(), growx);
		//
		add(this, pitchAccentImage = new JLabel(), String.format("span %1$s", 2));
		//
		add(this, btnCopyPitchAccentImage = new JButton("Copy"));
		//
		add(this, btnSavePitchAccentImage = new JButton("Save"), wrap);
		//
		add(this, new JLabel("Stroke"));
		//
		add(this, strokeImage = new JLabel(), String.format("span %1$s", 5));
		//
		setEditable(false, tfResponseCode, tfHiragana, tfRomaji, tfAudioUrl, tfPitchAccent);
		//
		setEnabled(false, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl, btnDownloadAudio, btnPlayAudio,
				btnCopyPitchAccentImage, btnSavePitchAccentImage);
		//
		Util.forEach(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(JapanDictGui.class), Arrays::stream,
						null), x -> Util.isAssignableFrom(AbstractButton.class, Util.getType(x))),
				x -> Util.addActionListener(Util.cast(AbstractButton.class, Narcissus.getField(this, x)), this));
		//
	}

	private static boolean equals(@Nullable final Strings instance, final String str1, final String str2) {
		return instance != null && instance.equals(str1, str2);
	}

	@Nullable
	private static Object evaluate(@Nullable final Page instance, final String expression) {
		return instance != null ? instance.evaluate(expression) : null;
	}

	@Nullable
	private static Page newPage(@Nullable final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Stream<T> instance, final IntFunction<T[]> function) {
		return instance != null && Boolean.logicalOr(function != null, Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(function)
				: null;
	}

	private static void setEnabled(final boolean enabled, final Component a, final Component b,
			@Nullable final Component... cs) {
		//
		Util.setEnabled(a, enabled);
		//
		Util.setEnabled(b, enabled);
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			Util.setEnabled(ArrayUtils.get(cs, i), enabled);
			//
		} // for
			//
	}

	private static void setEditable(final boolean editable, @Nullable final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = ArrayUtils.get(jtcs, i)) == null) {
				//
				continue;
				//
			} // if
				//
			jtc.setEditable(editable);
			//
		} // for
			//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			setText(null, tfResponseCode, tfHiragana, tfRomaji, tfAudioUrl, tfPitchAccent);
			//
			setEnabled(false, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl, btnDownloadAudio, btnPlayAudio,
					btnCopyPitchAccentImage, btnSavePitchAccentImage);
			//
			Util.setSelectedItem(cbmJlptLevel, "");
			//
			Util.forEach(Stream.of(pitchAccentImage, strokeImage), x -> Util.setIcon(x, null));
			//
			pitchAccentBufferedImage = null;
			//
			final URIBuilder uriBuilder = new URIBuilder();
			//
			final String scheme = "https";
			//
			uriBuilder.setScheme(scheme);
			//
			uriBuilder.setHost("www.japandict.com");
			//
			final String text = Util.getText(tfText);
			//
			uriBuilder.setPath(text);
			//
			Document document = null;
			//
			InputStream is = null;
			//
			URI uri = null;
			//
			final IH ih = new IH();
			//
			final BooleanSupplier booleanSupplier = Reflection.newProxy(BooleanSupplier.class, ih);
			//
			try {
				//
				final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
						Util.openConnection(Util.toURL(uri = URIBuilderUtil.build(uriBuilder))));
				//
				setRequestProperty(httpURLConnection, "User-Agent", getUserAgent());
				//
				if (httpURLConnection != null) {
					//
					final int responseCode = httpURLConnection.getResponseCode();
					//
					Util.setText(tfResponseCode, Integer.toString(responseCode));
					//
					final boolean success = HttpStatus.isSuccess(responseCode);
					//
					ih.booleanValue = Boolean.valueOf(success);
					//
					document = testAndApply(x -> Util.and(x != null, !isTestMode()),
							is = testAndApply(x -> success, httpURLConnection, x -> getInputStream(x), null),
							x -> Jsoup.parse(x, "utf-8", ""), null);
					//
				} // if
					//
			} catch (final Exception e) {
				//
				TaskDialogs.showException(e);
				//
			} finally {
				//
				IOUtils.closeQuietly(is);
				//
			} // if
				//
				// JLPT
				//
			setJcbJlptLevel(getJlptLevelIndices(cbmJlptLevel,
					ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.select(document, "span.badge[title^='#jlpt'].me-1"),
							x -> IterableUtils.get(x, 0), null))));
			//
			// Hiragana
			//
			final Pattern patten = Pattern.compile("^\\p{InHiragana}+$");
			//
			testAndAccept(x -> !IterableUtils.isEmpty(x),
					Util.toList(
							Util.map(
									Util.filter(
											NodeUtil.nodeStream(testAndApply(x -> IterableUtils.size(x) > 0,
													testAndApply(x -> IterableUtils.size(x) > 0,
															ElementUtil.select(document,
																	".d-inline-block.align-middle.p-2"),
															x -> IterableUtils.get(x, 0), null),
													x -> IterableUtils.get(x, 0), null)),
											x -> Util.matches(Util.matcher(patten, Util.toString(x)))),
									Util::toString)),
					x -> Util.setText(tfHiragana, String.join("", x))
					//
					, x -> Util.matches(Util.matcher(patten, x)), text, x -> Util.setText(tfHiragana, x));
			//
			final boolean isNotBlank = StringUtils.isNotBlank(Util.getText(tfHiragana));
			//
			ih.booleanValue = ih.booleanValue != null
					? Boolean.valueOf(Boolean.logicalAnd(ih.booleanValue.booleanValue(), isNotBlank))
					: Boolean.valueOf(isNotBlank);
			//
			setEnabled(getAsBoolean(booleanSupplier), btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl, btnDownloadAudio,
					btnPlayAudio);
			//
			try {
				//
				Util.setText(tfAudioUrl,
						getAudioUrl(
								scheme, Strings.CS, Util
										.cast(Iterable.class,
												ObjectMapperUtil.readValue(new ObjectMapper(),
														NodeUtil.attr(
																testAndApply(x -> IterableUtils.size(x) > 0,
																		ElementUtil.select(document,
																				".d-inline-block.align-middle.p-2 a"),
																		x -> IterableUtils.get(x, 0), null),
																"data-reading"),
														Object.class))));
				//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogs.showException(e);
				//
			} // try
				//
				// romaji
				//
			Util.setText(tfRomaji, StringUtils.trim(ElementUtil.text(testAndApply(x -> IterableUtils.size(x) > 0,
					ElementUtil.select(document, ".xxsmall"), x -> IterableUtils.get(x, 0), null))));
			//
			// Pitch Accent
			//
			Util.setText(tfPitchAccent,
					ElementUtil
							.text(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil.select(
											testAndApply(Objects::nonNull,
													NodeUtil.attr(
															testAndApply(x -> IterableUtils.size(x) == 1,
																	ElementUtil.select(document, "[data-bs-content]"),
																	x -> IterableUtils.get(x, 0), null),
															"data-bs-content"),
													x -> Jsoup.parse(x, ""), null),
											"p span[class='h5']"),
									x -> IterableUtils.get(x, 0), null)));
			//
			// Pitch Accent Image
			//
			final Iterable<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Playwright.class), Arrays::stream, null),
					x -> Objects.equals(Util.getName(x), Util.getSelectedItem(cbmBrowserType))));
			//
			testAndRun(IterableUtils.size(ms) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			try (final Playwright playwright = Playwright.create();
					final Browser browser = BrowserTypeUtil.launch(ObjectUtils.getIfNull(
							Util.cast(BrowserType.class, testAndApply(Objects::nonNull,
									testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0),
											null),
									x -> Narcissus.invokeMethod(playwright, x), null)),
							() -> chromium(playwright)));
					final Page page = newPage(browser)) {
				//
				final boolean isSuccess = isSuccess(PageUtil.navigate(page, Util.toString(uri)));
				//
				final boolean hasPitchAccentImage = IterableUtils
						.size(ElementUtil.select(document, ".d-flex.justify-content-between.align-items-center")) == 1;
				//
				setEnabled(hasPitchAccentImage, btnCopyPitchAccentImage, btnSavePitchAccentImage);
				//
				final BoundingBox boundingBox = testAndGet(Util.and(isSuccess, !isTestMode(), hasPitchAccentImage),
						() -> boundingBox(
								locator(page, ".d-flex.justify-content-between.align-items-center div:first-child")));
				//
				testAndAccept(
						x -> Boolean.logicalAnd(isSuccess,
								startsWith(Strings.CS,
										getMimeType(testAndApply(Objects::nonNull, x, new ContentInfoUtil()::findMatch,
												null)),
										"image/")),
						testAndApply(
								(a, b) -> Boolean.logicalAnd(!IterableUtils.isEmpty(ElementUtil.select(a, b)),
										hasPitchAccentImage),
								document, ".d-flex.justify-content-between.align-items-center",
								(a, b) -> screenshot(locator(page, b)), null),
						x -> {
							//
							try {
								//
								Util.setIcon(pitchAccentImage,
										new ImageIcon(pitchAccentBufferedImage = chopImage(x, boundingBox)));
								//
							} catch (final IOException e) {
								//
								TaskDialogs.showException(e);
								//
							} // try
								//
						});
				//
				// Stroke Image
				//
				try {
					//
					Util.setIcon(strokeImage, testAndApply(Objects::nonNull, chopImage(getStrokeImage(this, page)),
							ImageIcon::new, null));
					//
				} catch (final IOException | InterruptedException e) {
					//
					TaskDialogs.showException(e);
					//
				} // try
					//
				pack(window);
				//
			} // try
				//
		} // if
			//
		final Iterable<BiPredicate<JapanDictGui, Object>> predicates = Arrays.asList(JapanDictGui::actionPerformed1,
				JapanDictGui::actionPerformed2);
		//
		for (int i = 0; i < IterableUtils.size(predicates); i++) {
			//
			if (Util.test(IterableUtils.get(predicates, i), this, source)) {
				//
				return;
				//
			} // if
				//
		} // for
			//
	}

	private static BufferedImage getStrokeImage(final JapanDictGui instance, final Page page)
			throws IOException, InterruptedException {
		//
		click(testAndApply(x -> IterableUtils.size(x) == 1,
				querySelectorAll(page, ".dmak-play.btn.btn-primary.btn-circle.px-5"), x -> IterableUtils.get(x, 0),
				null));
		//
		if ((testAndApply(x -> IterableUtils.size(x) == 1, querySelectorAll(page, "div.card-body div.dmak"),
				x -> IterableUtils.get(x, 0), null)) != null) {
			//
			final long currentTimeMillis = System.currentTimeMillis();
			//
			BufferedImage before = null, after = null;
			//
			while (System.currentTimeMillis() - currentTimeMillis < Math
					.max(toMillis(instance != null ? instance.storkeImageDuration : null, 20000), 0)) {
				//
				if (before == null) {
					//
					before = toBufferedImage(screenshot(locator(page, "div.card-body div.dmak")));
					//
				} else {
					//
					if (Objects.equals(getImageComparisonState(new ImageComparison(before,
							after = toBufferedImage(screenshot(locator(page, "div.card-body div.dmak"))))
							.compareImages()), ImageComparisonState.MATCH)) {
						//
						break;
						//
					} // if
						//
					before = after;
					//
				} // if
					//
				testAndAccept(x -> x >= 0,
						Math.max(toMillis(instance != null ? instance.storkeImageSleepDuration : null, 100), 0),
						Thread::sleep);
				//
			} // while
				//
			return after;
			//
		} // if
			//
		return null;
		//
	}

	private static <E extends Throwable> void testAndAccept(final LongPredicate predicate, final long l,
			final FailableLongConsumer<E> consumer) throws E {
		if (predicate != null && predicate.test(l) && consumer != null) {
			consumer.accept(l);
		}
	}

	private static long toMillis(@Nullable final Duration instance, final long defaultValue) {
		return instance != null ? instance.toMillis() : defaultValue;
	}

	private static void click(@Nullable final ElementHandle instance) {
		if (instance != null) {
			instance.click();
		}
	}

	@Nullable
	private static ImageComparisonState getImageComparisonState(@Nullable final ImageComparisonResult instance) {
		return instance != null ? instance.getImageComparisonState() : null;
	}

	private static BufferedImage toBufferedImage(@Nullable final byte[] bs) throws IOException, RuntimeException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			return testAndApply(Objects::nonNull, is, ImageIO::read, null);
			//
		} // try
			//
	}

	@Nullable
	private static List<ElementHandle> querySelectorAll(@Nullable final Page instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	private static boolean getAsBoolean(@Nullable final BooleanSupplier instance) {
		return instance != null && instance.getAsBoolean();
	}

	private static <A, B> void testAndAccept(final Predicate<A> predicateA, final A a, final Consumer<A> consumerA,
			final Predicate<B> predicateB, final B b, final Consumer<B> consumerB) {
		//
		if (Util.test(predicateA, a)) {
			//
			Util.accept(consumerA, a);
			//
		} else if (Util.test(predicateB, b)) {
			//
			Util.accept(consumerB, b);
			//
		} // if
			//
	}

	private static BufferedImage chopImage(@Nullable final BufferedImage bufferedImage) throws IOException {
		//
		if (bufferedImage == null) {
			//
			return bufferedImage;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(bufferedImage))),
						x -> Objects.equals(Util.getName(x), "raster")));
		//
		testAndRun(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final boolean condition = testAndApply(Objects::nonNull,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
				x -> Narcissus.getField(bufferedImage, x), null) != null;
		//
		final int width = Util.intValue(condition ? bufferedImage.getWidth() : null, 0);
		//
		Integer first = null;
		//
		Integer integerX = null;
		//
		for (int x = Util.intValue(width, 0) - 1; x >= 0; x--) {
			//
			if (integerX != null) {
				//
				break;
				//
			} // if
				//
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				//
				if (first == null) {
					//
					first = Integer.valueOf(bufferedImage.getRGB(x, y));
					//
				} else if (first.intValue() != bufferedImage.getRGB(x, y)) {
					//
					integerX = Integer.valueOf(x);
					//
				} // if
					//
			} // for
				//
		} // for
			//
		final int height = Util.intValue(condition ? bufferedImage.getHeight() : null, 0);
		//
		return condition ? bufferedImage.getSubimage(0, 0, Math.min(Util.intValue(integerX, width), width), height)
				: null;
		//
	}

	private static BufferedImage chopImage(final byte[] bs, @Nullable final BoundingBox boundingBox)
			throws IOException {
		//
		BufferedImage bufferedImage = null;
		//
		if ((bufferedImage = toBufferedImage(bs)) != null && boundingBox != null && boundingBox.width > 0) {
			//
			Integer first = null;
			//
			Integer integerY = null;
			//
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				//
				if (integerY != null) {
					//
					break;
					//
				} // if
					//
				for (int x = 0; x < bufferedImage.getWidth(); x++) {
					//
					if (first == null) {
						//
						first = Integer.valueOf(bufferedImage.getRGB(x, y));
						//
					} else if (first.intValue() != bufferedImage.getRGB(x, y)) {
						//
						integerY = Integer.valueOf(y);
						//
					} // if
						//
				} // for
					//
			} // for
				//
			final int intY = Util.intValue(integerY, 0);
			//
			bufferedImage = bufferedImage.getSubimage(0, intY, (int) boundingBox.width,
					bufferedImage.getHeight() - intY);
			//
		} // if
			//
		return bufferedImage;
		//
	}

	@Nullable
	private static BoundingBox boundingBox(@Nullable final Locator instance) {
		return instance != null ? instance.boundingBox() : null;
	}

	private void setJcbJlptLevel(@Nullable final int[] ints) {
		//
		final int length = ints != null ? ints.length : 0;
		//
		testAndRun(length > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		if (length == 1) {
			//
			Util.setSelectedIndex(jcbJlptLevel, ints[0]);
			//
		} // if
			//
	}

	private static boolean isSuccess(@Nullable final Response instance) {
		return instance != null && HttpStatus.isSuccess(instance.status());
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static byte[] screenshot(@Nullable final Locator instance) {
		return instance != null ? instance.screenshot() : null;
	}

	@Nullable
	private static Locator locator(@Nullable final Page instance, final String selector) {
		return instance != null ? instance.locator(selector) : null;
	}

	@Nullable
	private static BrowserType chromium(@Nullable final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	@Nullable
	private static int[] getJlptLevelIndices(final ComboBoxModel<String> cbm, final String text) {
		//
		int[] ints = null;
		//
		for (int i = 0; i < Util.getSize(cbm); i++) {
			//
			if (StringUtils.isNotBlank(testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null),
					Util.getElementAt(cbm, i), text, com.google.common.base.Strings::commonSuffix, null))) {
				//
				ints = ArrayUtils.add(ints, i);
				//
			} // if
				//
		} // for
			//
		return ints;
		//
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	private static class IH implements InvocationHandler {

		private Image image = null;

		private Boolean booleanValue = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Transferable) {
				//
				if (Objects.equals(name, "getTransferDataFlavors")) {
					//
					return new DataFlavor[] { DataFlavor.imageFlavor };
					//
				} else if (Objects.equals(name, "getTransferData")) {
					//
					return image;
					//
				} // if
					//
			} else if (proxy instanceof BooleanSupplier && Objects.equals(name, "getAsBoolean")) {
				//
				return booleanValue;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static boolean actionPerformed1(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnCopyHiragana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfHiragana)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyRomaji)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfRomaji)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyAudioUrl)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfAudioUrl)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyPitchAccentImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = instance.pitchAccentBufferedImage;
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					Reflection.newProxy(Transferable.class, ih), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnSavePitchAccentImage)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText), StringBuilder::new,
					null);
			//
			final String format = "png";
			//
			append(append(sb, '.'), format);
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
			//
			if (Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), instance.pitchAccentBufferedImage != null)
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					ImageIO.write(instance.pitchAccentBufferedImage, format, jfc.getSelectedFile());
					//
				} catch (final IOException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
			} // if
				//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed2(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnDownloadAudio)) {
			//
			try {
				//
				final byte[] bs = download(Util.getText(instance.tfAudioUrl), instance.getUserAgent());
				//
				final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText),
						StringBuilder::new, null);
				//
				testAndAccept(
						x -> Objects.equals(getMessage(
								testAndApply(Objects::nonNull, x, y -> new ContentInfoUtil().findMatch(y), null)),
								"Audio file with ID3 version 2.4, MP3 encoding"),
						bs, x -> append(append(sb, '.'), "mp3"));
				//
				final JFileChooser jfc = new JFileChooser();
				//
				jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
				//
				if (Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), bs != null)
						&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					//
					FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), bs);
					//
				} // if
					//
			} catch (final URISyntaxException | IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnPlayAudio)) {
			//
			byte[] bs = null;
			//
			try (final InputStream is = testAndApply(Objects::nonNull,
					bs = download(Util.getText(instance.tfAudioUrl), instance.getUserAgent()),
					ByteArrayInputStream::new, null)) {
				//
				testAndAccept(
						x -> Objects.equals(getMessage(
								testAndApply(Objects::nonNull, x, y -> new ContentInfoUtil().findMatch(y), null)),
								"Audio file with ID3 version 2.4, MP3 encoding"),
						bs, x -> PlayerUtil.play(testAndApply(Objects::nonNull, is, Player::new, null)));
				//
			} catch (final URISyntaxException | IOException | JavaLayerException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private String getUserAgent() {
		//
		return Objects.toString(
				testAndApply(Util::containsKey, userAgentMap, Util.getSelectedItem(cbmBrowserType), Util::get, null),
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
		//
	}

	private static byte[] download(final String url, final String userAgent) throws IOException, URISyntaxException {
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class, Util.openConnection(Util.toURL(
				testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x), url, URI::new, null))));
		//
		setRequestProperty(httpURLConnection, "User-Agent", userAgent);
		//
		try (final InputStream is = Util.getInputStream(httpURLConnection)) {
			//
			return testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
		} // try
			//
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static void testAndRun(final boolean condition, final Runnable runnable) {
		if (condition) {
			Util.run(runnable);
		} // if
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static String getAudioUrl(final String scheme, final Strings strings,
			@Nullable final Iterable<?> iterable) {
		//
		StringBuilder sb = null;
		//
		String s = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			testAndAccept(x -> StringUtils.length(x) == StringUtils.length(scheme),
					sb = ObjectUtils.getIfNull(sb, () -> new StringBuilder(StringUtils.defaultString(scheme))),
					x -> append(x, ':'));
			//
			if (startsWith(strings, s = Util.toString(IterableUtils.get(iterable, i)), "//")) {
				//
				append(append(sb, s), "/read?outputFormat=mp3");
				//
				continue;
				//
			} // if
				//
			append(sb, '&');
			//
			if (NumberUtils.isParsable(s)) {
				//
				append(sb, String.join("=", "vid", s));
				//
			} else if (isXml(s)) {
				//
				append(sb, String.join("=", "text", URLEncoder.encode(s, StandardCharsets.UTF_8)));
				//
			} else if (StringUtils.countMatches(s, '.') == 2) {
				//
				append(sb, String.join("=", "jwt", s));
				//
			} // if
				//
		} // for
			//
		return Util.toString(sb);
		//
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final char c) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "value")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && instance != null && Narcissus.getField(instance, f) != null ? instance.append(c) : instance;
		//
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final String s) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "value")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && instance != null && Narcissus.getField(instance, f) != null ? instance.append(s) : null;
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> instance, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(instance, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	@Nullable
	private static InputStream getInputStream(@Nullable final URLConnection instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

	private static void setRequestProperty(@Nullable final URLConnection instance, final String key,
			final String value) {
		if (instance != null) {
			instance.setRequestProperty(key, value);
		}
	}

	private static void setText(@Nullable final String text, @Nullable final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = ArrayUtils.get(jtcs, i)) == null) {
				//
				continue;
				//
			} // if
				//
			jtc.setText(text);
			//
		} // for
			//
	}

	private static boolean isXml(final String string) {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, Util.getBytes(string), ByteArrayInputStream::new,
				null)) {
			//
			return parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), is) != null;
			//
		} catch (final IOException | ParserConfigurationException | SAXException e) {
			//
			return false;
			//
		} // try
			//
	}

	@Nullable
	private static DocumentBuilder newDocumentBuilder(@Nullable final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	@Nullable
	private static org.w3c.dom.Document parse(@Nullable final DocumentBuilder instance, @Nullable final InputStream is)
			throws SAXException, IOException {
		//
		if (Objects.equals("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderImpl",
				Util.getName(Util.getClass(instance))) && is == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.parse(is) : null;
		//
	}

	private static boolean startsWith(@Nullable final Strings instance, final String string, final String prefix) {
		return instance != null && instance.startsWith(string, prefix);
	}

	private static <T, R, E extends Exception> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
				JFrame::new);
		//
		setDefaultCloseOperation(jFrame, WindowConstants.EXIT_ON_CLOSE);
		// ,
		final JapanDictGui instance = new JapanDictGui();
		//
		final Map<?, ?> properties = System.getProperties();
		//
		testAndAccept(Util::containsKey, properties,
				"org.springframework.context.support.JapanDictGui.storkeImageDuration", (a, b) -> {
					instance.storkeImageDuration = toDuration(Util.get(a, b));
				});
		//
		testAndAccept(Util::containsKey, properties,
				"org.springframework.context.support.JapanDictGui.storkeImageSleepDuration", (a, b) -> {
					instance.storkeImageSleepDuration = toDuration(Util.get(a, b));
				});
		//
		instance.setLayout(new MigLayout());
		//
		instance.afterPropertiesSet();
		//
		add(jFrame, instance);
		//
		pack(instance.window = jFrame);
		//
		setVisible(jFrame, true);
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	@Nullable
	private static Duration toDuration(@Nullable final Object object) {
		//
		Duration value = null;
		//
		if (object == null) {
			//
			value = null;
			//
		} else if (object instanceof Duration duration) {
			//
			value = duration;
			//
		} else if (object instanceof Number number) {
			//
			value = Duration.ofMillis(Util.intValue(number, 0));
			//
		} else if (object instanceof CharSequence) {
			//
			final String string = Util.toString(object);
			//
			DateTimeParseException dpe = null;
			//
			try {
				//
				return parse(string);
				//
			} catch (final DateTimeParseException e) {
				//
				dpe = e;
				//
			} // try
				//
			final Number number = testAndApply(NumberUtils::isParsable, string,
					x -> Integer.valueOf(NumberUtils.toInt(x)), null);
			//
			if (number != null) {
				//
				return toDuration(number);
				//
			} // if
				//
			throw dpe;
			//
		} else if (object instanceof char[] cs) {
			//
			return toDuration(testAndApply(Objects::nonNull, cs, String::new, null));
			//
		} // if
			//
		return value;
		//
	}

	@Nullable
	private static Duration parse(final CharSequence text) {
		return StringUtils.isNotEmpty(text) ? Duration.parse(text) : null;
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void setVisible(@Nullable final Component instance, final boolean visible) {
		if (instance != null) {
			instance.setVisible(visible);
		}
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util.collect(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "objectLock")), Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) != null) {
			//
			instance.pack();
			//
		} // if
			//
	}

	private static void add(@Nullable final Container instance, final Component component) {
		//
		final Field f = getFieldByName(instance, "component");
		//
		if (f != null && Narcissus.getField(instance, f) != null) {
			//
			instance.add(component);
			//
		} // if
			//
	}

	private static Field getFieldByName(@Nullable final Object instance, final String name) {
		//
		final Iterable<Field> fs = Util.collect(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), name)), Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static void add(final Container instance, final Component component, final Object object) {
		//
		final Field f = getFieldByName(instance, "component");
		//
		if (f != null && Narcissus.getField(instance, f) != null) {
			//
			instance.add(component, object);
			//
		} // if
			//
	}

	private static void setDefaultCloseOperation(@Nullable final JFrame instance, final int defaultCloseOperation) {
		if (instance != null) {
			instance.setDefaultCloseOperation(defaultCloseOperation);
		}
	}

	@Nullable
	private static <T, E extends Throwable> T testAndGet(final boolean condition, final FailableSupplier<T, E> supplier)
			throws E {
		return condition ? FailableSupplierUtil.get(supplier) : null;
	}

}