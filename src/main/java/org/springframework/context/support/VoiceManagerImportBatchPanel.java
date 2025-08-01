package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiConsumerUtil;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableRunnableUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.function.OnlineNHKJapanesePronunciationsAccentFailableFunction;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentUtil;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.ooxml.POIXMLPropertiesUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.FormulaEvaluatorUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
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
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.VoiceManager.ByteConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterUtil;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Range;
import com.google.common.collect.RangeUtil;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;
import com.mariten.kanatools.KanaConverter;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.Mp3FileUtil;

import domain.JlptVocabulary;
import domain.Pronunciation;
import domain.Voice;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import fr.free.nrw.jakaroma.JakaromaUtil;
import io.github.toolfactory.narcissus.Narcissus;
import jnafilechooser.api.WindowsFolderBrowser;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerImportBatchPanel extends JPanel implements Titled, InitializingBean, EnvironmentAware,
		ActionListener, BeanFactoryPostProcessor, ItemListener, ChangeListener, ApplicationContextAware {

	private static final long serialVersionUID = 8152096292066653831L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerImportBatchPanel.class);

	private static final int TEMP_FILE_MINIMUM_PREFIX_LENGTH = Util.intValue(getTempFileMinimumPrefixLength(), 3);

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
	 * @see java.lang.Class#getResourceAsStream(java.lang.String)
	 */
	private static final String CLASS_RESOURCE_FORMAT = "/%1$s.class";

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER = "Romaji";

	private static final String NO_FILE_SELECTED = "No File Selected";

	private static final String OLE_2_COMPOUND_DOCUMENT = "OLE 2 Compound Document";

	private static final String SHA_512 = "SHA-512";

	private static final String FORMAT = "format";

	private static final String PASSWORD = "Password";

	private static final String LANGUAGE = "Language";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String SPEECH_RATE = "Speech Rate";

	private static final String COMPONENT = "component";

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static IValue0<Method> METHOD_RANDOM_ALPHABETIC = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Group("Import")
	@Note("Import a single Spread Sheet File")
	private AbstractButton btnImport = null;

	@Group("Import")
	@Note("Import Spread Sheet File(s) within a specified folder")
	private AbstractButton btnImportWithinFolder = null;

	@Group("Import")
	@Note("Generate a Blank Row in Import File Template")
	private AbstractButton cbImportFileTemplateGenerateBlankRow = null;

	@Group("Import")
	@Note("Import File Template")
	private AbstractButton btnImportFileTemplate = null;

	private AbstractButton btnExecute = null;

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateSlower = null;

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateNormal = null;

	@Group(SPEECH_RATE)
	private AbstractButton btnSpeechRateFaster = null;

	private JProgressBar progressBarImport = null;

	@Note("Current Processing File")
	private JTextComponent tfCurrentProcessingFile = null;

	@Note("Current Processing Sheet")
	private JTextComponent tfCurrentProcessingSheetName = null;

	@Note("Current Processing Voice")
	private JTextComponent tfCurrentProcessingVoice = null;

	@Note("Speech Rate")
	private JTextComponent tfSpeechRate = null;

	@Note("Speech Language Code")
	private JTextComponent tfSpeechLanguageCode = null;

	private JTextComponent tfSpeechLanguageName = null;

	private JTextComponent tfSpeechVolume = null;

	@Note("Import Result")
	private DefaultTableModel tmImportResult = null;

	private DefaultTableModel tmImportException = null;

	@Note("Speech Volume")
	private JSlider jsSpeechVolume = null;

	private JSlider jsSpeechRate = null;

	private JComboBox<Object> jcbVoiceId = null;

	private transient ComboBoxModel<String> cbmVoiceId = null;

	private transient PropertyResolver propertyResolver = null;

	@Note("Voice Folder")
	private String voiceFolder = null;

	private String messageDigestAlgorithm = null;

	private transient IValue0<List<String>> jlptLevels = null;

	private transient IValue0<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private ObjectMapper objectMapper = null;

	private transient SqlSessionFactory sqlSessionFactory = null;

	private transient SpeechApi speechApi = null;

	private transient Jakaroma jakaroma = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private transient ApplicationContext applicationContext = null;

	private String[] voiceIds = null;

	private transient OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction = null;

	private String preferredPronunciationAudioFormat = null;

	@Nullable
	private String[] mp3Tags = null;

	private transient Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter = null;

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = Unit.with(jlptLevels);
	}

	public void setGaKuNenBeTsuKanJiMultimap(final Multimap<String, String> gaKuNenBeTsuKanJiMultimap) {
		this.gaKuNenBeTsuKanJiMultimap = Unit.with(gaKuNenBeTsuKanJiMultimap);
	}

	public void setLanguageCodeToTextObjIntFunction(
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		this.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
	}

	public void setOnlineNHKJapanesePronunciationsAccentFailableFunction(
			final OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction) {
		this.onlineNHKJapanesePronunciationsAccentFailableFunction = onlineNHKJapanesePronunciationsAccentFailableFunction;
	}

	public void setPreferredPronunciationAudioFormat(final String preferredPronunciationAudioFormat) {
		this.preferredPronunciationAudioFormat = preferredPronunciationAudioFormat;
	}

	@SuppressWarnings("java:S1612")
	public void setMp3Tags(final Object value) {
		//
		mp3Tags = Util.toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setVoiceIdListCellRendererConverter(
			final Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter) {
		this.voiceIdListCellRendererConverter = voiceIdListCellRendererConverter;
	}

	@Nullable
	private static Integer getTempFileMinimumPrefixLength() {
		//
		Integer result = null;
		//
		final Class<?> clz = File.class;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format(CLASS_RESOURCE_FORMAT, StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/")))) {
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
				result = getTempFileMinimumPrefixLength(IterableUtils.get(ms, 0));
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

	@Override
	public String getTitle() {
		return "Import(Batch)";
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(ObjectUtils.getIfNull(IValue0Util.getValue0(getLayoutManager(applicationContext)), MigLayout::new));
		//
		// Voice Id
		//
		add(new JLabel("Voice Id"));
		//
		if ((cbmVoiceId = testAndApply(Objects::nonNull,
				voiceIds = testAndApply(x -> SpeechApi.isInstalled(x), speechApi, x -> SpeechApi.getVoiceIds(x), null),
				x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
			//
			(jcbVoiceId = new JComboBox(cbmVoiceId)).addItemListener(this);
			//
			testAndAccept((a, b) -> b != null, jcbVoiceId, ConverterUtil.convert(voiceIdListCellRendererConverter,
					Util.getRenderer(Util.cast(JComboBox.class, jcbVoiceId))), (a, b) -> setRenderer(a, b));
			//
			final JPanel jPanel = new JPanel();
			//
			jPanel.setLayout(new MigLayout());
			//
			add(jPanel, jcbVoiceId, String.format("%1$s,span %2$s", GROWX, 3));
			//
			add(jPanel, tfSpeechLanguageCode = new JTextField(), String.format("wmin %1$s", 30));
			//
			add(jPanel, tfSpeechLanguageName = new JTextField(), String.format("wmin %1$s", 230));
			//
			add(jPanel, String.format("%1$s,span %2$s", WRAP, 1));
			//
		} // if
			//
			// Speech Rate
			//
		final Object speechApiInstance = getInstance(speechApi);
		//
		final Lookup lookup = Util.cast(Lookup.class, speechApiInstance);
		//
		final Predicate<String> predicate = a -> Lookup.contains(lookup, "rate", a);
		//
		final FailableFunction<String, Object, RuntimeException> function = a -> Lookup.get(lookup, "rate", a);
		//
		final JPanel jPanel = new JPanel();
		//
		jPanel.setLayout(new MigLayout());
		//
		if (Boolean.logicalAnd(Util.test(predicate, "min"), Util.test(predicate, "max"))) {
			//
			add(new JLabel(SPEECH_RATE), "aligny top");
			//
			final Range<Integer> range = RangeUtil.createRange(
					toInteger(testAndApply(predicate, "min", function, null)),
					toInteger(testAndApply(predicate, "max", function, null)));
			//
			add(jPanel, jsSpeechRate = new JSlider(Util.intValue(RangeUtil.lowerEndpoint(range), 0),
					Util.intValue(RangeUtil.upperEndpoint(range), 0)), String.format("growx,wmin %1$s", 300));
			//
			setMajorTickSpacing(jsSpeechRate, 1);
			//
			setPaintTicks(jsSpeechRate, true);
			//
			setPaintLabels(jsSpeechRate, true);
			//
			add(jPanel, tfSpeechRate = new JTextField(), String.format("wmin %1$s", 25));
			//
			Util.setEditable(tfSpeechRate, false);
			//
			if (RangeUtil.hasLowerBound(range) && RangeUtil.hasUpperBound(range)
					&& RangeUtil.lowerEndpoint(range) != null && RangeUtil.upperEndpoint(range) != null) {
				//
				setValue(jsSpeechRate,
						PropertyResolverUtil.getProperty(propertyResolver,
								"org.springframework.context.support.VoiceManager.speechRate"),
						a -> stateChanged(new ChangeEvent(a)));
				//
			} // if
				//
			add(jPanel, new JLabel(""));
			//
		} // if
			//
			// Speech Volume
			//
		add(jPanel, new JLabel("Speech Volume"), "aligny top");
		//
		final Range<Integer> speechVolumeRange = createVolumeRange(speechApiInstance);
		//
		final Integer upperEnpoint = testAndApply(RangeUtil::hasUpperBound, speechVolumeRange, RangeUtil::upperEndpoint,
				null);
		//
		add(jPanel,
				jsSpeechVolume = new JSlider(Util.intValue(
						testAndApply(RangeUtil::hasLowerBound, speechVolumeRange, RangeUtil::lowerEndpoint, null), 0),
						Util.intValue(upperEnpoint, 100)),
				String.format("%1$s,wmin %2$s", GROWX, 300));
		//
		setSpeechVolume(valueOf(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.speechVolume")), upperEnpoint);
		//
		setMajorTickSpacing(jsSpeechVolume, 10);
		//
		setPaintTicks(jsSpeechVolume, true);
		//
		setPaintLabels(jsSpeechVolume, true);
		//
		add(jPanel, tfSpeechVolume = new JTextField(), String.format("wmin %1$s", 25));
		//
		tfSpeechVolume.setEnabled(false);
		//
		add(jPanel, String.format("%1$s,span %2$s", WRAP, 5));
		//
		add(new JLabel("Import"));
		//
		add(btnImport = new JButton("Import a Single Spreadsheet"), String.format(SPAN_ONLY_FORMAT, 2));
		//
		add(btnImportWithinFolder = new JButton("Import SpreadSheet(s) Within a Folder"),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		add(new JLabel("Import Template"));
		//
		add(cbImportFileTemplateGenerateBlankRow = new JCheckBox("Generate a Blank Row"));
		//
		cbImportFileTemplateGenerateBlankRow
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.importFileTemplateGenerateBlankRow")));
		//
		add(btnImportFileTemplate = new JButton("Generate"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		// Progress
		//
		add(progressBarImport = new JProgressBar(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5));
		//
		progressBarImport.setStringPainted(true);
		//
		add(new JLabel("Current Processing File"));
		//
		final String wrap = String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5);
		//
		add(tfCurrentProcessingFile = new JTextField(), wrap);
		//
		add(new JLabel("Current Processing Sheet"));
		//
		add(tfCurrentProcessingSheetName = new JTextField(), String.format("%1$s,wmin %2$s,span %3$s", GROWX, 300, 2));
		//
		add(new JLabel("Voice"));
		//
		add(tfCurrentProcessingVoice = new JTextField(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		add(new JLabel("Import Result"));
		//
		JScrollPane scp = new JScrollPane(new JTable(tmImportResult = new DefaultTableModel(
				new Object[] { "Number Of Sheet Processed", "Number of Voice Processed" }, 0)));
		//
		Dimension d = Util.getPreferredSize(scp);
		//
		if (d != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 40));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		add(scp, wrap);
		//
		add(new JLabel("Import Exception"));
		//
		add(scp = new JScrollPane(new JTable(tmImportException = new DefaultTableModel(
				new Object[] { "Text", ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER, "Exception" }, 0))), wrap);
		//
		if ((d = Util.getPreferredSize(scp)) != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 55));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		Util.forEach(Stream.of(btnImport, btnImportWithinFolder, btnImportFileTemplate),
				x -> Util.addActionListener(x, this));
		//
		Util.forEach(Stream.of(tfCurrentProcessingFile, tfCurrentProcessingSheetName, tfCurrentProcessingVoice),
				x -> Util.setEditable(x, false));
		//
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		final boolean b = folder != null && Util.exists(folder) && folder.isDirectory();
		//
		Util.setEnabled(btnExecute, b);
		//
		if (!b) {
			//
			setToolTipText(btnExecute, String.format("Please create \"%1$s\" folder.", Util.getAbsolutePath(folder)));
			//
		} // if
			//
		addChangeListener(this, jsSpeechVolume, jsSpeechRate);
		//
	}

	@Nullable
	private static IValue0<LayoutManager> getLayoutManager(final ApplicationContext applicationContext)
			throws Exception {
		//
		final Iterable<Entry<String, Object>> entrySet = Util
				.entrySet(ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class));
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<LayoutManager> iValue0 = null;
		//
		AutowireCapableBeanFactory acbf = null;
		//
		List<Field> fs = null;
		//
		LayoutManager lm = null;
		//
		for (final Entry<String, Object> entry : entrySet) {
			//
			if (!(Util.getValue(entry) instanceof LayoutManager)) {
				//
				continue;
				//
			} // if
				//
			fs = Util.toList(Util.filter(
					Util.stream(FieldUtils.getAllFieldsList(Util.getClass(
							acbf = ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext)))),
					x -> Objects.equals(Util.getName(x), "singletonObjects")));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if ((lm = Util
						.cast(LayoutManager.class,
								FactoryBeanUtil
										.getObject(Util.cast(FactoryBean.class,
												MapUtils.getObject(
														Util.cast(Map.class,
																Narcissus.getObjectField(acbf,
																		IterableUtils.get(fs, i))),
														Util.getKey(entry)))))) != null) {
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
		return iValue0;
		//
	}

	private static void addChangeListener(final ChangeListener changeListener, final JSlider instance,
			@Nullable final JSlider... vs) {
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

	private static void addChangeListener(@Nullable final JSlider instance, final ChangeListener changeListener) {
		if (instance != null) {
			instance.addChangeListener(changeListener);
		}
	}

	private void setSpeechVolume(@Nullable final Number speechVolume, @Nullable final Number upperEnpoint) {
		//
		if (speechVolume != null) {
			//
			setValue(jsSpeechVolume, Math.min(speechVolume.intValue(), Util.intValue(upperEnpoint, 100)));
			//
		} else if (upperEnpoint != null) {
			//
			setValue(jsSpeechVolume, upperEnpoint.intValue());
			//
		} // if
			//
	}

	private static Range<Integer> createVolumeRange(final Object instance) {
		//
		final Lookup lookup = Util.cast(Lookup.class, instance);
		//
		final BiPredicate<String, String> biPredicate = (a, b) -> Lookup.contains(lookup, a, b);
		//
		final FailableBiFunction<String, String, Object, RuntimeException> biFunction = (a, b) -> Lookup.get(lookup, a,
				b);
		//
		return RangeUtil.createRange(toInteger(testAndApply(biPredicate, "volume", "min", biFunction, null)),
				toInteger(testAndApply(biPredicate, "volume", "max", biFunction, null)));
		//
	}

	@Nullable
	private static Integer toInteger(final Object object) {
		//
		Integer integer = null;
		//
		if (object instanceof Integer i) {
			//
			integer = i;
			//
		} else if (object instanceof Number number) {
			//
			integer = Integer.valueOf(Util.intValue(number, 0));
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

	private static Object getInstance(final SpeechApi speechApi) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(speechApi)), Arrays::stream, null),
				f -> Objects.equals(Util.getName(f), "instance")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
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
			if (Narcissus.getObjectField(instance, Util.getDeclaredField(Container.class, COMPONENT)) == null) {
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

	private static void setValue(@Nullable final JSlider instance, final Method method,
			final Consumer<JSlider> consumer, final boolean headless) {
		//
		try {
			//
			final Integer i = Util.cast(Integer.class, invoke(method, instance));
			//
			if (instance != null && i != null) {
				//
				instance.setValue(i.intValue());
				//
				Util.accept(consumer, instance);
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

	private static void setValue(@Nullable final JSlider instance, final String string,
			final Consumer<JSlider> consumer) {
		//
		Integer i = valueOf(string);
		//
		if (i != null) {
			//
			if (instance != null && i >= instance.getMinimum() && i <= instance.getMaximum()) {
				//
				setValue(instance, i.intValue());
				//
				Util.accept(consumer, instance);
				//
			} // if
				//
		} else {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(instance)), Arrays::stream,
							null),
					x -> x != null && Objects.equals(Util.getReturnType(x), Integer.TYPE) && x.getParameterCount() == 0
							&& StringsUtil.startsWith(Strings.CI, Util.getName(x), "get" + string)));
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
						Util.collect(sorted(Util.map(Util.stream(ms), Util::getName), ObjectUtils::compare),
								Collectors.joining(",")));
				//
			} // if
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

	private static void setValue(@Nullable final JSlider instance, final int n) {
		if (instance != null) {
			instance.setValue(n);
		}
	}

	private static void setPaintLabels(@Nullable final JSlider instance, final boolean b) {
		if (instance != null) {
			instance.setPaintLabels(b);
		}
	}

	private static void setPaintTicks(@Nullable final JSlider instance, final boolean b) {
		if (instance != null) {
			instance.setPaintTicks(b);
		}
	}

	private static void setMajorTickSpacing(@Nullable final JSlider instance, final int n) {
		if (instance != null) {
			instance.setMajorTickSpacing(n);
		}
	}

	private static void add(@Nullable final Container instance, @Nullable final Component comp,
			final Object constraints) {
		//
		if (instance == null) {
			//
			return;
			//
		} //
			//
		try {
			//
			if (Narcissus.getObjectField(instance, Util.getDeclaredField(Container.class, COMPONENT)) == null) {
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

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static void setToolTipText(@Nullable final JComponent instance, final String toolTipText) {
		if (instance != null) {
			instance.setToolTipText(toolTipText);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final VoiceManager voiceManager = VoiceManager.INSTANCE;
		//
		accept(x -> Util.setText(Util.cast(JTextComponent.class, x), null), tfCurrentProcessingSheetName,
				tfCurrentProcessingVoice);
		//
		if (voiceManager != null) {
			//
			try {
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
		clear(tmImportResult);
		//
		final Object source = Util.getSource(evt);
		//
		// Import
		//
		testAndRun(Util.contains(getObjectsByGroupAnnotation(this, "Import"), source),
				() -> actionPerformedForImport(source, GraphicsEnvironment.isHeadless()));
		//
	}

	private void actionPerformedForImport(final Object source, final boolean headless) {
		//
		if (Objects.equals(source, btnImportFileTemplate)) {
			//
			actionPerformedForImportFileTemplate(headless, new JFileChooser("."));

			return;
			//
		} else if (Objects.equals(source, btnImport)) {
			//
			actionPerformedForBtnImport(headless);
			//
			return;
			//
		} else if (Objects.equals(source, btnImportWithinFolder)) {
			//
			importByWorkbookFiles(
					//
					listFiles(new WindowsFolderBrowser().showDialog(VoiceManager.INSTANCE))
					//
					, headless);
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private void importByWorkbookFiles(@Nullable final File[] fs, final boolean headless) {
		//
		File f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			try {
				//
				if (!Util.isFile(f = fs[i]) || getWorkbook(f) == null) {
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

	@Nullable
	private static File[] listFiles(@Nullable final File instance) {
		return instance != null ? instance.listFiles() : null;
	}

	private void actionPerformedForBtnImport(final boolean headless) {
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		setFileSelectionMode(jfc, JFileChooser.FILES_ONLY);
		//
		if (!headless && showOpenDialog(jfc, null) == JFileChooser.APPROVE_OPTION) {
			//
			importVoice(getSelectedFile(jfc));
			//
		} // if
			//
	}

	@Override
	public void stateChanged(final ChangeEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, jsSpeechVolume)) {
			//
			Util.setText(tfSpeechVolume, Util.toString(getValue(jsSpeechVolume)));
			//
		} else if (Objects.equals(source, jsSpeechRate)) {
			//
			if (jsSpeechRate != null) {
				//
				Util.setEnabled(btnSpeechRateSlower,
						Util.intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMinimum());
				//
				Util.setEnabled(btnSpeechRateFaster,
						Util.intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMaximum());
				//
			} // if
				//
			Util.setText(tfSpeechRate, Util.toString(getValue(jsSpeechRate)));
			//
		} else {
			//
//			throw new UnsupportedOperationException();//TODO
			//
		} // if
			//
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
					Util.addRow(tableModel, new Object[] { getText(v), getRomaji(v), m });
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

		private static void errorOrPrintln(@Nullable final Logger logger, @Nullable final PrintStream ps,
				final String message) {
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

	private static PrintStream getSystemPrintStreamByFieldName(final String name) throws IllegalAccessException {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(System.class), Arrays::stream, null),
				f -> Objects.equals(Util.getType(f), PrintStream.class) && Objects.equals(Util.getName(f), name)));
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return Util.cast(PrintStream.class, get(f, null));
		//
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
					Util.addRow(tableModel, new Object[] { getText(v), getRomaji(v), e });
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
			Util.setText(jTextComponent, getText(v));
			//
			incrementAndGet(atomicInteger);
			//
		}

		@Nullable
		private static Integer incrementAndGet(@Nullable final AtomicInteger instance) {
			return instance != null ? Integer.valueOf(instance.incrementAndGet()) : null;
		}

	}

	private static class ImportVoiceParameters {

		private ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

		@Nullable
		private static ObjIntFunction<String, String> getLanguageCodeToTextObjIntFunction(
				@Nullable final ImportVoiceParameters instance) {
			return instance != null ? instance.languageCodeToTextObjIntFunction : null;
		}

	}

	@SuppressWarnings("java:S1612")
	private void importVoice(final File file) {
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try (final Workbook workbook = getWorkbook(file)) {
			//
			if (workbook != null) {
				//
				Util.setText(tfCurrentProcessingFile, Util.getName(file));
				//
			} // if
				//
			final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
			//
			final POIXMLDocument poiXmlDocument = Util.cast(POIXMLDocument.class, workbook);
			//
			final List<String> sheetExclued = Util
					.toList(Util.map(
							Util.stream(getObjectList(getObjectMapper(),
									getLpwstr(testAndApply(VoiceManagerImportBatchPanel::contains,
											POIXMLPropertiesUtil.getCustomProperties(
													POIXMLDocumentUtil.getProperties(poiXmlDocument)),
											"sheetExcluded", VoiceManagerImportBatchPanel::getProperty, null)))),
							x -> Util.toString(x)));
			//
			ObjectMap.setObject(objectMap, File.class, file);
			//
			ObjectMap.setObject(objectMap, VoiceManager.class, VoiceManager.INSTANCE);
			//
			ObjectMap.setObject(objectMap, VoiceManagerImportBatchPanel.class, this);
			//
			ObjectMap.setObject(objectMap, String.class, voiceFolder);
			//
			ObjectMap.setObject(objectMap, SqlSessionFactory.class, sqlSessionFactory);
			//
			ObjectMap.setObject(objectMap, JProgressBar.class, progressBarImport);
			//
			ObjectMap.setObject(objectMap, Provider.class, Util.cast(Provider.class, speechApi));
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
			ObjectMap.setObject(objectMap, OnlineNHKJapanesePronunciationsAccentFailableFunction.class,
					onlineNHKJapanesePronunciationsAccentFailableFunction);
			//
			BiConsumer<Voice, String> errorMessageConsumer = null;
			//
			BiConsumer<Voice, Throwable> throwableConsumer = null;
			//
			Consumer<Voice> voiceConsumer = null;
			//
			Sheet sheet = null;
			//
			accept(VoiceManagerImportBatchPanel::clear, tmImportResult, tmImportException);
			//
			Integer numberOfSheetProcessed = null;
			//
			final AtomicInteger numberOfVoiceProcessed = new AtomicInteger();
			//
			IValue0<String> voiceId = null;
			//
			Collection<Object> throwableStackTraceHexs = null;
			//
			ImportVoiceParameters ivps = null;
			//
			StringMap stringMap = null;
			//
			for (int i = 0; i < WorkbookUtil.getNumberOfSheets(workbook); i++) {
				//
				if (Util.contains(sheetExclued, SheetUtil.getSheetName(sheet = WorkbookUtil.getSheetAt(workbook, i)))) {
					//
					continue;
					//
				} // if
					//
				setMaximum(progressBarImport, Math.max(0, (Util.intValue(getPhysicalNumberOfRows(sheet), 0)) - 1));
				//
				ObjectMap.setObject(objectMap, ByteConverter.class,
						getByteConverter(configurableListableBeanFactory, FORMAT,
								getLpwstr(testAndApply(VoiceManagerImportBatchPanel::contains,
										POIXMLPropertiesUtil
												.getCustomProperties(POIXMLDocumentUtil.getProperties(poiXmlDocument)),
										"audioFormat", VoiceManagerImportBatchPanel::getProperty, null))));
				//
				if ((ivps = ObjectUtils.getIfNull(ivps, ImportVoiceParameters::new)) != null) {
					//
					ivps.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
					//
				} // if
					//
				StringMap.setString(
						stringMap = ObjectUtils.getIfNull(stringMap,
								() -> Reflection.newProxy(StringMap.class, new IH())),
						"voiceId", IValue0Util.getValue0(voiceId));
				//
				StringMap.setString(stringMap, "preferredPronunciationAudioFormat", preferredPronunciationAudioFormat);
				//
				ObjectMap.setObject(objectMap, ImportVoiceParameters.class, ivps);
				//
				if (voiceId == null) {
					//
					voiceId = Unit.with(getIfNull(Util.toString(Util.getSelectedItem(cbmVoiceId)),
							() -> getVoiceIdForExecute(!isTestMode())));
					//
				} // if
					//
				importVoice(sheet, objectMap,
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
						, mp3Tags
				//
				);
				//
				Util.setText(tfCurrentProcessingSheetName, SheetUtil.getSheetName(sheet));
				//
				numberOfSheetProcessed = Integer.valueOf(Util.intValue(numberOfSheetProcessed, 0) + 1);
				//
			} // for
				//
			Util.addRow(tmImportResult, new Object[] { numberOfSheetProcessed, numberOfVoiceProcessed });
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			errorOrAssertOrShowException(headless, ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} catch (final Exception e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
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

		@Nullable
		private Voice voice = null;

		private File file = null;

		private NumberFormat percentNumberFormat = null;

		private String currentSheetName = null;

		@Override
		public void run() {
			//
			final Integer sheetCurrent = Util.getKey(sheetCurrentAndTotal);
			//
			final Integer sheetTotal = Util.getValue(sheetCurrentAndTotal);
			//
			final Fraction fraction1 = sheetTotal != null && sheetTotal.intValue() != 0
					? Fraction.getFraction(Util.intValue(sheetCurrent, 0), sheetTotal.intValue())
					: null;
			//
			Fraction fraction2 = sheetTotal != null ? Fraction.getFraction(1, sheetTotal) : null;
			//
			if (fraction2 != null && count != null) {
				//
				fraction2 = fraction2.multiplyBy(Fraction.getFraction(Util.intValue(counter, 0), count.intValue()));
				//
			} // if
				//
			final Fraction percentage = add(fraction1, fraction2);
			//
			try {
				//
				infoOrPrintln(LOG, getSystemPrintStreamByFieldName("out"), String.format(
						"%1$s %2$s/%3$s (%4$s) %5$s/%6$s",
						percentage != null
								? StringUtils.leftPad(format(percentNumberFormat, Util.doubleValue(percentage, 0)), 5,
										' ')
								: null,
						StringUtils.leftPad(Util.toString(sheetCurrent),
								StringUtils.length(Util.toString(ObjectUtils.max(sheetCurrent, sheetTotal))), ' '),
						sheetTotal, currentSheetName,
						StringUtils.leftPad(Util.toString(counter), StringUtils.length(Util.toString(count))), count));
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
				ObjectMap.setObject(objectMap, VoiceMapper.class,
						org.apache.ibatis.session.ConfigurationUtil.getMapper(
								SqlSessionFactoryUtil.getConfiguration(sqlSessionFactory), VoiceMapper.class,
								sqlSession = SqlSessionFactoryUtil.openSession(sqlSessionFactory)));
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
					Util.accept(voiceConsumer, voice);
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

		private static void setString(@Nullable final JProgressBar instance, final String string) {
			if (instance != null) {
				instance.setString(string);
			}
		}

		@Nullable
		private static String format(@Nullable final NumberFormat instance, final double number) {
			return instance != null ? instance.format(number) : null;
		}

		private static void setValue(@Nullable final JProgressBar instance, final int n) {
			if (instance != null) {
				instance.setValue(n);
			}
		}

		@Nullable
		private static Fraction add(@Nullable final Fraction a, @Nullable final Fraction b) {
			return a != null && b != null ? a.add(b) : a;
		}

		private static void infoOrPrintln(@Nullable final Logger logger, @Nullable final PrintStream ps,
				final String value) {
			//
			if (logger != null && !LoggerUtil.isNOPLogger(logger)) {
				LoggerUtil.info(logger, value);
			} else if (ps != null) {
				ps.println(value);
			} // if
				//
		}

	}

	private static interface IntMap<T> {

		@Nullable
		T getObject(final int key);

		boolean containsKey(final int key);

		void setObject(final int key, final T value);

		@Nullable
		static <T> T getObject(@Nullable final IntMap<T> instance, final int key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> boolean containsKey(@Nullable final IntMap<T> instance, final int key) {
			return instance != null && instance.containsKey(key);
		}

		static <T> void setObject(@Nullable final IntMap<T> instance, final int key, final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
		}

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, final String value);

		@Nullable
		private static String getString(@Nullable final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}

		private static void setString(@Nullable final StringMap instance, final String key, final String value) {
			if (instance != null) {
				instance.setString(key, value);
			}
		}

	}

	private static void importVoice(@Nullable final Sheet sheet, final ObjectMap _objectMap,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer, final Collection<Object> throwableStackTraceHexs,
			final String[] mp3Tags) throws Exception {
		//
		final File folder = getParentFile(ObjectMap.getObject(_objectMap, File.class));
		//
		ExecutorService es = null;
		//
		try {
			//
			if (Util.iterator(sheet) == null) {
				//
				return;
				//
			} // if
				//
			boolean first = true;
			//
			Voice voice = null;
			//
			ImportTask it = null;
			//
			NumberFormat percentNumberFormat = null;
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
			final Integer numberOfSheets = WorkbookUtil.getNumberOfSheets(workbook);
			//
			final int maxSheetNameLength = Util
					.orElse(Util.max(Util.mapToInt(
							Util.map(testAndApply(Objects::nonNull, spliterator(workbook),
									x -> StreamSupport.stream(x, false), null), SheetUtil::getSheetName),
							StringUtils::length)), 0);
			//
			FormulaEvaluator formulaEvaluator = null;
			//
			AtomicReference<IntMap<Field>> arIntMap = null;
			//
			IH ih = null;
			//
			for (final Row row : sheet) {
				//
				if (Util.iterator(row) == null) {
					//
					continue;
					//
				} // if
					//
				final ObjectMap objectMap = ObjectUtils.getIfNull(copyObjectMap(_objectMap), _objectMap);
				//
				ObjectMap.setObject(objectMap, Row.class, row);
				//
				ObjectMap.setObject(objectMap, ObjectMapper.class,
						objectMapper = getIfNull(objectMapper, ObjectMapper::new));
				//
				ObjectMap.setObject(objectMap, FormulaEvaluator.class, formulaEvaluator = getIfNull(formulaEvaluator,
						() -> CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(workbook))));
				//
				setHiraganaOrKatakanaAndRomaji(hiraganaKatakanaConversion, hiraganaRomajiConversion,
						//
						voice = createVoice(objectMap, first,
								arIntMap = ObjectUtils.getIfNull(arIntMap, AtomicReference::new))
						//
						, jakaroma = ObjectUtils.getIfNull(jakaroma,
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
						it.currentSheetName = StringUtils.leftPad(SheetUtil.getSheetName(sheet), maxSheetNameLength);
						//
						it.counter = Integer.valueOf(row.getRowNum());
						//
						it.count = Integer.valueOf(Util.intValue(getPhysicalNumberOfRows(sheet), 0) - 1);
						//
						it.percentNumberFormat = getIfNull(percentNumberFormat, () -> new DecimalFormat("#%"));
						//
						if ((it.voice = voice) != null) {
							//
							ObjectMap.setObject(objectMap, ImportTask.class, it);
							//
							final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
							//
							importVoice(objectMap, folder, StringMap.getString(stringMap, "voiceId"),
									ImportVoiceParameters.getLanguageCodeToTextObjIntFunction(
											ObjectMap.getObject(objectMap, ImportVoiceParameters.class)),
									StringMap.getString(stringMap, "preferredPronunciationAudioFormat"), mp3Tags);
							//
						} // if
							//
						it.objectMap = ObjectUtils.getIfNull(copyObjectMap(objectMap), objectMap);
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
						submit(es, ObjectUtils.getIfNull(Reflection.newProxy(Runnable.class, ih), it));
						//
					} else {
						//
						ObjectMap.setObject(objectMap, Voice.class, voice);
						//
						ObjectMap.setObject(objectMap, File.class, testAndApply(Objects::nonNull, voice,
								x -> Util.toFile(Path.of(Util.getAbsolutePath(folder), getFilePath(x))), x -> folder));
						//
						importVoice(objectMap, errorMessageConsumer, throwableConsumer);
						//
						Util.accept(voiceConsumer, voice);
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

	private static void importVoice(final ObjectMap objectMap, @Nullable final File folder,
			@Nullable final String voiceId,
			@Nullable final ObjIntFunction<String, String> languageCodeToTextObjIntFunction,
			@Nullable final String preferredPronunciationAudioFormat, final String[] mp3Tags) throws Exception {
		//
		final ImportTask it = ObjectMap.getObject(objectMap, ImportTask.class);
		//
		final Voice voice = it != null ? it.voice : null;
		//
		final String filePath = getFilePath(voice);
		//
		if (StringUtils.isNotBlank(filePath)) {
			//
			if (!Util.exists(it.file = Util.toFile(Path.of(filePath)))
					&& !Util.exists(it.file = Util.toFile(Path.of(Util.getAbsolutePath(folder), filePath)))) {
				//
				it.file = null;
				//
			} // if
				//
			setSource(it.voice, StringUtils.defaultIfBlank(getSource(voice),
					getMp3TagValue(it.file, x -> StringUtils.isNotBlank(Util.toString(x)), mp3Tags)));
			//
		} else {
			//
			if (Objects.equals(Boolean.FALSE, voice != null ? voice.getTts() : null)) {
				//
				importVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction(objectMap, filePath,
						preferredPronunciationAudioFormat);
				//
			} // if
				//
			if ((it == null || it.file == null)
					&& SpeechApi.isInstalled(ObjectMap.getObject(objectMap, SpeechApi.class))) {
				//
				ObjectMap.setObject(objectMap, ImportTask.class, it);
				//
				importVoiceBySpeechApi(objectMap, filePath, voiceId, languageCodeToTextObjIntFunction);
				//
			} // if
				//
		} // if
			//
	}

	@Nullable
	private static String getMp3TagValue(final File file, final Predicate<Object> predicate, final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		return getMp3TagValue(getMp3TagParirs(file, attributes), predicate);
		//
	}

	@Nullable
	private static List<Pair<String, ?>> getMp3TagParirs(final File file, final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		if (Objects.equals("mp3", getFileExtension(Util.cast(ContentInfo.class,
				testAndApply(Util::isFile, file, new ContentInfoUtil()::findMatch, null))))) {
			//
			final Mp3File mp3File = new Mp3File(file);
			//
			return getMp3TagParirs(
					ObjectUtils.getIfNull(Mp3FileUtil.getId3v2Tag(mp3File), Mp3FileUtil.getId3v1Tag(mp3File)),
					attributes);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static List<Pair<String, ?>> getMp3TagParirs(final ID3v1 id3v1, @Nullable final String... attributes)
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
			if ((methods = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, ms = getIfNull(ms, () -> Util.getMethods(Util.getClass(id3v1))),
							Arrays::stream, null),
					a -> Util.matches(
							Util.matcher(Pattern.compile(String.format("get%1$s", StringUtils.capitalize(attribute))),
									Util.getName(a)))))) == null
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
					Util.add(pairs = getIfNull(pairs, ArrayList::new), Pair.of(attribute, invoke(m, id3v1)));
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

	@Nullable
	private static String getMp3TagValue(@Nullable final List<Pair<String, ?>> pairs,
			@Nullable final Predicate<Object> predicate) {
		//
		String string = null;
		//
		for (int i = 0; i < IterableUtils.size(pairs); i++) {
			//
			if (Util.test(predicate, string = Util.toString(Util.getValue(Util.cast(Pair.class, get(pairs, i)))))
					|| predicate == null) {
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

	private static void importVoiceBySpeechApi(final ObjectMap objectMap, @Nullable final String filePath,
			@Nullable final String voiceId,
			@Nullable final ObjIntFunction<String, String> languageCodeToTextObjIntFunction)
			throws IllegalAccessException, InvocationTargetException, IOException {
		//
		final ImportTask it = ObjectMap.getObject(objectMap, ImportTask.class);
		//
		if (it == null) {
			//
			return;
			//
		} // if
			//
		final Voice voice = it.voice;
		//
		if ((it.file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), filePath)) != null) {
			//
			ObjectMap.setObject(objectMap, File.class, it.file);
			//
			final VoiceManagerImportBatchPanel voiceManagerImportBatchPanel = ObjectMap.getObject(objectMap,
					VoiceManagerImportBatchPanel.class);
			//
			writeVoiceToFile(objectMap, getText(voice),
					//
					// voiceId
					//
					voiceId
					//
					// rate
					//
					, voiceManagerImportBatchPanel != null ? voiceManagerImportBatchPanel.getRate() : null,
					//
					// volume
					//
					Math.min(Math.max(Util.intValue(getValue(ObjectMap.getObject(objectMap, JSlider.class)), 100), 0),
							100)
			//
			);
			//
			final ByteConverter byteConverter = ObjectMap.getObject(objectMap, ByteConverter.class);
			//
			testAndAccept(Objects::nonNull,
					byteConverter != null ? byteConverter.convert(Files.readAllBytes(Path.of(Util.toURI(it.file))))
							: null,
					x -> FileUtils.writeByteArrayToFile(it.file, x));
			//
			Util.deleteOnExit(it.file);
			//
		} // if
			//
		if (voice != null) {
			//
			voice.setTts(Boolean.TRUE);
			//
		} // if
			//
		setSource(voice, StringUtils.defaultIfBlank(getSource(voice),
				Provider.getProviderName(ObjectMap.getObject(objectMap, Provider.class))));
		//
		try {
			//
			setLanguage(voice, StringUtils.defaultIfBlank(getLanguage(voice), ObjIntFunctionUtil.apply(
					languageCodeToTextObjIntFunction,
					SpeechApi.getVoiceAttribute(ObjectMap.getObject(objectMap, SpeechApi.class), voiceId, LANGUAGE),
					16)));
			//
		} catch (final Error e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	private static void writeVoiceToFile(final ObjectMap objectMap, @Nullable final String text, final String voiceId,
			@Nullable final Integer rate, final Integer volume) {
		//
		final SpeechApi speechApi = ObjectMap.getObject(objectMap, SpeechApi.class);
		//
		if (speechApi != null) {
			//
			speechApi.writeVoiceToFile(text, voiceId
			//
					, Util.intValue(rate, 0)// rate
					//
					, Math.min(Math.max(Util.intValue(volume, 100), 0), 100)// volume
					, null, ObjectMap.getObject(objectMap, File.class));
			//
		} // if
			//
	}

	private Integer getRate() {
		//
		final Integer speechRate = getValue(jsSpeechRate);
		//
		return speechRate != null ? speechRate : getRate(Util.getText(tfSpeechRate));
		//
	}

	private static Integer getRate(final String string) {
		//
		Integer rate = valueOf(string);
		//
		if (rate == null) {
			//
			rate = getIfNull(rate,
					() -> getRate(Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredFields(Integer.class), Arrays::stream, null),
							f -> f != null
									&& (Util.isAssignableFrom(Number.class, Util.getType(f))
											|| Objects.equals(Integer.TYPE, Util.getType(f)))
									&& Objects.equals(Util.getName(f), string)))));
			//
		} // if
			//
		return rate;
		//
	}

	@Nullable
	private static Integer getRate(@Nullable final List<Field> fs) {
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
			if (Util.isStatic(f)) {
				//
				try {
					//
					final Number number = Util.cast(Number.class, get(f, null));
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

	@Nullable
	private static Integer getValue(@Nullable final JSlider instance) {
		return instance != null ? Integer.valueOf(instance.getValue()) : null;
	}

	private static void setLanguage(@Nullable final Voice instance, final String language) {
		if (instance != null) {
			instance.setLanguage(language);
		}
	}

	@Nullable
	private static String getLanguage(@Nullable final Voice instance) {
		return instance != null ? instance.getLanguage() : null;
	}

	private static void setSource(@Nullable final Voice instance, final String source) {
		if (instance != null) {
			instance.setSource(source);
		}
	}

	@Nullable
	private static File createTempFile(final String prefix, @Nullable final String suffix)
			throws IllegalAccessException, InvocationTargetException {
		//
		final List<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(File.class), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), "createTempFile")
						&& Arrays.equals(new Class<?>[] { String.class, String.class }, Util.getParameterTypes(x))));
		//
		return Util.cast(File.class,
				invoke(testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null), null,
						prefix, suffix));
		//
	}

	private static void importVoiceByOnlineNHKJapanesePronunciationsAccentFailableFunction(final ObjectMap objectMap,
			final String filePath, @Nullable final String preferredPronunciationAudioFormat) throws Exception {
		//
		final ImportTask it = ObjectMap.getObject(objectMap, ImportTask.class);
		//
		final Pronunciation pronunciation = testAndApply(x -> IterableUtils.size(x) == 1,
				FailableFunctionUtil.apply(
						ObjectMap.getObject(objectMap, OnlineNHKJapanesePronunciationsAccentFailableFunction.class),
						getText(it != null ? it.voice : null)),
				x -> IterableUtils.get(x, 0), null);
		//
		if (pronunciation != null) {
			//
			Map<String, String> audioUrls = null;
			//
			String audioUrl = null;
			//
			if (StringUtils.isBlank(audioUrl = testAndApply(Util::containsKey, audioUrls = pronunciation.getAudioUrls(),
					preferredPronunciationAudioFormat, Util::get, null))) {
				//
				final Entry<String, String> entry = testAndApply(CollectionUtils::isNotEmpty, Util.entrySet(audioUrls),
						x -> IterableUtils.get(x, 0), null);
				//
				audioUrl = Util.getValue(entry);
				//
			} // if
				//
			try (final InputStream is = Util
					.openStream(testAndApply(StringUtils::isNotBlank, audioUrl, x -> Util.toURL(new URI(x)), null))) {
				//
				if (is != null && it != null
						&& (it.file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH),
								filePath)) != null) {
					//
					FileUtils.copyInputStreamToFile(is, it.file);
					//
					Util.deleteOnExit(it.file);
					//
				} // if
					//
			} // try
				//
		} // if
			//
	}

	@Nullable
	private static String getSource(@Nullable final Voice instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void setHiraganaOrKatakanaAndRomaji(final boolean hiraganaKatakanaConversion,
			final boolean hiraganaRomajiConversion, @Nullable final Voice voice, final Jakaroma jakaroma) {
		//
		if (voice != null) {
			//
			// org.springframework.context.support.VoiceManager.setHiraganaOrKatakana(domain.Voice)
			//
			testAndAccept(a -> hiraganaKatakanaConversion, voice, a -> setHiraganaOrKatakana(a));
			//
			// org.springframework.context.support.VoiceManager.setRomaji(domain.Voice,fr.free.nrw.jakaroma.Jakaroma)
			//
			testAndAccept((a, b) -> hiraganaRomajiConversion, voice, jakaroma, VoiceManagerImportBatchPanel::setRomaji);
			//
		} // if
			///
	}

	private static void setRomaji(final Voice voice, final Jakaroma jakaroma) {
		//
		final String romaji = getRomaji(voice);
		//
		final String hiragana = getHiragana(voice);
		//
		if (StringUtils.isBlank(romaji) && StringUtils.isNotBlank(hiragana)) {
			//
			voice.setRomaji(JakaromaUtil.convert(jakaroma, hiragana, false, false));
			//
		} // if
			//
	}

	private static void setHiraganaOrKatakana(@Nullable final Voice voice) {
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

	@Nullable
	private static String getHiragana(@Nullable final Voice instance) {
		return instance != null ? instance.getHiragana() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate,
			@Nullable final T value, @Nullable final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static Voice createVoice(final ObjectMap objectMap, final boolean first,
			final AtomicReference<IntMap<Field>> arintMap) throws IllegalAccessException {
		//
		Voice voice = null;
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if (Util.iterator(row) != null) {
			//
			IntMap<Field> intMap = get(arintMap);
			//
			int columnIndex = 0;
			//
			Field f = null;
			//
			IValue0<?> value = null;
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
					IntMap.setObject(
							intMap = ObjectUtils.getIfNull(intMap, () -> Reflection.newProxy(IntMap.class, new IH())),
							cell.getColumnIndex(),
							Util.orElse(findFirst(Util.filter(
									testAndApply(Objects::nonNull, FieldUtils.getAllFields(Voice.class), Arrays::stream,
											null),
									field -> Objects.equals(Util.getName(field), CellUtil.getStringCellValue(cell)))),
									null));
					//
					set(arintMap, intMap);
					//
				} else if (IntMap.containsKey(intMap, columnIndex = cell.getColumnIndex())
						&& (f = IntMap.getObject(intMap, columnIndex)) != null) {
					//
					ObjectMap.setObject(objectMap, Field.class, f);
					//
					ObjectMap.setObject(objectMap, Cell.class, cell);
					//
					ifElse((value = getValueFromCell(objectMap)) == null, () -> {
						throw new IllegalStateException();
					}, null);
					//
					FieldUtils.writeField(f, voice = getIfNull(voice, Voice::new), IValue0Util.getValue0(value), true);
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return voice;
		//
	}

	private static <E extends Throwable> void ifElse(final boolean condition, final FailableRunnable<E> runnableTrue,
			@Nullable final FailableRunnable<E> runnableFalse) throws E {
		//
		if (condition) {
			//
			FailableRunnableUtil.run(runnableTrue);
			//
		} else {
			//
			FailableRunnableUtil.run(runnableFalse);
			//
		} // if
			//
	}

	private static <V> void set(@Nullable final AtomicReference<V> instance, final V value) {
		if (instance != null) {
			instance.set(value);
		}
	}

	@SuppressWarnings("java:S1612")
	@Nullable
	private static IValue0<?> getValueFromCell(final ObjectMap objectMap) {
		//
		final Field f = ObjectMap.getObject(objectMap, Field.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final Class<?> type = Util.getType(f);
		//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		List<?> list = null;
		//
		IValue0<?> value = null;
		//
		if (Objects.equals(type, String.class)) {
			//
			if (Objects.equals(cellType, CellType.NUMERIC)) {
				//
				value = Unit.with(Util.toString(getNumericCellValue(cell)));
				//
			} else {
				//
				value = Unit.with(CellUtil.getStringCellValue(cell));
				//
			} // if
				//
		} else if (Util.isAssignableFrom(Enum.class, type) && (list = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, getEnumConstants(type), Arrays::stream, null), e -> {
					//
					final String name = Util.name(Util.cast(Enum.class, e));
					//
					final String stringCellValue = CellUtil.getStringCellValue(cell);
					//
					return Objects.equals(name, stringCellValue) || (StringUtils.isNotEmpty(stringCellValue)
							&& StringsUtil.startsWith(Strings.CI, name, stringCellValue));
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
			value = Unit.with(
					Util.toList(Util.map(Util.stream(getObjectList(ObjectMap.getObject(objectMap, ObjectMapper.class),
							CellUtil.getStringCellValue(cell))), x -> Util.toString(x))));
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

	@Nullable
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	@Nullable
	private static IValue0<Boolean> getBooleanValueFromCell(final ObjectMap objectMap) {
		//
		final FormulaEvaluator formulaEvaluator = ObjectMap.getObject(objectMap, FormulaEvaluator.class);
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		String string = null;
		//
		IValue0<Boolean> value = null;
		//
		if (Objects.equals(cellType, CellType.BOOLEAN)) {
			//
			value = Unit.with(cell != null ? Boolean.valueOf(cell.getBooleanCellValue()) : null);
			//
		} else if (Objects.equals(cellType, CellType.FORMULA) && formulaEvaluator != null) {
			//
			value = Unit.with(getBooleanValue(FormulaEvaluatorUtil.evaluate(formulaEvaluator, cell)));
			//
		} else if (StringUtils.isNotEmpty(string = CellUtil.getStringCellValue(cell))) {
			//
			value = Unit.with(Boolean.valueOf(string));
			//
		} // if
			//
		return value;
		//
	}

	@Nullable
	private static Boolean getBooleanValue(@Nullable final CellValue instance) {
		return instance != null ? Boolean.valueOf(instance.getBooleanValue()) : null;
	}

	private static IValue0<Integer> getIntegerValueFromCell(final ObjectMap objectMap) {
		//
		final Cell cell = ObjectMap.getObject(objectMap, Cell.class);
		//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		IValue0<Integer> value = null;
		//
		final Double D = Objects.equals(cellType, CellType.NUMERIC) ? getNumericCellValue(cell) : null;
		//
		if (D != null) {
			//
			value = Unit.with(Integer.valueOf(D.intValue()));
			//
		} else {
			//
			value = Unit.with(valueOf(CellUtil.getStringCellValue(cell)));
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
	private static Double getNumericCellValue(@Nullable final Cell instance) {
		return instance != null ? Double.valueOf(instance.getNumericCellValue()) : null;
	}

	@Nullable
	private static <T> Optional<T> findFirst(@Nullable final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	@Nullable
	private static <V> V get(@Nullable final AtomicReference<V> instance) {
		return instance != null ? instance.get() : null;
	}

	private static void shutdown(@Nullable final ExecutorService instance) {
		if (instance != null) {
			instance.shutdown();
		}
	}

	private static void submit(@Nullable final ExecutorService instance, final Runnable task) {
		if (instance != null) {
			instance.submit(task);
		}
	}

	@Nullable
	private static Integer getCurrentSheetIndex(@Nullable final Sheet sheet) {
		//
		Integer currentSheetIndex = null;
		//
		final Workbook workbook = sheet != null ? sheet.getWorkbook() : null;
		//
		if (workbook != null) {
			//
			final int numberOfSheets = WorkbookUtil.getNumberOfSheets(workbook);
			//
			for (int i = 0; i < numberOfSheets; i++) {
				//
				if (!Objects.equals(workbook.getSheetName(i), SheetUtil.getSheetName(sheet))) {
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

	@Nullable
	private static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	@Nullable
	private static ObjectMap copyObjectMap(@Nullable final ObjectMap instance) {
		//
		if (instance != null && Proxy.isProxyClass(Util.getClass(instance))) {
			//
			final IH ihOld = Util.cast(IH.class, Proxy.getInvocationHandler(instance));
			//
			final IH ihNew = new IH();
			//
			if (ihOld != null) {
				//
				ihNew.objects = ObjectUtils.getIfNull(
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

	@Nullable
	private static IValue0<Boolean> getBoolean(final CustomProperties instance, final String name) {
		//
		final CTProperty ctProperty = testAndApply(VoiceManagerImportBatchPanel::contains, instance, name,
				VoiceManagerImportBatchPanel::getProperty, null);
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

	@Nullable
	private static File getParentFile(@Nullable final File instance) {
		return instance != null ? instance.getParentFile() : null;
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

	private String getVoiceIdForExecute(final boolean nonTest) {
		//
		String voiceId = Util.toString(Util.getSelectedItem(cbmVoiceId));
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
				(jcbVoiceIdLocal = new JComboBox<>(cbmVoiceIdLocal)).addItemListener(this);
				//
				testAndAccept((a, b) -> b != null, jcbVoiceIdLocal,
						ConverterUtil.convert(voiceIdListCellRendererConverter,
								Util.getRenderer(Util.cast(JComboBox.class, jcbVoiceIdLocal))),
						(a, b) -> setRenderer(a, b));
				//
			} // if
				//
				// Show "Voice ID" option dialog if this method is not run under test case
				//
			testAndAccept((a, b) -> Objects.equals(a, Boolean.TRUE), nonTest, jcbVoiceIdLocal,
					(a, b) -> JOptionPane.showMessageDialog(null, b, "Voice ID", JOptionPane.PLAIN_MESSAGE));
			//
			voiceId = Util.toString(Util.getSelectedItem(cbmVoiceIdLocal));
			//
		} // if
			//
		return voiceId;
		//
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (Util.test(instance, t, u)) {
			FailableBiConsumerUtil.accept(consumer, t, u);
		} // if
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
			if (Narcissus.invokeMethod(instance, Util.getDeclaredMethod(Component.class, "getObjectLock")) == null) {
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

	private static void setMaximum(@Nullable final JProgressBar instance, final int n) {
		if (instance != null) {
			instance.setMaximum(n);
		}
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

	@Nullable
	private static String getLpwstr(@Nullable final CTProperty instance) {
		return instance != null ? instance.getLpwstr() : null;
	}

	private static boolean contains(@Nullable final CustomProperties instance, final String name) {
		return instance != null && instance.contains(name);
	}

	@Nullable
	private static CTProperty getProperty(@Nullable final CustomProperties instance, final String name) {
		return instance != null ? instance.getProperty(name) : null;
	}

	@Nullable
	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			@Nullable final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Nullable
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
				} else if (Util.contains(oleEntryNames, "Workbook")) {
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

	@Nullable
	private static String getPassword(@Nullable final Console console) {
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
				? Util.getText(jtc)
				: null;
		//
	}

	@Nullable
	private static List<String> getOleEntryNames(@Nullable final POIFSFileSystem poifs) {
		//
		List<String> list = null;
		//
		final DirectoryNode root = poifs != null ? poifs.getRoot() : null;
		//
		final Iterator<org.apache.poi.poifs.filesystem.Entry> entries = root != null ? root.getEntries() : null;
		//
		org.apache.poi.poifs.filesystem.Entry entry = null;
		//
		while (Util.hasNext(entries)) {
			//
			if ((entry = Util.next(entries)) == null) {
				//
				continue;
				//
			} // if
				//
			Util.add(list = getIfNull(list, ArrayList::new), entry.getName());
			//
		} // while
			//
		return list;
		//
	}

	@Nullable
	private static Workbook getWorkbookByZipFile(final File file)
			throws IOException, SAXException, ParserConfigurationException, InvalidFormatException {
		//
		final ContentInfo ci = testAndApply(Util::isFile, file, new ContentInfoUtil()::findMatch, null);
		//
		try (final ZipFile zf = testAndApply(x -> Objects.equals(ContentType.ZIP, getContentType(ci)), file,
				ZipFile::new, null);
				final InputStream is = testAndApply(Objects::nonNull,
						testAndApply(Objects::nonNull, "[Content_Types].xml", x -> getEntry(zf, x), null),
						x -> getInputStream(zf, x), null)) {
			//
			final NodeList childNodes = Util.getChildNodes(getDocumentElement(
					is != null ? parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), is) : null));
			//
			boolean isXlsx = false;
			//
			for (int i = 0; i < Util.getLength(childNodes); i++) {
				//
				if (Objects.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
						Util.getTextContent(
								Util.getNamedItem(Util.getAttributes(Util.item(childNodes, i)), "ContentType")))
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

	@Nullable
	private static Element getDocumentElement(@Nullable final Document instance) {
		return instance != null ? instance.getDocumentElement() : null;
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

	@Nullable
	private static InputStream getInputStream(@Nullable final ZipFile instance, final ZipEntry entry)
			throws IOException {
		return instance != null ? instance.getInputStream(entry) : null;
	}

	@Nullable
	private static ZipEntry getEntry(@Nullable final ZipFile instance, final String name) {
		return instance != null ? instance.getEntry(name) : null;
	}

	@Nullable
	private static ContentType getContentType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getContentType() : null;
	}

	private static void importVoice(@Nullable final ObjectMap objectMap,
			final BiConsumer<Voice, String> errorMessageConsumer,
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
		final HexFormat hexFormat = HexFormat.of();
		//
		try {
			//
			final String fileExtension = getFileExtension(new ContentInfoUtil().findMatch(selectedFile));
			//
			final String checkFileExtension = checkFileExtension(fileExtension);
			//
			if (checkFileExtension != null) {
				//
				Util.accept(errorMessageConsumer, voice, checkFileExtension);
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
			final Voice voiceOld = searchByTextAndRomaji(voiceMapper, text, romaji);
			//
			final MessageDigest md = ObjectMap.getObject(objectMap, MessageDigest.class);
			//
			final String messageDigestAlgorithm = getAlgorithm(md);
			//
			Long length = length(selectedFile);
			//
			String fileDigest = formatHex(hexFormat,
					Util.digest(md, Files.readAllBytes(Path.of(Util.toURI(selectedFile)))));
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
				File file = Util.toFile(Path.of(voiceFolder, filePath = Util.toString(fileName)));
				//
				if (Util.exists(file)) {
					//
					file = Util.toFile(Path.of(voiceFolder, filePath = Util.toString(
							fileName.insert(StringUtils.lastIndexOf(fileName, '.') + 1, randomAlphabetic(2) + "."))));
					//
				} // if
					//
				FileUtils.copyFile(selectedFile, file);
				//
				length = length(file);
				//
				fileDigest = formatHex(hexFormat, Util.digest(md, Files.readAllBytes(Path.of(Util.toURI(file)))));
				//
			} else {
				//
				final File file = Util.toFile(Path.of(voiceFolder, getFilePath(voiceOld)));
				//
				if (!Util.exists(file)) {
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
				try {
					//
					final JTextComponent tfFile = Util.cast(JTextComponent.class,
							FieldUtils.readDeclaredField(voiceManager, "tfFile", true));
					//
					Util.setText(tfFile, Objects.toString(filePath, Util.getText(tfFile)));
					//
					Util.setText(
							Util.cast(JTextComponent.class,
									FieldUtils.readDeclaredField(voiceManager, "tfFileLength", true)),
							Util.toString(length));
					//
					Util.setText(Util.cast(JTextComponent.class,
							FieldUtils.readDeclaredField(voiceManager, "tfFileDigest", true)), fileDigest);
					//
				} catch (final IllegalAccessException e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
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
			Util.accept(throwableConsumer, voice, e);
			//
		} // try
			//
	}

	private static void insertOrUpdate(@Nullable final VoiceMapper instance, final Voice voice) {
		//
		if (instance != null) {
			//
			instance.deleteUnusedVoiceList();
			//
			// voice
			//
			final Voice voiceOld = searchByTextAndRomaji(instance, getText(voice), getRomaji(voice));
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
			if (Util.iterator(listNames) != null) {
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
					instance.insertVoiceListId(getId(ObjectUtils.getIfNull(voiceListOld, voiceList)), voiceId);
					//
				} // for
					//
			} // if
				//
		} // if
			//
	}

	@Nullable
	private static Integer getId(@Nullable final VoiceList instance) {
		return instance != null ? instance.getId() : null;
	}

	@Nullable
	private static Iterable<String> getListNames(@Nullable final Voice instance) {
		return instance != null ? instance.getListNames() : null;
	}

	private static void setCreateTs(@Nullable final Voice instance, final Date createTs) {
		if (instance != null) {
			instance.setCreateTs(createTs);
		}
	}

	private static void setUpdateTs(@Nullable final Voice instance, final Date updateTs) {
		if (instance != null) {
			instance.setUpdateTs(updateTs);
		}
	}

	private static void setId(@Nullable final Voice instance, final Integer id) {
		if (instance != null) {
			instance.setId(id);
		}
	}

	@Nullable
	private static Integer getId(@Nullable final Voice instance) {
		return instance != null ? instance.getId() : null;
	}

	@Nullable
	private static String getFilePath(@Nullable final Voice instance) {
		return instance != null ? instance.getFilePath() : null;
	}

	@Nullable
	private static String formatHex(@Nullable final HexFormat instance, @Nullable final byte[] bytes) {
		return instance != null && bytes != null ? instance.formatHex(bytes) : null;
	}

	private static String randomAlphabetic(final int count) {
		//
		Method method = IValue0Util.getValue0(METHOD_RANDOM_ALPHABETIC);
		//
		try {
			//
			if (method == null) {
				//
				METHOD_RANDOM_ALPHABETIC = Unit.with(
						method = Util.getDeclaredMethod(RandomStringUtils.class, "randomAlphabetic", Integer.TYPE));
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
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
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
			if (Narcissus.getField(instance, Util.getDeclaredField(File.class, "path")) == null) {
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
	private static String getAlgorithm(@Nullable final MessageDigest instance) {
		return instance != null ? instance.getAlgorithm() : null;
	}

	@Nullable
	private static Voice searchByTextAndRomaji(@Nullable final VoiceMapper instance, final String text,
			final String romaji) {
		return instance != null ? instance.searchByTextAndRomaji(text, romaji) : null;
	}

	@Nullable
	private static String getRomaji(@Nullable final Voice instance) {
		return instance != null ? instance.getRomaji() : null;
	}

	@Nullable
	private static String getText(@Nullable final Voice instance) {
		return instance != null ? instance.getText() : null;
	}

	@Nullable
	private static String checkFileExtension(@Nullable final String fileExtension) {
		//
		if (fileExtension == null) {
			//
			return "File Extension is null";
			//
		} else if (StringUtils.isEmpty(fileExtension)) {
			//
			return "File Extension is Empty";
			//
		} else if (StringUtils.isBlank(fileExtension)) {
			//
			return "File Extension is Blank";
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static String getFileExtension(@Nullable final ContentInfo ci) {
		//
		final String message = getMessage(ci);
		//
		if (or(x -> Util.matches(Util.matcher(x, message)), PATTERN_CONTENT_INFO_MESSAGE_MP3_1,
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
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static boolean checkImportFile(@Nullable final File file, final Voice voice,
			final BiConsumer<Voice, String> errorMessageConsumer) {
		//
		if (file == null) {
			//
			Util.accept(errorMessageConsumer, voice, NO_FILE_SELECTED);
			//
			return false;
			//
		} else if (!Util.exists(file)) {
			//
			Util.accept(errorMessageConsumer, voice,
					String.format("File \"%1$s\" does not exist", Util.getAbsolutePath(file)));
			//
			return false;
			//
		} else if (!Util.isFile(file)) {
			//
			Util.accept(errorMessageConsumer, voice, "Not A Regular File Selected");
			//
			return false;
			//
		} else if (longValue(length(file), 0) == 0) {
			//
			Util.accept(errorMessageConsumer, voice, "Empty File Selected");
			//
			return false;
			//
		} // if
			//
		return true;
		//
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static int showOpenDialog(@Nullable final JFileChooser instance, @Nullable final Component parent)
			throws HeadlessException {
		//
		if (instance == null) {
			//
			return JFileChooser.ERROR_OPTION;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, Util.getDeclaredField(JComponent.class, "ui")) == null) {
				//
				return JFileChooser.ERROR_OPTION;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.showOpenDialog(parent);
		//
	}

	private void actionPerformedForImportFileTemplate(final boolean headless, final JFileChooser jfc) {
		//
		setFileSelectionMode(jfc, JFileChooser.FILES_ONLY);
		//
		if (showSaveDialog(jfc, null) != JFileChooser.APPROVE_OPTION) {
			//
			return;
			//
		} // if
			//
		try {
			//
			FileUtils.writeByteArrayToFile(getSelectedFile(jfc),
					createImportFileTemplateByteArray(Util.isSelected(cbImportFileTemplateGenerateBlankRow),
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

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	@Nullable
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
			final Class<?> importFieldClass = Util.forName("domain.Voice$ImportField");
			//
			final List<Field> fs = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, FieldUtils.getAllFields(Voice.class), Arrays::stream, null),
					f -> Util.anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
							a -> Objects.equals(Util.annotationType(a), importFieldClass))));
			//
			Field f = null;
			//
			Sheet sheet = null;
			//
			Row row = null;
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if ((f = IterableUtils.get(fs, i)) == null) {
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
				CellUtil.setCellValue(RowUtil.createCell(row, i), Util.getName(f));
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

	@Nullable
	private static byte[] toByteArray(@Nullable final ByteArrayOutputStream instance) {
		return instance != null ? instance.toByteArray() : null;
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

	private static class IH implements InvocationHandler {

		private Map<Object, Object> objects = null;

		private Runnable runnable = null;

		private Collection<Object> throwableStackTraceHexs = null;

		private Map<Object, Object> strings = null;

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

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ObjectMap) {
				//
				final Map<Object, Object> os = getObjects();
				//
				if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					Util.put(os, args[0], args[1]);
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!Util.containsKey(os, key)) {
						//
						throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
								testAndApply(IH::isArray, Util.cast(Class.class, key), Util::getSimpleName, x -> key)));
						//
					} // if
						//
					return Util.get(os, key);
					//
				}
				//
			} // if
				//
			final IValue0<?> value = handleStringMap(methodName, getStrings(), args);
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

	private static void addImportFileTemplateBlankRow(@Nullable final Sheet sheet, final List<Field> fs,
			final boolean headless, final Collection<String> jlptValues,
			final Collection<String> gaKuNenBeTsuKanJiValues) {
		//
		final Row row = SheetUtil.createRow(sheet, Util.intValue(getPhysicalNumberOfRows(sheet), 0));
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
		final Class<?> classJlpt = Util.forName("domain.Voice$JLPT");
		//
		final Class<?> classGaKuNenBeTsuKanJi = Util.forName("domain.Voice$GaKuNenBeTsuKanJi");
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
			if (Objects.equals(Boolean.class, type = Util.getType(f = IterableUtils.get(fs, i)))) {// java.lang.Boolean
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
			} else if (Util.isAssignableFrom(Enum.class, type)) {// java.lang.Enum
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				addValidationDataForEnum(objectMap, type, i);
				//
			} else if (Util.anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
					a -> Objects.equals(Util.annotationType(a), classJlpt))) {// domain.Voice.JLPT
				//
				ObjectMap.setObject(objectMap, DataValidationHelper.class,
						dvh = getIfNull(dvh, () -> getDataValidationHelper(sheet)));
				//
				addValidationDataForValues(objectMap, jlptValues, i);
				//
			} else if (Util.anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
					a -> Objects.equals(Util.annotationType(a), classGaKuNenBeTsuKanJi))) {// domain.Voice.GaKuNenBeTsuKanJi
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
					createValidation(dvh, createExplicitListConstraint(dvh, Util.toArray(values, new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	private static void addValidationDataForEnum(final ObjectMap objectMap, final Class<?> type, final int index) {
		//
		final Stream<?> stream = testAndApply(Objects::nonNull, getEnumConstants(type), Arrays::stream, null);
		//
		final List<String> strings = Util
				.toList(Util.map(stream, x -> x instanceof Enum ? Util.name((Enum<?>) x) : Util.toString(x)));
		//
		final DataValidationHelper dvh = ObjectMap.getObject(objectMap, DataValidationHelper.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		if ((!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(strings)) && row != null) {
			//
			addValidationData(ObjectMap.getObject(objectMap, Sheet.class),
					createValidation(dvh, createExplicitListConstraint(dvh, Util.toArray(strings, new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	@Nullable
	private static <T> T[] getEnumConstants(@Nullable final Class<T> instance) {
		return instance != null ? instance.getEnumConstants() : null;
	}

	@SuppressWarnings("java:S1612")
	private static void addValidationDataForBoolean(final ObjectMap objectMap,
			@Nullable final IValue0<List<Boolean>> booleans, final int index) {
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
									Util.toArray(Util.toList(Util.map(Util.stream(bs), x -> Util.toString(x))),
											new String[] {})),
							new CellRangeAddressList(row.getRowNum(), row.getRowNum(), index, index)));
			//
		} // if
			//
	}

	private static void addValidationData(@Nullable final Sheet instance,
			@Nullable final DataValidation dataValidation) {
		if (instance != null) {
			instance.addValidationData(dataValidation);
		}
	}

	@Nullable
	private static DataValidation createValidation(@Nullable final DataValidationHelper instance,
			@Nullable final DataValidationConstraint constraint, final CellRangeAddressList cellRangeAddressList) {
		return instance != null ? instance.createValidation(constraint, cellRangeAddressList) : null;
	}

	@Nullable
	private static DataValidationConstraint createExplicitListConstraint(@Nullable final DataValidationHelper instance,
			@Nullable final String[] listOfValues) {
		return instance != null ? instance.createExplicitListConstraint(listOfValues) : null;
	}

	@Nullable
	private static List<Boolean> getBooleanValues() throws IllegalAccessException {
		//
		List<Boolean> list = null;
		//
		final List<Field> fs = Util.toList(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(Boolean.class), Arrays::stream, null),
						f -> Objects.equals(Util.getType(f), Boolean.class)));
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			if (!Objects.equals(Boolean.class, Util.getType(f = IterableUtils.get(fs, i)))) {
				//
				continue;
				//
			} // if
				//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.cast(Boolean.class, get(f, null)));
			//
		} // for
			//
		return list;
		//
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Nullable
	private static DataValidationHelper getDataValidationHelper(@Nullable final Sheet instance) {
		return instance != null ? instance.getDataValidationHelper() : null;
	}

	@Nullable
	private static Integer getPhysicalNumberOfRows(@Nullable final Sheet instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfRows()) : null;
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

	@Nullable
	private static Annotation[] getDeclaredAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	@Nullable
	private static File getSelectedFile(@Nullable final JFileChooser instance) {
		return instance != null ? instance.getSelectedFile() : null;
	}

	private static int showSaveDialog(@Nullable final JFileChooser instance, @Nullable final Component parent)
			throws HeadlessException {
		//
		if (instance == null) {
			//
			return JFileChooser.ERROR_OPTION;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, Util.getDeclaredField(JComponent.class, "ui")) == null) {
				//
				return JFileChooser.ERROR_OPTION;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.showSaveDialog(parent);
		//
	}

	private static void setFileSelectionMode(@Nullable final JFileChooser instance, final int mode) {
		if (instance != null) {
			instance.setFileSelectionMode(mode);
		}
	}

	private static <E extends Throwable> void testAndRun(final boolean b, @Nullable final FailableRunnable<E> runnable)
			throws E {
		//
		if (b) {
			//
			FailableRunnableUtil.run(runnable);
			//
		} // if
			//
	}

	private static List<?> getObjectsByGroupAnnotation(final Object instance, final String group) {
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(testAndApply(Objects::nonNull,
				Util.getDeclaredFields(VoiceManagerImportBatchPanel.class), Arrays::stream, null), f -> {
					final Group g = Util.isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringsUtil.equals(Strings.CS, g != null ? g.value() : null, group);
				}));
		//
		return Util.toList(FailableStreamUtil.stream(FailableStreamUtil.map(fs,
				f -> f != null && instance != null ? FieldUtils.readField(f, instance, true) : null)));
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void clear(@Nullable final DefaultTableModel instance) {
		//
		Util.clear(instance != null ? instance.getDataVector() : null);
		//
	}

	private static <T> void accept(final Consumer<? super T> action, final T a, final T b,
			@Nullable final T... values) {
		//
		Util.accept(action, a);
		//
		Util.accept(action, b);
		//
		for (int i = 0; values != null && i < values.length; i++) {
			//
			Util.accept(action, values[i]);
			//
		} // for
			//
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, jcbVoiceId)) {
			//
			try {
				//
				final String language = SpeechApi.getVoiceAttribute(speechApi,
						Util.toString(Util.getSelectedItem(cbmVoiceId)), LANGUAGE);
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
	private static String getLevel(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getLevel() : null;
	}

}