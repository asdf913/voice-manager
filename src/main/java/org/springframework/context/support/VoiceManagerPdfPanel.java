package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstructionUtil;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypeUtil;
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
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
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
import org.jsoup.nodes.ElementUtil;
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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.VoiceManager.ByteConverter;
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
import j2html.rendering.TagBuilder;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerPdfPanel extends JPanel implements Titled, InitializingBean, ActionListener,
		ApplicationContextAware, EnvironmentAware, DocumentListener {

	private static final long serialVersionUID = 284477348908531649L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerPdfPanel.class);

	private static final String GROWX = "growx";

	private static final String WRAP = "wrap";

	private static final String FORMAT = "format";

	private static final String PAGE1 = "page1";

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

	@Note("Is Original Size")
	private AbstractButton cbIsOriginalSize = null;

	@Note("Image File")
	private AbstractButton btnImageFile = null;

	@Note("Image From Clipboard")
	private AbstractButton btnImageFromClipboard = null;

	@Note("Image Clear")
	private AbstractButton btnImageClear = null;

	@Note("Image View")
	private AbstractButton btnImageView = null;

	@Note("Copy Output File Path")
	private AbstractButton btnCopyOutputFilePath = null;

	private AbstractButton btnBrowseOutputFolder = null;

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

	@Note("Image URL Mime Type")
	private JTextComponent tfImageUrlMimeType = null;

	@Note("Image File")
	private JTextComponent tfImageFile = null;

	private JTextComponent tfOutputFile = null;

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

	@Nullable
	private Map<Integer, String> fontSizeAndUnitMap = null;

	private transient RenderedImage renderedImage = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private transient ComboBoxModel<?> cbmAudioFormat = null;

	@Nullable
	private List<String> imageWriterSpiFormats = null;

	private List<String> imageFormatOrders = null;

	private ObjectMapper objectMapper = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private String audioFormat = null;

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
		final Object object = ObjectMapperUtil.readValue(getObjectMapper(), string, Object.class);
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

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
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

	public void setImageWriterSpiFormats(@Nullable final Object object) {
		//
		if (object instanceof Map) {
			//
			throw new IllegalArgumentException();
			//
		} else if (object == null) {
			//
			imageWriterSpiFormats = null;
			//
		} // if
			//
		List<String> collection = null;
		//
		if (object instanceof Iterable iterable) {
			//
			for (final Object o : iterable) {
				//
				Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new), Util.toString(o));
				//
			} // for
				//
			imageWriterSpiFormats = collection;
			//
		} else if (object instanceof Object[] os) {
			//
			for (int i = 0; i < length(os); i++) {
				//
				Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new),
						Util.toString(ArrayUtils.get(os, i)));
				//
			} // for
				//
		} else if (object instanceof Iterator iterator) {
			//
			while (Util.hasNext(iterator)) {
				//
				Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new),
						Util.toString(Util.next(iterator)));
				//
			} // while
				//
		} else if (object instanceof Enumeration enumeration) {
			//
			setImageWriterSpiFormats(asIterator(enumeration));
			//
		} else {
			//
			setImageWriterSpiFormats(Collections.singleton(object));
			//
		} // if
			//
	}

	public void setImageFormatOrders(@Nullable final Object object) {
		//
		IValue0<List<String>> value = null;
		//
		final Class<?> clz = Util.getClass(object);
		//
		if (object == null) {
			//
			value = Unit.with(null);
			//
		} else if (object instanceof List) {
			//
			value = Unit.with(Util.toList(Util.map(Util.stream(((List<?>) object)), Util::toString)));
			//
		} else if (object instanceof Iterable) {
			//
			value = Unit.with(Util.toList(
					Util.map(StreamSupport.stream(((Iterable<?>) object).spliterator(), false), Util::toString)));
			//
		} else if (clz != null && clz.isArray()) {
			//
			if (Objects.equals(clz.getComponentType(), Character.TYPE)) {
				//
				setImageFormatOrders(new String((char[]) object));
				//
				return;
				//
			} // if
				//
			value = Unit.with(Util.toList(Util.map(
					IntStream.range(0, Array.getLength(object)).mapToObj(i -> Array.get(object, i)), Util::toString)));
			//
		} else if (object instanceof String string) {
			//
			try {
				//
				final Object obj = ObjectMapperUtil.readValue(getObjectMapper(), string, Object.class);
				//
				if (obj != null) {
					//
					setImageFormatOrders(obj);
					//
					return;
					//
				} // if
					//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			value = Unit.with(Collections.singletonList(string));
			//
		} else if (Boolean.logicalOr(object instanceof Number, object instanceof Boolean)) {
			//
			value = Unit.with(Collections.singletonList(Util.toString(object)));
			//
		} // if
			//
		if (value != null) {
			//
			imageFormatOrders = IValue0Util.getValue0(value);
			//
		} else {
			//
			throw new UnsupportedOperationException(Util.toString(clz));
			//
		} // if
			//
	}

	public void setFontSizeAndUnitMap(final String string) {
		//
		try {
			//
			final Object object = ObjectMapperUtil.readValue(getObjectMapper(), string, Object.class);
			//
			if (object == null) {
				//
				fontSizeAndUnitMap = null;
				//
			} else if (object instanceof Map map) {
				//
				final Iterable<Object> entrySet = Util.entrySet(map);
				//
				if (Util.iterator(entrySet) != null) {
					//
					Entry<?, ?> entry = null;
					//
					for (final Object temp : entrySet) {
						//
						if ((entry = Util.cast(Entry.class, temp)) == null) {
							//
							continue;
							//
						} // if
							//
						Util.put(fontSizeAndUnitMap = ObjectUtils.getIfNull(fontSizeAndUnitMap, LinkedHashMap::new),
								testAndApply(NumberUtils::isCreatable, Util.toString(Util.getKey(entry)),
										NumberUtils::createInteger, null),
								Util.toString(Util.getValue(entry)));
						//
					} // for
						//
				} // if
					//
			} else {
				//
				throw new IllegalArgumentException();
				//
			} // if
				//
		} catch (final JsonProcessingException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
	}

	private Map<Integer, String> getFontSizeAndUnitMap() {
		//
		if (fontSizeAndUnitMap == null) {
			//
			putAll(fontSizeAndUnitMap = new LinkedHashMap<>(Map.of(Integer.valueOf(3), "266px", Integer.valueOf(4),
					"200px", Integer.valueOf(5), "160px", Integer.valueOf(6), "133px", Integer.valueOf(7), "114px",
					Integer.valueOf(8), "100px", Integer.valueOf(9), "88px", Integer.valueOf(10), "80px",
					Integer.valueOf(11), "72px", Integer.valueOf(12), "66px")),
					Map.of(Integer.valueOf(13), "61px", Integer.valueOf(14), "56px", Integer.valueOf(15), "53px",
							Integer.valueOf(16), "50px", Integer.valueOf(17), "45px", Integer.valueOf(18), "43px",
							Integer.valueOf(19), "42px", Integer.valueOf(25), "31px", Integer.valueOf(30), "26px",
							Integer.valueOf(36), "22px"));

		} // if
			//
		return fontSizeAndUnitMap;
		//
	}

	public void setAudioFormat(final String audioFormat) {
		this.audioFormat = audioFormat;
	}

	@Nullable
	private static <E> Iterator<E> asIterator(@Nullable final Enumeration<E> instance) {
		return instance != null ? instance.asIterator() : null;
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
		final int span = 5;
		//
		add(new JComboBox<>(
				cbmFontSize1 = new DefaultComboBoxModel<>(ArrayUtils.insert(0, ECSSUnit.values(), (ECSSUnit) null))),
				String.format("%1$s,span %2$s", WRAP, span - 1));
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
		add(jsp, String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, span));
		//
		// Font Size
		//
		add(new JLabel("Font Size"));
		//
		add(tfFontSize2 = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.fontSize2")),
				String.format("%1$s,%2$s,wmin %3$s", GROWX, WRAP, 100));
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
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, span));
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
			add(jcbVoiceId, String.format("%1$s,span %2$s", WRAP, span));
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
		add(tfImageUrlMimeType = new JTextField(), String.format("%1$s,wmin %2$s", WRAP, 65));
		//
		// Image File
		//
		add(new JLabel("Image File"));
		//
		add(tfImageFile = new JTextField(), String.format("%1$s,span %2$s", GROWX, span - 1));
		//
		setEditable(false, tfImageUrlStateCode, tfImageUrlMimeType, tfImageFile);
		//
		add(btnImageFile = new JButton("Select"), WRAP);
		//
		// Image From Clip board
		//
		add(new JLabel("Image From Clipboard"));
		//
		JPanel panel = new JPanel();
		//
		final GridLayout gridLayout = new GridLayout();
		//
		gridLayout.setHgap(7);
		//
		panel.setLayout(gridLayout);
		//
		panel.add(btnImageFromClipboard = new JButton("Copy"));
		//
		panel.add(btnImageClear = new JButton("Clear"));
		//
		panel.add(btnImageView = new JButton("View"));
		//
		final MutableComboBoxModel<String> mcbm = new DefaultComboBoxModel<>();
		//
		panel.add(new JComboBox<>(mcbm));
		//
		sort(imageWriterSpiFormats, createImageFormatComparator(imageFormatOrders));
		//
		forEach(imageWriterSpiFormats, mcbm::addElement);
		//
		cbmImageFormat = mcbm;
		//
		add(panel, String.format("%1$s,span %2$s", WRAP, 2));
		//
		// Original Size
		//
		add(new JLabel("Original Size"));
		//
		add(cbIsOriginalSize = new JCheckBox(), WRAP);
		//
		// Audio Format
		//
		add(new JLabel("Audio Format"));
		//
		final JComboBox<Object> jcbAudioFormat = new JComboBox(cbmAudioFormat = new DefaultComboBoxModel<Object>());
		//
		final Collection<?> formats = getByteConverterAttributeValues(
				configurableListableBeanFactory = ObjectUtils
						.defaultIfNull(Util.cast(ConfigurableListableBeanFactory.class, applicationContext),
								Util.cast(ConfigurableListableBeanFactory.class,
										ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext))),
				FORMAT);
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatWrite = Util.cast(MutableComboBoxModel.class, cbmAudioFormat);
		//
		addElement(mcbmAudioFormatWrite, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
		//
		mcbmAudioFormatWrite.setSelectedItem(audioFormat);
		//
		add(jcbAudioFormat, WRAP);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), WRAP);
		//
		add(new JLabel("Output"));
		//
		add(tfOutputFile = new JTextField(), String.format("%1$s,span %2$s", GROWX, span - 2));
		//
		setEditable(tfOutputFile, false);
		//
		(panel = new JPanel()).add(btnCopyOutputFilePath = new JButton("Copy"));
		//
		panel.add(btnBrowseOutputFolder = new JButton("Browse"));
		//
		add(panel, String.format("span %1$s", 2));
		//
		final FailableStream<Field> fs = testAndApply(Objects::nonNull,
				Util.filter(
						testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManagerPdfPanel.class),
								Arrays::stream, null),
						f -> Util.isAssignableFrom(AbstractButton.class, Util.getType(f))),
				FailableStream::new, null);
		//
		forEach(FailableStreamUtil.map(fs, f -> Util.cast(AbstractButton.class, f != null ? f.get(this) : null)),
				x -> addActionListener(x, this));
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
		actionPerformed(new ActionEvent(btnImageClear, 0, null));
		//
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, @Nullable final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	@Nullable
	private static Collection<Object> getByteConverterAttributeValues(
			final ConfigurableListableBeanFactory configurableListableBeanFactory, final String attribute) {
		//
		List<Object> list = null;
		//
		final Map<String, ByteConverter> byteConverters = ListableBeanFactoryUtil
				.getBeansOfType(configurableListableBeanFactory, ByteConverter.class);
		//
		final Set<Entry<String, ByteConverter>> entrySet = Util.entrySet(byteConverters);
		//
		if (entrySet != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : entrySet) {
				//
				if (en == null || (bd = ConfigurableListableBeanFactoryUtil
						.getBeanDefinition(configurableListableBeanFactory, Util.getKey(en))) == null
						|| !bd.hasAttribute(attribute)) {
					continue;
				} // if
					//
				Util.add(list = getIfNull(list, ArrayList::new), bd.getAttribute(attribute));
				//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	/*
	 * Copy from the below URL
	 * 
	 * https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/
	 * commons/lang3/ObjectUtils.java#L597
	 */
	private static <T, E extends Throwable> T getIfNull(@Nullable final T object,
			final FailableSupplier<T, E> defaultSupplier) throws E {
		return object != null ? object : FailableSupplierUtil.get(defaultSupplier);
	}

	private static <E> void sort(@Nullable final List<E> instance, @Nullable final Comparator<? super E> comparator) {
		//
		if (instance != null
				&& (Proxy.isProxyClass(Util.getClass(instance)) || (instance.size() > 1 && comparator != null))) {
			//
			instance.sort(comparator);
			//
		} // if
			//
	}

	private static Comparator<String> createImageFormatComparator(final List<?> imageFormatOrders) {
		//
		return (a, b) -> {
			//
			final int ia = imageFormatOrders != null ? imageFormatOrders.indexOf(a) : -1;
			//
			final int ib = imageFormatOrders != null ? imageFormatOrders.indexOf(b) : -1;
			//
			if (ia >= 0 && ib >= 0) {
				//
				return Integer.compare(ia, ib);
				//
			} else if (ia >= 0) {
				//
				return -1;
				//
			} else if (ib >= 0) {
				//
				return 1;
				//
			} // if
				//
			return ObjectUtils.compare(a, b);
			//
		};
		//
	}

	private static <T> void forEach(@Nullable final Iterable<T> instance, @Nullable final Consumer<? super T> action) {
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		}
	}

	private static <T> void forEach(@Nullable final FailableStream<T> instance, final FailableConsumer<T, ?> action) {
		if (instance != null) {
			instance.forEach(action);
		}
	}

	private static void addActionListener(@Nullable final AbstractButton instance, final ActionListener l) {
		if (instance != null) {
			instance.addActionListener(l);
		}
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
				x -> StringUtils.equalsIgnoreCase(Util.name(x), Util.toString(sb))));
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
	private static <E> ListCellRenderer<? super E> getRenderer(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			setText("", tfImageUrlStateCode, tfImageUrlMimeType, tfOutputFile);
			//
			final Path pathHtml = Path.of("test.html");
			//
			final Map<String, String> style = new LinkedHashMap<>(
					Map.of("text-align", "center", "display", "block", "margin-left", "auto", "margin-right", "auto"));
			//
			testAndAccept((a, b) -> Boolean.logicalAnd(a != null, b != null),
					testAndApply(NumberUtils::isCreatable, Util.getText(tfFontSize1), NumberUtils::createBigDecimal,
							null),
					Util.cast(ECSSUnit.class, getSelectedItem(cbmFontSize1)),
					(a, b) -> Util.put(style, "font-size", new CSSSimpleValueWithUnit(a, b).getFormatted()));
			//
			final File file = Util.toFile(Path
					.of(StringUtils.joinWith(".", StringUtils.defaultIfBlank(Util.getText(tfText), "test"), "pdf")));
			//
			PDDocument document = null;
			//
			try {
				//
				final HtmlBuilder<StringBuilder> htmlBuilder = FlatHtml.inMemory();
				//
				FileUtils
						.writeStringToFile(Util.toFile(pathHtml),
								Util.toString(
										output(appendEndTag(
												appendUnescapedText(completeTag(appendAttribute(
														appendStartTag(htmlBuilder, "div"), "style",
														Util.collect(
																Util.map(Util.stream(Util.entrySet(style)),
																		x -> StringUtils.joinWith(":", Util.getKey(x),
																				Util.getValue(x))),
																Collectors.joining(";")))),
														Util.getText(taHtml)),
												"div"))),
								StandardCharsets.UTF_8, false);
				//
				document = Loader.loadPDF(pdf(pathHtml));
				//
				final IH ih = new IH();
				//
				final StringMap stringMap = Reflection.newProxy(StringMap.class, ih);
				//
				StringMap.setString(stringMap, "text", Util.getText(tfText));
				//
				StringMap.setString(stringMap, "voiceId", Util.toString(getSelectedItem(cbmVoiceId)));
				//
				StringMap.setString(stringMap, "imageUrl", Util.getText(tfImageUrl));
				//
				StringMap.setString(stringMap, "html", Util.getText(taHtml));
				//
				final FloatMap floatMap = Reflection.newProxy(FloatMap.class, ih);
				//
				FloatMap.setFloat(floatMap, "fontSize", floatValue(testAndApply(NumberUtils::isCreatable,
						Util.getText(tfFontSize2), NumberUtils::createNumber, null), 14));
				//
				FloatMap.setFloat(floatMap, "defaultSize", floatValue(pdAnnotationRectangleSize, 61));
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
				ObjectMap.setObject(objectMap, RenderedImage.class, renderedImage);
				//
				ObjectMap.setObject(objectMap, ByteConverter.class,
						getByteConverter(configurableListableBeanFactory, FORMAT, getSelectedItem(cbmAudioFormat)));
				//
				addTextAndVoice(objectMap,
						ObjectUtils.getIfNull(speechSpeedMap, VoiceManagerPdfPanel::getDefaultSpeechSpeedMap),
						Util.intValue(speechVolume, 100), Util.isSelected(cbIsOriginalSize));
				//
				Util.setText(tfOutputFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException | NoSuchFieldException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnImageFile)) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final File file = getAbsoluteFile(jfc.getSelectedFile());
				//
				Collection<Object> allowedFileType = null;
				//
				Object fileType = null;
				//
				try {
					//
					final Entry<Method, Collection<Object>> entry = getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes();
					//
					allowedFileType = Util.getValue(entry);
					//
					fileType = Narcissus.invokeStaticMethod(Util.getKey(entry), Files.readAllBytes(Util.toPath(file)));
					//
				} catch (final IOException | NoSuchMethodException e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} // try
					//
				testAndRun(Util.contains(allowedFileType, fileType),
						() -> Util.setText(tfImageFile, Util.getAbsolutePath(file)), () -> {
							//
							JOptionPane.showMessageDialog(null, "Please select an image file");
							//
							Util.setText(tfImageFile, null);
							//
						});
				//
			} // if
				//
		} else if (Objects.equals(source, btnImageClear)) {
			//
			setEnabled((renderedImage = null) != null, btnImageClear, btnImageView);
			//
		} else if (Objects.equals(source, btnImageView)) {
			//
			JOptionPane.showMessageDialog(null,
					testAndApply(Objects::nonNull, Util.cast(BufferedImage.class, renderedImage), ImageIcon::new, null),
					"Image", JOptionPane.PLAIN_MESSAGE, null);
			//
		} else if (Objects.equals(source, btnCopyOutputFilePath)) {
			//
			setContents(testAndApply(x -> !GraphicsEnvironment.isHeadless(), Toolkit.getDefaultToolkit(),
					x -> getSystemClipboard(x), null), new StringSelection(Util.getText(tfOutputFile)), null);
			//
		} else if (Objects.equals(source, btnBrowseOutputFolder)) {
			//
			try {
				//
				testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)),
						Util.toFile(testAndApply(Objects::nonNull, Util.getText(tfOutputFile), Path::of, null)),
						x -> browse(Desktop.getDesktop(), toURI(getParentFile(x))));
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} // if
			//
		actionPerformedForBtnImageFromClipboard(source);
		//
	}

	private static ByteConverter getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String attribute, @Nullable final Object value) {
		//
		IValue0<ByteConverter> byteConverter = null;
		//
		final Map<String, ByteConverter> byteConverters = ListableBeanFactoryUtil
				.getBeansOfType(configurableListableBeanFactory, ByteConverter.class);
		//
		final Set<Entry<String, ByteConverter>> entrySet = Util.entrySet(byteConverters);
		//
		if (entrySet != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : entrySet) {
				//
				if (en == null
						|| (bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(configurableListableBeanFactory,
								Util.getKey(en))) == null
						|| !bd.hasAttribute(attribute)
						|| !Objects.equals(value, testAndApply(bd::hasAttribute, attribute, bd::getAttribute, null))) {
					//
					continue;
					//
				} // if
					//
				if (byteConverter == null) {
					//
					byteConverter = Unit.with(Util.getValue(en));
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return IValue0Util.getValue0(byteConverter);
		//
	}

	@Nullable
	private static File getParentFile(@Nullable final File instance) {
		return instance != null ? instance.getParentFile() : null;
	}

	private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
		if (instance != null) {
			instance.browse(uri);
		}
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private boolean actionPerformedForBtnImageFromClipboard(final Object source) {
		//
		if (Objects.equals(source, btnImageFromClipboard)) {
			//
			final Transferable transferable = getContents(testAndApply(x -> !GraphicsEnvironment.isHeadless(),
					Toolkit.getDefaultToolkit(), x -> getSystemClipboard(x), null), null);
			//
			debug(System.out, transferable);
			//
			final boolean isImageFlavorSupported = isDataFlavorSupported(transferable, DataFlavor.imageFlavor);
			//
			setEnabled(isImageFlavorSupported, btnImageClear, btnImageView);
			//
			try {
				//
				if (isImageFlavorSupported) {
					//
					renderedImage = Util.cast(RenderedImage.class,
							transferable.getTransferData(DataFlavor.imageFlavor));
					//
				} else if (isDataFlavorSupported(transferable, DataFlavor.javaFileListFlavor)) {
					//
					final File file = Util.cast(File.class,
							testAndApply(x -> IterableUtils.size(x) == 1,
									Util.cast(Iterable.class,
											transferable.getTransferData(DataFlavor.javaFileListFlavor)),
									x -> IterableUtils.get(x, 0), null));
					//
					final Entry<Method, Collection<Object>> entry = getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes();
					//
					if (Util.contains(Util.getValue(entry), testAndApply(f -> Util.exists(f) && Util.isFile(f), file,
							f -> Narcissus.invokeStaticMethod(Util.getKey(entry), Files.readAllBytes(Util.toPath(f))),
							null))) {
						//
						Util.setText(tfImageFile, Util.getAbsolutePath(file));
						//
					} else {
						//
						Util.setText(tfImageFile, null);
						//
					} // if
						//
				} // if
					//
			} catch (final UnsupportedFlavorException | IOException | NoSuchMethodException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
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

	private static void debug(final PrintStream ps, final Transferable transferable) {
		//
		final FailableStream<Field> fs = testAndApply(Objects::nonNull,
				Util.filter(
						testAndApply(Objects::nonNull, Util.getDeclaredFields(DataFlavor.class), Arrays::stream, null),
						f -> Boolean.logicalAnd(Objects.equals(DataFlavor.class, Util.getType(f)), Util.isStatic(f))),
				FailableStream::new, null);
		//
		final Iterable<Entry<String, DataFlavor>> entrySet = Util.entrySet(Util.collect(FailableStreamUtil.stream(fs),
				Collectors.toMap(Util::getName, f -> testAndApply(Util::isStatic, f,
						x -> Util.cast(DataFlavor.class, Narcissus.getStaticField(x)), null))));
		//
		Entry<String, DataFlavor> entry = null;
		//
		DataFlavor df = null;
		//
		final int maxLength1 = orElse(max(mapToInt(Util.map(
				testAndApply(Objects::nonNull, spliterator(entrySet), x -> StreamSupport.stream(x, false), null),
				Util::getKey), StringUtils::length)), 0);
		//
		final int maxLength2 = orElse(max(mapToInt(Util.map(
				testAndApply(Objects::nonNull, spliterator(entrySet), x -> StreamSupport.stream(x, false), null),
				x -> Util.toString(Util.getValue(x))), StringUtils::length)), 0);
		//
		for (int i = 0; i < IterableUtils.size(entrySet); i++) {
			//
			println(ps,
					StringUtils.joinWith("\t",
							StringUtils.rightPad(Util.getKey(entry = IterableUtils.get(entrySet, i)), maxLength1),
							StringUtils.rightPad(Util.toString(df = Util.getValue(entry)), maxLength2),
							isDataFlavorSupported(transferable, df)));// TODO
			//
		} // for
			//
	}

	private static void println(@Nullable final PrintStream instance, final String string) {
		if (instance != null) {
			instance.println(string);
		}
	}

	private static void setEnabled(final boolean enabled, final Component a, final Component b, final Component... cs) {
		//
		setEnabled(a, enabled);
		//
		setEnabled(b, enabled);
		//
		for (int i = 0; i < length(cs); i++) {
			//
			setEnabled(ArrayUtils.get(cs, i), enabled);
			//
		} // for
			//
	}

	private static void setEnabled(@Nullable final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	private static boolean isDataFlavorSupported(@Nullable final Transferable instance, final DataFlavor flavor) {
		return instance != null && instance.isDataFlavorSupported(flavor);
	}

	@Nullable
	private static Transferable getContents(@Nullable final Clipboard instance, @Nullable final Object requestor) {
		return instance != null ? instance.getContents(requestor) : null;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	@Nullable
	private static <T> IntStream mapToInt(@Nullable final Stream<T> instance,
			@Nullable final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	@Nullable
	private static OptionalInt max(@Nullable final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	private static int orElse(@Nullable final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	@Nullable
	private static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	@Nullable
	private static Entry<Method, Collection<Object>> getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes()
			throws IOException, NoSuchMethodException {
		//
		Class<?> clz = PDImageXObject.class;
		//
		Collection<Object> allowedFileType = null;
		//
		MutablePair<Method, Collection<Object>> entry = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			// org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromByteArray(org.apache.pdfbox.pdmodel.PDDocument,byte[],java.lang.String)
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					Util.getDeclaredMethod(clz, "createFromByteArray", PDDocument.class, byte[].class, String.class));
			//
			final Instruction[] ins = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			final ConstantPool cp = FieldOrMethodUtil.getConstantPool(m);
			//
			final ConstantPoolGen cpg = new ConstantPoolGen(cp);
			//
			Instruction in = null;
			//
			INVOKESTATIC invokestatic = null;
			//
			Object object = null;
			//
			Type[] argumentTypes = null;
			//
			List<Field> fs = null;
			//
			int size = 0;
			//
			for (int i = 0; i < length(ins); i++) {
				//
				if ((in = ArrayUtils.get(ins, i)) instanceof INVOKESTATIC temp && invokestatic == null) {
					//
					if (length(
							(argumentTypes = InvokeInstructionUtil.getArgumentTypes(invokestatic = temp, cpg))) == 1) {
						//
						MutablePairUtil.setLeft(entry = ObjectUtils.getIfNull(entry, MutablePair::new),
								Util.getDeclaredMethod(
										clz = Util.forName(InvokeInstructionUtil.getClassName(invokestatic, cpg)),
										InvokeInstructionUtil.getMethodName(invokestatic, cpg),
										Util.forName(TypeUtil.getClassName(ArrayUtils.get(argumentTypes, 0)))));
						//
					} // if
						//
				} else if (i > 0 && ArrayUtils.get(ins, i - 1) instanceof GETSTATIC getstatic
						&& Boolean.logicalOr(in instanceof IF_ACMPNE, in instanceof IF_ACMPEQ)) {
					//
					if ((size = IterableUtils.size(fs = Util.toList(Util.filter(
							Arrays.stream(Util.getDeclaredFields(
									Util.forName(TypeUtil.getClassName(getstatic.getFieldType(cpg))))),
							f -> Objects.equals(Util.getName(f),
									FieldInstructionUtil.getFieldName(getstatic, cpg)))))) > 1) {
						//
						throw new IllegalStateException();
						//
					} else if (size == 1
							&& !Util.contains(allowedFileType = ObjectUtils.getIfNull(allowedFileType, ArrayList::new),
									object = Narcissus.getStaticField(IterableUtils.get(fs, 0)))) {
						//
						Util.add(allowedFileType, object);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // try
			//
		setValue(entry = ObjectUtils.getIfNull(entry, MutablePair::new), allowedFileType);
		//
		return entry;
		//
	}

	private static <V> void setValue(@Nullable final Entry<?, V> instance, @Nullable final V value) {
		if (instance != null) {
			instance.setValue(value);
		}
	}

	private static void testAndRun(final boolean b, final Runnable ra, final Runnable rb) {
		if (b) {
			Util.run(ra);
		} else {
			Util.run(rb);
		}
	}

	@Nullable
	private static <T extends Appendable> T output(@Nullable final HtmlBuilder<T> instance) {
		return instance != null ? instance.output() : null;
	}

	@Nullable
	private static <T extends Appendable> HtmlBuilder<T> appendEndTag(@Nullable final HtmlBuilder<T> instance,
			final String name) throws IOException {
		return instance != null ? instance.appendEndTag(name) : null;
	}

	@Nullable
	private static <T extends Appendable> HtmlBuilder<T> appendUnescapedText(@Nullable final HtmlBuilder<T> instance,
			final String txt) throws IOException {
		return instance != null ? instance.appendUnescapedText(txt) : null;
	}

	@Nullable
	private static HtmlBuilder<? extends Appendable> completeTag(@Nullable final TagBuilder instance)
			throws IOException {
		return instance != null ? instance.completeTag() : null;
	}

	@Nullable
	private static TagBuilder appendAttribute(@Nullable final TagBuilder instance, final String name,
			final String value) throws IOException {
		return instance != null ? instance.appendAttribute(name, value) : null;
	}

	@Nullable
	private static TagBuilder appendStartTag(@Nullable final HtmlBuilder<?> instance, final String name)
			throws IOException {
		return instance != null ? instance.appendStartTag(name) : null;
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static File getAbsoluteFile(@Nullable final File instance) {
		return instance != null ? instance.getAbsoluteFile() : null;
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
		setFontSizeAndUnit(StringUtils
				.length(replaceAll(ElementUtil.text(body(testAndApply(Objects::nonNull, html, Jsoup::parse, null))),
						"\\([^\\(\\)]+\\)", "")));// TODO
		//
	}

	private void setFontSizeAndUnit(final int length) {
		//
		final Integer key = Integer.valueOf(length);
		//
		final Map<Integer, String> map = getFontSizeAndUnitMap();
		//
		if (Util.containsKey(map, key)) {
			//
			final StringBuilder sb = testAndApply(Objects::nonNull, MapUtils.getObject(map, key), StringBuilder::new,
					null);
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
					if (StringUtils.endsWithIgnoreCase(Util.toString(sb), name = Util.name(u = us[j]))) {
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

		private static void setFloat(@Nullable final FloatMap instance, final String key, final float i) {
			if (instance != null) {
				instance.setFloat(key, i);
			}
		}

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, @Nullable final String value);

		@Nullable
		static String getString(@Nullable final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}

		private static void setString(@Nullable final StringMap instance, final String key, final String value) {
			if (instance != null) {
				instance.setString(key, value);
			}
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
			final int volume, final boolean isOrginialSize) throws IOException, NoSuchFieldException {
		//
		final PDDocument document = ObjectMap.getObject(objectMap, PDDocument.class);
		//
		final VoiceManagerPdfPanel voiceManagerPdfPanel = ObjectMap.getObject(objectMap, VoiceManagerPdfPanel.class);
		//
		if (getNumberOfPages(document) > 0) {
			//
			final PDPage pd = document.getPage(0);
			//
			final PDRectangle md = getMediaBox(pd);
			//
			final PDFRenderer pdfRenderer = new PDFRenderer(document);
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
			final Path page1Path = write(bi, getImageWriterSpiFormats(voiceManagerPdfPanel), PAGE1);
			//
			LoggerUtil.info(LOG, Util.getAbsolutePath(Util.toFile(page1Path)));
			//
			final Integer largestY = getLargestY(bi);
			//
			testAndAccept(x -> Util.exists(Util.toFile(x)), page1Path, Files::delete);
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd, AppendMode.PREPEND, true);
			//
			final Path pathAudio = Path.of("test.wav");
			//
			LoggerUtil.info(LOG, Util.getAbsolutePath(Util.toFile(pathAudio)));
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
					SpeechApi.getVoiceAttribute(speechApi, voiceId, "Name"));
			//
			PDEmbeddedFile pdfEmbeddedFile = null;
			//
			PDComplexFileSpecification fileSpec = null;
			//
			PDAnnotationFileAttachment attachment = null;
			//
			float lastHeight = 0;
			//
			ContentInfoUtil ciu = null;
			//
			for (final Entry<Integer, String> entry : Util.entrySet(map)) {
				//
				if ((key = Util.getKey(entry)) == null) {
					//
					continue;
					//
				} // if
					//
				writeVoiceToFile(speechApi, text, voiceId, Util.intValue(key, 0), volume, Util.toFile(pathAudio));
				//
				duration = null;
				//
				try {
					//
					duration = getAudioDuration(Util.toFile(pathAudio));
					//
				} catch (final Exception e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} // try
					//
				convertAndwriteByteArrayToFile(ObjectMap.getObject(objectMap, ByteConverter.class), pathAudio);
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
					(pdfEmbeddedFile = new PDEmbeddedFile(document, is))
							.setSubtype(getMimeType(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), pathAudio));
					//
					testAndAccept((a, b) -> b != null, pdfEmbeddedFile, Util.toFile(pathAudio),
							(a, b) -> setSize(a, Util.intValue(length(b), 0)));
					//
					(fileSpec = new PDComplexFileSpecification()).setFile(Util.getName(Util.toFile(pathAudio)));
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
					if (duration != null) {
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
			ObjectMap.setObject(om, PDRectangle.class, md);
			//
			ObjectMap.setObject(om, PDPageContentStream.class, cs);
			//
			ObjectMap.setObject(om, VoiceManagerPdfPanel.class,
					ObjectMap.getObject(objectMap, VoiceManagerPdfPanel.class));
			//
			ObjectMap.setObject(om, File.class, testAndApply(Objects::nonNull,
					Util.getText(getTfImageFile(voiceManagerPdfPanel)), File::new, null));
			//
			ObjectMap.setObject(om, StringMap.class, stringMap);
			//
			StringMap.setString(stringMap, "imageFormat",
					Util.toString(getSelectedItem(getCbmImageFormat(voiceManagerPdfPanel))));
			//
			addImage(ObjectMap.getObject(objectMap, RenderedImage.class), om, lastHeight, isOrginialSize);
			//
			IOUtils.close(cs);
			//
			document.save(ObjectMap.getObject(objectMap, File.class));
			//
		} // if
			//
	}

	@Nullable
	private static Path write(@Nullable final BufferedImage bi, @Nullable final Iterable<String> imageWriterSpiFormats,
			final String fileNamePrefix) throws IOException {
		//
		if (bi == null) {
			//
			return null;
			//
		} // if
			//
		Path path = null;
		//
		if (IterableUtils.contains(imageWriterSpiFormats, "png") || IterableUtils.isEmpty(imageWriterSpiFormats)) {
			//
			ImageIO.write(bi, "png", Util.toFile(
					path = Path.of(String.join(".", StringUtils.defaultIfBlank(fileNamePrefix, PAGE1), "png"))));
			//
		} else if (!IterableUtils.isEmpty(imageWriterSpiFormats)) {
			//
			final String format = IterableUtils.get(imageWriterSpiFormats, 0);
			//
			ImageIO.write(bi, format, Util.toFile(
					path = Path.of(String.join(".", StringUtils.defaultIfBlank(fileNamePrefix, PAGE1), format))));
			//
		} // if
			//
		return path;
		//
	}

	private static void convertAndwriteByteArrayToFile(@Nullable final ByteConverter byteConverter, final Path path) {
		//
		final File file = Util.toFile(path);
		//
		if (byteConverter != null && Util.exists(file) && Util.isFile(file)) {
			//
			try {
				//
				FileUtils.writeByteArrayToFile(file, byteConverter.convert(Files.readAllBytes(path)));
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

	@Nullable
	private static List<String> getImageWriterSpiFormats(@Nullable final VoiceManagerPdfPanel instance) {
		return instance != null ? instance.imageWriterSpiFormats : null;
	}

	@Nullable
	private static ComboBoxModel<String> getCbmImageFormat(@Nullable final VoiceManagerPdfPanel instance) {
		return instance != null ? instance.cbmImageFormat : null;
	}

	private static void addImage(@Nullable final RenderedImage renderedImage, final ObjectMap om, float lastHeight,
			final boolean isOrginialSize) throws IOException, NoSuchFieldException {
		//
		if (renderedImage != null) {
			//
			try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				//
				ImageIO.write(renderedImage,
						StringUtils.defaultIfBlank(
								StringMap.getString(ObjectMap.getObject(om, StringMap.class), "imageFormat"), "PNG"),
						baos);
				//
				ObjectMap.setObject(om, byte[].class, baos.toByteArray());
				//
				addImage(om, lastHeight, isOrginialSize);
				//
			} // try
				//
			final VoiceManagerPdfPanel voiceManagerPdfPanel = ObjectMap.getObject(om, VoiceManagerPdfPanel.class);
			//
			actionPerformed(voiceManagerPdfPanel,
					testAndApply(Objects::nonNull,
							testAndApply(Objects::nonNull, voiceManagerPdfPanel,
									x -> Narcissus.getField(voiceManagerPdfPanel,
											Narcissus.findField(VoiceManagerPdfPanel.class, "btnImageClear")),
									null),
							x -> new ActionEvent(x, 0, null), null));
			//
			return;
			//
		} // if
			//
		final File file = ObjectMap.getObject(om, File.class);
		//
		if (Boolean.logicalAnd(Util.exists(file), Util.isFile(file))) {
			//
			ObjectMap.setObject(om, byte[].class, Files.readAllBytes(Util.toPath(file)));
			//
			addImage(om, lastHeight, isOrginialSize);
			//
		} else {
			//
			ObjectMap.setObject(om, String.class,
					StringMap.getString(ObjectMap.getObject(om, StringMap.class), "imageUrl"));
			//
			addImageByUrl(om, lastHeight, isOrginialSize);
			//
		} // if
			//
	}

	private static void actionPerformed(@Nullable final ActionListener instance, final ActionEvent evt) {
		if (instance != null) {
			instance.actionPerformed(evt);
		}
	}

	private static int getNumberOfPages(@Nullable final PDDocument instance) {
		return instance != null ? instance.getNumberOfPages() : 0;
	}

	@Nullable
	private static JTextComponent getTfImageFile(@Nullable final VoiceManagerPdfPanel instance) {
		return instance != null ? instance.tfImageFile : null;
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
			if (toBufferedImage(bs) != null && objectMap != null) {
				//
				objectMap.setObject(byte[].class, bs);
				//
				addImage(objectMap, lastHeight, isOrginialSize);
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

	private static void addImage(@Nullable final ObjectMap objectMap, final float lastHeight,
			final boolean isOrginialSize) throws IOException {
		//
		final byte[] bs = ObjectMap.getObject(objectMap, byte[].class);
		//
		final BufferedImage bi = toBufferedImage(bs);
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
		final PDImageXObject image = testAndApply(Objects::nonNull, bs,
				x -> PDImageXObject.createFromByteArray(ObjectMap.getObject(objectMap, PDDocument.class), x, null),
				null);
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
				drawImage(cs, image, (width - imageWidth) / 2, lastHeight - getHeight(bi), imageWidth, imageHeight);
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
			drawImage(cs, image, 0, lastHeight - (float) (imageHeight * ratio), width, (float) (imageHeight * ratio));
			//
		} // if
			//
	}

	private static <T, U, E extends Exception> void accept(@Nullable final FailableBiConsumer<T, U, E> instance,
			@Nullable final T t, final U u) throws E {
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

	private static BufferedImage toBufferedImage(@Nullable final byte[] bs) throws IOException {
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
			@Nullable final String voiceId, final int rate, final int volume, @Nullable final File file) {
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
		actionPerformed(instance, new ActionEvent(instance.btnExecute, 0, null));
		//
	}

	private static String getMimeType(final ContentInfoUtil instance, @Nullable final Path path) throws IOException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, path, Files::newInputStream, null)) {
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, is, x -> findMatch(instance, x), null);
			//
			return getMimeType(ci);
			//
		} // try
			//
	}

	private static ContentInfo findMatch(final ContentInfoUtil instance, final InputStream is) throws IOException {
		return instance != null ? instance.findMatch(is) : null;
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
	private static byte[] pdf(@Nullable final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(toURI(Util.toFile(pathHtml)))), page::navigate);
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

	private static <T, E extends Throwable> void testAndAccept(@Nullable final Predicate<T> predicate,
			@Nullable final T value, @Nullable final FailableConsumer<T, E> consumer) throws E {
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

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		float width = 0;
		for (int i = 0; i < StringUtils.length(text); i++) {
			// Get the width of each character and add it to the total width
			width += font.getWidth(text.charAt(i)) / 1000.0f;
		}
		return width * fontSize;
	}

}