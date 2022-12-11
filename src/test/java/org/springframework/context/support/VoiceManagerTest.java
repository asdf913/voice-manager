package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.ItemSelectable;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.time.Duration;
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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;

import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.slf4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.PropertyResolver;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentType;
import com.mpatric.mp3agic.ID3v1;

import domain.Voice;
import domain.Voice.Yomi;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Template;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.tags.specialized.ATag;
import mapper.VoiceMapper;

class VoiceManagerTest {

	private static final String EMPTY = "";

	private static final String SPACE = " ";

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static Class<?> CLASS_OBJECT_MAP, CLASS_BOOLEAN_MAP, CLASS_IH, CLASS_EXPORT_TASK = null;

	private static Method METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS, METHOD_GET_FILE_EXTENSION, METHOD_DIGEST,
			METHOD_GET_MAPPER, METHOD_INSERT_OR_UPDATE, METHOD_SET_ENABLED_2, METHOD_SET_ENABLED_3,
			METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_CAST, METHOD_INT_VALUE, METHOD_LONG_VALUE,
			METHOD_GET_PROPERTY_PROPERTY_RESOLVER, METHOD_GET_PROPERTY_CUSTOM_PROPERTIES, METHOD_PARSE_EXPRESSION,
			METHOD_GET_VALUE, METHOD_GET_SOURCE, METHOD_EXPORT, METHOD_MAP, METHOD_MAP_TO_INT, METHOD_MAP_TO_LONG,
			METHOD_MAX_STREAM, METHOD_MAX_INT_STREAM, METHOD_OR_ELSE_OPTIONAL, METHOD_OR_ELSE_OPTIONAL_INT,
			METHOD_FOR_EACH_STREAM, METHOD_FOR_EACH_ITERABLE, METHOD_CREATE_WORK_BOOK_LIST,
			METHOD_CREATE_WORK_BOOK_MULTI_MAP, METHOD_CREATE_VOICE, METHOD_GET_MESSAGE, METHOD_INVOKE,
			METHOD_ANNOTATION_TYPE, METHOD_FIND_FIRST, METHOD_GET_DECLARED_METHODS, METHOD_FOR_NAME, METHOD_FILTER,
			METHOD_SET_TEXT, METHOD_GET_PREFERRED_WIDTH, METHOD_IMPORT_VOICE1, METHOD_IMPORT_VOICE3,
			METHOD_IMPORT_VOICE5, METHOD_ERROR_OR_PRINT_LN, METHOD_ADD, METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY,
			METHOD_CREATE_CELL, METHOD_SET_CELL_VALUE, METHOD_ANY_MATCH, METHOD_COLLECT, METHOD_NAME,
			METHOD_GET_SELECTED_ITEM, METHOD_WRITE, METHOD_MATCHER, METHOD_SET_VALUE_J_PROGRESS_BAR,
			METHOD_SET_VALUE_J_SLIDER, METHOD_SET_STRING_J_PROGRESS_BAR, METHOD_SET_STRING_COMMENT,
			METHOD_SET_TOOL_TIP_TEXT, METHOD_FORMAT, METHOD_CONTAINS_KEY, METHOD_VALUE_OF1, METHOD_VALUE_OF2,
			METHOD_GET_CLASS, METHOD_CREATE_RANGE, METHOD_GET_PROVIDER_NAME, METHOD_GET_PROVIDER_VERSION,
			METHOD_WRITE_VOICE_TO_FILE, METHOD_GET_MP3_TAG_VALUE_FILE, METHOD_GET_MP3_TAG_VALUE_LIST,
			METHOD_GET_MP3_TAG_PARIRS_ID3V1, METHOD_GET_METHODS_CLASS, METHOD_GET_METHODS_JAVA_CLASS,
			METHOD_COPY_OBJECT_MAP, METHOD_DELETE, METHOD_DELETE_ON_EXIT, METHOD_CONVERT_LANGUAGE_CODE_TO_TEXT,
			METHOD_IS_SELECTED, METHOD_SET_HIRAGANA_OR_KATAKANA, METHOD_SET_ROMAJI, METHOD_OR,
			METHOD_CLEAR_DEFAULT_TABLE_MODEL, METHOD_CLEAR_STRING_BUILDER, METHOD_EXECUTE, METHOD_PUT_MAP,
			METHOD_PUT_MULTI_MAP, METHOD_GET_BYTE_CONVERTER, METHOD_GET_PROPERTIES, METHOD_GET_CUSTOM_PROPERTIES,
			METHOD_CONTAINS_CUSTOM_PROPERTIES, METHOD_CONTAINS_COLLECTION, METHOD_CONTAINS_LOOKUP, METHOD_GET_LPW_STR,
			METHOD_GET_SHEET_NAME, METHOD_ACCEPT, METHOD_TO_ARRAY, METHOD_TO_LIST, METHOD_GET_ID, METHOD_SET_MAXIMUM,
			METHOD_GET_CURRENT_SHEET_INDEX, METHOD_GET_JLPT_LEVELS, METHOD_PARSE_JLPT_PAGE_HTML,
			METHOD_GET_DATA_VALIDATION_HELPER, METHOD_CREATE_EXPLICIT_LIST_CONSTRAINT, METHOD_CREATE_VALIDATION,
			METHOD_CREATE_EXPORT_TASK, METHOD_GET_TAB_INDEX_BY_TITLE, METHOD_GET_DECLARED_FIELD,
			METHOD_GET_ABSOLUTE_PATH, METHOD_IS_ASSIGNABLE_FROM, METHOD_GET_ENUM_CONSTANTS, METHOD_LIST_FILES,
			METHOD_GET_TYPE, METHOD_GET_ELEMENTS_BY_TAG_NAME_SGML_PAGE, METHOD_GET_ELEMENTS_BY_TAG_NAME_DOM_ELEMENT,
			METHOD_GET_COLUMN_NAME, METHOD_GET_KEY_SET, METHOD_PUT_ALL, METHOD_CREATE_SHEET1, METHOD_CREATE_SHEET2,
			METHOD_ENTRIES, METHOD_GET_WORK_BOOK, METHOD_GET_OLE_ENTRY_NAMES, METHOD_NEW_DOCUMENT_BUILDER, METHOD_PARSE,
			METHOD_GET_DOCUMENT_ELEMENT, METHOD_GET_CHILD_NODES, METHOD_GET_NAMED_ITEM, METHOD_GET_TEXT_CONTENT,
			METHOD_GET_NODE_NAME, METHOD_GET_NODE_VALUE, METHOD_GET_NAME_FILE, METHOD_GET_NAME_CLASS,
			METHOD_GET_PASS_WORD, METHOD_GET_SUPPLIER, METHOD_GET_LOOKUP, METHOD_GET_LIST,
			METHOD_CREATE_MICROSOFT_SPEECH_OBJECT_LIBRARY_WORK_BOOK, METHOD_WRITE_VALUE_AS_STRING,
			METHOD_CREATE_DRAWING_PATRIARCH, METHOD_GET_CREATION_HELPER, METHOD_CREATE_CELL_COMMENT,
			METHOD_CREATE_CLIENT_ANCHOR, METHOD_CREATE_RICH_TEXT_STRING, METHOD_SET_CELL_COMMENT, METHOD_SET_AUTHOR,
			METHOD_TEST_AND_ACCEPT_PREDICATE, METHOD_TEST_AND_ACCEPT_BI_PREDICATE, METHOD_FIND_FIELDS_BY_VALUE,
			METHOD_GET_DECLARED_FIELDS, METHOD_GET_DECLARING_CLASS, METHOD_GET_PACKAGE, METHOD_BROWSE, METHOD_TO_URI,
			METHOD_GET_TITLE_TEXT, METHOD_SET_CSS_ENABLED, METHOD_STOP, METHOD_ELAPSED, METHOD_GET_DECLARED_CLASSES,
			METHOD_GET_DLL_PATH, METHOD_GET_RATE, METHOD_ADD_CHANGE_LISTENER, METHOD_IS_ANNOTATION_PRESENT,
			METHOD_PROCESS, METHOD_ENCODE_TO_STRING, METHOD_GET_VOICE_MULTI_MAP_BY_LIST_NAME,
			METHOD_GET_VOICE_MULTI_MAP_BY_JLPT, METHOD_GET_TEMPLATE, METHOD_GET_FILE_EXTENSIONS,
			METHOD_CREATE_CELL_STYLE, METHOD_REDUCE, METHOD_APPEND_STRING, METHOD_APPEND_CHAR,
			METHOD_GET_PROVIDER_PLATFORM, METHOD_OPEN_CONNECTION, METHOD_GET_RESOURCE_AS_STREAM,
			METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH, METHOD_GET_ATTRIBUTES, METHOD_GET_LENGTH, METHOD_ITEM,
			METHOD_GET_OS_VERSION_INFO_EX_MAP, METHOD_CREATE_JLPT_SHEET, METHOD_GET_NEXT_ELEMENT_SIBLING,
			METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_TBODY, METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_DOM_NODES,
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG1,
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG2,
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG3, METHOD_SET_VISIBLE, METHOD_RANDOM_ALPHABETIC,
			METHOD_GET_MEDIA_FORMAT_LINK, METHOD_GET_EVENT_TYPE, METHOD_GET_PARENT_FILE,
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET,
			METHOD_SET_MICROSOFT_SPEECH_OBJECT_LIBRARY_SHEET_FIRST_ROW, METHOD_EXPORT_JLPT,
			METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT, METHOD_SET_SHEET_HEADER_ROW, METHOD_ENCRYPT,
			METHOD_GET_WORKBOOK_BY_ZIP_FILE, METHOD_SELECT, METHOD_TEXT, METHOD_ATTR = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManager.class;
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION = clz.getDeclaredMethod("getFileExtension", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_DIGEST = clz.getDeclaredMethod("digest", MessageDigest.class, byte[].class)).setAccessible(true);
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
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_LONG_VALUE = clz.getDeclaredMethod("longValue", Number.class, Long.TYPE)).setAccessible(true);
		//
		(METHOD_GET_PROPERTY_PROPERTY_RESOLVER = clz.getDeclaredMethod("getProperty", PropertyResolver.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_PROPERTY_CUSTOM_PROPERTIES = clz.getDeclaredMethod("getProperty", CustomProperties.class,
				String.class)).setAccessible(true);
		//
		(METHOD_PARSE_EXPRESSION = clz.getDeclaredMethod("parseExpression", ExpressionParser.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Expression.class, EvaluationContext.class))
				.setAccessible(true);
		//
		(METHOD_GET_SOURCE = clz.getDeclaredMethod("getSource", EventObject.class)).setAccessible(true);
		//
		(METHOD_EXPORT = clz.getDeclaredMethod("export", List.class, Map.class,
				CLASS_OBJECT_MAP = Class.forName("org.springframework.context.support.VoiceManager$ObjectMap")))
				.setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
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
		(METHOD_OR_ELSE_OPTIONAL = clz.getDeclaredMethod("orElse", Optional.class, Object.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE_OPTIONAL_INT = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_FOR_EACH_STREAM = clz.getDeclaredMethod("forEach", Stream.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_ITERABLE = clz.getDeclaredMethod("forEach", Iterable.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_WORK_BOOK_LIST = clz.getDeclaredMethod("createWorkbook", List.class,
				CLASS_BOOLEAN_MAP = Class.forName("org.springframework.context.support.VoiceManager$BooleanMap")))
				.setAccessible(true);
		//
		(METHOD_CREATE_WORK_BOOK_MULTI_MAP = clz.getDeclaredMethod("createWorkbook", Pair.class, Multimap.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_VOICE = clz.getDeclaredMethod("createVoice", ObjectMapper.class, VoiceManager.class))
				.setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", Throwable.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_ANNOTATION_TYPE = clz.getDeclaredMethod("annotationType", Annotation.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_SET_TEXT = clz.getDeclaredMethod("setText", JTextComponent.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_PREFERRED_WIDTH = clz.getDeclaredMethod("getPreferredWidth", Component.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE1 = clz.getDeclaredMethod("importVoice", File.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE3 = clz.getDeclaredMethod("importVoice", CLASS_OBJECT_MAP, BiConsumer.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE5 = clz.getDeclaredMethod("importVoice", Sheet.class, CLASS_OBJECT_MAP, BiConsumer.class,
				BiConsumer.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_LN = clz.getDeclaredMethod("errorOrPrintln", Logger.class, PrintStream.class,
				String.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY = clz.getDeclaredMethod("createImportFileTemplateByteArray",
				Boolean.TYPE, Collection.class, Collection.class)).setAccessible(true);
		//
		(METHOD_CREATE_CELL = clz.getDeclaredMethod("createCell", Row.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SET_CELL_VALUE = clz.getDeclaredMethod("setCellValue", Cell.class, String.class)).setAccessible(true);
		//
		(METHOD_ANY_MATCH = clz.getDeclaredMethod("anyMatch", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", Workbook.class, OutputStream.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_SET_VALUE_J_PROGRESS_BAR = clz.getDeclaredMethod("setValue", JProgressBar.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_SET_VALUE_J_SLIDER = clz.getDeclaredMethod("setValue", JSlider.class, String.class, Consumer.class))
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
		(METHOD_CONTAINS_KEY = clz.getDeclaredMethod("containsKey", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF1 = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF2 = clz.getDeclaredMethod("valueOf", String.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_RANGE = clz.getDeclaredMethod("createRange", Integer.class, Integer.class)).setAccessible(true);
		//
		(METHOD_GET_PROVIDER_NAME = clz.getDeclaredMethod("getProviderName", Provider.class)).setAccessible(true);
		//
		(METHOD_GET_PROVIDER_VERSION = clz.getDeclaredMethod("getProviderVersion", Provider.class)).setAccessible(true);
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
		(METHOD_GET_METHODS_CLASS = clz.getDeclaredMethod("getMethods", Class.class)).setAccessible(true);
		//
		(METHOD_GET_METHODS_JAVA_CLASS = clz.getDeclaredMethod("getMethods", JavaClass.class)).setAccessible(true);
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
		(METHOD_PUT_MAP = clz.getDeclaredMethod("put", Map.class, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_PUT_MULTI_MAP = clz.getDeclaredMethod("put", Multimap.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_BYTE_CONVERTER = clz.getDeclaredMethod("getByteConverter", ConfigurableListableBeanFactory.class,
				String.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_PROPERTIES = clz.getDeclaredMethod("getProperties", POIXMLDocument.class)).setAccessible(true);
		//
		(METHOD_GET_CUSTOM_PROPERTIES = clz.getDeclaredMethod("getCustomProperties", POIXMLProperties.class))
				.setAccessible(true);
		//
		(METHOD_CONTAINS_CUSTOM_PROPERTIES = clz.getDeclaredMethod("contains", CustomProperties.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CONTAINS_COLLECTION = clz.getDeclaredMethod("contains", Collection.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_CONTAINS_LOOKUP = clz.getDeclaredMethod("contains", Lookup.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_LPW_STR = clz.getDeclaredMethod("getLpwstr", CTProperty.class)).setAccessible(true);
		//
		(METHOD_GET_SHEET_NAME = clz.getDeclaredMethod("getSheetName", Sheet.class)).setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", Consumer.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_ID = clz.getDeclaredMethod("getId", VoiceList.class)).setAccessible(true);
		//
		(METHOD_SET_MAXIMUM = clz.getDeclaredMethod("setMaximum", JProgressBar.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_CURRENT_SHEET_INDEX = clz.getDeclaredMethod("getCurrentSheetIndex", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_GET_JLPT_LEVELS = clz.getDeclaredMethod("getJlptLevels", String.class)).setAccessible(true);
		//
		(METHOD_PARSE_JLPT_PAGE_HTML = clz.getDeclaredMethod("parseJlptPageHtml", String.class)).setAccessible(true);
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
		(METHOD_GET_TAB_INDEX_BY_TITLE = clz.getDeclaredMethod("getTabIndexByTitle", Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_ABSOLUTE_PATH = clz.getDeclaredMethod("getAbsolutePath", File.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_GET_ENUM_CONSTANTS = clz.getDeclaredMethod("getEnumConstants", Class.class)).setAccessible(true);
		//
		(METHOD_LIST_FILES = clz.getDeclaredMethod("listFiles", File.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME_SGML_PAGE = clz.getDeclaredMethod("getElementsByTagName", SgmlPage.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME_DOM_ELEMENT = clz.getDeclaredMethod("getElementsByTagName", DomElement.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_NAME = clz.getDeclaredMethod("getColumnName", Class.class, Field.class)).setAccessible(true);
		//
		(METHOD_GET_KEY_SET = clz.getDeclaredMethod("keySet", Multimap.class)).setAccessible(true);
		//
		(METHOD_PUT_ALL = clz.getDeclaredMethod("putAll", Multimap.class, Object.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_SHEET1 = clz.getDeclaredMethod("createSheet", Workbook.class)).setAccessible(true);
		//
		(METHOD_CREATE_SHEET2 = clz.getDeclaredMethod("createSheet", Workbook.class, String.class)).setAccessible(true);
		//
		(METHOD_ENTRIES = clz.getDeclaredMethod("entries", Multimap.class)).setAccessible(true);
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
		(METHOD_GET_NODE_NAME = clz.getDeclaredMethod("getNodeName", Node.class)).setAccessible(true);
		//
		(METHOD_GET_NODE_VALUE = clz.getDeclaredMethod("getNodeValue", Node.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_FILE = clz.getDeclaredMethod("getName", File.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_CLASS = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
		(METHOD_GET_PASS_WORD = clz.getDeclaredMethod("getPassword", Console.class)).setAccessible(true);
		//
		(METHOD_GET_SUPPLIER = clz.getDeclaredMethod("get", Supplier.class)).setAccessible(true);
		//
		(METHOD_GET_LOOKUP = clz.getDeclaredMethod("get", Lookup.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_LIST = clz.getDeclaredMethod("get", List.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_CREATE_MICROSOFT_SPEECH_OBJECT_LIBRARY_WORK_BOOK = clz
				.getDeclaredMethod("createMicrosoftSpeechObjectLibraryWorkbook", SpeechApi.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_WRITE_VALUE_AS_STRING = clz.getDeclaredMethod("writeValueAsString", ObjectMapper.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_DRAWING_PATRIARCH = clz.getDeclaredMethod("createDrawingPatriarch", Sheet.class))
				.setAccessible(true);
		//
		(METHOD_GET_CREATION_HELPER = clz.getDeclaredMethod("getCreationHelper", Workbook.class)).setAccessible(true);
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
				Object.class, BiConsumer.class)).setAccessible(true);
		//
		(METHOD_FIND_FIELDS_BY_VALUE = clz.getDeclaredMethod("findFieldsByValue", Field[].class, Object.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELDS = clz.getDeclaredMethod("getDeclaredFields", Class.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARING_CLASS = clz.getDeclaredMethod("getDeclaringClass", Member.class)).setAccessible(true);
		//
		(METHOD_GET_PACKAGE = clz.getDeclaredMethod("getPackage", Class.class)).setAccessible(true);
		//
		(METHOD_BROWSE = clz.getDeclaredMethod("browse", Desktop.class, URI.class)).setAccessible(true);
		//
		(METHOD_TO_URI = clz.getDeclaredMethod("toURI", File.class)).setAccessible(true);
		//
		(METHOD_GET_TITLE_TEXT = clz.getDeclaredMethod("getTitleText", HtmlPage.class)).setAccessible(true);
		//
		(METHOD_SET_CSS_ENABLED = clz.getDeclaredMethod("setCssEnabled", WebClientOptions.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_STOP = clz.getDeclaredMethod("stop", Stopwatch.class)).setAccessible(true);
		//
		(METHOD_ELAPSED = clz.getDeclaredMethod("elapsed", Stopwatch.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_CLASSES = clz.getDeclaredMethod("getDeclaredClasses", Class.class)).setAccessible(true);
		//
		(METHOD_GET_DLL_PATH = clz.getDeclaredMethod("getDllPath", Object.class)).setAccessible(true);
		//
		(METHOD_GET_RATE = clz.getDeclaredMethod("getRate")).setAccessible(true);
		//
		(METHOD_ADD_CHANGE_LISTENER = clz.getDeclaredMethod("addChangeListener", ChangeListener.class, JSlider.class,
				JSlider[].class)).setAccessible(true);
		//
		(METHOD_IS_ANNOTATION_PRESENT = clz.getDeclaredMethod("isAnnotationPresent", AnnotatedElement.class,
				Class.class)).setAccessible(true);
		//
		(METHOD_PROCESS = clz.getDeclaredMethod("process", Template.class, Object.class, Writer.class))
				.setAccessible(true);
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
		(METHOD_GET_TEMPLATE = clz.getDeclaredMethod("getTemplate", freemarker.template.Configuration.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSIONS = clz.getDeclaredMethod("getFileExtensions", ContentType.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_CELL_STYLE = clz.getDeclaredMethod("createCellStyle", Workbook.class)).setAccessible(true);
		//
		(METHOD_REDUCE = clz.getDeclaredMethod("reduce", LongStream.class, Long.TYPE, LongBinaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_APPEND_STRING = clz.getDeclaredMethod("append", StringBuilder.class, String.class)).setAccessible(true);
		//
		(METHOD_APPEND_CHAR = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_GET_PROVIDER_PLATFORM = clz.getDeclaredMethod("getProviderPlatform", Provider.class))
				.setAccessible(true);
		//
		(METHOD_OPEN_CONNECTION = clz.getDeclaredMethod("openConnection", URL.class)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH = clz.getDeclaredMethod("getTempFileMinimumPrefixLength",
				org.apache.bcel.classfile.Method.class)).setAccessible(true);
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
		(METHOD_GET_NEXT_ELEMENT_SIBLING = clz.getDeclaredMethod("getNextElementSibling", DomNode.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_TBODY = clz.getDeclaredMethod("createJoYoKanJiWorkbookByTBody",
				DomNode.class)).setAccessible(true);
		//
		(METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_DOM_NODES = clz.getDeclaredMethod("createJoYoKanJiWorkbookByDomNodes",
				List.class)).setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG1 = clz
				.getDeclaredMethod("errorOrPrintStackTraceOrShowMessageDialog", Throwable.class)).setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG2 = clz
				.getDeclaredMethod("errorOrPrintStackTraceOrShowMessageDialog", Boolean.TYPE, Throwable.class))
				.setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG3 = clz.getDeclaredMethod(
				"errorOrPrintStackTraceOrShowMessageDialog", Boolean.TYPE, Logger.class, Throwable.class))
				.setAccessible(true);
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
		(METHOD_SELECT = clz.getDeclaredMethod("select", org.jsoup.nodes.Element.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEXT = clz.getDeclaredMethod("text", org.jsoup.nodes.Element.class)).setAccessible(true);
		//
		(METHOD_ATTR = clz.getDeclaredMethod("attr", org.jsoup.nodes.Element.class, String.class)).setAccessible(true);
		//
		CLASS_IH = Class.forName("org.springframework.context.support.VoiceManager$IH");
		//
		CLASS_EXPORT_TASK = Class.forName("org.springframework.context.support.VoiceManager$ExportTask");
		//
	}

	private static class IH implements InvocationHandler {

		private Error errorGetVoiceAttribute = null;

		private Voice voice = null;

		private Set<Entry<?, ?>> entrySet = null;

		private String toString, stringCellValue, providerName, providerVersion, artist, voiceAttribute, lpwstr,
				sheetName, textContent, nodeName, dllPath, providerPlatform = null;

		private Configuration configuration = null;

		private SqlSession sqlSession = null;

		private Expression expression = null;

		private Object value, min, max, selectedItem = null;

		private Iterator<Row> rows = null;

		private Iterator<Cell> cells = null;

		private Boolean anyMatch, contains, multiMapPut, multiMapPutAll, isInstalled = null;

		private String[] voiceIds = null;

		private Map<Object, String> properties = null;

		private Iterator<?> iterator = null;

		private Map<Object, Object> beansOfType = null;

		private Map<Object, BeanDefinition> beanDefinitions = null;

		private Map<Object, Object> beanDefinitionAttributes = null;

		private Object[] toArray = null;

		private VoiceList voiceList = null;

		private Workbook workbook = null;

		private Integer numberOfSheets, length = null;

		private IntStream intStream = null;

		private LongStream longStream = null;

		private Integer columnIndex = null;

		private Collection<Entry<?, ?>> multiMapEntries = null;

		private Node namedItem, parentNode, appendChild, removeChild, cloneNode, item = null;

		private Sheet sheet = null;

		private NamedNodeMap attributes = null;

		private Map<Object, String> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

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

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
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
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return toArray;
					//
				} else if (Objects.equals(methodName, "contains")) {
					//
					return contains;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProperties(), args[0]);
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
					return containsKey(getBeanDefinitionAttributes(), args[0]);
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
				} else if (Objects.equals(methodName, "createSheet")) {
					//
					return sheet;
					//
				} // if
					//
			} else if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "put")) {
					//
					return multiMapPut;
					//
				} else if (Objects.equals(methodName, "keySet")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "putAll")) {
					//
					return multiMapPutAll;
					//
				} else if (Objects.equals(methodName, "entries")) {
					//
					return multiMapEntries;
					//
				} // if
					//
			} else if (proxy instanceof WebWindow) {
				//
				if (Objects.equals(methodName, "getWebClient")) {
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
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManager instance = null;

	private IH ih = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private Stream<?> stream = null;

	private SpeechApi speechApi = null;

	private Sheet sheet = null;

	private BeanDefinition beanDefinition = null;

	private Multimap<?, ?> multimap = null;

	private Node node = null;

	private NodeList nodeList = null;

	private Lookup lookup = null;

	private Iterable<?> iterable = null;

	private Logger logger = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
		//
		final Constructor<VoiceManager> constructor = VoiceManager.class.getDeclaredConstructor();
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		instance = constructor != null && !GraphicsEnvironment.isHeadless() ? constructor.newInstance() : null;
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
		lookup = Reflection.newProxy(Lookup.class, ih);
		//
		iterable = Reflection.newProxy(Iterable.class, ih);
		//
		logger = Reflection.newProxy(Logger.class, ih);
		//
	}

	@Test
	void testSetFreeMarkerVersion() throws NoSuchFieldException, IllegalAccessException {
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
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setFreeMarkerVersion(new int[] {}));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(new int[] { ONE, 2, 3 }));
		//
		Assertions.assertEquals(new Version(ONE, 2, 3), get(freeMarkerVersion, instance));
		//
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
		final ConfigurableListableBeanFactory configurableListableBeanFactory = Reflection
				.newProxy(ConfigurableListableBeanFactory.class, ih);
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
		ih.getBeanDefinitions().put(null, beanDefinition);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		ih.getBeanDefinitionAttributes().put("format", null);
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
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> instance.setOutputFolderFileNameExpressions(Integer.toString(ONE)));
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.afterPropertiesSet());
		//
		instance.setSpeechApi(speechApi);
		//
		ih.voiceIds = new String[] {};
		//
		ih.isInstalled = Boolean.FALSE;
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.afterPropertiesSet());
		//
		ih.isInstalled = Boolean.TRUE;
		//
		Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
		//
		instance.setGaKuNenBeTsuKanJiListPageUrl(EMPTY);
		//
		Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
		//
		instance.setGaKuNenBeTsuKanJiListPageUrl(SPACE);
		//
		Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
		//
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
		final String json = new ObjectMapper().writeValueAsString(Collections.singleton(EMPTY));
		//
		Assertions.assertDoesNotThrow(() -> instance.setMp3Tags(json));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { EMPTY }, get(mp3Tags, instance)));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setMp3Tags("{}"));
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
	void testActionPerformed1() throws IllegalAccessException {
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
		final AbstractButton btnCopyRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyRomaji", btnCopyRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyRomaji, 0, null)));
		//
		final AbstractButton btnCopyHiragana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyHiragana", btnCopyHiragana, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyHiragana, 0, null)));
		//
		final AbstractButton btnCopyKatakana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyKatakana", btnCopyKatakana, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyKatakana, 0, null)));
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
		// btnSpeak
		//
		final AbstractButton btnSpeak = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSpeak", btnSpeak, true);
			//
		} // if
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
		// btnExportGaKuNenBeTsuKanJi
		//
		final AbstractButton btnExportGaKuNenBeTsuKanJi = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExportGaKuNenBeTsuKanJi", btnExportGaKuNenBeTsuKanJi, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> actionPerformed(instance, new ActionEvent(btnExportGaKuNenBeTsuKanJi, 0, null)));
		//
		// btnExportJoYoKanJi
		//
		final AbstractButton btnExportJoYoKanJi = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExportJoYoKanJi", btnExportJoYoKanJi, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnExportJoYoKanJi, 0, null)));
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
		// btnSpeechRateSlower
		//
		final AbstractButton btnSpeechRateSlower = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSpeechRateSlower", btnSpeechRateSlower, true);
			//
		} // if
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
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSpeechRateNormal", btnSpeechRateNormal, true);
			//
		} // if
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
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSpeechRateFaster", btnSpeechRateFaster, true);
			//
		} // if
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

	@Test
	void testActionPerformed2() throws IllegalAccessException {
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
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnDllPathCopy, 0, null)));
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
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnExportCopy, 0, null)));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent actionEvent) {
		if (instance != null) {
			instance.actionPerformed(actionEvent);
		} // if
	}

	@Test
	void testItemStateChanged() {
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
		setText(tfListNames, "{}");
		//
		Assertions.assertDoesNotThrow(() -> keyReleased(instance, keyEventTfListNames));
		//
		// btnCheckGaKuNenBeTsuKanJi
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
	}

	private static void keyReleased(final KeyListener instance, final KeyEvent keyEvent) {
		if (instance != null) {
			instance.keyReleased(keyEvent);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
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
	}

	private static String getFileExtension(final ContentInfo ci) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION.invoke(null, ci);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDigest() throws Throwable {
		//
		Assertions.assertNull(digest(null, null));
		//
		final MessageDigest md = MessageDigest.getInstance("SHA-512");
		//
		Assertions.assertNull(digest(md, null));
		//
		Assertions.assertEquals(
				"cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e",
				Hex.encodeHexString(digest(md, new byte[] {})));
		//
	}

	private static byte[] digest(final MessageDigest instance, final byte[] input) throws Throwable {
		try {
			final Object obj = METHOD_DIGEST.invoke(null, instance, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMapper() throws Throwable {
		//
		Assertions.assertNull(getMapper(null, null, null));
		//
		Assertions.assertThrows(BindingException.class, () -> getMapper(new Configuration(), null, null));
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
		final VoiceMapper voiceMapper = Reflection.newProxy(VoiceMapper.class, ih);
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

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProperty() throws Throwable {
		//
		Assertions.assertNull(getProperty((CustomProperties) null, null));
		//
		Assertions.assertNull(getProperty(Reflection.newProxy(PropertyResolver.class, ih), null));
		//
		Assertions.assertNull(getProperty(getCustomProperties(getProperties(null)), null));
		//
		try (final XSSFWorkbook wb = new XSSFWorkbook()) {
			//
			Assertions.assertNull(getProperty(getCustomProperties(getProperties(wb)), null));
			//
		} // try
			//
	}

	private static String getProperty(final PropertyResolver instance, final String key) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY_PROPERTY_RESOLVER.invoke(null, instance, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getProperty(final CustomProperties instance, final String key) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY_CUSTOM_PROPERTIES.invoke(null, instance, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParseExpression() throws Throwable {
		//
		Assertions.assertNull(parseExpression(null, null));
		//
		Assertions.assertNull(parseExpression(Reflection.newProxy(ExpressionParser.class, ih), null));
		//
	}

	private static Expression parseExpression(final ExpressionParser instance, final String expressionString)
			throws Throwable {
		try {
			final Object obj = METHOD_PARSE_EXPRESSION.invoke(null, instance, expressionString);
			if (obj == null) {
				return null;
			} else if (obj instanceof Expression) {
				return (Expression) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertNull(getSource(null));
		//
	}

	private static Object getSource(final EventObject instance) throws Throwable {
		try {
			return METHOD_GET_SOURCE.invoke(null, instance);
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
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class,
				() -> export(voices, Collections.singletonMap(EMPTY, "true"), Reflection.newProxy(CLASS_OBJECT_MAP,
						cast(InvocationHandler.class, constructor != null ? constructor.newInstance() : null))));
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
		Assertions.assertNull(map(null, null));
		//
		Assertions.assertSame(stream, map(stream, null));
		//
		Assertions.assertNull(map(Stream.empty(), null));
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertNull(orElse(null, null));
		//
		Assertions.assertEquals(ZERO, orElse(OptionalInt.empty(), ZERO));
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T other) throws Throwable {
		try {
			return (T) METHOD_OR_ELSE_OPTIONAL.invoke(null, instance, other);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE_OPTIONAL_INT.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
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

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) throws Throwable {
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
		Assertions.assertNotNull(createWorkbook(Reflection.newProxy(List.class, ih), null));
		//
		Assertions.assertNotNull(createWorkbook(Collections.singletonList(null), null));
		//
		final Voice voice = new Voice();
		//
		voice.setFileLength(Long.valueOf(0));
		//
		voice.setCreateTs(new Date());
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
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
		Assertions.assertNotNull(createWorkbook(Collections.nCopies(2, voice), booleanMap));
		//
		// org.apache.commons.lang3.tuple.Pair,com.google.common.collect.Multimap
		//
		Assertions.assertNull(createWorkbook((Pair) null, multimap));
		//
		this.ih.multiMapEntries = Collections.singleton(null);
		//
		Assertions.assertNull(createWorkbook((Pair) null, multimap));
		//
		this.ih.multiMapEntries = Collections.singleton(Pair.of(null, null));
		//
		Assertions.assertNotNull(createWorkbook((Pair) null, multimap));
		//
	}

	private static Workbook createWorkbook(final List<Voice> voices, final Object booleanMap) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_WORK_BOOK_LIST.invoke(null, voices, booleanMap);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Workbook createWorkbook(final Pair<String, String> columnNames, final Multimap<?, ?> multimap)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_WORK_BOOK_MULTI_MAP.invoke(null, columnNames, multimap);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateVoice() throws Throwable {
		//
		Assertions.assertNull(createVoice(null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(createVoice(null, instance));
			//
		} else {
			//
			Assertions.assertNotNull(createVoice(null, instance));
			//
		} // if
			//
	}

	private static Voice createVoice(final ObjectMapper objectMapper, final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VOICE.invoke(null, objectMapper, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Voice) {
				return (Voice) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMessage() throws Throwable {
		//
		Assertions.assertNull(getMessage(null));
		//
		Assertions.assertNull(getMessage(new Throwable()));
		//
	}

	private static String getMessage(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MESSAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethods(null));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHODS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName(EMPTY));
		//
		Assertions.assertNull(forName(SPACE));
		//
		Assertions.assertNull(forName("A"));
		//
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetText() {
		//
		Assertions.assertDoesNotThrow(() -> setText(new JTextField(), null));
		//
	}

	private static void setText(final JTextComponent instance, final String text) throws Throwable {
		try {
			METHOD_SET_TEXT.invoke(null, instance, text);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportVoice() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, null, null, null, null));
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> importVoice(objectMap, null, null));
		//
		final Map<?, ?> objects = cast(Map.class, FieldUtils.readDeclaredField(ih, "objects", true));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("."));
			//
			((Map) objects).put(Voice.class, null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("NON_EXISTS"));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("pom.xml"));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, (v, m) -> {
		}, null));
		//
		final File file = File.createTempFile(RandomStringUtils.randomAlphabetic(3), null);
		//
		if (file != null) {
			//
			file.deleteOnExit();
			//
		} // if
			//
		if (objects != null) {
			//
			((Map) objects).put(File.class, file);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null));
		//
		final Row row = Reflection.newProxy(Row.class, this.ih);
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null));
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		this.ih.cells = Iterators.forArray(null, Reflection.newProxy(Cell.class, this.ih));
		//
		this.ih.columnIndex = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null, null));
		//
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
			METHOD_IMPORT_VOICE3.invoke(null, objectMap, errorMessageConsumer, throwableConsumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void importVoice(final Sheet sheet, final Object objectMap,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer) throws Throwable {
		try {
			METHOD_IMPORT_VOICE5.invoke(null, sheet, objectMap, errorMessageConsumer, throwableConsumer, voiceConsumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrPrintln() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintln(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintln(logger, null, null));
		//
		try (final OutputStream os = new ByteArrayOutputStream(); final PrintStream ps = new PrintStream(os)) {
			//
			Assertions.assertDoesNotThrow(() -> errorOrPrintln(null, ps, null));
			//
		} // try
			//
	}

	private static void errorOrPrintln(final Logger logger, final PrintStream ps, final String message)
			throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_LN.invoke(null, logger, ps, message);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateCell() throws Throwable {
		//
		Assertions.assertNull(createCell(null, 0));
		//
	}

	private static Cell createCell(final Row instance, final int column) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_CELL.invoke(null, instance, column);
			if (obj == null) {
				return null;
			} else if (obj instanceof Cell) {
				return (Cell) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCellValue() {
		//
		Assertions.assertDoesNotThrow(() -> setCellValue(null, null));
		//
	}

	private static void setCellValue(final Cell instance, final String value) throws Throwable {
		try {
			METHOD_SET_CELL_VALUE.invoke(null, instance, value);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCollect() throws Throwable {
		//
		Assertions.assertNull(collect(null, null));
		//
		Assertions.assertNull(collect(Stream.empty(), null));
		//
		Assertions.assertNull(collect(stream, null));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector)
			throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, collector);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
		Assertions.assertNotNull(name(Yomi.KUN_YOMI));
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
			throw new Throwable(toString(getClass(obj)));
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
	void testWrite() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> write(Reflection.newProxy(Workbook.class, ih), null));
		//
		try (final Workbook workbook = new XSSFWorkbook()) {
			//
			Assertions.assertDoesNotThrow(() -> write(workbook, null));
			//
		} // try
			//
	}

	private static void write(final Workbook instance, final OutputStream stream) throws Throwable {
		try {
			METHOD_WRITE.invoke(null, instance, stream);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetValue() {
		//
		Assertions.assertDoesNotThrow(() -> setValue(new JProgressBar(), 0));
		//
		Assertions.assertDoesNotThrow(() -> setValue(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> setValue(null, "0", null));
		//
		final JSlider jSlider = new JSlider();
		//
		Assertions.assertDoesNotThrow(() -> setValue(jSlider, "0", null));
		//
		Assertions.assertDoesNotThrow(() -> setValue(jSlider, "max", null));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> setValue(jSlider, "ma", null));
		//
		Assertions.assertDoesNotThrow(() -> setValue(jSlider, Integer.toString(jSlider.getMinimum() - 1), null));
		//
		Assertions.assertDoesNotThrow(() -> setValue(jSlider, Integer.toString(jSlider.getMaximum() + 1), null));
		//
	}

	private static void setValue(final JProgressBar instance, final int n) throws Throwable {
		try {
			METHOD_SET_VALUE_J_PROGRESS_BAR.invoke(null, instance, n);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setValue(final JSlider instance, final String string, final Consumer<JSlider> consumer)
			throws Throwable {
		try {
			METHOD_SET_VALUE_J_SLIDER.invoke(null, instance, string, consumer);
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
		Assertions.assertEquals(null, format(null, 0d));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContainsKey() throws Throwable {
		//
		Assertions.assertFalse(containsKey(null, null));
		//
		Assertions.assertFalse(containsKey(Collections.emptyMap(), null));
		//
		Assertions.assertTrue(containsKey(Collections.singletonMap(null, null), null));
		//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_KEY.invoke(null, instance, key);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testCreateRange() throws Throwable {
		//
		Assertions.assertEquals(String.format("[%1$s..+∞)", ZERO), toString(createRange(ZERO, null)));
		//
		Assertions.assertEquals(String.format("(-∞..%1$s]", ZERO), toString(createRange(null, ZERO)));
		//
		Assertions.assertEquals(String.format("(%1$s..%2$s)", ZERO, ONE), toString(createRange(ZERO, ONE)));
		//
	}

	private static Range<Integer> createRange(final Integer minValue, final Integer maxValue) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_RANGE.invoke(null, minValue, maxValue);
			if (obj == null) {
				return null;
			} else if (obj instanceof Range) {
				return (Range) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProviderName() throws Throwable {
		//
		Assertions.assertNull(getProviderName(Reflection.newProxy(Provider.class, ih)));
		//
	}

	@Test
	void testGetProviderVersioni() throws Throwable {
		//
		Assertions.assertNull(getProviderVersion(Reflection.newProxy(Provider.class, ih)));
		//
	}

	private static String getProviderVersion(final Provider instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROVIDER_VERSION.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getProviderName(final Provider instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROVIDER_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriteVoiceToFile() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> writeVoiceToFile(null, null, null, null, null));
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
		//
		final Field fieldObjects = ih != null ? getDeclaredField(ih.getClass(), "objects") : null;
		//
		if (fieldObjects != null) {
			//
			fieldObjects.setAccessible(true);
			//
		} // if
			//
		Map<Object, Object> objects = ObjectUtils.getIfNull(cast(Map.class, get(fieldObjects, ih)), LinkedHashMap::new);
		//
		if (objects != null) {
			//
			objects.put(SpeechApi.class, speechApi);
			//
			objects.put(File.class, null);
			//
		} // if
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMethods() throws Throwable {
		//
		Assertions.assertNull(getMethods((Class<?>) null));
		//
		Assertions.assertNull(getMethods((JavaClass) null));
		//
	}

	private static Method[] getMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_METHODS_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static org.apache.bcel.classfile.Method[] getMethods(final JavaClass instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_METHODS_JAVA_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof org.apache.bcel.classfile.Method[]) {
				return (org.apache.bcel.classfile.Method[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
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
	void testDelete() throws Exception {
		//
		Assertions.assertDoesNotThrow(() -> delete(null));
		//
		Assertions.assertDoesNotThrow(() -> delete(File.createTempFile(RandomStringUtils.randomAlphabetic(3), null)));
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
	void testDeleteOnExit() throws Exception {
		//
		Assertions.assertDoesNotThrow(() -> deleteOnExit(null));
		//
		Assertions.assertDoesNotThrow(
				() -> deleteOnExit(File.createTempFile(RandomStringUtils.randomAlphabetic(3), null)));
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
		Assertions.assertThrows(IllegalStateException.class,
				() -> convertLanguageCodeToText(new LocaleID[] { localeId, localeId },
						localeId != null ? Integer.valueOf(localeId.getLcid()) : null));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
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
		Map<Object, Object> objects = ObjectUtils.getIfNull(cast(Map.class, get(fieldObjects, ih)), LinkedHashMap::new);
		//
		if (objects != null) {
			//
			objects.put(File.class, null);
			//
			objects.put(VoiceManager.class, instance);
			//
			objects.put(Voice.class, null);
			//
		} // if
			//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tmImportException", new DefaultTableModel(), true);
			//
		} // if
			//
		set(fieldObjects, ih, objects);
		//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		if (objects != null) {
			//
			objects.put(File.class, new File("NON_EXISTS"));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> execute(objectMap));
		//
		if (objects != null) {
			//
			objects.put(File.class, new File("."));
			//
		} // if
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
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> put((Map<?, ?>) null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> put((Multimap<?, ?>) null, null, null));
		//
		if (ih != null) {
			//
			ih.multiMapPut = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> put(multimap, null, null));
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT_MAP.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT_MULTI_MAP.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetByteConverter() throws Throwable {
		//
		Assertions.assertNull(getByteConverter(null, null, null));
		//
		final ConfigurableListableBeanFactory configurableListableBeanFactory = Reflection
				.newProxy(ConfigurableListableBeanFactory.class, ih);
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
		ih.getBeanDefinitions().put(null, beanDefinition);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, null, null));
		//
		final String format = toString(FieldUtils.readDeclaredStaticField(VoiceManager.class, "FORMAT", true));
		//
		ih.getBeanDefinitionAttributes().put(format, null);
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, format, null));
		//
		ih.getBeanDefinitionAttributes().put(format, "");
		//
		Assertions.assertNull(getByteConverter(configurableListableBeanFactory, format, null));
		//
		(ih.beansOfType = new LinkedHashMap<Object, Object>(Collections.singletonMap(null, null))).put("", null);
		//
		ih.getBeanDefinitions().put("", beanDefinition);
		//
		ih.getBeanDefinitionAttributes().put(format, null);
		//
		Assertions.assertThrows(IllegalStateException.class,
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

	private static POIXMLProperties getProperties(final POIXMLDocument instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTIES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof POIXMLProperties) {
				return (POIXMLProperties) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static CustomProperties getCustomProperties(final POIXMLProperties instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CUSTOM_PROPERTIES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof CustomProperties) {
				return (CustomProperties) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(getCustomProperties(getProperties(null)), null));
		//
		try (final XSSFWorkbook wb = new XSSFWorkbook()) {
			//
			Assertions.assertFalse(contains(getCustomProperties(getProperties(wb)), null));
			//
		} // try
			//
		Assertions.assertFalse(contains((Collection<?>) null, null));
		//
		final Collection<?> collection = Reflection.newProxy(Collection.class, ih);
		//
		Assertions.assertEquals(ih.contains = Boolean.FALSE, Boolean.valueOf(contains(collection, null)));
		//
		Assertions.assertEquals(ih.contains = Boolean.TRUE, Boolean.valueOf(contains(collection, null)));
		//
		// org.springframework.context.support.VoiceManagerTest.contains(org.springframework.context.support.Lookup,java.lang.Object,java.lang.Object)
		//
		Assertions.assertEquals(ih.contains = Boolean.FALSE, contains(lookup, null, null));
		//
		Assertions.assertEquals(ih.contains = Boolean.TRUE, contains(lookup, null, null));
		//
	}

	private static boolean contains(final CustomProperties instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_CUSTOM_PROPERTIES.invoke(null, instance, name);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_COLLECTION.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static boolean contains(final Lookup instance, final Object row, final Object column) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_LOOKUP.invoke(null, instance, row, column);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertNull(toArray(null, null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
		Assertions.assertNull(toArray(Reflection.newProxy(Collection.class, ih), null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		ih.workbook = Reflection.newProxy(Workbook.class, ih);
		//
		ih.numberOfSheets = Integer.valueOf(ONE);
		//
		Assertions.assertEquals(Integer.valueOf(ZERO), getCurrentSheetIndex(sheet));
		//
		ih.numberOfSheets = Integer.valueOf(2);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getCurrentSheetIndex(sheet));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetJlptLevels() throws Throwable {
		//
		Assertions.assertNull(getJlptLevels(""));
		//
		Assertions.assertNull(getJlptLevels(SPACE));
		//
	}

	private static List<String> getJlptLevels(final String urlString) throws Throwable {
		try {
			final Object obj = METHOD_GET_JLPT_LEVELS.invoke(null, urlString);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParseJlptPageHtml() throws Throwable {
		//
		Assertions.assertNull(parseJlptPageHtml(""));
		//
		Assertions.assertEquals(Arrays.asList(Integer.toString(ZERO)),
				parseJlptPageHtml(String.format("<th scope=\"col\" class=\"thLeft\">%1$s</th>", ZERO)));
		//
	}

	private static List<String> parseJlptPageHtml(final String html) throws Throwable {
		try {
			final Object obj = METHOD_PARSE_JLPT_PAGE_HTML.invoke(null, html);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertNull(getTabIndexByTitle(null, null));
		//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		final Collection<Object> pages = cast(Collection.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getField(x, getDeclaredField(getClass(x), "pages")), null));
		//
		add(pages, null);
		//
		Assertions.assertNull(getTabIndexByTitle(jTabbedPane, null));
		//
		add(pages, "");
		//
		Assertions.assertNull(getTabIndexByTitle(jTabbedPane, null));
		//
		if (pages != null) {
			//
			pages.clear();
			//
		} // if
			//
		jTabbedPane.addTab(EMPTY, null);
		//
		Assertions.assertEquals(Integer.valueOf(0), getTabIndexByTitle(jTabbedPane, EMPTY));
		//
		jTabbedPane.addTab(EMPTY, null);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getTabIndexByTitle(jTabbedPane, EMPTY));
		//
	}

	private static Integer getTabIndexByTitle(final Object jTabbedPane, final Object title) throws Throwable {
		try {
			final Object obj = METHOD_GET_TAB_INDEX_BY_TITLE.invoke(null, jTabbedPane, title);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAbsolutePath() throws Throwable {
		//
		Assertions.assertNull(getAbsolutePath(null));
		//
	}

	private static String getAbsolutePath(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ABSOLUTE_PATH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
		Assertions.assertTrue(isAssignableFrom(CharSequence.class, String.class));
		//
		Assertions.assertFalse(isAssignableFrom(String.class, CharSequence.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(null));
		//
	}

	private static Class<?> getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetElementsByTagName() throws Throwable {
		//
		Assertions.assertNotNull(
				getElementsByTagName(new HtmlPage(null, Reflection.newProxy(WebWindow.class, ih)), null));
		//
		Assertions.assertNull(getElementsByTagName((DomElement) null, null));
		//
		Assertions.assertNotNull(getElementsByTagName(new DomElement(null, EMPTY, null, null), null));
		//
	}

	private DomNodeList<DomElement> getElementsByTagName(final SgmlPage instance, final String tagName)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_TAG_NAME_SGML_PAGE.invoke(null, instance, tagName);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomNodeList) {
				return (DomNodeList) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static DomNodeList<HtmlElement> getElementsByTagName(final DomElement instance, final String tagName)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_TAG_NAME_DOM_ELEMENT.invoke(null, instance, tagName);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomNodeList) {
				return (DomNodeList) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testKeySet() throws Throwable {
		//
		Assertions.assertNull(keySet(multimap));
		//
	}

	private static <K> Set<K> keySet(final Multimap<K, ?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_KEY_SET.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Set) {
				return (Set) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> putAll(null, null, null));
		//
		ih.multiMapPutAll = Boolean.FALSE;
		//
		Assertions.assertDoesNotThrow(() -> putAll(multimap, null, null));
		//
	}

	private static <K, V> void putAll(final Multimap<K, V> instance, final K key, final Iterable<? extends V> values)
			throws Throwable {
		try {
			METHOD_PUT_ALL.invoke(null, instance, key, values);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateSheet() throws Throwable {
		//
		Assertions.assertNull(createSheet(null));
		//
		Assertions.assertNull(createSheet(null, null));
		//
		Assertions.assertNull(createSheet(Reflection.newProxy(Workbook.class, ih), null));
		//
	}

	private static Sheet createSheet(final Workbook instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_SHEET1.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Sheet) {
				return (Sheet) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Sheet createSheet(final Workbook instance, final String sheetName) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_SHEET2.invoke(null, instance, sheetName);
			if (obj == null) {
				return null;
			} else if (obj instanceof Sheet) {
				return (Sheet) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEntries() throws Throwable {
		//
		Assertions.assertNull(entries(multimap));
		//
	}

	private static <K, V> Collection<Entry<K, V>> entries(final Multimap<K, V> instance) throws Throwable {
		try {
			final Object obj = METHOD_ENTRIES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWorkbook() throws Throwable {
		//
		final int tempFileMinimumPrefixLength = intValue(cast(Number.class,
				FieldUtils.readDeclaredStaticField(VoiceManager.class, "TEMP_FILE_MINIMUM_PREFIX_LENGTH", true)), 3);
		//
		final File folder = new File(".");
		//
		File file = File.createTempFile(RandomStringUtils.randomAlphanumeric(tempFileMinimumPrefixLength), null,
				folder);
		//
		deleteOnExit(file);
		//
		try (final Workbook workbook = new HSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			workbook.write(os);
			//
			Assertions.assertNotNull(getWorkbook(file));
			//
		} // try
			//
		try (final Workbook workbook = new XSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			workbook.write(os);
			//
			Assertions.assertNotNull(getWorkbook(file));
			//
		} // try
			//
		ZipUtil.removeEntry(file, "[Content_Types].xml", file = File
				.createTempFile(RandomStringUtils.randomAlphanumeric(tempFileMinimumPrefixLength), null, folder));
		//
		deleteOnExit(file);
		//
		Assertions.assertNull(getWorkbook(file));
		//
	}

	private static Workbook getWorkbook(final File file) throws Throwable {
		try {
			final Object obj = METHOD_GET_WORK_BOOK.invoke(null, file);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNodeName() throws Throwable {
		//
		Assertions.assertNull(getNodeName(null));
		//
		Assertions.assertEquals(ih.nodeName = EMPTY, getNodeName(node));
		//
	}

	private static String getNodeName(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NODE_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNodeValue() throws Throwable {
		//
		Assertions.assertNull(getNodeValue(null));
		//
	}

	private static String getNodeValue(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NODE_VALUE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName((File) null));
		//
		Assertions.assertNull(getName((Class<?>) null));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getName(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null));
		//
		Assertions.assertNull(get(null, null, null));
		//
		Assertions.assertNull(get(lookup, null, null));
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

	private static Object get(final Lookup instance, final Object row, final Object column) throws Throwable {
		try {
			return METHOD_GET_LOOKUP.invoke(null, instance, row, column);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriteValueAsString() throws Throwable {
		//
		Assertions.assertNull(writeValueAsString(null, null));
		//
	}

	private static String writeValueAsString(final ObjectMapper instance, final Object value) throws Throwable {
		try {
			final Object obj = METHOD_WRITE_VALUE_AS_STRING.invoke(null, instance, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCellComment() {
		//
		Assertions.assertDoesNotThrow(() -> setCellComment(null,
				createCellComment(createDrawingPatriarch(null), createClientAnchor(getCreationHelper(null)))));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static CreationHelper getCreationHelper(final Workbook instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CREATION_HELPER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof CreationHelper) {
				return (CreationHelper) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> biPredicate, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredFields() throws Throwable {
		//
		Assertions.assertNull(getDeclaredFields(null));
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELDS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field[]) {
				return (Field[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaringClass() throws Throwable {
		//
		Assertions.assertNull(getDeclaringClass(null));
		//
	}

	private static Class<?> getDeclaringClass(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARING_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class[]) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertNotNull(toURI(new File("")));
		//
	}

	private static URI toURI(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTitleText() throws Throwable {
		//
		Assertions.assertNull(getTitleText(null));
		//
	}

	private static String getTitleText(final HtmlPage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TITLE_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCssEnabled() {
		//
		Assertions.assertDoesNotThrow(() -> setCssEnabled(null, false));
		//
	}

	private static void setCssEnabled(final WebClientOptions instance, final boolean enabled) throws Throwable {
		try {
			METHOD_SET_CSS_ENABLED.invoke(null, instance, enabled);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testStop() {
		//
		Assertions.assertDoesNotThrow(() -> stop(null));
		//
	}

	private static Stopwatch stop(final Stopwatch instance) throws Throwable {
		try {
			final Object obj = METHOD_STOP.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stopwatch) {
				return (Stopwatch) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testElapsed() {
		//
		Assertions.assertDoesNotThrow(() -> elapsed(null));
		//
	}

	private static Duration elapsed(final Stopwatch instance) throws Throwable {
		try {
			final Object obj = METHOD_ELAPSED.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Duration) {
				return (Duration) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRate() throws Throwable {
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechRate", new JSlider(), true);
			//
		} // if
			//
		Assertions.assertNotNull(getRate());
		//
	}

	private Integer getRate() throws Throwable {
		try {
			final Object obj = METHOD_GET_RATE.invoke(instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddChangeListener() {
		//
		Assertions.assertDoesNotThrow(() -> addChangeListener(instance, null, (JSlider[]) null));
		//
	}

	private static void addChangeListener(final ChangeListener changeListener, final JSlider instance,
			final JSlider... vs) throws Throwable {
		try {
			METHOD_ADD_CHANGE_LISTENER.invoke(null, changeListener, instance, vs);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testProcess() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> process(null, null, null));
		//
		final Template template = new Template(EMPTY, EMPTY, null);
		//
		Assertions.assertDoesNotThrow(() -> process(template, null, null));
		//
		try (final Writer writer = new StringWriter()) {
			//
			Assertions.assertDoesNotThrow(() -> process(template, null, writer));
			//
		} // try
			//
	}

	private static void process(final Template instance, final Object dataModel, final Writer out) throws Throwable {
		try {
			METHOD_PROCESS.invoke(null, instance, dataModel, out);
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTemplate() throws Throwable {
		//
		Assertions.assertNull(getTemplate(null, null));
		//
		final freemarker.template.Configuration configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.getVersion());
		//
		Assertions.assertNull(getTemplate(configuration, null));
		//
		Assertions.assertNull(getTemplate(configuration, EMPTY));
		//
		configuration.setTemplateLoader(new ClassTemplateLoader(VoiceManager.class, "/"));
		//
		Assertions.assertNotNull(getTemplate(configuration, EMPTY));
		//
	}

	private static Template getTemplate(final freemarker.template.Configuration instance, final String name)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TEMPLATE.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Template) {
				return (Template) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateCellStyle() throws Throwable {
		//
		Assertions.assertNull(createCellStyle(null));
		//
	}

	private static CellStyle createCellStyle(final Workbook instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_CELL_STYLE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof CellStyle) {
				return (CellStyle) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReduce() throws Throwable {
		//
		final long l = 0;
		//
		Assertions.assertEquals(l, reduce(null, l, null));
		//
	}

	private static long reduce(final LongStream instance, final long identity, final LongBinaryOperator op)
			throws Throwable {
		try {
			final Object obj = METHOD_REDUCE.invoke(null, instance, identity, op);
			if (obj instanceof Long) {
				return ((Long) obj).longValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProviderPlatform() throws Throwable {
		//
		Assertions.assertNull(getProviderPlatform(Reflection.newProxy(Provider.class, ih)));
		//
	}

	private static String getProviderPlatform(final Provider instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROVIDER_PLATFORM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenConnection() throws Throwable {
		//
		Assertions.assertNotNull(openConnection(new File("pom.xml").toURI().toURL()));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTempFileMinimumPrefixLength() throws Throwable {
		//
		Assertions.assertNull(getTempFileMinimumPrefixLength(null));
		//
	}

	private static Integer getTempFileMinimumPrefixLength(final org.apache.bcel.classfile.Method method)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TEMP_FILE_MINIMUM_PREFIX_LENGTH.invoke(null, method);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetOsVersionInfoExMap() throws Throwable {
		//
		Assertions.assertNotNull(getOsVersionInfoExMap());
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
			throw new Throwable(toString(getClass(obj)));
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
	void testGetNextElementSibling() throws Throwable {
		//
		Assertions.assertNull(getNextElementSibling(new DomText(null, null)));
		//
	}

	private static DomElement getNextElementSibling(final DomNode instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NEXT_ELEMENT_SIBLING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomElement) {
				return (DomElement) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateJoYoKanJiWorkbookByTBody() throws Throwable {
		//
		final DomNode domNode = new DomText(null, null);
		//
		domNode.appendChild(new DomText(null, null));
		//
		domNode.appendChild(new DomElement(null, EMPTY, null, null));
		//
		Assertions.assertNotNull(createJoYoKanJiWorkbookByTBody(domNode));
		//
	}

	private static Workbook createJoYoKanJiWorkbookByTBody(final DomNode tbody) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_TBODY.invoke(null, tbody);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateJoYoKanJiWorkbookByDomNodes() throws Throwable {
		//
		Assertions.assertNull(createJoYoKanJiWorkbookByDomNodes(Collections.singletonList(null)));
		//
	}

	private static Workbook createJoYoKanJiWorkbookByDomNodes(final List<DomNode> domNodes) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_JO_YO_KAN_JI_WORKBOOK_BY_DOM_NODES.invoke(null, domNodes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrPrintStackTraceOrShowMessageDialog() {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> errorOrPrintStackTraceOrShowMessageDialog(null));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTraceOrShowMessageDialog(true, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTraceOrShowMessageDialog(true, null, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTraceOrShowMessageDialog(true, null, new Throwable()));
		//
	}

	private static void errorOrPrintStackTraceOrShowMessageDialog(final Throwable throwable) throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG1.invoke(null, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void errorOrPrintStackTraceOrShowMessageDialog(final boolean headless, final Throwable throwable)
			throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG2.invoke(null, headless, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void errorOrPrintStackTraceOrShowMessageDialog(final boolean headless, final Logger logger,
			final Throwable throwable) throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_STACK_TRACE_OR_SHOW_MESSAGE_DIALOG3.invoke(null, headless, logger, throwable);
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
		Assertions.assertNotEquals(randomAlphabetic(ONE), randomAlphabetic(ONE));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
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
		final List<?> pages = cast(List.class,
				Narcissus.getObjectField(jTabbedPane, getDeclaredField(JTabbedPane.class, "pages")));
		//
		add(pages, null);
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
			throw new Throwable(toString(getClass(obj)));
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
	void testEencrypt() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> encrypt(null, null, null));
		//
		final File file = File.createTempFile(randomAlphabetic(3), null);
		//
		deleteOnExit(file);
		//
		// xls
		//
		try (final Workbook workbook = new HSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			workbook.write(os);
			//
		} // try
			//
		encrypt(file, null, SPACE);
		//
		// xlsx
		//
		try (final Workbook workbook = new XSSFWorkbook(); final OutputStream os = new FileOutputStream(file)) {
			//
			workbook.write(os);
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
		final File file = File.createTempFile(randomAlphabetic(3), null);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSelect() throws Throwable {
		//
		Assertions.assertNotNull(select(new org.jsoup.nodes.Element("A"), ".a"));
		//
	}

	private static Elements select(final org.jsoup.nodes.Element instance, final String cssQuery) throws Throwable {
		try {
			final Object obj = METHOD_SELECT.invoke(null, instance, cssQuery);
			if (obj == null) {
				return null;
			} else if (obj instanceof Elements) {
				return (Elements) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testText() throws Throwable {
		//
		Assertions.assertNull(text(null));
		//
		Assertions.assertEquals(EMPTY, text(new org.jsoup.nodes.Element("A")));
		//
	}

	private static String text(final org.jsoup.nodes.Element instance) throws Throwable {
		try {
			final Object obj = METHOD_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAttr() throws Throwable {
		//
		Assertions.assertNull(attr(null, null));
		//
		Assertions.assertEquals(EMPTY, attr(new org.jsoup.nodes.Element("A"), EMPTY));
		//
	}

	private static String attr(final org.jsoup.nodes.Element instance, final String attributeKey) throws Throwable {
		try {
			final Object obj = METHOD_ATTR.invoke(null, instance, attributeKey);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(null, null, null));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.getObject(java.lang.Class)
			//
			final Method getObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
					: null;
			//
			final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, getObject, null));
			//
			final Object[] empty = new Object[] {};
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, getObject, empty));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.containsObject(java.lang.Class)
			//
			final Method containsObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("containsObject", Class.class)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, containsObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, containsObject, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(objectMap, containsObject, new Object[] { null }));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
			//
			final Method setObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, setObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectMap, setObject, empty));
			//
			// org.springframework.context.support.VoiceManager$BooleanMap.setBoolean(java.lang.String,boolean)
			//
			final Object booleanMap = Reflection.newProxy(CLASS_BOOLEAN_MAP, ih);
			//
			final Method setBoolean = CLASS_BOOLEAN_MAP != null
					? CLASS_BOOLEAN_MAP.getDeclaredMethod("setBoolean", String.class, Boolean.TYPE)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(booleanMap, setBoolean, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(booleanMap, setBoolean, empty));
			//
			Assertions.assertDoesNotThrow(() -> ih.invoke(booleanMap, setBoolean, new Object[] { null, null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.getObject(int)
			//
			final Class<?> classIntMap = forName("org.springframework.context.support.VoiceManager$IntMap");
			//
			final Object intMap = Reflection.newProxy(classIntMap, ih);
			//
			final Method intMapGetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("getObject", Integer.TYPE)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapGetObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapGetObject, empty));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapGetObject, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.containsKey(int)
			//
			final Method intMapContainsKey = classIntMap != null
					? classIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapContainsKey, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapContainsKey, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(intMap, intMapContainsKey, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.setObject(int,java.lang.Object)
			//
			final Method intMapSetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("setObject", Integer.TYPE, Object.class)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapSetObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intMap, intMapSetObject, empty));
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
			final Class<?> classIntIntMap = forName("org.springframework.context.support.VoiceManager$IntIntMap");
			//
			final Method intIntMapSetInt = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("setInt", Integer.TYPE, Integer.TYPE)
					: null;
			//
			final Object intIntMap = Reflection.newProxy(classIntIntMap, ih);
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intIntMap, intIntMapSetInt, null));
			//
			final Object[] empty = new Object[] {};
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intIntMap, intIntMapSetInt, empty));
			//
			final Integer two = Integer.valueOf(2);
			//
			Assertions.assertDoesNotThrow(
					() -> ih.invoke(intIntMap, intIntMapSetInt, new Object[] { Integer.valueOf(ONE), two }));
			//
			// org.springframework.context.support.VoiceManager$IntIntMap.containsKey(int)
			//
			final Method intIntMapContainsKey = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intIntMap, intIntMapContainsKey, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(intIntMap, intIntMapContainsKey, empty));
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
			// org.springframework.context.support.VoiceManager$IH.getSimpleName(java.lang.class)
			//
			final Method getSimpleName = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("getSimpleName", Class.class)
					: null;
			//
			if (getSimpleName != null) {
				//
				getSimpleName.setAccessible(true);
				//
			} // if
				//
			Assertions.assertNull(invoke(getSimpleName, null, (Object) null));
			//
			Assertions.assertEquals("byte[]", invoke(getSimpleName, null, clz));
			//
		} // if
			//
	}

	private static InvocationHandler createVoiceManagerIH() throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, Throwable {
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		return cast(InvocationHandler.class, constructor != null ? constructor.newInstance() : null);
		//
	}

	@Test
	void testImportTask() throws Throwable {
		//
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$ImportTask");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Runnable runnable = cast(Runnable.class, constructor != null ? constructor.newInstance() : null);
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
		FieldUtils.writeDeclaredField(runnable, "counter", Integer.valueOf(0), true);
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
		// org.springframework.context.support.VoiceManager.ImportTask.infoOrPrintln(org.slf4j.Logger,java.io.PrintStream,java.lang.String)
		//
		final Method infoOrPrintln = clz != null
				? clz.getDeclaredMethod("infoOrPrintln", Logger.class, PrintStream.class, String.class)
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
		final Method add = clz != null ? clz.getDeclaredMethod("add", Fraction.class, Fraction.class) : null;
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
				? CLASS_EXPORT_TASK.getDeclaredMethod("generateOdfPresentationDocuments", InputStream.class,
						CLASS_BOOLEAN_MAP, String.class, Table.class, String.class)
				: null;
		//
		if (generateOdfPresentationDocuments != null) {
			//
			generateOdfPresentationDocuments.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(generateOdfPresentationDocuments, null, null, null, null, null, null));
		//
		Assertions.assertThrows(InvocationTargetException.class, () -> invoke(generateOdfPresentationDocuments, null,
				null, null, null, ImmutableTable.of(EMPTY, EMPTY, new Voice()), null));
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
		Assertions.assertNull(invoke(newTransformer, null, (Object) null));
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
		// org.springframework.context.support.VoiceManager$ExportTask.replaceText(javax.xml.xpath.XPath,org.w3c.dom.Node,domain.Voice)
		//
		final Method replaceText = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("replaceText", CLASS_OBJECT_MAP)
				: null;
		//
		if (replaceText != null) {
			//
			replaceText.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(replaceText, null, (Object) null));
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
		// org.springframework.context.support.VoiceManager$ExportTask.putTemplate(freemarker.cache.StringTemplateLoader,java.lang.String,java.lang.String)
		//
		final Method putTemplate = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("putTemplate", StringTemplateLoader.class, String.class,
						String.class)
				: null;
		//
		if (putTemplate != null) {
			//
			putTemplate.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(putTemplate, null, null, null, null));
		//
		Assertions.assertNull(invoke(putTemplate, null, new StringTemplateLoader(), EMPTY, EMPTY));
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
		final ObjectMapper objectMapper = new ObjectMapper();
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
		// org.springframework.context.support.VoiceManager$ExportTask.testAndRun(boolean,org.apache.commons.lang3.function.FailableRunnable)
		//
		final Method testAndRun = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("testAndRun", Boolean.TYPE, FailableRunnable.class)
				: null;
		//
		if (testAndRun != null) {
			//
			testAndRun.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(testAndRun, null, Boolean.FALSE, null));
		//
		Assertions.assertNull(invoke(testAndRun, null, Boolean.TRUE, null));
		//
		Assertions.assertNull(invoke(testAndRun, null, Boolean.TRUE, Reflection.newProxy(FailableRunnable.class, ih)));
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
		final Constructor<?> constructor = OdfPackage.class.getDeclaredConstructor();
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPassword, null, constructor != null ? constructor.newInstance() : null, null));
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
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$VoiceIdListCellRenderer");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor(VoiceManager.class) : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final ListCellRenderer<?> listCellRenderer1 = cast(ListCellRenderer.class,
				constructor != null ? constructor.newInstance(this.instance) : null);
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
	void testAudioToFlacByteConverter() throws Throwable {
		//
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$AudioToFlacByteConverter");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = constructor != null ? constructor.newInstance() : null;
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
								: null, null, new AudioFormat(0, 1, 2, true, false)),
						ToStringStyle.SHORT_PREFIX_STYLE));
		//
	}

	@Test
	void testAudioToMp3ByteConverter() throws Throwable {
		//
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$AudioToMp3ByteConverter");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		// convert(byte[])
		//
		final Method convert = clz != null ? clz.getDeclaredMethod("convert", byte[].class) : null;
		//
		Assertions.assertNull(invoke(convert, instance, (Object) null));
		//
		Assertions.assertNull(invoke(convert, instance, new byte[] {}));
		//
		Assertions.assertThrows(RuntimeException.class, () -> {
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
		// get(java.util.Map,java.lang.Object)
		//
		Assertions.assertNull(invoke(clz != null ? clz.getDeclaredMethod("get", Map.class, Object.class) : null,
				instance, null, null));
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
		Assertions.assertThrows(IllegalStateException.class, () -> {
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
		// getName(org.apache.bcel.classfile.FieldOrMethod)
		//
		final Method getName = clz != null ? clz.getDeclaredMethod("getName", FieldOrMethod.class) : null;
		//
		Assertions.assertNull(invoke(getName, instance, (Object) null));
		//
	}

	@Test
	void testEmptyFilePredicate() throws Throwable {
		//
		final Predicate<Object> predicate = cast(Predicate.class,
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
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$JLabelLink");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor(ATag.class) : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final MouseListener[] mouseListeners = getMouseListeners(
				cast(Component.class, constructor != null ? constructor.newInstance((Object) null) : null));
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

}