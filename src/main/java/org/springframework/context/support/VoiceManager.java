package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
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
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sql.DataSource;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.CodeUtil;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.ObjectType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentUtil;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.ooxml.POIXMLPropertiesUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IntList;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jetty.http.HttpStatus;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.odftoolkit.odfdom.doc.OdfPresentationDocument;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.github.curiousoddman.rgxgen.RgxGen;
import com.google.common.base.Functions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;
import com.google.common.reflect.Reflection;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;
import com.healthmarketscience.jackcess.impl.DatabaseImpl.FileFormatDetails;
import com.healthmarketscience.jackcess.impl.JetFormat;
import com.healthmarketscience.jackcess.util.ImportUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;
import com.mariten.kanatools.KanaConverter;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;

import de.sciss.jump3r.lowlevel.LameEncoder;
import de.sciss.jump3r.mp3.Lame;
import domain.JlptVocabulary;
import domain.Voice;
import domain.Voice.Yomi;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import j2html.tags.specialized.ATag;
import j2html.tags.specialized.ATagUtil;
import jnafilechooser.api.WindowsFolderBrowser;
import mapper.VoiceMapper;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.javaflacencoder.AudioStreamEncoder;
import net.sourceforge.javaflacencoder.FLACEncoder;
import net.sourceforge.javaflacencoder.FLACStreamOutputStream;
import net.sourceforge.javaflacencoder.StreamConfiguration;

public class VoiceManager extends JFrame implements ActionListener, ItemListener, ChangeListener, KeyListener,
		EnvironmentAware, BeanFactoryPostProcessor, InitializingBean, DocumentListener {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static final int TEMP_FILE_MINIMUM_PREFIX_LENGTH = intValue(getTempFileMinimumPrefixLength(), 3);

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

	private static final String NO_FILE_SELECTED = "No File Selected";

	private static final String LANGUAGE = "Language";

	private static final String EXPORT_PRESENTATION = "exportPresentation";

	private static final String OVER_MP3_TITLE = "overMp3Title";

	private static final String ORDINAL_POSITION_AS_FILE_NAME_PREFIX = "ordinalPositionAsFileNamePrefix";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String EMBED_AUDIO_IN_PRESENTATION = "embedAudioInPresentation";

	private static final String HIDE_AUDIO_IMAGE_IN_PRESENTATION = "hideAudioImageInPresentation";

	private static final String SOURCE = "Source";

	private static final String PASSWORD = "Password";

	private static final String SPEECH_RATE = "Speech Rate";

	private static final String OLE_2_COMPOUND_DOCUMENT = "OLE 2 Compound Document";

	private static final String VOICE = "voice";

	private static final String SHA_512 = "SHA-512";

	private static final String FOLDER_IN_PRESENTATION = "folderInPresentation";

	private static final String MESSAGE_DIGEST_ALGORITHM = "messageDigestAlgorithm";

	private static final Predicate<File> EMPTY_FILE_PREDICATE = f -> f != null && f.exists() && isFile(f)
			&& longValue(length(f), 0) == 0;

	private static IValue0<Method> METHOD_RANDOM_ALPHABETIC = null;

	private transient PropertyResolver propertyResolver = null;

	@Note("Folder")
	private JTextComponent tfFolder = null;

	@Note("File Length")
	private JTextComponent tfFileLength = null;

	@Note("File Digest")
	private JTextComponent tfFileDigest = null;

	@Note("Text in TTS Pnael")
	private JTextComponent tfTextTts = null;

	@Note("Text in Import Pnael")
	private JTextComponent tfTextImport = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Katakana")
	private JTextComponent tfKatakana = null;

	@Note("Romaji")
	private JTextComponent tfRomaji = null;

	@Note("Speech Rate")
	private JTextComponent tfSpeechRate = null;

	@Note("Source")
	private JTextComponent tfSource = null;

	@Note("Provider Name")
	private JTextComponent tfProviderName = null;

	@Note("Provider Version")
	private JTextComponent tfProviderVersion = null;

	@Note("Provider Platform")
	private JTextComponent tfProviderPlatform = null;

	@Note("Speech Language Code")
	private JTextComponent tfSpeechLanguageCode = null;

	@Note("Speech Language Name")
	private JTextComponent tfSpeechLanguageName = null;

	@Note("Language")
	private JTextComponent tfLanguage = null;

	@Note("Speech Volume")
	private JTextComponent tfSpeechVolume = null;

	@Note("Current Processing File")
	private JTextComponent tfCurrentProcessingFile = null;

	@Note("Current Processing Sheet")
	private JTextComponent tfCurrentProcessingSheetName = null;

	@Note("Current Processing Voice")
	private JTextComponent tfCurrentProcessingVoice = null;

	@Note("List Name(s)")
	private JTextComponent tfListNames = null;

	private JTextComponent tfFile, tfPhraseCounter, tfPhraseTotal, tfJlptFolderNamePrefix,
			tfOrdinalPositionFileNamePrefix, tfIpaSymbol, tfExportFile, tfElapsed, tfDllPath, tfExportHtmlFileName,
			tfExportPassword, tfPronunciationPageUrl, tfPronunciationPageStatusCode = null;

	private transient ComboBoxModel<Yomi> cbmYomi = null;

	@Note("Voice ID")
	private transient ComboBoxModel<String> cbmVoiceId = null;

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

	private transient ComboBoxModel<Boolean> cbmIsKanji = null;

	@Url("https://ja.wikipedia.org/wiki/%E5%B8%B8%E7%94%A8%E6%BC%A2%E5%AD%97%E4%B8%80%E8%A6%A7")
	private transient ComboBoxModel<Boolean> cbmJouYouKanJi = null;

	private transient ComboBoxModel<Method> cbmSpeakMethod = null;

	private transient ComboBoxModel<Class> cbmWorkbookClass = null;

	private transient ComboBoxModel<EncryptionMode> cbmEncryptionMode = null;

	private transient ComboBoxModel<CompressionLevel> cbmCompressionLevel = null;

	private transient ComboBoxModel<FileFormat> cbmMicrosoftAccessFileFormat = null;

	private transient MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = null;

	private transient javax.swing.text.Document tfTextImportDocument = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateSlower = null;

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateNormal = null;

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateFaster = null;

	@Group("Conversion")
	private AbstractButton btnConvertToRomaji = null;

	@Group("Conversion")
	private AbstractButton btnConvertToKatakana = null;

	@Group("TTS Button")
	private AbstractButton btnSpeak = null;

	@Group("TTS Button")
	private AbstractButton btnWriteVoice = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Import File Template")
	private AbstractButton btnImportFileTemplate = null;

	@Note("Import a single Spread Sheet File")
	private AbstractButton btnImport = null;

	@Note("Import Spread Sheet File(s) within a specified folder")
	private AbstractButton btnImportWithinFolder = null;

	@Note("Over MP3 Title")
	private AbstractButton cbOverMp3Title = null;

	@Note("Ordinal Position As File Name Prefix")
	private AbstractButton cbOrdinalPositionAsFileNamePrefix = null;

	@Note("Export")
	private AbstractButton btnExport = null;

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

	@Note("Check Pronunciation Page")
	private AbstractButton btnPronunciationPageUrlCheck = null;

	@Note("IPA Symbol")
	private AbstractButton btnIpaSymbol = null;

	private AbstractButton cbUseTtsVoice = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface SystemClipboard {
	}

	@SystemClipboard
	private AbstractButton btnCopyRomaji = null;

	@SystemClipboard
	private AbstractButton btnCopyHiragana = null;

	@SystemClipboard
	private AbstractButton btnCopyKatakana = null;

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

	@Note("Slider For Speech Volumne")
	private JSlider jsSpeechVolume = null;

	@Note("Slider For Speech Rate")
	private JSlider jsSpeechRate = null;

	private JComboBox<Object> jcbVoiceId = null;

	private JComboBox<JlptVocabulary> jcbJlptVocabulary = null;

	@Note("List Name(s)")
	private JLabel jlListNames = null;

	@Note("List Count")
	private JLabel jlListNameCount = null;

	@Note("Import Exception")
	private DefaultTableModel tmImportException = null;

	@Note("Import Result")
	private DefaultTableModel tmImportResult = null;

	private transient SqlSessionFactory sqlSessionFactory = null;

	private String voiceFolder = null;

	private String outputFolder = null;

	private Map<String, String> outputFolderFileNameExpressions = null;

	private transient SpeechApi speechApi = null;

	@Note("MP3 Tag(s)")
	private String[] mp3Tags = null;

	private String[] microsoftSpeechObjectLibraryAttributeNames = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private transient Jakaroma jakaroma = null;

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

	private transient IValue0<List<String>> jlptLevels = null;

	private transient LayoutManager layoutManager = null;

	private IValue0<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private transient IValue0<String> microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle = null;

	@Note("Export HTML Template File")
	private String exportHtmlTemplateFile = null;

	@Note("Export Web Speech Synthesis HTML Template File")
	private String exportWebSpeechSynthesisHtmlTemplateFile = null;

	private transient freemarker.template.Configuration freeMarkerConfiguration = null;

	private Version freeMarkerVersion = null;

	private String exportPresentationTemplate = null;

	private String folderInPresentation = null;

	private String[] voiceIds = null;

	private FileFormat microsoftAccessFileFormat = null;

	private transient IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> workbookClassFailableSupplierMap = null;

	private Class<?> workbookClass = null;

	private transient Map<Object, Object> exportWebSpeechSynthesisHtmlTemplateProperties = null;

	private Duration jSoupParseTimeout = null;

	private IValue0<Multimap<String, String>> ipaSymbolMultimap = null;

	private String messageDigestAlgorithm = null;

	private transient IValue0<List<String>> jouYouKanJiList = null;

	private transient IValue0<List<JlptVocabulary>> jlptVocabularyList = null;

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		//
		this.configurableListableBeanFactory = configurableListableBeanFactory;
		//
		final Collection<?> formats = getByteConverterAttributeValues(configurableListableBeanFactory, FORMAT);
		//
		// cbmAudioFormatWrite
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatWrite = cast(MutableComboBoxModel.class, cbmAudioFormatWrite);
		//
		addElement(mcbmAudioFormatWrite, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
		//
		// cbmAudioFormatExecute
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatExecute = cast(MutableComboBoxModel.class,
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
	}

	private static <E> void addElement(final MutableComboBoxModel<E> instance, final E item) {
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

	private static Long length(final File instance) {
		return instance != null ? Long.valueOf(instance.length()) : null;
	}

	private static Integer getTempFileMinimumPrefixLength() {
		//
		Integer result = null;
		//
		final Class<?> clz = File.class;
		//
		try (final InputStream is = getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(getName(clz), ".", "/")))) {
			//
			final Object[] objectTypes = map(Stream.of("java.lang.String", "java.lang.String", "java.io.File"),
					ObjectType::getInstance).toArray();
			//
			final List<org.apache.bcel.classfile.Method> ms = toList(filter(
					testAndApply(Objects::nonNull,
							getMethods(ClassParserUtil
									.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null))),
							Arrays::stream, null),
					m -> m != null && Objects.equals(m.getName(), "createTempFile")
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

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static RuntimeException toRuntimeException(final Throwable instance) {
		//
		if (instance instanceof RuntimeException) {
			//
			return (RuntimeException) instance;
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

	private static Integer getTempFileMinimumPrefixLength(final org.apache.bcel.classfile.Method method) {
		//
		Integer result = null;
		//
		if (method != null) {
			//
			final byte[] bs = CodeUtil.getCode(method.getCode());
			//
			final String[] lines = StringUtils
					.split(StringUtils.trim(Utility.codeToString(bs, method.getConstantPool(), 0, length(bs))), '\n');
			//
			Pattern pattern1 = null, pattern2 = null;
			//
			Matcher matcher = null;
			//
			for (int i = 0; lines != null && i < lines.length; i++) {
				//
				if (//
				matches(matcher(pattern1 = ObjectUtils.getIfNull(pattern1,
						() -> Pattern.compile("^\\d+:\\s+if_icmpge\\s+#\\d+$")), lines[i]))
						//
						&& matches((matcher = matcher(pattern2 = ObjectUtils.getIfNull(pattern2,
								() -> Pattern.compile("^\\d+:\\s+iconst_(\\d+)$")), lines[i - 1])))
						&& matcher.groupCount() > 0 && (result = valueOf(matcher.group(1))) != null) {
					//
					break;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return result;
		//
	}

	private static int length(final byte[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static InputStream getResourceAsStream(final Class<?> instance, final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

	private static org.apache.bcel.classfile.Method[] getMethods(final JavaClass instance) {
		return instance != null ? instance.getMethods() : null;
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	public void setSqlSessionFactory(final SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setOutputFolder(final String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setOutputFolderFileNameExpressions(final Object value) throws JsonProcessingException {
		//
		if (value == null) {
			//
			this.outputFolderFileNameExpressions = null;
			//
			return;
			//
		} // if
			//
		final Map<?, ?> map = cast(Map.class, value);
		//
		if (entrySet(map) != null) {
			//
			for (final Entry<?, ?> entry : entrySet(map)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				put(outputFolderFileNameExpressions = ObjectUtils.getIfNull(outputFolderFileNameExpressions,
						LinkedHashMap::new), toString(getKey(entry)), toString(getValue(entry)));
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
		final Object object = testAndApply(StringUtils::isNotEmpty, toString(value),
				x -> ObjectMapperUtil.readValue(getObjectMapper(), x, Object.class), null);
		//
		if (object instanceof Map || object == null) {
			setOutputFolderFileNameExpressions(object);
		} else {
			throw new IllegalArgumentException(toString(getClass(object)));
		} // if
			//
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	public void setMp3Tags(final Object value) {
		//
		mp3Tags = toArray(toList(map(stream(getObjectList(getObjectMapper(), value)), VoiceManager::toString)),
				new String[] {});
		//
	}

	public void setMicrosoftSpeechObjectLibraryAttributeNames(final Object value) {
		//
		this.microsoftSpeechObjectLibraryAttributeNames = toArray(
				toList(map(stream(getObjectList(getObjectMapper(), value)), VoiceManager::toString)), new String[] {});
		//
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = Unit.with(jlptLevels);
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

	public void setMessageDigestAlgorithm(final String messageDigestAlgorithm) {
		this.messageDigestAlgorithm = messageDigestAlgorithm;
	}

	public void setExportWebSpeechSynthesisHtmlTemplateProperties(final Object arg) throws JsonProcessingException {
		//
		if (arg == null || arg instanceof Map) {
			//
			final Map<?, ?> map = (Map<?, ?>) arg;
			//
			if (iterator(entrySet(map)) != null) {
				//
				for (final Entry<?, ?> entry : entrySet(map)) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					put(exportWebSpeechSynthesisHtmlTemplateProperties = ObjectUtils
							.getIfNull(exportWebSpeechSynthesisHtmlTemplateProperties, LinkedHashMap::new),
							getKey(entry), getValue(entry));
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
					ObjectMapperUtil.readValue(getObjectMapper(), toString(arg), Object.class));
			//
		} else {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	public void setFreeMarkerVersion(final Object value) {
		//
		if (value instanceof Version) {
			//
			this.freeMarkerVersion = (Version) value;
			//
		} else if (value instanceof CharSequence) {
			//
			this.freeMarkerVersion = new Version(toString(value));
			//
		} else if (value instanceof Number) {
			//
			this.freeMarkerVersion = new Version(((Number) value).intValue());
			//
		} // if
			//
		final int[] ints = cast(int[].class, value);
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

	public void setExportPresentationTemplate(final String exportPresentationTemplate) {
		this.exportPresentationTemplate = exportPresentationTemplate;
	}

	public void setFolderInPresentation(final String folderInPresentation) {
		this.folderInPresentation = folderInPresentation;
	}

	public void setMicrosoftAccessFileFormat(final Object object) {
		//
		if (object instanceof FileFormat) {
			//
			this.microsoftAccessFileFormat = (FileFormat) object;
			//
			return;
			//
		} // if
			//
		final List<FileFormat> fileFormats = toList(
				filter(testAndApply(Objects::nonNull, FileFormat.values(), Arrays::stream, null),
						x -> StringUtils.startsWithIgnoreCase(name(x), toString(object))
								|| StringUtils.startsWithIgnoreCase(getFileExtension(x), toString(object))));
		//
		final int size = IterableUtils.size(fileFormats);
		//
		if (size == 1) {
			//
			this.microsoftAccessFileFormat = IterableUtils.get(fileFormats, 0);
			//
		} else if (size > 1) {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
	}

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> getWorkbookClassFailableSupplierMap() {
		//
		if (workbookClassFailableSupplierMap == null) {
			//
			workbookClassFailableSupplierMap = Unit.with(collect(
					stream(new Reflections("org.apache.poi").getSubTypesOf(Workbook.class)),
					Collectors.toMap(Functions.identity(), x -> new FailableSupplier<Workbook, RuntimeException>() {

						@Override
						public Workbook get() throws RuntimeException {
							try {
								//
								return cast(Workbook.class, newInstance(getDeclaredConstructor(x)));
								//
							} catch (final NoSuchMethodException | InstantiationException | IllegalAccessException
									| InvocationTargetException e) {
								//
								throw toRuntimeException(e);
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
		final String toString = toString(object);
		//
		if ((workbookClass = forName(toString)) != null) {
			//
			return;
			//
		} // if
			//
		final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map = IValue0Util
				.getValue0(getWorkbookClassFailableSupplierMap());
		//
		final List<Class<? extends Workbook>> classes = toList(
				filter(stream(keySet(map)), x -> Boolean.logicalOr(Objects.equals(getName(x), toString),
						StringUtils.endsWithIgnoreCase(getName(x), toString))));
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

	public void setjSoupParseTimeout(final Object object) {
		//
		if (object == null) {
			//
			this.jSoupParseTimeout = null;
			//
			return;
			//
		} else if (object instanceof Duration) {
			//
			this.jSoupParseTimeout = (Duration) object;
			//
			return;
			//
		} else if (object instanceof Number) {
			//
			this.jSoupParseTimeout = Duration.ofMillis(((Number) object).longValue());
			//
			return;
			//
		} // if
			//
		final String string = toString(object);
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

	public void setIpaSymbolMultimap(final Multimap<String, String> ipaSymbolMultimap) {
		this.ipaSymbolMultimap = Unit.with(ipaSymbolMultimap);
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

	private static IValue0<Class<? extends Workbook>> getWorkbookClass(
			final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map,
			final String string) {
		//
		final Set<Entry<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> entrySet = entrySet(
				map);
		//
		if (iterator(entrySet) == null) {
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
			try (final Workbook wb = get(getValue(en));
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
					testAndAccept((a, b) -> !contains(a, b), classes = getIfNull(classes, ArrayList::new), getKey(en),
							VoiceManager::add);
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

	private static <K> Set<K> keySet(final Map<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	private static List<Object> getObjectList(final ObjectMapper objectMapper, final Object value) {
		//
		if (value == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<?> iterable = cast(Iterable.class, value);
		//
		if (iterable != null) {
			//
			if (iterator(iterable) == null) {
				//
				return null;
				//
			} //
				//
			List<Object> list = null;
			//
			for (final Object v : iterable) {
				//
				add(list = ObjectUtils.getIfNull(list, ArrayList::new), v);
				//
			} // for
				//
			return ObjectUtils.getIfNull(list, ArrayList::new);
			//
		} // if
			//
		try {
			//
			final Object object = ObjectMapperUtil.readValue(objectMapper, toString(value), Object.class);
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
				throw new IllegalArgumentException(toString(getClass(object)));
				//
			} // if
		} catch (final JsonProcessingException e) {
			//
			return getObjectList(objectMapper, Collections.singleton(value));
			//
		} // try
			//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static class JTabbedPaneChangeListener implements ChangeListener {

		private Component component = null;

		private String[] importTabNames = null;

		@Override
		public void stateChanged(final ChangeEvent evt) {
			//
			final JTabbedPane jtp = cast(JTabbedPane.class, getSource(evt));
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
		final boolean isInstalled = isInstalled(speechApi);
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
					ObjectUtils.compare(valueOf(toString(get(getOsVersionInfoExMap(), "getMajor"))), 10) >= 10) {
				//
				jPanelWarning = createMicrosoftWindowsCompatibilityWarningJPanel(lm,
						microsoftWindowsCompatibilitySettingsPageUrl);
				//
			} // if
				//
			try {
				//
				voiceIds = getVoiceIds(speechApi);
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
				testAndAccept(y -> y instanceof MigLayout, lm, y -> add(x, WRAP));
				//
				testAndAccept(y -> !(y instanceof MigLayout), lm, y -> add(x));
				//
			});
			//
		} // if
			//
		testAndRun(!isInstalled,
				() -> add(craeteSpeechApiInstallationWarningJPanel(microsoftSpeechPlatformRuntimeDownloadPageUrl),
						WRAP));
		//
		jTabbedPane.addTab("TTS", createTtsPanel(cloneLayoutManager(), voiceIds));
		//
		jTabbedPane.addTab(TAB_TITLE_IMPORT_SINGLE, createSingleImportPanel(cloneLayoutManager(), voiceIds));
		//
		jTabbedPane.addTab(TAB_TITLE_IMPORT_BATCH, createBatchImportPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab("Export", createExportPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab("Misc", createMiscellaneousPanel(cloneLayoutManager(), voiceIds));
		//
		// maximum preferred height of all tab page(s)
		//
		final Double preferredHeight = getMaxPagePreferredHeight(jTabbedPane);
		//
		final freemarker.template.Configuration configuration = ObjectUtils.getIfNull(freeMarkerConfiguration,
				() -> new freemarker.template.Configuration(ObjectUtils.getIfNull(freeMarkerVersion,
						() -> freemarker.template.Configuration.getVersion())));
		//
		testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
				x -> setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
		//
		jTabbedPane.addTab("Help", createHelpPanel(preferredHeight, configuration, mediaFormatPageUrl,
				poiEncryptionPageUrl, jSoupParseTimeout));
		//
		final List<?> pages = cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getField(x, getDeclaredField(getClass(x), "pages")), null));
		//
		final Integer tabIndex = getTabIndexByTitle(pages, jTabbedPane, PropertyResolverUtil
				.getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.tabTitle"));
		//
		if (tabIndex != null) {
			//
			jTabbedPane.setSelectedIndex(tabIndex.intValue());
			//
		} // if
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
		if (jPanelWarning != null) {
			//
			final Double maxPreferredWidth = ObjectUtils.max(getPreferredWidth(jPanelWarning),
					getPreferredWidth(jTabbedPane));
			//
			if (maxPreferredWidth != null) {
				//
				setPreferredWidth(maxPreferredWidth.intValue(), jPanelWarning);
				//
			} // if
				//
		} // if
			//
		final Predicate<Component> predicate = createFocusableComponentPredicate(
				Arrays.asList(JLabel.class, JScrollPane.class, JProgressBar.class, JPanel.class));
		//
		forEach(toList(map(
				new FailableStream<>(stream(pages))
						.map(x -> Narcissus.getObjectField(x, getDeclaredField(getClass(x), "component"))).stream(),
				x -> cast(Container.class, x))), c -> {
					//
					// https://stackoverflow.com/questions/35508128/setting-personalized-focustraversalpolicy-on-tab-in-jtabbedpane
					//
					setFocusCycleRoot(c, true);
					//
					setFocusTraversalPolicy(c,
							new TabFocusTraversalPolicy(toList(
									filter(testAndApply(Objects::nonNull, getComponents(c), Arrays::stream, null),
											predicate))));
					//
				});
		//
	}

	private static Predicate<Component> createFocusableComponentPredicate(
			final Collection<Class<?>> compontentClassNotFocus) {
		//
		return x -> {
			//
			final JTextComponent jtc = cast(JTextComponent.class, x);
			//
			if (jtc != null) {
				//
				return jtc.isEditable();
				//
			} // if
				//
			return !contains(compontentClassNotFocus, getClass(x));
			//
		};
		//
	}

	private static void setFocusCycleRoot(final Container instance, final boolean focusCycleRoot) {
		if (instance != null) {
			instance.setFocusCycleRoot(focusCycleRoot);
		}
	}

	private static void setFocusTraversalPolicy(final Container instance, final FocusTraversalPolicy policy) {
		if (instance != null) {
			instance.setFocusTraversalPolicy(policy);
		}
	}

	private static Component[] getComponents(final Container instance) {
		return instance != null ? instance.getComponents() : null;
	}

	private static JPanel createVoiceIdWarningPanel(final VoiceManager instance) {
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
		jPanelWarning.add(pageTitle != null
				? new JLabelLink(
						new ATag().withHref(microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl).withText(title))
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
			jPanelWarning.add(jLabel, WRAP);
		} else {
			jPanelWarning.add(jLabel);
		} // if
			//
		ATag aTag = null;
		//
		try {
			//
			aTag = ATagUtil.createByUrl(microsoftWindowsCompatibilitySettingsPageUrl);
			//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		jPanelWarning.add(pageTitle != null ? new JLabelLink(aTag)
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
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		jPanelWarning.add(pageTitle != null ? new JLabelLink(aTag)
				: new JLabel(StringUtils.defaultIfBlank(pageTitle,
						"Download Microsoft Speech Platform - Runtime (Version 11) from Official Microsoft Download Center")));
		//
		return jPanelWarning;
		//
	}

	private static Double getMaxPagePreferredHeight(final Object jTabbedPane) throws NoSuchFieldException {
		//
		Double preferredHeight = null;
		//
		final List<?> pages = cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
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
				f = getDeclaredField(getClass(page), "component");
				//
			} // if
				//
			preferredHeight = ObjectUtils.max(preferredHeight,
					getPreferredHeight(cast(Component.class, page != null ? Narcissus.getObjectField(page, f) : null)));
			//
		} // for
			//
		return preferredHeight;
		//
	}

	private static TemplateLoader getTemplateLoader(final freemarker.template.Configuration instance) {
		return instance != null ? instance.getTemplateLoader() : null;
	}

	private static void setTemplateLoader(final freemarker.template.Configuration instance,
			final TemplateLoader templateLoader) {
		if (instance != null) {
			instance.setTemplateLoader(templateLoader);
		}
	}

	private static void setVisible(final Component instance, final boolean b) {
		if (instance != null) {
			instance.setVisible(b);
		}
	}

	private static IValue0<Boolean> IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = toList(filter(testAndApply(Objects::nonNull,
					getDeclaredMethods(forName("com.sun.jna.platform.win32.VersionHelpers")), Arrays::stream, null),
					m -> m != null && Objects.equals(getName(m), "IsWindows10OrGreater") && m.getParameterCount() == 0
							&& isStatic(m)));
			//
			if (ms == null || ms.isEmpty()) {
				//
				return null;
				//
			} // if
				//
			final Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
			//
			return Unit.with(cast(Boolean.class, invoke(m, null)));
			//
		} catch (final IllegalAccessException | InvocationTargetException e) {
			//
		} // try
			//
		return null;
		//
	}

	private static Map<String, Object> getOsVersionInfoExMap() {
		//
		try {
			//
			final Object osVersionInfoEx = getOsVersionInfoEx();
			//
			final List<Method> ms = osVersionInfoEx != null ? toList(filter(
					testAndApply(Objects::nonNull, getDeclaredMethods(getClass(osVersionInfoEx)), Arrays::stream, null),
					x -> x != null && !Objects.equals(x.getReturnType(), Void.TYPE))) : null;
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
					put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), getName(m), invoke(m, osVersionInfoEx));
					//
				} // for
					//
				return map;
				//
			} // if
				//
		} catch (final IllegalAccessException | InvocationTargetException | InstantiationException e) {
			//
		} // try
			//
		return null;
		//
	}

	private static Object getOsVersionInfoEx()
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		final Class<?> clz = forName("com.sun.jna.platform.win32.Kernel32");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#INSTANCE
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(clz), Arrays::stream, null),
						f -> Objects.equals(getName(f), "INSTANCE") && Objects.equals(getType(f), clz) && isStatic(f)));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		final Class<?> clzOsVersionInfoEx = forName("com.sun.jna.platform.win32.WinNT$OSVERSIONINFOEX");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#GetVersionEx-com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX-
		//
		final List<Method> ms = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredMethods(clz), Arrays::stream, null),
						m -> Objects.equals(getName(m), "GetVersionEx")
								&& Arrays.equals(new Class[] { clzOsVersionInfoEx }, getParameterTypes(m))));
		//
		Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
		//
		final Object osVersionInfoEx = clzOsVersionInfoEx != null ? clzOsVersionInfoEx.newInstance() : null;
		//
		return Objects.equals(Boolean.TRUE, invoke(m, FieldUtils.readStaticField(f), osVersionInfoEx)) ? osVersionInfoEx
				: null;
		//
	}

	private static Class<?>[] getParameterTypes(final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static Object getInstance(final SpeechApi speechApi) {
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(VoiceManager.getClass(speechApi)),
						Arrays::stream, null), f -> Objects.equals(getName(f), "instance")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		try {
			//
			if (f != null) {
				//
				f.setAccessible(true);
				//
				return f.get(speechApi);
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
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

	private static IValue0<String> getPageTitle(final String url, final Duration timeout) {
		//
		try {
			//
			return Unit
					.with(ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
							ElementUtil.getElementsByTag(testAndApply(Objects::nonNull,
									testAndApply(StringUtils::isNotBlank, url, URL::new, null),
									x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null), "title"),
							x -> get(x, 0), null)));
			//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return null;
		//
	}

	private static Long toMillis(final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static class JLabelLink extends JLabel {

		private static final long serialVersionUID = 8848505138795752227L;

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
						testAndAccept(Objects::nonNull, testAndApply(Objects::nonNull, url, URI::new, null), x -> {
							//
							browse(Desktop.getDesktop(), x);
							//
						});
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

		private JLabelLink(final ATag aTag) {
			//
			super(getChildrenAsString(aTag));
			//
			this.url = getValue(getAttributeByName(aTag, "href"));
			//
		}

		private static String getValue(final Attribute instance) {
			return instance != null ? instance.getValue() : null;
		}

		private static String getChildrenAsString(final ContainerTag<?> instance) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "children", true), null)));
				//
				return testAndApply(Objects::nonNull,
						toList(map(
								testAndApply(Objects::nonNull, spliterator, x -> StreamSupport.stream(x, false), null),
								VoiceManager::toString)),
						x -> String.join("", x), null);
				//
			} catch (final IllegalAccessException e) {
				//
				return null;
				//
			} // try
				//
		}

		private static Attribute getAttributeByName(final Tag<?> instance, final String name) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "attributes", true), null)));
				//
				final List<Attribute> as = toList(filter(
						map(testAndApply(Objects::nonNull, spliterator, x -> StreamSupport.stream(x, false), null),
								x -> cast(Attribute.class, x)),
						a -> {
							return Objects.equals(name, a != null ? a.getName() : null);
						}));
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

		private static Color darker(final Color instance) {
			return instance != null ? instance.darker() : null;
		}

	}

	private static Integer getTabIndexByTitle(final List<?> pages, final Object jTabbedPane, final Object title)
			throws NoSuchFieldException {
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
				fieldTitle = getFieldByName(getClass(page), "title");
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

	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final List<Field> fs = toList(
				filter(stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(getName(f), name)));
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

	private static Field getDeclaredField(final Class<?> instance, final String name) throws NoSuchFieldException {
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
			testAndAccept((a, b) -> a != null && a.containsProperty(b), propertyResolver,
					"net.miginfocom.swing.MigLayout.layoutConstraints",
					(a, b) -> migLayout.setLayoutConstraints(PropertyResolverUtil.getProperty(a, b)));
			//
			lm = migLayout;
			//
		} else if (lm instanceof Serializable) {
			//
			lm = cast(LayoutManager.class, SerializationUtils.clone((Serializable) lm));
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

		public Component getComponentAfter(final Container focusCycleRoot, final Component aComponent) {
			//
			final int size = IterableUtils.size(components);
			//
			return size != 0 ? get(components, (intValue(indexOf(components, aComponent), -1) + 1) % size) : null;
			//
		}

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

		public Component getDefaultComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		public Component getLastComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, components.size() - 1) : null;
		}

		public Component getFirstComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		@Override
		public Component getInitialComponent(final Window window) {
			return window != null ? super.getInitialComponent(window) : null;
		}

		private static Integer indexOf(final List<?> items, final Object item) {
			return items != null ? Integer.valueOf(items.indexOf(item)) : null;
		}

	}

	private JPanel createTtsPanel(final LayoutManager layoutManager, final String[] voiceIds) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.add(new JLabel("Text"));
		//
		panel.add(
				tfTextTts = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.text")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		// Provider
		//
		panel.add(new JLabel("Provider"));
		//
		final Provider provider = cast(Provider.class, speechApi);
		//
		panel.add(tfProviderName = new JTextField(getProviderName(provider)),
				String.format("%1$s,span %2$s", GROWX, 3));
		//
		final boolean isInstalled = isInstalled(speechApi);
		//
		// Provider Version
		//
		testAndAccept(Objects::nonNull, tfProviderVersion = createProviderVersionJTextComponent(isInstalled, provider),
				x -> panel.add(x, String.format("span %1$s,width %2$s", 3, 64)));
		//
		// Provider Platform
		//
		testAndAccept(Objects::nonNull,
				tfProviderPlatform = createProviderPlatformJTextComponent(isInstalled, provider),
				x -> panel.add(x, WRAP));
		//
		// Voice Id
		//
		panel.add(new JLabel("Voice Id"));
		//
		if ((cbmVoiceId = testAndApply(Objects::nonNull, voiceIds,
				x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
			//
			final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer();
			//
			voiceIdListCellRenderer.listCellRenderer = (jcbVoiceId = new JComboBox(cbmVoiceId)).getRenderer();
			//
			jcbVoiceId.addItemListener(this);
			//
			voiceIdListCellRenderer.commonPrefix = String.join("",
					StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
			//
			jcbVoiceId.setRenderer(voiceIdListCellRenderer);
			//
			panel.add(jcbVoiceId, String.format("%1$s,span %2$s", GROWX, 3));
			//
			panel.add(tfSpeechLanguageCode = new JTextField(), String.format("width %1$s,span %2$s", 30, 2));
			//
			panel.add(tfSpeechLanguageName = new JTextField(),
					String.format("%1$s,span %2$s,width %3$s", WRAP, 2, 230));
			//
		} // if
			//
			// Voice ID
			//
		final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
		//
		ObjectMap.setObject(objectMap, PropertyResolver.class, propertyResolver);
		//
		ObjectMap.setObject(objectMap, String[].class, voiceIds);
		//
		ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
		//
		testAndAccept(Objects::nonNull, getVoiceId(objectMap),
				x -> setSelectedItem(cbmVoiceId, IValue0Util.getValue0(x)));
		//
		// Speech Rate
		//
		final Object speechApiInstance = getInstance(speechApi);
		//
		final Lookup lookup = cast(Lookup.class, speechApiInstance);
		//
		final Predicate<String> predicate = (a) -> contains(lookup, "rate", a);
		//
		final FailableFunction<String, Object, RuntimeException> function = (a) -> get(lookup, "rate", a);
		//
		if (Boolean.logicalAnd(test(predicate, "min"), test(predicate, "max"))) {
			//
			final Range<Integer> range = createRange(toInteger(testAndApply(predicate, "min", function, null)),
					toInteger(testAndApply(predicate, "max", function, null)));
			//
			if (hasLowerBound(range) && hasUpperBound(range) && lowerEndpoint(range) != null
					&& upperEndpoint(range) != null) {
				//
				panel.add(new JLabel(SPEECH_RATE), "aligny top");
				//
				panel.add(jsSpeechRate = new JSlider(intValue(lowerEndpoint(range), 0),
						intValue(upperEndpoint(range), 0)), String.format("%1$s,span %2$s", GROWX, 7));
				//
				jsSpeechRate.setMajorTickSpacing(1);
				//
				jsSpeechRate.setPaintTicks(true);
				//
				jsSpeechRate.setPaintLabels(true);
				//
				panel.add(tfSpeechRate = new JTextField(), String.format("%1$s,width %2$s", WRAP, 24));
				//
				setEditable(false, tfSpeechRate);
				//
				setValue(jsSpeechRate,
						PropertyResolverUtil.getProperty(propertyResolver,
								"org.springframework.context.support.VoiceManager.speechRate"),
						a -> stateChanged(new ChangeEvent(a)));
				//
				panel.add(new JLabel(""));
				//
				panel.add(btnSpeechRateSlower = new JButton("Slower"));
				//
				panel.add(btnSpeechRateNormal = new JButton("Normal"));
				//
				panel.add(btnSpeechRateFaster = new JButton("Faster"), WRAP);
				//
				final Double maxWidth = ObjectUtils.max(getPreferredWidth(btnSpeechRateSlower),
						getPreferredWidth(btnSpeechRateNormal), getPreferredWidth(btnSpeechRateFaster));
				//
				if (maxWidth != null) {
					//
					setPreferredWidth(maxWidth.intValue(), btnSpeechRateSlower, btnSpeechRateNormal,
							btnSpeechRateFaster);
					//
				} // if
					//
			} // if
				//
		} // if
			//
		if (jsSpeechRate == null && tfSpeechRate == null) {
			//
			panel.add(new JLabel(SPEECH_RATE));
			//
			panel.add(
					tfSpeechRate = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
							"org.springframework.context.support.VoiceManager.speechRate")),
					String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
			//
		} // if
			//
			// Speech Volume
			//
		panel.add(new JLabel("Speech Volume"), "aligny top");
		//
		final Range<Integer> speechVolumeRange = createVolumeRange(speechApiInstance);
		//
		final Integer upperEnpoint = testAndApply(VoiceManager::hasUpperBound, speechVolumeRange,
				VoiceManager::upperEndpoint, null);
		//
		panel.add(jsSpeechVolume = new JSlider(intValue(
				testAndApply(VoiceManager::hasLowerBound, speechVolumeRange, VoiceManager::lowerEndpoint, null), 0),
				intValue(upperEnpoint, 100)), String.format("%1$s,span %2$s", GROWX, 3));
		//
		setSpeechVolume(valueOf(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.speechVolume")), upperEnpoint);
		//
		jsSpeechVolume.setMajorTickSpacing(10);
		//
		jsSpeechVolume.setPaintTicks(true);
		//
		jsSpeechVolume.setPaintLabels(true);
		//
		panel.add(tfSpeechVolume = new JTextField(), String.format("%1$s,width %2$s", WRAP, 27));
		//
		// Button(s)
		//
		panel.add(new JLabel());
		//
		final List<Method> speakMethods = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(getClass(speechApiInstance)), Arrays::stream, null),
				m -> isAnnotationPresent(m, SpeakMethod.class)));
		//
		JComboBox<Method> jcbSpeakMethod = null;
		//
		if (CollectionUtils.isNotEmpty(speakMethods)) {
			//
			jcbSpeakMethod = new JComboBox<>(
					cbmSpeakMethod = testAndApply(Objects::nonNull, toArray(speakMethods, new Method[] {}),
							DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>()));
			//
			final ListCellRenderer<?> listCellRenderer = jcbSpeakMethod.getRenderer();
			//
			jcbSpeakMethod.setRenderer(new ListCellRenderer<Object>() {

				@Override
				public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
						final boolean isSelected, final boolean cellHasFocus) {
					//
					return VoiceManager.getListCellRendererComponent((ListCellRenderer) listCellRenderer, list,
							getName(cast(Member.class, value)), index, isSelected, cellHasFocus);
					//
				}
			});
			//
			panel.add(jcbSpeakMethod);
			//
		} // if
			//
		panel.add(btnSpeak = new JButton("Speak"), WRAP);
		//
		// Audio Format
		//
		panel.add(new JLabel("Write To File"));
		//
		final JComboBox<Object> jcbAudioFormat = new JComboBox(
				cbmAudioFormatWrite = new DefaultComboBoxModel<Object>());
		//
		panel.add(jcbAudioFormat);
		//
		panel.add(btnWriteVoice = new JButton("Write"), WRAP);
		//
		// elapsed
		//
		panel.add(new JLabel("Elapsed"));
		//
		panel.add(tfElapsed = new JTextField(), String.format("%1$s,span %2$s", GROWX, 2));
		//
		setEditable(false, tfSpeechLanguageCode, tfSpeechLanguageName, tfProviderName, tfProviderVersion,
				tfProviderPlatform, tfSpeechVolume, tfElapsed);
		//
		addActionListener(this, btnSpeak, btnWriteVoice, btnSpeechRateSlower, btnSpeechRateNormal, btnSpeechRateFaster);
		//
		setEnabled(Boolean.logicalAnd(isInstalled, voiceIds != null), btnSpeak, btnWriteVoice);
		//
		addChangeListener(this, jsSpeechVolume, jsSpeechRate);
		//
		final Double maxWidth = ObjectUtils.max(getPreferredWidth(jcbAudioFormat), getPreferredWidth(jcbSpeakMethod));
		//
		if (maxWidth != null) {
			//
			setPreferredWidth(maxWidth.intValue(), jcbAudioFormat, jcbSpeakMethod);
			//
		} // if
			//
			// Find the maximum width of the "java.awt.Component" instance from the field
			// with "org.springframework.context.support.VoiceManager.Group" annotation with
			// same value (i.e. "TTS Button"),
			// then set the maximum width to each "java.awt.Component" in the list.
			//
		final Collection<Component> cs = getObjectsByGroupAnnotation(this, "TTS Button", Component.class);
		//
		setPreferredWidth(intValue(
				getPreferredWidth(
						Collections.max(cs, (a, b) -> ObjectUtils.compare(getPreferredWidth(a), getPreferredWidth(b)))),
				0), cs);
		//
		return panel;
		//
	}

	private static IValue0<?> getVoiceId(final ObjectMap objectMap) {
		//
		final PropertyResolver propertyResolver = ObjectMap.getObject(objectMap, PropertyResolver.class);
		//
		final SpeechApi speechApi = ObjectMap.getObject(objectMap, SpeechApi.class);
		//
		final String[] voiceIds = ObjectMap.getObject(objectMap, String[].class);
		//
		final String voiceId = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.voiceId");
		//
		List<?> temp = toList(filter(testAndApply(Objects::nonNull, voiceIds, Arrays::stream, null),
				x -> Boolean.logicalOr(Objects.equals(x, voiceId),
						Objects.equals(getVoiceAttribute(speechApi, x, "Name"), voiceId))));
		//
		int size = IterableUtils.size(temp);
		//
		if (size == 1) {
			//
			return Unit.with(get(temp, 0));
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final String voiceLanguage = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.voiceLanguage");
		//
		if (StringUtils.isNotEmpty(voiceLanguage)) {
			//
			if ((size = IterableUtils
					.size(temp = toList(filter(testAndApply(Objects::nonNull, voiceIds, Arrays::stream, null), x -> {
						//
						final String language = getVoiceAttribute(speechApi, x, LANGUAGE);
						//
						return StringUtils.startsWithIgnoreCase(convertLanguageCodeToText(language, 16), voiceLanguage)
								|| Objects.equals(language, voiceLanguage);
						//
					})))) == 1) {
				//
				return Unit.with(get(temp, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException(
						String.format("There are more than one Voice %1$s found for Lanaguge \"%2$s\"",
								toList(map(stream(temp), x -> StringUtils.wrap(toString(x), "\""))), voiceLanguage));
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private void setSpeechVolume(final Number speechVolume, final Number upperEnpoint) {
		//
		if (speechVolume != null) {
			//
			setValue(jsSpeechVolume, Math.min(speechVolume.intValue(), intValue(upperEnpoint, 100)));
			//
		} else if (upperEnpoint != null) {
			//
			setValue(jsSpeechVolume, upperEnpoint.intValue());
			//
		} // if
			//
	}

	private static JTextComponent createProviderVersionJTextComponent(final boolean isInstalled,
			final Provider provider) {
		//
		return isInstalled ? new JTextField(getProviderVersion(provider)) : new JTextField();
		//
	}

	private static JTextComponent createProviderPlatformJTextComponent(final boolean isInstalled,
			final Provider provider) {
		//
		try {
			//
			return isInstalled ? new JTextField(getProviderPlatform(provider)) : new JTextField();
			//
		} catch (final Error e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return null;
		//
	}

	private static boolean hasLowerBound(final Range<?> instance) {
		return instance != null && instance.hasLowerBound();
	}

	private static boolean hasUpperBound(final Range<?> instance) {
		return instance != null && instance.hasUpperBound();
	}

	private static <C extends Comparable<C>> C lowerEndpoint(final Range<C> instance) {
		return instance != null ? instance.lowerEndpoint() : null;
	}

	private static <C extends Comparable<C>> C upperEndpoint(final Range<C> instance) {
		return instance != null ? instance.upperEndpoint() : null;
	}

	private static void setValue(final JSlider instance, final int n) {
		if (instance != null) {
			instance.setValue(n);
		}
	}

	private static String getProviderPlatform(final Provider instance) {
		return instance != null ? instance.getProviderPlatform() : null;
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
	}

	private static void setValue(final JSlider instance, final String string, final Consumer<JSlider> consumer) {
		//
		Integer i = valueOf(string);
		//
		if (i != null) {
			//
			if (instance != null && i >= instance.getMinimum() && i <= instance.getMaximum()) {
				//
				setValue(instance, i.intValue());
				//
				accept(consumer, instance);
				//
			} // if
				//
		} else {
			//
			final List<Method> ms = toList(
					filter(testAndApply(Objects::nonNull, getDeclaredMethods(getClass(instance)), Arrays::stream, null),
							x -> x != null && Objects.equals(x.getReturnType(), Integer.TYPE)
									&& x.getParameterCount() == 0
									&& StringUtils.startsWithIgnoreCase(getName(x), "get" + string)));
			//
			final int size = CollectionUtils.size(ms);
			//
			if (size == 1) {
				//
				setValue(instance, get(ms, 0), consumer, GraphicsEnvironment.isHeadless());
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException(
						collect(map(stream(ms), VoiceManager::getName), Collectors.joining(",")));
				//
			} // if
				//
		} // if
			//
	}

	private static void setValue(final JSlider instance, final Method method, final Consumer<JSlider> consumer,
			final boolean headless) {
		//
		try {
			//
			final Integer i = cast(Integer.class, invoke(method, instance));
			//
			if (i != null) {
				//
				instance.setValue(i.intValue());
				//
				accept(consumer, instance);
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
	}

	private static <E> E get(final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	private static void addChangeListener(final ChangeListener changeListener, final JSlider instance,
			final JSlider... vs) {
		//
		addChangeListener(instance, changeListener);
		//
		for (int i = 0; vs != null && i < vs.length; i++) {
			//
			addChangeListener(vs[i], changeListener);
			//
		} // for
			//
	}

	private static void addChangeListener(final JSlider instance, final ChangeListener changeListener) {
		if (instance != null) {
			instance.addChangeListener(changeListener);
		}
	}

	private JPanel createSingleImportPanel(final LayoutManager layoutManager, final String[] voiceIds) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		// Language
		//
		panel.add(new JLabel(LANGUAGE));
		//
		panel.add(
				tfLanguage = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.language")),
				String.format("%1$s,span %2$s", GROWX, 11));
		//
		// Source
		//
		panel.add(new JLabel(SOURCE));
		//
		panel.add(
				tfSource = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.source")),
				String.format("%1$s,span %2$s,wmin %3$s", GROWX, 4, 50));
		//
		// Kanji
		//
		panel.add(new JLabel("Kanji"));
		//
		List<Boolean> booleans = null;
		//
		try {
			//
			booleans = getBooleanValues();
			//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		add(booleans, 0, null);
		//
		final Supplier<ComboBoxModel<Boolean>> booleanComboBoxModelSupplier = new BooleanComboBoxModelSupplier(
				booleans);
		//
		panel.add(new JComboBox<>(cbmIsKanji = get(booleanComboBoxModelSupplier)));
		//
		setSelectedItem(cbmIsKanji,
				testAndApply(StringUtils::isNotEmpty, PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.isKanji"), Boolean::valueOf, null));
		//
		// 学年別漢字 GaKuNenBeTsuKanJi
		//
		panel.add(new JLabel("学年別漢字"));
		//
		final Set<String> gaKuNenBeTsuKanJiList = MultimapUtil.keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap));
		//
		panel.add(new JComboBox<>(cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
				ArrayUtils.insert(0, toArray(gaKuNenBeTsuKanJiList, new String[] {}), (String) null),
				DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>())), String.format("span %1$s", 2));
		//
		final String string = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.gaKuNenBeTsuKanJi");
		//
		if (StringUtils.isNotEmpty(string)) {
			//
			setSelectedItem(cbmGaKuNenBeTsuKanJi, string);
			//
		} // if
			//
			// 常用漢字 JoYoKanJi
			//
		panel.add(new JLabel("常用漢字"));
		//
		panel.add(new JComboBox<>(cbmJouYouKanJi = get(booleanComboBoxModelSupplier)),
				String.format("%1$s,span %2$s", WRAP, 1));
		//
		setSelectedItem(cbmJouYouKanJi,
				testAndApply(StringUtils::isNotEmpty, PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.yoKoKanJi"), Boolean::valueOf, null));
		//
		// Text
		//
		panel.add(new JLabel("Text"));
		//
		panel.add(
				tfTextImport = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.text")),
				String.format("%1$s,span %2$s", GROWX, 20));
		//
		tfTextImport.addKeyListener(this);
		//
		addDocumentListener(tfTextImportDocument = tfTextImport.getDocument(), this);
		//
		(jcbJlptVocabulary = new JComboBox<JlptVocabulary>(cbmJlptVocabulary = new DefaultComboBoxModel<>()))
				.addItemListener(this);
		//
		panel.add(jcbJlptVocabulary, String.format("span %1$s", 3));
		//
		final ListCellRenderer<?> render = jcbJlptVocabulary.getRenderer();
		//
		jcbJlptVocabulary.setRenderer(new ListCellRenderer<JlptVocabulary>() {

			@Override
			public Component getListCellRendererComponent(final JList<? extends JlptVocabulary> list,
					final JlptVocabulary value, final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return VoiceManager.getListCellRendererComponent(((ListCellRenderer) render), list,
						testAndApply(Objects::nonNull, value,
								x -> String.join(" ", getKanji(x), getKana(x), getLevel(x)), null),
						index, isSelected, cellHasFocus);
				//
			}
		});
		//
		panel.add(btnConvertToRomaji = new JButton("Convert To Romaji"), String.format("%1$s", WRAP));
		//
		// Yomi
		//
		panel.add(new JLabel("Yomi"));
		//
		final Yomi[] yomis = Yomi.values();
		//
		final JComboBox<Object> jcbYomi = new JComboBox(
				cbmYomi = new DefaultComboBoxModel<>(ArrayUtils.insert(0, yomis, (Yomi) null)));
		//
		final ListCellRenderer<Object> listCellRenderer = jcbYomi.getRenderer();
		//
		Map<String, String> yomiNameMap = null;
		//
		try {
			//
			yomiNameMap = createYomiNameMap();
			//
		} catch (final Exception e) {
			//
			errorOrAssertOrShowException(GraphicsEnvironment.isHeadless(), e);
			//
		} // try
			//
		final Map<String, String> yomiNameMapTemp = yomiNameMap;
		//
		jcbYomi.setRenderer(new ListCellRenderer<Object>() {

			@Override
			public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				//
				final String name = name(cast(Enum.class, value));
				//
				if (containsKey(yomiNameMapTemp, name)) {
					//
					return VoiceManager.getListCellRendererComponent(listCellRenderer, list, get(yomiNameMapTemp, name),
							index, isSelected, cellHasFocus);
					//
				} // if
					//
				return VoiceManager.getListCellRendererComponent(listCellRenderer, list, value, index, isSelected,
						cellHasFocus);
				//
			}
		});
		//
		panel.add(jcbYomi);
		//
		panel.add(new JLabel("IPA"));
		//
		panel.add(
				tfIpaSymbol = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ipaSymbol")),
				String.format("%1$s,wmin %2$s,span %3$s", GROWX, 100, 2));
		//
		final List<Yomi> yomiList = toList(filter(testAndApply(Objects::nonNull, yomis, Arrays::stream, null),
				y -> Objects.equals(name(y), PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.yomi"))));
		//
		final int size = IterableUtils.size(yomiList);
		//
		if (size == 1) {
			//
			setSelectedItem(cbmYomi, get(yomiList, 0));
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		panel.add(btnIpaSymbol = new JButton("Get IPA"));
		//
		panel.add(new JLabel("List(s)"));
		//
		final String tags = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.listNames");
		//
		panel.add(tfListNames = new JTextField(tags), String.format("%1$s,span %2$s", GROWX, 9));
		//
		tfListNames.addKeyListener(this);
		//
		panel.add(jlListNames = new JLabel(), String.format("span %1$s", 5));
		//
		panel.add(jlListNameCount = new JLabel(), String.format("wmax %1$s", 20));
		//
		if (StringUtils.isNotBlank(tags)) {
			//
			keyReleased(new KeyEvent(tfListNames, 0, 0, 0, 0, ' '));
			//
		} // if
			//
			// JLPT level
			//
		panel.add(new JLabel("JLPT Level"));
		//
		final List<String> jlptLevelList = testAndApply(Objects::nonNull, IValue0Util.getValue0(jlptLevels),
				ArrayList::new, null);
		//
		testAndAccept(CollectionUtils::isNotEmpty, jlptLevelList, x -> add(x, 0, null));
		//
		panel.add(new JComboBox<String>(
				cbmJlptLevel = testAndApply(Objects::nonNull, toArray(jlptLevelList, new String[] {}),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<String>())),
				WRAP);
		//
		setSelectedItem(cbmJlptLevel, PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptLevel"));
		//
		// Romaji
		//
		panel.add(new JLabel("Romaji"));
		//
		panel.add(
				tfRomaji = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.romaji")),
				String.format("%1$s,span %2$s", GROWX, 23));
		//
		panel.add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		// Hiragana
		//
		panel.add(new JLabel("Hiragana"));
		//
		panel.add(
				tfHiragana = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.hiragana")),
				String.format("%1$s,span %2$s", GROWX, 13));
		//
		panel.add(btnCopyHiragana = new JButton("Copy"));
		//
		panel.add(btnConvertToKatakana = new JButton("Convert"));
		//
		// Katakana
		//
		panel.add(new JLabel("Katakana"), String.format("span %1$s", 2));
		//
		panel.add(
				tfKatakana = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.katakana")),
				String.format("%1$s,span %2$s", GROWX, 6));
		//
		panel.add(btnCopyKatakana = new JButton("Copy"), WRAP);
		//
		// Pronunciation Page URL
		//
		panel.add(new JLabel("Pronunciation Page URL"), String.format("span %1$s", 2));
		//
		panel.add(
				tfPronunciationPageUrl = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.pronunciationPageUrl")),
				String.format("%1$s,span %2$s", GROWX, 20));
		//
		panel.add(new JLabel("Status"));
		//
		panel.add(tfPronunciationPageStatusCode = new JTextField(), String.format("wmin %1$s", 30));
		//
		panel.add(btnPronunciationPageUrlCheck = new JButton("Check"), WRAP);
		//
		panel.add(new JLabel());
		//
		panel.add(cbUseTtsVoice = new JCheckBox("TTS Voice"));
		//
		cbUseTtsVoice.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.useTtsVoice")));
		//
		// Audio Format
		//
		panel.add(new JComboBox(cbmAudioFormatExecute = new DefaultComboBoxModel<Object>()));
		//
		panel.add(btnExecute = new JButton("Execute"), String.format("span %1$s", 3));
		//
		Double maxPreferredWidth = ObjectUtils.max(getPreferredWidth(tfListNames),
				getPreferredWidth(btnConvertToKatakana));
		//
		if (maxPreferredWidth != null) {
			//
			final Dimension d = tfListNames.getSize();
			//
			if (d != null) {
				//
				tfListNames.setMinimumSize(new Dimension(maxPreferredWidth.intValue(), (int) d.getHeight()));
				//
			} // if
				//
			setPreferredWidth(maxPreferredWidth.intValue(), tfListNames, btnConvertToKatakana);
			//
		} // if
			//
		if ((maxPreferredWidth = ObjectUtils.max(getPreferredWidth(jcbYomi), getPreferredWidth(tfHiragana),
				getPreferredWidth(tfKatakana))) != null) {
			//
			final Dimension d = tfKatakana.getSize();
			//
			if (d != null) {
				//
				tfKatakana.setMinimumSize(new Dimension(maxPreferredWidth.intValue(), (int) d.getHeight()));
				//
			} // if
				//
			setPreferredWidth(maxPreferredWidth.intValue(), tfHiragana, tfKatakana);
			//
		} // if
			//
		addActionListener(this, btnExecute, btnConvertToRomaji, btnConvertToKatakana, btnCopyRomaji, btnCopyHiragana,
				btnCopyKatakana, btnPronunciationPageUrlCheck, btnIpaSymbol);
		//
		setEnabled(voiceIds != null, cbUseTtsVoice);
		//
		setEnabled(false, tfPronunciationPageStatusCode);
		//
		return panel;
		//
	}

	private static void addDocumentListener(final javax.swing.text.Document instance, final DocumentListener listener) {
		if (instance != null) {
			instance.addDocumentListener(listener);
		}
	}

	private static <T> T get(final Supplier<T> instance) {
		return instance != null ? instance.get() : null;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static int getLength(final NodeList instance) {
		return instance != null ? instance.getLength() : 0;
	}

	private static Node item(final NodeList instance, final int index) {
		return instance != null ? instance.item(index) : null;
	}

	private static class BooleanComboBoxModelSupplier implements Supplier<ComboBoxModel<Boolean>> {

		private Collection<Boolean> booleans = null;

		private BooleanComboBoxModelSupplier(final Collection<Boolean> booleans) {
			//
			this.booleans = booleans;
			//
		}

		@Override
		public ComboBoxModel<Boolean> get() {
			//
			return booleans != null ? new DefaultComboBoxModel<>(toArray(booleans, new Boolean[] {}))
					: new DefaultComboBoxModel<>();
			//
		}

	}

	private static List<Boolean> getBooleanValues() throws IllegalAccessException {
		//
		List<Boolean> list = null;
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(Boolean.class), Arrays::stream, null),
						f -> Objects.equals(getType(f), Boolean.class)));
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			if (!Objects.equals(Boolean.class, getType(f = get(fs, i)))) {
				//
				continue;
				//
			} // if
				//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), cast(Boolean.class, f.get(null)));
			//
		} // for
			//
		return list;
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private JPanel createBatchImportPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.add(new JLabel("Import"));
		//
		panel.add(btnImport = new JButton("Import a Single Spreadsheet"), String.format("span %1$s", 2));
		//
		panel.add(btnImportWithinFolder = new JButton("Import SpreadSheet(s) Within a Folder"),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		panel.add(new JLabel("Import Template"));
		//
		panel.add(cbImportFileTemplateGenerateBlankRow = new JCheckBox("Generate a Blank Row"));
		//
		cbImportFileTemplateGenerateBlankRow
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.importFileTemplateGenerateBlankRow")));
		//
		panel.add(btnImportFileTemplate = new JButton("Generate"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		// Progress
		//
		panel.add(progressBarImport = new JProgressBar(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5));
		//
		progressBarImport.setStringPainted(true);
		//
		panel.add(new JLabel("Current Processing File"));
		//
		final String wrap = String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5);
		//
		panel.add(tfCurrentProcessingFile = new JTextField(), wrap);
		//
		panel.add(new JLabel("Current Processing Sheet"));
		//
		panel.add(tfCurrentProcessingSheetName = new JTextField(),
				String.format("%1$s,wmin %2$s,span %3$s", GROWX, 300, 2));
		//
		panel.add(new JLabel("Voice"));
		//
		panel.add(tfCurrentProcessingVoice = new JTextField(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		panel.add(new JLabel("Import Result"));
		//
		JScrollPane scp = new JScrollPane(new JTable(tmImportResult = new DefaultTableModel(
				new Object[] { "Number Of Sheet Processed", "Number of Voice Processed" }, 0)));
		//
		Dimension d = getPreferredSize(scp);
		//
		if (d != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 40));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		panel.add(scp, wrap);
		//
		panel.add(new JLabel("Import Exception"));
		//
		panel.add(
				scp = new JScrollPane(new JTable(
						tmImportException = new DefaultTableModel(new Object[] { "Text", "Romaji", "Exception" }, 0))),
				wrap);
		//
		if ((d = getPreferredSize(scp)) != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 55));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		addActionListener(this, btnImport, btnImportWithinFolder, btnImportFileTemplate);
		//
		setEditable(false, tfCurrentProcessingFile, tfCurrentProcessingSheetName, tfCurrentProcessingVoice);
		//
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		final boolean b = folder != null && folder.exists() && folder.isDirectory();
		//
		setEnabled(btnExecute, b);
		//
		if (!b) {
			//
			setToolTipText(btnExecute,
					String.format("Please create \"%1$s\" folder.", folder != null ? folder.getAbsolutePath() : null));
			//
		} // if
			//
		return panel;
		//
	}

	private JPanel createImportResultPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.setBorder(BorderFactory.createTitledBorder("Import Result"));
		//
		panel.add(new JLabel("Folder"));
		//
		panel.add(
				tfFolder = new JTextField(
						getAbsolutePath(testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null))),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		panel.add(new JLabel("File"));
		//
		panel.add(tfFile = new JTextField(), GROWX);
		//
		panel.add(new JLabel("Length"));
		//
		panel.add(tfFileLength = new JTextField(), String.format("%1$s,%2$s", GROWX, WRAP));
		//
		panel.add(new JLabel("File Digest"));
		//
		panel.add(tfFileDigest = new JTextField(), String.format("%1$s,span %2$s", GROWX, 3));
		//
		setEditable(false, tfFolder, tfFile, tfFileLength, tfFileDigest);
		//
		return panel;
		//
	}

	private static Class<?> getType(final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	private static String getAbsolutePath(final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	private static class MicrosoftAccessFileFormatListCellRenderer implements ListCellRenderer<Object> {

		private String commonPrefix = null;

		private ListCellRenderer<Object> listCellRenderer = null;

		@Override
		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			//
			final FileFormat fileFormat = cast(FileFormat.class, value);
			//
			if (fileFormat != null) {
				//
				final String toString = VoiceManager.toString(value);
				//
				int idx = StringUtils.indexOf(toString, ' ');
				//
				StringBuilder sb = null;
				//
				if (idx >= 0) {
					//
					(sb = new StringBuilder(StringUtils.defaultString(toString))).insert(idx,
							String.format(" (%1$s)", fileFormat.getFileExtension()));
					//
				} // if
					//
				if ((idx = StringUtils.indexOf(sb, commonPrefix)) >= 0
						&& (sb = getIfNull(sb, () -> new StringBuilder(StringUtils.defaultString(toString)))) != null) {
					//
					sb.delete(idx, idx + StringUtils.length(commonPrefix));
					//
				} // if
					//
				if (sb != null) {
					//
					return VoiceManager.getListCellRendererComponent(this, list, sb, index, isSelected, cellHasFocus);
					//
				} // if
					//
			} // if
				//
			return VoiceManager.getListCellRendererComponent(listCellRenderer, list, value, index, isSelected,
					cellHasFocus);
			//
		}

	}

	private JPanel createExportPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		// Microsoft Excel Format
		//
		panel.add(new JLabel("Workbook Implementation"), String.format("span %1$s", 5));
		//
		final List<Class<? extends Workbook>> classes = testAndApply(Objects::nonNull,
				new Reflections("org.apache.poi").getSubTypesOf(Workbook.class), ArrayList::new, null);
		//
		if (classes != null) {
			//
			classes.add(0, null);
			//
		} // if
			//
		final JComboBox<Class> jcbClass = new JComboBox<Class>(
				cbmWorkbookClass = new DefaultComboBoxModel<>((Class[]) toArray(classes, new Class[] {})));
		//
		testAndRun(contains(keySet(IValue0Util.getValue0(getWorkbookClassFailableSupplierMap())), workbookClass),
				() -> setSelectedItem(cbmWorkbookClass, workbookClass));
		//
		final ListCellRenderer<?> lcr = jcbClass.getRenderer();
		//
		jcbClass.setRenderer(new ListCellRenderer<>() {

			@Override
			public Component getListCellRendererComponent(final JList<? extends Class> list, final Class value,
					final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return VoiceManager.getListCellRendererComponent((ListCellRenderer) lcr, list, getName(value), index,
						isSelected, cellHasFocus);
				//
			}

		});
		//
		panel.add(jcbClass, String.format("%1$s,span %2$s", WRAP, 7));
		//
		// Encryption Mode
		//
		panel.add(new JLabel("Encryption Mode"), String.format("span %1$s", 5));
		//
		final EncryptionMode[] encryptionModes = EncryptionMode.values();
		//
		panel.add(
				new JComboBox<>(cbmEncryptionMode = new DefaultComboBoxModel<>(
						ArrayUtils.insert(0, encryptionModes, (EncryptionMode) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(
				cbmEncryptionMode, orElse(
						findFirst(filter(Arrays.stream(encryptionModes),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.encryptionMode")))),
						null));
		//
		// ZIP Compression Level
		//
		panel.add(new JLabel("ZIP Compression Level"), String.format("span %1$s", 5));
		//
		final CompressionLevel[] compressionLevels = CompressionLevel.values();
		//
		panel.add(
				new JComboBox<>(cbmCompressionLevel = new DefaultComboBoxModel<>(
						ArrayUtils.insert(0, compressionLevels, (CompressionLevel) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(
				cbmCompressionLevel, orElse(
						findFirst(filter(Arrays.stream(compressionLevels),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.compressionLevel")))),
						null));
		//
		// Password
		//
		panel.add(new JLabel(PASSWORD), String.format("span %1$s", 4));
		//
		panel.add(
				tfExportPassword = new JPasswordField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.exportPassword")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		panel.add(new JLabel("Option(s)"), String.format("span %1$s", 4));
		//
		panel.add(cbOverMp3Title = new JCheckBox("Over Mp3 Title"), String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbOverMp3Title.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.overMp3Title")));
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbOrdinalPositionAsFileNamePrefix = new JCheckBox("Ordinal Position As File Name Prefix"),
				String.format("span %1$s", 4));
		//
		cbOrdinalPositionAsFileNamePrefix
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ordinalPositionAsFileNamePrefix")));
		//
		panel.add(new JLabel("Prefix"));
		//
		panel.add(
				tfOrdinalPositionFileNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ordinalPositionFileNamePrefix")),
				String.format("%1$s,%2$s", GROWX, WRAP));
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbJlptAsFolder = new JCheckBox("JLPT As Folder"), String.format("span %1$s", 3));
		//
		cbJlptAsFolder.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptAsFolder")));
		//
		panel.add(new JLabel("Folder Name Prefix"));
		//
		panel.add(
				tfJlptFolderNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.jlptFolderNamePrefix")),
				String.format("%1$s,wmin %2$s,span %3$s", WRAP, 100, 2));
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbExportHtml = new JCheckBox("Export HTML"), String.format("span %1$s", 3));
		//
		cbExportHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtml")));
		//
		// File Name
		//
		panel.add(new JLabel("File Name"));
		//
		panel.add(
				tfExportHtmlFileName = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.exportHtmlFileName")),
				String.format("wmin %1$s,span %2$s", 100, 2));
		//
		final String[] fileExtensions = getFileExtensions(ContentType.HTML);
		//
		setToolTipText(tfExportHtmlFileName,
				String.format("If the File Name does not ends with %1$s, file extension \".%2$s\" will be appended.",
						collect(map(testAndApply(Objects::nonNull, fileExtensions, Arrays::stream, null).sorted(),
								x -> StringUtils.wrap(StringUtils.join('.', x), '"')), Collectors.joining(" or ")),
						StringUtils.defaultIfBlank(
								orElse(max(fileExtensions != null ? Arrays.stream(fileExtensions) : null,
										(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null),
								"")));
		//
		panel.add(cbExportListHtml = new JCheckBox("Export List"));
		//
		cbExportListHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtml")));
		//
		// cbExportWebSpeechSynthesisHtml
		//
		panel.add(cbExportWebSpeechSynthesisHtml = new JCheckBox("Export Web Speech Synthesis HTML"));
		//
		cbExportWebSpeechSynthesisHtml
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.exportWebSpeechSynthesisHtml")));
		//
		// ZIP
		//
		panel.add(cbExportHtmlAsZip = new JCheckBox("Zip"));
		//
		cbExportHtmlAsZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtmlAsZip")));
		//
		panel.add(cbExportHtmlRemoveAfterZip = new JCheckBox("Remove Html After Zip"), WRAP);
		//
		cbExportHtmlRemoveAfterZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtmlRemoveAfterZip")));
		//
		// Export List Sheet
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbExportListSheet = new JCheckBox("Export List Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportListSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListSheet")));
		//
		// Export JLPT Sheet
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbExportJlptSheet = new JCheckBox("Export JLPT Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportJlptSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportJlptSheet")));
		//
		// Export Presentation
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbExportPresentation = new JCheckBox("Export Presentation"), String.format(",span %1$s", 3));
		//
		setToolTipText(cbExportPresentation, "Open Document Format (odp) format, Libre Office is recommended");
		//
		cbExportPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportPresentation")));
		//
		panel.add(cbEmbedAudioInPresentation = new JCheckBox("Emded Audio In Presentation"),
				String.format("span %1$s", 3));
		//
		cbEmbedAudioInPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.embedAudioInPresentation")));
		//
		panel.add(cbHideAudioImageInPresentation = new JCheckBox("Hide Audio Image In Presentation"),
				String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbHideAudioImageInPresentation
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.hideAudioImageInPresentation")));
		//
		// Export Microsoft Access
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbExportMicrosoftAccess = new JCheckBox("Export Microsoft Access"), String.format("span %1$s", 3));
		//
		cbExportMicrosoftAccess.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportMicrosoftAccess")));
		//
		final Map<?, ?> fileFormatDetails = new LinkedHashMap<>();
		//
		try {
			//
			testAndAccept(Objects::nonNull, cast(Map.class,
					FieldUtils.readDeclaredStaticField(DatabaseImpl.class, "FILE_FORMAT_DETAILS", true)), x -> {
						putAll(fileFormatDetails, x);
					});
			//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final FileFormat[] fileFormats = testAndApply(Objects::nonNull,
				toArray(toList(filter(testAndApply(Objects::nonNull, FileFormat.values(), Arrays::stream, null), x -> {
					//
					final FileFormatDetails ffds = cast(FileFormatDetails.class, get(fileFormatDetails, x));
					//
					final JetFormat format = getFormat(ffds);
					//
					return Boolean.logicalAnd(
							Objects.equals(Boolean.FALSE, format != null ? Boolean.valueOf(format.READ_ONLY) : null),
							getEmptyFilePath(ffds) != null);
					//
				})), new FileFormat[] {}), x -> ArrayUtils.addFirst(x, null), null);
		//
		final JComboBox<FileFormat> jcbFileFormat = new JComboBox<>(
				cbmMicrosoftAccessFileFormat = new DefaultComboBoxModel<>(fileFormats));
		//
		final MicrosoftAccessFileFormatListCellRenderer mafflcr = new MicrosoftAccessFileFormatListCellRenderer();
		//
		mafflcr.listCellRenderer = (ListCellRenderer) jcbFileFormat.getRenderer();
		//
		mafflcr.commonPrefix = orElse(filter(
				map(map(map(testAndApply(Objects::nonNull, fileFormats, Arrays::stream, null),
						DatabaseImpl::getFileFormatDetails), VoiceManager::getFormat), VoiceManager::toString),
				Objects::nonNull).reduce(StringUtils::getCommonPrefix), null);
		//
		jcbFileFormat.setRenderer(mafflcr);
		//
		panel.add(jcbFileFormat, String.format("%1$s,span %2$s", WRAP, 5));
		//
		cbmMicrosoftAccessFileFormat.setSelectedItem(microsoftAccessFileFormat);
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(btnExport = new JButton("Export"), WRAP);
		//
		// Progress
		//
		panel.add(tfPhraseCounter = new JTextField("0"));
		//
		panel.add(new JLabel("/"), "align center");
		//
		panel.add(tfPhraseTotal = new JTextField("0"));
		//
		panel.add(progressBarExport = new JProgressBar(), String.format("%1$s,span %2$s", GROWX, 7));
		//
		progressBarExport.setStringPainted(true);
		//
		addActionListener(this, btnExport);
		//
		setEditable(false, tfPhraseCounter, tfPhraseTotal);
		//
		return panel;
		//
	}

	private static <K, V> void putAll(final Map<K, V> a, final Map<? extends K, ? extends V> b) {
		if (a != null && b != null) {
			a.putAll(b);
		}
	}

	private static JetFormat getFormat(final FileFormatDetails instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static String getEmptyFilePath(final FileFormatDetails instance) {
		return instance != null ? instance.getEmptyFilePath() : null;
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
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
			panel.add(
					new JLabelLink(new ATag().withHref(microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl).withText(
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
			panelDllPath.add(tfDllPath = new JTextField(toString(IValue0Util.getValue0(dllPath))));
			//
			panelDllPath.add(btnDllPathCopy = new JButton("Copy"));
			//
			panel.add(panelDllPath, String.format("%1$s,span %2$s", WRAP, 2));
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
		if (StringUtils
				.isNotBlank(append(btnExportMicrosoftSpeechObjectLibraryInformationName, StringUtils.defaultIfBlank(
						getProviderName(cast(Provider.class, speechApi)), "Microsoft Speech Object Library")))) {
			//
			append(btnExportMicrosoftSpeechObjectLibraryInformationName, ' ');
			//
		} // if
			//
		panel.add(
				btnExportMicrosoftSpeechObjectLibraryInformation = new JButton(
						toString(append(btnExportMicrosoftSpeechObjectLibraryInformationName, "Information"))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		final JPanel panelFile = new JPanel();
		//
		panelFile.setLayout(cloneLayoutManager());
		//
		panelFile.setBorder(BorderFactory.createTitledBorder("File"));
		//
		panelFile.add(tfExportFile = new JTextField(), String.format("wmin %1$s", 300));
		//
		panelFile.add(btnExportCopy = new JButton("Copy"));
		//
		panelFile.add(btnExportBrowse = new JButton("Browse"));
		//
		panel.add(panelFile, String.format("span %1$s", 2));
		//
		setEditable(false, tfDllPath, tfExportFile);
		//
		addActionListener(this, btnExportMicrosoftSpeechObjectLibraryInformation, btnExportCopy, btnExportBrowse,
				btnDllPathCopy);
		//
		setEnabled(isInstalled(speechApi) && voiceIds != null, btnExportMicrosoftSpeechObjectLibraryInformation);
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

	private static JScrollPane createHelpPanel(final Number preferredHeight,
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
			map.put("mediaFormatLink", getMediaFormatLink(mediaFormatPageUrl));
			//
			map.put("encryptionTableHtml", getEncryptionTableHtml(
					testAndApply(StringUtils::isNotBlank, poiEncryptionPageUrl, URL::new, null), timeout));
			//
			process(getTemplate(configuration, "help.html.ftl"), map, writer);
			//
			final String html = toString(writer);
			//
			setEditable(false,
					jep = new JEditorPane(StringUtils.defaultIfBlank(
							getMimeType(new ContentInfoUtil().findMatch(VoiceManager.getBytes(html))), "text/html"),
							html));
			//
		} catch (final IOException | TemplateException | IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		if (jep != null) {
			//
			jep.addHyperlinkListener(x -> {
				//
				try {
					//
					if (Objects.equals(EventType.ACTIVATED, getEventType(x))) {
						//
						browse(Desktop.getDesktop(), x != null ? x.getURL().toURI() : null);
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
		} // if
			//
		final JScrollPane jsp = new JScrollPane(jep);
		//
		jsp.setPreferredSize(new Dimension(intValue(getPreferredWidth(jsp), 0),
				intValue(preferredHeight, intValue(getPreferredHeight(jsp), 0))));
		//
		final JPanel jPanel = new JPanel();
		//
		jPanel.add(jsp);
		//
		return jsp;
		//
	}

	private static String getEncryptionTableHtml(final URL url, final Duration timeout) throws IOException {
		//
		org.jsoup.nodes.Document document = testAndApply(
				x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x), "http", "https"), url,
				x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null);
		//
		if (document == null) {
			//
			document = testAndApply(Objects::nonNull,
					testAndApply(Objects::nonNull, url, x -> IOUtils.toString(x, "utf-8"), null), Jsoup::parse, null);
			//
		} // if
			//
		final Elements h2s = ElementUtil.selectXpath(document, "//h2[text()=\"Supported feature matrix\"]");
		//
		return html(nextElementSibling(IterableUtils.size(h2s) == 1 ? IterableUtils.get(h2s, 0) : null));
		//
	}

	private static org.jsoup.nodes.Element nextElementSibling(final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	private static String html(final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.html() : null;
	}

	private static EventType getEventType(final HyperlinkEvent instance) {
		return instance != null ? instance.getEventType() : null;
	}

	private static ATag getMediaFormatLink(final String url)
			throws IllegalAccessException, InvocationTargetException, IOException {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final List<Method> ms = toList(filter(
					testAndApply(Objects::nonNull, getDeclaredMethods(forName("com.sun.jna.Platform")), Arrays::stream,
							null),
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
				m.setAccessible(true);
				//
				if (!Objects.equals(Boolean.TRUE, invoke(m, null))) {
					//
					continue;
					//
				} // if
					//
				add(methodNames = ObjectUtils.getIfNull(methodNames, ArrayList::new), getName(m));
				//
			} // for
				//
			final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull,
					(is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) != null
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
					(aTag = new ATag()).withText(textContent);
					//
					aTag.attr("href", ElementUtil.attr(element, "href"));
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

	private static InputStream openStream(final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	private static byte[] getBytes(final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	private static StringBuilder append(final StringBuilder instance, final String string) {
		return instance != null ? instance.append(string) : null;
	}

	private static StringBuilder append(final StringBuilder instance, final char c) {
		return instance != null ? instance.append(c) : null;
	}

	private static IValue0<Object> getDllPath(final Object instance) {
		//
		final Class<?>[] declaredClasses = getDeclaredClasses(getClass(instance));
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
					|| (fs = toList(filter(
							testAndApply(Objects::nonNull, getDeclaredFields(declaredClass), Arrays::stream, null),
							x -> Objects.equals(getType(x), declaredClass)))) == null
					|| IterableUtils.size(fs) != 1 || (f = get(fs, 0)) == null
					//
					|| (ms = toList(filter(
							testAndApply(Objects::nonNull, getDeclaredMethods(declaredClass), Arrays::stream, null),
							x -> Objects.equals(getName(x), "getDllPath")))) == null
					|| IterableUtils.size(ms) != 1 || (m = get(ms, 0)) == null) {
				continue;
			} // if
				//
			try {
				//
				dllPath = Unit.with(invoke(m, f.get(null)));
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

	private static Class<?>[] getDeclaredClasses(final Class<?> instance) {
		return instance != null ? instance.getDeclaredClasses() : null;
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static Map<String, String> createYomiNameMap() {
		//
		final Class<?> nameClass = forName("domain.Voice$Name");
		//
		final List<Pair<String, String>> pairs = toList(
				//
				filter(map(testAndApply(Objects::nonNull, getDeclaredFields(Yomi.class), Arrays::stream, null), f -> {
					//
					final List<?> objects = toList(stream(new FailableStream<>(
							filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
									a -> Objects.equals(annotationType(a), nameClass)))
							.map(a -> {
								//
								final List<Method> ms = toList(
										filter(testAndApply(Objects::nonNull, getDeclaredMethods(annotationType(a)),
												Arrays::stream, null), ma -> Objects.equals(getName(ma), VALUE)));
								//
								if (ms == null || ms.isEmpty()) {
									//
									return false;
									//
								} // if
									//
								Method m = get(ms, 0);
								//
								if (IterableUtils.size(ms) == 1 && m != null) {
									//
									m.setAccessible(true);
									//
									return invoke(m, a);
									//
								} // if
									//
								throw new IllegalStateException();
								//
							})));
					//
					if (CollectionUtils.isEmpty(objects)) {
						//
						return null;
						//
					} // if
						//
					if (IterableUtils.size(objects) == 1) {
						//
						return Pair.of(getName(f), toString(get(objects, 0)));
						//
					} // if
						//
					throw new IllegalStateException();
					//
				}), Objects::nonNull));

		//
		Pair<String, String> pair = null;
		//
		Map<String, String> map = null;
		//
		for (int i = 0; i < IterableUtils.size(pairs); i++) {
			//
			if ((pair = get(pairs, i)) == null) {
				//
				continue;
				//
			} // if
				//
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), getKey(pair), getValue(pair));
			//
		} // for
			//
		return map;
		//
	}

	private class VoiceIdListCellRenderer implements ListCellRenderer<Object> {

		private ListCellRenderer<Object> listCellRenderer = null;

		private String commonPrefix = null;

		@Override
		public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value,
				final int index, final boolean isSelected, final boolean cellHasFocus) {
			//
			if (getInstance(speechApi) instanceof SpeechApiSystemSpeechImpl) {
				//
				setEnabled(getSelectedItem(cbmVoiceId) != null, btnSpeak, btnWriteVoice);
				//
			} // if
				//
			final String s = VoiceManager.toString(value);
			//
			try {
				//
				final String name = getVoiceAttribute(speechApi, s, "Name");
				//
				if (StringUtils.isNotBlank(name)) {
					//
					return VoiceManager.getListCellRendererComponent(listCellRenderer, list, name, index, isSelected,
							cellHasFocus);
					//
				} // if
					//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			return VoiceManager.getListCellRendererComponent(listCellRenderer, list,
					StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
					index, isSelected, cellHasFocus);
			//
		}

	}

	private static boolean isInstalled(final SpeechApi instance) {
		return instance != null && instance.isInstalled();
	}

	private static String getVoiceAttribute(final SpeechApi instance, final String voiceId, final String attribute) {
		return instance != null ? instance.getVoiceAttribute(voiceId, attribute) : null;
	}

	private static String[] getVoiceIds(final SpeechApi instance) {
		return instance != null ? instance.getVoiceIds() : null;
	}

	private static Range<Integer> createVolumeRange(final Object instance) {
		//
		final Lookup lookup = cast(Lookup.class, instance);
		//
		final BiPredicate<String, String> biPredicate = (a, b) -> contains(lookup, a, b);
		//
		final FailableBiFunction<String, String, Object, RuntimeException> biFunction = (a, b) -> get(lookup, a, b);
		//
		return createRange(toInteger(testAndApply(biPredicate, "volume", "min", biFunction, null)),
				toInteger(testAndApply(biPredicate, "volume", "max", biFunction, null)));
		//
	}

	private static boolean contains(final Lookup instance, final Object row, final Object column) {
		//
		return instance != null && instance.contains(row, column);
		//
	}

	private static Object get(final Lookup instance, final Object row, final Object column) {
		//
		return instance != null ? instance.get(row, column) : instance;
		//
	}

	private static Integer toInteger(final Object object) {
		//
		Integer integer = null;
		//
		if (object instanceof Integer) {
			//
			integer = (Integer) object;
			//
		} else if (object instanceof Number) {
			//
			integer = Integer.valueOf(((Number) object).intValue());
			//
		} else {
			//
			integer = valueOf(toString(object));
			//
		} // if
			//
		return integer;
		//
	}

	private static Range<Integer> createRange(final Integer minValue, final Integer maxValue) {
		//
		if (minValue != null && maxValue != null) {
			return Range.open(minValue.intValue(), maxValue.intValue());
		} else if (minValue != null) {
			return Range.atLeast(minValue.intValue());
		} else if (maxValue != null) {
			return Range.atMost(maxValue.intValue());
		} // if
			//
		return Range.all();
		//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static Integer valueOf(final String instance, final int base) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance, base) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	private static <T> LongStream mapToLong(final Stream<T> instance, final ToLongFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToLong(mapper)
				: null;
		//
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || comparator != null)
				? instance.max(comparator)
				: null;
		//
	}

	private static OptionalInt max(final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	private static <T> T orElse(final Optional<T> instance, final T other) {
		return instance != null ? instance.orElse(other) : null;
	}

	private static int orElse(final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	private static long reduce(final LongStream instance, final long identity, final LongBinaryOperator op) {
		return instance != null ? instance.reduce(identity, op) : identity;
	}

	private static void setEnabled(final boolean b, final Component instance, final Component... cs) {
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

	private static void setEnabled(final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	private static <T, U, R, E extends Throwable> R apply(final FailableBiFunction<T, U, R, E> instance, final T t,
			final U u) throws E {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static void setEditable(final boolean editable, final JTextComponent... jtcs) {
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

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static long longValue(final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... abs) {
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
		accept(x -> setText(x, null), tfCurrentProcessingSheetName, tfCurrentProcessingVoice);
		//
		clear(tmImportResult);
		//
		final Object source = getSource(evt);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try {
			//
			if (anyMatch(stream(findFieldsByValue(getDeclaredFields(getClass()), this, source)),
					f -> isAnnotationPresent(f, ExportButton.class))) {
				//
				setText(tfExportFile, null);
				//
				actionPerformedForExportButtons(source, headless);
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} //
			//
		final boolean nonTest = !isTestMode();
		//
		// if the "source" is one of the value of the field annotated with
		// "@SystemClipboard", pass the "source" to
		// "actionPerformedForSystemClipboardAnnotated(java.lang.Object)" method
		//
		testAndRun(
				contains(toList(filter(stream(new FailableStream<>(filter(
						testAndApply(Objects::nonNull, getDeclaredFields(VoiceManager.class), Arrays::stream, null),
						f -> isAnnotationPresent(f, SystemClipboard.class)))
						.map(f -> FieldUtils.readField(f, this, true))), Objects::nonNull)), source),
				() -> actionPerformedForSystemClipboardAnnotated(nonTest, source));
		//
		// Speech Rate
		//
		testAndRun(contains(getObjectsByGroupAnnotation(this, SPEECH_RATE), source),
				() -> actionPerformedForSpeechRate(source));
		//
		// Conversion
		//
		testAndRun(contains(getObjectsByGroupAnnotation(this, "Conversion"), source),
				() -> actionPerformedForConversion(source));
		//
		if (Objects.equals(source, btnSpeak)) {
			//
			actionPerformedForSpeak(headless);
			//
		} else if (Objects.equals(source, btnWriteVoice)) {
			//
			actionPerformedForWriteVoice(headless);
			//
		} else if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedForExecute(headless, nonTest);
			//
		} else if (Objects.equals(source, btnExportBrowse)) {
			//
			actionPerformedForExportBrowse(headless);
			//
		} else if (Objects.equals(source, btnExport)) {
			//
			actionPerformedForExport(headless);
			//
		} else if (Objects.equals(source, btnImportFileTemplate)) {
			//
			actionPerformedForImportFileTemplate(headless);
			//
		} else if (Objects.equals(source, btnImport)) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				importVoice(jfc.getSelectedFile());
				//
			} // if
				//
		} else if (Objects.equals(source, btnImportWithinFolder)) {
			//
			importByWorkbookFiles(
					//
					listFiles(new WindowsFolderBrowser().showDialog(this))
					//
					, headless);
			//
		} else if (Objects.equals(source, btnPronunciationPageUrlCheck)) {
			//
			actionPerformedForPronunciationPageUrlCheck(headless);
			//
		} else if (Objects.equals(source, btnIpaSymbol)) {
			//
			actionPerformedForIpaSymbol(headless);
			//
		} // if
			//
	}

	private void importByWorkbookFiles(final File[] fs, final boolean headless) {
		//
		File f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			try {
				//
				if (!isFile(f = fs[i]) || getWorkbook(f) == null) {
					//
					continue;
					//
				} // if
					//
			} catch (final IOException | InvalidFormatException | GeneralSecurityException | SAXException
					| ParserConfigurationException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
			importVoice(f);
			//
		} // for
			//
	}

	private static boolean isTestMode() {
		return forName("org.junit.jupiter.api.Test") != null;
	}

	private static <T> List<T> getObjectsByGroupAnnotation(final Object instance, final String group,
			final Class<T> clz) {
		//
		return toList(map(stream(getObjectsByGroupAnnotation(instance, group)), x -> cast(clz, x)));
		//
	}

	private static List<?> getObjectsByGroupAnnotation(final Object instance, final String group) {
		//
		return toList(stream(new FailableStream<>(filter(
				testAndApply(Objects::nonNull, getDeclaredFields(VoiceManager.class), Arrays::stream, null), f -> {
					final Group g = isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringUtils.equals(g != null ? g.value() : null, group);
				})).map(f -> FieldUtils.readField(f, instance, true))));
		//
	}

	private void actionPerformedForSpeak(final boolean headless) {
		//
		final Stopwatch stopwatch = Stopwatch.createStarted();
		//
		final Method method = cast(Method.class, getSelectedItem(cbmSpeakMethod));
		//
		final Object instance = getInstance(speechApi);
		//
		final String text = getText(tfTextTts);
		//
		final String voiceId = toString(getSelectedItem(cbmVoiceId));
		//
		final int rate = intValue(getRate(), 0);
		//
		final int volume = Math.min(Math.max(intValue(getValue(jsSpeechVolume), 100), 0), 100);
		//
		if (Arrays.equals(getParameterTypes(method),
				new Class<?>[] { String.class, String.class, Integer.TYPE, Integer.TYPE })) {
			//
			try {
				//
				invoke(method, instance, text, voiceId, rate, volume);
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
		} else if (speechApi != null) {
			//
			speechApi.speak(text, voiceId, rate, volume);
			//
		} // if
			//
		setText(tfElapsed, toString(elapsed(stop(stopwatch))));
		//
	}

	private void actionPerformedForWriteVoice(final boolean headless) {
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			//
			return;
			//
		} //
			//
		final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
		//
		final File file = jfc.getSelectedFile();
		//
		ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
		//
		ObjectMap.setObject(objectMap, File.class, file);
		//
		writeVoiceToFile(objectMap, getText(tfTextTts), toString(getSelectedItem(cbmVoiceId))
		//
				, intValue(getRate(), 0)// rate
				//
				, Math.min(Math.max(intValue(getValue(jsSpeechVolume), 100), 0), 100)// volume
		);
		//
		final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory, FORMAT,
				getSelectedItem(cbmAudioFormatWrite));
		//
		if (byteConverter != null) {
			//
			try {
				//
				FileUtils.writeByteArrayToFile(file, byteConverter.convert(FileUtils.readFileToByteArray(file)));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
	}

	private void actionPerformedForExecute(final boolean headless, final boolean nonTest) {
		//
		forEach(Stream.of(tfFile, tfFileLength, tfFileDigest), x -> setText(x, null));
		//
		File file = null;
		//
		final Voice voice = createVoice(getObjectMapper(), this);
		//
		if (isSelected(cbUseTtsVoice)) {
			//
			final String voiceId = getVoiceIdForExecute(nonTest);
			//
			if (voiceId == null) {
				//
				// Show "Please select a Voice" message if this method is not run under test
				// case
				//
				testAndRun(nonTest, () -> JOptionPane.showMessageDialog(null, "Please select a Voice"));
				//
				return;
				//
			} // if
				//
			try {
				//
				deleteOnExit(file = generateTtsAudioFile(headless, voiceId, voice));
				//
			} catch (IllegalAccessException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} catch (InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				errorOrAssertOrShowException(headless,
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
								ExceptionUtils.getRootCause(e), e));
				//
			} // try
				//
		} else {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
				//
				clear(tmImportException);
				//
				ifElse(tmImportException != null,
						() -> addRow(tmImportException,
								new Object[] { getText(voice), getRomaji(voice), NO_FILE_SELECTED }),
						() -> JOptionPane.showMessageDialog(null, NO_FILE_SELECTED));
				//
				return;
				//
			} // if
				//
			try {
				//
				setSource(voice,
						StringUtils.defaultIfBlank(getSource(voice), getMp3TagValue(file = jfc.getSelectedFile(),
								x -> StringUtils.isNotBlank(toString(x)), mp3Tags)));
				//
			} catch (final IOException | BaseException | IllegalAccessException e) {
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
		SqlSession sqlSession = null;
		//
		try {
			//
			final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
			//
			ObjectMap.setObject(objectMap, File.class, file);
			//
			ObjectMap.setObject(objectMap, Voice.class, voice);
			//
			ObjectMap.setObject(objectMap, VoiceMapper.class, getMapper(getConfiguration(sqlSessionFactory),
					VoiceMapper.class, sqlSession = openSession(sqlSessionFactory)));
			//
			ObjectMap.setObject(objectMap, VoiceManager.class, this);
			//
			ObjectMap.setObject(objectMap, String.class, voiceFolder);
			//
			ObjectMap.setObject(objectMap, DefaultTableModel.class, tmImportException);
			//
			try {
				//
				ObjectMap.setObject(objectMap, MessageDigest.class,
						MessageDigest.getInstance(StringUtils.defaultIfBlank(messageDigestAlgorithm, SHA_512)));
				//
			} catch (final NoSuchAlgorithmException e) {
				//
				throw toRuntimeException(e);
				//
			} // try
				//
			execute(objectMap);
			//
		} finally {
			//
			IOUtils.closeQuietly(sqlSession);
			//
		} // try
			//
	}

	private static <E extends Throwable> void ifElse(final boolean condition, final FailableRunnable<E> runnableTrue,
			final FailableRunnable<E> runnableFalse) throws E {
		//
		if (condition) {
			//
			run(runnableTrue);
			//
		} else {
			//
			run(runnableFalse);
			//
		} // if
			//
	}

	private static <E extends Throwable> void run(final FailableRunnable<E> instance) throws E {
		//
		if (instance != null) {
			//
			instance.run();
			//
		} // if
			//
	}

	private static void addRow(final DefaultTableModel instance, final Object[] rowData) {
		//
		if (instance != null) {
			//
			instance.addRow(rowData);
			//
		} // if
			//
	}

	private String getVoiceIdForExecute(final boolean nonTest) {
		//
		String voiceId = toString(getSelectedItem(cbmVoiceId));
		//
		if (StringUtils.isBlank(voiceId)) {
			//
			final ComboBoxModel<Object> cbmVoiceIdLocal = testAndApply(Objects::nonNull, voiceIds,
					x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null);
			//
			JComboBox<Object> jcbVoiceIdLocal = null;
			//
			if (cbmVoiceIdLocal != null) {
				//
				final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer();
				//
				voiceIdListCellRenderer.listCellRenderer = (jcbVoiceIdLocal = new JComboBox<>(cbmVoiceIdLocal))
						.getRenderer();
				//
				jcbVoiceIdLocal.addItemListener(this);
				//
				voiceIdListCellRenderer.commonPrefix = String.join("",
						StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
				//
				jcbVoiceIdLocal.setRenderer(voiceIdListCellRenderer);
				//
			} // if
				//
				// Show "Voice ID" option dialog if this method is not run under test case
				//
			testAndAccept((a, b) -> Objects.equals(a, Boolean.TRUE), nonTest, jcbVoiceIdLocal,
					(a, b) -> JOptionPane.showMessageDialog(null, b, "Voice ID", JOptionPane.PLAIN_MESSAGE));
			//
			voiceId = toString(getSelectedItem(cbmVoiceIdLocal));
			//
		} // if
			//
		return voiceId;
		//
	}

	private File generateTtsAudioFile(final boolean headless, final String voiceId, final Voice voice)
			throws IllegalAccessException, InvocationTargetException {
		//
		File file = null;
		//
		if (speechApi != null) {
			//
			try {
				//
				speechApi.writeVoiceToFile(getText(tfTextImport), voiceId
				//
						, intValue(getRate(), 0)// rate
						//
						, Math.min(Math.max(intValue(getValue(jsSpeechVolume), 100), 0), 100)// volume
						, file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null)
				//
				);
				//
				if (Objects.equals("wav", getFileExtension(cast(ContentInfo.class,
						testAndApply(Objects::nonNull, file, new ContentInfoUtil()::findMatch, null))))) {
					//
					final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory, FORMAT,
							getSelectedItem(cbmAudioFormatExecute));
					//
					if (byteConverter != null) {
						//
						FileUtils.writeByteArrayToFile(file,
								byteConverter.convert(FileUtils.readFileToByteArray(file)));
						//
					} // if
						//
				} // if
					//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
		setLanguage(voice, StringUtils.defaultIfBlank(getLanguage(voice),
				convertLanguageCodeToText(getVoiceAttribute(speechApi, voiceId, LANGUAGE), 16)));
		//
		setSource(voice,
				StringUtils.defaultIfBlank(getSource(voice), getProviderName(cast(Provider.class, speechApi))));
		//
		return file;
		//
	}

	private static File createTempFile(final String prefix, final String suffix)
			throws IllegalAccessException, InvocationTargetException {
		//
		final List<Method> ms = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredMethods(File.class), Arrays::stream, null),
						x -> Objects.equals(getName(x), "createTempFile")
								&& Arrays.equals(new Class<?>[] { String.class, String.class }, getParameterTypes(x))));
		//
		return cast(File.class,
				invoke(testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null), null,
						prefix, suffix));
		//

	}

	private void actionPerformedForImportFileTemplate(final boolean headless) {
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			//
			return;
			//
		} // if
			//
		try {
			//
			FileUtils.writeByteArrayToFile(jfc.getSelectedFile(),
					createImportFileTemplateByteArray(isSelected(cbImportFileTemplateGenerateBlankRow),
							IValue0Util.getValue0(jlptLevels),
							MultimapUtil.keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap))));
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
	}

	private void actionPerformedForExportBrowse(final boolean headless) {
		//
		try {
			//
			testAndAccept(Objects::nonNull,
					toURI(testAndApply(Objects::nonNull, getText(tfExportFile), File::new, null)),
					x -> browse(Desktop.getDesktop(), x));
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
	}

	private void actionPerformedForPronunciationPageUrlCheck(final boolean headless) {
		//
		setText(tfPronunciationPageStatusCode, null);
		//
		setBackground(tfPronunciationPageStatusCode, null);
		//
		final String urlString = getText(tfPronunciationPageUrl);
		//
		if (StringUtils.isNotBlank(urlString)) {
			//
			try {
				//
				final Integer responseCode = getResponseCode(
						cast(HttpURLConnection.class, new URL(urlString).openConnection()));
				//
				setText(tfPronunciationPageStatusCode, Integer.toString(responseCode));
				//
				if (responseCode != null) {
					//
					Color background = null;
					//
					if (HttpStatus.isSuccess(responseCode.intValue())) {
						//
						background = Color.GREEN;
						//
					} else if (Boolean.logicalOr(HttpStatus.isClientError(responseCode.intValue()),
							HttpStatus.isServerError(responseCode.intValue()))) {
						//
						background = Color.RED;
						//
					} // if
						//
					setBackground(tfPronunciationPageStatusCode, background);
					//
				} // if
					//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
	}

	private void actionPerformedForIpaSymbol(final boolean headless) {
		//
		final Collection<String> values = MultimapUtil.get(IValue0Util.getValue0(ipaSymbolMultimap),
				getText(tfTextImport));
		//
		final int size = IterableUtils.size(values);
		//
		if (size == 1) {
			//
			setText(tfIpaSymbol, toString(IterableUtils.get(values, 0)));
			//
		} else if (!headless && !isTestMode()) {
			//
			final JList<Object> list = new JList<>(values != null ? values.toArray() : null);
			//
			JOptionPane.showMessageDialog(null, list, "IPA", JOptionPane.PLAIN_MESSAGE);
			//
			setText(tfIpaSymbol, toString(list.getSelectedValue()));
			//
		} // if
			//
	}

	private static Integer getResponseCode(final HttpURLConnection instance) throws IOException {
		return instance != null ? Integer.valueOf(instance.getResponseCode()) : null;
	}

	private static void setLanguage(final Voice instance, final String language) {
		if (instance != null) {
			instance.setLanguage(language);
		}
	}

	private static String getLanguage(final Voice instance) {
		return instance != null ? instance.getLanguage() : null;
	}

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> runnable) throws E {
		//
		if (b && runnable != null) {
			//
			runnable.run();
			//
		} // if
			//
	}

	private static <O> Stream<O> stream(final FailableStream<O> instance) {
		return instance != null ? instance.stream() : null;
	}

	private void actionPerformedForSystemClipboardAnnotated(final boolean nonTest, final Object source) {
		//
		final Clipboard clipboard = getSystemClipboard(getToolkit());
		//
		IValue0<String> stringValue = null;
		//
		if (Objects.equals(source, btnCopyRomaji)) {
			//
			stringValue = Unit.with(getText(tfRomaji));
			//
		} else if (Objects.equals(source, btnCopyHiragana)) {
			//
			stringValue = Unit.with(getText(tfHiragana));
			//
		} else if (Objects.equals(source, btnCopyKatakana)) {
			//
			stringValue = Unit.with(getText(tfKatakana));
			//
		} else if (Objects.equals(source, btnExportCopy)) {
			//
			stringValue = Unit.with(getText(tfExportFile));
			//
		} else if (Objects.equals(source, btnDllPathCopy)) {
			//
			stringValue = Unit.with(getText(tfDllPath));
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

	private static class IntValue {

		private int value = 0;

		private IntValue(final int value) {
			this.value = value;
		}

	}

	private void actionPerformedForSpeechRate(final Object source) {
		//
		IntValue intValue = null;
		//
		if (Objects.equals(source, btnSpeechRateSlower)) {
			//
			intValue = new IntValue(intValue(getValue(jsSpeechRate), 0) - 1);
			//
		} else if (Objects.equals(source, btnSpeechRateNormal)) {
			//
			intValue = new IntValue(0);
			//
		} else if (Objects.equals(source, btnSpeechRateFaster)) {
			//
			intValue = new IntValue(intValue(getValue(jsSpeechRate), 0) + 1);
			//
		} // if
			//
		if (intValue != null) {
			//
			setValue(jsSpeechRate, intValue.value);
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private void actionPerformedForConversion(final Object source) {
		//
		Entry<JTextComponent, String> pair = null;
		//
		if (Objects.equals(source, btnConvertToRomaji)) {
			//
			pair = Pair.of(tfRomaji, convert(jakaroma = ObjectUtils.getIfNull(jakaroma, Jakaroma::new),
					getText(tfTextImport), false, false));
			//
		} else if (Objects.equals(source, btnConvertToKatakana)) {
			//
			pair = Pair.of(tfKatakana, testAndApply(Objects::nonNull, getText(tfHiragana),
					x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_HIRA_TO_ZEN_KATA), null));
			//
		}
		//
		if (pair != null) {
			//
			setText(getKey(pair), getValue(pair));
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
						microsoftSpeechObjectLibraryAttributeNames), os);
				//
				setText(tfExportFile, getAbsolutePath(file));
				//
			} catch (final IOException | IllegalAccessException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				testAndAccept(EMPTY_FILE_PREDICATE, file, FileUtils::deleteQuietly);
				//
			} // try
				//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private void actionPerformedForExport(final boolean headless) {
		//
		try {
			//
			actionPerformedForExport(headless, get(IValue0Util.getValue0(getWorkbookClassFailableSupplierMap()),
					getSelectedItem(cbmWorkbookClass)));
			//
		} catch (final IOException | IllegalAccessException | TemplateException | InvalidFormatException
				| GeneralSecurityException | SQLException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
	}

	private void actionPerformedForExport(final boolean headless,
			final FailableSupplier<Workbook, RuntimeException> workbookSupplier)
			throws IOException, IllegalAccessException, InvocationTargetException, InvalidFormatException,
			GeneralSecurityException, TemplateException, SQLException {
		//
		SqlSession sqlSession = null;
		//
		Workbook workbook = null;
		//
		File file = null;
		//
		try {
			//
			final VoiceMapper voiceMapper = getMapper(getConfiguration(sqlSessionFactory), VoiceMapper.class,
					sqlSession = openSession(sqlSessionFactory));
			//
			final List<Voice> voices = retrieveAllVoices(voiceMapper);
			//
			forEach(voices, v -> setListNames(v, searchVoiceListNamesByVoiceId(voiceMapper, getId(v))));
			//
			final IH ih = new IH();
			//
			ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
			//
			ObjectMap.setObject(objectMap, VoiceManager.class, this);
			//
			// org.springframework.context.support.VoiceManager$BooleanMap
			//
			final BooleanMap bm = Reflection.newProxy(BooleanMap.class, ih);
			//
			BooleanMap.setBoolean(bm, OVER_MP3_TITLE, isSelected(cbOverMp3Title));
			//
			BooleanMap.setBoolean(bm, ORDINAL_POSITION_AS_FILE_NAME_PREFIX,
					isSelected(cbOrdinalPositionAsFileNamePrefix));
			//
			BooleanMap.setBoolean(bm, "jlptAsFolder", isSelected(cbJlptAsFolder));
			//
			BooleanMap.setBoolean(bm, EXPORT_PRESENTATION, isSelected(cbExportPresentation));
			//
			BooleanMap.setBoolean(bm, EMBED_AUDIO_IN_PRESENTATION, isSelected(cbEmbedAudioInPresentation));
			//
			BooleanMap.setBoolean(bm, HIDE_AUDIO_IMAGE_IN_PRESENTATION, !isSelected(cbHideAudioImageInPresentation));
			//
			ObjectMap.setObject(objectMap, BooleanMap.class, bm);
			//
			// org.springframework.context.support.VoiceManager$StringMap
			//
			final StringMap stringMap = Reflection.newProxy(StringMap.class, ih);
			//
			StringMap.setString(stringMap, "ordinalPositionFileNamePrefix", getText(tfOrdinalPositionFileNamePrefix));
			//
			StringMap.setString(stringMap, "exportPresentationTemplate", exportPresentationTemplate);
			//
			StringMap.setString(stringMap, "exportPassword", getText(tfExportPassword));
			//
			StringMap.setString(stringMap, FOLDER_IN_PRESENTATION, folderInPresentation);
			//
			StringMap.setString(stringMap, "jlptFolderNamePrefix", getText(tfJlptFolderNamePrefix));
			//
			StringMap.setString(stringMap, MESSAGE_DIGEST_ALGORITHM, messageDigestAlgorithm);
			//
			ObjectMap.setObject(objectMap, StringMap.class, stringMap);
			//
			export(voices, outputFolderFileNameExpressions, objectMap);
			//
			// Export Spreadsheet
			//
			boolean fileToBeDeleted = false;
			//
			final String fileExtension = getFileExtension(workbookSupplier);
			//
			try (final OutputStream os = new FileOutputStream(
					file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.%2$s", new Date(),
							StringUtils.defaultIfEmpty(fileExtension, "xlsx"))))) {
				//
				final BooleanMap booleanMap = Reflection.newProxy(BooleanMap.class, ih);
				//
				BooleanMap.setBoolean(booleanMap, "exportListSheet", isSelected(cbExportListSheet));
				//
				BooleanMap.setBoolean(booleanMap, "exportJlptSheet", isSelected(cbExportJlptSheet));
				//
				WorkbookUtil.write(workbook = createWorkbook(voices, booleanMap, workbookSupplier), os);
				//
				if (!(fileToBeDeleted = longValue(length(file), 0) == 0)) {
					//
					fileToBeDeleted = intValue(
							getPhysicalNumberOfRows(testAndApply(x -> intValue(getNumberOfSheets(x), 0) == 1, workbook,
									x -> WorkbookUtil.getSheetAt(x, 0), null)),
							0) == 0;
					//
				} // if
					//
			} // try
				//
				// encrypt the file if "password" is set
				//
			encrypt(file, cast(EncryptionMode.class, getSelectedItem(cbmEncryptionMode)), getText(tfExportPassword));
			//
			// Delete empty Spreadsheet
			//
			testAndAccept((a, b) -> Objects.equals(Boolean.TRUE, a), fileToBeDeleted, file, (a, b) -> delete(b));
			//
			// Export HTML file
			//
			if (isSelected(cbExportHtml)) {
				//
				final Version version = ObjectUtils.getIfNull(freeMarkerVersion,
						freemarker.template.Configuration::getVersion);
				//
				ObjectMap.setObject(objectMap = Reflection.newProxy(ObjectMap.class, ih), Version.class, version);
				//
				final freemarker.template.Configuration configuration = ObjectUtils.getIfNull(freeMarkerConfiguration,
						() -> new freemarker.template.Configuration(version));
				//
				testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
						x -> setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
				//
				ObjectMap.setObject(objectMap, freemarker.template.Configuration.class, configuration);
				//
				ObjectMap.setObject(objectMap, TemplateHashModel.class, new BeansWrapper(version).getStaticModels());
				//
				List<File> files = null;
				//
				final Map<Object, Object> map = new LinkedHashMap<>();
				//
				put(map, "folder", voiceFolder);
				//
				put(map, "voices", voices);
				//
				try (final Writer writer = new StringWriter()) {
					//
					ObjectMap.setObject(objectMap, Writer.class, writer);
					//
					exportHtml(objectMap, exportHtmlTemplateFile, map);
					//
					final StringBuilder sb = new StringBuilder(
							StringUtils.defaultString(getText(tfExportHtmlFileName)));
					//
					final String[] fileExtensions = getFileExtensions(ContentType.HTML);
					//
					if (!anyMatch(testAndApply(Objects::nonNull, fileExtensions, Arrays::stream, null),
							x -> StringUtils.endsWithIgnoreCase(sb, StringUtils.join('.', x)))) {
						//
						// append "." if the file name does not ends with "."
						//
						testAndAccept(x -> !StringUtils.endsWith(x, "."), sb, x -> append(x, '.'));
						//
						append(sb, getLongestString(fileExtensions));
						//
					} // if
						//
					FileUtils.writeStringToFile(
							file = new File(StringUtils.defaultIfBlank(toString(sb), "export.html")), toString(writer),
							StandardCharsets.UTF_8);
					//
					add(files = ObjectUtils.getIfNull(files, ArrayList::new), file);
					//
				} // try
					//
				final boolean exportWebSpeechSynthesisHtml = isSelected(cbExportWebSpeechSynthesisHtml);
				//
				if (exportWebSpeechSynthesisHtml) {

					try (final Writer writer = new StringWriter()) {
						//
						ObjectMap.setObject(objectMap, Writer.class, writer);
						//
						putAll(map, exportWebSpeechSynthesisHtmlTemplateProperties);
						//
						exportHtml(objectMap, exportWebSpeechSynthesisHtmlTemplateFile, map);
						//
						final StringBuilder sb = new StringBuilder(
								StringUtils.defaultString("WebSpeechSynthesis.html"));
						//
						final String[] fileExtensions = getFileExtensions(ContentType.HTML);
						//
						if (!anyMatch(testAndApply(Objects::nonNull, fileExtensions, Arrays::stream, null),
								x -> StringUtils.endsWithIgnoreCase(sb, StringUtils.join('.', x)))) {
							//
							// append "." if the file name does not ends with "."
							//
							testAndAccept(x -> !StringUtils.endsWith(x, "."), sb, x -> append(x, '.'));
							//
							append(sb, getLongestString(fileExtensions));
							//
						} // if
							//
						FileUtils.writeStringToFile(
								file = new File(StringUtils.defaultIfBlank(toString(sb), "WebSpeechSynthesis.html")),
								toString(writer), StandardCharsets.UTF_8);
						//
						add(files = ObjectUtils.getIfNull(files, ArrayList::new), file);
						//
					} // try
						//
				} // if
					//
				if (isSelected(cbExportListHtml)) {
					//
					final Multimap<String, Voice> multimap = getVoiceMultimapByListName(voices);
					//
					exportHtml(objectMap, multimap, Pair.of(exportHtmlTemplateFile, x -> String.format("%1$s.html", x)),
							null, files = ObjectUtils.getIfNull(files, ArrayList::new));
					//
					exportWebSpeechSynthesisHtml(exportWebSpeechSynthesisHtml, objectMap, multimap,
							files = ObjectUtils.getIfNull(files, ArrayList::new));
					//
				} // if
					//
				if (isSelected(cbExportHtmlAsZip)
						&& reduce(mapToLong(stream(files), f -> longValue(length(f), 0)), 0, Long::sum) > 0) {
					//
					ObjectMap.setObject(objectMap, File.class,
							file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.zip", new Date())));
					//
					ObjectMap.setObject(objectMap, EncryptionMethod.class, EncryptionMethod.ZIP_STANDARD);
					//
					ObjectMap.setObject(objectMap, CompressionLevel.class,
							cast(CompressionLevel.class, getSelectedItem(cbmCompressionLevel)));
					//
					createZipFile(objectMap, getText(tfExportPassword), files);
					//
					// Delete HTML File(s) is "Remove HTML After ZIP" option is checked
					//
					testAndAccept((a, b) -> a, isSelected(cbExportHtmlRemoveAfterZip), files,
							(a, b) -> forEach(b, VoiceManager::delete));
					//
				} // if
					//
			} // if
				//
				// Export Microsoft Access
				//
			if (isSelected(cbExportMicrosoftAccess)) {
				//
				final FileFormat fileFormat = cast(FileFormat.class, getSelectedItem(cbmMicrosoftAccessFileFormat));
				//
				ObjectMap.setObject(objectMap, File.class,
						file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.%2$s", new Date(),
								StringUtils.substringAfter(getFileExtension(fileFormat), '.'))));
				//
				ObjectMap.setObject(objectMap, FileFormat.class, fileFormat);
				//
				exportMicrosoftAccess(objectMap, values(
						ListableBeanFactoryUtil.getBeansOfType(configurableListableBeanFactory, DataSource.class)));
				//
			} // if
				//
		} finally {
			//
			IOUtils.closeQuietly(sqlSession);
			//
			IOUtils.closeQuietly(workbook);
			//
			testAndAccept(EMPTY_FILE_PREDICATE, file, FileUtils::deleteQuietly);
			//
		} // try
			//
	}

	private static Integer getNumberOfSheets(final Workbook instance) {
		return instance != null ? Integer.valueOf(instance.getNumberOfSheets()) : null;
	}

	private void exportWebSpeechSynthesisHtml(final boolean condition, final ObjectMap objectMap,
			final Multimap<String, Voice> multimap, final Collection<File> files)
			throws IOException, TemplateException {
		//
		if (condition) {
			//
			exportHtml(objectMap, multimap,
					//
					Pair.of(exportWebSpeechSynthesisHtmlTemplateFile,
							x -> String.format("%1$s.WebSpeechSynthesis.html", x)),
					//
					exportWebSpeechSynthesisHtmlTemplateProperties
					//
					, files);
			//
		} // if
			//
	}

	private static String getFileExtension(final FailableSupplier<Workbook, RuntimeException> supplier)
			throws IOException {
		//
		try (final Workbook wb = get(supplier); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.write(wb, baos);
			//
			if (Objects.equals(getMessage(new ContentInfoUtil().findMatch(baos.toByteArray())),
					OLE_2_COMPOUND_DOCUMENT)) {
				//
				return "xls";
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> clz, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T newInstance(final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
	}

	private static String getFileExtension(final FileFormat instance) {
		return instance != null ? instance.getFileExtension() : null;
	}

	private static <V> Collection<V> values(final Map<?, V> instance) {
		return instance != null ? instance.values() : null;
	}

	private static void exportMicrosoftAccess(final ObjectMap objectMap, final Iterable<DataSource> dss)
			throws IOException, SQLException {
		//
		final File file = ObjectMap.getObject(objectMap, File.class);
		//
		try (final Database db = create(setFileFormat(testAndApply(Objects::nonNull, file, DatabaseBuilder::new, null),
				ObjectUtils.defaultIfNull(ObjectMap.getObject(objectMap, FileFormat.class), FileFormat.V2000)))) {
			//
			if (iterator(dss) != null) {
				//
				for (final DataSource ds : dss) {
					//
					ObjectMap.setObject(objectMap, Database.class, db);
					//
					ObjectMap.setObject(objectMap, DataSource.class, ds);
					//
					exportMicrosoftAccess(objectMap);
					//
				} // for
					//
			} // if
				//
		} // try
			//
		try (final Database db = testAndApply(Objects::nonNull, file, DatabaseBuilder::open, null)) {
			//
			testAndAccept(CollectionUtils::isEmpty, getTableNames(db), x -> delete(file));
			//
		} // try
			//
	}

	private static void exportMicrosoftAccess(final ObjectMap objectMap) throws IOException, SQLException {
		//
		try (final Connection connection = getConnection(ObjectMap.getObject(objectMap, DataSource.class))) {
			//
			// Retrieve all table name(s) from "information_schema.tables" table
			//
			final PreparedStatement ps = prepareStatement(connection,
					"select distinct table_name from information_schema.tables where table_schema='PUBLIC' order by table_name");
			//
			ResultSet rs = executeQuery(ps);
			//
			Set<String> tableNames = null;
			//
			while (rs != null && rs.next()) {
				//
				add(tableNames = getIfNull(tableNames, LinkedHashSet::new), rs.getString("table_name"));
				//
			} // while
				//
			DbUtils.closeQuietly(rs);
			//
			DbUtils.closeQuietly(ps);
			//
			ObjectMap.setObject(objectMap, Connection.class, connection);
			//
			importResultSet(objectMap, tableNames);
			//
		} // try
			//
	}

	private static void importResultSet(final ObjectMap objectMap, final Iterable<String> tableNames)
			throws IOException, SQLException {
		//
		final Database db = ObjectMap.getObject(objectMap, Database.class);
		//
		if (iterator(tableNames) != null) {
			//
			final Connection connection = ObjectMap.getObject(objectMap, Connection.class);
			//
			for (final String tableName : tableNames) {
				//
				try (final ResultSet rs = executeQuery(
						prepareStatement(connection, String.format("select * from %1$s", tableName)))) {
					//
					if (and(Objects::nonNull, rs, db, tableName)) {
						//
						ImportUtil.importResultSet(rs, db, tableName);
						//
					} // if
						//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	private static Connection getConnection(final DataSource instance) throws SQLException {
		return instance != null ? instance.getConnection() : null;
	}

	private static PreparedStatement prepareStatement(final Connection instance, final String sql) throws SQLException {
		return instance != null ? instance.prepareStatement(sql) : null;
	}

	private static ResultSet executeQuery(final PreparedStatement instance) throws SQLException {
		return instance != null ? instance.executeQuery() : null;
	}

	private static DatabaseBuilder setFileFormat(final DatabaseBuilder instance, final Database.FileFormat fileFormat) {
		return instance != null ? instance.setFileFormat(fileFormat) : instance;
	}

	private static Database create(final DatabaseBuilder instance) throws IOException {
		return instance != null ? instance.create() : null;
	}

	private static Set<String> getTableNames(final Database instance) throws IOException {
		return instance != null ? instance.getTableNames() : null;
	}

	private static String getLongestString(final String[] ss) {
		//
		return orElse(max(testAndApply(Objects::nonNull, ss, Arrays::stream, null),
				(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null);
		//
	}

	private static void setSource(final Voice instance, final String source) {
		if (instance != null) {
			instance.setSource(source);
		}
	}

	private static String getSource(final Voice instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void setListNames(final Voice instance, final Iterable<String> listNames) {
		if (instance != null) {
			instance.setListNames(listNames);
		}
	}

	private static List<Voice> retrieveAllVoices(final VoiceMapper instance) {
		return instance != null ? instance.retrieveAllVoices() : null;
	}

	private static List<String> searchVoiceListNamesByVoiceId(final VoiceMapper instance, final Integer voiceId) {
		return instance != null ? instance.searchVoiceListNamesByVoiceId(voiceId) : null;
	}

	private static void createZipFile(final ObjectMap objectMap, final String password, final Iterable<File> files)
			throws IOException {
		//
		final ZipParameters zipParameters = new ZipParameters();
		//
		zipParameters.setEncryptFiles(StringUtils.isNotEmpty(password));
		//
		zipParameters
				.setEncryptionMethod(ObjectUtils.firstNonNull(ObjectMap.getObject(objectMap, EncryptionMethod.class),
						zipParameters.getEncryptionMethod(), EncryptionMethod.ZIP_STANDARD));
		//
		zipParameters
				.setCompressionLevel(ObjectUtils.firstNonNull(ObjectMap.getObject(objectMap, CompressionLevel.class),
						zipParameters.getCompressionLevel(), CompressionLevel.NORMAL));
		//
		try (final net.lingala.zip4j.ZipFile zipFile = testAndApply(Objects::nonNull,
				ObjectMap.getObject(objectMap, File.class),
				x -> new net.lingala.zip4j.ZipFile(x, toCharArray(password)), null)) {
			//
			forEach(files, x -> {
				//
				if (zipFile != null && x != null) {
					//
					zipFile.addFile(x, zipParameters);
					//
				} // if
					//
			});
			//
		} // try
			//
	}

	private static char[] toCharArray(final String instance) {
		return instance != null ? instance.toCharArray() : null;
	}

	private static void encrypt(final File file, final EncryptionMode encryptionMode, final String password)
			throws IOException, InvalidFormatException, GeneralSecurityException {
		//
		try (final InputStream is = testAndApply(x -> x != null && x.length > 0,
				testAndApply(Objects::nonNull, file, FileUtils::readFileToByteArray, null), ByteArrayInputStream::new,
				null); final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
			//
			if (wb instanceof XSSFWorkbook && StringUtils.isNotEmpty(password)) {
				//
				try (final POIFSFileSystem fs = new POIFSFileSystem()) {
					//
					final Encryptor encryptor = new EncryptionInfo(
							ObjectUtils.defaultIfNull(encryptionMode, EncryptionMode.agile)).getEncryptor();
					//
					if (encryptor != null) {
						//
						encryptor.confirmPassword(password);
						//
					} // if
						//
					try (final OPCPackage opc = OPCPackage.open(file);
							final OutputStream os = encryptor != null ? encryptor.getDataStream(fs) : null) {
						//
						opc.save(os);
						//
					} // try
						//
					try (final FileOutputStream fos = new FileOutputStream(file)) {
						//
						fs.writeFilesystem(fos);
						//
					} // try
						//
				} // try
					//
			} else if (wb instanceof HSSFWorkbook && StringUtils.isNotEmpty(password)) {
				//
				Biff8EncryptionKey.setCurrentUserPassword(password);
				//
				try (final POIFSFileSystem fs = new POIFSFileSystem(file, true);
						final Workbook wb2 = new HSSFWorkbook(fs.getRoot(), true);
						final OutputStream os = new FileOutputStream(file)) {
					//
					WorkbookUtil.write(wb2, os);
					//
				} finally {
					//
					Biff8EncryptionKey.setCurrentUserPassword(null);
					//
				} // try
					//
			} // if
				//
		} // try
			//
	}

	private static Integer getValue(final JSlider instance) {
		return instance != null ? Integer.valueOf(instance.getValue()) : null;
	}

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
			return toString(invoke(method, null, count));
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
			throw toRuntimeException(throwable);
			//
		} // try
			//
	}

	private static String[] getFileExtensions(final ContentType instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	private static Multimap<String, Voice> getVoiceMultimapByListName(final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (iterator(voices) != null) {
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

	private static Multimap<String, Voice> getVoiceMultimapByJlpt(final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (iterator(voices) != null) {
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

	private static String getJlptLevel(final Voice instance) {
		return instance != null ? instance.getJlptLevel() : null;
	}

	private static List<Field> findFieldsByValue(final Field[] fs, final Object instance, final Object value)
			throws IllegalAccessException {
		//
		Field f = null;
		//
		Object fieldValue = null;
		//
		List<Field> list = null;
		//
		Method methodIsAccessible = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if (!Narcissus.invokeBooleanMethod(f, methodIsAccessible = getIfNull(methodIsAccessible,
					VoiceManager::getAccessibleObjectIsAccessibleMethod))) {
				//
				if (ArrayUtils.contains(new String[] { "javax.swing", "java.awt" },
						getName(getPackage(getDeclaringClass(f))))) {
					//
					continue;
					//
				} // if
					//
				f.setAccessible(true);
				//
			} // if
				//
			if ((fieldValue = isStatic(f) ? f.get(null)
					: testAndApply((a, b) -> b != null, f, instance, (a, b) -> FieldUtils.readField(a, b),
							null)) != value
					|| !Objects.equals(fieldValue, value)) {
				//
				continue;
				//
			} // if
				//
			testAndAccept((a, b) -> !contains(a, b), list = ObjectUtils.getIfNull(list, ArrayList::new), f,
					(a, b) -> add(a, b), null);
			//
		} // for
			//
		return list;
		//
	}

	private static Method getAccessibleObjectIsAccessibleMethod() {
		//
		final List<Method> ms = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(AccessibleObject.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(getName(m), "isAccessible") && m.getParameterCount() == 0));
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
	private static <T, E extends Throwable> T getIfNull(final T object, final FailableSupplier<T, E> defaultSupplier)
			throws E {
		return object != null ? object : get(defaultSupplier);
	}

	private static <T, E extends Throwable> T get(final FailableSupplier<T, E> instance) throws E {
		return instance != null ? instance.get() : null;
	}

	private void exportHtml(final ObjectMap objectMap, final Multimap<String, Voice> multimap,
			final Entry<?, UnaryOperator<Object>> filePair, final Map<Object, Object> parameters,
			final Collection<File> files) throws IOException, TemplateException {
		//
		final Iterable<String> keySet = MultimapUtil.keySet(multimap);
		//
		if (iterator(keySet) != null) {
			//
			File file = null;
			//
			for (final String key : keySet) {
				//
				final Map<Object, Object> map = new LinkedHashMap<>();
				//
				try (final Writer writer = testAndApply(Objects::nonNull, file = testAndApply(Objects::nonNull,
						toString(apply(getValue(filePair), key)), File::new, null), FileWriter::new, null)) {
					//
					ObjectMap.setObject(objectMap, Writer.class, writer);
					//
					put(map, "folder", voiceFolder);
					//
					put(map, "voices", MultimapUtil.get(multimap, key));
					//
					final Collection<Entry<Object, Object>> entrySet = entrySet(parameters);
					//
					if (iterator(entrySet) != null) {
						//
						for (final Entry<?, ?> parameter : entrySet) {
							//
							if (parameter == null) {
								//
								continue;
								//
							} // if
								//
							map.put(toString(getKey(parameter)), getValue(parameter));
							//
						} // for
							//
					} // if
						//
					exportHtml(objectMap, toString(getKey(filePair)), map);
					//
					add(files, file);
					//
				} finally {
					//
					testAndAccept(x -> intValue(length(x), 0) == 0, file, VoiceManager::delete);
					//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	private static <T, R> R apply(final Function<T, R> instance, final T t) {
		return instance != null ? instance.apply(t) : null;
	}

	private static void exportHtml(final ObjectMap objectMap, final String templateFile,
			final Map<Object, Object> parameters) throws IOException, TemplateException {
		//
		final Version version = getIfNull(ObjectMap.getObject(objectMap, Version.class),
				freemarker.template.Configuration::getVersion);
		//
		final freemarker.template.Configuration configuration = getIfNull(
				ObjectMap.getObject(objectMap, freemarker.template.Configuration.class),
				() -> new freemarker.template.Configuration(version));
		//
		testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
				x -> setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
		//
		final Map<String, Object> map = new LinkedHashMap<>(
				Collections.singletonMap("statics", getIfNull(ObjectMap.getObject(objectMap, TemplateHashModel.class),
						() -> new BeansWrapper(version).getStaticModels())));
		//
		final Collection<Entry<Object, Object>> entrySet = entrySet(parameters);
		//
		if (iterator(entrySet) != null) {
			//
			for (final Entry<?, ?> parameter : entrySet) {
				//
				if (parameter == null) {
					//
					continue;
					//
				} // if
					//
				map.put(toString(getKey(parameter)), getValue(parameter));
				//
			} // for
				//
		} // if
			//
		process(testAndApply(Objects::nonNull, templateFile, a -> getTemplate(configuration, a), null), map,
				ObjectMap.getObject(objectMap, Writer.class));
		//
	}

	private static Template getTemplate(final freemarker.template.Configuration instance, final String name)
			throws IOException {
		//
		return instance != null && name != null && getTemplateLoader(instance) != null ? instance.getTemplate(name)
				: null;
		//
	}

	private static void process(final Template instance, final Object dataModel, final Writer out)
			throws TemplateException, IOException {
		//
		if (instance != null && out != null) {
			//
			instance.process(dataModel, out);
			//
		} // if
			//
	}

	public static Pair<String, String> getMimeTypeAndBase64EncodedString(final String folderPath, final String filePath)
			throws IOException {
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
				x -> encodeToString(Base64.getEncoder(), FileUtils.readFileToByteArray(x)), null));
		//
	}

	private static String encodeToString(final Encoder instance, final byte[] src) {
		return instance != null && src != null ? instance.encodeToString(src) : null;
	}

	private static Stopwatch stop(final Stopwatch instance) {
		return instance != null ? instance.stop() : null;
	}

	private static Duration elapsed(final Stopwatch instance) {
		return instance != null ? instance.elapsed() : null;
	}

	private static void browse(final Desktop instance, final URI uri) throws IOException {
		if (instance != null) {
			instance.browse(uri);
		}
	}

	private static URI toURI(final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static Class<?> getDeclaringClass(final Member instance) {
		return instance != null ? instance.getDeclaringClass() : null;
	}

	private static Package getPackage(final Class<?> instance) {
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

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> a, final BiConsumer<T, U> b) {
		if (test(instance, t, u)) {
			accept(a, t, u);
		} else {
			accept(b, t, u);
		} // if
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static File[] listFiles(final File instance) {
		return instance != null ? instance.listFiles() : null;
	}

	private static class VoiceThrowableMessageBiConsumer implements BiConsumer<Voice, String> {

		private boolean headless = false;

		private DefaultTableModel tableModel = null;

		private VoiceThrowableMessageBiConsumer(final boolean headless, final DefaultTableModel tableModel) {
			this.headless = headless;
			this.tableModel = tableModel;
		}

		@Override
		public void accept(final Voice v, final String m) {
			//
			if (headless) {
				//
				try {
					//
					errorOrPrintln(LOG, getSystemPrintStreamByFieldName("err"), m);
					//
				} catch (final IllegalAccessException e) {
					//
					final RuntimeException runtimeException = toRuntimeException(e);
					//
					if (runtimeException != null) {
						//
						throw runtimeException;
						//
					} // if
						//
				} // try
					//
			} else {
				//
				if (tableModel != null) {
					//
					addRow(tableModel, new Object[] { getText(v), getRomaji(v), m });
					//
				} else {
					//
					JOptionPane.showMessageDialog(null, m);
					//
				} // if
					//
			} // if
				//
		}

		private static void errorOrPrintln(final Logger logger, final PrintStream ps, final String message) {
			//
			if (logger != null) {
				//
				logger.error(message);
				//
			} else if (ps != null) {
				//
				ps.println(message);
				//
			} // if
				//
		}

	}

	private static class VoiceThrowableBiConsumer implements BiConsumer<Voice, Throwable> {

		private boolean headless = false;

		private DefaultTableModel tableModel = null;

		private VoiceThrowableBiConsumer(final boolean headless, final DefaultTableModel tableModel) {
			this.headless = headless;
			this.tableModel = tableModel;
		}

		@Override
		public void accept(final Voice v, final Throwable e) {
			//
			if (headless) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} else {
				//
				if (tableModel != null) {
					//
					addRow(tableModel, new Object[] { getText(v), getRomaji(v), e });
					//
				} else {
					//
					JOptionPane.showMessageDialog(null, e);
					//
				} // if
					//
			} // if
				//
		}

	}

	private static class VoiceConsumer implements Consumer<Voice> {

		private JTextComponent jTextComponent = null;

		private AtomicInteger atomicInteger = null;

		private VoiceConsumer(final JTextComponent jTextComponent, final AtomicInteger atomicInteger) {
			this.jTextComponent = jTextComponent;
			this.atomicInteger = atomicInteger;
		}

		@Override
		public void accept(final Voice v) {
			//
			setText(jTextComponent, getText(v));
			//
			incrementAndGet(atomicInteger);
			//
		}

	}

	private static PrintStream getSystemPrintStreamByFieldName(final String name) throws IllegalAccessException {
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, System.class.getDeclaredFields(), Arrays::stream, null),
						f -> Objects.equals(getType(f), PrintStream.class) && Objects.equals(getName(f), name)));
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return cast(PrintStream.class, f != null ? f.get(null) : null);
		//
	}

	private static Integer incrementAndGet(final AtomicInteger instance) {
		return instance != null ? Integer.valueOf(instance.incrementAndGet()) : null;
	}

	private void importVoice(final File file) {
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try (final Workbook workbook = getWorkbook(file)) {
			//
			if (workbook != null) {
				//
				setText(tfCurrentProcessingFile, getName(file));
				//
			} // if
				//
			final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
			//
			final POIXMLDocument poiXmlDocument = cast(POIXMLDocument.class, workbook);
			//
			final List<String> sheetExclued = toList(map(stream(getObjectList(getObjectMapper(),
					getLpwstr(testAndApply(VoiceManager::contains,
							POIXMLPropertiesUtil.getCustomProperties(POIXMLDocumentUtil.getProperties(poiXmlDocument)),
							"sheetExcluded", VoiceManager::getProperty, null)))),
					VoiceManager::toString));
			//
			ObjectMap.setObject(objectMap, File.class, file);
			//
			ObjectMap.setObject(objectMap, VoiceManager.class, this);
			//
			ObjectMap.setObject(objectMap, String.class, voiceFolder);
			//
			ObjectMap.setObject(objectMap, SqlSessionFactory.class, sqlSessionFactory);
			//
			ObjectMap.setObject(objectMap, JProgressBar.class, progressBarImport);
			//
			ObjectMap.setObject(objectMap, Provider.class, cast(Provider.class, speechApi));
			//
			ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
			//
			ObjectMap.setObject(objectMap, POIXMLDocument.class, poiXmlDocument);
			//
			ObjectMap.setObject(objectMap, Jakaroma.class, jakaroma = getIfNull(jakaroma, Jakaroma::new));
			//
			ObjectMap.setObject(objectMap, JSlider.class, jsSpeechVolume);
			//
			ObjectMap.setObject(objectMap, MessageDigest.class,
					MessageDigest.getInstance(StringUtils.defaultIfBlank(messageDigestAlgorithm, SHA_512)));
			//
			BiConsumer<Voice, String> errorMessageConsumer = null;
			//
			BiConsumer<Voice, Throwable> throwableConsumer = null;
			//
			Consumer<Voice> voiceConsumer = null;
			//
			Sheet sheet = null;
			//
			accept(VoiceManager::clear, tmImportResult, tmImportException);
			//
			Integer numberOfSheetProcessed = null;
			//
			final AtomicInteger numberOfVoiceProcessed = new AtomicInteger();
			//
			IValue0<String> voiceId = null;
			//
			Collection<Object> throwableStackTraceHexs = null;
			//
			for (int i = 0; i < intValue(getNumberOfSheets(workbook), 0); i++) {
				//
				if (contains(sheetExclued, getSheetName(sheet = WorkbookUtil.getSheetAt(workbook, i)))) {
					//
					continue;
					//
				} // if
					//
				setMaximum(progressBarImport, Math.max(0, (intValue(getPhysicalNumberOfRows(sheet), 0)) - 1));
				//
				ObjectMap.setObject(objectMap, ByteConverter.class,
						getByteConverter(configurableListableBeanFactory, FORMAT,
								getLpwstr(testAndApply(VoiceManager::contains,
										POIXMLPropertiesUtil
												.getCustomProperties(POIXMLDocumentUtil.getProperties(poiXmlDocument)),
										"audioFormat", VoiceManager::getProperty, null))));
				//
				if (voiceId == null) {
					//
					voiceId = Unit.with(getIfNull(toString(getSelectedItem(cbmVoiceId)),
							() -> getVoiceIdForExecute(!isTestMode())));
					//
				} // if
					//
				importVoice(sheet, objectMap, IValue0Util.getValue0(voiceId),
						//
						errorMessageConsumer = getIfNull(errorMessageConsumer,
								() -> new VoiceThrowableMessageBiConsumer(headless, tmImportException)),
						//
						throwableConsumer = getIfNull(throwableConsumer,
								() -> new VoiceThrowableBiConsumer(headless, tmImportException)),
						//
						voiceConsumer = getIfNull(voiceConsumer,
								() -> new VoiceConsumer(tfCurrentProcessingVoice, numberOfVoiceProcessed))
						//
						, throwableStackTraceHexs = ObjectUtils.getIfNull(throwableStackTraceHexs, ArrayList::new)
				//
				);
				//
				setText(tfCurrentProcessingSheetName, getSheetName(sheet));
				//
				numberOfSheetProcessed = Integer.valueOf(intValue(numberOfSheetProcessed, 0) + 1);
				//
			} // for
				//
			addRow(tmImportResult, new Object[] { numberOfSheetProcessed, numberOfVoiceProcessed });
			//
		} catch (final InvalidFormatException | IOException | IllegalAccessException | BaseException
				| GeneralSecurityException | SAXException | ParserConfigurationException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
	}

	private static Workbook getWorkbook(final File file) throws IOException, GeneralSecurityException,
			InvalidFormatException, SAXException, ParserConfigurationException {
		//
		final ContentInfo ci = testAndApply(Objects::nonNull, file, new ContentInfoUtil()::findMatch, null);
		//
		final String message = getMessage(ci);
		//
		final String mimeType = getMimeType(ci);
		//
		if (Objects.equals(message, OLE_2_COMPOUND_DOCUMENT)) {
			//
			try (final POIFSFileSystem poifs = new POIFSFileSystem(file)) {
				//
				final List<String> oleEntryNames = getOleEntryNames(poifs);
				//
				if (Objects.equals(oleEntryNames,
						Arrays.asList(Decryptor.DEFAULT_POIFS_ENTRY, EncryptionInfo.ENCRYPTION_INFO_ENTRY))) {
					//
					final Decryptor decryptor = Decryptor.getInstance(new EncryptionInfo(poifs));
					//
					if (decryptor != null && decryptor.verifyPassword(getPassword(System.console()))) {
						//
						try (final InputStream is = decryptor.getDataStream(poifs)) {
							//
							return new XSSFWorkbook(is);
							//
						} // try
							//
					} // if
						//
				} else if (contains(oleEntryNames, "Workbook")) {
					//
					try {
						//
						return new HSSFWorkbook(poifs);
						//
					} catch (final EncryptedDocumentException e) {
						//
						Biff8EncryptionKey.setCurrentUserPassword(getPassword(System.console()));
						//
						try {
							//
							return new HSSFWorkbook(poifs);
							//
						} finally {
							//
							Biff8EncryptionKey.setCurrentUserPassword(null);
							//
						} // try
							//
					} // try
						//
				} // if
					//
			} // try
				//
		} else if (Boolean.logicalOr(Objects.equals(message, "Microsoft Office Open XML"),
				Objects.equals(mimeType, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
			//
			return new XSSFWorkbook(file);
			//
		} else if (Objects.equals(mimeType, "application/zip")) {
			//
			return getWorkbookByZipFile(file);
			//
		} // if
			//
		return null;
		//
	}

	private static Workbook getWorkbookByZipFile(final File file)
			throws IOException, SAXException, ParserConfigurationException, InvalidFormatException {
		//
		final ContentInfo ci = testAndApply(x -> x != null && x.isFile(), file, new ContentInfoUtil()::findMatch, null);
		//
		try (final ZipFile zf = testAndApply(x -> Objects.equals(ContentType.ZIP, getContentType(ci)), file,
				ZipFile::new, null);
				final InputStream is = testAndApply(Objects::nonNull,
						testAndApply(Objects::nonNull, "[Content_Types].xml", x -> getEntry(zf, x), null),
						x -> getInputStream(zf, x), null)) {
			//
			final NodeList childNodes = getChildNodes(getDocumentElement(
					is != null ? parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), is) : null));
			//
			boolean isXlsx = false;
			//
			for (int i = 0; i < getLength(childNodes); i++) {
				//
				if (Objects.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
						getTextContent(getNamedItem(getAttributes(item(childNodes, i)), "ContentType")))
						&& (isXlsx = true)) {
					//
					break;
					//
				} // if
					//
			} // for
				//
			if (isXlsx) {
				//
				return new XSSFWorkbook(file);
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	private static ContentType getContentType(final ContentInfo instance) {
		return instance != null ? instance.getContentType() : null;
	}

	private static ZipEntry getEntry(final ZipFile instance, final String name) {
		return instance != null ? instance.getEntry(name) : null;
	}

	private static InputStream getInputStream(final ZipFile instance, final ZipEntry entry) throws IOException {
		return instance != null ? instance.getInputStream(entry) : null;
	}

	private static NamedNodeMap getAttributes(final Node instance) {
		return instance != null ? instance.getAttributes() : null;
	}

	private static List<String> getOleEntryNames(final POIFSFileSystem poifs) {
		//
		List<String> list = null;
		//
		final DirectoryNode root = poifs != null ? poifs.getRoot() : null;
		//
		final Iterator<org.apache.poi.poifs.filesystem.Entry> entries = root != null ? root.getEntries() : null;
		//
		org.apache.poi.poifs.filesystem.Entry entry = null;
		//
		while (entries != null && entries.hasNext()) {
			//
			if ((entry = entries.next()) == null) {
				//
				continue;
				//
			} // if
				//
			add(list = getIfNull(list, ArrayList::new), entry.getName());
			//
		} // while
			//
		return list;
		//
	}

	private static String getPassword(final Console console) {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			return testAndApply(Objects::nonNull, console != null ? console.readPassword(PASSWORD) : null, String::new,
					null);
			//
		} // if
			//
		final JTextComponent jtc = new JPasswordField();
		//
		return JOptionPane.showConfirmDialog(null, jtc, PASSWORD, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION
				? getText(jtc)
				: null;
		//
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	private static Document parse(final DocumentBuilder instance, final InputStream is)
			throws SAXException, IOException {
		return instance != null ? instance.parse(is) : null;
	}

	private static Element getDocumentElement(final Document instance) {
		return instance != null ? instance.getDocumentElement() : null;
	}

	private static NodeList getChildNodes(final Node instance) {
		return instance != null ? instance.getChildNodes() : null;
	}

	private static Node getNamedItem(final NamedNodeMap instance, final String name) {
		return instance != null ? instance.getNamedItem(name) : null;
	}

	private static String getTextContent(final Node instance) {
		return instance != null ? instance.getTextContent() : null;
	}

	private static String getName(final File instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static String getMessage(final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static boolean isFile(final File instance) {
		return instance != null && instance.isFile();
	}

	private static void setMaximum(final JProgressBar instance, final int n) {
		if (instance != null) {
			instance.setMaximum(n);
		}
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <T> void accept(final Consumer<? super T> action, final T a, final T b, final T... values) {
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

	private static <T> void accept(final Consumer<T> instance, final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

	private static String getSheetName(final Sheet instance) {
		return instance != null ? instance.getSheetName() : null;
	}

	private static Integer getPhysicalNumberOfRows(final Sheet instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfRows()) : null;
	}

	private static boolean contains(final CustomProperties instance, final String name) {
		return instance != null && instance.contains(name);
	}

	private static CTProperty getProperty(final CustomProperties instance, final String name) {
		return instance != null ? instance.getProperty(name) : null;
	}

	private static String getLpwstr(final CTProperty instance) {
		return instance != null ? instance.getLpwstr() : null;
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, jcbVoiceId)) {
			//
			try {
				//
				final String language = getVoiceAttribute(speechApi, toString(getSelectedItem(cbmVoiceId)), LANGUAGE);
				//
				setText(tfSpeechLanguageCode, language);
				//
				setText(tfSpeechLanguageName,
						StringUtils.defaultIfBlank(convertLanguageCodeToText(language, 16), language));
				//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, jcbJlptVocabulary)) {
			//
			final JlptVocabulary jv = cast(JlptVocabulary.class,
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

	private static String convert(final Jakaroma instance, final String input, final boolean trailingSpace,
			final boolean capitalizeWords) {
		return instance != null ? instance.convert(input, trailingSpace, capitalizeWords) : null;
	}

	private static Collection<Object> getByteConverterAttributeValues(
			final ConfigurableListableBeanFactory configurableListableBeanFactory, final String attribute) {
		//
		List<Object> list = null;
		//
		final Map<String, ByteConverter> byteConverters = ListableBeanFactoryUtil
				.getBeansOfType(configurableListableBeanFactory, ByteConverter.class);
		//
		final Set<Entry<String, ByteConverter>> entrySet = entrySet(byteConverters);
		//
		if (entrySet != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : entrySet) {
				//
				if (en == null || (bd = ConfigurableListableBeanFactoryUtil
						.getBeanDefinition(configurableListableBeanFactory, getKey(en))) == null
						|| !bd.hasAttribute(attribute)) {
					continue;
				} // if
					//
				add(list = getIfNull(list, ArrayList::new), bd.getAttribute(attribute));
				//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	private static ByteConverter getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String attribute, final Object value) {
		//
		IValue0<ByteConverter> byteConverter = null;
		//
		final Map<String, ByteConverter> byteConverters = ListableBeanFactoryUtil
				.getBeansOfType(configurableListableBeanFactory, ByteConverter.class);
		//
		final Set<Entry<String, ByteConverter>> entrySet = entrySet(byteConverters);
		//
		if (entrySet != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : entrySet) {
				//
				if (en == null
						|| (bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(configurableListableBeanFactory,
								getKey(en))) == null
						|| !bd.hasAttribute(attribute)
						|| !Objects.equals(value, testAndApply(bd::hasAttribute, attribute, bd::getAttribute, null))) {
					//
					continue;
					//
				} // if
					//
				if (byteConverter == null) {
					//
					byteConverter = Unit.with(getValue(en));
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

	@Override
	public void stateChanged(final ChangeEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, jsSpeechVolume)) {
			//
			setText(tfSpeechVolume, toString(getValue(jsSpeechVolume)));
			//
		} else if (Objects.equals(source, jsSpeechRate)) {
			//
			if (jsSpeechRate != null) {
				//
				setEnabled(btnSpeechRateSlower, intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMinimum());
				//
				setEnabled(btnSpeechRateFaster, intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMaximum());
				//
			} // if
				//
			setText(tfSpeechRate, toString(getValue(jsSpeechRate)));
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
		final Object source = getSource(evt);
		//
		final JTextComponent jtf = cast(JTextComponent.class, source);
		//
		if (Objects.equals(source, tfListNames)) {
			//
			try {
				//
				setBackground(jtf, Color.WHITE);
				//
				final ObjectMapper om = getObjectMapper();
				//
				final List<?> list = getObjectList(om, getText(jtf));
				//
				setText(jlListNames, ObjectMapperUtil.writeValueAsString(om, list));
				//
				setText(jlListNameCount, Integer.toString(IterableUtils.size(list)));
				//
			} catch (final Exception e) {
				//
				accept(x -> setText(x, null), jlListNames, jlListNameCount);
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

	private void keyReleasedForTextImport(final JTextComponent jTextComponent) {
		//
		keyReleasedForTextImport(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap), jTextComponent,
				cbmGaKuNenBeTsuKanJi);
		//
		// 常用漢字
		//
		final String text = getText(jTextComponent);
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
					StringUtils.length(text) <= orElse(max(mapToInt(stream(list), StringUtils::length)), 0)
							? contains(list, text)
							: null);
			//
		} // if
			//
	}

	private static void keyReleasedForTextImport(final Multimap<String, String> multiMap,
			final JTextComponent jTextComponent, final ComboBoxModel<String> comboBoxModel) {
		//
		final Collection<Entry<String, String>> entries = MultimapUtil.entries(multiMap);
		//
		if (iterator(entries) == null) {
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
			if (en == null || !StringUtils.equals(getValue(en), getText(jTextComponent))) {
				//
				continue;
				//
			} // if
				//
			if (!contains(list = getIfNull(list, ArrayList::new), key = getKey(en))) {
				//
				add(list = getIfNull(list, ArrayList::new), key);
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
		} // if
			//
	}

	private static void setJlptVocabularyAndLevel(final VoiceManager instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final String text = getText(instance.tfTextImport);
		//
		final MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = instance.cbmJlptVocabulary;
		//
		for (int i = getSize(cbmJlptVocabulary) - 1; i >= 0; i--) {
			//
			cbmJlptVocabulary.removeElementAt(i);
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
			final List<JlptVocabulary> temp = toList(filter(stream(jlptVocabularies),
					x -> Boolean.logicalOr(Objects.equals(text, getKanji(x)), Objects.equals(text, getKana(x)))));
			//
			forEach(temp, x -> addElement(cbmJlptVocabulary, x));
			//
			if (IterableUtils.size(temp) > 1) {
				//
				setSelectedItem(instance.cbmJlptLevel, null);
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						toList(map(stream(temp), VoiceManager::getLevel).distinct()),
						x -> setSelectedItemByString(cbmJlptLevel, IterableUtils.get(x, 0)));
				//
				return;
				//
			} // if
				//
			testAndAccept(Objects::nonNull,
					testAndApply(x -> IterableUtils.size(x) == 1, temp, x -> IterableUtils.get(x, 0), null),
					x -> setSelectedItemByString(cbmJlptLevel, getLevel(x)));
			//
		} // if
			//
	}

	private static String getKanji(final JlptVocabulary instance) {
		return instance != null ? instance.getKanji() : null;
	}

	private static String getKana(final JlptVocabulary instance) {
		return instance != null ? instance.getKana() : null;
	}

	private static String getLevel(final JlptVocabulary instance) {
		return instance != null ? instance.getLevel() : null;
	}

	private static void setSelectedItemByString(final ComboBoxModel<String> cbm, final String string) {
		//
		IntList intList = null;
		//
		for (int i = 0; i < getSize(cbm); i++) {
			//
			if (StringUtils.equalsAnyIgnoreCase(cbm.getElementAt(i), string)
					&& (intList = ObjectUtils.getIfNull(intList, IntList::new)) != null) {
				//
				intList.add(i);
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
		} else if (size == 1 && cbm != null) {
			//
			setSelectedItem(cbm, cbm.getElementAt(intList.get(0)));
			//
		} // if
			//
	}

	private static int getSize(final ListModel<?> instance) {
		return instance != null ? instance.getSize() : 0;
	}

	@Override
	public void changedUpdate(final DocumentEvent evt) {
		//
	}

	private static void setBackground(final Component instance, final Color color) {
		if (instance != null) {
			instance.setBackground(color);
		}
	}

	private static void setText(final JLabel instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static javax.swing.text.Document getDocument(final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
	}

	private static interface ByteConverter {

		byte[] convert(final byte[] source);

	}

	private static class AudioToFlacByteConverter implements ByteConverter {

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

		private static AudioFormat getFormat(final AudioInputStream instance) {
			return instance != null ? instance.getFormat() : null;
		}

		private static StreamConfiguration createStreamConfiguration(final AudioFormat format) {
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

	private static byte[] toByteArray(final ByteArrayOutputStream instance) {
		return instance != null ? instance.toByteArray() : null;
	}

	private static class AudioToMp3ByteConverter implements ByteConverter, InitializingBean {

		private static final Pattern PATTERN_NEW_ARRAY_FLOAT = Pattern.compile("^\\d+:\\s+newarray\\s+<float>$");

		private static final Pattern PATTERN_VBR_Q = Pattern
				.compile("^\\d+:\\s?getfield\\s+de.sciss.jump3r.mp3.LameGlobalFlags.VBR_q\\s+I\\s+\\(\\d+\\)$");

		private static final Pattern PATTERN_LDC_NUMBER = Pattern.compile("^\\d+:\\s+ldc\\s+[^\\s]+\\s+\\(\\d+\\)?");

		private static final Pattern PATTERN_LDC_STRING = Pattern
				.compile("^\\d+:\\s+ldc\\s+\"([^\"]+)\"\\s+\\(\\d+\\)$");

		private static final Pattern PATTERN_BI_PUSH = Pattern.compile("^\\d+:\\s+bipush\\s+(\\d+)$");

		private static final Pattern PATTERN_ICONST = Pattern.compile("^\\d+:\\s+iconst_(\\d+)$");

		@Note("Bitrate")
		private Integer bitRate = null;

		@Note("Quality")
		private Integer quality = null;

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
				String string = VoiceManager.toString(quality);
				//
				if (containsKey(map, string) || containsKey(map, string = StringUtils.lowerCase(string))) {
					//
					setQuality(get(map, string));
					//
				} // if
					//
			} // if
				//
		}

		private Integer getQuality() throws IllegalAccessException {
			//
			if (quality != null) {
				return quality;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_QUALITY", true);
			//
			if (object instanceof Integer) {
				return (Integer) object;
			} // if
				//
			return null;
			//
		}

		public void setVbr(final Object vbr) {
			//
			if (vbr == null) {
				//
				this.vbr = null;
				//
			} else if (vbr instanceof Boolean) {
				//
				this.vbr = (Boolean) vbr;
				//
			} else {
				//
				final String string = VoiceManager.toString(vbr);
				//
				setVbr(StringUtils.isNotBlank(string) ? Boolean.valueOf(string) : null);
				//
			} // if
				//
		}

		private Boolean getVbr() throws IllegalAccessException {
			//
			if (vbr != null) {
				return vbr;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_VBR", true);
			//
			if (object instanceof Boolean) {
				return (Boolean) object;
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
							lowerEndpoint(range), range.upperEndpoint()));
					//
				} // if
					//
			} // if
				//
		}

		private static Range<Integer> createQualityRange() throws IOException {
			//
			final Class<?> clz = Lame.class;
			//
			try (final InputStream is = getResourceAsStream(clz,
					String.format("/%1$s.class", StringUtils.replace(VoiceManager.getName(clz), ".", "/")))) {
				//
				final org.apache.bcel.classfile.Method[] ms = getMethods(
						ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
				//
				org.apache.bcel.classfile.Method m = null;
				//
				byte[] bs = null;
				//
				Map<Integer, Integer> map = null;
				//
				for (int i = 0; ms != null && i < ms.length; i++) {
					//
					if ((m = ms[i]) == null) {
						//
						continue;
						//
					} // if
						//
					put(map = getIfNull(map, LinkedHashMap::new), Integer.valueOf(i),
							createQuality(StringUtils
									.split(StringUtils.trim(Utility.codeToString(bs = CodeUtil.getCode(m.getCode()),
											m.getConstantPool(), 0, length(bs))), '\n')));
					//
				} // for
					//
				final List<Integer> counts = toList(
						filter(map(stream(entrySet(map)), VoiceManager::getValue), Objects::nonNull));
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

		private static Integer createQuality(final String[] lines) {
			//
			String line = null;
			//
			Integer index1 = null, index2 = null, count = null;
			//
			for (int j = 0; lines != null && j < lines.length; j++) {
				//
				if ((line = lines[j]) == null) {
					//
					continue;
					//
				} // if
					//
				if (index1 == null && matches(matcher(PATTERN_NEW_ARRAY_FLOAT, line))) {
					index1 = Integer.valueOf(j);
				} else if (index2 == null && matches(matcher(PATTERN_VBR_Q, line))) {
					index2 = Integer.valueOf(j);
					break;
				} // if
					//
				if (index1 != null && matches(matcher(PATTERN_LDC_NUMBER, line))) {
					count = Integer.valueOf(intValue(count, 0) + 1);
				} // if
					//
			} // for
				//
			return count;
			//
		}

		private static Map<String, Integer> createQualityMap() throws IOException {
			//
			Map<String, Integer> map = null;
			//
			final Class<?> clz = LameEncoder.class;
			//
			try (final InputStream is = getResourceAsStream(clz,
					String.format("/%1$s.class", StringUtils.replace(VoiceManager.getName(clz), ".", "/")))) {
				//
				final List<org.apache.bcel.classfile.Method> ms = toList(filter(testAndApply(Objects::nonNull,
						getMethods(ClassParserUtil
								.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null))),
						Arrays::stream, null), x -> Objects.equals(getName(x), "string2quality")));
				//
				org.apache.bcel.classfile.Method m = null;
				//
				byte[] bs = null;
				//
				String line, key = null;
				//
				String[] lines = null;
				//
				Matcher matcher = null;
				//
				for (int i = 0; i < IterableUtils.size(ms); i++) {
					//
					if ((m = VoiceManager.get(ms, i)) == null) {
						//
						continue;
						//
					} // if
						//
					lines = StringUtils.split(StringUtils.trim(Utility.codeToString(bs = CodeUtil.getCode(m.getCode()),
							m.getConstantPool(), 0, length(bs))), '\n');
					//
					for (int j = 0; lines != null && j < lines.length; j++) {
						//
						if (matches(matcher = matcher(PATTERN_LDC_STRING, line = lines[j]))
								&& matcher.groupCount() > 0) {
							key = matcher.group(1);
						} else if (matches(matcher = matcher(PATTERN_BI_PUSH, line)) && matcher.groupCount() > 0) {
							put(map = getIfNull(map, LinkedHashMap::new), key, valueOf(matcher.group(1)));
						} else if (matches(matcher = matcher(PATTERN_ICONST, line)) && matcher.groupCount() > 0) {
							put(map = getIfNull(map, LinkedHashMap::new), key, valueOf(matcher.group(1)));
						} // if
							//
					} // for
						//
				} // for
					//
				return map;
				//
			} // try
				//
		}

		private static String getName(final FieldOrMethod instance) {
			return instance != null ? instance.getName() : null;
		}

		private static org.apache.bcel.classfile.Method[] getMethods(final JavaClass instance) {
			return instance != null ? instance.getMethods() : null;
		}

		@Override
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
								cast(Integer.class,
										FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_BITRATE",
												true))),
						cast(Integer.class,
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

	private static boolean isSelected(final AbstractButton instance) {
		return instance != null && instance.isSelected();
	}

	private static String convertLanguageCodeToText(final String instance, final int base) {
		//
		return StringUtils.defaultIfBlank(convertLanguageCodeToText(LocaleID.values(), valueOf(instance, base)),
				instance);
		//
	}

	private static String convertLanguageCodeToText(final LocaleID[] enums, final Integer value) {
		//
		final List<LocaleID> localeIds = toList(filter(testAndApply(Objects::nonNull, enums, Arrays::stream, null),
				a -> a != null && Objects.equals(Integer.valueOf(a.getLcid()), value)));
		//
		if (localeIds != null && !localeIds.isEmpty()) {
			//
			if (IterableUtils.size(localeIds) == 1) {
				//
				final LocaleID localeId = get(localeIds, 0);
				//
				return localeId != null ? localeId.getDescription() : null;
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static void clear(final DefaultTableModel instance) {
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

	private static void delete(final File instance) {
		if (instance != null) {
			instance.delete();
		}
	}

	private static void deleteOnExit(final File instance) {
		if (instance != null) {
			instance.deleteOnExit();
		}
	}

	private static String getMp3TagValue(final File file, final Predicate<Object> predicate, final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		return getMp3TagValue(getMp3TagParirs(file, attributes), predicate);
		//
	}

	private static String getMp3TagValue(final List<Pair<String, ?>> pairs, final Predicate<Object> predicate) {
		//
		String string = null;
		//
		for (int i = 0; i < IterableUtils.size(pairs); i++) {
			//
			if (test(predicate, string = toString(getValue(cast(Pair.class, get(pairs, i))))) || predicate == null) {
				//
				break;
				//
			} // if
				//
		} // for
			//
		return string;
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static List<Pair<String, ?>> getMp3TagParirs(final File file, final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		if (Objects.equals("mp3", getFileExtension(cast(ContentInfo.class,
				testAndApply(VoiceManager::isFile, file, new ContentInfoUtil()::findMatch, null))))) {
			//
			final Mp3File mp3File = new Mp3File(file);
			//
			return getMp3TagParirs(ObjectUtils.defaultIfNull(mp3File.getId3v2Tag(), mp3File.getId3v1Tag()), attributes);
			//
		} // if
			//
		return null;
		//
	}

	private static List<Pair<String, ?>> getMp3TagParirs(final ID3v1 id3v1, final String... attributes)
			throws IllegalAccessException, InvocationTargetException {
		//
		List<Pair<String, ?>> pairs = null;
		//
		Method[] ms = null;
		//
		List<Method> methods = null;
		//
		Method m = null;
		//
		for (int i = 0; attributes != null && i < attributes.length; i++) {
			//
			final String attribute = attributes[i];
			//
			if ((methods = toList(filter(
					testAndApply(Objects::nonNull, ms = getIfNull(ms, () -> getMethods(getClass(id3v1))),
							Arrays::stream, null),
					a -> matches(matcher(Pattern.compile(String.format("get%1$s", StringUtils.capitalize(attribute))),
							getName(a)))))) == null
					|| methods.isEmpty()) {
				//
				continue;
				//
			} // if
				//
			if (IterableUtils.size(methods) == 1) {
				//
				if ((m = get(methods, 0)) != null && m.getParameterCount() == 0) {
					//
					add(pairs = getIfNull(pairs, ArrayList::new), Pair.of(attribute, invoke(m, id3v1)));
					//
				} // if
					//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return pairs;
		//
	}

	private static String getProviderName(final Provider instance) {
		return instance != null ? instance.getProviderName() : null;
	}

	private static String getProviderVersion(final Provider instance) {
		return instance != null ? instance.getProviderVersion() : null;
	}

	private static void execute(final ObjectMap objectMap) {
		//
		final File file = ObjectMap.getObject(objectMap, File.class);
		//
		final DefaultTableModel tmImportException = ObjectMap.getObject(objectMap, DefaultTableModel.class);
		//
		final Voice voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		clear(tmImportException);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		final boolean nonTest = !isTestMode();
		//
		final boolean nonHeadlessAndnonTest = !headless && nonTest;
		//
		if (file == null) {
			//
			testAndAccept((a, b) -> a != null, tmImportException, NO_FILE_SELECTED,
					//
					(a, b) -> addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonHeadlessAndnonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} else if (!file.exists()) {
			//
			testAndAccept((a, b) -> a != null, tmImportException,
					String.format("File \"%1$s\" does not exist", getAbsolutePath(file)),
					//
					(a, b) -> addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonHeadlessAndnonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} else if (!isFile(file)) {
			//
			testAndAccept((a, b) -> a != null, tmImportException, "Not A Regular File Selected",
					//
					(a, b) -> addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonHeadlessAndnonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} else if (longValue(length(file), 0) == 0) {
			//
			testAndAccept((a, b) -> a != null, tmImportException, "Empty File Selected",
					//
					(a, b) -> addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} // if
			//
		SqlSession sqlSession = null;
		//
		try {
			//
			importVoice(objectMap, new VoiceThrowableMessageBiConsumer(headless, tmImportException),
					new VoiceThrowableBiConsumer(headless, tmImportException));
			//
		} finally {
			//
			IOUtils.closeQuietly(sqlSession);
			//
		} // try
			//
	}

	private static void writeVoiceToFile(final ObjectMap objectMap, final String text, final String voiceId,
			final Integer rate, final Integer volume) {
		//
		final SpeechApi speechApi = ObjectMap.getObject(objectMap, SpeechApi.class);
		//
		if (speechApi != null) {
			//
			speechApi.writeVoiceToFile(text, voiceId
			//
					, intValue(rate, 0)// rate
					//
					, Math.min(Math.max(intValue(volume, 100), 0), 100)// volume
					, ObjectMap.getObject(objectMap, File.class));
			//
		} // if
			//
	}

	private Integer getRate() {
		//
		final Integer speechRate = getValue(jsSpeechRate);
		//
		return speechRate != null ? speechRate : getRate(getText(tfSpeechRate));
		//
	}

	private static Integer getRate(final String string) {
		//
		Integer rate = valueOf(string);
		//
		if (rate == null) {
			//
			rate = getIfNull(rate, () -> getRate(toList(filter(
					testAndApply(Objects::nonNull, getDeclaredFields(Integer.class), Arrays::stream, null),
					f -> f != null
							&& (isAssignableFrom(Number.class, getType(f)) || Objects.equals(Integer.TYPE, getType(f)))
							&& Objects.equals(getName(f), string)))));
			//
		} // if
			//
		return rate;
		//
	}

	private static Integer getRate(final List<Field> fs) {
		//
		if (fs != null && !fs.isEmpty()) {
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Field f = get(fs, 0);
			//
			if (f != null && isStatic(f)) {
				//
				try {
					//
					final Number number = cast(Number.class, f.get(null));
					//
					return number != null ? Integer.valueOf(number.intValue()) : null;
					//
				} catch (final IllegalAccessException e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static Integer getRate(final VoiceManager instance) {
		return instance != null ? instance.getRate() : null;
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static Annotation[] getDeclaredAnnotations(final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	private static byte[] createImportFileTemplateByteArray(final boolean generateBlankRow,
			final Collection<String> jlptValues, final Collection<String> gaKuNenBeTsuKanJiValues) {
		//
		Workbook workbook = null;
		//
		byte[] bs = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Class<?> importFieldClass = forName("domain.Voice$ImportField");
			//
			final List<Field> fs = toList(
					filter(testAndApply(Objects::nonNull, FieldUtils.getAllFields(Voice.class), Arrays::stream, null),
							f -> anyMatch(
									testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
									a -> Objects.equals(annotationType(a), importFieldClass))));
			//
			Field f = null;
			//
			Sheet sheet = null;
			//
			Row row = null;
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if ((f = get(fs, i)) == null) {
					//
					continue;
					//
				} // if
					//
				if (sheet == null) {
					//
					sheet = WorkbookUtil.createSheet(workbook = getIfNull(workbook, XSSFWorkbook::new));
					//
				} // if
					//
				if (row == null) {
					//
					row = SheetUtil.createRow(sheet, 0);
					//
				} // if
					//
				CellUtil.setCellValue(RowUtil.createCell(row, i), getName(f));
				//
			} // for
				//
			if (generateBlankRow && fs != null) {
				//
				addImportFileTemplateBlankRow(sheet, fs, headless, jlptValues, gaKuNenBeTsuKanJiValues);
				//
			} // if
				//
			WorkbookUtil.write(workbook, baos);
			//
			bs = toByteArray(baos);
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} finally {
			//
			IOUtils.closeQuietly(workbook);
			//
		} // try
			//
		return bs;
		//
	}

	private static void addImportFileTemplateBlankRow(final Sheet sheet, final List<Field> fs, final boolean headless,
			final Collection<String> jlptValues, final Collection<String> gaKuNenBeTsuKanJiValues) {
		//
		final Row row = SheetUtil.createRow(sheet, intValue(getPhysicalNumberOfRows(sheet), 0));
		//
		ObjectMap objectMap = null;
		//
		Field f = null;
		//
		Class<?> type;
		//
		DataValidationHelper dvh = null;
		//
		IValue0<List<Boolean>> booleans = null;
		//
		final Class<?> classJlpt = forName("domain.Voice$JLPT");
		//
		final Class<?> classGaKuNenBeTsuKanJi = forName("domain.Voice$GaKuNenBeTsuKanJi");
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			CellUtil.setCellValue(RowUtil.createCell(row, i), null);
			//
			if (objectMap == null) {
				//
				ObjectMap.setObject(objectMap = Reflection.newProxy(ObjectMap.class, new IH()), Row.class, row);
				//
				ObjectMap.setObject(objectMap, Sheet.class, sheet);
				//
			} // if
				//
			if (Objects.equals(Boolean.class, type = getType(f = get(fs, i)))) {// java.lang.Boolean
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				if (booleans == null) {
					//
					try {
						//
						booleans = Unit.with(getBooleanValues());
						//
					} catch (final IllegalAccessException e) {
						//
						errorOrAssertOrShowException(headless, e);
						//
					} // try
						//
				} // if
					//
				addValidationDataForBoolean(objectMap, booleans, i);
				//
			} else if (isAssignableFrom(Enum.class, type)) {// java.lang.Enum
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				addValidationDataForEnum(objectMap, type, i);
				//
			} else if (anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
					a -> Objects.equals(annotationType(a), classJlpt))) {// domain.Voice.JLPT
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				addValidationDataForValues(objectMap, jlptValues, i);
				//
			} else if (anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
					a -> Objects.equals(annotationType(a), classGaKuNenBeTsuKanJi))) {// domain.Voice.GaKuNenBeTsuKanJi
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				addValidationDataForValues(objectMap, gaKuNenBeTsuKanJiValues, i);
				//
			} // if
				//
		} // for
			//
	}

	private static void addValidationDataForValues(final ObjectMap objectMap, final Collection<String> values,
			final int index) {
		//
		final DataValidationHelper dvh = ObjectMap.getObject(objectMap, DataValidationHelper.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if ((!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(values)) && row != null) {
			//
			addValidationData(ObjectMap.getObject(objectMap, Sheet.class),
					createValidation(dvh, createExplicitListConstraint(dvh, toArray(values, new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	private static void addValidationDataForBoolean(final ObjectMap objectMap, final IValue0<List<Boolean>> booleans,
			final int index) {
		//
		final Collection<Boolean> bs = IValue0Util.getValue0(booleans);
		//
		final DataValidationHelper dvh = ObjectMap.getObject(objectMap, DataValidationHelper.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if ((!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(bs)) && row != null) {
			//
			addValidationData(ObjectMap.getObject(objectMap, Sheet.class),
					createValidation(dvh,
							createExplicitListConstraint(dvh,
									toArray(toList(map(stream(bs), VoiceManager::toString)), new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	private static void addValidationDataForEnum(final ObjectMap objectMap, final Class<?> type, final int index) {
		//
		final List<String> strings = toList(
				map(testAndApply(Objects::nonNull, getEnumConstants(type), Arrays::stream, null),
						x -> x instanceof Enum ? name((Enum<?>) x) : toString(x)));
		//
		final DataValidationHelper dvh = ObjectMap.getObject(objectMap, DataValidationHelper.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if ((!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(strings)) && row != null) {
			//
			addValidationData(ObjectMap.getObject(objectMap, Sheet.class),
					createValidation(dvh, createExplicitListConstraint(dvh, toArray(strings, new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	private static void addValidationData(final Sheet instance, final DataValidation dataValidation) {
		if (instance != null) {
			instance.addValidationData(dataValidation);
		}
	}

	private static DataValidationHelper getDataValidationHelper(final Sheet instance) {
		return instance != null ? instance.getDataValidationHelper() : null;
	}

	private static DataValidationConstraint createExplicitListConstraint(final DataValidationHelper instance,
			final String[] listOfValues) {
		return instance != null ? instance.createExplicitListConstraint(listOfValues) : null;
	}

	private static DataValidation createValidation(final DataValidationHelper instance,
			final DataValidationConstraint constraint, final CellRangeAddressList cellRangeAddressList) {
		return instance != null ? instance.createValidation(constraint, cellRangeAddressList) : null;
	}

	private static <T> boolean anyMatch(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				&& instance.anyMatch(predicate);
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		//
		return instance != null ? instance.toList() : null;
		//
	}

	private static interface ObjectMap {

		<T> T getObject(final Class<T> key);

		boolean containsObject(final Class<?> key);

		<T> void setObject(final Class<T> key, final T value);

		static <T> T getObject(final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> void setObject(final ObjectMap instance, final Class<T> key, final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
		}

		static <T> boolean containsObject(final ObjectMap instance, final Class<T> key) {
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

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, final String value);

		static String getString(final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}

		static void setString(final StringMap instance, final String key, final String value) {
			if (instance != null) {
				instance.setString(key, value);
			}
		}
	}

	private static class ImportTask implements Runnable {

		private static final Logger LOG = LoggerFactory.getLogger(ImportTask.class);

		private Pair<Integer, Integer> sheetCurrentAndTotal = null;

		private Integer counter = null;

		private Integer count = null;

		private ObjectMap objectMap = null;

		private BiConsumer<Voice, String> errorMessageConsumer = null;

		private BiConsumer<Voice, Throwable> throwableConsumer = null;

		private Consumer<Voice> voiceConsumer = null;

		private Voice voice = null;

		private File file = null;

		private NumberFormat percentNumberFormat = null;

		private String currentSheetName = null;

		@Override
		public void run() {
			//
			final Integer sheetCurrent = getKey(sheetCurrentAndTotal);
			//
			final Integer sheetTotal = getValue(sheetCurrentAndTotal);
			//
			final Fraction fraction1 = sheetTotal != null && sheetTotal.intValue() != 0
					? Fraction.getFraction(intValue(sheetCurrent, 0), sheetTotal.intValue())
					: null;
			//
			Fraction fraction2 = sheetTotal != null ? Fraction.getFraction(1, sheetTotal) : null;
			//
			if (fraction2 != null && count != null) {
				//
				fraction2 = fraction2.multiplyBy(Fraction.getFraction(intValue(counter, 0), count.intValue()));
				//
			} // if
				//
			final Fraction percentage = add(fraction1, fraction2);
			//
			try {
				//
				infoOrPrintln(LOG, getSystemPrintStreamByFieldName("out"),
						String.format("%1$s %2$s/%3$s (%4$s) %5$s/%6$s",
								percentage != null
										? StringUtils.leftPad(format(percentNumberFormat, percentage.doubleValue()), 5,
												' ')
										: null,
								StringUtils.leftPad(VoiceManager.toString(sheetCurrent),
										StringUtils.length(
												VoiceManager.toString(ObjectUtils.max(sheetCurrent, sheetTotal))),
										' '),
								sheetTotal, currentSheetName, StringUtils.leftPad(VoiceManager.toString(counter),
										StringUtils.length(VoiceManager.toString(count))),
								count));
				//
			} catch (final IllegalAccessException e) {
				//
				final RuntimeException runtimeException = toRuntimeException(e);
				//
				if (runtimeException != null) {
					//
					throw runtimeException;
					//
				} // if
					//
			} // try
				//
			SqlSession sqlSession = null;
			//
			try {
				//
				final SqlSessionFactory sqlSessionFactory = ObjectMap.getObject(objectMap, SqlSessionFactory.class);
				//
				ObjectMap.setObject(objectMap, VoiceMapper.class, getMapper(getConfiguration(sqlSessionFactory),
						VoiceMapper.class, sqlSession = openSession(sqlSessionFactory)));
				//
				ObjectMap.setObject(objectMap, Voice.class, voice);
				//
				ObjectMap.setObject(objectMap, File.class, file);
				//
				importVoice(objectMap, errorMessageConsumer, throwableConsumer);
				//
				if (counter != null) {
					//
					final JProgressBar progressBar = ObjectMap.getObject(objectMap, JProgressBar.class);
					//
					setValue(progressBar, counter.intValue());
					//
					if (count != null) {
						//
						final String string = String.format("%1$s/%2$s (%3$s)", counter, count,
								format(percentNumberFormat, counter.intValue() * 1.0 / count.intValue()));
						//
						setToolTipText(progressBar, string);
						//
						setString(progressBar, string);
						//
					} // if
						//
					accept(voiceConsumer, voice);
					//
				} // if
					//
			} finally {
				//
				IOUtils.closeQuietly(sqlSession);
				//
			} // try
				//
		}

		private static Fraction add(final Fraction a, final Fraction b) {
			return a != null && b != null ? a.add(b) : a;
		}

		private static void infoOrPrintln(final Logger logger, final PrintStream ps, final String value) {
			//
			if (logger != null && !LoggerUtil.isNOPLogger(logger)) {
				logger.info(value);
			} else if (ps != null) {
				ps.println(value);
			} // if
				//
		}

	}

	private static void setValue(final JProgressBar instance, final int n) {
		if (instance != null) {
			instance.setValue(n);
		}
	}

	private static void setString(final JProgressBar instance, final String string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

	private static void setToolTipText(final JComponent instance, final String toolTipText) {
		if (instance != null) {
			instance.setToolTipText(toolTipText);
		}
	}

	private static String format(final NumberFormat instance, final double number) {
		return instance != null ? instance.format(number) : null;
	}

	private static interface IntMap<T> {

		T getObject(final int key);

		boolean containsKey(final int key);

		void setObject(final int key, final T value);

		static <T> T getObject(final IntMap<T> instance, final int key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> boolean containsKey(final IntMap<T> instance, final int key) {
			return instance != null && instance.containsKey(key);
		}

		static <T> void setObject(final IntMap<T> instance, final int key, final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
		}

	}

	private static interface IntIntMap {

		int getInt(final int key);

		boolean containsKey(final int key);

		void setInt(final int key, final int value);

	}

	private static void importVoice(final Sheet sheet, final ObjectMap _objectMap, final String voiceId,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer, final Collection<Object> throwableStackTraceHexs)
			throws IllegalAccessException, IOException, InvocationTargetException, BaseException {
		//
		final File folder = getParentFile(ObjectMap.getObject(_objectMap, File.class));
		//
		IntMap<Field> intMap = null;
		//
		ExecutorService es = null;
		//
		try {
			//
			if (iterator(sheet) == null) {
				//
				return;
				//
			} // if
				//
			boolean first = true;
			//
			Field[] fs = null;
			//
			Voice voice = null;
			//
			Field f = null;
			//
			int columnIndex;
			//
			ImportTask it = null;
			//
			NumberFormat percentNumberFormat = null;
			//
			String filePath = null;
			//
			VoiceManager voiceManager = null;
			//
			Provider provider = null;
			//
			JSlider jsSpeechVolume = null;
			//
			String[] mp3Tags = null;
			//
			SpeechApi speechApi = null;
			//
			ByteConverter byteConverter = null;
			//
			Jakaroma jakaroma = null;
			//
			final CustomProperties customProperties = POIXMLPropertiesUtil.getCustomProperties(
					POIXMLDocumentUtil.getProperties(ObjectMap.getObject(_objectMap, POIXMLDocument.class)));
			//
			final boolean hiraganaKatakanaConversion = BooleanUtils.toBooleanDefaultIfNull(
					IValue0Util.getValue0(getBoolean(customProperties, "hiraganaKatakanaConversion")), false);
			//
			final boolean hiraganaRomajiConversion = BooleanUtils.toBooleanDefaultIfNull(
					IValue0Util.getValue0(getBoolean(customProperties, "hiraganaRomajiConversion")), false);
			//
			ObjectMapper objectMapper = null;
			//
			final Workbook workbook = sheet.getWorkbook();
			//
			final Integer numberOfSheets = getNumberOfSheets(workbook);
			//
			final int maxSheetNameLength = orElse(
					max(mapToInt(
							map(testAndApply(Objects::nonNull, spliterator(workbook),
									x -> StreamSupport.stream(x, false), null), VoiceManager::getSheetName),
							StringUtils::length)),
					0);
			//
			FormulaEvaluator formulaEvaluator = null;
			//
			IValue0<?> value = null;
			//
			for (final Row row : sheet) {
				//
				if (iterator(row) == null) {
					continue;
				} // if
					//
				voice = null;
				//
				final ObjectMap objectMap = ObjectUtils.defaultIfNull(copyObjectMap(_objectMap), _objectMap);
				//
				IH ih = null;
				//
				for (final Cell cell : row) {
					//
					if (cell == null) {
						//
						continue;
						//
					} // if
						//
					if (first) {
						//
						IntMap.setObject(intMap = getIfNull(intMap, () -> Reflection.newProxy(IntMap.class, new IH())),
								cell.getColumnIndex(),
								orElse(findFirst(testAndApply(Objects::nonNull,
										fs = getIfNull(fs, () -> FieldUtils.getAllFields(Voice.class)), Arrays::stream,
										null)
										.filter(field -> Objects.equals(getName(field), cell.getStringCellValue()))),
										null));
						//
					} else if (IntMap.containsKey(intMap, columnIndex = cell.getColumnIndex())
							&& (f = IntMap.getObject(intMap, columnIndex)) != null) {
						//
						f.setAccessible(true);
						//
						ObjectMap.setObject(objectMap, Field.class, f);
						//
						ObjectMap.setObject(objectMap, Cell.class, cell);
						//
						ObjectMap.setObject(objectMap, ObjectMapper.class,
								objectMapper = getIfNull(objectMapper, ObjectMapper::new));
						//
						ObjectMap.setObject(objectMap, FormulaEvaluator.class,
								formulaEvaluator = getIfNull(formulaEvaluator,
										() -> createFormulaEvaluator(getCreationHelper(workbook))));
						//
						ifElse((value = getValueFromCell(objectMap)) == null, () -> {
							throw new IllegalStateException();
						}, null);
						//
						f.set(voice = getIfNull(voice, Voice::new), IValue0Util.getValue0(value));
						//
					} // if
						//
				} // for
					//
				setHiraganaOrKatakanaAndRomaji(hiraganaKatakanaConversion, hiraganaRomajiConversion, voice,
						jakaroma = ObjectUtils.getIfNull(jakaroma,
								() -> ObjectMap.getObject(objectMap, Jakaroma.class)));
				//
				if (first) {
					//
					first = false;
					//
				} else {
					//
					if ((es = getIfNull(es, () -> Executors.newFixedThreadPool(1))) != null) {
						//
						(it = new ImportTask()).sheetCurrentAndTotal = Pair.of(getCurrentSheetIndex(sheet),
								numberOfSheets);
						//
						it.currentSheetName = StringUtils.leftPad(getSheetName(sheet), maxSheetNameLength);
						//
						it.counter = Integer.valueOf(row.getRowNum());
						//
						it.count = Integer.valueOf(intValue(getPhysicalNumberOfRows(sheet), 0) - 1);
						//
						it.percentNumberFormat = getIfNull(percentNumberFormat, () -> new DecimalFormat("#%"));
						//
						if ((it.voice = voice) != null) {
							//
							final VoiceManager vm = voiceManager = getIfNull(voiceManager,
									() -> ObjectMap.getObject(objectMap, VoiceManager.class));
							//
							if (StringUtils.isNotBlank(filePath = getFilePath(voice))) {
								//
								if (!(it.file = new File(filePath)).exists()) {
									//
									it.file = new File(folder, filePath);
									//
								} // if
									//
								setSource(it.voice,
										StringUtils.defaultIfBlank(getSource(voice),
												getMp3TagValue(it.file, x -> StringUtils.isNotBlank(toString(x)),
														mp3Tags = getIfNull(mp3Tags, () -> getMp3Tags(vm)))));
								//
							} else {
								//
								if (isInstalled(speechApi = getIfNull(speechApi,
										() -> ObjectMap.getObject(objectMap, SpeechApi.class)))) {
									//
									if ((it.file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH),
											filePath)) != null) {
										//
										ObjectMap.setObject(objectMap, File.class, it.file);
										//
										writeVoiceToFile(objectMap, getText(voice),
												//
												// voiceId
												//
												voiceId
												//
												// rate
												//
												, getRate(vm),
												//
												// volume
												//
												Math.min(Math.max(intValue(
														getValue(jsSpeechVolume = getIfNull(jsSpeechVolume,
																() -> ObjectMap.getObject(_objectMap, JSlider.class))),
														100), 0), 100)
										//
										);
										//
										if ((byteConverter = getIfNull(byteConverter,
												() -> ObjectMap.getObject(objectMap, ByteConverter.class))) != null) {
											//
											FileUtils.writeByteArrayToFile(it.file,
													byteConverter.convert(FileUtils.readFileToByteArray(it.file)));
											//
										} // if
											//
										deleteOnExit(it.file);
										//
									} // if
										//
									setSource(it.voice,
											StringUtils.defaultIfBlank(getSource(voice),
													getProviderName(provider = getIfNull(provider,
															() -> ObjectMap.getObject(objectMap, Provider.class)))));
									//
									try {
										//
										setLanguage(it.voice,
												StringUtils.defaultIfBlank(getLanguage(it.voice),
														convertLanguageCodeToText(
																getVoiceAttribute(speechApi, voiceId, LANGUAGE), 16)));
										//
									} catch (final Error e) {
										//
										TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
										//
									} // try
										//
								} // if
									//
							} // if
								//
						} // if
							//
						it.objectMap = ObjectUtils.defaultIfNull(copyObjectMap(objectMap), objectMap);
						//
						it.errorMessageConsumer = errorMessageConsumer;
						//
						it.throwableConsumer = throwableConsumer;
						//
						it.voiceConsumer = voiceConsumer;
						//
						// Wrap the java.lang.Runnable by a java.lang.reflect.Proxy
						//
						(ih = new IH()).runnable = it;
						//
						ih.throwableStackTraceHexs = throwableStackTraceHexs;
						//
						es.submit(ObjectUtils.defaultIfNull(Reflection.newProxy(Runnable.class, ih), it));
						//
					} else {
						//
						ObjectMap.setObject(objectMap, Voice.class, voice);
						//
						ObjectMap.setObject(objectMap, File.class, testAndApply(Objects::nonNull, voice,
								x -> new File(folder, getFilePath(x)), x -> folder));
						//
						importVoice(objectMap, errorMessageConsumer, throwableConsumer);
						//
						accept(voiceConsumer, voice);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} finally {
			//
			shutdown(es);
			//
		} // try
			//
	}

	private static void setHiraganaOrKatakanaAndRomaji(final boolean hiraganaKatakanaConversion,
			final boolean hiraganaRomajiConversion, final Voice voice, final Jakaroma jakaroma) {
		//
		if (voice != null) {
			//
			// org.springframework.context.support.VoiceManager.setHiraganaOrKatakana(domain.Voice)
			//
			testAndAccept(a -> hiraganaKatakanaConversion, voice, a -> setHiraganaOrKatakana(a));
			//
			// org.springframework.context.support.VoiceManager.setRomaji(domain.Voice,fr.free.nrw.jakaroma.Jakaroma)
			//
			testAndAccept((a, b) -> hiraganaRomajiConversion, voice, jakaroma, (a, b) -> setRomaji(a, b));
			//
		} // if
			///
	}

	private static String[] getMp3Tags(final VoiceManager instance) {
		return instance != null ? instance.mp3Tags : null;
	}

	private static IValue0<?> getValueFromCell(final ObjectMap objectMap) {
		//
		final Field f = ObjectMap.getObject(objectMap, Field.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final Class<?> type = getType(f);
		//
		final CellType cellType = getCellType(cell);
		//
		List<?> list = null;
		//
		IValue0<?> value = null;
		//
		if (Objects.equals(type, String.class)) {
			//
			if (Objects.equals(cellType, CellType.NUMERIC)) {
				//
				value = Unit.with(Double.toString(cell.getNumericCellValue()));
				//
			} else {
				//
				value = Unit.with(cell.getStringCellValue());
				//
			} // if
				//
		} else if (isAssignableFrom(Enum.class, type) && (list = toList(
				filter(testAndApply(Objects::nonNull, getEnumConstants(type), Arrays::stream, null), e -> {
					//
					final String name = name(cast(Enum.class, e));
					//
					final String stringCellValue = cell.getStringCellValue();
					//
					return Objects.equals(name, stringCellValue) || (StringUtils.isNotEmpty(stringCellValue)
							&& StringUtils.startsWithIgnoreCase(name, stringCellValue));
					//
				}))) != null) {
			//
			if (list.isEmpty()) {
				//
				value = Unit.with(null);
				//
			} else if (IterableUtils.size(list) == 1) {
				//
				value = Unit.with(get(list, 0));
				//
			} else {
				//
				throw new IllegalStateException("list.size()>1");
				//
			} // if
				//
		} else if (Objects.equals(type, Iterable.class)) {
			//
			value = Unit.with(toList(map(stream(
					getObjectList(ObjectMap.getObject(objectMap, ObjectMapper.class), cell.getStringCellValue())),
					VoiceManager::toString)));
			//
		} else if (Objects.equals(type, Integer.class)) {
			//
			value = getIntegerValueFromCell(objectMap);
			//
		} else if (Objects.equals(type, Boolean.class)) {
			//
			value = getBooleanValueFromCell(objectMap);
			//
		} // if
			//
		return value;
		//
	}

	private static IValue0<Integer> getIntegerValueFromCell(final ObjectMap objectMap) {
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final CellType cellType = getCellType(cell);
		//
		IValue0<Integer> value = null;
		//
		final Double D = Objects.equals(cellType, CellType.NUMERIC) ? Double.valueOf(cell.getNumericCellValue()) : null;
		//
		if (D != null) {
			//
			value = Unit.with(Integer.valueOf(D.intValue()));
			//
		} else {
			//
			value = Unit.with(valueOf(cell.getStringCellValue()));
			//
		} // if
			//
		return value;
		//
	}

	private static IValue0<Boolean> getBooleanValueFromCell(final ObjectMap objectMap) {
		//
		final FormulaEvaluator formulaEvaluator = ObjectMap.getObject(objectMap, FormulaEvaluator.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final CellType cellType = getCellType(cell);
		//
		String string = null;
		//
		IValue0<Boolean> value = null;
		//
		if (Objects.equals(cellType, CellType.BOOLEAN)) {
			//
			value = Unit.with(cell.getBooleanCellValue());
			//
		} else if (Objects.equals(cellType, CellType.FORMULA) && formulaEvaluator != null) {
			//
			value = Unit.with(getBooleanValue(formulaEvaluator.evaluate(cell)));
			//
		} else if (StringUtils.isNotEmpty(string = cell.getStringCellValue())) {
			//
			value = Unit.with(Boolean.valueOf(string));
			//
		} // if
			//
		return value;
		//
	}

	private static CellType getCellType(final Cell instance) {
		return instance != null ? instance.getCellType() : null;
	}

	private static Boolean getBooleanValue(final CellValue instance) {
		return instance != null ? Boolean.valueOf(instance.getBooleanValue()) : null;
	}

	private static FormulaEvaluator createFormulaEvaluator(final CreationHelper instance) {
		return instance != null ? instance.createFormulaEvaluator() : null;
	}

	private static File getParentFile(final File instance) {
		return instance != null ? instance.getParentFile() : null;
	}

	private static <T> Spliterator<T> spliterator(final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	private static String getFilePath(final Voice instance) {
		return instance != null ? instance.getFilePath() : null;
	}

	private static Integer getCurrentSheetIndex(final Sheet sheet) {
		//
		Integer currentSheetIndex = null;
		//
		final Workbook workbook = sheet != null ? sheet.getWorkbook() : null;
		//
		if (workbook != null) {
			//
			final int numberOfSheets = getNumberOfSheets(workbook);
			//
			for (int i = 0; i < numberOfSheets; i++) {
				//
				if (!Objects.equals(workbook.getSheetName(i), getSheetName(sheet))) {
					continue;
				} // if
					//
				if (currentSheetIndex == null) {
					currentSheetIndex = Integer.valueOf(i);
				} else {
					throw new IllegalStateException();
				} // if
					//
			} // for
				//
		} // if
			//
		return currentSheetIndex;
		//
	}

	private static <T> T[] getEnumConstants(final Class<T> instance) {
		return instance != null ? instance.getEnumConstants() : null;
	}

	private static IValue0<Boolean> getBoolean(final CustomProperties instance, final String name) {
		//
		final CTProperty ctProperty = testAndApply(VoiceManager::contains, instance, name, VoiceManager::getProperty,
				null);
		//
		final Boolean B = ctProperty != null ? ctProperty.getBool() : null;
		//
		IValue0<Boolean> result = null;
		//
		final String lLpwstr = getLpwstr(ctProperty);
		//
		if (StringUtils.isNotBlank(lLpwstr)) {
			//
			result = Unit.with(Boolean.valueOf(lLpwstr));
			//
		} else if (B != null) {
			//
			result = Unit.with(B);
			//
		} // if
			//
		return result;
		//
	}

	private static String getText(final Voice instance) {
		return instance != null ? instance.getText() : null;
	}

	private static String getRomaji(final Voice instance) {
		return instance != null ? instance.getRomaji() : null;
	}

	private static String getHiragana(final Voice instance) {
		return instance != null ? instance.getHiragana() : null;
	}

	private static void setHiraganaOrKatakana(final Voice voice) {
		//
		final String hiragana = getHiragana(voice);
		//
		final String katakana = voice != null ? voice.getKatakana() : null;
		//
		final boolean isHiraganaBlank = StringUtils.isBlank(hiragana);
		//
		final boolean isKatakanaBlank = StringUtils.isBlank(katakana);
		//
		if ((isHiraganaBlank || isKatakanaBlank) && !(isHiraganaBlank && isKatakanaBlank)) {
			//
			if (isHiraganaBlank) {
				//
				voice.setHiragana(testAndApply(Objects::nonNull, katakana,
						x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA), null));
				//
			} else {
				//
				voice.setKatakana(testAndApply(Objects::nonNull, hiragana,
						x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_HIRA_TO_ZEN_KATA), null));
				//
			} // if
				//
		} // if
			//
	}

	private static void setRomaji(final Voice voice, final Jakaroma jakaroma) {
		//
		final String romaji = getRomaji(voice);
		//
		final String hiragana = getHiragana(voice);
		//
		if (StringUtils.isBlank(romaji) && StringUtils.isNotBlank(hiragana)) {
			//
			voice.setRomaji(convert(jakaroma, hiragana, false, false));
			//
		} // if
			//
	}

	private static ObjectMap copyObjectMap(final ObjectMap instance) {
		//
		if (instance != null && Proxy.isProxyClass(getClass(instance))) {
			//
			final IH ihOld = cast(IH.class, Proxy.getInvocationHandler(instance));
			//
			final IH ihNew = new IH();
			//
			if (ihOld != null) {
				//
				ihNew.objects = ObjectUtils.defaultIfNull(
						testAndApply(Objects::nonNull, ihOld.objects, LinkedHashMap::new, null), ihNew.objects);
				//
			} // if
				//
			return Reflection.newProxy(ObjectMap.class, ihNew);
			//
		} // if
			//
		return null;
		//
	}

	private static void shutdown(final ExecutorService instance) {
		if (instance != null) {
			instance.shutdown();
		}
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <E> void add(final List<E> instance, final int index, final E element) {
		if (instance != null) {
			instance.add(index, element);
		}
	}

	private static void importVoice(final ObjectMap objectMap, final BiConsumer<Voice, String> errorMessageConsumer,
			final BiConsumer<Voice, Throwable> throwableConsumer) {
		//
		final File selectedFile = ObjectMap.getObject(objectMap, File.class);
		//
		final Voice voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		if (!checkImportFile(selectedFile, voice, errorMessageConsumer)) {
			//
			return;
			//
		} // if
			//
		try {
			//
			final String fileExtension = getFileExtension(new ContentInfoUtil().findMatch(selectedFile));
			//
			if (fileExtension == null) {
				//
				accept(errorMessageConsumer, voice, "File Extension is null");
				//
				return;
				//
			} else if (StringUtils.isEmpty(fileExtension)) {
				//
				accept(errorMessageConsumer, voice, "File Extension is Empty");
				//
				return;
				//
			} else if (StringUtils.isBlank(fileExtension)) {
				//
				accept(errorMessageConsumer, voice, "File Extension is Blank");
				//
				return;
				//
			} // if
				//
			String filePath = null;
			//
			final String text = getText(voice);
			//
			final String romaji = getRomaji(voice);
			//
			final VoiceMapper voiceMapper = ObjectMap.getObject(objectMap, VoiceMapper.class);
			//
			final Voice voiceOld = voiceMapper != null ? voiceMapper.searchByTextAndRomaji(text, romaji) : null;
			//
			final MessageDigest md = ObjectMap.getObject(objectMap, MessageDigest.class);
			//
			final String messageDigestAlgorithm = getAlgorithm(md);
			//
			Long length = length(selectedFile);
			//
			String fileDigest = Hex.encodeHexString(digest(md, FileUtils.readFileToByteArray(selectedFile)));
			//
			final String voiceFolder = ObjectMap.getObject(objectMap, String.class);
			//
			if (voiceOld == null || !Objects.equals(voiceOld.getFileLength(), length(selectedFile))
					|| !Objects.equals(voiceOld.getFileDigestAlgorithm(), messageDigestAlgorithm)
					|| !Objects.equals(voiceOld.getFileDigest(), fileDigest)) {
				//
				final StringBuilder fileName = new StringBuilder(
						String.format("%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS_%1$tL.%2$s", new Date(), fileExtension));
				//
				File file = new File(voiceFolder, filePath = toString(fileName));
				//
				if (file.exists()) {
					//
					file = new File(voiceFolder, filePath = toString(
							fileName.insert(StringUtils.lastIndexOf(fileName, '.') + 1, randomAlphabetic(2) + ".")));
					//
				} // if
					//
				FileUtils.copyFile(selectedFile, file);
				//
				length = length(file);
				//
				fileDigest = Hex.encodeHexString(digest(md, FileUtils.readFileToByteArray(file)));
				//
			} else {
				//
				final File file = new File(voiceFolder, getFilePath(voiceOld));
				//
				if (!file.exists()) {
					//
					FileUtils.copyFile(selectedFile, file);
					//
				} // if
					//
				filePath = getFilePath(voiceOld);
				//
			} // if
				//
			final VoiceManager voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
			//
			if (voiceManager != null) {
				//
				setText(voiceManager.tfFile, StringUtils.defaultString(filePath, getText(voiceManager.tfFile)));
				//
				setText(voiceManager.tfFileLength, toString(length));
				//
				setText(voiceManager.tfFileDigest, fileDigest);
				//
			} // if
				//
			if (voice != null) {
				//
				voice.setFilePath(filePath);
				//
				voice.setFileLength(length);
				//
				voice.setFileDigestAlgorithm(messageDigestAlgorithm);
				//
				voice.setFileDigest(fileDigest);
				//
				voice.setFileExtension(fileExtension);
				//
			} // if
				//
			insertOrUpdate(voiceMapper, voice);
			//
		} catch (final IOException e) {
			//
			accept(throwableConsumer, voice, e);
			//
		} // try
			//
	}

	private static boolean checkImportFile(final File file, final Voice voice,
			final BiConsumer<Voice, String> errorMessageConsumer) {
		//
		if (file == null) {
			//
			accept(errorMessageConsumer, voice, NO_FILE_SELECTED);
			//
			return false;
			//
		} else if (!file.exists()) {
			//
			accept(errorMessageConsumer, voice, String.format("File \"%1$s\" does not exist", getAbsolutePath(file)));
			//
			return false;
			//
		} else if (!isFile(file)) {
			//
			accept(errorMessageConsumer, voice, "Not A Regular File Selected");
			//
			return false;
			//
		} else if (longValue(length(file), 0) == 0) {
			//
			accept(errorMessageConsumer, voice, "Empty File Selected");
			//
			return false;
			//
		} // if
			//
		return true;
		//
	}

	private static String getAlgorithm(final MessageDigest instance) {
		return instance != null ? instance.getAlgorithm() : null;
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) {
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

		private Map<Object, Object> getBooleans() {
			if (booleans == null) {
				booleans = new LinkedHashMap<>();
			}
			return booleans;
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

		private Map<Object, Object> getStrings() {
			if (strings == null) {
				strings = new LinkedHashMap<>();
			}
			return strings;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
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
			} else if (proxy instanceof BooleanMap) {
				//
				value = handleBooleanMap(methodName, getBooleans(), args);
				//
			} else if (proxy instanceof IntMap) {
				//
				value = handleIntMap(methodName, getIntMapObjects(), args);
				//
			} else if (proxy instanceof IntIntMap) {
				//
				value = handleIntIntMap(methodName, getIntIntMapObjects(), args);
				//
			} else if (proxy instanceof StringMap) {
				//
				value = handleStringMap(methodName, getStrings(), args);
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

		private static IValue0<Object> handleRunnable(final Method method, final Runnable runnable, final Object[] args,
				final Collection<Object> throwableStackTraceHexs) throws Throwable {
			//
			try {
				//
				if (Objects.equals(getName(method), "run")) {
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
						final String hex = testAndApply(Objects::nonNull, getBytes(VoiceManager.toString(w)),
								DigestUtils::sha512Hex, null);
						//
						if (!contains(throwableStackTraceHexs, hex)) {
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
							add(throwableStackTraceHexs, hex);
							//
						} // if
							//
					} // try
						//
				} // if
					//
				throw targetExceptionRootCause;
				//
			} // try
				//
			return null;
			//
		}

		private static void printStackTrace(final Throwable throwable) {
			//
			final List<Method> ms = toList(filter(
					testAndApply(Objects::nonNull, getDeclaredMethods(Throwable.class), Arrays::stream, null),
					m -> m != null && StringUtils.equals(getName(m), "printStackTrace") && m.getParameterCount() == 0));
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

		private static IValue0<Object> handleObjectMap(final String methodName, final Map<Object, Object> map,
				final Object[] args) {
			//
			if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
							testAndApply(IH::isArray, cast(Class.class, key), IH::getSimpleName, x -> key)));
					//
				} // if
					//
				return Unit.with(get(map, key));
				//
			} else if (Objects.equals(methodName, "containsObject") && args != null && args.length > 0) {
				//
				return Unit.with(Boolean.valueOf(containsKey(map, args[0])));
				//
			} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
				//
				put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static IValue0<Object> handleBooleanMap(final String methodName, final Map<Object, Object> map,
				final Object[] args) {
			//
			if (Objects.equals(methodName, "getBoolean") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE, key));
					//
				} // if
					//
				return Unit.with(get(map, key));
				//
			} else if (Objects.equals(methodName, "setBoolean") && args != null && args.length > 1) {
				//
				put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static IValue0<Object> handleIntMap(final String methodName, final Map<Object, Object> map,
				final Object[] args) {
			//
			if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE, key));
					//
				} // if
					//
				return Unit.with(get(map, key));
				//
			} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
				//
				return Unit.with(containsKey(map, args[0]));
				//
			} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
				//
				put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static IValue0<Object> handleIntIntMap(final String methodName, final Map<Object, Object> map,
				final Object[] args) {
			//
			if (Objects.equals(methodName, "getInt") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE, key));
					//
				} // if
					//
				return Unit.with(get(map, key));
				//
			} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
				//
				return Unit.with(containsKey(map, args[0]));
				//
			} else if (Objects.equals(methodName, "setInt") && args != null && args.length > 1) {
				//
				put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static IValue0<Object> handleStringMap(final String methodName, final Map<Object, Object> map,
				final Object[] args) {
			//
			if (Objects.equals(methodName, "getString") && args != null && args.length > 0) {
				//
				final Object key = args[0];
				//
				if (!containsKey(map, key)) {
					//
					throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE, key));
					//
				} // if
					//
				return Unit.with(get(map, key));
				//
			} else if (Objects.equals(methodName, "setString") && args != null && args.length > 1) {
				//
				put(map, args[0], args[1]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

		private static boolean isArray(final OfField<?> instance) {
			return instance != null && instance.isArray();
		}

		private static String getSimpleName(final Class<?> instance) {
			return instance != null ? instance.getSimpleName() : null;
		}

	}

	private static Voice createVoice(final ObjectMapper objectMapper, final VoiceManager instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Voice voice = new Voice();
		//
		voice.setLanguage(getText(instance.tfLanguage));
		//
		voice.setText(getText(instance.tfTextImport));
		//
		setSource(voice, getText(instance.tfSource));
		//
		voice.setRomaji(getText(instance.tfRomaji));
		//
		voice.setHiragana(getText(instance.tfHiragana));
		//
		voice.setKatakana(getText(instance.tfKatakana));
		//
		voice.setYomi(cast(Yomi.class, getSelectedItem(instance.cbmYomi)));
		//
		setListNames(voice, toList(
				map(stream(getObjectList(objectMapper, getText(instance.tfListNames))), VoiceManager::toString)));
		//
		voice.setJlptLevel(toString(getSelectedItem(instance.cbmJlptLevel)));
		//
		voice.setIpaSymbol(getText(instance.tfIpaSymbol));
		//
		voice.setIsKanji(cast(Boolean.class, getSelectedItem(instance.cbmIsKanji)));
		//
		voice.setJouYouKanji(cast(Boolean.class, getSelectedItem(instance.cbmJouYouKanJi)));
		//
		voice.setGaKuNenBeTsuKanJi(toString(getSelectedItem(instance.cbmGaKuNenBeTsuKanJi)));
		//
		voice.setPronunciationPageUrl(getText(instance.tfPronunciationPageUrl));
		//
		return voice;
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(getClass(instance)))) {
			instance.forEach(action);
		} // if
			//
	}

	private static <T, E extends Throwable> void forEach(final Iterable<T> items,
			final FailableConsumer<? super T, E> action) throws E {
		//
		if (iterator(items) != null && (action != null || Proxy.isProxyClass(getClass(items)))) {
			//
			for (final T item : items) {
				//
				action.accept(item);
				//
			} // for
				//
		} // if
			//
	}

	private static class ExportTask implements Runnable {

		private static String FILE_NAME_PREFIX_PADDING = orElse(
				min(stream(IteratorUtils.toList(new RgxGen("\\d").iterateUnique())), StringUtils::compare), null);

		@Note("Count")
		private Integer count;

		@Note("Counter")
		private Integer counter;

		private Integer ordinalPositionDigit;

		private Voice voice = null;

		private Map<String, String> outputFolderFileNameExpressions = null;

		private EvaluationContext evaluationContext = null;

		private ExpressionParser expressionParser = null;

		private NumberFormat percentNumberFormat = null;

		private boolean overMp3Title = false;

		@Note("Ordinal Position As File Name Prefix")
		private boolean ordinalPositionAsFileNamePrefix = false;

		@Note("Export Presentation")
		private boolean exportPresentation = false;

		@Note("Embed Audio In Presentation")
		private boolean embedAudioInPresentation = false;

		private boolean hideAudioImageInPresentation = false;

		private VoiceManager voiceManager = null;

		private Fraction pharse = null;

		private String ordinalPositionFileNamePrefix = null;

		@Note("Export Presentation Template")
		private String exportPresentationTemplate = null;

		@Note("Folder In Presentation")
		private String folderInPresentation = null;

		private Table<String, String, Voice> voiceFileNames = null;

		private ObjectMapper objectMapper = null;

		private String password = null;

		private String messageDigestAlgorithm = null;

		@Override
		public void run() {
			//
			try {
				//
				final String filePath = getFilePath(voice);
				//
				final Set<Entry<String, String>> entrySet = entrySet(outputFolderFileNameExpressions);
				//
				if (Boolean.logicalOr(filePath == null, iterator(entrySet) == null)) {
					//
					return;
					//
				} // if
					//
				final Voice v = clone(objectMapper, Voice.class, voice);
				//
				setStringFieldDefaultValue(v);
				//
				setVariable(evaluationContext, VOICE, ObjectUtils.defaultIfNull(v, voice));
				//
				String key = null;
				//
				String value, ordinalPositionString, voiceFolder = null;
				//
				StringBuilder fileName = null;
				//
				File fileSource = null;
				//
				File fileDestination;
				//
				File folder = null;
				//
				JProgressBar progressBar = null;
				//
				final String outputFolder = getOutputFolder(voiceManager);
				//
				for (final Entry<String, String> folderFileNamePattern : entrySet) {
					//
					if (folderFileNamePattern == null || (key = getKey(folderFileNamePattern)) == null
							|| StringUtils.isBlank(value = getValue(folderFileNamePattern))
							|| !(fileSource = testAndApply(Objects::nonNull,
									voiceFolder = getIfNull(voiceFolder, () -> getVoiceFolder(voiceManager)),
									x -> new File(x, filePath), x -> new File(filePath))).exists()) {
						//
						continue;
						//
					} // if
						//
						// fileName
						//
					clear(fileName = getIfNull(fileName, StringBuilder::new));
					//
					if (ordinalPositionAsFileNamePrefix && StringUtils
							.isNotBlank(ordinalPositionString = VoiceManager.toString(getOrdinalPosition(voice)))) {
						//
						append(append(fileName,
								ordinalPositionDigit != null
										? StringUtils.leftPad(ordinalPositionString, ordinalPositionDigit.intValue(),
												StringUtils.defaultString(FILE_NAME_PREFIX_PADDING))
										: ordinalPositionString),
								StringUtils.defaultIfBlank(ordinalPositionFileNamePrefix, ""));
						//
					} // if
						//
					append(fileName, VoiceManager.toString(getValue(expressionParser, evaluationContext, value)));
					//
					// org.apache.commons.io.FileUtils.copyFile(java.io.File,java.io.File)
					//
					final String k = key;
					//
					FileUtils.copyFile(fileSource,
							fileDestination = new File(VoiceManager.toString(testAndApply(Objects::nonNull,
									folder = getIfNull(folder,
											() -> testAndApply(Objects::nonNull, outputFolder, File::new, null)),
									x -> new File(x, k), x -> new File(k))), VoiceManager.toString(fileName)));
					//
					TableUtil.put(voiceFileNames, fileDestination.getParent(), VoiceManager.toString(fileName), voice);
					//
					// Set MP3 Title if "overMp3Title" is true
					//
					testAndAccept(x -> overMp3Title, fileDestination, ExportTask::setMp3Title);
					//
				} // for
					//
				if (counter != null) {
					//
					setValue(progressBar = getIfNull(progressBar, () -> getProgressBarExport(voiceManager)),
							counter.intValue());
					//
					if (count != null) {
						//
						setMaximum(progressBar, count.intValue());
						//
						final String string = String.format("%1$s/%2$s (%3$s)", counter, count,
								format(percentNumberFormat, counter.intValue() * 1.0 / count.intValue()));
						//
						setToolTipText(progressBar, string);
						//
						setString(progressBar, string);
						//
						if (counter.intValue() == count.intValue() && pharse != null) {
							//
							FieldUtils.writeDeclaredField(pharse, "numerator", pharse.getNumerator() + 1, true);
							//
						} // if
							//
					} // if
						//
				} // if
					//
				showPharse(voiceManager, pharse);
				//
				if (pharse != null && pharse.getNumerator() == pharse.getDenominator() && Objects.equals(counter, count)
						&& exportPresentation) {
					//
					try (final InputStream is = getResourceAsStream(VoiceManager.class, exportPresentationTemplate)) {
						//
						final IH ih = new IH();
						//
						// org.springframework.context.support.VoiceManager$BooleanMap
						//
						final BooleanMap booleanMap = Reflection.newProxy(BooleanMap.class, ih);
						//
						BooleanMap.setBoolean(booleanMap, EMBED_AUDIO_IN_PRESENTATION, embedAudioInPresentation);
						//
						BooleanMap.setBoolean(booleanMap, HIDE_AUDIO_IMAGE_IN_PRESENTATION,
								hideAudioImageInPresentation);
						//
						// org.springframework.context.support.VoiceManager$StringMap
						//
						final StringMap stringMap = Reflection.newProxy(StringMap.class, ih);
						//
						StringMap.setString(stringMap, FOLDER_IN_PRESENTATION, folderInPresentation);
						//
						StringMap.setString(stringMap, MESSAGE_DIGEST_ALGORITHM, messageDigestAlgorithm);
						//
						StringMap.setString(stringMap, "password", password);
						//
						// org.springframework.context.support.VoiceManager$ObjectMap
						//
						final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
						//
						ObjectMap.setObject(objectMap, InputStream.class, is);
						//
						ObjectMap.setObject(objectMap, BooleanMap.class, booleanMap);
						//
						ObjectMap.setObject(objectMap, StringMap.class, stringMap);
						//
						generateOdfPresentationDocuments(objectMap, voiceFileNames);
						//
					} // try
						//
				} // if
					//
			} catch (final Exception e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		}

		private static String getOutputFolder(final VoiceManager instance) {
			return instance != null ? instance.outputFolder : null;
		}

		private static String getVoiceFolder(final VoiceManager instance) {
			return instance != null ? instance.voiceFolder : null;
		}

		private static JProgressBar getProgressBarExport(final VoiceManager instance) {
			return instance != null ? instance.progressBarExport : null;
		}

		private static void setStringFieldDefaultValue(final Object instance) throws IllegalAccessException {
			//
			final Field[] fs = getDeclaredFields(VoiceManager.getClass(instance));
			//
			Field f = null;
			//
			for (int i = 0; fs != null && i < fs.length; i++) {
				//
				if ((f = fs[i]) == null || !Objects.equals(getType(f), String.class)) {
					//
					continue;
					//
				} // if
					//
				f.setAccessible(true);
				//
				f.set(instance, StringUtils.defaultString(VoiceManager.toString(f.get(instance))));
				//
			} // if
				//
		}

		private static <T> T clone(final ObjectMapper objectMapper, final Class<T> clz, final T instance)
				throws IOException {
			//
			return objectMapper != null && clz != null
					? objectMapper.readValue(ObjectMapperUtil.writeValueAsBytes(objectMapper, instance), clz)
					: null;
			//
		}

		private static void setVariable(final EvaluationContext instance, final String name, final Object value) {
			if (instance != null) {
				instance.setVariable(name, value);
			}
		}

		private static void showPharse(final VoiceManager voiceManager, final Fraction pharse) {
			//
			if (voiceManager != null && pharse != null) {
				//
				setText(voiceManager.tfPhraseCounter, VoiceManager.toString(pharse.getNumerator()));
				//
				setText(voiceManager.tfPhraseTotal, VoiceManager.toString(pharse.getDenominator()));
				//
			} //
				//
		}

		private static <T> Optional<T> min(final Stream<T> instance, final Comparator<? super T> comparator) {
			//
			return instance != null && (Proxy.isProxyClass(VoiceManager.getClass(instance)) || comparator != null)
					? instance.min(comparator)
					: null;
			//
		}

		private static void setMp3Title(final File file)
				throws IOException, BaseException, IllegalAccessException, InvocationTargetException {
			//
			final String fileExtension = getFileExtension(cast(ContentInfo.class,
					testAndApply(VoiceManager::isFile, file, new ContentInfoUtil()::findMatch, null)));
			//
			if (Objects.equals("mp3", fileExtension)) {
				//
				final File tempFile = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null);
				//
				deleteOnExit(tempFile);
				//
				FileUtils.copyFile(file, tempFile);
				//
				final Mp3File mp3File = new Mp3File(tempFile);
				//
				final ID3v1 id3v1 = ObjectUtils.defaultIfNull(mp3File.getId3v2Tag(), mp3File.getId3v1Tag());
				//
				final String titleOld = id3v1 != null ? id3v1.getTitle() : null;
				//
				String titleNew = titleOld;
				//
				if (StringUtils.isNotEmpty(titleNew)) {
					//
					final String fileName = getName(file);
					//
					if (StringUtils.isNotBlank(fileName) && StringUtils
							.endsWith(titleNew = StringUtils.substringBeforeLast(fileName, fileExtension), ".")) {
						//
						titleNew = StringUtils.substringBeforeLast(titleNew, ".");
						//
					} // if
						//
					if (!Objects.equals(titleOld, titleNew)) {
						//
						id3v1.setTitle(titleNew);
						//
						mp3File.save(getAbsolutePath(file));
						//
					} // if
						//
				} // if
					//
			} // if
				//
		}

		private static void generateOdfPresentationDocuments(final ObjectMap om,
				final Table<String, String, Voice> table) throws Exception {
			//
			final Set<String> rowKeySet = rowKeySet(table);
			//
			if (rowKeySet == null) {
				//
				return;
				//
			} // if
				//
			final byte[] bs = testAndApply(Objects::nonNull, ObjectMap.getObject(om, InputStream.class),
					IOUtils::toByteArray, null);
			//
			OdfPresentationDocument odfPd = null;
			//
			ObjectMap objectMap = null;
			//
			File file = null;
			//
			int counter = 0;
			//
			final int size = IterableUtils.size(rowKeySet);
			//
			Stopwatch stopwatch = null;
			//
			String elapsedString = null;
			//
			int maxElapsedStringLength = 0;
			//
			final StringMap stringMap = ObjectMap.getObject(om, StringMap.class);
			//
			for (final String rowKey : rowKeySet) {
				//
				if (objectMap == null) {
					//
					ObjectMap.setObject(objectMap = Reflection.newProxy(ObjectMap.class, new IH()), byte[].class, bs);
					//
					ObjectMap.setObject(objectMap, XPath.class, newXPath(XPathFactory.newDefaultInstance()));
					//
					ObjectMap.setObject(objectMap, Transformer.class, newTransformer(TransformerFactory.newInstance()));
					//
					ObjectMap.setObject(objectMap, BooleanMap.class, ObjectMap.getObject(om, BooleanMap.class));
					//
					ObjectMap.setObject(objectMap, StringMap.class, stringMap);
					//
				} // if
					//
				StringMap.setString(stringMap, "outputFolder", rowKey);
				//
				if ((odfPd = generateOdfPresentationDocument(objectMap, table.row(rowKey))) != null) {
					//
					final String[] fileExtensions = getFileExtensions(ContentType.OPENDOCUMENT_PRESENTATION);
					//
					if ((stopwatch = getIfNull(stopwatch, Stopwatch::createUnstarted)) != null) {
						//
						stopwatch.reset();
						//
						stopwatch.start();
						//
					} // if
						//
						// Set "Password"
						//
					testAndAccept((a, b) -> StringUtils.isNotEmpty(b), odfPd.getPackage(),
							StringMap.getString(stringMap, "password"), (a, b) -> setPassword(a, b));
					//
					odfPd.save(file = new File(rowKey, String.join(".",
							StringUtils.substringAfter(rowKey, File.separatorChar),
							StringUtils.defaultIfBlank(
									fileExtensions != null && fileExtensions.length == 1 ? fileExtensions[0] : null,
									"odp"))));
					//
					info(LOG,
							String.format("%1$s/%2$s,Elapsed=%3$s,File=%4$s",
									StringUtils.leftPad(Integer.toString(++counter),
											StringUtils.length(Integer.toString(size))),
									size,
									//
									// elapsed
									//
									StringUtils.leftPad(elapsedString = VoiceManager.toString(elapsed(stopwatch)),
											maxElapsedStringLength),
									//
									// File
									//
									getAbsolutePath(file)));
					//
					maxElapsedStringLength = Math.max(maxElapsedStringLength, StringUtils.length(elapsedString));
					//
				} // if
					//
			} // for
				//
		}

		private static void setPassword(final OdfPackage instance, final String password) {
			if (instance != null) {
				instance.setPassword(password);
			}
		}

		private static <R> Set<R> rowKeySet(final Table<R, ?, ?> instance) {
			return instance != null ? instance.rowKeySet() : null;
		}

		private static void info(final Logger instance, final String message) {
			if (instance != null) {
				instance.info(message);
			}
		}

		private static OdfPresentationDocument generateOdfPresentationDocument(final ObjectMap objectMap,
				final Map<String, Voice> voices) throws Exception {
			//
			OdfPresentationDocument newOdfPresentationDocument = null;
			//
			try (final InputStream is = testAndApply(Objects::nonNull, ObjectMap.getObject(objectMap, byte[].class),
					ByteArrayInputStream::new, null)) {
				//
				final Set<Entry<String, Voice>> entrySet = entrySet(voices);
				//
				if (entrySet != null) {
					//
					final Document document = testAndApply(Objects::nonNull,
							testAndApply(Objects::nonNull,
									testAndApply(Objects::nonNull, is, x -> ZipUtil.unpackEntry(x, "content.xml"),
											null),
									ByteArrayInputStream::new, null),
							x -> parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), x), null);
					//
					final XPath xp = ObjectMap.getObject(objectMap, XPath.class);
					//
					final NodeList pages = cast(NodeList.class, testAndApply(Objects::nonNull, document, x -> evaluate(
							xp,
							"/*[local-name()='document-content']/*[local-name()='body']/*[local-name()='presentation']/*[local-name()='page']",
							x, XPathConstants.NODESET), null));
					//
					final Node page = testAndApply(x -> getLength(x) == 1, pages, x -> item(x, 0), null);
					//
					final Node parentNode = getParentNode(page);
					//
					Node pageCloned = null;
					//
					Voice voice, temp = null;
					//
					final boolean embedAudioInPresentation = BooleanMap
							.getBoolean(ObjectMap.getObject(objectMap, BooleanMap.class), EMBED_AUDIO_IN_PRESENTATION);
					//
					final boolean hideAudioImageInPresentation = BooleanMap.getBoolean(
							ObjectMap.getObject(objectMap, BooleanMap.class), HIDE_AUDIO_IMAGE_IN_PRESENTATION);
					//
					final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
					//
					final String folderInPresentation = StringMap.getString(stringMap, FOLDER_IN_PRESENTATION);
					//
					for (final Entry<String, Voice> entry : entrySet) {
						//
						if (Boolean.logicalOr((voice = getValue(entry)) == null,
								(pageCloned = cloneNode(page, true)) == null)) {
							//
							continue;
							//
						} // if
							//
							// p
							//
						ObjectMap.setObject(objectMap, Node.class, pageCloned);
						//
						if (!ObjectMap.containsObject(objectMap, ObjectMapper.class)) {
							//
							ObjectMap.setObject(objectMap, ObjectMapper.class, new ObjectMapper());
							//
						} // if
							//
						if (!ObjectMap.containsObject(objectMap, Pattern.class)) {
							//
							ObjectMap.setObject(objectMap, Pattern.class, Pattern.compile("(\\w+:)?href"));
							//
						} // if
							//
						setStringFieldDefaultValue(
								temp = clone(objectMap.getObject(ObjectMapper.class), Voice.class, voice));
						//
						ObjectMap.setObject(objectMap, Voice.class, ObjectUtils.defaultIfNull(temp, voice));
						//
						replaceText(objectMap, StringMap.getString(stringMap, MESSAGE_DIGEST_ALGORITHM));
						//
						// plugin
						//
						setPluginHref(objectMap, getKey(entry), embedAudioInPresentation, folderInPresentation);
						//
						// Delete customShape with the name is "AudioCoverImage" if
						// "hideAudioImageInPresentation" is true
						//
						testAndRun(hideAudioImageInPresentation,
								() -> removeCustomShapeByName(objectMap, "AudioCoverImage"));
						//
						appendChild(parentNode, pageCloned);
						//
					} // for
						//
					removeChild(parentNode, page);
					//
					final StringWriter writer = new StringWriter();
					//
					transform(ObjectMap.getObject(objectMap, Transformer.class), new DOMSource(document),
							new StreamResult(writer));
					//
					newOdfPresentationDocument = generateOdfPresentationDocument(VoiceManager.toString(writer),
							StringMap.getString(stringMap, "outputFolder"), keySet(voices), embedAudioInPresentation,
							folderInPresentation);
					//
				} // if
					//
			} // try
				//
			return newOdfPresentationDocument;
			//
		}

		private static void removeCustomShapeByName(final ObjectMap objectMap, final String name)
				throws XPathExpressionException {
			//
			final StringBuilder sb = new StringBuilder("./*[local-name()='custom-shape']");
			//
			if (name != null) {
				//
				append(sb, "[@*[local-name()='name' and .='");
				//
				append(sb, name);
				//
				append(sb, "']]");
				//
			} // if
				//
			final NodeList customShapes = cast(NodeList.class, evaluate(ObjectMap.getObject(objectMap, XPath.class),
					VoiceManager.toString(sb), ObjectMap.getObject(objectMap, Node.class), XPathConstants.NODESET));
			//
			Node customShape = null;
			//
			for (int i = 0; i < getLength(customShapes); i++) {
				//
				if ((customShape = item(customShapes, i)) == null) {
					//
					continue;
					//
				} // if
					//
				removeChild(getParentNode(customShape), customShape);
				//
			} // for
				//
		}

		private static OdfPresentationDocument generateOdfPresentationDocument(final String string,
				final String outputFolder, final Collection<String> voiceLKeySet,
				final boolean embedAudioInPresentation, final String folderInPresentation) throws Exception {
			//
			OdfPresentationDocument newOdfPresentationDocument = null;
			//
			if ((newOdfPresentationDocument = OdfPresentationDocument.newPresentationDocument()) != null) {
				//
				final File file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null);
				//
				newOdfPresentationDocument.save(file);
				//
				ZipUtil.replaceEntry(file, "content.xml", getBytes(string));
				//
				if (embedAudioInPresentation) {
					//
					ZipUtil.addEntries(file, map(stream(voiceLKeySet),
							x -> new FileSource(String.join("/", folderInPresentation, x), new File(outputFolder, x)))
							.toArray(ZipEntrySource[]::new));
					//
				} // if
					//
				newOdfPresentationDocument = OdfPresentationDocument.loadDocument(file);
				//
				delete(file);
				//
			} // if
				//
			return newOdfPresentationDocument;
			//
		}

		private static void replaceText(final ObjectMap objectMap, final String messageDigestAlgorithm)
				throws XPathExpressionException, NoSuchAlgorithmException {
			//
			final NodeList ps = cast(NodeList.class,
					evaluate(ObjectMap.getObject(objectMap, XPath.class),
							"./*[local-name()='frame']/*[local-name()='text-box']/*[local-name()='p']",
							ObjectMap.getObject(objectMap, Node.class), XPathConstants.NODESET));
			//
			Map<String, Object> map = null;
			//
			ObjectMap.setObjectIfAbsent(objectMap, freemarker.template.Configuration.class,
					new freemarker.template.Configuration(freemarker.template.Configuration.getVersion()));
			//
			ObjectMap.setObjectIfAbsent(objectMap, StringTemplateLoader.class, new StringTemplateLoader());
			//
			final freemarker.template.Configuration configuration = getIfNull(
					ObjectMap.getObject(objectMap, freemarker.template.Configuration.class),
					() -> new freemarker.template.Configuration(freemarker.template.Configuration.getVersion()));
			//
			final StringTemplateLoader stl = ObjectUtils
					.getIfNull(ObjectMap.getObject(objectMap, StringTemplateLoader.class), StringTemplateLoader::new);
			//
			final boolean headless = GraphicsEnvironment.isHeadless();
			//
			ObjectMap om = null;
			//
			for (int i = 0; i < getLength(ps); i++) {
				//
				if (map == null) {
					//
					try {
						//
						map = describe(ObjectMap.getObject(objectMap, Voice.class));
						//
					} catch (final Throwable e) {
						//
						errorOrAssertOrShowException(headless, e);
						//
					} // try
						//
				} // if
					//
				if (om == null) {
					//
					ObjectMap.setObject(om = Reflection.newProxy(ObjectMap.class, new IH()),
							freemarker.template.Configuration.class, configuration);
					//
					ObjectMap.setObject(om, StringTemplateLoader.class, stl);
					//
					if (!ObjectMap.containsObject(objectMap, MessageDigest.class)) {
						//
						ObjectMap.setObject(objectMap, MessageDigest.class,
								MessageDigest.getInstance(StringUtils.defaultString(messageDigestAlgorithm, SHA_512)));
						//
					} // if
						//
					ObjectMap.setObject(om, MessageDigest.class, ObjectMap.getObject(objectMap, MessageDigest.class));
					//
				} // if
					//
				ObjectMap.setObject(om, Node.class, ps.item(i));
				//
				replaceTextContent(om, map);
				//
			} // for
				//
		}

		private static void replaceTextContent(final ObjectMap objectMap, final Map<?, ?> map) {
			//
			final freemarker.template.Configuration configuration = ObjectMap.getObject(objectMap,
					freemarker.template.Configuration.class);
			//
			final StringTemplateLoader stl = ObjectMap.getObject(objectMap, StringTemplateLoader.class);
			//
			testAndAccept(x -> getTemplateLoader(configuration) == null, configuration, x -> setTemplateLoader(x, stl));
			//
			final Node node = ObjectMap.getObject(objectMap, Node.class);
			//
			final String textContent = getTextContent(node);
			//
			// template key
			//
			final String key = Collections.min(Arrays.asList(
					//
					textContent,
					testAndApply(Objects::nonNull,
							digest(ObjectMap.getObject(objectMap, MessageDigest.class), getBytes(textContent)),
							Hex::encodeHexString, null)
			//
			), (a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b)));
			//
			putTemplate(stl, key, textContent);
			//
			try (final Writer writer = new StringWriter()) {
				//
				process(getTemplate(configuration, key), map, writer);
				//
				if (node != null) {
					//
					node.setTextContent(VoiceManager.toString(writer));
					//
				} // if
					//
			} catch (final IOException | TemplateException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		}

		private static Map<String, Object> describe(final Object data) throws Throwable {
			//
			try {
				//
				return testAndApply(Objects::nonNull, data, PropertyUtils::describe, null);
				//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				throw ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
						ExceptionUtils.getRootCause(e), e);
				//
			} // try
				//
		}

		private static void putTemplate(final StringTemplateLoader instance, final String name,
				final String templateContent) {
			if (instance != null) {
				instance.putTemplate(name, templateContent);
			}
		}

		private static void setPluginHref(final ObjectMap objectMap, final String key,
				final boolean embedAudioInPresentation, final String folder) throws XPathExpressionException {
			//
			final NodeList plugins = cast(NodeList.class,
					evaluate(ObjectMap.getObject(objectMap, XPath.class),
							"./*[local-name()='frame']/*[local-name()='plugin']",
							ObjectMap.getObject(objectMap, Node.class), XPathConstants.NODESET));
			//
			Node attribute = null;
			//
			NamedNodeMap attributes = null;
			//
			StringBuilder sb = null;
			//
			final Pattern pattern = ObjectMap.getObject(objectMap, Pattern.class);
			//
			for (int i = 0; i < getLength(plugins); i++) {
				//
				if ((attributes = getAttributes(item(plugins, i))) == null) {
					//
					continue;
					//
				} // if
					//
				for (int j = 0; j < attributes.getLength(); j++) {

					if ((attribute = attributes.item(j)) == null) {
						//
						continue;
						//
					} // if
						//
					if (matches(matcher(pattern, getNodeName(attribute)))) {
						//
						clear(sb = getIfNull(sb, StringBuilder::new));
						//
						attribute
								.setNodeValue(
										VoiceManager.toString(append(
												append(append(sb,
														Boolean.logicalAnd(embedAudioInPresentation,
																StringUtils.isNotBlank(folder)) ? folder : ".."),
														'/'),
												key)));
						//
					} // if
						//
				} // for
					//
			} // for
				//
		}

		private static String getNodeName(final Node instance) {
			return instance != null ? instance.getNodeName() : null;
		}

		private static XPath newXPath(final XPathFactory instance) {
			return instance != null ? instance.newXPath() : null;
		}

		private static Node getParentNode(final Node instance) {
			return instance != null ? instance.getParentNode() : null;
		}

		private static void appendChild(final Node instance, final Node child) throws DOMException {
			if (instance != null) {
				instance.appendChild(child);
			}
		}

		private static void removeChild(final Node instance, final Node child) throws DOMException {
			if (instance != null) {
				instance.removeChild(child);
			}
		}

		private static Node cloneNode(final Node instance, final boolean deep) {
			return instance != null ? instance.cloneNode(deep) : null;
		}

		private static Transformer newTransformer(final TransformerFactory instance)
				throws TransformerConfigurationException {
			return instance != null ? instance.newTransformer() : null;
		}

		private static void transform(final Transformer instance, final Source xmlSource, final Result outputTarget)
				throws TransformerException {
			if (instance != null) {
				instance.transform(xmlSource, outputTarget);
			}
		}

		private static Object evaluate(final XPath instance, final String expression, final Object item,
				final QName returnType) throws XPathExpressionException {
			return instance != null ? instance.evaluate(expression, item, returnType) : null;
		}

	}

	private static Integer getOrdinalPosition(final Voice instance) {
		return instance != null ? instance.getOrdinalPosition() : null;
	}

	private static interface BooleanMap {

		boolean getBoolean(final String key);

		void setBoolean(final String key, final boolean value);

		static boolean getBoolean(final BooleanMap instance, final String key) {
			return instance != null && instance.getBoolean(key);
		}

		static void setBoolean(final BooleanMap instance, final String key, final boolean value) {
			if (instance != null) {
				instance.setBoolean(key, value);
			}
		}

	}

	private static void export(final List<Voice> voices, final Map<String, String> outputFolderFileNameExpressions,
			final ObjectMap objectMap) throws IOException {
		//
		EvaluationContext evaluationContext = null;
		//
		ExpressionParser expressionParser = null;
		//
		ExecutorService es = null;
		//
		ExportTask et = null;
		//
		NumberFormat percentNumberFormat = null;
		//
		final BooleanMap booleanMap = ObjectMap.getObject(objectMap, BooleanMap.class);
		//
		final VoiceManager voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
		//
		final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
		//
		try {
			//
			int size = IterableUtils.size(voices);
			//
			Integer numberOfOrdinalPositionDigit = Integer.valueOf(StringUtils.length(
					toString(orElse(max(filter(map(stream(voices), x -> getOrdinalPosition(x)), Objects::nonNull),
							ObjectUtils::compare), null))));
			//
			String ordinalPositionFileNamePrefix = null;
			//
			Table<String, String, Voice> voiceFileNames = null;
			//
			ObjectMapper objectMapper = null;
			//
			final boolean jlptAsFolder = BooleanMap.getBoolean(booleanMap, "jlptAsFolder");
			//
			final AtomicInteger denominator = new AtomicInteger(2);
			//
			testAndRun(jlptAsFolder, () -> incrementAndGet(denominator));
			//
			final Fraction pharse = Fraction.getFraction(0, intValue(denominator, 2));
			//
			for (int i = 0; i < size; i++) {
				//
				if ((es = getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
					//
					continue;
					//
				} // if
					//
				(et = new ExportTask()).voiceManager = voiceManager;
				//
				et.counter = Integer.valueOf(i + 1);
				//
				et.pharse = pharse;
				//
				et.count = size;
				//
				et.percentNumberFormat = getIfNull(percentNumberFormat, () -> new DecimalFormat("#%"));
				//
				et.evaluationContext = evaluationContext = getIfNull(evaluationContext, StandardEvaluationContext::new);
				//
				et.expressionParser = expressionParser = getIfNull(expressionParser, SpelExpressionParser::new);
				//
				et.outputFolderFileNameExpressions = outputFolderFileNameExpressions;
				//
				et.voice = get(voices, i);
				//
				et.ordinalPositionDigit = numberOfOrdinalPositionDigit;
				//
				et.ordinalPositionFileNamePrefix = ordinalPositionFileNamePrefix = getIfNull(
						ordinalPositionFileNamePrefix,
						() -> StringMap.getString(stringMap, "ordinalPositionFileNamePrefix")
				//
				);
				//
				if (booleanMap != null) {
					//
					et.overMp3Title = booleanMap.getBoolean(OVER_MP3_TITLE);
					//
					et.ordinalPositionAsFileNamePrefix = booleanMap.getBoolean(ORDINAL_POSITION_AS_FILE_NAME_PREFIX);
					//
					et.exportPresentation = booleanMap.getBoolean(EXPORT_PRESENTATION);
					//
					et.embedAudioInPresentation = booleanMap.getBoolean(EMBED_AUDIO_IN_PRESENTATION);
					//
					et.hideAudioImageInPresentation = booleanMap.getBoolean(HIDE_AUDIO_IMAGE_IN_PRESENTATION);
					//
				} // if
					//
				et.voiceFileNames = voiceFileNames = getIfNull(voiceFileNames, HashBasedTable::create);
				//
				ObjectMap.setObjectIfAbsent(objectMap, ObjectMapper.class, objectMapper = getIfNull(objectMapper,
						() -> new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY)));
				//
				et.objectMapper = objectMapper;
				//
				et.exportPresentationTemplate = StringMap.getString(stringMap, "exportPresentationTemplate");
				//
				et.folderInPresentation = StringMap.getString(stringMap, FOLDER_IN_PRESENTATION);
				//
				et.password = StringMap.getString(stringMap, "exportPassword");
				//
				et.messageDigestAlgorithm = StringMap.getString(stringMap, MESSAGE_DIGEST_ALGORITHM);
				//
				es.submit(et);
				//
			} // for
				//
			final Multimap<String, Voice> multimap = createMultimapByListNames(voices);
			//
			Collection<Entry<String, Voice>> entries = MultimapUtil.entries(multimap);
			//
			if (iterator(entries) != null) {
				//
				int coutner = 0;
				//
				size = MultimapUtil.size(multimap);
				//
				numberOfOrdinalPositionDigit = Integer.valueOf(StringUtils.length(toString(
						orElse(max(filter(map(stream(multimap.values()), x -> getOrdinalPosition(x)), Objects::nonNull),
								ObjectUtils::compare), null))));
				//
				final AtomicInteger numerator = new AtomicInteger(1);
				//
				testAndRun(jlptAsFolder, () -> incrementAndGet(numerator));
				//
				ObjectMap.setObjectIfAbsent(objectMap, Fraction.class, pharse);
				//
				for (final Entry<String, Voice> en : entries) {
					//
					if (en == null || (es = getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
						//
						continue;
						//
					} // if
						//
					ObjectMap.setObjectIfAbsent(objectMap, NumberFormat.class, percentNumberFormat = ObjectUtils
							.getIfNull(percentNumberFormat, () -> new DecimalFormat("#%")));
					//
					ObjectMap.setObjectIfAbsent(objectMap, EvaluationContext.class, evaluationContext = ObjectUtils
							.getIfNull(evaluationContext, StandardEvaluationContext::new));
					//
					ObjectMap.setObjectIfAbsent(objectMap, ExpressionParser.class,
							expressionParser = getIfNull(expressionParser, SpelExpressionParser::new));
					//
					ObjectMap.setObject(objectMap, Voice.class, getValue(en));
					//
					es.submit(
							createExportTask(objectMap, size, Integer.valueOf(++coutner), numberOfOrdinalPositionDigit,
									Collections.singletonMap(getKey(en),
											"(#voice.text+'('+#voice.romaji+').'+#voice.fileExtension)"),
									voiceFileNames = getIfNull(voiceFileNames, HashBasedTable::create)));
					//
				} // for
					//
			} // if
				//
				// JLPT
				//
			testAndRun(jlptAsFolder, () -> exportJlpt(objectMap, voices));
			//
		} finally {
			//
			shutdown(es);
			//
		} // try
			//
	}

	private static Multimap<String, Voice> createMultimapByListNames(final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (iterator(voices) != null) {
			//
			Iterable<String> listNames = null;
			//
			for (final Voice voice : voices) {
				//
				if (iterator(listNames = getListNames(voice)) == null) {
					//
					continue;
					//
				} // if
					//
				for (final String listName : listNames) {
					//
					if (StringUtils.isEmpty(listName)) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(multimap = getIfNull(multimap, LinkedListMultimap::create), listName, voice);
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

	private static void exportJlpt(final ObjectMap objectMap, final List<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		Voice v = null;
		//
		String jlptLevel = null;
		//
		for (int i = 0; i < IterableUtils.size(voices); i++) {
			//
			if ((jlptLevel = getJlptLevel(v = get(voices, i))) == null) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = getIfNull(multimap, LinkedListMultimap::create), jlptLevel, v);
			//
		} // for
			//
		String jlptFolderNamePrefix = null;
		//
		StringBuilder folder = null;
		//
		final Collection<Entry<String, Voice>> entries = MultimapUtil.entries(multimap);
		//
		if (entries == null) {
			//
			return;
			//
		} // if
			//
		int coutner = 0;
		//
		final int size = MultimapUtil.size(multimap);
		//
		final int numberOfOrdinalPositionDigit = Integer.valueOf(StringUtils.length(toString(
				orElse(max(filter(map(stream(multimap.values()), x -> getOrdinalPosition(x)), Objects::nonNull),
						ObjectUtils::compare), null))));
		//
		EvaluationContext evaluationContext = testAndApply(c -> ObjectMap.containsObject(objectMap, c),
				EvaluationContext.class, c -> ObjectMap.getObject(objectMap, c), null);
		//
		ExpressionParser expressionParser = testAndApply(c -> ObjectMap.containsObject(objectMap, c),
				ExpressionParser.class, c -> ObjectMap.getObject(objectMap, c), null);
		//
		ExecutorService es = testAndApply(c -> ObjectMap.containsObject(objectMap, c), ExecutorService.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		final StringMap stringMap = testAndApply(c -> ObjectMap.containsObject(objectMap, c), StringMap.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		Table<String, String, Voice> voiceFileNames = null;
		//
		for (final Entry<String, Voice> en : entries) {
			//
			if (en == null || (es = getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
				//
				continue;
				//
			} // if
				//
			if (!ObjectMap.containsObject(objectMap, EvaluationContext.class)) {
				//
				ObjectMap.setObject(objectMap, EvaluationContext.class,
						evaluationContext = getIfNull(evaluationContext, StandardEvaluationContext::new));
				//
			} // if
				//
			if (!ObjectMap.containsObject(objectMap, ExpressionParser.class)) {
				//
				ObjectMap.setObject(objectMap, ExpressionParser.class,
						expressionParser = getIfNull(expressionParser, SpelExpressionParser::new));
				//
			} // if
				//
			ObjectMap.setObject(objectMap, Voice.class, getValue(en));
			//
			clear(folder = getIfNull(folder, StringBuilder::new));
			//
			if (folder != null) {
				//
				append(append(folder, StringUtils.defaultIfBlank(jlptFolderNamePrefix = getIfNull(jlptFolderNamePrefix,
						() -> StringMap.getString(stringMap, "jlptFolderNamePrefix")), "")), getKey(en));
				//
			} // if
				//
			es.submit(createExportTask(objectMap, size, Integer.valueOf(++coutner), numberOfOrdinalPositionDigit,
					Collections.singletonMap(toString(folder),
							"(#voice.text+'('+#voice.romaji+').'+#voice.fileExtension)"),
					voiceFileNames = getIfNull(voiceFileNames, HashBasedTable::create)));
			//
		} // for
			//
	}

	private static void clear(final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
	}

	private static ExportTask createExportTask(final ObjectMap objectMap, final Integer size, final Integer counter,
			final Integer numberOfOrdinalPositionDigit, final Map<String, String> outputFolderFileNameExpressions,
			final Table<String, String, Voice> voiceFileNames) {
		//
		final ExportTask et = new ExportTask();
		//
		et.pharse = testAndApply(c -> ObjectMap.containsObject(objectMap, c), Fraction.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		et.counter = counter;
		//
		et.count = size;
		//
		et.percentNumberFormat = testAndApply(c -> ObjectMap.containsObject(objectMap, c), NumberFormat.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		et.evaluationContext = ObjectMap.getObject(objectMap, EvaluationContext.class);
		//
		et.expressionParser = ObjectMap.getObject(objectMap, ExpressionParser.class);
		//
		et.objectMapper = testAndApply(c -> ObjectMap.containsObject(objectMap, c), ObjectMapper.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		et.outputFolderFileNameExpressions = outputFolderFileNameExpressions;
		//
		et.voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		et.ordinalPositionDigit = numberOfOrdinalPositionDigit;
		//
		final VoiceManager voiceManager = testAndApply(c -> ObjectMap.containsObject(objectMap, c), VoiceManager.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		et.ordinalPositionFileNamePrefix = getText(
				(et.voiceManager = voiceManager) != null ? voiceManager.tfOrdinalPositionFileNamePrefix : null);
		//
		et.exportPresentationTemplate = voiceManager != null ? voiceManager.exportPresentationTemplate : null;
		//
		et.folderInPresentation = voiceManager != null ? voiceManager.folderInPresentation : null;
		//
		et.password = getText(voiceManager != null ? voiceManager.tfExportPassword : null);
		//
		final BooleanMap booleanMap = testAndApply(c -> ObjectMap.containsObject(objectMap, c), BooleanMap.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		if (booleanMap != null) {
			//
			et.overMp3Title = booleanMap.getBoolean(OVER_MP3_TITLE);
			//
			et.ordinalPositionAsFileNamePrefix = booleanMap.getBoolean(ORDINAL_POSITION_AS_FILE_NAME_PREFIX);
			//
			et.exportPresentation = booleanMap.getBoolean(EXPORT_PRESENTATION);
			//
			et.embedAudioInPresentation = booleanMap.getBoolean(EMBED_AUDIO_IN_PRESENTATION);
			//
			et.hideAudioImageInPresentation = booleanMap.getBoolean(HIDE_AUDIO_IMAGE_IN_PRESENTATION);
			//
		} // if
			//
		et.voiceFileNames = voiceFileNames;
		//
		et.messageDigestAlgorithm = voiceManager != null ? voiceManager.messageDigestAlgorithm : null;
		//
		return et;
		//
	}

	private static Workbook createMicrosoftSpeechObjectLibraryWorkbook(final SpeechApi speechApi,
			final String... attributes) throws IllegalAccessException {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		final String[] voiceIds = getVoiceIds(speechApi);
		//
		final String commonPrefix = String.join("",
				StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
		//
		String voiceId = null;
		//
		final String[] as = toArray(toList(
				filter(testAndApply(Objects::nonNull, attributes, Arrays::stream, null), StringUtils::isNotEmpty)),
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
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), commonPrefix);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)),
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
			setMicrosoftSpeechObjectLibrarySheet(objectMap, voiceId, as);
			//
		} // for
			//
		ObjectMap.setObject(objectMap, Sheet.class, WorkbookUtil.createSheet(workbook, "Locale ID"));
		//
		ObjectMap.setObject(objectMap, LocaleID[].class, LocaleID.values());
		//
		setLocaleIdSheet(objectMap);
		//
		if (sheet != null && row != null) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
		return workbook;
		//
	}

	private static void setMicrosoftSpeechObjectLibrarySheetFirstRow(final Sheet sheet, final String[] columnNames) {
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

	private static void setMicrosoftSpeechObjectLibrarySheet(final ObjectMap objectMap, final String voiceId,
			final String[] attributes) {
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
		String attribute, value = null;
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
				creationHelper = getCreationHelper(workbook);
				//
			} // if
				//
			try {
				//
				CellUtil.setCellValue(
						cell = RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : 0, 0)),
						value = getVoiceAttribute(ObjectMap.getObject(objectMap, SpeechApi.class), voiceId,
								attribute = attributes[j]));
				//
				if (Objects.equals(LANGUAGE, attribute)) {
					//
					setString(comment = createCellComment(drawing, createClientAnchor(creationHelper)),
							createRichTextString(creationHelper, convertLanguageCodeToText(value, 16)));
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
				setAuthor(comment, getName(getClass(e)));
				//
				setCellComment(cell, comment);
				//
			} // try
				//
		} // for
			//
	}

	private static void setLocaleIdSheet(final ObjectMap objectMap) throws IllegalAccessException {
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
				fs = toList(filter(
						testAndApply(Objects::nonNull, FieldUtils.getAllFields(LocaleID.class), Arrays::stream, null),
						x -> x != null && !Objects.equals(getType(x), getDeclaringClass(x)) && !x.isSynthetic()
								&& !isStatic(x))
						.sorted((a, b) -> StringUtils.compare(getName(getPackage(getDeclaringClass(a))),
								getName(getPackage(getDeclaringClass(b))))));
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

	private static Row addLocaleIdRow(final ObjectMap objectMap, final List<Field> fs, final Object instance)
			throws IllegalAccessException {
		//
		final Method methodIsAccessible = ObjectMap.getObject(objectMap, Method.class);
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		Field f = null;
		//
		boolean javaLangPacakge = false;
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
			if (Boolean.logicalAnd(!Narcissus.invokeBooleanMethod(f, methodIsAccessible), !(javaLangPacakge = ArrayUtils
					.contains(new String[] { "java.lang" }, getName(getPackage(getDeclaringClass(f))))))) {
				//
				f.setAccessible(true);
				//
			} // if
				//
			if (javaLangPacakge) {
				//
				if (Objects.equals(getType(f), Integer.TYPE)) {
					//
					value = Integer.valueOf(Narcissus.getIntField(instance, f));
					//
				} else {
					//
					value = Narcissus.getObjectField(instance, f);
					//
				} // if
					//
			} else {
				//
				value = f.get(instance);
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(
					row = getIfNull(row, () -> SheetUtil.createRow(sheet, intValue(getPhysicalNumberOfRows(sheet), 0))),
					intValue(getPhysicalNumberOfCells(row), 0)), toString(value));
			//
		} // for
			//
		return row;
		//
	}

	private static void addLocaleIdSheetHeaderRow(final Sheet sheet, final List<Field> fs) {
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
						intValue(getPhysicalNumberOfCells(row), 0)), getName(fs.get(j)));
				//
			} // for
				//
		} // if
			//
	}

	private static Integer getPhysicalNumberOfCells(final Row instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfCells()) : null;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Drawing<?> createDrawingPatriarch(final Sheet instance) {
		return instance != null ? instance.createDrawingPatriarch() : null;
	}

	private static CreationHelper getCreationHelper(final Workbook instance) {
		return instance != null ? instance.getCreationHelper() : null;
	}

	private static Comment createCellComment(final Drawing<?> instance, final ClientAnchor anchor) {
		return instance != null ? instance.createCellComment(anchor) : null;
	}

	private static ClientAnchor createClientAnchor(final CreationHelper instance) {
		return instance != null ? instance.createClientAnchor() : null;
	}

	private static RichTextString createRichTextString(final CreationHelper instance, final String text) {
		return instance != null ? instance.createRichTextString(text) : null;
	}

	private static void setString(final Comment instance, final RichTextString string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

	private static void setAuthor(final Comment instance, final String string) {
		if (instance != null) {
			instance.setAuthor(string);
		}
	}

	private static void setCellComment(final Cell instance, final Comment comment) {
		if (instance != null) {
			instance.setCellComment(comment);
		}
	}

	private static Workbook createWorkbook(final List<Voice> voices, final BooleanMap booleanMap,
			final FailableSupplier<Workbook, RuntimeException> supplier)
			throws IllegalAccessException, InvocationTargetException {
		//
		Workbook workbook = null;
		//
		setSheet(workbook = getIfNull(workbook, supplier), WorkbookUtil.createSheet(workbook), voices);
		//
		if (BooleanMap.getBoolean(booleanMap, "exportListSheet")) {
			//
			final Multimap<String, Voice> multimap = getVoiceMultimapByListName(voices);
			//
			final Set<String> keySet = MultimapUtil.keySet(multimap);
			//
			if (iterator(keySet) != null) {
				//
				for (final String key : keySet) {
					//
					setSheet(workbook, WorkbookUtil.createSheet(workbook, key), MultimapUtil.get(multimap, key));
					//
				} // for
					//
			} // if
				//
		} // if
			//
		if (BooleanMap.getBoolean(booleanMap, "exportJlptSheet")) {
			//
			createJlptSheet(workbook, voices);
			//
		} // if
			//
		return workbook;
		//
	}

	private static void createJlptSheet(final Workbook workbook, final Iterable<Voice> voices)
			throws IllegalAccessException, InvocationTargetException {
		//
		final Multimap<String, Voice> multimap = getVoiceMultimapByJlpt(voices);
		//
		final Set<String> keySet = MultimapUtil.keySet(multimap);
		//
		if (iterator(keySet) != null) {
			//
			for (final String key : keySet) {
				//
				if (key == null) {
					//
					continue;
					//
				} // if
					//
				setSheet(workbook, StringUtils.length(key) > 0 ? WorkbookUtil.createSheet(workbook, key)
						: WorkbookUtil.createSheet(workbook), MultimapUtil.get(multimap, key));
				//
			} // for
				//
		} // if
			//
	}

	private static void setSheet(final Workbook workbook, final Sheet sheet, final Iterable<Voice> voices)
			throws IllegalAccessException, InvocationTargetException {
		//
		ObjectMap objectMap = null;
		//
		if (iterator(voices) != null) {
			//
			Field[] fs = null;
			//
			final Class<?> spreadsheetColumnClass = forName("domain.Voice$SpreadsheetColumn");
			//
			String[] fieldOrder = getFieldOrder();
			//
			for (final Voice voice : voices) {
				//
				if (voice == null) {
					//
					continue;
					//
				} // if
					//
				testAndAccept(Objects::nonNull, fs = getIfNull(fs, () -> FieldUtils.getAllFields(Voice.class)), a -> {
					//
					Arrays.sort(a, (x, y) -> {
						//
						return Integer.compare(ArrayUtils.indexOf(fieldOrder, getName(x)),
								ArrayUtils.indexOf(fieldOrder, getName(y)));
						//
					});
					//
				});
				//
				// header
				//
				setSheetHeaderRow(sheet, fs, spreadsheetColumnClass);
				//
				// content
				//
				if (objectMap == null) {
					//
					ObjectMap.setObject(
							objectMap = getIfNull(objectMap, () -> Reflection.newProxy(ObjectMap.class, new IH())),
							Sheet.class, sheet);
					//
					ObjectMap.setObject(objectMap, Field[].class, fs);
					//
					ObjectMap.setObject(objectMap, Workbook.class, workbook);
					//
				} // if
					//
				ObjectMap.setObject(objectMap, Voice.class, voice);
				//
				setContent(objectMap);
				//
			} // for
				//
		} // if
			//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if (sheet != null && row != null) {
			//
			final IValue0<?> writer = getWriter(sheet);
			//
			if (writer != null && writer.getValue0() == null) {
				//
				return;
				//
			} // if
				//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
	}

	private static IValue0<Object> getWriter(final Object instance) throws IllegalAccessException {
		//
		final List<Field> fs = toList(filter(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, getClass(instance), FieldUtils::getAllFields, null), Arrays::stream,
				null), f -> Objects.equals(getName(f), "_writer")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		if (f != null) {
			//
			f.setAccessible(true);
			//
			return Unit.with(f.get(instance));
			//
		} // if
			//
		return null;
		//
	}

	private static void setContent(final ObjectMap objectMap) throws IllegalAccessException, InvocationTargetException {
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		final Row row = sheet != null ? SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1) : null;
		//
		ObjectMap.setObject(objectMap, Row.class, row);
		//
		if (row == null) {
			//
			return;
			//
		} // if
			//
		final Field[] fs = ObjectMap.getObject(objectMap, Field[].class);
		//
		Field f = null;
		//
		final Class<?> dateFormatClass = forName("domain.Voice$DateFormat");
		//
		final Class<?> dataFormatClass = forName("domain.Voice$DataFormat");
		//
		for (int j = 0; fs != null && j < fs.length; j++) {
			//
			if ((f = fs[j]) == null) {
				//
				continue;
			} // if
				//
			f.setAccessible(true);
			//
			ObjectMap.setObject(objectMap, Field.class, f);
			//
			ObjectMap.setObject(objectMap, Cell.class, RowUtil.createCell(row, j));
			//
			setSheetCellValue(objectMap, f.get(ObjectMap.getObject(objectMap, Voice.class)), dataFormatClass,
					dateFormatClass);
			//
		} // for
			//
	}

	private static void setSheetHeaderRow(final Sheet sheet, final Field[] fs, final Class<?> spreadsheetColumnClass)
			throws IllegalAccessException, InvocationTargetException {
		//
		if (sheet != null && sheet.getLastRowNum() < 0) {
			//
			final Row row = SheetUtil.createRow(sheet, 0);
			//
			for (int j = 0; fs != null && j < fs.length; j++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(row, j), getColumnName(spreadsheetColumnClass, fs[j]));
				//
			} // for
				//
		} // if
			//
	}

	private static void setSheetCellValue(final ObjectMap objectMap, final Object value, final Class<?> dataFormatClass,
			final Class<?> dateFormatClass) throws IllegalAccessException, InvocationTargetException {
		//
		final Field field = ObjectMap.getObject(objectMap, Field.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		Method m = null;
		//
		Annotation a = null;
		//
		if (value instanceof Number) {
			//
			CellStyle cellStyle = null;
			//
			if ((m = orElse(
					findFirst(filter(
							testAndApply(Objects::nonNull,
									getDeclaredMethods(
											annotationType(a = orElse(
													findFirst(filter(testAndApply(Objects::nonNull,
															getDeclaredAnnotations(field), Arrays::stream, null),
															x -> Objects.equals(annotationType(x), dataFormatClass))),
													null))),
									Arrays::stream, null),
							x -> Objects.equals(getName(x), VALUE))),
					null)) != null
					&& (cellStyle = WorkbookUtil
							.createCellStyle(ObjectMap.getObject(objectMap, Workbook.class))) != null) {
				//
				m.setAccessible(true);
				//
				final short dataFormatIndex = HSSFDataFormat.getBuiltinFormat(toString(invoke(m, a)));
				//
				if (dataFormatIndex >= 0) {
					//
					cellStyle.setDataFormat(dataFormatIndex);
					//
					CellUtil.setCellStyle(cell, cellStyle);
					//
				} // if
					//
			} // if
				//
			cell.setCellValue(((Number) value).doubleValue());
			//
		} else if (value instanceof Date) {
			//
			if ((m = orElse(
					findFirst(filter(
							testAndApply(Objects::nonNull,
									getDeclaredMethods(
											annotationType(a = orElse(
													findFirst(filter(testAndApply(Objects::nonNull,
															getDeclaredAnnotations(field), Arrays::stream, null),
															x -> Objects.equals(annotationType(x), dateFormatClass))),
													null))),
									Arrays::stream, null),
							x -> Objects.equals(getName(x), VALUE))),
					null)) != null) {
				//
				m.setAccessible(true);
				//
				CellUtil.setCellValue(cell, new SimpleDateFormat(toString(invoke(m, a))).format(value));
				//
			} else {
				//
				CellUtil.setCellValue(cell, toString(value));
				//
			} // if
				//
		} else {
			//
			CellUtil.setCellValue(cell, toString(value));
			//
		} // if
			//
	}

	private static String getColumnName(final Class<?> spreadsheetColumnClass, final Field f)
			throws IllegalAccessException, InvocationTargetException {
		//
		final String name = getName(f);
		//
		final List<Annotation> annotations = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
						a -> Objects.equals(annotationType(a), spreadsheetColumnClass)));
		//
		if (annotations != null) {
			//
			int size = CollectionUtils.size(annotations);
			//
			final Annotation annotation = size == 1 ? get(annotations, 0) : null;
			//
			if (size == 1) {
				//
				final List<Method> ms = toList(filter(
						testAndApply(Objects::nonNull, getDeclaredMethods(getClass(annotation)), Arrays::stream, null),
						m -> Objects.equals(getName(m), VALUE)));
				//
				final Method m = (size = CollectionUtils.size(ms)) == 1 ? get(ms, 0) : null;
				//
				if (m != null) {
					//
					m.setAccessible(true);
					//
					return StringUtils.defaultIfBlank(toString(invoke(m, annotation)), name);
					//
				} else if (size > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return name;
		//
	}

	private static String[] getFieldOrder() {
		//
		final Annotation a = orElse(findFirst(
				filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(Voice.class), Arrays::stream, null),
						z -> Objects.equals(annotationType(z), forName("domain.FieldOrder")))),
				null);
		//
		final Method method = orElse(findFirst(
				filter(Arrays.stream(getDeclaredMethods(annotationType(a))), z -> Objects.equals(getName(z), VALUE))),
				null);
		//
		if (method != null) {
			method.setAccessible(true);
		} // if
			//
		String[] orders = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try {
			//
			orders = cast(String[].class, invoke(method, a));
			//
		} catch (final IllegalAccessException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		final List<String> fieldNames = toList(
				map(Arrays.stream(FieldUtils.getAllFields(Voice.class)), VoiceManager::getName));
		//
		String fieldName = null;
		//
		for (int i = 0; i < IterableUtils.size(fieldNames); i++) {
			//
			if (!ArrayUtils.contains(orders, fieldName = get(fieldNames, i))) {
				//
				orders = ArrayUtils.add(orders, fieldName);
				//
			} // if
				//
		} // for
			//
		return orders;
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static Class<? extends Annotation> annotationType(final Annotation instance) {
		return instance != null ? instance.annotationType() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Package instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	private static Method[] getMethods(final Class<?> instance) {
		return instance != null ? instance.getMethods() : null;
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Object getValue(final ExpressionParser spelExpressionParser,
			final EvaluationContext evaluationContext, final String expression) {
		//
		return getValue(parseExpression(spelExpressionParser, expression), evaluationContext);
		//
	}

	private static Configuration getConfiguration(final SqlSessionFactory instance) {
		return instance != null ? instance.getConfiguration() : null;
	}

	private static SqlSession openSession(final SqlSessionFactory instance) {
		return instance != null ? instance.openSession() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Expression parseExpression(final ExpressionParser instance, final String expressionString) {
		return instance != null ? instance.parseExpression(expressionString) : null;
	}

	private static Object getValue(final Expression instance, final EvaluationContext evaluationContext) {
		return instance != null ? instance.getValue(evaluationContext) : null;
	}

	private static void insertOrUpdate(final VoiceMapper instance, final Voice voice) {
		//
		if (instance != null) {
			//
			instance.deleteUnusedVoiceList();
			//
			// voice
			//
			final Voice voiceOld = instance.searchByTextAndRomaji(getText(voice), getRomaji(voice));
			//
			if (voiceOld != null) {
				//
				setId(voice, getId(voiceOld));
				//
				setUpdateTs(voice, new Date());
				//
				instance.updateVoice(voice);
				//
			} else {
				//
				setCreateTs(voice, new Date());
				//
				instance.insertVoice(voice);
				//
			} // if
				//
				// voice_list
				//
			final Integer voiceId = getId(voice);
			//
			instance.deleteVoiceListByVoiceId(voiceId);
			//
			final Iterable<String> listNames = getListNames(voice);
			//
			if (iterator(listNames) != null) {
				//
				VoiceList voiceListOld = null;
				//
				VoiceList voiceList = null;
				//
				for (final String listName : listNames) {
					//
					if ((voiceListOld = instance.searchVoiceListByName(listName)) == null) {
						//
						(voiceList = new VoiceList()).setName(listName);
						//
						instance.insertVoiceList(voiceList);
						//
					} // if
						//
					instance.insertVoiceListId(getId(ObjectUtils.defaultIfNull(voiceListOld, voiceList)), voiceId);
					//
				} // for
					//
			} // if
				//
		} // if
			//
	}

	private static void setId(final Voice instance, final Integer id) {
		if (instance != null) {
			instance.setId(id);
		}
	}

	private static void setCreateTs(final Voice instance, final Date createTs) {
		if (instance != null) {
			instance.setCreateTs(createTs);
		}
	}

	private static void setUpdateTs(final Voice instance, final Date updateTs) {
		if (instance != null) {
			instance.setUpdateTs(updateTs);
		}
	}

	private static Integer getId(final Voice instance) {
		return instance != null ? instance.getId() : null;
	}

	private static Iterable<String> getListNames(final Voice instance) {
		return instance != null ? instance.getListNames() : null;
	}

	private static Integer getId(final VoiceList instance) {
		return instance != null ? instance.getId() : null;
	}

	private static <T> T getMapper(final Configuration instance, final Class<T> type, final SqlSession sqlSession) {
		return instance != null ? instance.getMapper(type, sqlSession) : null;
	}

	private static byte[] digest(final MessageDigest instance, final byte[] input) {
		return instance != null && input != null ? instance.digest(input) : null;
	}

	private static String getFileExtension(final ContentInfo ci) {
		//
		final String message = getMessage(ci);
		//
		if (or(x -> matches(matcher(x, message)), PATTERN_CONTENT_INFO_MESSAGE_MP3_1,
				PATTERN_CONTENT_INFO_MESSAGE_MP3_2, PATTERN_CONTENT_INFO_MESSAGE_MP3_3)) {
			//
			return "mp3";
			//
		} // if
			//
		final String name = ci != null ? ci.getName() : null;
		//
		if (Objects.equals(name, "wav") || Objects.equals(name, "flac")) {
			//
			return name;
			//
		} else if (Objects.equals(getMimeType(ci), "audio/x-hx-aac-adts")) {
			//
			return "aac";
			//
		} // if
			//
		return null;
		//
	}

	private static <T> boolean and(final Predicate<T> predicate, final T a, final T b, final T... values) {
		//
		boolean result = test(predicate, a) && test(predicate, b);
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			result &= test(predicate, values[i]);
			//
		} // for
			//
		return result;
		//
	}

	private static <T> boolean or(final Predicate<T> predicate, final T a, final T b, final T... values) {
		//
		boolean result = test(predicate, a) || test(predicate, b);
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			result |= test(predicate, values[i]);
			//
		} // for
			//
		return result;
		//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		return pattern != null && input != null ? pattern.matcher(input) : null;
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void setPreferredWidth(final int width, final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = getPreferredSize(c)) == null) {
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

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) {
		//
		if (iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = getPreferredSize(c)) == null) {
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

	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

	private static Double getPreferredHeight(final Component c) {
		//
		final Dimension d = getPreferredSize(c);
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
//		instance.init();
		//
		instance.pack();
		//
		setVisible(instance, true);
		//
	}

}