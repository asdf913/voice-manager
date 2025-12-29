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
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
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
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class JapanDictGui extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4598144203806679104L;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Response Code")
	private JTextComponent tfResponseCode = null;

	private JTextComponent tfAudioUrl = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Hiragana")
	private AbstractButton btnCopyHiragana = null;

	private AbstractButton btnCopyAudioUrl = null;

	private JapanDictGui() {
		//
		setLayout(new MigLayout());
		//
		add(new JLabel("Text"));
		//
		final String wrap = "wrap";
		//
		final String growx = "growx";
		//
		add(tfText = new JTextField(), String.format("%1$s,%2$s", growx, wrap));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		add(new JLabel("Response Code"));
		//
		add(tfResponseCode = new JTextField(), String.format("%1$s,%2$s", growx, wrap));
		//
		add(new JLabel("Hiragana"));
		//
		add(tfHiragana = new JTextField(), growx);
		//
		add(btnCopyHiragana = new JButton("Copy"), wrap);
		//
		add(new JLabel("Audio URL"));
		//
		add(tfAudioUrl = new JTextField(), growx);
		//
		add(btnCopyAudioUrl = new JButton("Copy"), wrap);
		//
		setEditable(false, tfHiragana, tfResponseCode, tfAudioUrl);
		//
		setEnabled(false, btnCopyHiragana, btnCopyAudioUrl);
		//
		addActionListener(this, btnExecute, btnCopyHiragana, btnCopyAudioUrl);
		//
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
			setText(null, tfHiragana, tfResponseCode, tfAudioUrl);
			//
			setEnabled(false, btnCopyHiragana, btnCopyAudioUrl);
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
				setRequestProperty(httpURLConnection, "User-Agent",
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
				//
				if (httpURLConnection != null) {
					//
					final int responseCode = httpURLConnection.getResponseCode();
					//
					Util.setText(tfResponseCode, Integer.toString(responseCode));
					//
					final boolean success = HttpStatus.isSuccess(responseCode);
					//
					setEnabled(success, btnCopyHiragana, btnCopyAudioUrl);
					//
					if (success) {
						//
						document = testAndApply(x -> Boolean.logicalAnd(x != null, !isTestMode()),
								is = getInputStream(httpURLConnection), x -> Jsoup.parse(x, "utf-8", ""), null);
						//
					} // if
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
			final Element element = testAndApply(x -> IterableUtils.size(x) > 0,
					ElementUtil.select(document, ".d-inline-block.align-middle.p-2 a"), x -> IterableUtils.get(x, 0),
					null);
			//
			try {
				//
				Util.setText(tfAudioUrl, getAudioUrl(scheme, Strings.CS, Util.cast(Iterable.class, ObjectMapperUtil
						.readValue(new ObjectMapper(), NodeUtil.attr(element, "data-reading"), Object.class))));
				//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogs.showException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnCopyHiragana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(tfHiragana)), null));
			//
		} else if (Objects.equals(source, btnCopyAudioUrl)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(tfAudioUrl)), null));
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

	public static void main(final String[] args) {
		//
		final JFrame jFrame = testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
				JFrame::new);
		//
		setDefaultCloseOperation(jFrame, WindowConstants.EXIT_ON_CLOSE);
		// ,
		add(jFrame, new JapanDictGui());
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
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util.collect(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "component")), Collectors.toList());
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
			instance.add(component);
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