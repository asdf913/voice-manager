package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableBiPredicate;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;
import com.helger.css.ECSSUnit;
import com.helger.css.propertyvalue.CSSSimpleValueWithUnit;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
import j2html.rendering.FlatHtml;
import j2html.rendering.HtmlBuilder;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerPdfPanel extends JPanel implements Titled, InitializingBean, ActionListener,
		ApplicationContextAware, EnvironmentAware, DocumentListener {

	private static final long serialVersionUID = 284477348908531649L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerPdfPanel.class);

	private static final String GROWX = "growx";

	private static final FailableBiConsumer<JTextComponent, HttpURLConnection, IOException> J_TEXT_COMPONENT_HTTP_URL_CONNECTION_FAILABLE_BI_PREDICATE = (
			a, b) -> {
		//
		if (b != null) {
			//
			Util.setText(a, Integer.toString(b.getResponseCode()));
			//
		} // if
			//
	};

	private transient SpeechApi speechApi = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton cbIsOriginalSize = null;

	@Note("HTML")
	private JTextComponent taHtml = null;

	@Note("Text")
	private JTextComponent tfText = null;

	@Note("Font Size 1")
	private JTextComponent tfFontSize1 = null;

	@Note("Font Size 2")
	private JTextComponent tfFontSize2 = null;

	@Note("Image URL")
	private JTextComponent tfImageUrl = null;

	@Note("Image URL State Code")
	private JTextComponent tfImageUrlStateCode = null;

	private JTextComponent tfImageUrlMimeType = null;

	private transient Document taHtmlDocument = null;

	private transient ComboBoxModel<ECSSUnit> cbmFontSize1 = null;

	private transient ComboBoxModel<String> cbmVoiceId = null;

	private transient ApplicationContext applicationContext = null;

	private transient PropertyResolver propertyResolver = null;

	@Note("org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation.rectangle Size")
	private Number pdAnnotationRectangleSize = null;

	private Number speechVolume = null;

	@Nullable
	private Map<Integer, String> speechSpeedMap = null;

	private Map<Integer, String> fontSizeAndUnitMap = null;

	@Override
	public String getTitle() {
		return "PDF";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setPdAnnotationRectangleSize(final Object pdAnnotationRectangleSize) {
		//
		final IValue0<Number> iValue0 = getNumber(pdAnnotationRectangleSize);
		//
		if (iValue0 != null) {
			//
			this.pdAnnotationRectangleSize = IValue0Util.getValue0(iValue0);
			//
		} // if
			//
	}

	public void setSpeechVolume(final Object speechVolume) {
		//
		final IValue0<Number> iValue0 = getNumber(speechVolume);
		//
		if (iValue0 != null) {
			//
			this.speechVolume = IValue0Util.getValue0(iValue0);
			//
		} // if
			//
	}

	private static IValue0<Number> getNumber(@Nullable final Object object) {
		//
		IValue0<Number> iValue0 = null;
		//
		if (object == null) {
			//
			iValue0 = Unit.with(null);
			//
		} else if (object instanceof Number number) {
			//
			iValue0 = Unit.with(number);
			//
		} else if (object instanceof String string && NumberUtils.isCreatable(string)) {
			//
			iValue0 = Unit.with(NumberUtils.createNumber(string));
			//
		} // if
			//
		final String string = Util.toString(object);
		//
		if (iValue0 == null && NumberUtils.isCreatable(string)) {
			//
			iValue0 = Unit.with(NumberUtils.createNumber(string));
			//
		} // if
			//
		if (iValue0 == null) {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
		return iValue0;
		//
	}

	public void setSpeechSpeedMap(final String string) throws JsonProcessingException {
		//
		final Object object = ObjectMapperUtil.readValue(new ObjectMapper(), string, Object.class);
		//
		if (object == null) {
			//
			this.speechSpeedMap = null;
			//
			return;
			//
		} else if (object instanceof Map m) {
			//
			setSpeechSpeedMap(m);
			//
			return;
			//
		} // if
			//
		throw new IllegalArgumentException();
		//
	}

	public void setSpeechSpeedMap(final Map map) {
		//
		final Iterable<Entry<?, ?>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			Integer i = null;
			//
			Number n = null;
			//
			for (final Entry<?, ?> entry : entrySet) {
				//
				i = null;
				//
				if (Util.getKey(entry) instanceof CharSequence cs) {
					//
					if ((n = testAndApply(NumberUtils::isCreatable, Util.toString(cs), NumberUtils::createNumber,
							null)) != null) {
						//
						i = Integer.valueOf(n.intValue());
						//
					} else {
						//
						throw new IllegalArgumentException();
						//
					} // if
						//
				} // if
					//
				Util.put(speechSpeedMap = ObjectUtils.getIfNull(speechSpeedMap, LinkedHashMap::new), i,
						Util.toString(Util.getValue(entry)));
				//
			} // for
				//
		} // if
			//
	}

	private class VoiceIdListCellRenderer implements ListCellRenderer<Object> {

		private ListCellRenderer<Object> listCellRenderer = null;

		private String commonPrefix = null;

		@Override
		@Nullable
		public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value,
				final int index, final boolean isSelected, final boolean cellHasFocus) {
			//
			final String s = Util.toString(value);
			//
			try {
				//
				final String name = SpeechApi.getVoiceAttribute(speechApi, s, "Name");
				//
				if (StringUtils.isNotBlank(name)) {
					//
					return getListCellRendererComponent(listCellRenderer, list, name, index, isSelected, cellHasFocus);
					//
				} // if
					//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			return getListCellRendererComponent(listCellRenderer, list,
					StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
					index, isSelected, cellHasFocus);
			//
		}

		@Nullable
		private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
				final JList<? extends E> list, final E value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			//
			return instance != null
					? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
					: null;
			//
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		// Font Size
		//
		add(new JLabel("Font Size"));
		//
		Entry<String, Object> entry = getNumberAndUnit(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.fontSize1"));
		//
		add(tfFontSize1 = new JTextField(Util.getKey(entry)), String.format("%1$s,wmin %2$s", GROWX, 100));
		//
		add(new JComboBox<>(
				cbmFontSize1 = new DefaultComboBoxModel<>(ArrayUtils.insert(0, ECSSUnit.values(), (ECSSUnit) null))),
				"wrap");
		//
		setSelectedItem(cbmFontSize1, Util.getValue(entry));
		//
		// HTML
		//
		add(new JLabel("HTML"));
		//
		final String html = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.html");
		//
		final JScrollPane jsp = new JScrollPane(taHtml = new JTextArea(html));
		//
		addDocumentListener(taHtmlDocument = taHtml.getDocument(), this);
		//
		final Dimension preferredSize = jsp.getPreferredSize();
		//
		if (preferredSize != null) {
			//
			jsp.setPreferredSize(new Dimension((int) preferredSize.getWidth(), (int) preferredSize.getHeight() * 2));
			//
		} // if
			//
		final int span = 4;
		//
		add(jsp, String.format("%1$s,%2$s,span %3$s", GROWX, "wrap", span));
		//
		// Font Size
		//
		add(new JLabel("Font Size"));
		//
		add(tfFontSize2 = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.fontSize2")),
				String.format("%1$s,%2$s,wmin %3$s", GROWX, "wrap", 100));
		//
		setSelectedItem(cbmFontSize1, Util.getValue(entry));
		//
		setFontSizeAndUnit(html);
		//
		// Text
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.text")),
				String.format("%1$s,%2$s,span %3$s", GROWX, "wrap", span));
		//
		// Voice ID
		//
		add(new JLabel("Voice Id"));
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(speechApi);
		//
		if ((cbmVoiceId = testAndApply(Objects::nonNull, voiceIds,
				x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
			//
			final JComboBox<Object> jcbVoiceId = new JComboBox<>(Util.cast(ComboBoxModel.class, cbmVoiceId));
			//
			final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer();
			//
			voiceIdListCellRenderer.listCellRenderer = getRenderer(Util.cast(JComboBox.class, jcbVoiceId));
			//
			voiceIdListCellRenderer.commonPrefix = String.join("",
					StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
			//
			jcbVoiceId.setRenderer(voiceIdListCellRenderer);
			//
			add(jcbVoiceId, String.format("%1$s,span %2$s", "wrap", span));
			//
			final String s = PropertyResolverUtil.getProperty(propertyResolver,
					"org.springframework.context.support.VoiceManagerPdfPanel.voiceId");
			//
			Object element = null;
			//
			for (int i = 0; i < cbmVoiceId.getSize(); i++) {
				//
				if (s != null && Util.contains(Arrays.asList(element = cbmVoiceId.getElementAt(i),
						speechApi.getVoiceAttribute(Util.toString(element), "Name")), s)) {
					//
					setSelectedItem(cbmVoiceId, element);
					//
				} // if
					//
			} // for
				//
		} // if
			//
			// Image Url
			//
		add(new JLabel("Image URL"));
		//
		add(tfImageUrl = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.imageUrl")),
				String.format("%1$s,span %2$s", GROWX, span - 2));
		//
		add(tfImageUrlStateCode = new JTextField(), String.format("wmin %1$s", 27));
		//
		add(tfImageUrlMimeType = new JTextField(), String.format("%1$s,wmin %2$s", "wrap", 65));
		//
		setEditable(false, tfImageUrlStateCode, tfImageUrlMimeType);
		//
		add(new JLabel("Original Size"));
		//
		add(cbIsOriginalSize = new JCheckBox(), "wrap");
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"));
		//
		btnExecute.addActionListener(this);
		//
		final Double width = getWidth(btnExecute.getPreferredSize());
		//
		if (width != null) {
			//
			Double height = getHeight(getPreferredSize(tfFontSize1));
			//
			if (height != null) {
				//
				setMaximumSize(tfFontSize1, new Dimension(width.intValue(), height.intValue()));
				//
			} // if
				//
			if ((height = getHeight(getPreferredSize(tfFontSize2))) != null) {
				//
				setMaximumSize(tfFontSize2, new Dimension(width.intValue(), height.intValue()));
				//
			} // if
				//
		} // if
			//
	}

	private static void setEditable(final boolean flag, final JTextComponent a, final JTextComponent b,
			@Nullable final JTextComponent... bs) {
		//
		setEditable(a, flag);
		//
		setEditable(b, flag);
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			setEditable(bs[i], flag);
			//
		} //
			//
	}

	private static void setEditable(@Nullable final JTextComponent instance, final boolean flag) {
		if (instance != null) {
			instance.setEditable(flag);
		}
	}

	private static void addDocumentListener(@Nullable final Document instance, final DocumentListener listener) {
		if (instance != null) {
			instance.addDocumentListener(listener);
		}
	}

	@Nullable
	private static Dimension getPreferredSize(@Nullable final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

	private static void setMaximumSize(@Nullable final Component instance, final Dimension maximumSize) {
		if (instance != null) {
			instance.setMaximumSize(maximumSize);
		}
	}

	@Nullable
	private static Double getHeight(@Nullable final Dimension instance) {
		return instance != null ? Double.valueOf(instance.getHeight()) : null;
	}

	@Nullable
	private static Double getWidth(@Nullable final Dimension instance) {
		return instance != null ? Double.valueOf(instance.getWidth()) : null;
	}

	private static Entry<String, Object> getNumberAndUnit(final String input) {
		//
		final StringBuilder sb = testAndApply(Objects::nonNull, input, StringBuilder::new, null);
		//
		String s = null, strNumber = null;
		//
		for (int i = StringUtils.length(sb) - 1; i >= 0; i--) {
			//
			if (NumberUtils.isCreatable(s = sb.substring(0, i))) {
				//
				strNumber = Util.toString(s);
				//
				sb.delete(0, i);
				//
				break;
				//
			} // if
				//
		} // while
			//
		Object ecssUnit = null;
		//
		final List<ECSSUnit> list = Util.toList(Util.filter(Arrays.stream(ECSSUnit.values()),
				x -> StringUtils.equalsIgnoreCase(name(x), Util.toString(sb))));
		//
		final int size = IterableUtils.size(list);
		//
		if (size == 1) {
			//
			ecssUnit = IterableUtils.get(list, 0);
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return Pair.of(strNumber, ecssUnit);
		//
	}

	@Nullable
	private static LayoutManager getLayoutManager(final AutowireCapableBeanFactory acbf,
			final Iterable<Entry<String, Object>> entrySet) throws Exception {
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<LayoutManager> iValue0 = null;
		//
		List<Field> fs = null;
		//
		for (final Entry<String, Object> entry : entrySet) {
			//
			if (!(Util.getValue(entry) instanceof LayoutManager)) {
				//
				continue;
				//
			} // if
				//
			fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(acbf))),
					x -> Objects.equals(Util.getName(x), "singletonObjects")));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if (FactoryBeanUtil
						.getObject(
								Util.cast(
										FactoryBean.class, MapUtils
												.getObject(
														Util.cast(Map.class,
																Narcissus.getObjectField(acbf,
																		IterableUtils.get(fs, i))),
														Util.getKey(entry)))) instanceof LayoutManager lm) {
					//
					if (iValue0 == null) {
						//
						iValue0 = Unit.with(lm);
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
		} // for
			//
		return IValue0Util.getValue0(iValue0);
		//
	}

	@Nullable
	private static String name(@Nullable final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	@Nullable
	private static <E> ListCellRenderer<? super E> getRenderer(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			setText("", tfImageUrlStateCode, tfImageUrlMimeType);
			//
			final Path pathHtml = Path.of("test.html");
			//
			final Map<String, String> style = new LinkedHashMap<>(
					Map.of("text-align", "center", "display", "block", "margin-left", "auto", "margin-right", "auto"));
			//
			final BigDecimal fontSize = testAndApply(NumberUtils::isCreatable, Util.getText(tfFontSize1),
					NumberUtils::createBigDecimal, null);
			//
			final ECSSUnit ecssUnit = Util.cast(ECSSUnit.class, getSelectedItem(cbmFontSize1));
			//
			if (fontSize != null && ecssUnit != null) {
				//
				style.put("font-size", new CSSSimpleValueWithUnit(fontSize, ecssUnit).getFormatted());
				//
			} // if
				//
			final File file = toFile(Path
					.of(StringUtils.joinWith(".", StringUtils.defaultIfBlank(Util.getText(tfText), "test"), "pdf")));
			//
			LoggerUtil.info(LOG, getAbsolutePath(file));
			//
			PDDocument document = null;
			//
			try {
				//
				final HtmlBuilder<StringBuilder> htmlBuilder = FlatHtml.inMemory();
				//
				FileUtils.writeStringToFile(toFile(pathHtml), Util.toString(htmlBuilder != null
						? htmlBuilder
								.appendStartTag(
										"div")
								.appendAttribute("style",
										Util.collect(Util.map(Util.stream(Util.entrySet(style)),
												x -> StringUtils.joinWith(":", Util.getKey(x), Util.getValue(x))),
												Collectors.joining(";")))
								.completeTag().appendUnescapedText(Util.getText(taHtml)).appendEndTag("div").output()
						: null), StandardCharsets.UTF_8, false);
				//
				document = Loader.loadPDF(pdf(pathHtml));
				//
				final IH ih = new IH();
				//
				final StringMap stringMap = Reflection.newProxy(StringMap.class, ih);
				//
				if (stringMap != null) {
					//
					stringMap.setString("text", Util.getText(tfText));
					//
					stringMap.setString("voiceId", Util.toString(getSelectedItem(cbmVoiceId)));
					//
					stringMap.setString("imageUrl", Util.getText(tfImageUrl));
					//
					stringMap.setString("html", Util.getText(taHtml));
					//
				} // if
					//
				final FloatMap floatMap = Reflection.newProxy(FloatMap.class, ih);
				//
				if (floatMap != null) {
					//
					floatMap.setFloat("fontSize", floatValue(testAndApply(NumberUtils::isCreatable,
							Util.getText(tfFontSize2), NumberUtils::createNumber, null), 14));
					//
					floatMap.setFloat("defaultSize", floatValue(pdAnnotationRectangleSize, 61));
					//
				} // if
					//
				final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
				//
				ObjectMap.setObject(objectMap, PDDocument.class, document);
				//
				ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
				//
				ObjectMap.setObject(objectMap, PDFont.class, new PDType1Font(FontName.HELVETICA));
				//
				ObjectMap.setObject(objectMap, File.class, file);
				//
				ObjectMap.setObject(objectMap, StringMap.class, stringMap);
				//
				ObjectMap.setObject(objectMap, FloatMap.class, floatMap);
				//
				ObjectMap.setObject(objectMap, VoiceManagerPdfPanel.class, this);
				//
				addTextAndVoice(objectMap,
						ObjectUtils.getIfNull(speechSpeedMap, VoiceManagerPdfPanel::getDefaultSpeechSpeedMap),
						Util.intValue(speechVolume, 100), isSelected(cbIsOriginalSize));
				//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} // if
			//
	}

	private static void setText(final String string, final JTextComponent a, final JTextComponent b,
			@Nullable final JTextComponent... jtcs) {
		//
		Util.setText(a, string);
		//
		Util.setText(b, string);
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			Util.setText(jtcs[i], string);
			//
		} // for
			//
	}

	private static boolean isSelected(@Nullable final AbstractButton instance) {
		return instance != null && instance.isSelected();
	}

	@Override
	public void changedUpdate(final DocumentEvent evt) {
		//
	}

	@Override
	public void insertUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), taHtmlDocument)) {
			//
			setFontSizeAndUnit(Util.getText(taHtml));
			//
		} // if
			//
	}

	@Override
	public void removeUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), taHtmlDocument)) {
			//
			setFontSizeAndUnit(Util.getText(taHtml));
			//
		} // if
			//
	}

	private void setFontSizeAndUnit(final String html) {
		//
		setFontSizeAndUnit(StringUtils.length(replaceAll(
				text(body(testAndApply(Objects::nonNull, html, Jsoup::parse, null))), "\\([^\\(\\)]+\\)", "")));// TODO
		//
	}

	private void setFontSizeAndUnit(final int length) {
		//
		if (fontSizeAndUnitMap == null) {
			//
			// TODO
			//
			putAll(fontSizeAndUnitMap = new LinkedHashMap<>(Map.of(Integer.valueOf(5), "160px", Integer.valueOf(6),
					"133px", Integer.valueOf(7), "114px", Integer.valueOf(8), "100px", Integer.valueOf(9), "88px",
					Integer.valueOf(10), "80px", Integer.valueOf(11), "72px", Integer.valueOf(12), "66px",
					Integer.valueOf(13), "61px", Integer.valueOf(14), "56px")),
					Map.of(Integer.valueOf(15), "53px", Integer.valueOf(16), "50px", Integer.valueOf(17), "45px",
							Integer.valueOf(18), "43px", Integer.valueOf(19), "42px", Integer.valueOf(30), "26px",
							Integer.valueOf(36), "22px"));
			//
		} // if
			//
		final Integer key = Integer.valueOf(length);
		//
		if (Util.containsKey(fontSizeAndUnitMap, key)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, MapUtils.getObject(fontSizeAndUnitMap, key),
					StringBuilder::new, null);
			//
			final ECSSUnit[] us = ECSSUnit.values();
			//
			ECSSUnit u = null;
			//
			String name = null;
			//
			for (int i = 0; i < StringUtils.length(sb); i++) {
				//
				for (int j = 0; us != null && i < us.length; j++) {
					//
					if (StringUtils.endsWithIgnoreCase(Util.toString(sb), name = name(u = us[j]))) {
						//
						Util.setText(tfFontSize1, sb.substring(0, StringUtils.length(sb) - StringUtils.length(name)));
						//
						setSelectedItem(cbmFontSize1, u);
						//
						return;
						//
					} // if
						//
				} // for
					//
			} // for
				//
		} // if
			//
	}

	private static <K, V> void putAll(@Nullable final Map<K, V> a, @Nullable final Map<? extends K, ? extends V> b) {
		if (a != null && b != null) {
			a.putAll(b);
		}
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance, @Nullable final Object anItem) {
		if (instance != null) {
			instance.setSelectedItem(anItem);
		}
	}

	@Nullable
	private static String replaceAll(@Nullable final String instance, final String regex, final String replacement) {
		return instance != null ? instance.replaceAll(regex, replacement) : instance;
	}

	@Nullable
	private static String text(@Nullable final Element instance) {
		return instance != null ? instance.text() : null;
	}

	@Nullable
	private static Element body(@Nullable final org.jsoup.nodes.Document instance) {
		return instance != null ? instance.body() : null;
	}

	@Nullable
	private static Document getDocument(@Nullable final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
	}

	private static Map<Integer, String> getDefaultSpeechSpeedMap() {
		//
		final Map<Integer, String> map = new LinkedHashMap<>(Collections.singletonMap(0, "100% Speed"));
		//
		map.put(-1, "90% Speed");
		//
		map.put(-2, "80% Speed");
		//
		map.put(-3, "70% Speed");
		//
		map.put(-4, "60% Speed");
		//
		map.put(-5, "50% Speed");
		//
		map.put(-6, "40% Speed");
		//
		map.put(-7, "30% Speed");
		//
		map.put(-8, "20% Speed");
		//
		map.put(-9, "10% Speed");
		//
		return map;
		//
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static interface ObjectMap {

		@Nullable
		<T> T getObject(final Class<?> clz);

		<T> void setObject(final Class<T> key, @Nullable final T value);

		@Nullable
		static <T> T getObject(@Nullable final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> void setObject(@Nullable final ObjectMap instance, final Class<T> key, @Nullable final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
		}

	}

	private static interface FloatMap {

		float getFloat(final String key);

		void setFloat(final String key, final float i);

		private static float getFloat(@Nullable final FloatMap instance, final String key, final float defaultValue) {
			return instance != null ? instance.getFloat(key) : defaultValue;
		}

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, @Nullable final String value);

		@Nullable
		static String getString(@Nullable final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}
	}

	private static class IH implements InvocationHandler {

		private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

		private Map<Object, Object> objects = null;

		private Map<Object, Object> strings = null;

		private Map<Object, Object> floats = null;

		private Map<Object, Object> getObjects() {
			if (objects == null) {
				objects = new LinkedHashMap<>();
			}
			return objects;
		}

		private Map<Object, Object> getStrings() {
			if (strings == null) {
				strings = new LinkedHashMap<>();
			}
			return strings;
		}

		private Map<Object, Object> getFloats() {
			if (floats == null) {
				floats = new LinkedHashMap<>();
			}
			return floats;
		}

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ObjectMap) {
				//
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!Util.containsKey(getObjects(), key)) {
						//
						throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
								testAndApply(IH::isArray, Util.cast(Class.class, key), Util::getSimpleName, x -> key)));
						//
					} // if
						//
					return MapUtils.getObject(getObjects(), key);
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					Util.put(getObjects(), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} // if
				//
			IValue0<?> iValue0 = handleStringMap(methodName, getStrings(), args);
			//
			if (iValue0 != null || (iValue0 = handleFloatMap(methodName, getFloats(), args)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		@Nullable
		private static IValue0<Object> handleFloatMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getFloat") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!Util.containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
							testAndApply(IH::isArray, Util.cast(Class.class, key), Util::getSimpleName, x -> key)));
					//
				} // if
					//
				return Unit.with(MapUtils.getObject(map, key));
				//
			} else if (Objects.equals(methodName, "setFloat") && args != null && args.length > 1) {
				//
				Util.put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		@Nullable
		private static IValue0<Object> handleStringMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getString") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!Util.containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE, key));
					//
				} // if
					//
				return Unit.with(Util.get(map, key));
				//
			} else if (Objects.equals(methodName, "setString") && args != null && args.length > 1) {
				//
				Util.put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static boolean isArray(@Nullable final OfField<?> instance) {
			return instance != null && instance.isArray();
		}

	}

	private static void addTextAndVoice(@Nullable final ObjectMap objectMap, final Map<Integer, String> map,
			final int volume, final boolean isOrginialSize) throws IOException {
		//
		final PDDocument document = ObjectMap.getObject(objectMap, PDDocument.class);
		//
		if (document != null && document.getNumberOfPages() > 0) {
			//
			final PDPage pd = document.getPage(0);
			//
			final PDRectangle md = getMediaBox(pd);
			//
			final PDFRenderer pdfRenderer = new PDFRenderer(document);
			//
			final Path page1Path = Path.of("page1.png");
			//
			LoggerUtil.info(LOG, getAbsolutePath(toFile(page1Path)));
			//
			BufferedImage bi = pdfRenderer.renderImage(0);
			//
			final FloatMap floatMap = ObjectMap.getObject(objectMap, FloatMap.class);
			//
			final float size = floatValue(
					testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null), getWidth(bi), MapUtils.size(map),
							(a, b) -> divide(BigDecimal.valueOf(a), BigDecimal.valueOf(b)), null),
					FloatMap.getFloat(floatMap, "defaultSize", (float) 61.2));
			//
			ImageIO.write(bi, "png", toFile(page1Path));
			//
			final Integer largestY = getLargestY(bi);
			//
			Files.delete(page1Path);
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd, AppendMode.PREPEND, true);
			//
			final Path pathAudio = Path.of("test.wav");
			//
			LoggerUtil.info(LOG, getAbsolutePath(toFile(pathAudio)));
			//
			int index = 0;
			//
			Integer key = null;
			//
			String value = null;
			//
			Duration duration = null;
			//
			final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
			//
			final String text = StringMap.getString(stringMap, "text");
			//
			final PDDocumentInformation pdDocumentInformation = document.getDocumentInformation();
			//
			setSubject(pdDocumentInformation, text);
			//
			setCustomMetadataValue(pdDocumentInformation, "html", StringMap.getString(stringMap, "html"));
			//
			setCustomMetadataValue(pdDocumentInformation, "text", StringMap.getString(stringMap, "text"));
			//
			final SpeechApi speechApi = ObjectMap.getObject(objectMap, SpeechApi.class);
			//
			final String voiceId = StringMap.getString(stringMap, "voiceId");
			//
			setCustomMetadataValue(pdDocumentInformation, "voice",
					speechApi != null ? speechApi.getVoiceAttribute(voiceId, "Name") : null);
			//
			PDEmbeddedFile pdfEmbeddedFile = null;
			//
			PDComplexFileSpecification fileSpec = null;
			//
			PDAnnotationFileAttachment attachment = null;
			//
			float lastHeight = 0;
			//
			for (final Entry<Integer, String> entry : Util.entrySet(map)) {
				//
				if ((key = Util.getKey(entry)) == null) {
					//
					continue;
					//
				} // if
					//
				writeVoiceToFile(speechApi, text, voiceId, Util.intValue(key, 0), volume, toFile(pathAudio));
				//
//				try (final InputStream is = PdfTest.class.getResourceAsStream("\\NotoSansCJKjp-Regular.otf")) {
				//
//					font = PDType0Font.load(document, new OTFParser().parseEmbedded(is), false);
				//
//				} // try
				//
				Pattern pattern = null;
				//
				try (final InputStream is = Files.newInputStream(pathAudio)) {
					//
					(pdfEmbeddedFile = new PDEmbeddedFile(document, is)).setSubtype(getMimeType(pathAudio));
					//
					testAndAccept((a, b) -> b != null, pdfEmbeddedFile, toFile(pathAudio),
							(a, b) -> setSize(a, Util.intValue(length(b), 0)));
					//
					(fileSpec = new PDComplexFileSpecification()).setFile(getName(toFile(pathAudio)));
					//
					fileSpec.setEmbeddedFile(pdfEmbeddedFile);
					//
					(attachment = new PDAnnotationFileAttachment()).setFile(fileSpec);
					//
					// Position on the page
					//
					attachment.setRectangle(new PDRectangle(index++ * size,
							getHeight(md) - Util.intValue(largestY, 0) - size, size, size));
					//
					attachment.setContents(value = Util.getValue(entry));
					//
					Util.add(getAnnotations(pd), attachment);
					//
					// Label (Speed)
					//
					cs.beginText();
					//
					final PDFont font = ObjectMap.getObject(objectMap, PDFont.class);
					//
					final float fontSize = FloatMap.getFloat(floatMap, "fontSize", 14);
					//
					cs.setFont(font, fontSize);
					//
					final String value_ = value;
					//
					cs.newLineAtOffset((index - 1) * size + getTextWidth(
							//
							value = testAndApply(x -> and(x, y -> matches(y), y -> groupCount(y) > 0),
									matcher(pattern = ObjectUtils.getIfNull(pattern,
											() -> Pattern.compile("^(\\d+%).+$")), value),
									x -> group(x, 1), x -> value_)
							//
							, font, fontSize) / 2, lastHeight = (getHeight(md) - Util.intValue(largestY, 0) - size
					//
									- (font.getFontDescriptor().getAscent() / 1000 * fontSize)
									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize))
					//
					);
					//
//					cs.newLineAtOffset((pd.getMediaBox().getWidth() - getTextWidth(text, font, fontSize)) / 2,
//							pd.getMediaBox().getHeight() - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
//									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize));
					//
					cs.showText(value);
					//
					cs.endText();
					//
					// Label (Duration)
					//
					if ((duration = getAudioDuration(toFile(pathAudio))) != null) {
						//
						cs.beginText();
						//
						cs.setFont(font, fontSize);
						//
						cs.newLineAtOffset((index - 1) * size + getTextWidth(value, font, fontSize) / 2,
								lastHeight = lastHeight - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
										+ (font.getFontDescriptor().getDescent() / 1000 * fontSize)
						//
						);
						cs.showText(String.format("%1$s s", duration.toMillis() / 1000d));
						//
						cs.endText();
						//
					} // if
						//
				} catch (final Exception e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} finally {
					//
					Files.delete(pathAudio);
					//
				} // try
					//
			} // for
				//
				// Image
				//
			final ObjectMap om = Reflection.newProxy(ObjectMap.class, new IH());
			//
			ObjectMap.setObject(om, PDDocument.class, document);
			//
			ObjectMap.setObject(om, String.class, StringMap.getString(stringMap, "imageUrl"));
			//
			ObjectMap.setObject(om, PDRectangle.class, md);
			//
			ObjectMap.setObject(om, PDPageContentStream.class, cs);
			//
			ObjectMap.setObject(om, VoiceManagerPdfPanel.class,
					ObjectMap.getObject(objectMap, VoiceManagerPdfPanel.class));
			//
			addImageByUrl(om, lastHeight, isOrginialSize);
			//
			IOUtils.close(cs);
			//
			final File file = ObjectMap.getObject(objectMap, File.class);
			//
			document.save(file);
			//
		} // if
			//
	}

	private static void setCustomMetadataValue(@Nullable final PDDocumentInformation instance, final String fieldName,
			@Nullable final String fieldValue) {
		if (instance != null) {
			instance.setCustomMetadataValue(fieldName, fieldValue);
		}
	}

	private static void addImageByUrl(@Nullable final ObjectMap objectMap, final float lastHeight,
			final boolean isOrginialSize) throws IOException {
		//
		URL url = null;
		//
		final String string = ObjectMap.getObject(objectMap, String.class);
		//
		if (StringUtils.isNotBlank(string)) {
			//
			try {
				//
				url = new URL(string);
				//
			} catch (final MalformedURLException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // if
				//
		} // if
			//
		final URLConnection urlConnection = openConnection(url);
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class, urlConnection);
		//
		final VoiceManagerPdfPanel voiceManagerPdfPanel = ObjectMap.getObject(objectMap, VoiceManagerPdfPanel.class);
		//
		final JTextComponent tfImageUrlStateCode = voiceManagerPdfPanel != null
				? voiceManagerPdfPanel.tfImageUrlStateCode
				: null;
		//
		try (final InputStream is = getInputStream(urlConnection)) {
			//
			final byte[] bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
			//
			Util.setText(voiceManagerPdfPanel != null ? voiceManagerPdfPanel.tfImageUrlMimeType : null,
					getMimeType(ci));
			//
			accept(J_TEXT_COMPONENT_HTTP_URL_CONNECTION_FAILABLE_BI_PREDICATE, tfImageUrlStateCode, httpURLConnection);
			//
			final BufferedImage bi = toBufferedImage(bs);
			//
			if (bi != null) {
				//
				final PDRectangle md = ObjectMap.getObject(objectMap, PDRectangle.class);
				//
				final float width = getWidth(md, 0);
				//
				final double ratioWidth = width / floatValue(getWidth(bi), width);
				//
				final float pdfHeight = Math.min(getHeight(md, 0), lastHeight);
				//
				final double ratioHeight = pdfHeight / floatValue(getHeight(bi), pdfHeight);
				//
				final PDPageContentStream cs = ObjectMap.getObject(objectMap, PDPageContentStream.class);
				//
				final PDImageXObject image = PDImageXObject
						.createFromByteArray(ObjectMap.getObject(objectMap, PDDocument.class), bs, null);
				//
				final int imageWidth = Util.intValue(getWidth(bi), 1);
				//
				final int imageHeight = Util.intValue(getHeight(bi), 0);
				//
				final double ratio = Math.min(ratioWidth, ratioHeight);
				//
				if (Boolean.logicalAnd(imageWidth < width, imageHeight < pdfHeight)) {
					//
					if (isOrginialSize) {
						//
						drawImage(cs, image, (width - imageWidth) / 2, lastHeight - getHeight(bi), imageWidth,
								imageHeight);
						//
					} else {
						//
						drawImage(cs, image, (float) ((width - imageWidth * ratio) / 2),
								(float) (lastHeight - (getHeight(bi) * ratio)), (float) (imageWidth * ratio),
								(float) (imageHeight * ratio));
						//
					} // if
						//
				} else {
					//
					drawImage(cs, image, 0, lastHeight - (float) (imageHeight * ratio), width,
							(float) (imageHeight * ratio));
					//
				} // if
					//
			} // if
				//
		} catch (final IOException e) {
			//
			accept(J_TEXT_COMPONENT_HTTP_URL_CONNECTION_FAILABLE_BI_PREDICATE, tfImageUrlStateCode, httpURLConnection);
			//
		} // try
			//
	}

	private static <T, U, E extends Exception> void accept(final FailableBiConsumer<T, U, E> instance, final T t,
			final U u) throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Nullable
	private static InputStream getInputStream(@Nullable final URLConnection instance) throws IOException {
		return instance != null ? instance.getInputStream() : null;
	}

	@Nullable
	private static URLConnection openConnection(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openConnection() : null;
	}

	private static void drawImage(@Nullable final PDPageContentStream instance, final PDImageXObject image,
			final float x, final float y, final float width, final float height) throws IOException {
		if (instance != null) {
			instance.drawImage(image, x, y, width, height);
		}
	}

	private static BufferedImage toBufferedImage(final byte[] bs) throws IOException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			return testAndApply(Objects::nonNull, is, ImageIO::read, null);
			//
		} // try
			//
	}

	private static float getHeight(@Nullable final PDRectangle instance, final float defaultValue) {
		return instance != null ? instance.getHeight() : defaultValue;
	}

	private static float getWidth(@Nullable final PDRectangle instance, final float defaultValue) {
		return instance != null ? instance.getWidth() : defaultValue;
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	@Nullable
	private static Integer getWidth(@Nullable final BufferedImage instance) {
		return instance != null ? Integer.valueOf(instance.getWidth()) : null;
	}

	@Nullable
	private static Integer getHeight(@Nullable final BufferedImage instance) {
		return instance != null ? Integer.valueOf(instance.getHeight()) : null;
	}

	@Nullable
	private static BigDecimal divide(@Nullable final BigDecimal a, @Nullable final BigDecimal b) {
		return a != null && b != null && !b.equals(BigDecimal.valueOf(0)) ? a.divide(b) : a;
	}

	private static float floatValue(@Nullable final Number instance, final float defaultValue) {
		return instance != null ? instance.floatValue() : defaultValue;
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, @Nullable final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, @Nullable final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static void setSize(@Nullable final PDEmbeddedFile instance, final int size) {
		if (instance != null) {
			instance.setSize(size);
		}
	}

	@Nullable
	private static Long length(@Nullable final File instance) {
		return instance != null ? Long.valueOf(instance.length()) : null;
	}

	@Nullable
	private static String group(@Nullable final MatchResult instance, final int group) {
		return instance != null ? instance.group(group) : null;
	}

	private static <T> boolean and(@Nullable final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	private static void writeVoiceToFile(@Nullable final SpeechApi instance, @Nullable final String text,
			final String voiceId, final int rate, final int volume, @Nullable final File file) {
		if (instance != null) {
			instance.writeVoiceToFile(text, voiceId, rate, volume, file);
		}
	}

	public static void main(final String[] args) throws Exception {
		//
		final VoiceManagerPdfPanel instance = new VoiceManagerPdfPanel();
		//
		instance.afterPropertiesSet();
		//
		instance.actionPerformed(new ActionEvent(instance.btnExecute, 0, null));
		//
	}

	private static String getMimeType(@Nullable final Path path) throws IOException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, path, Files::newInputStream, null)) {
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, is, new ContentInfoUtil()::findMatch, null);
			//
			return getMimeType(ci);
			//
		} // try
			//
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(@Nullable final PDPage instance) throws IOException {
		return instance != null ? instance.getAnnotations() : null;
	}

	@Nullable
	private static PDRectangle getMediaBox(@Nullable final PDPage instance) {
		return instance != null ? instance.getMediaBox() : null;
	}

	@Nullable
	private static Integer getLargestY(@Nullable final BufferedImage bi) {
		//
		Color color = null;
		//
		IntList ily = null;
		//
		for (int y = 0; bi != null && y < bi.getHeight(); y++) {
			//
			for (int x = 0; x < bi.getWidth(); x++) {
				//
				if (color == null) {
					//
					color = new Color(bi.getRGB(x, y));
					//
				} else {
					//
					if (!Objects.equals(color, new Color(bi.getRGB(x, y)))
							&& !Util.contains(ily = ObjectUtils.getIfNull(ily, IntList::create), y)) {
						//
						IntCollectionUtil.addInt(ily, y);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		sortInts(ily);
		//
		return ily != null && !ily.isEmpty() ? ily.getInt(ily.size() - 1) : null;
		//
	}

	private static void setSubject(@Nullable final PDDocumentInformation instance, @Nullable final String keywords) {
		if (instance != null) {
			instance.setSubject(keywords);
		}
	}

	private static Duration getAudioDuration(@Nullable final File file) throws Exception {
		//
		final AudioFileFormat fileFormat = testAndApply(Objects::nonNull, file, AudioSystem::getAudioFileFormat, null);
		//
		return testAndApply((a, b) -> a != null && b != null, fileFormat, getFormat(fileFormat),
				(a, b) -> Duration.parse(String.format("PT%1$sS", a.getFrameLength() / b.getFrameRate())), null);
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, @Nullable final T t,
			@Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static AudioFormat getFormat(@Nullable final AudioFileFormat instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static int groupCount(@Nullable final Matcher instance) {
		return instance != null ? instance.groupCount() : 0;
	}

	private static boolean matches(@Nullable final Matcher instance) {
		return instance != null && instance.matches();
	}

	@Nullable
	private static Matcher matcher(@Nullable final Pattern instance, final CharSequence input) {
		return instance != null ? instance.matcher(input) : null;
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getName(@Nullable final File instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static byte[] pdf(@Nullable final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(toURI(toFile(pathHtml)))), page::navigate);
				//
				return page.pdf();
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Browser launch(@Nullable final BrowserType instance) {
		return instance != null ? instance.launch() : null;
	}

	@Nullable
	private static BrowserContext newContext(@Nullable final Browser instance) {
		return instance != null ? instance.newContext() : null;
	}

	@Nullable
	private static Page newPage(@Nullable final BrowserContext instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static void sortInts(@Nullable final IntList instance) {
		if (instance != null) {
			instance.sortInts();
		}
	}

	@Nullable
	private static String getAbsolutePath(@Nullable final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	@Nullable
	private static File toFile(@Nullable final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		float width = 0;
		for (int i = 0; i < StringUtils.length(text); i++) {
			// Get the width of each character and add it to the total width
			width += font.getWidth(text.charAt(i)) / 1000.0f;
		}
		return width * fontSize;
	}

}