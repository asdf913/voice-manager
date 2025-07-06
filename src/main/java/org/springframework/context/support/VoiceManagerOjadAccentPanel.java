package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
import java.net.URI;
import java.net.URISyntaxException;
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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
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
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilderUtil;
import org.apache.jena.atlas.RuntimeIOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.ImageWriterSpiFormatIterableFactoryBean;
import org.springframework.beans.factory.InitializingBean;

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

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOjadAccentPanel extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -3247760984161467171L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerOjadAccentPanel.class);

	private static final String CSS_SELECTOR_MIDASHI = ".midashi";

	private static final String CANVAS = "canvas";

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

	private JTextComponent tfIndex = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Part Of Speech")
	private AbstractButton btnCopyPartOfSpeech = null;

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

	@Note("Accent")
	private JLabel lblAccent = null;

	@Note("Curve")
	private JLabel lblCurve = null;

	private JLabel lblCount = null;

	private Window window = null;

	private transient ComboBoxModel<Entry<String, String>> cbmCurve = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private DefaultTableModel dtmVoice = null;

	private static class TextAndImage {

		@Note("Kanji")
		private String kanji = null;

		@Note("Hiragana")
		private String hiragana = null;

		private String partOfSpeech = null;

		@Note("Accent Image")
		private BufferedImage accentImage = null;

		private Integer accentImageWidth = null;

		@Note("Curve Image")
		private BufferedImage curveImage = null;

		private Map<String, byte[]> voiceUrlImages = null;

		private Integer getAccentImageWidth() {
			//
			if (accentImageWidth == null && accentImage != null) {
				//
				final List<Field> fs = Util
						.toList(Util.filter(
								Util.stream(testAndApply(Objects::nonNull, Util.getClass(accentImage),
										FieldUtils::getAllFieldsList, null)),
								f -> Objects.equals(Util.getName(f), "raster")));
				//
				testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				final Field f = testAndApply(x -> IterableUtils.size(fs) == 1, fs, x -> IterableUtils.get(fs, 0), null);
				//
				if (f == null || Narcissus.getField(accentImage, f) != null) {
					//
					accentImageWidth = Integer.valueOf(accentImage.getWidth());
					//
				} // if
					//
			} // if
				//
			return accentImageWidth;
			//
		}

	}

	private JComboBox<TextAndImage> jcbTextAndImage = null;

	private transient MutableComboBoxModel<TextAndImage> mcbmTextAndImage = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());// TODO
		//
		Field f = getFieldByName(Util.getClass(this), "component");
		//
		if (f == null || Narcissus.getField(this, f) != null) {
			//
			add(new JLabel("Text"));
			//
			final String wrap = "wrap";
			//
			final String growx = "growx";
			//
			final int span = 2;
			//
			add(tfTextInput = new JTextField(), String.format("%1$s,%2$s,span %3$s", wrap, growx, span));
			//
			String html = null;
			//
			try (final InputStream is = Util.openStream(Util.toURL(URIBuilderUtil
					.build(new URIBuilder("https://www.gavo.t.u-tokyo.ac.jp").setPath("ojad/search/index/word:"))))) {
				//
				html = IOUtils.toString(is, StandardCharsets.UTF_8);
				//
			} // try
				//
			Iterable<Element> es = ElementUtil.select(Jsoup.parse(html), "[id=\"search_curve\"]");
			//
			testAndRunThrows(IterableUtils.size(es) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final Element element = testAndApply(x -> IterableUtils.size(x) == 1, es, x -> IterableUtils.get(x, 0),
					null);
			//
			add(new JLabel(ElementUtil.text(ElementUtil.previousElementSibling(element))));
			//
			es = ElementUtil.select(element, "option");
			//
			final DefaultComboBoxModel<Entry<String, String>> dcbm = new DefaultComboBoxModel<>();
			//
			Element e = null;
			//
			for (int i = 0; i < IterableUtils.size(es); i++) {
				//
				dcbm.addElement(
						Pair.of(Util.getValue(attribute(e = IterableUtils.get(es, i), "value")), ElementUtil.text(e)));
				//
			} // for
				//
			this.cbmCurve = dcbm;
			//
			final JComboBox<Entry<String, String>> jcbCurve = new JComboBox<>(dcbm);
			//
			final ListCellRenderer<? super Entry<String, String>> lcr = jcbCurve.getRenderer();
			//
			jcbCurve.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
				//
				final Component component = Util.getListCellRendererComponent(lcr, list, value, index, isSelected,
						cellHasFocus);
				//
				Util.setText(Util.cast(JLabel.class, component), Util.getValue(value));
				//
				return component;
				//
			});
			//
			add(jcbCurve, String.format("%1$s,span %2$s", wrap, 2));
			//
			add(new JLabel());
			//
			add(btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2));
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
			panalText.add(btnCopyPartOfSpeech = new JButton("Copy"), wrap);
			//
			panalText.add(new JLabel("Kanji"));
			//
			panalText.add(tfKanji = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyKanji = new JButton("Copy"), wrap);
			//
			panalText.add(new JLabel("Hiragana"));
			//
			panalText.add(tfHiragana = new JTextField(), String.format("%1$s,wmin %2$s", growx, 59));
			//
			panalText.add(btnCopyHiragana = new JButton("Copy"), wrap);
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
			panel.add(btnCopyAccentImage = new JButton("Copy"));
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
			(panel = new JPanel()).add(btnCopyCurveImage = new JButton("Copy"));
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
			final String gender = "Gender";
			//
			final JTable jTable = new JTable(dtmVoice = new DefaultTableModel(new Object[] { gender, "URL" }, 0) {

				private static final long serialVersionUID = -3821080690688708407L;

				@Override
				public Class<?> getColumnClass(final int columnIndex) {
					//
					if (Objects.equals(getColumnName(columnIndex), gender)) {
						//
						return byte[].class;
						//
					} // if
						//
					return super.getColumnClass(columnIndex);
					//
				}

			});
			//
			setMaxWidth(jTable.getColumn(gender), 44);
			//
			jTable.setDefaultRenderer(byte[].class, (table, value, isSelected, hasFocus, row, column) -> {
				//
				final byte[] bs = Util.cast(byte[].class, value);
				//
				try (final InputStream is = new ByteArrayInputStream(bs)) {
					//
					return new JLabel(new ImageIcon(ImageIO.read(is)));
					//
				} catch (final IOException ioe) {
					//
					throw new RuntimeIOException(ioe);
					//
				} // try
					//
			});
			//
			panelVoice.add(new JScrollPane(jTable), String.format("hmax %1$s", 56));
			//
			add(panelVoice, String.format("span %1$s,%2$s", 3, growx));
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
			Util.forEach(Stream.of(btnCopyPartOfSpeech, btnCopyKanji, btnCopyHiragana, btnCopyAccentImage,
					btnCopyCurveImage, btnSaveAccentImage, btnSaveCurveImage), x -> Util.setEnabled(x, false));
			//
			Util.forEach(Stream.of(tfPartOfSpeech, tfKanji, tfHiragana), x -> Util.setEditable(x, false));
			//
		} // if
			//
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
						return Util.intValue(textAndImage != null ? textAndImage.getAccentImageWidth() : null, 0);
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

	private static String getPartOfSpeech(final TextAndImage instance) {
		return instance != null ? instance.partOfSpeech : null;
	}

	@Nullable
	private static BufferedImage getAccentImage(@Nullable final TextAndImage instance) {
		return instance != null ? instance.accentImage : null;
	}

	@Nullable
	private static BufferedImage getCurveImage(@Nullable final TextAndImage instance) {
		return instance != null ? instance.curveImage : null;
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

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
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
			throw new Throwable(methodName);
			//
		}

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
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
			if (textAndImage != null) {
				//
				Util.forEach(Util.entrySet(textAndImage.voiceUrlImages),
						en -> Util.addRow(dtmVoice, new Object[] { Util.getValue(en), Util.getKey(en) }));
				//
			} // if
				//
			pack(window);
			//
		} else if (Objects.equals(source, btnCopyAccentImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = getAccentImage(Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage)));
			//
			setContents(getClipboard(), Reflection.newProxy(Transferable.class, ih), null);
			//
		} else if (Objects.equals(source, btnCopyCurveImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = getCurveImage(Util.cast(TextAndImage.class, Util.getSelectedItem(jcbTextAndImage)));
			//
			setContents(getClipboard(), Reflection.newProxy(Transferable.class, ih), null);
			//
		} else if (Objects.equals(source, btnCopyPartOfSpeech)) {
			//
			setContents(getClipboard(), new StringSelection(Util.getText(tfPartOfSpeech)), null);
			//
		} else if (Objects.equals(source, btnCopyKanji)) {
			//
			setContents(getClipboard(), new StringSelection(Util.getText(tfKanji)), null);
			//
		} else if (Objects.equals(source, btnCopyHiragana)) {
			//
			setContents(getClipboard(), new StringSelection(Util.getText(tfHiragana)), null);
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
		} // if
			//
	}

	private static <R> R testAndApply(final IntPredicate predicate, final int value, final IntFunction<R> functionTrue,
			final IntFunction<R> functionFalse) {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <R> R apply(final IntFunction<R> instance, final int value) {
		return instance != null ? instance.apply(value) : null;
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
			final Stream<Method> ms = testAndApply(Objects::nonNull, Util.getDeclaredMethods(Entry.class),
					Arrays::stream, null);
			//
			Util.put(map, "curve",
					Util.toString(testAndApply((a, b) -> a instanceof Entry, Util.getSelectedItem(cbmCurve),
							testAndApply(x -> IterableUtils.size(x) == 1,
									Util.toList(Util.filter(ms,
											m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "getKey"),
													Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {})))),
									x -> IterableUtils.get(x, 0), null),
							Narcissus::invokeMethod, null)));
			//
			final String baseUrl = "https://www.gavo.t.u-tokyo.ac.jp/ojad/search/index";
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
									"value"))), 1)
							* size);
					//
					PageUtil.navigate(page = newPage(browser), url = createUrl(baseUrl, map));
					//
				} // if
					//
			} // if
				//
			Util.forEach(Util.map(Util.sorted(Util.map(IntStream.range(1, Util.getSize(mcbmTextAndImage)), i -> -i)),
					i -> -i), i -> Util.removeElementAt(mcbmTextAndImage, i));
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
			final Collection<TextAndImage> textAndImages = getIfNull(toTextAndImages(ehs, words),
					Arrays.asList(() -> toTextAndImages1(ehs, textInput, words, p),
							() -> toTextAndImages2(ehs, textInput, words, partOfSpeeches, p),
							() -> toTextAndImages3(words, document, p)));
			//
			Util.forEach(Util.stream(textAndImages), x -> Util.addElement(mcbmTextAndImage, x));
			//
			Util.setText(lblCount, Integer.toString(IterableUtils.size(textAndImages)));
			//
			Util.setEditable(tfIndex, CollectionUtils.isNotEmpty(textAndImages));
			//
			if (CollectionUtils.isEmpty(textAndImages)) {
				//
				Util.setText(tfIndex, null);
				//
			} // if
				//
			if (IterableUtils.size(partOfSpeeches) == 1) {
				//
				Util.forEach(Util.stream(textAndImages), x -> {
					//
					if (x != null) {
						//
						x.partOfSpeech = IterableUtils.get(partOfSpeeches, 0);
						//
					} // if
						//
				});
				//
			} // if
				//
			Util.forEach(Util.stream(textAndImages), createTextAndImageConsumer());
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
		} catch (final IOException |

				URISyntaxException e) {
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

	private static Consumer<TextAndImage> createTextAndImageConsumer() {
		//
		return x -> {
			//
			final Map<String, byte[]> voiceUrlImages = x != null ? x.voiceUrlImages : null;
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
					if (!StringUtils.endsWith(sb, "/")) {
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

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
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
			@Nullable final Iterable<ElementHandle> words) {
		//
		TextAndImage textAndImage = null;
		//
		ElementHandle eh = null;
		//
		if (IterableUtils.size(ehs) == 1) {
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
			return Collections.singleton(textAndImage);
			//
		} // if
			//
		Collection<TextAndImage> textAndImages = null;
		//
		if (IterableUtils.size(words) == IterableUtils.size(ehs)) {
			//
			for (int i = 0; i < IterableUtils.size(words); i++) {
				//
				(textAndImage = new TextAndImage()).kanji = StringUtils
						.trim(textContent(querySelector(IterableUtils.get(words, i), CSS_SELECTOR_MIDASHI)));
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
		for (int i = 0; i < IterableUtils.size(ehs); i++) {
			//
			if (!StringUtils.equals(textInput, StringUtils.trim(textContent(eh = IterableUtils.get(ehs, i))))) {
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
				if (StringUtils.startsWith(textInput,
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
	private static Map<String, byte[]> getVoiceUrlImages(@Nullable final Iterable<ElementHandle> ehs, final Page page,
			final String format) {
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
		for (int i = 0; i < IterableUtils.size(words); i++) {
			//
			id = getAttribute(word = IterableUtils.get(words, i), "id");
			//
			if (Boolean.logicalAnd(
					StringUtils.isNotBlank(
							textContent = StringUtils.trim(textContent(querySelector(word, "td:nth-child(2)")))),
					!StringUtils.contains(textContent, '・'))) {
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
				textAndImage.partOfSpeech = getPartOfSpeech(document, id);
				//
				textAndImage.voiceUrlImages = getVoiceUrlImages(
						querySelectorAll(querySelector(querySelector(eh, ".."), ".."), ".katsuyo_proc_button a"), page,
						"mp3");
				//
				Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
				//
			} else if (length(ss = StringUtils.split(textContent, '・')) == 2) {
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
					textAndImage.partOfSpeech = getPartOfSpeech(document, id);
					//
					textAndImage.voiceUrlImages = getVoiceUrlImages(
							querySelectorAll(querySelector(querySelector(querySelector(eh, ".."), ".."), ".."),
									".katsuyo_proc_button a"),
							page, "mp3");
					//
					Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
					//
					if (StringUtils.endsWith(textAndImage.kanji = ArrayUtils.get(ss, j), "[な]")) {
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
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
	private static byte[] toByteArray(@Nullable final RenderedImage image, @Nullable final String format)
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

	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplierTrue,
			final Supplier<T> supplierFalse) {
		return condition ? Util.get(supplierTrue) : Util.get(supplierFalse);
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

}