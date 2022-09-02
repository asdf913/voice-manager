package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.CodeUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.ObjectType;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.github.curiousoddman.rgxgen.RgxGen;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.mariten.kanatools.KanaConverter;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;

import de.sciss.jump3r.lowlevel.LameEncoder;
import de.sciss.jump3r.mp3.Lame;
import domain.Voice;
import domain.Voice.Yomi;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import io.github.toolfactory.narcissus.Narcissus;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.javaflacencoder.AudioStreamEncoder;
import net.sourceforge.javaflacencoder.FLACEncoder;
import net.sourceforge.javaflacencoder.FLACStreamOutputStream;
import net.sourceforge.javaflacencoder.StreamConfiguration;

public class VoiceManager extends JFrame implements ActionListener, ItemListener, ChangeListener, KeyListener,
		EnvironmentAware, BeanFactoryPostProcessor, InitializingBean {

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

	private PropertyResolver propertyResolver = null;

	private JTextComponent tfFolder, tfFile, tfFileLength, tfFileDigest, tfTextTts, tfTextImport, tfHiragana,
			tfKatakana, tfRomaji, tfSpeechRate, tfSource, tfProviderName, tfProviderVersion, tfProviderPlatform,
			tfSpeechLanguage, tfLanguage, tfSpeechVolume, tfCurrentProcessingFile, tfCurrentProcessingSheetName,
			tfCurrentProcessingVoice, tfListNames, tfPhraseCounter, tfPhraseTotal, tfJlptFolderNamePrefix,
			tfOrdinalPositionFileNamePrefix, tfIpaSymbol = null;

	private ComboBoxModel<Yomi> cbmYomi = null;

	private ComboBoxModel<String> cbmVoiceId, cbmJlptLevel, cbmGaKuNenBeTsuKanJi = null;

	private ComboBoxModel<?> cbmAudioFormatWrite, cbmAudioFormatExecute = null;

	private ComboBoxModel<Boolean> cbmIsKanji = null;

	private AbstractButton btnSpeak, btnWriteVoice, btnConvertToRomaji, btnConvertToKatakana, btnCopyRomaji,
			btnCopyHiragana, btnCopyKatakana, cbUseTtsVoice, btnExecute, btnImportFileTemplate, btnImport,
			btnImportWithinFolder, cbOverMp3Title, cbOrdinalPositionAsFileNamePrefix, btnExport,
			cbImportFileTemplateGenerateBlankRow, cbJlptAsFolder, btnExportGaKuNenBeTsuKanJi = null;

	private JProgressBar progressBarImport, progressBarExport = null;

	private JSlider jsSpeechVolume = null;

	private JComboBox<Object> jcbVoiceId = null;

	private JLabel jlListNames, jlListNameCount = null;

	private DefaultTableModel tmImportException, tmImportResult = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private String voiceFolder = null;

	private String outputFolder = null;

	private Map<String, String> outputFolderFileNameExpressions = null;

	private SpeechApi speechApi = null;

	private String[] mp3Tags = null;

	private ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private Jakaroma jakaroma = null;

	private Toolkit toolkit = null;

	private ObjectMapper objectMapper = null;

	private String jlptLevelPageUrl, gaKuNenBeTsuKanJiListPageUrl = null;

	private Unit<List<String>> jlptLevels = null;

	private LayoutManager layoutManager = null;

	private JPanel jPanelImportResult = null;

	private Unit<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

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
		// cbmAudioFormatWrite
		//
		MutableComboBoxModel<Object> mcbm = cast(MutableComboBoxModel.class, cbmAudioFormatWrite);
		//
		final Collection<?> formats = getByteConverterAttributeValues(configurableListableBeanFactory, FORMAT);
		//
		if (mcbm != null) {
			//
			mcbm.addElement(null);
			//
			forEach(formats, mcbm::addElement);
			//
		} // if
			//
			// cbmAudioFormatExecute
			//
		if ((mcbm = cast(MutableComboBoxModel.class, cbmAudioFormatExecute)) != null) {
			//
			mcbm.addElement(null);
			//
			forEach(formats, mcbm::addElement);
			//
		} // if
			//
		final String audioFormat = getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.audioFormat");
		//
		setSelectedItem(cbmAudioFormatWrite, audioFormat);
		//
		setSelectedItem(cbmAudioFormatExecute, audioFormat);
		//
	}

	@Override
	public void afterPropertiesSet() {
		//
		init();
		//
	}

	@Override
	public void setLayout(final LayoutManager layoutManager) {
		///
		super.setLayout(this.layoutManager = layoutManager);
		//
	}

	private static Integer getTempFileMinimumPrefixLength() {
		//
		Integer result = null;
		//
		final Class<?> clz = File.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
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
			org.apache.bcel.classfile.Method method = null;
			//
			if (ms != null && !ms.isEmpty()) {
				//
				if (ms.size() > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				method = ms.get(0);
				//
			} // if
				//
			if (method != null) {
				//
				final byte[] bs = CodeUtil.getCode(method.getCode());
				// )
				final String[] lines = StringUtils.split(
						StringUtils.trim(
								Utility.codeToString(bs, method.getConstantPool(), 0, bs != null ? bs.length : 0)),
						'\n');
				//
				String line = null;
				//
				Pattern pattern = null;
				//
				Matcher matcher = null;
				//
				for (int i = 0; lines != null && i < lines.length; i++) {
					//
					if ((line = lines[i]) == null) {
						continue;
					} // if
						//
					if (line.matches("^\\d+:\\s+if_icmpge\\s+#\\d+$")
							&& matches((matcher = matcher(pattern = ObjectUtils.getIfNull(pattern,
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
		} catch (final IOException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		return result;
		//
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
		if (map != null && map.entrySet() != null) {
			//
			for (final Entry<?, ?> entry : map.entrySet()) {
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
				x -> (objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new)) != null
						? objectMapper.readValue(x, Object.class)
						: null,
				null);
		//
		if (object instanceof Map || object == null) {
			setOutputFolderFileNameExpressions(object);
		} else {
			throw new IllegalArgumentException(toString(getClass(object)));
		} // if
			//
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
		mp3Tags = toArray(toList(
				map(stream(getObjectList(objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new), value)),
						VoiceManager::toString)),
				new String[] {});
		//
	}

	public void setJlptLevelPageUrl(final String jlptLevelPageUrl) {
		this.jlptLevelPageUrl = jlptLevelPageUrl;
	}

	public void setGaKuNenBeTsuKanJiListPageUrl(final String gaKuNenBeTsuKanJiListPageUrl) {
		this.gaKuNenBeTsuKanJiListPageUrl = gaKuNenBeTsuKanJiListPageUrl;
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
			if (iterable.iterator() == null) {
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
			final Object object = objectMapper != null ? objectMapper.readValue(toString(value), Object.class) : null;
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

	private void init() {
		//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		final String TAB_TITLE_IMPORT_SINGLE = "Import(Single)";
		//
		final String TAB_TITLE_IMPORT_BATCH = "Import(Batch)";
		//
		final String[] TAB_TITLE_IMPORTS = new String[] { TAB_TITLE_IMPORT_SINGLE, TAB_TITLE_IMPORT_BATCH };
		//
		jTabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent evt) {
				//
				final JTabbedPane jtp = cast(JTabbedPane.class, getSource(evt));
				//
				if (jtp != null && jPanelImportResult != null) {
					//
					jPanelImportResult
							.setVisible(ArrayUtils.contains(TAB_TITLE_IMPORTS, jtp.getTitleAt(jtp.getSelectedIndex())));
					//
				} // if
					//
			}
		});
		//
		jTabbedPane.addTab("TTS", createTtsPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab(TAB_TITLE_IMPORT_SINGLE, createSingleImportPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab(TAB_TITLE_IMPORT_BATCH, createBatchImportPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab("Export", createExportPanel(cloneLayoutManager()));
		//
		jTabbedPane.addTab("Misc", createMiscellaneousPanel(cloneLayoutManager()));
		//
		try {
			//
			final Integer tabIndex = getTabIndexByTitle(jTabbedPane,
					getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.tabTitle"));
			//
			if (tabIndex != null) {
				//
				jTabbedPane.setSelectedIndex(tabIndex.intValue());
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		if (layoutManager instanceof MigLayout) {
			//
			add(jTabbedPane, WRAP);
			//
			add(jPanelImportResult = createImportResultPanel(cloneLayoutManager()), GROWX);
			//
		} // if
			//
	}

	private static Integer getTabIndexByTitle(final Object jTabbedPane, final Object title)
			throws NoSuchFieldException {
		//
		Integer tabIndex = null;
		//
		final List<?> pages = cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getField(x, getDeclaredField(getClass(x), "pages")), null));
		//
		Object page = null;
		//
		Field fieldTitle = null;
		//
		for (int i = 0; pages != null && i < pages.size(); i++) {
			//
			if ((page = pages.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (fieldTitle == null) {
				//
				final List<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(getClass(page))),
						f -> Objects.equals(getName(f), "title")));
				//
				if (fs != null && !fs.isEmpty()) {
					//
					if (fs.size() == 1) {
						//
						fieldTitle = fs.get(0);
						//
					} else {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
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

	private static Field getDeclaredField(final Class<?> instance, final String name) throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private LayoutManager cloneLayoutManager() {
		//
		final LayoutManager layoutManagerDefault = null;
		//
		LayoutManager layoutManager = ObjectUtils.defaultIfNull(this.layoutManager, layoutManagerDefault);
		//
		if (layoutManager instanceof Serializable) {
			//
			layoutManager = cast(LayoutManager.class, SerializationUtils.clone((Serializable) layoutManager));
			//
		} // if
			//
		return ObjectUtils.defaultIfNull(layoutManager, layoutManagerDefault);
		//
	}

	private JPanel createTtsPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.add(new JLabel("Text"));
		//
		panel.add(
				tfTextTts = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.text")),
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
		panel.add(tfProviderVersion = new JTextField(getProviderVersion(provider)), String.format("span %1$s", 2));
		//
		try {
			//
			panel.add(tfProviderPlatform = new JTextField(provider != null ? provider.getProviderPlatform() : null),
					WRAP);
			//
		} catch (final Error e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
			// Voice Id
			//
		panel.add(new JLabel("Voice Id"));
		//
		final String[] voiceIds = speechApi != null ? speechApi.getVoiceIds() : null;
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
			panel.add(jcbVoiceId, String.format("span %1$s", 3));
			//
			panel.add(tfSpeechLanguage = new JTextField(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
			//
		} // if
			//
		if (voiceIds != null) {
			//
			final List<?> temp = toList(filter(Arrays.stream(voiceIds), x -> Objects.equals(x,
					getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.voiceId"))));
			//
			if (temp != null && !temp.isEmpty()) {
				//
				if (temp.size() == 1) {
					//
					setSelectedItem(cbmVoiceId, temp.get(0));
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // if
			//
			// Speech Rate
			//
		panel.add(new JLabel("Speech Rate"));
		//
		panel.add(
				tfSpeechRate = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.speechRate")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		// Speech Volume
		//
		panel.add(new JLabel("Speech Volume"), "aligny top");
		//
		final Range<Integer> speechVolumeRange = createVolumnRange(getClass(speechApi));
		//
		final Integer upperEnpoint = speechVolumeRange != null && speechVolumeRange.hasUpperBound()
				? speechVolumeRange.upperEndpoint()
				: null;
		//
		panel.add(jsSpeechVolume = new JSlider(intValue(
				speechVolumeRange != null && speechVolumeRange.hasLowerBound() ? speechVolumeRange.lowerEndpoint()
						: null,
				0), intValue(upperEnpoint, 100)), String.format("%1$s,span %2$s", GROWX, 3));
		//
		jsSpeechVolume.addChangeListener(this);
		//
		final Integer speechVolume = valueOf(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.speechVolume"));
		//
		if (speechVolume != null) {
			//
			jsSpeechVolume.setValue(Integer.valueOf(Math.min(speechVolume.intValue(), intValue(upperEnpoint, 100))));
			//
		} else if (upperEnpoint != null) {
			//
			jsSpeechVolume.setValue(upperEnpoint.intValue());
			//
		} // if
			//
		jsSpeechVolume.setMajorTickSpacing(10);
		//
		jsSpeechVolume.setPaintTicks(true);
		//
		jsSpeechVolume.setPaintLabels(true);
		//
		panel.add(tfSpeechVolume = new JTextField(), String.format("%1$s,width %2$s", WRAP, 25));
		//
		// Button(s)
		//
		panel.add(new JLabel());
		//
		panel.add(btnSpeak = new JButton("Speak"));
		//
		// Audio Format
		//
		panel.add(new JComboBox(cbmAudioFormatWrite = new DefaultComboBoxModel<Object>()));
		//
		panel.add(btnWriteVoice = new JButton("Write"));
		//
		setEditable(false, tfSpeechLanguage, tfProviderName, tfProviderVersion, tfProviderPlatform, tfSpeechVolume);
		//
		addActionListener(this, btnSpeak, btnWriteVoice);
		//
		return panel;
		//
	}

	private JPanel createSingleImportPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		// Language
		//
		panel.add(new JLabel("Language"));
		//
		panel.add(
				tfLanguage = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.language")),
				String.format("%1$s,span %2$s", GROWX, 11));
		//
		// Source
		//
		panel.add(new JLabel("Source"));
		//
		panel.add(
				tfSource = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.source")),
				String.format("%1$s,span %2$s,wmin %3$s", GROWX, 2, 50));
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
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		if (booleans != null) {
			//
			booleans.add(0, null);
			//
		} // if
			//
		panel.add(new JComboBox<>(
				cbmIsKanji = booleans != null ? new DefaultComboBoxModel<>(toArray(booleans, new Boolean[] {}))
						: new DefaultComboBoxModel<>()));
		//
		if (cbmIsKanji != null) {
			//
			cbmIsKanji.setSelectedItem(testAndApply(StringUtils::isNotEmpty,
					getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.isKanji"),
					Boolean::valueOf, null));
			//
		} // if
			//
			// 学年別漢字 GaKuNenBeTsuKanJi
			//
		panel.add(new JLabel("学年別漢字"));
		//
		Set<String> gaKuNenBeTsuKanJiList = null;
		//
		try {
			//
			gaKuNenBeTsuKanJiList = keySet(getGaKuNenBeTsuKanJiMultimap());
			//
		} catch (IOException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		panel.add(
				new JComboBox<>(cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
						ArrayUtils.insert(0, toArray(gaKuNenBeTsuKanJiList, new String[] {}), (String) null),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>())),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		if (cbmGaKuNenBeTsuKanJi != null) {
			//
			final String string = getProperty(propertyResolver,
					"org.springframework.context.support.VoiceManager.gaKuNenBeTsuKanJi");
			//
			if (StringUtils.isNotEmpty(string)) {
				//
				cbmGaKuNenBeTsuKanJi.setSelectedItem(string);
				//
			} // if
				//
		} // if
			//
			// Text
			//
		panel.add(new JLabel("Text"));
		//
		panel.add(
				tfTextImport = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.text")),
				String.format("%1$s,span %2$s", GROWX, 19));
		//
		tfTextImport.addKeyListener(this);
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
		final Map<String, String> yomiNameMap = createYomiNameMap();
		//
		jcbYomi.setRenderer(new ListCellRenderer<Object>() {

			@Override
			public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				//
				final String name = name(cast(Enum.class, value));
				//
				if (containsKey(yomiNameMap, name)) {
					//
					return VoiceManager.getListCellRendererComponent(listCellRenderer, list,
							MapUtils.getObject(yomiNameMap, name), index, isSelected, cellHasFocus);
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
				tfIpaSymbol = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.ipaSymbol")),
				GROWX);
		//
		final List<Yomi> yomiList = toList(
				filter(testAndApply(Objects::nonNull, yomis, Arrays::stream, null), y -> Objects.equals(name(y),
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.yomi"))));
		//
		final int size = yomiList != null ? yomiList.size() : 0;
		//
		if (size == 1) {
			//
			setSelectedItem(cbmYomi, yomiList.get(0));
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		panel.add(new JLabel("List(s)"));
		//
		final String tags = getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.listNames");
		//
		panel.add(tfListNames = new JTextField(tags), String.format("%1$s,span %2$s", GROWX, 5));
		//
		tfListNames.addKeyListener(this);
		//
		panel.add(jlListNames = new JLabel(), String.format("span %1$s", 6));
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
		panel.add(new JLabel("JLPT Level"), String.format("span %1$s", 2));
		//
		final List<String> jlptLevels = testAndApply(Objects::nonNull, getJlptLevels(), ArrayList::new, null);
		//
		if (jlptLevels != null && !jlptLevels.isEmpty()) {
			//
			jlptLevels.add(0, null);
			//
		} // if
			//
		panel.add(new JComboBox<String>(
				cbmJlptLevel = testAndApply(Objects::nonNull, toArray(jlptLevels, new String[] {}),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<String>())),
				WRAP);
		//
		setSelectedItem(cbmJlptLevel,
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.jlptLevel"));
		//
		// Romaji
		//
		panel.add(new JLabel("Romaji"));
		//
		panel.add(
				tfRomaji = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.romaji")),
				String.format("%1$s,span %2$s", GROWX, 19));
		//
		panel.add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		// Hiragana
		//
		panel.add(new JLabel("Hiragana"));
		//
		panel.add(
				tfHiragana = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.hiragana")),
				String.format("%1$s,span %2$s", GROWX, 7));
		//
		panel.add(btnCopyHiragana = new JButton("Copy"), String.format("span %1$s", 2));
		//
		panel.add(btnConvertToKatakana = new JButton("Convert"));
		//
		// Katakana
		//
		panel.add(new JLabel("Katakana"), String.format("span %1$s", 2));
		//
		panel.add(
				tfKatakana = new JTextField(
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.katakana")),
				String.format("%1$s,span %2$s", GROWX, 7));
		//
		panel.add(btnCopyKatakana = new JButton("Copy"), WRAP);
		//
		panel.add(new JLabel());
		//
		panel.add(cbUseTtsVoice = new JCheckBox("TTS Voice"), String.format("span %1$s", 2));
		//
		cbUseTtsVoice.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.useTtsVoice")));
		//
		// Audio Format
		//
		panel.add(new JComboBox(cbmAudioFormatExecute = new DefaultComboBoxModel<Object>()));
		//
		panel.add(btnExecute = new JButton("Execute"), String.format("span %1$s", 2));
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
				btnCopyKatakana);
		//
		return panel;
		//
	}

	private Multimap<String, String> getGaKuNenBeTsuKanJiMultimap() throws IOException {
		//
		if (gaKuNenBeTsuKanJiMultimap == null) {
			//
			gaKuNenBeTsuKanJiMultimap = Unit.with(getGaKuNenBeTsuKanJiMultimap(gaKuNenBeTsuKanJiListPageUrl));
			//
		} // if
			//
		return getValue0(gaKuNenBeTsuKanJiMultimap);
		//
	}

	private static Multimap<String, String> getGaKuNenBeTsuKanJiMultimap(final String url) throws IOException {
		//
		Multimap<String, String> multimap = null;
		//
		try (final WebClient webClient = new WebClient()) {
			//
			final DomNodeList<DomElement> domElements = getElementsByTagName(
					cast(SgmlPage.class, testAndApply(StringUtils::isNotBlank, url, webClient::getPage, null)), "h2");
			//
			DomElement domElement = null;
			//
			Pattern pattern = null;
			//
			Matcher matcher = null;
			//
			for (int i = 0; domElements != null && i < domElements.getLength(); i++) {
				//
				if ((domElement = domElements.get(i)) == null || !matches(matcher = matcher(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("(第(\\d+)学年)（\\d+字）\\[編集]")),
						domElement.getTextContent())) || matcher == null || matcher.groupCount() <= 0) {
					//
					continue;
					//
				} // if
					//
				putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), matcher.group(1),
						toList(map(stream(getElementsByTagName(domElement.getNextElementSibling(), "a")),
								a -> a != null ? a.getTextContent() : null)));
				//
			} // for
				//
		} // try
			//
		return multimap;
		//
	}

	private static List<Boolean> getBooleanValues() throws IllegalAccessException {
		//
		List<Boolean> list = null;
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, Boolean.class.getDeclaredFields(), Arrays::stream, null),
						f -> Objects.equals(getType(f), Boolean.class)));
		//
		Field f = null;
		//
		for (int i = 0; fs != null && i < fs.size(); i++) {
			//
			if (!Objects.equals(Boolean.class, getType(f = fs.get(i)))) {
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

	private static <K> Set<K> keySet(final Multimap<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	private static <K, V> void putAll(final Multimap<K, V> instance, final K key, final Iterable<? extends V> values) {
		if (instance != null) {
			instance.putAll(key, values);
		}
	}

	private static DomNodeList<DomElement> getElementsByTagName(final SgmlPage instance, final String tagName) {
		return instance != null ? instance.getElementsByTagName(tagName) : null;
	}

	private static DomNodeList<HtmlElement> getElementsByTagName(final DomElement instance, final String tagName) {
		return instance != null ? instance.getElementsByTagName(tagName) : null;
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
		cbImportFileTemplateGenerateBlankRow.setSelected(Boolean.parseBoolean(getProperty(propertyResolver,
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
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		panel.add(new JLabel("Import Result"));
		//
		JScrollPane scp = new JScrollPane(new JTable(tmImportResult = new DefaultTableModel(
				new Object[] { "Number Of Sheet Processed", "Number of Voice Processed" }, 0)));
		//
		Dimension d = scp.getPreferredSize();
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
		if ((d = scp.getPreferredSize()) != null) {
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
		setEnabled(btnExecute, folder != null && folder.exists() && folder.isDirectory());
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

	private JPanel createExportPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.add(new JLabel("Option(s)"), String.format("span %1$s", 4));
		//
		panel.add(cbOverMp3Title = new JCheckBox("Over Mp3 Title"), String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbOverMp3Title.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.overMp3Title")));
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbOrdinalPositionAsFileNamePrefix = new JCheckBox("Ordinal Position As File Name Prefix"),
				String.format("span %1$s", 4));
		//
		cbOrdinalPositionAsFileNamePrefix.setSelected(Boolean.parseBoolean(getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.ordinalPositionAsFileNamePrefix")));
		//
		panel.add(new JLabel("Prefix"));
		//
		panel.add(
				tfOrdinalPositionFileNamePrefix = new JTextField(getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ordinalPositionFileNamePrefix")),
				String.format("%1$s,%2$s", GROWX, WRAP));
		//
		panel.add(new JLabel(), String.format("span %1$s", 4));
		//
		panel.add(cbJlptAsFolder = new JCheckBox("JLPT As Folder"), String.format("span %1$s", 3));
		//
		cbJlptAsFolder.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.jlptAsFolder")));
		//
		panel.add(new JLabel("Folder Name Prefix"));
		//
		panel.add(
				tfJlptFolderNamePrefix = new JTextField(getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.jlptFolderNamePrefix")),
				String.format("%1$s,wmin %2$s,span %3$s", WRAP, 100, 2));
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

	private JPanel createMiscellaneousPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.add(btnExportGaKuNenBeTsuKanJi = new JButton("Export 学年別漢字"));
		//
		addActionListener(this, btnExportGaKuNenBeTsuKanJi);
		//
		return panel;
		//
	}

	private List<String> getJlptLevels() {
		//
		if (jlptLevels == null) {
			//
			jlptLevels = Unit.with(getJlptLevels(jlptLevelPageUrl));
			//
		} // if
			//
		return getValue0(jlptLevels);
		//
	}

	private static List<String> getJlptLevels(final String urlString) {
		//
		HttpURLConnection httpURLConnection = null;
		//
		try {
			//
			final URL url = testAndApply(StringUtils::isNotBlank, urlString, URL::new, null);
			//
			httpURLConnection = cast(HttpURLConnection.class, url != null ? url.openConnection() : null);
			//
		} catch (final IOException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		String html = null;
		//
		try (final InputStream is = httpURLConnection != null ? httpURLConnection.getInputStream() : null) {
			//
			html = testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, "utf-8"), null);
			//
		} catch (final IOException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} // try
			//
		return parseJlptPageHtml(html);
		//
	}

	private static List<String> parseJlptPageHtml(final String html) {
		//
		List<String> result = null;
		//
		final Matcher matcher = matcher(Pattern.compile("<th scope=\"col\" class=\"thLeft\">(\\w+)</th>"), html);
		//
		while (matcher != null && matcher.find() && matcher.groupCount() > 0) {
			//
			add(result = ObjectUtils.getIfNull(result, ArrayList::new), matcher.group(1));
			//
		} // while
			//
		return result;
		//
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
				filter(map(testAndApply(Objects::nonNull, Yomi.class.getDeclaredFields(), Arrays::stream, null), f -> {
					//
					final List<Object> objects = toList(
							map(filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
									a -> Objects.equals(annotationType(a), nameClass)), a -> {
										//
										final List<Method> ms = toList(filter(
												testAndApply(Objects::nonNull, getDeclaredMethods(annotationType(a)),
														Arrays::stream, null),
												ma -> Objects.equals(getName(ma), "value")));
										//
										if (ms == null || ms.isEmpty()) {
											//
											return false;
											//
										} // if
											//
										Method m = ms.get(0);
										//
										if (ms.size() == 1 && m != null) {
											//
											m.setAccessible(true);
											//
											try {
												//
												return m.invoke(a);
												//
											} catch (final IllegalAccessException e) {
												//
												if (GraphicsEnvironment.isHeadless()) {
													//
													if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
														LOG.error(getMessage(e), e);
													} else if (e != null) {
														e.printStackTrace();
													} // if
														//
												} else {
													//
													JOptionPane.showMessageDialog(null, getMessage(e));
													//
												} // if
													//
											} catch (final InvocationTargetException e) {
												//
												final Throwable targetException = e.getTargetException();
												//
												final Throwable rootCause = ObjectUtils.firstNonNull(
														ExceptionUtils.getRootCause(targetException), targetException,
														ExceptionUtils.getRootCause(e), e);
												//
												if (GraphicsEnvironment.isHeadless()) {
													//
													if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
														LOG.error(getMessage(rootCause), rootCause);
													} else if (rootCause != null) {
														rootCause.printStackTrace();
													} // if
														//
												} else {
													//
													JOptionPane.showMessageDialog(null, getMessage(rootCause));
													//
												} // if
													//
											} // try
												//
										} // if
											//
										throw new IllegalStateException();
										//
									}));
					//
					if (objects == null || objects.isEmpty()) {
						//
						return null;
						//
					} // if
						//
					if (objects.size() == 1) {
						//
						return Pair.of(getName(f), toString(objects.get(0)));
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
		for (int i = 0; pairs != null && i < pairs.size(); i++) {
			//
			if ((pair = pairs.get(i)) == null) {
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
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} // try
				//
			return VoiceManager.getListCellRendererComponent(listCellRenderer, list,
					StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
					index, isSelected, cellHasFocus);
			//
		}

	}

	private static String getVoiceAttribute(final SpeechApi instance, final String voiceId, final String attribute) {
		return instance != null ? instance.getVoiceAttribute(voiceId, attribute) : null;
	}

	private static Range<Integer> createVolumnRange(final Class<?> clz) {
		//
		final Map<String, Object> map = collect(
				filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(clz), Arrays::stream, null), a -> {
					//
					final String simpleName = getSimpleName(annotationType(a));
					//
					if (Objects.equals(simpleName, "MinValue") || Objects.equals(simpleName, "MaxValue")) {
						//
						final List<Method> temp = toList(
								filter(testAndApply(Objects::nonNull, getDeclaredMethods(annotationType(a)),
										Arrays::stream, null), ma -> Objects.equals(getName(ma), "name")));
						//
						if (temp == null || temp.isEmpty()) {
							//
							return false;
							//
						} else if (temp.size() == 1 && temp.get(0) != null) {
							//
							try {
								//
								return Objects.equals("volume", temp.get(0).invoke(a));
								//
							} catch (final IllegalAccessException e) {
								//
								if (GraphicsEnvironment.isHeadless()) {
									//
									if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
										LOG.error(getMessage(e), e);
									} else if (e != null) {
										e.printStackTrace();
									} // if
										//
								} else {
									//
									JOptionPane.showMessageDialog(null, getMessage(e));
									//
								} // if
									//
							} catch (final InvocationTargetException e) {
								//
								final Throwable targetException = e.getTargetException();
								//
								final Throwable rootCause = ObjectUtils.firstNonNull(
										ExceptionUtils.getRootCause(targetException), targetException,
										ExceptionUtils.getRootCause(e), e);
								//
								if (GraphicsEnvironment.isHeadless()) {
									//
									if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
										LOG.error(getMessage(rootCause), rootCause);
									} else if (rootCause != null) {
										rootCause.printStackTrace();
									} // if
										//
								} else {
									//
									JOptionPane.showMessageDialog(null, getMessage(rootCause));
									//
								} // if
									//
							} // try
								//
						} // if
							//
						throw new IllegalArgumentException();
						//
					} // if
						//
					return false;
					//
				}), Collectors.toMap(a -> getSimpleName(annotationType(a)), a -> {
					//
					final List<Method> temp = toList(filter(
							testAndApply(Objects::nonNull, getDeclaredMethods(annotationType(a)), Arrays::stream, null),
							ma -> Objects.equals(getName(ma), "value")));
					//
					if (temp == null || temp.isEmpty()) {
						//
						return false;
						//
					} else if (temp.size() == 1 && temp.get(0) != null) {
						//
						try {
							//
							return temp.get(0).invoke(a);
							//
						} catch (final IllegalAccessException e) {
							//
							if (GraphicsEnvironment.isHeadless()) {
								//
								if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
									LOG.error(getMessage(e), e);
								} else if (e != null) {
									e.printStackTrace();
								} // if
									//
							} else {
								//
								JOptionPane.showMessageDialog(null, getMessage(e));
								//
							} // if
								//
						} catch (final InvocationTargetException e) {
							//
							final Throwable targetException = e.getTargetException();
							//
							final Throwable rootCause = ObjectUtils.firstNonNull(
									ExceptionUtils.getRootCause(targetException), targetException,
									ExceptionUtils.getRootCause(e), e);
							//
							if (GraphicsEnvironment.isHeadless()) {
								//
								if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
									LOG.error(getMessage(rootCause), rootCause);
								} else if (rootCause != null) {
									rootCause.printStackTrace();
								} // if
									//
							} else {
								//
								JOptionPane.showMessageDialog(null, getMessage(rootCause));
								//
							} // if
								//
						} // try
							//
					} // if
						//
					throw new IllegalArgumentException();
					//
				}));
		//
		return createRange(
				toInteger(testAndApply(VoiceManager::containsKey, map, "MinValue", MapUtils::getObject, null)),
				toInteger(testAndApply(VoiceManager::containsKey, map, "MaxValue", MapUtils::getObject, null)));
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

	private static void setEnabled(final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	private static <T, U, R> R apply(final BiFunction<T, U, R> instance, final T t, final U u) {
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

	private static String getProperty(final PropertyResolver instance, final String key) {
		return instance != null ? instance.getProperty(key) : null;
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
		if (Objects.equals(source, btnSpeak)) {
			//
			if (speechApi != null) {
				//
				speechApi.speak(getText(tfTextTts), toString(getSelectedItem(cbmVoiceId))
				//
						, intValue(getRate(getText(tfSpeechRate)), 0)// rate
						//
						,
						Math.min(Math.max(intValue(jsSpeechVolume != null ? jsSpeechVolume.getValue() : null, 100), 0),
								100)// volume

				);
				//
			} // if
				//
		} else if (Objects.equals(source, btnWriteVoice)) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
				//
				final File file = jfc.getSelectedFile();
				//
				if (objectMap != null) {
					//
					objectMap.setObject(SpeechApi.class, speechApi);
					//
					objectMap.setObject(File.class, file);
					//
				} // if
					//
				writeVoiceToFile(objectMap, getText(tfTextTts), toString(getSelectedItem(cbmVoiceId))
				//
						, intValue(getRate(getText(tfSpeechRate)), 0)// rate
						//
						,
						Math.min(Math.max(intValue(jsSpeechVolume != null ? jsSpeechVolume.getValue() : null, 100), 0),
								100)// volume
				);
				//

				//
				final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory, FORMAT,
						getSelectedItem(cbmAudioFormatWrite));
				//
				if (byteConverter != null) {
					//
					try {
						//
						FileUtils.writeByteArrayToFile(file,
								byteConverter.convert(FileUtils.readFileToByteArray(file)));
						//
					} catch (final IOException e) {
						//
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								LOG.error(getMessage(e), e);
							} else if (e != null) {
								e.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, getMessage(e));
							//
						} // if
							//
					} // try
						//
				} // if
					//
			} // if
				//
		} else if (Objects.equals(source, btnExecute)) {
			//
			forEach(Stream.of(tfFile, tfFileLength, tfFileDigest), x -> setText(x, null));
			//
			File file = null;
			//
			final Voice voice = createVoice(objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new),
					this);
			//
			if (isSelected(cbUseTtsVoice)) {
				//
				final String voiceId = toString(getSelectedItem(cbmVoiceId));
				//
				if (speechApi != null) {
					//
					try {
						//
						speechApi.writeVoiceToFile(getText(tfTextImport), voiceId
						//
								, intValue(getRate(getText(tfSpeechRate)), 0)// rate
								//
								,
								Math.min(Math.max(
										intValue(jsSpeechVolume != null ? jsSpeechVolume.getValue() : null, 100), 0),
										100)// volume
								, file = File.createTempFile(
										RandomStringUtils.randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null)
						//
						);
						//
						if (Objects.equals("wav", getFileExtension(
								testAndApply(Objects::nonNull, file, new ContentInfoUtil()::findMatch, null)))) {
							//
							final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory,
									FORMAT, getSelectedItem(cbmAudioFormatExecute));
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
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								LOG.error(getMessage(e), e);
							} else if (e != null) {
								e.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, getMessage(e));
							//
						} // if
							//
					} // try
						//
				} // if
					//
				if (voice != null) {
					//
					voice.setLanguage(StringUtils.defaultIfBlank(voice.getLanguage(),
							convertLanguageCodeToText(getVoiceAttribute(speechApi, voiceId, "Language"), 16)));
					//
					voice.setSource(StringUtils.defaultIfBlank(voice.getSource(),
							getProviderName(cast(Provider.class, speechApi))));
					//
				} // if
					//
				deleteOnExit(file);
				//
			} else {
				//
				final JFileChooser jfc = new JFileChooser(".");
				//
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					//
					try {
						//
						if (voice != null) {
							//
							voice.setSource(StringUtils.defaultIfBlank(voice.getSource(), getMp3TagValue(
									file = jfc.getSelectedFile(), x -> StringUtils.isNotBlank(toString(x)), mp3Tags)));
							//
						} // if
							//
					} catch (final IOException | BaseException | IllegalAccessException e) {
						//
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								LOG.error(getMessage(e), e);
							} else if (e != null) {
								e.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, getMessage(e));
							//
						} // if
							//
					} catch (final InvocationTargetException e) {
						//
						final Throwable targetException = e.getTargetException();
						//
						final Throwable rootCause = ObjectUtils.firstNonNull(
								ExceptionUtils.getRootCause(targetException), targetException,
								ExceptionUtils.getRootCause(e), e);
						//
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								LOG.error(getMessage(rootCause), rootCause);
							} else if (rootCause != null) {
								rootCause.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, getMessage(rootCause));
							//
						} // if
							//
					} // try
						//
				} else {
					//
					clear(tmImportException);
					//
					final String message = "No File Selected";
					//
					if (tmImportException != null) {
						//
						tmImportException.addRow(new Object[] { getText(voice), getRomaji(voice), message });
						//
					} else {
						//
						JOptionPane.showMessageDialog(null, message);
						//
					} // if
						//
					return;
					//
				} // if
					//
			} // if
				//
			SqlSession sqlSession = null;
			//
			try {
				//
				final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
				//
				if (objectMap != null) {
					//
					objectMap.setObject(File.class, file);
					//
					objectMap.setObject(Voice.class, voice);
					//
					objectMap.setObject(VoiceMapper.class, getMapper(getConfiguration(sqlSessionFactory),
							VoiceMapper.class, sqlSession = openSession(sqlSessionFactory)));
					//
					objectMap.setObject(VoiceManager.class, this);
					//
					objectMap.setObject(String.class, voiceFolder);
					//
				} // if
					//
				execute(objectMap);
				//
			} finally {
				//
				IOUtils.closeQuietly(sqlSession);
				//
			} // try
				//
		} else if (Objects.equals(source, btnConvertToRomaji)) {
			//
			setText(tfRomaji, convert(jakaroma = ObjectUtils.getIfNull(jakaroma, Jakaroma::new), getText(tfTextImport),
					false, false));
			//
		} else if (Objects.equals(source, btnConvertToKatakana)) {
			//
			setText(tfKatakana, testAndApply(Objects::nonNull, getText(tfHiragana),
					x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_HIRA_TO_ZEN_KATA), null));
			//
		} else if (Objects.equals(source, btnCopyRomaji)) {
			//
			setContents(getSystemClipboard(toolkit = ObjectUtils.getIfNull(toolkit, Toolkit::getDefaultToolkit)),
					new StringSelection(getText(tfRomaji)), null);
			//
		} else if (Objects.equals(source, btnCopyHiragana)) {
			//
			setContents(getSystemClipboard(toolkit = ObjectUtils.getIfNull(toolkit, Toolkit::getDefaultToolkit)),
					new StringSelection(getText(tfHiragana)), null);
			//
		} else if (Objects.equals(source, btnCopyKatakana)) {
			//
			setContents(getSystemClipboard(toolkit = ObjectUtils.getIfNull(toolkit, Toolkit::getDefaultToolkit)),
					new StringSelection(getText(tfKatakana)), null);
			//
		} else if (Objects.equals(source, btnExport)) {
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
				final List<Voice> voices = voiceMapper != null ? voiceMapper.retrieveAllVoices() : null;
				//
				forEach(voices, v -> {
					//
					if (v != null && voiceMapper != null) {
						//
						v.setListNames(voiceMapper.searchVoiceListNamesByVoiceId(v.getId()));
						//
					} // if
						//
				});
				//
				final IH ih = new IH();
				//
				final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
				//
				if (objectMap != null) {
					//
					objectMap.setObject(VoiceManager.class, this);
					//
					// org.springframework.context.support.VoiceManager$BooleanMap
					//
					final BooleanMap booleanMap = Reflection.newProxy(BooleanMap.class, ih);
					//
					if (booleanMap != null) {
						//
						booleanMap.setBoolean("overMp3Title", isSelected(cbOverMp3Title));
						//
						booleanMap.setBoolean("ordinalPositionAsFileNamePrefix",
								isSelected(cbOrdinalPositionAsFileNamePrefix));
						//
						booleanMap.setBoolean("jlptAsFolder", isSelected(cbJlptAsFolder));
						//
					} // if
						//
					objectMap.setObject(BooleanMap.class, booleanMap);
					//
				} // if
					//
				export(voices, outputFolderFileNameExpressions, objectMap);
				//
				try (final OutputStream os = new FileOutputStream(
						file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
					//
					write(workbook = createWorkbook(voices), os);
					//
				} // try
					//
			} catch (final IOException | IllegalAccessException e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				final Throwable rootCause = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
						targetException, ExceptionUtils.getRootCause(e), e);
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(rootCause), rootCause);
					} else if (rootCause != null) {
						rootCause.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(rootCause));
					//
				} // if
					//
			} finally {
				//
				IOUtils.closeQuietly(sqlSession);
				//
				IOUtils.closeQuietly(workbook);
				//
				if (file != null && file.exists() && isFile(file) && file.length() == 0) {
					//
					file.delete();
					//
				} // if
					//
			} // try
				//
		} else if (Objects.equals(source, btnImportFileTemplate)) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					FileUtils.writeByteArrayToFile(jfc.getSelectedFile(),
							createImportFileTemplateByteArray(isSelected(cbImportFileTemplateGenerateBlankRow),
									getJlptLevels(), keySet(getGaKuNenBeTsuKanJiMultimap())));
					//
				} catch (final IOException e) {
					//
					if (GraphicsEnvironment.isHeadless()) {
						//
						if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
							LOG.error(getMessage(e), e);
						} else if (e != null) {
							e.printStackTrace();
						} // if
							//
					} else {
						//
						JOptionPane.showMessageDialog(null, getMessage(e));
						//
					} // if
						//
				} // try
					//
			} // if
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
			final File[] fs = listFiles(testAndApply(Objects::nonNull,
					JOptionPane.showInputDialog("Folder",
							getProperty(propertyResolver,
									"org.springframework.context.support.VoiceManager.importFolder")),
					File::new, null));
			//
			File f = null;
			//
			for (int i = 0; fs != null && i < fs.length; i++) {
				//
				try {
					//
					if ((f = fs[i]) == null || !isXlsxFile(f)) {
						//
						continue;
						//
					} // if
						//
				} catch (final IOException e) {
					//
					if (GraphicsEnvironment.isHeadless()) {
						//
						if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
							LOG.error(getMessage(e), e);
						} else if (e != null) {
							e.printStackTrace();
						} // if
							//
					} else {
						//
						JOptionPane.showMessageDialog(null, getMessage(e));
						//
					} // if
						//
				} // try
					//
				importVoice(f);
				//
			} // for;
				//
		} else if (Objects.equals(source, btnExportGaKuNenBeTsuKanJi)) {
			//
			File file = null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = new FileOutputStream(
					file = new File(String.format("学年別漢字_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
				//
				write(workbook = createWorkbook(Pair.of("学年", "漢字"), getGaKuNenBeTsuKanJiMultimap()), os);
				//
			} catch (final IOException e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				if (file != null && file.exists() && isFile(file) && file.length() == 0) {
					//
					file.delete();
					//
				} // if
					//
			} // try
				//
		} // if
			//
	}

	private static File[] listFiles(final File instance) {
		return instance != null ? instance.listFiles() : null;
	}

	private void importVoice(final File file) {
		//
		try (final Workbook workbook = isXlsxFile(file) ? new XSSFWorkbook(file) : null) {
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
			final List<String> sheetExclued = toList(
					map(stream(getObjectList(objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new),
							getLpwstr(testAndApply(VoiceManager::contains,
									getCustomProperties(getProperties(poiXmlDocument)), "sheetExcluded",
									VoiceManager::getProperty, null)))),
							VoiceManager::toString));
			//
			if (objectMap != null) {
				//
				objectMap.setObject(File.class, file);
				//
				objectMap.setObject(VoiceManager.class, this);
				//
				objectMap.setObject(String.class, voiceFolder);
				//
				objectMap.setObject(SqlSessionFactory.class, sqlSessionFactory);
				//
				objectMap.setObject(JProgressBar.class, progressBarImport);
				//
				objectMap.setObject(Provider.class, cast(Provider.class, speechApi));
				//
				objectMap.setObject(SpeechApi.class, speechApi);
				//
				objectMap.setObject(POIXMLDocument.class, poiXmlDocument);
				//
				objectMap.setObject(Jakaroma.class, jakaroma = ObjectUtils.getIfNull(jakaroma, Jakaroma::new));
				//
			} // if
				//
			final boolean headless = GraphicsEnvironment.isHeadless();
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
			for (int i = 0; workbook != null && i < workbook.getNumberOfSheets(); i++) {
				//
				if (errorMessageConsumer == null) {
					//
					errorMessageConsumer = (v, m) -> {
						//
						if (headless) {
							//
							errorOrPrintln(LOG, System.err, m);
							//
						} else {
							//
							if (tmImportException != null) {
								//
								tmImportException.addRow(new Object[] { getText(v), getRomaji(v), m });
								//
							} else {
								//
								JOptionPane.showMessageDialog(null, m);
								//
							} // if
								//
						} // if
							//
					};
					//
				} // if
					//
				if (throwableConsumer == null) {
					//
					throwableConsumer = (v, e) -> {
						//
						if (headless) {
							//
							if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
								//
								LOG.error(getMessage(e), e);
								//
							} else if (e != null) {
								//
								e.printStackTrace();
								//
							} // if
								//
						} else {
							//
							if (tmImportException != null) {
								//
								tmImportException.addRow(new Object[] { getText(v), getRomaji(v), e });
								//
							} else {
								//
								JOptionPane.showMessageDialog(null, e);
								//
							} // if
								//
						} // if
							//
					};
					//
				} // if
					//
				if (voiceConsumer == null) {
					//
					voiceConsumer = v -> {
						//
						setText(tfCurrentProcessingVoice, getText(v));
						//
						if (numberOfVoiceProcessed != null) {
							//
							numberOfVoiceProcessed.incrementAndGet();
							//
						} // if
							//
					};
					//
				} // if
					//
				if (contains(sheetExclued, getSheetName(sheet = workbook.getSheetAt(i)))) {
					//
					continue;
					//
				} // if
					//
				setMaximum(progressBarImport, Math.max(0, (sheet != null ? sheet.getPhysicalNumberOfRows() : 0) - 1));
				//
				if (objectMap != null) {
					//
					objectMap.setObject(ByteConverter.class,
							getByteConverter(configurableListableBeanFactory, FORMAT,
									getLpwstr(testAndApply(VoiceManager::contains,
											getCustomProperties(getProperties(poiXmlDocument)), "audioFormat",
											VoiceManager::getProperty, null))));
					//
				} // if
					//
				importVoice(sheet, objectMap, errorMessageConsumer, throwableConsumer, voiceConsumer);
				//
				setText(tfCurrentProcessingSheetName, getSheetName(sheet));
				//
				numberOfSheetProcessed = Integer.valueOf(intValue(numberOfSheetProcessed, 0) + 1);
				//
			} // for
				//
			if (tmImportResult != null) {
				//
				tmImportResult.addRow(new Object[] { numberOfSheetProcessed, numberOfVoiceProcessed });
				//
			} // if
				//
		} catch (final InvalidFormatException | IOException | IllegalAccessException | BaseException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			final Throwable rootCause = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
					targetException, ExceptionUtils.getRootCause(e), e);
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(rootCause), rootCause);
				} else if (rootCause != null) {
					rootCause.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(rootCause));
				//
			} // if
				//
		} // try
			//
	}

	private static String getName(final File instance) {
		return instance != null ? instance.getName() : null;
	}

	private static boolean isXlsxFile(final File file) throws IOException {
		//
		final String mimeType = getMimeType(
				testAndApply(VoiceManager::isFile, file, new ContentInfoUtil()::findMatch, null));
		//
		return or(x -> Objects.equals(mimeType, x), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"application/vnd.openxmlformats-officedocument");
		//
	}

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
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

	private static <A> A getValue0(final Unit<A> instance) {
		return instance != null ? instance.getValue0() : null;
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

	private static POIXMLProperties getProperties(final POIXMLDocument instance) {
		return instance != null ? instance.getProperties() : null;
	}

	private static CustomProperties getCustomProperties(final POIXMLProperties instance) {
		return instance != null ? instance.getCustomProperties() : null;
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
		if (Objects.equals(getSource(evt), jcbVoiceId)) {
			//
			try {
				//
				final String language = getVoiceAttribute(speechApi, toString(getSelectedItem(cbmVoiceId)), "Language");
				//
				setText(tfSpeechLanguage,
						StringUtils.defaultIfBlank(convertLanguageCodeToText(language, 16), language));
				//
			} catch (final Error e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} // try
				//
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
		final Map<String, ByteConverter> byteConverters = getBeansOfType(configurableListableBeanFactory,
				ByteConverter.class);
		//
		if (byteConverters != null && byteConverters.entrySet() != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : byteConverters.entrySet()) {
				//
				if (en == null || (bd = configurableListableBeanFactory.getBeanDefinition(getKey(en))) == null
						|| !bd.hasAttribute(attribute)) {
					continue;
				} // if
					//
				add(list = ObjectUtils.getIfNull(list, ArrayList::new), bd.getAttribute(attribute));
				//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	private static <T> Map<String, T> getBeansOfType(final ListableBeanFactory instance, final Class<T> type) {
		return instance != null ? instance.getBeansOfType(type) : null;
	}

	private static ByteConverter getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String attribute, final Object value) {
		//
		Unit<ByteConverter> byteConverter = null;
		//
		final Map<String, ByteConverter> byteConverters = getBeansOfType(configurableListableBeanFactory,
				ByteConverter.class);
		//
		if (byteConverters != null && byteConverters.entrySet() != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : byteConverters.entrySet()) {
				//
				if (en == null || (bd = configurableListableBeanFactory.getBeanDefinition(getKey(en))) == null
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
		return getValue0(byteConverter);
		//
	}

	@Override
	public void stateChanged(final ChangeEvent evt) {
		//
		if (Objects.equals(getSource(evt), jsSpeechVolume)) {
			//
			setText(tfSpeechVolume, jsSpeechVolume != null ? Integer.toString(jsSpeechVolume.getValue()) : null);
			//
		} // if
			//
	}

	@Override
	public void keyTyped(final KeyEvent evt) {
	}

	@Override
	public void keyPressed(final KeyEvent evt) {
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
				final List<?> list = getObjectList(
						objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new), getText(jtf));
				//
				if ((objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new)) != null) {
					//
					setText(jlListNames, objectMapper.writeValueAsString(list));
					//
				} // if
					//
				setText(jlListNameCount, list != null ? Integer.toString(list.size()) : null);
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
			Multimap<String, String> gaKuNenBeTsuKanJiMultimap = null;
			//
			try {
				//
				gaKuNenBeTsuKanJiMultimap = getGaKuNenBeTsuKanJiMultimap();
				//
			} catch (final IOException e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} // try
				//
			final Collection<Entry<String, String>> entries = entries(gaKuNenBeTsuKanJiMultimap);
			//
			if (entries != null) {
				//
				List<String> list = null;
				//
				String key = null;
				//
				for (final Entry<String, String> en : entries) {
					//
					if (en == null || !StringUtils.equals(getValue(en), getText(jtf))) {
						continue;
					} // if
						//
					if (!contains(list = ObjectUtils.getIfNull(list, ArrayList::new), key = getKey(en))) {
						//
						add(list = ObjectUtils.getIfNull(list, ArrayList::new), key);
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
					setSelectedItem(cbmGaKuNenBeTsuKanJi, IterableUtils.get(list, 0));
					//
				} else if (size < 1) {
					//
					setSelectedItem(cbmGaKuNenBeTsuKanJi, null);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // if
			//
	}

	private static <K, V> Collection<Entry<K, V>> entries(final Multimap<K, V> instance) {
		return instance != null ? instance.entries() : null;
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

		private Integer bitRate, quality = null;

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
				if (containsKey(map, string)) {
					//
					setQuality(get(map, string));
					//
				} else if (containsKey(map, string = StringUtils.lowerCase(string))) {
					//
					setQuality(get(map, string));
					//
				} // if
					//
			} // if
				//
		}

		private static <V> V get(final Map<?, V> instance, final Object key) {
			return instance != null ? instance.get(key) : null;
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
				final Integer quality = getQuality();
				//
				if (range != null && !range.contains(quality)) {
					//
					throw new IllegalStateException(String.format("Under VBR,\"quality\" cound be with in %1$s to %2$s",
							range.lowerEndpoint(), range.upperEndpoint()));
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
			try (final InputStream is = clz != null
					? clz.getResourceAsStream(
							String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
					: null) {
				//
				final org.apache.bcel.classfile.Method[] ms = getMethods(
						ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
				//
				org.apache.bcel.classfile.Method m = null;
				//
				byte[] bs = null;
				//
				String line = null;
				//
				String[] lines = null;
				//
				Integer index1 = null, index2 = null, count = null;
				//
				for (int i = 0; ms != null && i < ms.length; i++) {
					//
					if ((m = ms[i]) == null) {
						//
						continue;
						//
					} // if
						//
					lines = StringUtils.split(StringUtils.trim(Utility.codeToString(bs = CodeUtil.getCode(m.getCode()),
							m.getConstantPool(), 0, bs != null ? bs.length : 0)), '\n');
					//
					index1 = index2 = count = null;
					//
					for (int j = 0; lines != null && j < lines.length; j++) {
						//
						if ((line = lines[j]) == null) {
							continue;
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
					if (index1 != null && index2 != null) {
						break;
					} // if
						//
				} // for
					//
				return count != null ? Range.closed(0, count.intValue() - 1) : null;
				//
			} // try
				//
		}

		private static Map<String, Integer> createQualityMap() throws IOException {
			//
			Map<String, Integer> map = null;
			//
			final Class<?> clz = LameEncoder.class;
			//
			try (final InputStream is = clz != null
					? clz.getResourceAsStream(
							String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
					: null) {
				//
				final List<org.apache.bcel.classfile.Method> ms = toList(filter(testAndApply(Objects::nonNull,
						getMethods(ClassParserUtil
								.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null))),
						Arrays::stream, null), x -> x != null && Objects.equals(x.getName(), "string2quality")));
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
				for (int i = 0; ms != null && i < ms.size(); i++) {
					//
					if ((m = ms.get(i)) == null) {
						//
						continue;
						//
					} // if
						//
					lines = StringUtils.split(StringUtils.trim(Utility.codeToString(bs = CodeUtil.getCode(m.getCode()),
							m.getConstantPool(), 0, bs != null ? bs.length : 0)), '\n');
					//
					for (int j = 0; lines != null && j < lines.length; j++) {
						//
						if (matches(matcher = matcher(PATTERN_LDC_STRING, line = lines[j]))
								&& matcher.groupCount() > 0) {
							key = matcher.group(1);
						} else if (matches(matcher = matcher(PATTERN_BI_PUSH, line)) && matcher.groupCount() > 0) {
							put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), key, valueOf(matcher.group(1)));
						} else if (matches(matcher = matcher(PATTERN_ICONST, line)) && matcher.groupCount() > 0) {
							put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), key, valueOf(matcher.group(1)));
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
			try (final ByteArrayInputStream bais = testAndApply(x -> x != null && x.length > 0, source,
					ByteArrayInputStream::new, null);
					final AudioInputStream ais = bais != null ? AudioSystem.getAudioInputStream(bais) : null) {
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
					if ((baos = ObjectUtils.getIfNull(baos, ByteArrayOutputStream::new)) != null) {
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
			if (localeIds.size() == 1) {
				//
				final LocaleID localeId = localeIds.get(0);
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
		Pair<String, ?> pair = null;
		//
		for (int i = 0; pairs != null && i < pairs.size(); i++) {
			//
			if ((pair = pairs.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (test(predicate, string = toString(getValue(pair))) || predicate == null) {
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
		if (Objects.equals("mp3",
				getFileExtension(testAndApply(VoiceManager::isFile, file, new ContentInfoUtil()::findMatch, null)))) {
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
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		List<Pair<String, ?>> pairs = null;
		//
		if (id3v1 != null && attributes != null) {
			//
			Method[] ms = null;
			//
			List<Method> methods = null;
			//
			Method m = null;
			//
			for (int i = 0; i < attributes.length; i++) {
				//
				final String attribute = attributes[i];
				//
				if ((methods = toList(filter(testAndApply(Objects::nonNull,
						ms = ObjectUtils.getIfNull(ms, () -> getMethods(getClass(id3v1))), Arrays::stream, null),
						a -> matches(
								matcher(Pattern.compile(String.format("get%1$s", StringUtils.capitalize(attribute))),
										getName(a)))))) == null
						|| methods.isEmpty()) {
					//
					continue;
					//
				} // if
					//
				if (methods.size() == 1) {
					//
					if ((m = methods.get(0)) != null && m.getParameterCount() == 0) {
						//
						add(pairs = ObjectUtils.getIfNull(pairs, ArrayList::new), Pair.of(attribute, m.invoke(id3v1)));
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
		} // if
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
		final VoiceManager voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
		//
		final DefaultTableModel tmImportException = voiceManager != null ? voiceManager.tmImportException : null;
		//
		final Voice voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		String message = null;
		//
		clear(tmImportException);
		//
		if (file == null) {
			//
			message = "No File Selected";
			//
			if (tmImportException != null) {
				//
				tmImportException.addRow(new Object[] { getText(voice), getRomaji(voice), message });
				//
			} else {
				//
				JOptionPane.showMessageDialog(null, message);
				//
			} // if
				//
			return;
			//
		} else if (!file.exists()) {
			//
			message = String.format("File \"%1$s\" does not exist", getAbsolutePath(file));
			//
			if (tmImportException != null) {
				//
				tmImportException.addRow(new Object[] { getText(voice), getRomaji(voice), message });
				//
			} else {
				//
				JOptionPane.showMessageDialog(null, message);
				//
			} // if
				//
			return;
			//
		} else if (!isFile(file)) {
			//
			message = "Not A Regular File Selected";
			//
			if (tmImportException != null) {
				//
				tmImportException.addRow(new Object[] { getText(voice), getRomaji(voice), message });
				//
			} else {
				//
				JOptionPane.showMessageDialog(null, message);
				//
			} // if
				//
			return;
			//
		} else if (file.length() == 0) {
			//
			message = "Empty File Selected";
			//
			if (tmImportException != null) {
				//
				tmImportException.addRow(new Object[] { getText(voice), getRomaji(voice), message });
				//
			} else {
				//
				JOptionPane.showMessageDialog(null, message);
				//
			} // if
				//
			return;
			//
		} // if
			//
		SqlSession sqlSession = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		try {
			//
			importVoice(objectMap, (v, m) -> {
				//
				if (headless) {
					//
					errorOrPrintln(LOG, System.err, m);
					//
				} else {
					//
					if (tmImportException != null) {
						//
						tmImportException.addRow(new Object[] { getText(v), getRomaji(v), m });
						//
					} else {
						//
						JOptionPane.showMessageDialog(null, m);
						//
					} // if
						//
				} // if
					//
			}, (v, e) -> {
				//
				if (headless) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						//
						LOG.error(getMessage(e), e);
						//
					} else if (e != null) {
						//
						e.printStackTrace();
						//
					} // if
						//
				} else {
					//
					if (tmImportException != null) {
						//
						tmImportException.addRow(new Object[] { getText(v), getRomaji(v), e });
						//
					} else {
						//
						JOptionPane.showMessageDialog(null, e);
						//
					} // if
						//
				} // if
					//
			});
			//
		} finally {
			//
			IOUtils.closeQuietly(sqlSession);
			//
		} // try
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

	private static Integer getRate(final String string) {
		//
		Integer rate = valueOf(string);
		//
		if (rate == null) {
			//
			final List<Field> fs = toList(filter(
					testAndApply(Objects::nonNull, Integer.class.getDeclaredFields(), Arrays::stream, null),
					f -> f != null
							&& (isAssignableFrom(Number.class, getType(f)) || Objects.equals(Integer.TYPE, getType(f)))
							&& Objects.equals(getName(f), string)));
			//
			if (fs != null && !fs.isEmpty()) {
				//
				final int size = fs.size();
				//
				if (size > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				final Field f = fs.get(0);
				//
				if (f != null) {
					//
					if (Modifier.isStatic(f.getModifiers())) {
						//
						try {
							//
							final Number number = cast(Number.class, f.get(null));
							//
							if (number != null) {
								//
								rate = number.intValue();
								//
							} // if
								//
						} catch (final IllegalAccessException e) {
							//
							if (GraphicsEnvironment.isHeadless()) {
								//
								if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
									LOG.error(getMessage(e), e);
								} else if (e != null) {
									e.printStackTrace();
								} // if
									//
							} else {
								//
								JOptionPane.showMessageDialog(null, getMessage(e));
								//
							} // if
								//
						} // try
							//
					} // if
						//
				} // if
					//
			} // if
				//
		} // if
			//
		return rate;
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static void write(final Workbook instance, final OutputStream stream) throws IOException {
		//
		if (instance != null && (stream != null || Proxy.isProxyClass(getClass(instance)))) {
			//
			instance.write(stream);
			//
		} // if
			//
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
			final Class<?> classJlpt = forName("domain.Voice$JLPT");
			//
			final Class<?> classGaKuNenBeTsuKanJi = forName("domain.Voice$GaKuNenBeTsuKanJi");
			//
			DataValidationHelper dvh = null;
			//
			Unit<List<Boolean>> booleans = null;
			//
			Class<?> type = null;
			//
			List<String> strings = null;
			//
			for (int i = 0; fs != null && i < fs.size(); i++) {
				//
				if ((f = fs.get(i)) == null) {
					//
					continue;
					//
				} // if
					//
				if (sheet == null) {
					//
					sheet = createSheet(workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
					//
				} // if
					//
				if (row == null && sheet != null) {
					//
					row = sheet.createRow(0);
					//
				} // if
					//
				setCellValue(createCell(row, i), getName(f));
				//
			} // for
				//
			if (generateBlankRow && fs != null) {
				//
				if (sheet != null) {
					//
					row = sheet.createRow(sheet.getPhysicalNumberOfRows());
					//
				} // if
					//
				for (int i = 0; i < fs.size(); i++) {
					//
					setCellValue(createCell(row, i), null);
					//
					if (Objects.equals(Boolean.class, type = getType(f = fs.get(i)))) {// java.lang.Boolean
						//
						if (dvh == null) {
							//
							dvh = getDataValidationHelper(sheet);
							//
						} // if
							//
						if (booleans == null) {
							//
							try {
								//
								booleans = Unit.with(getBooleanValues());
								//
							} catch (final IllegalAccessException e) {
								//
								if (GraphicsEnvironment.isHeadless()) {
									//
									if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
										LOG.error(getMessage(e), e);
									} else if (e != null) {
										e.printStackTrace();
									} // if
										//
								} else {
									//
									JOptionPane.showMessageDialog(null, getMessage(e));
									//
								} // if
									//
							} // try
								//
						} // if
							//
						if (!(dvh instanceof XSSFDataValidationHelper)
								|| CollectionUtils.isNotEmpty(getValue0(booleans))) {
							//
							sheet.addValidationData(createValidation(dvh,
									createExplicitListConstraint(dvh,
											toArray(toList(map(stream(getValue0(booleans)), VoiceManager::toString)),
													new String[] {})),
									new CellRangeAddressList(row.getRowNum(), row.getRowNum(), i, i)));
							//
						} // if
							//
					} else if (isAssignableFrom(Enum.class, type)) {// java.lang.Enum
						//
						if (dvh == null) {
							//
							dvh = getDataValidationHelper(sheet);
							//
						} // if
							//
						if (!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(strings = toList(
								map(testAndApply(Objects::nonNull, type.getEnumConstants(), Arrays::stream, null),
										x -> x instanceof Enum ? name((Enum<?>) x) : toString(x))))) {
							//
							sheet.addValidationData(createValidation(dvh,
									createExplicitListConstraint(dvh, toArray(strings, new String[] {})),
									new CellRangeAddressList(row.getRowNum(), row.getRowNum(), i, i)));
							//
						} // if
							//
					} else if (anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
							a -> Objects.equals(annotationType(a), classJlpt))) {// domain.Voice.JLPT
						//
						if (dvh == null) {
							//
							dvh = getDataValidationHelper(sheet);
							//
						} // if
							//
						if (!(dvh instanceof XSSFDataValidationHelper) || CollectionUtils.isNotEmpty(jlptValues)) {
							//
							sheet.addValidationData(createValidation(dvh,
									createExplicitListConstraint(dvh, toArray(jlptValues, new String[] {})),
									new CellRangeAddressList(row.getRowNum(), row.getRowNum(), i, i)));
							//
						} // if
							//
					} else if (anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
							a -> Objects.equals(annotationType(a), classGaKuNenBeTsuKanJi))) {// domain.Voice.GaKuNenBeTsuKanJi
						//
						if (dvh == null) {
							//
							dvh = getDataValidationHelper(sheet);
							//
						} // if
							//
						if (!(dvh instanceof XSSFDataValidationHelper)
								|| CollectionUtils.isNotEmpty(gaKuNenBeTsuKanJiValues)) {
							//
							sheet.addValidationData(createValidation(dvh,
									createExplicitListConstraint(dvh,
											toArray(gaKuNenBeTsuKanJiValues, new String[] {})),
									new CellRangeAddressList(row.getRowNum(), row.getRowNum(), i, i)));
							//
						} // if
							//
					} // if
						//
				} // for
					//
			} // if
				//
			write(workbook, baos);
			//
			bs = toByteArray(baos);
			//
		} catch (final IOException e) {
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
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

	private static Sheet createSheet(final Workbook instance) {
		return instance != null ? instance.createSheet() : null;
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

	private static Cell createCell(final Row instance, final int column) {
		return instance != null ? instance.createCell(column) : null;
	}

	private static void setCellValue(final Cell instance, final String value) {
		if (instance != null) {
			instance.setCellValue(value);
		}
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
			infoOrPrintln(LOG, System.out, String.format("%1$s %2$s/%3$s (%4$s) %5$s/%6$s",
					percentage != null
							? StringUtils.leftPad(format(percentNumberFormat, percentage.doubleValue()), 5, ' ')
							: null,
					StringUtils.leftPad(VoiceManager.toString(sheetCurrent),
							StringUtils.length(VoiceManager.toString(ObjectUtils.max(sheetCurrent, sheetTotal))), ' '),
					sheetTotal, currentSheetName, StringUtils.leftPad(VoiceManager.toString(counter),
							StringUtils.length(VoiceManager.toString(count))),
					count));
			//
			SqlSession sqlSession = null;
			//
			try {
				//
				if (objectMap != null) {
					//
					final SqlSessionFactory sqlSessionFactory = ObjectMap.getObject(objectMap, SqlSessionFactory.class);
					//
					objectMap.setObject(VoiceMapper.class, getMapper(getConfiguration(sqlSessionFactory),
							VoiceMapper.class, sqlSession = openSession(sqlSessionFactory)));
					//
					objectMap.setObject(Voice.class, voice);
					//
					objectMap.setObject(File.class, file);
					//
				} // if
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

	}

	private static void importVoice(final Sheet sheet, final ObjectMap _objectMap,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer)
			throws IllegalAccessException, IOException, InvocationTargetException, BaseException {
		//
		final File file = ObjectMap.getObject(_objectMap, File.class);
		//
		final File folder = file != null ? file.getParentFile() : null;
		//
		IntMap<Field> intMap = null;
		//
		ExecutorService es = null;
		//
		try {
			//
			if (sheet != null && sheet.iterator() != null) {
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
				Class<?> type = null;
				//
				List<?> list = null;
				//
				ImportTask it = null;
				//
				NumberFormat percentNumberFormat = null;
				//
				String string, filePath = null;
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
				String voiceId = null;
				//
				ByteConverter byteConverter = null;
				//
				Jakaroma jakaroma = null;
				//
				final CustomProperties customProperties = getCustomProperties(
						getProperties(ObjectMap.getObject(_objectMap, POIXMLDocument.class)));
				//
				final boolean hiraganaKatakanaConversion = BooleanUtils
						.toBooleanDefaultIfNull(getBoolean(customProperties, "hiraganaKatakanaConversion"), false);
				//
				final boolean hiraganaRomajiConversion = BooleanUtils
						.toBooleanDefaultIfNull(getBoolean(customProperties, "hiraganaRomajiConversion"), false);
				//
				ObjectMap objectMap = null;
				//
				ObjectMapper objectMapper = null;
				//
				Double D = null;
				//
				Integer integer = null;
				//
				Boolean b = null;
				//
				final Workbook workbook = sheet.getWorkbook();
				//
				final Integer numberOfSheets = workbook != null ? Integer.valueOf(workbook.getNumberOfSheets()) : null;
				//
				final int maxSheetNameLength = orElse(max(mapToInt(
						map(testAndApply(Objects::nonNull, workbook != null ? workbook.spliterator() : null,
								x -> StreamSupport.stream(x, false), null), VoiceManager::getSheetName),
						StringUtils::length)), 0);
				//
				for (final Row row : sheet) {
					//
					if (row == null || row.iterator() == null) {
						continue;
					} // if
						//
					voice = null;
					//
					for (final Cell cell : row) {
						//
						if (cell == null) {
							continue;
						} // if
							//
						if (first) {
							//
							if (fs == null) {
								//
								fs = FieldUtils.getAllFields(Voice.class);
								//
							} // if
								//
							if ((intMap = ObjectUtils.getIfNull(intMap,
									() -> Reflection.newProxy(IntMap.class, new IH()))) != null) {
								//
								intMap.setObject(cell.getColumnIndex(), orElse(
										findFirst(testAndApply(Objects::nonNull, fs, Arrays::stream, null).filter(
												field -> Objects.equals(getName(field), cell.getStringCellValue()))),
										null));
								//
							} // if
								//
						} else if (intMap != null && intMap.containsKey(columnIndex = cell.getColumnIndex())
								&& (f = intMap.getObject(columnIndex)) != null) {
							//
							f.setAccessible(true);
							//
							if (Objects.equals(type = getType(f), String.class)) {
								//
								if (Objects.equals(cell.getCellType(), CellType.NUMERIC)) {
									//
									string = Double.toString(cell.getNumericCellValue());
									//
								} else {
									//
									string = cell.getStringCellValue();
									//
								} // if
									//
								f.set(voice = ObjectUtils.getIfNull(voice, Voice::new), string);
								//
							} else if (isAssignableFrom(Enum.class, type) && (list = toList(filter(
									testAndApply(Objects::nonNull, getEnumConstants(type), Arrays::stream, null), e -> {
										//
										final String name = name(cast(Enum.class, e));
										//
										final String stringCellValue = cell.getStringCellValue();
										//
										return Objects.equals(name, stringCellValue)
												|| (StringUtils.isNotEmpty(stringCellValue)
														&& StringUtils.startsWithIgnoreCase(name, stringCellValue));
										//
									}))) != null && !list.isEmpty()) {
								//
								if (list.size() == 1) {
									//
									f.set(voice = ObjectUtils.getIfNull(voice, Voice::new), list.get(0));
									//
								} else {
									//
									throw new IllegalStateException("list.size()>1");
									//
								} // if
									//
							} else if (Objects.equals(type, Iterable.class)) {
								//
								f.set(voice = ObjectUtils.getIfNull(voice, Voice::new),
										toList(map(
												stream(getObjectList(objectMapper = ObjectUtils.getIfNull(objectMapper,
														ObjectMapper::new), cell.getStringCellValue())),
												VoiceManager::toString)));
								//
							} else if (Objects.equals(type, Integer.class)) {
								//
								if (Objects.equals(cell.getCellType(), CellType.NUMERIC)
										&& (D = Double.valueOf(cell.getNumericCellValue())) != null) {
									//
									integer = Integer.valueOf(D.intValue());
									//
								} else {
									//
									integer = valueOf(cell.getStringCellValue());
									//
								} // if
									//
								f.set(voice = ObjectUtils.getIfNull(voice, Voice::new), integer);
								//
							} else if (Objects.equals(type, Boolean.class)) {
								//
								if (Objects.equals(cell.getCellType(), CellType.BOOLEAN)) {
									//
									b = cell.getBooleanCellValue();
									//
								} else {
									//
									b = Boolean.valueOf(cell.getStringCellValue());
									//
								} // if
									//
								f.set(voice = ObjectUtils.getIfNull(voice, Voice::new), b);
								//
							} // if
								//
						} // if
							//
					} // for
						//
					objectMap = ObjectUtils.defaultIfNull(copyObjectMap(_objectMap), _objectMap);
					//
					if (voice != null) {
						//
						if (hiraganaKatakanaConversion) {
							//
							setHiraganaOrKatakana(voice);
							//
						} // if
							//
						if (hiraganaRomajiConversion) {
							//
							if (jakaroma == null) {
								//
								jakaroma = ObjectMap.getObject(objectMap, Jakaroma.class);
								//
							} // if
								//
							setRomaji(voice, jakaroma);
							//
						} // if
							//
					} // if
						//
					if (first) {
						//
						first = false;
						//
					} else {
						//
						if ((es = ObjectUtils.getIfNull(es, () -> Executors.newFixedThreadPool(1))) != null) {
							//
							(it = new ImportTask()).sheetCurrentAndTotal = Pair.of(getCurrentSheetIndex(sheet),
									numberOfSheets);
							//
							it.currentSheetName = StringUtils.leftPad(getSheetName(sheet), maxSheetNameLength);
							//
							it.counter = Integer.valueOf(row.getRowNum());
							//
							it.count = Integer.valueOf(sheet.getPhysicalNumberOfRows() - 1);
							//
							it.percentNumberFormat = ObjectUtils.getIfNull(percentNumberFormat,
									() -> new DecimalFormat("#%"));
							//
							if ((it.voice = voice) != null) {
								//
								if (StringUtils.isNotBlank(filePath = voice.getFilePath())) {
									//
									if (!(it.file = new File(filePath)).exists()) {
										//
										it.file = new File(folder, filePath);
										//
									} // if
										//
									if (voiceManager == null) {
										//
										voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
										//
									} // if
										//
									if (mp3Tags == null && voiceManager != null) {
										//
										mp3Tags = voiceManager.mp3Tags;
										//
									} // if
										//
									it.voice.setSource(StringUtils.defaultIfBlank(voice.getSource(), getMp3TagValue(
											it.file, x -> StringUtils.isNotBlank(toString(x)), mp3Tags)));
									//
								} else {
									//
									if ((it.file = File.createTempFile(
											RandomStringUtils.randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH),
											filePath)) != null) {
										//
										if (objectMap != null) {
											//
											objectMap.setObject(File.class, it.file);
											//
										} // if
											//
										if (voiceManager == null) {
											//
											voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
											//
										} // if
											//
										if (voiceManager != null) {
											//
											if (jsSpeechVolume == null && voiceManager != null) {
												//
												jsSpeechVolume = voiceManager.jsSpeechVolume;
												//
											} // if
												//
											if (voiceId == null && voiceManager != null) {
												//
												voiceId = toString(getSelectedItem(
														voiceManager != null ? voiceManager.cbmVoiceId : null));
												//
											} // if
												//
											writeVoiceToFile(objectMap, getText(voice), voiceId
											//
													, getRate(getText(
															voiceManager != null ? voiceManager.tfSpeechRate : null))// rate
													//
													,
													Math.min(Math.max(intValue(
															jsSpeechVolume != null ? jsSpeechVolume.getValue() : null,
															100), 0), 100)// volume
											);
											//
											if (byteConverter == null) {
												//
												byteConverter = ObjectMap.getObject(objectMap, ByteConverter.class);
												//
											} // if
												//
											if (byteConverter != null) {
												//
												FileUtils.writeByteArrayToFile(it.file,
														byteConverter.convert(FileUtils.readFileToByteArray(it.file)));
												//
											} // if
												//
										} // if
											//
										deleteOnExit(it.file);
										//
									} // if
										//
									if (provider == null) {
										//
										provider = ObjectMap.getObject(objectMap, Provider.class);
										//
									} // if
										//
									it.voice.setSource(
											StringUtils.defaultIfBlank(voice.getSource(), getProviderName(provider)));
									//
									try {
										//
										if (speechApi == null) {
											//
											speechApi = ObjectMap.getObject(objectMap, SpeechApi.class);
											//
										} // if
											//
										it.voice.setLanguage(StringUtils.defaultIfBlank(it.voice.getLanguage(),
												convertLanguageCodeToText(
														getVoiceAttribute(speechApi, voiceId, "Language"), 16)));
										//
									} catch (final Error e) {
										//
										if (GraphicsEnvironment.isHeadless()) {
											//
											if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
												LOG.error(getMessage(e), e);
											} else if (e != null) {
												e.printStackTrace();
											} // if
												//
										} else {
											//
											JOptionPane.showMessageDialog(null, getMessage(e));
											//
										} // if
											//
									} // try
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
							es.submit(it);
							//
						} else {
							//
							if (objectMap != null) {
								//
								objectMap.setObject(Voice.class, voice);
								//
								objectMap.setObject(File.class,
										voice != null ? new File(folder, voice.getFilePath()) : folder);
								//
							} // if
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
			} // if
				//
		} finally {
			//
			shutdown(es);
			//
		} // try
			//
	}

	private static Integer getCurrentSheetIndex(final Sheet sheet) {
		//
		Integer currentSheetIndex = null;
		//
		final Workbook workbook = sheet != null ? sheet.getWorkbook() : null;
		//
		if (workbook != null) {
			//
			final int numberOfSheets = workbook.getNumberOfSheets();
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

	private static Boolean getBoolean(final CustomProperties instance, final String name) {
		//
		final CTProperty ctProperty = testAndApply(VoiceManager::contains, instance, name, VoiceManager::getProperty,
				null);
		//
		final Boolean B = ctProperty != null ? ctProperty.getBool() : null;
		//
		Boolean result = null;
		//
		final String lLpwstr = getLpwstr(ctProperty);
		//
		if (StringUtils.isNotBlank(lLpwstr)) {
			//
			result = Boolean.valueOf(lLpwstr);
			//
		} else if (B != null) {
			//
			result = B.booleanValue();
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

	private static void importVoice(final ObjectMap objectMap, final BiConsumer<Voice, String> errorMessageConsumer,
			final BiConsumer<Voice, Throwable> throwableConsumer) {
		//
		final File selectedFile = ObjectMap.getObject(objectMap, File.class);
		//
		final Voice voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		if (selectedFile == null) {
			//
			accept(errorMessageConsumer, voice, "No File Selected");
			//
			return;
			//
		} else if (!selectedFile.exists()) {
			//
			accept(errorMessageConsumer, voice,
					String.format("File \"%1$s\" does not exist", getAbsolutePath(selectedFile)));
			//
			return;
			//
		} else if (!isFile(selectedFile)) {
			//
			accept(errorMessageConsumer, voice, "Not A Regular File Selected");
			//
			return;
			//
		} else if (selectedFile.length() == 0) {
			//
			accept(errorMessageConsumer, voice, "Empty File Selected");
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
			final MessageDigest md = MessageDigest.getInstance("SHA-512");
			//
			final String messageDigestAlgorithm = md != null ? md.getAlgorithm() : null;
			//
			Long length = selectedFile != null ? Long.valueOf(selectedFile.length()) : null;
			//
			String fileDigest = Hex.encodeHexString(digest(md, FileUtils.readFileToByteArray(selectedFile)));
			//
			final String voiceFolder = ObjectMap.getObject(objectMap, String.class);
			//
			if (voiceOld == null
					|| !Objects.equals(voiceOld.getFileLength(),
							selectedFile != null ? Long.valueOf(selectedFile.length()) : null)
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
					file = new File(voiceFolder,
							filePath = toString(fileName.insert(StringUtils.lastIndexOf(fileName, '.') + 1,
									RandomStringUtils.randomAlphabetic(2) + ".")));
					//
				} // if
					//
				FileUtils.copyFile(selectedFile, file);
				//
				length = Long.valueOf(file.length());
				//
				fileDigest = Hex.encodeHexString(digest(md, FileUtils.readFileToByteArray(file)));
				//
			} else {
				//
				final File file = new File(voiceFolder, voiceOld.getFilePath());
				//
				if (!file.exists()) {
					//
					FileUtils.copyFile(selectedFile, file);
					//
				} // if
					//
				filePath = voiceOld.getFilePath();
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
		} catch (IOException | NoSuchAlgorithmException e) {
			//
			accept(throwableConsumer, voice, e);
			//
		} // try
			//
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> objects = null;

		private Map<Object, Object> booleans = null;

		private Map<Object, Object> intMapObjects = null;

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

		public Map<Object, Object> getIntMapObjects() {
			if (intMapObjects == null) {
				intMapObjects = new LinkedHashMap<>();
			}
			return intMapObjects;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
			//
			if (proxy instanceof ObjectMap) {
				//
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!containsKey(getObjects(), key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found", key));
						//
					} // if
						//
					return getObjects().get(key);
					//
				} else if (Objects.equals(methodName, "containsObject") && args != null && args.length > 0) {
					//
					return Boolean.valueOf(containsKey(getObjects(), args[0]));
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					put(getObjects(), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof BooleanMap) {
				//
				if (Objects.equals(methodName, "getBoolean") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!containsKey(getBooleans(), key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found", key));
						//
					} // if
						//
					return getBooleans().get(key);
					//
				} else if (Objects.equals(methodName, "setBoolean") && args != null && args.length > 1) {
					//
					put(getBooleans(), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof IntMap) {
				//
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!containsKey(getIntMapObjects(), key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found", key));
						//
					} // if
						//
					return getIntMapObjects().get(key);
					//
				} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
					//
					return containsKey(getIntMapObjects(), args[0]);
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					put(getIntMapObjects(), args[0], args[1]);
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
		voice.setSource(getText(instance.tfSource));
		//
		voice.setRomaji(getText(instance.tfRomaji));
		//
		voice.setHiragana(getText(instance.tfHiragana));
		//
		voice.setKatakana(getText(instance.tfKatakana));
		//
		voice.setYomi(cast(Yomi.class, getSelectedItem(instance.cbmYomi)));
		//
		voice.setListNames(toList(
				map(stream(getObjectList(objectMapper, getText(instance.tfListNames))), VoiceManager::toString)));
		//
		voice.setJlptLevel(toString(getSelectedItem(instance.cbmJlptLevel)));
		//
		voice.setIpaSymbol(getText(instance.tfIpaSymbol));
		//
		voice.setIsKanji(cast(Boolean.class, getSelectedItem(instance.cbmIsKanji)));
		//
		voice.setGaKuNenBeTsuKanJi(toString(getSelectedItem(instance.cbmGaKuNenBeTsuKanJi)));
		//
		return voice;
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static String getMessage(final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(getClass(instance)))) {
			instance.forEach(action);
		} // if
			//
	}

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(getClass(instance)))) {
			instance.forEach(action);
		} // if
			//
	}

	private static class ExportTask implements Runnable {

		private static String FILE_NAME_PREFIX_PADDING = orElse(
				min(stream(IteratorUtils.toList(new RgxGen("\\d").iterateUnique())), StringUtils::compare), null);

		private Integer counter, count, ordinalPositionDigit;

		private Voice voice = null;

		private Map<String, String> outputFolderFileNameExpressions = null;

		private EvaluationContext evaluationContext = null;

		private ExpressionParser expressionParser = null;

		private NumberFormat percentNumberFormat = null;

		private boolean overMp3Title = false, ordinalPositionAsFileNamePrefix = false;

		private VoiceManager voiceManager = null;

		private Fraction pharse = null;

		private String ordinalPositionFileNamePrefix = null;

		@Override
		public void run() {
			//
			try {
				//
				final String filePath = voice != null ? voice.getFilePath() : null;
				//
				if (filePath == null || outputFolderFileNameExpressions == null
						|| outputFolderFileNameExpressions.entrySet() == null) {
					//
					return;
					//
				} // if
					//
				setVariable(evaluationContext, "voice", voice);
				//
				String key, value, ordinalPositionString, voiceFolder = null;
				//
				StringBuilder fileName = null;
				//
				File fileSource, fileDestination, folder = null;
				//
				JProgressBar progressBar = null;
				//
				final String outputFolder = voiceManager != null ? voiceManager.outputFolder : null;
				//
				for (final Entry<String, String> folderFileNamePattern : outputFolderFileNameExpressions.entrySet()) {
					//
					if (voiceFolder == null && voiceManager != null) {
						//
						voiceFolder = voiceManager != null ? voiceManager.voiceFolder : null;
						//
					} // if
						//
					if (folderFileNamePattern == null || (key = getKey(folderFileNamePattern)) == null
							|| StringUtils.isBlank(value = getValue(folderFileNamePattern))
							|| !(fileSource = voiceFolder != null ? new File(voiceFolder, filePath)
									: new File(filePath)).exists()) {
						continue;
					} // if
						//
					clear(fileName = ObjectUtils.getIfNull(fileName, StringBuilder::new));
					//
					if (fileName != null) {
						//
						if (ordinalPositionAsFileNamePrefix && StringUtils.isNotBlank(
								ordinalPositionString = VoiceManager.toString(voice.getOrdinalPosition()))) {
							//
							fileName.append(ordinalPositionDigit != null
									? StringUtils.leftPad(ordinalPositionString, ordinalPositionDigit.intValue(),
											StringUtils.defaultString(FILE_NAME_PREFIX_PADDING))
									: ordinalPositionString);
							//
							fileName.append(StringUtils.defaultIfBlank(ordinalPositionFileNamePrefix, ""));
							//
						} // if
							//
						fileName.append(VoiceManager.toString(getValue(expressionParser, evaluationContext, value)));
						//
					} // if
						//
					FileUtils.copyFile(fileSource,
							fileDestination = new File((folder = ObjectUtils.getIfNull(folder,
									() -> outputFolder != null ? new File(outputFolder) : null)) != null
											? new File(folder, key)
											: new File(key),
									VoiceManager.toString(fileName)));
					//
					if (overMp3Title) {
						//
						setMp3Title(fileDestination);
						//
					} // if
						//
				} // for
					//
				if (counter != null) {
					//
					if (progressBar == null && voiceManager != null) {
						//
						progressBar = voiceManager.progressBarExport;
						//
					} // if
						//
					setValue(progressBar, counter.intValue());
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
					} // if
						//
				} // if
					//
				showPharse(voiceManager, pharse);
				//
			} catch (final IOException | BaseException e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
						LOG.error(getMessage(e), e);
					} else if (e != null) {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} // try
				//
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

		private static void setMp3Title(final File file) throws IOException, BaseException {
			//
			final String fileExtension = getFileExtension(
					testAndApply(VoiceManager::isFile, file, new ContentInfoUtil()::findMatch, null));
			//
			if (Objects.equals("mp3", fileExtension)) {
				//
				final File tempFile = File
						.createTempFile(RandomStringUtils.randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null);
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
					if (StringUtils.isNotBlank(fileName)) {
						//
						if (StringUtils.endsWith(titleNew = StringUtils.substringBeforeLast(fileName, fileExtension),
								".")) {
							//
							titleNew = StringUtils.substringBeforeLast(titleNew, ".");
							//
						} // if
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

	}

	private static interface BooleanMap {

		boolean getBoolean(final String key);

		void setBoolean(final String key, final boolean value);

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
		try {
			//
			int size = voices != null ? voices.size() : 0;
			//
			Integer numberOfOrdinalPositionDigit = Integer.valueOf(StringUtils.length(toString(orElse(
					max(filter(map(stream(voices), x -> x != null ? x.getOrdinalPosition() : null), Objects::nonNull),
							ObjectUtils::compare),
					null))));
			//
			Fraction pharse = null;
			//
			String ordinalPositionFileNamePrefix = null;
			//
			for (int i = 0; i < size; i++) {
				//
				if ((es = ObjectUtils.getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
					//
					continue;
					//
				} // if
					//
				(et = new ExportTask()).voiceManager = voiceManager;
				//
				et.counter = Integer.valueOf(i + 1);
				//
				et.pharse = ObjectUtils.getIfNull(pharse, () -> Fraction.getFraction(1, 3));
				//
				et.count = size;
				//
				et.percentNumberFormat = ObjectUtils.getIfNull(percentNumberFormat, () -> new DecimalFormat("#%"));
				//
				et.evaluationContext = evaluationContext = ObjectUtils.getIfNull(evaluationContext,
						StandardEvaluationContext::new);
				//
				et.expressionParser = expressionParser = ObjectUtils.getIfNull(expressionParser,
						SpelExpressionParser::new);
				//
				et.outputFolderFileNameExpressions = outputFolderFileNameExpressions;
				//
				et.voice = voices.get(i);
				//
				et.ordinalPositionDigit = numberOfOrdinalPositionDigit;
				//
				et.ordinalPositionFileNamePrefix = ordinalPositionFileNamePrefix = ObjectUtils.getIfNull(
						ordinalPositionFileNamePrefix,
						() -> getText(voiceManager != null ? voiceManager.tfOrdinalPositionFileNamePrefix : null));
				//
				if (booleanMap != null) {
					//
					et.overMp3Title = booleanMap.getBoolean("overMp3Title");
					//
					et.ordinalPositionAsFileNamePrefix = booleanMap.getBoolean("ordinalPositionAsFileNamePrefix");
					//
				} // if
					//
				es.submit(et);
				//
			} // for
				//
			Voice v = null;
			//
			Iterable<String> listNames = null;
			//
			Multimap<String, Voice> multimap = null;
			//
			for (int i = 0; voices != null && i < voices.size(); i++) {
				//
				if ((v = voices.get(i)) == null || (listNames = v.getListNames()) == null) {
					continue;
				} // if
					//
				for (final String listName : listNames) {
					//
					if (StringUtils.isEmpty(listName)) {
						continue;
					} // if
						//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), listName, v);
					//
				} // for
					//
			} // for
				//
			Collection<Entry<String, Voice>> entries = entries(multimap);
			//
			if (entries != null) {
				//
				int coutner = 0;
				//
				size = multimap.size();
				//
				numberOfOrdinalPositionDigit = Integer
						.valueOf(
								StringUtils.length(toString(orElse(max(
										filter(map(stream(multimap.values()),
												x -> x != null ? x.getOrdinalPosition() : null), Objects::nonNull),
										ObjectUtils::compare), null))));
				//
				if (pharse != null) {
					//
					pharse = Fraction.getFraction(pharse.getNumerator() + 1, pharse.getDenominator());
					//
				} // if
					//
				if (objectMap != null) {
					//
					objectMap.setObject(Fraction.class,
							pharse = ObjectUtils.getIfNull(pharse, () -> Fraction.getFraction(2, 3)));
					//
				} // if
					//
				for (final Entry<String, Voice> en : entries) {
					//
					if (en == null) {
						//
						continue;
						//
					} // if
						//
					if ((es = ObjectUtils.getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
						//
						continue;
						//
					} // if
						//
					if (objectMap != null) {
						//
						if (!objectMap.containsObject(NumberFormat.class)) {
							//
							objectMap.setObject(NumberFormat.class, percentNumberFormat = ObjectUtils
									.getIfNull(percentNumberFormat, () -> new DecimalFormat("#%")));
							//
						} // if
							//
						if (!objectMap.containsObject(EvaluationContext.class)) {
							//
							objectMap.setObject(EvaluationContext.class, evaluationContext = ObjectUtils
									.getIfNull(evaluationContext, StandardEvaluationContext::new));
							//
						} // if
							//
						if (!objectMap.containsObject(ExpressionParser.class)) {
							//
							objectMap.setObject(ExpressionParser.class, expressionParser = ObjectUtils
									.getIfNull(expressionParser, SpelExpressionParser::new));
							//
						} // if
							//
						objectMap.setObject(Voice.class, getValue(en));
						//
					} // if
						//
					es.submit(createExportTask(objectMap, size, Integer.valueOf(++coutner),
							numberOfOrdinalPositionDigit, Collections.singletonMap(getKey(en),
									"(#voice.text+'('+#voice.romaji+').'+#voice.fileExtension)")));
					//
				} // for
					//
			} // if
				//
				// JLPT
				//
			if (booleanMap != null && booleanMap.getBoolean("jlptAsFolder")) {
				//
				clear(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create));
				//
				String jlptLevel = null;
				//
				for (int i = 0; voices != null && i < voices.size(); i++) {
					//
					if ((v = voices.get(i)) == null || (jlptLevel = v.getJlptLevel()) == null) {
						//
						continue;
						//
					} // if
						//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), jlptLevel, v);
					//
				} // for
					//
				if (pharse != null) {
					//
					pharse = Fraction.getFraction(pharse.getNumerator() + 1, pharse.getDenominator());
					//
				} // if
					//
				if (objectMap != null) {
					//
					objectMap.setObject(Fraction.class,
							pharse = ObjectUtils.getIfNull(pharse, () -> Fraction.getFraction(3, 3)));
					//
				} // if
					//
				String jlptFolderNamePrefix = null;
				//
				StringBuilder folder = null;
				//
				if ((entries = entries(multimap)) != null) {
					//
					int coutner = 0;
					//
					size = multimap.size();
					//
					numberOfOrdinalPositionDigit = Integer
							.valueOf(StringUtils.length(toString(orElse(max(
									filter(map(stream(multimap.values()),
											x -> x != null ? x.getOrdinalPosition() : null), Objects::nonNull),
									ObjectUtils::compare), null))));
					//
					for (final Entry<String, Voice> en : multimap.entries()) {
						//
						if (en == null) {
							//
							continue;
							//
						} // if
							//
						if ((es = ObjectUtils.getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
							//
							continue;
							//
						} // if
							//
						if (objectMap != null) {
							//
							if (!objectMap.containsObject(NumberFormat.class)) {
								//
								objectMap.setObject(NumberFormat.class, percentNumberFormat = ObjectUtils
										.getIfNull(percentNumberFormat, () -> new DecimalFormat("#%")));
								//
							} // if
								//
							if (!objectMap.containsObject(EvaluationContext.class)) {
								//
								objectMap.setObject(EvaluationContext.class, evaluationContext = ObjectUtils
										.getIfNull(evaluationContext, StandardEvaluationContext::new));
								//
							} // if
								//
							if (!objectMap.containsObject(ExpressionParser.class)) {
								//
								objectMap.setObject(ExpressionParser.class, expressionParser = ObjectUtils
										.getIfNull(expressionParser, SpelExpressionParser::new));
								//
							} // if
								//
							objectMap.setObject(Voice.class, getValue(en));
							//
						} // if
							//
						if (jlptFolderNamePrefix == null && voiceManager != null) {
							//
							jlptFolderNamePrefix = getText(voiceManager.tfJlptFolderNamePrefix);
							//
						} // if
							//
						clear(folder = ObjectUtils.getIfNull(folder, StringBuilder::new));
						//
						if (folder != null) {
							//
							folder.append(StringUtils.defaultIfBlank(jlptFolderNamePrefix, ""));
							//
							folder.append(getKey(en));
							//
						} // if
							//
						es.submit(createExportTask(objectMap, size, Integer.valueOf(++coutner),
								numberOfOrdinalPositionDigit, Collections.singletonMap(toString(folder),
										"(#voice.text+'('+#voice.romaji+').'+#voice.fileExtension)")));
						//
					} // for
						//
				} // if
					//
			} // if
				//
		} finally {
			//
			shutdown(es);
			//
		} // try
			//
	}

	private static void clear(final Multimap<?, ?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static void clear(final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
	}

	private static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static ExportTask createExportTask(final ObjectMap objectMap, final Integer size, final Integer counter,
			final Integer numberOfOrdinalPositionDigit, final Map<String, String> outputFolderFileNameExpressions) {
		//
		final ExportTask et = new ExportTask();
		//
		et.pharse = ObjectMap.getObject(objectMap, Fraction.class);
		//
		et.counter = counter;
		//
		et.count = size;
		//
		et.percentNumberFormat = ObjectMap.getObject(objectMap, NumberFormat.class);
		//
		et.evaluationContext = ObjectMap.getObject(objectMap, EvaluationContext.class);
		//
		et.expressionParser = ObjectMap.getObject(objectMap, ExpressionParser.class);
		//
		et.outputFolderFileNameExpressions = outputFolderFileNameExpressions;
		//
		et.voice = ObjectMap.getObject(objectMap, Voice.class);
		//
		et.ordinalPositionDigit = numberOfOrdinalPositionDigit;
		//
		final VoiceManager voiceManager = ObjectMap.getObject(objectMap, VoiceManager.class);
		//
		et.ordinalPositionFileNamePrefix = getText(
				(et.voiceManager = voiceManager) != null ? voiceManager.tfOrdinalPositionFileNamePrefix : null);
		//
		final BooleanMap booleanMap = ObjectMap.getObject(objectMap, BooleanMap.class);
		//
		if (booleanMap != null) {
			//
			et.overMp3Title = booleanMap.getBoolean("overMp3Title");
			//
			et.ordinalPositionAsFileNamePrefix = booleanMap.getBoolean("ordinalPositionAsFileNamePrefix");
			//
		} // if
			//
		return et;
		//
	}

	private static Workbook createWorkbook(final Pair<String, String> columnNames, final Multimap<?, ?> multimap) {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		if (multimap != null && multimap.entries() != null) {
			//
			for (final Entry<?, ?> en : multimap.entries()) {
				//
				if (en == null) {
					//
					continue;
					//
				} // if
					//
				if (sheet == null) {
					//
					sheet = createSheet(workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
					//
				} // if
					//
					// header
					//
				if (sheet != null && sheet.getLastRowNum() < 0) {
					//
					if ((row = sheet.createRow(sheet.getLastRowNum() + 1)) == null) {
						//
						continue;
						//
					} // if
						//
					setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), getKey(columnNames));
					//
					setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), getValue(columnNames));
					//
				} // if
					//
					// content
					//
				if ((row = sheet.createRow(sheet.getLastRowNum() + 1)) == null) {
					//
					continue;
					//
				} // if
					//
				setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), toString(getKey(en)));
				//
				setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), toString(getValue(en)));
				//
			} // for
				//
		} // if
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

	private static Workbook createWorkbook(final List<Voice> voices)
			throws IllegalAccessException, InvocationTargetException {
		//
		Voice voice = null;
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		Cell cell = null;
		//
		Field[] fs = null;
		//
		Field f = null;
		//
		Object value = null;
		//
		final Class<?> dateFormatClass = forName("domain.Voice$DateFormat");
		//
		final Class<?> dataFormatClass = forName("domain.Voice$DataFormat");
		//
		final Class<?> spreadsheetColumnClass = forName("domain.Voice$SpreadsheetColumn");
		//
		Annotation a = null;
		//
		Method m = null;
		//
		String[] fieldOrder = getFieldOrder();
		//
		CellStyle cellStyle = null;
		//
		short dataFormatIndex;
		//
		for (int i = 0; voices != null && i < voices.size(); i++) {
			//
			if ((voice = voices.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (sheet == null) {
				//
				sheet = createSheet(workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
				//
			} // if
				//
			if ((fs = ObjectUtils.getIfNull(fs, () -> FieldUtils.getAllFields(Voice.class))) != null) {
				//
				Arrays.sort(fs, (x, y) -> {
					//
					return Integer.compare(ArrayUtils.indexOf(fieldOrder, getName(x)),
							ArrayUtils.indexOf(fieldOrder, getName(y)));
					//
				});
				//
			} // if
				//
				// header
				//
			if (sheet.getLastRowNum() < 0) {
				//
				if ((row = sheet.createRow(0)) == null) {
					continue;
				} // if
					//
				for (int j = 0; fs != null && j < fs.length; j++) {
					//
					setCellValue(createCell(row, j), getColumnName(spreadsheetColumnClass, fs[j]));
					//
				} // for
					//
			} // if
				//
				// content
				//
			if ((row = sheet.createRow(sheet.getLastRowNum() + 1)) == null) {
				continue;
			} // if
				//
			for (int j = 0; fs != null && j < fs.length; j++) {
				//
				if ((f = fs[j]) == null || (cell = createCell(row, j)) == null) {
					continue;
				} // if
					//
				f.setAccessible(true);
				//
				if ((value = f.get(voice)) instanceof Number) {
					//
					if ((m = orElse(
							findFirst(
									filter(testAndApply(Objects::nonNull,
											getDeclaredMethods(annotationType(a = orElse(findFirst(filter(
													testAndApply(Objects::nonNull, getDeclaredAnnotations(f),
															Arrays::stream, null),
													x -> Objects.equals(annotationType(x), dataFormatClass))), null))),
											Arrays::stream, null), x -> Objects.equals(getName(x), "value"))),
							null)) != null && (cellStyle = workbook.createCellStyle()) != null) {
						//
						m.setAccessible(true);
						//
						if ((dataFormatIndex = HSSFDataFormat.getBuiltinFormat(toString(invoke(m, a)))) >= 0) {
							//
							cellStyle.setDataFormat(dataFormatIndex);
							//
							cell.setCellStyle(cellStyle);
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
							findFirst(
									filter(testAndApply(Objects::nonNull,
											getDeclaredMethods(annotationType(a = orElse(findFirst(filter(
													testAndApply(Objects::nonNull, getDeclaredAnnotations(f),
															Arrays::stream, null),
													x -> Objects.equals(annotationType(x), dateFormatClass))), null))),
											Arrays::stream, null), x -> Objects.equals(getName(x), "value"))),
							null)) != null) {
						//
						m.setAccessible(true);
						//
						setCellValue(cell, new SimpleDateFormat(toString(invoke(m, a))).format(value));
						//
					} else {
						//
						setCellValue(cell, toString(value));
						//
					} // if
						//
				} else {
					//
					setCellValue(cell, toString(value));
					//
				} // if
					//
			} // for
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
		return workbook;
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
			final Annotation annotation = size == 1 ? annotations.get(0) : null;
			//
			if (size == 1) {
				//
				final List<Method> ms = toList(filter(
						testAndApply(Objects::nonNull, getDeclaredMethods(getClass(annotation)), Arrays::stream, null),
						m -> Objects.equals(getName(m), "value")));
				//
				final Method m = (size = CollectionUtils.size(ms)) == 1 ? ms.get(0) : null;
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
				filter(Arrays.stream(getDeclaredMethods(annotationType(a))), z -> Objects.equals(getName(z), "value"))),
				null);
		//
		if (method != null) {
			method.setAccessible(true);
		} // if
			//
		String[] orders = null;
		//
		try {
			//
			orders = cast(String[].class, invoke(method, a));
			//
		} catch (final IllegalAccessException e) {
			// =
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			final Throwable rootCause = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
					targetException, ExceptionUtils.getRootCause(e), e);
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null && !LoggerUtil.isNOPLogger(LOG)) {
					LOG.error(getMessage(rootCause), rootCause);
				} else if (rootCause != null) {
					rootCause.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(rootCause));
				//
			} // if
				//
		} // try
			//
		final List<String> fieldNames = toList(
				map(Arrays.stream(FieldUtils.getAllFields(Voice.class)), VoiceManager::getName));
		//
		String fieldName = null;
		//
		for (int i = 0; fieldNames != null && i < fieldNames.size(); i++) {
			//
			if (!ArrayUtils.contains(orders, fieldName = fieldNames.get(i))) {
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

	private static String getSimpleName(final Class<?> instance) {
		return instance != null ? instance.getSimpleName() : null;
	}

	private static String getName(final Member instance) {
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

	private static void setVariable(final EvaluationContext instance, final String name, final Object value) {
		if (instance != null) {
			instance.setVariable(name, value);
		}
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
				if (voice != null) {
					//
					voice.setId(voiceOld.getId());
					//
					voice.setUpdateTs(new Date());
					//
				} // if
					//
				instance.updateVoice(voice);
				//
			} else {
				//
				if (voice != null) {
					//
					voice.setCreateTs(new Date());
					//
				} // if
					//
				instance.insertVoice(voice);
				//
			} // if
				//
				// voice_list
				//
			final Integer voiceId = voice != null ? voice.getId() : null;
			//
			instance.deleteVoiceListByVoiceId(voiceId);
			//
			final Iterable<String> listNames = voice != null ? voice.getListNames() : null;
			//
			if (listNames != null && listNames.iterator() != null) {
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
		if (ci == null) {
			//
			return null;
			//
		} // if
			//
		final String message = ci.getMessage();
		//
		if (or(x -> matches(matcher(x, message)), PATTERN_CONTENT_INFO_MESSAGE_MP3_1,
				PATTERN_CONTENT_INFO_MESSAGE_MP3_2, PATTERN_CONTENT_INFO_MESSAGE_MP3_3)) {
			//
			return "mp3";
			//
		} // if
			//
		final String name = ci.getName();
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
			if ((c = cs[i]) == null || (d = c.getPreferredSize()) == null) {
				continue;
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = c != null ? c.getPreferredSize() : null;
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	public static void main(final String[] args) {
		//
		final VoiceManager instance = new VoiceManager();
		//
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		instance.setLayout(new MigLayout());
		//
		instance.setTitle("Voice Manager");
		//
//		instance.init();
		//
		instance.pack();
		//
		instance.setVisible(true);
		//
	}

}