package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
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
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryUtil;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.odftoolkit.odfdom.doc.OdfPresentationDocument;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.pkg.OdfPackageDocument;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
import com.google.common.base.StopwatchUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
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
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.Mp3FileUtil;
import com.mpatric.mp3agic.NotSupportedException;

import domain.Voice;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.StringTemplateLoaderUtil;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ConfigurationUtil;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateUtil;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import mapper.VoiceMapper;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerExportPanel extends JPanel
		implements Titled, InitializingBean, EnvironmentAware, ActionListener, BeanFactoryPostProcessor {

	private static final long serialVersionUID = -2806818680922579630L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L534">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String SPAN_ONLY_FORMAT = "span %1$s";

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String PASSWORD = "Password";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String OVER_MP3_TITLE = "overMp3Title";

	private static final String ORDINAL_POSITION_AS_FILE_NAME_PREFIX = "ordinalPositionAsFileNamePrefix";

	private static final String EMBED_AUDIO_IN_PRESENTATION = "embedAudioInPresentation";

	private static final String HIDE_AUDIO_IMAGE_IN_PRESENTATION = "hideAudioImageInPresentation";

	private static final String FOLDER_IN_PRESENTATION = "folderInPresentation";

	private static final String MESSAGE_DIGEST_ALGORITHM = "messageDigestAlgorithm";

	private static final String EXPORT_PRESENTATION = "exportPresentation";

	private static final String OLE_2_COMPOUND_DOCUMENT = "OLE 2 Compound Document";

	private static final String LANGUAGE = "Language";

	private static final String VALUE = "value";

	private static final String VOICE = "voice";

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static final String SHA_512 = "SHA-512";

	private static final FailablePredicate<File, RuntimeException> EMPTY_FILE_PREDICATE = f -> f != null && f.exists()
			&& isFile(f) && longValue(length(f), 0) == 0;

	private static final int TEMP_FILE_MINIMUM_PREFIX_LENGTH = intValue(getTempFileMinimumPrefixLength(), 3);

	private static IValue0<Method> METHOD_RANDOM_ALPHABETIC = null;

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see java.lang.Class#getResourceAsStream(java.lang.String)
	 */
	private static final String CLASS_RESOURCE_FORMAT = "/%1$s.class";

	private transient ComboBoxModel<Class> cbmWorkbookClass = null;

	private transient ComboBoxModel<EncryptionMode> cbmEncryptionMode = null;

	private transient ComboBoxModel<CompressionLevel> cbmCompressionLevel = null;

	private transient ComboBoxModel<FileFormat> cbmMicrosoftAccessFileFormat = null;

	private transient IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> workbookClassFailableSupplierMap = null;

	@Nullable
	private Class<?> workbookClass = null;

	private transient PropertyResolver propertyResolver = null;

	private Duration presentationSlideDuration = null;

	private FileFormat microsoftAccessFileFormat = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Export Password")
	private JTextComponent tfExportPassword = null;

	@Note("Ordinal Position File Name Prefix")
	private JTextComponent tfOrdinalPositionFileNamePrefix = null;

	@Note("JLPT Folder Name Prefix")
	private JTextComponent tfJlptFolderNamePrefix = null;

	@Note("Export HTML File Name")
	private JTextComponent tfExportHtmlFileName = null;

	@Note("Presentation Slide Duration")
	private JTextComponent tfPresentationSlideDuration = null;

	@Note("Phrase Counter")
	private JTextComponent tfPhraseCounter = null;

	private JTextComponent tfPhraseTotal = null;

	@Note("Over MP3 Title")
	private AbstractButton cbOverMp3Title = null;

	@Note("Ordinal Position As File Name Prefix")
	private AbstractButton cbOrdinalPositionAsFileNamePrefix = null;

	@Note("JLPT As Folder")
	private AbstractButton cbJlptAsFolder = null;

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

	private AbstractButton btnExport = null;

	private JProgressBar progressBarExport = null;

	@Nullable
	private String[] microsoftSpeechObjectLibraryAttributeNames = null;

	private transient SqlSessionFactory sqlSessionFactory = null;

	private String exportPresentationTemplate, folderInPresentation, messageDigestAlgorithm, voiceFolder,
			exportHtmlTemplateFile, exportWebSpeechSynthesisHtmlTemplateFile, outputFolder = null;

	@Nullable
	private Map<String, String> outputFolderFileNameExpressions = null;

	private transient Map<Object, Object> exportWebSpeechSynthesisHtmlTemplateProperties = null;

	private Version freeMarkerVersion = null;

	private transient freemarker.template.Configuration freeMarkerConfiguration = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private ObjectMapper objectMapper = null;

	@Override
	public String getTitle() {
		return "Export";
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setExportHtmlTemplateFile(final String exportHtmlTemplateFile) {
		this.exportHtmlTemplateFile = exportHtmlTemplateFile;
	}

	public void setExportPresentationTemplate(final String exportPresentationTemplate) {
		this.exportPresentationTemplate = exportPresentationTemplate;
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

	public void setFolderInPresentation(final String folderInPresentation) {
		this.folderInPresentation = folderInPresentation;
	}

	public void setFreeMarkerConfiguration(final freemarker.template.Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	public void setFreeMarkerVersion(final Version freeMarkerVersion) {
		this.freeMarkerVersion = freeMarkerVersion;
	}

	public void setMessageDigestAlgorithm(final String messageDigestAlgorithm) {
		this.messageDigestAlgorithm = messageDigestAlgorithm;
	}

	public void setMicrosoftAccessFileFormat(final FileFormat microsoftAccessFileFormat) {
		this.microsoftAccessFileFormat = microsoftAccessFileFormat;
	}

	public void setMicrosoftSpeechObjectLibraryAttributeNames(final Object value) {
		//
		this.microsoftSpeechObjectLibraryAttributeNames = toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setOutputFolder(final String outputFolder) {
		this.outputFolder = outputFolder;
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

	public void setPresentationSlideDuration(final Object object) {
		//
		final IValue0<Duration> value = toDurationIvalue0(object);
		//
		if (value != null) {
			//
			this.presentationSlideDuration = IValue0Util.getValue0(value);
			//
		} else {
			//
			throw new IllegalArgumentException(Util.toString(Util.getClass(object)));
			//
		} // if
			//
	}

	public void setSqlSessionFactory(final SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
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

	@Nullable
	private static IValue0<Class<? extends Workbook>> getWorkbookClass(
			final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map,
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

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
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
	private static Object[] toArray(@Nullable final Stream<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static InputStream getResourceAsStream(@Nullable final Class<?> instance, @Nullable final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static boolean isFile(@Nullable final File instance) {
		return instance != null && instance.isFile();
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
	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private static class MicrosoftAccessFileFormatListCellRenderer implements ListCellRenderer<Object> {

		private String commonPrefix = null;

		private ListCellRenderer<Object> listCellRenderer = null;

		@Override
		@Nullable
		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			//
			final FileFormat fileFormat = Util.cast(FileFormat.class, value);
			//
			if (fileFormat != null) {
				//
				final String toString = Util.toString(value);
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
					return VoiceManagerExportPanel.getListCellRendererComponent(this, list, sb, index, isSelected,
							cellHasFocus);
					//
				} // if
					//
			} // if
				//
			return VoiceManagerExportPanel.getListCellRendererComponent(listCellRenderer, list, value, index,
					isSelected, cellHasFocus);
			//
		}

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

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		// Microsoft Excel Format
		//
		add(new JLabel("Workbook Implementation"), String.format(SPAN_ONLY_FORMAT, 5));
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
		testAndRun(
				Util.contains(Util.keySet(IValue0Util.getValue0(getWorkbookClassFailableSupplierMap())), workbookClass),
				() -> setSelectedItem(cbmWorkbookClass, workbookClass));
		//
		final ListCellRenderer<?> lcr = getRenderer(jcbClass);
		//
		setRenderer(jcbClass, new ListCellRenderer<>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends Class> list, final Class value,
					final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return VoiceManagerExportPanel.getListCellRendererComponent((ListCellRenderer) lcr, list,
						Util.getName(value), index, isSelected, cellHasFocus);
				//
			}

		});
		//
		add(jcbClass, String.format("%1$s,span %2$s", WRAP, 7));
		//
		// Encryption Mode
		//
		add(new JLabel("Encryption Mode"), String.format(SPAN_ONLY_FORMAT, 5));
		//
		final EncryptionMode[] encryptionModes = EncryptionMode.values();
		//
		add(new JComboBox<>(cbmEncryptionMode = new DefaultComboBoxModel<>(
				ArrayUtils.insert(0, encryptionModes, (EncryptionMode) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(cbmEncryptionMode,
				Util.orElse(
						findFirst(Util.filter(Arrays.stream(encryptionModes),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.encryptionMode")))),
						null));
		//
		// ZIP Compression Level
		//
		add(new JLabel("ZIP Compression Level"), String.format(SPAN_ONLY_FORMAT, 5));
		//
		final CompressionLevel[] compressionLevels = CompressionLevel.values();
		//
		add(new JComboBox<>(cbmCompressionLevel = new DefaultComboBoxModel<>(
				ArrayUtils.insert(0, compressionLevels, (CompressionLevel) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(cbmCompressionLevel,
				Util.orElse(
						findFirst(Util.filter(Arrays.stream(compressionLevels),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.compressionLevel")))),
						null));
		//
		// Password
		//
		add(new JLabel(PASSWORD), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(tfExportPassword = new JPasswordField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportPassword")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		add(new JLabel("Option(s)"), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbOverMp3Title = new JCheckBox("Over Mp3 Title"), String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbOverMp3Title.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.overMp3Title")));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbOrdinalPositionAsFileNamePrefix = new JCheckBox("Ordinal Position As File Name Prefix"),
				String.format(SPAN_ONLY_FORMAT, 4));
		//
		cbOrdinalPositionAsFileNamePrefix
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ordinalPositionAsFileNamePrefix")));
		//
		add(new JLabel("Prefix"));
		//
		add(tfOrdinalPositionFileNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.ordinalPositionFileNamePrefix")),
				String.format("%1$s,%2$s", GROWX, WRAP));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbJlptAsFolder = new JCheckBox("JLPT As Folder"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbJlptAsFolder.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptAsFolder")));
		//
		add(new JLabel("Folder Name Prefix"));
		//
		add(tfJlptFolderNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptFolderNamePrefix")),
				String.format("%1$s,wmin %2$s,span %3$s", WRAP, 100, 2));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportHtml = new JCheckBox("Export HTML"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbExportHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtml")));
		//
		// File Name
		//
		add(new JLabel("File Name"));
		//
		add(tfExportHtmlFileName = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtmlFileName")),
				String.format("wmin %1$s,span %2$s", 100, 2));
		//
		final String[] fileExtensions = getFileExtensions(ContentType.HTML);
		//
		setToolTipText(
				tfExportHtmlFileName, String
						.format("If the File Name does not ends with %1$s, file extension \".%2$s\" will be appended.",
								Util.collect(
										Util.map(
												testAndApply(Objects::nonNull, fileExtensions, Arrays::stream, null)
														.sorted(),
												x -> StringUtils.wrap(StringUtils.join('.', x), '"')),
										Collectors.joining(" or ")),
								StringUtils.defaultIfBlank(Util.orElse(max(
										fileExtensions != null ? Arrays.stream(fileExtensions) : null,
										(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null),
										"")));
		//
		add(cbExportListHtml = new JCheckBox("Export List"));
		//
		cbExportListHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtml")));
		//
		// cbExportWebSpeechSynthesisHtml
		//
		add(cbExportWebSpeechSynthesisHtml = new JCheckBox("Export Web Speech Synthesis HTML"));
		//
		cbExportWebSpeechSynthesisHtml
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.exportWebSpeechSynthesisHtml")));
		//
		// ZIP
		//
		add(cbExportHtmlAsZip = new JCheckBox("Zip"));
		//
		cbExportHtmlAsZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtmlAsZip")));
		//
		add(cbExportHtmlRemoveAfterZip = new JCheckBox("Remove Html After Zip"),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbExportHtmlRemoveAfterZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtmlRemoveAfterZip")));
		//
		// Export List Sheet
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportListSheet = new JCheckBox("Export List Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportListSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListSheet")));
		//
		// Export JLPT Sheet
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportJlptSheet = new JCheckBox("Export JLPT Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportJlptSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportJlptSheet")));
		//
		// Export Presentation
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportPresentation = new JCheckBox("Export Presentation"), String.format(",span %1$s", 3));
		//
		setToolTipText(cbExportPresentation, "Open Document Format (odp) format, Libre Office is recommended");
		//
		cbExportPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportPresentation")));
		//
		add(cbEmbedAudioInPresentation = new JCheckBox("Emded Audio In Presentation"),
				String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbEmbedAudioInPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.embedAudioInPresentation")));
		//
		add(cbHideAudioImageInPresentation = new JCheckBox("Hide Audio Image In Presentation"),
				String.format(SPAN_ONLY_FORMAT, 2));
		//
		cbHideAudioImageInPresentation
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.hideAudioImageInPresentation")));
		//
		add(new JLabel("Presentation Slide Duration"), String.format(SPAN_ONLY_FORMAT, 2));
		//
		add(tfPresentationSlideDuration = new JTextField(
				StringUtils.defaultString(Util.toString(presentationSlideDuration))),
				String.format("%1$s,wmin %2$spx", WRAP, 100));
		//
		// Export Microsoft Access
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportMicrosoftAccess = new JCheckBox("Export Microsoft Access"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbExportMicrosoftAccess.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportMicrosoftAccess")));
		//
		final Map<?, ?> fileFormatDetails = new LinkedHashMap<>();
		//
		try {
			//
			final FailableConsumer<Map<?, ?>, IllegalAccessException> consumer = x -> putAll((Map) fileFormatDetails,
					x);
			//
			testAndAccept(Objects::nonNull,
					Util.cast(Map.class,
							FieldUtils.readDeclaredStaticField(DatabaseImpl.class, "FILE_FORMAT_DETAILS", true)),
					consumer);
			//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final FileFormat[] fileFormats = testAndApply(Objects::nonNull, toArray(Util
				.toList(Util.filter(testAndApply(Objects::nonNull, FileFormat.values(), Arrays::stream, null), x -> {
					//
					final FileFormatDetails ffds = Util.cast(FileFormatDetails.class, Util.get(fileFormatDetails, x));
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
		mafflcr.listCellRenderer = Util.cast(ListCellRenderer.class, getRenderer(jcbFileFormat));
		//
		final Stream<FileFormat> ffs = testAndApply(Objects::nonNull, fileFormats, Arrays::stream, null);
		//
		mafflcr.commonPrefix = Util
				.orElse(reduce(
						Util.filter(Util.map(Util.map(Util.map(ffs, DatabaseImpl::getFileFormatDetails),
								VoiceManagerExportPanel::getFormat), x -> Util.toString(x)), Objects::nonNull),
						StringUtils::getCommonPrefix), null);
		//
		setRenderer(jcbFileFormat, mafflcr);
		//
		add(jcbFileFormat, String.format("%1$s,span %2$s", WRAP, 5));
		//
		cbmMicrosoftAccessFileFormat.setSelectedItem(microsoftAccessFileFormat);
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(btnExport = new JButton("Export"), WRAP);
		//
		// Progress
		//
		add(tfPhraseCounter = new JTextField("0"));
		//
		add(new JLabel("/"), "align center");
		//
		add(tfPhraseTotal = new JTextField("0"));
		//
		add(progressBarExport = new JProgressBar(), String.format("%1$s,span %2$s", GROWX, 7));
		//
		progressBarExport.setStringPainted(true);
		//
		addActionListener(this, btnExport);
		//
		setEditable(false, tfPhraseCounter, tfPhraseTotal);
		//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final VoiceManager voiceManager = VoiceManager.INSTANCE;
		//
		if (voiceManager != null) {
			//
			try {
				//
				accept(x -> Util.setText(Util.cast(JTextComponent.class, x), null),
						FieldUtils.readDeclaredField(voiceManager, "tfCurrentProcessingSheetName", true),
						FieldUtils.readDeclaredField(voiceManager, "tfCurrentProcessingVoice", true));
				//
				clear(Util.cast(DefaultTableModel.class,
						FieldUtils.readDeclaredField(voiceManager, "tmImportResult", true)));
				//
			} catch (final IllegalAccessException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // if
			//
		if (Objects.equals(Util.getSource(evt), btnExport)) {
			//
			actionPerformedForExport(GraphicsEnvironment.isHeadless());
			//
		} // if
			//
	}

	private void actionPerformedForExport(final boolean headless) {
		//
		try {
			//
			actionPerformedForExport(Util.get(IValue0Util.getValue0(getWorkbookClassFailableSupplierMap()),
					getSelectedItem(cbmWorkbookClass)));
			//
		} catch (final IOException | IllegalAccessException | TemplateException | InvalidFormatException
				| GeneralSecurityException | SQLException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} catch (final Exception e) {
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
	}

	private static interface BooleanMap {

		boolean getBoolean(final String key);

		void setBoolean(final String key, final boolean value);

		static boolean getBoolean(@Nullable final BooleanMap instance, final String key) {
			return instance != null && instance.getBoolean(key);
		}

		static void setBoolean(@Nullable final BooleanMap instance, final String key, final boolean value) {
			if (instance != null) {
				instance.setBoolean(key, value);
			}
		}

	}

	private static interface IntMap<T> {

		T getObject(final int key);

		boolean containsKey(final int key);

		void setObject(final int key, final T value);

	}

	private static interface IntIntMap {

		int getInt(final int key);

		boolean containsKey(final int key);

		void setInt(final int key, final int value);

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, final String value);

		@Nullable
		static String getString(@Nullable final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}

		static void setString(@Nullable final StringMap instance, final String key, final String value) {
			if (instance != null) {
				instance.setString(key, value);
			}
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

		@Nullable
		private static IValue0<Object> handleRunnable(final Method method, final Runnable runnable, final Object[] args,
				final Collection<Object> throwableStackTraceHexs) throws Throwable {
			//
			try {
				//
				if (Objects.equals(Util.getName(method), "run")) {
					//
					return Unit.with(VoiceManagerExportPanel.invoke(method, runnable, args));
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
				testAndAccept(m -> throwable != null || isStatic(m), method,
						m -> VoiceManagerExportPanel.invoke(m, throwable));
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
		private static IValue0<Object> handleBooleanMap(final String methodName, final Map<Object, Object> map,
				@Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getBoolean") && args != null && args.length > 0) {
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
			} else if (Objects.equals(methodName, "setBoolean") && args != null && args.length > 1) {
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

	@Nullable
	private static byte[] getBytes(@Nullable final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private void actionPerformedForExport(final FailableSupplier<Workbook, RuntimeException> workbookSupplier)
			throws IOException, IllegalAccessException, InvalidFormatException, GeneralSecurityException,
			TemplateException, SQLException {
		//
		SqlSession sqlSession = null;
		//
		Workbook workbook = null;
		//
		File file = null;
		//
		try {
			//
			final VoiceMapper voiceMapper = org.apache.ibatis.session.ConfigurationUtil.getMapper(
					SqlSessionFactoryUtil.getConfiguration(sqlSessionFactory), VoiceMapper.class,
					sqlSession = SqlSessionFactoryUtil.openSession(sqlSessionFactory));
			//
			final List<Voice> voices = retrieveAllVoices(voiceMapper);
			//
			forEach(voices, v -> setListNames(v, searchVoiceListNamesByVoiceId(voiceMapper, getId(v))));
			//
			final IH ih = new IH();
			//
			ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
			//
			ObjectMap.setObject(objectMap, VoiceManagerExportPanel.class, this);
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
			StringMap.setString(stringMap, "ordinalPositionFileNamePrefix",
					Util.getText(tfOrdinalPositionFileNamePrefix));
			//
			StringMap.setString(stringMap, "exportPresentationTemplate", exportPresentationTemplate);
			//
			StringMap.setString(stringMap, "exportPassword", Util.getText(tfExportPassword));
			//
			StringMap.setString(stringMap, FOLDER_IN_PRESENTATION, folderInPresentation);
			//
			StringMap.setString(stringMap, "jlptFolderNamePrefix", Util.getText(tfJlptFolderNamePrefix));
			//
			StringMap.setString(stringMap, MESSAGE_DIGEST_ALGORITHM, messageDigestAlgorithm);
			//
			ObjectMap.setObject(objectMap, StringMap.class, stringMap);
			//
			// presentationSlideDuration
			//
			ObjectMap.setObject(objectMap, Duration.class,
					ObjectUtils.defaultIfNull(
							IValue0Util.getValue0(toDurationIvalue0(Util.getText(tfPresentationSlideDuration))),
							presentationSlideDuration));
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
			encrypt(file, Util.cast(EncryptionMode.class, getSelectedItem(cbmEncryptionMode)),
					Util.getText(tfExportPassword));
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
						x -> ConfigurationUtil.setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
				//
				ObjectMap.setObject(objectMap, freemarker.template.Configuration.class, configuration);
				//
				ObjectMap.setObject(objectMap, TemplateHashModel.class, new BeansWrapper(version).getStaticModels());
				//
				List<File> files = null;
				//
				final Map<Object, Object> map = new LinkedHashMap<>();
				//
				Util.put(map, "folder", voiceFolder);
				//
				Util.put(map, "voices", voices);
				//
				Util.put(map, "Base64Encoder", Base64.getEncoder());
				//
				try (final Writer writer = new StringWriter()) {
					//
					ObjectMap.setObject(objectMap, Writer.class, writer);
					//
					exportHtml(objectMap, exportHtmlTemplateFile, map);
					//
					final StringBuilder sb = new StringBuilder(
							StringUtils.defaultString(Util.getText(tfExportHtmlFileName)));
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
							file = new File(StringUtils.defaultIfBlank(Util.toString(sb), "export.html")),
							Util.toString(writer), StandardCharsets.UTF_8);
					//
					Util.add(files = ObjectUtils.getIfNull(files, ArrayList::new), file);
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
								file = new File(
										StringUtils.defaultIfBlank(Util.toString(sb), "WebSpeechSynthesis.html")),
								Util.toString(writer), StandardCharsets.UTF_8);
						//
						Util.add(files = ObjectUtils.getIfNull(files, ArrayList::new), file);
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
						&& reduce(mapToLong(Util.stream(files), f -> longValue(length(f), 0)), 0, Long::sum) > 0) {
					//
					ObjectMap.setObject(objectMap, File.class,
							file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.zip", new Date())));
					//
					ObjectMap.setObject(objectMap, EncryptionMethod.class, EncryptionMethod.ZIP_STANDARD);
					//
					ObjectMap.setObject(objectMap, CompressionLevel.class,
							Util.cast(CompressionLevel.class, getSelectedItem(cbmCompressionLevel)));
					//
					createZipFile(objectMap, Util.getText(tfExportPassword), files);
					//
					// Delete HTML File(s) is "Remove HTML After ZIP" option is checked
					//
					testAndAccept((a, b) -> a, isSelected(cbExportHtmlRemoveAfterZip), files,
							(a, b) -> forEach(b, VoiceManagerExportPanel::delete));
					//
				} // if
					//
			} // if
				//
				// Export Microsoft Access
				//
			if (isSelected(cbExportMicrosoftAccess)) {
				//
				final FileFormat fileFormat = Util.cast(FileFormat.class,
						getSelectedItem(cbmMicrosoftAccessFileFormat));
				//
				ObjectMap.setObject(objectMap, File.class,
						file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.%2$s", new Date(),
								StringUtils.substringAfter(getFileExtension(fileFormat), '.'))));
				//
				ObjectMap.setObject(objectMap, FileFormat.class, fileFormat);
				//
				exportMicrosoftAccess(objectMap, Util.values(
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

	private static void exportMicrosoftAccess(final ObjectMap objectMap, final Iterable<DataSource> dss)
			throws IOException, SQLException {
		//
		final File file = ObjectMap.getObject(objectMap, File.class);
		//
		try (final Database db = create(setFileFormat(testAndApply(Objects::nonNull, file, DatabaseBuilder::new, null),
				ObjectUtils.defaultIfNull(ObjectMap.getObject(objectMap, FileFormat.class), FileFormat.V2000)))) {
			//
			if (Util.iterator(dss) != null) {
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

	@Nullable
	private static Database create(@Nullable final DatabaseBuilder instance) throws IOException {
		return instance != null ? instance.create() : null;
	}

	@Nullable
	private static Set<String> getTableNames(@Nullable final Database instance) throws IOException {
		return instance != null ? instance.getTableNames() : null;
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
				Util.add(tableNames = getIfNull(tableNames, LinkedHashSet::new), rs.getString("table_name"));
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

	private static void importResultSet(final ObjectMap objectMap, @Nullable final Iterable<String> tableNames)
			throws IOException, SQLException {
		//
		final Database db = ObjectMap.getObject(objectMap, Database.class);
		//
		if (Util.iterator(tableNames) != null) {
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

	private static <T> boolean and(final Predicate<T> predicate, @Nullable final T a, final T b,
			@Nullable final T... values) {
		//
		boolean result = Util.test(predicate, a) && Util.test(predicate, b);
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			result &= Util.test(predicate, values[i]);
			//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Connection getConnection(@Nullable final DataSource instance) throws SQLException {
		return instance != null ? instance.getConnection() : null;
	}

	@Nullable
	private static PreparedStatement prepareStatement(@Nullable final Connection instance, final String sql)
			throws SQLException {
		return instance != null ? instance.prepareStatement(sql) : null;
	}

	@Nullable
	private static ResultSet executeQuery(@Nullable final PreparedStatement instance) throws SQLException {
		return instance != null ? instance.executeQuery() : null;
	}

	@Nullable
	private static DatabaseBuilder setFileFormat(@Nullable final DatabaseBuilder instance,
			final Database.FileFormat fileFormat) {
		return instance != null ? instance.setFileFormat(fileFormat) : instance;
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

	@Nullable
	private static char[] toCharArray(@Nullable final String instance) {
		return instance != null ? instance.toCharArray() : null;
	}

	private static long reduce(@Nullable final LongStream instance, final long identity, final LongBinaryOperator op) {
		return instance != null ? instance.reduce(identity, op) : identity;
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

	private void exportHtml(final ObjectMap objectMap, final Multimap<String, Voice> multimap,
			final Entry<?, UnaryOperator<Object>> filePair, final Map<Object, Object> parameters,
			final Collection<File> files) throws IOException, TemplateException {
		//
		final Iterable<String> keySet = MultimapUtil.keySet(multimap);
		//
		if (Util.iterator(keySet) != null) {
			//
			File file = null;
			//
			for (final String key : keySet) {
				//
				final Map<Object, Object> map = new LinkedHashMap<>();
				//
				try (final Writer writer = testAndApply(
						Objects::nonNull, file = testAndApply(Objects::nonNull,
								Util.toString(Util.apply(Util.getValue(filePair), key)), File::new, null),
						FileWriter::new, null)) {
					//
					ObjectMap.setObject(objectMap, Writer.class, writer);
					//
					Util.put(map, "folder", voiceFolder);
					//
					Util.put(map, "voices", MultimapUtil.get(multimap, key));
					//
					Util.put(map, "Base64Encoder", Base64.getEncoder());
					//
					final Collection<Entry<Object, Object>> entrySet = Util.entrySet(parameters);
					//
					if (Util.iterator(entrySet) != null) {
						//
						for (final Entry<?, ?> parameter : entrySet) {
							//
							if (parameter == null) {
								//
								continue;
								//
							} // if
								//
							Util.put(map, Util.toString(Util.getKey(parameter)), Util.getValue(parameter));
							//
						} // for
							//
					} // if
						//
					exportHtml(objectMap, Util.toString(Util.getKey(filePair)), map);
					//
					Util.add(files, file);
					//
				} finally {
					//
					testAndAccept(x -> intValue(length(x), 0) == 0, file, VoiceManagerExportPanel::delete);
					//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static String getFileExtension(@Nullable final FileFormat instance) {
		return instance != null ? instance.getFileExtension() : null;
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

	private static String getLongestString(@Nullable final String[] ss) {
		//
		return Util.orElse(max(testAndApply(Objects::nonNull, ss, Arrays::stream, null),
				(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null);
		//
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
				x -> ConfigurationUtil.setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
		//
		final Map<String, Object> map = new LinkedHashMap<>(
				Collections.singletonMap("statics", getIfNull(ObjectMap.getObject(objectMap, TemplateHashModel.class),
						() -> new BeansWrapper(version).getStaticModels())));
		//
		final Collection<Entry<Object, Object>> entrySet = Util.entrySet(parameters);
		//
		if (Util.iterator(entrySet) != null) {
			//
			for (final Entry<?, ?> parameter : entrySet) {
				//
				if (parameter == null) {
					//
					continue;
					//
				} // if
					//
				Util.put(map, Util.toString(Util.getKey(parameter)), Util.getValue(parameter));
				//
			} // for
				//
		} // if
			//
		TemplateUtil
				.process(
						testAndApply(Objects::nonNull, templateFile,
								a -> ConfigurationUtil.getTemplate(configuration, a), null),
						map, ObjectMap.getObject(objectMap, Writer.class));
		//
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final String string) {
		return instance != null ? instance.append(string) : null;
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final char c) {
		return instance != null ? instance.append(c) : null;
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U, E extends Throwable> void accept(@Nullable final FailableBiConsumer<T, U, E> instance,
			final T t, @Nullable final U u) throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static void delete(final File instance) throws IOException {
		//
		testAndAccept(Objects::nonNull, testAndApply(Objects::nonNull, toURI(instance), Path::of, null), Files::delete);
		//
	}

	@Nullable
	private static TemplateLoader getTemplateLoader(@Nullable final freemarker.template.Configuration instance) {
		return instance != null ? instance.getTemplateLoader() : null;
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

	@Nullable
	private static Integer getNumberOfSheets(@Nullable final Workbook instance) {
		return instance != null ? Integer.valueOf(instance.getNumberOfSheets()) : null;
	}

	private static Workbook createWorkbook(@Nullable final List<Voice> voices, final BooleanMap booleanMap,
			final FailableSupplier<Workbook, RuntimeException> supplier) throws IllegalAccessException {
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
			if (Util.iterator(keySet) != null) {
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

	private static void createJlptSheet(final Workbook workbook, @Nullable final Iterable<Voice> voices)
			throws IllegalAccessException {
		//
		final Multimap<String, Voice> multimap = getVoiceMultimapByJlpt(voices);
		//
		final Set<String> keySet = MultimapUtil.keySet(multimap);
		//
		if (Util.iterator(keySet) != null) {
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

	private static void setSheet(final Workbook workbook, @Nullable final Sheet sheet,
			@Nullable final Iterable<Voice> voices) throws IllegalAccessException {
		//
		ObjectMap objectMap = null;
		//
		if (Util.iterator(voices) != null) {
			//
			Field[] fs = null;
			//
			final Class<?> spreadsheetColumnClass = Util.forName("domain.Voice$SpreadsheetColumn");
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
					// Filter out the "java.lang.reflect.Field" instance(s) which is/are annotated
					// by "domain.Voice$Visibility" and its "values" method return "false"
					//
				testAndAccept(Objects::nonNull,
						fs = getIfNull(fs, () -> toArray(getVisibileVoiceFields(), new Field[] {})), a ->
						//
						Arrays.sort(a, (x, y) -> Integer.compare(ArrayUtils.indexOf(fieldOrder, Util.getName(x)),
								ArrayUtils.indexOf(fieldOrder, Util.getName(y))))
				//
				);
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
			if (writer != null && IValue0Util.getValue0(writer) == null) {
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

	private static void setSheetHeaderRow(@Nullable final Sheet sheet, @Nullable final Field[] fs,
			final Class<?> spreadsheetColumnClass) {
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

	private static String getColumnName(final Class<?> spreadsheetColumnClass, final Field f) {
		//
		final String name = Util.getName(f);
		//
		final List<Annotation> annotations = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
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
				final List<Method> ms = Util.toList(
						Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(annotation)),
								Arrays::stream, null), m -> Objects.equals(Util.getName(m), VALUE)));
				//
				final Method m = (size = CollectionUtils.size(ms)) == 1 ? get(ms, 0) : null;
				//
				if (m != null) {
					//
					return StringUtils.defaultIfBlank(Util.toString(Narcissus.invokeMethod(annotation, m)), name);
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

	private static void setContent(final ObjectMap objectMap) throws IllegalAccessException {
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
		final Class<?> dateFormatClass = Util.forName("domain.Voice$DateFormat");
		//
		final Class<?> dataFormatClass = Util.forName("domain.Voice$DataFormat");
		//
		for (int j = 0; fs != null && j < fs.length; j++) {
			//
			if ((f = fs[j]) == null) {
				//
				continue;
			} // if
				//
			setAccessible(f, true);
			//
			ObjectMap.setObject(objectMap, Field.class, f);
			//
			ObjectMap.setObject(objectMap, Cell.class, RowUtil.createCell(row, j));
			//
			setSheetCellValue(objectMap, get(f, ObjectMap.getObject(objectMap, Voice.class)), dataFormatClass,
					dateFormatClass);
			//
		} // for
			//
	}

	private static void setSheetCellValue(final ObjectMap objectMap, final Object value, final Class<?> dataFormatClass,
			final Class<?> dateFormatClass) {
		//
		final Field field = ObjectMap.getObject(objectMap, Field.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		Method m = null;
		//
		Annotation a = null;
		//
		if (value instanceof Number number) {
			//
			CellStyle cellStyle = null;
			//
			if ((m = Util
					.orElse(findFirst(
							Util.filter(
									testAndApply(Objects::nonNull,
											Util.getDeclaredMethods(annotationType(a = Util.orElse(
													findFirst(Util.filter(testAndApply(Objects::nonNull,
															getDeclaredAnnotations(field), Arrays::stream, null),
															x -> Objects.equals(annotationType(x), dataFormatClass))),
													null))),
											Arrays::stream, null),
									x -> Objects.equals(Util.getName(x), VALUE))),
							null)) != null
					&& (cellStyle = WorkbookUtil
							.createCellStyle(ObjectMap.getObject(objectMap, Workbook.class))) != null) {
				//
				final short dataFormatIndex = HSSFDataFormat
						.getBuiltinFormat(Util.toString(Narcissus.invokeMethod(a, m)));
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
			cell.setCellValue(doubleValue(number, 0));
			//
		} else if (value instanceof Date) {
			//
			if ((m = Util
					.orElse(findFirst(
							Util.filter(
									testAndApply(Objects::nonNull,
											Util.getDeclaredMethods(annotationType(a = Util.orElse(
													findFirst(Util.filter(testAndApply(Objects::nonNull,
															getDeclaredAnnotations(field), Arrays::stream, null),
															x -> Objects.equals(annotationType(x), dateFormatClass))),
													null))),
											Arrays::stream, null),
									x -> Objects.equals(Util.getName(x), VALUE))),
							null)) != null) {
				//
				CellUtil.setCellValue(cell,
						new SimpleDateFormat(Util.toString(Narcissus.invokeMethod(a, m))).format(value));
				//
			} else {
				//
				CellUtil.setCellValue(cell, Util.toString(value));
				//
			} // if
				//
		} else {
			//
			CellUtil.setCellValue(cell, Util.toString(value));
			//
		} // if
			//
	}

	private static double doubleValue(@Nullable final Number instance, final double defaultValue) {
		return instance != null ? instance.doubleValue() : defaultValue;
	}

	@Nullable
	private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	@Nullable
	private static IValue0<Object> getWriter(final Object instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFields, null), Arrays::stream,
				null), f -> Objects.equals(Util.getName(f), "_writer")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		if (f != null) {
			//
			return Unit.with(Narcissus.getField(instance, f));
			//
		} // if
			//
		return null;
		//
	}

	private static List<Field> getVisibileVoiceFields() {
		//
		return Util.toList(FailableStreamUtil.stream(new FailableStream<>(
				testAndApply(x -> x != null, FieldUtils.getAllFields(Voice.class), Arrays::stream, null)).filter(f -> {
					//
					final Annotation[] as = getDeclaredAnnotations(f);
					//
					Annotation a = null;
					//
					for (int i = 0; as != null && i < as.length; i++) {
						//
						if ((a = as[i]) == null) {
							//
							continue;
							//
						} // if
							//
						if (Objects.equals("domain.Voice$Visibility", Util.getName(annotationType(a)))) {
							//
							final Boolean visible = Util.cast(Boolean.class, MethodUtils.invokeMethod(a, VALUE));
							//
							if (visible != null) {
								//
								return visible.booleanValue();
								//
							} // if
								//
						} // if
							//
					} // for
						//
					return true;
					//
				} //
		)));
		//
	}

	@Nullable
	private static Class<? extends Annotation> annotationType(@Nullable final Annotation instance) {
		return instance != null ? instance.annotationType() : null;
	}

	@Nullable
	private static String[] getFieldOrder() {
		//
		final Annotation a = Util.orElse(findFirst(
				Util.filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(Voice.class), Arrays::stream, null),
						z -> Objects.equals(annotationType(z), Util.forName("domain.FieldOrder")))),
				null);
		//
		final Method method = Util
				.orElse(findFirst(Util.filter(Arrays.stream(Util.getDeclaredMethods(annotationType(a))),
						z -> Objects.equals(Util.getName(z), VALUE))), null);
		//
		String[] orders = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try {
			//
			orders = Util.cast(String[].class, Narcissus.invokeMethod(a, method));
			//
		} catch (final Exception e) {
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		final List<String> fieldNames = Util
				.toList(Util.map(Arrays.stream(FieldUtils.getAllFields(Voice.class)), Util::getName));
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

	@Nullable
	private static Annotation[] getDeclaredAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	private static void export(@Nullable final List<Voice> voices,
			final Map<String, String> outputFolderFileNameExpressions, final ObjectMap objectMap) {
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
		final VoiceManagerExportPanel voiceManagerExportPanel = ObjectMap.getObject(objectMap,
				VoiceManagerExportPanel.class);
		//
		final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
		//
		try {
			//
			int size = IterableUtils.size(voices);
			//
			Integer numberOfOrdinalPositionDigit = Integer.valueOf(StringUtils.length(Util.toString(Util.orElse(
					max(Util.filter(Util.map(Util.stream(voices), x -> getOrdinalPosition(x)), Objects::nonNull),
							ObjectUtils::compare),
					null))));
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
				(et = new ExportTask()).voiceManagerExportPanel = voiceManagerExportPanel;
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
				et.presentationSlideDuration = ObjectMap.getObject(objectMap, Duration.class);
				//
				submit(es = getIfNull(es, () -> Executors.newFixedThreadPool(1)), et);
				//
			} // for
				//
			final Multimap<String, Voice> multimap = createMultimapByListNames(voices);
			//
			Collection<Entry<String, Voice>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				int coutner = 0;
				//
				size = MultimapUtil.size(multimap);
				//
				numberOfOrdinalPositionDigit = Integer
						.valueOf(
								StringUtils
										.length(Util
												.toString(
														Util.orElse(
																max(Util.filter(
																		Util.map(
																				Util.stream(
																						MultimapUtil.values(multimap)),
																				x -> getOrdinalPosition(x)),
																		Objects::nonNull), ObjectUtils::compare),
																null))));
				//
				final AtomicInteger numerator = new AtomicInteger(1);
				//
				testAndRun(jlptAsFolder, () -> incrementAndGet(numerator));
				//
				ObjectMap.setObjectIfAbsent(objectMap, Fraction.class, pharse);
				//
				for (final Entry<String, Voice> en : entries) {
					//
					if (en == null) {
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
					ObjectMap.setObject(objectMap, Voice.class, Util.getValue(en));
					//
					submit(es = getIfNull(es, () -> Executors.newFixedThreadPool(1)),
							createExportTask(objectMap, size, Integer.valueOf(++coutner), numberOfOrdinalPositionDigit,
									Collections.singletonMap(Util.getKey(en),
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

	@Nullable
	private static Multimap<String, Voice> createMultimapByListNames(final Iterable<Voice> voices) {
		//
		Multimap<String, Voice> multimap = null;
		//
		if (Util.iterator(voices) != null) {
			//
			Iterable<String> listNames = null;
			//
			for (final Voice voice : voices) {
				//
				if (Util.iterator(listNames = getListNames(voice)) == null) {
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

	@Nullable
	private static Iterable<String> getListNames(@Nullable final Voice instance) {
		return instance != null ? instance.getListNames() : null;
	}

	private static void submit(@Nullable final ExecutorService instance, final Runnable task) {
		if (instance != null) {
			instance.submit(task);
		}
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
		final int numberOfOrdinalPositionDigit = StringUtils
				.length(Util
						.toString(
								Util.orElse(
										max(Util.filter(Util.map(Util.stream(MultimapUtil.values(multimap)),
												x -> getOrdinalPosition(x)), Objects::nonNull), ObjectUtils::compare),
										null)));
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
			if (en == null) {
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
				// java.time.Duration
				//
			testAndAccept((a, b) -> !ObjectMap.containsObject(a, b), objectMap, Duration.class,
					(a, b) -> ObjectMap.setObject(a, b, null));
			//
			ObjectMap.setObject(objectMap, Voice.class, Util.getValue(en));
			//
			clear(folder = getIfNull(folder, StringBuilder::new));
			//
			if (folder != null) {
				//
				append(append(folder, StringUtils.defaultIfBlank(jlptFolderNamePrefix = getIfNull(jlptFolderNamePrefix,
						() -> StringMap.getString(stringMap, "jlptFolderNamePrefix")), "")), Util.getKey(en));
				//
			} // if
				//
			submit(es = getIfNull(es, () -> Executors.newFixedThreadPool(1)),
					createExportTask(objectMap, size, Integer.valueOf(++coutner), numberOfOrdinalPositionDigit,
							Collections.singletonMap(Util.toString(folder),
									"(#voice.text+'('+#voice.romaji+').'+#voice.fileExtension)"),
							voiceFileNames = getIfNull(voiceFileNames, HashBasedTable::create)));
			//
		} // for
			//
	}

	private static class ExportTask implements Runnable {

		private static String FILE_NAME_PREFIX_PADDING = Util.orElse(
				min(Util.stream(IteratorUtils.toList(RgxGen.parse("\\d").iterateUnique())), StringUtils::compare),
				null);

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

		private VoiceManagerExportPanel voiceManagerExportPanel = null;

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

		private Duration presentationSlideDuration = null;

		@Override
		public void run() {
			//
			try {
				//
				final String filePath = getFilePath(voice);
				//
				final Set<Entry<String, String>> entrySet = Util.entrySet(outputFolderFileNameExpressions);
				//
				if (Boolean.logicalOr(filePath == null, Util.iterator(entrySet) == null)) {
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
				// key
				//
				String key = null;
				//
				// value
				//
				String value = null;
				//
				String ordinalPositionString, voiceFolder = null;
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
				final String outputFolder = getOutputFolder(voiceManagerExportPanel);
				//
				for (final Entry<String, String> folderFileNamePattern : entrySet) {
					//
					if (folderFileNamePattern == null || (key = Util.getKey(folderFileNamePattern)) == null
							|| StringUtils.isBlank(value = Util.getValue(folderFileNamePattern))
							|| !(fileSource = testAndApply(Objects::nonNull,
									voiceFolder = getIfNull(voiceFolder, () -> getVoiceFolder(voiceManagerExportPanel)),
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
							.isNotBlank(ordinalPositionString = Util.toString(getOrdinalPosition(voice)))) {
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
					append(fileName, Util.toString(getValue(expressionParser, evaluationContext, value)));
					//
					// org.apache.commons.io.FileUtils.copyFile(java.io.File,java.io.File)
					//
					final String k = key;
					//
					FileUtils.copyFile(fileSource,
							fileDestination = new File(Util.toString(testAndApply(Objects::nonNull,
									folder = getIfNull(folder,
											() -> testAndApply(Objects::nonNull, outputFolder, File::new, null)),
									x -> new File(x, k), x -> new File(k))), Util.toString(fileName)));
					//
					TableUtil.put(voiceFileNames, fileDestination.getParent(), Util.toString(fileName), voice);
					//
					// Set MP3 Title if "overMp3Title" is true
					//
					testAndAccept(x -> overMp3Title, fileDestination, ExportTask::setMp3Title);
					//
				} // for
					//
				testAndAccept((a, b) -> b != null,
						progressBar = getIfNull(progressBar, () -> getProgressBarExport(voiceManagerExportPanel)),
						counter, (a, b) -> setValue(a, intValue(b, 0)));
				//
				if (counter != null && count != null) {
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
					// increment "numerator" in "org.apache.commons.lang3.math.Fraction" class by 1
					//
					testAndAccept(x -> Boolean.logicalAnd(counter.intValue() == count.intValue(), x != null), pharse,
							x -> FieldUtils.writeDeclaredField(x, "numerator", intValue(getNumerator(x), 0) + 1, true));
					//
				} // if
					//
				showPharse(voiceManagerExportPanel, pharse);
				//
				if (and(Objects.equals(getNumerator(pharse), getDenominator(pharse)), Objects.equals(counter, count),
						exportPresentation)) {
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
						ObjectMap.setObject(objectMap, Duration.class, presentationSlideDuration);
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

		private static void showPharse(@Nullable final VoiceManagerExportPanel voiceManager, final Fraction pharse) {
			//
			if (voiceManager != null) {
				//
				Util.setText(voiceManager.tfPhraseCounter, Util.toString(getNumerator(pharse)));
				//
				Util.setText(voiceManager.tfPhraseTotal, Util.toString(getDenominator(pharse)));
				//
			} //
				//
		}

		private static void setString(@Nullable final JProgressBar instance, final String string) {
			if (instance != null) {
				instance.setString(string);
			}
		}

		@Nullable
		private static String format(@Nullable final NumberFormat instance, final double number) {
			return instance != null ? instance.format(number) : null;
		}

		private static void setMaximum(@Nullable final JProgressBar instance, final int n) {
			if (instance != null) {
				instance.setMaximum(n);
			}
		}

		private static void setValue(@Nullable final JProgressBar instance, final int n) {
			if (instance != null) {
				instance.setValue(n);
			}
		}

		@Nullable
		private static String getFilePath(@Nullable final Voice instance) {
			return instance != null ? instance.getFilePath() : null;
		}

		private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
			//
			boolean result = a && b;
			//
			if (!result) {
				//
				return false;
				//
			} // if
				//
			for (int i = 0; bs != null && i < bs.length; i++) {
				//
				if (!(result &= bs[i])) {
					//
					return false;
					//
				} // if
					//
			} // for
				//
			return result;
			//
		}

		@Nullable
		private static Integer getNumerator(@Nullable final Fraction instnace) {
			return instnace != null ? instnace.getNumerator() : null;
		}

		@Nullable
		private static Integer getDenominator(@Nullable final Fraction instnace) {
			return instnace != null ? instnace.getDenominator() : null;
		}

		@Nullable
		private static Object getValue(final ExpressionParser spelExpressionParser,
				final EvaluationContext evaluationContext, final String expression) {
			//
			return getValue(parseExpression(spelExpressionParser, expression), evaluationContext);
			//
		}

		@Nullable
		private static Object getValue(@Nullable final Expression instance, final EvaluationContext evaluationContext) {
			return instance != null ? instance.getValue(evaluationContext) : null;
		}

		@Nullable
		private static Expression parseExpression(@Nullable final ExpressionParser instance,
				final String expressionString) {
			return instance != null ? instance.parseExpression(expressionString) : null;
		}

		@Nullable
		private static String getOutputFolder(@Nullable final VoiceManagerExportPanel instance) {
			return instance != null ? instance.outputFolder : null;
		}

		@Nullable
		private static String getVoiceFolder(@Nullable final VoiceManagerExportPanel instance) {
			return instance != null ? instance.voiceFolder : null;
		}

		@Nullable
		private static JProgressBar getProgressBarExport(@Nullable final VoiceManagerExportPanel instance) {
			return instance != null ? instance.progressBarExport : null;
		}

		private static void setStringFieldDefaultValue(@Nullable final Object instance) {
			//
			final Field[] fs = Util.getDeclaredFields(Util.getClass(instance));
			//
			Field f = null;
			//
			for (int i = 0; fs != null && i < fs.length; i++) {
				//
				if ((f = fs[i]) == null || !Objects.equals(Util.getType(f), String.class)) {
					//
					continue;
					//
				} // if
					//
				Narcissus.setField(instance, f,
						StringUtils.defaultString(Util.toString(Narcissus.getField(instance, f))));
				//
			} // if
				//
		}

		@Nullable
		private static <T> T clone(@Nullable final ObjectMapper objectMapper, @Nullable final Class<T> clz,
				@Nullable final T instance) throws IOException {
			//
			return objectMapper != null && clz != null
					? objectMapper.readValue(ObjectMapperUtil.writeValueAsBytes(objectMapper, instance), clz)
					: null;
			//
		}

		private static void setVariable(@Nullable final EvaluationContext instance, final String name,
				final Object value) {
			if (instance != null) {
				instance.setVariable(name, value);
			}
		}

		@Nullable
		private static <T> Optional<T> min(@Nullable final Stream<T> instance,
				@Nullable final Comparator<? super T> comparator) {
			//
			return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || comparator != null)
					? instance.min(comparator)
					: null;
			//
		}

		private static void setMp3Title(final File file)
				throws IOException, BaseException, IllegalAccessException, InvocationTargetException {
			//
			final String fileExtension = getFileExtension(Util.cast(ContentInfo.class,
					testAndApply(VoiceManagerExportPanel::isFile, file, new ContentInfoUtil()::findMatch, null)));
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
				final ID3v1 id3v1 = ObjectUtils.defaultIfNull(Mp3FileUtil.getId3v2Tag(mp3File),
						Mp3FileUtil.getId3v1Tag(mp3File));
				//
				final String titleOld = getTitle(id3v1);
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
						save(mp3File, Util.getAbsolutePath(file));
						//
					} // if
						//
				} // if
					//
			} // if
				//
		}

		private static void deleteOnExit(@Nullable final File instance) {
			if (instance != null) {
				instance.deleteOnExit();
			}
		}

		@Nullable
		private static String getName(@Nullable final File instance) {
			return instance != null ? instance.getName() : null;
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
		private static String getFileExtension(@Nullable final ContentInfo ci) {
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
		private static String getMimeType(@Nullable final ContentInfo instance) {
			return instance != null ? instance.getMimeType() : null;
		}

		@Nullable
		private static String getTitle(@Nullable final ID3v1 instance) {
			return instance != null ? instance.getTitle() : null;
		}

		private static void save(@Nullable final Mp3File instance, final String newFilename)
				throws IOException, NotSupportedException {
			//
			if (instance == null) {
				//
				return;
				//
			} // if
				//
			try {
				//
				if (FieldUtils.readField(instance, "path", true) == null) {
					//
					return;
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			instance.save(newFilename);
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
					ObjectMap.setObject(objectMap, Duration.class, ObjectMap.getObject(om, Duration.class));
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
					start(reset(stopwatch = getIfNull(stopwatch, Stopwatch::createUnstarted)));
					//
					// Set "Password"
					//
					testAndAccept((a, b) -> StringUtils.isNotEmpty(b), odfPd.getPackage(),
							StringMap.getString(stringMap, "password"), ExportTask::setPassword);
					//
					save(odfPd, file = new File(rowKey, String.join(".",
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
									StringUtils.leftPad(elapsedString = Util.toString(StopwatchUtil.elapsed(stopwatch)),
											maxElapsedStringLength),
									//
									// File
									//
									Util.getAbsolutePath(file)));
					//
					maxElapsedStringLength = Math.max(maxElapsedStringLength, StringUtils.length(elapsedString));
					//
				} // if
					//
			} // for
				//
		}

		@Nullable
		private static Stopwatch reset(@Nullable final Stopwatch instance) {
			return instance != null ? instance.reset() : instance;
		}

		@Nullable
		private static Stopwatch start(@Nullable final Stopwatch instance) {
			//
			if (instance == null) {
				//
				return null;
				//
			} // if
				//
			try {
				//
				final Field ticker = Stopwatch.class.getDeclaredField("ticker");
				//
				setAccessible(ticker, true);
				//
				if (get(ticker, instance) == null) {
					//
					return instance;
					//
				} // if
					//
			} catch (final NoSuchFieldException | IllegalAccessException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			return instance.start();
			//
		}

		@Nullable
		private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
			return field != null ? field.get(instance) : null;
		}

		private static void save(@Nullable final OdfPackageDocument instance, final File file) throws Exception {
			//
			if (instance == null) {
				//
				return;
				//
			} // if
				//
			try {
				//
				final Field mPackage = OdfPackageDocument.class.getDeclaredField("mPackage");
				//
				setAccessible(mPackage, true);
				//
				if (get(mPackage, instance) == null) {
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
			instance.save(file);
			//
		}

		private static void setPassword(@Nullable final OdfPackage instance, final String password) {
			if (instance != null) {
				instance.setPassword(password);
			}
		}

		@Nullable
		private static <R> Set<R> rowKeySet(@Nullable final Table<R, ?, ?> instance) {
			return instance != null ? instance.rowKeySet() : null;
		}

		private static void info(@Nullable final Logger instance, final String message) {
			if (instance != null) {
				instance.info(message);
			}
		}

		@Nullable
		private static OdfPresentationDocument generateOdfPresentationDocument(final ObjectMap objectMap,
				final Map<String, Voice> voices) throws Exception {
			//
			OdfPresentationDocument newOdfPresentationDocument = null;
			//
			try (final InputStream is = testAndApply(Objects::nonNull, ObjectMap.getObject(objectMap, byte[].class),
					ByteArrayInputStream::new, null)) {
				//
				final Set<Entry<String, Voice>> entrySet = Util.entrySet(voices);
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
					final NodeList pages = Util.cast(NodeList.class,
							testAndApply(Objects::nonNull, document, x -> evaluate(xp,
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
					// Default Slide Duration
					//
					final Node style = Util.cast(Node.class, evaluate(xp,
							"/*/*[local-name()='automatic-styles']/*[local-name()='style']/@*[local-name()='name']",
							document, XPathConstants.NODE));
					//
					if (Objects.equals(getNodeValue(style), "dp1")) {
						//
						testAndAccept(Objects::nonNull, ObjectMap.getObject(objectMap, Duration.class),
								x -> setNodeValue(Util.cast(Node.class, evaluate(xp,
										"../*[local-name()='drawing-page-properties']/@*[local-name()='duration']",
										style, XPathConstants.NODE)), Util.toString(x)));
						//
					} // if
						//
					for (final Entry<String, Voice> entry : entrySet) {
						//
						if (Boolean.logicalOr((voice = Util.getValue(entry)) == null,
								(pageCloned = cloneNode(page, true)) == null)) {
							//
							continue;
							//
						} // if
							//
							// Set Slide Name
							//
						setNodeValue(getNamedItem(getAttributes(pageCloned), "draw:name"), getSlideName(voice));
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
						setPluginHref(objectMap, Util.getKey(entry), embedAudioInPresentation, folderInPresentation);
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
					newOdfPresentationDocument = generateOdfPresentationDocument(Util.toString(writer),
							StringMap.getString(stringMap, "outputFolder"), Util.keySet(voices),
							embedAudioInPresentation, folderInPresentation);
					//
				} // if
					//
			} // try
				//
			return newOdfPresentationDocument;
			//
		}

		@Nullable
		private static Node getNamedItem(@Nullable final NamedNodeMap instance, final String name) {
			return instance != null ? instance.getNamedItem(name) : null;
		}

		@Nullable
		private static NamedNodeMap getAttributes(@Nullable final Node instance) {
			return instance != null ? instance.getAttributes() : null;
		}

		@Nullable
		private static Node item(@Nullable final NodeList instance, final int index) {
			return instance != null ? instance.item(index) : null;
		}

		@Nullable
		private static Document parse(@Nullable final DocumentBuilder instance, final InputStream is)
				throws SAXException, IOException {
			return instance != null ? instance.parse(is) : null;
		}

		@Nullable
		private static DocumentBuilder newDocumentBuilder(@Nullable final DocumentBuilderFactory instance)
				throws ParserConfigurationException {
			return instance != null ? instance.newDocumentBuilder() : null;
		}

		private static String getSlideName(final Voice voice) {
			//
			StringBuilder sb = null;
			//
			// text
			//
			final String text = getText(voice);
			//
			if (StringUtils.isNotBlank(text)) {
				//
				append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), text);
				//
			} // if
				//
				// hiragana and romaji
				//
			final String string = StringUtils
					.trim(Util.collect(Util.filter(Util.stream(Arrays.asList(getHiragana(voice), getRomaji(voice))),
							StringUtils::isNotBlank), Collectors.joining(" ")));
			//
			if (StringUtils.isNotBlank(string)) {
				//
				final int length = StringUtils.length(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
				//
				if (StringUtils.isNotBlank(sb)) {
					//
					append(sb, ' ');
					//
					append(sb, '(');
					//
				} // if
					//
				append(sb, string);
				//
				if (length > 0) {
					//
					append(sb, ')');
					//
				} // if
					//
			} // if
				//
			return Util.toString(sb);
			//
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

		private static void removeCustomShapeByName(final ObjectMap objectMap, @Nullable final String name)
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
			final NodeList customShapes = Util.cast(NodeList.class,
					evaluate(ObjectMap.getObject(objectMap, XPath.class), Util.toString(sb),
							ObjectMap.getObject(objectMap, Node.class), XPathConstants.NODESET));
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

		@Nullable
		private static OdfPresentationDocument generateOdfPresentationDocument(final String string,
				@Nullable final String outputFolder, final Collection<String> voiceLKeySet,
				final boolean embedAudioInPresentation, @Nullable final String folderInPresentation) throws Exception {
			//
			final File file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null);
			//
			try (final OdfPresentationDocument newOdfPresentationDocument = OdfPresentationDocument
					.newPresentationDocument()) {
				//
				save(newOdfPresentationDocument, file);
				//
				if (newOdfPresentationDocument != null) {
					//
					ZipUtil.replaceEntry(file, "content.xml", getBytes(string));
					//
					if (embedAudioInPresentation) {
						//
						ZipUtil.addEntries(file,
								toArray(Util.map(Util.stream(voiceLKeySet),
										x -> new FileSource(String.join("/", folderInPresentation, x),
												new File(outputFolder, x))),
										ZipEntrySource[]::new));
						//
					} // if
						//
					return OdfPresentationDocument.loadDocument(file);
					//
				} // if
					//
				return newOdfPresentationDocument;
				//
			} finally {
				//
				delete(file);
				//
			} // try
				//
		}

		private static File createTempFile(final String prefix, @Nullable final String suffix)
				throws IllegalAccessException, InvocationTargetException {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(File.class), Arrays::stream, null),
					x -> Objects.equals(Util.getName(x), "createTempFile")
							&& Arrays.equals(new Class<?>[] { String.class, String.class }, getParameterTypes(x))));
			//
			return Util.cast(File.class,
					invoke(testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null), null,
							prefix, suffix));
			//
		}

		@Nullable
		private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
			return instance != null ? instance.getParameterTypes() : null;
		}

		@Nullable
		private static <T, A> A[] toArray(@Nullable final Stream<T> instance,
				@Nullable final IntFunction<A[]> generator) {
			//
			return instance != null && (generator != null || Proxy.isProxyClass(Util.getClass(instance)))
					? instance.toArray(generator)
					: null;
			//
		}

		private static void replaceText(final ObjectMap objectMap, @Nullable final String messageDigestAlgorithm)
				throws XPathExpressionException, NoSuchAlgorithmException {
			//
			final NodeList ps = Util.cast(NodeList.class,
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
								MessageDigest.getInstance(Objects.toString(messageDigestAlgorithm, SHA_512)));
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

		private static void replaceTextContent(final ObjectMap objectMap, @Nullable final Map<?, ?> map) {
			//
			final freemarker.template.Configuration configuration = ObjectMap.getObject(objectMap,
					freemarker.template.Configuration.class);
			//
			final StringTemplateLoader stl = ObjectMap.getObject(objectMap, StringTemplateLoader.class);
			//
			testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
					x -> ConfigurationUtil.setTemplateLoader(x, stl));
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
							Util.digest(ObjectMap.getObject(objectMap, MessageDigest.class), getBytes(textContent)),
							Hex::encodeHexString, null)
			//
			), (a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b)));
			//
			StringTemplateLoaderUtil.putTemplate(stl, key, textContent);
			//
			try (final Writer writer = new StringWriter()) {
				//
				TemplateUtil.process(ConfigurationUtil.getTemplate(configuration, key), map, writer);
				//
				setTextContent(node, Util.toString(writer));
				//
			} catch (final IOException | TemplateException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		}

		@Nullable
		private static String getTextContent(@Nullable final Node instance) {
			return instance != null ? instance.getTextContent() : null;
		}

		private static void setTextContent(@Nullable final Node instance, final String textContent)
				throws DOMException {
			if (instance != null) {
				instance.setTextContent(textContent);
			}
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

		private static void setPluginHref(final ObjectMap objectMap, final String key,
				final boolean embedAudioInPresentation, @Nullable final String folder) throws XPathExpressionException {
			//
			final NodeList plugins = Util.cast(NodeList.class,
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
					//
					if (matches(matcher(pattern, getNodeName(attribute = attributes.item(j))))) {
						//
						clear(sb = getIfNull(sb, StringBuilder::new));
						//
						setNodeValue(
								attribute, Util
										.toString(append(
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

		private static int getLength(@Nullable final NodeList instance) {
			return instance != null ? instance.getLength() : 0;
		}

		private static void setNodeValue(@Nullable final Node instance, final String nodeValue) {
			if (instance != null) {
				instance.setNodeValue(nodeValue);
			}
		}

		@Nullable
		private static String getNodeValue(@Nullable final Node instance) {
			return instance != null ? instance.getNodeValue() : null;
		}

		@Nullable
		private static String getNodeName(@Nullable final Node instance) {
			return instance != null ? instance.getNodeName() : null;
		}

		@Nullable
		private static XPath newXPath(@Nullable final XPathFactory instance) {
			return instance != null ? instance.newXPath() : null;
		}

		@Nullable
		private static Node getParentNode(@Nullable final Node instance) {
			return instance != null ? instance.getParentNode() : null;
		}

		private static void appendChild(@Nullable final Node instance, @Nullable final Node child) throws DOMException {
			if (instance != null) {
				instance.appendChild(child);
			}
		}

		private static void removeChild(@Nullable final Node instance, final Node child) throws DOMException {
			if (instance != null) {
				instance.removeChild(child);
			}
		}

		@Nullable
		private static Node cloneNode(@Nullable final Node instance, final boolean deep) {
			return instance != null ? instance.cloneNode(deep) : null;
		}

		@Nullable
		private static Transformer newTransformer(@Nullable final TransformerFactory instance)
				throws TransformerConfigurationException {
			return instance != null ? instance.newTransformer() : null;
		}

		private static void transform(@Nullable final Transformer instance, final Source xmlSource,
				final Result outputTarget) throws TransformerException {
			if (instance != null) {
				instance.transform(xmlSource, outputTarget);
			}
		}

		@Nullable
		private static Object evaluate(@Nullable final XPath instance, final String expression, final Object item,
				final QName returnType) throws XPathExpressionException {
			return instance != null ? instance.evaluate(expression, item, returnType) : null;
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
		final VoiceManagerExportPanel voiceManagerExportPanel = testAndApply(
				c -> ObjectMap.containsObject(objectMap, c), VoiceManagerExportPanel.class,
				c -> ObjectMap.getObject(objectMap, c), null);
		//
		et.ordinalPositionFileNamePrefix = Util.getText((et.voiceManagerExportPanel = voiceManagerExportPanel) != null
				? voiceManagerExportPanel.tfOrdinalPositionFileNamePrefix
				: null);
		//
		et.exportPresentationTemplate = voiceManagerExportPanel != null
				? voiceManagerExportPanel.exportPresentationTemplate
				: null;
		//
		et.folderInPresentation = voiceManagerExportPanel != null ? voiceManagerExportPanel.folderInPresentation : null;
		//
		et.password = Util.getText(voiceManagerExportPanel != null ? voiceManagerExportPanel.tfExportPassword : null);
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
		et.messageDigestAlgorithm = voiceManagerExportPanel != null ? voiceManagerExportPanel.messageDigestAlgorithm
				: null;
		//
		et.presentationSlideDuration = ObjectMap.getObject(objectMap, Duration.class);
		//
		return et;
		//
	}

	private static void clear(@Nullable final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
	}

	private static void shutdown(@Nullable final ExecutorService instance) {
		if (instance != null) {
			instance.shutdown();
		}
	}

	@Nullable
	private static Integer incrementAndGet(@Nullable final AtomicInteger instance) {
		return instance != null ? Integer.valueOf(instance.incrementAndGet()) : null;
	}

	@Nullable
	private static Integer getOrdinalPosition(@Nullable final Voice instance) {
		return instance != null ? instance.getOrdinalPosition() : null;
	}

	@Nullable
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

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
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
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Nullable
	private static Duration parse(final CharSequence text) {
		return StringUtils.isNotEmpty(text) ? Duration.parse(text) : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static void setListNames(@Nullable final Voice instance, final Iterable<String> listNames) {
		if (instance != null) {
			instance.setListNames(listNames);
		}
	}

	@Nullable
	private static List<String> searchVoiceListNamesByVoiceId(@Nullable final VoiceMapper instance,
			final Integer voiceId) {
		return instance != null ? instance.searchVoiceListNamesByVoiceId(voiceId) : null;
	}

	private static boolean isSelected(@Nullable final AbstractButton instance) {
		return instance != null && instance.isSelected();
	}

	@Nullable
	private static Integer getId(@Nullable final Voice instance) {
		return instance != null ? instance.getId() : null;
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
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	@Nullable
	private static List<Voice> retrieveAllVoices(@Nullable final VoiceMapper instance) {
		return instance != null ? instance.retrieveAllVoices() : null;
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
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
					VoiceManagerExportPanel::getAccessibleObjectIsAccessibleMethod));
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
	private static Row addLocaleIdRow(final ObjectMap objectMap, @Nullable final List<Field> fs,
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

	private static Method getAccessibleObjectIsAccessibleMethod() {
		//
		final List<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(AccessibleObject.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(Util.getName(m), "isAccessible") && m.getParameterCount() == 0));
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> get(x, 0), null);
		//
	}

	@Nullable
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
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
	private static Integer getPhysicalNumberOfRows(@Nullable final Sheet instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfRows()) : null;
	}

	@Nullable
	private static Package getPackage(@Nullable final Class<?> instance) {
		return instance != null ? instance.getPackage() : null;
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

	private static void setAuthor(@Nullable final Comment instance, final String string) {
		if (instance != null) {
			instance.setAuthor(string);
		}
	}

	private static void setCellComment(@Nullable final Cell instance, @Nullable final Comment comment) {
		if (instance != null) {
			instance.setCellComment(comment);
		}
	}

	private static void setString(@Nullable final Comment instance, @Nullable final RichTextString string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

	@Nullable
	private static RichTextString createRichTextString(@Nullable final CreationHelper instance, final String text) {
		return instance != null ? instance.createRichTextString(text) : null;
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
	private static Drawing<?> createDrawingPatriarch(@Nullable final Sheet instance) {
		return instance != null ? instance.createDrawingPatriarch() : null;
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
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

	private static <T> boolean anyMatch(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(Util.getClass(instance)))
				&& instance.anyMatch(predicate);
		//
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
	private static List<Field> findFieldsByValue(@Nullable final Field[] fs, final Object instance,
			@Nullable final Object value) {
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
			if ((fieldValue = testAndApply(VoiceManagerExportPanel::isStatic, f, Narcissus::getStaticField,
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> a, @Nullable final BiConsumer<T, U> b) {
		if (test(instance, t, u)) {
			accept(a, t, u);
		} else {
			accept(b, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, @Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
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

	private static void setEditable(final boolean editable, @Nullable final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
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

	@Nullable
	private static <T> Optional<T> reduce(@Nullable final Stream<T> instance,
			@Nullable final BinaryOperator<T> accumulator) {
		//
		return instance != null && (accumulator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.reduce(accumulator)
				: null;
		//
	}

	@Nullable
	private static String getEmptyFilePath(@Nullable final FileFormatDetails instance) {
		return instance != null ? instance.getEmptyFilePath() : null;
	}

	@Nullable
	private static JetFormat getFormat(@Nullable final FileFormatDetails instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <K, V> void putAll(@Nullable final Map<K, V> a, @Nullable final Map<? extends K, ? extends V> b) {
		if (a != null && b != null) {
			a.putAll(b);
		}
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate,
			@Nullable final T value, final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static void setToolTipText(@Nullable final JComponent instance, final String toolTipText) {
		if (instance != null) {
			instance.setToolTipText(toolTipText);
		}
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
	private static String[] getFileExtensions(@Nullable final ContentType instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static String name(@Nullable final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	@Nullable
	private static <T> Optional<T> findFirst(@Nullable final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static <E> void setRenderer(@Nullable final JComboBox<E> instance,
			final ListCellRenderer<? super E> aRenderer) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (Narcissus.invokeMethod(instance, Component.class.getDeclaredMethod("getObjectLock")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchMethodException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.setRenderer(aRenderer);
		//
	}

	@Nullable
	private static <E> ListCellRenderer<? super E> getRenderer(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> getWorkbookClassFailableSupplierMap() {
		//
		if (workbookClassFailableSupplierMap == null) {
			//
			workbookClassFailableSupplierMap = Unit.with(Util.collect(
					Util.stream(new Reflections("org.apache.poi").getSubTypesOf(Workbook.class)),
					Collectors.toMap(Functions.identity(), x -> new FailableSupplier<Workbook, RuntimeException>() {

						@Override
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
	private static <T> T newInstance(@Nullable final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
	}

	@Nullable
	private static <T> Constructor<T> getDeclaredConstructor(@Nullable final Class<T> clz,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredConstructor(parameterTypes) : null;
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory)
			throws BeansException {
		//
		this.configurableListableBeanFactory = configurableListableBeanFactory;
		//
	}

}