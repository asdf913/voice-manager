package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilderUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.meeuw.functional.Predicates;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriConsumerUtil;
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
import com.microsoft.playwright.ElementHandleUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightUtil;
import com.microsoft.playwright.options.BoundingBox;

import io.github.toolfactory.narcissus.Narcissus;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import net.miginfocom.swing.MigLayout;

public class JapanDictGui extends JPanel implements ActionListener, InitializingBean, ListSelectionListener {

	private static final long serialVersionUID = -4598144203806679104L;

	private static final String VALUE = "value";

	private static final String USER_AGENT = "User-Agent";

	private static final String DATA_READING = "data-reading";

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Input {
	}

	@Note("Text")
	@Input
	private JTextComponent tfText = null;

	@Note("Response Code")
	private JTextComponent tfResponseCode = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Katakana")
	private JTextComponent tfKatakana = null;

	@Note("Romaji")
	private JTextComponent tfRomaji = null;

	@Note("Audio URL")
	private JTextComponent tfAudioUrl = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Execute {
	}

	@Note("Execute")
	@Execute
	private AbstractButton btnExecute = null;

	@Note("Copy Hiragana")
	private AbstractButton btnCopyHiragana = null;

	@Note("Copy Katakana")
	private AbstractButton btnCopyKatakana = null;

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

	@Note("Save Pitch Accent Image")
	private AbstractButton btnSavePitchAccentImage = null;

	@Note("Copy Stroke Image")
	private AbstractButton btnCopyStrokeImage = null;

	@Note("Save Stroke Image")
	private AbstractButton btnSaveStrokeImage = null;

	@Note("Copy Stroke With Number Image")
	private AbstractButton btnCopyStrokeWithNumberImage = null;

	private AbstractButton btnSaveStrokeWithNumberImage = null;

	private JComboBox<String> jcbJlptLevel = null;

	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private Map<String, String> userAgentMap = null;

	private transient ComboBoxModel<String> cbmBrowserType = null;

	@Note("Stroke Image")
	private JLabel strokeImage = null;

	private JLabel strokeWithNumberImage = null;

	@Note("Stroke Image")
	private transient BufferedImage strokeBufferedImage = null;

	private transient BufferedImage strokeWithNumberBufferedImage = null;

	private Window window = null;

	@Note("Stroke Image Duration")
	private Duration storkeImageDuration = null;

	private Duration storkeImageSleepDuration = null;

	private JTable jTable = null;

	private DefaultTableModel dtm = null;

	private transient ListSelectionModel lsm = null;

	private static class PitchAccent {

		private String type = null;

		private BufferedImage image = null;

	}

	private transient MutableComboBoxModel<PitchAccent> mcbmPitchAccent = null;

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
			final boolean runningInGitHubActions = StringsUtil.equals(Strings.CI, "true",
					System.getenv("GITHUB_ACTIONS"));
			//
			for (int i = 0; i < IterableUtils.size(browserTypes); i++) {
				//
				if ((browserType = IterableUtils.get(browserTypes, i)) == null) {
					//
					continue;
					//
				} // if
					//
				try (final Browser browser = testAndApply(Predicates.always(!runningInGitHubActions), browserType,
						BrowserTypeUtil::launch, null); final Page page = newPage(browser)) {
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
		add(this, new JLabel());
		//
		add(this, new JScrollPane(jTable = new JTable(dtm = new DefaultTableModel(
				new Object[] { "", "JLPT Level", "Hiragana", "Katakana", "Romaji", "Pitch Accent" }, 0) {

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}

		})), String.format("%1$s,wmin %2$s,span %3$s", wrap, 100, 6));
		//
		if ((lsm = jTable.getSelectionModel()) != null) {
			//
			lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//
			lsm.addListSelectionListener(this);
			//
		} // if
			//
		jTable.setDefaultRenderer(Object.class, createTableCellRenderer(jTable.getDefaultRenderer(Object.class)));
		//
		final Dimension preferredSize = Util.getPreferredSize(jTable);
		//
		setPreferredScrollableViewportSize(jTable,
				new Dimension((int) getWidth(preferredSize), (int) getHeight(preferredSize)));
		//
		add(this, new JLabel("JLPT Level"));
		//
		final MutableComboBoxModel<String> mcbmJlptLevel = new DefaultComboBoxModel<>();
		//
		mcbmJlptLevel.addElement(null);
		//
		add(this, jcbJlptLevel = new JComboBox<>(cbmJlptLevel = mcbmJlptLevel), wrap);
		//
		SwingUtilities.invokeLater(() -> {
			//
			final JlptLevelListFactoryBean jlptLevelListFactoryBean = new JlptLevelListFactoryBean();
			//
			jlptLevelListFactoryBean.setUrl("https://www.jlpt.jp/about/levelsummary.html");
			//
			try {
				//
				Util.forEach(Util.stream(ObjectUtils.getIfNull(FactoryBeanUtil.getObject(jlptLevelListFactoryBean),
						Collections::emptySet)), x -> Util.addElement(mcbmJlptLevel, x));
				//
			} catch (final Exception e) {
				//
				throw e instanceof RuntimeException runtimeException ? runtimeException : new RuntimeException(e);
				//
			} // try
				//
		});
		//
		add(this, new JLabel("Hiragana"));
		//
		add(this, tfHiragana = new JTextField(), String.format("%1$s,span %2$s", growx, 3));
		//
		add(this, btnCopyHiragana = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Katakana"));
		//
		add(this, tfKatakana = new JTextField(), String.format("%1$s,span %2$s", growx, 3));
		//
		add(this, btnCopyKatakana = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Romaji"));
		//
		add(this, tfRomaji = new JTextField(), String.format("%1$s,span %2$s", growx, 3));
		//
		add(this, btnCopyRomaji = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Audio URL"));
		//
		add(this, tfAudioUrl = new JTextField(), String.format("%1$s,span %2$s,wmax %3$spx", growx, 3, 150));
		//
		add(this, btnCopyAudioUrl = new JButton("Copy"));
		//
		add(this, btnDownloadAudio = new JButton("Download"));
		//
		add(this, btnPlayAudio = new JButton("Play"), wrap);
		//
		add(this, new JLabel("Pitch Accent"));
		//
		final JComboBox<PitchAccent> jcbPitchAccent = new JComboBox<>(mcbmPitchAccent = new DefaultComboBoxModel<>());
		//
		jcbPitchAccent.setRenderer(createPitchAccentListCellRenderer(jcbPitchAccent, jcbPitchAccent.getRenderer(),
				Util.getPreferredSize(jcbPitchAccent)));
		//
		add(this, jcbPitchAccent, String.format("span %1$s", 3));
		//
		add(this, btnCopyPitchAccentImage = new JButton("Copy"));
		//
		add(this, btnSavePitchAccentImage = new JButton("Save"), wrap);
		//
		add(this, new JLabel("Stroke"), String.format("span %1$s", 2));
		//
		add(this, strokeImage = new JLabel(), String.format("span %1$s", 6));
		//
		add(this, btnCopyStrokeImage = new JButton("Copy"), String.format("flowy,split %1$s", 2));
		//
		add(this, btnSaveStrokeImage = new JButton("Save"), String.format("%1$s,%2$s", growx, wrap));
		//
		add(this, new JLabel("Stroke with Number"), String.format("span %1$s", 2));
		//
		add(this, strokeWithNumberImage = new JLabel(), String.format("span %1$s", 6));
		//
		add(this, btnCopyStrokeWithNumberImage = new JButton("Copy"), String.format("flowy,split %1$s", 2));
		//
		add(this, btnSaveStrokeWithNumberImage = new JButton("Save"), String.format("%1$s,%2$s", growx, wrap));
		//
		Util.forEach(Util.map(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, JapanDictGui.class, FieldUtils::getAllFieldsList, null)),
				x -> Boolean.logicalAnd(Util.isAssignableFrom(JTextComponent.class, Util.getType(x)),
						!Util.isAnnotationPresent(x, Input.class))),
				x -> Util.cast(JTextComponent.class, Narcissus.getField(this, x))), x -> Util.setEditable(x, false));
		//
		Util.forEach(Util.map(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, JapanDictGui.class, FieldUtils::getAllFieldsList, null)),
				x -> Boolean.logicalAnd(Util.isAssignableFrom(AbstractButton.class, Util.getType(x)),
						!Util.isAnnotationPresent(x, Execute.class))),
				x -> Util.cast(AbstractButton.class, Narcissus.getField(this, x))), x -> Util.setEnabled(x, false));
		//
		Util.forEach(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(JapanDictGui.class), Arrays::stream,
						null), x -> Util.isAssignableFrom(AbstractButton.class, Util.getType(x))),
				x -> Util.addActionListener(Util.cast(AbstractButton.class, Narcissus.getField(this, x)), this));
		//
	}

	private static ListCellRenderer<PitchAccent> createPitchAccentListCellRenderer(final Component component,
			final ListCellRenderer<? super PitchAccent> lcr, final Dimension pd) {
		//
		return (list, value, index, isSelected, cellHasFocus) -> {
			//
			if (value != null && value.image != null) {
				//
				final JPanel panel = new JPanel();
				//
				panel.setLayout(new MigLayout());
				//
				add(panel, new JLabel(new ImageIcon(value.image)));
				//
				if (StringUtils.isNotBlank(value.type)) {
					//
					add(panel, new JLabel(String.format("(%1$s)", value.type)), "align right");
					//
				} // if
					//
				final Dimension pd2 = Util.getPreferredSize(panel);
				//
				if (pd != null && pd2 != null) {
					//
					setPreferredSize(component, new Dimension((int) pd.getWidth(), (int) pd2.getHeight()));
					//
				} // if
					//
				return panel;
				//
			} // if
				//
			return Util.getListCellRendererComponent(lcr, list, value, index, isSelected, cellHasFocus);
			//
		};
		//
	}

	private static void setPreferredSize(@Nullable final Component instance, final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
		}
	}

	private static TableCellRenderer createTableCellRenderer(final TableCellRenderer tcr) {
		//
		return (table, value, isSelected, hasFocus, row, column) -> {
			//
			final String columnName = getColumnName(table, column);
			//
			final JapanDictEntry entry = Util.cast(JapanDictEntry.class,
					ObjectUtils.getIfNull(value, () -> getValueAt(getModel(table), row, 0)));
			//
			final Component c = JapanDictGui.getTableCellRendererComponent(tcr, table, value, isSelected, hasFocus, row,
					column);
			//
			final JLabel jLabel = Util.cast(JLabel.class, c);
			//
			final Strings strings = Strings.CI;
			//
			final Iterable<Field> fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(entry), FieldUtils::getAllFieldsList, null)),
					x -> StringsUtil.equals(strings, Util.getName(x),
							StringsUtil.replace(strings, columnName, " ", ""))));
			//
			testAndRun(IterableUtils.size(fs) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
			//
			if (f != null && entry != null) {
				//
				Util.setText(jLabel, Util.toString(Narcissus.getField(entry, f)));
				//
			} else if (Objects.equals(columnName, "")) {
				//
				Util.setText(jLabel, entry != null ? entry.text : null);
				//
			} // if
				//
			return c;
			//
		};

	}

	private static void setPreferredScrollableViewportSize(@Nullable final JTable instance, final Dimension size) {
		if (instance != null) {
			instance.setPreferredScrollableViewportSize(size);
		}
	}

	@Nullable
	private static Component getTableCellRendererComponent(@Nullable final TableCellRenderer instance,
			final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row,
			final int column) {
		return instance != null
				? instance.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
				: null;
	}

	@Nullable
	private static TableModel getModel(@Nullable final JTable instance) {
		return instance != null ? instance.getModel() : null;
	}

	@Nullable
	private static Object getValueAt(@Nullable final TableModel instance, final int row, final int column) {
		return instance != null ? instance.getValueAt(row, column) : null;
	}

	@Nullable
	private static String getColumnName(@Nullable final JTable instance, final int column) {
		return instance != null && getModel(instance) != null ? instance.getColumnName(column) : null;
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

	private static class JapanDictEntry {

		@Note("ID")
		private String id = null;

		@Note("Text")
		private String text = null;

		@Note("JLPT Level")
		private String jlptLevel = null;

		@Note("Hiragana")
		private String hiragana = null;

		@Note("Katakana")
		private String katakana = null;

		@Note("Romaji")
		private String romaji = null;

		@Note("Audio URL")
		private String audioUrl = null;

		private String pageUrl = null;

		private Integer index = null;

		@Note("Stroke Image")
		private BufferedImage strokeImage = null;

		private BufferedImage strokeWithNumberImage = null;

		private Iterable<PitchAccent> pitchAccents = null;

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			reset();
			//
			Util.setText(tfResponseCode, "");
			//
			Util.forEach(IntStream.iterate(Util.getRowCount(dtm) - 1, i -> i >= 0, i -> i - 1),
					i -> Util.removeRow(dtm, i));
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
			String pageUrl = null;
			//
			try {
				//
				pageUrl = Util.toString(uri = URIBuilderUtil.build(uriBuilder));
				//
				final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
						Util.openConnection(Util.toURL(uri)));
				//
				setRequestProperty(httpURLConnection, USER_AGENT, getUserAgent());
				//
				if (httpURLConnection != null) {
					//
					final int responseCode = httpURLConnection.getResponseCode();
					//
					Util.setText(tfResponseCode, Integer.toString(responseCode));
					//
					final boolean success = HttpStatus.isSuccess(responseCode);
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
			final Iterable<Element> es1 = ElementUtil.select(document, ".container-fluid.bg-white.p-0");
			//
			Iterable<Element> es2 = null;
			//
			Element e1 = null;
			//
			Pattern patternHiragana = null, patternKatkana = null, p2 = null;
			//
			String id, h1, jlptLevel = null;
			//
			ObjectMapper objectMapper = null;
			//
			Map<?, ?> map = null;
			//
			final Iterable<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Map.class), Arrays::stream, null),
					x -> Boolean.logicalAnd(Objects.equals(Util.getName(x), "put"),
							Arrays.equals(Util.getParameterTypes(x), new Class<?>[] { Object.class, Object.class }))));
			//
			testAndRun(IterableUtils.size(ms) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final Method put = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
			//
			for (int i = 0; i < IterableUtils.size(es1); i++) {
				//
				if ((e1 = IterableUtils.get(es1, i)) == null) {
					//
					continue;
					//
				} // if
					//
				id = testAndApply(x -> and(x, Util::matches, y -> Util.groupCount(y) > 0),
						Util.matcher(p2 = ObjectUtils.getIfNull(p2, () -> Pattern.compile("^[^\\d]+(\\d+)$")),
								NodeUtil.attr(e1, "id")),
						x -> Util.group(x, 1), null);
				//
				h1 = ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1, ElementUtil.select(e1, "h1"),
						x -> IterableUtils.get(x, 0), null));
				//
				jlptLevel = getJlptLevel(cbmJlptLevel,
						ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
								ElementUtil.select(e1, "span.badge[title^='#jlpt'].me-1"), x -> IterableUtils.get(x, 0),
								null)));
				//
				es2 = ElementUtil.select(e1, "div[aria-labelledby^='modal-reading'] + ul li");
				//
				for (int j = 0; j < IterableUtils.size(es2); j++) {
					//
					clear(map = ObjectUtils.getIfNull(map, () -> Reflection.newProxy(Map.class, new IH())));
					//
					Narcissus.invokeMethod(map, put, "jlptLevel", jlptLevel);
					//
					Narcissus.invokeMethod(map, put, "text", h1);
					//
					Narcissus.invokeMethod(map, put, "pageUrl", pageUrl);
					//
					Narcissus.invokeMethod(map, put, "id", id);
					//
					Narcissus.invokeMethod(map, put, "scheme", scheme);
					//
					Util.addRow(dtm,
							new Object[] { getJapanDictEntry(IterableUtils.get(es2, j),
									patternHiragana = ObjectUtils.getIfNull(patternHiragana,
											() -> Pattern.compile("^\\p{InHiragana}+$")),
									patternKatkana = ObjectUtils.getIfNull(patternKatkana,
											() -> Pattern.compile("^\\p{InKatakana}+$")),
									objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new),
									Util.getRowCount(dtm), j, map) });
					//
				} // for
					//
			} // for
				//
			final Dimension preferredSize = Util.getPreferredSize(jTable);
			//
			setPreferredScrollableViewportSize(jTable, new Dimension((int) getWidth(preferredSize),
					(int) Math.min(Util.map(IntStream.range(0, Util.getRowCount(dtm)), x ->
					//
					Math.max(getRowHeight(jTable),
							Util.orElse(
									Util.max(
											Util.map(IntStream.range(0, getColumnCount(jTable)),
													column -> (int) getHeight(Util.getPreferredSize(prepareRenderer(
															jTable, getCellRenderer(jTable, x, column), x, column))))),
									0))
					//
					).sum(), getHeight(preferredSize))));
			//
			testAndRun(Boolean.logicalAnd(Util.getRowCount(dtm) == 1, getColumnCount(dtm) > 0),
					() -> setRowSelectionInterval(jTable, 0, 0));
			//
			pack(window);
			//
		} // if
			//
		final Iterable<BiPredicate<JapanDictGui, Object>> predicates = Arrays.asList(JapanDictGui::actionPerformed1,
				JapanDictGui::actionPerformed2, JapanDictGui::actionPerformed3, JapanDictGui::actionPerformed4);
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

	private static void setRowSelectionInterval(@Nullable final JTable instance, final int row, final int column) {
		if (instance != null && instance.getModel() != null) {
			instance.setRowSelectionInterval(row, column);
		}
	}

	private static int getColumnCount(@Nullable final TableModel instance) {
		return instance != null ? instance.getColumnCount() : 0;
	}

	@Nullable
	private static Component prepareRenderer(@Nullable final JTable instance,
			@Nullable final TableCellRenderer tableCellRenderer, final int row, final int column) {
		return instance != null && instance.getColumnModel() != null && tableCellRenderer != null
				? instance.prepareRenderer(tableCellRenderer, row, column)
				: null;
	}

	@Nullable
	private static TableCellRenderer getCellRenderer(@Nullable final JTable instance, final int row, final int column) {
		return instance != null && instance.getColumnModel() != null ? instance.getCellRenderer(row, column) : null;
	}

	private static int getColumnCount(@Nullable final JTable instance) {
		return instance != null && instance.getColumnModel() != null ? instance.getColumnCount() : 0;
	}

	private static void clear(@Nullable final Map<?, ?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static JapanDictEntry getJapanDictEntry(final Element e, final Pattern patternHiragana,
			final Pattern patternKatakana, final ObjectMapper objectMapper, final int index1, final int index2,
			final Map<?, ?> map) {
		//
		final JapanDictEntry entry = new JapanDictEntry();
		//
		final Element hiraganaOrKatakana = testAndApply(
				x -> IterableUtils.size(x) > 0, testAndApply(x -> IterableUtils.size(x) > 0,
						ElementUtil.select(e, ".d-inline-block.align-middle.p-2"), x -> IterableUtils.get(x, 0), null),
				x -> IterableUtils.get(x, 0), null);
		//
		entry.hiragana = testAndApply(Objects::nonNull,
				Util.toList(
						Util.map(
								Util.filter(NodeUtil.nodeStream(hiraganaOrKatakana),
										x -> Util.matches(Util.matcher(patternHiragana, Util.toString(x)))),
								Util::toString)),
				x -> String.join("", x), null);
		//
		entry.katakana = testAndApply(Objects::nonNull,
				Util.toList(
						Util.map(
								Util.filter(NodeUtil.nodeStream(hiraganaOrKatakana),
										x -> Util.matches(Util.matcher(patternKatakana, Util.toString(x)))),
								Util::toString)),
				x -> String.join("", x), null);
		//
		entry.jlptLevel = Util.toString(Util.get(map, "jlptLevel"));
		//
		// Romaji
		//
		final Iterable<Element> es = ElementUtil.select(e, ".xxsmall");
		//
		if (IterableUtils.size(es) == 1) {
			//
			entry.romaji = ElementUtil.text(IterableUtils.get(es, 0));
			//
		} else {
			//
			final Iterable<String> ss = Util.toList(Util.distinct(Util.map(
					testAndApply(Objects::nonNull, Util.spliterator(es), x -> StreamSupport.stream(x, false), null),
					ElementUtil::text)));
			//
			testAndRun(IterableUtils.size(ss) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			testAndAccept(x -> IterableUtils.size(x) == 1, ss, x -> entry.romaji = IterableUtils.get(x, 0));
			//
		} // if
			//
		entry.text = Util.toString(Util.get(map, "text"));
		//
		entry.index = Integer.valueOf(index1);
		//
		entry.index = Integer.valueOf(index2);
		//
		entry.pageUrl = Util.toString(Util.get(map, "pageUrl"));
		//
		entry.id = Util.toString(Util.get(map, "id"));
		//
		try {
			//
			entry.audioUrl = getAudioUrl(Util.toString(Util.get(map, "scheme")), Strings.CS,
					Util.cast(Iterable.class,
							ObjectMapperUtil.readValue(objectMapper,
									NodeUtil.attr(testAndApply(x -> IterableUtils.size(x) > 0,
											ElementUtil.select(e, ".d-inline-block.align-middle.p-2 a"),
											x -> IterableUtils.get(x, 0), null), DATA_READING),
									Object.class)));
			//
		} catch (final JsonProcessingException ex) {
			//
		} // try
			//
		return entry;
		//
	}

	private static <T> boolean and(final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	private void reset() {
		//
		setText(null, tfHiragana, tfKatakana, tfRomaji, tfAudioUrl);
		//
		Util.forEach(Util.map(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, JapanDictGui.class, FieldUtils::getAllFieldsList, null)),
				x -> Boolean.logicalAnd(Util.isAssignableFrom(AbstractButton.class, Util.getType(x)),
						!Util.isAnnotationPresent(x, Execute.class))),
				x -> Util.cast(AbstractButton.class, Narcissus.getField(this, x))), x -> Util.setEnabled(x, false));
		//
		Util.setSelectedItem(cbmJlptLevel, "");
		//
		Util.forEach(IntStream.iterate(Util.getSize(mcbmPitchAccent) - 1, i -> i >= 0, i -> i - 1),
				i -> Util.removeElementAt(mcbmPitchAccent, i));
		//
		Util.forEach(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(JapanDictGui.class), Arrays::stream,
						null), x -> Util.isAssignableFrom(JLabel.class, Util.getType(x))),
				x -> Util.setIcon(Util.cast(JLabel.class, Narcissus.getField(this, x)), null));
		//
		Util.forEach(
				Util.filter(Util.stream(FieldUtils.getAllFieldsList(JapanDictGui.class)),
						x -> Objects.equals(Util.getType(x), BufferedImage.class)),
				x -> Narcissus.setField(this, x, null));
		//
	}

	private static int getRowHeight(@Nullable final JTable instance) {
		return instance != null ? instance.getRowHeight() : 0;
	}

	private static double getWidth(@Nullable final Dimension instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static double getHeight(@Nullable final Dimension instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getJlptLevel(@Nullable final ComboBoxModel<String> cbm, final String text) {
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
		if (ints != null) {
			//
			if (ints.length > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (ints.length == 1 && cbm != null && ints[0] < cbm.getSize()) {
				//
				return cbm.getElementAt(ints[0]);
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static int[] getRGBs(@Nullable final BufferedImage instance) {
		//
		int[] ints = null;
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
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
				x -> Narcissus.getField(instance, x), null) != null;
		//
		for (int x = 0; condition && instance != null && x < instance.getWidth(); x++) {
			//
			for (int y = 0; y < instance.getHeight(); y++) {
				//
				if (!ArrayUtils.contains(ints = ObjectUtils.getIfNull(ints, () -> new int[] { 0 }),
						instance.getRGB(x, y))) {
					//
					ints = ArrayUtils.add(ints, instance.getRGB(x, y));
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return ints;
		//
	}

	private static <E extends Throwable> void testAndAccept(@Nullable final LongPredicate predicate, final long l,
			@Nullable final FailableLongConsumer<E> consumer) throws E {
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
	private static BufferedImage chopImage(@Nullable final BufferedImage bufferedImage) {
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

		private Map<?, ?> map = null;

		@Override
		@Nullable
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
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(name, "clear")) {
					//
					Narcissus.invokeMethod(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), method);
					//
					return null;
					//
				} else if (Objects.equals(name, "put")) {
					//
					testAndAccept(Objects::nonNull, args, x -> Narcissus
							.invokeMethod(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), method, x));
					//
					return null;
					//
				} else if (Objects.equals(name, "get")) {
					//
					final Object arg1 = ArrayUtils.get(args, 0);
					//
					if (!Util.containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), arg1)) {
						//
						throw new IllegalStateException(Util.toString(arg1));
						//
					} // if
						//
					return Util.get(map, arg1);
					//
				} // if
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
		if (Objects.equals(source, instance.btnCopyRomaji)) {
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
			final PitchAccent pitchAccent = Util.cast(PitchAccent.class,
					Util.getSelectedItem(instance.mcbmPitchAccent));
			//
			ih.image = pitchAccent != null ? pitchAccent.image : null;
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
			append(append(append(sb, "(Pitch Accent)"), '.'), format);
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
			//
			final PitchAccent pitchAccent = Util.cast(PitchAccent.class,
					Util.getSelectedItem(instance.mcbmPitchAccent));
			//
			final BufferedImage image = pitchAccent != null ? pitchAccent.image : null;
			//
			if (Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), image != null)
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					ImageIO.write(image, format, jfc.getSelectedFile());
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
		if (Objects.equals(source, instance.btnCopyHiragana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfHiragana)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnDownloadAudio)) {
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
			final String userAgent = instance.getUserAgent();
			//
			try {
				//
				if ((bs = download(Util.getText(instance.tfAudioUrl), userAgent)) == null) {
					//
					final JapanDictEntry japanDictEntry = Util.cast(JapanDictEntry.class, getValueAt(instance.dtm,
							instance.jTable != null ? instance.jTable.getSelectedRow() : 0, 0));
					//
					final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
							Util.openConnection(Util.toURL(URIBuilderUtil.build(testAndApply(Objects::nonNull,
									japanDictEntry != null ? japanDictEntry.pageUrl : null, URIBuilder::new, null)))));
					//
					setRequestProperty(httpURLConnection, USER_AGENT, userAgent);
					//
					try (final InputStream is = Util.getInputStream(httpURLConnection)) {
						//
						final URIBuilder uriBuilder = testAndApply(Objects::nonNull,
								japanDictEntry != null ? japanDictEntry.audioUrl : null, URIBuilder::new, null);
						//
						final List<NameValuePair> queryParams = uriBuilder != null ? uriBuilder.getQueryParams() : null;
						//
						final ObjectMapper objectMapper = new ObjectMapper();
						//
						final Iterable<Element> es = Util
								.toList(FailableStreamUtil
										.stream(filter(
												new FailableStream<>(
														Util.stream(
																ElementUtil.select(
																		testAndApply(Objects::nonNull,
																				testAndApply(Objects::nonNull, is,
																						x -> IOUtils.toString(x,
																								StandardCharsets.UTF_8),
																						null),
																				Jsoup::parse, null),
																		"a[data-reading]"))),
												x -> {
													//
													final Iterable<?> iterable = Util.cast(Iterable.class,
															ObjectMapperUtil.readValue(objectMapper,
																	NodeUtil.attr(x, DATA_READING), Object.class));
													//
													NameValuePair nvp = null;
													//
													boolean found = false;
													//
													String value = null;
													//
													for (int i = 0; i < IterableUtils.size(queryParams); i++) {
														//
														if ((nvp = IterableUtils.get(queryParams, i)) == null) {
															//
															continue;
															//
														} // if
															//
														for (int j = 0; j < IterableUtils.size(iterable); j++) {
															//
															if (!Objects.equals(IterableUtils.get(iterable, j),
																	(value = nvp.getValue()))
																	|| NumberUtils.isParsable(value)) {
																//
																continue;
																//
															} // if
																//
															if (found) {
																//
																throw new IllegalStateException();
																//
															} // if
																//
															found = true;
															//
														} // for
															//
													} // for
														//
													return found;
													//
												})));
						//
						testAndRun(IterableUtils.size(es) > 1, () -> {
							//
							throw new IllegalStateException();
							//
						});
						//
						final Element e = testAndApply(x -> IterableUtils.size(x) == 1, es,
								x -> IterableUtils.get(x, 0), null);
						//
						final Iterable<NameValuePair> nvps = Util.toList(Util.filter(Util.stream(queryParams),
								x -> StringUtils.countMatches(x != null ? x.getValue() : null, '.') == 2));
						//
						testAndRun(IterableUtils.size(nvps) > 1, () -> {
							//
							throw new IllegalStateException();
							//
						});
						//
						final NameValuePair nvp = testAndApply(x -> IterableUtils.size(x) == 1, nvps,
								x -> IterableUtils.get(x, 0), null);
						//
						final Iterable<?> iterable = Util.cast(Iterable.class,
								ObjectMapperUtil.readValue(objectMapper, NodeUtil.attr(e, DATA_READING), Object.class));
						//
						IValue0<String> iValue0 = null;
						//
						String string = null;
						//
						for (int i = 0; i < IterableUtils.size(iterable); i++) {
							//
							if (StringUtils.countMatches(string = Util.toString(IterableUtils.get(iterable, i)),
									'.') != 2
									|| StringsUtil.contains(Strings.CS,
											japanDictEntry != null ? japanDictEntry.audioUrl : null, string)) {
								//
								continue;
								//
							} // if
								//
							if (iValue0 != null) {
								//
								throw new IllegalStateException();
								//
							} // if
								//
							iValue0 = Unit.with(string);
							//
						} // for
							//
						final Iterable<Field> fs = Util.toList(Util.filter(
								Util.stream(testAndApply(Objects::nonNull, Util.getClass(nvp),
										FieldUtils::getAllFieldsList, null)),
								x -> Objects.equals(Util.getName(x), VALUE)));
						//
						testAndRun(IterableUtils.size(fs) > 1, () -> {
							//
							throw new IllegalStateException();
							//
						});
						//
						final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0),
								null);
						//
						testAndAccept((a, b) -> a != null, nvp, iValue0,
								(a, b) -> Narcissus.setField(a, f, IValue0Util.getValue0(b)));
						//
						if (uriBuilder != null) {
							//
							uriBuilder.clearParameters();
							//
							uriBuilder.addParameters(queryParams);
							//
						} // if
							//
						Util.setText(instance.tfAudioUrl, Util.toString(URIBuilderUtil.build(uriBuilder)));
						//
					} // try
						//
				} // if
					//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // if
				//
			try (final InputStream is = testAndApply(Objects::nonNull,
					bs = bs != null ? download(Util.getText(instance.tfAudioUrl), userAgent) : null,
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
		} else if (Objects.equals(source, instance.btnCopyStrokeImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = instance.strokeBufferedImage;
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					Reflection.newProxy(Transferable.class, ih), null));
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static <T> FailableStream<T> filter(@Nullable final FailableStream<T> instance,
			final FailablePredicate<T, ?> predicate) {
		return instance != null && instance.stream() != null ? instance.filter(predicate) : instance;
	}

	private static boolean actionPerformed3(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnSaveStrokeImage)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText), StringBuilder::new,
					null);
			//
			final String format = "png";
			//
			append(append(append(sb, "(Stroke)"), '.'), format);
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
			//
			if (Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), instance.strokeBufferedImage != null)
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					ImageIO.write(instance.strokeBufferedImage, format, jfc.getSelectedFile());
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
		} else if (Objects.equals(source, instance.btnCopyKatakana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfKatakana)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyStrokeWithNumberImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = instance.strokeWithNumberBufferedImage;
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					Reflection.newProxy(Transferable.class, ih), null));
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed4(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnSaveStrokeWithNumberImage)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText), StringBuilder::new,
					null);
			//
			final String format = "png";
			//
			append(append(append(sb, "(Stroke With Number)"), '.'), format);
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
			//
			if (Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(),
					instance.strokeWithNumberBufferedImage != null)
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					ImageIO.write(instance.strokeWithNumberBufferedImage, format, jfc.getSelectedFile());
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
		setRequestProperty(httpURLConnection, USER_AGENT, userAgent);
		//
		if (httpURLConnection == null) {
			//
			return null;
			//
		} // if
			//
		final int responseCode = httpURLConnection.getResponseCode();
		//
		if (!HttpStatus.isSuccess(responseCode)) {
			//
			return null;
			//
		} // if
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
				x -> Objects.equals(Util.getName(x), VALUE)));
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
				x -> Objects.equals(Util.getName(x), VALUE)));
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

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> instance, @Nullable final T value,
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
				"org.springframework.context.support.JapanDictGui.storkeImageDuration",
				(a, b) -> instance.storkeImageDuration = toDuration(Util.get(a, b)));
		//
		testAndAccept(Util::containsKey, properties,
				"org.springframework.context.support.JapanDictGui.storkeImageSleepDuration",
				(a, b) -> instance.storkeImageSleepDuration = toDuration(Util.get(a, b)));
		//
		instance.setLayout(new MigLayout());
		//
		instance.afterPropertiesSet();
		//
		add(jFrame, instance);
		//
		pack(instance.window = jFrame);
		//
		Util.setVisible(jFrame, true);
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
			return null;
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

	@Override
	public void valueChanged(@Nullable final ListSelectionEvent evt) {
		//
		int[] selectedIndices = null;
		//
		int selectedIndex = 0;
		//
		if (Objects.equals(Util.getSource(evt), lsm) && evt != null && !evt.getValueIsAdjusting() && jTable != null
				&& (selectedIndices = getSelectedIndices(lsm)) != null && selectedIndices.length == 1
				&& (selectedIndex = selectedIndices[0]) < jTable.getRowCount()) {
			//
			reset();
			//
			valueChanged(this, Util.cast(JapanDictEntry.class, getValueAt(dtm, selectedIndex, 0)));
			//
		} // if
			//
	}

	private static void valueChanged(@Nullable final JapanDictGui instance, @Nullable final JapanDictEntry entry) {
		//
		if (instance == null || entry == null) {
			//
			return;
			//
		} // if
			//
		instance.reset();
		//
		final TriConsumer<JTextComponent, String, Iterable<Component>> triConsumer = (a, b, c) -> {
			//
			Util.setText(a, b);
			//
			Util.forEach(c, x -> Util.setEnabled(x, StringUtils.isNotBlank(b)));
			//
		};
		//
		instance.setJcbJlptLevel(getJlptLevelIndices(instance.cbmJlptLevel, entry.jlptLevel));
		//
		TriConsumerUtil.accept(triConsumer, instance.tfHiragana, entry.hiragana,
				Collections.singleton(instance.btnCopyHiragana));
		//
		TriConsumerUtil.accept(triConsumer, instance.tfKatakana, entry.katakana,
				Collections.singleton(instance.btnCopyKatakana));
		//
		TriConsumerUtil.accept(triConsumer, instance.tfRomaji, entry.romaji,
				Collections.singleton(instance.btnCopyRomaji));
		//
		TriConsumerUtil.accept(triConsumer, instance.tfAudioUrl, entry.audioUrl,
				Arrays.asList(instance.btnCopyAudioUrl, instance.btnDownloadAudio, instance.btnPlayAudio));
		//
		final Iterable<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(Playwright.class), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), Util.getSelectedItem(instance.cbmBrowserType))));
		//
		testAndRun(IterableUtils.size(ms) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final String pageUrl = entry.pageUrl;
		//
		try (final Playwright playwright = testAndGet(
				Boolean.logicalOr(entry.strokeImage == null, IterableUtils.isEmpty(entry.pitchAccents)),
				Playwright::create);
				final Browser browser = testAndApply(
						Predicates.always(UrlValidatorUtil.isValid(UrlValidator.getInstance(), pageUrl)), playwright,
						x -> BrowserTypeUtil.launch(ObjectUtils.getIfNull(
								Util.cast(BrowserType.class, testAndApply(Objects::nonNull,
										testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0),
												null),
										y -> Narcissus.invokeMethod(x, y), null)),
								() -> PlaywrightUtil.chromium(x))),
						null);
				final Page page = newPage(browser)) {
			//
			PageUtil.navigate(page, pageUrl);
			//
			// Pitch Accents
			//
			if (IterableUtils.isEmpty(entry.pitchAccents)) {
				//
				entry.pitchAccents = getPitchAccents(ElementHandleUtil.querySelectorAll(testAndApply(
						x -> IterableUtils.size(x) > Util.intValue(entry.index, 0),
						PageUtil.querySelectorAll(page,
								String.format("#entry-%1$s ul li.list-group-item[lang='ja'] .d-flex.p-2", entry.id)),
						x -> IterableUtils.get(x, Util.intValue(entry.index, 0)), null), "div.d-flex"));
				//
			} // if
				//
			Util.forEach(entry.pitchAccents, x -> Util.addElement(instance.mcbmPitchAccent, x));
			//
			setEnabled(!IterableUtils.isEmpty(entry.pitchAccents), instance.btnCopyPitchAccentImage,
					instance.btnSavePitchAccentImage);
			//
			// Stroke Image
			//
			if (entry.strokeImage == null) {
				//
				try {
					//
					testAndApply(Objects::nonNull,
							entry.strokeImage = chopImage(getStrokeImage(instance, page, entry.id)), ImageIcon::new,
							null);
					//
				} catch (final IOException | InterruptedException e) {
					//
					TaskDialogs.showException(e);
					//
				} // try
					//
			} // if
				//
			Util.setIcon(instance.strokeImage, testAndApply(Objects::nonNull,
					instance.strokeBufferedImage = entry.strokeImage, ImageIcon::new, null));
			//
			setEnabled(entry.strokeImage != null, instance.btnCopyStrokeImage, instance.btnSaveStrokeImage);
			//
		} // try
			//
		try (final Playwright playwright = testAndGet(entry.strokeWithNumberImage == null, Playwright::create);
				final Browser browser = testAndApply(
						Predicates.always(UrlValidatorUtil.isValid(UrlValidator.getInstance(), pageUrl)), playwright,
						x -> BrowserTypeUtil.launch(ObjectUtils.getIfNull(
								Util.cast(BrowserType.class, testAndApply(Objects::nonNull,
										testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0),
												null),
										y -> Narcissus.invokeMethod(x, y), null)),
								() -> PlaywrightUtil.chromium(x))),
						null);
				final Page page = newPage(browser)) {
			//
			PageUtil.navigate(page, pageUrl);
			//
			// Stroke With Number Image
			//
			check(testAndApply(x -> IterableUtils.size(x) == 1,
					PageUtil.querySelectorAll(page, String.format("#dmak-show-stroke-check-%1$s", entry.id)),
					x -> IterableUtils.get(x, 0), null));
			//
			click(testAndApply(x -> IterableUtils.size(x) == 1,
					PageUtil.querySelectorAll(page, String.format("#dmak-reset-%1$s", entry.id)),
					x -> IterableUtils.get(x, 0), null));
			//
			if (entry.strokeWithNumberImage == null) {
				//
				try {
					//
					testAndApply(Objects::nonNull,
							entry.strokeWithNumberImage = chopImage(getStrokeImage(instance, page, entry.id)),
							ImageIcon::new, null);
					//
				} catch (final IOException | InterruptedException e) {
					//
					TaskDialogs.showException(e);
					//
				} // try
					//
			} // if
				//
			Util.setIcon(instance.strokeWithNumberImage, testAndApply(Objects::nonNull,
					instance.strokeWithNumberBufferedImage = entry.strokeWithNumberImage, ImageIcon::new, null));
			//
			setEnabled(entry.strokeWithNumberImage != null, instance.btnCopyStrokeWithNumberImage,
					instance.btnSaveStrokeWithNumberImage);
			//
		} // try
			//
		pack(instance.window);
		//
	}

	private static Iterable<PitchAccent> getPitchAccents(final Iterable<ElementHandle> iterable) {
		//
		ElementHandle eh = null;
		//
		Iterable<ElementHandle> ehs = null;
		//
		PitchAccent pa = null;
		//
		Collection<PitchAccent> collection = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			final BoundingBox boundingBox = boundingBox(Util.cast(ElementHandle.class,
					testAndApply(CollectionUtils::isNotEmpty,
							ElementHandleUtil.querySelectorAll(eh = IterableUtils.get(iterable, i), "div"),
							y -> IterableUtils.get(y, 0), null)));
			//
			try {
				//
				if (StringsUtil.contains(Strings.CS, ElementHandleUtil.getAttribute(eh, "class"), "flex-colum")) {
					//
					(pa = new PitchAccent()).image = testAndApply(
							x -> startsWith(Strings.CS,
									getMimeType(
											testAndApply(Objects::nonNull, x, new ContentInfoUtil()::findMatch, null)),
									"image/"),
							ElementHandleUtil.screenshot(testAndApply(CollectionUtils::isNotEmpty,
									ElementHandleUtil.querySelectorAll(eh, "div"), x -> IterableUtils.get(x, 0), null)),
							x -> toBufferedImage(x), null);
					//
				} else {
					//
					(pa = new PitchAccent()).image = testAndApply(x -> startsWith(Strings.CS,
							getMimeType(testAndApply(Objects::nonNull, x, new ContentInfoUtil()::findMatch, null)),
							"image/"), ElementHandleUtil.screenshot(eh), x -> chopImage(x, boundingBox), null);
					//
				} // if
					//
			} catch (final IOException e) {
				//
				TaskDialogs.showException(e);
				//
			} // try
				//
			if (IterableUtils.size(ehs = ElementHandleUtil.querySelectorAll(eh, "[data-bs-content]")) == 1) {
				//
				pa.type = ElementUtil
						.text(testAndApply(x -> IterableUtils.size(x) == 1,
								ElementUtil.select(
										testAndApply(Objects::nonNull,
												ElementHandleUtil.getAttribute(IterableUtils.get(ehs, 0),
														"data-bs-content"),
												x -> Jsoup.parse(x, ""), null),
										"p span[class='h5']"),
								x -> IterableUtils.get(x, 0), null));
				//
			} else {
				//
				final Stream<ElementHandle> stream = testAndApply(Objects::nonNull, Util.spliterator(ehs),
						x -> StreamSupport.stream(x, false), null);
				//
				final Iterable<String> ss = Util.toList(Util.distinct(Util.map(stream,
						x -> ElementUtil.text(testAndApply(y -> IterableUtils.size(y) == 1,
								ElementUtil.select(
										Jsoup.parse(ElementHandleUtil.getAttribute(x, "data-bs-content"), ""),
										"p span[class='h5']"),
								y -> IterableUtils.get(y, 0), null)))));
				//
				testAndRun(IterableUtils.size(ss) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				testAndAccept((a, b) -> IterableUtils.size(b) == 1, pa, ss, (a, b) -> {
					//
					if (a != null) {
						//
						a.type = IterableUtils.get(b, 0);
						//
					} // if
						//
				});
				//
			} // if
				//
			Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new), pa);
			//
		} // for
			//
		return collection;
		//
	}

	private static void check(@Nullable final ElementHandle instance) {
		if (instance != null) {
			instance.check();
		}
	}

	private static BufferedImage getStrokeImage(final JapanDictGui instance, final Page page, final String id)
			throws IOException, InterruptedException {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(id), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), VALUE)));
		//
		testAndRun(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final String idString = testAndApply((a, b) -> a != null,
				testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null), id,
						testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
						Narcissus::getField, null),
				id, (a, b) -> b, null);
		//
		click(testAndApply(x -> IterableUtils.size(x) == 1,
				PageUtil.querySelectorAll(page, String.format("#dmak-play-%1$s", idString)),
				x -> IterableUtils.get(x, 0), null));
		//
		final String dmakCssSelector = String.format("#dmak-%1$s", idString);
		//
		if ((testAndApply(x -> IterableUtils.size(x) == 1, PageUtil.querySelectorAll(page, dmakCssSelector),
				x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return null;
			//
		} // if
			//
		final long currentTimeMillis = System.currentTimeMillis();
		//
		BufferedImage before = null, after = null;
		//
		int[] ints = null;
		//
		while (System.currentTimeMillis() - currentTimeMillis < Math
				.max(toMillis(instance != null ? instance.storkeImageDuration : null, 20000), 0)) {
			//
			if (before == null) {
				//
				before = toBufferedImage(screenshot(locator(page, dmakCssSelector)));
				//
			} else {
				//
				if (Objects.equals(
						getImageComparisonState(new ImageComparison(before,
								after = toBufferedImage(screenshot(locator(page, dmakCssSelector)))).compareImages()),
						ImageComparisonState.MATCH) && ((ints = getRGBs(after)) == null || ints.length < 500)) {
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
	}

	@Nullable
	private static int[] getSelectedIndices(@Nullable final ListSelectionModel instance) {
		return instance != null ? instance.getSelectedIndices() : null;
	}

	@Nullable
	private static BoundingBox boundingBox(@Nullable final ElementHandle instance) {
		return instance != null ? instance.boundingBox() : null;
	}

}