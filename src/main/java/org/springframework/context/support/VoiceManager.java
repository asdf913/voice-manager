package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.BIPUSH;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEWARRAY;
import org.apache.bcel.generic.ObjectType;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.BeanFactoryUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionUtil;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Functions;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Range;
import com.google.common.collect.RangeUtil;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;

import de.sciss.jump3r.lowlevel.LameEncoder;
import de.sciss.jump3r.mp3.Lame;
import domain.JlptVocabulary;
import domain.Pronunciation;
import domain.Voice;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ConfigurationUtil;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.ContainerTag;
import j2html.tags.ContainerTagUtil;
import j2html.tags.Tag;
import j2html.tags.TagUtil;
import j2html.tags.specialized.ATag;
import j2html.tags.specialized.ATagUtil;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.javaflacencoder.AudioStreamEncoder;
import net.sourceforge.javaflacencoder.FLACEncoder;
import net.sourceforge.javaflacencoder.FLACStreamOutputStream;
import net.sourceforge.javaflacencoder.StreamConfiguration;

@Title("Voice Manager")
public class VoiceManager extends JFrame implements ActionListener, ItemListener, ChangeListener, KeyListener,
		EnvironmentAware, BeanFactoryPostProcessor, InitializingBean, DocumentListener, ApplicationContextAware {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static final String HANDLER = "handler";

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String FORMAT = "format";

	private static final String WARNING = "Warning";

	private static final String VALUE = "value";

	private static final String LANGUAGE = "Language";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String OLE_2_COMPOUND_DOCUMENT = "OLE 2 Compound Document";

	private static final String COMPONENT = "component";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see java.lang.Class#getResourceAsStream(java.lang.String)
	 */
	private static final String CLASS_RESOURCE_FORMAT = "/%1$s.class";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L534">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String SPAN_ONLY_FORMAT = "span %1$s";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L780">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String WMIN_ONLY_FORMAT = "wmin %1$s";

	private static final FailablePredicate<File, RuntimeException> EMPTY_FILE_PREDICATE = f -> f != null && f.exists()
			&& isFile(f) && longValue(length(f), 0) == 0;

	private static IValue0<Method> METHOD_RANDOM_ALPHABETIC = null;

	private transient ApplicationContext applicationContext = null;

	private transient PropertyResolver propertyResolver = null;

	static VoiceManager INSTANCE = null;

	@Note("Folder")
	private JTextComponent tfFolder = null;

	@Note("File Length")
	private JTextComponent tfFileLength = null;

	@Note("File Digest")
	private JTextComponent tfFileDigest = null;

	@Note("Text in Import Pnael")
	private JTextComponent tfTextImport = null;

	@Note("Speech Rate")
	private JTextComponent tfSpeechRate = null;

	@Note("Current Processing Sheet")
	private JTextComponent tfCurrentProcessingSheetName = null;

	@Note("Current Processing Voice")
	private JTextComponent tfCurrentProcessingVoice = null;

	@Note("List Name(s)")
	private JTextComponent tfListNames = null;

	@Note("DLL Path")
	private JTextComponent tfDllPath = null;

	@Note("File")
	private JTextComponent tfFile = null;

	@Note("Export File")
	private JTextComponent tfExportFile = null;

	private transient ComboBoxModel<String> cbmGaKuNenBeTsuKanJi = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Url("https://www.jlpt.jp/about/levelsummary.html")
	private transient ComboBoxModel<String> cbmJlptLevel = null;

	@Note("For Write Voice in TTS Panel")
	private transient ComboBoxModel<?> cbmAudioFormatWrite = null;

	@Note("For Import Panel")
	private transient ComboBoxModel<?> cbmAudioFormatExecute = null;

	@Url("https://ja.wikipedia.org/wiki/%E5%B8%B8%E7%94%A8%E6%BC%A2%E5%AD%97%E4%B8%80%E8%A6%A7")
	private transient ComboBoxModel<Boolean> cbmJouYouKanJi = null;

	private transient MutableComboBoxModel<JlptVocabulary> mcbmJlptVocabulary = null;

	private transient MutableComboBoxModel<Pronunciation> mcbmPronunciation = null;

	private transient javax.swing.text.Document tfTextImportDocument = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Note("Over MP3 Title")
	private AbstractButton cbOverMp3Title = null;

	@Note("Ordinal Position As File Name Prefix")
	private AbstractButton cbOrdinalPositionAsFileNamePrefix = null;

	@Note("Export HTML")
	private AbstractButton cbExportHtml = null;

	@Note("Export List As HTML")
	private AbstractButton cbExportListHtml = null;

	@Note("Export Web Speech Synthesis HTML")
	private AbstractButton cbExportWebSpeechSynthesisHtml = null;

	@Note("Export HTML as ZIP")
	private AbstractButton cbExportHtmlAsZip = null;

	@Note("Remove Exported HTML After Zip")
	private AbstractButton cbExportHtmlRemoveAfterZip = null;

	@Note("Export List Sheet")
	private AbstractButton cbExportListSheet = null;

	@Note("Export JLPT Sheet")
	private AbstractButton cbExportJlptSheet = null;

	@Note("Export Presentation")
	private AbstractButton cbExportPresentation = null;

	@Note("Embed Audio In Presentation")
	private AbstractButton cbEmbedAudioInPresentation = null;

	@Note("Hide Audio Image In Presentation")
	private AbstractButton cbHideAudioImageInPresentation = null;

	@Note("Export Microsoft Access")
	private AbstractButton cbExportMicrosoftAccess = null;

	@Note("Generate a Blank Row in Import File Template")
	private AbstractButton cbImportFileTemplateGenerateBlankRow = null;

	@Note("JLPT As Folder")
	private AbstractButton cbJlptAsFolder = null;

	@Note("Browse Button For Export Function")
	private AbstractButton btnExportBrowse = null;

	@Note("TTS Voice")
	private AbstractButton cbUseTtsVoice = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface SystemClipboard {
	}

	@SystemClipboard
	private AbstractButton btnExportCopy = null;

	@SystemClipboard
	private AbstractButton btnDllPathCopy = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ExportButton {
	}

	@ExportButton
	private AbstractButton btnExportMicrosoftSpeechObjectLibraryInformation = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Progress Bar For Import")
	private JProgressBar progressBarImport = null;

	@Note("Progress Bar For Export")
	private JProgressBar progressBarExport = null;

	@Note("Slider For Speech Rate")
	private JSlider jsSpeechRate = null;

	private JComboBox<JlptVocabulary> jcbJlptVocabulary = null;

	@Note("List Name(s)")
	private JLabel jlListNames = null;

	@Note("List Count")
	private JLabel jlListNameCount = null;

	@Note("Import Exception")
	private DefaultTableModel tmImportException = null;

	@Note("Import Result")
	private DefaultTableModel tmImportResult = null;

	private String voiceFolder = null;

	@Nullable
	private Map<String, String> outputFolderFileNameExpressions = null;

	private transient SpeechApi speechApi = null;

	@Note("MP3 Tag(s)")
	@Nullable
	private String[] mp3Tags = null;

	@Nullable
	private String[] microsoftSpeechObjectLibraryAttributeNames = null;

	@Nullable
	private String[] tabOrders = null;

	private transient Toolkit toolkit = null;

	private ObjectMapper objectMapper = null;

	@Url("https://ja.wikipedia.org/wiki/%E5%AD%A6%E5%B9%B4%E5%88%A5%E6%BC%A2%E5%AD%97%E9%85%8D%E5%BD%93%E8%A1%A8")
	private String gaKuNenBeTsuKanJiListPageUrl = null;

	private String microsoftSpeechPlatformRuntimeDownloadPageUrl = null;

	@Url("https://support.microsoft.com/en-us/windows/make-older-apps-or-programs-compatible-with-windows-10-783d6dd7-b439-bdb0-0490-54eea0f45938")
	private String microsoftWindowsCompatibilitySettingsPageUrl = null;

	@Url("https://www.microsoft.com/en-us/download/details.aspx?id=27224")
	private String microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl = null;

	@Url("https://help.libreoffice.org/latest/en-US/text/shared/01/moviesound.html")
	private String mediaFormatPageUrl = null;

	@Url("https://poi.apache.org/encryption.html")
	private String poiEncryptionPageUrl = null;

	private transient LayoutManager layoutManager = null;

	private transient IValue0<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	@Nullable
	private transient IValue0<String> microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle = null;

	@Note("Export HTML Template File")
	private String exportHtmlTemplateFile = null;

	@Note("Export Web Speech Synthesis HTML Template File")
	private String exportWebSpeechSynthesisHtmlTemplateFile = null;

	private transient freemarker.template.Configuration freeMarkerConfiguration = null;

	private Version freeMarkerVersion = null;

	private String[] voiceIds = null;

	private transient IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> workbookClassFailableSupplierMap = null;

	@Nullable
	private Class<?> workbookClass = null;

	private transient Map<Object, Object> exportWebSpeechSynthesisHtmlTemplateProperties = null;

	@Nullable
	private Duration jSoupParseTimeout = null;

	private transient IValue0<List<String>> jouYouKanJiList = null;

	private transient IValue0<List<JlptVocabulary>> jlptVocabularyList = null;

	@Nullable
	private transient Collection<Multimap> multimapHiragana = null;

	@Nullable
	@Note("Katakana")
	private transient Collection<Multimap> multimapKatakana = null;

	@Nullable
	private transient Collection<Map> mapHiragana = null;

	@Nullable
	private transient Collection<Map> mapRomaji = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		//
		// If the
		// "org.springframework.context.support.VoiceManager.getDefaultCloseOperation(java.lang.Iterable)"
		// return a "java.lang.Number" instance, pass the value of
		// "java.lang.Number.intValue()" of
		// the "java.lang.Number" instance to the
		// "javax.swing.JFrame.setDefaultCloseOperation(int)" method
		//
		final Iterable<?> bpps = Util.values(
				ListableBeanFactoryUtil.getBeansOfType(configurableListableBeanFactory, BeanPostProcessor.class));
		//
		final Number defaultCloseOperation = getDefaultCloseOperation(bpps);
		//
		if (defaultCloseOperation != null) {
			//
			setDefaultCloseOperation(defaultCloseOperation.intValue());
			//
		} // if
			//
		if (Util.iterator(bpps) != null) {
			//
			Collection<Method> ms = null;
			//
			for (final Object obj : bpps) {
				//
				addAll(ms = ObjectUtils.getIfNull(ms, ArrayList::new), Util.toList(Util.filter(
						testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(obj)), Arrays::stream,
								null),
						m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "setTitle"), Arrays.equals(
								getParameterTypes(m), new Class<?>[] { Frame.class, PropertyResolver.class })))));
				//
			} // for
				//
			final int size = IterableUtils.size(ms);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Method m = testAndApply(x -> size == 1, ms, x -> IterableUtils.get(x, 0), null);
			//
			setAccessible(m, true);
			//
			if (m != null) {
				//
				final boolean headless = GraphicsEnvironment.isHeadless();
				//
				try {
					//
					m.invoke(null, this, propertyResolver);
					//
				} catch (final IllegalAccessException e) {
					//
					errorOrAssertOrShowException(headless, e);
					//
				} catch (final InvocationTargetException e) {
					//
					final Throwable targetException = e.getTargetException();
					//
					errorOrAssertOrShowException(headless,
							ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
									ExceptionUtils.getRootCause(e), e));
					//
				} // try
					//
			} // if
				//
		} // if
			//
		final Collection<?> formats = getByteConverterAttributeValues(configurableListableBeanFactory, FORMAT);
		//
		// cbmAudioFormatWrite
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatWrite = Util.cast(MutableComboBoxModel.class,
				cbmAudioFormatWrite);
		//
		addElement(mcbmAudioFormatWrite, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
		//
		// cbmAudioFormatExecute
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatExecute = Util.cast(MutableComboBoxModel.class,
				cbmAudioFormatExecute);
		//
		addElement(mcbmAudioFormatExecute, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatExecute, x));
		//
		final String audioFormat = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.audioFormat");
		//
		setSelectedItem(cbmAudioFormatWrite, audioFormat);
		//
		setSelectedItem(cbmAudioFormatExecute, audioFormat);
		//
		// Get the "Bean Definition" which class could be assigned as a
		// "com.google.common.collect.Multimap" and the "Bean Definition" has "value"
		// attribute which value is "hiragana"
		//
		multimapHiragana = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Multimap.class,
						Collections.singletonMap(VALUE, "hiragana"))),
				x -> Util.cast(Multimap.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
		//
		// Get the "Bean Definition" which class could be assigned as a "java.util.Map"
		// and the "Bean Definition" has "value" attribute which value
		// is "hiragana"
		//
		mapHiragana = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Map.class,
						Collections.singletonMap(VALUE, "hiragana"))),
				x -> Util.cast(Map.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
		//
		// Get the "Bean Definition" which class could be assigned as a
		// "com.google.common.collect.Multimap" and the "Bean Definition" has "value"
		// attribute which value is "katakana"
		//
		multimapKatakana = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Multimap.class,
						Collections.singletonMap(VALUE, "katakana"))),
				x -> Util.cast(Multimap.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
		//
		// Get the "Bean Definition" which class could be assigned as a "java.util.Map"
		// and the "Bean Definition" has "value" attribute which value
		// is "romaji"
		//
		mapRomaji = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Map.class,
						Collections.singletonMap(VALUE, "romaji"))),
				x -> Util.cast(Map.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
		//
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	@Nullable
	private static List<String> getBeanDefinitionNamesByClassAndAttributes(
			final ConfigurableListableBeanFactory instnace, final Class<?> classToBeFound, final Map<?, ?> attributes) {
		//
		List<String> multimapBeanDefinitionNames = null;
		//
		final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(instnace);
		//
		BeanDefinition bd = null;
		//
		Class<?> clz = null;
		//
		FactoryBean<?> fb = null;
		//
		String beanDefinitionName = null;
		//
		for (int i = 0; beanDefinitionNames != null && i < beanDefinitionNames.length; i++) {
			//
			if ((bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(instnace,
					beanDefinitionName = beanDefinitionNames[i])) == null) {
				//
				continue;
				//
			} // if
				//
			if (((Util.isAssignableFrom(FactoryBean.class, clz = Util.forName(BeanDefinitionUtil.getBeanClassName(bd)))
					&& (fb = Util.cast(FactoryBean.class, Narcissus.allocateInstance(clz))) != null
					&& Util.isAssignableFrom(classToBeFound, FactoryBeanUtil.getObjectType(fb)))
					|| Util.isAssignableFrom(classToBeFound, clz)) && isAllAttributesMatched(attributes, bd)) {
				//
				Util.add(multimapBeanDefinitionNames = ObjectUtils.getIfNull(multimapBeanDefinitionNames,
						ArrayList::new), beanDefinitionName);
				//
			} // if
				//
		} // for
			//
		return multimapBeanDefinitionNames;
		//
	}

	private static boolean isAllAttributesMatched(@Nullable final Map<?, ?> attributes,
			@Nullable final AttributeAccessor aa) {
		//
		if (Util.iterator(Util.entrySet(attributes)) != null) {
			//
			for (final Entry<?, ?> entry : Util.entrySet(attributes)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				if (aa != null && (!aa.hasAttribute(Util.toString(Util.getKey(entry)))
						|| !Objects.equals(aa.getAttribute(Util.toString(Util.getKey(entry))), Util.getValue(entry)))) {
					//
					return false;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return true;
		//
	}

	private static <E> void addAll(@Nullable final Collection<E> a, @Nullable final Collection<? extends E> b) {
		if (a != null && (b != null || Proxy.isProxyClass(Util.getClass(a)))) {
			a.addAll(b);
		}
	}

	/**
	 * If there is a object in "values" with "defaultCloseOperation" field and the
	 * field could be cast as a "java.lang.Number" instance, return the field value
	 */
	@Nullable
	private static Number getDefaultCloseOperation(@Nullable final Iterable<?> values) {
		//
		if (Util.iterator(values) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<Number> result = null;
		//
		for (final Object obj : values) {
			//
			if (result == null) {
				//
				result = getNumber(obj,
						Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(obj)),
										Arrays::stream, null),
								x -> Objects.equals(Util.getName(x), "defaultCloseOperation"))));
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return IValue0Util.getValue0(result);
		//
	}

	@Nullable
	private static IValue0<Number> getNumber(@Nullable final Object instance, @Nullable final Iterable<Field> fs) {
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		IValue0<Number> result = null;
		//
		final Field f = size == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null) {
			//
			final Number number = Util.cast(Number.class,
					testAndApply(VoiceManager::isStatic, f, Narcissus::getStaticField,
							a -> testAndApply(Objects::nonNull, instance, b -> Narcissus.getField(b, f), null)));
			//
			if (number != null || Util.isAssignableFrom(Number.class, Util.getType(f))) {
				//
				result = Unit.with(number);
				//
			} // if
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, @Nullable final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	@Override
	public void afterPropertiesSet() {
		//
		try {
			//
			init();
			//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	@Override
	public void setLayout(final LayoutManager layoutManager) {
		///
		super.setLayout(this.layoutManager = layoutManager);
		//
	}

	@Nullable
	private static Long length(@Nullable final File instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(File.class, "path")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return Long.valueOf(instance.length());
		//
	}

	@Nullable
	private static Integer getTempFileMinimumPrefixLength() {
		//
		Integer result = null;
		//
		final Class<?> clz = File.class;
		//
		try (final InputStream is = getResourceAsStream(clz,
				String.format(CLASS_RESOURCE_FORMAT, StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			final Object[] objectTypes = toArray(Util
					.map(Stream.of("java.lang.String", "java.lang.String", "java.io.File"), ObjectType::getInstance));
			//
			final List<org.apache.bcel.classfile.Method> ms = Util
					.toList(Util.filter(
							testAndApply(Objects::nonNull,
									JavaClassUtil.getMethods(ClassParserUtil.parse(
											testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null))),
									Arrays::stream, null),
							m -> m != null && Objects.equals(FieldOrMethodUtil.getName(m), "createTempFile")
									&& Objects.deepEquals(m.getArgumentTypes(), objectTypes)));
			//
			if (ms != null && !ms.isEmpty()) {
				//
				if (IterableUtils.size(ms) > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				result = getTempFileMinimumPrefixLength(get(ms, 0));
				//
			} // if
				//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return result;
		//
	}

	@Nullable
	private static Object[] toArray(@Nullable final Stream<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	private static RuntimeException toRuntimeException(final Throwable instance) {
		//
		if (instance instanceof RuntimeException re) {
			//
			return re;
			//
		} else if (instance instanceof Throwable) {
			//
			return new RuntimeException(instance);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Integer getTempFileMinimumPrefixLength(final org.apache.bcel.classfile.Method method) {
		//
		return getTempFileMinimumPrefixLength(InstructionListUtil.getInstructions(MethodGenUtil
				.getInstructionList(testAndApply(Objects::nonNull, method, x -> new MethodGen(x, null, null), null))));
		//
	}

	@Nullable
	private static Integer getTempFileMinimumPrefixLength(@Nullable final Instruction[] instructions) {
		//
		Integer result = null;
		//
		ICONST iconst = null;
		//
		Number value = null;
		//
		for (int i = 0; instructions != null && i < instructions.length; i++) {
			//
			if ((iconst = Util.cast(ICONST.class, instructions[i])) != null && i < instructions.length - 1
					&& instructions[i + 1] instanceof IF_ICMPGE && (value = iconst.getValue()) != null) {
				//
				result = Integer.valueOf(value.intValue());
				//
				break;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static InputStream getResourceAsStream(@Nullable final Class<?> instance, @Nullable final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance,
			@Nullable final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setOutputFolderFileNameExpressions(@Nullable final Object value) throws JsonProcessingException {
		//
		if (value == null) {
			//
			this.outputFolderFileNameExpressions = null;
			//
			return;
			//
		} // if
			//
		final Map<?, ?> map = Util.cast(Map.class, value);
		//
		if (Util.entrySet(map) != null) {
			//
			for (final Entry<?, ?> entry : Util.entrySet(map)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				Util.put(
						outputFolderFileNameExpressions = ObjectUtils.getIfNull(outputFolderFileNameExpressions,
								LinkedHashMap::new),
						Util.toString(Util.getKey(entry)), Util.toString(Util.getValue(entry)));
				//
			} // for
				//
			outputFolderFileNameExpressions = ObjectUtils.getIfNull(outputFolderFileNameExpressions,
					Collections::emptyMap);
			//
			return;
			//
		} // if
			//
		final Object object = testAndApply(StringUtils::isNotEmpty, Util.toString(value),
				x -> ObjectMapperUtil.readValue(getObjectMapper(), x, Object.class), null);
		//
		if (object instanceof Map || object == null) {
			setOutputFolderFileNameExpressions(object);
		} else {
			throw new IllegalArgumentException(Util.toString(Util.getClass(object)));
		} // if
			//
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@SuppressWarnings("java:S1612")
	public void setMp3Tags(final Object value) {
		//
		mp3Tags = toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	@SuppressWarnings("java:S1612")
	public void setMicrosoftSpeechObjectLibraryAttributeNames(final Object value) {
		//
		this.microsoftSpeechObjectLibraryAttributeNames = toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setTabOrders(final Object value) {
		//
		this.tabOrders = toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setGaKuNenBeTsuKanJiListPageUrl(final String gaKuNenBeTsuKanJiListPageUrl) {
		this.gaKuNenBeTsuKanJiListPageUrl = gaKuNenBeTsuKanJiListPageUrl;
	}

	public void setMicrosoftSpeechPlatformRuntimeDownloadPageUrl(
			final String microsoftSpeechPlatformRuntimeDownloadPageUrl) {
		this.microsoftSpeechPlatformRuntimeDownloadPageUrl = microsoftSpeechPlatformRuntimeDownloadPageUrl;
	}

	public void setMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageUrl(
			final String microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl) {
		this.microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl = microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl;
	}

	public void setMicrosoftWindowsCompatibilitySettingsPageUrl(
			final String microsoftWindowsCompatibilitySettingsPageUrl) {
		this.microsoftWindowsCompatibilitySettingsPageUrl = microsoftWindowsCompatibilitySettingsPageUrl;
	}

	public void setMediaFormatPageUrl(final String mediaFormatPageUrl) {
		this.mediaFormatPageUrl = mediaFormatPageUrl;
	}

	public void setPoiEncryptionPageUrl(final String poiEncryptionPageUrl) {
		this.poiEncryptionPageUrl = poiEncryptionPageUrl;
	}

	public void setExportHtmlTemplateFile(final String exportHtmlTemplateFile) {
		this.exportHtmlTemplateFile = exportHtmlTemplateFile;
	}

	public void setExportWebSpeechSynthesisHtmlTemplateFile(final String exportWebSpeechSynthesisHtmlTemplateFile) {
		this.exportWebSpeechSynthesisHtmlTemplateFile = exportWebSpeechSynthesisHtmlTemplateFile;
	}

	public void setExportWebSpeechSynthesisHtmlTemplateProperties(@Nullable final Object arg)
			throws JsonProcessingException {
		//
		if (arg == null || arg instanceof Map) {
			//
			final Map<?, ?> map = (Map<?, ?>) arg;
			//
			if (Util.iterator(Util.entrySet(map)) != null) {
				//
				for (final Entry<?, ?> entry : Util.entrySet(map)) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					Util.put(
							exportWebSpeechSynthesisHtmlTemplateProperties = ObjectUtils
									.getIfNull(exportWebSpeechSynthesisHtmlTemplateProperties, LinkedHashMap::new),
							Util.getKey(entry), Util.getValue(entry));
					//
				} // for
					//
			} // if
				//
			if (Boolean.logicalAnd(Boolean.logicalAnd(map != null, MapUtils.isEmpty(map)),
					exportWebSpeechSynthesisHtmlTemplateProperties == null)) {
				//
				exportWebSpeechSynthesisHtmlTemplateProperties = new LinkedHashMap<>();
				//
			} // if
				//
		} else if (arg instanceof CharSequence) {
			//
			setExportWebSpeechSynthesisHtmlTemplateProperties(
					ObjectMapperUtil.readValue(getObjectMapper(), Util.toString(arg), Object.class));
			//
		} else {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
	}

	public void setFreeMarkerVersion(final Object value) {
		//
		if (value instanceof Version version) {
			//
			this.freeMarkerVersion = version;
			//
		} else if (value instanceof CharSequence) {
			//
			this.freeMarkerVersion = new Version(Util.toString(value));
			//
		} else if (value instanceof Number number) {
			//
			this.freeMarkerVersion = new Version(intValue(number, 0));
			//
		} // if
			//
		final int[] ints = Util.cast(int[].class, value);
		//
		if (ints != null) {
			//
			if (ints.length != 3) {
				//
				throw new IllegalArgumentException();
				//
			} else {
				//
				this.freeMarkerVersion = new Version(ints[0], ints[1], ints[2]);
				//
			} // if
				//
		} // if
			//
	}

	public void setFreeMarkerConfiguration(final freemarker.template.Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> getWorkbookClassFailableSupplierMap() {
		//
		if (workbookClassFailableSupplierMap == null) {
			//
			workbookClassFailableSupplierMap = Unit.with(Util.collect(
					Util.stream(new Reflections("org.apache.poi").getSubTypesOf(Workbook.class)),
					Collectors.toMap(Functions.identity(), x -> new FailableSupplier<Workbook, RuntimeException>() {

						@Override
						@Nullable
						public Workbook get() throws RuntimeException {
							try {
								//
								return Util.cast(Workbook.class, newInstance(getDeclaredConstructor(x)));
								//
							} catch (final NoSuchMethodException | InstantiationException | IllegalAccessException
									| InvocationTargetException e) {
								//
								throw ObjectUtils.getIfNull(toRuntimeException(e), RuntimeException::new);
								//
							} // try
						}

					})));
			//
		} // if
			//
		return workbookClassFailableSupplierMap;
		//
	}

	public void setWorkbookClass(final Object object) {
		//
		if (object instanceof Class<?>) {
			//
			workbookClass = (Class<?>) object;
			//
			return;
			//
		} // if
			//
		final String toString = Util.toString(object);
		//
		if ((workbookClass = Util.forName(toString)) != null) {
			//
			return;
			//
		} // if
			//
		final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map = IValue0Util
				.getValue0(getWorkbookClassFailableSupplierMap());
		//
		final List<Class<? extends Workbook>> classes = Util.toList(Util.filter(Util.stream(Util.keySet(map)),
				x -> Boolean.logicalOr(Objects.equals(Util.getName(x), toString),
						StringUtils.endsWithIgnoreCase(Util.getName(x), toString))));
		//
		final int size = IterableUtils.size(classes);
		//
		if (size == 1) {
			//
			workbookClass = get(classes, 0);
			//
			return;
			//
		} else if (size > 1) {
			//
			final IValue0<Class<? extends Workbook>> iValue0 = getWorkbookClass(map, "xlsx");
			//
			if (iValue0 != null) {
				//
				workbookClass = IValue0Util.getValue0(iValue0);
				//
				return;
				//
			} // if
				//
			throw new IllegalArgumentException();
			//
		} // if
			//
		final IValue0<Class<? extends Workbook>> iValue0 = getWorkbookClass(map, toString);
		//
		if (iValue0 != null) {
			//
			workbookClass = IValue0Util.getValue0(iValue0);
			//
		} // if
			//
	}

	public void setjSoupParseTimeout(@Nullable final Object object) {
		//
		if (object == null) {
			//
			this.jSoupParseTimeout = null;
			//
			return;
			//
		} else if (object instanceof Duration duration) {
			//
			this.jSoupParseTimeout = duration;
			//
			return;
			//
		} else if (object instanceof Number number) {
			//
			this.jSoupParseTimeout = Duration.ofMillis(longValue(number, 0));
			//
			return;
			//
		} // if
			//
		final String string = Util.toString(object);
		//
		final Integer integer = valueOf(string);
		//
		if (integer != null) {
			//
			setjSoupParseTimeout(integer);
			//
			return;
			//
		} // if
			//
		this.jSoupParseTimeout = testAndApply(StringUtils::isNotBlank, string, Duration::parse, null);
		//
	}

	public void setGaKuNenBeTsuKanJiMultimap(final Multimap<String, String> gaKuNenBeTsuKanJiMultimap) {
		this.gaKuNenBeTsuKanJiMultimap = Unit.with(gaKuNenBeTsuKanJiMultimap);
	}

	public void setJouYouKanJiList(final List<String> jouYouKanJiList) {
		this.jouYouKanJiList = Unit.with(jouYouKanJiList);
	}

	public void setJlptVocabularyList(final List<JlptVocabulary> jlptVocabularyList) {
		this.jlptVocabularyList = Unit.with(jlptVocabularyList);
	}

	@Nullable
	private static IValue0<Duration> toDurationIvalue0(@Nullable final Object object) {
		//
		IValue0<Duration> value = null;
		//
		if (object == null) {
			//
			value = Unit.with(null);
			//
		} else if (object instanceof Duration duration) {
			//
			value = Unit.with(duration);
			//
		} else if (object instanceof Number number) {
			//
			value = Unit.with(Duration.ofMillis(intValue(number, 0)));
			//
		} else if (object instanceof CharSequence) {
			//
			final String string = Util.toString(object);
			//
			DateTimeParseException dpe = null;
			//
			try {
				//
				return Unit.with(parse(string));
				//
			} catch (final DateTimeParseException e) {
				//
				dpe = e;
				//
			} // try
				//
			final Number number = valueOf(string);
			//
			if (number != null) {
				//
				return toDurationIvalue0(number);
				//
			} // if
				//
			throw dpe;
			//
		} else if (object instanceof char[] cs) {
			//
			return toDurationIvalue0(testAndApply(Objects::nonNull, cs, String::new, null));
			//
		} // if
			//
		return value;
		//
	}

	@Nullable
	private static Duration parse(CharSequence text) {
		return StringUtils.isNotEmpty(text) ? Duration.parse(text) : null;
	}

	public void setLanguageCodeToTextObjIntFunction(
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		this.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
	}

	@Nullable
	private static IValue0<Class<? extends Workbook>> getWorkbookClass(
			@Nullable final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map,
			final String string) {
		//
		final Set<Entry<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> entrySet = Util
				.entrySet(map);
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		List<Class<? extends Workbook>> classes = null;
		//
		String message = null;
		//
		for (final Entry<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> en : entrySet) {
			//
			if (en == null) {
				//
				continue;
				//
			} // if
				//
			try (final Workbook wb = get(Util.getValue(en));
					final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				//
				WorkbookUtil.write(wb, baos);
				//
				if (Boolean
						.logicalOr(
								Boolean.logicalAnd(StringUtils.equalsIgnoreCase("xls", string),
										Objects.equals(OLE_2_COMPOUND_DOCUMENT,
												message = getMessage(
														new ContentInfoUtil().findMatch(baos.toByteArray())))),
								Boolean.logicalAnd(StringUtils.equalsIgnoreCase("xlsx", string),
										Objects.equals("Microsoft Office Open XML", message)))) {
					//
					testAndAccept((a, b) -> !Util.contains(a, b), classes = getIfNull(classes, ArrayList::new),
							Util.getKey(en), Util::add);
					//
				} // if
					//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} //
		} // for
			//
		final int size = IterableUtils.size(classes);
		//
		if (size == 1) {
			//
			return Unit.with(get(classes, 0));
			//
		} else if (size > 1) {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Object[] toArray(@Nullable final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	@Nullable
	private static List<Object> getObjectList(final ObjectMapper objectMapper, @Nullable final Object value) {
		//
		if (value == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<?> iterable = Util.cast(Iterable.class, value);
		//
		if (iterable != null) {
			//
			if (Util.iterator(iterable) == null) {
				//
				return null;
				//
			} //
				//
			List<Object> list = null;
			//
			for (final Object v : iterable) {
				//
				Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), v);
				//
			} // for
				//
			return ObjectUtils.getIfNull(list, ArrayList::new);
			//
		} // if
			//
		try {
			//
			final Object object = ObjectMapperUtil.readValue(objectMapper, Util.toString(value), Object.class);
			//
			if (object instanceof Iterable || object == null) {
				//
				return getObjectList(objectMapper, object);
				//
			} else if (object instanceof String || object instanceof Boolean || object instanceof Number) {
				//
				return getObjectList(objectMapper, Collections.singleton(object));
				//
			} else {
				//
				throw new IllegalArgumentException(Util.toString(Util.getClass(object)));
				//
			} // if
		} catch (final JsonProcessingException e) {
			//
			return getObjectList(objectMapper, Collections.singleton(value));
			//
		} // try
			//
	}

	private static class JTabbedPaneChangeListener implements ChangeListener {

		private Component component = null;

		private String[] importTabNames = null;

		@Override
		public void stateChanged(final ChangeEvent evt) {
			//
			final JTabbedPane jtp = Util.cast(JTabbedPane.class, Util.getSource(evt));
			//
			if (jtp != null) {
				//
				setVisible(component, ArrayUtils.contains(importTabNames, jtp.getTitleAt(jtp.getSelectedIndex())));
				//
			} // if
				//
		}
	}

	private void init() throws NoSuchFieldException {
		//
		final Field[] fs = Util.getDeclaredFields(VoiceManager.class);
		//
		for (int i = 0; i < length(fs); i++) {
			//
			try {
				//
				testAndAccept(x -> and(x, y -> Objects.equals(Util.getDeclaringClass(y), Util.getType(y)),
						y -> isStatic(y), y -> get(y, null) == null), fs[i], x -> Narcissus.setStaticField(x, this));
				//
			} catch (final IllegalArgumentException | IllegalAccessException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // for
			//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		final String TAB_TITLE_IMPORT_SINGLE = "Import(Single)";
		//
		final String TAB_TITLE_IMPORT_BATCH = "Import(Batch)";
		//
		final JTabbedPaneChangeListener jTabbedPaneChangeListener = new JTabbedPaneChangeListener();
		//
		jTabbedPaneChangeListener.importTabNames = new String[] { TAB_TITLE_IMPORT_SINGLE, TAB_TITLE_IMPORT_BATCH };
		//
		jTabbedPane.addChangeListener(jTabbedPaneChangeListener);
		//
		JPanel jPanelWarning = null;
		//
		final boolean isInstalled = SpeechApi.isInstalled(speechApi);
		//
		if (isInstalled) {
			//
			final LayoutManager lm = cloneLayoutManager();
			//
			if (Boolean.logicalAnd(Objects.equals(Boolean.TRUE, IValue0Util.getValue0(IsWindows10OrGreater())),
					getInstance(speechApi) instanceof SpeechApiSpeechServerImpl) &&
			//
			// https://learn.microsoft.com/en-us/windows/win32/api/winnt/ns-winnt-osversioninfoexa
			//
			// dwMajorVersion
			//
					ObjectUtils.compare(valueOf(Util.toString(Util.get(getOsVersionInfoExMap(), "getMajor"))),
							10) >= 10) {
				//
				jPanelWarning = createMicrosoftWindowsCompatibilityWarningJPanel(lm,
						microsoftWindowsCompatibilitySettingsPageUrl);
				//
			} // if
				//
			try {
				//
				voiceIds = SpeechApi.getVoiceIds(speechApi);
				//
			} catch (final Error e) {
				//
				final boolean headless = GraphicsEnvironment.isHeadless();
				//
				testAndRun(headless, () -> errorOrAssertOrShowException(true, e));
				//
				if (!headless) {
					//
					jPanelWarning = createVoiceIdWarningPanel(this);
					//
				} // if
					//
			} // try
				//
			testAndAccept(Objects::nonNull, jPanelWarning, x -> {
				//
				testAndAccept(MigLayout.class::isInstance, lm, y -> add(x, WRAP));
				//
				testAndAccept(y -> !(y instanceof MigLayout), lm, y -> add(x));
				//
			});
			//
		} // if
			//
			// If the instance is instantiated by
			// "io.github.toolfactory.narcissus.Narcissus.allocateInstance(java.lang.Class)",
			// the "component" field in "ava.awt.Container" class will be null.
			//
			// If the "component" field in "ava.awt.Container" class is null, call "return"
			// at once and stop the rest statement(s).
			//
		if (Narcissus.getObjectField(this, Container.class.getDeclaredField(COMPONENT)) == null) {
			//
			return;
			//
		} // if
			//
		testAndRun(!isInstalled,
				() -> add(craeteSpeechApiInstallationWarningJPanel(microsoftSpeechPlatformRuntimeDownloadPageUrl),
						WRAP));
		//
		forEach(Util.stream(Util.entrySet(getTitledComponentMap(
				ListableBeanFactoryUtil.getBeansOfType(applicationContext, Component.class), tabOrders))),
				x -> jTabbedPane.addTab(Util.getKey(x), Util.getValue(x)));
		//
		jTabbedPane.addTab("Misc", createMiscellaneousPanel(cloneLayoutManager(), voiceIds));
		//
		// maximum preferred height of all tab page(s)
		//
		final Double preferredHeight = getMaxPagePreferredHeight(jTabbedPane);
		//
		final freemarker.template.Configuration configuration = ObjectUtils.getIfNull(freeMarkerConfiguration,
				() -> new freemarker.template.Configuration(
						ObjectUtils.getIfNull(freeMarkerVersion, freemarker.template.Configuration::getVersion)));
		//
		testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
				x -> ConfigurationUtil.setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
		//
		jTabbedPane.addTab("Help", createHelpPanel(preferredHeight, configuration, mediaFormatPageUrl,
				poiEncryptionPageUrl, jSoupParseTimeout));
		//
		final List<?> pages = Util.cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getField(x, getDeclaredField(Util.getClass(x), "pages")), null));
		//
		setSelectedIndex(jTabbedPane, getTabIndexByTitle(pages, PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.tabTitle")));
		//
		if (layoutManager instanceof MigLayout) {
			//
			add(jTabbedPane, WRAP);
			//
			add(jTabbedPaneChangeListener.component = createImportResultPanel(cloneLayoutManager()), GROWX);
			//
			jTabbedPaneChangeListener.stateChanged(testAndApply(Objects::nonNull, jTabbedPane, ChangeEvent::new, null));
			//
		} // if
			//
		setPreferredWidth(jPanelWarning, () -> getPreferredWidth(jTabbedPane));
		//
		final Predicate<Component> predicate = createFocusableComponentPredicate(
				Arrays.asList(JLabel.class, JScrollPane.class, JProgressBar.class, JPanel.class));
		//
		forEach(Util.toList(Util.map(
				FailableStreamUtil.stream(FailableStreamUtil.map(new FailableStream<>(Util.stream(pages)),
						x -> Narcissus.getObjectField(x, getDeclaredField(Util.getClass(x), COMPONENT)))),
				x -> Util.cast(Container.class, x))), c -> {
					//
					// https://stackoverflow.com/questions/35508128/setting-personalized-focustraversalpolicy-on-tab-in-jtabbedpane
					//
					setFocusCycleRoot(c, true);
					//
					setFocusTraversalPolicy(c,
							new TabFocusTraversalPolicy(Util.toList(
									Util.filter(testAndApply(Objects::nonNull, getComponents(c), Arrays::stream, null),
											predicate))));
					//
				});
		//
	}

	private static Map<String, Component> getTitledComponentMap(final Map<String, Component> cs,
			final String... orders) {
		//
		final Iterable<Entry<String, Component>> entrySet = Util.entrySet(cs);
		//
		Map<String, Component> m1 = null;
		//
		if (Util.iterator(entrySet) != null) {
			//
			Component c = null;
			//
			Titled titled = null;
			//
			for (final Entry<String, Component> entry : entrySet) {
				//
				if ((titled = Util.cast(Titled.class, c = Util.getValue(entry))) == null) {
					//
					continue;
					//
				} // if
					//
				Util.put(m1 = ObjectUtils.getIfNull(m1, LinkedHashMap::new), getTitle(titled), c);
				//
			} // for
				//
		} // if
			//
		final Map<String, Component> m2 = new TreeMap<String, Component>((a, b) -> {
			//
			final int ia = ArrayUtils.indexOf(orders, a);
			//
			final int ib = ArrayUtils.indexOf(orders, b);
			//
			if (ia > ib) {
				//
				return 1;
				//
			} else if (ia < ib) {
				//
				return -1;
				//
			} // if
				//
			return 0;
			//
		});
		//
		if (m1 != null) {
			//
			m2.putAll(m1);
			//
		} // if
			//
		return m2;
		//
	}

	private static <T, E extends Throwable> boolean and(final T value, final FailablePredicate<T, E> a,
			final FailablePredicate<T, E> b, final FailablePredicate<T, E>... ps) throws E {
		//
		boolean result = Util.test(a, value) && Util.test(b, value);
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; i < length(ps); i++) {
			//
			if (!(result &= Util.test(ps[i], value))) {
				//
				return result;
				//
			} // if
				//
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static String getTitle(@Nullable final Titled instance) {
		return instance != null ? instance.getTitle() : null;
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static void setSelectedIndex(@Nullable final JTabbedPane instance, @Nullable final Number index) {
		//
		if (instance != null && index != null && instance.getTabCount() > index.intValue()) {
			//
			instance.setSelectedIndex(index.intValue());
			//
		} // if
			//
	}

	private static void setPreferredWidth(@Nullable final Component component,
			@Nullable final Supplier<Double> supplier) {
		//
		if (component != null) {
			//
			final Double maxPreferredWidth = ObjectUtils.max(getPreferredWidth(component),
					supplier != null ? supplier.get() : null);
			//
			if (maxPreferredWidth != null) {
				//
				setPreferredWidth(maxPreferredWidth.intValue(), component);
				//
			} // if
				//
		} // if
			//
	}

	private static Predicate<Component> createFocusableComponentPredicate(
			final Collection<Class<?>> compontentClassNotFocus) {
		//
		return x -> {
			//
			final JTextComponent jtc = Util.cast(JTextComponent.class, x);
			//
			if (jtc != null) {
				//
				return jtc.isEditable();
				//
			} // if
				//
			return !Util.contains(compontentClassNotFocus, Util.getClass(x));
			//
		};
		//
	}

	private static void setFocusCycleRoot(@Nullable final Container instance, final boolean focusCycleRoot) {
		if (instance != null) {
			instance.setFocusCycleRoot(focusCycleRoot);
		}
	}

	private static void setFocusTraversalPolicy(@Nullable final Container instance, final FocusTraversalPolicy policy) {
		if (instance != null) {
			instance.setFocusTraversalPolicy(policy);
		}
	}

	@Nullable
	private static Component[] getComponents(@Nullable final Container instance) {
		return instance != null ? instance.getComponents() : null;
	}

	private static JPanel createVoiceIdWarningPanel(@Nullable final VoiceManager instance) {
		//
		final JPanel jPanelWarning = new JPanel();
		//
		jPanelWarning.setBorder(BorderFactory.createTitledBorder(WARNING));
		//
		final IValue0<String> pageTitle = instance != null
				? instance.getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle()
				: null;
		//
		final String title = StringUtils.defaultIfBlank(IValue0Util.getValue0(pageTitle),
				"Download Microsoft Speech Platform - Runtime Languages (Version 11) from Official Microsoft Download Center");
		//
		final String microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl = instance != null
				? instance.microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl
				: null;
		//
		add(jPanelWarning,
				pageTitle != null
						? new JLabelLink(ContainerTagUtil.withText(
								new ATag().withHref(microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl), title))
						: new JLabel(title));
		//
		return jPanelWarning;
		//
	}

	private static JPanel createMicrosoftWindowsCompatibilityWarningJPanel(final LayoutManager lm,
			final String microsoftWindowsCompatibilitySettingsPageUrl) {
		//
		final JPanel jPanelWarning = new JPanel(lm);
		//
		jPanelWarning.setBorder(BorderFactory.createTitledBorder(WARNING));
		//
		final JLabel jLabel = new JLabel("Please set Compatibility Mode to \"Windows 8\" or prior version");
		//
		if (lm instanceof MigLayout) {
			add(jPanelWarning, jLabel, WRAP);
		} else {
			add(jPanelWarning, jLabel);
		} // if
			//
		ATag aTag = null;
		//
		try {
			//
			aTag = ATagUtil.createByUrl(microsoftWindowsCompatibilitySettingsPageUrl);
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		add(jPanelWarning,
				pageTitle != null ? new JLabelLink(aTag)
						: new JLabel(StringUtils.defaultIfBlank(pageTitle,
								"Make older apps or programs compatible with Windows 10")));
		//
		return jPanelWarning;
		//
	}

	private static JPanel craeteSpeechApiInstallationWarningJPanel(
			final String microsoftSpeechPlatformRuntimeDownloadPageUrl) {
		//
		final JPanel jPanelWarning = new JPanel();
		//
		jPanelWarning.setBorder(BorderFactory.createTitledBorder(WARNING));
		//
		ATag aTag = null;
		//
		try {
			//
			aTag = ATagUtil.createByUrl(microsoftSpeechPlatformRuntimeDownloadPageUrl);
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		add(jPanelWarning, pageTitle != null ? new JLabelLink(aTag)
				: new JLabel(StringUtils.defaultIfBlank(pageTitle,
						"Download Microsoft Speech Platform - Runtime (Version 11) from Official Microsoft Download Center")));
		//
		return jPanelWarning;
		//
	}

	@Nullable
	private static Double getMaxPagePreferredHeight(final Object jTabbedPane) throws NoSuchFieldException {
		//
		Double preferredHeight = null;
		//
		final List<?> pages = Util.cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getObjectField(x, getDeclaredField(JTabbedPane.class, "pages")), null));
		//
		Object page = null;
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(pages); i++) {
			//
			page = get(pages, i);
			//
			if (f == null) {
				//
				f = getDeclaredField(Util.getClass(page), COMPONENT);
				//
			} // if
				//
			preferredHeight = ObjectUtils.max(preferredHeight, getPreferredHeight(
					Util.cast(Component.class, page != null ? Narcissus.getObjectField(page, f) : null)));
			//
		} // for
			//
		return preferredHeight;
		//
	}

	@Nullable
	private static TemplateLoader getTemplateLoader(@Nullable final freemarker.template.Configuration instance) {
		return instance != null ? instance.getTemplateLoader() : null;
	}

	private static void setVisible(@Nullable final Component instance, final boolean b) {
		if (instance != null) {
			instance.setVisible(b);
		}
	}

	@Nullable
	private static IValue0<Boolean> IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull,
							Util.getDeclaredMethods(Util.forName("com.sun.jna.platform.win32.VersionHelpers")),
							Arrays::stream, null),
					m -> m != null && Objects.equals(Util.getName(m), "IsWindows10OrGreater")
							&& m.getParameterCount() == 0 && isStatic(m)));
			//
			if (ms == null || ms.isEmpty()) {
				//
				return null;
				//
			} // if
				//
			final Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
			//
			return Unit.with(Util.cast(Boolean.class, invoke(m, null)));
			//
		} catch (final IllegalAccessException | InvocationTargetException e) {
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Map<String, Object> getOsVersionInfoExMap() {
		//
		try {
			//
			final Object osVersionInfoEx = getOsVersionInfoEx();
			//
			final List<Method> ms = osVersionInfoEx != null
					? Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(osVersionInfoEx)),
									Arrays::stream, null),
							x -> x != null && !Objects.equals(x.getReturnType(), Void.TYPE)))
					: null;
			//
			Method m = null;
			//
			if (osVersionInfoEx != null && ms != null) {
				//
				Map<String, Object> map = null;
				//
				for (int i = 0; i < IterableUtils.size(ms); i++) {
					//
					if ((m = get(ms, i)) == null || m.isSynthetic()) {
						//
						continue;
						//
					} // if
						//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Util.getName(m),
							invoke(m, osVersionInfoEx));
					//
				} // for
					//
				return map;
				//
			} // if
				//
		} catch (final IllegalAccessException | InvocationTargetException | InstantiationException
				| NoSuchMethodException e) {
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Object getOsVersionInfoEx()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//
		final Class<?> clz = Util.forName("com.sun.jna.platform.win32.Kernel32");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#INSTANCE
		//
		final List<Field> fs = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(clz), Arrays::stream, null),
						f -> Objects.equals(Util.getName(f), "INSTANCE") && Objects.equals(Util.getType(f), clz)
								&& isStatic(f)));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		final Class<?> clzOsVersionInfoEx = Util.forName("com.sun.jna.platform.win32.WinNT$OSVERSIONINFOEX");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#GetVersionEx-com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX-
		//
		final List<Method> ms = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(clz), Arrays::stream, null),
						m -> Objects.equals(Util.getName(m), "GetVersionEx")
								&& Arrays.equals(new Class[] { clzOsVersionInfoEx }, getParameterTypes(m))));
		//
		final Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
		//
		final Object osVersionInfoEx = newInstance(getDeclaredConstructor(clzOsVersionInfoEx));
		//
		return Objects.equals(Boolean.TRUE, invoke(m, FieldUtils.readStaticField(f), osVersionInfoEx)) ? osVersionInfoEx
				: null;
		//
	}

	@Nullable
	private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static Object getInstance(final SpeechApi speechApi) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(speechApi)), Arrays::stream, null),
				f -> Objects.equals(Util.getName(f), "instance")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		if (f != null) {
			//
			return Narcissus.getField(speechApi, f);
			//
		} // if
			//
		return speechApi;
		//
	}

	private IValue0<String> getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle() {
		//
		if (microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle == null) {
			//
			microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle = getPageTitle(
					microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl, jSoupParseTimeout);
			//
		} // if
			//
		return microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle;
		//
	}

	@Nullable
	private static IValue0<String> getPageTitle(final String url, final Duration timeout) {
		//
		try {
			//
			return Unit
					.with(ElementUtil
							.text(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil
											.getElementsByTag(
													testAndApply(Objects::nonNull,
															testAndApply(StringUtils::isNotBlank, url,
																	x -> new URI(x).toURL(), null),
															x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null),
													"title"),
									x -> get(x, 0), null)));
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static class JLabelLink extends JLabel {

		private static final long serialVersionUID = 8848505138795752227L;

		@Nullable
		private String url = null;

		{
			//
			super.setForeground(darker(Color.BLUE));
			//
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			//
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(final MouseEvent evt) {
					//
					try {
						//
						testAndAccept(Objects::nonNull, testAndApply(Objects::nonNull, url, URI::new, null),
								x -> browse(Desktop.getDesktop(), x));
						//
					} catch (final IOException | URISyntaxException e) {
						//
						TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
						//
					} // try
						//
				}

			});
			//
		}

		private JLabelLink(@Nullable final ATag aTag) {
			//
			super(getChildrenAsString(aTag));
			//
			this.url = getValue(getAttributeByName(aTag, "href"));
			//
		}

		@Nullable
		private static String getValue(@Nullable final Attribute instance) {
			return instance != null ? instance.getValue() : null;
		}

		@Nullable
		@SuppressWarnings("java:S1612")
		private static String getChildrenAsString(@Nullable final ContainerTag<?> instance) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(Util.cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "children", true), null)));
				//
				final Stream<?> stream = testAndApply(Objects::nonNull, spliterator,
						x -> StreamSupport.stream(x, false), null);
				//
				return testAndApply(Objects::nonNull, Util.toList(Util.map(stream, x -> Util.toString(x))),
						x -> String.join("", x), null);
				//
			} catch (final IllegalAccessException e) {
				//
				return null;
				//
			} // try
				//
		}

		@Nullable
		private static Attribute getAttributeByName(@Nullable final Tag<?> instance, final String name) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(Util.cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "attributes", true), null)));
				//
				final Stream<?> stream = testAndApply(Objects::nonNull, spliterator,
						x -> StreamSupport.stream(x, false), null);
				//
				final List<Attribute> as = Util.toList(Util.filter(Util.map(stream, x -> Util.cast(Attribute.class, x)),
						a -> Objects.equals(name, getName(a))));
				//
				if (as == null || as.isEmpty()) {
					//
					return null;
					//
				} else if (IterableUtils.size(as) != 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				return get(as, 0);
				//
			} catch (final IllegalAccessException e) {
				//
				return null;
				//
			} // try
				//
		}

		@Nullable
		private static String getName(@Nullable final Attribute instance) {
			return instance != null ? instance.getName() : null;
		}

		@Nullable
		private static Color darker(@Nullable final Color instance) {
			return instance != null ? instance.darker() : null;
		}

	}

	@Nullable
	private static Integer getTabIndexByTitle(@Nullable final List<?> pages, final Object title) {
		//
		Integer tabIndex = null;
		//
		Object page = null;
		//
		Field fieldTitle = null;
		//
		for (int i = 0; i < IterableUtils.size(pages); i++) {
			//
			if ((page = get(pages, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (fieldTitle == null) {
				//
				fieldTitle = getFieldByName(Util.getClass(page), "title");
				//
			} // if
				//
			if (fieldTitle == null || !Objects.equals(Narcissus.getField(page, fieldTitle), title)) {
				//
				continue;
				//
			} // if
				//
			if (tabIndex == null) {
				//
				tabIndex = Integer.valueOf(i);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return tabIndex;
		//
	}

	@Nullable
	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final List<Field> fs = Util.toList(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getName(f), name)));
		//
		Field field = null;
		//
		if (fs != null && !fs.isEmpty()) {
			//
			if (IterableUtils.size(fs) == 1) {
				//
				field = get(fs, 0);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return field;
		//
	}

	@Nullable
	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private LayoutManager cloneLayoutManager() {
		//
		final LayoutManager layoutManagerDefault = null;
		//
		LayoutManager lm = ObjectUtils.defaultIfNull(this.layoutManager, layoutManagerDefault);
		//
		if (lm instanceof MigLayout) {
			//
			final MigLayout migLayout = new MigLayout();
			//
			testAndAccept(PropertyResolverUtil::containsProperty, propertyResolver,
					"net.miginfocom.swing.MigLayout.layoutConstraints",
					(a, b) -> migLayout.setLayoutConstraints(PropertyResolverUtil.getProperty(a, b)));
			//
			lm = migLayout;
			//
		} else if (lm instanceof Serializable serializable) {
			//
			lm = Util.cast(LayoutManager.class, SerializationUtils.clone(serializable));
			//
		} // if
			//
		return ObjectUtils.defaultIfNull(lm, layoutManagerDefault);
		//
	}

	private static class TabFocusTraversalPolicy extends FocusTraversalPolicy {

		private List<Component> components = null;

		private TabFocusTraversalPolicy(final List<Component> components) {
			this.components = components;
		}

		@Nullable
		public Component getComponentAfter(final Container focusCycleRoot, final Component aComponent) {
			//
			final int size = IterableUtils.size(components);
			//
			return size != 0 ? get(components, (intValue(indexOf(components, aComponent), -1) + 1) % size) : null;
			//
		}

		@Nullable
		public Component getComponentBefore(final Container focusCycleRoot, final Component aComponent) {
			//
			int idx = intValue(indexOf(components, aComponent), -1) - 1;
			//
			if (idx < 0) {
				//
				idx = IterableUtils.size(components) - 1;
				//
			} // if
				//
			return get(components, idx);
			//
		}

		@Nullable
		public Component getDefaultComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		@Nullable
		public Component getLastComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, components.size() - 1) : null;
		}

		@Nullable
		public Component getFirstComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		@Override
		@Nullable
		public Component getInitialComponent(@Nullable final Window window) {
			return window != null ? super.getInitialComponent(window) : null;
		}

		@Nullable
		private static Integer indexOf(@Nullable final List<?> items, final Object item) {
			return items != null ? Integer.valueOf(items.indexOf(item)) : null;
		}

	}

	@Nullable
	private static <E> ListCellRenderer<? super E> getRenderer(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
	}

	private static void add(@Nullable final Container instance, @Nullable final Component comp) {
		//
		if (instance == null) {
			//
			return;
			//
		} //
			//
		try {
			//
			if (Narcissus.getObjectField(instance, getDeclaredField(Container.class, COMPONENT)) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		if (comp != null) {
			//
			instance.add(comp);
			//
		} // if
			//
	}

	private static void add(@Nullable final Container instance, @Nullable final Component comp,
			@Nullable final Object constraints) {
		//
		if (instance == null) {
			//
			return;
			//
		} //
			//
		try {
			//
			if (Narcissus.getObjectField(instance, getDeclaredField(Container.class, COMPONENT)) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		if (comp != null) {
			//
			instance.add(comp, constraints);
			//
		} // if
			//
	}

	private static boolean isAnnotationPresent(@Nullable final AnnotatedElement instance,
			@Nullable final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
	}

	@Nullable
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	@Nullable
	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Nullable
	private static Node item(@Nullable final NodeList instance, final int index) {
		return instance != null ? instance.item(index) : null;
	}

	private JPanel createImportResultPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.setBorder(BorderFactory.createTitledBorder("Import Result"));
		//
		add(panel, new JLabel("Folder"));
		//
		add(panel,
				tfFolder = new JTextField(
						Util.getAbsolutePath(testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null))),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		add(panel, new JLabel("File"));
		//
		add(panel, tfFile = new JTextField(), GROWX);
		//
		add(panel, new JLabel("Length"));
		//
		add(panel, tfFileLength = new JTextField(), String.format("%1$s,%2$s", GROWX, WRAP));
		//
		add(panel, new JLabel("File Digest"));
		//
		add(panel, tfFileDigest = new JTextField(), String.format("%1$s,span %2$s", GROWX, 3));
		//
		setEditable(false, tfFolder, tfFile, tfFileLength, tfFileDigest);
		//
		return panel;
		//
	}

	@Nullable
	private static <T> Optional<T> reduce(@Nullable final Stream<T> instance,
			@Nullable final BinaryOperator<T> accumulator) {
		//
		return instance != null && (accumulator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.reduce(accumulator)
				: null;
		//
	}

	private JPanel createMiscellaneousPanel(final LayoutManager layoutManager, final String[] voiceIds) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		final Object speechApiInstance = getInstance(speechApi);
		//
		if (speechApiInstance instanceof SpeechApiSpeechServerImpl) {
			//
			add(panel,
					new JLabelLink(ContainerTagUtil.withText(
							new ATag().withHref(microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl),
							IValue0Util.getValue0(getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle()))),
					WRAP);
			//
		} // if
			//
		final IValue0<Object> dllPath = getDllPath(speechApiInstance);
		//
		JPanel panelDllPath = null;
		//
		if (dllPath != null) {
			//
			(panelDllPath = new JPanel()).setLayout(cloneLayoutManager());
			//
			panelDllPath.setBorder(BorderFactory.createTitledBorder("Dll Path"));
			//
			add(panelDllPath, tfDllPath = new JTextField(Util.toString(IValue0Util.getValue0(dllPath))));
			//
			add(panelDllPath, btnDllPathCopy = new JButton("Copy"));
			//
			add(panel, panelDllPath, String.format("%1$s,span %2$s", WRAP, 2));
			//
		} // if
			//
			// Find the maximum width of the "java.awt.Component" instance from the field
			// with "org.springframework.context.support.VoiceManager.Group" annotation with
			// same value (i.e. "Short Export Button"),
			// then set the maximum width to each "java.awt.Component" in the list.
			//
		final List<Component> cs = getObjectsByGroupAnnotation(this, "Short Export Button", Component.class);
		//
		if (CollectionUtils.isNotEmpty(cs)) {
			//
			setPreferredWidth(intValue(getPreferredWidth(
					Collections.max(cs, (a, b) -> ObjectUtils.compare(getPreferredWidth(a), getPreferredWidth(b)))), 0),
					cs);
			//
		} // if
			//
			// btnExportMicrosoftSpeechObjectLibraryInformation
			//
		final StringBuilder btnExportMicrosoftSpeechObjectLibraryInformationName = new StringBuilder("Export ");
		//
		if (StringUtils.isNotBlank(append(btnExportMicrosoftSpeechObjectLibraryInformationName,
				StringUtils.defaultIfBlank(Provider.getProviderName(Util.cast(Provider.class, speechApi)),
						"Microsoft Speech Object Library")))) {
			//
			append(btnExportMicrosoftSpeechObjectLibraryInformationName, ' ');
			//
		} // if
			//
		add(panel,
				btnExportMicrosoftSpeechObjectLibraryInformation = new JButton(
						Util.toString(append(btnExportMicrosoftSpeechObjectLibraryInformationName, "Information"))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		final JPanel panelFile = new JPanel();
		//
		panelFile.setLayout(cloneLayoutManager());
		//
		panelFile.setBorder(BorderFactory.createTitledBorder("File"));
		//
		add(panelFile, tfExportFile = new JTextField(), String.format(WMIN_ONLY_FORMAT, 300));
		//
		add(panelFile, btnExportCopy = new JButton("Copy"));
		//
		add(panelFile, btnExportBrowse = new JButton("Browse"));
		//
		add(panel, panelFile, String.format(SPAN_ONLY_FORMAT, 2));
		//
		setEditable(false, tfDllPath, tfExportFile);
		//
		addActionListener(this, btnExportMicrosoftSpeechObjectLibraryInformation, btnExportCopy, btnExportBrowse,
				btnDllPathCopy);
		//
		setEnabled(SpeechApi.isInstalled(speechApi) && voiceIds != null,
				btnExportMicrosoftSpeechObjectLibraryInformation);
		//
		if (panelDllPath != null) {
			//
			setPreferredWidth((int) Math.max(getPreferredWidth(panelDllPath), getPreferredWidth(panelFile)),
					panelDllPath, panelFile);
			//
		} // if
			//
		return panel;
		//
	}

	private static JScrollPane createHelpPanel(@Nullable final Number preferredHeight,
			final freemarker.template.Configuration configuration, final String mediaFormatPageUrl,
			final String poiEncryptionPageUrl, final Duration timeout) {
		//
		JEditorPane jep = null;
		//
		try (final Writer writer = new StringWriter()) {
			//
			final Map<Object, Object> map = new LinkedHashMap<>(Collections.singletonMap("statics",
					new BeansWrapper(freemarker.template.Configuration.getVersion()).getStaticModels()));
			//
			Util.put(map, "mediaFormatLink", getMediaFormatLink(mediaFormatPageUrl));
			//
			Util.put(map, "encryptionTableHtml",
					getEncryptionTableHtml(
							testAndApply(StringUtils::isNotBlank, poiEncryptionPageUrl, x -> new URI(x).toURL(), null),
							timeout));
			//
			TemplateUtil.process(ConfigurationUtil.getTemplate(configuration, "help.html.ftl"), map, writer);
			//
			final String html = Util.toString(writer);
			//
			setEditable(false,
					jep = new JEditorPane(StringUtils.defaultIfBlank(
							getMimeType(new ContentInfoUtil().findMatch(VoiceManager.getBytes(html))), "text/html"),
							html));
			//
		} catch (final IOException | TemplateException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(
					ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		addHyperlinkListener(jep, x -> {
			//
			try {
				//
				if (Objects.equals(EventType.ACTIVATED, getEventType(x))) {
					//
					browse(Desktop.getDesktop(), toURI(getURL(x)));
					//
				} // if
					//
			} catch (final IOException | URISyntaxException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		});
		//
		final JScrollPane jsp = new JScrollPane(jep);
		//
		jsp.setPreferredSize(new Dimension(intValue(getPreferredWidth(jsp), 0),
				intValue(preferredHeight, intValue(getPreferredHeight(jsp), 0))));
		//
		final JPanel jPanel = new JPanel();
		//
		add(jPanel, jsp);
		//
		return jsp;
		//
	}

	private static void addHyperlinkListener(@Nullable final JEditorPane instance, final HyperlinkListener listener) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(instance, JComponent.class.getDeclaredField("listenerList")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.addHyperlinkListener(listener);
		//
	}

	@Nullable
	private static URL getURL(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getURL() : null;
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
		try {
			//
			if (Narcissus.getField(instance, URL.class.getDeclaredField(HANDLER)) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.toURI();
		//
	}

	@Nullable
	private static String getEncryptionTableHtml(final URL url, final Duration timeout) throws IOException {
		//
		org.jsoup.nodes.Document document = testAndApply(
				x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x), ProtocolUtil.getAllowProtocols()), url,
				x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null);
		//
		if (document == null) {
			//
			document = testAndApply(Objects::nonNull,
					testAndApply(Objects::nonNull, url, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null),
					Jsoup::parse, null);
			//
		} // if
			//
		final Elements h2s = ElementUtil.selectXpath(document, "//h2[text()=\"Supported feature matrix\"]");
		//
		return html(ElementUtil.nextElementSibling(IterableUtils.size(h2s) == 1 ? IterableUtils.get(h2s, 0) : null));
		//
	}

	@Nullable
	private static String html(@Nullable final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.html() : null;
	}

	@Nullable
	private static EventType getEventType(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getEventType() : null;
	}

	@Nullable
	private static ATag getMediaFormatLink(final String url) throws Exception {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.forName("com.sun.jna.Platform")),
							Arrays::stream, null),
					m -> m != null && Objects.equals(m.getReturnType(), Boolean.TYPE) && m.getParameterCount() == -0));
			//
			Method m = null;
			//
			List<String> methodNames = null;
			//
			for (int i = 0; i < IterableUtils.size(ms); i++) {
				//
				if ((m = get(ms, i)) == null || !isStatic(m)) {
					//
					continue;
					//
				} // if
					//
				if (!Objects.equals(Boolean.TRUE, Narcissus.invokeStaticMethod(m))) {
					//
					continue;
					//
				} // if
					//
				Util.add(methodNames = ObjectUtils.getIfNull(methodNames, ArrayList::new), Util.getName(m));
				//
			} // for
				//
			final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull,
					(is = openStream(testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null))) != null
							? IOUtils.toString(is, StandardCharsets.UTF_8)
							: null,
					Jsoup::parse, null), ".relatedtopics a[href]");
			//
			org.jsoup.nodes.Element element = null;
			//
			String textContent, methodName = null;
			//
			for (int i = 0; i < IterableUtils.size(elements); i++) {
				//
				for (int j = 0; j < IterableUtils.size(methodNames); j++) {
					//
					if (!StringUtils.startsWithIgnoreCase(methodName = get(methodNames, j), "is") || !StringUtils
							.containsIgnoreCase(textContent = ElementUtil.text(element = get(elements, i)),
									StringUtils.substringAfter(methodName, "is"))
							|| aTag != null) {
						//
						continue;
						//
					} // if
						//
					TagUtil.attr(ContainerTagUtil.withText(aTag = new ATag(), textContent), "href",
							NodeUtil.attr(element, "href"));
					//
				} // for
					//
			} // for
				//
		} finally {
			//
			IOUtils.closeQuietly(is);
			//
		} // try
			//
		return aTag;
		//
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(Util.getClass(instance), HANDLER)) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.openStream();
	}

	@Nullable
	private static byte[] getBytes(@Nullable final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, @Nullable final String string) {
		return instance != null ? instance.append(string) : null;
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final char c) {
		return instance != null ? instance.append(c) : null;
	}

	@Nullable
	private static IValue0<Object> getDllPath(final Object instance) {
		//
		final Class<?>[] declaredClasses = getDeclaredClasses(Util.getClass(instance));
		//
		List<Field> fs = null;
		//
		Field f = null;
		//
		List<Method> ms = null;
		//
		Method m = null;
		//
		IValue0<Object> dllPath = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		for (int i = 0; declaredClasses != null && i < declaredClasses.length; i++) {
			//
			final Class<?> declaredClass = declaredClasses[i];
			//
			if (declaredClass == null
					//
					|| (fs = Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredFields(declaredClass), Arrays::stream, null),
							x -> Objects.equals(Util.getType(x), declaredClass)))) == null
					|| IterableUtils.size(fs) != 1 || (f = get(fs, 0)) == null
					//
					|| (ms = Util
							.toList(Util.filter(
									testAndApply(Objects::nonNull, Util.getDeclaredMethods(declaredClass),
											Arrays::stream, null),
									x -> Objects.equals(Util.getName(x), "getDllPath")))) == null
					|| IterableUtils.size(ms) != 1 || (m = get(ms, 0)) == null) {
				continue;
			} // if
				//
			try {
				//
				dllPath = Unit.with(invoke(m, get(f, null)));
				//
			} catch (final IllegalAccessException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				errorOrAssertOrShowException(headless,
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
								ExceptionUtils.getRootCause(e), e));
				//
			} // try
				//
		} // for
			//
		return dllPath;
		//
	}

	@Nullable
	private static Class<?>[] getDeclaredClasses(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredClasses() : null;
	}

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
			final JList<? extends E> list, @Nullable final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	@Nullable
	private static Integer toInteger(@Nullable final Object object) {
		//
		Integer integer = null;
		//
		if (object instanceof Integer i) {
			//
			integer = i;
			//
		} else if (object instanceof Number number) {
			//
			integer = Integer.valueOf(intValue(number, 0));
			//
		} else {
			//
			integer = valueOf(Util.toString(object));
			//
		} // if
			//
		return integer;
		//
	}

	@Nullable
	private static Integer valueOf(@Nullable final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Nullable
	private static IntStream map(@Nullable final IntStream instance, @Nullable final IntUnaryOperator mapper) {
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || mapper != null)
				? instance.map(mapper)
				: instance;
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
	private static <T> LongStream mapToLong(@Nullable final Stream<T> instance,
			@Nullable final ToLongFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || mapper != null)
				? instance.mapToLong(mapper)
				: null;
		//
	}

	@Nullable
	private static <T> Optional<T> max(@Nullable final Stream<T> instance,
			@Nullable final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || comparator != null)
				? instance.max(comparator)
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

	private static void setEnabled(final boolean b, final Component instance, @Nullable final Component... cs) {
		//
		setEnabled(instance, b);
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			setEnabled(cs[i], b);
			//
		} // for
			//
	}

	private static void setEnabled(@Nullable final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			@Nullable final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static void setEditable(final boolean editable, @Nullable final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
				continue;
			} // if
				//
			jtc.setEditable(editable);
			//
		} // for
			//
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
			//
			if ((ab = abs[i]) == null) {
				continue;
			} // if
				//
			ab.addActionListener(actionListener);
			//
		} // for
			//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		accept(x -> Util.setText(x, null), tfCurrentProcessingSheetName, tfCurrentProcessingVoice);
		//
		clear(tmImportResult);
		//
		final Object source = Util.getSource(evt);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (anyMatch(Util.stream(findFieldsByValue(Util.getDeclaredFields(getClass()), this, source)),
				f -> isAnnotationPresent(f, ExportButton.class))) {
			//
			Util.setText(tfExportFile, null);
			//
			actionPerformedForExportButtons(source, headless);
			//
		} // if
			//
		final boolean nonTest = !isTestMode();
		//
		// if the "source" is one of the value of the field annotated with
		// "@SystemClipboard", pass the "source" to
		// "actionPerformedForSystemClipboardAnnotated(java.lang.Object)" method
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null),
				f -> isAnnotationPresent(f, SystemClipboard.class)));
		//
		testAndRun(Util.contains(Util.toList(Util.filter(
				FailableStreamUtil.stream(FailableStreamUtil.map(fs, f -> FieldUtils.readField(f, this, true))),
				Objects::nonNull)), source), () -> actionPerformedForSystemClipboardAnnotated(nonTest, source));
		//
		if (Objects.equals(source, btnExportBrowse)) {
			//
			actionPerformedForExportBrowse(headless);
			//
		} // if
			//
	}

	private static void removeElementAt(@Nullable final MutableComboBoxModel<?> instance, final int index) {
		if (instance != null) {
			instance.removeElementAt(index);
		}
	}

	/**
	 * @see <a href="https://stackoverflow.com/a/24011264">list - Java 8 stream
	 *      reverse order - Stack Overflow</a>
	 */
	@Nullable
	private static IntStream reverseRange(final int from, final int to) {
		return map(IntStream.range(from, to), i -> to - i + from - 1);
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Nullable
	private static <T> List<T> getObjectsByGroupAnnotation(final Object instance, final String group,
			final Class<T> clz) {
		//
		return Util.toList(Util.map(Util.stream(getObjectsByGroupAnnotation(instance, group)), x -> Util.cast(clz, x)));
		//
	}

	@Nullable
	private static List<?> getObjectsByGroupAnnotation(final Object instance, final String group) {
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null), f -> {
					final Group g = isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringUtils.equals(g != null ? g.value() : null, group);
				}));
		//
		return Util.toList(
				FailableStreamUtil.stream(FailableStreamUtil.map(fs, f -> FieldUtils.readField(f, instance, true))));
		//
	}

	@Nullable
	private static File createTempFile(@Nullable final String prefix, @Nullable final String suffix)
			throws IllegalAccessException, InvocationTargetException {
		//
		final List<Method> ms = Util.toList(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(File.class), Arrays::stream, null),
						x -> Objects.equals(Util.getName(x), "createTempFile")
								&& Arrays.equals(new Class<?>[] { String.class, String.class }, getParameterTypes(x))));
		//
		return Util.cast(File.class,
				invoke(testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null), null,
						prefix, suffix));
		//

	}

	private void actionPerformedForExportBrowse(final boolean headless) {
		//
		try {
			//
			final File file = testAndApply(Objects::nonNull, Util.getText(tfExportFile), File::new, null);
			//
			testAndAccept(Objects::nonNull, toURI(file), x -> browse(Desktop.getDesktop(), x));
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
	}

	private static <E extends Throwable> void testAndRun(final boolean b, @Nullable final FailableRunnable<E> runnable)
			throws E {
		//
		if (b && runnable != null) {
			//
			runnable.run();
			//
		} // if
			//
	}

	private void actionPerformedForSystemClipboardAnnotated(final boolean nonTest, final Object source) {
		//
		final Clipboard clipboard = getSystemClipboard(getToolkit());
		//
		IValue0<String> stringValue = null;
		//
		if (Objects.equals(source, btnExportCopy)) {
			//
			stringValue = Unit.with(Util.getText(tfExportFile));
			//
		} else if (Objects.equals(source, btnDllPathCopy)) {
			//
			stringValue = Unit.with(Util.getText(tfDllPath));
			//
		} // if
			//
		if (stringValue != null) {
			//
			// if this method is not run under unit test, call
			// "java.awt.datatransfer.Clipboard.setContents(java.awt.datatransfer.Transferable,java.awt.datatransfer.ClipboardOwner)"
			// method
			//
			final String string = IValue0Util.getValue0(stringValue);
			//
			testAndRun(nonTest, () -> setContents(clipboard, new StringSelection(string), null));
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private void actionPerformedForExportButtons(final Object source, final boolean headless) {
		//
		if (Objects.equals(source, btnExportMicrosoftSpeechObjectLibraryInformation)) {
			//
			File file = null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = new FileOutputStream(file = new File(
					String.format("MicrosoftSpeechObjectLibrary_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
				//
				WorkbookUtil.write(workbook = createMicrosoftSpeechObjectLibraryWorkbook(speechApi,
						languageCodeToTextObjIntFunction, microsoftSpeechObjectLibraryAttributeNames), os);
				//
				Util.setText(tfExportFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				testAndAccept(EMPTY_FILE_PREDICATE, file, FileUtils::deleteQuietly);
				// ini
			} // try
				//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	@Nullable
	private static <T> Constructor<T> getDeclaredConstructor(@Nullable final Class<T> clz,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredConstructor(parameterTypes) : null;
	}

	@Nullable
	private static <T> T newInstance(@Nullable final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
	}

	@Nullable
	private static char[] toCharArray(@Nullable final String instance) {
		return instance != null ? instance.toCharArray() : null;
	}

	@Nullable
	private static Integer getValue(@Nullable final JSlider instance) {
		return instance != null ? Integer.valueOf(instance.getValue()) : null;
	}

	@Nullable
	private static String randomAlphabetic(final int count) {
		//
		Method method = IValue0Util.getValue0(METHOD_RANDOM_ALPHABETIC);
		//
		try {
			//
			if (method == null) {
				//
				METHOD_RANDOM_ALPHABETIC = Unit
						.with(method = RandomStringUtils.class.getDeclaredMethod("randomAlphabetic", Integer.TYPE));
				//
			} // if
				//
			return Util.toString(invoke(method, null, count));
			//
		} catch (final IllegalAccessException | NoSuchMethodException e) {
			//
			throw new RuntimeException(e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			final Throwable throwable = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
					targetException, ExceptionUtils.getRootCause(e));
			//
			throw ObjectUtils.getIfNull(toRuntimeException(throwable), RuntimeException::new);
			//
		} // try
			//
	}

	@Nullable
	private static String[] getFileExtensions(@Nullable final ContentType instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static Multimap<String, Voice> getVoiceMultimapByListName(@Nullable final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (Util.iterator(voices) != null) {
			//
			Iterable<String> listNames = null;
			//
			for (final Voice v : voices) {
				//
				if (v == null || (listNames = v.getListNames()) == null) {
					//
					continue;
					//
				} // if
					//
				for (final String listName : listNames) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), listName,
							v);
					//
				} // for
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, Voice> getVoiceMultimapByJlpt(@Nullable final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (Util.iterator(voices) != null) {
			//
			for (final Voice v : voices) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create),
						getJlptLevel(v), v);
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static String getJlptLevel(@Nullable final Voice instance) {
		return instance != null ? instance.getJlptLevel() : null;
	}

	@Nullable
	private static List<Field> findFieldsByValue(@Nullable final Field[] fs, final Object instance,
			final Object value) {
		//
		Field f = null;
		//
		Object fieldValue = null;
		//
		List<Field> list = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if ((fieldValue = testAndApply(VoiceManager::isStatic, f, Narcissus::getStaticField,
					a -> testAndApply(Objects::nonNull, instance, b -> Narcissus.getField(b, a), null))) != value
					|| !Objects.equals(fieldValue, value)) {
				//
				continue;
				//
			} // if
				//
			testAndAccept((a, b) -> !Util.contains(a, b), list = ObjectUtils.getIfNull(list, ArrayList::new), f,
					Util::add, null);
			//
		} // for
			//
		return list;
		//
	}

	private static Method getAccessibleObjectIsAccessibleMethod() {
		//
		final List<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(AccessibleObject.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(Util.getName(m), "isAccessible") && m.getParameterCount() == 0));
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> get(x, 0), null);
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
		return object != null ? object : get(defaultSupplier);
	}

	@Nullable
	private static <T, E extends Throwable> T get(@Nullable final FailableSupplier<T, E> instance) throws E {
		return instance != null ? instance.get() : null;
	}

	public static Pair<String, String> getMimeTypeAndBase64EncodedString(@Nullable final String folderPath,
			@Nullable final String filePath) throws IOException {
		//
		final File f = folderPath != null && filePath != null ? new File(folderPath, filePath)
				: testAndApply(Objects::nonNull, filePath, File::new, null);
		//
		final ContentInfo ci = testAndApply(VoiceManager::isFile, f, new ContentInfoUtil()::findMatch, null);
		//
		String mimeType = getMimeType(ci);
		//
		if (StringUtils.isBlank(mimeType)
				&& or(x -> matches(matcher(x, getMessage(ci))), PATTERN_CONTENT_INFO_MESSAGE_MP3_1,
						PATTERN_CONTENT_INFO_MESSAGE_MP3_2, PATTERN_CONTENT_INFO_MESSAGE_MP3_3)) {
			//
			mimeType = "audio/mpeg";
			//
		} // if
			//
		return Pair.of(mimeType, testAndApply(VoiceManager::isFile, f,
				x -> encodeToString(Base64.getEncoder(), Files.readAllBytes(Path.of(toURI(x)))), null));
		//
	}

	@Nullable
	private static String encodeToString(@Nullable final Encoder instance, @Nullable final byte[] src) {
		return instance != null && src != null ? instance.encodeToString(src) : null;
	}

	private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
		if (instance != null) {
			instance.browse(uri);
		}
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	@Nullable
	private static Package getPackage(@Nullable final Class<?> instance) {
		return instance != null ? instance.getPackage() : null;
	}

	@Override
	public Toolkit getToolkit() {
		//
		if (toolkit == null) {
			//
			toolkit = Toolkit.getDefaultToolkit();
			//
		} // if
			//
		return getIfNull(toolkit, super::getToolkit);
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate,
			@Nullable final T value, @Nullable final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> a, @Nullable final BiConsumer<T, U> b) {
		if (test(instance, t, u)) {
			accept(a, t, u);
		} else {
			accept(b, t, u);
		} // if
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, @Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static Integer incrementAndGet(@Nullable final AtomicInteger instance) {
		return instance != null ? Integer.valueOf(instance.incrementAndGet()) : null;
	}

	@Nullable
	private static NamedNodeMap getAttributes(@Nullable final Node instance) {
		return instance != null ? instance.getAttributes() : null;
	}

	@Nullable
	private static DocumentBuilder newDocumentBuilder(@Nullable final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	@Nullable
	private static Document parse(@Nullable final DocumentBuilder instance, final InputStream is)
			throws SAXException, IOException {
		return instance != null ? instance.parse(is) : null;
	}

	@Nullable
	private static Node getNamedItem(@Nullable final NamedNodeMap instance, final String name) {
		return instance != null ? instance.getNamedItem(name) : null;
	}

	@Nullable
	private static String getTextContent(@Nullable final Node instance) {
		return instance != null ? instance.getTextContent() : null;
	}

	@Nullable
	private static String getName(@Nullable final File instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static boolean isFile(@Nullable final File instance) {
		return instance != null && instance.isFile();
	}

	private static <T> void accept(final Consumer<? super T> action, final T a, final T b,
			@Nullable final T... values) {
		//
		accept(action, a);
		//
		accept(action, b);
		//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			accept(action, values[i]);
			//
		} // for
			//
	}

	private static <T> void accept(@Nullable final Consumer<T> instance, final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

	@Nullable
	private static Integer getPhysicalNumberOfRows(@Nullable final Sheet instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfRows()) : null;
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, jcbJlptVocabulary)) {
			//
			final JlptVocabulary jv = Util.cast(JlptVocabulary.class,
					jcbJlptVocabulary != null ? jcbJlptVocabulary.getSelectedItem() : null);
			//
			if (jv != null) {
				//
				setSelectedItemByString(cbmJlptLevel, getLevel(jv));
				//
			} // if
				//
		} // if
			//
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

	@Override
	public void stateChanged(final ChangeEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, jsSpeechRate)) {
			//
			Util.setText(tfSpeechRate, Util.toString(getValue(jsSpeechRate)));
			//
		} // if
			//
	}

	@Override
	public void keyTyped(final KeyEvent evt) {
		//
	}

	@Override
	public void keyPressed(final KeyEvent evt) {
		//
	}

	@Override
	public void keyReleased(final KeyEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		final JTextComponent jtf = Util.cast(JTextComponent.class, source);
		//
		if (Objects.equals(source, tfListNames)) {
			//
			try {
				//
				setBackground(jtf, Color.WHITE);
				//
				final ObjectMapper om = getObjectMapper();
				//
				final List<?> list = getObjectList(om, Util.getText(jtf));
				//
				Util.setText(jlListNames, ObjectMapperUtil.writeValueAsString(om, list));
				//
				Util.setText(jlListNameCount, Integer.toString(IterableUtils.size(list)));
				//
			} catch (final Exception e) {
				//
				accept(x -> Util.setText(x, null), jlListNames, jlListNameCount);
				//
				setBackground(jtf, Color.RED);
				//
			} // try
				//
		} else if (Objects.equals(source, tfTextImport)) {
			//
			keyReleasedForTextImport(jtf);
			//
		} // if
			//
	}

	private void keyReleasedForTextImport(@Nullable final JTextComponent jTextComponent) {
		//
		keyReleasedForTextImport(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap), jTextComponent,
				cbmGaKuNenBeTsuKanJi);
		//
		// 常用漢字
		//
		final String text = Util.getText(jTextComponent);
		//
		final List<String> list = IValue0Util.getValue0(jouYouKanJiList);
		//
		if (StringUtils.isEmpty(text) || CollectionUtils.isEmpty(list)) {
			//
			setSelectedItem(cbmJouYouKanJi, null);
			//
		} else if (jouYouKanJiList != null) {
			//
			setSelectedItem(cbmJouYouKanJi,
					StringUtils.length(text) <= orElse(max(mapToInt(Util.stream(list), StringUtils::length)), 0)
							? Util.contains(list, text)
							: null);
			//
		} // if
			//
	}

	private static void keyReleasedForTextImport(final Multimap<String, String> multiMap,
			@Nullable final JTextComponent jTextComponent, final ComboBoxModel<String> comboBoxModel) {
		//
		final Collection<Entry<String, String>> entries = MultimapUtil.entries(multiMap);
		//
		if (Util.iterator(entries) == null) {
			//
			return;
			//
		} // if
			//
		List<String> list = null;
		//
		String key = null;
		//
		for (final Entry<String, String> en : entries) {
			//
			if (en == null || !StringUtils.equals(Util.getValue(en), Util.getText(jTextComponent))) {
				//
				continue;
				//
			} // if
				//
			if (!Util.contains(list = getIfNull(list, ArrayList::new), key = Util.getKey(en))) {
				//
				Util.add(list, key);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		final int size = CollectionUtils.size(list);
		//
		if (size == 1) {
			//
			setSelectedItem(comboBoxModel, get(list, 0));
			//
		} else if (size < 1) {
			//
			setSelectedItem(comboBoxModel, null);
			//
		} else {
			//
			throw new IllegalStateException();
			//
		} // if
			//
	}

	@Override
	public void insertUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), tfTextImportDocument)) {
			//
			setJlptVocabularyAndLevel(this);
			//
			// Remove all element(s) in "mcbmPronounication"
			//
			Util.forEach(reverseRange(0, getSize(mcbmPronunciation)), i -> removeElementAt(mcbmPronunciation, i));
			//
		} // if
			//
	}

	@Override
	public void removeUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), tfTextImportDocument)) {
			//
			setJlptVocabularyAndLevel(this);
			//
			// Remove all element(s) in "mcbmPronounication"
			//
			Util.forEach(reverseRange(0, getSize(mcbmPronunciation)), i -> removeElementAt(mcbmPronunciation, i));
			//
		} // if
			//
	}

	private static void setJlptVocabularyAndLevel(@Nullable final VoiceManager instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final String text = Util.getText(instance.tfTextImport);
		//
		final MutableComboBoxModel<JlptVocabulary> mcbmJlptVocabulary = instance.mcbmJlptVocabulary;
		//
		for (int i = getSize(mcbmJlptVocabulary) - 1; i >= 0; i--) {
			//
			removeElementAt(mcbmJlptVocabulary, i);
			//
		} // for
			//
		setSelectedItem(instance.cbmJlptLevel, null);
		//
		final IValue0<List<JlptVocabulary>> jlptVocabularyList = instance.jlptVocabularyList;
		//
		final List<JlptVocabulary> jlptVocabularies = IValue0Util.getValue0(jlptVocabularyList);
		//
		if (StringUtils.isNotEmpty(text) && CollectionUtils.isNotEmpty(jlptVocabularies)
				&& jlptVocabularyList != null) {
			//
			final ComboBoxModel<String> cbmJlptLevel = instance.cbmJlptLevel;
			//
			final List<JlptVocabulary> temp = Util.toList(Util.filter(Util.stream(jlptVocabularies),
					x -> Boolean.logicalOr(Objects.equals(text, getKanji(x)), Objects.equals(text, getKana(x)))));
			//
			forEach(temp, x -> addElement(mcbmJlptVocabulary, x));
			//
			if (IterableUtils.size(temp) > 1) {
				//
				setSelectedItem(instance.cbmJlptLevel, null);
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						Util.toList(Util.distinct(Util.map(Util.stream(temp), VoiceManager::getLevel))),
						x -> setSelectedItemByString(cbmJlptLevel, IterableUtils.get(x, 0)));
				//
				if (instance != null && instance.jcbJlptVocabulary != null) {
					//
					instance.itemStateChanged(new ItemEvent(instance.jcbJlptVocabulary, 0, "", 0));
					//
				} // if
					//
				return;
				//
			} // if
				//
			testAndAccept(Objects::nonNull,
					testAndApply(x -> IterableUtils.size(x) == 1, temp, x -> IterableUtils.get(x, 0), null),
					x -> setSelectedItemByString(cbmJlptLevel, getLevel(x)));
			//
			if (instance != null && instance.jcbJlptVocabulary != null) {
				//
				instance.itemStateChanged(new ItemEvent(instance.jcbJlptVocabulary, 0, "", 0));
				//
			} // if
				//
		} // if
			//
	}

	@Nullable
	private static String getKanji(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKanji() : null;
	}

	@Nullable
	private static String getKana(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKana() : null;
	}

	@Nullable
	private static String getLevel(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getLevel() : null;
	}

	private static void setSelectedItemByString(@Nullable final ComboBoxModel<String> cbm, final String string) {
		//
		IntList intList = null;
		//
		for (int i = 0; i < getSize(cbm); i++) {
			//
			if (StringUtils.equalsAnyIgnoreCase(getElementAt(cbm, i), string)) {
				//
				IntCollectionUtil.addInt(intList = ObjectUtils.getIfNull(intList, IntList::create), i);
				//
			} // if
				//
		} // for
			//
		final int size = intList != null ? intList.size() : 0;
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			setSelectedItem(cbm, getElementAt(cbm, intList.get(0)));
			//
		} // if
			//
	}

	@Nullable
	private static <E> E getElementAt(@Nullable final ListModel<E> instance, final int index) {
		return instance != null ? instance.getElementAt(index) : null;
	}

	private static int getSize(@Nullable final ListModel<?> instance) {
		return instance != null ? instance.getSize() : 0;
	}

	@Override
	public void changedUpdate(final DocumentEvent evt) {
		//
	}

	private static void setBackground(@Nullable final Component instance, @Nullable final Color color) {
		if (instance != null) {
			instance.setBackground(color);
		}
	}

	@Nullable
	private static javax.swing.text.Document getDocument(@Nullable final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
	}

	static interface ByteConverter {

		@Nullable
		byte[] convert(final byte[] source);

	}

	private static class AudioToFlacByteConverter implements ByteConverter {

		@Nullable
		private Integer audioStreamEncoderByteArrayLength = null;

		public void setAudioStreamEncoderByteArrayLength(final Object audioStreamEncoderByteArrayLength) {
			this.audioStreamEncoderByteArrayLength = toInteger(audioStreamEncoderByteArrayLength);
		}

		@Override
		public byte[] convert(final byte[] source) {
			//
			FLACStreamOutputStream flacStreamOutputStream = null;
			//
			try (final ByteArrayInputStream bais = testAndApply(x -> x != null && x.length > 0, source,
					ByteArrayInputStream::new, null);
					final AudioInputStream ais = bais != null ? AudioSystem.getAudioInputStream(bais) : null;
					final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				//
				final AudioFormat format = getFormat(ais);
				//
				final FLACEncoder flac = new FLACEncoder();
				//
				final StreamConfiguration streamConfiguration = createStreamConfiguration(format);
				//
				if (streamConfiguration != null) {
					//
					flac.setStreamConfiguration(streamConfiguration);
					//
				} // if
					//
				if (format != null) {
					//
					flac.setOutputStream(flacStreamOutputStream = new FLACStreamOutputStream(baos));
					//
					flac.openFLACStream();
					//
					AudioStreamEncoder.encodeAudioInputStream(ais,
							Math.max(intValue(audioStreamEncoderByteArrayLength, 0), 2), flac, false);
					//
				} // if
					//
				return toByteArray(baos);
				//
			} catch (final IOException | UnsupportedAudioFileException e) {
				//
				throw new RuntimeException(e);
				//
			} finally {
				//
				IOUtils.closeQuietly(flacStreamOutputStream);
				//
			} // try
				//
		}

		@Nullable
		private static AudioFormat getFormat(@Nullable final AudioInputStream instance) {
			return instance != null ? instance.getFormat() : null;
		}

		@Nullable
		private static StreamConfiguration createStreamConfiguration(@Nullable final AudioFormat format) {
			//
			if (format == null) {
				return null;
			} // if
				//
			final StreamConfiguration sc = new StreamConfiguration();
			//
			sc.setSampleRate((int) format.getSampleRate());
			sc.setBitsPerSample(format.getSampleSizeInBits());
			sc.setChannelCount(format.getChannels());
			//
			return sc;
			//
		}

	}

	@Nullable
	private static byte[] toByteArray(@Nullable final ByteArrayOutputStream instance) {
		return instance != null ? instance.toByteArray() : null;
	}

	private static class AudioToMp3ByteConverter implements ByteConverter, InitializingBean {

		@Note("Bitrate")
		@Nullable
		private Integer bitRate = null;

		@Note("Quality")
		@Nullable
		private Integer quality = null;

		@Nullable
		private Boolean vbr = null;

		public void setBitRate(final Object bitRate) {
			this.bitRate = toInteger(bitRate);
		}

		public void setQuality(final Object quality) throws IOException {
			//
			if ((this.quality = toInteger(quality)) == null) {
				//
				final Map<String, Integer> map = createQualityMap();
				//
				String string = Util.toString(quality);
				//
				if (Util.containsKey(map, string) || Util.containsKey(map, string = StringUtils.lowerCase(string))) {
					//
					setQuality(Util.get(map, string));
					//
				} // if
					//
			} // if
				//
		}

		@Nullable
		private Integer getQuality() throws IllegalAccessException {
			//
			if (quality != null) {
				return quality;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_QUALITY", true);
			//
			if (object instanceof Integer integer) {
				return integer;
			} // if
				//
			return null;
			//
		}

		public void setVbr(@Nullable final Object vbr) {
			//
			if (vbr == null) {
				//
				this.vbr = null;
				//
			} else if (vbr instanceof Boolean b) {
				//
				this.vbr = b;
				//
			} else {
				//
				final String string = Util.toString(vbr);
				//
				setVbr(StringUtils.isNotBlank(string) ? Boolean.valueOf(string) : null);
				//
			} // if
				//
		}

		@Nullable
		private Boolean getVbr() throws IllegalAccessException {
			//
			if (vbr != null) {
				return vbr;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_VBR", true);
			//
			if (object instanceof Boolean b) {
				return b;
			} // if
				//
			return null;
			//
		}

		@Override
		public void afterPropertiesSet() throws IOException, IllegalAccessException {
			//
			if (Objects.equals(Boolean.TRUE, getVbr())) {
				//
				final Range<Integer> range = createQualityRange();
				//
				final Integer q = getQuality();
				//
				if (range != null && !range.contains(q)) {
					//
					throw new IllegalStateException(String.format("Under VBR,\"quality\" cound be with in %1$s to %2$s",
							RangeUtil.lowerEndpoint(range), RangeUtil.upperEndpoint(range)));
					//
				} // if
					//
			} // if
				//
		}

		/**
		 * @see <a href=
		 *      "https://github.com/Sciss/jump3r/blob/master/src/main/java/de/sciss/jump3r/mp3/Lame.java#L1067">de.sciss.jump3r.mp3.Lame.lame_init_params(de.sciss.jump3r.mp3.LameGlobalFlags)</a>
		 * 
		 * @see de.sciss.jump3r.mp3.Lame#lame_init_params(de.sciss.jump3r.mp3.LameGlobalFlags)
		 */
		@Nullable
		private static Range<Integer> createQualityRange() throws IOException {
			//
			final Class<?> clz = Lame.class;
			//
			try (final InputStream is = getResourceAsStream(clz,
					String.format(CLASS_RESOURCE_FORMAT, StringUtils.replace(Util.getName(clz), ".", "/")))) {
				//
				final org.apache.bcel.classfile.Method[] ms = JavaClassUtil.getMethods(
						ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
				//
				Map<Integer, Integer> map = null;
				//
				for (int i = 0; ms != null && i < ms.length; i++) {
					//
					Util.put(map = getIfNull(map, LinkedHashMap::new), Integer.valueOf(i),
							createQuality(InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(
									testAndApply(Objects::nonNull, ms[i], x -> new MethodGen(x, null, null), null)))));
					//
				} // for
					//
				final List<Integer> counts = Util.toList(
						Util.filter(Util.map(Util.stream(Util.entrySet(map)), Util::getValue), Objects::nonNull));
				//
				final int size = IterableUtils.size(counts);
				//
				Integer count = null;
				//
				if (size == 1) {
					count = IterableUtils.get(counts, 0);
				} else if (size > 1) {
					throw new IllegalStateException();
				} // if
					//
				return count != null ? Range.closed(0, count.intValue() - 1) : null;
				//
			} // try
				//
		}

		@Nullable
		private static Integer createQuality(final Instruction[] ins) {
			//
			Instruction in = null;
			//
			// index
			//
			Integer index = null;
			//
			// count
			//
			Integer count = null;
			//
			final int length = ins != null ? ins.length : 0;
			//
			for (int i = 0; ins != null && i < length; i++) {
				//
				if ((in = ins[i]) instanceof NEWARRAY newArray && newArray != null
						&& newArray.getTypecode() == Const.T_FLOAT) {
					//
					index = Integer.valueOf(i);
					//
				} else if (Boolean.logicalAnd(in instanceof LDC, index != null)) {
					//
					count = Integer.valueOf(intValue(count, 0) + 1);
					//
					if (i < ins.length - 2 && ins[i + 2] instanceof ASTORE) {
						//
						break;
						//
					} // if
						//
				} // if
					//
			} // for
				//
			return count != null ? Integer.valueOf(count.intValue()) : null;
			//
		}

		/**
		 * @see <a href=
		 *      "https://github.com/Sciss/jump3r/blob/master/src/main/java/de/sciss/jump3r/lowlevel/LameEncoder.java#L598">de.sciss.jump3r.lowlevel.LameEncoder.string2quality(java.lang.String,int)</a>
		 * 
		 * @see de.sciss.jump3r.lowlevel.LameEncoder#string2quality(java.lang.String,int)
		 */
		@Nullable
		private static Map<String, Integer> createQualityMap() throws IOException {
			//
			Map<String, Integer> map = null;
			//
			final Class<?> clz = LameEncoder.class;
			//
			try (final InputStream is = getResourceAsStream(clz,
					String.format(CLASS_RESOURCE_FORMAT, StringUtils.replace(Util.getName(clz), ".", "/")))) {
				//
				final List<org.apache.bcel.classfile.Method> ms = Util
						.toList(Util.filter(
								testAndApply(Objects::nonNull,
										JavaClassUtil.getMethods(ClassParserUtil.parse(testAndApply(Objects::nonNull,
												is, x -> new ClassParser(x, null), null))),
										Arrays::stream, null),
								x -> Objects.equals(FieldOrMethodUtil.getName(x), "string2quality")));
				//
				org.apache.bcel.classfile.Method m = null;
				//
				IValue0<Map<String, Integer>> temp = null;
				//
				boolean found = false;
				//
				for (int i = 0; i < IterableUtils.size(ms); i++) {
					//
					if (found) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					if ((temp = createQualityMap(FieldOrMethodUtil.getConstantPool(m = IterableUtils.get(ms, i)),
							InstructionListUtil
									.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, m,
											x -> new MethodGen(x, null, null), null))))) != null) {
						//
						map = IValue0Util.getValue0(temp);
						//
						found = true;
						//
					} // if
						//
				} // for
					//
				return map;
				//
			} // try
				//
		}

		@Nullable
		private static IValue0<Map<String, Integer>> createQualityMap(final ConstantPool constantPool,
				@Nullable final Instruction[] instructions) {
			//
			IValue0<Map<String, Integer>> result = null;
			//
			String key = null;
			//
			Instruction instruction = null;
			//
			Number value = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = instructions[i]) instanceof LDC ldc) {
					//
					key = Util.toString(
							getValue(ldc, testAndApply(Objects::nonNull, constantPool, ConstantPoolGen::new, null)));
					//
				} else if (instruction instanceof BIPUSH biPush) {
					//
					Util.put(IValue0Util.getValue0(result = getIfNull(result, () -> Unit.with(new LinkedHashMap<>()))),
							key, (value = getValue(biPush)) != null ? value.intValue() : null);
					//
				} else if (instruction instanceof ICONST iConst) {
					//
					Util.put(IValue0Util.getValue0(result = getIfNull(result, () -> Unit.with(new LinkedHashMap<>()))),
							key, (value = getValue(iConst)) != null ? value.intValue() : null);
				} // if
					//
			} // for
				//
			return result;
			//
		}

		@Nullable
		private static Object getValue(@Nullable final LDC instance, @Nullable final ConstantPoolGen cpg) {
			return instance != null && cpg != null && cpg.getConstantPool() != null
					&& cpg.getConstantPool().getConstant(instance.getIndex()) != null ? instance.getValue(cpg) : null;
		}

		@Nullable
		private static Number getValue(@Nullable final ConstantPushInstruction instance) {
			return instance != null ? instance.getValue() : null;
		}

		private static int length(@Nullable final byte[] instance) {
			return instance != null ? instance.length : 0;
		}

		@Override
		@Nullable
		public byte[] convert(final byte[] source) {
			//
			LameEncoder encoder = null;
			//
			ByteArrayOutputStream baos = null;
			//
			try (final ByteArrayInputStream bais = testAndApply(x -> length(x) > 0, source, ByteArrayInputStream::new,
					null); final AudioInputStream ais = bais != null ? AudioSystem.getAudioInputStream(bais) : null) {
				//
				final byte[] inputBuffer = new byte[(encoder = ais != null ? new LameEncoder(ais.getFormat()
				//
				// bitRate
				//
						,
						ObjectUtils.defaultIfNull(bitRate,
								Util.cast(Integer.class,
										FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_BITRATE",
												true))),
						Util.cast(Integer.class,
								FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_CHANNEL_MODE", true))
						//
						, getQuality()// quality
						, getVbr()// vbr
				) : new LameEncoder()).getPCMBufferSize()];
				//
				final byte[] outputBuffer = new byte[encoder.getPCMBufferSize()];
				//
				int bytesRead;
				//
				int bytesWritten;
				//
				while (ais != null && 0 < (bytesRead = ais.read(inputBuffer))) {
					//
					bytesWritten = encoder.encodeBuffer(inputBuffer, 0, bytesRead, outputBuffer);
					//
					if ((baos = getIfNull(baos, ByteArrayOutputStream::new)) != null) {
						//
						baos.write(outputBuffer, 0, bytesWritten);
						//
					} // if
						//
				} // while
					//
			} catch (final IOException | UnsupportedAudioFileException | IllegalAccessException e) {
				//
				throw new RuntimeException(e);
				//
			} finally {
				//
				try {
					if (encoder != null && FieldUtils.readDeclaredField(encoder, "lame", true) != null) {
						//
						encoder.close();
						//
					} // if
				} catch (final IllegalAccessException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
			} // try
				//
			return toByteArray(baos);
			//
		}

	}

	private static void clear(@Nullable final DefaultTableModel instance) {
		//
		final Collection<?> dataVector = instance != null ? instance.getDataVector() : null;
		//
		if (dataVector != null) {
			//
			dataVector.clear();
			//
		} // if
			//
	}

	@Nullable
	private static Annotation[] getDeclaredAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	private static <T> boolean anyMatch(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(Util.getClass(instance)))
				&& instance.anyMatch(predicate);
		//
	}

	private static interface ObjectMap {

		@Nullable
		<T> T getObject(final Class<T> key);

		boolean containsObject(final Class<?> key);

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

		static <T> boolean containsObject(@Nullable final ObjectMap instance, final Class<T> key) {
			return instance != null && instance.containsObject(key);
		}

		static <T> void setObjectIfAbsent(final ObjectMap instance, final Class<T> key, final T value) {
			//
			if (!ObjectMap.containsObject(instance, key)) {
				//
				ObjectMap.setObject(instance, key, value);
				//
			} // if
				//
		}

	}

	@Nullable
	private static String format(@Nullable final NumberFormat instance, final double number) {
		return instance != null ? instance.format(number) : null;
	}

	private static interface IntMap<T> {

		@Nullable
		T getObject(final int key);

		boolean containsKey(final int key);

		void setObject(final int key, @Nullable final T value);

		@Nullable
		static <T> T getObject(@Nullable final IntMap<T> instance, final int key) {
			return instance != null ? instance.getObject(key) : null;
		}

	}

	private static interface IntIntMap {

		int getInt(final int key);

		boolean containsKey(final int key);

		void setInt(final int key, final int value);

	}

	@Nullable
	private static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	@Nullable
	private static String getFilePath(@Nullable final Voice instance) {
		return instance != null ? instance.getFilePath() : null;
	}

	@Nullable
	private static String getText(@Nullable final Voice instance) {
		return instance != null ? instance.getText() : null;
	}

	@Nullable
	private static String getRomaji(@Nullable final Voice instance) {
		return instance != null ? instance.getRomaji() : null;
	}

	@Nullable
	private static String getHiragana(@Nullable final Voice instance) {
		return instance != null ? instance.getHiragana() : null;
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, @Nullable final T t,
			@Nullable final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static <T, U, E extends Throwable> void accept(@Nullable final FailableBiConsumer<T, U, E> instance,
			final T t, @Nullable final U u) throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static class IH implements InvocationHandler {

		private Collection<Object> throwableStackTraceHexs = null;

		private Runnable runnable = null;

		private Map<Object, Object> objects = null;

		private Map<Object, Object> booleans = null;

		private Map<Object, Object> intMapObjects = null;

		private Map<Object, Object> intIntMapObjects = null;

		private Map<Object, Object> strings = null;

		private Map<Object, Object> getObjects() {
			if (objects == null) {
				objects = new LinkedHashMap<>();
			}
			return objects;
		}

		private Map<Object, Object> getIntMapObjects() {
			if (intMapObjects == null) {
				intMapObjects = new LinkedHashMap<>();
			}
			return intMapObjects;
		}

		private Map<Object, Object> getIntIntMapObjects() {
			if (intIntMapObjects == null) {
				intIntMapObjects = new LinkedHashMap<>();
			}
			return intIntMapObjects;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			IValue0<?> value = null;
			//
			if (proxy instanceof Runnable) {
				//
				if (runnable == null) {
					//
					throw new IllegalStateException("runnable is null");
					//
				} else if (runnable == proxy) {
					//
					throw new IllegalStateException("runnable==proxy");
					//
				} // if
					//
				value = handleRunnable(method, runnable, args, throwableStackTraceHexs);
				//
			} else if (proxy instanceof ObjectMap) {
				//
				value = handleObjectMap(methodName, getObjects(), args);
				//
			} else if (proxy instanceof IntMap) {
				//
				value = handleIntMap(methodName, getIntMapObjects(), args);
				//
			} else if (proxy instanceof IntIntMap) {
				//
				value = handleIntIntMap(methodName, getIntIntMapObjects(), args);
				//
			} // if
				//
			if (value != null) {
				//
				return IValue0Util.getValue0(value);
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		@Nullable
		private static IValue0<Object> handleRunnable(final Method method, final Runnable runnable, final Object[] args,
				final Collection<Object> throwableStackTraceHexs) throws Throwable {
			//
			try {
				//
				if (Objects.equals(Util.getName(method), "run")) {
					//
					return Unit.with(VoiceManager.invoke(method, runnable, args));
					//
				} // if
					//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetExceptionRootCause = ExceptionUtils.getRootCause(e.getTargetException());
				//
				if (targetExceptionRootCause != null) {
					//
					try (final Writer w = new StringWriter(); final PrintWriter ps = new PrintWriter(w)) {
						//
						targetExceptionRootCause.printStackTrace(ps);
						//
						final String hex = testAndApply(Objects::nonNull, getBytes(Util.toString(w)),
								DigestUtils::sha512Hex, null);
						//
						if (!Util.contains(throwableStackTraceHexs, hex)) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								//
								LoggerUtil.error(LOG, null, targetExceptionRootCause);
								//
							} else {
								//
								printStackTrace(targetExceptionRootCause);
								//
							} // if
								//
							Util.add(throwableStackTraceHexs, hex);
							//
						} // if
							//
					} // try
						//
				} // if
					//
				throw ObjectUtils.getIfNull(targetExceptionRootCause, RuntimeException::new);
				//
			} // try
				//
			return null;
			//
		}

		private static void printStackTrace(final Throwable throwable) {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Throwable.class), Arrays::stream, null),
					m -> m != null && StringUtils.equals(Util.getName(m), "printStackTrace")
							&& m.getParameterCount() == 0));
			//
			final Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
			//
			try {
				//
				testAndAccept(m -> throwable != null || isStatic(m), method, m -> VoiceManager.invoke(m, throwable));
				//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				printStackTrace(ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
						ExceptionUtils.getRootCause(e), e));
				//
			} catch (final ReflectiveOperationException e) {
				//
				printStackTrace(throwable);
				//
			} // try
				//
		}

		@Nullable
		private static IValue0<Object> handleObjectMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
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
				return Unit.with(Util.get(map, key));
				//
			} else if (Objects.equals(methodName, "containsObject") && args != null && args.length > 0) {
				//
				return Unit.with(Boolean.valueOf(Util.containsKey(map, args[0])));
				//
			} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
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
		private static IValue0<Object> handleIntMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
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
			} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
				//
				return Unit.with(Util.containsKey(map, args[0]));
				//
			} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
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
		private static IValue0<Object> handleIntIntMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getInt") && args != null && args.length > 0) {
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
			} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
				//
				return Unit.with(Util.containsKey(map, args[0]));
				//
			} else if (Objects.equals(methodName, "setInt") && args != null && args.length > 1) {
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

	@Nullable
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static <T> void forEach(@Nullable final Stream<T> instance, @Nullable final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		} // if
			//
	}

	private static <T, E extends Throwable> void forEach(@Nullable final Iterable<T> items,
			@Nullable final FailableConsumer<? super T, E> action) throws E {
		//
		if (Util.iterator(items) != null && (action != null || Proxy.isProxyClass(Util.getClass(items)))) {
			//
			for (final T item : items) {
				//
				FailableConsumerUtil.accept(action, item);
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static Workbook createMicrosoftSpeechObjectLibraryWorkbook(final SpeechApi speechApi,
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction, final String... attributes) {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(speechApi);
		//
		final String commonPrefix = String.join("",
				StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
		//
		String voiceId = null;
		//
		final String[] as = toArray(Util.toList(
				Util.filter(testAndApply(Objects::nonNull, attributes, Arrays::stream, null), StringUtils::isNotEmpty)),
				new String[] {});
		//
		ObjectMap objectMap = null;
		//
		for (int i = 0; voiceIds != null && as != null && i < voiceIds.length; i++) {
			//
			if (sheet == null) {
				//
				setMicrosoftSpeechObjectLibrarySheetFirstRow(
						sheet = WorkbookUtil.createSheet(workbook = getIfNull(workbook, XSSFWorkbook::new)), as);
				//
			} // if
				//
			if (sheet != null && (row = SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1)) == null) {
				//
				continue;
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : null, 0)),
					commonPrefix);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : 0, 0)),
					StringUtils.defaultIfBlank(testAndApply(StringUtils::contains, commonPrefix, voiceId = voiceIds[i],
							StringUtils::substringAfter, null), voiceId));
			//
			if (objectMap == null && (objectMap = Reflection.newProxy(ObjectMap.class, new IH())) != null) {
				//
				ObjectMap.setObject(objectMap, Workbook.class, workbook);
				//
				ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
				//
			} // if
				//
			ObjectMap.setObject(objectMap, Sheet.class, sheet);
			//
			ObjectMap.setObject(objectMap, Row.class, row);
			//
			setMicrosoftSpeechObjectLibrarySheet(objectMap, voiceId, as, languageCodeToTextObjIntFunction);
			//
		} // for
			//
		ObjectMap.setObject(objectMap, Sheet.class, WorkbookUtil.createSheet(workbook, "Locale ID"));
		//
		ObjectMap.setObject(objectMap, LocaleID[].class, LocaleID.values());
		//
		setLocaleIdSheet(objectMap);
		//
		setAutoFilter(sheet);
		//
		return workbook;
		//
	}

	private static void setAutoFilter(@Nullable final Sheet sheet) {
		//
		final Row row = sheet != null ? sheet.getRow(sheet.getLastRowNum()) : null;
		//
		if (sheet != null && row != null && sheet.getFirstRowNum() < sheet.getLastRowNum()) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
	}

	private static void setMicrosoftSpeechObjectLibrarySheetFirstRow(@Nullable final Sheet sheet,
			@Nullable final String[] columnNames) {
		//
		final Row row = sheet != null ? SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1) : null;
		//
		if (row != null) {
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), "Common Prefix");
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), "ID");
			//
			for (int j = 0; columnNames != null && j < columnNames.length; j++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), columnNames[j]);
				//
			} // for
				//
		} // if
			//
	}

	private static void setMicrosoftSpeechObjectLibrarySheet(@Nullable final ObjectMap objectMap, final String voiceId,
			final String[] attributes, final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		//
		final Workbook workbook = ObjectMap.getObject(objectMap, Workbook.class);
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		Cell cell = null;
		//
		Drawing<?> drawing = null;
		//
		CreationHelper creationHelper = null;
		//
		Comment comment = null;
		//
		// attribute
		//
		String attribute = null;
		//
		// value
		//
		String value = null;
		//
		for (int j = 0; attributes != null && j < attributes.length; j++) {
			//
			if (drawing == null) {
				//
				drawing = createDrawingPatriarch(sheet);
				//
			} // if
				//
			if (creationHelper == null) {
				//
				creationHelper = WorkbookUtil.getCreationHelper(workbook);
				//
			} // if
				//
			try {
				//
				CellUtil.setCellValue(
						cell = RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : 0, 0)),
						value = SpeechApi.getVoiceAttribute(ObjectMap.getObject(objectMap, SpeechApi.class), voiceId,
								attribute = attributes[j]));
				//
				if (Objects.equals(LANGUAGE, attribute)) {
					//
					setString(comment = createCellComment(drawing, createClientAnchor(creationHelper)),
							createRichTextString(creationHelper,
									ObjIntFunctionUtil.apply(languageCodeToTextObjIntFunction, value, 16)));
					//
					setCellComment(cell, comment);
					//
				} // if
					//
			} catch (final Error e) {
				//
				setString(comment = createCellComment(drawing, createClientAnchor(creationHelper)),
						createRichTextString(creationHelper, e.getMessage()));
				//
				setAuthor(comment, Util.getName(Util.getClass(e)));
				//
				setCellComment(cell, comment);
				//
			} // try
				//
		} // for
			//
	}

	private static void setLocaleIdSheet(@Nullable final ObjectMap objectMap) {
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		final LocaleID[] localeIds = ObjectMap.getObject(objectMap, LocaleID[].class);
		//
		LocaleID localeId = null;
		//
		List<Field> fs = null;
		//
		Row row = null;
		//
		Method methodIsAccessible = null;
		//
		for (int i = 0; localeIds != null && i < localeIds.length; i++) {
			//
			if ((localeId = localeIds[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if (fs == null) {
				//
				fs = Util.toList(sorted(Util.filter(
						testAndApply(Objects::nonNull, FieldUtils.getAllFields(LocaleID.class), Arrays::stream, null),
						x -> x != null && !Objects.equals(Util.getType(x), Util.getDeclaringClass(x))
								&& !x.isSynthetic() && !isStatic(x)),
						(a, b) -> StringUtils.compare(Util.getName(getPackage(Util.getDeclaringClass(a))),
								Util.getName(getPackage(Util.getDeclaringClass(b))))));
				//
			} // if
				//
				// Header Row
				//
			addLocaleIdSheetHeaderRow(sheet, fs);
			//
			ObjectMap.setObject(objectMap, Method.class, methodIsAccessible = getIfNull(methodIsAccessible,
					VoiceManager::getAccessibleObjectIsAccessibleMethod));
			//
			row = addLocaleIdRow(objectMap, fs, localeId);
			//
		} // for
			//
		if (sheet != null && row != null) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
	}

	@Nullable
	private static <T> Stream<T> sorted(@Nullable final Stream<T> instance,
			@Nullable final Comparator<? super T> comparator) {
		//
		return instance != null && (comparator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.sorted(comparator)
				: instance;
		//
	}

	@Nullable
	private static Row addLocaleIdRow(@Nullable final ObjectMap objectMap, @Nullable final List<Field> fs,
			final Object instance) {
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		Field f = null;
		//
		Object value = null;
		//
		Row row = null;
		//
		for (int j = 0; fs != null && j < fs.size(); j++) {
			//
			if ((f = fs.get(j)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(Util.getType(f), Integer.TYPE)) {
				//
				value = Integer.valueOf(Narcissus.getIntField(instance, f));
				//
			} else {
				//
				value = Narcissus.getField(instance, f);
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(
					row = getIfNull(row, () -> SheetUtil.createRow(sheet, intValue(getPhysicalNumberOfRows(sheet), 0))),
					intValue(getPhysicalNumberOfCells(row), 0)), Util.toString(value));
			//
		} // for
			//
		return row;
		//
	}

	private static void addLocaleIdSheetHeaderRow(final Sheet sheet, @Nullable final List<Field> fs) {
		//
		final int physicalNumberOfRows = intValue(getPhysicalNumberOfRows(sheet), 0);
		//
		if (physicalNumberOfRows == 0) {
			//
			Row row = null;
			//
			for (int j = 0; fs != null && j < fs.size(); j++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(
						row = getIfNull(row, () -> SheetUtil.createRow(sheet, intValue(physicalNumberOfRows, 0))),
						intValue(getPhysicalNumberOfCells(row), 0)), Util.getName(fs.get(j)));
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static Integer getPhysicalNumberOfCells(@Nullable final Row instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfCells()) : null;
	}

	@Nullable
	private static Drawing<?> createDrawingPatriarch(@Nullable final Sheet instance) {
		return instance != null ? instance.createDrawingPatriarch() : null;
	}

	@Nullable
	private static Comment createCellComment(@Nullable final Drawing<?> instance, @Nullable final ClientAnchor anchor) {
		return instance != null ? instance.createCellComment(anchor) : null;
	}

	@Nullable
	private static ClientAnchor createClientAnchor(@Nullable final CreationHelper instance) {
		return instance != null ? instance.createClientAnchor() : null;
	}

	@Nullable
	private static RichTextString createRichTextString(@Nullable final CreationHelper instance, final String text) {
		return instance != null ? instance.createRichTextString(text) : null;
	}

	private static void setString(@Nullable final Comment instance, @Nullable final RichTextString string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

	private static void setAuthor(@Nullable final Comment instance, @Nullable final String string) {
		if (instance != null) {
			instance.setAuthor(string);
		}
	}

	private static void setCellComment(@Nullable final Cell instance, @Nullable final Comment comment) {
		if (instance != null) {
			instance.setCellComment(comment);
		}
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static <T> boolean or(final Predicate<T> predicate, final T a, final T b, @Nullable final T... values) {
		//
		boolean result = Util.test(predicate, a) || Util.test(predicate, b);
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			result |= Util.test(predicate, values[i]);
			//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Matcher matcher(@Nullable final Pattern instance, @Nullable final CharSequence input) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(instance, Pattern.class.getDeclaredField("pattern")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return input != null ? instance.matcher(input) : null;
		//
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static boolean matches(@Nullable final Matcher instance) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(instance, Matcher.class.getDeclaredField("groups")) == null) {
				//
				return false;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.matches();
		//
	}

	private static void setPreferredWidth(final int width, @Nullable final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = Util.getPreferredSize(c)) == null) {
				//
				continue;
				//
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static void setPreferredWidth(final int width, @Nullable final Iterable<Component> cs) {
		//
		if (Util.iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = Util.getPreferredSize(c)) == null) {
					//
					continue;
					//
				} // if
					//
				c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static Double getPreferredWidth(@Nullable final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	@Nullable
	private static Double getPreferredHeight(@Nullable final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getHeight()) : null;
		//
	}

	public static void main(final String[] args) {
		//
		final VoiceManager instance = new VoiceManager();
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		instance.setLayout(new MigLayout());
		//
		instance.setTitle("Voice Manager");
		//
		instance.pack();
		//
		setVisible(instance, true);
		//
	}

}