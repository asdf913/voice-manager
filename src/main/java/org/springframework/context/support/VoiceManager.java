package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
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
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
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

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
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
import com.google.common.collect.Range;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.mariten.kanatools.KanaConverter;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;

import domain.Voice;
import domain.Voice.Yomi;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.javaflacencoder.AudioStreamEncoder;
import net.sourceforge.javaflacencoder.FLACEncoder;
import net.sourceforge.javaflacencoder.FLACStreamOutputStream;
import net.sourceforge.javaflacencoder.StreamConfiguration;

public class VoiceManager extends JFrame implements ActionListener, ItemListener, ChangeListener, KeyListener,
		EnvironmentAware, BeanFactoryPostProcessor {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static final String WRAP = "wrap";

	private PropertyResolver propertyResolver = null;

	private JTextComponent tfFolder, tfFile, tfFileLength, tfFileDigest, tfText, tfHiragana, tfKatakana, tfRomaji,
			tfSpeechRate, tfSource, tfProviderName, tfProviderVersion, tfProviderPlatform, tfSpeechLanguage, tfLanguage,
			tfSpeechVolume, tfCurrentProcessingSheetName, tfCurrentProcessingVoice, tfListNames = null;

	private ComboBoxModel<Yomi> cbmYomi = null;

	private ComboBoxModel<String> cbmVoiceId = null;

	private AbstractButton btnSpeak, cbWriteVoiceAsFlac, btnWriteVoice, btnConvertToRomaji, btnConvertToKatakana,
			btnCopyRomaji, btnCopyHiragana, btnCopyKatakana, cbUseTtsVoice, cbConvertToFlac, btnExecute,
			btnImportFileTemplate, btnImport, cbOverMp3Title, btnExport = null;

	private JProgressBar progressBar = null;

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

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
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
						LinkedHashMap::new), toString(entry.getKey()), toString(entry.getValue()));
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
		// Language
		//
		add(new JLabel("Language"));
		//
		add(tfLanguage = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.language")),
				String.format("spanx %1$s,growx", 5));
		//
		// source
		//
		add(new JLabel("Source"));
		//
		add(tfSource = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.source")),
				String.format("spanx %1$s,growx,%2$s", 5, WRAP));
		//
		add(new JLabel("Text"));
		//
		String span = String.format("spanx %1$s,growx", 11);
		//
		add(tfText = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.text")), span);
		//
		add(btnConvertToRomaji = new JButton("Convert To Romaji"), String.format("span %1$s,%2$s", 3, WRAP));
		//
		// Provider
		//
		add(new JLabel("Provider"));
		//
		final Provider provider = cast(Provider.class, speechApi);
		//
		add(tfProviderName = new JTextField(getProviderName(provider)), String.format("spanx %1$s,growx", 9));
		//
		add(tfProviderVersion = new JTextField(getProviderVersion(provider)), String.format("width %1$s", 90));
		//
		try {
			//
			add(tfProviderPlatform = new JTextField(provider != null ? provider.getProviderPlatform() : null),
					String.format("width %1$s,%2$s", 50, WRAP));
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
			// Voice Id
			//
		add(new JLabel("Voice Id"));
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
			add(jcbVoiceId, String.format("span %1$s,growx", 9));
			//
			add(tfSpeechLanguage = new JTextField(), String.format("width %1$s,span %2$s,%3$s", 147, 2, WRAP));
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
					cbmVoiceId.setSelectedItem(temp.get(0));
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
		add(new JLabel("Speech Rate"));
		//
		add(tfSpeechRate = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.speechRate")),
				String.format("span %1$s,growx,%2$s", 11, WRAP));
		//
		// Speech Volume
		//
		add(new JLabel("Speech Volume"));
		//
		final Range<Integer> speechVolumeRange = createVolumnRange(getClass(speechApi));
		//
		final Integer upperEnpoint = speechVolumeRange != null && speechVolumeRange.hasUpperBound()
				? speechVolumeRange.upperEndpoint()
				: null;
		//
		add(jsSpeechVolume = new JSlider(intValue(
				speechVolumeRange != null && speechVolumeRange.hasLowerBound() ? speechVolumeRange.lowerEndpoint()
						: null,
				0), intValue(upperEnpoint, 100)), String.format("span %1$s,growx", 10));
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
		add(tfSpeechVolume = new JTextField(), String.format("width %1$s", 90));
		//
		add(btnSpeak = new JButton("Speak"));
		//
		add(cbWriteVoiceAsFlac = new JCheckBox("Flac"));
		//
		cbWriteVoiceAsFlac.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.writeVoiceAsFlac")));
		//
		add(btnWriteVoice = new JButton("Write"), WRAP);
		//
		// yomi
		//
		add(new JLabel("Yomi"));
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
		add(jcbYomi);
		//
		add(new JLabel("List(s)"));
		//
		final String tags = getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.listNames");
		//
		add(tfListNames = new JTextField(tags), String.format("growx,span %1$s", 3));
		//
		tfListNames.addKeyListener(this);
		//
		add(jlListNames = new JLabel(), String.format("growx,span %1$s", 5));
		//
		add(jlListNameCount = new JLabel(), String.format("growx,%1$s", WRAP));
		//
		if (StringUtils.isNotBlank(tags)) {
			//
			keyReleased(new KeyEvent(tfListNames, 0, 0, 0, 0, ' '));
			//
		} // if
			//
		final List<Yomi> yomiList = toList(
				filter(testAndApply(Objects::nonNull, yomis, Arrays::stream, null), y -> Objects.equals(name(y),
						getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.yomi"))));
		//
		final int size = yomiList != null ? yomiList.size() : 0;
		//
		if (size == 1) {
			//
			cbmYomi.setSelectedItem(yomiList.get(0));
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		add(new JLabel("Romaji"));
		//
		add(tfRomaji = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.romaji")),
				span = String.format("spanx %1$s,growx", 10));
		//
		add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		add(new JLabel("Hiragana"));
		//
		add(tfHiragana = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.hiragana")),
				String.format("spanx %1$s,growx", 2));
		//
		add(btnCopyHiragana = new JButton("Copy"));
		//
		add(btnConvertToKatakana = new JButton("Convert"));
		//
		add(new JLabel("Katakana"));
		//
		add(tfKatakana = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.katakana")),
				String.format("spanx %1$s,growx", 5));
		//
		add(btnCopyKatakana = new JButton("Copy"), WRAP);
		//
		add(new JLabel());
		//
		add(cbUseTtsVoice = new JCheckBox("TTS Voice"));
		//
		cbUseTtsVoice.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.useTtsVoice")));
		//
		add(cbConvertToFlac = new JCheckBox("Convert To Flac"), String.format("span %1$s", 2));
		//
		cbConvertToFlac.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.convertToFlac")));
		//
		add(btnExecute = new JButton("Execute"), WRAP);
		//
		add(new JLabel("Export"));
		//
		add(cbOverMp3Title = new JCheckBox("Over Mp3 Title"));
		//
		cbOverMp3Title.setSelected(Boolean.parseBoolean(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.overMp3Title")));
		//
		add(btnExport = new JButton("Export"), WRAP);
		//
		add(new JLabel("Import"));
		//
		add(btnImport = new JButton("Import"), WRAP);
		//
		add(new JLabel(""));
		//
		add(btnImportFileTemplate = new JButton("Import File Template"), String.format("span %1$s,%2$s", 3, WRAP));
		//
		add(progressBar = new JProgressBar(), String.format("span %1$s,growx,%2$s", 15, WRAP));
		//
		progressBar.setStringPainted(true);
		//
		add(new JLabel("Current Processing Sheet"), String.format("span %1$s", 2));
		//
		add(tfCurrentProcessingSheetName = new JTextField(), String.format("span %1$s,growx", 6));
		//
		add(new JLabel("Voice"));
		//
		add(tfCurrentProcessingVoice = new JTextField(), String.format("span %1$s,growx,%2$s", 6, WRAP));
		//
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		add(new JLabel("Folder"));
		//
		String wrap = String.format("span %1$s,growx,%2$s", 11, WRAP);
		//
		add(tfFolder = new JTextField(folder != null ? folder.getAbsolutePath() : null),
				wrap = String.format("span %1$s,growx,%2$s", 14, WRAP));
		//
		add(new JLabel("File"));
		//
		add(tfFile = new JTextField(), String.format("span %1$s,growx", 7));
		//
		add(new JLabel("Length"));
		//
		add(tfFileLength = new JTextField(), String.format("span %1$s,growx,%2$s", 6, WRAP));
		//
		add(new JLabel("File Digest"));
		//
		add(tfFileDigest = new JTextField(), wrap);
		//
		add(new JLabel("Import Result"));
		//
		add(new JScrollPane(new JTable(tmImportResult = new DefaultTableModel(
				new Object[] { "Number Of Sheet Processed", "Number of Voice Processed" }, 0))), wrap);
		//
		add(new JLabel("Import Exception"));
		//
		add(new JScrollPane(new JTable(
				tmImportException = new DefaultTableModel(new Object[] { "Text", "Romaji", "Exception" }, 0))), wrap);
		//
		setEditable(false, tfSpeechLanguage, tfProviderName, tfProviderVersion, tfProviderPlatform, tfFolder, tfFile,
				tfFileLength, tfFileDigest, tfSpeechVolume, tfCurrentProcessingSheetName, tfCurrentProcessingVoice);
		//
		addActionListener(this, btnSpeak, btnWriteVoice, btnExecute, btnConvertToRomaji, btnConvertToKatakana,
				btnCopyRomaji, btnCopyHiragana, btnCopyKatakana, btnImportFileTemplate, btnImport, btnExport);
		//
		setPreferredWidth(intValue(
				orElse(max(map(Stream.of(tfFolder, tfFile, tfFileLength, tfFileDigest, tfText, tfHiragana, tfKatakana,
						tfRomaji), VoiceManager::getPreferredWidth), Comparator.comparing(x -> intValue(x, 0))), null),
				0) - intValue(getPreferredWidth(btnConvertToRomaji), 0), tfText, tfRomaji, tfHiragana);
		//
		setEnabled(btnExecute, folder != null && folder.exists() && folder.isDirectory());
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
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), pair.getKey(), pair.getValue());
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
		Object object = null;
		//
		Integer minValue = null;
		//
		if ((object = testAndApply(VoiceManager::containsKey, map, "MinValue", MapUtils::getObject,
				null)) instanceof Integer) {
			minValue = (Integer) object;
		} else if (object instanceof Number) {
			minValue = Integer.valueOf(((Number) object).intValue());
		} else {
			minValue = valueOf(toString(object));
		} // if
			//
		Integer maxValue = null;
		//
		if ((object = testAndApply(VoiceManager::containsKey, map, "MaxValue", MapUtils::getObject,
				null)) instanceof Integer) {
			maxValue = (Integer) object;
		} else if (object instanceof Number) {
			maxValue = Integer.valueOf(((Number) object).intValue());
		} else {
			maxValue = valueOf(toString(object));
		} // if
			//
		return createRange(minValue, maxValue);
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

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || comparator != null)
				? instance.max(comparator)
				: null;
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T other) {
		return instance != null ? instance.orElse(other) : null;
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
				speechApi.speak(getText(tfText), toString(getSelectedItem(cbmVoiceId))
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
				writeVoiceToFile(objectMap, getText(tfText), toString(getSelectedItem(cbmVoiceId))
				//
						, intValue(getRate(getText(tfSpeechRate)), 0)// rate
						//
						,
						Math.min(Math.max(intValue(jsSpeechVolume != null ? jsSpeechVolume.getValue() : null, 100), 0),
								100)// volume
				);
				//
				final ByteConverter byteConverter = isSelected(cbWriteVoiceAsFlac)
						? getByteConverter(configurableListableBeanFactory, "flac")
						: null;
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
						speechApi.writeVoiceToFile(getText(tfText), voiceId
						//
								, intValue(getRate(getText(tfSpeechRate)), 0)// rate
								//
								,
								Math.min(Math.max(
										intValue(jsSpeechVolume != null ? jsSpeechVolume.getValue() : null, 100), 0),
										100)// volume
								, file = File.createTempFile(RandomStringUtils.randomAlphabetic(3), null)
						//
						);
						//
						if (isSelected(cbConvertToFlac) && Objects.equals("wav", getFileExtension(
								testAndApply(Objects::nonNull, file, new ContentInfoUtil()::findMatch, null)))) {
							//
							final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory,
									"flac");
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
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
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
			setText(tfRomaji,
					convert(jakaroma = ObjectUtils.getIfNull(jakaroma, Jakaroma::new), getText(tfText), false, false));
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
				export(voices, outputFolderFileNameExpressions, voiceFolder, outputFolder, progressBar,
						isSelected(cbOverMp3Title));
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
				if (file != null && file.exists() && file.isFile() && file.length() == 0) {
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
					FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), createImportFileTemplateByteArray());
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
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final File selectedFile = jfc.getSelectedFile();
				//
				ContentInfo ci = null;
				try {
					ci = new ContentInfoUtil().findMatch(selectedFile);
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
				if (ci == null) {
					//
					JOptionPane.showMessageDialog(null, "com.j256.simplemagic.ContentInfo is null");
					//
					return;
					//
				} // if
					//
				final String mimeType = ci.getMimeType();
				//
				if (!(Objects.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", mimeType)
						|| Objects.equals("application/vnd.openxmlformats-officedocument", mimeType))) {
					//
					JOptionPane.showMessageDialog(null,
							String.format("File [%1$s] is not a XLSX File", selectedFile.getAbsolutePath()));
					//
					return;
					//
				} // if
					//
				try (final Workbook workbook = new XSSFWorkbook(selectedFile)) {
					//
					final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
					//
					final POIXMLDocument poiXmlDocument = cast(POIXMLDocument.class, workbook);
					//
					final List<String> sheetExclued = toList(map(
							stream(getObjectList(objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new),
									getLpwstr(testAndApply(VoiceManager::contains,
											getCustomProperties(getProperties(poiXmlDocument)), "sheetExcluded",
											VoiceManager::getProperty, null)))),
							VoiceManager::toString));
					//
					if (objectMap != null) {
						//
						objectMap.setObject(File.class, selectedFile);
						//
						objectMap.setObject(VoiceManager.class, this);
						//
						objectMap.setObject(String.class, voiceFolder);
						//
						objectMap.setObject(SqlSessionFactory.class, sqlSessionFactory);
						//
						objectMap.setObject(JProgressBar.class, progressBar);
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
					for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
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
						if (progressBar != null) {
							//
							progressBar
									.setMaximum(Math.max(0, (sheet != null ? sheet.getPhysicalNumberOfRows() : 0) - 1));
							//
						} // if
							//
						if (objectMap != null) {
							//
							objectMap.setObject(ByteConverter.class,
									getByteConverter(configurableListableBeanFactory,
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
			} // if
				//
		} // if
			//
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

	private static ByteConverter getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String format) {
		//
		Unit<ByteConverter> byteConverter = null;
		//
		final Map<String, ByteConverter> byteConverters = configurableListableBeanFactory != null
				? configurableListableBeanFactory.getBeansOfType(ByteConverter.class)
				: null;
		//
		if (byteConverters != null && byteConverters.entrySet() != null) {
			//
			BeanDefinition bd = null;
			//
			for (final Entry<String, ByteConverter> en : byteConverters.entrySet()) {
				//
				if (en == null || (bd = configurableListableBeanFactory.getBeanDefinition(en.getKey())) == null
						|| !Objects.equals(format, testAndApply(bd::hasAttribute, "format", bd::getAttribute, null))) {
					continue;
				} // if
					//
				if (byteConverter == null) {
					//
					byteConverter = Unit.with(en.getValue());
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
		if (Objects.equals(source, tfListNames)) {
			//
			final JTextComponent jtf = cast(JTextComponent.class, source);
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
		} // if
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

	private static interface ByteConverter {

		byte[] convert(final byte[] source);

	}

	private static class AudioToFlacByteConverter implements ByteConverter {

		private Integer audioStreamEncoderByteArrayLength = null;

		public void setAudioStreamEncoderByteArrayLength(final Object audioStreamEncoderByteArrayLength) {
			//
			if (audioStreamEncoderByteArrayLength instanceof Integer) {
				this.audioStreamEncoderByteArrayLength = (Integer) audioStreamEncoderByteArrayLength;
			} else if (audioStreamEncoderByteArrayLength instanceof Number) {
				this.audioStreamEncoderByteArrayLength = Integer
						.valueOf(((Number) audioStreamEncoderByteArrayLength).intValue());
			} else {
				this.audioStreamEncoderByteArrayLength = valueOf(
						VoiceManager.toString(audioStreamEncoderByteArrayLength));
			} // if
				//
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
				return baos.toByteArray();
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
			if (test(predicate, string = toString(pair.getValue())) || predicate == null) {
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
		if (Objects.equals("mp3", getFileExtension(
				testAndApply(f -> f != null && f.isFile(), file, new ContentInfoUtil()::findMatch, null)))) {
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
			message = String.format("File \"%1$s\" does not exist", file.getAbsolutePath());
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
		} else if (!file.isFile()) {
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
							&& (Number.class.isAssignableFrom(f.getType()) || Objects.equals(Integer.TYPE, f.getType()))
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

	private static byte[] createImportFileTemplateByteArray() {
		//
		final Class<?> importFieldClass = forName("domain.Voice$ImportField");
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, FieldUtils.getAllFields(Voice.class), Arrays::stream, null),
						f -> anyMatch(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
								a -> Objects.equals(annotationType(a), importFieldClass))));
		//
		Field f = null;
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		byte[] bs = null;
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			for (int i = 0; fs != null && i < fs.size(); i++) {
				//
				if ((f = fs.get(i)) == null
						|| (workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new)) == null) {
					continue;
				} // if
					//
				if (sheet == null) {
					sheet = workbook.createSheet();
				} // if
					//
				if (row == null && sheet != null) {
					row = sheet.createRow(0);
				} // if
					//
				setCellValue(createCell(row, i), getName(f));
				//
			} // for
				//
			write(workbook, baos);
			//
			bs = baos.toByteArray();
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

		<T> void setObject(final Class<T> key, final T value);

		static <T> T getObject(final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

	}

	private static class ImportTask implements Runnable {

		private Integer counter = null;

		private Integer count = null;

		private ObjectMap objectMap = null;

		private BiConsumer<Voice, String> errorMessageConsumer = null;

		private BiConsumer<Voice, Throwable> throwableConsumer = null;

		private Consumer<Voice> voiceConsumer = null;

		private Voice voice = null;

		private File file = null;

		private NumberFormat percentNumberFormat = null;

		@Override
		public void run() {
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

	private static void importVoice(final Sheet sheet, final ObjectMap _objectMap,
			final BiConsumer<Voice, String> errorMessageConsumer, final BiConsumer<Voice, Throwable> throwableConsumer,
			final Consumer<Voice> voiceConsumer)
			throws IllegalAccessException, IOException, InvocationTargetException, BaseException {
		//
		final File file = ObjectMap.getObject(_objectMap, File.class);
		//
		final File folder = file != null ? file.getParentFile() : null;
		//
		List<Field> fieldOrder = null;
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
							add(fieldOrder = ObjectUtils.getIfNull(fieldOrder, ArrayList::new),
									orElse(findFirst(testAndApply(Objects::nonNull, fs, Arrays::stream, null).filter(
											field -> Objects.equals(getName(field), cell.getStringCellValue()))),
											null));
							//
						} else if (fieldOrder.size() > (columnIndex = cell.getColumnIndex())
								&& (f = fieldOrder.get(columnIndex)) != null) {
							//
							f.setAccessible(true);
							//
							if (Objects.equals(type = f.getType(), String.class)) {
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
							} else if (type != null && Enum.class.isAssignableFrom(type) && (list = toList(filter(
									testAndApply(Objects::nonNull, type.getEnumConstants(), Arrays::stream, null),
									e -> Objects.equals(name(cast(Enum.class, e)), cell.getStringCellValue())))) != null
									&& !list.isEmpty()) {
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
							(it = new ImportTask()).counter = Integer.valueOf(row.getRowNum());
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
									if ((it.file = File.createTempFile(RandomStringUtils.randomAlphabetic(3),
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
					String.format("File \"%1$s\" does not exist", selectedFile.getAbsolutePath()));
			//
			return;
			//
		} else if (!selectedFile.isFile()) {
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

		private Map<Object, Object> getObjects() {
			if (objects == null) {
				objects = new LinkedHashMap<>();
			}
			return objects;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ObjectMap) {
				//
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!getObjects().containsKey(key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found", key));
						//
					} // if
						//
					return getObjects().get(key);
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					put(getObjects(), args[0], args[1]);
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
		voice.setText(getText(instance.tfText));
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

		private Integer counter = null;

		private Integer count = null;

		private Voice voice = null;

		private Map<String, String> outputFolderFileNameExpressions = null;

		private EvaluationContext evaluationContext = null;

		private String voiceFolder = null;

		private String outputFolder = null;

		private JProgressBar progressBar = null;

		private ExpressionParser expressionParser = null;

		private NumberFormat percentNumberFormat = null;

		private boolean overMp3Title = false;

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
				String key, value = null;
				//
				File fileSource, fileDestination, folder = null;
				//
				for (final Entry<String, String> folderFileNamePattern : outputFolderFileNameExpressions.entrySet()) {
					//
					if (folderFileNamePattern == null || (key = folderFileNamePattern.getKey()) == null
							|| StringUtils.isBlank(value = folderFileNamePattern.getValue())
							|| !(fileSource = voiceFolder != null ? new File(voiceFolder, filePath)
									: new File(filePath)).exists()) {
						continue;
					} // if
						//
					FileUtils.copyFile(fileSource,
							fileDestination = new File(
									(folder = ObjectUtils.getIfNull(folder,
											() -> outputFolder != null ? new File(outputFolder) : null)) != null
													? new File(folder, key)
													: new File(key),
									VoiceManager.toString(getValue(expressionParser, evaluationContext, value))));
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
				} // if
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

		private static void setMp3Title(final File file) throws IOException, BaseException {
			//
			final String fileExtension = getFileExtension(
					testAndApply(f -> f != null && f.isFile(), file, new ContentInfoUtil()::findMatch, null));
			//
			if (Objects.equals("mp3", fileExtension)) {
				//
				final File tempFile = File.createTempFile(RandomStringUtils.randomAlphabetic(3), null);
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
					final String fileName = file.getName();
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
						mp3File.save(file.getAbsolutePath());
						//
					} // if
						//
				} // if
					//
			} // if
				//
		}

	}

	private static void export(final List<Voice> voices, final Map<String, String> outputFolderFileNameExpressions,
			final String voiceFolder, final String outputFolder, final JProgressBar progressBar,
			final boolean overMp3Title) throws IOException {
		//
		if (progressBar != null) {
			//
			progressBar.setMaximum(Math.max(0, voices != null ? voices.size() : 0));
			//
		} // if
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
		try {
			//
			final int size = voices != null ? voices.size() : 0;
			//
			for (int i = 0; i < size; i++) {
				//
				if ((es = ObjectUtils.getIfNull(es, () -> Executors.newFixedThreadPool(1))) == null) {
					//
					continue;
					//
				} // if
					//
				(et = new ExportTask()).counter = Integer.valueOf(i + 1);
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
				et.outputFolder = outputFolder;
				//
				et.outputFolderFileNameExpressions = outputFolderFileNameExpressions;
				//
				et.progressBar = progressBar;
				//
				et.voice = voices.get(i);
				//
				et.voiceFolder = voiceFolder;
				//
				et.overMp3Title = overMp3Title;
				//
				es.submit(et);
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
			if ((voice = voices.get(i)) == null
					|| (workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new)) == null) {
				continue;
			} // if
				//
			if (sheet == null) {
				sheet = workbook.createSheet();
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
					setCellValue(createCell(row, j), getName(fs[j]));
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
							findFirst(filter(
									testAndApply(Objects::nonNull,
											getDeclaredMethods(annotationType(a = orElse(
													findFirst(filter(Arrays.stream(getDeclaredAnnotations(f)),
															x -> Objects.equals(annotationType(x), dataFormatClass))),
													null))),
											Arrays::stream, null),
									x -> Objects.equals(getName(x), "value"))),
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
							findFirst(filter(
									testAndApply(Objects::nonNull,
											getDeclaredMethods(annotationType(a = orElse(
													findFirst(filter(Arrays.stream(getDeclaredAnnotations(f)),
															x -> Objects.equals(annotationType(x), dateFormatClass))),
													null))),
											Arrays::stream, null),
									x -> Objects.equals(getName(x), "value"))),
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
		} else if (Objects.equals(ci.getMimeType(), "audio/x-hx-aac-adts")) {
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
		instance.init();
		//
		instance.pack();
		//
		instance.setVisible(true);
		//
	}

}