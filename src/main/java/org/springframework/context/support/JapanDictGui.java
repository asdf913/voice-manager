package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
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
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiConsumerUtil;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableRunnableUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilderUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageUtil;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDRectangleUtil;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptorUtil;
import org.apache.pdfbox.pdmodel.font.PDFontUtil;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageUtil;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.PDFRendererUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.meeuw.functional.Predicates;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
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
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
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
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import net.miginfocom.swing.MigLayout;

public class JapanDictGui extends JPanel implements ActionListener, InitializingBean, ListSelectionListener {

	private static final long serialVersionUID = -4598144203806679104L;

	private static final String VALUE = "value";

	private static final String USER_AGENT = "User-Agent";

	private static final String DATA_READING = "data-reading";

	private static final String PITCH_ACCENT = "Pitch Accent";

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

	@Note("Copy Furigana Image")
	private AbstractButton btnCopyFuriganaImage = null;

	@Note("Save Furigana Image")
	private AbstractButton btnSaveFuriganaImage = null;

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

	@Note("Save Stroke With Number Image")
	private AbstractButton btnSaveStrokeWithNumberImage = null;

	@Note("Copy URL")
	private AbstractButton btnCopyUrl = null;

	@Note("Browse URL")
	private AbstractButton btnBrowseUrl = null;

	private AbstractButton btnPdf = null;

	private JComboBox<String> jcbJlptLevel = null;

	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private Map<String, String> userAgentMap = null;

	private transient ComboBoxModel<String> cbmBrowserType = null;

	@Note("Furigana Image")
	private JLabel furiganaImage = null;

	@Note("Stroke Image")
	private JLabel strokeImage = null;

	private JLabel strokeWithNumberImage = null;

	@Note("Furigana Image")
	private transient BufferedImage furiganaBufferedImage = null;

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

		private void setType(final String type) {
			this.type = type;
		}

		private static void setType(@Nullable final PitchAccent instance, final String type) {
			if (instance != null) {
				instance.setType(type);
			}
		}

	}

	private transient MutableComboBoxModel<PitchAccent> mcbmPitchAccent = null;

	private static class Link {

		private URL url;

		private String text;

		private URL getUrl() {
			return url;
		}

		@Nullable
		private static URL getUrl(@Nullable final Link instance) {
			return instance != null ? instance.getUrl() : null;
		}

	}

	private JTable jTableLink = null;

	private DefaultTableModel dtmLink = null;

	private transient ListSelectionModel lsmLink = null;

	private transient PDFont pdFont = null;

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
		final int width = 100;
		//
		add(this, tfText = new JTextField(), String.format("%1$s,%2$s,span %3$s,wmax %4$s", growx, wrap, 3, width));
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
				new Object[] { "", "JLPT Level", "Hiragana", "Katakana", "Romaji", PITCH_ACCENT }, 0) {

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}

		})), String.format("%1$s,wmin %2$s,span %3$s", wrap, 100, 9));
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
		Dimension preferredSize = Util.getPreferredSize(jTable);
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
				throwRuntimeException(e);
				//
			} // try
				//
		});
		//
		add(this, new JLabel("Hiragana"));
		//
		add(this, tfHiragana = new JTextField(), String.format("%1$s,span %2$s,wmax %3$s", growx, 3, width));
		//
		add(this, btnCopyHiragana = new JButton("Copy"));
		//
		add(this, new JLabel("Katakana"));
		//
		add(this, tfKatakana = new JTextField(), String.format("%1$s,wmin %2$s", growx, width));
		//
		add(this, btnCopyKatakana = new JButton("Copy"));
		//
		add(this, new JLabel("Romaji"));
		//
		add(this, tfRomaji = new JTextField(), String.format("%1$s,span %2$s,wmin %3$s", growx, 1, width));
		//
		add(this, btnCopyRomaji = new JButton("Copy"), wrap);
		//
		add(this, new JLabel("Audio URL"));
		//
		add(this, tfAudioUrl = new JTextField(), String.format("%1$s,span %2$s,wmax %3$spx", growx, 3, 150));
		//
		JPanel jPanel = new JPanel();
		//
		add(jPanel, btnCopyAudioUrl = new JButton("Copy"));
		//
		add(jPanel, btnDownloadAudio = new JButton("Download"));
		//
		add(jPanel, btnPlayAudio = new JButton("Play"));
		//
		add(this, jPanel, String.format("%1$s,span %2$s", wrap, 4));
		//
		add(this, new JLabel("Furigana"));
		//
		add(this, furiganaImage = new JLabel(), String.format("span %1$s", 5));
		//
		add(jPanel = new JPanel(), btnCopyFuriganaImage = new JButton("Copy"));
		//
		add(jPanel, btnSaveFuriganaImage = new JButton("Save"));
		//
		add(this, jPanel, String.format("%1$s,span %2$s", wrap, 2));
		//
		add(this, new JLabel(PITCH_ACCENT));
		//
		final JComboBox<PitchAccent> jcbPitchAccent = new JComboBox<>(mcbmPitchAccent = new DefaultComboBoxModel<>());
		//
		if (Objects.equals(Util.getName(Util.getClass(FileSystems.getDefault())), "sun.nio.fs.MacOSXFileSystem")) {
			//
			jcbPitchAccent.setUI(new MetalComboBoxUI());
			//
		} // if
			//
		jcbPitchAccent.setRenderer(createPitchAccentListCellRenderer(jcbPitchAccent, jcbPitchAccent.getRenderer(),
				Util.getPreferredSize(jcbPitchAccent)));
		//
		add(this, jcbPitchAccent, String.format("span %1$s", 5));
		//
		add(jPanel = new JPanel(), btnCopyPitchAccentImage = new JButton("Copy"));
		//
		add(jPanel, btnSavePitchAccentImage = new JButton("Save"));
		//
		add(this, jPanel, String.format("%1$s,span %2$s", wrap, 2));
		//
		add(this, new JLabel("Stroke"), String.format("span %1$s", 2));
		//
		add(this, strokeImage = new JLabel(), String.format("span %1$s", 6));
		//
		add(this, btnCopyStrokeImage = new JButton("Copy"), String.format("flowy,split %1$s", 2));
		//
		add(this, btnSaveStrokeImage = new JButton("Save"), growx);
		//
		add(this, new JLabel(), wrap);
		//
		add(this, new JLabel("Stroke with Number"), String.format("span %1$s", 2));
		//
		add(this, strokeWithNumberImage = new JLabel(), String.format("span %1$s", 6));
		//
		add(this, btnCopyStrokeWithNumberImage = new JButton("Copy"), String.format("flowy,split %1$s", 2));
		//
		add(this, btnSaveStrokeWithNumberImage = new JButton("Save"), growx);
		//
		add(this, new JLabel(), wrap);
		//
		add(this, new JLabel("Link"));
		//
		add(this, new JScrollPane(
				jTableLink = new JTable(dtmLink = new DefaultTableModel(new Object[] { "Text", "URL" }, 0)) {

					@Override
					public boolean isCellEditable(final int row, final int column) {
						return false;
					}

				}), String.format("span %1$s,%2$s", 12, wrap));
		//
		jTableLink.setDefaultRenderer(Object.class,
				createTableCellRenderer(jTableLink.getDefaultRenderer(Object.class)));
		//
		final TableColumnModel tcm = getColumnModel(jTableLink);
		//
		setMinWidth(getColumn(tcm, 0), 87);
		//
		setMinWidth(getColumn(tcm, 1), 475);
		//
		setPreferredScrollableViewportSize(jTableLink, new Dimension(
				(int) getWidth(preferredSize = Util.getPreferredSize(jTableLink)), (int) getHeight(preferredSize)));
		//
		if ((lsmLink = jTableLink.getSelectionModel()) != null) {
			//
			lsmLink.addListSelectionListener(this);
			//
		} // if
			//
		add(this, new JLabel());
		//
		add(jPanel = new JPanel(), btnCopyUrl = new JButton("Copy"));
		//
		add(jPanel, btnBrowseUrl = new JButton("Browse"));
		//
		add(this, jPanel, String.format("span %1$s,%2$s", 2, wrap));
		//
		add(this, new JLabel());
		//
		add(this, btnPdf = new JButton("PDF"));
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

	@Nullable
	private static TableColumnModel getColumnModel(@Nullable final JTable instance) {
		return instance != null ? instance.getColumnModel() : null;
	}

	@Nullable
	private static TableColumn getColumn(@Nullable final TableColumnModel instance, final int columnIndex) {
		return instance != null ? instance.getColumn(columnIndex) : null;
	}

	private static void setMinWidth(@Nullable final TableColumn instance, final int minWidth) {
		if (instance != null) {
			instance.setMinWidth(minWidth);
		}
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
					add(panel,
							new JLabel(
									Util.toString(append(append(append(new StringBuilder(), '('), value.type), ')'))),
							"align right");
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
			final Object object = getValueAt(getModel(table), row, 0);
			//
			// org.springframework.context.support.JapanDictGui$JapanDictEntry
			//
			final JapanDictEntry entry = Util.cast(JapanDictEntry.class, ObjectUtils.getIfNull(value, () -> object));
			//
			final Component c = JapanDictGui.getTableCellRendererComponent(tcr, table, value, isSelected, hasFocus, row,
					column);
			//
			final JLabel jLabel = Util.cast(JLabel.class, c);
			//
			final Strings strings = Strings.CI;
			//
			Iterable<Field> fs = Util.toList(Util.filter(
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
			Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
			//
			if (f != null && entry != null) {
				//
				Util.setText(jLabel, Util.toString(Narcissus.getField(entry, f)));
				//
			} else if (Objects.equals(columnName, "")) {
				//
				Util.setText(jLabel, JapanDictEntry.getText(entry));
				//
			} else if (Objects.equals(columnName, PITCH_ACCENT)) {
				//
				final Stream<PitchAccent> stream = testAndApply(Objects::nonNull,
						Util.spliterator(entry != null ? entry.pitchAccents : null),
						x -> StreamSupport.stream(x, false), null);
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						Util.toList(Util.distinct(Util.map(stream, x -> x != null ? x.type : null))),
						x -> Util.setText(jLabel, IterableUtils.get(x, 0)));
				//
			} // if
				//
				// org.springframework.context.support.JapanDictGui$Link
				//
			final Link link = Util.cast(Link.class, ObjectUtils.getIfNull(value, () -> object));
			//
			testAndRun(IterableUtils.size(fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(link), FieldUtils::getAllFieldsList, null)),
					x -> StringsUtil.equals(strings, Util.getName(x),
							StringsUtil.replace(strings, columnName, " ", ""))))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
			//
			if (f != null && link != null) {
				//
				Util.setText(jLabel, Util.toString(Narcissus.getField(link, f)));
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

		@Note("Furigana Image")
		private BufferedImage furiganaImage = null;

		@Note("Stroke Image")
		private BufferedImage strokeImage = null;

		private BufferedImage strokeWithNumberImage = null;

		private Iterable<PitchAccent> pitchAccents = null;

		private byte[] audioData = null;

		private Iterable<Link> links = null;

		private byte[] getAudioData() {
			return audioData;
		}

		@Nullable
		private static byte[] getAudioData(@Nullable final JapanDictEntry instance) {
			return instance != null ? instance.getAudioData() : null;
		}

		private String getText() {
			return text;
		}

		@Nullable
		private static String getText(@Nullable final JapanDictEntry instance) {
			return instance != null ? instance.getText() : null;
		}

		private String getId() {
			return id;
		}

		@Nullable
		private static String getId(@Nullable final JapanDictEntry instance) {
			return instance != null ? instance.getId() : null;
		}

		private void setStrokeWithNumberImage(final BufferedImage strokeWithNumberImage) {
			this.strokeWithNumberImage = strokeWithNumberImage;
		}

		private static void setStrokeWithNumberImage(@Nullable final JapanDictEntry instance,
				final BufferedImage strokeWithNumberImage) {
			if (instance != null) {
				instance.setStrokeWithNumberImage(strokeWithNumberImage);
			}
		}

		private BufferedImage getStrokeWithNumberImage() {
			return strokeWithNumberImage;
		}

		@Nullable
		private static BufferedImage getStrokeWithNumberImage(@Nullable final JapanDictEntry instance) {
			return instance != null ? instance.getStrokeWithNumberImage() : null;
		}
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
				testAndRun(GraphicsEnvironment.isHeadless(), () -> throwRuntimeException(e));
				//
				TaskDialogs.showException(e);
				//
			} finally {
				//
				IOUtils.closeQuietly(is);
				//
			} // if
				//
			final Iterable<Element> es = ElementUtil.select(document, ".container-fluid.bg-white.p-0");
			//
			final Pattern pattern = Pattern.compile("^[^\\d]+(\\d+)$");
			//
			final Stream<Element> stream = testAndApply(Objects::nonNull, Util.spliterator(es),
					x -> StreamSupport.stream(x, false), null);
			//
			addRows(this, es, scheme, pageUrl,
					getLinkMultimap(document,
							Util.toList(Util.map(stream,
									x -> testAndApply(y -> and(Util.matches(y), () -> Util.groupCount(y) > 0),
											Util.matcher(pattern, NodeUtil.attr(x, "id")), y -> Util.group(y, 1),
											null)))));
			//
			Dimension preferredSize = Util.getPreferredSize(jTable);
			//
			setPreferredScrollableViewportSize(jTable, new Dimension((int) getWidth(preferredSize),
					(int) Math.min(sum(Util.map(IntStream.range(0, Util.getRowCount(dtm)), x ->
					//
					Math.max(getRowHeight(jTable),
							Util.orElse(
									Util.max(
											Util.map(IntStream.range(0, getColumnCount(jTable)),
													column -> (int) getHeight(Util.getPreferredSize(prepareRenderer(
															jTable, getCellRenderer(jTable, x, column), x, column))))),
									0))
					//
					)), getHeight(preferredSize))));
			//
			testAndRun(Boolean.logicalAnd(Util.getRowCount(dtm) == 1, getColumnCount(dtm) > 0),
					() -> setRowSelectionInterval(jTable, 0, 0));
			//
			// Stroke With Number Image
			//
			final Iterable<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Playwright.class), Arrays::stream, null),
					x -> Objects.equals(Util.getName(x), Util.getSelectedItem(cbmBrowserType))));
			//
			JapanDictEntrySupplier japanDictEntrySupplier = null;
			//
			Collection<String> ids = null;
			//
			final Function<Playwright, BrowserType> function = x -> ObjectUtils.getIfNull(
					Util.cast(BrowserType.class,
							testAndApply(Objects::nonNull,
									testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0),
											null),
									y -> Narcissus.invokeMethod(x, y), null)),
					() -> PlaywrightUtil.chromium(x));
			//
			for (int i = 0; i < Util.getRowCount(dtm); i++) {
				//
				final JapanDictEntry japanDictEntry = Util.cast(JapanDictEntry.class, getValueAt(dtm, i, 0));
				//
				(japanDictEntrySupplier = new JapanDictEntrySupplier()).browserTypeFunction = function;
				//
				japanDictEntrySupplier.japanDictEntry = japanDictEntry;
				//
				japanDictEntrySupplier.japanDictGui = this;
				//
				try {
					//
					thenAcceptAsync(CompletableFuture.supplyAsync(japanDictEntrySupplier), x -> {
						//
						copyField(x, japanDictEntry, "furiganaImage");
						//
						copyField(x, japanDictEntry, "pitchAccents");
						//
						copyField(x, japanDictEntry, "strokeImage");
						//
						setStrokeImageAndStrokeWithNumberImage(dtm, japanDictEntry);
						//
					});
					//
				} catch (final Exception ex) {
					//
					throw ObjectUtils.getIfNull(Util.cast(RuntimeException.class, ex), () -> new RuntimeException(ex));
					//
				} // try
					//
				testAndAccept((a, b) -> !IterableUtils.contains(a, b), ids = ObjectUtils.getIfNull(ids, ArrayList::new),
						JapanDictEntry.getId(japanDictEntry), (a, b) -> {
							//
							final StrokeWithNumberImageSupplier strokeWithNumberImageSupplier = new StrokeWithNumberImageSupplier();
							//
							strokeWithNumberImageSupplier.browserTypeFunction = function;
							//
							strokeWithNumberImageSupplier.japanDictEntry = japanDictEntry;
							//
							strokeWithNumberImageSupplier.japanDictGui = this;
							//
							thenAcceptAsync(CompletableFuture.supplyAsync(strokeWithNumberImageSupplier), y -> {
								//
								JapanDictEntry.setStrokeWithNumberImage(japanDictEntry, ObjectUtils
										.getIfNull(JapanDictEntry.getStrokeWithNumberImage(japanDictEntry), y));
								//
								setStrokeImageAndStrokeWithNumberImage(dtm, japanDictEntry);
								//
							});
							//
							Util.add(a, b);
							//
						}, x -> {
							//
							throw ObjectUtils.getIfNull(Util.cast(RuntimeException.class, x),
									() -> new RuntimeException(x));
							//
						});
				//
			} // for
				//
				// links
				//
			setPreferredScrollableViewportSize(jTableLink,
					new Dimension((int) getWidth(preferredSize = Util.getPreferredSize(jTableLink)),
							(int) Math.min(sum(Util.map(IntStream.range(0, Util.getRowCount(dtmLink)), x ->
							//
							Math.max(getRowHeight(jTableLink),
									Util.orElse(Util.max(Util.map(IntStream.range(0, getColumnCount(jTableLink)),
											column -> (int) getHeight(Util.getPreferredSize(prepareRenderer(jTableLink,
													getCellRenderer(jTableLink, x, column), x, column))))),
											0))
							//
							)), getHeight(preferredSize))));
			//
			pack(window);
			//
		} // if
			//
		final Iterable<BiPredicate<JapanDictGui, Object>> predicates = Arrays.asList(JapanDictGui::actionPerformed1,
				JapanDictGui::actionPerformed2, JapanDictGui::actionPerformed3, JapanDictGui::actionPerformed4,
				JapanDictGui::actionPerformed5, JapanDictGui::actionPerformed6);
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

	@Nullable
	private static Multimap<String, Link> getLinkMultimap(final Element element, final Iterable<String> ids) {
		//
		Multimap<String, Link> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(ids); i++) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), getLinkMultimap(
					IterableUtils.get(ids, i),
					ElementUtil.select(element, String.format("#collapseDicts%1$s li", IterableUtils.get(ids, i)))));
			//
		} // for
			//
		return multimap;
		//
	}

	private static Multimap<String, Link> getLinkMultimap(final String id, final Iterable<Element> es) {
		//
		Multimap<String, Link> multimap = null;
		//
		Link link = null;
		//
		Element e, e1, e2 = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (NodeUtil.childNodeSize(e = IterableUtils.get(es, i)) == 1 && (e1 = IterableUtils.get(e, 0)) != null
					&& ElementUtil.childrenSize(e1) == 1
					&& (e2 = IterableUtils.get(ElementUtil.children(e1), 0)) != null) {
				//
				(link = new Link()).text = ElementUtil.text(e2);
				//
				try {
					//
					link.url = Util.toURL(new URI(NodeUtil.attr(e2, "href")));
					//
				} catch (final MalformedURLException | URISyntaxException ex) {
					//
					throw new RuntimeException(ex);
					//
				} // try
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), id, link);
				//
			} else if (NodeUtil.childNodeSize(e) == 2 && NodeUtil.childNode(e, 0) instanceof TextNode textNode
					&& NodeUtil.childNode(e, 1) instanceof Element e3) {
				//
				(link = new Link()).text = TextNodeUtil.text(textNode);
				//
				try {
					//
					link.url = Util.toURL(new URI(NodeUtil.attr(e3, "href")));
					//
				} catch (final MalformedURLException | URISyntaxException ex) {
					//
					throw new RuntimeException(ex);
					//
				} // try
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), id, link);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static void copyField(final Object source, final Object destination, final String fieldName) {
		//
		Iterable<Field> fields = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(source), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), fieldName)));
		//
		final Runnable runnable = () -> {
			//
			throw new IllegalStateException();
			//
		};
		//
		testAndRun(IterableUtils.size(fields) > 1, runnable);
		//
		final Field fs = testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0), null);
		//
		testAndRun(IterableUtils.size(fields = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(destination), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), fieldName)))) > 1, runnable);
		//
		final Field fd = testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0), null);
		//
		if (fd != null && fs != null) {
			//
			Narcissus.setField(destination, fd, Narcissus.getField(source, fs));
			//
		} // if
			//
	}

	private static <T, U, E extends Exception> void testAndAccept(final BiPredicate<T, U> instance, @Nullable final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> failableConsumer,
			final Consumer<Exception> consumer) {
		//
		if (Util.test(instance, t, u)) {
			//
			try {
				//
				FailableBiConsumerUtil.accept(failableConsumer, t, u);
				//
			} catch (final Exception ex) {
				//
				Util.accept(consumer, ex);
				//
			} // try
				//
		} // if
			//
	}

	private static int sum(@Nullable final IntStream instance) {
		return instance != null ? instance.sum() : 0;
	}

	private static <T> void thenAcceptAsync(@Nullable final CompletableFuture<T> instance,
			@Nullable final Consumer<T> consumer) {
		if (instance != null && consumer != null) {
			instance.thenAcceptAsync(consumer);
		}
	}

	private static void addRows(@Nullable final JapanDictGui instance, final Iterable<Element> es, final String scheme,
			final String pageUrl, @Nullable final Multimap<String, Link> links) {
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
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if ((e1 = IterableUtils.get(es, i)) == null) {
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
			jlptLevel = getJlptLevel(instance != null ? instance.cbmJlptLevel : null,
					ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.select(e1, "span.badge[title^='#jlpt'].me-1"), x -> IterableUtils.get(x, 0),
							null)));
			//
			es2 = ElementUtil.select(e1, "div[aria-labelledby^='modal-reading'] + ul li");
			//
			final DefaultTableModel dtm = instance != null ? instance.dtm : null;
			//
			for (int j = 0; j < IterableUtils.size(es2); j++) {
				//
				clear(map = ObjectUtils.getIfNull(map, () -> Reflection.newProxy(Map.class, new IH())));
				//
				Narcissus.invokeMethod(map, put, "jlptLevel", jlptLevel);
				//
				Narcissus.invokeMethod(map, put, "text", h1);
				//
				Narcissus.invokeMethod(map, put, "id", id);
				//
				Narcissus.invokeMethod(map, put, "scheme", scheme);
				//
				Narcissus.invokeMethod(map, put, "pageUrl", pageUrl);
				//
				Util.addRow(dtm,
						new Object[] { getJapanDictEntry(IterableUtils.get(es2, j),
								patternHiragana = ObjectUtils.getIfNull(patternHiragana,
										() -> Pattern.compile("^\\p{InHiragana}+$")),
								patternKatkana = ObjectUtils.getIfNull(patternKatkana,
										() -> Pattern.compile("^\\p{InKatakana}+$")),
								objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new), j, map,
								MultimapUtil.get(links, id)) });
				//
			} // for
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
		return instance != null && getColumnModel(instance) != null && tableCellRenderer != null
				? instance.prepareRenderer(tableCellRenderer, row, column)
				: null;
	}

	@Nullable
	private static TableCellRenderer getCellRenderer(@Nullable final JTable instance, final int row, final int column) {
		return instance != null && getColumnModel(instance) != null ? instance.getCellRenderer(row, column) : null;
	}

	private static int getColumnCount(@Nullable final JTable instance) {
		return instance != null && getColumnModel(instance) != null ? instance.getColumnCount() : 0;
	}

	private static void clear(@Nullable final Map<?, ?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static JapanDictEntry getJapanDictEntry(final Element e, final Pattern patternHiragana,
			final Pattern patternKatakana, final ObjectMapper objectMapper, final int index2, final Map<?, ?> map,
			final Iterable<Link> links) {
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
		entry.links = links;
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
		Util.forEach(IntStream.iterate(Util.getRowCount(dtmLink) - 1, i -> i >= 0, i -> i - 1),
				i -> Util.removeRow(dtmLink, i));
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
		final int width = getWidth(instance);
		//
		for (int x = 0; instance != null && x < width; x++) {
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
		final int width = getWidth(bufferedImage);
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

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
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
			append(sb, "(Pitch Accent)");
			//
			testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfHiragana),
					x -> append(append(append(sb, '('), x), ')'));
			//
			testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfKatakana),
					x -> append(append(append(sb, '('), x), ')'));
			//
			final String format = "png";
			//
			append(append(sb, '.'), format);
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
			if (and(Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), image != null),
					() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
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
		} else if (Objects.equals(source, instance.btnCopyHiragana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfHiragana)), null));
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean and(final boolean condition, final BooleanSupplier booleanSupplier) {
		return condition && booleanSupplier != null && booleanSupplier.getAsBoolean();
	}

	private static boolean actionPerformed2(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnPlayAudio)) {
			//
			final JapanDictEntry japanDictEntry = Util.cast(JapanDictEntry.class,
					getValueAt(instance.dtm, getSelectedRow(instance.jTable), 0));
			//
			byte[] bs = JapanDictEntry.getAudioData(japanDictEntry);
			//
			final String userAgent = instance.getUserAgent();
			//
			try {
				//
				if ((bs = bs == null ? download(Util.getText(instance.tfAudioUrl), userAgent) : bs) == null) {
					//
					Util.setText(instance.tfAudioUrl, getAudioUrl(japanDictEntry, userAgent));
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
					bs = bs == null ? download(Util.getText(instance.tfAudioUrl), userAgent) : bs,
					ByteArrayInputStream::new, null)) {
				//
				testAndAccept(
						x -> Objects.equals(getMessage(
								testAndApply(Objects::nonNull, x, y -> new ContentInfoUtil().findMatch(y), null)),
								"Audio file with ID3 version 2.4, MP3 encoding"),
						bs, x -> PlayerUtil.play(testAndApply(Objects::nonNull, is, Player::new, null)));
				//
				if (japanDictEntry != null) {
					//
					japanDictEntry.audioData = bs;
					//
				} // if
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

	private static String getAudioUrl(@Nullable final JapanDictEntry japanDictEntry, final String userAgent)
			throws IOException, URISyntaxException {
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
				Util.openConnection(Util.toURL(URIBuilderUtil.build(testAndApply(Objects::nonNull,
						japanDictEntry != null ? japanDictEntry.pageUrl : null, URIBuilder::new, null)))));
		//
		setRequestProperty(httpURLConnection, USER_AGENT, userAgent);
		//
		try (final InputStream is = Util.getInputStream(httpURLConnection)) {
			//
			final String audioUrl = japanDictEntry != null ? japanDictEntry.audioUrl : null;
			//
			final URIBuilder uriBuilder = testAndApply(Objects::nonNull, audioUrl, URIBuilder::new, null);
			//
			final List<NameValuePair> queryParams = getQueryParams(uriBuilder);
			//
			final ObjectMapper objectMapper = new ObjectMapper();
			//
			final Iterable<Element> es = Util.toList(FailableStreamUtil
					.stream(filter(new FailableStream<>(Util.stream(ElementUtil.select(testAndApply(Objects::nonNull,
							testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null),
							Jsoup::parse, null), "a[data-reading]"))), x -> {
								//
								final Iterable<?> iterable = Util.cast(Iterable.class, ObjectMapperUtil
										.readValue(objectMapper, NodeUtil.attr(x, DATA_READING), Object.class));
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
										if (Boolean
												.logicalOr(
														!Objects.equals(IterableUtils.get(iterable, j),
																(value = getValue(nvp))),
														NumberUtils.isParsable(value))) {
											//
											continue;
											//
										} // if
											//
										testAndRun(found, () -> {
											//
											throw new IllegalStateException();
											//
										});
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
			final Element e = testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null);
			//
			final Iterable<NameValuePair> nvps = Util.toList(
					Util.filter(Util.stream(queryParams), x -> StringUtils.countMatches(getValue(x), '.') == 2));
			//
			testAndRun(IterableUtils.size(nvps) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final NameValuePair nvp = testAndApply(x -> IterableUtils.size(x) == 1, nvps, x -> IterableUtils.get(x, 0),
					null);
			//
			final Iterable<Field> fs = Util.toList(Util.filter(
					Util.stream(testAndApply(Objects::nonNull, Util.getClass(nvp), FieldUtils::getAllFieldsList, null)),
					x -> Objects.equals(Util.getName(x), VALUE)));
			//
			testAndRun(IterableUtils.size(fs) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			testAndAccept((a, b) -> a != null, nvp,
					getJwt(Util.cast(Iterable.class,
							ObjectMapperUtil.readValue(objectMapper, NodeUtil.attr(e, DATA_READING), Object.class)),
							audioUrl),
					(a, b) -> Narcissus.setField(a,
							testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
							IValue0Util.getValue0(b)));
			//
			return Util.toString(URIBuilderUtil.build(addParameters(clearParameters(uriBuilder), queryParams)));
			//
		} // try
			//
	}

	@Nullable
	private static IValue0<String> getJwt(final Iterable<?> iterable, @Nullable final String url) {
		//
		String string = null;
		//
		IValue0<String> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			if (Boolean.logicalOr(
					StringUtils.countMatches(string = Util.toString(IterableUtils.get(iterable, i)), '.') != 2,
					StringsUtil.contains(Strings.CS, url, string))) {
				//
				continue;
				//
			} // if
				//
			testAndRun(iValue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			iValue0 = Unit.with(string);
			//
		} // for
			//
		return iValue0;
		//
	}

	@Nullable
	private static List<NameValuePair> getQueryParams(@Nullable final URIBuilder instance) {
		return instance != null ? instance.getQueryParams() : null;
	}

	@Nullable
	private static String getValue(@Nullable final NameValuePair instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Nullable
	private static URIBuilder addParameters(@Nullable final URIBuilder instance, final List<NameValuePair> nvps) {
		return instance != null && Util.toArray(nvps) != null ? instance.addParameters(nvps) : instance;
	}

	@Nullable
	private static URIBuilder clearParameters(@Nullable final URIBuilder instance) {
		return instance != null ? instance.clearParameters() : instance;
	}

	@Nullable
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
		if (Objects.equals(source, instance.btnDownloadAudio)) {
			//
			try {
				//
				final JapanDictEntry japanDictEntry = Util.cast(JapanDictEntry.class,
						getValueAt(instance.dtm, getSelectedRow(instance.jTable), 0));
				//
				byte[] bs = JapanDictEntry.getAudioData(japanDictEntry);
				//
				final String userAgent = instance.getUserAgent();
				//
				if ((bs = bs == null ? download(Util.getText(instance.tfAudioUrl), userAgent) : bs) == null) {
					//
					Util.setText(instance.tfAudioUrl, getAudioUrl(japanDictEntry, userAgent));
					//
				} // if
					//
				final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText),
						StringBuilder::new, null);
				//
				testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfHiragana),
						x -> append(append(append(sb, '('), x), ')'));
				//
				testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfKatakana),
						x -> append(append(append(sb, '('), x), ')'));
				//
				testAndAccept(
						x -> Objects.equals(getMessage(
								testAndApply(Objects::nonNull, x, y -> new ContentInfoUtil().findMatch(y), null)),
								"Audio file with ID3 version 2.4, MP3 encoding"),
						bs = bs == null ? download(Util.getText(instance.tfAudioUrl), userAgent) : bs,
						x -> append(append(sb, '.'), "mp3"));
				//
				if (japanDictEntry != null) {
					//
					japanDictEntry.audioData = bs;
					//
				} // if
					//
				final JFileChooser jfc = new JFileChooser();
				//
				jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
				//
				if (and(Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), bs != null),
						() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
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

	private static int getSelectedRow(@Nullable final JTable instance) {
		//
		return instance != null && instance.getSelectionModel() != null ? instance.getSelectedRow() : -1;
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
			if (and(Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(),
					instance.strokeWithNumberBufferedImage != null),
					() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
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
		} else if (Objects.equals(source, instance.btnSaveStrokeImage)) {
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
			if (and(Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), instance.strokeBufferedImage != null),
					() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
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
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed5(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnCopyKatakana)) {
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					new StringSelection(Util.getText(instance.tfKatakana)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyFuriganaImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = instance.furiganaBufferedImage;
			//
			testAndRun(!isTestMode(), () -> Util.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
					Reflection.newProxy(Transferable.class, ih), null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnSaveFuriganaImage)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, Util.getText(instance.tfText), StringBuilder::new,
					null);
			//
			append(sb, "(Furigana)");
			//
			testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfHiragana),
					x -> append(append(append(sb, '('), x), ')'));
			//
			testAndAccept(StringUtils::isNotBlank, Util.getText(instance.tfKatakana),
					x -> append(append(append(sb, '('), x), ')'));
			//
			final String format = "png";
			//
			append(append(sb, '.'), format);
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.toString(sb), Path::of, null)));
			//
			if (and(Util.and(!GraphicsEnvironment.isHeadless(), !isTestMode(), instance.furiganaBufferedImage != null),
					() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
				//
				try {
					//
					ImageIO.write(instance.furiganaBufferedImage, format, jfc.getSelectedFile());
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
		} else if (Objects.equals(source, instance.btnCopyUrl)) {
			//
			testAndRun(!isTestMode(),
					() -> Util
							.setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
									new StringSelection(Util.toString(Link.getUrl(Util.cast(Link.class,
											testAndApply(x -> getRowCount(x) > 0, instance.jTableLink,
													x -> getValueAt(instance.dtmLink, getSelectedRow(x), 0), null))))),
									null));
			//
			return true;
			//
		} else if (Objects.equals(source, instance.btnBrowseUrl)) {
			//
			testAndRun(!isTestMode(),
					() -> browse(Desktop.getDesktop(),
							toURI(Link.getUrl(Util.cast(Link.class,
									testAndApply(x -> getRowCount(x) > 0, instance.jTableLink,
											x -> getValueAt(instance.dtmLink, getSelectedRow(x), 0), null))))),
					JapanDictGui::throwRuntimeException);
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed6(@Nullable final JapanDictGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnPdf)) {
			//
			final PDPage pdPage = new PDPage();
			//
			try (final PDDocument document = new PDDocument();
					final PDPageContentStream pageContentStream = new PDPageContentStream(document, pdPage)) {
				//
				document.addPage(pdPage);
				//
				if (instance.pdFont == null) {
					//
					try (final InputStream is = Util.getResourceAsStream(JapanDictGui.class,
							"/NotoSansCJKjp-Regular.otf")) {
						//
						final byte[] bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
						//
						final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch,
								null);
						//
						try (final ByteArrayInputStream bais = testAndApply(Objects::nonNull, bs,
								ByteArrayInputStream::new, null)) {
							//
							instance.pdFont = testAndApply(Objects::nonNull,
									testAndApply(
											x -> Boolean.logicalAnd(x != null,
													StringsUtil.equals(Strings.CI, getName(ci), "OpenType")),
											bais, new OTFParser()::parseEmbedded, null),
									x -> PDType0Font.load(document, x, false), null);
							//
						} // try
							//
					} // try
						//
				} // if
					//
					// Text
					//
				beginText(pageContentStream);
				//
				final int fontSize = 10;
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				final PDFontDescriptor pdFontDescriptor = PDFontUtil.getFontDescriptor(instance.pdFont);
				//
				final float ascent = PDFontDescriptorUtil.getAscent(pdFontDescriptor, 0);
				//
				final float descent = PDFontDescriptorUtil.getDescent(pdFontDescriptor, 0);
				//
				float pageHeight = PDRectangleUtil.getHeight(PDPageUtil.getMediaBox(pdPage))
						- (ascent / 1000 * fontSize) + (descent / 1000 * fontSize);
				//
				final float width = Util
						.floatValue(
								orElse(Util.max(
										FailableStreamUtil.stream(FailableStreamUtil.map(
												new FailableStream<>(
														Stream.of("Romaji", "Hiragana", "Katakana", "Furigana")),
												x -> Float.valueOf(getTextWidth(x, instance.pdFont, fontSize)))),
										ObjectUtils::compare), null),
								0);
				//
				newLineAtOffset(pageContentStream, width, pageHeight);
				//
				final JapanDictEntry japanDictEntry = Util.cast(JapanDictEntry.class,
						getValueAt(instance.dtm, getSelectedRow(instance.jTable), 0));
				//
				showText(pageContentStream, JapanDictEntry.getText(japanDictEntry));
				//
				endText(pageContentStream);
				//
				// Romaji
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, 0,
						pageHeight = pageHeight - (ascent / 1000 * fontSize) + (descent / 1000 * fontSize));
				//
				showText(pageContentStream, "Romaji");
				//
				endText(pageContentStream);
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, width, pageHeight);
				//
				showText(pageContentStream, japanDictEntry != null ? japanDictEntry.romaji : null);
				//
				endText(pageContentStream);
				//
				// Hiragana
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, 0,
						pageHeight = pageHeight - (ascent / 1000 * fontSize) + (descent / 1000 * fontSize));
				//
				showText(pageContentStream, "Hiragana");
				//
				endText(pageContentStream);
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, width, pageHeight);
				//
				showText(pageContentStream, japanDictEntry != null ? japanDictEntry.hiragana : null);
				//
				endText(pageContentStream);
				//
				// katakana
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, 0,
						pageHeight = pageHeight - (ascent / 1000 * fontSize) + (descent / 1000 * fontSize));
				//
				showText(pageContentStream, "Katakana");
				//
				endText(pageContentStream);
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, width, pageHeight);
				//
				showText(pageContentStream, japanDictEntry != null ? japanDictEntry.katakana : null);
				//
				endText(pageContentStream);
				//
				// Furigana
				//
				beginText(pageContentStream);
				//
				testAndAccept(Objects::nonNull, instance.pdFont, x -> pageContentStream.setFont(x, fontSize));
				//
				newLineAtOffset(pageContentStream, 0,
						pageHeight = pageHeight - (ascent / 1000 * fontSize) + (descent / 1000 * fontSize));
				//
				showText(pageContentStream, "Furigana");
				//
				endText(pageContentStream);
				//
				PDImageXObject pdImageXObject = null;
				//
				try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					//
					testAndAccept((a, b) -> Boolean.logicalAnd(a != null, b != null),
							japanDictEntry != null ? japanDictEntry.furiganaImage : null, baos,
							(a, b) -> ImageIO.write(a, "png", b), null);
					//
					pdImageXObject = testAndApply((a, b) -> b != null && b.length > 0, document, baos.toByteArray(),
							(a, b) -> PDImageXObject.createFromByteArray(a, b, null), null);
					//
				} // try
					//
				drawImage(pageContentStream, pdImageXObject, width,
						pageHeight - PDImageUtil.getHeight(pdImageXObject)
								+ getTextHeight(instance.pdFont, fontSize,
										PDRectangleUtil.getWidth(PDPageUtil.getMediaBox(pdPage)) / 10,
										PDPageUtil.getMediaBox(pdPage)));
				//
				IOUtils.closeQuietly(pageContentStream);
				//
				final File file = Util.toFile(Path.of("test.pdf"));
				//
				System.out.println(Util.getAbsolutePath(file));
				//
				document.save(file);
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // if
			//
		return false;
		//
	}

	private static String getName(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getName() : null;
	}

	private static void drawImage(@Nullable final PDPageContentStream instance, @Nullable final PDImageXObject image,
			final float x, final float y) throws IOException {
		//
		if (instance != null && image != null && image.getStream() != null) {
			//
			instance.drawImage(image, x, y);
			//
		} // if
			//
	}

	private static void newLineAtOffset(@Nullable final PDPageContentStream instance, float tx, float ty)
			throws IOException {
		//
		if (instance != null && Narcissus.getBooleanField(instance, getFieldByName(instance, "inTextMode"))) {
			//
			instance.newLineAtOffset(tx, ty);
			//
		} // if
			//
	}

	private static void beginText(@Nullable final PDPageContentStream instance) throws IOException {
		//
		if (instance != null && Narcissus.getField(instance, getFieldByName(instance, "outputStream")) != null) {
			//
			instance.beginText();
			//
		} // if
			//
	}

	private static void endText(@Nullable final PDPageContentStream instance) throws IOException {
		//
		if (instance != null && Narcissus.getBooleanField(instance, getFieldByName(instance, "inTextMode"))) {
			//
			instance.endText();
			//
		} // if
			//
	}

	private static int getTextHeight(final PDFont font, final float fontSize, final float size,
			final PDRectangle pdRectangle) throws IOException {
		//
		try (final PDDocument pdDocument = new PDDocument()) {
			//
			final PDPage pdPage = testAndApply(Objects::nonNull, pdRectangle, PDPage::new, x -> new PDPage());
			//
			pdDocument.addPage(pdPage);
			//
			try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
				//
				addText(cs, font, fontSize, pdPage, size);
				//
			} // try
				//
			final IntIntPair intIntPair = getMinimumAndMaximumY(
					PDFRendererUtil.renderImage(new PDFRenderer(pdDocument), 0));
			//
			return intIntPair != null ? intIntPair.rightInt() - intIntPair.leftInt() + 1 : 0;
			//
		} // try
			//
	}

	@Nullable
	private static IntIntPair getMinimumAndMaximumY(@Nullable final BufferedImage bi) {
		//
		Color c = null;
		//
		IntIntMutablePair intIntPair = null;
		//
		for (int x = 0; bi != null && Narcissus.getField(bi, getFieldByName(bi, "raster")) != null
				&& x < bi.getWidth(); x++) {
			//
			for (int y = 0; y < bi.getHeight(); y++) {
				//
				if (c == null) {
					//
					c = new Color(bi.getRGB(x, y));
					//
				} else if (!Objects.equals(c, new Color(bi.getRGB(x, y)))) {
					//
					if (intIntPair == null) {
						//
						intIntPair = IntIntMutablePair.of(y, y);
						//
					} else {
						//
						intIntPair.left(Math.min(intIntPair.leftInt(), y));
						//
						intIntPair.right(Math.max(intIntPair.rightInt(), y));
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return intIntPair;
		//
	}

	private static void addText(@Nullable final PDPageContentStream cs, final PDFont font, final float fontSize,
			final PDPage pdPage, final float size) throws IOException {
		//
		PDFontDescriptor pdFontDescriptor = null;
		//
		String value = null;
		//
		Field f = null;
		//
		for (int i = 0; cs != null && i < 10; i++) {
			//
			beginText(cs);
			//
			testAndAccept(Objects::nonNull, font, x -> cs.setFont(x, fontSize));
			//
			newLineAtOffset(cs, i * size + (size - getTextWidth(
					//
					value = Integer.toString(100 - i * 10) + "%"
					//
					, font, fontSize)) / 2, (PDRectangleUtil.getHeight(PDPageUtil.getMediaBox(pdPage)) - size
			//
							- (PDFontDescriptorUtil.getAscent(pdFontDescriptor = PDFontUtil.getFontDescriptor(font), 0)
									/ 1000 * fontSize)
							+ (PDFontDescriptorUtil.getDescent(pdFontDescriptor, 0) / 1000 * fontSize))
			//
			);
			//
			if (f == null) {
				//
				final Collection<Field> fs = Util
						.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(cs))),
								x -> Objects.equals(Util.getName(x), "fontStack")));
				//
				testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
				//
			} // if
				//
			if (f == null || !IterableUtils.isEmpty(Util.cast(Iterable.class, Narcissus.getField(cs, f)))) {
				//
				cs.showText(value);
				//
			} // if
				//
			endText(cs);
			//
		} // for
			//
	}

	@Nullable
	private static <T> T orElse(@Nullable final Optional<T> instance, @Nullable final T defaultValue) {
		return instance != null ? instance.orElse(defaultValue) : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		//
		float width = 0;
		//
		if (testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null), text, getFieldByName(text, VALUE),
				Narcissus::getField, null) != null) {
			//
			for (int i = 0; i < StringUtils.length(text); i++) {
				//
				// Get the width of each character and add it to the total width
				//
				width += getWidth(font, text.charAt(i), 0) / 1000.0f;
				//
			} // for
				//
		} // if
			//
		return width * fontSize;
		//
	}

	private static float getWidth(@Nullable final PDFont instance, final int code, final float defaultValue)
			throws IOException {
		//
		if (instance == null) {
			//
			return defaultValue;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance);
		//
		final String name = Util.getName(clz);
		//
		List<Field> fs = null;
		//
		Field f = null;
		//
		if (Util.contains(Arrays.asList("org.apache.pdfbox.pdmodel.font.PDMMType1Font",
				"org.apache.pdfbox.pdmodel.font.PDTrueTypeFont", "org.apache.pdfbox.pdmodel.font.PDType1CFont",
				"org.apache.pdfbox.pdmodel.font.PDType1Font"), name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "codeToWidthMap")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} else if (Objects.equals("org.apache.pdfbox.pdmodel.font.PDType0Font", name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "descendantFont")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} else if (Objects.equals("org.apache.pdfbox.pdmodel.font.PDType3Font", name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "dict")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} // if
			//
		return instance.getWidth(code);
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static void throwRuntimeException(final Throwable e) {
		//
		throw e instanceof RuntimeException re ? re : new RuntimeException(e);
		//
	}

	private static void showText(@Nullable final PDPageContentStream instance, @Nullable final String text)
			throws IOException {
		//
		if (instance != null
				&& !IterableUtils.isEmpty(
						Util.cast(Iterable.class, Narcissus.getField(instance, getFieldByName(instance, "fontStack"))))
				&& text != null) {
			//
			instance.showText(text);
			//
		} // if
			//
	}

	private static int getRowCount(@Nullable final JTable instance) {
		return instance != null && instance.getModel() != null ? instance.getRowCount() : 0;
	}

	private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "peer")));
		//
		testAndRun(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) != null) {
			//
			instance.browse(uri);
			//
		} // if
			//
	}

	@Nullable
	private static URI toURI(@Nullable final URL instance) throws URISyntaxException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "handler")));
		//
		testAndRun(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && Narcissus.getField(instance, f) != null ? instance.toURI() : null;
		//
	}

	private String getUserAgent() {
		//
		return Objects.toString(
				testAndApply(Util::containsKey, userAgentMap, Util.getSelectedItem(cbmBrowserType), Util::get, null),
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
		//
	}

	@Nullable
	private static byte[] download(final String url, final String userAgent) throws IOException, URISyntaxException {
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class, Util.openConnection(Util.toURL(
				testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x), url, URI::new, null))));
		//
		setRequestProperty(httpURLConnection, USER_AGENT, userAgent);
		//
		if (httpURLConnection == null || !HttpStatus.isSuccess(httpURLConnection.getResponseCode())) {
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
		testAndAccept(Util::containsKey, properties, "org.springframework.context.support.JapanDictGui.text",
				(a, b) -> Util.setText(instance.tfText, Util.toString(Util.get(a, b))));
		//
		add(jFrame, instance);
		//
		pack(instance.window = jFrame);
		//
		Util.setVisible(jFrame, true);
		//
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (Util.test(instance, t, u)) {
			FailableBiConsumerUtil.accept(consumer, t, u);
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
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, lsm) && evt != null && !evt.getValueIsAdjusting() && jTable != null
				&& (selectedIndices = getSelectedIndices(lsm)) != null && selectedIndices.length == 1
				&& (selectedIndex = selectedIndices[0]) < getRowCount(jTable)) {
			//
			valueChanged(this, Util.cast(JapanDictEntry.class, getValueAt(dtm, selectedIndex, 0)));
			//
		} else if (Objects.equals(source, lsmLink)) {
			//
			setEnabled(true, btnCopyUrl, btnBrowseUrl);
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
		final Runnable runnable = () -> {
			//
			throw new IllegalStateException();
			//
		};
		//
		testAndRun(IterableUtils.size(ms) > 1, runnable);
		//
		setStrokeImageAndStrokeWithNumberImage(instance.dtm, entry);
		//
		final Consumer<Throwable> consumer = x -> {
			//
			throw new RuntimeException(x);
			//
		};
		//
		final Function<Playwright, BrowserType> function = x -> ObjectUtils
				.getIfNull(
						Util.cast(BrowserType.class,
								testAndApply(Objects::nonNull,
										testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0),
												null),
										y -> Narcissus.invokeMethod(x, y), null)),
						() -> PlaywrightUtil.chromium(x));
		//
		testAndRun(
				or(entry.furiganaImage == null, entry.strokeImage == null, IterableUtils.isEmpty(entry.pitchAccents)),
				() -> {
					//
					//
					final JapanDictEntrySupplier japanDictEntrySupplier = new JapanDictEntrySupplier();
					//
					japanDictEntrySupplier.browserTypeFunction = function;
					//
					japanDictEntrySupplier.japanDictEntry = entry;
					//
					japanDictEntrySupplier.japanDictGui = instance;
					//
					final JapanDictEntry result = Util.get(japanDictEntrySupplier);
					//
					if (result != null) {
						//
						entry.furiganaImage = ObjectUtils.getIfNull(result.furiganaImage, entry.furiganaImage);
						//
						entry.pitchAccents = ObjectUtils.getIfNull(result.pitchAccents, entry.pitchAccents);
						//
						entry.strokeImage = ObjectUtils.getIfNull(result.strokeImage, entry.strokeImage);
						//
					} // if
						//
				}, consumer);
		//
		// Furigana
		//
		Util.setIcon(instance.furiganaImage, testAndApply(Objects::nonNull,
				instance.furiganaBufferedImage = entry.furiganaImage, ImageIcon::new, null));
		//
		setEnabled(entry.furiganaImage != null, instance.btnCopyFuriganaImage, instance.btnSaveFuriganaImage);
		//
		// Pitch Accents
		//
		Util.forEach(entry.pitchAccents, x -> Util.addElement(instance.mcbmPitchAccent, x));
		//
		setEnabled(!IterableUtils.isEmpty(entry.pitchAccents), instance.btnCopyPitchAccentImage,
				instance.btnSavePitchAccentImage);
		//
		// Stroke Image
		//
		Util.setIcon(instance.strokeImage,
				testAndApply(Objects::nonNull, instance.strokeBufferedImage = entry.strokeImage, ImageIcon::new, null));
		//
		setEnabled(entry.strokeImage != null, instance.btnCopyStrokeImage, instance.btnSaveStrokeImage);
		//
		// Stroke With Number Image
		//
		testAndRun(JapanDictEntry.getStrokeWithNumberImage(entry) == null, () -> {
			//
			final StrokeWithNumberImageSupplier supplier = new StrokeWithNumberImageSupplier();
			//
			supplier.browserTypeFunction = x -> ObjectUtils.getIfNull(
					Util.cast(BrowserType.class,
							testAndApply(Objects::nonNull,
									testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0),
											null),
									y -> Narcissus.invokeMethod(x, y), null)),
					() -> PlaywrightUtil.chromium(x));
			//
			supplier.japanDictEntry = entry;
			//
			supplier.japanDictGui = instance;
			//
			JapanDictEntry.setStrokeWithNumberImage(entry,
					ObjectUtils.getIfNull(JapanDictEntry.getStrokeWithNumberImage(entry), Util.get(supplier)));
			//
			setStrokeImageAndStrokeWithNumberImage(instance.dtm, entry);
			//
		}, consumer);
		//
		Util.setIcon(instance.strokeWithNumberImage,
				testAndApply(Objects::nonNull,
						instance.strokeWithNumberBufferedImage = JapanDictEntry.getStrokeWithNumberImage(entry),
						ImageIcon::new, null));
		//
		setEnabled(JapanDictEntry.getStrokeWithNumberImage(entry) != null, instance.btnCopyStrokeWithNumberImage,
				instance.btnSaveStrokeWithNumberImage);
		//
		// links
		//
		Util.forEach(entry.links, x -> Util.addRow(instance.dtmLink, new Object[] { x }));
		//
		final JTable jTable = instance.jTableLink;
		//
		final Dimension preferredSize = Util.getPreferredSize(jTable);
		//
		setPreferredScrollableViewportSize(instance.jTableLink, new Dimension((int) getWidth(preferredSize),
				(int) Math.min(sum(Util.map(IntStream.range(0, Util.getRowCount(instance.dtmLink)), x ->
				//
				Math.max(getRowHeight(jTable),
						Util.orElse(Util.max(Util.map(IntStream.range(0, getColumnCount(jTable)),
								column -> (int) getHeight(Util.getPreferredSize(
										prepareRenderer(jTable, getCellRenderer(jTable, x, column), x, column))))),
								0))
				//
				)), getHeight(preferredSize))));
		//
		// pdf
		//
		Util.setEnabled(instance.btnPdf, true);
		//
		pack(instance.window);
		//
	}

	private static void setStrokeImageAndStrokeWithNumberImage(final DefaultTableModel dtm,
			@Nullable final JapanDictEntry japanDictEntry) {
		//
		JapanDictEntry temp = null;
		//
		for (int i = 0; japanDictEntry != null && i < Util.getRowCount(dtm); i++) {
			//
			if ((temp = Util.cast(JapanDictEntry.class, getValueAt(dtm, i, 0))) != null
					&& Objects.equals(JapanDictEntry.getId(japanDictEntry), JapanDictEntry.getId(temp))) {
				//
				temp.strokeImage = ObjectUtils.getIfNull(temp.strokeImage, japanDictEntry.strokeImage);
				//
				JapanDictEntry.setStrokeWithNumberImage(temp,
						ObjectUtils.getIfNull(JapanDictEntry.getStrokeWithNumberImage(temp),
								JapanDictEntry.getStrokeWithNumberImage(japanDictEntry)));
				//
			} // if
				//
		} // for
			//
	}

	private static class JapanDictEntrySupplier implements Supplier<JapanDictEntry> {

		private JapanDictGui japanDictGui = null;

		private JapanDictEntry japanDictEntry = null;

		private Function<Playwright, BrowserType> browserTypeFunction = null;

		@Override
		public JapanDictEntry get() {
			//
			final JapanDictEntry result = new JapanDictEntry();
			//
			final String pageUrl = japanDictEntry != null ? japanDictEntry.pageUrl : null;
			//
			try (final Playwright playwright = testAndGet(
					japanDictEntry != null && JapanDictEntry.getStrokeWithNumberImage(japanDictEntry) == null,
					Playwright::create);
					final Browser browser = testAndApply(
							Predicates.always(UrlValidatorUtil.isValid(UrlValidator.getInstance(), pageUrl)),
							playwright,
							x -> BrowserTypeUtil.launch(ObjectUtils.getIfNull(Util.apply(browserTypeFunction, x),
									() -> PlaywrightUtil.chromium(x))),
							null);
					final Page page = newPage(browser)) {
				//
				PageUtil.navigate(page, pageUrl);
				//
				final String id = JapanDictEntry.getId(japanDictEntry);
				//
				final Integer index = japanDictEntry != null ? japanDictEntry.index : null;
				//
				// Furigana
				//
				final BufferedImage bi = toBufferedImage(
						ElementHandleUtil
								.screenshot(testAndApply(y -> IterableUtils.size(y) == 1,
										ElementHandleUtil.querySelectorAll(
												testAndApply(y -> IterableUtils.size(y) > Util.intValue(index, 0),
														PageUtil.querySelectorAll(page,
																String.format("#entry-%1$s ruby", id)),
														y -> IterableUtils.get(y, Util.intValue(index, 0)), null),
												".."),
										y -> IterableUtils.get(y, 0), null)));
				//
				result.furiganaImage = chopImage(bi, getFirstPixelColor(bi, BufferedImage.TYPE_3BYTE_BGR,
						getData(Util.cast(DataBufferByte.class, getDataBuffer(getRaster(bi))))));
				//
				// Pitch Accents
				//
				result.pitchAccents = getPitchAccents(
						ElementHandleUtil
								.querySelectorAll(
										testAndApply(x -> IterableUtils.size(x) > Util.intValue(index, 0),
												PageUtil.querySelectorAll(page, String.format(
														"#entry-%1$s ul li.list-group-item[lang='ja'] .d-flex.p-2",
														id)),
												x -> IterableUtils.get(x, Util.intValue(index, 0)), null),
										"div.d-flex"));
				//
				// Stroke Image
				//
				result.strokeImage = JapanDictGui.chopImage(getStrokeImage(japanDictGui, page, japanDictEntry));
				//
			} catch (final IOException | InterruptedException ex) {
				//
				throwRuntimeException(ex);
				//
			} // try
				//
			return result;
			//
		}

		@Nullable
		private static BufferedImage chopImage(@Nullable final BufferedImage instance, @Nullable final int[] color) {
			//
			final byte[] data = getData(Util.cast(DataBufferByte.class, getDataBuffer(getRaster(instance))));
			//
			int pixelIndex = 0;
			//
			final int width = getWidth(instance);
			//
			int[] xs = null, ys = null;
			//
			for (int x = 0; color != null && data != null && instance != null && x < width; x++) {
				//
				for (int y = 0; y < instance.getHeight(); y++) {
					//
					if (Arrays.equals(color,
							new int[] { data[pixelIndex = (y * width + x) * 3/* 3 bytes per pixel */] & 0xff// blue
									, data[pixelIndex + 1] & 0xff// green
									, data[pixelIndex + 2]// red
							})) {
						//
						continue;
						//
					} // if
						//
					xs = getMinMax(xs, x);
					//
					ys = getMinMax(ys, y);
					//
				} // for
					//
			} // for
				//
			return xs != null && xs.length > 1 && ys != null && ys.length > 1
					? instance.getSubimage(xs[0], ys[0], xs[1] - xs[0], ys[1] - ys[0] + 1)
					: instance;
			//
		}

		@Nullable
		private static int[] getFirstPixelColor(@Nullable final BufferedImage bi, final int type,
				@Nullable final byte[] data) {
			//
			int[] color = null;
			//
			final int width = getWidth(bi);
			//
			int pixelIndex = 0;
			//
			for (int x = 0; bi != null && bi.getType() == type && x < width; x++) {
				//
				if (color != null) {
					//
					break;
					//
				} // if
					//
				for (int y = 0; y < bi.getHeight(); y++) {
					//
					if (color == null) {
						//
						color = data != null
								? new int[] { data[pixelIndex = (y * width + x) * 3/* 3 bytes per pixel */] & 0xff// blue
										, data[pixelIndex + 1] & 0xff// green
										, data[pixelIndex + 2]// red

								}
								: null;
						//
						break;
						//
					} // if
						//
				} // for
					//
			} // for
				//
			return color;
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
										getMimeType(testAndApply(Objects::nonNull, x, new ContentInfoUtil()::findMatch,
												null)),
										"image/"),
								ElementHandleUtil.screenshot(testAndApply(CollectionUtils::isNotEmpty,
										ElementHandleUtil.querySelectorAll(eh, "div"), x -> IterableUtils.get(x, 0),
										null)),
								x -> toBufferedImage(x), null);
						//
					} else {
						//
						(pa = new PitchAccent()).image = testAndApply(
								x -> startsWith(Strings.CS,
										getMimeType(testAndApply(Objects::nonNull, x, new ContentInfoUtil()::findMatch,
												null)),
										"image/"),
								ElementHandleUtil.screenshot(eh), x -> JapanDictGui.chopImage(x, boundingBox), null);
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
					PitchAccent.setType(pa,
							ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil.select(
											testAndApply(Objects::nonNull,
													ElementHandleUtil.getAttribute(IterableUtils.get(ehs, 0),
															"data-bs-content"),
													x -> Jsoup.parse(x, ""), null),
											"p span[class='h5']"),
									x -> IterableUtils.get(x, 0), null)));
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
					testAndAccept((a, b) -> IterableUtils.size(b) == 1, pa, ss,
							(a, b) -> PitchAccent.setType(a, IterableUtils.get(b, 0)));
					//
					if (StringUtils.isBlank(pa.type) && pa.image != null) {
						//
						final BufferedImage bi = pa.image;
						//
						pa.image = chopImage(bi, getFirstPixelColor(bi, bi.getType(),
								getData(Util.cast(DataBufferByte.class, getDataBuffer(getRaster(bi))))));
						//
					} // if
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

	}

	private static class StrokeWithNumberImageSupplier implements Supplier<BufferedImage> {

		private JapanDictGui japanDictGui = null;

		private JapanDictEntry japanDictEntry = null;

		private Function<Playwright, BrowserType> browserTypeFunction = null;

		@Override
		@Nullable
		public BufferedImage get() {
			//
			final String pageUrl = japanDictEntry != null ? japanDictEntry.pageUrl : null;
			//
			try (final Playwright playwright = testAndGet(
					japanDictEntry != null && JapanDictEntry.getStrokeWithNumberImage(japanDictEntry) == null,
					Playwright::create);
					final Browser browser = testAndApply(
							Predicates.always(UrlValidatorUtil.isValid(UrlValidator.getInstance(), pageUrl)),
							playwright,
							x -> BrowserTypeUtil.launch(ObjectUtils.getIfNull(Util.apply(browserTypeFunction, x),
									() -> PlaywrightUtil.chromium(x))),
							null);
					final Page page = newPage(browser)) {
				//
				PageUtil.navigate(page, pageUrl);
				//
				final String id = JapanDictEntry.getId(japanDictEntry);
				//
				// Stroke With Number Image
				//
				check(testAndApply(x -> IterableUtils.size(x) == 1,
						PageUtil.querySelectorAll(page, String.format("#dmak-show-stroke-check-%1$s", id)),
						x -> IterableUtils.get(x, 0), null));
				//
				click(testAndApply(x -> IterableUtils.size(x) == 1,
						PageUtil.querySelectorAll(page, String.format("#dmak-reset-%1$s", id)),
						x -> IterableUtils.get(x, 0), null));
				//
				return chopImage(getStrokeImage(japanDictGui, page, japanDictEntry));
				//
			} catch (final IOException | InterruptedException ex) {
				//
				throw ex instanceof RuntimeException re ? re : new RuntimeException(ex);
				//
			} // try
				//
		}

		private static void check(@Nullable final ElementHandle instance) {
			if (instance != null) {
				instance.check();
			}
		}

	}

	private static int[] getMinMax(@Nullable final int[] ints, final int i) {
		//
		if (ints == null || ints.length == 0 || (ints.length == 1 && i > ints[ints.length - 1])) {
			//
			return ArrayUtils.add(ints, i);
			//
		} else if (i > ints[ints.length - 1]) {
			//
			ints[ints.length - 1] = i;
			//
		} else if (i < ints[0]) {
			//
			ints[0] = i;
			//
		} // if
			//
		return ints;
		//
	}

	private static int getWidth(@Nullable final BufferedImage instance) {
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
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && Narcissus.getField(instance, f) != null ? instance.getWidth() : 0;
		//
	}

	@Nullable
	private static byte[] getData(@Nullable final DataBufferByte instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "theTrackable")));
		//
		testAndRun(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && Narcissus.getField(instance, f) != null ? instance.getData() : null;
		//
	}

	@Nullable
	private static DataBuffer getDataBuffer(@Nullable final Raster instance) {
		return instance != null ? instance.getDataBuffer() : null;
	}

	@Nullable
	private static WritableRaster getRaster(@Nullable final BufferedImage instance) {
		return instance != null ? instance.getRaster() : null;
	}

	private static <E extends Throwable> void testAndRun(final boolean condition, final FailableRunnable<E> runnable,
			final Consumer<Throwable> consumer) {
		//
		if (condition) {
			//
			try {
				//
				FailableRunnableUtil.run(runnable);
				//
			} catch (final Throwable e) {
				//
				Util.accept(consumer, e);
				//
			} // try
		} // if
			//
	}

	private static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = Boolean.logicalOr(a, b);
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static BufferedImage getStrokeImage(final JapanDictGui instance, final Page page,
			@Nullable final JapanDictEntry japanDictEntry) throws IOException, InterruptedException {
		//
		final String id = JapanDictEntry.getId(japanDictEntry);
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
		while (System.currentTimeMillis() - currentTimeMillis < NumberUtils.max(
				toMillis(instance != null ? instance.storkeImageDuration : null, 20000),
				((long) StringUtils.length(JapanDictEntry.getText(japanDictEntry))) * 4100, 0)) {
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