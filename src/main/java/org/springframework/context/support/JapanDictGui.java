package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
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
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
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

	private JTextComponent tfAudioUrl = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Hiragana")
	private AbstractButton btnCopyHiragana = null;

	@Note("Copy Romaji")
	private AbstractButton btnCopyRomaji = null;

	private AbstractButton btnCopyAudioUrl = null;

	private JComboBox<String> jcbJlptLevel = null;

	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private Map<String, String> userAgentMap = null;

	private ComboBoxModel<String> cbmBrowserType = null;

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
		add(this, tfText = new JTextField(), String.format("%1$s,%2$s", growx, wrap));
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
				wrap);
		//
		add(this, new JLabel());
		//
		add(this, btnExecute = new JButton("Execute"), wrap);
		//
		add(this, new JLabel("Response Code"));
		//
		add(this, tfResponseCode = new JTextField(), String.format("%1$s,%2$s", growx, wrap));
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
		add(this, tfHiragana = new JTextField(), growx);
		//
		add(this, btnCopyHiragana = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Romaji"));
		//
		add(this, tfRomaji = new JTextField(), growx);
		//
		add(this, btnCopyRomaji = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Audio URL"));
		//
		add(this, tfAudioUrl = new JTextField(), growx);
		//
		add(this, btnCopyAudioUrl = new JButton("Copy"), wrap);
		//
		setEditable(false, tfResponseCode, tfHiragana, tfRomaji, tfAudioUrl);
		//
		setEnabled(false, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl);
		//
		addActionListener(this, btnExecute, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl);
		//
	}

	private static boolean equals(final Strings instance, final String str1, final String str2) {
		return instance != null && instance.equals(str1, str2);
	}

	private static Object evaluate(final Page instance, final String expression) {
		return instance != null ? instance.evaluate(expression) : null;
	}

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

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
			//
			Util.addActionListener(ArrayUtils.get(abs, i), actionListener);
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
			setText(null, tfResponseCode, tfHiragana, tfRomaji, tfAudioUrl);
			//
			setEnabled(false, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl);
			//
			Util.setSelectedItem(cbmJlptLevel, "");
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
			try {
				//
				final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
						Util.openConnection(Util.toURL(URIBuilderUtil.build(uriBuilder))));
				//
				setRequestProperty(httpURLConnection, "User-Agent", Objects.toString(
						testAndApply((a, b) -> Util.containsKey(a, b), userAgentMap,
								Util.getSelectedItem(cbmBrowserType), (a, b) -> Util.get(a, b), null),
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36"));
				//
				if (httpURLConnection != null) {
					//
					final int responseCode = httpURLConnection.getResponseCode();
					//
					Util.setText(tfResponseCode, Integer.toString(responseCode));
					//
					final boolean success = HttpStatus.isSuccess(responseCode);
					//
					setEnabled(success, btnCopyHiragana, btnCopyRomaji, btnCopyAudioUrl);
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
			final int[] ints = getJlptLevelIndices(cbmJlptLevel,
					ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.select(document, "span.badge[title^='#jlpt'].me-1"),
							x -> IterableUtils.get(x, 0), null)));
			//
			if (ints != null) {
				//
				final int length = ints.length;
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
			} // if
				//
			final Pattern patten = Pattern.compile("^\\p{InHiragana}+$");
			//
			final Iterable<String> ss = Util.toList(Util.map(Util.filter(
					NodeUtil.nodeStream(testAndApply(x -> IterableUtils.size(x) > 0,
							testAndApply(x -> IterableUtils.size(x) > 0,
									ElementUtil.select(document, ".d-inline-block.align-middle.p-2"),
									x -> IterableUtils.get(x, 0), null),
							x -> IterableUtils.get(x, 0), null)),
					x -> Util.matches(Util.matcher(patten, Util.toString(x)))), Util::toString));
			//
			if (!IterableUtils.isEmpty(ss)) {
				//
				Util.setText(tfHiragana, String.join("", ss));
				//
			} else if (Util.matches(Util.matcher(patten, text))) {
				//
				Util.setText(tfHiragana, text);
				//
			} // if
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
		} // if
			//
		actionPerformed1(this, source);
		//
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

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	private static void actionPerformed1(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnCopyHiragana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfHiragana)), null));
			//
		} else if (Objects.equals(source, instance.btnCopyRomaji)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfRomaji)), null));
			//
		} else if (Objects.equals(source, instance.btnCopyAudioUrl)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfAudioUrl)), null));
			//
		} // if
			//
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
				sb.append(s);
				//
				sb.append("/read?outputFormat=mp3");
				//
				continue;
				//
			} // if
				//
			sb.append('&');
			//
			if (NumberUtils.isParsable(s)) {
				//
				sb.append(String.join("=", "vid", s));
				//
			} else if (isXml(s)) {
				//
				sb.append(String.join("=", "text", URLEncoder.encode(s, StandardCharsets.UTF_8)));
				//
			} else if (StringUtils.countMatches(s, '.') == 2) {
				//
				sb.append(String.join("=", "jwt", s));
				//
			} // if
				//
		} // for
			//
		return Util.toString(sb);
		//
	}

	private static void append(@Nullable final StringBuilder instance, final char c) {
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
		if (f != null && instance != null && Narcissus.getField(instance, f) != null) {
			//
			instance.append(c);
			//
		} // if
			//
	}

	private static <T> void testAndAccept(final Predicate<T> instance, final T value, final Consumer<T> consumer) {
		if (Util.test(instance, value)) {
			Util.accept(consumer, value);
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
		instance.setLayout(new MigLayout());
		//
		instance.afterPropertiesSet();
		//
		add(jFrame, instance);
		//
		pack(jFrame);
		//
		setVisible(jFrame, true);
		//
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