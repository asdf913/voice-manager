package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
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
import java.net.URISyntaxException;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
import javax.swing.BorderFactory;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableRunnableUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.d2ab.function.ObjIntPredicate;
import org.d2ab.function.ObjIntPredicateUtil;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.javatuples.valueintf.IValue1Util;
import org.javatuples.valueintf.IValue2Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.atilika.kuromoji.TokenBase;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.reflect.Reflection;
import com.helger.commons.url.URLValidator;
import com.helger.css.ECSSUnit;
import com.helger.css.propertyvalue.CSSSimpleValueWithUnit;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.mariten.kanatools.KanaConverter;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import j2html.rendering.FlatHtml;
import j2html.rendering.HtmlBuilder;
import j2html.rendering.TagBuilder;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerPdfPanel extends JPanel implements Titled, InitializingBean, ActionListener,
		ApplicationContextAware, EnvironmentAware, DocumentListener, ItemListener {

	private static final long serialVersionUID = 284477348908531649L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerPdfPanel.class);

	private static final String GROWX = "growx";

	private static final String WRAP = "wrap";

	private static final String FORMAT = "format";

	private static final String PAGE1 = "page1";

	private static final String IMAGE = "Image";

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

	@Note("Browse Output Folder")
	private AbstractButton btnBrowseOutputFolder = null;

	@Note("Copy Text To HTML")
	private AbstractButton btnCopyTextToHtml = null;

	@Note("Generate Ruby Html")
	private AbstractButton btnGenerateRubyHtml = null;

	@Note("Preview Ruby In PDF")
	private AbstractButton btnPreviewRubyPdf = null;

	@Note("Preserve Image")
	private AbstractButton btnPreserveImage = null;

	@Note("Set Original Audio")
	private AbstractButton btnSetOriginalAudio = null;

	private AbstractButton btnClearOriginalAudio = null;

	@Note("HTML")
	private JTextComponent taHtml = null;

	@Note("Text")
	private JTextComponent tfText = null;

	@Note("Description")
	private JTextComponent tfDescription = null;

	@Note("Font Size 1")
	private JTextComponent tfFontSize1 = null;

	@Note("Font Size 2")
	private JTextComponent tfFontSize2 = null;

	@Note("Font Size 3")
	private JTextComponent tfFontSize3 = null;

	@Note("Image URL")
	private JTextComponent tfImageUrl = null;

	@Note("Image URL State Code")
	private JTextComponent tfImageUrlStateCode = null;

	@Note("Image URL Mime Type")
	private JTextComponent tfImageUrlMimeType = null;

	@Note("Image File")
	private JTextComponent tfImageFile = null;

	@Note("Output File")
	private JTextComponent tfOutputFile = null;

	@Note("Speech Language Code")
	private JTextComponent tfSpeechLanguageCode = null;

	private JTextComponent tfSpeechLanguageName = null;

	private transient Document taHtmlDocument = null;

	private transient ComboBoxModel<ECSSUnit> cbmFontSize1 = null;

	@Note("Voice ID")
	private transient ComboBoxModel<String> cbmVoiceId = null;

	private transient ComboBoxModel<String> cbmTextAlign1 = null;

	private transient ApplicationContext applicationContext = null;

	private transient PropertyResolver propertyResolver = null;

	@Note("org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation.rectangle Size")
	private Number pdAnnotationRectangleSize = null;

	private Number speechVolume = null;

	@Nullable
	private Map<Integer, String> speechSpeedMap = null;

	@Nullable
	private Map<Integer, String> fontSizeAndUnitMap = null;

	@Nullable
	private transient RenderedImage renderedImage = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private transient ComboBoxModel<?> cbmAudioFormat = null;

	private JComboBox<Object> jcbVoiceId = null;

	@Nullable
	private List<String> imageWriterSpiFormats = null;

	private List<String> imageFormatOrders = null;

	private ObjectMapper objectMapper = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	@Note("Audio Format")
	private String audioFormat = null;

	private String cssSpecificationUrl = null;

	private transient Object textAlign = null;

	private transient IValue0<List<String>> textAligns = null;

	private transient Configuration freeMarkerConfiguration = null;

	private transient FailableFunction<Playwright, BrowserType, ReflectiveOperationException> playwrightBrowserTypeFunction = null;

	private Pattern patternInteger = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private transient Resource audioResource = null;

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
			Util.putAll(
					fontSizeAndUnitMap = new LinkedHashMap<>(Map.of(Integer.valueOf(3), "266px", Integer.valueOf(4),
							"200px", Integer.valueOf(5), "160px", Integer.valueOf(6), "133px", Integer.valueOf(7),
							"114px", Integer.valueOf(8), "100px", Integer.valueOf(9), "88px", Integer.valueOf(10),
							"80px", Integer.valueOf(11), "72px", Integer.valueOf(12), "66px")),
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

	public void setCssSpecificationUrl(final String cssSpecificationUrl) {
		this.cssSpecificationUrl = cssSpecificationUrl;
	}

	public void setTextAlign(final Object textAlign) {
		this.textAlign = textAlign;
	}

	public void setTextAligns(@Nullable final Object instance) {
		//
		if (instance == null) {
			//
			textAligns = Unit.with(null);
			//
			return;
			//
		} else if (instance instanceof Iterable iterable) {
			//
			textAligns = Unit
					.with(Util.toList(Util.map(StreamSupport.stream(spliterator(iterable), false), Objects::toString)));
			//
			return;
			//
		} else if (instance instanceof String s1) {
			//
			if (StringUtils.isBlank(s1)) {
				//
				textAligns = Unit.with(Collections.singletonList(s1));
				//
				return;
				//
			} // if
				//
			try {
				//
				final Object result = ObjectMapperUtil.readValue(getObjectMapper(), s1, Object.class);
				//
				if (result instanceof String s2) {
					//
					textAligns = Unit.with(Collections.singletonList(s2));
					//
					return;
					//
				} // if
					//
				setTextAligns(result);
				//
				return;
				//
			} catch (final JsonProcessingException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} else if (instance instanceof Number || instance instanceof Boolean) {
			//
			textAligns = Unit.with(Collections.singletonList(Util.toString(instance)));
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException(Util.toString(Util.getClass(instance)));
		//
	}

	@Nullable
	private static <E> Iterator<E> asIterator(@Nullable final Enumeration<E> instance) {
		return instance != null ? instance.asIterator() : null;
	}

	public void setFreeMarkerConfiguration(final Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	public void setBrowserType(final String browserType) {
		//
		this.playwrightBrowserTypeFunction = playWright -> {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Playwright.class), Arrays::stream, null),
					m -> m != null && Objects.equals(m.getReturnType(), BrowserType.class) && m.getParameterCount() == 0
							&& StringUtils.startsWithIgnoreCase(Util.getName(m), browserType)));
			//
			final int size = IterableUtils.size(ms);
			//
			if (size == 0) {
				//
				return chromium(playWright);
				//
			} else if (size > 1) {
				//
				throw new IllegalArgumentException();
				//
			} // if
				//
			final Method m = IterableUtils.get(ms, 0);
			//
			if (m == null) {
				//
				throw new IllegalArgumentException();
				//
			} // if
				//
			return Util.cast(BrowserType.class, m.invoke(playWright));
			//
		};
	}

	public void setLanguageCodeToTextObjIntFunction(
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		this.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
	}

	@Nullable
	private static BrowserType chromium(@Nullable final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	private FailableFunction<Playwright, BrowserType, ReflectiveOperationException> getPlaywrightBrowserTypeFunction() {
		//
		if (playwrightBrowserTypeFunction == null) {
			//
			playwrightBrowserTypeFunction = VoiceManagerPdfPanel::chromium;
			//
		} // if
			//
		return playwrightBrowserTypeFunction;
		//
	}

	private static class VoiceIdListCellRenderer implements ListCellRenderer<Object> {

		private SpeechApi speechApi = null;

		private ListCellRenderer<Object> listCellRenderer = null;

		private String commonPrefix = null;

		private VoiceIdListCellRenderer(final SpeechApi speechApi) {
			this.speechApi = speechApi;
		}

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

	/**
	 *
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this,
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
		final int wmin = 30;
		//
		add(tfFontSize1 = new JTextField(Util.getKey(entry)), String.format("%1$s,wmin %2$s", GROWX, wmin));
		//
		final int span = 6;
		//
		add(new JComboBox<>(
				cbmFontSize1 = new DefaultComboBoxModel<>(ArrayUtils.insert(0, ECSSUnit.values(), (ECSSUnit) null))),
				String.format("%1$s,span %2$s", WRAP, span - 1));
		//
		setSelectedItem(cbmFontSize1, Util.getValue(entry));
		//
		// Text Align
		//
		add(new JLabel("Text Align"));
		//
		add(new JComboBox<>(cbmTextAlign1 = new DefaultComboBoxModel<>(ObjectUtils.defaultIfNull(
				ArrayUtils.insert(0, Util.toArray(getTextAligns(), new String[] {}), (String) null), new String[] {}))),
				String.format("%1$s,span %2$s", WRAP, span));
		//
		setSelectedItem(cbmTextAlign1, textAlign);
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
		add(jsp, String.format("%1$s,span %2$s", GROWX, span - 2));
		//
		JPanel panel = new JPanel();
		//
		setLayout(panel, new GridBagLayout());
		//
		final GridBagConstraints gbc = new GridBagConstraints();
		//
		gbc.weightx = 1;
		//
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		//
		panel.add(btnGenerateRubyHtml = new JButton("Generate Ruby HTML"), gbc);
		//
		panel.add(btnPreviewRubyPdf = new JButton("Preview Ruby"), gbc);
		//
		add(panel, String.format("%1$s,%2$s,span %3$s", WRAP, GROWX, 3));
		//
		// Description
		//
		add(new JLabel("Description"));
		//
		add(tfFontSize3 = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.fontSize3")),
				String.format("%1$s,wmin %2$s", GROWX, wmin));
		//
		add(tfDescription = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.description")),
				String.format("%1$s,%2$s,span %3$s", WRAP, GROWX, span - 1));
		//
		// Font Size
		//
		add(new JLabel("Font Size (Label)"));
		//
		add(tfFontSize2 = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.fontSize2")),
				String.format("%1$s,%2$s,wmin %3$s", GROWX, WRAP, wmin));
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
				String.format("%1$s,span %2$s", GROWX, span - 1));
		//
		add(btnCopyTextToHtml = new JButton("Copy"), WRAP);
		//
		// Image
		//
		(panel = createImagePanel(this,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new)))
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), IMAGE));
		//
		add(panel, String.format("%1$s,%2$s,span %3$s", WRAP, GROWX, span + 1));
		//
		// Audio
		//
		(panel = createAudioPanel(this,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new)))
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Audio"));
		//
		add(panel, String.format("%1$s,%2$s,span %3$s", WRAP, GROWX, span + 1));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", WRAP, 2));
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
		Util.forEach(Arrays.asList(btnImageClear, btnClearOriginalAudio),
				x -> actionPerformed(new ActionEvent(x, 0, null)));
		//
		final IH ih = new IH();
		//
		ih.docuemnt = taHtmlDocument;
		//
		insertUpdate(Reflection.newProxy(DocumentEvent.class, ih));
		//
	}

	private static JPanel createAudioPanel(final VoiceManagerPdfPanel instance, final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel(layoutManager);
		//
		if (instance != null) {
			//
			// Voice ID
			//
			panel.add(new JLabel("Voice ID"));
			//
			final SpeechApi speechApi = instance.speechApi;
			//
			final String[] voiceIds = testAndApply(x -> SpeechApi.isInstalled(x), speechApi,
					x -> SpeechApi.getVoiceIds(x), null);
			//
			if ((instance.cbmVoiceId = testAndApply(Objects::nonNull, voiceIds,
					x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
				//
				final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer(speechApi);
				//
				voiceIdListCellRenderer.listCellRenderer = getRenderer(Util.cast(JComboBox.class,
						instance.jcbVoiceId = new JComboBox<>(Util.cast(ComboBoxModel.class, instance.cbmVoiceId))));
				//
				voiceIdListCellRenderer.commonPrefix = String.join("",
						StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
				//
				instance.jcbVoiceId.setRenderer(voiceIdListCellRenderer);
				//
				instance.jcbVoiceId.addItemListener(instance);
				//
				panel.add(instance.jcbVoiceId, String.format("span %1$s", 2));
				//
				panel.add(instance.tfSpeechLanguageCode = new JTextField(), String.format("width %1$s", 30));
				//
				panel.add(instance.tfSpeechLanguageName = new JTextField(),
						String.format("%1$s,width %2$s", WRAP, 230));
				//
				final String s = PropertyResolverUtil.getProperty(instance.propertyResolver,
						"org.springframework.context.support.VoiceManagerPdfPanel.voiceId");
				//
				Object element = null;
				//
				for (int i = 0; i < instance.cbmVoiceId.getSize(); i++) {
					//
					if (s != null && Util.contains(Arrays.asList(element = instance.cbmVoiceId.getElementAt(i),
							SpeechApi.getVoiceAttribute(speechApi, Util.toString(element), "Name")), s)) {
						//
						setSelectedItem(instance.cbmVoiceId, element);
						//
					} // if
						//
				} // for
					//
			} // if
				//
				// Original
				//
			panel.add(new JLabel("Original"));
			//
			panel.add(instance.btnSetOriginalAudio = new JButton("Set"));
			//
			panel.add(instance.btnClearOriginalAudio = new JButton("Clear"), WRAP);
			//
			// Format
			//
			panel.add(new JLabel("Format"));
			//
			final JComboBox<Object> jcbAudioFormat = new JComboBox(
					instance.cbmAudioFormat = new DefaultComboBoxModel<Object>());
			//
			final ApplicationContext applicationContext = instance.applicationContext;
			//
			final Collection<?> formats = getByteConverterAttributeValues(
					instance.configurableListableBeanFactory = ObjectUtils
							.defaultIfNull(Util.cast(ConfigurableListableBeanFactory.class, applicationContext),
									Util.cast(ConfigurableListableBeanFactory.class,
											ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext))),
					FORMAT);
			//
			final MutableComboBoxModel<Object> mcbmAudioFormatWrite = Util.cast(MutableComboBoxModel.class,
					instance.cbmAudioFormat);
			//
			addElement(mcbmAudioFormatWrite, null);
			//
			Util.forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
			//
			mcbmAudioFormatWrite.setSelectedItem(instance.audioFormat);
			//
			panel.add(jcbAudioFormat, String.format("%1$s,span %2$s", WRAP, 2));
			//
		} // if
			//
		return panel;
		//
	}

	private static JPanel createImagePanel(@Nullable final VoiceManagerPdfPanel instance,
			final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel(layoutManager);
		//
		if (instance != null) {
			//
			final int span = 6;
			//
			// URL
			//
			panel.add(new JLabel("URL"));
			//
			panel.add(
					instance.tfImageUrl = new JTextField(PropertyResolverUtil.getProperty(instance.propertyResolver,
							"org.springframework.context.support.VoiceManagerPdfPanel.imageUrl")),
					String.format("%1$s,span %2$s", GROWX, span - 2));
			//
			panel.add(instance.tfImageUrlStateCode = new JTextField(), String.format("wmin %1$s", 27));
			//
			panel.add(instance.tfImageUrlMimeType = new JTextField(), String.format("%1$s,wmin %2$s", WRAP, 65));
			//
			// File
			//
			panel.add(new JLabel("File"));
			//
			panel.add(instance.tfImageFile = new JTextField(), String.format("%1$s,span %2$s", GROWX, span - 1));
			//
			setEditable(false, instance.tfImageUrlStateCode, instance.tfImageUrlMimeType, instance.tfImageFile,
					instance.tfSpeechLanguageCode, instance.tfSpeechLanguageName);
			//
			panel.add(instance.btnImageFile = new JButton("Select"), WRAP);
			//
			// Clip board
			//
			panel.add(new JLabel("Clipboard"));
			//
			panel.add(instance.btnImageFromClipboard = new JButton("Copy"));
			//
			panel.add(instance.btnImageClear = new JButton("Clear"));
			//
			panel.add(instance.btnImageView = new JButton("View"));
			//
			final MutableComboBoxModel<String> mcbm = new DefaultComboBoxModel<>();
			//
			panel.add(new JComboBox<>(mcbm));
			//
			sort(instance.imageWriterSpiFormats, createImageFormatComparator(instance.imageFormatOrders));
			//
			Util.forEach(instance.imageWriterSpiFormats, mcbm::addElement);
			//
			instance.cbmImageFormat = mcbm;
			//
			panel.add(instance.btnPreserveImage = new JCheckBox("Prserve Image"),
					String.format("%1$s,span %2$s", WRAP, 2));
			//
			// Original Size
			//
			panel.add(new JLabel("Original Size"));
			//
			panel.add(instance.cbIsOriginalSize = new JCheckBox(), String.format("%1$s,span %2$s", WRAP, 2));
			//
		} // if
			//
		return panel;
		//
	}

	private List<String> getTextAligns() throws IOException, URISyntaxException {
		//
		if (textAligns != null || isTestMode()) {
			//
			return IValue0Util.getValue0(textAligns);
			//
		} // if
			//
		return Util
				.toList(Util.filter(
						Util.map(
								flatMap(Util.map(
										Util.filter(flatMap(
												Util.map(
														Util.filter(Util.stream(ElementUtil.select(
																testAndApply(Objects::nonNull, toURL(testAndApply(
																		Objects::nonNull,
																		StringUtils.defaultIfBlank(cssSpecificationUrl,
																				"https://www.w3.org/TR/css-text-4/"),
																		URI::new, null)), x -> Jsoup.parse(x, 0), null),
																"th")),
																x -> Objects.equals(ElementUtil.text(x), "Name:")
																		&& Objects.equals("text-align",
																				ElementUtil.text(ElementUtil
																						.nextElementSibling(x)))),
														x -> NodeUtil.childNodes(
																NodeUtil.nextSibling(NodeUtil.parentNode(x)))),
												Util::stream), x -> Objects.equals("td", NodeUtil.nodeName(x))),
										NodeUtil::childNodes), Util::stream),
								x -> StringUtils.trim(TextNodeUtil.text(Util.cast(TextNode.class, x)))),
						StringUtils::isNotBlank));
		//
	}

	@Nullable
	private static <T, R> Stream<R> flatMap(@Nullable final Stream<T> instance,
			final Function<? super T, ? extends Stream<? extends R>> mapper) {
		return instance != null ? instance.flatMap(mapper) : null;
	}

	private static void setLayout(@Nullable final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
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
			final File file = Util.toFile(Path
					.of(StringUtils.joinWith(".", StringUtils.defaultIfBlank(Util.getText(tfText), "test"), "pdf")));
			//
			PDDocument document = null;
			//
			try {
				//
				final String captionHtml = Util.getText(taHtml);
				//
				final Map<Object, Object> map = new LinkedHashMap<>();
				//
				map.put("captionHtml", captionHtml);
				//
				final Map<String, String> captionStyle = new LinkedHashMap<>(createStyleMap());
				//
				testAndAccept(CollectionUtils::isNotEmpty, ElementUtil
						.getElementsByTag(testAndApply(Objects::nonNull, captionHtml, Jsoup::parse, null), "ruby"),
						x -> Util.put(captionStyle, "display", "ruby-text"));
				//
				final String captionStyleKey = "captionStyle";
				//
				map.put(captionStyleKey, captionStyle);
				//
				final int borderWidth = 1;
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, map),
						StandardCharsets.UTF_8, false);
				//
				final FailableFunction<Playwright, BrowserType, ReflectiveOperationException> function = getPlaywrightBrowserTypeFunction();
				//
				IntIntPair intIntPair = null;
				//
				try (final InputStream is = testAndApply(Objects::nonNull, screenshot(pathHtml, function),
						ByteArrayInputStream::new, null)) {
					//
					intIntPair = getMinimumAndMaximumY(testAndApply(Objects::nonNull, is, ImageIO::read, null));
					//
				} // try
					//
				final String captionOuterStyle = "captionOuterStyle";
				//
				map.put(captionOuterStyle, Map.of("border", String.format("solid %1$spx", borderWidth)));
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, map),
						StandardCharsets.UTF_8, false);
				//
				map.remove(captionOuterStyle);
				//
				Integer top = null;
				//
				try (final InputStream is = testAndApply(Objects::nonNull, screenshot(pathHtml, function),
						ByteArrayInputStream::new, null)) {
					//
					leftOrRight(intIntPair,
							rightInt(intIntPair, 0) - Util.intValue(top = Integer.valueOf(leftInt(
									getMinimumAndMaximumY(testAndApply(Objects::nonNull, is, ImageIO::read, null)), 0)),
									0));
					//
				} // try
					//
				map.put("descriptionHtml", Util.getText(tfDescription));
				//
				// TODO
				//
				// 30 character per line
				//
				final String position = "position";
				//
				final String absolute = "absolute";
				//
				final BigDecimal fontSize3 = testAndApply(x -> matches(x),
						matcher(patternInteger = ObjectUtils.getIfNull(patternInteger,
								() -> Pattern.compile("^(-?\\d+(\\.\\d+)?)$")), Util.getText(tfFontSize3)),
						x -> new BigDecimal(group(x, 0)), null);
				//
				final Map<String, String> descriptionStyle = testAndApply(
						(a, b) -> Boolean.logicalAnd(a != null, b != null),
						Map.of("font-size",
								String.format("%1$spx",
										ObjectUtils.max(ObjectUtils.defaultIfNull(fontSize3, BigDecimal.ZERO),
												new BigDecimal("40"))),
								position, absolute),
						intIntPair, (a, b) -> new LinkedHashMap<>(a), (a, b) -> a);
				//
				final String descriptionStyleKey = "descriptionStyle";
				//
				map.put(descriptionStyleKey, descriptionStyle);
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, map),
						StandardCharsets.UTF_8, false);
				//
				Map<?, ?> m1 = Util.cast(Map.class, ObjectMapperUtil.readValue(getObjectMapper(),
						ObjectMapperUtil.writeValueAsString(getObjectMapper(), map), Object.class));
				//
				Util.put((Map) Util.cast(Map.class, Util.get(m1, captionStyleKey)), "visibility", "hidden");
				//
				Util.putAll((Map) Util.cast(Map.class, Util.get(m1, descriptionStyleKey)),
						Util.collect(
								Util.map(
										Util.stream(Sets.cartesianProduct(
												new LinkedHashSet<>(Arrays.asList("border-top", "border-bottom")),
												Collections.singleton(String.format("solid %1$spx", borderWidth)))),
										x ->
										//
										testAndApply(y -> IterableUtils.size(y) == 2, x,
												y -> ImmutablePair.of(IterableUtils.get(y, 0), IterableUtils.get(y, 1)),
												null)
								//
								), Collectors.toMap(Util::getKey, Util::getValue)));
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, m1),
						StandardCharsets.UTF_8, false);
				//
				try (final InputStream is = testAndApply(Objects::nonNull, screenshot(pathHtml, function),
						ByteArrayInputStream::new, null)) {
					//
					final BufferedImage bi = testAndApply(Objects::nonNull, is, ImageIO::read, null);
					//
					if (intIntPair != null) {
						//
						final IntIntPair temp = getMinimumAndMaximumY(bi);
						//
						right(intIntPair,
								rightInt(intIntPair, 0) - (leftInt(temp, 0) - rightInt(intIntPair, 0)
										- leftInt(getMinimumAndMaximumY(getSubimage(bi, 0,
												leftInt(temp, 0) + borderWidth, Util.intValue(getWidth(bi), 0),
												rightInt(temp, 0) - leftInt(temp, 0) - borderWidth)), 0)));
						//
					} // if
						//
				} // try
					//
				if (intIntPair != null) {
					//
					Util.put(descriptionStyle, "top", StringUtils.joinWith("", rightInt(intIntPair, 0), "px"));
					//
				} // if
					//
				testAndAccept((a, b) -> a != null, top, map, (a, b) -> Util.put(b, captionOuterStyle,
						Map.of(position, absolute, "top", Util.toString(Util.intValue(a, 0) * -1))));
				//
				final Color colorCaption = new Color(255, 0, 0);
				//
				Util.put(
						(Map) Util.cast(Map.class,
								Util.get(m1 = Util.cast(Map.class, ObjectMapperUtil.readValue(getObjectMapper(),
										ObjectMapperUtil.writeValueAsString(getObjectMapper(), map), Object.class)),
										captionStyleKey)),
						"color", String.format("rgb(%1$s,%2$s,%3$s)", colorCaption.getRed(), colorCaption.getGreen(),
								colorCaption.getBlue()));
				//
				final Color colorDescription = new Color(0, 255, 0);
				//
				Util.put((Map) Util.cast(Map.class, Util.get(m1, descriptionStyleKey)), "color",
						String.format("rgb(%1$s,%2$s,%3$s)", colorDescription.getRed(), colorDescription.getGreen(),
								colorDescription.getBlue()));
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, m1),
						StandardCharsets.UTF_8, false);
				//
				try (final InputStream is = testAndApply(Objects::nonNull, screenshot(pathHtml, function),
						ByteArrayInputStream::new, null)) {
					//
					final BufferedImage bi = testAndApply(Objects::nonNull, is, ImageIO::read, null);
					//
					final IntIntPair intIntPairCaption = getMinimumAndMaximumY(bi, colorCaption);
					//
					if (top != null) {
						//
						top = Integer.valueOf(top.intValue() + leftInt(intIntPairCaption, 0));
						//
					} // if
						//
					if ((intIntPair = right(intIntPair,
							rightInt(intIntPair, 0) - leftInt(intIntPairCaption, 0) + rightInt(intIntPairCaption, 0)
									- leftInt(getMinimumAndMaximumY(bi, colorDescription), 0))) != null) {
						//
						Util.put(descriptionStyle, "top", StringUtils.joinWith("", rightInt(intIntPair, 0), "px"));
						//
					} // if
						//
				} // try
					//
				testAndAccept((a, b) -> a != null, top, map, (a, b) -> Util.put(b, captionOuterStyle,
						Map.of(position, absolute, "top", Util.toString(Util.intValue(a, 0) * -1))));
				//
				FileUtils.writeStringToFile(Util.toFile(pathHtml), generatePdfHtml(freeMarkerConfiguration, map),
						StandardCharsets.UTF_8, false);
				//
				document = Loader.loadPDF(pdf(pathHtml, function));
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
				ObjectMap.setObject(objectMap, Resource.class, audioResource);
				//
				addTextAndVoice(objectMap,
						ObjectUtils.getIfNull(speechSpeedMap, VoiceManagerPdfPanel::getDefaultSpeechSpeedMap),
						Util.intValue(speechVolume, 100), Util.isSelected(cbIsOriginalSize));
				//
				Util.setText(tfOutputFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException | TemplateException | ReflectiveOperationException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} // if
			//
		final Iterable<Predicate<Object>> predicates = Arrays.asList(this::actionPerformed2, this::actionPerformed3,
				this::actionPerformed4);
		//
		for (int i = 0; i < IterableUtils.size(predicates); i++) {
			//
			if (Util.test(IterableUtils.get(predicates, i), source)) {
				//
				break;
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static String generatePdfHtml(final Configuration configuration, final Map<?, ?> map)
			throws IOException, TemplateException {
		//
		final Template template = ConfigurationUtil.getTemplate(configuration, "pdf.html.ftl");
		//
		try (final Writer writer = new StringWriter()) {
			//
			TemplateUtil.process(template, map, writer);
			//
			return Util.toString(writer);
			//
		} // try
			//
	}

	private boolean actionPerformed2(final Object source) {
		//
		if (Objects.equals(source, btnImageClear)) {
			//
			setEnabled((renderedImage = null) != null, btnImageClear, btnImageView);
			//
		} else if (Objects.equals(source, btnImageView)) {
			//
			testAndRun(!isTestMode(),
					() -> JOptionPane.showMessageDialog(null, testAndApply(Objects::nonNull,
							Util.cast(BufferedImage.class, renderedImage), ImageIcon::new, null), IMAGE,
							JOptionPane.PLAIN_MESSAGE, null),
					null);
			//
		} else if (Objects.equals(source, btnCopyOutputFilePath)) {
			//
			setContents(testAndApply(x -> !GraphicsEnvironment.isHeadless(), Toolkit.getDefaultToolkit(),
					x -> getSystemClipboard(x), null), new StringSelection(Util.getText(tfOutputFile)), null);
			//
		} else if (Objects.equals(source, btnCopyTextToHtml)) {
			//
			Util.setText(taHtml, Util.getText(tfText));
			//
		} else if (Objects.equals(source, btnImageFile)) {
			//
			final JFileChooser jfc = testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
					() -> new JFileChooser("."));
			//
			if (jfc != null && jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
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
		} else if (Objects.equals(source, btnGenerateRubyHtml)) {
			//
			try {
				//
				testAndAccept(VoiceManagerPdfPanel::isPlainText, Util.getText(taHtml),
						x -> Util.setText(taHtml, toHtml(x)));
				//
			} catch (final IOException e) {
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

	@Nullable
	private static <T> T testAndGet(final boolean b, @Nullable final Supplier<T> supplier) {
		return b && supplier != null ? supplier.get() : null;
	}

	private boolean actionPerformed3(final Object source) {
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
							getTransferData(transferable, DataFlavor.imageFlavor));
					//
				} else if (isDataFlavorSupported(transferable, DataFlavor.javaFileListFlavor)) {
					//
					final File file = Util.cast(File.class,
							testAndApply(x -> IterableUtils.size(x) == 1,
									Util.cast(Iterable.class,
											getTransferData(transferable, DataFlavor.javaFileListFlavor)),
									x -> IterableUtils.get(x, 0), null));
					//
					final Entry<Method, Collection<Object>> entry = getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes();
					//
					if (Util.contains(Util.getValue(entry),
							testAndApply(
									f -> Boolean.logicalAnd(Util.exists(f), Util.isFile(f)), file, f -> Narcissus
											.invokeStaticMethod(Util.getKey(entry), Files.readAllBytes(Util.toPath(f))),
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
		} else if (Objects.equals(source, btnBrowseOutputFolder)) {
			//
			try {
				//
				testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)),
						Util.toFile(testAndApply(Objects::nonNull, Util.getText(tfOutputFile), Path::of, null)),
						x -> browse(Desktop.getDesktop(), Util.toURI(getParentFile(x))));
				//
			} catch (final IOException e) {
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

	private boolean actionPerformed4(final Object source) {
		//
		if (Objects.equals(source, btnPreviewRubyPdf)) {
			//
			File file = null;
			//
			PDDocument document = null;
			//
			try {
				//
				final Map<Object, Object> map = new LinkedHashMap<>(
						Collections.singletonMap("captionStyle", createStyleMap()));
				//
				map.put("captionHtml", Util.getText(taHtml));
				//
				final String html = generatePdfHtml(freeMarkerConfiguration, map);
				//
				FileUtils.writeStringToFile(
						file = File.createTempFile(nextAlphabetic(RandomStringUtils.secureStrong(), 3), null), html,
						StandardCharsets.UTF_8, false);
				//
				final String[] fileExtensions = getFileExtensions(findMatch(new ContentInfoUtil(), file));
				//
				final Matcher matcher = testAndApply((a, b) -> length(b) > 0, file, fileExtensions,
						(a, b) -> matcher(Pattern.compile("^([^.]+.)[^.]+$"), Util.getName(a)), null);
				//
				if (matches(matcher) && groupCount(matcher) > 0) {
					//
					FileUtils.deleteQuietly(file);
					//
					FileUtils.writeStringToFile(
							file = Util.toFile(
									Path.of(StringUtils.join(group(matcher, 1), ArrayUtils.get(fileExtensions, 0)))),
							html, StandardCharsets.UTF_8, false);
					//
				} // if
					//
				testAndAccept(a -> !GraphicsEnvironment.isHeadless() && !isTestMode(),
						document = Loader.loadPDF(pdf(Util.toPath(file), getPlaywrightBrowserTypeFunction())),
						a -> JOptionPane.showMessageDialog(null,
								testAndApply(Objects::nonNull,
										chop(testAndApply(x -> getNumberOfPages(x) > 0, a,
												x -> new PDFRenderer(x).renderImage(0), null)),
										ImageIcon::new, null),
								IMAGE, JOptionPane.PLAIN_MESSAGE, null));
				//
			} catch (final IOException | ReflectiveOperationException | TemplateException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} finally {
				//
				FileUtils.deleteQuietly(file);
				//
				IOUtils.closeQuietly(document);
				//
			} // try
				//
			return true;
			//
		} else if (Objects.equals(source, btnSetOriginalAudio)) {
			//
			try {
				//
				setEnabled(btnClearOriginalAudio,
						(audioResource = getAudioResource(
								getContents(
										testAndApply(x -> !GraphicsEnvironment.isHeadless(),
												Toolkit.getDefaultToolkit(), x -> getSystemClipboard(x), null),
										null))) != null);
				//
			} catch (final Exception e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnClearOriginalAudio)) {
			//
			setEnabled(btnClearOriginalAudio, (audioResource = null) != null);
			//
		} // if
			//
		return false;
		//
	}

	private static Resource getAudioResource(final Transferable transferable) throws Exception {
		//
		Resource resource = null;
		//
		final Iterable<?> iterable = Util.cast(Iterable.class, testAndApply(VoiceManagerPdfPanel::isDataFlavorSupported,
				transferable, DataFlavor.javaFileListFlavor, VoiceManagerPdfPanel::getTransferData, null));
		//
		File file = Util.cast(File.class,
				testAndApply(x -> IterableUtils.size(x) == 1, iterable, x -> IterableUtils.get(x, 0), null));
		//
		ContentInfoUtil ciu = null;
		//
		JFileChooser jfc = null;
		//
		if (isDirectory(file)) {
			//
			if ((resource = toAudioResource(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new),
					listFiles(file))) != null
					|| ((jfc = new JFileChooser(file)).showOpenDialog(null) == JFileChooser.APPROVE_OPTION
							&& (resource = toAudioResource(ciu, jfc.getSelectedFile())) != null)) {
				//
				return resource;
				//
			} // if
				//
		} else if ((resource = toAudioResource(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), file)) != null) {
			//
			return resource;
			//
		} // if
			//
		final String string = Util.toString(testAndApply(VoiceManagerPdfPanel::isDataFlavorSupported, transferable,
				DataFlavor.stringFlavor, (a, b) -> getTransferData(a, b), null));
		//
		final URL url = testAndApply(URLValidator::isValid, string, URL::new, null);
		//
		try (final InputStream is = openStream(url)) {
			//
			final byte[] bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
			final String mimeType = getMimeType(testAndApply((a, b) -> b != null,
					ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), bs, (a, b) -> findMatch(a, b), null));
			//
			if (StringUtils.startsWith(mimeType, "audio")) {
				//
				return new ByteArrayResource(bs);
				//
			} // if
				//
		} // try
			//
		if ((resource = toAudioResource(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new),
				file = Util.toFile(testAndApply(Objects::nonNull, string, Path::of, null)))) != null
				|| (resource = testAndApply((a, b) -> isDirectory(b), ciu, file,
						(a, b) -> toAudioResource(a, listFiles(b)), null)) != null) {
			//
			return resource;
			//
		} // if
			//
		if (!GraphicsEnvironment.isHeadless() && !isTestMode()
				&& (jfc = new JFileChooser()).showOpenDialog(null) == JFileChooser.APPROVE_OPTION
				&& (resource = toAudioResource(ciu, jfc.getSelectedFile())) != null) {
			//
			return resource;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static File[] listFiles(@Nullable final File instance) {
		return instance != null ? instance.listFiles() : null;
	}

	private static boolean isDirectory(@Nullable final File instance) {
		return instance != null && instance.isDirectory();
	}

	@Nullable
	private static Object getTransferData(@Nullable final Transferable instance, final DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return instance != null ? instance.getTransferData(flavor) : null;
	}

	@Nullable
	private static Resource toAudioResource(final ContentInfoUtil ciu, @Nullable final File[] fs) throws IOException {
		//
		Resource r = null;
		//
		List<Resource> rs = null;
		//
		if (fs != null) {
			//
			for (final File f : fs) {
				//
				if ((r = toAudioResource(ciu, f)) != null) {
					//
					Util.add(rs = ObjectUtils.getIfNull(rs, ArrayList::new), r);
					//
				} // if
					//
			} // for
				//
		} // if
			//
		if (IterableUtils.size(rs) == 1 && (r = IterableUtils.get(rs, 0)) != null) {
			//
			return r;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Resource toAudioResource(final ContentInfoUtil ciu, @Nullable final File file) throws IOException {
		//
		final ContentInfo ci = findMatch(ciu, file);
		//
		if (StringUtils.startsWith(getMimeType(ci), "audio")) {
			//
			return new FileSystemResource(file);
			//
		} // if
			//
			// TODO
			//
		System.out.println("0 " + Util.getAbsolutePath(file) + " " + getMimeType(ci));
		//
		return null;
		//
	}

	@Nullable
	private static ContentInfo findMatch(@Nullable final ContentInfoUtil instance, @Nullable final byte[] bs) {
		return instance != null && bs != null ? instance.findMatch(bs) : null;
	}

	@Nullable
	private static ContentInfo findMatch(@Nullable final ContentInfoUtil instance, @Nullable final File file)
			throws IOException {
		return instance != null && Util.exists(file) && Util.isFile(file) ? instance.findMatch(file) : null;
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), jcbVoiceId)) {
			//
			try {
				//
				final String language = SpeechApi.getVoiceAttribute(speechApi,
						Util.toString(getSelectedItem(cbmVoiceId)), "Language");
				//
				Util.setText(tfSpeechLanguageCode, language);
				//
				Util.setText(tfSpeechLanguageName, StringUtils.defaultIfBlank(
						ObjIntFunctionUtil.apply(languageCodeToTextObjIntFunction, language, 16), language));
				//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // if
			//
	}

	@Nullable
	private static String[] getFileExtensions(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static BufferedImage chop(@Nullable final BufferedImage bi) {
		//
		Integer rgb = null;
		//
		IntList ilx = null;
		//
		IntList ily = null;
		//
		final FailableConsumer<IntList, RuntimeException> failableConsumer = a -> {
			//
			sortInts(a);
			//
			for (int i = IterableUtils.size(a) - 2; i > 0; i--) {
				//
				a.remove(i);
				//
			} // for
				//
		};
		//
		for (int x = 0; x < Util.intValue(getWidth(bi), 0); x++) {
			//
			for (int y = 0; y < Util.intValue(getHeight(bi), 0); y++) {
				//
				if (rgb == null && bi != null) {
					//
					rgb = Integer.valueOf(bi.getRGB(x, y));
					//
				} else if (rgb != null && rgb.intValue() != bi.getRGB(x, y)) {
					//
					// ilx
					//
					testAndAccept((a, b) -> !Util.contains(a, b), ilx = ObjectUtils.getIfNull(ilx, IntList::create), x,
							IntCollectionUtil::addInt);
					//
					testAndAccept(a -> IterableUtils.size(a) > 2, ilx, failableConsumer);
					//
					// ily
					//
					testAndAccept((a, b) -> !Util.contains(a, b), ily = ObjectUtils.getIfNull(ily, IntList::create), y,
							IntCollectionUtil::addInt);
					//
					testAndAccept(a -> IterableUtils.size(a) > 2, ily, failableConsumer);
					//
				} // if
					//
			} // for
				//
		} // for
			//
		sortInts(ilx);
		//
		sortInts(ily);
		//
		final int sizeIlx = IterableUtils.size(ilx);
		//
		final int sizeIly = IterableUtils.size(ily);
		//
		if (Boolean.logicalAnd(sizeIlx > 1, sizeIly > 1)) {
			//
			final int firstX = getInt(ilx, 0, 0);
			//
			final int firstY = getInt(ily, 0, 0);
			//
			return ObjectUtils.defaultIfNull(getSubimage(bi, firstX, firstY, getInt(ilx, sizeIlx - 1, 0) - firstX + 1,
					getInt(ily, sizeIly - 1, 0) - firstY + 1), bi);
			//
		} // if
			//
		return bi;
		//
	}

	private static <T> void testAndAccept(final ObjIntPredicate<T> predicate, final T t, final int i,
			@Nullable final ObjIntConsumer<T> consumer) {
		if (ObjIntPredicateUtil.test(predicate, t, i) && consumer != null) {
			consumer.accept(t, i);
		}
	}

	@Nullable
	private static BufferedImage getSubimage(@Nullable final BufferedImage instance, final int x, final int y,
			final int w, final int h) {
		return instance != null ? instance.getSubimage(x, y, w, h) : null;
	}

	private static int getInt(@Nullable final IntList instance, final int index, final int defaultValue) {
		return instance != null ? instance.getInt(index) : defaultValue;
	}

	private Map<String, String> createStyleMap() {
		//
		return createStyleMap(
				Map.of("text-align", ObjectUtils.defaultIfNull(Util.toString(getSelectedItem(cbmTextAlign1)), "center"),
						"display", "block", "margin-left", "auto", "margin-right", "auto"),
				testAndApply(NumberUtils::isCreatable, Util.getText(tfFontSize1), NumberUtils::createBigDecimal, null),
				Util.cast(ECSSUnit.class, getSelectedItem(cbmFontSize1)));
		//
	}

	private static Map<String, String> createStyleMap(final Map<String, String> map, final BigDecimal fontSize,
			final ECSSUnit ecssUnit) {
		//
		final Map<String, String> result = new LinkedHashMap<>(ObjectUtils.getIfNull(map, Collections::emptyMap));
		//
		testAndAccept((a, b) -> Boolean.logicalAnd(a != null, b != null), fontSize, ecssUnit,
				(a, b) -> Util.put(result, "font-size", new CSSSimpleValueWithUnit(a, b).getFormatted()));
		//
		return result;
		//
	}

	@Nullable
	private static String nextAlphabetic(@Nullable final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphabetic(count) : null;
	}

	@Nullable
	private static String toHtml(final String string) throws IOException {
		//
		final Collection<Token> tokens = testAndApply(x -> Boolean.logicalAnd(Objects.nonNull(x), isPlainText(x)),
				string, new Tokenizer()::tokenize, null);
		//
		if (Util.iterator(tokens) == null) {
			//
			return null;
			//
		} // if
			//
		HtmlBuilder<StringBuilder> htmlBuilder = null;
		//
		for (final Token token : tokens) {
			//
			toHtml(htmlBuilder = ObjectUtils.getIfNull(htmlBuilder, FlatHtml::inMemory), token);
			//
		} // for
			//
		return Util.toString(output(htmlBuilder));
		//
	}

	private static void toHtml(final HtmlBuilder<StringBuilder> htmlBuilder, final TokenBase token) throws IOException {
		//
		final String[] allFeatures = getAllFeaturesArray(token);
		//
		if (length(allFeatures) < 9) {
			//
			return;
			//
		} // if
			//
		final String surface = getSurface(token);
		//
		final String reading = ArrayUtils.get(allFeatures, 7);
		//
		final String convertKana = KanaConverter.convertKana(reading, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA);
		//
		if (Boolean.logicalOr(StringUtils.equals(surface, convertKana), Objects.equals(surface, reading))
				|| (surface != null && surface.matches("^\\d+$") && Objects.equals(reading, "*"))) {
			//
			appendUnescapedText(htmlBuilder, surface);
			//
			return;
			//
		} // if
			//
		final String lcs = longestCommonSubstring(surface, convertKana);
		//
		final String[] ss1 = StringUtils.split(surface, lcs);
		//
		final String[] ss2 = StringUtils.split(convertKana, lcs);
		//
		final int length1 = length(ss1);
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(lcs), length1 == length(ss2))) {
			//
			String commonPrefix, commonSuffix, s1, s2 = null;
			//
			for (int i = 0; i < length1; i++) {
				//
				testAndRun(
						Boolean.logicalOr(Boolean.logicalAnd(i == 1, length1 == 2),
								Objects.equals(commonPrefix = Strings.commonPrefix(surface, convertKana), lcs)),
						() -> appendUnescapedText(htmlBuilder, lcs), null);
				//
				completeTag(appendStartTag(completeTag(appendStartTag(htmlBuilder, "ruby")), "rb"));
				//
				if (StringUtils.isNotBlank(commonSuffix = Strings.commonSuffix(s1 = ArrayUtils.get(ss1, i),
						s2 = ArrayUtils.get(ss2, i)))) {
					//
					appendUnescapedText(htmlBuilder,
							StringUtils.substring(s1, 0, StringUtils.length(s1) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					appendUnescapedText(htmlBuilder, s1);
					//
				} // if
					//
				completeTag(
						appendStartTag(
								appendEndTag(appendUnescapedText(
										completeTag(appendStartTag(appendEndTag(htmlBuilder, "rb"), "rp")), "("), "rp"),
								"rt"));
				//
				if (StringUtils.isNotBlank(commonSuffix = Strings.commonSuffix(s1, s2))) {
					//
					appendUnescapedText(htmlBuilder,
							StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					appendUnescapedText(htmlBuilder, s2);
					//
				} // if
					//
				appendEndTag(
						appendEndTag(appendUnescapedText(
								completeTag(appendStartTag(appendEndTag(htmlBuilder, "rt"), "rp")), ")"), "rp"),
						"ruby");
				//
				testAndAccept((a, b) -> StringUtils.isNotBlank(b), htmlBuilder, commonSuffix,
						VoiceManagerPdfPanel::appendUnescapedText);
				//
				testAndRun(and(i == 0, length1 == 1, !Objects.equals(commonPrefix, lcs)),
						() -> appendUnescapedText(htmlBuilder, lcs), null);
				//
			} // for
				//
			return;
			//
		} // if
			//
		toHtml(htmlBuilder, surface, convertKana);
		//
	}

	private static void toHtml(final HtmlBuilder<StringBuilder> htmlBuilder, @Nullable final String text,
			final String ruby) throws IOException {
		//
		final String commonPrefix = testAndApply((a, b) -> a != null && b != null, text, ruby, Strings::commonPrefix,
				null);
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, commonPrefix);
			//
		} // if
			//
		completeTag(appendStartTag(completeTag(appendStartTag(htmlBuilder, "ruby")), "rb"));
		//
		final String commonSuffix = testAndApply((a, b) -> a != null && b != null, text, ruby, Strings::commonSuffix,
				null);
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, StringUtils.substringAfter(text, commonPrefix));
			//
		} else if (StringUtils.isNotBlank(commonSuffix)) {
			//
			appendUnescapedText(htmlBuilder,
					StringUtils.substring(text, 0, StringUtils.length(text) - StringUtils.length(commonSuffix)));
			//
		} else {
			//
			appendUnescapedText(htmlBuilder, text);
			//
		} // if
			//
		completeTag(appendStartTag(appendEndTag(
				appendUnescapedText(completeTag(appendStartTag(appendEndTag(htmlBuilder, "rb"), "rp")), "("), "rp"),
				"rt"));
		//
		if (StringUtils.isNotBlank(commonPrefix)) {
			//
			appendUnescapedText(htmlBuilder, StringUtils.substringAfter(ruby, commonPrefix));
			//
		} else if (StringUtils.isNotBlank(commonSuffix)) {
			//
			appendUnescapedText(htmlBuilder,
					StringUtils.substring(ruby, 0, StringUtils.length(ruby) - StringUtils.length(commonSuffix)));
			//
		} else {
			//
			appendUnescapedText(htmlBuilder, ruby);
			//
		} // if
			//
		appendEndTag(appendEndTag(
				appendUnescapedText(completeTag(appendStartTag(appendEndTag(htmlBuilder, "rt"), "rp")), ")"), "rp"),
				"ruby");
		//
		testAndAccept((a, b) -> StringUtils.isNotBlank(b), htmlBuilder, commonSuffix,
				VoiceManagerPdfPanel::appendUnescapedText);
		//
	}

	private static String longestCommonSubstring(final String a, final String b) {
		//
		int start = 0, max = 0;
		//
		for (int i = 0; i < StringUtils.length(a); i++) {
			//
			for (int j = 0; j < StringUtils.length(b); j++) {
				//
				int x = 0;
				//
				while (a.charAt(i + x) == b.charAt(j + x)) {
					//
					x++;
					//
					if (((i + x) >= a.length()) || ((j + x) >= b.length())) {
						//
						break;
						//
					} // if
						//
				} // while
					//
				if (x > max) {
					//
					max = x;
					//
					start = i;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return StringUtils.substring(a, start, start + max);
		//
	}

	@Nullable
	private static String getSurface(@Nullable final TokenBase instance) {
		return instance != null ? instance.getSurface() : null;
	}

	@Nullable
	private static String[] getAllFeaturesArray(@Nullable final TokenBase instance) {
		return instance != null ? instance.getAllFeaturesArray() : null;
	}

	private static boolean isPlainText(final String string) {
		//
		Element element = testAndApply(x -> IterableUtils.size(x) == 1,
				ElementUtil.children(testAndApply(Objects::nonNull, string, Jsoup::parse, null)),
				x -> IterableUtils.get(x, 0), null);
		//
		final List<Element> es = ElementUtil.children(element);
		//
		boolean plainText = true;
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(string), IterableUtils.size(es) == 2)) {
			//
			plainText &= and(ElementUtil.childrenSize(element = IterableUtils.get(es, 0)) == 0,
					attributesSize(element) == 0,
					//
					ArrayUtils.contains(new int[] { 0, 1 }, NodeUtil.childNodeSize(element = IterableUtils.get(es, 1))),
					attributesSize(element) == 0
					//
					,
					StringUtils.equals(TextNodeUtil.text(Util.cast(TextNode.class,
							testAndApply(x -> NodeUtil.childNodeSize(x) == 1, element, x -> childNode(x, 0), null))),
							string));
			//
		} // if
			//
		return plainText;
		//
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = Boolean.logicalAnd(a, b);
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			result &= bs[i];
			//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Node childNode(@Nullable final Node instance, final int index) {
		return instance != null ? instance.childNode(index) : null;
	}

	private static int attributesSize(@Nullable final Node instance) {
		return instance != null ? instance.attributesSize() : 0;
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
			@Nullable final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
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

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> ra,
			@Nullable final FailableRunnable<E> rb) throws E {
		if (b) {
			FailableRunnableUtil.run(ra);
		} else {
			FailableRunnableUtil.run(rb);
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
			@Nullable final String txt) throws IOException {
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
			final String html = Util.getText(taHtml);
			//
			setFontSizeAndUnit(html);
			//
			setEnabled(btnPreviewRubyPdf, StringUtils.isNotBlank(html));
			//
		} // if
			//
	}

	@Override
	public void removeUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), taHtmlDocument)) {
			//
			final String html = Util.getText(taHtml);
			//
			setFontSizeAndUnit(html);
			//
			setEnabled(btnPreviewRubyPdf, StringUtils.isNotBlank(html));
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

		private Document docuemnt = null;

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
			} else if (proxy instanceof DocumentEvent && Objects.equals(methodName, "getDocument")) {
				//
				return docuemnt;
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
			final BufferedImage bi = pdfRenderer.renderImage(0);
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
			final IntIntPair intIntPair = getMinimumAndMaximumY(bi);
			//
			final int largestY = rightInt(intIntPair, 0);
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
			Resource resource = ObjectMap.getObject(objectMap, Resource.class);
			//
			File fileAudio = null;
			//
			PDFontDescriptor pdFontDescriptor = null;
			//
			for (final Entry<Integer, String> entry : Util.entrySet(map)) {
				//
				if ((key = Util.getKey(entry)) == null) {
					//
					continue;
					//
				} // if
					//
				testAndAccept(x -> IValue0Util.getValue0(x) != null,
						Triplet.with(resource, fileAudio = Util.toFile(pathAudio), key),
						x -> FileUtils.writeByteArrayToFile(IValue1Util.getValue1(x),
								ResourceUtil.getContentAsByteArray(IValue0Util.getValue0(x))),
						x -> writeVoiceToFile(speechApi, text, voiceId, Util.intValue(IValue2Util.getValue2(x), 0),
								volume, IValue1Util.getValue1(x)));
				//
				resource = null;
				//
				duration = null;
				//
				try {
					//
					duration = getAudioDuration(fileAudio);
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
				try (final InputStream is = testAndApply(x -> {
					//
					final File f = Util.toFile(x);
					//
					return Boolean.logicalAnd(Util.exists(f), Util.isFile(f));
					//
				}, pathAudio, Files::newInputStream, null)) {
					//
					setSubtype(
							pdfEmbeddedFile = testAndApply(Objects::nonNull, is, x -> new PDEmbeddedFile(document, is),
									null),
							getMimeType(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), pathAudio));
					//
					testAndAccept((a, b) -> b != null, pdfEmbeddedFile, fileAudio,
							(a, b) -> setSize(a, Util.intValue(length(b), 0)));
					//
					(fileSpec = new PDComplexFileSpecification()).setFile(Util.getName(fileAudio));
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
					cs.newLineAtOffset((index - 1) * size + (size - getTextWidth(
							//
							value = testAndApply(x -> and(x, y -> matches(y), y -> groupCount(y) > 0),
									matcher(pattern = ObjectUtils.getIfNull(pattern,
											() -> Pattern.compile("^(\\d+%).+$")), value),
									x -> group(x, 1), x -> value_)
							//
							, font, fontSize)) / 2, lastHeight = (getHeight(md) - Util.intValue(largestY, 0) - size
					//
									- (getAscent(pdFontDescriptor = getFontDescriptor(font), 0) / 1000 * fontSize)
									+ (getDescent(pdFontDescriptor, 0) / 1000 * fontSize))
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
					cs.beginText();
					//
					cs.setFont(font, fontSize);
					//
					cs.newLineAtOffset(
							(index - 1) * size + (size - getTextWidth(
									value = String.format("%1$.2f s", toMillis(duration, 0) / 1000d), font, fontSize))
									/ 2,
							lastHeight = lastHeight
									- (getAscent(pdFontDescriptor = getFontDescriptor(font), 0) / 1000 * fontSize)
									+ (getDescent(pdFontDescriptor, 0) / 1000 * fontSize)
					//
					);
					cs.showText(value);
					//
					cs.endText();
					//
				} catch (final Exception e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} finally {
					//
					testAndAccept(x -> Util.exists(Util.toFile(x)), pathAudio, Files::delete);
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
	private static PDFontDescriptor getFontDescriptor(@Nullable final PDFont instance) {
		return instance != null ? instance.getFontDescriptor() : null;
	}

	private static float getAscent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getAscent() : defaultValue;
	}

	private static float getDescent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getDescent() : defaultValue;
	}

	private static long toMillis(@Nullable final Duration instance, final long defaultValue) {
		return instance != null ? instance.toMillis() : defaultValue;
	}

	private static void setSubtype(@Nullable final PDEmbeddedFile instance, final String mimeType) {
		if (instance != null) {
			instance.setSubtype(mimeType);
		}
	}

	private static int leftInt(@Nullable final IntIntPair instance, final int defaultValue) {
		return instance != null ? instance.leftInt() : defaultValue;
	}

	private static int rightInt(@Nullable final IntIntPair instance, final int defaultValue) {
		return instance != null ? instance.rightInt() : defaultValue;
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
			if (format != null) {
				//
				ImageIO.write(bi, format, Util.toFile(
						path = Path.of(String.join(".", StringUtils.defaultIfBlank(fileNamePrefix, PAGE1), format))));
				//
			} // if
				//
		} // if
			//
		return path;
		//
	}

	private static void convertAndwriteByteArrayToFile(@Nullable final ByteConverter byteConverter, final Path path) {
		//
		try {
			//
			testAndAccept((a, b) -> Util.exists(a) && Util.isFile(a) && b != null, Util.toFile(path),
					byteConverter != null ? byteConverter.convert(Files.readAllBytes(path)) : null,
					FileUtils::writeByteArrayToFile);
			//
		} catch (final IOException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
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
			final AbstractButton btnPreserveImage = voiceManagerPdfPanel != null ? voiceManagerPdfPanel.btnPreserveImage
					: null;
			//
			if (btnPreserveImage == null || !btnPreserveImage.isSelected()) {
				//
				actionPerformed(voiceManagerPdfPanel,
						testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, voiceManagerPdfPanel,
								x -> Narcissus.getField(voiceManagerPdfPanel,
										Narcissus.findField(VoiceManagerPdfPanel.class, "btnImageClear")),
								null), x -> new ActionEvent(x, 0, null), null));
				//
			} // if
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
			@Nullable final T t, @Nullable final U u) throws E {
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

	private static <T, U, E extends Exception> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
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
		try (final InputStream is = testAndApply(p -> Util.exists(Util.toFile(p)) && Util.isFile(Util.toFile(p)), path,
				Files::newInputStream, null)) {
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, is, x -> findMatch(instance, x), null);
			//
			return getMimeType(ci);
			//
		} // try
			//
	}

	@Nullable
	private static ContentInfo findMatch(@Nullable final ContentInfoUtil instance, final InputStream is)
			throws IOException {
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
	private static IntIntPair getMinimumAndMaximumY(@Nullable final BufferedImage bi) {
		//
		Color color = null;
		//
		IntIntPair intIntPair = null;
		//
		final List<IntIntPair> intIntPairs = bi != null ? toIntIntPairList(bi.getWidth(), bi.getHeight()) : null;
		//
		IntIntPair temp = null;
		//
		int y;
		//
		for (int i = 0; i < IterableUtils.size(intIntPairs); i++) {
			//
			if ((temp = IterableUtils.get(intIntPairs, i)) == null || color == null) {
				//
				color = color == null && bi != null ? new Color(bi.getRGB(leftInt(temp, 0), rightInt(temp, 0))) : null;
				//
				continue;
				//
			} // if
				//
			if (!Objects.equals(color, new Color(bi.getRGB(leftInt(temp, 0), y = rightInt(temp, 0))))) {
				//
				leftOrRight(intIntPair = ObjectUtils.getIfNull(intIntPair, () -> IntIntMutablePair.of(-1, -1)), y);
				//
			} // if
				//
		} // for
			//
		return intIntPair;
		//
	}

	@Nullable
	private static IntIntPair getMinimumAndMaximumY(@Nullable final BufferedImage bi, final Color c) {
		//
		Color color = c;
		//
		IntIntPair intIntPair = null;
		//
		final List<IntIntPair> intIntPairs = bi != null ? toIntIntPairList(bi.getWidth(), bi.getHeight()) : null;
		//
		IntIntPair temp = null;
		//
		int y;
		//
		for (int i = 0; i < IterableUtils.size(intIntPairs); i++) {
			//
			if ((temp = IterableUtils.get(intIntPairs, i)) == null || color == null) {
				//
				color = color == null && bi != null ? new Color(bi.getRGB(leftInt(temp, 0), rightInt(temp, 0))) : null;
				//
				continue;
				//
			} // if
				//
			if (bi != null && Objects.equals(color, new Color(bi.getRGB(leftInt(temp, 0), y = rightInt(temp, 0))))) {
				//
				leftOrRight(intIntPair = ObjectUtils.getIfNull(intIntPair, () -> IntIntMutablePair.of(-1, -1)), y);
				//
			} // if
				//
		} // for
			//
		return intIntPair;
		//
	}

	@Nullable
	private static IntIntPair leftOrRight(@Nullable final IntIntPair intIntPair, final int y) {
		//
		IntIntPair result = intIntPair;
		//
		if (leftInt(result, 0) < 0 && (result = left(result, y)) != null) {
			//
			result = right(result, y);
			//
		} else if (y < leftInt(result, 0)) {
			//
			result = left(result, y);
			//
		} else if (y > rightInt(result, 0)) {
			//
			result = right(result, y);
			//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static IntIntPair left(@Nullable final IntIntPair intIntPair, final int y) {
		return intIntPair != null ? intIntPair.left(y) : intIntPair;
	}

	@Nullable
	private static IntIntPair right(@Nullable final IntIntPair intIntPair, final int y) {
		return intIntPair != null ? intIntPair.right(y) : intIntPair;
	}

	@Nullable
	private static List<IntIntPair> toIntIntPairList(final int a, final int b) {
		//
		List<IntIntPair> list = null;
		//
		for (int i = 0; i < a; i++) {
			//
			for (int j = 0; j < b; j++) {
				//
				Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), IntIntPair.of(i, j));
				//
			} // for
				//
		} // for
			//
		return list;
		//
	}

	private static void setSubject(@Nullable final PDDocumentInformation instance, @Nullable final String keywords) {
		if (instance != null) {
			instance.setSubject(keywords);
		}
	}

	private static Duration getAudioDuration(@Nullable final File file) throws Exception {
		//
		final AudioFileFormat fileFormat = testAndApply(f -> Util.exists(f) && Util.isFile(f), file,
				AudioSystem::getAudioFileFormat, null);
		//
		return testAndApply((a, b) -> a != null && b != null, fileFormat, getFormat(fileFormat),
				(a, b) -> Duration.parse(String.format("PT%1$sS", a.getFrameLength() / b.getFrameRate())), null);
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			@Nullable final U u, final FailableBiFunction<T, U, R, E> functionTrue,
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
	private static Matcher matcher(@Nullable final Pattern instance, @Nullable final CharSequence input) {
		return instance != null && input != null ? instance.matcher(input) : null;
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static byte[] pdf(@Nullable final Path pathHtml,
			final FailableFunction<Playwright, BrowserType, ReflectiveOperationException> function)
			throws MalformedURLException, ReflectiveOperationException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(FailableFunctionUtil.apply(function, playwright))));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(Util.toURI(Util.toFile(pathHtml)))),
						page::navigate);
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
	private static byte[] screenshot(final Path pathHtml,
			final FailableFunction<Playwright, BrowserType, ReflectiveOperationException> function)
			throws MalformedURLException, ReflectiveOperationException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(FailableFunctionUtil.apply(function, playwright))));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(Util.toURI(Util.toFile(pathHtml)))),
						page::navigate);
				//
				return page.screenshot();
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
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumerTrue, final FailableConsumer<T, E> consumerFalse) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumerTrue, value);
		} else {
			FailableConsumerUtil.accept(consumerFalse, value);
		}
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
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
			width += getWidth(font, text.charAt(i), 0) / 1000.0f;
		}
		return width * fontSize;
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
		try {
			//
			if (or(Util.contains(Arrays.asList("org.apache.pdfbox.pdmodel.font.PDMMType1Font",
					"org.apache.pdfbox.pdmodel.font.PDTrueTypeFont", "org.apache.pdfbox.pdmodel.font.PDType1CFont",
					"org.apache.pdfbox.pdmodel.font.PDType1Font"), Util.getName(Util.getClass(instance)))
					&& FieldUtils.readField(instance, "codeToWidthMap", true) == null,
					Objects.equals("org.apache.pdfbox.pdmodel.font.PDType0Font", Util.getName(Util.getClass(instance)))
							&& FieldUtils.readDeclaredField(instance, "descendantFont", true) == null,
					Objects.equals("org.apache.pdfbox.pdmodel.font.PDType3Font", Util.getName(Util.getClass(instance)))
							&& FieldUtils.readField(instance, "dict", true) == null)) {
				//
				return defaultValue;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.getWidth(code);
		//
	}

	private static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		if (a || b) {
			//
			return true;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (bs[i]) {
				//
				return true;
				//
			} // if
				//
		} // for
			//
		return false;
		//
	}

}