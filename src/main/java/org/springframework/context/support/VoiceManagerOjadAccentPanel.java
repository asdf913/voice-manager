package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.IllegalSelectorException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
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
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilderUtil;
import org.apache.jena.atlas.RuntimeIOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentUtil;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDPageUtil;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.util.Matrix;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.ImageWriterSpiFormatIterableFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Strings;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;

import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOjadAccentPanel extends JPanel implements InitializingBean, ActionListener, KeyListener {

	private static final long serialVersionUID = -3247760984161467171L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerOjadAccentPanel.class);

	private static final String CSS_SELECTOR_MIDASHI = ".midashi";

	private static final String CANVAS = "canvas";

	private static final String COPY = "Copy";

	private static final String DOWNLOAD = "Download";

	private static final String PLAY = "Play";

	private static final String RASTER = "raster";

	private static final String CURVE = "curve";

	private static final String VALUE = "value";

	private static final String THEAD = "thead";

	private static final String CLASS = "class";

	private static final String GENDER = "Gender";

	//
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Input Text")
	private JTextComponent tfTextInput = null;

	@Note("Kanji")
	private JTextComponent tfKanji = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Part Of Speech")
	private JTextComponent tfPartOfSpeech = null;

	@Note("Conjugation")
	private JTextComponent tfConjugation = null;

	private JTextComponent tfIndex = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Part Of Speech")
	private AbstractButton btnCopyPartOfSpeech = null;

	@Note("Copy Conjugation")
	private AbstractButton btnCopyConjugation = null;

	@Note("Copy Text")
	private AbstractButton btnCopyKanji = null;

	@Note("Copy Hiragana")
	private AbstractButton btnCopyHiragana = null;

	@Note("Copy Accent Image")
	private AbstractButton btnCopyAccentImage = null;

	@Note("Copy Curve Image")
	private AbstractButton btnCopyCurveImage = null;

	@Note("Save Accent Image")
	private AbstractButton btnSaveAccentImage = null;

	@Note("Save Curve Image")
	private AbstractButton btnSaveCurveImage = null;

	private AbstractButton btnPdf = null;

	@Note("Accent")
	private JLabel lblAccent = null;

	@Note("Curve")
	private JLabel lblCurve = null;

	private JLabel lblCategory, lblCurveSearch, lblCount = null;

	private Window window = null;

	@Note("品詞")
	private transient ComboBoxModel<Entry<String, String>> cbmCategory = null;

	private transient ComboBoxModel<Entry<String, String>> cbmCurve = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private transient ComboBoxModel<Boolean> cbmCompression = null;

	private DefaultTableModel dtmVoice = null;

	private JTable jtVoice = null;

	private static class TextAndImage {

		@Note("Kanji")
		private String kanji = null;

		@Note("Hiragana")
		private String hiragana = null;

		@Note("Part Of Speech")
		private String partOfSpeech = null;

		@Note("Conjugation")
		private String conjugation = null;

		private String id = null;

		@Note("Accent Image")
		private BufferedImage accentImage = null;

		@Note("Accent Image Width")
		private Integer accentImageWidth = null;

		@Note("Curve Image")
		private BufferedImage curveImage = null;

		@Note("Curve Image Width")
		private Integer curveImageWidth = null;

		private Map<String, byte[]> voiceUrlImages = null;

		private Integer getAccentImageWidth() {
			//
			return accentImageWidth = ObjectUtils.getIfNull(accentImageWidth, () -> getWidth(accentImage));
			//
		}

		@Nullable
		private static Integer getWidth(final RenderedImage image) {
			//
			final List<Field> fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(image), FieldUtils::getAllFieldsList, null)),
					f -> Objects.equals(Util.getName(f), RASTER)));
			//
			testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final Field f = testAndApply(x -> IterableUtils.size(fs) == 1, fs, x -> IterableUtils.get(fs, 0), null);
			//
			return f != null && Narcissus.getField(image, f) != null ? Integer.valueOf(image.getWidth()) : null;
			//
		}

		private Integer getCurveImageWidth() {
			//
			return curveImageWidth = ObjectUtils.getIfNull(curveImageWidth, () -> getWidth(curveImage));
			//
		}

	}

	private JComboBox<TextAndImage> jcbTextAndImage = null;

	private JComboBox<Entry<String, ? extends Image>> jcbLanguage = null;

	private transient MutableComboBoxModel<TextAndImage> mcbmTextAndImage = null;

	private String url = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		url = ObjectUtils.getIfNull(url, "https://www.gavo.t.u-tokyo.ac.jp/ojad/search/index");// TODO
		//
		setLayout(new MigLayout());// TODO
		//
		Field f = getFieldByName(Util.getClass(this), "component");
		//
		if (f == null || Narcissus.getField(this, f) != null) {
			//
			add(new JLabel());
			//
			final TextStringBuilder tsb = new TextStringBuilder(url);
			//
			testAndAccept((a, b) -> length(b) > 2, tsb, StringUtils.split(url, '/'),
					(a, b) -> TextStringBuilderUtil.append(TextStringBuilderUtil.clear(a),
							StringUtils.joinWith("//", (Object[]) ArrayUtils.subarray(b, 0, 2))));
			//
			String html = null;
			//
			try (final InputStream is = testAndGet(!isTestMode(), () -> Util.openStream(Util.toURL(
					URIBuilderUtil.build(new URIBuilder(Util.toString(tsb)).setPath("ojad/search/index/word:")))),
					null)) {
				//
				html = testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null);
				//
			} // try
				//
			final Document document = testAndApply(Objects::nonNull, html, x -> Jsoup.parse(x, Util.toString(tsb)),
					null);
			//
			List<Element> es = ElementUtil.select(document, ".flags_ul li a");
			//
			final DefaultComboBoxModel<Entry<String, ? extends Image>> dcbmUrlImage = new DefaultComboBoxModel<>();
			//
			testAndAccept(Objects::nonNull, Util
					.toList(FailableStreamUtil.stream(FailableStreamUtil.map(new FailableStream<>(Util.stream(es)), x ->
					//
					Pair.of(NodeUtil.absUrl(x, "href"),
							toBufferedImage(
									toByteArray(new URL(NodeUtil.absUrl(testAndApply(y -> IterableUtils.size(y) > 0,
											ElementUtil.select(x, "img"), y -> IterableUtils.get(y, 0), null), "src"))),
									e -> LoggerUtil.error(LOG, e.getMessage(), e)))
					//
					))), dcbmUrlImage::addAll);
			//
			final ListCellRenderer<? super Entry<String, ? extends Image>> lcr = (jcbLanguage = new JComboBox<>(
					dcbmUrlImage)).getRenderer();
			//
			jcbLanguage.addActionListener(this);
			//
			final Dimension preferredSize = Util.getPreferredSize(jcbLanguage);
			//
			jcbLanguage.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
				//
				final Component component = Util.getListCellRendererComponent(lcr, list, value, index, isSelected,
						cellHasFocus);
				//
				final JLabel jLabel = Util.cast(JLabel.class, component);
				//
				Util.setText(jLabel, null);
				//
				if (preferredSize != null) {
					//
					setIcon(jLabel, testAndApply(Objects::nonNull,
							getScaledInstance(Util.getValue(value), Math.min((int) preferredSize.getWidth(), 17),
									Math.min((int) preferredSize.getHeight(), 17), Image.SCALE_DEFAULT),
							ImageIcon::new, null));
					//
				} // if
					//
				return component;
				//
			});
			//
			testAndAccept(x -> Util.getSize(getModel(x)) > 0, jcbLanguage, x -> Util.setSelectedIndex(x, 0));
			//
			final String wrap = "wrap";
			//
			add(jcbLanguage, wrap);
			//
			add(new JLabel("Text"));
			//
			final String growx = "growx";
			//
			final int span = 2;
			//
			add(tfTextInput = new JTextField(), String.format("%1$s,%2$s,span %3$s", wrap, growx, span));
			//
			// 品詞
			//
			testAndRunThrows(IterableUtils.size(es = ElementUtil.select(document, "[id=\"search_category\"]")) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			Element element = testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null);
			//
			add(lblCategory = new JLabel(ElementUtil.text(ElementUtil.previousElementSibling(element))));
			//
			es = ElementUtil.select(element, "option");
			//
			DefaultComboBoxModel<Entry<String, String>> dcbm = new DefaultComboBoxModel<>();
			//
			Element e = null;
			//
			for (int i = 0; i < IterableUtils.size(es); i++) {
				//
				dcbm.addElement(
						Pair.of(Util.getValue(attribute(e = IterableUtils.get(es, i), VALUE)), ElementUtil.text(e)));
				//
			} // for
				//
			this.cbmCategory = dcbm;
			//
			final JComboBox<Entry<String, String>> jcbCategory = new JComboBox<>(dcbm);
			//
			jcbCategory.setRenderer(createListCellRenderer(jcbCategory.getRenderer()));
			//
			add(jcbCategory, String.format("%1$s,span %2$s", wrap, 2));
			//
			// ピッチカーブ
			//
			testAndRunThrows(IterableUtils.size(es = ElementUtil.select(document, "[id=\"search_curve\"]")) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			add(lblCurveSearch = new JLabel(ElementUtil.text(ElementUtil.previousElementSibling(
					element = testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null)))));
			//
			es = ElementUtil.select(element, "option");
			//
			dcbm = new DefaultComboBoxModel<>();
			//
			for (int i = 0; i < IterableUtils.size(es); i++) {
				//
				dcbm.addElement(
						Pair.of(Util.getValue(attribute(e = IterableUtils.get(es, i), VALUE)), ElementUtil.text(e)));
				//
			} // for
				//
			this.cbmCurve = dcbm;
			//
			final JComboBox<Entry<String, String>> jcbCurve = new JComboBox<>(dcbm);
			//
			jcbCurve.setRenderer(createListCellRenderer(jcbCurve.getRenderer()));
			//
			add(jcbCurve, String.format("%1$s,span %2$s", wrap, 2));
			//
			add(new JLabel());
			//
			// 実行
			//
			testAndRunThrows(IterableUtils.size(es = ElementUtil.select(document, "[type=\"submit\"]")) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			add(btnExecute = new JButton(NodeUtil.attr(
					testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null), VALUE)),
					String.format("%1$s,span %2$s", wrap, 2));
			//
			add(new JLabel("Text And Image"));
			//
			// Index And Count
			//
			final JPanel paneIC = new JPanel();
			//
			paneIC.add(tfIndex = new JTextField());
			//
			tfIndex.setPreferredSize(new Dimension(19, (int) getHeight(Util.getPreferredSize(tfIndex))));
			//
			paneIC.add(new JLabel("/"));
			//
			paneIC.add(lblCount = new JLabel());
			//
			add(paneIC);
			//
			add(jcbTextAndImage = new JComboBox<>(
					mcbmTextAndImage = new DefaultComboBoxModel<>(new TextAndImage[] { null })),
					String.format("%1$s,%2$s,span %3$s", wrap, growx, span));
			//
			jcbTextAndImage.addActionListener(this);
			//
			jcbTextAndImage.setRenderer(createTextAndImageListCellRenderer(tfTextInput));
			//
			// Text
			//
			final JPanel panalText = new JPanel();
			//
			panalText.setLayout(new MigLayout());
			//
			panalText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Text"));
			//
			panalText.add(new JLabel("Part of Speech"));
			//
			panalText.add(tfPartOfSpeech = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyPartOfSpeech = new JButton(COPY), wrap);
			//
			panalText.add(new JLabel("Conjugation"));
			//
			panalText.add(tfConjugation = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyConjugation = new JButton(COPY), wrap);
			//
			panalText.add(new JLabel("Kanji"));
			//
			panalText.add(tfKanji = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyKanji = new JButton(COPY), wrap);
			//
			panalText.add(new JLabel("Hiragana"));
			//
			panalText.add(tfHiragana = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyHiragana = new JButton(COPY), wrap);
			//
			add(panalText, String.format("span %1$s,%2$s,%3$s", 3, growx, wrap));
			//
			// Image
			//
			final JPanel panelImage = new JPanel();
			//
			panelImage.setLayout(new MigLayout());
			//
			panelImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Image"));
			//
			panelImage.add(new JLabel("Format"));
			//
			final List<String> list = testAndApply(Objects::nonNull, FactoryBeanUtil.getObject(
					//
					new ImageWriterSpiFormatIterableFactoryBean()// TODO
			//
			), IterableUtils::toList, null);
			//
			Util.sort(list, createComparatorByOrder(
					//
					Arrays.asList("png", "jpeg", "gif")// TODO
			//
			));
			//
			panelImage.add(
					new JComboBox<>(cbmImageFormat = new DefaultComboBoxModel<>(Util.toArray(list, new String[] {}))),
					wrap);
			//
			panelImage.add(new JLabel("Accent"));
			//
			panelImage.add(lblAccent = new JLabel(), String.format("%1$s,span %2$s", wrap, span));
			//
			panelImage.add(new JLabel());
			//
			JPanel panel = new JPanel();
			//
			panel.add(btnCopyAccentImage = new JButton(COPY));
			//
			panel.add(btnSaveAccentImage = new JButton("Save"));
			//
			panelImage.add(panel, wrap);
			//
			panelImage.add(new JLabel("Curve"));
			//
			panelImage.add(lblCurve = new JLabel(), String.format("%1$s,span %2$s", wrap, span));
			//
			panelImage.add(new JLabel());
			//
			(panel = new JPanel()).add(btnCopyCurveImage = new JButton(COPY));
			//
			panel.add(btnSaveCurveImage = new JButton("Save"));
			//
			panelImage.add(panel, wrap);
			//
			add(panelImage, String.format("span %1$s,%2$s,%3$s", 3, growx, wrap));
			//
			// Voice
			//
			final JPanel panelVoice = new JPanel();
			//
			panelVoice.setLayout(new MigLayout());
			//
			panelVoice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Voice"));
			//
			setMaxWidth((jtVoice = new JTable(
					dtmVoice = createDefaultTableModel(new Object[] { GENDER, "URL", COPY, DOWNLOAD, PLAY }, 0)))
					.getColumn(GENDER), 44);
			//
			setMaxWidth(jtVoice.getColumn(COPY), 37);
			//
			setMaxWidth(jtVoice.getColumn(DOWNLOAD), 67);
			//
			setMaxWidth(jtVoice.getColumn(PLAY), 33);
			//
			final IH ih = new IH();
			//
			ih.actionListener = this;
			//
			final Object proxy = Proxy.newProxyInstance(getClass().getClassLoader(),
					new Class<?>[] { TableCellRenderer.class, TableCellEditor.class }, ih);
			//
			testAndAccept(Objects::nonNull, Util.cast(TableCellRenderer.class, proxy), tcr -> Util
					.forEach(Stream.of(byte[].class, String.class), x -> jtVoice.setDefaultRenderer(x, tcr)));
			//
			testAndAccept((a, b) -> b instanceof TableCellEditor, jtVoice, proxy,
					(a, b) -> jtVoice.setDefaultEditor(String.class, Util.cast(TableCellEditor.class, b)));
			//
			panelVoice.add(new JScrollPane(jtVoice), String.format("hmax %1$s", 56));
			//
			add(panelVoice, String.format("span %1$s,%2$s,%3$s", 3, growx, wrap));
			//
			// PDF
			//
			final JPanel panelPdf = new JPanel();
			//
			panelPdf.setLayout(new MigLayout());
			//
			panelPdf.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "PDF"));
			//
			panelPdf.add(new JLabel("Compression"));
			//
			panelPdf.add(
					new JComboBox<>(
							cbmCompression = new DefaultComboBoxModel<>(
									ArrayUtils
											.addFirst(
													Util.toArray(Util.collect(
															Util.map(
																	Util.filter(
																			testAndApply(
																					Objects::nonNull,
																					Util.getDeclaredFields(
																							Boolean.class),
																					Arrays::stream, null),
																			x -> Boolean.logicalAnd(
																					Objects.equals(Util.getType(x),
																							Boolean.class),
																					Util.isStatic(x))),
																	x -> Util.cast(Boolean.class,
																			Narcissus.getStaticField(x))),
															Collectors.toList()), new Boolean[] {}),
													null))),
					wrap);
			//
			panelPdf.add(new JLabel());
			//
			panelPdf.add(btnPdf = new JButton("PDF"));
			//
			add(panelPdf, String.format("span %1$s,%2$s", 3, growx));
			//
			final List<Field> fs = Util
					.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManagerOjadAccentPanel.class),
									Arrays::stream, null),
							x -> Util.isAssignableFrom(AbstractButton.class, Util.getType(x))));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if ((f = IterableUtils.get(fs, i)) == null) {
					//
					continue;
					//
				} // if
					//
				Util.addActionListener(Util.cast(AbstractButton.class, Narcissus.getField(this, f)), this);
				//
			} // for
				//
			Util.forEach(
					Stream.of(btnCopyPartOfSpeech, btnCopyConjugation, btnCopyKanji, btnCopyHiragana,
							btnCopyAccentImage, btnCopyCurveImage, btnSaveAccentImage, btnSaveCurveImage, btnPdf),
					x -> Util.setEnabled(x, false));
			//
			Util.forEach(Stream.of(tfIndex, tfPartOfSpeech, tfConjugation, tfKanji, tfHiragana),
					x -> Util.setEditable(x, false));
			//
			Util.forEach(Stream.of(tfTextInput, btnExecute, tfIndex), x -> addKeyListener(x, this));
			//
		} // if
			//
	}

	private static DefaultTableModel createDefaultTableModel(final Object[] columnNames, final int rowCount) {
		//
		return new DefaultTableModel(columnNames, rowCount) {

			private static final long serialVersionUID = -3821080690688708407L;

			@Override
			public Class<?> getColumnClass(final int columnIndex) {
				//
				final String columnName = getColumnName(columnIndex);
				//
				if (Objects.equals(columnName, GENDER)) {
					//
					return byte[].class;
					//
				} else if (Util.contains(Arrays.asList(COPY, DOWNLOAD, PLAY), columnName)) {
					//
					return String.class;
					//
				} // if
					//
				return super.getColumnClass(columnIndex);
				//
			}

		};
		//
	}

	@Nullable
	private static Image getScaledInstance(@Nullable final Image instance, final int width, final int height,
			final int hints) {
		//
		if (instance == null || width <= 0 || height <= 0) {
			//
			return null;
			//
		} // if
			//
		final List<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), RASTER)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return (f == null || Narcissus.getField(instance, f) != null) ? instance.getScaledInstance(width, height, hints)
				: null;
		//
	}

	private static ListCellRenderer<? super Entry<String, String>> createListCellRenderer(
			final ListCellRenderer<? super Entry<String, String>> lcr) {
		//
		return (list, value, index, isSelected, cellHasFocus) -> {
			//
			final Component component = Util.getListCellRendererComponent(lcr, list, value, index, isSelected,
					cellHasFocus);
			//
			Util.setText(Util.cast(JLabel.class, component), Util.getValue(value));
			//
			return component;
			//
		};
		//
	}

	private static void addKeyListener(@Nullable final Component instance, final KeyListener keyListener) {
		if (instance != null) {
			instance.addKeyListener(keyListener);
		}
	}

	private static <E> Comparator<E> createComparatorByOrder(final List<?> order) {
		//
		return (a, b) -> {
			//
			final int ia = indexOf(order, a);
			//
			final int ib = indexOf(order, b);
			//
			if (ia >= 0 && ib >= 0) {
				//
				return Integer.compare(ia, ib);
				//
			} else if (ia >= 0) {
				//
				return -1;
				//
			} // if
				//
			return 0;
			//
		};
		//
	}

	private static void setMaxWidth(@Nullable final TableColumn instance, final int maxWidth) {
		if (instance != null) {
			instance.setMaxWidth(maxWidth);
		}
	}

	private static int indexOf(@Nullable final List<?> instance, final Object item) {
		return instance != null ? instance.indexOf(item) : -1;
	}

	private static ListCellRenderer<? super TextAndImage> createTextAndImageListCellRenderer(
			final Component component) {
		//
		return (list, value, index, isSelected, cellHasFocus) -> {
			//
			final JPanel panel = new JPanel();
			//
			final ListModel<? extends TextAndImage> model = Util.getModel(list);
			//
			panel.setLayout(new MigLayout());
			//
			final Dimension2D preferredSize = Util.getPreferredSize(panel);
			//
			if (preferredSize != null) {
				//
				if (Util.getSize(Util.getModel(list)) == 1) {
					//
					panel.setPreferredSize(new Dimension((int) preferredSize.getWidth(),
							(int) getHeight(Util.getPreferredSize(component))));
					//
				} else {
					//
					// TODO
					//
					if (value == null) {
						//
						panel.setPreferredSize(new Dimension((int) preferredSize.getWidth(),
								Math.max((int) getHeight(Util.getPreferredSize(component)), 26)));
						//
					} // if
						//
				} // if
					//
			} // if
				//
			panel.add(
					new JLabel(
							StringUtils
									.rightPad(getKanji(value),
											Util.orElse(
													Util.max(Util.map(IntStream.range(0, Util.getSize(model)),
															i -> StringUtils
																	.length(getKanji(Util.getElementAt(model, i))))),
													0),
											'\u3000')),
					"left");
			//
			panel.add(
					new JLabel(
							StringUtils
									.rightPad(getHiragana(value),
											Util.orElse(
													Util.max(Util.map(IntStream.range(0, Util.getSize(model)),
															i -> StringUtils
																	.length(getHiragana(Util.getElementAt(model, i))))),
													0),
											'\u3000')),
					"left");
			//
			JLabel label = new JLabel();
			//
			label.setIcon(testAndApply(Objects::nonNull, getAccentImage(value), ImageIcon::new, x -> new ImageIcon()));
			//
			panel.add(label, String.format("right,wmin %1$s",
					Util.orElse(Util.max(Util.map(IntStream.range(0, Util.getSize(model)), i -> {
						//
						final TextAndImage textAndImage = Util.getElementAt(model, i);
						//
						return Util.intValue(getAccentImageWidth(textAndImage), 0);
						//
					})), 0)));
			//
			(label = new JLabel()).setIcon(
					testAndApply(Objects::nonNull, getCurveImage(value), ImageIcon::new, x -> new ImageIcon()));
			//
			panel.add(label, "right");
			//
			return panel;
			//
		};
		//
	}

	@Nullable
	private static Attribute attribute(@Nullable final Element instance, final String key) {
		return instance != null ? instance.attribute(key) : null;
	}

	private static double getHeight(@Nullable final Dimension2D instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getKanji(@Nullable final TextAndImage instance) {
		return instance != null ? instance.kanji : null;
	}

	@Nullable
	private static String getHiragana(@Nullable final TextAndImage instance) {
		return instance != null ? instance.hiragana : null;
	}

	@Nullable
	private static String getPartOfSpeech(@Nullable final TextAndImage instance) {
		return instance != null ? instance.partOfSpeech : null;
	}

	private static void setPartOfSpeech(@Nullable final TextAndImage instance, final String partOfSpeech) {
		if (instance != null) {
			instance.partOfSpeech = partOfSpeech;
		}
	}

	@Nullable
	private static BufferedImage getAccentImage(@Nullable final TextAndImage instance) {
		return instance != null ? instance.accentImage : null;
	}

	@Nullable
	private static Integer getAccentImageWidth(@Nullable final TextAndImage instance) {
		return instance != null ? instance.getAccentImageWidth() : null;
	}

	@Nullable
	private static BufferedImage getCurveImage(@Nullable final TextAndImage instance) {
		return instance != null ? instance.curveImage : null;
	}

	@Nullable
	private static Integer getCurveImageWidth(@Nullable final TextAndImage instance) {
		return instance != null ? instance.getCurveImageWidth() : null;
	}

	@Nullable
	private static Map<String, byte[]> getVoiceUrlImages(@Nullable final TextAndImage instance) {
		return instance != null ? instance.voiceUrlImages : null;
	}

	@Nullable
	private static String getConjugation(@Nullable final TextAndImage instance) {
		return instance != null ? instance.conjugation : null;
	}

	private static Field getFieldByName(final Class<?> clz, final String fieldName) {
		//
		final List<Field> fs = Util.toList(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getName(f), fieldName)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean condition,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (condition) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class IH implements InvocationHandler {

		private Image image = null;

		private ActionListener actionListener = null;

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			if (ReflectionUtils.isEqualsMethod(method)) {
				//
				return false;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (Boolean.logicalAnd(proxy instanceof CellEditor,
					Util.contains(Arrays.asList("isCellEditable", "stopCellEditing", "shouldSelectCell",
							"addCellEditorListener", "removeCellEditorListener"), methodName))) {
				//
				return true;
				//
			} // if
				//
			if (proxy instanceof Transferable) {
				//
				if (Objects.equals(methodName, "getTransferDataFlavors")) {
					//
					return new DataFlavor[] { DataFlavor.imageFlavor };
					//
				} else if (Objects.equals(methodName, "getTransferData")) {
					//
					return image;
					//
				} // if
					//
			} // if
				//
			final Iterable<TriFunction<Object, String, Object[], IValue0<?>>> functions = Arrays.asList(this::invoke1,
					this::invoke2);
			//
			IValue0<?> iValue0 = null;
			//
			for (int i = 0; i < IterableUtils.size(functions); i++) {
				//
				if ((iValue0 = TriFunctionUtil.apply(IterableUtils.get(functions, i), proxy, methodName,
						args)) != null) {
					//
					return IValue0Util.getValue0(iValue0);
					//
				} // if
					//
			} // for
				//
			throw new Throwable(methodName);
			//
		}

		@Nullable
		private IValue0<?> invoke1(final Object proxy, final String methodName, @Nullable final Object... args) {
			//
			if (proxy instanceof TableCellEditor && Objects.equals(methodName, "getTableCellEditorComponent")
					&& args != null && args.length > 4) {
				//
				final JTable jTable = Util.cast(JTable.class, ArrayUtils.get(args, 0));
				//
				final Integer row = Util.cast(Integer.class, ArrayUtils.get(args, 3));
				//
				final Integer column = Util.cast(Integer.class, ArrayUtils.get(args, 4));
				//
				final String columnName = column != null ? getColumnName(jTable, column.intValue()) : null;
				//
				if (column != null && Util.contains(Arrays.asList(COPY, DOWNLOAD, PLAY), columnName)) {
					//
					final JButton button = new JButton(columnName);
					//
					button.setMargin(new Insets(0, 0, 0, 0));
					//
					button.setActionCommand(StringUtils.joinWith(",", columnName,
							row != null ? getValueAt(jTable, row, column) : null));
					//
					button.addActionListener(actionListener);
					//
					return Unit.with(button);
					//
				} // if
					//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		@Nullable
		private IValue0<?> invoke2(final Object proxy, final String methodName, @Nullable final Object... args) {
			//
			if (proxy instanceof TableCellRenderer && Objects.equals(methodName, "getTableCellRendererComponent")
					&& args != null && args.length > 5) {
				//
				final Object value = ArrayUtils.get(args, 1);
				//
				final byte[] bs = Util.cast(byte[].class, value);
				//
				if (bs != null) {
					//
					try (final InputStream is = new ByteArrayInputStream(bs)) {
						//
						return Unit.with(testAndApply(Objects::nonNull,
								testAndApply(Objects::nonNull, ImageIO.read(is), ImageIcon::new, null), JLabel::new,
								x -> new JLabel()));
						//
					} catch (final IOException ioe) {
						//
						throw new RuntimeIOException(ioe);
						//
					} // try
						//
				} // if
					//
				final Integer column = Util.cast(Integer.class, ArrayUtils.get(args, 5));
				//
				final String columnName = column != null
						? getColumnName(Util.cast(JTable.class, ArrayUtils.get(args, 0)), column.intValue())
						: null;
				//
				if (Util.contains(Arrays.asList(COPY, DOWNLOAD, PLAY), columnName)) {
					//
					final JButton button = new JButton(columnName);
					//
					button.setMargin(new Insets(0, 0, 0, 0));
					//
					return Unit.with(button);
					//
				} // if
					//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		@Nullable
		private static Object getValueAt(@Nullable final JTable instance, final int row, final int column) {
			return instance != null ? instance.getValueAt(row, column) : null;
		}

		@Nullable
		private static String getColumnName(@Nullable final JTable instance, final int column) {
			return instance != null ? instance.getColumnName(column) : null;
		}

	}

	@Override
	public void actionPerformed(@Nullable final ActionEvent evt) {
		//
		if (actionPerformed(getActionCommand(evt))) {
			//
			return;
			//
		} // if
			//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedBtnExecute();
			//
		} else if (Objects.equals(source, jcbTextAndImage)) {
			//
			if (jcbTextAndImage != null) {
				//
				Util.setText(tfIndex,
						testAndApply(x -> x >= 0, jcbTextAndImage.getSelectedIndex(), x -> Integer.toString(x), null));
				//
				pack(window);
				//
			} // if
				//
			final TextAndImage textAndImage = Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage));
			//
			// Part Of Speech
			//
			Util.accept(x -> {
				//
				Util.setText(tfPartOfSpeech, x);
				//
				Util.setEnabled(btnCopyPartOfSpeech, StringUtils.isNotBlank(x));
				//
			}, getPartOfSpeech(textAndImage));
			//
			// Conjugation
			//
			Util.accept(x -> {
				//
				Util.setText(tfConjugation, x);
				//
				Util.setEnabled(btnCopyConjugation, StringUtils.isNotBlank(x));
				//
			}, getConjugation(textAndImage));
			//
			// Kanji
			//
			Util.accept(x -> {
				//
				Util.setText(tfKanji, x);
				//
				Util.setEnabled(btnCopyKanji, StringUtils.isNotBlank(x));
				//
			}, getKanji(textAndImage));
			//
			// Hiragana
			//
			Util.accept(x -> {
				//
				Util.setText(tfHiragana, x);
				//
				Util.setEnabled(btnCopyHiragana, StringUtils.isNotBlank(x));
				//
			}, getHiragana(textAndImage));
			//
			// Accent Image
			//
			Util.accept(image -> {
				//
				setIcon(lblAccent, testAndApply(Objects::nonNull, image, ImageIcon::new, x -> new ImageIcon()));
				//
				Util.forEach(Stream.of(btnCopyAccentImage, btnSaveAccentImage), x -> Util.setEnabled(x, image != null));
				//
			}, getAccentImage(textAndImage));
			//
			// Curve Image
			//
			Util.accept(image -> {
				//
				setIcon(lblCurve, testAndApply(Objects::nonNull, image, ImageIcon::new, x -> new ImageIcon()));
				//
				Util.forEach(Stream.of(btnCopyCurveImage, btnSaveCurveImage), x -> Util.setEnabled(x, image != null));
				//
			}, getCurveImage(textAndImage));
			//
			// Voice
			//
			Util.forEach(Util.map(Util.sorted(Util.map(IntStream.rangeClosed(0, Util.getRowCount(dtmVoice)), i -> -i)),
					i -> -i), i -> Util.removeRow(dtmVoice, i));
			//
			removeAll(jtVoice);
			//
			if (textAndImage != null) {
				//
				Util.forEach(Util.entrySet(getVoiceUrlImages(textAndImage)), en -> {
					//
					final String key = Util.getKey(en);
					//
					Util.addRow(dtmVoice, new Object[] { Util.getValue(en), key, key, key, key });
					//
				});
				//
			} // if
				//
				// PDF
				//
			Util.setEnabled(btnPdf, textAndImage != null);
			//
			pack(window);
			//
		} else if (Objects.equals(source, jcbLanguage)) {
			//
			String html = null;
			//
			final Entry<?, ?> entry = Util.cast(Entry.class, Util.getSelectedItem(jcbLanguage));
			//
			try (final InputStream is = testAndGet(!isTestMode(),
					() -> Util.openStream(Util.toURL(new URI(Util.toString(Util.getKey(entry))))), null)) {
				//
				html = testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null);
				//
			} catch (final Exception e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			final Document document = testAndApply(Objects::nonNull, html, x -> Jsoup.parse(x), null);
			//
			List<Element> es = ElementUtil.select(document, "[id=\"search_category\"]");
			//
			// 品詞
			//
			testAndRunThrows(IterableUtils.size(es) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			Util.setText(lblCategory, ElementUtil.text(ElementUtil.previousElementSibling(
					testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null))));
			//
			// ピッチカーブ
			//
			testAndRunThrows(IterableUtils.size(es = ElementUtil.select(document, "[id=\"search_curve\"]")) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			Util.setText(lblCurveSearch, ElementUtil.text(ElementUtil.previousElementSibling(
					testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null))));
			//
			// 実行
			//
			testAndRunThrows(IterableUtils.size(es = ElementUtil.select(document, "[type=\"submit\"]")) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (btnExecute != null) {
				//
				btnExecute.setText(NodeUtil.attr(
						testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0), null), VALUE));
				//
			} // if
				//
		} else if (Objects.equals(source, btnSaveAccentImage)) {
			//
			final String format = Util.toString(Util.getSelectedItem(cbmImageFormat));
			//
			saveImage(getAccentImage(Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage))),
					() -> Util
							.toFile(Path.of(String.format("%1$s(%2$s).%3$s", Util.getText(tfKanji), "Accent", format))),
					format);
			//
		} else if (Objects.equals(source, btnSaveCurveImage)) {
			//
			final String format = Util.toString(Util.getSelectedItem(cbmImageFormat));
			//
			saveImage(getCurveImage(Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage))),
					() -> Util
							.toFile(Path.of(String.format("%1$s(%2$s).%3$s", Util.getText(tfKanji), "Curve", format))),
					format);
			//
		} else if (Objects.equals(source, btnPdf)) {
			//
			try {
				//
				actionPerformedBtnPdf(this, Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage)));
				//
			} catch (final IOException | TemplateException | ReflectiveOperationException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // if
			//
		final Supplier<Clipboard> supplier = () -> getClipboard();
		//
		final Map<Object, JTextComponent> objectJTextComponentMap = new LinkedHashMap<>(
				Collections.singletonMap(btnCopyPartOfSpeech, tfPartOfSpeech));
		//
		objectJTextComponentMap.put(btnCopyConjugation, tfConjugation);
		//
		objectJTextComponentMap.put(btnCopyKanji, tfKanji);
		//
		objectJTextComponentMap.put(btnCopyHiragana, tfHiragana);
		//
		if (setContents(source, supplier, objectJTextComponentMap)) {
			//
			return;
			//
		} // if
			//
		final Map<Object, Function<TextAndImage, Image>> objectFunctionMap = new LinkedHashMap<>(
				Collections.singletonMap(btnCopyAccentImage, x -> getAccentImage(x)));
		//
		objectFunctionMap.put(btnCopyCurveImage, x -> getCurveImage(x));
		//
		setContents(source, supplier, Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage)),
				objectFunctionMap);
		//
	}

	private static class ImageDimensionPosition {

		@Note("Width")
		private Integer width = null;

		private Integer height = null;

		@Note("Translate X")
		private Float translateX = null;

		private Float translateY = null;

	}

	private static class MH implements MethodHandler {

		private Collection<ImageDimensionPosition> imageDimensionPositions = null;

		@Nullable
		@Override
		public Object invoke(final Object self, final Method thisMethod, @Nullable final Method proceed,
				@Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof PDFGraphicsStreamEngine) {
				//
				if (proceed != null && !Modifier.isAbstract(proceed.getModifiers())) {
					//
					return Narcissus.invokeMethod(self, proceed, args);
					//
				} else if (Util.contains(Arrays.asList("drawImage"), methodName) && args != null && args.length > 0) {
					//
					ImageDimensionPosition idp = null;
					//
					final PDImage pdImage = Util.cast(PDImage.class, ArrayUtils.get(args, 0));
					//
					if (pdImage != null && (idp = ObjectUtils.getIfNull(idp, ImageDimensionPosition::new)) != null) {
						//
						idp.width = Integer.valueOf(pdImage.getWidth());
						//
						idp.height = Integer.valueOf(pdImage.getHeight());
						//
					} // if
						//
					final Matrix ctm = getCurrentTransformationMatrix(
							getGraphicsState(Util.cast(PDFGraphicsStreamEngine.class, self)));
					//
					if (ctm != null && (idp = ObjectUtils.getIfNull(idp, ImageDimensionPosition::new)) != null) {
						//
						idp.translateX = Float.valueOf(ctm.getTranslateX());
						//
						idp.translateY = Float.valueOf(ctm.getTranslateY());
						//
					} // if
						//
					Util.add(imageDimensionPositions = ObjectUtils.getIfNull(imageDimensionPositions, ArrayList::new),
							idp);
					//
					return null;
					//
				} else if (Util.contains(Arrays.asList(Void.TYPE, Point2D.class), Util.getReturnType(thisMethod))) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		@Nullable
		private static PDGraphicsState getGraphicsState(@Nullable final PDFGraphicsStreamEngine instance) {
			return instance != null ? instance.getGraphicsState() : null;
		}

		@Nullable
		private static Matrix getCurrentTransformationMatrix(@Nullable final PDGraphicsState instance) {
			return instance != null ? instance.getCurrentTransformationMatrix() : null;
		}

	}

	private static void actionPerformedBtnPdf(@Nullable final VoiceManagerOjadAccentPanel instance,
			final TextAndImage textAndImage) throws IOException, TemplateException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		final Version version = Configuration.getVersion();
		//
		final Configuration configuration = new Configuration(version);
		//
		configuration.setTemplateLoader(new ClassTemplateLoader(VoiceManagerOjadAccentPanel.class, "/"));
		//
		PDDocument pdDocument = null;
		//
		try (final Writer w = new StringWriter(); final Playwright playwright = Playwright.create()) {
			//
			final Template template = configuration.getTemplate("ojad.ftl");
			//
			final Collection<TextAndImage> textAndImages = getTextAndImages(instance, textAndImage);
			//
			final int imageTotalWidth = Util.orElse(
					Util.max(Util.mapToInt(Util.stream(textAndImages), x -> Util.intValue(getAccentImageWidth(x), 0))),
					0)
					+ Util.orElse(Util.max(
							Util.mapToInt(Util.stream(textAndImages), x -> Util.intValue(getCurveImageWidth(x), 0))),
							0);
			//
			final Map<String, Object> map = new LinkedHashMap<>(
					Collections.singletonMap("textAndImages", textAndImages));
			//
			Util.put(map, "static", new BeansWrapper(version).getStaticModels());
			//
			final int size = IterableUtils.size(textAndImages);
			//
			final List<Integer> list = Collections.nCopies(size, null);
			//
			final List<Integer> maxConjugationLength = testAndApply(Objects::nonNull, list, ArrayList::new,
					x -> new ArrayList<>());
			//
			final List<Integer> maxKanjiLength = testAndApply(Objects::nonNull, list, ArrayList::new,
					x -> new ArrayList<>());
			//
			final List<Integer> maxHiraganaLength = testAndApply(Objects::nonNull, list, ArrayList::new,
					x -> new ArrayList<>());
			//
			forEachOrdered(IntStream.range(0, size), i -> set(textAndImages, i, textAndImage, imageTotalWidth,
					maxConjugationLength, maxKanjiLength, maxHiraganaLength));
			//
			Util.put(map, "maxConjugationLength", maxConjugationLength);
			//
			Util.put(map, "maxKanjiLength", maxKanjiLength);
			//
			Util.put(map, "maxHiraganaLength", maxHiraganaLength);
			//
			TemplateUtil.process(template, map, w);
			//
			final Page page = newPage(BrowserTypeUtil.launch(chromium(playwright)));
			//
			setContent(page, Util.toString(w));
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setSelectedFile(Util.toFile(Path.of(StringUtils.joinWith(".", getKanji(textAndImage), "pdf"))));
			//
			if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode())
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final byte[] bs = pdf(page);
				//
				final PDPage pdPage = testAndApply(x -> PDDocumentUtil.getNumberOfPages(x) > 0,
						pdDocument = Loader.loadPDF(bs), x -> PDDocumentUtil.getPage(x, 0), null);
				//
				final ProxyFactory proxyFactory = new ProxyFactory();
				//
				proxyFactory.setSuperclass(PDFGraphicsStreamEngine.class);
				//
				final Constructor<?> constructor = Util.getDeclaredConstructor(proxyFactory.createClass(),
						PDPage.class);
				//
				Util.setAccessible(constructor, true);
				//
				final Object temp = Util.newInstance(constructor, pdPage);
				//
				final MH mh = new MH();
				//
				setHandler(Util.cast(javassist.util.proxy.Proxy.class, temp), mh);
				//
				processPage(Util.cast(PDFGraphicsStreamEngine.class, temp), pdPage);
				//
				addAnnotations(pdDocument, pdPage, mh.imageDimensionPositions, textAndImages,
						BooleanUtils.toBooleanDefaultIfNull(
								Util.cast(Boolean.class,
										Util.getSelectedItem(instance != null ? instance.cbmCompression : null)),
								true));
				//
				final File file = jfc.getSelectedFile();
				//
				testAndAccept(f -> !Util.exists(f), file, f -> {
					//
					// java.io.File.createNewFile();
					//
					final Iterable<Method> ms = Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(file)), Arrays::stream,
									null),
							m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "createNewFile"),
									Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {}))));
					//
					testAndRunThrows(IterableUtils.size(ms) > 1, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					testAndAccept((a, b) -> Boolean.logicalAnd(a != null, b != null), f,
							testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null),
							Narcissus::invokeMethod);
					//
				});
				//
				PDDocumentUtil.save(pdDocument, file);
				//
			} // if
				//
		} finally {
			//
			IOUtils.closeQuietly(pdDocument);
			//
		} // try
			//
	}

	private static void forEachOrdered(@Nullable final IntStream instance, @Nullable final IntConsumer consumer) {
		if (instance != null && (consumer != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEachOrdered(consumer);
		}
	}

	private static void set(final Iterable<TextAndImage> textAndImages, final int i, final TextAndImage textAndImage,
			final int imageTotalWidth, final List<Integer> maxConjugationLength, final List<Integer> maxKanjiLength,
			final List<Integer> maxHiraganaLength) {
		//
		final TextAndImage tai = testAndApply(x -> IterableUtils.size(x) > i, textAndImages,
				x -> IterableUtils.get(x, i), null);
		//
		final String conjugation = getConjugation(textAndImage);
		//
		final int la = StringUtils.length(getConjugation(tai));
		//
		final int lb = StringUtils.length(getKanji(tai));
		//
		final int lc = StringUtils.length(getHiragana(tai));
		//
		Integer integer = null;
		//
		if (Util.and(imageTotalWidth == 310// 補助的[な]
				, la == 8// 〜じゃなかった形
				, lb == 9// 補助的じゃなかった
				, lc == 11// ほじょてきじゃなかった
		)) {
			//
			set(maxConjugationLength, i, integer = Integer.valueOf(13));
			//
			set(maxKanjiLength, i, integer);
			//
			set(maxHiraganaLength, i, integer);
			//
		} else if (Util.and(imageTotalWidth == 310, // しゃべり続ける
				StringUtils.length(conjugation) == 3// 辞書形
				, la == 6// 〜なかった形
		)) {
			//
			set(maxConjugationLength, i, Integer.valueOf(11));
			//
			set(maxKanjiLength, i, integer = Integer.valueOf(14));
			//
			set(maxHiraganaLength, i, integer);
			//
		} else if (Util.and(imageTotalWidth == 338, // 勉強になる
				StringUtils.length(conjugation) == 3// 辞書形
				, la == 6// 〜なかった形
		)) {
			//
			set(maxConjugationLength, i, Integer.valueOf(11));
			//
			set(maxKanjiLength, i, Integer.valueOf(12));
			//
			set(maxHiraganaLength, i, Integer.valueOf(13));
			//
		} else if (imageTotalWidth == 422) {// 実況中継する
			//
			set(maxConjugationLength, i, integer = Integer.valueOf(8));
			//
			set(maxKanjiLength, i, integer);
			//
			set(maxHiraganaLength, i, integer);
			//
		} else if (imageTotalWidth == 394) {// 受験勉強する
			//
			if (la == 6) {// 〜なかった形
				//
				set(maxConjugationLength, i, integer = Integer.valueOf(9));
				//
				set(maxKanjiLength, i, integer);
				//
				set(maxHiraganaLength, i, integer);
				//
			} else {
				//
				set(maxConjugationLength, i, Integer.valueOf(iif(la == 3/* 辞書形 */, 15, 14)));
				//
				integer = Integer.valueOf(11);
				//
				if (lb == 7) {// 受験勉強します
					//
					set(maxKanjiLength, i, Integer.valueOf(14));
					//
				} else if (lb != 6) {
					//
					set(maxKanjiLength, i, integer);
					//
				} // if
					//
				set(maxHiraganaLength, i,
						Integer.valueOf(iif(lc == 11/* じゅけんべんきょうする */, 12, Util.intValue(integer, 11))));
				//
			} // if
				//
		} // if
			//
	}

	private static int iif(final boolean condition, final int valueTrue, final int valueFalse) {
		return condition ? valueTrue : valueFalse;
	}

	private static <T> void set(@Nullable final List<T> instance, final int index, final T value) {
		if (instance != null) {
			instance.set(index, value);
		}
	}

	private static void addAnnotations(final PDDocument pdDocument, final PDPage pdPage,
			final Collection<ImageDimensionPosition> idps, final Collection<TextAndImage> textAndImages,
			final boolean compression) throws IOException {
		//
		try (final PDPageContentStream cs = testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null), pdDocument,
				pdPage, (a, b) -> new PDPageContentStream(a, b, AppendMode.APPEND, compression), null)) {
			//
			double[] ds = getTranslateXs(idps, 0);
			//
			final double[] translateXs = testAndApply(x -> x > 2, length(ds), x -> ArrayUtils.subarray(ds, x - 2, x),
					x -> ds);
			//
			final Predicate<ImageDimensionPosition> predicate = createImageDimensionPositionPredicate(translateXs);
			//
			final double[] translateYs = toArray(distinct(sorted(mapToDouble(Util.filter(Util.stream(idps), predicate),
					x -> x != null ? Util.floatValue(x.translateY, 0) : null))));
			//
			final int[] widths = toArray(distinct(Util.sorted(Util.mapToInt(Util.filter(Util.stream(idps), predicate),
					x -> x != null ? Util.intValue(x.width, 0) : null))));
			//
			final Integer width = length(widths) == 1 ? Integer.valueOf(get(widths, 0, 0)) : null;
			//
			PDAnnotationFileAttachment pdAnnotationFileAttachment = null;
			//
			PDComplexFileSpecification pdComplexFileSpecification = null;
			//
			final int size = getSize(idps, predicate, 10);
			//
			byte[] bs = null;
			//
			final Pattern pattern = Pattern.compile("^\\d+_(\\d+)_\\d+_(\\w+)\\.\\w+$");
			//
			IValue0<String> iValue0 = null;
			//
			ContentInfoUtil ciu = null;
			//
			TextAndImage textAndImage = null;
			//
			final List<String> ys = Util.toList(Util.distinct(Util.map(
					flatMap(Util.map(Util.stream(textAndImages), x -> Util.keySet(getVoiceUrlImages(x))), Util::stream),
					createFunction(pattern))));
			//
			for (int x = 0; x < length(translateXs); x++) {
				//
				for (int y = 0; y < length(translateYs); y++) {
					//
					(pdAnnotationFileAttachment = new PDAnnotationFileAttachment())
							.setRectangle(new PDRectangle((float) translateXs[x] + Util.floatValue(width, 0) - size / 2, // TODO
									(float) translateYs[y] + size// TODO
									, size, size));
					//
					try (final InputStream is = testAndApply(Objects::nonNull,
							bs = toByteArray(
									testAndApply(Objects::nonNull,
											IValue0Util
													.getValue0(
															iValue0 = getVoiceUrlByX(pattern,
																	Util.keySet(getVoiceUrlImages(
																			textAndImage = getTextAndImageByXY(pattern,
																					textAndImages, x,
																					CollectionUtils.isNotEmpty(ys)
																							? IterableUtils.get(ys,
																									IterableUtils.size(
																											ys) - y - 1)
																							: null))),
																	x)),
											URL::new, null)),
							ByteArrayInputStream::new, null)) {
						//
						if (is == null) {
							//
							continue;
							//
						} // if
							//
						(pdComplexFileSpecification = new PDComplexFileSpecification())
								.setEmbeddedFile(createPDEmbeddedFile(pdDocument, is,
										ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), bs));
						//
						pdComplexFileSpecification
								.setFile(StringUtils.substringAfterLast(IValue0Util.getValue0(iValue0), '/'));
						//
					} // try
						//
					pdAnnotationFileAttachment.setFile(pdComplexFileSpecification);
					//
					pdAnnotationFileAttachment.setSubject(getKanji(textAndImage));
					//
					Util.add(PDPageUtil.getAnnotations(pdPage), pdAnnotationFileAttachment);
					//
				} // for
					//
			} // for
				//
		} // try
			//
	}

	private static Function<String, String> createFunction(final Pattern pattern) {
		//
		return x -> {
			//
			final Matcher matcher = Util.matcher(pattern, StringUtils.substringAfterLast(x, '/'));
			//
			return Util.matches(matcher) && Util.groupCount(matcher) > 0 ? Util.group(matcher, 1) : null;
			//
		};
		//
	}

	private static Predicate<ImageDimensionPosition> createImageDimensionPositionPredicate(final double[] translateXs) {
		//
		return x -> x != null && x.translateX != null && translateXs != null
				&& ArrayUtils.contains(translateXs, x.translateX);
		//
	}

	@Nullable
	private static <T, R> Stream<R> flatMap(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends Stream<? extends R>> mapper) {
		//
		return instance != null && (mapper != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.flatMap(mapper)
				: null;
		//
	}

	@Nullable
	private static double[] getTranslateXs(final Collection<ImageDimensionPosition> idps, final double defaultValue) {
		//
		return toArray(distinct(sorted(mapToDouble(Util.stream(idps),
				x -> x != null ? Util.doubleValue(x.translateX, defaultValue) : defaultValue))));
		//
	}

	private static int getSize(final Collection<ImageDimensionPosition> idps,
			final Predicate<ImageDimensionPosition> predicate, final int defaultValue) {
		//
		final int[] heights = toArray(distinct(Util.sorted(Util.mapToInt(Util.filter(Util.stream(idps), predicate),
				x -> x != null ? Util.intValue(x.height, defaultValue) : defaultValue))));
		//
		return length(heights) == 1 ? get(heights, 0, defaultValue) : defaultValue;
		//
	}

	private static PDEmbeddedFile createPDEmbeddedFile(final PDDocument pdDocument, final InputStream is,
			final ContentInfoUtil ciu, @Nullable final byte[] bs) throws IOException {
		//
		final PDEmbeddedFile pdEmbeddedFile = testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null),
				pdDocument, is, PDEmbeddedFile::new, null);
		//
		if (pdEmbeddedFile != null) {
			//
			pdEmbeddedFile.setSubtype(getMimeType(findMatch(ciu, bs)));
			//
			if (bs != null) {
				//
				pdEmbeddedFile.setSize(bs.length);
				//
			} // if
				//
		} // if
			//
		return pdEmbeddedFile;
		//
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static IValue0<String> getVoiceUrlByX(final Pattern pattern, final Iterable<String> ss, final int x) {
		//
		String s = null;
		//
		Matcher matcher = null;
		//
		IValue0<String> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(ss); i++) {
			//
			if (!Util.matches(
					matcher = Util.matcher(pattern, StringUtils.substringAfterLast(s = IterableUtils.get(ss, i), '/')))
					|| Util.groupCount(matcher) != 2) {
				//
				continue;
				//
			} // if
				//
			if (Boolean.logicalOr(Boolean.logicalAnd(x == 0, Objects.equals(Util.group(matcher, 2), "female"))// 1184_1_1_female
					, Boolean.logicalAnd(x == 1, Objects.equals(Util.group(matcher, 2), "male")))// 1184_1_1_male
			) {
				//
				testAndRunThrows(iValue0 != null, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				iValue0 = Unit.with(s);
				//
			} // if
				//
		} // for
			//
		return iValue0;
		//
	}

	@Nullable
	private static TextAndImage getTextAndImageByXY(final Pattern pattern, final Iterable<TextAndImage> textAndImages,
			final int x, @Nullable final String y) {
		//
		TextAndImage textAndImage = null;
		//
		Iterable<String> urls = null;
		//
		Matcher matcher = null;
		//
		final int size = IterableUtils.size(textAndImages);
		//
		TextAndImage result = null;
		//
		for (int i = 0; i < size; i++) {
			//
			if ((textAndImage = IterableUtils.get(textAndImages, i)) == null) {
				//
				continue;
				//
			} // if
				//
			urls = Util.toList(Util.stream(Util.keySet(getVoiceUrlImages(textAndImage))));
			//
			for (int j = 0; j < IterableUtils.size(urls); j++) {
				//
				if (!Util.matches(matcher = Util.matcher(pattern,
						StringUtils.substringAfterLast(IterableUtils.get(urls, j), '/')))
						|| Util.groupCount(matcher) != 2 || !Objects.equals(y, Util.group(matcher, 1))) {
					//
					continue;
					//
				} // if
					//
				if (Boolean.logicalOr(Boolean.logicalAnd(x == 0, Objects.equals(Util.group(matcher, 2), "female"))// 1184_1_1_female
						, Boolean.logicalAnd(x == 1, Objects.equals(Util.group(matcher, 2), "male")))// 1184_1_1_male
				) {
					//
					testAndRunThrows(result != null, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					result = textAndImage;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return result;
		//
	}

	private static int get(@Nullable final int[] instance, final int index, final int defaultValue) {
		return instance != null ? instance[index] : defaultValue;
	}

	@Nullable
	private static double[] toArray(@Nullable final DoubleStream instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static int[] toArray(@Nullable final IntStream instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static DoubleStream distinct(@Nullable final DoubleStream instance) {
		return instance != null ? instance.distinct() : instance;
	}

	@Nullable
	private static IntStream distinct(@Nullable final IntStream instance) {
		return instance != null ? instance.distinct() : instance;
	}

	@Nullable
	private static DoubleStream sorted(@Nullable final DoubleStream instance) {
		return instance != null ? instance.sorted() : instance;
	}

	@Nullable
	private static <T> DoubleStream mapToDouble(@Nullable final Stream<T> instance,
			@Nullable final ToDoubleFunction<? super T> function) {
		return instance != null && (function != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.mapToDouble(function)
				: null;
	}

	private static void setHandler(@Nullable final javassist.util.proxy.Proxy instance, final MethodHandler mh) {
		if (instance != null) {
			instance.setHandler(mh);
		}
	}

	private static int length(@Nullable final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static int length(@Nullable final double[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static void processPage(@Nullable final PDFGraphicsStreamEngine instance, @Nullable final PDPage page)
			throws IOException {
		if (instance != null && page != null) {
			instance.processPage(page);
		}
	}

	private static void setContents(@Nullable final Object source, final Supplier<Clipboard> suppler,
			final TextAndImage textAndImage, final Map<Object, Function<TextAndImage, Image>> map) {
		//
		final Iterable<Entry<Object, Function<TextAndImage, Image>>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			for (final Entry<Object, Function<TextAndImage, Image>> entry : entrySet) {
				//
				if (Objects.equals(source, Util.getKey(entry))) {
					//
					final IH ih = new IH();
					//
					ih.image = Util.apply(Util.getValue(entry), textAndImage);
					//
					setContents(Util.get(suppler), Reflection.newProxy(Transferable.class, ih), null);
					//
					return;
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static boolean setContents(@Nullable final Object source, final Supplier<Clipboard> suppler,
			final Map<Object, JTextComponent> map) {
		//
		final Iterable<Entry<Object, JTextComponent>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			for (final Entry<Object, JTextComponent> entry : entrySet) {
				//
				if (Objects.equals(source, Util.getKey(entry))) {
					//
					setContents(Util.get(suppler), new StringSelection(Util.getText(Util.getValue(entry))), null);
					//
					return true;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return false;
		//
	}

	private static void setContent(@Nullable final Page instance, final String html) {
		if (instance != null) {
			instance.setContent(html);
		}
	}

	@Nullable
	private static byte[] pdf(@Nullable final Page instance) {
		return instance != null ? instance.pdf() : null;
	}

	private static void removeAll(@Nullable final Container instance) {
		if (instance != null) {
			instance.removeAll();
		}
	}

	private static boolean actionPerformed(@Nullable final String actionCommand) {
		//
		final org.apache.commons.lang3.Strings strings = org.apache.commons.lang3.Strings.CS;
		//
		if (startsWith(strings, actionCommand, StringUtils.join(COPY, ','))) {
			//
			setContents(getClipboard(), new StringSelection(StringUtils.substringAfter(actionCommand, ',')), null);
			//
			return true;
			//
		} else if (startsWith(strings, actionCommand, StringUtils.join(DOWNLOAD, ','))) {
			//
			try {
				//
				saveURL(Util.toURL(testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x),
						StringUtils.substringAfter(actionCommand, ','), URI::new, null)));
				//
			} catch (final URISyntaxException | IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} else if (startsWith(strings, actionCommand, StringUtils.join(PLAY, ','))) {
			//
			Player player = null;
			//
			try (final InputStream is = testAndApply(Objects::nonNull,
					toByteArray(Util.toURL(testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x),
							StringUtils.substringAfter(actionCommand, ','), URI::new, null))),
					ByteArrayInputStream::new, null)) {
				//
				PlayerUtil.play(player = testAndApply(Objects::nonNull, is, Player::new, null));
				//
			} catch (final URISyntaxException | IOException | JavaLayerException e) {
				//
				throw new RuntimeException(e);
				//
			} finally {
				//
				close(player);
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

	private static boolean startsWith(@Nullable final org.apache.commons.lang3.Strings instance,
			@Nullable final CharSequence str, @Nullable final CharSequence prefix) {
		return instance != null && instance.startsWith(str, prefix);
	}

	private static void close(@Nullable final Player instance) {
		if (instance != null) {
			instance.close();
		}
	}

	private static void saveURL(final URL url) throws IOException {
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		jfc.setSelectedFile(Util.toFile(testAndApply(Objects::nonNull, Util.getFile(url), Path::of, null)));
		//
		if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode())
				&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			//
			FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), toByteArray(url));
			//
		} // try
			//
	}

	private static byte[] toByteArray(final URL url) throws IOException {
		//
		try (final InputStream is = Util.openStream(url)) {
			//
			return testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
		} // try
			//
	}

	@Nullable
	private static String getActionCommand(@Nullable final ActionEvent instance) {
		return instance != null ? instance.getActionCommand() : null;
	}

	private static <R> R testAndApply(@Nullable final IntPredicate predicate, final int value,
			final IntFunction<R> functionTrue, @Nullable final IntFunction<R> functionFalse) {
		return test(predicate, value) ? Util.apply(functionTrue, value) : Util.apply(functionFalse, value);
	}

	private static boolean test(@Nullable final IntPredicate instance, final int value) {
		return instance != null && instance.test(value);
	}

	private static Clipboard getClipboard() {
		//
		return testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
				() -> getSystemClipboard(Toolkit.getDefaultToolkit()),
				() -> Util.cast(Clipboard.class, Narcissus.allocateInstance(Clipboard.class)));
		//
	}

	private static void saveImage(@Nullable final RenderedImage image, final Supplier<File> supplier,
			final String format) {
		//
		if (image != null) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setSelectedFile(Util.get(supplier));
			//
			if (!GraphicsEnvironment.isHeadless() && !isTestMode()
					&& jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					ImageIO.write(image, StringUtils.defaultIfBlank(format, "png"), jfc.getSelectedFile());
					//
				} catch (final IOException e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} // try
					//
			} // if
				//
		} // if
			//
	}

	private void actionPerformedBtnExecute() {
		//
		final String textInput = Util.getText(tfTextInput);
		//
		if (StringUtils.isEmpty(textInput)) {
			//
			return;
			//
		} // if
			//
		Page page = null;
		//
		Playwright playwright = null;
		//
		Browser browser = null;
		//
		try {
			//
			Util.setText(lblCount, null);
			//
			final Map<Object, Object> map = new LinkedHashMap<>(Collections.singletonMap("word", testAndApply(
					Objects::nonNull, textInput, x -> URLEncoder.encode(x, StandardCharsets.UTF_8), null)));
			//
			final Method getKey = getMapEntryGetKeyMethod();
			//
			// 品詞
			//
			final Entry<?, ?> entry = Util.cast(Entry.class, Util.getSelectedItem(cbmCategory));
			//
			if (!Objects.equals(Util.getValue(entry), "すべて")) {
				//
				Util.put(map, "category",
						Util.toString(testAndApply((a, b) -> a != null, entry, getKey, Narcissus::invokeMethod, null)));
				//
			} // if
				//
				// ピッチカーブ
				//
			Util.put(map, CURVE, Util.toString(testAndApply((a, b) -> a instanceof Entry,
					Util.getSelectedItem(cbmCurve), getKey, Narcissus::invokeMethod, null)));
			//
			String u = createUrl(url, map);
			//
			final boolean testMode = isTestMode();
			//
			if (!testMode) {
				//
				PageUtil.navigate(
						page = newPage(browser = BrowserTypeUtil.launch(chromium(playwright = Playwright.create()))),
						u);
				//
				final int size = IterableUtils.size(querySelectorAll(page, "#paginator a"));
				//
				if (size > 0) {
					//
					Util.put(map, "limit", NumberUtils
							.toInt(Util.toString(jsonValue(getProperty(testAndApply(x -> IterableUtils.size(x) == 1,
									querySelectorAll(page, "#search_limit"), x -> IterableUtils.get(x, 0), null),
									VALUE))), 1)
							* size);
					//
					PageUtil.navigate(page = newPage(browser), u = createUrl(url, map));
					//
				} // if
					//
			} // if
				//
			Util.forEach(Util.map(Util.sorted(Util.map(IntStream.range(1, Util.getSize(mcbmTextAndImage)), i -> -i)),
					i -> -i), i -> Util.removeElementAt(mcbmTextAndImage, i));
			//
			final List<ElementHandle> words = querySelectorAll(page, "tr[id^=\"word\"]");
			//
			final Collection<TextAndImage> textAndImages = getTextAndImages(url, textInput, map);
			//
			Util.forEach(Util.stream(textAndImages), x -> Util.addElement(mcbmTextAndImage, x));
			//
			Util.setText(lblCount, Integer.toString(IterableUtils.size(textAndImages)));
			//
			Util.setEditable(tfIndex, CollectionUtils.isNotEmpty(textAndImages));
			//
			testAndAccept((a, b) -> CollectionUtils.isEmpty(a), textAndImages, tfIndex,
					(a, b) -> Util.setText(b, null));
			//
			if (IterableUtils.size(textAndImages) == 1
					|| (IterableUtils.size(words) == 1 && IterableUtils.size(textAndImages) == 1)) {
				//
				Util.setSelectedItem(mcbmTextAndImage, IterableUtils.get(textAndImages, 0));
				//
			} // if
				//
			pack(window);
			//
		} catch (final IOException | URISyntaxException e) {
			//
			throw new RuntimeException(e);
			//
		} finally {
			//
			close(browser);
			//
			close(playwright);
			//
		} // try
			//
	}

	private static Method getMapEntryGetKeyMethod() {
		//
		final Stream<Method> stream = testAndApply(Objects::nonNull, Util.getDeclaredMethods(Entry.class),
				Arrays::stream, null);
		//
		return testAndApply(x -> IterableUtils.size(x) == 1,
				Util.toList(Util.filter(stream,
						m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "getKey"),
								Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {})))),
				x -> IterableUtils.get(x, 0), null);
		//
	}

	@Nullable
	private static Collection<TextAndImage> getTextAndImages(final String baseUrl, final String textInput,
			final Map<Object, Object> map) throws IOException, URISyntaxException {
		//
		if (StringUtils.isEmpty(textInput)) {
			//
			return null;
			//
		} // if
			//
		Page page = null;
		//
		Playwright playwright = null;
		//
		Browser browser = null;
		//
		try {
			//
			String url = createUrl(baseUrl, map);
			//
			final boolean testMode = isTestMode();
			//
			if (!testMode) {
				//
				PageUtil.navigate(
						page = newPage(browser = BrowserTypeUtil.launch(chromium(playwright = Playwright.create()))),
						url);
				//
				final int size = IterableUtils.size(querySelectorAll(page, "#paginator a"));
				//
				if (size > 0) {
					//
					Util.put(map, "limit", NumberUtils
							.toInt(Util.toString(jsonValue(getProperty(testAndApply(x -> IterableUtils.size(x) == 1,
									querySelectorAll(page, "#search_limit"), x -> IterableUtils.get(x, 0), null),
									VALUE))), 1)
							* size);
					//
					PageUtil.navigate(page = newPage(browser), url = createUrl(baseUrl, map));
					//
				} // if
					//
			} // if
				//
			final List<ElementHandle> ehs = querySelectorAll(page, ".katsuyo_accent");
			//
			final List<ElementHandle> words = querySelectorAll(page, "tr[id^=\"word\"]");
			//
			final Iterable<String> partOfSpeeches = Util.toList(Util.map(
					Util.filter(Util.stream(querySelectorAll(page, CSS_SELECTOR_MIDASHI)),
							x -> IterableUtils.isEmpty(querySelectorAll(x, "div"))),
					x -> StringUtils.trim(textContent(x))));
			//
			String html = null;
			//
			if (!testMode) {
				//
				try (final InputStream is = Util.openStream(Util.toURL(new URI(url)))) {
					//
					html = IOUtils.toString(is, StandardCharsets.UTF_8);
					//
				} // try
					//
			} // if
				//
			final Document document = testAndApply(Objects::nonNull, html, Jsoup::parse, null);
			//
			final Page p = page;
			//
			final Collection<TextAndImage> textAndImages = getIfNull(toTextAndImages(ehs, words, p),
					Arrays.asList(() -> toTextAndImages1(ehs, textInput, words, p),
							() -> toTextAndImages2(ehs, textInput, words, partOfSpeeches, p),
							() -> toTextAndImages3(words, document, p)));
			//
			if (IterableUtils.size(partOfSpeeches) == 1) {
				//
				Util.forEach(Util.stream(textAndImages), x -> setPartOfSpeech(x, IterableUtils.get(partOfSpeeches, 0)));
				//
				testAndAccept(items -> IterableUtils.size(items) == 1,
						Util.toList(Util.filter(Util.stream(querySelectorAll(page, "thead tr th")),
								el -> Util.anyMatch(
										testAndApply(Objects::nonNull, StringUtils.split(getAttribute(el, CLASS), " "),
												Arrays::stream, null),
										y -> StringsUtil.startsWith(org.apache.commons.lang3.Strings.CS, y, "katsuyo_"))
										&& StringUtils.isNotBlank(textContent(el)))),
						items ->
						//
						Util.forEach(items, eh -> Util.forEach(Util.stream(textAndImages), tai -> {
							//
							if (tai != null) {
								//
								tai.conjugation = StringUtils.trim(textContent(eh));
								//
							} // if
								//
						}))
				//
				);
				//
			} // if
				//
			adjustImageColor(textAndImages);
			//
			Util.forEach(Util.stream(textAndImages), createTextAndImageConsumer());
			//
			return textAndImages;
			//
		} finally {
			//
			close(browser);
			//
			close(playwright);
			//
		} // try
			//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
		}
	}

	@Nullable
	private static Collection<TextAndImage> getTextAndImages(@Nullable final VoiceManagerOjadAccentPanel instance,
			final TextAndImage input) {
		//
		if (input == null) {
			//
			return null;
			//
		} // if
			//
		Page page = null;
		//
		Playwright playwright = null;
		//
		Browser browser = null;
		//
		try {
			//
			final Map<Object, Object> map = new LinkedHashMap<>(Collections.singletonMap("word", testAndApply(
					Objects::nonNull, getKanji(input), x -> URLEncoder.encode(x, StandardCharsets.UTF_8), null)));
			//
			final ComboBoxModel<?> cbmCurve = instance != null ? instance.cbmCurve : null;
			//
			Method getKey = null;
			//
			Object key = null;
			//
			final Object curveImage = getCurveImage(input);
			//
			for (int i = 0; i < Util.getSize(cbmCurve); i++) {
				//
				if (Boolean
						.logicalOr(
								Boolean.logicalAnd(
										Objects.equals(key = Narcissus.invokeMethod(Util.getElementAt(cbmCurve, i),
												getKey = ObjectUtils.getIfNull(getKey,
														() -> getMapEntryGetKeyMethod())),
												"invisible"),
										curveImage == null),
								Boolean.logicalAnd(Objects.equals(key, "fujisaki"), curveImage != null))) {
					//
					Util.put(map, CURVE, key);
					//
				} // if
					//
			} // for
				//
			final String baseUrl = instance != null ? instance.url : null;
			//
			String url = createUrl(baseUrl, map);
			//
			if (!isTestMode()) {
				//
				PageUtil.navigate(
						page = newPage(browser = BrowserTypeUtil.launch(chromium(playwright = Playwright.create()))),
						url);
				//
			} // if
				//
				// word
				//
			final Iterable<ElementHandle> words = testAndApply((a, b) -> StringUtils.isNotBlank(b), page, input.id,
					(a, b) -> querySelectorAll(a, String.format("tr[id=\"%1$s\"]", b)),
					(a, b) -> querySelectorAll(a, "tr[id^=\"word\"]"));
			//
			testAndRunThrows(IterableUtils.size(words) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final ElementHandle word = testAndApply(x -> IterableUtils.size(x) == 1, words,
					x -> IterableUtils.get(x, 0), null);
			//
			// midashi
			//
			final Iterable<ElementHandle> midashis = querySelectorAll(word, CSS_SELECTOR_MIDASHI);
			//
			testAndRunThrows(IterableUtils.size(midashis) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final String midashi = textContent(
					testAndApply(x -> IterableUtils.size(x) == 1, midashis, x -> IterableUtils.get(x, 0), null));
			//
			final String[] ss = StringUtils.split(midashi, "・");
			//
			final Collection<ElementHandle> katsuyoEhs = querySelectorAll(word,
					".katsuyo p .katsuyo_accent .accented_word");
			//
			Collection<TextAndImage> textAndImages = null;
			//
			if (length(ss) == 2) {
				//
				// さだめる
				//
				final String cp1 = Strings.commonPrefix(StringUtils.trim(ArrayUtils.get(ss, 0)),
						StringUtils.trim(ArrayUtils.get(ss, 1)));
				//
				final List<String> katsuyos = Util
						.toList(Util.map(Util.stream(katsuyoEhs), x -> StringUtils.trim(textContent(x))));
				//
				final String cp2 = commonPrefix(katsuyos);
				//
				String katsuyo = null;
				//
				TextStringBuilder tsb = null;
				//
				TextAndImage textAndImage = null;
				//
				ElementHandle eh = null;
				//
				final ElementHandle thead = testAndApply(x -> IterableUtils.size(x) == 1, querySelectorAll(page, THEAD),
						x -> IterableUtils.get(x, 0), null);
				//
				final Iterable<ElementHandle> ths = querySelectorAll(thead, "th");
				//
				for (int i = 0; i < Math.min(IterableUtils.size(katsuyos), IterableUtils.size(katsuyoEhs)); i++) {
					//
					if (startsWith(org.apache.commons.lang3.Strings.CS,
							(textAndImage = new TextAndImage()).hiragana = katsuyo = IterableUtils.get(katsuyos, i),
							cp2)) {
						//
						TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
						//
						textAndImage.kanji = Util.toString(TextStringBuilderUtil.append(
								TextStringBuilderUtil.append(tsb, cp1), StringUtils.substringAfter(katsuyo, cp2)));
						//
						textAndImage.accentImage = toBufferedImage(screenshot(eh = IterableUtils.get(katsuyoEhs, i)),
								e -> LoggerUtil.error(LOG, e.getMessage(), e));
						//
						textAndImage.curveImage = toBufferedImage(screenshot(querySelector(
								querySelector(querySelector(querySelector(querySelector(eh, ".."), ".."), ".."), ".."),
								CANVAS)), e -> LoggerUtil.error(LOG, e.getMessage(), e));
						//
						textAndImage.voiceUrlImages = getVoiceUrlImages(
								querySelectorAll(querySelector(querySelector(querySelector(eh, ".."), ".."), ".."),
										".katsuyo_proc_button a"),
								page, "mp3");
						//
						textAndImage.conjugation = StringUtils
								.trim(textContent(testAndApply(x -> IterableUtils.size(ths) > x + 2, i,
										x -> IterableUtils.get(ths, x + 2), null)));
						//
					} // if
						//
					Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
					//
				} // for
					//
			} else if (IterableUtils.size(words) == 1) {
				//
				textAndImages = Collections.singleton(input);
				//
			} // if
				//
			testAndAccept((a, b) -> IterableUtils.size(b) == 1, textAndImages,
					Util.toList(Util.map(
							Util.filter(Util.stream(querySelectorAll(page, CSS_SELECTOR_MIDASHI)),
									x -> IterableUtils.isEmpty(querySelectorAll(x, "div"))),
							x -> StringUtils.trim(textContent(x)))),
					(a, b) -> Util.forEach(Util.stream(a), x -> setPartOfSpeech(x, IterableUtils.get(b, 0))));
			//
			Util.forEach(Util.stream(textAndImages), createTextAndImageConsumer());
			//
			return textAndImages;
			//
		} finally {
			//
			close(browser);
			//
			close(playwright);
			//
		} // try
			//
	}

	@Nullable
	private static String commonPrefix(final Iterable<String> instance) {
		//
		IValue0<String> iValue0 = null;
		//
		String next = null;
		//
		final int size = IterableUtils.size(instance);
		//
		for (int i = 0; i < size - 1; i++) {
			//
			next = IterableUtils.get(instance, i + 1);
			//
			if (iValue0 == null) {
				//
				iValue0 = testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null),
						IterableUtils.get(instance, i), next, (a, b) -> Unit.with(Strings.commonPrefix(a, b)), null);
				//
			} else {
				//
				iValue0 = Unit.with(Strings.commonPrefix(IValue0Util.getValue0(iValue0), next));
				//
			} // if
				//
		} // for
			//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return size == 1 ? IterableUtils.get(instance, 0) : null;
		//
	}

	private static void adjustImageColor(final Iterable<TextAndImage> textAndImages) {
		//
		if (Boolean.logicalAnd(Objects.equals(OperatingSystem.LINUX, OperatingSystemUtil.getOperatingSystem()),
				IterableUtils.size(textAndImages) > 1)) {
			//
			Integer mostOccurenceColor = null;
			//
			Integer currentMostOccurenceColor = null;
			//
			BufferedImage image = null;
			//
			for (final TextAndImage textAndImage : textAndImages) {
				//
				if (textAndImage == null || mostOccurenceColor == null
						&& (mostOccurenceColor = getMostOccurenceColor(textAndImage.accentImage)) != null) {
					//
					continue;
					//
				} // if
					//
				if (!Objects.equals(mostOccurenceColor,
						currentMostOccurenceColor = getMostOccurenceColor(image = textAndImage.accentImage))) {
					//
					setRGB(image, currentMostOccurenceColor, mostOccurenceColor);
					//
				} // if
					//
				if (!Objects.equals(mostOccurenceColor,
						currentMostOccurenceColor = getMostOccurenceColor(image = textAndImage.curveImage))) {
					//
					setRGB(image, currentMostOccurenceColor, mostOccurenceColor);
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static void setRGB(@Nullable final BufferedImage image, @Nullable final Integer a,
			@Nullable final Integer b) {
		//
		final Field f = getFieldByName(Util.getClass(image), RASTER);
		//
		if (f == null || Narcissus.getField(image, f) == null) {
			//
			return;
			//
		} // if
			//
		for (int x = 0; image != null && x < image.getWidth(); x++) {
			//
			for (int y = 0; y < image.getHeight(); y++) {
				//
				if (a != null && Objects.equals(a.intValue(), image.getRGB(x, y)) && b != null) {
					//
					image.setRGB(x, y, b.intValue());
					//
				} // if
					//
			} // for
				//
		} // for
			//
	}

	@Nullable
	private static Integer getMostOccurenceColor(@Nullable final BufferedImage image) {
		//
		final Field f = getFieldByName(Util.getClass(image), RASTER);
		//
		if (f == null || Narcissus.getField(image, f) == null) {
			//
			return null;
			//
		} // if
			//
		Bag<Integer> bag = null;
		//
		for (int x = 0; image != null && x < image.getWidth(); x++) {
			//
			for (int y = 0; y < image.getHeight(); y++) {
				//
				Util.add(bag = ObjectUtils.getIfNull(bag, TreeBag::new), Integer.valueOf(image.getRGB(x, y)));
				//
			} // for
				//
		} // for
			//
		if (bag != null) {
			//
			int maxCount = 0;
			//
			for (final Object x : bag) {
				//
				maxCount = Math.max(maxCount, bag.getCount(x));
				//
			} // for
				//
			for (final Integer i : bag) {
				//
				if (maxCount == bag.getCount(i) && i != null) {
					//
					return i;
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

	private static Consumer<TextAndImage> createTextAndImageConsumer() {
		//
		return x -> {
			//
			final Map<String, byte[]> voiceUrlImages = getVoiceUrlImages(x);
			//
			final Iterable<Entry<String, byte[]>> entrySet = testAndApply(Objects::nonNull,
					Util.entrySet(voiceUrlImages), y -> new ArrayList<>(y), y -> y);
			//
			if (Util.iterator(entrySet) != null) {
				//
				String key = null;
				//
				for (final Entry<String, ?> entry : entrySet) {
					//
					if (matches(key = Util.getKey(entry), "^.+\\?\\d+$")) {
						//
						Util.put(voiceUrlImages, StringUtils.substringBeforeLast(key, "?"), voiceUrlImages.remove(key));
						//
					} // if
						//
				} // for
					//
			} // if
				//
		};
	}

	private static boolean matches(@Nullable final String a, @Nullable final String b) {
		return a != null && b != null && a.matches(b);
	}

	@Nullable
	private static Object jsonValue(@Nullable final JSHandle instnace) {
		return instnace != null ? instnace.jsonValue() : null;
	}

	@Nullable
	private static JSHandle getProperty(@Nullable final JSHandle instance, final String propertyName) {
		return instance != null ? instance.getProperty(propertyName) : instance;
	}

	private static String createUrl(final String url, final Map<Object, Object> map) {
		//
		final StringBuilder sb = new StringBuilder(StringUtils.defaultString(url));
		//
		final Iterable<Entry<Object, Object>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			String value = null;
			//
			for (final Entry<Object, Object> entry : entrySet) {
				//
				if (StringUtils.isNotBlank(value = Util.toString(Util.getValue(entry)))) {
					//
					if (!startsWith(org.apache.commons.lang3.Strings.CS, sb, "/")) {
						//
						sb.append('/');
						//
					} // if
						//
					sb.append(StringUtils.joinWith(":", Util.getKey(entry), value));
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return Util.toString(sb);
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	@Nullable
	private static <T> T getIfNull(@Nullable final T object, final Iterable<Supplier<T>> suppliers) {
		//
		if (object != null) {
			//
			return object;
			//
		} // if
			//
		T result = null;
		//
		for (int i = 0; i < IterableUtils.size(suppliers); i++) {
			//
			if ((result = Util.get(IterableUtils.get(suppliers, i))) != null) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return object;
		//
	}

	private static void close(@Nullable final Browser instance) {
		if (instance != null) {
			instance.close();
		}
	}

	private static void close(@Nullable final Playwright instance) {
		if (instance != null) {
			instance.close();
		}
	}

	private static Collection<TextAndImage> toTextAndImages(@Nullable final Iterable<ElementHandle> ehs,
			@Nullable final Iterable<ElementHandle> words, @Nullable final Page page) {
		//
		TextAndImage textAndImage = null;
		//
		ElementHandle eh = null;
		//
		if (IterableUtils.size(ehs) == 1) {
			//
			// 坂
			//
			(textAndImage = new TextAndImage()).kanji = StringUtils.trim(textContent(querySelector(
					testAndApply(x -> IterableUtils.size(x) == 1, words, x -> IterableUtils.get(x, 0), null),
					CSS_SELECTOR_MIDASHI)));
			//
			textAndImage.accentImage = toBufferedImage(screenshot(eh = IterableUtils.get(ehs, 0)),
					e -> LoggerUtil.error(LOG, e.getMessage(), e));
			//
			textAndImage.curveImage = toBufferedImage(
					screenshot(querySelector(querySelector(querySelector(eh, ".."), ".."), CANVAS)),
					e -> LoggerUtil.error(LOG, e.getMessage(), e));
			//
			textAndImage.hiragana = StringUtils.trim(textContent(eh));
			//
			final ElementHandle thead = testAndApply(x -> IterableUtils.size(x) == 1, querySelectorAll(page, THEAD),
					x -> IterableUtils.get(x, 0), null);
			//
			textAndImage.conjugation = StringUtils.trim(textContent(testAndApply(x -> IterableUtils.size(x) > 2,
					querySelectorAll(thead, "th"), x -> IterableUtils.get(x, 2), null)));
			//
			return Collections.singleton(textAndImage);
			//
		} // if
			//
		Collection<TextAndImage> textAndImages = null;
		//
		if (IterableUtils.size(words) == IterableUtils.size(ehs)) {
			//
			// 家
			//
			ElementHandle word = null;
			//
			for (int i = 0; i < IterableUtils.size(words); i++) {
				//
				(textAndImage = new TextAndImage()).kanji = StringUtils
						.trim(textContent(querySelector(word = IterableUtils.get(words, i), CSS_SELECTOR_MIDASHI)));
				//
				textAndImage.accentImage = toBufferedImage(screenshot(eh = IterableUtils.get(ehs, i)),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.curveImage = toBufferedImage(
						screenshot(querySelector(querySelector(querySelector(eh, ".."), ".."), CANVAS)),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.hiragana = StringUtils.trim(textContent(eh));
				//
				textAndImage.id = getAttribute(word, "id");
				//
				Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
				//
			} // for
				//
			return textAndImages;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Collection<TextAndImage> toTextAndImages1(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words, final Page page) {
		//
		if (IterableUtils.size(words) != 1) {
			//
			return null;
			//
		} // if
			//
		ElementHandle eh = null;
		//
		String commonPrefix;
		//
		String[] ws = null;
		//
		TextAndImage textAndImage = null;
		//
		Collection<TextAndImage> textAndImages = null;
		//
		TextStringBuilder tsb = null;
		//
		final org.apache.commons.lang3.Strings strings = org.apache.commons.lang3.Strings.CS;
		//
		final ElementHandle thead = testAndApply(x -> IterableUtils.size(x) == 1, querySelectorAll(page, THEAD),
				x -> IterableUtils.get(x, 0), null);
		//
		final Iterable<ElementHandle> ths = querySelectorAll(thead, "th");
		//
		for (int i = 0; i < IterableUtils.size(ehs); i++) {
			//
			if (!startsWith(strings, textInput, StringUtils.trim(textContent(eh = IterableUtils.get(ehs, i))))) {
				//
				continue;
				//
			} // if
				//
			(textAndImage = new TextAndImage()).accentImage = toBufferedImage(screenshot(eh),
					e -> LoggerUtil.error(LOG, e.getMessage(), e));
			//
			textAndImage.curveImage = toBufferedImage(
					screenshot(querySelector(querySelector(querySelector(eh, ".."), ".."), CANVAS)),
					e -> LoggerUtil.error(LOG, e.getMessage(), e));
			//
			textAndImage.hiragana = StringUtils.trim(textContent(eh));
			//
			ws = StringUtils.split(
					StringUtils.trim(textContent(querySelector(IterableUtils.get(words, 0), CSS_SELECTOR_MIDASHI))),
					'・');
			//
			if (Boolean.logicalAnd(length(ws) == 2, IterableUtils.size(ehs) > 1)) {
				//
				TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
				//
				if (startsWith(strings, textInput,
						commonPrefix = Strings.commonPrefix(StringUtils.trim(textContent(IterableUtils.get(ehs, 0))),
								StringUtils.trim(textContent(IterableUtils.get(ehs, 1)))))) {
					//
					TextStringBuilderUtil.append(
							TextStringBuilderUtil.append(tsb,
									Strings.commonPrefix(ArrayUtils.get(ws, 0), ArrayUtils.get(ws, 1))),
							StringUtils.substringAfter(textInput, commonPrefix));
					//
				} // if
					//
				textAndImage.kanji = Util.toString(tsb);
				//
				textAndImage.voiceUrlImages = getVoiceUrlImages(
						querySelectorAll(querySelector(querySelector(eh, ".."), ".."), ".katsuyo_proc_button a"), page,
						"mp3");
				//
				textAndImage.conjugation = StringUtils
						.trim(textContent(testAndApply(x -> IterableUtils.size(ths) > x + 2, i,
								x -> IterableUtils.get(ths, x + 2), null)));
				//
			} // if
				//
				// きれいです
				//
			if (matches(textAndImage.kanji, "^\\p{InHiragana}+$")
					&& matches(textAndImage.hiragana, "^\\p{InHiragana}+$")
					&& !Objects.equals(textAndImage.kanji, textAndImage.hiragana)) {
				//
				continue;
				//
			} // if
				//
			testAndAccept((a, b) -> !contains(a, b),
					textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage, Util::add);
			//
		} // for
			//
		return textAndImages;
		//
	}

	@Nullable
	private static Collection<TextAndImage> toTextAndImages2(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words, final Iterable<String> partOfSpeeches, final Page page) {
		//
		if (IterableUtils.size(words) != 1) {
			//
			return null;
			//
		} // if
			//
		TextAndImage textAndImage = null;
		//
		Collection<TextAndImage> textAndImages = null;
		//
		ElementHandle eh = null;
		//
		final String[] ws = StringUtils.split(
				StringUtils.trim(textContent(querySelector(IterableUtils.get(words, 0), CSS_SELECTOR_MIDASHI))), '・');
		//
		int size = IterableUtils.size(ehs);
		//
		if (Util.contains(Arrays.asList("1グループの動詞", "2グループの動詞", "3グループの動詞", "い形容詞"),
				testAndApply(x -> IterableUtils.size(x) == 1, partOfSpeeches, x -> IterableUtils.get(x, 0), null))) {
			//
			size = 2;
			//
		} // if
			//
		final ElementHandle thead = testAndApply(x -> IterableUtils.size(x) == 1, querySelectorAll(page, THEAD),
				x -> IterableUtils.get(x, 0), null);
		//
		final Iterable<ElementHandle> ths = querySelectorAll(thead, "th");
		//
		for (int i = 0; i < length(ws); i++) {
			//
			if (!Boolean.logicalAnd(Objects.equals(textInput, ArrayUtils.get(ws, i)), length(ws) == 2)) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < size; j++) {
				//
				if (!StringUtils.isNotBlank(Strings.commonSuffix(ArrayUtils.get(ws, i),
						StringUtils.trim(textContent(eh = IterableUtils.get(ehs, j)))))) {
					//
					continue;
					//
				} // if
					//
				(textAndImage = new TextAndImage()).accentImage = toBufferedImage(screenshot(eh),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.curveImage = toBufferedImage(
						screenshot(querySelector(querySelector(querySelector(eh, ".."), ".."), CANVAS)),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.kanji = ArrayUtils.get(ws, i);
				//
				textAndImage.hiragana = StringUtils.trim(textContent(eh));
				//
				textAndImage.voiceUrlImages = getVoiceUrlImages(
						querySelectorAll(querySelector(querySelector(eh, ".."), ".."), ".katsuyo_proc_button a"), page,
						"mp3");
				//
				textAndImage.conjugation = StringUtils
						.trim(textContent(testAndApply(x -> IterableUtils.size(ths) > x + 2, i,
								x -> IterableUtils.get(ths, x + 2), null)));
				//
				Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
				//
			} // for
				//
		} // for
			//
		return textAndImages;
		//
	}

	@Nullable
	private static Map<String, byte[]> getVoiceUrlImages(@Nullable final Iterable<ElementHandle> ehs,
			@Nullable final Page page, final String format) {
		//
		ElementHandle eh = null;
		//
		Map<String, byte[]> map = null;
		//
		for (int k = 0; k < IterableUtils.size(ehs); k++) {
			//
			if ((eh = IterableUtils.get(ehs, k)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals("function", evaluate(page, "typeof get_pronounce_url"))) {
				//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
						Util.toString(evaluate(page, String.format("get_pronounce_url(\"%1$s\",\"%2$s\")",
								getAttribute(eh, "id"), StringUtils.defaultIfBlank(format, "mp3")))),
						screenshot(eh));
				//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Object evaluate(@Nullable final Page instance, final String expression) {
		return instance != null ? instance.evaluate(expression) : null;
	}

	@Nullable
	private static String getAttribute(@Nullable final ElementHandle instance, final String name) {
		return instance != null ? instance.getAttribute(name) : null;
	}

	@Nullable
	private static Collection<TextAndImage> toTextAndImages3(final Iterable<ElementHandle> words,
			final Document document, final Page page) {
		//
		Collection<TextAndImage> textAndImages = null;
		//
		TextAndImage textAndImage = null;
		//
		ElementHandle eh = null;
		//
		ElementHandle word = null;
		//
		String textContent = null;
		//
		String[] ss = null;
		//
		String id = null;
		//
		Iterable<String> conjugations = null;
		//
		for (int i = 0; i < IterableUtils.size(words); i++) {
			//
			conjugations = getConjugations(document, id = getAttribute(word = IterableUtils.get(words, i), "id"));
			//
			if (Boolean.logicalAnd(
					StringUtils.isNotBlank(
							textContent = StringUtils.trim(textContent(querySelector(word, "td:nth-child(2)")))),
					!StringUtils.contains(textContent, '・'))) {
				//
				// 求
				//
				(textAndImage = new TextAndImage()).accentImage = toBufferedImage(
						screenshot(eh = IValue0Util.getValue0(getFirstChild(3, word, ".accented_word"))),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.curveImage = toBufferedImage(
						screenshot(querySelector(word,
								String.format("td:nth-child(%1$s) .katsuyo_proc_accent_curve canvas", 3))),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				textAndImage.kanji = textContent;
				//
				textAndImage.hiragana = StringUtils.trim(textContent(eh));
				//
				setPartOfSpeech(textAndImage, getPartOfSpeech(document, id));
				//
				textAndImage.voiceUrlImages = getVoiceUrlImages(
						querySelectorAll(querySelector(querySelector(eh, ".."), ".."), ".katsuyo_proc_button a"), page,
						"mp3");
				//
				textAndImage.conjugation = testAndApply(x -> IterableUtils.size(x) == 1, conjugations,
						x -> IterableUtils.get(x, 0), null);
				//
				Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
				//
			} else if (length(ss = StringUtils.split(textContent, '・')) == 2) {
				//
				// きれい
				//
				for (int j = 0; j < 2; j++) {
					//
					(textAndImage = new TextAndImage()).accentImage = toBufferedImage(screenshot(
							eh = querySelector(word, String.format("td:nth-child(%1$s) .accented_word", j + 3))),
							e -> LoggerUtil.error(LOG, e.getMessage(), e));
					//
					textAndImage.curveImage = toBufferedImage(
							screenshot(querySelector(word,
									String.format("td:nth-child(%1$s) .katsuyo_proc_accent_curve canvas", j + 3))),
							e -> LoggerUtil.error(LOG, e.getMessage(), e));
					//
					textAndImage.hiragana = StringUtils.trim(textContent(eh));
					//
					setPartOfSpeech(textAndImage, getPartOfSpeech(document, id));
					//
					textAndImage.voiceUrlImages = getVoiceUrlImages(
							querySelectorAll(querySelector(querySelector(querySelector(eh, ".."), ".."), ".."),
									".katsuyo_proc_button a"),
							page, "mp3");
					//
					textAndImage.conjugation = getConjugation(conjugations, j);
					//
					Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
					//
					if (startsWith(org.apache.commons.lang3.Strings.CS, textAndImage.kanji = ArrayUtils.get(ss, j),
							"[な]")) {
						//
						break;
						//
					} // if
						//
				} // for
					//
			} // if
				//
		} // for
			//
		return textAndImages;
		//
	}

	private static String getConjugation(final Iterable<String> ss, final int i) {
		return testAndApply(x -> x != null && IterableUtils.size(x.key()) > x.rightInt(),
				ObjectIntImmutablePair.of(ss, i), x -> x != null ? IterableUtils.get(x.key(), x.rightInt()) : null,
				null);
	}

	@Nullable
	private static Iterable<String> getConjugations(final Element element, @Nullable final String id) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(element), FieldUtils::getAllFieldsList, null)),
				f -> Objects.equals(Util.getName(f), "childNodes")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalSelectorException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		Iterable<Element> previousElementSiblings = previousElementSiblings(
				(f == null || Narcissus.getField(element, f) != null) && StringUtils.isNotEmpty(id)
						? element.getElementById(id)
						: null);
		//
		if (IterableUtils.isEmpty(previousElementSiblings)) {
			//
			return Util.toList(Util.map(Util
					.filter(Util.filter(Util.stream(ElementUtil.children(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.children(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil.select(element, THEAD), x -> IterableUtils.get(x, 0), null)),
							x -> IterableUtils.get(x, 0), null))), x ->
					//
					Util.anyMatch(
							testAndApply(Objects::nonNull, StringUtils.split(NodeUtil.attr(x, CLASS), " "),
									Arrays::stream, null),
							y -> StringsUtil.startsWith(org.apache.commons.lang3.Strings.CS, y, "katsuyo_"))
					//
					), x -> StringUtils.isNotBlank(ElementUtil.text(x))), ElementUtil::text));
			//
		} else {
			//
			Element previousElementSibling = null;
			//
			for (int i = 0; i < IterableUtils.size(previousElementSiblings); i++) {
				//
				if ((previousElementSibling = IterableUtils.get(previousElementSiblings, i)) == null) {
					//
					continue;
					//
				} // if
					//
				if (!NodeUtil.hasAttr(previousElementSibling, "id")) {
					//
					return Util.toList(Util
							.map(Util.filter(Util.filter(Util.stream(ElementUtil.children(previousElementSibling)), x ->
							//
							Util.anyMatch(
									testAndApply(Objects::nonNull, StringUtils.split(NodeUtil.attr(x, CLASS), " "),
											Arrays::stream, null),
									y -> StringsUtil.startsWith(org.apache.commons.lang3.Strings.CS, y, "katsuyo_"))
							//
							), x -> StringUtils.isNotBlank(ElementUtil.text(x))), ElementUtil::text));
					//
				} else if (previousElementSibling.previousElementSibling() == null) {
					//
					return Util.toList(Util.map(Util.filter(Util.filter(Util.stream(element.select("thead tr th")), x ->
					//
					Util.anyMatch(
							testAndApply(Objects::nonNull, StringUtils.split(NodeUtil.attr(x, CLASS), " "),
									Arrays::stream, null),
							y -> StringsUtil.startsWith(org.apache.commons.lang3.Strings.CS, y, "katsuyo_"))
					//
					), x -> StringUtils.isNotBlank(ElementUtil.text(x))), ElementUtil::text));
					//
				} // if
					//
			} // for
				//
			return null;
			//
		} // if
			//
	}

	private static String getPartOfSpeech(final Element element, @Nullable final String id) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(element), FieldUtils::getAllFieldsList, null)),
				f -> Objects.equals(Util.getName(f), "childNodes")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalSelectorException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		Iterable<Element> previousElementSiblings = previousElementSiblings(
				(f == null || Narcissus.getField(element, f) != null) && StringUtils.isNotEmpty(id)
						? element.getElementById(id)
						: null);
		//
		if (IterableUtils.isEmpty(previousElementSiblings)) {
			//
			return ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
					ElementUtil.select(element, "thead * .midashi"), x -> IterableUtils.get(x, 0), null));
			//
		} else {
			//
			Element previousElementSibling = null;
			//
			for (int i = 0; i < IterableUtils.size(previousElementSiblings); i++) {
				//
				if ((previousElementSibling = IterableUtils.get(previousElementSiblings, i)) == null) {
					//
					continue;
					//
				} // if
					//
				if (!NodeUtil.hasAttr(previousElementSibling, "id")) {
					//
					return ElementUtil.text(previousElementSibling.selectFirst(CSS_SELECTOR_MIDASHI));
					//
				} // if
					//
			} // for
				//
			return ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
					ElementUtil.select(element, "thead * .midashi"), x -> IterableUtils.get(x, 0), null));
			//
		} // if
			//
	}

	@Nullable
	private static Elements previousElementSiblings(@Nullable final Element instance) {
		return instance != null ? instance.previousElementSiblings() : null;
	}

	@Nullable
	private static IValue0<ElementHandle> getFirstChild(final int start, @Nullable final ElementHandle word,
			final String cssSelector) {
		//
		Integer end = null;
		//
		for (int j = start; j < Integer.MAX_VALUE; j++) {
			//
			if (querySelector(word, String.format("td:nth-child(%1$s)", j)) == null) {
				//
				end = Integer.valueOf(j);
				//
				break;
				//
			} // if
				//
		} // for
			//
		ElementHandle eh = null;
		//
		IValue0<ElementHandle> iValue0 = null;
		//
		for (int j = start; end != null && j < end.intValue(); j++) {
			//
			if (StringUtils.isBlank(
					StringUtils.trim(textContent(eh = querySelector(word, String.format("td:nth-child(%1$s)", j)))))) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(iValue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			iValue0 = Unit.with(querySelector(eh, cssSelector));
			//
		} // for
			//
		return iValue0;
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, @Nullable final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	private static boolean contains(@Nullable final Iterable<TextAndImage> textAndImages,
			final TextAndImage textAndImage) {
		//
		TextAndImage temp = null;
		//
		for (int j = 0; j < IterableUtils.size(textAndImages); j++) {
			//
			try {
				//
				if (Boolean.logicalAnd(
						Objects.equals(getKanji(textAndImage), getKanji(temp = IterableUtils.get(textAndImages, j))),
						Arrays.equals(toByteArray(getAccentImage(textAndImage), "PNG"),
								toByteArray(getAccentImage(temp), "PNG")))) {
					//
					return true;
					//
				} // if
					//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // for
			//
		return false;
		//
	}

	@Nullable
	public static byte[] toByteArray(@Nullable final RenderedImage image, @Nullable final String format)
			throws IOException {
		//
		if (image == null || format == null) {
			//
			return null;
			//
		} // if
			//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(image, format, baos);
			//
			return baos.toByteArray();
			//
		} // try
			//
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static <T, E extends Throwable> T testAndGet(final boolean condition,
			final FailableSupplier<T, E> supplierTrue, @Nullable final FailableSupplier<T, E> supplierFalse) throws E {
		return condition ? FailableSupplierUtil.get(supplierTrue) : FailableSupplierUtil.get(supplierFalse);
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			@Nullable final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Nullable
	private static BufferedImage toBufferedImage(@Nullable final byte[] bs, final Consumer<IOException> consumer) {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			return testAndApply(Objects::nonNull, is, ImageIO::read, null);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static String textContent(@Nullable final ElementHandle instance) {
		return instance != null ? instance.textContent() : null;
	}

	@Nullable
	private static ElementHandle querySelector(@Nullable final ElementHandle instance, final String selector) {
		return instance != null ? instance.querySelector(selector) : null;
	}

	private static void setIcon(@Nullable final JLabel instance, final Icon icon) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Field f = getFieldByName(Util.getClass(instance), "objectLock");
		//
		if (f == null || Narcissus.getField(instance, f) != null) {
			//
			instance.setIcon(icon);
		} // if
			//
	}

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()) ? new JFrame()
				: Util.cast(JFrame.class, Narcissus.allocateInstance(JFrame.class));
		//
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		final Field f = getFieldByName(JFrame.class, "component");
		//
		final boolean gui = f == null || Narcissus.getField(jFrame, f) != null;
		//
		final VoiceManagerOjadAccentPanel instance = new VoiceManagerOjadAccentPanel();
		//
		instance.afterPropertiesSet();
		//
		if (gui) {
			//
			jFrame.add(instance);
			//
		} // if
			//
		pack(instance.window = jFrame);
		//
		if (gui) {
			//
			jFrame.setVisible(true);
			//
		} // if
			//
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Field f = getFieldByName(Util.getClass(instance), "objectLock");
		//
		if (f == null || Narcissus.getField(instance, f) != null) {
			//
			instance.pack();
			//
		} // if
			//
	}

	@Nullable
	private static String[] getFileExtensions(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static ContentInfo findMatch(@Nullable final ContentInfoUtil instance, @Nullable final byte[] bytes) {
		return instance != null && bytes != null ? instance.findMatch(bytes) : null;
	}

	@Nullable
	private static BrowserType chromium(@Nullable final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	@Nullable
	private static Page newPage(@Nullable final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

	@Nullable
	private static List<ElementHandle> querySelectorAll(@Nullable final Page instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	@Nullable
	private static List<ElementHandle> querySelectorAll(@Nullable final ElementHandle instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	@Nullable
	private static byte[] screenshot(@Nullable final ElementHandle instance) {
		return instance != null ? instance.screenshot() : null;
	}

	@Override
	public void keyTyped(final KeyEvent evt) {
	}

	@Override
	public void keyPressed(final KeyEvent evt) {
	}

	@Override
	public void keyReleased(@Nullable final KeyEvent evt) {
		//
		if (evt != null) {
			//
			final int keyCode = evt.getKeyCode();
			//
			final Object source = Util.getSource(evt);
			//
			if (Boolean.logicalAnd(keyCode == KeyEvent.VK_ENTER, source == tfIndex)) {
				//
				final String string = Util.getText(tfIndex);
				//
				if (NumberUtils.isCreatable(string)) {
					//
					int index = NumberUtils.toInt(string);
					//
					final int size = Util.getSize(getModel(jcbTextAndImage));
					//
					if (index >= size) {
						//
						index = size - 1;
						//
					} else if (index < 0) {
						//
						index = 0;
						//
					} // if
						//
					testAndAccept(x -> x < size, index, x -> Util.setSelectedIndex(jcbTextAndImage, x));
					//
				} // if
					//
			} else if (Boolean.logicalOr(
					Boolean.logicalAnd(Boolean.logicalOr(keyCode == KeyEvent.VK_ENTER, keyCode == KeyEvent.VK_SPACE),
							source == btnExecute),
					Boolean.logicalAnd(keyCode == KeyEvent.VK_ENTER, source == tfTextInput))) {
				//
				actionPerformed(new ActionEvent(btnExecute, 0, null));
				//
			} // if
				//
		} // if
			//
	}

	private static void testAndAccept(final IntPredicate predicate, final int value,
			@Nullable final IntConsumer consumer) {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static <E> ComboBoxModel<E> getModel(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getModel() : null;
	}

}