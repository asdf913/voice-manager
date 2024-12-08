package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FocusTraversalPolicy;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.ItemSelectable;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sql.DataSource;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.ObjectType;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.OnlineNHKJapanesePronunciationsAccentFailableFunction;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.pkg.OdfPackageDocument;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.slf4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.AttributeAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.util.ReflectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zeroturnaround.zip.ZipUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.reflect.Reflection;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.impl.DatabaseImpl.FileFormatDetails;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentType;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import domain.JlptVocabulary;
import domain.Pronunciation;
import domain.Voice;
import domain.Voice.ByteArray;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.specialized.ATag;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import javazoom.jl.player.Player;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

class VoiceManagerTest {

	private static final String EMPTY = "";

	private static final String SPACE = " ";

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static final int TWO = 2;

	private static final int THREE = 3;

	private static Class<?> CLASS_OBJECT_MAP, CLASS_BOOLEAN_MAP, CLASS_STRING_MAP, CLASS_IH, CLASS_EXPORT_TASK,
			CLASS_IMPORT_TASK, CLASS_BYTE_CONVERTER = null;

	private static Integer TEMP_FILE_MINIMUM_PREFIX_LENGTH = null;

	private static Method METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS, METHOD_GET_FILE_EXTENSION_CONTENT_INFO,
			METHOD_GET_FILE_EXTENSION_FILE_FORMAT, METHOD_GET_FILE_EXTENSION_FAILABLE_SUPPLIER, METHOD_GET_MAPPER,
			METHOD_INSERT_OR_UPDATE, METHOD_SET_ENABLED_2, METHOD_SET_ENABLED_3, METHOD_TEST_AND_APPLY4,
			METHOD_TEST_AND_APPLY5, METHOD_INT_VALUE, METHOD_LONG_VALUE, METHOD_GET_PROPERTY_CUSTOM_PROPERTIES,
			METHOD_GET_VALUE, METHOD_GET_SOURCE_VOICE, METHOD_EXPORT, METHOD_MAP_INT_STREAM, METHOD_MAP_TO_INT,
			METHOD_MAP_TO_LONG, METHOD_MAX_STREAM, METHOD_MAX_INT_STREAM, METHOD_OR_ELSE_OPTIONAL_INT,
			METHOD_FOR_EACH_STREAM, METHOD_FOR_EACH_ITERABLE, METHOD_CREATE_WORK_BOOK_LIST,
			METHOD_CREATE_VOICE_OBJECT_MAPPER, METHOD_CREATE_VOICE_OBJECT_MAP, METHOD_INVOKE, METHOD_ANNOTATION_TYPE,
			METHOD_FIND_FIRST, METHOD_GET_PREFERRED_WIDTH, METHOD_IMPORT_VOICE1,
			METHOD_IMPORT_VOICE_OBJECT_MAP_BI_CONSUMER, METHOD_IMPORT_VOICE_OBJECT_MAP_FILE, METHOD_IMPORT_VOICE5,
			METHOD_IMPORT_VOICE_BY_SPEECH_API,
			METHOD_IMPORT_VOICE_BY_ONLINE_NHK_JAPANESE_PRONUNCIATIONS_ACCENT_FAILABLE_FUNCTION, METHOD_ADD_CONTAINER2,
			METHOD_ADD_CONTAINER3, METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY, METHOD_ANY_MATCH, METHOD_NAME,
			METHOD_GET_SELECTED_ITEM, METHOD_MATCHER, METHOD_MATCHES, METHOD_SET_VALUE_J_PROGRESS_BAR,
			METHOD_SET_STRING_J_PROGRESS_BAR, METHOD_SET_STRING_COMMENT, METHOD_SET_TOOL_TIP_TEXT, METHOD_FORMAT,
			METHOD_VALUE_OF1, METHOD_VALUE_OF2, METHOD_WRITE_VOICE_TO_FILE, METHOD_GET_MP3_TAG_VALUE_FILE,
			METHOD_GET_MP3_TAG_VALUE_LIST, METHOD_GET_MP3_TAG_PARIRS_ID3V1, METHOD_COPY_OBJECT_MAP, METHOD_DELETE,
			METHOD_DELETE_ON_EXIT, METHOD_CONVERT_LANGUAGE_CODE_TO_TEXT, METHOD_IS_SELECTED,
			METHOD_SET_HIRAGANA_OR_KATAKANA, METHOD_SET_ROMAJI, METHOD_AND, METHOD_OR, METHOD_CLEAR_DEFAULT_TABLE_MODEL,
			METHOD_CLEAR_STRING_BUILDER, METHOD_EXECUTE, METHOD_GET_BYTE_CONVERTER, METHOD_GET_LPW_STR,
			METHOD_GET_SHEET_NAME, METHOD_ACCEPT, METHOD_TO_ARRAY_COLLECTION, METHOD_TO_ARRAY_STREAM1,
			METHOD_TO_ARRAY_STREAM2, METHOD_GET_ID, METHOD_SET_MAXIMUM, METHOD_GET_CURRENT_SHEET_INDEX,
			METHOD_GET_DATA_VALIDATION_HELPER, METHOD_CREATE_EXPLICIT_LIST_CONSTRAINT, METHOD_CREATE_VALIDATION,
			METHOD_CREATE_EXPORT_TASK, METHOD_GET_TAB_INDEX_BY_TITLE, METHOD_GET_DECLARED_FIELD,
			METHOD_GET_ENUM_CONSTANTS, METHOD_LIST_FILES, METHOD_GET_COLUMN_NAME, METHOD_PUT_ALL_MAP,
			METHOD_GET_WORK_BOOK, METHOD_GET_OLE_ENTRY_NAMES, METHOD_NEW_DOCUMENT_BUILDER, METHOD_PARSE,
			METHOD_GET_DOCUMENT_ELEMENT, METHOD_GET_CHILD_NODES, METHOD_GET_NAMED_ITEM, METHOD_GET_TEXT_CONTENT,
			METHOD_GET_NAME_FILE, METHOD_GET_PASS_WORD, METHOD_GET_SUPPLIER, METHOD_GET_LIST,
			METHOD_CREATE_MICROSOFT_SPEECH_OBJECT_LIBRARY_WORK_BOOK, METHOD_CREATE_DRAWING_PATRIARCH,
			METHOD_CREATE_CELL_COMMENT, METHOD_CREATE_CLIENT_ANCHOR, METHOD_CREATE_RICH_TEXT_STRING,
			METHOD_SET_CELL_COMMENT, METHOD_SET_AUTHOR, METHOD_TEST_AND_ACCEPT_PREDICATE,
			METHOD_TEST_AND_ACCEPT_BI_PREDICATE, METHOD_FIND_FIELDS_BY_VALUE, METHOD_GET_PACKAGE, METHOD_BROWSE,
			METHOD_TO_URI_FILE, METHOD_TO_URI_URL, METHOD_GET_DECLARED_CLASSES, METHOD_GET_DLL_PATH, METHOD_GET_RATE0,
			METHOD_GET_RATE_VOICE_MANAGER, METHOD_GET_RATE_FIELD_LIST, METHOD_IS_ANNOTATION_PRESENT,
			METHOD_ENCODE_TO_STRING, METHOD_GET_VOICE_MULTI_MAP_BY_LIST_NAME, METHOD_GET_VOICE_MULTI_MAP_BY_JLPT,
			METHOD_GET_FILE_EXTENSIONS, METHOD_REDUCE2, METHOD_REDUCE3, METHOD_APPEND_STRING, METHOD_APPEND_CHAR,
			METHOD_GET_RESOURCE_AS_STREAM, METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_METHOD,
			METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_INSTRUCTION_ARRAY, METHOD_GET_ATTRIBUTES, METHOD_GET_LENGTH,
			METHOD_ITEM, METHOD_GET_OS_VERSION_INFO_EX_MAP, METHOD_CREATE_JLPT_SHEET,
			METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2, METHOD_SET_VISIBLE, METHOD_RANDOM_ALPHABETIC,
			METHOD_GET_MEDIA_FORMAT_LINK, METHOD_GET_EVENT_TYPE, METHOD_GET_PARENT_FILE,
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET,
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET_FIRST_ROW, METHOD_EXPORT_JLPT,
			METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT, METHOD_SET_SHEET_HEADER_ROW, METHOD_ENCRYPT,
			METHOD_GET_WORKBOOK_BY_ZIP_FILE, METHOD_GET_ENCRYPTION_TABLE_HTML, METHOD_HTML, METHOD_LENGTH,
			METHOD_CREATE_ZIP_FILE, METHOD_RETRIEVE_ALL_VOICES, METHOD_SEARCH_VOICE_LIST_NAMES_BY_VOICE_ID,
			METHOD_SET_LIST_NAMES, METHOD_SET_SOURCE, METHOD_GET_PHYSICAL_NUMBER_OF_ROWS, METHOD_EXPORT_HTML,
			METHOD_ACTION_PERFORMED_FOR_SYSTEM_CLIPBOARD_ANNOTATED, METHOD_ACTION_PERFORMED_FOR_CONVERSION,
			METHOD_TEST_AND_RUN, METHOD_TO_CHAR_ARRAY, METHOD_GET_IF_NULL, METHOD_SET_LANGUAGE, METHOD_GET_LANGUAGE,
			METHOD_GET_BOOLEAN_VALUE, METHOD_GET_RESPONSE_CODE, METHOD_TO_RUNTIME_EXCEPTION, METHOD_GET_ALGORITHM,
			METHOD_SET_PREFERRED_WIDTH_ARRAY, METHOD_SET_PREFERRED_WIDTH_ITERABLE, METHOD_SET_PREFERRED_WIDTH_2,
			METHOD_GET_VALUE_FROM_CELL, METHOD_GET_MP3_TAGS, METHOD_KEY_RELEASED_FOR_TEXT_IMPORT, METHOD_IS_STATIC,
			METHOD_IMPORT_BY_WORK_BOOK_FILES, METHOD_ACTION_PERFORMED_FOR_EXPORT_BUTTONS,
			METHOD_CREATE_MULTI_MAP_BY_LIST_NAMES, METHOD_GET_FIELD_BY_NAME,
			METHOD_CREATE_PROVIDER_PLATFORM_J_TEXT_COMPONENT, METHOD_EXPORT_MICROSOFT_ACCESS, METHOD_IMPORT_RESULT_SET,
			METHOD_CREATE_VOICE_ID_WARNING_PANEL, METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL,
			METHOD_GET_EMPTY_FILE_PATH, METHOD_SET_LOCALE_ID_SHEET, METHOD_ADD_LOCALE_ID_ROW,
			METHOD_SET_FOCUS_CYCLE_ROOT, METHOD_SET_FOCUS_TRAVERSAL_POLICY, METHOD_GET_COMPONENTS,
			METHOD_GET_WORKBOOK_CLASS_FAILABLE_SUPPLIER_MAP, METHOD_GET_DECLARED_CONSTRUCTOR, METHOD_NEW_INSTANCE,
			METHOD_GET_WRITER, METHOD_GET_WORK_BOOK_CLASS, METHOD_GET_SYSTEM_PRINT_STREAM_BY_FIELD_NAME, METHOD_IF_ELSE,
			METHOD_GET_PAGE_TITLE, METHOD_SET_HIRAGANA_OR_KATAKANA_AND_ROMAJI, METHOD_TO_MILLIS,
			METHOD_SET_JLPT_VOCABULARY_AND_LEVEL, METHOD_GET_LEVEL, METHOD_ADD_ALL, METHOD_PLAY_AUDIO, METHOD_PLAY,
			METHOD_PRONOUNICATION_CHANGED, METHOD_REMOVE_ELEMENT_AT, METHOD_ACTION_PERFORMED_FOR_BTN_IMPORT,
			METHOD_GET_LIST_CELL_RENDERER_COMPONENT, METHOD_GET_FILE,
			METHOD_GET_PRONUNCIATION_AUDIO_FILE_BY_AUDIO_FORMAT, METHOD_GET_AUDIO_FILE3, METHOD_GET_AUDIO_FILE4,
			METHOD_IS_ALL_ATTRIBUTES_MATCHED, METHOD_CREATE_FUNCTION_FOR_BTN_CONVERT_TO_HIRAGANA, METHOD_WRITER,
			METHOD_READ_LINE, METHOD_PRINT_LN, METHOD_SET_PITCH_ACCENT_IMAGE, METHOD_GET_NUMERIC_CELL_VALUE,
			METHOD_SET_AUTO_FILTER, METHOD_CREATE_BYTE_ARRAY, METHOD_DOUBLE_VALUE, METHOD_GET_ELEMENT_AT,
			METHOD_GET_IMAGE_FORMAT, METHOD_GET_I_VALUE0_FROM_MAPS_BY_KEY, METHOD_IS_ALL_CHARACTERS_ALLOWED,
			METHOD_GET_VALUE_COLLECTION_BY_KEY, METHOD_CREATE_YOMI_NAME_MAP0, METHOD_CREATE_YOMI_NAME_MAP1,
			METHOD_GET_NUMBER, METHOD_GET_RENDERER, METHOD_SET_RENDERER, METHOD_SORTED, METHOD_GET_ID3V1_TAG,
			METHOD_GET_ID3V2_TAG, METHOD_ADD_VALIDATION_DATA, METHOD_CREATE_IMPORT_RESULT_PANEL, METHOD_GET_URL,
			METHOD_ADD_HYPER_LINK_LISTENER, METHOD_SHOW_OPEN_DIALOG, METHOD_OPEN_STREAM,
			METHOD_ACTION_PERFORMED_FOR_IMPORT_FILE_TEMPLATE, METHOD_SUBMIT, METHOD_OPEN_CONNECTION, METHOD_FORMAT_HEX,
			METHOD_SET_SELECTED_INDEX = null;

	@BeforeAll
	static void beforeAll() throws Throwable {
		//
		final Class<?> clz = VoiceManager.class;
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION_CONTENT_INFO = clz.getDeclaredMethod("getFileExtension", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION_FILE_FORMAT = clz.getDeclaredMethod("getFileExtension", FileFormat.class))
				.setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION_FAILABLE_SUPPLIER = clz.getDeclaredMethod("getFileExtension",
				FailableSupplier.class)).setAccessible(true);
		//
		(METHOD_GET_MAPPER = clz.getDeclaredMethod("getMapper", Configuration.class, Class.class, SqlSession.class))
				.setAccessible(true);
		//
		(METHOD_INSERT_OR_UPDATE = clz.getDeclaredMethod("insertOrUpdate", VoiceMapper.class, Voice.class))
				.setAccessible(true);
		//
		(METHOD_SET_ENABLED_2 = clz.getDeclaredMethod("setEnabled", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_SET_ENABLED_3 = clz.getDeclaredMethod("setEnabled", Boolean.TYPE, Component.class, Component[].class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_LONG_VALUE = clz.getDeclaredMethod("longValue", Number.class, Long.TYPE)).setAccessible(true);
		//
		(METHOD_GET_PROPERTY_CUSTOM_PROPERTIES = clz.getDeclaredMethod("getProperty", CustomProperties.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Expression.class, EvaluationContext.class))
				.setAccessible(true);
		//
		(METHOD_GET_SOURCE_VOICE = clz.getDeclaredMethod("getSource", Voice.class)).setAccessible(true);
		//
		(METHOD_EXPORT = clz.getDeclaredMethod("export", List.class, Map.class,
				CLASS_OBJECT_MAP = Class.forName("org.springframework.context.support.VoiceManager$ObjectMap")))
				.setAccessible(true);
		//
		(METHOD_MAP_INT_STREAM = clz.getDeclaredMethod("map", IntStream.class, IntUnaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_MAP_TO_INT = clz.getDeclaredMethod("mapToInt", Stream.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_MAP_TO_LONG = clz.getDeclaredMethod("mapToLong", Stream.class, ToLongFunction.class))
				.setAccessible(true);
		//
		(METHOD_MAX_STREAM = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_MAX_INT_STREAM = clz.getDeclaredMethod("max", IntStream.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE_OPTIONAL_INT = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_FOR_EACH_STREAM = clz.getDeclaredMethod("forEach", Stream.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_ITERABLE = clz.getDeclaredMethod("forEach", Iterable.class, FailableConsumer.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_WORK_BOOK_LIST = clz.getDeclaredMethod("createWorkbook", List.class,
				CLASS_BOOLEAN_MAP = Class.forName("org.springframework.context.support.VoiceManager$BooleanMap"),
				FailableSupplier.class)).setAccessible(true);
		//
		(METHOD_CREATE_VOICE_OBJECT_MAPPER = clz.getDeclaredMethod("createVoice", ObjectMapper.class,
				VoiceManager.class)).setAccessible(true);
		//
		(METHOD_CREATE_VOICE_OBJECT_MAP = clz.getDeclaredMethod("createVoice", CLASS_OBJECT_MAP, Boolean.TYPE,
				AtomicReference.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_ANNOTATION_TYPE = clz.getDeclaredMethod("annotationType", Annotation.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_PREFERRED_WIDTH = clz.getDeclaredMethod("getPreferredWidth", Component.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE1 = clz.getDeclaredMethod("importVoice", File.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE_OBJECT_MAP_BI_CONSUMER = clz.getDeclaredMethod("importVoice", CLASS_OBJECT_MAP,
				BiConsumer.class, BiConsumer.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE_OBJECT_MAP_FILE = clz.getDeclaredMethod("importVoice", CLASS_OBJECT_MAP, File.class,
				String.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE5 = clz.getDeclaredMethod("importVoice", Sheet.class, CLASS_OBJECT_MAP, String.class,
				BiConsumer.class, BiConsumer.class, Consumer.class, Collection.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE_BY_SPEECH_API = clz.getDeclaredMethod("importVoiceBySpeechApi", CLASS_OBJECT_MAP,
				String.class, String.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE_BY_ONLINE_NHK_JAPANESE_PRONUNCIATIONS_ACCENT_FAILABLE_FUNCTION = clz.getDeclaredMethod(
				"importVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction", CLASS_OBJECT_MAP, String.class))
				.setAccessible(true);
		//
		(METHOD_ADD_CONTAINER2 = clz.getDeclaredMethod("add", Container.class, Component.class)).setAccessible(true);
		//
		(METHOD_ADD_CONTAINER3 = clz.getDeclaredMethod("add", Container.class, Component.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY = clz.getDeclaredMethod("createImportFileTemplateByteArray",
				Boolean.TYPE, Collection.class, Collection.class)).setAccessible(true);
		//
		(METHOD_ANY_MATCH = clz.getDeclaredMethod("anyMatch", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_SET_VALUE_J_PROGRESS_BAR = clz.getDeclaredMethod("setValue", JProgressBar.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_SET_STRING_J_PROGRESS_BAR = clz.getDeclaredMethod("setString", JProgressBar.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_STRING_COMMENT = clz.getDeclaredMethod("setString", Comment.class, RichTextString.class))
				.setAccessible(true);
		//
		(METHOD_SET_TOOL_TIP_TEXT = clz.getDeclaredMethod("setToolTipText", JComponent.class, String.class))
				.setAccessible(true);
		//
		(METHOD_FORMAT = clz.getDeclaredMethod("format", NumberFormat.class, Double.TYPE)).setAccessible(true);
		//
		(METHOD_VALUE_OF1 = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF2 = clz.getDeclaredMethod("valueOf", String.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_WRITE_VOICE_TO_FILE = clz.getDeclaredMethod("writeVoiceToFile", CLASS_OBJECT_MAP, String.class,
				String.class, Integer.class, Integer.class)).setAccessible(true);
		//
		(METHOD_GET_MP3_TAG_VALUE_FILE = clz.getDeclaredMethod("getMp3TagValue", File.class, Predicate.class,
				String[].class)).setAccessible(true);
		//
		(METHOD_GET_MP3_TAG_VALUE_LIST = clz.getDeclaredMethod("getMp3TagValue", List.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_GET_MP3_TAG_PARIRS_ID3V1 = clz.getDeclaredMethod("getMp3TagParirs", ID3v1.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_COPY_OBJECT_MAP = clz.getDeclaredMethod("copyObjectMap", CLASS_OBJECT_MAP)).setAccessible(true);
		//
		(METHOD_DELETE = clz.getDeclaredMethod("delete", File.class)).setAccessible(true);
		//
		(METHOD_DELETE_ON_EXIT = clz.getDeclaredMethod("deleteOnExit", File.class)).setAccessible(true);
		//
		(METHOD_CONVERT_LANGUAGE_CODE_TO_TEXT = clz.getDeclaredMethod("convertLanguageCodeToText", LocaleID[].class,
				Integer.class)).setAccessible(true);
		//
		(METHOD_IS_SELECTED = clz.getDeclaredMethod("isSelected", AbstractButton.class)).setAccessible(true);
		//
		(METHOD_SET_HIRAGANA_OR_KATAKANA = clz.getDeclaredMethod("setHiraganaOrKatakana", Voice.class))
				.setAccessible(true);
		//
		(METHOD_SET_ROMAJI = clz.getDeclaredMethod("setRomaji", Voice.class, Jakaroma.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Predicate.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Predicate.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_CLEAR_DEFAULT_TABLE_MODEL = clz.getDeclaredMethod("clear", DefaultTableModel.class))
				.setAccessible(true);
		//
		(METHOD_CLEAR_STRING_BUILDER = clz.getDeclaredMethod("clear", StringBuilder.class)).setAccessible(true);
		//
		(METHOD_EXECUTE = clz.getDeclaredMethod("execute", CLASS_OBJECT_MAP)).setAccessible(true);
		//
		(METHOD_GET_BYTE_CONVERTER = clz.getDeclaredMethod("getByteConverter", ConfigurableListableBeanFactory.class,
				String.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_LPW_STR = clz.getDeclaredMethod("getLpwstr", CTProperty.class)).setAccessible(true);
		//
		(METHOD_GET_SHEET_NAME = clz.getDeclaredMethod("getSheetName", Sheet.class)).setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", Consumer.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_TO_ARRAY_COLLECTION = clz.getDeclaredMethod("toArray", Collection.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_TO_ARRAY_STREAM1 = clz.getDeclaredMethod("toArray", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_ID = clz.getDeclaredMethod("getId", VoiceList.class)).setAccessible(true);
		//
		(METHOD_SET_MAXIMUM = clz.getDeclaredMethod("setMaximum", JProgressBar.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_CURRENT_SHEET_INDEX = clz.getDeclaredMethod("getCurrentSheetIndex", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_GET_DATA_VALIDATION_HELPER = clz.getDeclaredMethod("getDataValidationHelper", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_EXPLICIT_LIST_CONSTRAINT = clz.getDeclaredMethod("createExplicitListConstraint",
				DataValidationHelper.class, String[].class)).setAccessible(true);
		//
		(METHOD_CREATE_VALIDATION = clz.getDeclaredMethod("createValidation", DataValidationHelper.class,
				DataValidationConstraint.class, CellRangeAddressList.class)).setAccessible(true);
		//
		(METHOD_CREATE_EXPORT_TASK = clz.getDeclaredMethod("createExportTask", CLASS_OBJECT_MAP, Integer.class,
				Integer.class, Integer.class, Map.class, Table.class)).setAccessible(true);
		//
		(METHOD_GET_TAB_INDEX_BY_TITLE = clz.getDeclaredMethod("getTabIndexByTitle", List.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_ENUM_CONSTANTS = clz.getDeclaredMethod("getEnumConstants", Class.class)).setAccessible(true);
		//
		(METHOD_LIST_FILES = clz.getDeclaredMethod("listFiles", File.class)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_NAME = clz.getDeclaredMethod("getColumnName", Class.class, Field.class)).setAccessible(true);
		//
		(METHOD_PUT_ALL_MAP = clz.getDeclaredMethod("putAll", Map.class, Map.class)).setAccessible(true);
		//
		(METHOD_GET_WORK_BOOK = clz.getDeclaredMethod("getWorkbook", File.class)).setAccessible(true);
		//
		(METHOD_GET_OLE_ENTRY_NAMES = clz.getDeclaredMethod("getOleEntryNames", POIFSFileSystem.class))
				.setAccessible(true);
		//
		(METHOD_NEW_DOCUMENT_BUILDER = clz.getDeclaredMethod("newDocumentBuilder", DocumentBuilderFactory.class))
				.setAccessible(true);
		//
		(METHOD_PARSE = clz.getDeclaredMethod("parse", DocumentBuilder.class, InputStream.class)).setAccessible(true);
		//
		(METHOD_GET_DOCUMENT_ELEMENT = clz.getDeclaredMethod("getDocumentElement", Document.class)).setAccessible(true);
		//
		(METHOD_GET_CHILD_NODES = clz.getDeclaredMethod("getChildNodes", Node.class)).setAccessible(true);
		//
		(METHOD_GET_NAMED_ITEM = clz.getDeclaredMethod("getNamedItem", NamedNodeMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_TEXT_CONTENT = clz.getDeclaredMethod("getTextContent", Node.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_FILE = clz.getDeclaredMethod("getName", File.class)).setAccessible(true);
		//
		(METHOD_GET_PASS_WORD = clz.getDeclaredMethod("getPassword", Console.class)).setAccessible(true);
		//
		(METHOD_GET_SUPPLIER = clz.getDeclaredMethod("get", Supplier.class)).setAccessible(true);
		//
		(METHOD_GET_LIST = clz.getDeclaredMethod("get", List.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_CREATE_MICROSOFT_SPEECH_OBJECT_LIBRARY_WORK_BOOK = clz
				.getDeclaredMethod("createMicrosoftSpeechObjectLibraryWorkbook", SpeechApi.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_CREATE_DRAWING_PATRIARCH = clz.getDeclaredMethod("createDrawingPatriarch", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_CELL_COMMENT = clz.getDeclaredMethod("createCellComment", Drawing.class, ClientAnchor.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_CLIENT_ANCHOR = clz.getDeclaredMethod("createClientAnchor", CreationHelper.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_RICH_TEXT_STRING = clz.getDeclaredMethod("createRichTextString", CreationHelper.class,
				String.class)).setAccessible(true);
		//
		(METHOD_SET_CELL_COMMENT = clz.getDeclaredMethod("setCellComment", Cell.class, Comment.class))
				.setAccessible(true);
		//
		(METHOD_SET_AUTHOR = clz.getDeclaredMethod("setAuthor", Comment.class, String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_PREDICATE = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_BI_PREDICATE = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class,
				Object.class, FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_FIND_FIELDS_BY_VALUE = clz.getDeclaredMethod("findFieldsByValue", Field[].class, Object.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_GET_PACKAGE = clz.getDeclaredMethod("getPackage", Class.class)).setAccessible(true);
		//
		(METHOD_BROWSE = clz.getDeclaredMethod("browse", Desktop.class, URI.class)).setAccessible(true);
		//
		(METHOD_TO_URI_FILE = clz.getDeclaredMethod("toURI", File.class)).setAccessible(true);
		//
		(METHOD_TO_URI_URL = clz.getDeclaredMethod("toURI", URL.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_CLASSES = clz.getDeclaredMethod("getDeclaredClasses", Class.class)).setAccessible(true);
		//
		(METHOD_GET_DLL_PATH = clz.getDeclaredMethod("getDllPath", Object.class)).setAccessible(true);
		//
		(METHOD_GET_RATE0 = clz.getDeclaredMethod("getRate")).setAccessible(true);
		//
		(METHOD_GET_RATE_VOICE_MANAGER = clz.getDeclaredMethod("getRate", VoiceManager.class)).setAccessible(true);
		//
		(METHOD_GET_RATE_FIELD_LIST = clz.getDeclaredMethod("getRate", List.class)).setAccessible(true);
		//
		(METHOD_IS_ANNOTATION_PRESENT = clz.getDeclaredMethod("isAnnotationPresent", AnnotatedElement.class,
				Class.class)).setAccessible(true);
		//
		(METHOD_ENCODE_TO_STRING = clz.getDeclaredMethod("encodeToString", Encoder.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_GET_VOICE_MULTI_MAP_BY_LIST_NAME = clz.getDeclaredMethod("getVoiceMultimapByListName", Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_VOICE_MULTI_MAP_BY_JLPT = clz.getDeclaredMethod("getVoiceMultimapByJlpt", Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSIONS = clz.getDeclaredMethod("getFileExtensions", ContentType.class))
				.setAccessible(true);
		//
		(METHOD_REDUCE2 = clz.getDeclaredMethod("reduce", Stream.class, BinaryOperator.class)).setAccessible(true);
		//
		(METHOD_REDUCE3 = clz.getDeclaredMethod("reduce", LongStream.class, Long.TYPE, LongBinaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_APPEND_STRING = clz.getDeclaredMethod("append", StringBuilder.class, String.class)).setAccessible(true);
		//
		(METHOD_APPEND_CHAR = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_METHOD = clz.getDeclaredMethod("getTempFileMinimumPrefixLength",
				org.apache.bcel.classfile.Method.class)).setAccessible(true);
		//
		(METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_INSTRUCTION_ARRAY = clz
				.getDeclaredMethod("getTempFileMinimumPrefixLength", Instruction[].class)).setAccessible(true);
		//
		(METHOD_GET_ATTRIBUTES = clz.getDeclaredMethod("getAttributes", Node.class)).setAccessible(true);
		//
		(METHOD_GET_LENGTH = clz.getDeclaredMethod("getLength", NodeList.class)).setAccessible(true);
		//
		(METHOD_ITEM = clz.getDeclaredMethod("item", NodeList.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_OS_VERSION_INFO_EX_MAP = clz.getDeclaredMethod("getOsVersionInfoExMap")).setAccessible(true);
		//
		(METHOD_CREATE_JLPT_SHEET = clz.getDeclaredMethod("createJlptSheet", Workbook.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2 = clz.getDeclaredMethod("errorOrAssertOrShowException", Boolean.TYPE,
				Throwable.class)).setAccessible(true);
		//
		(METHOD_SET_VISIBLE = clz.getDeclaredMethod("setVisible", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_RANDOM_ALPHABETIC = clz.getDeclaredMethod("randomAlphabetic", Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_MEDIA_FORMAT_LINK = clz.getDeclaredMethod("getMediaFormatLink", String.class)).setAccessible(true);
		//
		(METHOD_GET_EVENT_TYPE = clz.getDeclaredMethod("getEventType", HyperlinkEvent.class)).setAccessible(true);
		//
		(METHOD_GET_PARENT_FILE = clz.getDeclaredMethod("getParentFile", File.class)).setAccessible(true);
		//
		(METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET = clz.getDeclaredMethod(
				"setMicrosoftSpeechObjectLibrarySheet", CLASS_OBJECT_MAP, String.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET_FIRST_ROW = clz
				.getDeclaredMethod("setMicrosoftSpeechObjectLibrarySheetFirstRow", Sheet.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_EXPORT_JLPT = clz.getDeclaredMethod("exportJlpt", CLASS_OBJECT_MAP, List.class)).setAccessible(true);
		//
		(METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT = clz.getDeclaredMethod("getMaxPagePreferredHeight", Object.class))
				.setAccessible(true);
		//
		(METHOD_SET_SHEET_HEADER_ROW = clz.getDeclaredMethod("setSheetHeaderRow", Sheet.class, Field[].class,
				Class.class)).setAccessible(true);
		//
		(METHOD_ENCRYPT = clz.getDeclaredMethod("encrypt", File.class, EncryptionMode.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_WORKBOOK_BY_ZIP_FILE = clz.getDeclaredMethod("getWorkbookByZipFile", File.class))
				.setAccessible(true);
		//
		(METHOD_GET_ENCRYPTION_TABLE_HTML = clz.getDeclaredMethod("getEncryptionTableHtml", URL.class, Duration.class))
				.setAccessible(true);
		//
		(METHOD_HTML = clz.getDeclaredMethod("html", org.jsoup.nodes.Element.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", File.class)).setAccessible(true);
		//
		(METHOD_CREATE_ZIP_FILE = clz.getDeclaredMethod("createZipFile", CLASS_OBJECT_MAP, String.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_RETRIEVE_ALL_VOICES = clz.getDeclaredMethod("retrieveAllVoices", VoiceMapper.class))
				.setAccessible(true);
		//
		(METHOD_SEARCH_VOICE_LIST_NAMES_BY_VOICE_ID = clz.getDeclaredMethod("searchVoiceListNamesByVoiceId",
				VoiceMapper.class, Integer.class)).setAccessible(true);
		//
		(METHOD_SET_LIST_NAMES = clz.getDeclaredMethod("setListNames", Voice.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_SET_SOURCE = clz.getDeclaredMethod("setSource", Voice.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_PHYSICAL_NUMBER_OF_ROWS = clz.getDeclaredMethod("getPhysicalNumberOfRows", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_EXPORT_HTML = clz.getDeclaredMethod("exportHtml", CLASS_OBJECT_MAP, Multimap.class, Entry.class,
				Map.class, Collection.class)).setAccessible(true);
		//
		(METHOD_ACTION_PERFORMED_FOR_SYSTEM_CLIPBOARD_ANNOTATED = clz
				.getDeclaredMethod("actionPerformedForSystemClipboardAnnotated", Boolean.TYPE, Object.class))
				.setAccessible(true);
		//
		(METHOD_ACTION_PERFORMED_FOR_CONVERSION = clz.getDeclaredMethod("actionPerformedForConversion", Object.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, FailableRunnable.class))
				.setAccessible(true);
		//
		(METHOD_TO_CHAR_ARRAY = clz.getDeclaredMethod("toCharArray", String.class)).setAccessible(true);
		//
		(METHOD_GET_IF_NULL = clz.getDeclaredMethod("getIfNull", Object.class, FailableSupplier.class))
				.setAccessible(true);
		//
		(METHOD_SET_LANGUAGE = clz.getDeclaredMethod("setLanguage", Voice.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_LANGUAGE = clz.getDeclaredMethod("getLanguage", Voice.class)).setAccessible(true);
		//
		(METHOD_GET_BOOLEAN_VALUE = clz.getDeclaredMethod("getBooleanValue", CellValue.class)).setAccessible(true);
		//
		(METHOD_GET_RESPONSE_CODE = clz.getDeclaredMethod("getResponseCode", HttpURLConnection.class))
				.setAccessible(true);
		//
		(METHOD_TO_RUNTIME_EXCEPTION = clz.getDeclaredMethod("toRuntimeException", Throwable.class))
				.setAccessible(true);
		//
		(METHOD_GET_ALGORITHM = clz.getDeclaredMethod("getAlgorithm", MessageDigest.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH_ARRAY = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Component[].class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH_ITERABLE = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH_2 = clz.getDeclaredMethod("setPreferredWidth", Component.class, Supplier.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE_FROM_CELL = clz.getDeclaredMethod("getValueFromCell", CLASS_OBJECT_MAP)).setAccessible(true);
		//
		(METHOD_GET_MP3_TAGS = clz.getDeclaredMethod("getMp3Tags", VoiceManager.class)).setAccessible(true);
		//
		(METHOD_KEY_RELEASED_FOR_TEXT_IMPORT = clz.getDeclaredMethod("keyReleasedForTextImport", Multimap.class,
				JTextComponent.class, ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_IMPORT_BY_WORK_BOOK_FILES = clz.getDeclaredMethod("importByWorkbookFiles", File[].class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_ACTION_PERFORMED_FOR_EXPORT_BUTTONS = clz.getDeclaredMethod("actionPerformedForExportButtons",
				Object.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_BY_LIST_NAMES = clz.getDeclaredMethod("createMultimapByListNames", Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_FIELD_BY_NAME = clz.getDeclaredMethod("getFieldByName", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_PROVIDER_PLATFORM_J_TEXT_COMPONENT = clz
				.getDeclaredMethod("createProviderPlatformJTextComponent", Boolean.TYPE, Provider.class))
				.setAccessible(true);
		//
		(METHOD_EXPORT_MICROSOFT_ACCESS = clz.getDeclaredMethod("exportMicrosoftAccess", CLASS_OBJECT_MAP,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_IMPORT_RESULT_SET = clz.getDeclaredMethod("importResultSet", CLASS_OBJECT_MAP, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_VOICE_ID_WARNING_PANEL = clz.getDeclaredMethod("createVoiceIdWarningPanel", VoiceManager.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL = clz.getDeclaredMethod(
				"createMicrosoftWindowsCompatibilityWarningJPanel", LayoutManager.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_EMPTY_FILE_PATH = clz.getDeclaredMethod("getEmptyFilePath", FileFormatDetails.class))
				.setAccessible(true);
		//
		(METHOD_SET_LOCALE_ID_SHEET = clz.getDeclaredMethod("setLocaleIdSheet", CLASS_OBJECT_MAP)).setAccessible(true);
		//
		(METHOD_ADD_LOCALE_ID_ROW = clz.getDeclaredMethod("addLocaleIdRow", CLASS_OBJECT_MAP, List.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SET_FOCUS_CYCLE_ROOT = clz.getDeclaredMethod("setFocusCycleRoot", Container.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_SET_FOCUS_TRAVERSAL_POLICY = clz.getDeclaredMethod("setFocusTraversalPolicy", Container.class,
				FocusTraversalPolicy.class)).setAccessible(true);
		//
		(METHOD_GET_COMPONENTS = clz.getDeclaredMethod("getComponents", Container.class)).setAccessible(true);
		//
		(METHOD_GET_WORKBOOK_CLASS_FAILABLE_SUPPLIER_MAP = clz.getDeclaredMethod("getWorkbookClassFailableSupplierMap"))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_CONSTRUCTOR = clz.getDeclaredMethod("getDeclaredConstructor", Class.class, Class[].class))
				.setAccessible(true);
		//
		(METHOD_NEW_INSTANCE = clz.getDeclaredMethod("newInstance", Constructor.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_WRITER = clz.getDeclaredMethod("getWriter", Object.class)).setAccessible(true);
		//
		(METHOD_GET_WORK_BOOK_CLASS = clz.getDeclaredMethod("getWorkbookClass", Map.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_SYSTEM_PRINT_STREAM_BY_FIELD_NAME = clz.getDeclaredMethod("getSystemPrintStreamByFieldName",
				String.class)).setAccessible(true);
		//
		(METHOD_IF_ELSE = clz.getDeclaredMethod("ifElse", Boolean.TYPE, FailableRunnable.class, FailableRunnable.class))
				.setAccessible(true);
		//
		(METHOD_GET_PAGE_TITLE = clz.getDeclaredMethod("getPageTitle", String.class, Duration.class))
				.setAccessible(true);
		//
		(METHOD_SET_HIRAGANA_OR_KATAKANA_AND_ROMAJI = clz.getDeclaredMethod("setHiraganaOrKatakanaAndRomaji",
				Boolean.TYPE, Boolean.TYPE, Voice.class, Jakaroma.class)).setAccessible(true);
		//
		(METHOD_TO_MILLIS = clz.getDeclaredMethod("toMillis", Duration.class)).setAccessible(true);
		//
		(METHOD_SET_JLPT_VOCABULARY_AND_LEVEL = clz.getDeclaredMethod("setJlptVocabularyAndLevel", VoiceManager.class))
				.setAccessible(true);
		//
		(METHOD_GET_LEVEL = clz.getDeclaredMethod("getLevel", JlptVocabulary.class)).setAccessible(true);
		//
		(METHOD_ADD_ALL = clz.getDeclaredMethod("addAll", Collection.class, Collection.class)).setAccessible(true);
		//
		(METHOD_PLAY_AUDIO = clz.getDeclaredMethod("playAudio", Pronunciation.class, Object.class)).setAccessible(true);
		//
		(METHOD_PLAY = clz.getDeclaredMethod("play", Player.class)).setAccessible(true);
		//
		(METHOD_PRONOUNICATION_CHANGED = clz.getDeclaredMethod("pronounicationChanged", Pronunciation.class,
				MutableComboBoxModel.class, String.class, JTextComponent.class)).setAccessible(true);
		//
		(METHOD_REMOVE_ELEMENT_AT = clz.getDeclaredMethod("removeElementAt", MutableComboBoxModel.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_ACTION_PERFORMED_FOR_BTN_IMPORT = clz.getDeclaredMethod("actionPerformedForBtnImport", Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_LIST_CELL_RENDERER_COMPONENT = clz.getDeclaredMethod("getListCellRendererComponent",
				ListCellRenderer.class, JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_FILE = clz.getDeclaredMethod("getFile", URL.class)).setAccessible(true);
		//
		(METHOD_GET_PRONUNCIATION_AUDIO_FILE_BY_AUDIO_FORMAT = clz
				.getDeclaredMethod("getPronunciationAudioFileByAudioFormat", Pronunciation.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_AUDIO_FILE3 = clz.getDeclaredMethod("getAudioFile", Boolean.TYPE, Voice.class,
				DefaultTableModel.class)).setAccessible(true);
		//
		(METHOD_GET_AUDIO_FILE4 = clz.getDeclaredMethod("getAudioFile", Boolean.TYPE, JFileChooser.class, Voice.class,
				DefaultTableModel.class)).setAccessible(true);
		//
		(METHOD_IS_ALL_ATTRIBUTES_MATCHED = clz.getDeclaredMethod("isAllAttributesMatched", Map.class,
				AttributeAccessor.class)).setAccessible(true);
		//
		(METHOD_CREATE_FUNCTION_FOR_BTN_CONVERT_TO_HIRAGANA = clz
				.getDeclaredMethod("createFunctionForBtnConvertToHiraganaOrKatakana", String.class))
				.setAccessible(true);
		//
		(METHOD_WRITER = clz.getDeclaredMethod("writer", Console.class)).setAccessible(true);
		//
		(METHOD_READ_LINE = clz.getDeclaredMethod("readLine", Console.class, String.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_PRINT_LN = clz.getDeclaredMethod("println", PrintWriter.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_PITCH_ACCENT_IMAGE = clz.getDeclaredMethod("setPitchAccentImage", Voice.class, ByteArray.class))
				.setAccessible(true);
		//
		(METHOD_GET_NUMERIC_CELL_VALUE = clz.getDeclaredMethod("getNumericCellValue", Cell.class)).setAccessible(true);
		//
		(METHOD_SET_AUTO_FILTER = clz.getDeclaredMethod("setAutoFilter", Sheet.class)).setAccessible(true);
		//
		(METHOD_CREATE_BYTE_ARRAY = clz.getDeclaredMethod("createByteArray", RenderedImage.class, String.class,
				Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_DOUBLE_VALUE = clz.getDeclaredMethod("doubleValue", Number.class, Double.TYPE)).setAccessible(true);
		//
		(METHOD_GET_ELEMENT_AT = clz.getDeclaredMethod("getElementAt", ListModel.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_IMAGE_FORMAT = clz.getDeclaredMethod("getImageFormat", IValue0.class, Collection.class))
				.setAccessible(true);
		//
		(METHOD_GET_I_VALUE0_FROM_MAPS_BY_KEY = clz.getDeclaredMethod("getIValue0FromMapsByKey", Iterable.class,
				Object.class, Function.class)).setAccessible(true);
		//
		(METHOD_IS_ALL_CHARACTERS_ALLOWED = clz.getDeclaredMethod("isAllCharactersAllowed", CharSequence.class,
				char[].class)).setAccessible(true);
		//
		(METHOD_GET_VALUE_COLLECTION_BY_KEY = clz.getDeclaredMethod("getValueCollectionByKey", Iterable.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_YOMI_NAME_MAP0 = clz.getDeclaredMethod("createYomiNameMap")).setAccessible(true);
		//
		(METHOD_CREATE_YOMI_NAME_MAP1 = clz.getDeclaredMethod("createYomiNameMap", Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_NUMBER = clz.getDeclaredMethod("getNumber", Object.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_RENDERER = clz.getDeclaredMethod("getRenderer", JComboBox.class)).setAccessible(true);
		//
		(METHOD_SET_RENDERER = clz.getDeclaredMethod("setRenderer", JComboBox.class, ListCellRenderer.class))
				.setAccessible(true);
		//
		(METHOD_SORTED = clz.getDeclaredMethod("sorted", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_GET_ID3V1_TAG = clz.getDeclaredMethod("getId3v1Tag", Mp3File.class)).setAccessible(true);
		//
		(METHOD_GET_ID3V2_TAG = clz.getDeclaredMethod("getId3v2Tag", Mp3File.class)).setAccessible(true);
		//
		(METHOD_ADD_VALIDATION_DATA = clz.getDeclaredMethod("addValidationData", Sheet.class, DataValidation.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_IMPORT_RESULT_PANEL = clz.getDeclaredMethod("createImportResultPanel", LayoutManager.class))
				.setAccessible(true);
		//
		(METHOD_GET_URL = clz.getDeclaredMethod("getURL", HyperlinkEvent.class)).setAccessible(true);
		//
		(METHOD_ADD_HYPER_LINK_LISTENER = clz.getDeclaredMethod("addHyperlinkListener", JEditorPane.class,
				HyperlinkListener.class)).setAccessible(true);
		//
		(METHOD_SHOW_OPEN_DIALOG = clz.getDeclaredMethod("showOpenDialog", JFileChooser.class, Component.class))
				.setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_ACTION_PERFORMED_FOR_IMPORT_FILE_TEMPLATE = clz
				.getDeclaredMethod("actionPerformedForImportFileTemplate", Boolean.TYPE, JFileChooser.class))
				.setAccessible(true);
		//
		(METHOD_SUBMIT = clz.getDeclaredMethod("submit", ExecutorService.class, Runnable.class)).setAccessible(true);
		//
		(METHOD_OPEN_CONNECTION = clz.getDeclaredMethod("openConnection", URL.class)).setAccessible(true);
		//
		(METHOD_FORMAT_HEX = clz.getDeclaredMethod("formatHex", HexFormat.class, byte[].class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDEX = clz.getDeclaredMethod("setSelectedIndex", JTabbedPane.class, Number.class))
				.setAccessible(true);
		//
		CLASS_IH = Class.forName("org.springframework.context.support.VoiceManager$IH");
		//
		(METHOD_TO_ARRAY_STREAM2 = (CLASS_EXPORT_TASK = Class
				.forName("org.springframework.context.support.VoiceManager$ExportTask"))
				.getDeclaredMethod("toArray", Stream.class, IntFunction.class)).setAccessible(true);
		//
		CLASS_IMPORT_TASK = Class.forName("org.springframework.context.support.VoiceManager$ImportTask");
		//
		CLASS_STRING_MAP = Class.forName("org.springframework.context.support.VoiceManager$StringMap");
		//
		CLASS_BYTE_CONVERTER = Class.forName("org.springframework.context.support.VoiceManager$ByteConverter");
		//
		TEMP_FILE_MINIMUM_PREFIX_LENGTH = Integer.valueOf(intValue(Util.cast(Number.class,
				FieldUtils.readDeclaredStaticField(VoiceManager.class, "TEMP_FILE_MINIMUM_PREFIX_LENGTH", true)),
				THREE));
		//
	}

	private static class IH implements InvocationHandler {

		private Error errorGetVoiceAttribute = null;

		private Voice voice = null;

		private Set<Entry<?, ?>> entrySet = null;

		private String toString, stringCellValue, providerName, providerVersion, artist, title, voiceAttribute, lpwstr,
				sheetName, textContent, nodeName, dllPath, providerPlatform = null;

		private Configuration configuration = null;

		private SqlSession sqlSession = null;

		private Expression expression = null;

		private Object value, min, max, selectedItem, nodeValue, key = null;

		private Iterator<Row> rows = null;

		private Iterator<Cell> cells = null;

		private Boolean anyMatch, contains, isInstalled, isEmpty = null;

		private String[] voiceIds = null;

		private Iterator<?> iterator = null;

		private Map<Object, Object> beansOfType = null;

		private Map<Object, BeanDefinition> beanDefinitions = null;

		private Map<Object, Object> beanDefinitionAttributes = null;

		private Object[] toArray = null;

		private VoiceList voiceList = null;

		private Workbook workbook = null;

		private Integer numberOfSheets, length, columnIndex = null;

		private IntStream intStream = null;

		private LongStream longStream = null;

		private Collection<Entry<?, ?>> multiMapEntries = null;

		private Node namedItem, parentNode, appendChild, removeChild, cloneNode, item = null;

		private NamedNodeMap attributes = null;

		private List<Voice> voices = null;

		private Set<?> keySet = null;

		private CellType cellType = null;

		private PreparedStatement preparedStatement = null;

		private ResultSet resultSet = null;

		private javax.swing.text.Document document = null;

		private Collection<?> values = null;

		private byte[] convertedByteArray = null;

		private List<Pronunciation> pronunciations = null;

		private String[] beanDefinitionNames = null;

		private Map<?, ?> attributeMap = null;

		private Double numericCellValue = null;

		private Map<Object, BeanDefinition> getBeanDefinitions() {
			if (beanDefinitions == null) {
				beanDefinitions = new LinkedHashMap<>();
			}
			return beanDefinitions;
		}

		private Map<Object, Object> getBeanDefinitionAttributes() {
			if (beanDefinitionAttributes == null) {
				beanDefinitionAttributes = new LinkedHashMap<>();
			}
			return beanDefinitionAttributes;
		}

		private Map<?, ?> getAttributeMap() {
			if (attributeMap == null) {
				attributeMap = new LinkedHashMap<>();
			}
			return attributeMap;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (ReflectionUtils.isToStringMethod(method)) {
				//
				return toString;
				//
			} else if (ReflectionUtils.isEqualsMethod(method) && args != null && args.length > 0) {
				//
				return EqualsBuilder.reflectionEquals(this, args[0]);
				//
			} // if
				//
			final Class<?> returnType = method != null ? method.getReturnType() : null;
			//
			if (Objects.equals(Void.TYPE, returnType)) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof ListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeansOfType")) {
					//
					return beansOfType;
					//
				} else if (Objects.equals(methodName, "getBeanDefinitionNames")) {
					//
					return beanDefinitionNames;
					//
				} // if
					//
			} else if (proxy instanceof OnlineNHKJapanesePronunciationsAccentFailableFunction) {
				//
				if (Objects.equals(methodName, "apply")) {
					//
					return pronunciations;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Iterable) {
				//
				if (proxy instanceof Sheet) {
					//
					if (Objects.equals(methodName, "getSheetName")) {
						//
						return sheetName;
						//
					} else if (Objects.equals(methodName, "getWorkbook")) {
						//
						return workbook;
						//
					} // if
						//
					return rows;
					//
				} else if (proxy instanceof Row) {
					//
					return cells;
					//
				} else if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof VoiceMapper) {
				//
				if (Objects.equals(methodName, "searchByTextAndRomaji")) {
					//
					return voice;
					//
				} else if (Objects.equals(methodName, "searchVoiceListByName")) {
					//
					return voiceList;
					//
				} else if (Objects.equals(methodName, "retrieveAllVoices")) {
					//
					return voices;
					//
				} else if (Objects.equals(methodName, "searchVoiceListNamesByVoiceId")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return toArray;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} else if (Objects.equals(methodName, "isEmpty")) {
					//
					return isEmpty;
					//
				} else if (Objects.equals(methodName, "values")) {
					//
					return values;
					//
				} // if
					//
			} else if (proxy instanceof SqlSessionFactory) {
				//
				if (Objects.equals(methodName, "getConfiguration")) {
					//
					return configuration;
					//
				} else if (Objects.equals(methodName, "openSession")) {
					//
					return sqlSession;
					//
				} // if
					//
			} else if (proxy instanceof ExpressionParser) {
				//
				if (Objects.equals(methodName, "parseExpression")) {
					//
					return expression;
					//
				} // if
					//
			} else if (proxy instanceof Expression) {
				//
				if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(Stream.class, returnType)) {
					//
					return proxy;
					//
				} else if (Objects.equals(methodName, "min")) {
					//
					return min;
					//
				} else if (Objects.equals(methodName, "max")) {
					//
					return max;
					//
				} else if (Objects.equals(methodName, "collect")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "anyMatch")) {
					//
					return anyMatch;
					//
				} else if (Objects.equals(methodName, "mapToInt")) {
					//
					return intStream;
					//
				} else if (Objects.equals(methodName, "mapToLong")) {
					//
					return longStream;
					//
				} else if (Objects.equals(methodName, "toArray")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "reduce")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof IntStream) {
				//
				if (Objects.equals(IntStream.class, returnType)) {
					//
					return proxy;
					//
				} // if
					//
			} else if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getStringCellValue")) {
					//
					return stringCellValue;
					//
				} else if (Objects.equals(methodName, "getColumnIndex")) {
					//
					return columnIndex;
					//
				} else if (Objects.equals(methodName, "getCellType")) {
					//
					return cellType;
					//
				} else if (Objects.equals(methodName, "getNumericCellValue")) {
					//
					return numericCellValue;
					//
				} // if
					//
			} else if (proxy instanceof ComboBoxModel) {
				//
				if (Objects.equals(methodName, "getSelectedItem")) {
					//
					return selectedItem;
					//
				} // if
					//
			} else if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(methodName, "getVoiceIds")) {
					//
					return voiceIds;
					//
				} else if (Objects.equals(methodName, "getVoiceAttribute")) {
					//
					if (errorGetVoiceAttribute != null) {
						//
						throw errorGetVoiceAttribute;
						//
					} // if
						//
					return voiceAttribute;
					//
				} else if (Objects.equals(methodName, "isInstalled")) {
					//
					return isInstalled;
					//
				} // if
					//
			} else if (proxy instanceof Provider) {
				//
				if (Objects.equals(methodName, "getProviderName")) {
					//
					return providerName;
					//
				} else if (Objects.equals(methodName, "getProviderVersion")) {
					//
					return providerVersion;
					//
				} else if (Objects.equals(methodName, "getProviderPlatform")) {
					//
					return providerPlatform;
					//
				} // if
					//
			} else if (proxy instanceof ID3v1) {
				//
				if (Objects.equals(methodName, "getArtist")) {
					//
					return artist;
					//
				}
				if (Objects.equals(methodName, "getTitle")) {
					//
					return title;
					//
				} // if
					//
			} else if (proxy instanceof ListCellRenderer) {
				//
				if (Objects.equals(methodName, "getListCellRendererComponent")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof ConfigurableListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinition") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getBeanDefinitions(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof BeanDefinition) {
				//
				if (Objects.equals(methodName, "hasAttribute") && args != null && args.length > 0) {
					//
					return Util.containsKey(getBeanDefinitionAttributes(), args[0]);
					//
				} else if (Objects.equals(methodName, "getAttribute") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getBeanDefinitionAttributes(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof CTProperty) {
				//
				if (Objects.equals(methodName, "getLpwstr")) {
					//
					return lpwstr;
					//
				} // if
					//
			} else if (proxy instanceof Workbook) {
				//
				if (Objects.equals(methodName, "getNumberOfSheets")) {
					//
					return numberOfSheets;
					//
				} else if (Objects.equals(methodName, "getSheetName")) {
					//
					return sheetName;
					//
				} // if
					//
			} else if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "keySet")) {
					//
					return keySet;
					//
				} else if (Objects.equals(methodName, "entries")) {
					//
					return multiMapEntries;
					//
				} else if (Objects.equals(methodName, "get")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof NamedNodeMap) {
				//
				if (Objects.equals(methodName, "getNamedItem")) {
					//
					return namedItem;
					//
				} // if
					//
			} else if (proxy instanceof Node) {
				//
				if (Objects.equals(methodName, "getTextContent")) {
					//
					return textContent;
					//
				} else if (Objects.equals(methodName, "getNodeName")) {
					//
					return nodeName;
					//
				} else if (Objects.equals(methodName, "getParentNode")) {
					//
					return parentNode;
					//
				} else if (Objects.equals(methodName, "appendChild")) {
					//
					return appendChild;
					//
				} else if (Objects.equals(methodName, "removeChild")) {
					//
					return removeChild;
					//
				} else if (Objects.equals(methodName, "cloneNode")) {
					//
					return cloneNode;
					//
				} else if (Objects.equals(methodName, "getAttributes")) {
					//
					return attributes;
					//
				} else if (Objects.equals(methodName, "getNodeValue")) {
					//
					return nodeValue;
					//
				} // if
					//
			} else if (proxy instanceof NodeList) {
				//
				if (Objects.equals(methodName, "getLength")) {
					//
					return length;
					//
				} else if (Objects.equals(methodName, "item")) {
					//
					return item;
					//
				} // if
					//
			} else if (proxy instanceof InnerClass.InnerInterface) {
				//
				if (Objects.equals(methodName, "getDllPath")) {
					//
					return dllPath;
					//
				} // if
					//
			} else if (proxy instanceof Lookup) {
				//
				if (Objects.equals(methodName, "contains")) {
					//
					return contains;
					//
				} else if (Objects.equals(methodName, "get")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Connection) {
				//
				if (Objects.equals(methodName, "prepareStatement")) {
					//
					return preparedStatement;
					//
				} // if
					//
			} else if (proxy instanceof PreparedStatement) {
				//
				if (Objects.equals(methodName, "executeQuery")) {
					//
					return resultSet;
					//
				} // if
					//
			} else if (proxy instanceof DocumentEvent) {
				//
				if (Objects.equals(methodName, "getDocument")) {
					//
					return document;
					//
				} // if
					//
			} else if (proxy instanceof Entry) {
				//
				if (Objects.equals(methodName, "getKey")) {
					//
					return key;
					//
				} else if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} else if (proxy instanceof AttributeAccessor) {
				//
				if (Objects.equals(methodName, "hasAttribute") && args != null && args.length > 0) {
					//
					return Util.containsKey(getAttributeMap(), args[0]);
					//
				} else if (Objects.equals(methodName, "getAttribute") && args != null && args.length > 0) {
					//
					return Util.get(getAttributeMap(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof XPath) {
				//
				if (Objects.equals(methodName, "evaluate")) {
					//
					return null;
					//
				} // if
					//
			} else if (Util.isAssignableFrom(CLASS_BYTE_CONVERTER, Util.getClass(proxy))) {
				//
				if (Objects.equals(methodName, "convert")) {
					//
					return convertedByteArray;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Component && Objects.equals(methodName, "getPreferredSize")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManager instance = null;

	private ObjectMapper objectMapper = null;

	private IH ih = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private Stream<?> stream = null;

	private SpeechApi speechApi = null;

	private Sheet sheet = null;

	private BeanDefinition beanDefinition = null;

	private Multimap<?, ?> multimap = null;

	private Node node = null;

	private NodeList nodeList = null;

	private Iterable<?> iterable = null;

	private Logger logger = null;

	private VoiceMapper voiceMapper = null;

	private org.jsoup.nodes.Element element = null;

	private Workbook workbook = null;

	private Cell cell = null;

	private DocumentEvent documentEvent = null;

	private javax.swing.text.Document document = null;

	private Collection<?> collection = null;

	private OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction = null;

	private ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private RandomStringUtils randomStringUtils;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		element = new org.jsoup.nodes.Element("A");
		//
		final Constructor<VoiceManager> constructor = getDeclaredConstructor(VoiceManager.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		objectMapper = new ObjectMapper();
		//
		instance = !GraphicsEnvironment.isHeadless() ? newInstance(constructor)
				: Util.cast(VoiceManager.class, Narcissus.allocateInstance(VoiceManager.class));
		//
		sqlSessionFactory = Reflection.newProxy(SqlSessionFactory.class, ih = new IH());
		//
		stream = Reflection.newProxy(Stream.class, ih);
		//
		speechApi = Reflection.newProxy(SpeechApi.class, ih);
		//
		sheet = Reflection.newProxy(Sheet.class, ih);
		//
		beanDefinition = Reflection.newProxy(BeanDefinition.class, ih);
		//
		multimap = Reflection.newProxy(Multimap.class, ih);
		//
		node = Reflection.newProxy(Node.class, ih);
		//
		nodeList = Reflection.newProxy(NodeList.class, ih);
		//
		iterable = Reflection.newProxy(Iterable.class, ih);
		//
		logger = Reflection.newProxy(Logger.class, ih);
		//
		voiceMapper = Reflection.newProxy(VoiceMapper.class, ih);
		//
		workbook = Reflection.newProxy(Workbook.class, ih);
		//
		cell = Reflection.newProxy(Cell.class, ih);
		//
		documentEvent = Reflection.newProxy(DocumentEvent.class, ih);
		//
		document = Reflection.newProxy(javax.swing.text.Document.class, ih);
		//
		collection = Reflection.newProxy(Collection.class, ih);
		//
		onlineNHKJapanesePronunciationsAccentFailableFunction = Reflection
				.newProxy(OnlineNHKJapanesePronunciationsAccentFailableFunction.class, ih);
		//
		configurableListableBeanFactory = Reflection.newProxy(ConfigurableListableBeanFactory.class, ih);
		//
		randomStringUtils = RandomStringUtils.secureStrong();
		//
	}

	@AfterEach
	void afterEach() {
		//
		final File file = new File(".html");
		//
		if (file.exists() && file.isFile() && file.length() == 0) {
			//
			if (logger != null) {
				//
				logger.info("file to be deleted={}", file.getAbsolutePath());
				//
			} // if
				//
			file.delete();
			//
		} // if
			//
	}

	@Test
	void testSetFreeMarkerVersion() throws NoSuchFieldException, IllegalAccessException, IOException {
		//
		final Field freeMarkerVersion = VoiceManager.class.getDeclaredField("freeMarkerVersion");
		//
		if (freeMarkerVersion != null) {
			//
			freeMarkerVersion.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(null));
		//
		Assertions.assertNull(get(freeMarkerVersion, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(ONE));
		//
		Assertions.assertEquals(new Version(0, 0, ONE), get(freeMarkerVersion, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(EMPTY));
		//
		Assertions.assertEquals(new Version(EMPTY), get(freeMarkerVersion, instance));
		//
		final Version version = freemarker.template.Configuration.getVersion();
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(version));
		//
		Assertions.assertSame(version, get(freeMarkerVersion, instance));
		//
		set(freeMarkerVersion, instance, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> instance.setFreeMarkerVersion(new int[] {}));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(new int[] { ONE, TWO, THREE }));
		//
		Assertions.assertEquals(new Version(ONE, TWO, THREE), get(freeMarkerVersion, instance));
		//
	}

	@Test
	void testSetMicrosoftAccessFileFormat() throws Exception {
		//
		final Field microsoftAccessFileFormat = VoiceManager.class.getDeclaredField("microsoftAccessFileFormat");
		//
		if (microsoftAccessFileFormat != null) {
			//
			microsoftAccessFileFormat.setAccessible(true);
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setMicrosoftAccessFileFormat(null);
			//
		} // if
			//
		Assertions.assertNull(get(microsoftAccessFileFormat, instance));
		//
		if (instance != null) {
			//
			instance.setMicrosoftAccessFileFormat("V1");
			//
		} // if
			//
		Assertions.assertEquals(FileFormat.V1997, get(microsoftAccessFileFormat, instance));
		//
		if (instance != null) {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
					() -> instance.setMicrosoftAccessFileFormat("V2"));
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setMicrosoftAccessFileFormat(".mn");
			//
		} // if
			//
		Assertions.assertEquals(FileFormat.MSISAM, get(microsoftAccessFileFormat, instance));
		//
		// com.healthmarketscience.jackcess.Database.FileFormat
		//
		if (instance != null) {
			//
			instance.setMicrosoftAccessFileFormat(FileFormat.GENERIC_JET4);
			//
		} // if
			//
		Assertions.assertEquals(FileFormat.GENERIC_JET4, get(microsoftAccessFileFormat, instance));
		//
	}

	@Test
	void testSetPresentationSlideDuration() throws NoSuchFieldException, IllegalAccessException, IOException {
		//
		final Field presentationSlideDuration = VoiceManager.class.getDeclaredField("presentationSlideDuration");
		//
		if (presentationSlideDuration != null) {
			//
			presentationSlideDuration.setAccessible(true);
			//
		} // if
			//
			// java.lang.CharSequence
			//
		AssertionsUtil.assertThrowsAndEquals(DateTimeParseException.class,
				"{parsedString=A, localizedMessage=Text cannot be parsed to a Duration, errorIndex=0, message=Text cannot be parsed to a Duration}",
				() -> setPresentationSlideDuration(instance, "A"));
		//
		Assertions.assertDoesNotThrow(() -> setPresentationSlideDuration(instance, Integer.toString(ONE)));
		//
		Assertions.assertEquals(Duration.ofMillis(ONE), get(presentationSlideDuration, instance));
		//
		Assertions.assertDoesNotThrow(() -> setPresentationSlideDuration(instance, EMPTY));
		//
		Assertions.assertNull(get(presentationSlideDuration, instance));
		//
		final String string = String.format("PT%1$sS", ONE);
		//
		Assertions.assertDoesNotThrow(() -> setPresentationSlideDuration(instance, string));
		//
		final Duration duration = Duration.ofSeconds(ONE);
		//
		Assertions.assertEquals(duration, get(presentationSlideDuration, instance));
		//
		// java.time.Duration
		//
		Assertions.assertDoesNotThrow(() -> setPresentationSlideDuration(instance, duration));
		//
		Assertions.assertSame(duration, get(presentationSlideDuration, instance));
		//
		// char[]
		//
		Assertions.assertDoesNotThrow(() -> setPresentationSlideDuration(instance, new char[] {}));
		//
		Assertions.assertNull(get(presentationSlideDuration, instance));
		//
		Assertions.assertDoesNotThrow(
				() -> setPresentationSlideDuration(instance, string != null ? string.toCharArray() : null));
		//
		Assertions.assertEquals(duration, get(presentationSlideDuration, instance));
		//
		// java.lang.Object
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=class java.lang.Object, message=class java.lang.Object}",
				() -> setPresentationSlideDuration(instance, new Object()));
		//
	}

	private static void setPresentationSlideDuration(final VoiceManager instance, final Object object) {
		if (instance != null) {
			instance.setPresentationSlideDuration(object);
		}
	}

	@Test
	void testGetMimeTypeAndBase64EncodedString() throws IOException {
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(null, null));
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(EMPTY, null));
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(EMPTY, EMPTY));
		//
	}

	@Test
	void testPostProcessBeanFactory() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		final MutableComboBoxModel<?> mcbm = Reflection.newProxy(MutableComboBoxModel.class, ih);
		//
		FieldUtils.writeDeclaredField(instance, "cbmAudioFormatWrite", mcbm, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		FieldUtils.writeDeclaredField(instance, "cbmAudioFormatExecute", mcbm, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		ih.beansOfType = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		ih.entrySet = Collections.singleton(null);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		ih.beansOfType = Collections.singletonMap(null, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		Assertions.assertDoesNotThrow(() -> Util.put(ih.getBeanDefinitions(), null, beanDefinition));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		Assertions.assertDoesNotThrow(() -> Util.put(ih.getBeanDefinitions(), "format", null));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
	}

	@Test
	void testSetOutputFolderFileNameExpressions() throws Throwable {
		//
		final Field outputFolderFileNameExpressions = getDeclaredField(VoiceManager.class,
				"outputFolderFileNameExpressions");
		//
		if (outputFolderFileNameExpressions != null) {
			outputFolderFileNameExpressions.setAccessible(true);
		} // if
			//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(null));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(EMPTY));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		final Map<?, ?> emptyMap = Collections.emptyMap();
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(map));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		ih.entrySet = Collections.singleton(null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(map));
		//
		Assertions.assertEquals(emptyMap, get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.setOutputFolderFileNameExpressions(Collections.singletonMap(null, null)));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.setOutputFolderFileNameExpressions(Collections.singletonMap(null, null)));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions("{}"));
		//
		Assertions.assertEquals(emptyMap, get(outputFolderFileNameExpressions, instance));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=class java.lang.Integer, message=class java.lang.Integer}",
				() -> instance.setOutputFolderFileNameExpressions(Integer.toString(ONE)));
		//
	}

	@Test
	void testAfterPropertiesSet() throws IOException, IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (headless) {
			//
			Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=cannot add to layout: unknown constraint: wrap, message=cannot add to layout: unknown constraint: wrap}",
					() -> instance.afterPropertiesSet());
			//
		} // if
			//
		instance.setSpeechApi(speechApi);
		//
		ih.voiceIds = new String[] {};
		//
		ih.isInstalled = Boolean.FALSE;
		//
		if (headless) {
			//
			Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=cannot add to layout: unknown constraint: wrap, message=cannot add to layout: unknown constraint: wrap}",
					() -> instance.afterPropertiesSet());
			//
		} // if
			//
		ih.isInstalled = Boolean.TRUE;
		//
		final Class<?> clz = getClass();
		//
		final URL url = getResource(clz, "/help.html.ftl");
		//
		UnknownHostException uhe = null;
		//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			final freemarker.template.Configuration configuration = new freemarker.template.Configuration(
					freemarker.template.Configuration.getVersion());
			//
			final TemplateLoader tl = new ClassTemplateLoader(clz, "/");
			//
			tl.findTemplateSource("/help.html.ftl");
			//
			configuration.setTemplateLoader(tl);
			//
			final Template template = configuration.getTemplate("/help.html.ftl");
			//
			final Iterable<String> urls = new FailableStream<>(Util.filter(
					Stream.of(Util.cast(Object[].class,
							FieldUtils.readField(FieldUtils.readDeclaredField(template, "rootElement", true),
									"childBuffer", true))),
					x -> Objects.equals(Util.getName(Util.getClass(x)), "freemarker.core.Assignment"))).map(x -> {
						//
						final URI uri = new URI(Util.toString(FieldUtils
								.readDeclaredField(FieldUtils.readDeclaredField(x, "valueExp", true), "value", true)));
						//
						return StringUtils.join("", uri.getScheme(), "://", uri.getHost());
						//
					}).collect(Collectors.toSet());
			//
			if (urls != null && urls.iterator() != null) {
				//
				for (final String u : urls) {
					//
					try (final InputStream is = new URL(u).openStream()) {
						//
						IOUtils.toByteArray(is);
						//
					} catch (final UnknownHostException e) {
						//
						uhe = e;
						//
					} // try
						//
				} // for
					//
			} // if
				//
			if (uhe != null) {
				//
				Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
				//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
		instance.setGaKuNenBeTsuKanJiListPageUrl(EMPTY);
		//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			if (uhe != null) {
				//
				Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
				//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
		instance.setGaKuNenBeTsuKanJiListPageUrl(SPACE);
		//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			if (uhe != null) {
				//
				Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
				//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
	}

	private static URL getResource(final Class<?> instance, final String name) {
		return instance != null ? instance.getResource(name) : null;
	}

	@Test
	void testSetMp3Tags() throws Throwable {
		//
		final Field mp3Tags = getDeclaredField(VoiceManager.class, "mp3Tags");
		//
		if (mp3Tags != null) {
			//
			mp3Tags.setAccessible(true);
			//
		} // if
			//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(null));
		//
		Assertions.assertNull(get(mp3Tags, instance));
		//
		set(mp3Tags, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(iterable));
		//
		Assertions.assertNull(get(mp3Tags, instance));
		//
		set(mp3Tags, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(Collections.emptySet()));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] {}, get(mp3Tags, instance)));
		//
		set(mp3Tags, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(EMPTY));
		//
		Assertions.assertNull(get(mp3Tags, instance));
		//
		set(mp3Tags, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(Integer.toString(ONE)));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { Integer.toString(ONE) }, get(mp3Tags, instance)));
		//
		set(mp3Tags, instance, null);
		//
		final boolean b = true;
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(Boolean.toString(b)));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { Boolean.toString(b) }, get(mp3Tags, instance)));
		//
		set(mp3Tags, instance, null);
		//
		final String string = StringUtils.wrap(EMPTY, '"');
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(string));
		//
		Assertions.assertNull(get(mp3Tags, instance));
		//
		set(mp3Tags, instance, null);
		//
		final String json = ObjectMapperUtil.writeValueAsString(objectMapper, Collections.singleton(EMPTY));
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(json));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { EMPTY }, get(mp3Tags, instance)));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=class java.util.LinkedHashMap, message=class java.util.LinkedHashMap}",
				() -> instance.setMp3Tags("{}"));
		//
	}

	@Test
	void testSetMicrosoftSpeechObjectLibraryAttributeNames() {
		//
		if (instance != null) {
			//
			Assertions.assertDoesNotThrow(() -> instance.setMicrosoftSpeechObjectLibraryAttributeNames(null));
			//
		} // if
			//
	}

	@Test
	void testSetWorkbookClass() throws Throwable {
		//
		final Field workbookClass = VoiceManager.class.getDeclaredField("workbookClass");
		//
		if (workbookClass != null) {
			//
			workbookClass.setAccessible(true);
			//
		} // if
			//
		if (instance != null) {
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass(null));
			//
			Assertions.assertNull(get(workbookClass, instance));
			//
			// java.lang.Class
			//
			final Class<?> clz = Class.class;
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass(clz));
			//
			Assertions.assertSame(clz, get(workbookClass, instance));
			//
			// java.lang.String
			//
			final String toString = Util.getName(String.class);
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass(toString));
			//
			Assertions.assertSame(Util.forName(toString), get(workbookClass, instance));
			//
			// org.apache.poi.hssf.usermodel.HSSFWorkbook
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass("HSSFWorkbook"));
			//
			Assertions.assertSame(HSSFWorkbook.class, get(workbookClass, instance));
			//
			// xls
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass("xls"));
			//
			Assertions.assertSame(HSSFWorkbook.class, get(workbookClass, instance));
			//
			// xlsx
			//
			Assertions.assertDoesNotThrow(() -> instance.setWorkbookClass("xlsx"));
			//
			Assertions.assertSame(XSSFWorkbook.class, get(workbookClass, instance));
			//
		} // if
			//
	}

	@Test
	void testSetExportWebSpeechSynthesisHtmlTemplateProperties()
			throws NoSuchFieldException, IllegalAccessException, IOException {
		//
		Field exportWebSpeechSynthesisHtmlTemplateProperties = VoiceManager.class
				.getDeclaredField("exportWebSpeechSynthesisHtmlTemplateProperties");
		//
		if (exportWebSpeechSynthesisHtmlTemplateProperties != null) {
			//
			exportWebSpeechSynthesisHtmlTemplateProperties.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(null));
		//
		Assertions.assertNull(get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(EMPTY));
		//
		Assertions.assertNull(get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		final Integer integer = Integer.valueOf(ZERO);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(integer));
		//
		final Date date = new Date();
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(date));
		//
		final Calendar calendar = Calendar.getInstance();
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(calendar));
		//
		// java.util.Map
		//
		final Map<?, ?> map1 = Collections.emptyMap();
		//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(map1));
		//
		Assertions.assertEquals(map1, get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		final Map<?, ?> map2 = Collections.singletonMap(null, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(map2));
		//
		Assertions.assertEquals(map2, get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		set(exportWebSpeechSynthesisHtmlTemplateProperties, instance, null);
		//
		final Map<?, ?> map3 = Reflection.newProxy(Map.class, ih);
		//
		if (ih != null) {
			//
			ih.isEmpty = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(map3));
		//
		Assertions.assertNull(get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(map3));
		//
		Assertions.assertNull(get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
		set(exportWebSpeechSynthesisHtmlTemplateProperties, instance, null);
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setExportWebSpeechSynthesisHtmlTemplateProperties(map3));
		//
		Assertions.assertNull(get(exportWebSpeechSynthesisHtmlTemplateProperties, instance));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void set(final Field field, final Object instance, final Object value)
			throws IllegalAccessException {
		if (field != null) {
			field.set(instance, value);
		}
	}

	@Test
	void testActionPerformed1() throws Throwable {
		//
		final Class<?> clz = Util.getClass(instance != null ? instance.getToolkit() : null);
		//
		final Class<? extends Throwable> throwableClassByGetSystemClipboard = getThrowingThrowableClass(clz,
				clz != null ? clz.getDeclaredMethod("getSystemClipboard") : null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		final AbstractButton btnConvertToRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToRomaji", btnConvertToRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnConvertToRomaji, 0, null)));
		//
		final AbstractButton btnConvertToKatakana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToKatakana", btnConvertToKatakana, true);
			//
		} // if
			//
		ActionEvent actionEventBtnConvertToKatakana = new ActionEvent(btnConvertToKatakana, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToKatakana));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfHiragana", new JTextField(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToKatakana));
		//
		// btnExport
		//
		final AbstractButton btnExport = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExport", btnExport, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnExport = new ActionEvent(btnExport, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		if (instance != null) {
			//
			instance.setSqlSessionFactory(sqlSessionFactory);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		final AbstractButton cbExportHtml = new JCheckBox();
		//
		cbExportHtml.setSelected(true);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbExportHtml", cbExportHtml, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		final AbstractButton cbExportListHtml = new JCheckBox();
		//
		cbExportListHtml.setSelected(true);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbExportListHtml", cbExportListHtml, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		final AbstractButton cbExportHtmlAsZip = new JCheckBox();
		//
		cbExportHtmlAsZip.setSelected(true);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbExportHtmlAsZip", cbExportHtmlAsZip, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		final AbstractButton cbExportMicrosoftAccess = new JCheckBox();
		//
		cbExportMicrosoftAccess.setSelected(true);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbExportMicrosoftAccess", cbExportMicrosoftAccess, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		// btnSpeak
		//
		final AbstractButton btnSpeak = new JButton();
		//
		final ActionEvent actionEventBtnSpeak = new ActionEvent(btnSpeak, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechVolume", new JSlider(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		final JTextComponent tfSpeechRate = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfSpeechRate", tfSpeechRate, true);
			//
		} // if
			//
		tfSpeechRate.setText("MAX_VALUE");
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		// btnSpeechRateSlower
		//
		final AbstractButton btnSpeechRateSlower = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateSlower = new ActionEvent(btnSpeechRateSlower, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateSlower));
		//
		final JSlider jsSpeechRate = new JSlider();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", jsSpeechRate, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateSlower));
		//
		// btnSpeechRateNormal
		//
		final AbstractButton btnSpeechRateNormal = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateNormal = new ActionEvent(btnSpeechRateNormal, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateNormal));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", null, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateNormal));
		//
		// btnSpeechRateFaster
		//
		final AbstractButton btnSpeechRateFaster = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateFaster = new ActionEvent(btnSpeechRateFaster, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateFaster));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", jsSpeechRate, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateFaster));
		//
	}

	private static Class<? extends Throwable> getThrowingThrowableClass(final Class<?> clz, final Method method)
			throws Throwable {
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(javaClass, method);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			ObjectType objectType = null;
			//
			String className = null;
			//
			if (!Objects.equals(
					FailableStreamUtil.map(
							new FailableStream<>(testAndApply(Objects::nonNull, instructions, Arrays::stream, null)),
							x -> Util.getClass(x)).collect(Collectors.toList()),
					Arrays.asList(NEW.class, DUP.class, INVOKESPECIAL.class, ATHROW.class))) {
				//
				return null;
				//
			} // if
				//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instructions[i]) instanceof NEW _new) {
					//
					className = (objectType = _new != null
							? _new.getLoadClassType(new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m)))
							: null) != null ? objectType.getClassName() : null;
					//
				} // if
					//
			} // for
				//
			final Class<?> classTemp = Util.forName(className);
			//
			return Util.isAssignableFrom(Throwable.class, classTemp) ? (Class<? extends Throwable>) classTemp : null;
			//
		} // try
			//
	}

	@Test
	void testActionPerformed2() throws Throwable {
		//
		// btnExportMicrosoftSpeechObjectLibraryInformation
		//
		final AbstractButton btnExportMicrosoftSpeechObjectLibraryInformation = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExportMicrosoftSpeechObjectLibraryInformation",
					btnExportMicrosoftSpeechObjectLibraryInformation, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance,
				new ActionEvent(btnExportMicrosoftSpeechObjectLibraryInformation, 0, null)));
		//
		// btnImport
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (headless) {
			//
			final AbstractButton btnImport = new JButton();
			//
			if (instance != null) {
				//
				FieldUtils.writeDeclaredField(instance, "btnImport", btnImport, true);
				//
			} // if
				//
				//
			Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnImport, 0, null)));
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance != null ? instance.getToolkit() : null);
		//
		final Class<? extends Throwable> throwableClassByGetSystemClipboard = getThrowingThrowableClass(clz,
				clz != null ? clz.getDeclaredMethod("getSystemClipboard") : null);
		//
		// btnExportBrowse
		//
		final AbstractButton btnExportBrowse = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExportBrowse", btnExportBrowse, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnExportBrowse, 0, null)));
		//
		// btnPronunciationPageUrlCheck
		//
		final AbstractButton btnPronunciationPageUrlCheck = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnPronunciationPageUrlCheck", btnPronunciationPageUrlCheck, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnPronunciationPageUrlCheck = new ActionEvent(btnPronunciationPageUrlCheck, 0,
				null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnPronunciationPageUrlCheck));
		//
		final JTextComponent tfPronunciationPageUrl = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfPronunciationPageUrl", tfPronunciationPageUrl, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> Util.setText(tfPronunciationPageUrl, SPACE));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnPronunciationPageUrlCheck));
		//
		Assertions.assertDoesNotThrow(() -> Util.setText(tfPronunciationPageUrl, "a"));
		//
		if (headless) {
			//
			Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnPronunciationPageUrlCheck));
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(RuntimeException.class,
					"{localizedMessage=org.opentest4j.AssertionFailedError: URI is not absolute ==> Unexpected exception thrown: java.lang.IllegalArgumentException: URI is not absolute, message=org.opentest4j.AssertionFailedError: URI is not absolute ==> Unexpected exception thrown: java.lang.IllegalArgumentException: URI is not absolute}",
					() -> actionPerformed(instance, actionEventBtnPronunciationPageUrlCheck));
			//
		} // if
			//
			// btnExecute
			//
		final AbstractButton btnExecute = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExecute", btnExecute, true);
			//
			final AbstractButton cbUseTtsVoice = new JCheckBox();
			//
			cbUseTtsVoice.setSelected(true);
			//
			FieldUtils.writeDeclaredField(instance, "cbUseTtsVoice", cbUseTtsVoice, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnExecute = new ActionEvent(btnExecute, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExecute));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", speechApi, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExecute));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "voiceIds", new String[] { Integer.toString(ZERO) }, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExecute));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmVoiceId", new DefaultComboBoxModel<>(new Object[] { ZERO }),
					true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExecute));
		//
		if (instance != null) {
			//
			instance.setImageFormat(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExecute));
		//
		// btnIpaSymbol
		//
		final AbstractButton btnIpaSymbol = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnIpaSymbol", btnIpaSymbol, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnIpaSymbol, 0, null)));
		//
		// btnCheckPronounication
		//
		final AbstractButton btnCheckPronunciation = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCheckPronunciation", btnCheckPronunciation, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnCheckPronunciation = new ActionEvent(btnCheckPronunciation, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnCheckPronunciation));
		//
		// mcbmPronunciation
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "mcbmPronunciation",
					new DefaultComboBoxModel<>(new Object[] { null }), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnCheckPronunciation));
		//
		// jcbPronunciation
		//
		final JComboBox<?> jcbPronunciation = new JComboBox();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jcbPronunciation", jcbPronunciation, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(jcbPronunciation, 0, null)));
		//
		// btnPlayPronunciationAudio
		//
		final AbstractButton btnPlayPronunciationAudio = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnPlayPronunciationAudio", btnPlayPronunciationAudio, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> actionPerformed(instance, new ActionEvent(btnPlayPronunciationAudio, 0, null)));
		//
	}

	@Test
	void testActionPerformed3() throws Throwable {
		//
		// btnConvertToHiragana
		//
		final AbstractButton btnConvertToHiraganaOrKatakana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToHiraganaOrKatakana", btnConvertToHiraganaOrKatakana,
					true);
			//
		} // if
			//
		final ActionEvent actionEventBtnConvertToHiraganaOrKatakana = new ActionEvent(btnConvertToHiraganaOrKatakana, 0,
				null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToHiraganaOrKatakana));
		//
		// org.springframework.context.support.VoiceManager.multimaps
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "multimapHiragana", Reflection.newProxy(Collection.class, ih),
					true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToHiraganaOrKatakana));
		//
		final List<Multimap<?, ?>> multimaps = new ArrayList<>(Collections.singleton(null));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "multimapHiragana", multimaps, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToHiraganaOrKatakana));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextImport", new JTextField(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToHiraganaOrKatakana));
		//
		multimaps.set(0, ImmutableMultimap.of(EMPTY, EMPTY));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToHiraganaOrKatakana));
		//
	}

	@Test
	void testActionPerformed4() throws Throwable {
		//
		final Class<?> clz = Util.getClass(instance != null ? instance.getToolkit() : null);
		//
		final Class<? extends Throwable> throwableClassByGetSystemClipboard = getThrowingThrowableClass(clz,
				clz != null ? clz.getDeclaredMethod("getSystemClipboard") : null);
		//
		// btnCopyRomaji
		//
		final AbstractButton btnCopyRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyRomaji", btnCopyRomaji, true);
			//
		} // if
			//
		Executable executable = () -> actionPerformed(instance, new ActionEvent(btnCopyRomaji, 0, null));
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertDoesNotThrow(executable);
			//
		} // if
			//
			// btnCopyKatakana
			//
		final AbstractButton btnCopyKatakana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyKatakana", btnCopyKatakana, true);
			//
		} // if
			//
		executable = () -> actionPerformed(instance, new ActionEvent(btnCopyKatakana, 0, null));
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertDoesNotThrow(executable);
			//
		} // if
			//
			// btnDllPathCopy
			//
		final AbstractButton btnDllPathCopy = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnDllPathCopy", btnDllPathCopy, true);
			//
		} // if
			//
		executable = () -> actionPerformed(instance, new ActionEvent(btnDllPathCopy, 0, null));
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertDoesNotThrow(executable);
			//
		} // if
			//
			// btnExportCopy
			//
		final AbstractButton btnExportCopy = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExportCopy", btnExportCopy, true);
			//
		} // if
			//
		executable = () -> actionPerformed(instance, new ActionEvent(btnExportCopy, 0, null));
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertDoesNotThrow(executable);
			//
		} // if
			//
		final AbstractButton btnCopyHiragana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyHiragana", btnCopyHiragana, true);
			//
		} // if
			//
		executable = () -> actionPerformed(instance, new ActionEvent(btnCopyHiragana, 0, null));
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertDoesNotThrow(executable);
			//
		} // if
			//
	}

	private static Object getHeadlessMessage() throws NoSuchMethodException {
		return Narcissus.invokeStaticMethod(GraphicsEnvironment.class.getDeclaredMethod("getHeadlessMessage"));
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent actionEvent) {
		if (instance != null) {
			instance.actionPerformed(actionEvent);
		} // if
	}

	@Test
	void testItemStateChanged() throws IllegalAccessException {
		//
		if (instance != null) {
			//
			Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(null));
			//
		} // if
			//
		final ItemSelectable itemSelectable = Reflection.newProxy(ItemSelectable.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, new ItemEvent(itemSelectable, 0, "", 0)));
		//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		ih.voiceAttribute = Integer.toString(LocaleID.AF.getLcid(), 16);
		//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, null));
		//
		// org.springframework.context.support.VoiceManager.jcbJlptVocabulary
		//
		final JComboBox<Object> jcbJlptVocabulary = new JComboBox<>();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jcbJlptVocabulary", jcbJlptVocabulary, true);
			//
		} // if
			//
		final ItemEvent itemEvent = new ItemEvent(jcbJlptVocabulary, ZERO, null, ZERO);
		//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, itemEvent));
		//
		jcbJlptVocabulary.addItem(new JlptVocabulary());
		//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, itemEvent));
		//
	}

	@Test
	void testSetjSoupParseTimeout() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field jSoupParseTimeout = VoiceManager.class.getDeclaredField("jSoupParseTimeout");
		//
		if (jSoupParseTimeout != null) {
			//
			jSoupParseTimeout.setAccessible(true);
			//
		} // if
			//
		if (instance != null) {
			//
			// null
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout(null));
			//
			Assertions.assertNull(get(jSoupParseTimeout, instance));
			//
			// java.time.Duration
			//
			final Duration duration = Duration.ZERO;
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout(duration));
			//
			Assertions.assertSame(duration, get(jSoupParseTimeout, instance));
			//
			// java.lang.Number as java.lang.String
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout(Integer.toString(ZERO)));
			//
			Assertions.assertSame(duration, get(jSoupParseTimeout, instance));
			//
			// java.lang.String
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout(EMPTY));
			//
			Assertions.assertNull(get(jSoupParseTimeout, instance));
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout(SPACE));
			//
			Assertions.assertNull(get(jSoupParseTimeout, instance));
			//
			Assertions.assertDoesNotThrow(() -> instance.setjSoupParseTimeout("PT0S"));
			//
			Assertions.assertSame(duration, get(jSoupParseTimeout, instance));
			//
		} // if
			//
	}

	private static void itemStateChanged(final ItemListener instance, final ItemEvent itemEvent) {
		if (instance != null) {
			instance.itemStateChanged(itemEvent);
		}
	}

	@Test
	void testStateChanged() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> stateChanged(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> stateChanged(instance, new ChangeEvent("")));
		//
		final JSlider jsSpeechRate = new JSlider();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", jsSpeechRate, true);
			//
		} // if
			//
		final ChangeEvent changeEvent = new ChangeEvent(jsSpeechRate);
		//
		Assertions.assertDoesNotThrow(() -> stateChanged(instance, changeEvent));
		//
		jsSpeechRate.setValue(jsSpeechRate.getMinimum());
		//
		Assertions.assertDoesNotThrow(() -> stateChanged(instance, changeEvent));
		//
		jsSpeechRate.setValue(jsSpeechRate.getMaximum());
		//
		Assertions.assertDoesNotThrow(() -> stateChanged(instance, changeEvent));
		//
	}

	private static void stateChanged(final ChangeListener instance, final ChangeEvent evt) {
		if (instance != null) {
			instance.stateChanged(evt);
		}
	}

	@Test
	void testKeyTyped() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyTyped(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyPressed() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyPressed(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyReleased() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, null));
		//
		final JLabel jLabel = new JLabel();
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, new KeyEvent(jLabel, 0, 0, 0, 0, ' ')));
		//
		final JTextComponent tfListNames = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfListNames", tfListNames, true);
			//
			FieldUtils.writeDeclaredField(instance, "jlListNames", jLabel, true);
			//
		} // if
			//
		final KeyEvent keyEventTfListNames = new KeyEvent(tfListNames, 0, 0, 0, 0, ' ');
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfListNames));
		//
		Util.setText(tfListNames, "{}");
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfListNames));
		//
		// tfTextImport
		//
		final JTextComponent tfTextImport = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextImport", tfTextImport, true);
			//
		} // if
			//
		final KeyEvent keyEventTfTextImport = new KeyEvent(tfTextImport, 0, 0, 0, 0, ' ');
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfTextImport));
		//
		Util.setText(tfTextImport, SPACE);
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfTextImport));
		//
		// jouYouKanJiList
		//
		final Field jouYouKanJiList = getDeclaredField(Util.getClass(instance), "jouYouKanJiList");
		//
		if (jouYouKanJiList != null) {
			//
			jouYouKanJiList.setAccessible(true);
			//
		} // if
			//
		set(jouYouKanJiList, instance, Unit.with(Collections.singletonList(null)));
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfTextImport));
		//
		set(jouYouKanJiList, instance, Unit.with(Collections.singletonList(SPACE)));
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfTextImport));
		//
	}

	private static void keyReleased(final KeyListener instance, final KeyEvent keyEvent) {
		if (instance != null) {
			instance.keyReleased(keyEvent);
		}
	}

	@Test
	void testInsertUpdate() {
		//
		Assertions.assertDoesNotThrow(() -> insertUpdate(instance, null));
		//
		if (ih != null) {
			//
			ih.document = document;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> insertUpdate(instance, documentEvent));
		//
	}

	private static void insertUpdate(final DocumentListener instance, final DocumentEvent evt) {
		if (instance != null) {
			instance.insertUpdate(evt);
		}
	}

	@Test
	void testRemoveUpdate() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		if (ih != null) {
			//
			ih.document = document;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		// org.springframework.context.support.VoiceManager.tfTextImport
		//
		final String A = "A";
		//
		final JTextComponent tfTextImport = new JTextField(A);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextImport", tfTextImport, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		// org.springframework.context.support.VoiceManager.mcbmJlptVocabulary
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "mcbmJlptVocabulary", new DefaultComboBoxModel<>(), true);
			//
		} // if
			//
			// org.springframework.context.support.VoiceManager.jlptVocabularyList
			//
		final Field jlptVocabularyList = VoiceManager.class.getDeclaredField("jlptVocabularyList");
		//
		if (jlptVocabularyList != null) {
			//
			jlptVocabularyList.setAccessible(true);
			//
		} // if
			//
		set(jlptVocabularyList, instance, Unit.with(Collections.singletonList(null)));
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		final JlptVocabulary jv = new JlptVocabulary();
		//
		final List<JlptVocabulary> jvs = Collections.nCopies(TWO, jv);
		//
		set(jlptVocabularyList, instance, Unit.with(jvs));
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		// domain.JlptVocabulary.kana
		//
		FieldUtils.writeDeclaredField(jv, "kana", A, true);
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		// domain.JlptVocabulary.kanji
		//
		FieldUtils.writeDeclaredField(jv, "kanji", A, true);
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		// org.springframework.context.support.VoiceManager.cbmJlptLevel
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmJlptLevel", new DefaultComboBoxModel<>(new String[] { null }),
					true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		Util.setText(tfTextImport, "B");
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		Util.setText(tfTextImport, A);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmJlptLevel",
					new DefaultComboBoxModel<>(new String[] { null, null }), true);
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}", () -> removeUpdate(instance, null));
		//
	}

	private static void removeUpdate(final DocumentListener instance, final DocumentEvent evt) {
		if (instance != null) {
			instance.removeUpdate(evt);
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> setContents(new Clipboard(null), null, null));
		//
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner)
			throws Throwable {
		try {
			METHOD_SET_CONTENTS.invoke(null, instance, contents, owner);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFileExtension() throws Throwable {
		//
		Assertions.assertNull(getFileExtension((FileFormat) null));
		//
		Assertions.assertNull(getFileExtension(new ContentInfo(null, null, EMPTY, false)));
		//
		Assertions.assertEquals("mp3", getFileExtension(new ContentInfo(null, null, "MPEG ADTS, layer III.", false)));
		//
		final String name = "wav";
		//
		Assertions.assertEquals(name, getFileExtension(new ContentInfo(name, null, null, false)));
		//
		Assertions.assertEquals("aac", getFileExtension(new ContentInfo(null, "audio/x-hx-aac-adts", null, false)));
		//
		// org.springframework.context.support.VoiceManagerTest.getFileExtension(org.apache.commons.lang3.function.FailableSupplier)
		//
		Assertions.assertEquals("xls", getFileExtension(HSSFWorkbook::new));
		//
	}

	private static String getFileExtension(final ContentInfo ci) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION_CONTENT_INFO.invoke(null, ci);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getFileExtension(final FileFormat instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION_FILE_FORMAT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getFileExtension(final FailableSupplier<Workbook, RuntimeException> supplier)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION_FAILABLE_SUPPLIER.invoke(null, supplier);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMapper() throws Throwable {
		//
		Assertions.assertNull(getMapper(null, null, null));
		//
		Assertions.assertNull(
				getMapper(Util.cast(Configuration.class, Narcissus.allocateInstance(Configuration.class)), null, null));
		//
		Assertions.assertNull(getMapper(new Configuration(), null, null));
		//
	}

	private static <T> T getMapper(final Configuration instance, final Class<T> type, final SqlSession sqlSession)
			throws Throwable {
		try {
			return (T) METHOD_GET_MAPPER.invoke(null, instance, type, sqlSession);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInsertOrUpdate() {
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(null, null));
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, null));
		//
		final Voice voice = new Voice();
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
		ih.voice = new Voice();
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, null));
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
		voice.setListNames(Reflection.newProxy(Iterable.class, ih));
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
		voice.setListNames(Collections.singleton(null));
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
		ih.voiceList = new VoiceList();
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
	}

	private static void insertOrUpdate(final VoiceMapper instance, final Voice voice) throws Throwable {
		try {
			METHOD_INSERT_OR_UPDATE.invoke(null, instance, voice);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetEnabled() {
		//
		Assertions.assertDoesNotThrow((() -> setEnabled(null, false)));
		//
		Assertions.assertDoesNotThrow((() -> setEnabled(false, null, (Component[]) null)));
		//
	}

	private static void setEnabled(final Component instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_ENABLED_2.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setEnabled(final boolean b, final Component instance, final Component... cs) throws Throwable {
		try {
			METHOD_SET_ENABLED_3.invoke(null, b, instance, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, x -> x));
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, x -> x));
		//
		Assertions.assertNull(testAndApply((a, b) -> false, null, null, null, null));
		//
		Assertions.assertNull(testAndApply((a, b) -> true, null, null, (a, b) -> null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		Assertions.assertEquals(ZERO, intValue(null, ZERO));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLongValue() throws Throwable {
		//
		Assertions.assertEquals(ZERO, longValue(null, ZERO));
		//
	}

	private static long longValue(final Number instance, final long defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_LONG_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Long) {
				return ((Long) obj).longValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProperty() throws Throwable {
		//
		Assertions.assertNull(getProperty(null, null));
		//
	}

	private static String getProperty(final CustomProperties instance, final String key) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY_CUSTOM_PROPERTIES.invoke(null, instance, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null, null));
		//
		Assertions.assertNull(getValue(Reflection.newProxy(Expression.class, ih), null));
		//
	}

	private static Object getValue(final Expression instance, final EvaluationContext evaluationContext)
			throws Throwable {
		try {
			return METHOD_GET_VALUE.invoke(null, instance, evaluationContext);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSource() throws Throwable {
		//
		Assertions.assertNull(getSource((Voice) null));
		//
		Assertions.assertNull(getSource(new Voice()));
		//
	}

	private static String getSource(final Voice instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SOURCE_VOICE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExport() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> export(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(Collections.singletonList(null), null, null));
		//
		final Voice voice = new Voice();
		//
		final List<Voice> voices = Collections.singletonList(voice);
		//
		Assertions.assertDoesNotThrow(() -> export(voices, null, null));
		//
		voice.setFilePath(EMPTY);
		//
		Assertions.assertDoesNotThrow(() -> export(voices, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.emptyMap(), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Reflection.newProxy(Map.class, ih), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(null, null), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(EMPTY, null), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(EMPTY, EMPTY), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(EMPTY, SPACE), null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(EMPTY, "true"), null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Map<String, String> map = Collections.singletonMap(EMPTY, "true");
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP,
				Util.cast(InvocationHandler.class, newInstance(constructor)));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [interface org.springframework.context.support.VoiceManager$BooleanMap] Not Found, message=Key [interface org.springframework.context.support.VoiceManager$BooleanMap] Not Found}",
				() -> export(voices, map, objectMap));
		//

	}

	private static void export(final List<Voice> voices, final Map<String, String> outputFolderFileNameExpressions,
			final Object objectMap) throws Throwable {
		try {
			METHOD_EXPORT.invoke(null, voices, outputFolderFileNameExpressions, objectMap);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map((IntStream) null, null));
		//
		IntStream intStream = IntStream.empty();
		//
		Assertions.assertSame(intStream, map(intStream, null));
		//
		Assertions.assertSame(intStream = Reflection.newProxy(IntStream.class, ih), map(intStream, null));
		//
	}

	private static IntStream map(final IntStream instance, final IntUnaryOperator mapper) throws Throwable {
		try {
			final Object obj = METHOD_MAP_INT_STREAM.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMapToInt() throws Throwable {
		//
		Assertions.assertNull(mapToInt(stream, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertNull(mapToInt(empty, null));
		//
		Assertions.assertNotNull(mapToInt(empty, x -> 0));
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_INT.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMapToLong() throws Throwable {
		//
		Assertions.assertNull(mapToLong(null, null));
		//
		Assertions.assertNull(mapToLong(stream, null));
		//
		Assertions.assertNull(mapToLong(Stream.empty(), null));
		//
	}

	private static <T> LongStream mapToLong(final Stream<T> instance, final ToLongFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_LONG.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof LongStream) {
				return (LongStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null, null));
		//
		Assertions.assertNull(max(stream, null));
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
		Assertions.assertNotNull(max(IntStream.empty()));
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator)
			throws Throwable {
		try {
			final Object obj = METHOD_MAX_STREAM.invoke(null, instance, comparator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static OptionalInt max(final IntStream instance) throws Throwable {
		try {
			final Object obj = METHOD_MAX_INT_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertEquals(ZERO, orElse(OptionalInt.empty(), ZERO));
		//
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE_OPTIONAL_INT.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach((Stream<?>) null, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(stream, null));
		//
		final Collection<?> collection = Collections.emptySet();
		//
		Assertions.assertDoesNotThrow(() -> forEach(collection, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(collection, x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, null));
		//
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH_STREAM.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, E extends Throwable> void forEach(final Iterable<T> instance,
			final FailableConsumer<? super T, E> action) throws Throwable {
		try {
			METHOD_FOR_EACH_ITERABLE.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateWorkbook() throws Throwable {
		//
		// java.util.List,org.springframework.context.support.VoiceManager.BooleanMap
		//
		List<Voice> list = Reflection.newProxy(List.class, ih);
		//
		Assertions.assertNull(createWorkbook(list, null, null));
		//
		Assertions.assertNotNull(createWorkbook(list, null, XSSFWorkbook::new));
		//
		Assertions.assertNull(createWorkbook(list = Collections.singletonList(null), null, null));
		//
		Assertions.assertNotNull(createWorkbook(list, null, XSSFWorkbook::new));
		//
		final Voice voice = new Voice();
		//
		voice.setFileLength(Long.valueOf(0));
		//
		voice.setCreateTs(new Date());
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		// org.springframework.context.support.VoiceManager$BooleanMap.setBoolean(java.lang.String,boolean)
		//
		final Object booleanMap = Reflection.newProxy(CLASS_BOOLEAN_MAP, ih);
		//
		final Method setBoolean = CLASS_BOOLEAN_MAP != null
				? CLASS_BOOLEAN_MAP.getDeclaredMethod("setBoolean", String.class, Boolean.TYPE)
				: null;
		//
		if (setBoolean != null) {
			//
			setBoolean.setAccessible(true);
			//
			setBoolean.invoke(booleanMap, "exportListSheet", true);
			//
			setBoolean.invoke(booleanMap, "exportJlptSheet", true);
			//
		} // if
			//
		Assertions.assertNotNull(createWorkbook(Collections.nCopies(TWO, voice), booleanMap, XSSFWorkbook::new));
		//
	}

	private static Workbook createWorkbook(final List<Voice> voices, final Object booleanMap,
			final FailableSupplier<Workbook, RuntimeException> supplier) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_WORK_BOOK_LIST.invoke(null, voices, booleanMap, supplier);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateVoice() throws Throwable {
		//
		Assertions.assertNull(createVoice(null, null));
		//
		Assertions.assertNotNull(createVoice(null, instance));
		//
		// org.springframework.context.support.VoiceManager.ObjectMap
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, createVoiceManagerIH());
		//
		final Class<?> clz = Util.getClass(objectMap);
		//
		// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
		//
		final Method setObject = clz != null ? clz.getDeclaredMethod("setObject", Class.class, Object.class) : null;
		//
		if (setObject != null) {
			//
			setObject.setAccessible(true);
			//
		} // if
			//
		invoke(setObject, objectMap, Row.class, Reflection.newProxy(Row.class, ih));
		//
		Assertions.assertNull(createVoice(objectMap, true, null));
		//
		final List<Cell> cells = Arrays.asList(null, Reflection.newProxy(Cell.class, ih));
		//
		if (ih != null) {
			//
			ih.cells = iterator(cells);
			//
			ih.columnIndex = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertNull(createVoice(objectMap, true, null));
		//
		final AtomicReference<?> arintMap = new AtomicReference<>();

		if (ih != null) {
			//
			ih.cells = iterator(cells);
			//
		} // if
			//
		Assertions.assertNull(createVoice(objectMap, true, arintMap));
		//
		if (ih != null) {
			//
			ih.cells = iterator(cells);
			//
		} // if
			//
		Assertions.assertNull(createVoice(objectMap, false, arintMap));
		//
	}

	private static <E> Iterator<E> iterator(final Iterable<E> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static Voice createVoice(final ObjectMapper objectMapper, final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VOICE_OBJECT_MAPPER.invoke(null, objectMapper, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Voice) {
				return (Voice) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Voice createVoice(final Object objectMap, final boolean first, final AtomicReference<?> arintMap)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VOICE_OBJECT_MAP.invoke(null, objectMap, first, arintMap);
			if (obj == null) {
				return null;
			} else if (obj instanceof Voice) {
				return (Voice) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnnotationType() throws Throwable {
		//
		Assertions.assertNull(annotationType(null));
		//
	}

	private static Class<? extends Annotation> annotationType(final Annotation instance) throws Throwable {
		try {
			final Object obj = METHOD_ANNOTATION_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindFirst() throws Throwable {
		//
		Assertions.assertNull(findFirst(null));
		//
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_FIND_FIRST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredWidth() throws Throwable {
		//
		Assertions.assertNull(getPreferredWidth(null));
		//
	}

	private static Double getPreferredWidth(final Component c) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_WIDTH.invoke(null, c);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportVoice() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, (BiConsumer) null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, (File) null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, null, null, null, null, null, null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [class java.io.File] Not Found, message=Key [class java.io.File] Not Found}",
				() -> importVoice(objectMap, (BiConsumer) null, null));
		//
		final Map<?, ?> objects = Util.cast(Map.class, FieldUtils.readDeclaredField(ih, "objects", true));
		//
		Util.put(((Map) objects), File.class, new File("."));
		//
		Util.put(((Map) objects), Voice.class, null);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (BiConsumer) null, null));
		//
		Util.put(((Map) objects), File.class, new File("NON_EXISTS"));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (BiConsumer) null, null));
		//
		Util.put(((Map) objects), File.class, new File("pom.xml"));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (v, m) -> {
		}, null));
		//
		final File file = File.createTempFile(nextAlphabetic(randomStringUtils, THREE), null);
		//
		if (file != null) {
			//
			file.deleteOnExit();
			//
		} // if
			//
		Util.put(((Map) objects), File.class, file);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (BiConsumer) null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null, null, null));
		//
		final Row row = Reflection.newProxy(Row.class, this.ih);
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null, null, null));
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		this.ih.cells = Iterators.forArray(null, cell);
		//
		this.ih.columnIndex = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$importVoice(org.springframework.context.support.VoiceManager$ObjectMap,java.io.File,java.lang.String)
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [class org.springframework.context.support.VoiceManager] Not Found, message=Key [class org.springframework.context.support.VoiceManager] Not Found}",
				() -> importVoice(objectMap, (File) null, null));
		//
		final Class<?> clz = Util.getClass(objectMap);
		//
		// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
		//
		final Method setObject = clz != null ? clz.getDeclaredMethod("setObject", Class.class, Object.class) : null;
		//
		if (setObject != null) {
			//
			setObject.setAccessible(true);
			//
		} // if
			//
			// org.springframework.context.support.VoiceManager
			//
		invoke(setObject, objectMap, VoiceManager.class, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [class org.springframework.context.support.VoiceManager$ImportTask] Not Found, message=Key [class org.springframework.context.support.VoiceManager$ImportTask] Not Found}",
				() -> importVoice(objectMap, (File) null, null));
		//
		// org.springframework.context.support.VoiceManager$ImortTask
		//
		invoke(setObject, objectMap, CLASS_IMPORT_TASK, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [interface org.springframework.context.support.SpeechApi] Not Found, message=Key [interface org.springframework.context.support.SpeechApi] Not Found}",
				() -> importVoice(objectMap, (File) null, null));
		//
		// org.springframework.context.support.SpeechApi
		//
		invoke(setObject, objectMap, SpeechApi.class, null);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));// TODO
		//
		// org.springframework.context.support.VoiceManager$ImortTask
		//
		final Object importTask = Narcissus.allocateInstance(CLASS_IMPORT_TASK);
		//
		invoke(setObject, objectMap, CLASS_IMPORT_TASK, importTask);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// org.springframework.context.support.VoiceManager$ImortTask.voice
		//
		final Voice voice = new Voice();
		//
		if (importTask != null) {
			//
			FieldUtils.writeDeclaredField(importTask, "voice", voice, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// org.springframework.context.support.SpeechApi
		//
		invoke(setObject, objectMap, SpeechApi.class, speechApi);
		//
		if (this.ih != null) {
			//
			this.ih.isInstalled = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		if (this.ih != null) {
			//
			this.ih.isInstalled = Boolean.TRUE;
			//
		} // if
			//
			// org.springframework.context.support.VoiceManager$ByteConverter
			//
		invoke(setObject, objectMap, CLASS_BYTE_CONVERTER, Reflection.newProxy(CLASS_BYTE_CONVERTER, this.ih));
		//
		// org.springframework.context.support.Provider
		//
		invoke(setObject, objectMap, Provider.class, Reflection.newProxy(Provider.class, this.ih));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// domain.Voice.tts
		//
		FieldUtils.writeDeclaredField(voice, "tts", Boolean.FALSE, true);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// org.springframework.context.support.VoiceManager.onlineNHKJapanesePronunciationsAccentFailableFunction
		//
		if (instance != null) {
			//
			instance.setOnlineNHKJapanesePronunciationsAccentFailableFunction(
					onlineNHKJapanesePronunciationsAccentFailableFunction);
			//
		} // if
			//
		invoke(setObject, objectMap, VoiceManager.class, instance);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		final Pronunciation pronunciation = new Pronunciation();
		//
		if (this.ih != null) {
			//
			this.ih.pronunciations = Collections.singletonList(pronunciation);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// domain.Pronunciation.audioUrls
		//
		pronunciation.setAudioUrls(Collections.singletonMap(null, toString(toURL(toURI(new File("pom.xml"))))));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
		// domain.Voice.filePath
		//
		voice.setFilePath("non-exist-file");
		//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (File) null, null));
		//
	}

	private static String nextAlphabetic(final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphabetic(count) : null;
	}

	private void importVoice(final File file) throws Throwable {
		try {
			METHOD_IMPORT_VOICE1.invoke(instance, file);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void importVoice(final Object objectMap, final BiConsumer<Voice, String> errorMessageConsumer,
			final BiConsumer<Voice, Throwable> throwableConsumer) throws Throwable {
		try {
			METHOD_IMPORT_VOICE_OBJECT_MAP_BI_CONSUMER.invoke(null, objectMap, errorMessageConsumer, throwableConsumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void importVoice(final Sheet sheet, final Object _objectMap, final String voiceId,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer, final Collection<Object> throwableStackTraceHexs) throws Throwable {
		try {
			METHOD_IMPORT_VOICE5.invoke(null, sheet, _objectMap, voiceId, errorMessageConsumer, throwableConsumer,
					voiceConsumer, throwableStackTraceHexs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void importVoice(final Object objectMap, final File folder, final String voiceId) throws Throwable {
		try {
			METHOD_IMPORT_VOICE_OBJECT_MAP_FILE.invoke(null, objectMap, folder, voiceId);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportVoiceBySpeechApi() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> importVoiceBySpeechApi(null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ObjectMap
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, createVoiceManagerIH());
		//
		final Class<?> clz = Util.getClass(objectMap);
		//
		// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
		//
		final Method setObject = clz != null ? clz.getDeclaredMethod("setObject", Class.class, Object.class) : null;
		//
		if (setObject != null) {
			//
			setObject.setAccessible(true);
			//
		} // if
			//
			// org.springframework.context.support.VoiceManager$ImportTask
			//
		invoke(setObject, objectMap, CLASS_IMPORT_TASK, Narcissus.allocateInstance(CLASS_IMPORT_TASK));
		//
		// org.springframework.context.support.VoiceManager
		//
		invoke(setObject, objectMap, VoiceManager.class, instance);
		//
		// org.springframework.context.support.SpeechApi
		//
		invoke(setObject, objectMap, SpeechApi.class, speechApi);
		//
		// org.springframework.context.support.VoiceManager$ByteConverter
		//
		invoke(setObject, objectMap, CLASS_BYTE_CONVERTER, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [interface org.springframework.context.support.Provider] Not Found, message=Key [interface org.springframework.context.support.Provider] Not Found}",
				() -> importVoiceBySpeechApi(objectMap, null, null));
		//
	}

	private static void importVoiceBySpeechApi(final Object objectMap, final String filePath, final String voiceId)
			throws Throwable {
		try {
			METHOD_IMPORT_VOICE_BY_SPEECH_API.invoke(null, objectMap, filePath, voiceId);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction() throws Throwable {
		//
		Assertions.assertDoesNotThrow(
				() -> importVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction(null, null));
		//

	}

	private static void importVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction(final Object objectMap,
			final String filePath) throws Throwable {
		try {
			METHOD_IMPORT_VOICE_BY_ONLINE_NHK_JAPANESE_PRONUNCIATIONS_ACCENT_FAILABLE_FUNCTION.invoke(null, objectMap,
					filePath);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> add((Container) null, null));
		//
		final Container container = Util.cast(Container.class, Narcissus.allocateInstance(Container.class));
		//
		final Component component = new JTextField();
		//
		Assertions.assertDoesNotThrow(() -> add(container, component));
		//
		Assertions.assertDoesNotThrow(() -> add(container, component, null));
		//
		Assertions.assertDoesNotThrow(() -> add(new JPanel(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> add(new JPanel(), component, null));
		//
	}

	private static void add(final Container instance, final Component comp) throws Throwable {
		try {
			METHOD_ADD_CONTAINER2.invoke(null, instance, comp);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void add(final Container instance, final Component comp, final Object constraints) throws Throwable {
		try {
			METHOD_ADD_CONTAINER3.invoke(null, instance, comp, constraints);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}

	}

	@Test
	void testCreateImportFileTemplateByteArray() throws Throwable {
		//
		Assertions.assertNotNull(createImportFileTemplateByteArray(false, null, null));
		//
		Assertions.assertNotNull(createImportFileTemplateByteArray(true, null, null));
		//
		Assertions.assertNotNull(
				createImportFileTemplateByteArray(true, Collections.singleton(null), Collections.singleton(null)));
		//
	}

	private static byte[] createImportFileTemplateByteArray(final boolean generateBlankRow,
			final Collection<String> jlptValues, final Collection<String> gaKuNenBeTsuKanJiValues) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY.invoke(null, generateBlankRow, jlptValues,
					gaKuNenBeTsuKanJiValues);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnyMatch() throws Throwable {
		//
		Assertions.assertFalse(anyMatch(null, null));
		//
		Assertions.assertFalse(anyMatch(Stream.empty(), null));
		//
		Assertions.assertSame(ih.anyMatch = Boolean.FALSE, Boolean.valueOf(anyMatch(stream, null)));
		//
	}

	private static <T> boolean anyMatch(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_ANY_MATCH.invoke(null, instance, predicate);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
	}

	private static String name(final Enum<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(Reflection.newProxy(ComboBoxModel.class, ih)));
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
		//
		Assertions.assertNull(matcher(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), ""));
		//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) throws Throwable {
		try {
			final Object obj = METHOD_MATCHER.invoke(null, pattern, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Matcher) {
				return (Matcher) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
	}

	private static boolean matches(final Matcher instance) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetValue() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> setValue(new JProgressBar(), 0));
		//
	}

	private static void setValue(final JProgressBar instance, final int n) throws Throwable {
		try {
			METHOD_SET_VALUE_J_PROGRESS_BAR.invoke(null, instance, n);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetString() {
		//
		Assertions.assertDoesNotThrow(() -> setString(new JProgressBar(), null));
		//
		Assertions.assertDoesNotThrow(() -> setString((Comment) null, null));
		//
	}

	private static void setString(final JProgressBar instance, final String string) throws Throwable {
		try {
			METHOD_SET_STRING_J_PROGRESS_BAR.invoke(null, instance, string);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setString(final Comment instance, final RichTextString string) throws Throwable {
		try {
			METHOD_SET_STRING_COMMENT.invoke(null, instance, string);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetToolTipText() {
		//
		Assertions.assertDoesNotThrow(() -> setToolTipText(new JTextField(), null));
		//
	}

	private static void setToolTipText(final JComponent instance, final String toolTipText) throws Throwable {
		try {
			METHOD_SET_TOOL_TIP_TEXT.invoke(null, instance, toolTipText);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFormat() throws Throwable {
		//
		Assertions.assertNull(format(null, 0d));
		//
	}

	private static String format(final NumberFormat instance, final double number) throws Throwable {
		try {
			final Object obj = METHOD_FORMAT.invoke(null, instance, number);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(EMPTY));
		//
		Assertions.assertNull(valueOf(SPACE));
		//
		Assertions.assertNull(valueOf("A", 10));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF1.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Integer valueOf(final String instance, final int base) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF2.invoke(null, instance, base);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testWriteVoiceToFile() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> writeVoiceToFile(null, null, null, null, null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		final Field fieldObjects = ih != null ? getDeclaredField(ih.getClass(), "objects") : null;
		//
		if (fieldObjects != null) {
			//
			fieldObjects.setAccessible(true);
			//
		} // if
			//
		Map<Object, Object> objects = getIfNull(Util.cast(Map.class, get(fieldObjects, ih)), LinkedHashMap::new);
		//
		Util.put(objects, SpeechApi.class, speechApi);
		//
		Util.put(objects, File.class, null);
		//
		set(fieldObjects, ih, objects);
		//
		Assertions.assertDoesNotThrow(
				() -> writeVoiceToFile(Reflection.newProxy(CLASS_OBJECT_MAP, ih), null, null, null, null));
		//
	}

	private static void writeVoiceToFile(final Object objectMap, final String text, final String voiceId,
			final Integer rate, final Integer volume) throws Throwable {
		try {
			METHOD_WRITE_VOICE_TO_FILE.invoke(null, objectMap, text, voiceId, rate, volume);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMp3TagValue() throws Throwable {
		//
		Assertions.assertNull(getMp3TagValue((File) null, null));
		//
		Assertions.assertNull(getMp3TagValue(new File("NON_EXISTS"), null));
		//
		Assertions.assertNull(getMp3TagValue(new File("."), null));
		//
		Assertions.assertNull(getMp3TagValue(new File("pom.xml"), null));
		//
		Assertions.assertNull(getMp3TagValue(Collections.singletonList(null), null));
		//
		final List<Pair<String, ?>> pairs = Collections.singletonList(Pair.of(null, null));
		//
		Assertions.assertNull(getMp3TagValue(pairs, null));
		//
		Assertions.assertNull(getMp3TagValue(pairs, Predicates.alwaysFalse()));
		//
		Assertions.assertNull(getMp3TagValue(pairs, Predicates.alwaysTrue()));
		//
	}

	private static String getMp3TagValue(final File file, final Predicate<Object> predicate, final String... attributes)
			throws Throwable {
		//
		try {
			final Object obj = METHOD_GET_MP3_TAG_VALUE_FILE.invoke(null, file, predicate, attributes);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getMp3TagValue(final List<Pair<String, ?>> pairs, final Predicate<Object> predicate)
			throws Throwable {
		//
		try {
			final Object obj = METHOD_GET_MP3_TAG_VALUE_LIST.invoke(null, pairs, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMp3TagParirs() throws Throwable {
		//
		Assertions.assertNull(getMp3TagParirs((ID3v1) null));
		//
		final ID3v1 id3v1 = Reflection.newProxy(ID3v1.class, ih);
		//
		Assertions.assertNull(getMp3TagParirs(id3v1));
		//
		Assertions.assertNull(getMp3TagParirs(id3v1, (String[]) null));
		//
		Assertions.assertNull(getMp3TagParirs(id3v1, null, ""));
		//
		Assertions.assertEquals(Collections.singletonList(Pair.of("artist", null)), getMp3TagParirs(id3v1, "artist"));
		//
	}

	private static List<Pair<String, ?>> getMp3TagParirs(final ID3v1 id3v1, final String... attributes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_MP3_TAG_PARIRS_ID3V1.invoke(null, id3v1, attributes);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCopyObjectMap() throws Throwable {
		//
		Assertions.assertNull(copyObjectMap(null));
		//
		final Object objectMap1 = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		Assertions.assertNotSame(objectMap1, copyObjectMap(objectMap1));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		final Object objectMap2 = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		Assertions.assertNotSame(objectMap2, copyObjectMap(objectMap2));
		//
	}

	private static Object copyObjectMap(final Object objectMap) throws Throwable {
		try {
			return METHOD_COPY_OBJECT_MAP.invoke(null, objectMap);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDelete() {
		//
		Assertions.assertDoesNotThrow(() -> delete(null));
		//
		Assertions
				.assertDoesNotThrow(() -> delete(File.createTempFile(nextAlphabetic(randomStringUtils, THREE), null)));
		//
	}

	private static void delete(final File instance) throws Throwable {
		try {
			METHOD_DELETE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDeleteOnExit() {
		//
		Assertions.assertDoesNotThrow(() -> deleteOnExit(null));
		//
		Assertions.assertDoesNotThrow(
				() -> deleteOnExit(File.createTempFile(nextAlphabetic(randomStringUtils, THREE), null)));
		//
	}

	private static void deleteOnExit(final File instance) throws Throwable {
		try {
			METHOD_DELETE_ON_EXIT.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testConvertLanguageCodeToText() throws Throwable {
		//
		Assertions.assertNull(convertLanguageCodeToText(new LocaleID[] { null }, null));
		//
		final LocaleID localeId = LocaleID.JA_JP;
		//
		final Integer lcid = localeId != null ? Integer.valueOf(localeId.getLcid()) : null;
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> convertLanguageCodeToText(new LocaleID[] { localeId, localeId }, lcid));
		//
	}

	private static String convertLanguageCodeToText(final LocaleID[] enums, final Integer value) throws Throwable {
		try {
			final Object obj = METHOD_CONVERT_LANGUAGE_CODE_TO_TEXT.invoke(null, enums, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsSelected() throws Throwable {
		//
		Assertions.assertFalse(isSelected(null));
		//
		final AbstractButton ab = new JCheckBox();
		//
		Assertions.assertFalse(isSelected(ab));
		//
		ab.setSelected(true);
		//
		Assertions.assertTrue(isSelected(ab));
		//
	}

	private static boolean isSelected(final AbstractButton instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_SELECTED.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetHiraganaOrKatakana() {
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakana(null));
		//
		final Voice voice = new Voice();
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakana(voice));
		//
		voice.setHiragana("あ");
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakana(voice));
		//
		Assertions.assertEquals("ア", voice.getKatakana());
		//
		voice.setHiragana(null);
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakana(voice));
		//
		Assertions.assertEquals("あ", voice.getHiragana());
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakana(voice));
		//
	}

	private static void setHiraganaOrKatakana(final Voice voice) throws Throwable {
		try {
			METHOD_SET_HIRAGANA_OR_KATAKANA.invoke(null, voice);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetRomaji() {
		//
		Assertions.assertDoesNotThrow(() -> setRomaji(null, null));
		//
		final Voice voice = new Voice();
		//
		voice.setRomaji("a");
		//
		voice.setHiragana("あ");
		//
		Assertions.assertDoesNotThrow(() -> setRomaji(voice, null));
		//
		voice.setRomaji(null);
		//
		voice.setHiragana("い");
		//
		Assertions.assertDoesNotThrow(() -> setRomaji(voice, null));
		//
		Assertions.assertNull(voice.getRomaji());
		//
		Assertions.assertDoesNotThrow(() -> setRomaji(voice, new Jakaroma()));
		//
		Assertions.assertEquals("i", voice.getRomaji());
		//
	}

	private static void setRomaji(final Voice voice, final Jakaroma jakaroma) throws Throwable {
		try {
			METHOD_SET_ROMAJI.invoke(null, voice, jakaroma);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertTrue(and(Objects::nonNull, EMPTY, EMPTY, (Object[]) null));
		//
		Assertions.assertTrue(and(Objects::nonNull, EMPTY, EMPTY, EMPTY));
		//
	}

	private static <T> boolean and(final Predicate<T> predicate, final T a, final T b, final T... values)
			throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, predicate, a, b, values);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertTrue(or(x -> Objects.equals(x, EMPTY), null, EMPTY));
		//
		Assertions.assertFalse(or(Predicates.alwaysFalse(), null, null, (Object[]) null));
		//
	}

	private static <T> boolean or(final Predicate<T> predicate, final T a, final T b, final T... values)
			throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, predicate, a, b, values);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear((DefaultTableModel) null));
		//
		Assertions.assertDoesNotThrow(() -> clear((StringBuilder) null));
		//
		Assertions.assertDoesNotThrow(() -> clear(new DefaultTableModel()));
		//
		Assertions.assertDoesNotThrow(() -> clear(new StringBuilder()));
		//
	}

	private static void clear(final DefaultTableModel instance) throws Throwable {
		try {
			METHOD_CLEAR_DEFAULT_TABLE_MODEL.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void clear(final StringBuilder instance) throws Throwable {
		try {
			METHOD_CLEAR_STRING_BUILDER.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecute() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		final Field fieldObjects = ih != null ? getDeclaredField(ih.getClass(), "objects") : null;
		//
		if (fieldObjects != null) {
			//
			fieldObjects.setAccessible(true);
			//
		} // if
			//
		Map<Object, Object> objects = getIfNull(Util.cast(Map.class, get(fieldObjects, ih)), LinkedHashMap::new);
		//
		final DefaultTableModel defaultTableModel = new DefaultTableModel();
		//
		Util.put(objects, File.class, null);
		//
		Util.put(objects, VoiceManager.class, instance);
		//
		Util.put(objects, Voice.class, null);
		//
		Util.put(objects, DefaultTableModel.class, null);
		//
		set(fieldObjects, ih, objects);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		Util.put(objects, DefaultTableModel.class, defaultTableModel);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		// !java.io.File.exists()
		//
		Util.put(objects, File.class, new File("NON_EXISTS"));
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		Util.put(objects, DefaultTableModel.class, null);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		// !java.io.File.isFile()
		//
		Util.put(objects, File.class, new File("."));
		//
		Util.put(objects, DefaultTableModel.class, defaultTableModel);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		Util.put(objects, DefaultTableModel.class, null);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
	}

	private static void execute(final Object objectMap) throws Throwable {
		try {
			METHOD_EXECUTE.invoke(null, objectMap);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetByteConverter() throws Throwable {
		//
		Assertions.assertNull(getByteConverter(null, null, null));
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		ih.beansOfType = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		ih.entrySet = Collections.singleton(null);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		ih.beansOfType = Collections.singletonMap(null, null);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		Util.put(ih.getBeanDefinitions(), null, beanDefinition);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		final String format = toString(FieldUtils.readDeclaredStaticField(VoiceManager.class, "FORMAT", true));
		//
		Util.put(ih.getBeanDefinitionAttributes(), format, null);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, format, null));
		//
		Util.put(ih.getBeanDefinitionAttributes(), format, "");
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, format, null));
		//
		Util.put((ih.beansOfType = new LinkedHashMap<Object, Object>(Collections.singletonMap(null, null))), "", null);
		//
		Util.put(ih.getBeanDefinitions(), "", beanDefinition);
		//
		Util.put(ih.getBeanDefinitionAttributes(), format, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> getByteConverter(configurableListableBeanFactory, format, null));
		//
	}

	private static Object getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String attribute, final Object value) throws Throwable {
		try {
			return METHOD_GET_BYTE_CONVERTER.invoke(null, configurableListableBeanFactory, attribute, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLpwstr() throws Throwable {
		//
		Assertions.assertNull(getLpwstr(null));
		//
		Assertions.assertNull(getLpwstr(Reflection.newProxy(CTProperty.class, ih)));
		//
	}

	private static String getLpwstr(final CTProperty instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LPW_STR.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSheetName() throws Throwable {
		//
		Assertions.assertNull(getSheetName(null));
		//
		Assertions.assertNull(getSheetName(sheet));
		//
	}

	private static String getSheetName(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SHEET_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> accept(null, null, null, (Object[]) null));
		//
		Assertions.assertDoesNotThrow(() -> accept(null, null, null, (Object) null));
		//
	}

	private static <T> void accept(final Consumer<? super T> action, final T a, final T b, final T... values)
			throws Throwable {
		try {
			METHOD_ACCEPT.invoke(null, action, a, b, values);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(null));
		//
		Assertions.assertNull(toArray((Collection<?>) null, null));
		//
		Assertions.assertNull(toArray((Stream<?>) null, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertNull(toArray(empty, null));
		//
		Assertions.assertThrows(NullPointerException.class, () -> toArray(empty, x -> null));
		//
		Assertions.assertNull(toArray(stream, null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
		Assertions.assertNull(toArray(collection, null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY_COLLECTION.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object[] toArray(final Stream<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_ARRAY_STREAM1.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Object[]) {
				return (Object[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, A> A[] toArray(final Stream<T> instance, final IntFunction<A[]> generator) throws Throwable {
		try {
			return (A[]) METHOD_TO_ARRAY_STREAM2.invoke(null, instance, generator);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetId() throws Throwable {
		//
		Assertions.assertNull(getId(null));
		//
	}

	private static Integer getId(final VoiceList instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ID.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetMaximum() {
		//
		Assertions.assertDoesNotThrow(() -> setMaximum(new JProgressBar(), 0));
		//
	}

	private static void setMaximum(final JProgressBar instance, final int n) throws Throwable {
		try {
			METHOD_SET_MAXIMUM.invoke(null, instance, n);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetCurrentSheetIndex() throws Throwable {
		//
		Assertions.assertNull(getCurrentSheetIndex(null));
		//
		Assertions.assertNull(getCurrentSheetIndex(sheet));
		//
		ih.workbook = workbook;
		//
		ih.numberOfSheets = Integer.valueOf(ONE);
		//
		Assertions.assertEquals(Integer.valueOf(ZERO), getCurrentSheetIndex(sheet));
		//
		ih.numberOfSheets = Integer.valueOf(TWO);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}", () -> getCurrentSheetIndex(sheet));
		//
	}

	private static Integer getCurrentSheetIndex(final Sheet sheet) throws Throwable {
		try {
			final Object obj = METHOD_GET_CURRENT_SHEET_INDEX.invoke(null, sheet);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDataValidationHelper() throws Throwable {
		//
		Assertions.assertNull(getDataValidationHelper(null));
		//
	}

	private static DataValidationHelper getDataValidationHelper(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DATA_VALIDATION_HELPER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DataValidationHelper) {
				return (DataValidationHelper) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateExplicitListConstraint() throws Throwable {
		//
		Assertions.assertNull(createExplicitListConstraint(null, null));
		//
	}

	private static DataValidationConstraint createExplicitListConstraint(final DataValidationHelper instance,
			final String[] listOfValues) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_EXPLICIT_LIST_CONSTRAINT.invoke(null, instance, listOfValues);
			if (obj == null) {
				return null;
			} else if (obj instanceof DataValidationConstraint) {
				return (DataValidationConstraint) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateValidation() throws Throwable {
		//
		Assertions.assertNull(createValidation(null, null, null));
		//
	}

	private static DataValidation createValidation(final DataValidationHelper instance,
			final DataValidationConstraint constraint, final CellRangeAddressList cellRangeAddressList)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VALIDATION.invoke(null, instance, constraint, cellRangeAddressList);
			if (obj == null) {
				return null;
			} else if (obj instanceof DataValidation) {
				return (DataValidation) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateExportTask() throws Throwable {
		//
		Assertions.assertNotNull(createExportTask(null, null, null, null, null, null));
		//
	}

	private static Object createExportTask(final Object objectMap, final Integer size, final Integer counter,
			final Integer numberOfOrdinalPositionDigit, final Map<String, String> outputFolderFileNameExpressions,
			final Table<String, String, Voice> voiceFileNames) throws Throwable {
		try {
			return METHOD_CREATE_EXPORT_TASK.invoke(null, objectMap, size, counter, numberOfOrdinalPositionDigit,
					outputFolderFileNameExpressions, voiceFileNames);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTabIndexByTitle() throws Throwable {
		//
		Assertions.assertNull(getTabIndexByTitle(Collections.singletonList(null), null));
		//
	}

	private static Integer getTabIndexByTitle(final List<?> pages, final Object title) throws Throwable {
		try {
			final Object obj = METHOD_GET_TAB_INDEX_BY_TITLE.invoke(null, pages, title);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assertions.assertNull(getDeclaredField(null, null));
		//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELD.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetEnumConstants() throws Throwable {
		//
		Assertions.assertNull(getEnumConstants(null));
		//
		Assertions.assertNull(getEnumConstants(Object.class));
		//
	}

	private static <T> T[] getEnumConstants(final Class<T> instance) throws Throwable {
		try {
			return (T[]) METHOD_GET_ENUM_CONSTANTS.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testListFiles() throws Throwable {
		//
		Assertions.assertNull(listFiles(null));
		//
		Assertions.assertNull(listFiles(new File("pom.xml")));
		//
	}

	private static File[] listFiles(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_LIST_FILES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof File[]) {
				return (File[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetColumnName() throws Throwable {
		//
		Assertions.assertNull(getColumnName(null, null));
		//
	}

	private static String getColumnName(final Class<?> spreadsheetColumnClass, final Field f) throws Throwable {
		try {
			final Object obj = METHOD_GET_COLUMN_NAME.invoke(null, spreadsheetColumnClass, f);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> putAll(null, null));
		//
	}

	private static <K, V> void putAll(final Map<K, V> a, final Map<? extends K, ? extends V> b) throws Throwable {
		try {
			METHOD_PUT_ALL_MAP.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWorkbook() throws Throwable {
		//
		final int tempFileMinimumPrefixLength = intValue(TEMP_FILE_MINIMUM_PREFIX_LENGTH, THREE);
		//
		final File folder = new File(".");
		//
		File file = File.createTempFile(nextAlphanumeric(randomStringUtils, tempFileMinimumPrefixLength), null, folder);
		//
		deleteOnExit(file);
		//
		try (final Workbook workbook = new HSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			WorkbookUtil.write(workbook, os);
			//
			Assertions.assertNotNull(getWorkbook(file));
			//
		} // try
			//
		try (final Workbook workbook = new XSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			WorkbookUtil.write(workbook, os);
			//
			Assertions.assertNotNull(getWorkbook(file));
			//
		} // try
			//
		ZipUtil.removeEntry(file, "[Content_Types].xml", file = File
				.createTempFile(nextAlphanumeric(randomStringUtils, tempFileMinimumPrefixLength), null, folder));
		//
		deleteOnExit(file);
		//
		Assertions.assertNull(getWorkbook(file));
		//
	}

	private static String nextAlphanumeric(final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphanumeric(count) : null;
	}

	private static Workbook getWorkbook(final File file) throws Throwable {
		try {
			final Object obj = METHOD_GET_WORK_BOOK.invoke(null, file);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetOleEntryNames() throws Throwable {
		//
		Assertions.assertNull(getOleEntryNames(null));
		//
	}

	private static List<String> getOleEntryNames(final POIFSFileSystem poifs) throws Throwable {
		try {
			final Object obj = METHOD_GET_OLE_ENTRY_NAMES.invoke(null, poifs);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParse() throws Throwable {
		//
		Assertions.assertNull(parse(newDocumentBuilder(null), null));
		//
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance) throws Throwable {
		try {
			final Object obj = METHOD_NEW_DOCUMENT_BUILDER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DocumentBuilder) {
				return (DocumentBuilder) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Document parse(final DocumentBuilder instance, final InputStream is) throws Throwable {
		try {
			final Object obj = METHOD_PARSE.invoke(null, instance, is);
			if (obj == null) {
				return null;
			} else if (obj instanceof Document) {
				return (Document) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetChildNodes() throws Throwable {
		//
		try (final InputStream is = new ByteArrayInputStream(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><a/>".getBytes())) {
			//
			Assertions.assertNotNull(getChildNodes(
					getDocumentElement(parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), is))));
			//
		} // try
			//
	}

	private static Element getDocumentElement(final Document instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DOCUMENT_ELEMENT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Element) {
				return (Element) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static NodeList getChildNodes(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CHILD_NODES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof NodeList) {
				return (NodeList) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTextContent() throws Throwable {
		//
		Assertions.assertNull(getTextContent(getNamedItem(null, null)));
		//
		final NamedNodeMap namedNodeMap = Reflection.newProxy(NamedNodeMap.class, ih);
		//
		Assertions.assertNull(getTextContent(getNamedItem(namedNodeMap, null)));
		//
		ih.namedItem = node;
		//
		Assertions.assertNull(getTextContent(getNamedItem(namedNodeMap, null)));
		//
	}

	private static Node getNamedItem(final NamedNodeMap instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAMED_ITEM.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Node) {
				return (Node) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getTextContent(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT_CONTENT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName((File) null));
		//
		Assertions.assertNotNull(getName(new File(".")));
		//
	}

	private static String getName(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_FILE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPassword() throws Throwable {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(getPassword(null));
			//
		} // if
			//
	}

	private static String getPassword(final Console console) throws Throwable {
		try {
			final Object obj = METHOD_GET_PASS_WORD.invoke(null, console);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null));
		//
		Assertions.assertNull(get(null, 0));
		//
	}

	private static <T> T get(final Supplier<T> instance) throws Throwable {
		try {
			return (T) METHOD_GET_SUPPLIER.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <E> E get(final List<E> instance, final int index) throws Throwable {
		try {
			return (E) METHOD_GET_LIST.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMicrosoftSpeechObjectLibraryWorkbook() throws Throwable {
		//
		Assertions.assertNull(createMicrosoftSpeechObjectLibraryWorkbook(null, (String[]) null));
		//
		ih.voiceIds = new String[] {};
		//
		Assertions.assertNull(createMicrosoftSpeechObjectLibraryWorkbook(speechApi, (String[]) null));
		//
		ih.voiceIds = new String[] { null };
		//
		Assertions.assertNotNull(createMicrosoftSpeechObjectLibraryWorkbook(speechApi, (String) null));
		//
		Assertions.assertNotNull(createMicrosoftSpeechObjectLibraryWorkbook(speechApi, SPACE));
		//
		ih.errorGetVoiceAttribute = new Error();
		//
		Assertions.assertNotNull(createMicrosoftSpeechObjectLibraryWorkbook(speechApi, SPACE));
		//
	}

	private static Workbook createMicrosoftSpeechObjectLibraryWorkbook(final SpeechApi speechApi,
			final String... attributes) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MICROSOFT_SPEECH_OBJECT_LIBRARY_WORK_BOOK.invoke(null, speechApi,
					attributes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateRichTextString() throws Throwable {
		//
		Assertions.assertNull(createRichTextString(null, null));
		//
	}

	private static RichTextString createRichTextString(final CreationHelper instance, final String text)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_RICH_TEXT_STRING.invoke(null, instance, text);
			if (obj == null) {
				return null;
			} else if (obj instanceof RichTextString) {
				return (RichTextString) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCellComment() {
		//
		Assertions.assertDoesNotThrow(
				() -> setCellComment(null, createCellComment(createDrawingPatriarch(null), createClientAnchor(null))));
		//
	}

	private static Drawing<?> createDrawingPatriarch(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_DRAWING_PATRIARCH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Drawing) {
				return (Drawing) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Comment createCellComment(final Drawing<?> instance, final ClientAnchor anchor) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_CELL_COMMENT.invoke(null, instance, anchor);
			if (obj == null) {
				return null;
			} else if (obj instanceof Comment) {
				return (Comment) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static ClientAnchor createClientAnchor(final CreationHelper instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_CLIENT_ANCHOR.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ClientAnchor) {
				return (ClientAnchor) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setCellComment(final Cell instance, final Comment comment) throws Throwable {
		try {
			METHOD_SET_CELL_COMMENT.invoke(null, instance, comment);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetAuthor() {
		//
		Assertions.assertDoesNotThrow(() -> setAuthor(null, null));
		//
	}

	private static void setAuthor(final Comment instance, final String string) throws Throwable {
		try {
			METHOD_SET_AUTHOR.invoke(null, instance, string);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
		//
		final BiPredicate<?, ?> biPredicate = (a, b) -> true;
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, (a, b) -> {
		}));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_PREDICATE.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> biPredicate, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_BI_PREDICATE.invoke(null, biPredicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindFieldsByValue() throws Throwable {
		//
		Assertions.assertNull(findFieldsByValue(null, null, null));
		//
		Assertions.assertNull(findFieldsByValue(new Field[] { null }, null, null));
		//
		Assertions.assertNotNull(findFieldsByValue(FieldUtils.getAllFields(VoiceManager.class), null, null));
		//
		final Field f = ArrayList.class.getDeclaredField("size");
		//
		final Field[] fs = new Field[] { f };
		//
		Assertions.assertNotNull(findFieldsByValue(fs, null, null));
		//
		final Collection<?> collection = new ArrayList<>();
		//
		Assertions.assertNull(findFieldsByValue(fs, collection, null));
		//
		Assertions.assertNull(findFieldsByValue(fs, collection, Integer.valueOf(1)));
		//
		Assertions.assertEquals(Collections.singletonList(f), findFieldsByValue(fs, collection, Integer.valueOf(0)));
		//
	}

	private static List<Field> findFieldsByValue(final Field[] fs, final Object instance, final Object value)
			throws Throwable {
		try {
			final Object obj = METHOD_FIND_FIELDS_BY_VALUE.invoke(null, fs, instance, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPackage() throws Throwable {
		//
		Assertions.assertNull(getPackage(null));
		//
	}

	private static Package getPackage(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PACKAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Package) {
				return (Package) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testBrowse() {
		//
		Assertions.assertDoesNotThrow(() -> browse(null, null));
		//
	}

	private static void browse(final Desktop instance, final URI uri) throws Throwable {
		try {
			METHOD_BROWSE.invoke(null, instance, uri);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURI() throws Throwable {
		//
		Assertions.assertNull(toURI((URL) null));
		//
		Assertions.assertNull(toURI(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
		final File file = new File("");
		//
		Assertions.assertNotNull(toURI(file));
		//
		final URI uri = file.toURI();
		//
		Assertions.assertNotNull(toURI(toURL(uri)));
		//
	}

	private static URI toURI(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI_FILE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static URI toURI(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredClasses() throws Throwable {
		//
		Assertions.assertNull(getDeclaredClasses(null));
		//
	}

	private static Class<?>[] getDeclaredClasses(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_CLASSES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>[]) {
				return (Class<?>[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private class InnerClass {

		private interface InnerInterface {

			InnerInterface INSTANCE = Reflection.newProxy(InnerInterface.class, new IH());

			String getDllPath();

		}

	}

	@Test
	void testGetDllPath() throws Throwable {
		//
		Assertions.assertEquals(Unit.with(null), getDllPath(new InnerClass()));
		//
	}

	private static IValue0<Object> getDllPath(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DLL_PATH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRate() throws Throwable {
		//
		Assertions.assertNull(getRate(instance));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", new JSlider(), true);
			//
		} // if
			//
		Assertions.assertNotNull(getRate());
		//
		Assertions.assertNull(getRate((VoiceManager) null));
		//
		Assertions.assertNull(getRate((List) null));
		//
		final List<Field> list = Collections.nCopies(TWO, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}", () -> getRate(list));
		//
	}

	private Integer getRate() throws Throwable {
		try {
			final Object obj = METHOD_GET_RATE0.invoke(instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Integer getRate(final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_RATE_VOICE_MANAGER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Integer getRate(final List<Field> fs) throws Throwable {
		try {
			final Object obj = METHOD_GET_RATE_FIELD_LIST.invoke(null, fs);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAnnotationPresent() throws Throwable {
		//
		Assertions.assertFalse(isAnnotationPresent(null, null));
		//
		Assertions.assertFalse(isAnnotationPresent(Object.class, null));
		//
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) throws Throwable {
		try {
			final Object obj = METHOD_IS_ANNOTATION_PRESENT.invoke(null, instance, annotationClass);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEncodeToString() throws Throwable {
		//
		Assertions.assertNull(encodeToString(null, null));
		//
		final Encoder encoder = Base64.getEncoder();
		//
		Assertions.assertNull(encodeToString(encoder, null));
		//
		Assertions.assertEquals(EMPTY, encodeToString(encoder, new byte[] {}));
		//
	}

	private static String encodeToString(final Encoder instance, final byte[] src) throws Throwable {
		try {
			final Object obj = METHOD_ENCODE_TO_STRING.invoke(null, instance, src);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetVoiceMultimapByListName() throws Throwable {
		//
		Assertions.assertNull(getVoiceMultimapByListName(Collections.singleton(null)));
		//
		Assertions.assertNull(getVoiceMultimapByListName(Reflection.newProxy(Iterable.class, ih)));
		//
		final Voice voice = new Voice();
		//
		final Iterable<Voice> voices = Collections.singleton(voice);
		//
		Assertions.assertNull(getVoiceMultimapByListName(voices));
		//
		voice.setListNames(Collections.singleton(EMPTY));
		//
		Assertions.assertEquals(ImmutableMultimap.of(EMPTY, voice), getVoiceMultimapByListName(voices));
		//
	}

	private static Multimap<String, Voice> getVoiceMultimapByListName(final Iterable<Voice> voices) throws Throwable {
		try {
			final Object obj = METHOD_GET_VOICE_MULTI_MAP_BY_LIST_NAME.invoke(null, voices);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetVoiceMultimapByJlpt() throws Throwable {
		//
		Assertions.assertNull(getVoiceMultimapByJlpt(null));
		//
		Assertions.assertNull(getVoiceMultimapByJlpt(Reflection.newProxy(Iterable.class, ih)));
		//
	}

	private static Multimap<String, Voice> getVoiceMultimapByJlpt(final Iterable<Voice> voices) throws Throwable {
		try {
			final Object obj = METHOD_GET_VOICE_MULTI_MAP_BY_JLPT.invoke(null, voices);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFileExtensions() throws Throwable {
		//
		Assertions.assertNull(getFileExtensions(null));
		//
	}

	private static String[] getFileExtensions(final ContentType instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReduce() throws Throwable {
		//
		Assertions.assertNull(reduce(Stream.empty(), null));
		//
		Assertions.assertNull(reduce(stream, null));
		//
		final long l = 0;
		//
		Assertions.assertEquals(l, reduce(null, l, null));
		//
	}

	private static <T> Optional<T> reduce(final Stream<T> instance, final BinaryOperator<T> accumulator)
			throws Throwable {
		try {
			final Object obj = METHOD_REDUCE2.invoke(null, instance, accumulator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static long reduce(final LongStream instance, final long identity, final LongBinaryOperator op)
			throws Throwable {
		try {
			final Object obj = METHOD_REDUCE3.invoke(null, instance, identity, op);
			if (obj instanceof Long) {
				return ((Long) obj).longValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAppend() throws Throwable {
		//
		Assertions.assertNull(append(null, null));
		//
		Assertions.assertNull(append(null, ' '));
		//
	}

	private static StringBuilder append(final StringBuilder instance, final String string) throws Throwable {
		try {
			final Object obj = METHOD_APPEND_STRING.invoke(null, instance, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof StringBuilder) {
				return (StringBuilder) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static StringBuilder append(final StringBuilder instance, final char c) throws Throwable {
		try {
			final Object obj = METHOD_APPEND_CHAR.invoke(null, instance, c);
			if (obj == null) {
				return null;
			} else if (obj instanceof StringBuilder) {
				return (StringBuilder) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetResourceAsStream() throws Throwable {
		//
		Assertions.assertNull(getResourceAsStream(null, null));
		//
		Assertions.assertNull(getResourceAsStream(Object.class, null));
		//
	}

	private static InputStream getResourceAsStream(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_RESOURCE_AS_STREAM.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTempFileMinimumPrefixLength() throws Throwable {
		//
		Assertions.assertNull(getTempFileMinimumPrefixLength((org.apache.bcel.classfile.Method) null));
		//
		Assertions.assertNull(getTempFileMinimumPrefixLength((Instruction[]) null));
		//
		final ICONST iconst = new ICONST(0);
		//
		Assertions.assertNull(getTempFileMinimumPrefixLength(new Instruction[] { iconst }));
		//
		Assertions.assertNull(getTempFileMinimumPrefixLength(new Instruction[] { iconst, null }));
		//
	}

	private static Integer getTempFileMinimumPrefixLength(final org.apache.bcel.classfile.Method method)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_METHOD.invoke(null, method);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Integer getTempFileMinimumPrefixLength(final Instruction[] instructions) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH_INSTRUCTION_ARRAY.invoke(null,
					(Object) instructions);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAttributes() throws Throwable {
		//
		Assertions.assertNull(getAttributes(null));
		//
		Assertions.assertNull(getAttributes(node));
		//
	}

	private static NamedNodeMap getAttributes(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ATTRIBUTES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof NamedNodeMap) {
				return (NamedNodeMap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLength() throws Throwable {
		//
		if (ih != null) {
			//
			ih.length = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(ZERO, getLength(nodeList));
		//
	}

	private static int getLength(final NodeList instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LENGTH.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testItem() throws Throwable {
		//
		Assertions.assertNull(item(null, 0));
		//
		Assertions.assertNull(item(nodeList, 0));
		//
	}

	private static Node item(final NodeList instance, final int index) throws Throwable {
		try {
			final Object obj = METHOD_ITEM.invoke(null, instance, index);
			if (obj == null) {
				return null;
			} else if (obj instanceof Node) {
				return (Node) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetOsVersionInfoExMap() throws Throwable {
		//
		if (isUnderWindows()) {
			//
			Assertions.assertNotNull(getOsVersionInfoExMap());
			//
		} // if
			//
	}

	private static boolean isUnderWindows() throws Throwable {
		//
		final FileSystem fs = FileSystems.getDefault();
		//
		return Objects.equals("sun.nio.fs.WindowsFileSystemProvider",
				Util.getName(Util.getClass(fs != null ? fs.provider() : null)));
		//
	}

	private static Map<String, Object> getOsVersionInfoExMap() throws Throwable {
		try {
			final Object obj = METHOD_GET_OS_VERSION_INFO_EX_MAP.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateJlptSheet() {
		//
		Assertions.assertDoesNotThrow(() -> createJlptSheet(null, null));
		//
		Assertions.assertDoesNotThrow(() -> createJlptSheet(null, Collections.singleton(null)));
		//
		final Voice voice = new Voice();
		//
		voice.setJlptLevel(EMPTY);
		//
		Assertions.assertDoesNotThrow(() -> createJlptSheet(null, Collections.singleton(voice)));
		//
	}

	private static void createJlptSheet(final Workbook workbook, final Iterable<Voice> voices) throws Throwable {
		try {
			METHOD_CREATE_JLPT_SHEET.invoke(null, workbook, voices);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrAssertOrShowException() {
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(true, null));
		//
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable)
			throws Throwable {
		try {
			METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2.invoke(null, headless, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetVisible() {
		//
		Assertions.assertDoesNotThrow(() -> setVisible(new JTextField(), false));
		//
	}

	private static void setVisible(final Component instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_VISIBLE.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRandomAlphabetic() throws Throwable {
		//
		Assertions.assertNotEquals(randomAlphabetic(THREE), randomAlphabetic(THREE));
		//
	}

	private static String randomAlphabetic(final int count) throws Throwable {
		try {
			final Object obj = METHOD_RANDOM_ALPHABETIC.invoke(null, count);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMediaFormatLink() throws Throwable {
		//
		Assertions.assertNull(getMediaFormatLink(null));
		//
	}

	private static ATag getMediaFormatLink(final String url) throws Throwable {
		try {
			final Object obj = METHOD_GET_MEDIA_FORMAT_LINK.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof ATag) {
				return (ATag) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetEventType() throws Throwable {
		//
		Assertions.assertNull(getEventType(null));
		//
		Assertions.assertNull(getEventType(new HyperlinkEvent(EMPTY, null, null)));
		//
	}

	private static EventType getEventType(final HyperlinkEvent instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_EVENT_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof EventType) {
				return (EventType) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetParentFile() throws Throwable {
		//
		Assertions.assertNull(getParentFile(new File(".")));
		//
	}

	private static File getParentFile(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PARENT_FILE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof File) {
				return (File) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetMicrosoftSpeechObjectLibrarySheet() {
		//
		Assertions.assertDoesNotThrow(() -> setMicrosoftSpeechObjectLibrarySheet(null, null, null));
		//
	}

	private static void setMicrosoftSpeechObjectLibrarySheet(final Object objectMap, final String voiceId,
			final String[] as) throws Throwable {
		try {
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET.invoke(null, objectMap, voiceId, as);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetMicrosoftSpeechObjectLibrarySheetFirstRow() {
		//
		Assertions.assertDoesNotThrow(() -> setMicrosoftSpeechObjectLibrarySheetFirstRow(null, null));
		//
	}

	private static void setMicrosoftSpeechObjectLibrarySheetFirstRow(final Sheet sheet, final String[] columnNames)
			throws Throwable {
		try {
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET_FIRST_ROW.invoke(null, sheet, columnNames);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExportJlpt() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> exportJlpt(null, null));
		//
		Assertions.assertDoesNotThrow(() -> exportJlpt(null, Collections.singletonList(null)));
		//
		final Voice voice = new Voice();
		//
		final List<Voice> voices = Collections.singletonList(voice);
		//
		Assertions.assertDoesNotThrow(() -> exportJlpt(null, voices));
		//
		voice.setJlptLevel("N1");
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		Assertions.assertDoesNotThrow(() -> exportJlpt(Reflection.newProxy(CLASS_OBJECT_MAP, ih), voices));
		//
	}

	private static void exportJlpt(final Object objectMap, final List<Voice> voices) throws Throwable {
		try {
			METHOD_EXPORT_JLPT.invoke(null, objectMap, voices);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMaxPagePreferredHeight() throws Throwable {
		//
		Assertions.assertNull(getMaxPagePreferredHeight(null));
		//
		final Object jTabbedPane = new JTabbedPane();
		//
		final List<?> pages = Util.cast(List.class,
				Narcissus.getObjectField(jTabbedPane, getDeclaredField(JTabbedPane.class, "pages")));
		//
		Util.add(pages, null);
		//
		Assertions.assertNull(getMaxPagePreferredHeight(jTabbedPane));
		//
	}

	private static Double getMaxPagePreferredHeight(final Object jTabbedPane) throws Throwable {
		try {
			final Object obj = METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT.invoke(null, jTabbedPane);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSheetHeaderRow() {
		//
		Assertions.assertDoesNotThrow(() -> setSheetHeaderRow(null, null, null));
		//
	}

	private static void setSheetHeaderRow(final Sheet sheet, final Field[] fs, final Class<?> spreadsheetColumnClass)
			throws Throwable {
		try {
			METHOD_SET_SHEET_HEADER_ROW.invoke(null, sheet, fs, spreadsheetColumnClass);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEncrypt() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> encrypt(null, null, null));
		//
		final File file = File.createTempFile(randomAlphabetic(THREE), null);
		//
		deleteOnExit(file);
		//
		// xls
		//
		try (final Workbook workbook = new HSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			WorkbookUtil.write(workbook, os);
			//
		} // try
			//
		encrypt(file, null, SPACE);
		//
		// xlsx
		//
		try (final Workbook workbook = new XSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			WorkbookUtil.write(workbook, os);
			//
		} // try
			//
		encrypt(file, null, SPACE);
		//
	}

	private static void encrypt(final File file, final EncryptionMode encryptionMode, final String password)
			throws Throwable {
		try {
			METHOD_ENCRYPT.invoke(null, file, encryptionMode, password);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWorkbookByZipFile() throws Throwable {
		//
		Assertions.assertNull(getWorkbookByZipFile(null));
		//
		Assertions.assertNull(getWorkbookByZipFile(new File(".")));
		//
		Assertions.assertNull(getWorkbookByZipFile(new File("pom.xml")));
		//
		final File file = File.createTempFile(randomAlphabetic(THREE), null);
		//
		deleteOnExit(file);
		//
		try (final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
			//
			zos.putNextEntry(new ZipEntry("/[Content_Types].xml"));
			//
			zos.write("1".getBytes());
			//
			zos.close();
			//
		} // try
			//
		Assertions.assertNull(getWorkbookByZipFile(file));
		//
	}

	private static Workbook getWorkbookByZipFile(final File file) throws Throwable {
		try {
			final Object obj = METHOD_GET_WORKBOOK_BY_ZIP_FILE.invoke(null, file);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetEncryptionTableHtml() throws Throwable {
		//
		final URL url = toURL(toURI(new File("pom.xml")));
		//
		Assertions.assertNull(getEncryptionTableHtml(url, null));
		//
		Assertions.assertNull(getEncryptionTableHtml(url, Duration.ZERO));
		//
	}

	private static String getEncryptionTableHtml(final URL url, final Duration timeout) throws Throwable {
		try {
			final Object obj = METHOD_GET_ENCRYPTION_TABLE_HTML.invoke(null, url, timeout);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	@Test
	void testHtml() throws Throwable {
		//
		Assertions.assertEquals(EMPTY, html(element));
		//
	}

	private static String html(final org.jsoup.nodes.Element instance) throws Throwable {
		try {
			final Object obj = METHOD_HTML.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertNull(length(null));
		//
		Assertions.assertNull(length(Util.cast(File.class, Narcissus.allocateInstance(File.class))));
		//
	}

	private static Long length(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Long) {
				return (Long) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateZipFile() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> createZipFile(null, null, Collections.singleton(null)));
		//
	}

	private static void createZipFile(final Object objectMap, final String password, final Iterable<File> files)
			throws Throwable {
		try {
			METHOD_CREATE_ZIP_FILE.invoke(null, objectMap, password, files);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRetrieveAllVoices() throws Throwable {
		//
		Assertions.assertNull(retrieveAllVoices(voiceMapper));
		//
	}

	private static List<Voice> retrieveAllVoices(final VoiceMapper instance) throws Throwable {
		try {
			final Object obj = METHOD_RETRIEVE_ALL_VOICES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSearchVoiceListNamesByVoiceId() throws Throwable {
		//
		Assertions.assertNull(searchVoiceListNamesByVoiceId(null, null));
		//
		Assertions.assertNull(searchVoiceListNamesByVoiceId(voiceMapper, null));
		//
	}

	private static List<String> searchVoiceListNamesByVoiceId(final VoiceMapper instance, final Integer voiceId)
			throws Throwable {
		try {
			final Object obj = METHOD_SEARCH_VOICE_LIST_NAMES_BY_VOICE_ID.invoke(null, instance, voiceId);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetListNames() {
		//
		Assertions.assertDoesNotThrow(() -> setListNames(null, null));
		//
	}

	private static void setListNames(final Voice instance, final Iterable<String> listNames) throws Throwable {
		try {
			METHOD_SET_LIST_NAMES.invoke(null, instance, listNames);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSource() {
		//
		Assertions.assertDoesNotThrow(() -> setSource(null, null));
		//
	}

	private static void setSource(final Voice instance, final String source) throws Throwable {
		try {
			METHOD_SET_SOURCE.invoke(null, instance, source);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPhysicalNumberOfRows() throws Throwable {
		//
		Assertions.assertNull(getPhysicalNumberOfRows(null));
		//
	}

	private static Integer getPhysicalNumberOfRows(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PHYSICAL_NUMBER_OF_ROWS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExportHtml() {
		//
		final Multimap<String, Voice> multimap = (Multimap) this.multimap;
		//
		Assertions.assertDoesNotThrow(() -> exportHtml(null, multimap, null, null, null));
		//
		ih.keySet = Reflection.newProxy(Set.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> exportHtml(null, multimap, null, null, null));
		//
		ih.keySet = Collections.singleton(null);
		//
		final Entry<?, UnaryOperator<Object>> pair = Pair.of(null, UnaryOperator.identity());
		//
		Assertions
				.assertDoesNotThrow(() -> exportHtml(null, multimap, pair, Collections.singletonMap(null, null), null));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> exportHtml(null, multimap, pair, map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> exportHtml(null, multimap, pair, map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> exportHtml(null, multimap, pair, map, null));
		//
	}

	private void exportHtml(final Object objectMap, final Multimap<String, Voice> multimap,
			final Entry<?, UnaryOperator<Object>> filePair, final Map<?, ?> parameters, final Collection<File> files)
			throws Throwable {
		try {
			METHOD_EXPORT_HTML.invoke(instance, objectMap, multimap, filePair, parameters, files);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testActionPerformedForSystemClipboardAnnotated() throws Throwable {
		//
		final Class<?> clz = Util.getClass(instance != null ? instance.getToolkit() : null);
		//
		final Class<? extends Throwable> throwableClassByGetSystemClipboard = getThrowingThrowableClass(clz,
				clz != null ? clz.getDeclaredMethod("getSystemClipboard") : null);
		//
		final Executable executable = () -> actionPerformedForSystemClipboardAnnotated(false, EMPTY);
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}", executable);
			//
		} // if
			//
	}

	private void actionPerformedForSystemClipboardAnnotated(final boolean nonTest, final Object source)
			throws Throwable {
		try {
			METHOD_ACTION_PERFORMED_FOR_SYSTEM_CLIPBOARD_ANNOTATED.invoke(instance, nonTest, source);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testActionPerformedForConversion() throws IllegalAccessException, IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> actionPerformedForConversion(EMPTY));
		//
		FieldUtils.writeDeclaredField(instance, "mapRomaji",
				Collections.singleton(Collections.singletonMap(null, null)), true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForConversion(null));
		//
		final String string = "一";
		//
		final JTextComponent tfTextImport = new JTextField(string);
		//
		FieldUtils.writeDeclaredField(instance, "tfTextImport", tfTextImport, true);
		//
		FieldUtils.writeDeclaredField(instance, "mapRomaji",
				Collections.singleton(Collections.singletonMap(string, null)), true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForConversion(null));
		//
	}

	private void actionPerformedForConversion(final Object source) throws Throwable {
		try {
			METHOD_ACTION_PERFORMED_FOR_CONVERSION.invoke(instance, source);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRun() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRun(true, null));
		//
	}

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> runnable)
			throws Throwable {
		try {
			METHOD_TEST_AND_RUN.invoke(null, b, runnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToCharArray() throws Throwable {
		//
		Assertions.assertNull(toCharArray(null));
		//
		Assertions.assertNotNull(toCharArray(EMPTY));
		//
	}

	private static char[] toCharArray(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_CHAR_ARRAY.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof char[]) {
				return (char[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIfNull() throws Throwable {
		//
		Assertions.assertNull(getIfNull(null, null));
		//
	}

	private static <T, E extends Throwable> T getIfNull(final T object, final FailableSupplier<T, E> defaultSupplier)
			throws Throwable {
		try {
			return (T) METHOD_GET_IF_NULL.invoke(null, object, defaultSupplier);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetLanguage() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setLanguage(null, null));
		//
	}

	private static void setLanguage(final Voice instance, final String language) throws Throwable {
		try {
			METHOD_SET_LANGUAGE.invoke(null, instance, language);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLanguage() throws Throwable {
		//
		Assertions.assertNull(getLanguage(null));
		//
	}

	private static String getLanguage(final Voice instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LANGUAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetBooleanValue() throws Throwable {
		//
		Assertions.assertNull(getBooleanValue(null));
		//
		Assertions.assertEquals(Boolean.FALSE, getBooleanValue(new CellValue(null)));
		//
	}

	private static Boolean getBooleanValue(final CellValue instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_BOOLEAN_VALUE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetResponseCode() throws Throwable {
		//
		Assertions.assertNull(getResponseCode(null));
		//
	}

	private static Integer getResponseCode(final HttpURLConnection instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_RESPONSE_CODE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToRuntimeException() throws Throwable {
		//
		Assertions.assertNull(toRuntimeException(null));
		//
		final RuntimeException runtimeException = new RuntimeException();
		//
		Assertions.assertSame(runtimeException, toRuntimeException(runtimeException));
		//
		final Throwable throwable = new Throwable();
		//
		Assertions.assertSame(throwable, ExceptionUtils.getRootCause(toRuntimeException(throwable)));
		//
	}

	private static RuntimeException toRuntimeException(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_RUNTIME_EXCEPTION.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof RuntimeException) {
				return (RuntimeException) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAlgorithm() throws Throwable {
		//
		Assertions.assertNull(getAlgorithm(null));
		//
		final String algorithm = "SHA-512";
		//
		Assertions.assertEquals(algorithm, getAlgorithm(MessageDigest.getInstance(algorithm)));
		//
	}

	private static String getAlgorithm(final MessageDigest instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ALGORITHM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Component[]) null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Component) null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Iterable) null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Iterable) iterable));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, Collections.singleton(null)));
		//
		final MH mh = new MH();
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Object object = newInstance(getDeclaredConstructor(proxyFactory.createClass()));
		//
		if (object instanceof ProxyObject) {
			//
			((ProxyObject) object).setHandler(mh);
			//
		} // if
			//
		final Component component = Util.cast(Component.class, object);
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(component, null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(component, () -> Double.valueOf(0)));
		//
	}

	private static void setPreferredWidth(final int width, final Component... cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH_ARRAY.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH_ITERABLE.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setPreferredWidth(final Component component, final Supplier<Double> supplier) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH_2.invoke(null, component, supplier);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValueFromCell() throws Throwable {
		//
		Assertions.assertNull(getValueFromCell(null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, newInstance(constructor));
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		final Method setObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
				: null;
		//
		Assertions.assertDoesNotThrow(() -> invoke(setObject, objectMap, Field.class, null));
		//
		Assertions.assertDoesNotThrow(() -> invoke(setObject, objectMap, Cell.class, null));
		//
		Assertions.assertNull(getValueFromCell(objectMap));
		//
		Assertions.assertDoesNotThrow(() -> invoke(setObject, objectMap, Cell.class, cell));
		//
		Assertions.assertNull(getValueFromCell(objectMap));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setObject, objectMap, Field.class, getDeclaredField(Voice.class, "language")));
		//
		Assertions.assertEquals(Unit.with(null), getValueFromCell(objectMap));
		//
		// java.lang.Integer
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setObject, objectMap, Field.class, getDeclaredField(Voice.class, "id")));
		//
		Assertions.assertEquals(Unit.with(null), getValueFromCell(objectMap));
		//
		// java.lang.Enum
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setObject, objectMap, Field.class, getDeclaredField(Voice.class, "yomi")));
		//
		Assertions.assertEquals(Unit.with(null), getValueFromCell(objectMap));
		//
		// java.lang.Iterable
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setObject, objectMap, Field.class, getDeclaredField(Voice.class, "listNames")));
		//
		Assertions.assertDoesNotThrow(() -> invoke(setObject, objectMap, ObjectMapper.class, null));
		//
		Assertions.assertEquals(Unit.with(null), getValueFromCell(objectMap));
		//
		// java.lang.Boolean
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setObject, objectMap, Field.class, getDeclaredField(Voice.class, "isKanji")));
		//
		Assertions.assertDoesNotThrow(() -> invoke(setObject, objectMap, FormulaEvaluator.class, null));
		//
		Assertions.assertNull(getValueFromCell(objectMap));
		//
	}

	private static IValue0<?> getValueFromCell(final Object objectMap) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_FROM_CELL.invoke(null, objectMap);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMp3Tags() throws Throwable {
		//
		Assertions.assertNull(getMp3Tags(null));
		//
		Assertions.assertNull(getMp3Tags(instance));
		//
	}

	private static String[] getMp3Tags(final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MP3_TAGS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testKeyReleasedForTextImport() {
		//
		Assertions.assertDoesNotThrow(() -> keyReleasedForTextImport((Multimap) multimap, null, null));
		//
		ih.multiMapEntries = Collections.singleton(null);
		//
		Assertions.assertDoesNotThrow(() -> keyReleasedForTextImport((Multimap) multimap, null, null));
		//
		ih.multiMapEntries = Reflection.newProxy(Collection.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> keyReleasedForTextImport((Multimap) multimap, null, null));
		//
		ih.multiMapEntries = Collections.singleton(Pair.of(null, EMPTY));
		//
		Assertions.assertDoesNotThrow(() -> keyReleasedForTextImport((Multimap) multimap, null, null));
		//
		ih.multiMapEntries = Collections.singleton(Pair.of(null, null));
		//
		Assertions.assertDoesNotThrow(() -> keyReleasedForTextImport((Multimap) multimap, null, null));
		//
	}

	private static void keyReleasedForTextImport(final Multimap<String, String> multiMap,
			final JTextComponent jTextComponent, final ComboBoxModel<String> comboBoxModel) throws Throwable {
		try {
			METHOD_KEY_RELEASED_FOR_TEXT_IMPORT.invoke(null, multiMap, jTextComponent, comboBoxModel);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertTrue(isStatic(Objects.class.getDeclaredMethod("hashCode", Object.class)));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportByWorkbookFiles() {
		//
		Assertions.assertDoesNotThrow(() -> importByWorkbookFiles(null, false));
		//
		Assertions.assertDoesNotThrow(() -> importByWorkbookFiles(new File[] { null }, false));
		//
	}

	private void importByWorkbookFiles(final File[] fs, final boolean headless) throws Throwable {
		try {
			METHOD_IMPORT_BY_WORK_BOOK_FILES.invoke(instance, fs, headless);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testActionPerformedForExportButtons() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> actionPerformedForExportButtons(EMPTY, false));
		//
	}

	private void actionPerformedForExportButtons(final Object source, final boolean headless) throws Throwable {
		try {
			METHOD_ACTION_PERFORMED_FOR_EXPORT_BUTTONS.invoke(instance, source, headless);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimapByListNames() throws Throwable {
		//
		Assertions.assertNull(createMultimapByListNames((Iterable) iterable));
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(createMultimapByListNames((Iterable) iterable));
		//
		final Voice voice = new Voice();
		//
		voice.setListNames((Iterable) iterable);
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.singleton(voice));
			//
		} // if
			//
		Assertions.assertNull(createMultimapByListNames((Iterable) iterable));
		//
		voice.setListNames(Arrays.asList(null, EMPTY, SPACE));
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.singleton(voice));
			//
		} // if
			//
		Assertions.assertEquals(ImmutableMultimap.of(SPACE, voice), createMultimapByListNames((Iterable) iterable));
		//
	}

	private static Multimap<String, Voice> createMultimapByListNames(final Iterable<Voice> voices) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_BY_LIST_NAMES.invoke(null, voices);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldByName() throws Throwable {
		//
		Assertions.assertNull(getFieldByName(null, null));
		//
	}

	private static Field getFieldByName(final Class<?> clz, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_BY_NAME.invoke(null, clz, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateProviderPlatformJTextComponent() throws Throwable {
		//
		Assertions.assertNotNull(createProviderPlatformJTextComponent(false, null));
		//
	}

	private static JTextComponent createProviderPlatformJTextComponent(final boolean isInstalled,
			final Provider provider) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_PROVIDER_PLATFORM_J_TEXT_COMPONENT.invoke(null, isInstalled, provider);
			if (obj == null) {
				return null;
			} else if (obj instanceof JTextComponent) {
				return (JTextComponent) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExportMicrosoftAccess() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> exportMicrosoftAccess(null, (Iterable) iterable));
		//
		Assertions.assertDoesNotThrow(() -> exportMicrosoftAccess(null, Collections.singleton(null)));
		//
		final Iterable<DataSource> dss = Collections.singleton(Reflection.newProxy(DataSource.class, ih));
		//
		if (ih != null) {
			//
			ih.preparedStatement = Reflection.newProxy(PreparedStatement.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> exportMicrosoftAccess(null, dss));
		//
	}

	private static void exportMicrosoftAccess(final Object objectMap, final Iterable<DataSource> dss) throws Throwable {
		try {
			METHOD_EXPORT_MICROSOFT_ACCESS.invoke(null, objectMap, dss);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportResultSet() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> importResultSet(null, (Iterable) iterable));
		//
		Assertions.assertDoesNotThrow(() -> importResultSet(null, Collections.singleton(null)));
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, createVoiceManagerIH());
		//
		MethodUtils.invokeMethod(objectMap, true, "setObject", Database.class, null);
		//
		MethodUtils.invokeMethod(objectMap, true, "setObject", Connection.class,
				Reflection.newProxy(Connection.class, ih));
		//
		if (ih != null) {
			//
			ih.preparedStatement = Reflection.newProxy(PreparedStatement.class, ih);
			//
			ih.resultSet = Reflection.newProxy(ResultSet.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importResultSet(objectMap, Arrays.asList(null, EMPTY)));
		//
	}

	private static void importResultSet(final Object objectMap, final Iterable<String> tableNames) throws Throwable {
		try {
			METHOD_IMPORT_RESULT_SET.invoke(null, objectMap, tableNames);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateVoiceIdWarningPanel() throws Throwable {
		//
		Assertions.assertNotNull(createVoiceIdWarningPanel(null));
		//
		Assertions.assertNotNull(createVoiceIdWarningPanel(instance));
		//
	}

	private static JPanel createVoiceIdWarningPanel(final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VOICE_ID_WARNING_PANEL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof JPanel) {
				return (JPanel) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMicrosoftWindowsCompatibilityWarningJPanel() throws Throwable {
		//
		Assertions.assertNotNull(createMicrosoftWindowsCompatibilityWarningJPanel(null, null));
		//
		Assertions.assertNotNull(createMicrosoftWindowsCompatibilityWarningJPanel(new MigLayout(), null));
		//
	}

	private static JPanel createMicrosoftWindowsCompatibilityWarningJPanel(final LayoutManager lm,
			final String microsoftWindowsCompatibilitySettingsPageUrl) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL.invoke(null, lm,
					microsoftWindowsCompatibilitySettingsPageUrl);
			if (obj == null) {
				return null;
			} else if (obj instanceof JPanel) {
				return (JPanel) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetEmptyFilePath() throws Throwable {
		//
		Assertions.assertNull(getEmptyFilePath(null));
		//
	}

	private static String getEmptyFilePath(final FileFormatDetails instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_EMPTY_FILE_PATH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetLocaleIdSheet() throws Throwable {
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, createVoiceManagerIH());
		//
		final Method setObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
				: null;
		//
		invoke(setObject, objectMap, Sheet.class, null);
		//
		invoke(setObject, objectMap, LocaleID[].class, new LocaleID[] { null });
		//
		Assertions.assertDoesNotThrow(() -> setLocaleIdSheet(objectMap));
		//
	}

	private static void setLocaleIdSheet(final Object objectMap) throws Throwable {
		try {
			METHOD_SET_LOCALE_ID_SHEET.invoke(null, objectMap);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddLocaleIdRow() throws Throwable {
		//
		Assertions.assertNull(addLocaleIdRow(null, null, null));
		//
		Assertions.assertNull(addLocaleIdRow(null, Collections.singletonList(null), null));
		//
	}

	private static Row addLocaleIdRow(final Object objectMap, final List<Field> fs, final Object instance)
			throws Throwable {
		try {
			final Object obj = METHOD_ADD_LOCALE_ID_ROW.invoke(null, objectMap, fs, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Row) {
				return (Row) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFocusCycleRoot() {
		//
		Assertions.assertDoesNotThrow(() -> setFocusCycleRoot(null, false));
		//
	}

	private static void setFocusCycleRoot(final Container instance, final boolean focusCycleRoot) throws Throwable {
		try {
			METHOD_SET_FOCUS_CYCLE_ROOT.invoke(null, instance, focusCycleRoot);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFocusTraversalPolicy() {
		//
		Assertions.assertDoesNotThrow(() -> setFocusTraversalPolicy(null, null));
		//
	}

	private static void setFocusTraversalPolicy(final Container instance, final FocusTraversalPolicy policy)
			throws Throwable {
		try {
			METHOD_SET_FOCUS_TRAVERSAL_POLICY.invoke(null, instance, policy);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetComponents() throws Throwable {
		//
		Assertions.assertNull(getComponents(null));
		//
	}

	private static Component[] getComponents(final Container instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_COMPONENTS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Component[]) {
				return (Component[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWorkbookClassFailableSupplierMap() throws Throwable {
		//
		final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map = IValue0Util
				.getValue0(getWorkbookClassFailableSupplierMap());
		//
		if (map != null) {
			//
			map.forEach((k, v) -> {
				//
				Assertions.assertNotNull(v != null ? v.get() : null);
				//
			});
			//
		} // if
			//
	}

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> getWorkbookClassFailableSupplierMap()
			throws Throwable {
		try {
			final Object obj = METHOD_GET_WORKBOOK_CLASS_FAILABLE_SUPPLIER_MAP.invoke(instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testgGetDeclaredConstructor() throws Throwable {
		//
		Assertions.assertNull(getDeclaredConstructor(null));
		//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> clz, final Class<?>... parameterTypes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_CONSTRUCTOR.invoke(null, clz, parameterTypes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Constructor) {
				return (Constructor) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNewInstance() throws Throwable {
		//
		Assertions.assertNull(newInstance(null));
		//
	}

	private static <T> T newInstance(final Constructor<T> constructor, final Object... initargs) throws Throwable {
		try {
			return (T) METHOD_NEW_INSTANCE.invoke(null, constructor, initargs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWriter() throws Throwable {
		//
		Assertions.assertNull(getWriter(null));
		//
		Assertions.assertEquals(Unit.with(null), getWriter(Narcissus.allocateInstance(SXSSFSheet.class)));
		//
	}

	private static IValue0<Object> getWriter(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WRITER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWorkbookClass() throws Throwable {
		//
		Assertions.assertNull(getWorkbookClass(null, null));
		//
		final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map = Reflection
				.newProxy(Map.class, ih);
		//
		Assertions.assertNull(getWorkbookClass(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertNull(getWorkbookClass(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertNull(getWorkbookClass(map, null));
		//
	}

	private static IValue0<Class<? extends Workbook>> getWorkbookClass(
			final Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>> map, final String string)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_WORK_BOOK_CLASS.invoke(null, map, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemPrintStreamByFieldName() throws Throwable {
		//
		Assertions.assertNull(getSystemPrintStreamByFieldName(null));
		//
	}

	private static PrintStream getSystemPrintStreamByFieldName(final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_PRINT_STREAM_BY_FIELD_NAME.invoke(null, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof PrintStream) {
				return (PrintStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIfElse() {
		//
		Assertions.assertDoesNotThrow(() -> ifElse(false, null, null));
		//
		Assertions.assertDoesNotThrow(() -> ifElse(true, Reflection.newProxy(FailableRunnable.class, ih), null));
		//
	}

	private static <E extends Throwable> void ifElse(final boolean condition, final FailableRunnable<E> runnableTrue,
			final FailableRunnable<E> runnableFalse) throws Throwable {
		try {
			METHOD_IF_ELSE.invoke(null, condition, runnableTrue, runnableFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPageTitle() throws Throwable {
		//
		Assertions.assertEquals(Unit.with(null), getPageTitle(null, null));
		//
		Assertions.assertEquals(Unit.with(null), getPageTitle(EMPTY, null));
		//
		Assertions.assertEquals(Unit.with(null), getPageTitle(SPACE, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> getPageTitle(toString(new File("pom.xml").toURI().toURL()), null));
			//
		} else {
			//
			final String string = toString(toURL(new File("pom.xml").toURI()));
			//
			AssertionsUtil.assertThrowsAndEquals(RuntimeException.class,
					"{localizedMessage=org.opentest4j.AssertionFailedError: java.net.URISyntaxException: Expected authority at index 7: file:// ==> Unexpected exception thrown: org.jsoup.helper.ValidationException: java.net.URISyntaxException: Expected authority at index 7: file://, message=org.opentest4j.AssertionFailedError: java.net.URISyntaxException: Expected authority at index 7: file:// ==> Unexpected exception thrown: org.jsoup.helper.ValidationException: java.net.URISyntaxException: Expected authority at index 7: file://}",
					() -> getPageTitle(string, null));
			//
		} // if
			//
	}

	private static Unit<String> getPageTitle(final String url, final Duration timeout) throws Throwable {
		try {
			final Object obj = METHOD_GET_PAGE_TITLE.invoke(null, url, timeout);
			if (obj == null) {
				return null;
			} else if (obj instanceof Unit) {
				return (Unit) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetHiraganaOrKatakanaAndRomaji() {
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaOrKatakanaAndRomaji(false, false, new Voice(), null));
		//
	}

	private static void setHiraganaOrKatakanaAndRomaji(final boolean hiraganaKatakanaConversion,
			final boolean hiraganaRomajiConversion, final Voice voice, final Jakaroma jakaroma) throws Throwable {
		try {
			METHOD_SET_HIRAGANA_OR_KATAKANA_AND_ROMAJI.invoke(null, hiraganaKatakanaConversion,
					hiraganaRomajiConversion, voice, jakaroma);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMillis() throws Throwable {
		//
		final double second = 1.234;
		//
		Assertions.assertEquals(
				Long.valueOf(BigDecimal.valueOf(second).multiply(BigDecimal.valueOf(1000)).longValueExact()),
				toMillis(Duration.parse(String.format("PT%1$sS", second))));
		//
	}

	private static Long toMillis(final Duration instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_MILLIS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Long) {
				return (Long) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetJlptVocabularyAndLevel() {
		//
		Assertions.assertDoesNotThrow(() -> setJlptVocabularyAndLevel(null));
		//
	}

	private static void setJlptVocabularyAndLevel(final VoiceManager instance) throws Throwable {
		try {
			METHOD_SET_JLPT_VOCABULARY_AND_LEVEL.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLevel() throws Throwable {
		//
		Assertions.assertNull(getLevel(null));
		//
	}

	private static String getLevel(final JlptVocabulary instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LEVEL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddAll() {
		//
		Assertions.assertDoesNotThrow(() -> addAll(null, null));
		//
		Assertions.assertDoesNotThrow(() -> addAll(Collections.emptyList(), null));
		//
		final Collection<Object> collection = new ArrayList<>();
		//
		Assertions.assertDoesNotThrow(() -> addAll(collection, collection));
		//
		Assertions.assertDoesNotThrow(() -> addAll(collection, null));
		//
	}

	private static <E> void addAll(final Collection<E> a, final Collection<? extends E> b) throws Throwable {
		try {
			METHOD_ADD_ALL.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPlayAudio() {
		//
		final Pronunciation pronunciation = new Pronunciation();
		//
		pronunciation.setAudioUrls(Collections.singletonMap(null, null));
		//
		Assertions.assertDoesNotThrow(() -> playAudio(pronunciation, null));
		//
	}

	private static void playAudio(final Pronunciation pronunciation, final Object audioFormat) throws Throwable {
		try {
			METHOD_PLAY_AUDIO.invoke(null, pronunciation, audioFormat);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPlay() {
		//
		Assertions.assertDoesNotThrow(() -> play(Util.cast(Player.class, Narcissus.allocateInstance(Player.class))));
		//
	}

	private static void play(final Player instance) throws Throwable {
		try {
			METHOD_PLAY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPronounicationChanged() {
		//
		final Pronunciation pronunciation = new Pronunciation();
		//
		pronunciation.setAudioUrls(Collections.singletonMap(null, null));
		//
		Assertions.assertDoesNotThrow(() -> pronounicationChanged(pronunciation, null, null, null));
		//
		pronunciation.setPageUrl("A");
		//
		Assertions.assertDoesNotThrow(() -> pronounicationChanged(pronunciation, null, null, null));
		//
	}

	private static void pronounicationChanged(final Pronunciation pronunciation,
			final MutableComboBoxModel<String> mcbmAudioFormat, final String preferredPronunciationAudioFormat,
			final JTextComponent jtc) throws Throwable {
		try {
			METHOD_PRONOUNICATION_CHANGED.invoke(null, pronunciation, mcbmAudioFormat,
					preferredPronunciationAudioFormat, jtc);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemoveElementAt() {
		//
		Assertions.assertDoesNotThrow(() -> removeElementAt(null, 0));
		//
	}

	private static void removeElementAt(final MutableComboBoxModel<?> instance, final int index) throws Throwable {
		try {
			METHOD_REMOVE_ELEMENT_AT.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testActionPerformedForBtnImport() {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForBtnImport(true));
		//
	}

	private void actionPerformedForBtnImport(final boolean headless) throws Throwable {
		try {
			METHOD_ACTION_PERFORMED_FOR_BTN_IMPORT.invoke(instance, headless);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) throws Throwable {
		try {
			final Object obj = METHOD_GET_LIST_CELL_RENDERER_COMPONENT.invoke(null, instance, list, value, index,
					isSelected, cellHasFocus);
			if (obj == null) {
				return null;
			} else if (obj instanceof Component) {
				return (Component) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFile() throws Throwable {
		//
		Assertions.assertNull(getFile(null));
		//
		Assertions.assertNull(getFile(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static String getFile(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPronunciationAudioFileByAudioFormat() throws Throwable {
		//
		final Pronunciation pronunciation = new Pronunciation();
		//
		final Map<String, String> audioUrls = new LinkedHashMap<>(Collections.singletonMap(null, EMPTY));
		//
		pronunciation.setAudioUrls(audioUrls);
		//
		Assertions.assertNull(getPronunciationAudioFileByAudioFormat(pronunciation, null));
		//
		audioUrls.put(null, SPACE);
		//
		Assertions.assertNull(getPronunciationAudioFileByAudioFormat(pronunciation, null));
		//
		final File file = File.createTempFile(
				nextAlphanumeric(randomStringUtils, intValue(TEMP_FILE_MINIMUM_PREFIX_LENGTH, THREE)), null);
		//
		deleteOnExit(file);
		//
		audioUrls.put(null, file != null ? toString(toURL(toURI(file))) : null);
		//
		final File result = getPronunciationAudioFileByAudioFormat(pronunciation, null);
		//
		deleteOnExit(result);
		//
		Assertions.assertEquals(getName(file), getName(result));
		//
	}

	private static File getPronunciationAudioFileByAudioFormat(final Pronunciation pronunciation,
			final Object pronounicationAudioFormat) throws Throwable {
		try {
			final Object obj = METHOD_GET_PRONUNCIATION_AUDIO_FILE_BY_AUDIO_FORMAT.invoke(null, pronunciation,
					pronounicationAudioFormat);
			if (obj == null) {
				return null;
			} else if (obj instanceof File) {
				return (File) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAudioFile() throws Throwable {
		//
		Assertions.assertNull(getAudioFile(true, null, null));
		///
		Assertions.assertNull(getAudioFile(true, null, null, null));
		///
		Assertions.assertNull(getAudioFile(false,
				Util.cast(JFileChooser.class, Narcissus.allocateInstance(JFileChooser.class)), null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			final Executable executable = () -> getAudioFile(false, null, null);
			//
			if (isUnderWindows()) {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}", getHeadlessMessage()), executable);
				//
			} // if
				//
		} // if
			//
	}

	private static File getAudioFile(final boolean headless, final Voice voice,
			final DefaultTableModel defaultTableModel) throws Throwable {
		try {
			final Object obj = METHOD_GET_AUDIO_FILE3.invoke(null, headless, voice, defaultTableModel);
			if (obj == null) {
				return null;
			} else if (obj instanceof File) {
				return (File) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static File getAudioFile(final boolean headless, final JFileChooser jfc, final Voice voice,
			final DefaultTableModel defaultTableModel) throws Throwable {
		try {
			final Object obj = METHOD_GET_AUDIO_FILE4.invoke(null, headless, jfc, voice, defaultTableModel);
			if (obj == null) {
				return null;
			} else if (obj instanceof File) {
				return (File) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllAttributesMatched() throws Throwable {
		//
		Assertions.assertTrue(isAllAttributesMatched(null, null));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(Reflection.newProxy(Entry.class, ih));
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		final AttributeAccessor aa = Reflection.newProxy(AttributeAccessor.class, ih);
		//
		Assertions.assertFalse(isAllAttributesMatched(map, aa));
		//
		if (ih != null) {
			//
			Util.put(ih.getAttributeMap(), null, null);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, aa));
		//
		if (ih != null) {
			//
			ih.value = EMPTY;
			//
		} // if
			//
		Assertions.assertFalse(isAllAttributesMatched(map, aa));
		//
	}

	private static boolean isAllAttributesMatched(final Map<?, ?> attributes, final AttributeAccessor aa)
			throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_ATTRIBUTES_MATCHED.invoke(null, attributes, aa);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateFunctionForBtnConvertToHiraganaOrKatakana() throws Throwable {
		//
		final Function<Collection<?>, IValue0<?>> function = createFunctionForBtnConvertToHiraganaOrKatakana(null);
		//
		Assertions.assertNull(Util.apply(function, null));
		//
		Assertions.assertNull(Util.apply(function, Collections.emptySet()));
		//
		Assertions.assertEquals(Unit.with(EMPTY), Util.apply(function, Collections.singleton(EMPTY)));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(Util.apply(function, Collections.nCopies(TWO, null)));
			//
		} // if
			//
	}

	private static Function<Collection<?>, IValue0<?>> createFunctionForBtnConvertToHiraganaOrKatakana(
			final String title) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_FUNCTION_FOR_BTN_CONVERT_TO_HIRAGANA.invoke(null, title);
			if (obj == null) {
				return null;
			} else if (obj instanceof Function) {
				return (Function) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriter() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(writer(null));
			//
		} // if
			//
		if (!isConsoleWriterMethodThrowException()) {
			//
			Assertions.assertNull(writer(Util.cast(Console.class, Narcissus.allocateInstance(Console.class))));
			//
		} // if
			//
	}

	private static boolean isConsoleWriterMethodThrowException() throws Throwable {
		//
		final Class<?> clz = Console.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			// java.io.Console.writer()
			//
			return Objects
					.equals(Arrays.asList(INVOKESTATIC.class, ATHROW.class),
							new FailableStream<>(
									testAndApply(Objects::nonNull,
											InstructionListUtil
													.getInstructions(MethodGenUtil
															.getInstructionList(testAndApply(Objects::nonNull,
																	JavaClassUtil.getMethod(
																			ClassParserUtil.parse(
																					testAndApply(Objects::nonNull, is,
																							x -> new ClassParser(x,
																									null),
																							null)),
																			clz != null
																					? clz.getDeclaredMethod("writer")
																					: null),
																	x -> new MethodGen(x, null, null), null))),
											Arrays::stream, null))
									.map(x -> Util.getClass(x)).collect(Collectors.toList()));
			//
		} // try
			//
	}

	private static PrintWriter writer(final Console instance) throws Throwable {
		try {
			final Object obj = METHOD_WRITER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof PrintWriter) {
				return (PrintWriter) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReadLine() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(readLine(null, null));
			//
		} // if
			//
		final Console console = Util.cast(Console.class, Narcissus.allocateInstance(Console.class));
		//
		final boolean isConsoleReadLineMethodThrowException = isConsoleReadLineMethodThrowException();
		//
		if (!isConsoleReadLineMethodThrowException) {
			//
			Assertions.assertNull(readLine(console, null));
			//
		} // if
			//
		final List<Field> fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(f -> f != null && Objects.equals(f.getName(), "writeLock")).toList();
		//
		final Field f = fs != null && fs.size() == 1 ? fs.get(0) : null;
		//
		if (f != null) {
			//
			Narcissus.setObjectField(console, f, new Object());
			//
		} // if
			//
		if (!isConsoleReadLineMethodThrowException) {
			//
			Assertions.assertNull(readLine(console, null));
			//
		} // if
			//
	}

	private static boolean isConsoleReadLineMethodThrowException() throws Throwable {
		//
		final Class<?> clz = Console.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))
				: null) {
			//
			// java.io.Console.readLine(java.lang.String,java.lang.Object...)
			//
			return Objects
					.equals(Arrays.asList(INVOKESTATIC.class, ATHROW.class),
							new FailableStream<>(
									testAndApply(Objects::nonNull,
											InstructionListUtil
													.getInstructions(
															MethodGenUtil.getInstructionList(testAndApply(
																	Objects::nonNull, JavaClassUtil.getMethod(
																			ClassParserUtil.parse(
																					testAndApply(Objects::nonNull, is,
																							x -> new ClassParser(x,
																									null),
																							null)),
																			clz != null ? clz.getDeclaredMethod(
																					"readLine", String.class,
																					Object[].class) : null),
																	x -> new MethodGen(x, null, null), null))),
											Arrays::stream, null))
									.map(x -> Util.getClass(x)).collect(Collectors.toList()));
			//
		} // try
			//
	}

	private static String readLine(final Console instance, final String fmt, final Object... args) throws Throwable {
		try {
			final Object obj = METHOD_READ_LINE.invoke(null, instance, fmt, args);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPrintln() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> println(null, null));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> println(Util.cast(PrintWriter.class, Narcissus.allocateInstance(PrintWriter.class)), null));
		//
		try (final OutputStream os = new ByteArrayOutputStream(); final PrintWriter pw = new PrintWriter(os)) {
			//
			Assertions.assertDoesNotThrow(() -> println(pw, null));
			//
		} // try
			//
	}

	private static void println(final PrintWriter instance, final String x) throws Throwable {
		try {
			METHOD_PRINT_LN.invoke(null, instance, x);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPitchAccentImage() {
		//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImage(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImage(new Voice(), null));
		//
	}

	private static void setPitchAccentImage(final Voice instance, final ByteArray pitchAccentImage) throws Throwable {
		try {
			METHOD_SET_PITCH_ACCENT_IMAGE.invoke(null, instance, pitchAccentImage);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNumericCellValue() throws Throwable {
		//
		Assertions.assertNull(getNumericCellValue(null));
		//
		final Double d = Double.valueOf(ZERO);
		//
		if (ih != null) {
			//
			ih.numericCellValue = d;
			//
		} // if
			//
		Assertions.assertEquals(d, getNumericCellValue(cell));
		//
	}

	private static Double getNumericCellValue(final Cell instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NUMERIC_CELL_VALUE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetAutoFilter() throws Throwable {
		//
		try (final Workbook wb = new XSSFWorkbook()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
			SheetUtil.createRow(sheet, sheet != null ? sheet.getPhysicalNumberOfRows() : 0);
			//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
			SheetUtil.createRow(sheet, sheet != null ? sheet.getPhysicalNumberOfRows() : 0);
			//
			final Row row = sheet != null ? sheet.getRow(sheet.getLastRowNum()) : null;
			//
			if (row != null) {
				//
				row.createCell(row.getPhysicalNumberOfCells());
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
		} // try
			//
	}

	private static void setAutoFilter(final Sheet sheet) throws Throwable {
		try {
			METHOD_SET_AUTO_FILTER.invoke(null, sheet);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateByteArray() throws Throwable {
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
			//
		} // if
			//
		final RenderedImage renderedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertEquals("{\"content\":\"\",\"mimeType\":\"application/octet-stream\"}",
				ObjectMapperUtil.writeValueAsString(objectMapper, createByteArray(renderedImage, null, false)));
		//
		Assertions.assertEquals("{\"content\":\"\",\"mimeType\":\"application/octet-stream\"}",
				ObjectMapperUtil.writeValueAsString(objectMapper, createByteArray(renderedImage, EMPTY, false)));
		//
	}

	private static ByteArray createByteArray(final RenderedImage image, final String format, final boolean headless)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_BYTE_ARRAY.invoke(null, image, format, headless);
			if (obj == null) {
				return null;
			} else if (obj instanceof ByteArray) {
				return (ByteArray) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDoubleValue() throws Throwable {
		//
		final double d = 0;
		//
		Assertions.assertEquals(d, doubleValue(null, d));
		//
	}

	private static double doubleValue(final Number instance, final double defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_DOUBLE_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Double) {
				return ((Double) obj).doubleValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetElementAt() throws Throwable {
		//
		Assertions.assertNull(getElementAt(null, ONE));
		//
	}

	private static <E> E getElementAt(final ListModel<E> instance, final int index) throws Throwable {
		try {
			return (E) METHOD_GET_ELEMENT_AT.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetImageFormat() throws Throwable {
		//
		Assertions.assertNull(getImageFormat(null, null));
		//
	}

	private static String getImageFormat(final IValue0<String> iValue0, final Collection<String> imageFormats)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_IMAGE_FORMAT.invoke(null, iValue0, imageFormats);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIValue0FromMapsByKey() throws Throwable {
		//
		final Iterable iterable = Reflection.newProxy(Iterable.class, ih);
		//
		Assertions.assertNull(getIValue0FromMapsByKey(iterable, null, null));
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getIValue0FromMapsByKey(iterable, null, null));
		//
		final Map<?, ?> map = Collections.singletonMap(null, null);
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.singleton(map));
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(null), getIValue0FromMapsByKey(iterable, null, null));
		//
		if (ih != null) {
			//
			ih.iterator = iterator(Collections.nCopies(2, map));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> getIValue0FromMapsByKey(iterable, null, null));
		//
	}

	private static IValue0<?> getIValue0FromMapsByKey(final Iterable<Map> maps, final Object key,
			final Function<Collection<?>, IValue0<?>> function) throws Throwable {
		try {
			final Object obj = METHOD_GET_I_VALUE0_FROM_MAPS_BY_KEY.invoke(null, maps, key, function);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllCharactersAllowed() throws Throwable {
		//
		Assertions.assertTrue(isAllCharactersAllowed(null, null));
		//
		Assertions.assertTrue(isAllCharactersAllowed(EMPTY, null));
		//
		Assertions.assertTrue(isAllCharactersAllowed(EMPTY, new char[] {}));
		//
		final String string = StringUtils.repeat(' ', 1);
		//
		Assertions.assertTrue(isAllCharactersAllowed(string, new char[] { ' ' }));
		//
		Assertions.assertFalse(isAllCharactersAllowed(string, new char[] { 'A' }));
		//
	}

	private static boolean isAllCharactersAllowed(final CharSequence cs, final char[] allowedChars) throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_CHARACTERS_ALLOWED.invoke(null, cs, allowedChars);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValueCollectionByKey() throws Throwable {
		//
		Assertions.assertNull(getValueCollectionByKey(null, null));
		//
		Assertions.assertNull(getValueCollectionByKey(Reflection.newProxy(Iterable.class, ih), null));
		//
		Assertions.assertNull(getValueCollectionByKey(Collections.emptySet(), null));
		//
		Assertions.assertNull(getValueCollectionByKey(Collections.singleton(null), null));
		//
		final Iterable<Map> maps = Collections.singleton(Collections.singletonMap(null, null));
		//
		Assertions.assertNull(getValueCollectionByKey(maps, EMPTY));
		//
		Assertions.assertEquals(Collections.singletonList(null), getValueCollectionByKey(maps, null));
		//
	}

	private static Collection<Object> getValueCollectionByKey(final Iterable<Map> maps, final Object key)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_COLLECTION_BY_KEY.invoke(null, maps, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateYomiNameMap() throws Throwable {
		//
		Assertions.assertNotNull(createYomiNameMap());
		//
		Assertions.assertNull(createYomiNameMap(null));
		//
		Assertions.assertNull(createYomiNameMap(Collections.singleton(null)));
		//
		Assertions.assertNull(createYomiNameMap(Reflection.newProxy(Iterable.class, ih)));
		//
	}

	private static Map<String, String> createYomiNameMap() throws Throwable {
		try {
			final Object obj = METHOD_CREATE_YOMI_NAME_MAP0.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Map<String, String> createYomiNameMap(final Iterable<Pair<String, String>> pairs) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_YOMI_NAME_MAP1.invoke(null, pairs);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNumber() throws Throwable {
		//
		Assertions.assertNull(getNumber(null, Collections.singleton(Boolean.class.getDeclaredField("TRUE"))));
		//
		final Collection<?> collection = new ArrayList<>();
		//
		Assertions.assertEquals(Unit.with(Integer.valueOf(collection.size())),
				getNumber(collection, Collections.singleton(ArrayList.class.getDeclaredField("size"))));
		//
		Assertions.assertEquals(Unit.with(Long.valueOf(Long.MAX_VALUE)),
				getNumber(null, Collections.singleton(Long.class.getDeclaredField("MAX_VALUE"))));
		//
	}

	private static IValue0<Number> getNumber(final Object instance, final Iterable<Field> fs) throws Throwable {
		try {
			final Object obj = METHOD_GET_NUMBER.invoke(null, instance, fs);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRenderer() throws Throwable {
		//
		Assertions.assertNull(getRenderer(Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class))));
		//
	}

	private static <E> ListCellRenderer<? super E> getRenderer(final JComboBox<E> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_RENDERER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ListCellRenderer) {
				return (ListCellRenderer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetRenderer() {
		//
		Assertions.assertDoesNotThrow(
				() -> setRenderer(Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class)), null));
		//
	}

	private static <E> void setRenderer(final JComboBox<E> instance, final ListCellRenderer<? super E> aRenderer)
			throws Throwable {
		try {
			METHOD_SET_RENDERER.invoke(null, instance, aRenderer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSorted() throws Throwable {
		//
		Assertions.assertNull(sorted(null, null));
		//
		Assertions.assertSame(stream, sorted(stream, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertSame(empty, sorted(empty, null));
		//
	}

	private static <T> Stream<T> sorted(final Stream<T> instance, final Comparator<? super T> comparator)
			throws Throwable {
		try {
			final Object obj = METHOD_SORTED.invoke(null, instance, comparator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetId3v1Tag() throws Throwable {
		//
		Assertions.assertNull(getId3v1Tag(null));
		//
		Assertions.assertNull(getId3v1Tag(Util.cast(Mp3File.class, Narcissus.allocateInstance(Mp3File.class))));
		//
	}

	private static ID3v1 getId3v1Tag(final Mp3File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ID3V1_TAG.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ID3v1) {
				return (ID3v1) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetId3v2Tag() throws Throwable {
		//
		Assertions.assertNull(getId3v2Tag(null));
		//
		Assertions.assertNull(getId3v2Tag(Util.cast(Mp3File.class, Narcissus.allocateInstance(Mp3File.class))));
		//
	}

	private static ID3v2 getId3v2Tag(final Mp3File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ID3V2_TAG.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ID3v2) {
				return (ID3v2) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddValidationData() {
		//
		Assertions.assertDoesNotThrow(() -> addValidationData(null, null));
		//
	}

	private static void addValidationData(final Sheet instance, final DataValidation dataValidation) throws Throwable {
		try {
			METHOD_ADD_VALIDATION_DATA.invoke(null, instance, dataValidation);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateImportResultPanel() throws Throwable {
		//
		Assertions.assertNotNull(createImportResultPanel(null));
		//
	}

	private JPanel createImportResultPanel(final LayoutManager layoutManager) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_IMPORT_RESULT_PANEL.invoke(instance, layoutManager);
			if (obj == null) {
				return null;
			} else if (obj instanceof JPanel) {
				return (JPanel) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetURL() throws Throwable {
		//
		Assertions.assertNull(getURL(null));
		//
		Assertions
				.assertNull(getURL(Util.cast(HyperlinkEvent.class, Narcissus.allocateInstance(HyperlinkEvent.class))));
		//
	}

	private static URL getURL(final HyperlinkEvent instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddHyperlinkListener() {
		//
		Assertions.assertDoesNotThrow(() -> addHyperlinkListener(null, null));
		//
		Assertions.assertDoesNotThrow(() -> addHyperlinkListener(
				Util.cast(JEditorPane.class, Narcissus.allocateInstance(JEditorPane.class)), null));
		//
	}

	private static void addHyperlinkListener(final JEditorPane instance, final HyperlinkListener listener)
			throws Throwable {
		try {
			METHOD_ADD_HYPER_LINK_LISTENER.invoke(null, instance, listener);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testShowOpenDialog() throws Throwable {
		//
		Assertions.assertEquals(JFileChooser.ERROR_OPTION, showOpenDialog(null, null));
		//
	}

	private static int showOpenDialog(final JFileChooser instance, final Component parent) throws Throwable {
		try {
			final Object obj = METHOD_SHOW_OPEN_DIALOG.invoke(null, instance, parent);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNull(openStream(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static InputStream openStream(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testActionPerformedForImportFileTemplate() {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForImportFileTemplate(false, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForImportFileTemplate(true, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformedForImportFileTemplate(false,
				Util.cast(JFileChooser.class, Narcissus.allocateInstance(JFileChooser.class))));
		//
	}

	private void actionPerformedForImportFileTemplate(final boolean headless, final JFileChooser jfc) throws Throwable {
		try {
			METHOD_ACTION_PERFORMED_FOR_IMPORT_FILE_TEMPLATE.invoke(instance, headless, jfc);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSubmit() {
		//
		Assertions.assertDoesNotThrow(() -> submit(null, null));
		//
	}

	private static void submit(final ExecutorService instance, final Runnable task) throws Throwable {
		try {
			METHOD_SUBMIT.invoke(null, instance, task);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenConnection() throws Throwable {
		//
		Assertions.assertNull(openConnection(null));
		//
		Assertions.assertNull(openConnection(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static URLConnection openConnection(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_CONNECTION.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URLConnection) {
				return (URLConnection) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFormatHex() throws Throwable {
		//
		Assertions.assertNull(formatHex(null, null));
		//
		final HexFormat hexFormat = Util.cast(HexFormat.class, Narcissus.allocateInstance(HexFormat.class));
		//
		Assertions.assertNull(formatHex(hexFormat, null));
		//
		Assertions.assertEquals("", formatHex(hexFormat, new byte[] {}));
		//
	}

	private static String formatHex(final HexFormat instance, final byte[] bytes) throws Throwable {
		try {
			final Object obj = METHOD_FORMAT_HEX.invoke(null, instance, bytes);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedIndex() {
		//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, null));
		//
		final Number zero = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, zero));
		//
		jTabbedPane.addTab(null, null);
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, zero));
		//
	}

	private static void setSelectedIndex(final JTabbedPane instance, final Number index) throws Throwable {
		try {
			METHOD_SET_SELECTED_INDEX.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIh1() throws Throwable {
		//
		final InvocationHandler ih = createVoiceManagerIH();
		//
		if (ih != null) {
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{}", () -> ih.invoke(null, null, null));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.getObject(java.lang.Class)
			//
			final Method getObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
					: null;
			//
			final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(objectMap, getObject, null));
			//
			final Object[] empty = new Object[] {};
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(objectMap, getObject, empty));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.containsObject(java.lang.Class)
			//
			final Method containsObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("containsObject", Class.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=containsObject, message=containsObject}",
					() -> ih.invoke(objectMap, containsObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=containsObject, message=containsObject}",
					() -> ih.invoke(objectMap, containsObject, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(objectMap, containsObject, new Object[] { null }));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
			//
			final Method setObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(objectMap, setObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(objectMap, setObject, empty));
			//
			// org.springframework.context.support.VoiceManager$BooleanMap.setBoolean(java.lang.String,boolean)
			//
			final Object booleanMap = Reflection.newProxy(CLASS_BOOLEAN_MAP, ih);
			//
			final Method setBoolean = CLASS_BOOLEAN_MAP != null
					? CLASS_BOOLEAN_MAP.getDeclaredMethod("setBoolean", String.class, Boolean.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setBoolean, message=setBoolean}",
					() -> ih.invoke(booleanMap, setBoolean, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setBoolean, message=setBoolean}",
					() -> ih.invoke(booleanMap, setBoolean, empty));
			//
			Assertions.assertDoesNotThrow(() -> ih.invoke(booleanMap, setBoolean, new Object[] { null, null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.getObject(int)
			//
			final Class<?> classIntMap = Util.forName("org.springframework.context.support.VoiceManager$IntMap");
			//
			final Object intMap = Reflection.newProxy(classIntMap, ih);
			//
			final Method intMapGetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("getObject", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(intMap, intMapGetObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(intMap, intMapGetObject, empty));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=Key [null] Not Found, message=Key [null] Not Found}",
					() -> ih.invoke(intMap, intMapGetObject, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.containsKey(int)
			//
			final Method intMapContainsKey = classIntMap != null
					? classIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intMap, intMapContainsKey, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intMap, intMapContainsKey, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(intMap, intMapContainsKey, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.setObject(int,java.lang.Object)
			//
			final Method intMapSetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("setObject", Integer.TYPE, Object.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(intMap, intMapSetObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(intMap, intMapSetObject, empty));
			//
		} // if
			//
	}

	@Test
	void testIh2() throws Throwable {
		//
		final InvocationHandler ih = createVoiceManagerIH();
		//
		if (ih != null) {
			//
			// org.springframework.context.support.VoiceManager$IntIntMap.setInt(int,int)
			//
			final Class<?> classIntIntMap = Util.forName("org.springframework.context.support.VoiceManager$IntIntMap");
			//
			final Method intIntMapSetInt = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("setInt", Integer.TYPE, Integer.TYPE)
					: null;
			//
			final Object intIntMap = Reflection.newProxy(classIntIntMap, ih);
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setInt, message=setInt}",
					() -> ih.invoke(intIntMap, intIntMapSetInt, null));
			//
			final Object[] empty = new Object[] {};
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setInt, message=setInt}",
					() -> ih.invoke(intIntMap, intIntMapSetInt, empty));
			//
			Assertions.assertDoesNotThrow(
					() -> ih.invoke(intIntMap, intIntMapSetInt, new Object[] { Integer.valueOf(ONE), TWO }));
			//
			// org.springframework.context.support.VoiceManager$IntIntMap.containsKey(int)
			//
			final Method intIntMapContainsKey = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intIntMap, intIntMapContainsKey, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intIntMap, intIntMapContainsKey, empty));
			//
			Assertions.assertEquals(Boolean.TRUE,
					ih.invoke(intIntMap, intIntMapContainsKey, new Object[] { Integer.valueOf(ONE) }));
			//
			// org.springframework.context.support.VoiceManager$IH.isArray(java.lang.invoke.TypeDescriptor.OfField)
			//
			final Method isArray = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("isArray", OfField.class) : null;
			//
			if (isArray != null) {
				//
				isArray.setAccessible(true);
				//
			} // if
				//
			Assertions.assertEquals(Boolean.FALSE, invoke(isArray, null, (Object) null));
			//
			final Class<?> clz = byte[].class;
			//
			Assertions.assertEquals(Boolean.TRUE, invoke(isArray, null, clz));
			//
			// java.lang.Runnable
			//
			final Runnable runnable = Reflection.newProxy(Runnable.class, ih);
			//
			final Method run = Runnable.class.getDeclaredMethod("run");
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable is null, message=runnable is null}",
					() -> ih.invoke(runnable, run, null));
			//
			FieldUtils.writeDeclaredField(ih, "runnable", runnable, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable==proxy, message=runnable==proxy}",
					() -> ih.invoke(runnable, run, null));
			//
			FieldUtils.writeDeclaredField(ih, "runnable", Reflection.newProxy(Runnable.class, ih), true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable==proxy, message=runnable==proxy}",
					() -> ih.invoke(runnable, run, null));
			//
			final Method toString = Object.class.getDeclaredMethod("toString");
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=toString, message=toString}",
					() -> ih.invoke(runnable, toString, null));
			//
			// printStackTrace(java.lang.Throwable)
			//
			final Method printStackTrace = CLASS_IH != null
					? CLASS_IH.getDeclaredMethod("printStackTrace", Throwable.class)
					: null;
			//
			Assertions.assertNull(invoke(printStackTrace, null, (Object) null));
			//
			Assertions.assertNull(invoke(printStackTrace, null, new Throwable()));
			//
			// printStackTrace(java.lang.Throwable)
			//
//			final Method printStackTrace = CLASS_IH != null
//					? CLASS_IH.getDeclaredMethod("printStackTrace", Throwable.class)
//					: null;
//			//
//			Assertions.assertNull(invoke(printStackTrace, null, (Object) null));
//			//
//			Assertions.assertNull(invoke(printStackTrace, null, new Throwable()));
			//
		} // if
			//
	}

	private static InvocationHandler createVoiceManagerIH() throws IllegalArgumentException, Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		return Util.cast(InvocationHandler.class, newInstance(constructor));
		//
	}

	@Test
	void testImportTask() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IMPORT_TASK);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Runnable runnable = Util.cast(Runnable.class, newInstance(constructor));
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
		FieldUtils.writeDeclaredField(runnable, "counter", Integer.valueOf(0), true);
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
		// org.springframework.context.support.VoiceManager.ImportTask.infoOrPrintln(org.slf4j.Logger,java.io.PrintStream,java.lang.String)
		//
		final Method infoOrPrintln = CLASS_IMPORT_TASK != null
				? CLASS_IMPORT_TASK.getDeclaredMethod("infoOrPrintln", Logger.class, PrintStream.class, String.class)
				: null;
		//
		Assertions.assertNull(invoke(infoOrPrintln, instance, null, null, null));
		//
		try (final PrintStream ps = new PrintStream(new ByteArrayOutputStream())) {
			//
			invoke(infoOrPrintln, instance, null, ps, null);
			//
		} // try
			//
			// org.springframework.context.support.VoiceManager.ImportTask.add(org.apache.commons.lang3.math.Fraction,org.apache.commons.lang3.math.Fraction)
			//
		final Method add = CLASS_IMPORT_TASK != null
				? CLASS_IMPORT_TASK.getDeclaredMethod("add", Fraction.class, Fraction.class)
				: null;
		//
		Assertions.assertNull(invoke(add, instance, null, null));
		//
		final Fraction a = Fraction.getFraction(1);
		//
		Assertions.assertSame(a, invoke(add, instance, a, null));
		//
		Assertions.assertSame(a, invoke(add, instance, a, Fraction.ZERO));
		//
	}

	@Test
	void testExportTask1() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager.ExportTask.setMp3Title(java.io.File)
		//
		final Method setMp3Title = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setMp3Title", File.class)
				: null;
		//
		if (setMp3Title != null) {
			//
			setMp3Title.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setMp3Title, null, (Object) null));
		//
		Assertions.assertNull(invoke(setMp3Title, null, new File(".")));
		//
		Assertions.assertNull(invoke(setMp3Title, null, new File("pom.xml")));
		//
		// org.springframework.context.support.VoiceManager.ExportTask.min(java.util.stream.Stream,java.util.Comparator)
		//
		final Method min = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("min", Stream.class, Comparator.class)
				: null;
		//
		if (min != null) {
			//
			min.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(min, null, null, null));
		//
		Assertions.assertNull(invoke(min, null, Stream.empty(), null));
		//
		Assertions.assertNull(invoke(min, null, stream, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.showPharse(org.springframework.context.support.VoiceManager,org.apache.commons.lang3.math.Fraction)
		//
		final Method showPharse = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("showPharse", VoiceManager.class, Fraction.class)
				: null;
		//
		if (showPharse != null) {
			//
			showPharse.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(showPharse, null, null, null));
		//
		Assertions.assertNull(invoke(showPharse, null, this.instance, null));
		//
		Assertions.assertNull(invoke(showPharse, null, this.instance, Fraction.ZERO));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.generateOdfPresentationDocuments(java.io.InputStream,boolean,com.google.common.collect.Table)
		//
		final Method generateOdfPresentationDocuments = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("generateOdfPresentationDocuments", CLASS_OBJECT_MAP, Table.class)
				: null;
		//
		if (generateOdfPresentationDocuments != null) {
			//
			generateOdfPresentationDocuments.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(generateOdfPresentationDocuments, null, null, null));
		//
		AssertionsUtil.assertThrowsAndEquals(InvocationTargetException.class, "{}",
				() -> invoke(generateOdfPresentationDocuments, null, null,
						ImmutableTable.of(EMPTY, EMPTY, new Voice())));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newXPath(javax.xml.xpath.XPathFactory)
		//
		final Method newXPath = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("newXPath", XPathFactory.class)
				: null;
		//
		if (newXPath != null) {
			//
			newXPath.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(newXPath, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getParentNode(org.w3c.dom.Node)
		//
		final Method getParentNode = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getParentNode", Node.class)
				: null;
		//
		if (getParentNode != null) {
			//
			getParentNode.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getParentNode, null, node));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.appendChild(org.w3c.dom.Node,org.w3c.dom.Node)
		//
		final Method appendChild = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("appendChild", Node.class, Node.class)
				: null;
		//
		if (appendChild != null) {
			//
			appendChild.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(appendChild, null, null, null));
		//
		Assertions.assertNull(invoke(appendChild, null, node, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.removeChild(org.w3c.dom.Node,org.w3c.dom.Node)
		//
		final Method removeChild = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("removeChild", Node.class, Node.class)
				: null;
		//
		if (removeChild != null) {
			//
			removeChild.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(removeChild, null, node, null));
		//
		Assertions.assertNull(invoke(removeChild, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newTransformer(javax.xml.transform.TransformerFactory)
		//
		final Method newTransformer = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("newTransformer", TransformerFactory.class)
				: null;
		//
		if (newTransformer != null) {
			//
			newTransformer.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(newTransformer, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newTransformer(javax.xml.transform.Transformer,javax.xml.transform.Source,javax.xml.transform.Result)
		//
		final Method transform = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("transform", Transformer.class, Source.class, Result.class)
				: null;
		//
		if (transform != null) {
			//
			transform.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(transform, null, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.cloneNode(org.w3c.dom.Node,boolean)
		//
		final Method cloneNode = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("cloneNode", Node.class, Boolean.TYPE)
				: null;
		//
		if (cloneNode != null) {
			//
			cloneNode.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(cloneNode, null, node, true));
		//
		Assertions.assertNull(invoke(cloneNode, null, null, true));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.replaceText(javax.xml.xpath.XPath,org.w3c.dom.Node,domain.Voice)
		//
		final Method replaceText = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("replaceText", CLASS_OBJECT_MAP, String.class)
				: null;
		//
		if (replaceText != null) {
			//
			replaceText.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(replaceText, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setPluginHref(org.springframework.context.support.VoiceManager$ObjectMap,java.lang.String,boolean,java.lang.String)
		//
		final Method setPluginHref = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setPluginHref", CLASS_OBJECT_MAP, String.class, Boolean.TYPE,
						String.class)
				: null;
		//
		if (setPluginHref != null) {
			//
			setPluginHref.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPluginHref, null, null, null, Boolean.TRUE, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setVariable(org.springframework.expression.EvaluationContext,java.lang.String,java.lang.Object)
		//
		final Method setVariable = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setVariable", EvaluationContext.class, String.class,
						Object.class)
				: null;
		//
		if (setVariable != null) {
			//
			setVariable.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setVariable, null, null, null, null));
		//
	}

	@Test
	void testExportTask2() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager$ExportTask.clone(com.fasterxml.jackson.databind.ObjectMapper,java.lang.Class,java.lang.Object)
		//
		final Method clone = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("clone", ObjectMapper.class, Class.class, Object.class)
				: null;
		//
		if (clone != null) {
			//
			clone.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(clone, null, objectMapper, null, null));
		//
		Assertions.assertNull(invoke(clone, null, objectMapper, Object.class, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setStringFieldDefaultValue(java.lang.Object)
		//
		final Method setStringFieldDefaultValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setStringFieldDefaultValue", Object.class)
				: null;
		//
		if (setStringFieldDefaultValue != null) {
			//
			setStringFieldDefaultValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setStringFieldDefaultValue, null, new Voice()));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.replaceTextContent(java.lang.Object)
		//
		final Method replaceTextContent = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("replaceTextContent", CLASS_OBJECT_MAP, Map.class)
				: null;
		//
		if (replaceTextContent != null) {
			//
			replaceTextContent.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(replaceTextContent, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.info(org.slf4j.Logger,java.lang.String)
		//
		final Method info = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("info", Logger.class, String.class)
				: null;
		//
		if (info != null) {
			//
			info.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(info, null, null, null));
		//
		Assertions.assertNull(invoke(info, null, logger, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.removeCustomShapeByName(org.springframework.context.support.VoiceManager$ObjectMap,java.lang.String)
		//
		final Method removeCustomShapeByName = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("removeCustomShapeByName", CLASS_OBJECT_MAP, String.class)
				: null;
		//
		if (removeCustomShapeByName != null) {
			//
			removeCustomShapeByName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(removeCustomShapeByName, null, null, null));
		//
		Assertions.assertNull(invoke(removeCustomShapeByName, null, null, ""));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setPassword(org.odftoolkit.odfdom.pkg.OdfPackage,java.lang.String)
		//
		final Method setPassword = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setPassword", OdfPackage.class, String.class)
				: null;
		//
		if (setPassword != null) {
			//
			setPassword.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPassword, null, null, null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(OdfPackage.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPassword, null, newInstance(constructor), null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getNodeName(org.w3c.dom.Node)
		//
		final Method getNodeName = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getNodeName", Node.class)
				: null;
		//
		if (getNodeName != null) {
			//
			getNodeName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getNodeName, null, (Object) null));
		//
		Assertions.assertEquals(ih.nodeName = EMPTY, invoke(getNodeName, null, node));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getOutputFolder(org.springframework.context.support.VoiceManager)
		//
		final Method getOutputFolder = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getOutputFolder", VoiceManager.class)
				: null;
		//
		if (getOutputFolder != null) {
			//
			getOutputFolder.setAccessible(true);
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setOutputFolder(EMPTY);
			//
		} // if
			//
		Assertions.assertEquals(EMPTY, invoke(getOutputFolder, null, instance));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getVoiceFolder(org.springframework.context.support.VoiceManager)
		//
		final Method getVoiceFolder = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getVoiceFolder", VoiceManager.class)
				: null;
		//
		if (getVoiceFolder != null) {
			//
			getVoiceFolder.setAccessible(true);
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setVoiceFolder(EMPTY);
			//
		} // if
			//
		Assertions.assertEquals(EMPTY, invoke(getVoiceFolder, null, instance));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getProgressBarExport(org.springframework.context.support.VoiceManager)
		//
		final Method getProgressBarExport = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getProgressBarExport", VoiceManager.class)
				: null;
		//
		if (getProgressBarExport != null) {
			//
			getProgressBarExport.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getProgressBarExport, null, instance));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setNodeValue(org.w3c.dom.Node,java.lang.String)
		//
		final Method setNodeValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setNodeValue", Node.class, String.class)
				: null;
		//
		if (setNodeValue != null) {
			//
			setNodeValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setNodeValue, null, null, null));
		//
		Assertions.assertNull(invoke(setNodeValue, null, node, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getNodeValue(org.w3c.dom.Node)
		//
		final Method getNodeValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getNodeValue", Node.class)
				: null;
		//
		if (getNodeValue != null) {
			//
			getNodeValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getNodeValue, null, (Object) null));
		//
		Assertions.assertNull(invoke(getNodeValue, null, node));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getSlideName(domain.Voice)
		//
		final Method getSlideName = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getSlideName", Voice.class)
				: null;
		//
		if (getSlideName != null) {
			//
			getSlideName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getSlideName, null, (Object) null));
		//
		final Voice voice = new Voice();
		//
		final String hiragana = "H";
		//
		voice.setHiragana(hiragana);
		//
		Assertions.assertEquals(hiragana, invoke(getSlideName, null, voice));
		//
		final String romaji = "R";
		//
		voice.setRomaji(romaji);
		//
		Assertions.assertEquals(String.join(" ", hiragana, romaji), invoke(getSlideName, null, voice));
		//
		final String text = "T";
		//
		voice.setText(text);
		//
		Assertions.assertEquals(String.format("%1$s (%2$s)", text, String.join(" ", hiragana, romaji)),
				invoke(getSlideName, null, voice));
		//
	}

	@Test
	void testExportTask3() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager$ExportTask.describe(java.lang.Object)
		//
		final Method describe = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("describe", Object.class)
				: null;
		//
		if (describe != null) {
			//
			describe.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(describe, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.parseExpression(org.springframework.expression.ExpressionParser,java.lang.String)
		//
		final Method parseExpression = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("parseExpression", ExpressionParser.class, String.class)
				: null;
		//
		if (parseExpression != null) {
			//
			parseExpression.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(parseExpression, null, null, null));
		//
		Assertions.assertNull(invoke(parseExpression, null, Reflection.newProxy(ExpressionParser.class, ih), null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.and(boolean,boolean,boolean...)
		//
		final Method and = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)
				: null;
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(and, null, Boolean.TRUE, Boolean.FALSE, null));
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(and, null, Boolean.TRUE, Boolean.TRUE, null));
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(and, null, Boolean.TRUE, Boolean.TRUE, new boolean[] { false }));
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(and, null, Boolean.TRUE, Boolean.TRUE, new boolean[] { true }));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.evaluate(javax.xml.xpath.XPath,java.lang.String,java.lang.Object,javax.xml.namespace.QName)
		//
		final Method evaluate = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("evaluate", XPath.class, String.class, Object.class, QName.class)
				: null;
		//
		Assertions.assertNull(invoke(evaluate, null, null, null, null, null));
		//
		Assertions.assertNull(invoke(evaluate, null, Reflection.newProxy(XPath.class, ih), null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getTitle(com.mpatric.mp3agic.ID3v1)
		//
		final Method getTitle = CLASS_EXPORT_TASK != null ? CLASS_EXPORT_TASK.getDeclaredMethod("getTitle", ID3v1.class)
				: null;
		//
		Assertions.assertNull(invoke(getTitle, null, (Object) null));
		//
		Assertions.assertNull(invoke(getTitle, null, Reflection.newProxy(ID3v1.class, ih)));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.save(com.mpatric.mp3agic.Mp3File,java.lang.String)
		//
		final Method saveMp3File = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("save", Mp3File.class, String.class)
				: null;
		//
		Assertions.assertNull(invoke(saveMp3File, null, null, null));
		//
		Assertions.assertNull(invoke(saveMp3File, null, Narcissus.allocateInstance(Mp3File.class), null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.save(org.odftoolkit.odfdom.pkg.OdfPackageDocument,java.io.File)
		//
		final Method saveOdfPackageDocument = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("save", OdfPackageDocument.class, File.class)
				: null;
		//
		Assertions.assertNull(invoke(saveOdfPackageDocument, null, null, null));
		//
		Assertions.assertNull(
				invoke(saveOdfPackageDocument, null, Narcissus.allocateInstance(OdfPackageDocument.class), null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.reset(com.google.common.base.Stopwatch)
		//
		final Method reset = CLASS_EXPORT_TASK != null ? CLASS_EXPORT_TASK.getDeclaredMethod("reset", Stopwatch.class)
				: null;
		//
		Assertions.assertNull(invoke(reset, null, (Object) null));
		//
		Stopwatch stopwatch = Stopwatch.createStarted();
		//
		Assertions.assertSame(stopwatch, invoke(reset, null, stopwatch));
		//
		Assertions.assertSame(stopwatch = Util.cast(Stopwatch.class, Narcissus.allocateInstance(Stopwatch.class)),
				invoke(reset, null, stopwatch));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.start(com.google.common.base.Stopwatch)
		//
		final Method start = CLASS_EXPORT_TASK != null ? CLASS_EXPORT_TASK.getDeclaredMethod("start", Stopwatch.class)
				: null;
		//
		Assertions.assertNull(invoke(start, null, (Object) null));
		//
		Assertions.assertSame(stopwatch = Stopwatch.createUnstarted(), invoke(start, null, stopwatch));
		//
		Assertions.assertSame(stopwatch = Util.cast(Stopwatch.class, Narcissus.allocateInstance(Stopwatch.class)),
				invoke(start, null, stopwatch));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setTextContent(org.w3c.dom.Node,java.lang.String)
		//
		Assertions.assertNull(invoke(CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setTextContent", Node.class, String.class)
				: null, null, node, null));
		//
	}

	private static void run(final Runnable instance) {
		if (instance != null) {
			instance.run();
		}
	}

	@Test
	void testVoiceIdListCellRenderer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$VoiceIdListCellRenderer"),
				VoiceManager.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final ListCellRenderer<?> listCellRenderer1 = Util.cast(ListCellRenderer.class,
				newInstance(constructor, this.instance));
		//
		if (listCellRenderer1 != null) {
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", speechApi, true);
			//
			ih.voiceAttribute = "A";
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", null, true);
			//
			FieldUtils.writeDeclaredField(listCellRenderer1, "listCellRenderer",
					Reflection.newProxy(ListCellRenderer.class, ih), true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			final SpeechApiImpl speechApiImpl = new SpeechApiImpl();
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", speechApiImpl, true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(speechApiImpl, "instance", new SpeechApiSystemSpeechImpl(), true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
		} // if
			//
	}

	@Test
	void testMicrosoftAccessFileFormatListCellRenderer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(Util
				.forName("org.springframework.context.support.VoiceManager$MicrosoftAccessFileFormatListCellRenderer"));
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final ListCellRenderer<?> listCellRenderer = Util.cast(ListCellRenderer.class, newInstance(constructor));
		//
		if (listCellRenderer != null) {
			//
			Assertions.assertNull(listCellRenderer.getListCellRendererComponent(null, null, 0, false, false));
			//
			final FileFormat fileFormat = FileFormat.V2000;
			//
			Assertions.assertNull(((ListCellRenderer) listCellRenderer).getListCellRendererComponent(null, fileFormat,
					0, false, false));
			//
			FieldUtils.writeDeclaredField(listCellRenderer, "commonPrefix", "VERSION_", true);
			//
			Assertions.assertNull(((ListCellRenderer) listCellRenderer).getListCellRendererComponent(null, fileFormat,
					0, false, false));
			//
		} // if
			//
	}

	@Test
	void testAudioToFlacByteConverter() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$AudioToFlacByteConverter");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = newInstance(constructor);
		//
		// setAudioStreamEncoderByteArrayLength(java.lang.Object)
		//
		final Method setAudioStreamEncoderByteArrayLength = clz != null
				? clz.getDeclaredMethod("setAudioStreamEncoderByteArrayLength", Object.class)
				: null;
		//
		final Field audioStreamEncoderByteArrayLength = getDeclaredField(clz, "audioStreamEncoderByteArrayLength");
		//
		if (audioStreamEncoderByteArrayLength != null) {
			//
			audioStreamEncoderByteArrayLength.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> invoke(setAudioStreamEncoderByteArrayLength, instance, (Object) null));
		//
		Assertions.assertNull(get(audioStreamEncoderByteArrayLength, instance));
		//
		final Integer one = Integer.valueOf(1);
		//
		Assertions.assertDoesNotThrow(() -> invoke(setAudioStreamEncoderByteArrayLength, instance, one));
		//
		Assertions.assertSame(one, get(audioStreamEncoderByteArrayLength, instance));
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setAudioStreamEncoderByteArrayLength, instance, Long.valueOf(one.longValue())));
		//
		Assertions.assertSame(one, get(audioStreamEncoderByteArrayLength, instance));
		//
		// convert(byte[])
		//
		final Method convert = clz != null ? clz.getDeclaredMethod("convert", byte[].class) : null;
		//
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(convert, instance, (Object) null)));
		//
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(convert, instance, new byte[] {})));
		//
		// createStreamConfiguration(javax.sound.sampled.AudioFormat)
		//
		Assertions.assertEquals(
				"StreamConfiguration[bitsPerSample=1,channelCount=2,maxBlockSize=4096,minBlockSize=4096,sampleRate=0,validConfig=true]",
				ToStringBuilder.reflectionToString(
						invoke(clz != null ? clz.getDeclaredMethod("createStreamConfiguration", AudioFormat.class)
								: null, null, new AudioFormat(ZERO, ONE, TWO, true, false)),
						ToStringStyle.SHORT_PREFIX_STYLE));
		//
		// org.springframework.context.support.VoiceManager.AudioToFlacByteConverter.getFormat(javax.sound.sampled.AudioInputStream)
		//
		final Method getFormat = clz != null ? clz.getDeclaredMethod("getFormat", AudioInputStream.class) : null;
		//
		if (getFormat != null) {
			//
			getFormat.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getFormat, null, Narcissus.allocateInstance(AudioInputStream.class)));
		//
	}

	@Test
	void testAudioToMp3ByteConverter() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$AudioToMp3ByteConverter");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = newInstance(constructor);
		//
		// convert(byte[])
		//
		final Method convert = clz != null ? clz.getDeclaredMethod("convert", byte[].class) : null;
		//
		Assertions.assertNull(invoke(convert, instance, (Object) null));
		//
		Assertions.assertNull(invoke(convert, instance, new byte[] {}));
		//
		AssertionsUtil.assertThrowsAndEquals(RuntimeException.class,
				"{localizedMessage=javax.sound.sampled.UnsupportedAudioFileException: Stream of unsupported format, message=javax.sound.sampled.UnsupportedAudioFileException: Stream of unsupported format}",
				() -> {
					//
					try {
						//
						invoke(convert, instance, new byte[] { 0 });
						//
					} catch (final InvocationTargetException e) {
						//
						throw e.getTargetException();
						//
					} // try
						//
				});
		//
		// setBitRate(java.lang.Object)
		//
		Assertions.assertNull(invoke(clz != null ? clz.getDeclaredMethod("setBitRate", Object.class) : null, instance,
				(Object) null));
		//
		// setQuality(java.lang.Object)
		//
		final Method setQuality = clz != null ? clz.getDeclaredMethod("setQuality", Object.class) : null;
		//
		Assertions.assertNull(invoke(setQuality, instance, (Object) null));
		//
		Assertions.assertNull(invoke(setQuality, instance, "lowest"));
		//
		Assertions.assertNull(invoke(setQuality, instance, "Lowest"));
		//
		// setVbr(java.lang.Object)
		//
		final Method setVbr = clz != null ? clz.getDeclaredMethod("setVbr", Object.class) : null;
		//
		Assertions.assertNull(invoke(setVbr, instance, ""));
		//
		// afterPropertiesSet()
		//
		final Method afterPropertiesSet = clz != null ? clz.getDeclaredMethod("afterPropertiesSet") : null;
		//
		Assertions.assertNull(invoke(afterPropertiesSet, instance));
		//
		Assertions.assertNull(invoke(setVbr, instance, "true"));
		//
		Assertions.assertNull(invoke(afterPropertiesSet, instance));
		//
		Assertions.assertNull(invoke(setQuality, instance, "-1"));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Under VBR,\"quality\" cound be with in 0 to 9, message=Under VBR,\"quality\" cound be with in 0 to 9}",
				() -> {
					//
					try {
						//
						invoke(afterPropertiesSet, instance);
						//
					} catch (final InvocationTargetException e) {
						//
						throw e.getTargetException();
						//
					} // try
						//
				});
		//
		// createQuality(org.apache.bcel.generic.Instruction[])
		//
		final Method createQuality = clz != null ? clz.getDeclaredMethod("createQuality", Instruction[].class) : null;
		//
		Assertions.assertNull(invoke(createQuality, instance, (Object) null));
		//
		Assertions.assertNull(invoke(createQuality, instance, new Instruction[] { null }));
		//
		// getValue(org.apache.bcel.generic.ConstantPushInstruction)
		//
		Assertions.assertNull(
				invoke(clz != null ? clz.getDeclaredMethod("getValue", ConstantPushInstruction.class) : null, instance,
						(Object) null));
		//
		// createQualityMap(org.apache.bcel.classfile.ConstantPool,org.apache.bcel.generic.Instruction[])
		//
		final Method createQualityMap = clz != null
				? clz.getDeclaredMethod("createQualityMap", ConstantPool.class, Instruction[].class)
				: null;
		//
		Assertions.assertNull(invoke(createQualityMap, instance, null, null));
		//
		Assertions.assertNull(invoke(createQualityMap, instance, null, new Instruction[] { null }));
		//
		// getValue(org.apache.bcel.generic.LDC,org.apache.bcel.generic.ConstantPoolGen)
		//
		final Method getValue = clz != null ? clz.getDeclaredMethod("getValue", LDC.class, ConstantPoolGen.class)
				: null;
		//
		Assertions.assertNull(invoke(getValue, instance, null, null));
		//
		final LDC ldc = new LDC(0);
		//
		Assertions.assertNull(invoke(getValue, instance, ldc, null));
		//
		if (Util.forName("org.apache.bcel.classfile.InvalidMethodSignatureException") == null) {
			//
			Assertions.assertNull(invoke(getValue, instance, ldc, new ConstantPoolGen()));
			//
		} // if
			//
	}

	@Test
	void testEmptyFilePredicate() throws Throwable {
		//
		final Predicate<Object> predicate = Util.cast(Predicate.class,
				FieldUtils.readDeclaredStaticField(VoiceManager.class, "EMPTY_FILE_PREDICATE", true));
		//
		if (predicate != null) {
			//
			Assertions.assertFalse(predicate.test(null));
			//
			Assertions.assertFalse(predicate.test(new File("non_exixts")));
			//
			Assertions.assertFalse(predicate.test(new File(".")));
			//
			Assertions.assertFalse(predicate.test(new File("pom.xml")));
			//
		} // if
			//
	}

	@Test
	void testJLabelLink() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$JLabelLink");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz, ATag.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final MouseListener[] mouseListeners = getMouseListeners(
				Util.cast(Component.class, newInstance(constructor, (Object) null)));
		//
		for (int i = 0; mouseListeners != null && i < mouseListeners.length; i++) {
			//
			final MouseListener ml = mouseListeners[i];
			//
			if (ml == null) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> ml.mouseClicked(null));
			//
		} // for
			//
			// org.springframework.context.support.VoiceManager$JLabelLink.darker(java.awt.Color)
			//
		final Method darker = clz != null ? clz.getDeclaredMethod("darker", Color.class) : null;
		//
		if (darker != null) {
			//
			darker.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(darker, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$JLabelLink.getName(j2html.attributes.Attribute)
		//
		final Method getName = clz != null ? clz.getDeclaredMethod("getName", Attribute.class) : null;
		//
		if (getName != null) {
			//
			getName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getName, null, (Object) null));
		//
		Assertions.assertNull(invoke(getName, null, Narcissus.allocateInstance(Attribute.class)));
		//
	}

	private static MouseListener[] getMouseListeners(final Component instance) {
		return instance != null ? instance.getMouseListeners() : null;
	}

	@Test
	void testObjectMap() throws Throwable {
		//
		final Method containsObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("containsObject", CLASS_OBJECT_MAP, Class.class)
				: null;
		//
		if (containsObject != null) {
			//
			containsObject.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, invoke(containsObject, null, null, null));
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, createVoiceManagerIH());
		//
		final Method setObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
				: null;
		//
		if (setObject != null) {
			//
			setObject.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setObject, objectMap, null, null));
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(containsObject, null, objectMap, null));
		//
	}

	@Test
	void testJTabbedPaneChangeListener() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$JTabbedPaneChangeListener"));
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> stateChanged(Util.cast(ChangeListener.class, newInstance(constructor)), null));
		//
	}

	@Test
	void testBooleanMap() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$BooleanMap");
		//
		// org.springframework.context.support.VoiceManager$BooleanMap.setBoolean(org.springframework.context.support.VoiceManager$BooleanMap,java.lang.String,boolean)
		//
		final Method setBoolean = clz != null ? clz.getDeclaredMethod("setBoolean", clz, String.class, Boolean.TYPE)
				: null;
		//
		if (setBoolean != null) {
			//
			setBoolean.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setBoolean, null, null, null, Boolean.FALSE));
		//
	}

	@Test
	void testStringMap() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager$StringMap.getString(org.springframework.context.support.VoiceManager$StringMap,java.lang.String)
		//
		final Method getString = CLASS_STRING_MAP != null
				? CLASS_STRING_MAP.getDeclaredMethod("getString", CLASS_STRING_MAP, String.class)
				: null;
		//
		if (getString != null) {
			//
			getString.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getString, null, null, null));
		//
		final Object stringMap = Reflection.newProxy(CLASS_STRING_MAP, createVoiceManagerIH());
		//
		AssertionsUtil.assertThrowsAndEquals(InvocationTargetException.class, "{}",
				() -> invoke(getString, null, stringMap, null));
		//
		// org.springframework.context.support.VoiceManager$StringMap.setString(java.lang.String,java.lang.String)
		//
		final Method setString = CLASS_STRING_MAP != null
				? CLASS_STRING_MAP.getDeclaredMethod("setString", CLASS_STRING_MAP, String.class, String.class)
				: null;
		//
		if (setString != null) {
			//
			setString.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setString, null, null, null, null));
		//
		Assertions.assertNull(invoke(setString, null, stringMap, null, null));
		//
		Assertions.assertNull(invoke(getString, null, stringMap, null));
		//
	}

	@Test
	void testVoiceThrowableMessageBiConsumer() throws Throwable {
		//
		final Class<?> clz = Util
				.forName("org.springframework.context.support.VoiceManager$VoiceThrowableMessageBiConsumer");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz, Boolean.TYPE, DefaultTableModel.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final BiConsumer<?, ?> biConsumer = Util.cast(BiConsumer.class, newInstance(constructor, Boolean.TRUE, null));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			accept(biConsumer, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(biConsumer, "headless", Boolean.FALSE, true);
		//
		FieldUtils.writeDeclaredField(biConsumer, "tableModel", new DefaultTableModel(), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			accept(biConsumer, null, null);
			//
		});
		//
		// errorOrPrintln(org.slf4j.Logger,java.io.PrintStream,java.lang.String)
		//
		final Method errorOrPrintln = clz != null
				? clz.getDeclaredMethod("errorOrPrintln", Logger.class, PrintStream.class, String.class)
				: null;
		//
		if (errorOrPrintln != null) {
			//
			errorOrPrintln.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(errorOrPrintln, null, null, null, null));
		//
		try (final OutputStream os = new ByteArrayOutputStream(); final PrintStream ps = new PrintStream(os)) {
			//
			Assertions.assertNull(invoke(errorOrPrintln, null, null, ps, null));
			//
		} // try
			//
	}

	@Test
	void testVoiceThrowableBiConsumer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$VoiceThrowableBiConsumer"), Boolean.TYPE,
				DefaultTableModel.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final BiConsumer<?, ?> biConsumer = Util.cast(BiConsumer.class, newInstance(constructor, Boolean.TRUE, null));
		//
		Assertions.assertDoesNotThrow(() -> accept(biConsumer, null, null));
		//
		FieldUtils.writeDeclaredField(biConsumer, "headless", Boolean.FALSE, true);
		//
		FieldUtils.writeDeclaredField(biConsumer, "tableModel", new DefaultTableModel(), true);
		//
		Assertions.assertDoesNotThrow(() -> accept(biConsumer, null, null));
		//
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Test
	void testVoiceConsumer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$VoiceConsumer"), JTextComponent.class,
				AtomicInteger.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Consumer<?> consumer = Util.cast(Consumer.class, newInstance(constructor, null, null));
		//
		Assertions.assertDoesNotThrow(() -> accept(consumer, null));
		//
		FieldUtils.writeDeclaredField(consumer, "atomicInteger", new AtomicInteger(), true);
		//
		Assertions.assertDoesNotThrow(() -> accept(consumer, null));
		//
	}

	private static <T> void accept(final Consumer<T> instance, final T t) {
		if (instance != null) {
			instance.accept(t);
		}
	}

	@Test
	void testTabFocusTraversalPolicy() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$TabFocusTraversalPolicy"), List.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final FocusTraversalPolicy focusTraversalPolicy = Util.cast(FocusTraversalPolicy.class,
				newInstance(constructor, (Object) null));
		//
		if (focusTraversalPolicy != null) {
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentAfter(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentBefore(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getDefaultComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getFirstComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getLastComponent(null));
			//
			FieldUtils.writeDeclaredField(focusTraversalPolicy, "components", Arrays.asList((Object) null), true);
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentAfter(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentBefore(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getDefaultComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getFirstComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getLastComponent(null));
			//
			// java.awt.FocusTraversalPolicy.getInitialComponent(java.awt.Window)
			//
			Assertions.assertNull(focusTraversalPolicy.getInitialComponent(null));
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				Assertions.assertNull(focusTraversalPolicy.getInitialComponent(instance));
				//
			} else {
				//
				Assertions.assertSame(instance, focusTraversalPolicy.getInitialComponent(instance));
				//
			} // if
				//
		} // if
			//
	}

}