package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
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
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiConsumerUtil;
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
import org.apache.commons.lang3.function.OnlineNHKJapanesePronunciationsAccentFailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.ConfigurationUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryUtil;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionUtil;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.VoiceManager.ByteConverter;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterUtil;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

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
import com.mariten.kanatools.KanaConverter;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.Mp3FileUtil;

import domain.JlptVocabulary;
import domain.Pronunciation;
import domain.Voice;
import domain.Voice.ByteArray;
import domain.Voice.Yomi;
import domain.VoiceList;
import fr.free.nrw.jakaroma.Jakaroma;
import fr.free.nrw.jakaroma.JakaromaUtil;
import io.github.toolfactory.narcissus.Narcissus;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerImportSinglePanel extends JPanel
		implements Titled, InitializingBean, EnvironmentAware, DocumentListener, ItemListener, KeyListener,
		ChangeListener, ActionListener, BeanFactoryPostProcessor, ApplicationContextAware {

	private static final long serialVersionUID = -3130553405296925918L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerImportSinglePanel.class);

	private static final String LANGUAGE = "Language";

	private static final String GROWX = "growx";

	private static final String SOURCE = "Source";

	private static final String PRONUNCIATION = "Pronunciation";

	private static final String VALUE = "value";

	private static final String ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER = "Romaji";

	private static final String HIRAGANA_WITH_FIRST_CAPTICALIZED_LETTER = "Hiragana";

	private static final String ALIGN_FORMAT = "align %1$s %1$s";

	private static final String SPEECH_RATE = "Speech Rate";

	private static final String COMPONENT = "component";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String SHA_512 = "SHA-512";

	private static final String FORMAT = "format";

	private static final String NO_FILE_SELECTED = "No File Selected";

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
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L780">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String WMIN_ONLY_FORMAT = "wmin %1$s";

	private static final String WRAP = "wrap";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see java.lang.Class#getResourceAsStream(java.lang.String)
	 */
	private static final String CLASS_RESOURCE_FORMAT = "/%1$s.class";

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static IValue0<Method> METHOD_RANDOM_ALPHABETIC = null;

	private transient PropertyResolver propertyResolver = null;

	@Note("Language")
	private JTextComponent tfLanguage = null;

	@Note("Source")
	private JTextComponent tfSource = null;

	@Note("Text Import")
	private JTextComponent tfTextImport = null;

	@Note("Speech Language Code")
	private JTextComponent tfSpeechLanguageCode = null;

	@Note("Speech Language Name")
	private JTextComponent tfSpeechLanguageName = null;

	@Note("IPA Symbol")
	private JTextComponent tfIpaSymbol = null;

	@Note("List Names")
	private JTextComponent tfListNames = null;

	@Note("Romaji")
	private JTextComponent tfRomaji = null;

	@Note("Hiragana")
	private JTextComponent tfHiragana = null;

	@Note("Katakana")
	private JTextComponent tfKatakana = null;

	@Note("Pronunciation Page URL")
	private JTextComponent tfPronunciationPageUrl = null;

	@Note("Pronunciation Page Status Code")
	private JTextComponent tfPronunciationPageStatusCode = null;

	@Note("Speech Rate")
	private JTextComponent tfSpeechRate = null;

	private JTextComponent tfSpeechVolume = null;

	private transient javax.swing.text.Document tfTextImportDocument = null;

	private transient ComboBoxModel<Boolean> cbmIsKanji = null;

	private transient ComboBoxModel<String> cbmGaKuNenBeTsuKanJi = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Url("https://ja.wikipedia.org/wiki/%E5%B8%B8%E7%94%A8%E6%BC%A2%E5%AD%97%E4%B8%80%E8%A6%A7")
	private transient ComboBoxModel<Boolean> cbmJouYouKanJi = null;

	@Url("https://www.jlpt.jp/about/levelsummary.html")
	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private transient ComboBoxModel<Yomi> cbmYomi = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Voice ID")
	private transient ComboBoxModel<String> cbmVoiceId = null;

	@Note("For Import Panel")
	private transient ComboBoxModel<?> cbmAudioFormatExecute = null;

	private JComboBox<JlptVocabulary> jcbJlptVocabulary = null;

	private JComboBox<Object> jcbVoiceId = null;

	@Group(PRONUNCIATION)
	private JComboBox<Pronunciation> jcbPronunciation = null;

	private transient IValue0<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private transient IValue0<List<JlptVocabulary>> jlptVocabularyList = null;

	private transient IValue0<List<String>> jlptLevels = null;

	private transient IValue0<String> imageFormat = null;

	private transient IValue0<List<String>> jouYouKanJiList = null;

	private transient IValue0<Multimap<String, String>> ipaSymbolMultimap = null;

	private transient MutableComboBoxModel<JlptVocabulary> mcbmJlptVocabulary = null;

	private transient MutableComboBoxModel<Pronunciation> mcbmPronunciation = null;

	private transient MutableComboBoxModel<String> mcbmPronounicationAudioFormat = null;

	private transient SpeechApi speechApi = null;

	private transient Collection<Map> mapRomaji = null;

	private char[] allowedRomajiCharacters = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Group(PRONUNCIATION)
	@Note("Check Pronunciation")
	private AbstractButton btnCheckPronunciation = null;

	private AbstractButton btnConvertToHiraganaOrKatakana = null;

	@Group("Conversion")
	private AbstractButton btnConvertToRomaji = null;

	@Group("Conversion")
	private AbstractButton btnConvertToKatakana = null;

	@Group(PRONUNCIATION)
	private AbstractButton btnPlayPronunciationAudio = null;

	@Group(PRONUNCIATION)
	@Note("Check Pronunciation Page")
	private AbstractButton btnPronunciationPageUrlCheck = null;

	@Note("IPA Symbol")
	private AbstractButton btnIpaSymbol = null;

	@Note("TTS Voice")
	private AbstractButton cbUseTtsVoice = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

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

	@Note("List Name(s)")
	private JLabel jlListNames = null;

	@Note("List Count")
	private JLabel jlListNameCount = null;

	@Note("Slider For Speech Rate")
	private JSlider jsSpeechRate = null;

	@Note("Slider For Speech Volumne")
	private JSlider jsSpeechVolume = null;

	private ObjectMapper objectMapper = null;

	private String preferredPronunciationAudioFormat = null;

	private transient OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction = null;

	private transient Jakaroma jakaroma = null;

	private transient Collection<Multimap> multimapHiragana = null;

	private transient Collection<Multimap> multimapKatakana = null;

	private transient Collection<Map> mapHiragana = null;

	private transient SqlSessionFactory sqlSessionFactory = null;

	private String[] voiceIds = null;

	@Nullable
	private String[] mp3Tags = null;

	@Note("Voice Folder")
	private String voiceFolder = null;

	private String messageDigestAlgorithm = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private transient ApplicationContext applicationContext = null;

	@Nullable
	private transient Iterable<String> imageWriterSpiFormats = null;

	private Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter = null;

	@Override
	public String getTitle() {
		return "Import(Single)";
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setGaKuNenBeTsuKanJiMultimap(final Multimap<String, String> gaKuNenBeTsuKanJiMultimap) {
		this.gaKuNenBeTsuKanJiMultimap = Unit.with(gaKuNenBeTsuKanJiMultimap);
	}

	public void setJlptVocabularyList(final List<JlptVocabulary> jlptVocabularyList) {
		this.jlptVocabularyList = Unit.with(jlptVocabularyList);
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = Unit.with(jlptLevels);
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setJouYouKanJiList(final List<String> jouYouKanJiList) {
		this.jouYouKanJiList = Unit.with(jouYouKanJiList);
	}

	public void setImageFormat(final String imageFormat) {
		this.imageFormat = Unit.with(imageFormat);
	}

	public void setPreferredPronunciationAudioFormat(final String preferredPronunciationAudioFormat) {
		this.preferredPronunciationAudioFormat = preferredPronunciationAudioFormat;
	}

	public void setOnlineNHKJapanesePronunciationsAccentFailableFunction(
			final OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction) {
		this.onlineNHKJapanesePronunciationsAccentFailableFunction = onlineNHKJapanesePronunciationsAccentFailableFunction;
	}

	public void setIpaSymbolMultimap(final Multimap<String, String> ipaSymbolMultimap) {
		this.ipaSymbolMultimap = Unit.with(ipaSymbolMultimap);
	}

	public void setSqlSessionFactory(final SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setMessageDigestAlgorithm(final String messageDigestAlgorithm) {
		this.messageDigestAlgorithm = messageDigestAlgorithm;
	}

	public void setMp3Tags(final Object value) {
		//
		mp3Tags = Util.toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setLanguageCodeToTextObjIntFunction(
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		this.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
	}

	public void setAllowedRomajiCharacters(final char[] allowedRomajiCharacters) {
		this.allowedRomajiCharacters = allowedRomajiCharacters;
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
		Collection<String> collection = null;
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

	public void setVoiceIdListCellRendererConverter(
			final Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter) {
		this.voiceIdListCellRendererConverter = voiceIdListCellRendererConverter;
	}

	@Nullable
	private static <E> Iterator<E> asIterator(@Nullable final Enumeration<E> instance) {
		return instance != null ? instance.asIterator() : null;
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
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
			return booleans != null ? new DefaultComboBoxModel<>(Util.toArray(booleans, new Boolean[] {}))
					: new DefaultComboBoxModel<>();
			//
		}

	}

	@Nullable
	private static Integer getTempFileMinimumPrefixLength() {
		//
		Integer result = null;
		//
		final Class<?> clz = File.class;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
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
		for (int i = 0; i < length(instructions); i++) {
			//
			if ((iconst = Util.cast(ICONST.class, ArrayUtils.get(instructions, i))) != null
					&& i < length(instructions) - 1 && ArrayUtils.get(instructions, i + 1) instanceof IF_ICMPGE
					&& (value = iconst.getValue()) != null) {
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

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(ObjectUtils.getIfNull(IValue0Util.getValue0(getLayoutManager(applicationContext)), MigLayout::new));
		//
		// Language
		//
		add(new JLabel(LANGUAGE));
		//
		add(tfLanguage = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.language")),
				String.format("%1$s,span %2$s", GROWX, 11));
		//
		// Source
		//
		add(new JLabel(SOURCE));
		//
		add(tfSource = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.source")),
				String.format("%1$s,span %2$s,wmin %3$s", GROWX, 4, 50));
		//
		// Kanji
		//
		add(new JLabel("Kanji"));
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
		add(new JComboBox<>(cbmIsKanji = Util.get(booleanComboBoxModelSupplier)));
		//
		Util.setSelectedItem(cbmIsKanji,
				testAndApply(StringUtils::isNotEmpty, PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.isKanji"), Boolean::valueOf, null));
		//
		// 学年別漢字 GaKuNenBeTsuKanJi
		//
		add(new JLabel("学年別漢字"));
		//
		final Set<String> gaKuNenBeTsuKanJiList = MultimapUtil.keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap));
		//
		add(new JComboBox<>(cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
				ArrayUtils.insert(0, Util.toArray(gaKuNenBeTsuKanJiList, new String[] {}), (String) null),
				DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>())), String.format(SPAN_ONLY_FORMAT, 2));
		//
		final String string = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.gaKuNenBeTsuKanJi");
		//
		if (StringUtils.isNotEmpty(string)) {
			//
			Util.setSelectedItem(cbmGaKuNenBeTsuKanJi, string);
			//
		} // if
			//
			// 常用漢字 JoYoKanJi
			//
		add(new JLabel("常用漢字"));
		//
		add(new JComboBox<>(cbmJouYouKanJi = Util.get(booleanComboBoxModelSupplier)),
				String.format("%1$s,span %2$s", WRAP, 1));
		//
		Util.setSelectedItem(cbmJouYouKanJi,
				testAndApply(StringUtils::isNotEmpty, PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.yoKoKanJi"), Boolean::valueOf, null));
		//
		// Text
		//
		add(new JLabel("Text"));
		//
		add(tfTextImport = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.text")), String.format("%1$s,span %2$s", GROWX, 18));
		//
		addDocumentListener(tfTextImportDocument = tfTextImport.getDocument(), this);
		//
		(jcbJlptVocabulary = new JComboBox<JlptVocabulary>(mcbmJlptVocabulary = new DefaultComboBoxModel<>()))
				.addItemListener(this);
		//
		add(jcbJlptVocabulary, String.format(SPAN_ONLY_FORMAT, 3));
		//
		final ListCellRenderer<?> render = Util.getRenderer(jcbJlptVocabulary);
		//
		setRenderer(jcbJlptVocabulary, new ListCellRenderer<JlptVocabulary>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends JlptVocabulary> list,
					final JlptVocabulary value, final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return Util.getListCellRendererComponent(((ListCellRenderer) render), list,
						testAndApply(Objects::nonNull, value, x -> StringUtils.trim(
								String.join(" ", StringUtils.defaultString(getKanji(x)), getKana(x), getLevel(x))),
								null),
						index, isSelected, cellHasFocus);
				//
			}
		});
		//
		add(btnCheckPronunciation = new JButton(PRONUNCIATION), String.format("%1$s,span %2$s", WRAP, 2));
		//
		add(new JLabel());
		//
		add(btnConvertToHiraganaOrKatakana = new JButton("Convert To Hiragana or Katakana"),
				String.format(SPAN_ONLY_FORMAT, 3));
		//
		add(btnConvertToRomaji = new JButton("Convert To Romaji"), String.format("%1$s,span %2$s", WRAP, 2));
		//
		// Yomi
		//
		add(new JLabel("Yomi"));
		//
		final Yomi[] yomis = Yomi.values();
		//
		final JComboBox<Object> jcbYomi = new JComboBox(
				cbmYomi = new DefaultComboBoxModel<>(ArrayUtils.insert(0, yomis, (Yomi) null)));
		//
		final ListCellRenderer<Object> listCellRenderer = Util.cast(ListCellRenderer.class, Util.getRenderer(jcbYomi));
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
		setRenderer(jcbYomi, new ListCellRenderer<Object>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				//
				final String name = Util.name(Util.cast(Enum.class, value));
				//
				if (Util.containsKey(yomiNameMapTemp, name)) {
					//
					return Util.getListCellRendererComponent(listCellRenderer, list, Util.get(yomiNameMapTemp, name),
							index, isSelected, cellHasFocus);
					//
				} // if
					//
				return Util.getListCellRendererComponent(listCellRenderer, list, value, index, isSelected,
						cellHasFocus);
				//
			}
		});
		//
		add(jcbYomi);
		//
		add(new JLabel("IPA"));
		//
		add(tfIpaSymbol = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.ipaSymbol")),
				String.format("%1$s,wmin %2$s,wmax %3$s,span %4$s", GROWX, 100, 200, 2));
		//
		final List<Yomi> yomiList = Util.toList(Util.filter(testAndApply(Objects::nonNull, yomis, Arrays::stream, null),
				y -> Objects.equals(Util.name(y), PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.yomi"))));
		//
		final int size = IterableUtils.size(yomiList);
		//
		if (size == 1) {
			//
			Util.setSelectedItem(cbmYomi, IterableUtils.get(yomiList, 0));
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		add(btnIpaSymbol = new JButton("Get IPA"));
		//
		add(new JLabel("List(s)"));
		//
		final String tags = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.listNames");
		//
		add(tfListNames = new JTextField(tags), String.format("%1$s,span %2$s", GROWX, 9));
		//
		tfListNames.addKeyListener(this);
		//
		add(jlListNames = new JLabel(), String.format(SPAN_ONLY_FORMAT, 5));
		//
		add(jlListNameCount = new JLabel(), String.format("wmax %1$s", 20));
		//
		testAndRun(StringUtils.isNotBlank(tags), () -> keyReleased(new KeyEvent(tfListNames, 0, 0, 0, 0, ' ')));
		//
		// JLPT level
		//
		add(new JLabel("JLPT Level"));
		//
		final List<String> jlptLevelList = testAndApply(Objects::nonNull, IValue0Util.getValue0(jlptLevels),
				ArrayList::new, null);
		//
		testAndAccept(CollectionUtils::isNotEmpty, jlptLevelList, x -> add(x, 0, null));
		//
		add(new JComboBox<String>(
				cbmJlptLevel = testAndApply(Objects::nonNull, Util.toArray(jlptLevelList, new String[] {}),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<String>())),
				WRAP);
		//
		Util.setSelectedItem(cbmJlptLevel, PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptLevel"));
		//
		// Romaji
		//
		add(new JLabel(ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER));
		//
		add(tfRomaji = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.romaji")),
				String.format("%1$s,span %2$s", GROWX, 23));
		//
		add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		// Hiragana
		//
		add(new JLabel(HIRAGANA_WITH_FIRST_CAPTICALIZED_LETTER));
		//
		add(tfHiragana = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.hiragana")),
				String.format("%1$s,span %2$s", GROWX, 9));
		//
		add(btnCopyHiragana = new JButton("Copy"));
		//
		add(btnConvertToKatakana = new JButton("Convert"));
		//
		// Katakana
		//
		add(new JLabel("Katakana"), String.format(SPAN_ONLY_FORMAT, 2));
		//
		add(tfKatakana = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.katakana")),
				String.format("%1$s,span %2$s", GROWX, 10));
		//
		add(btnCopyKatakana = new JButton("Copy"), WRAP);
		//
		// Pronunciation
		//
		add(new JLabel(PRONUNCIATION), String.format(SPAN_ONLY_FORMAT, 2));
		//
		// TODO
		//
		(jcbPronunciation = new JComboBox<>(mcbmPronunciation = new DefaultComboBoxModel<>())).addActionListener(this);
		//
		setRenderer(jcbPronunciation, createPronunciationListCellRenderer(Util.getRenderer(jcbPronunciation)));
		//
		// Set height
		//
		final Dimension pd = Util.getPreferredSize(jcbPronunciation);
		//
		if (pd != null) {
			//
			setPreferredSize(jcbPronunciation, new Dimension((int) pd.getWidth(), 33));
			//
		} // if
			//
		add(jcbPronunciation, String.format("%1$s,span %2$s", GROWX, 3));
		//
		add(new JComboBox<>(mcbmPronounicationAudioFormat = new DefaultComboBoxModel<>()),
				String.format(WMIN_ONLY_FORMAT, 94));
		//
		add(btnPlayPronunciationAudio = new JButton("Play"), WRAP);
		//
		// Pronunciation Page URL
		//
		add(new JLabel("Pronunciation Page URL"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		add(tfPronunciationPageUrl = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.pronunciationPageUrl")),
				String.format("%1$s,span %2$s", GROWX, 19));
		//
		add(new JLabel("Status"));
		//
		add(tfPronunciationPageStatusCode = new JTextField(), String.format(WMIN_ONLY_FORMAT, 30));
		//
		add(btnPronunciationPageUrlCheck = new JButton("Check"), WRAP);
		//
		add(new JLabel());
		//
		add(cbUseTtsVoice = new JCheckBox("TTS Voice"));
		//
		cbUseTtsVoice.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.useTtsVoice")));
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
		final JPanel panel2 = new JPanel();
		//
		panel2.setLayout(new MigLayout());
		//
		final int width = 300;
		//
		if (Boolean.logicalAnd(Util.test(predicate, "min"), Util.test(predicate, "max"))) {
			//
			addSpeedButtons(this, panel2,
					RangeUtil.createRange(toInteger(testAndApply(predicate, "min", function, null)),
							toInteger(testAndApply(predicate, "max", function, null))),
					width);
			//
		} // if
			//
		if (Boolean.logicalAnd(jsSpeechRate == null, tfSpeechRate == null)) {
			//
			add(panel2, new JLabel(SPEECH_RATE), String.format(ALIGN_FORMAT, "50%"));
			//
			add(panel2,
					tfSpeechRate = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
							"org.springframework.context.support.VoiceManager.speechRate")),
					String.format(WMIN_ONLY_FORMAT, 27));
			//
		} // if
			//
			// Speech Volume
			//
		add(panel2, new JLabel("Speech Volume"), String.format(ALIGN_FORMAT, "50%"));
		//
		final Range<Integer> speechVolumeRange = createVolumeRange(speechApiInstance);
		//
		final Integer upperEnpoint = testAndApply(RangeUtil::hasUpperBound, speechVolumeRange, RangeUtil::upperEndpoint,
				null);
		//
		add(panel2,
				jsSpeechVolume = new JSlider(Util.intValue(
						testAndApply(RangeUtil::hasLowerBound, speechVolumeRange, RangeUtil::lowerEndpoint, null), 0),
						Util.intValue(upperEnpoint, 100)),
				String.format(WMIN_ONLY_FORMAT, width));
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
		add(panel2, tfSpeechVolume = new JTextField(), String.format("align %1$s %1$s,wmin %2$s", "50%", 27));
		//
		// Audio Format
		//
		panel2.add(new JComboBox(cbmAudioFormatExecute = new DefaultComboBoxModel<Object>()));
		//
		add(panel2, String.format("%1$s,span %2$s", WRAP, 21));
		//
		add(new JLabel(""));
		//
		add(new JLabel(""));
		//
		add(btnExecute = new JButton("Execute"), String.format(SPAN_ONLY_FORMAT, 3));
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
			testAndAccept((a, b) -> b != null, maxPreferredWidth, tfKatakana.getSize(),
					(a, b) -> tfKatakana.setMinimumSize(new Dimension(a.intValue(), (int) b.getHeight())));
			//
			setPreferredWidth(maxPreferredWidth.intValue(), tfHiragana, tfKatakana);
			//
		} // if
			//
		addActionListener(this, btnExecute, btnConvertToRomaji, btnConvertToHiraganaOrKatakana, btnConvertToKatakana,
				btnCopyRomaji, btnCopyHiragana, btnCopyKatakana, btnPronunciationPageUrlCheck, btnIpaSymbol,
				btnCheckPronunciation, btnPlayPronunciationAudio);
		//
		addChangeListener(this, jsSpeechVolume, jsSpeechRate);
		//
		//
		Util.setEnabled(cbUseTtsVoice, (voiceIds = testAndApply(x -> SpeechApi.isInstalled(x), speechApi,
				x -> SpeechApi.getVoiceIds(x), null)) != null);
		//
		Util.setEnabled(tfPronunciationPageStatusCode, false);
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

	private static void setPreferredSize(@Nullable final Component instance, final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
		}
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory)
			throws BeansException {
		//
		// Get the "Bean Definition" which class could be assigned as a "java.util.Map"
		// and the "Bean Definition" has "value" attribute which value
		// is "romaji"
		//
		mapRomaji = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(
						this.configurableListableBeanFactory = configurableListableBeanFactory, Map.class,
						Collections.singletonMap(VALUE, "romaji"))),
				x -> Util.cast(Map.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
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
		// is "hiragana"
		//
		mapHiragana = Util.toList(Util.map(
				Util.stream(getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Map.class,
						Collections.singletonMap(VALUE, "hiragana"))),
				x -> Util.cast(Map.class, BeanFactoryUtil.getBean(configurableListableBeanFactory, x))));
		//
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
		for (int i = 0; i < length(beanDefinitionNames); i++) {
			//
			if ((bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(instnace,
					beanDefinitionName = ArrayUtils.get(beanDefinitionNames, i))) == null) {
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

	private static boolean isAllAttributesMatched(final Map<?, ?> attributes, @Nullable final AttributeAccessor aa) {
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

	@Override
	public void changedUpdate(final DocumentEvent evt) {
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
			Util.forEach(reverseRange(0, Util.getSize(mcbmPronunciation)), i -> removeElementAt(mcbmPronunciation, i));
			//
		} // if
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
		} else if (Objects.equals(source, jcbJlptVocabulary)) {
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
			Util.setText(tfSpeechRate, Util.toString(getValue(jsSpeechRate)));
			//
		} // if
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
		final Object source = Util.getSource(evt);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		final boolean nonTest = !isTestMode();
		//
		// if the "source" is one of the value of the field annotated with
		// "@SystemClipboard", pass the "source" to
		// "actionPerformedForSystemClipboardAnnotated(java.lang.Object)" method
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null),
				f -> Util.isAnnotationPresent(f, SystemClipboard.class)));
		//
		testAndRun(Util.contains(Util.toList(Util.filter(
				FailableStreamUtil.stream(FailableStreamUtil.map(fs, f -> FieldUtils.readField(f, this, true))),
				Objects::nonNull)), source), () -> actionPerformedForSystemClipboardAnnotated(nonTest, source));
		//
		// Conversion
		//
		testAndRun(Util.contains(getObjectsByGroupAnnotation(this, "Conversion"), source),
				() -> actionPerformedForConversion(source));
		//
		// Pronunciation
		//
		testAndRun(Util.contains(getObjectsByGroupAnnotation(this, PRONUNCIATION), source),
				() -> actionPerformedForPronunciation(source));
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedForExecute(headless, nonTest);
			//
		} else if (Objects.equals(source, btnIpaSymbol)) {
			//
			actionPerformedForIpaSymbol(headless);
			//
		} else if (Objects.equals(source, btnConvertToHiraganaOrKatakana)) {
			//
			actionPerformedForBtnConvertToHiraganaOrKatakana();
			//
		} // if
			//
	}

	private static void clear(@Nullable final DefaultTableModel instance) {
		//
		Util.clear(instance != null ? instance.getDataVector() : null);
		//
	}

	private void actionPerformedForBtnConvertToHiraganaOrKatakana() {
		//
		final String textImport = Util.getText(tfTextImport);
		//
		// Hiragana
		//
		IValue0<?> ivHiragana = getIValue0ByKey(multimapHiragana, textImport,
				createFunctionForBtnConvertToHiraganaOrKatakana(HIRAGANA_WITH_FIRST_CAPTICALIZED_LETTER));
		//
		if (ivHiragana == null) {
			//
			ivHiragana = getIValue0FromMapsByKey(mapHiragana, textImport,
					createFunctionForBtnConvertToHiraganaOrKatakana(HIRAGANA_WITH_FIRST_CAPTICALIZED_LETTER));
			//
		} // if
			//
			// Katakana
			//
		final IValue0<?> ivKatakana = getIValue0ByKey(multimapKatakana, textImport,
				createFunctionForBtnConvertToHiraganaOrKatakana("Katakana"));
		//
		if (ivHiragana != null && ivKatakana != null) {
			//
			Util.setText(tfHiragana, Util.toString(IValue0Util.getValue0(ivHiragana)));
			//
			Util.setText(tfKatakana, Util.toString(IValue0Util.getValue0(ivKatakana)));
			//
		} else if (ivHiragana != null) {
			//
			Util.setText(tfHiragana, Util.toString(IValue0Util.getValue0(ivHiragana)));
			//
		} else if (ivKatakana != null) {
			//
			Util.setText(tfKatakana, Util.toString(IValue0Util.getValue0(ivKatakana)));
			//
		} // if
			//
	}

	@Nullable
	private static IValue0<?> getIValue0ByKey(final Iterable<Multimap> multimaps, final Object key,
			@Nullable final Function<Collection<?>, IValue0<?>> function) {
		//
		if (Util.iterator(multimaps) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<?> iValue0 = null;
		//
		int size = 0;
		//
		for (final Multimap multimap : multimaps) {
			//
			if (MultimapUtil.containsKey(multimap, key)) {
				//
				final Collection<?> collection = MultimapUtil.get(multimap, key);
				//
				if ((size = IterableUtils.size(collection)) == 1) {
					//
					if (iValue0 == null) {
						//
						iValue0 = Unit.with(IterableUtils.get(collection, 0));
						//
					} else {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} else if (size > 1 && function != null) {
					//
					iValue0 = Util.apply(function, collection);
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return iValue0;
		//
	}

	private static Function<Collection<?>, IValue0<?>> createFunctionForBtnConvertToHiraganaOrKatakana(
			final String title) {
		//
		return vs -> {
			//
			final Object[] array = Util.toArray(vs);
			//
			final int length = length(array);
			//
			if (length == 0) {
				//
				return null;
				//
			} else if (length == 1) {
				//
				return Unit.with(ArrayUtils.get(array, 0));
				//
			} // if
				//
			if (GraphicsEnvironment.isHeadless()) {
				//
				final Console console = System.console();
				//
				PrintWriter pw = null;
				//
				for (int i = 0; i < length; i++) {
					//
					println(pw = ObjectUtils.getIfNull(pw, () -> writer(console)), i + " " + ArrayUtils.get(array, i));
					//
				} // for
					//
				final Integer index = valueOf(readLine(console, "Item"));
				//
				return index != null && index >= 0 && index.intValue() < length
						? Unit.with(ArrayUtils.get(array, index.intValue()))
						: null;
				//
			} // if
				//
			final JList<?> jList = testAndApply(Objects::nonNull, array, JList::new, x -> new JList<>());
			//
			return JOptionPane.showConfirmDialog(null, jList, title,
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ? Unit.with(jList.getSelectedValue()) : null;
			//
		};
		//
	}

	@Nullable
	private static String readLine(@Nullable final Console instance, final String fmt, final Object... args) {
		//
		// java.io.Console.writeLock
		//
		Object writeLock = null;
		//
		// java.io.Console.readLock
		//
		Object readLock = null;
		//
		try {
			//
			writeLock = testAndApply(Objects::nonNull, instance,
					x -> Narcissus.getObjectField(x, Util.getDeclaredField(Console.class, "writeLock")), null);
			//
			readLock = testAndApply(Objects::nonNull, instance,
					x -> Narcissus.getObjectField(x, Util.getDeclaredField(Console.class, "readLock")), null);
			//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return instance != null && writeLock != null && readLock != null ? instance.readLine(fmt, args) : null;
		//
	}

	private static void println(@Nullable final PrintWriter instance, final String string) {
		//
		Object lock = null;
		//
		try {
			//
			lock = testAndApply(Objects::nonNull, instance,
					x -> Narcissus.getObjectField(x, Util.getDeclaredField(Writer.class, "lock")), null);
			//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		if (instance != null && lock != null) {
			//
			instance.println(string);
			//
		} // if
			//
	}

	@Nullable
	private static PrintWriter writer(@Nullable final Console instance) {
		return instance != null ? instance.writer() : null;
	}

	private void actionPerformedForIpaSymbol(final boolean headless) {
		//
		final Collection<String> values = MultimapUtil.get(IValue0Util.getValue0(ipaSymbolMultimap),
				Util.getText(tfTextImport));
		//
		final int size = IterableUtils.size(values);
		//
		if (size == 1) {
			//
			Util.setText(tfIpaSymbol, Util.toString(IterableUtils.get(values, 0)));
			//
		} else if (!headless && !isTestMode()) {
			//
			final JList<Object> list = new JList<>(Util.toArray(values));
			//
			JOptionPane.showMessageDialog(null, list, "IPA", JOptionPane.PLAIN_MESSAGE);
			//
			Util.setText(tfIpaSymbol, Util.toString(list.getSelectedValue()));
			//
		} // if
			//
	}

	private void actionPerformedForPronunciation(final Object source) {
		//
		if (Objects.equals(source, jcbPronunciation)) {
			//
			pronounicationChanged(Util.cast(Pronunciation.class, Util.getSelectedItem(mcbmPronunciation)),
					mcbmPronounicationAudioFormat, preferredPronunciationAudioFormat, tfPronunciationPageUrl);
			//
			return;
			//
		} else if (Objects.equals(source, btnPronunciationPageUrlCheck)) {
			//
			actionPerformedForPronunciationPageUrlCheck(GraphicsEnvironment.isHeadless());
			//
			return;
			//
		} else if (Objects.equals(source, btnCheckPronunciation)) {
			//
			actionPerformedForBtnCheckPronunciation();
			//
			return;
			//
		} else if (Objects.equals(source, btnPlayPronunciationAudio)) {
			//
			playAudio(Util.cast(Pronunciation.class, Util.getSelectedItem(mcbmPronunciation)),
					Util.getSelectedItem(mcbmPronounicationAudioFormat));
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private static void playAudio(final Pronunciation pronunciation, @Nullable final Object audioFormat) {
		//
		final Set<Entry<String, String>> entrySet = Util.entrySet(getAudioUrls(pronunciation));
		//
		if (Util.iterator(entrySet) != null) {
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				try {
					//
					if (Objects.equals(audioFormat, Util.getKey(entry)) && playAudio(Util.getValue(entry)) != null) {
						//
						break;
						//
					} // if
						//
				} catch (final Exception e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	private static Object playAudio(final String value) throws Exception {
		//
		try (final InputStream is = Util
				.openStream(testAndApply(Objects::nonNull, value, x -> new URI(x).toURL(), null))) {
			//
			PlayerUtil.play(testAndApply(Objects::nonNull, is, Player::new, null));
			//
			return "";
			//
		} // try
			//
	}

	private void actionPerformedForBtnCheckPronunciation() {
		//
		// Remove all element(s) in "mcbmPronounication"
		//
		Util.forEach(reverseRange(0, Util.getSize(mcbmPronunciation)), i -> removeElementAt(mcbmPronunciation, i));
		//
		// Remove all element(s) in "mcbmPronounicationAudioFormat"
		//
		Util.forEach(reverseRange(0, Util.getSize(mcbmPronounicationAudioFormat)),
				i -> removeElementAt(mcbmPronounicationAudioFormat, i));
		//
		try {
			//
			final List<Pronunciation> pronounications = FailableFunctionUtil
					.apply(onlineNHKJapanesePronunciationsAccentFailableFunction, Util.getText(tfTextImport));
			//
			final IValue0<Pronunciation> pronunciation = IterableUtils.size(pronounications) == 1
					? Unit.with(IterableUtils.get(pronounications, 0))
					: null;
			//
			if (CollectionUtils.isNotEmpty(pronounications)) {
				//
				pronounications.add(0, null);
				//
			} // if
				//
			if (pronunciation != null) {
				//
				Util.setSelectedItem(mcbmPronunciation, IValue0Util.getValue0(pronunciation));
				//
			} // if
				//
			forEach(pronounications, x -> Util.addElement(mcbmPronunciation, x));
			//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	private void actionPerformedForPronunciationPageUrlCheck(final boolean headless) {
		//
		Util.setText(tfPronunciationPageStatusCode, null);
		//
		setBackground(tfPronunciationPageStatusCode, null);
		//
		final String urlString = Util.getText(tfPronunciationPageUrl);
		//
		if (StringUtils.isNotBlank(urlString)) {
			//
			try {
				//
				final Integer responseCode = getResponseCode(
						Util.cast(HttpURLConnection.class, Util.openConnection(new URI(urlString).toURL())));
				//
				Util.setText(tfPronunciationPageStatusCode, Integer.toString(responseCode));
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
			} catch (final Exception e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
	}

	@Nullable
	private static Integer getResponseCode(@Nullable final HttpURLConnection instance) throws IOException {
		return instance != null ? Integer.valueOf(instance.getResponseCode()) : null;
	}

	private static void pronounicationChanged(@Nullable final Pronunciation pronunciation,
			final MutableComboBoxModel<String> mcbmAudioFormat, final String preferredPronunciationAudioFormat,
			final JTextComponent jtc) {
		//
		Util.forEach(reverseRange(0, Util.getSize(mcbmAudioFormat)), i -> removeElementAt(mcbmAudioFormat, i));
		//
		Util.setSelectedItem(mcbmAudioFormat, null);
		//
		final Map<String, String> audioUrls = getAudioUrls(pronunciation);
		//
		if (MapUtils.isNotEmpty(audioUrls)) {
			//
			Util.addElement(mcbmAudioFormat, null);
			//
			Util.forEach(audioUrls, (k, v) -> Util.addElement(mcbmAudioFormat, k));
			//
		} // if
			//
		if (Util.containsKey(audioUrls, preferredPronunciationAudioFormat)) {
			//
			Util.setSelectedItem(mcbmAudioFormat, preferredPronunciationAudioFormat);
			//
		} // if
			//
		final String pageUrl = pronunciation != null ? pronunciation.getPageUrl() : null;
		//
		if (StringUtils.isNotBlank(pageUrl)) {
			//
			Util.setText(jtc, pageUrl);
			//
		} // if
			//
	}

	@Nullable
	private static Map<String, String> getAudioUrls(@Nullable final Pronunciation instnace) {
		return instnace != null ? instnace.getAudioUrls() : null;
	}

	private void actionPerformedForConversion(final Object source) {
		//
		Entry<JTextComponent, String> pair = null;
		//
		if (Objects.equals(source, btnConvertToRomaji)) {
			//
			actionPerformedForBtnConvertToRomaji();
			//
			return;
			//
		} else if (Objects.equals(source, btnConvertToKatakana)) {
			//
			pair = Pair.of(tfKatakana, testAndApply(Objects::nonNull, Util.getText(tfHiragana),
					x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_HIRA_TO_ZEN_KATA), null));
			//
		} // if
			//
		if (pair != null) {
			//
			Util.setText(Util.getKey(pair), Util.getValue(pair));
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private void actionPerformedForBtnConvertToRomaji() {
		//
		final String string = Util.getText(tfTextImport);
		//
		final IValue0<?> iValue0 = getIValue0FromMapsByKey(mapRomaji, string,
				createFunctionForBtnConvertToHiraganaOrKatakana(ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER));
		//
		final String romaji = JakaromaUtil.convert(jakaroma = ObjectUtils.getIfNull(jakaroma, Jakaroma::new), string,
				false, false);
		//
		if (iValue0 != null && StringUtils.isNotEmpty(Util.toString(IValue0Util.getValue0(iValue0)))
				&& StringUtils.isNotBlank(romaji)) {
			//
			final List<Object> objects = new ArrayList<>(Collections.singleton(IValue0Util.getValue0(iValue0)));
			//
			if (isAllCharactersAllowed(romaji, allowedRomajiCharacters) && !Util.contains(objects, romaji)) {
				//
				Util.add(objects, romaji);
				//
			} // if
				//
			if (IterableUtils.size(objects) == 1) {
				//
				Util.setText(tfRomaji, Util.toString(IterableUtils.get(objects, 0)));
				//
				return;
				//
			} // if
				//
			final JList<?> list = new JList<>(Util.toArray(objects));
			//
			if (!GraphicsEnvironment.isHeadless() && JOptionPane.showConfirmDialog(null, list,
					ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//
				Util.setText(tfRomaji, Util.toString(list.getSelectedValue()));
				//
				return;
				//
			} // if
				//
		} // if
			//
		Util.setText(tfRomaji, romaji);
		//
	}

	private static boolean isAllCharactersAllowed(@Nullable final CharSequence cs,
			@Nullable final char[] allowedChars) {
		//
		boolean allCharacterAllowed = true;
		//
		for (int i = 0; cs != null && allowedChars != null && allowedChars.length > 0
				&& i < StringUtils.length(cs); i++) {
			//
			if (!(allCharacterAllowed = ArrayUtils.contains(allowedChars, cs.charAt(i)))) {
				//
				break;
				//
			} // if
				//
		} // for
			//
		return allCharacterAllowed;
		//
	}

	@Nullable
	private static IValue0<?> getIValue0FromMapsByKey(final Iterable<Map> maps, final Object key,
			final Function<Collection<?>, IValue0<?>> function) {
		//
		if (Util.iterator(maps) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<?> iValue0 = null;
		//
		Object value = null;
		//
		for (final Map map : maps) {
			//
			if (Util.containsKey(map, key)) {
				//
				value = Util.get(map, key);
				//
				if (iValue0 == null) {
					//
					iValue0 = Unit.with(value);
					//
				} else if (!Objects.equals(IValue0Util.getValue0(iValue0), value)) {
					//
					final IValue0<?> iv0 = Util.apply(function, getValueCollectionByKey(maps, key));
					//
					if (iv0 != null) {
						//
						return iv0;
						//
					} //
						//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return iValue0;
		//
	}

	@Nullable
	private static Collection<Object> getValueCollectionByKey(final Iterable<Map> maps, final Object key) {
		//
		if (Util.iterator(maps) == null) {
			//
			return null;
			//
		} // if
			//
		Collection<Object> vs = null;
		//
		for (final Map<?, ?> m : maps) {
			//
			if (m == null || !Util.containsKey(m, key)) {
				//
				continue;
				//
			} // if
				//
			Util.add(vs = ObjectUtils.getIfNull(vs, ArrayList::new), Util.get(m, key));
			//
		} // for
			//
		return vs;
		//
	}

	private void actionPerformedForSystemClipboardAnnotated(final boolean nonTest, final Object source) {
		//
		final Clipboard clipboard = getSystemClipboard(getToolkit());
		//
		IValue0<String> stringValue = null;
		//
		if (Objects.equals(source, btnCopyRomaji)) {
			//
			stringValue = Unit.with(Util.getText(tfRomaji));
			//
		} else if (Objects.equals(source, btnCopyHiragana)) {
			//
			stringValue = Unit.with(Util.getText(tfHiragana));
			//
		} else if (Objects.equals(source, btnCopyKatakana)) {
			//
			stringValue = Unit.with(Util.getText(tfKatakana));
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

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static List<?> getObjectsByGroupAnnotation(final Object instance, final String group) {
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null), f -> {
					final Group g = Util.isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringUtils.equals(g != null ? g.value() : null, group);
				}));
		//
		return Util.toList(FailableStreamUtil.stream(
				FailableStreamUtil.map(fs, f -> instance != null ? FieldUtils.readField(f, instance, true) : null)));
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
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

	}

	private static class IH implements InvocationHandler {

		private Collection<Object> throwableStackTraceHexs = null;

		private Runnable runnable = null;

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
					return Unit.with(VoiceManagerImportSinglePanel.invoke(method, runnable, args));
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

		@Nullable
		private static byte[] getBytes(@Nullable final String instance) {
			return instance != null ? instance.getBytes() : null;
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
				testAndAccept(m -> throwable != null || Util.isStatic(m), method,
						m -> VoiceManagerImportSinglePanel.invoke(m, throwable));
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
			if (Objects.equals(methodName, "getObject") && length(args) > 0) {
				//
				final Object key = ArrayUtils.get(args, 0);
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
			} else if (Objects.equals(methodName, "containsObject") && length(args) > 0) {
				//
				return Unit.with(Boolean.valueOf(Util.containsKey(map, ArrayUtils.get(args, 0))));
				//
			} else if (Objects.equals(methodName, "setObject") && length(args) > 1) {
				//
				Util.put(map, ArrayUtils.get(args, 0), ArrayUtils.get(args, 1));
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

	private void actionPerformedForExecute(final boolean headless, final boolean nonTest) {
		//
		final VoiceManager voiceManager = VoiceManager.INSTANCE;
		//
		setJTextComponentText(headless, voiceManager, null, "tfFile", "tfFileLength", "tfFileDigest");
		//
		// try to retrieve the "Pronunciation" Audio File
		//
		File file = getPronunciationAudioFileByAudioFormat(
				Util.cast(Pronunciation.class, Util.getSelectedItem(mcbmPronunciation)),
				Util.getSelectedItem(mcbmPronounicationAudioFormat));
		//
		Util.deleteOnExit(file);
		//
		final Voice voice = createVoice(getObjectMapper(), this);
		//
		final boolean useTtsVoice = Util.isSelected(cbUseTtsVoice);
		//
		if (Boolean.logicalOr(file == null, useTtsVoice)) {
			//
			try {
				//
				Util.deleteOnExit(file = generateTtsAudioFile(headless, nonTest, voice));
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
		DefaultTableModel tmImportException = null;
		//
		try {
			//
			tmImportException = testAndApply(Objects::nonNull, voiceManager,
					x -> Util.cast(DefaultTableModel.class, FieldUtils.readDeclaredField(x, "tmImportException", true)),
					null);
			//
		} catch (final IllegalAccessException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
		if (Boolean.logicalAnd(file == null, !useTtsVoice)) {
			//
			try {
				//
				setSource(voice,
						StringUtils.defaultIfBlank(getSource(voice),
								getMp3TagValue(file = getAudioFile(headless, voice, tmImportException),
										x -> StringUtils.isNotBlank(Util.toString(x)), mp3Tags)));
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
			// Pronunciation PitchAccentImage
			//
		final RenderedImage pitchAccentImage = getPitchAccentImage(
				Util.cast(Pronunciation.class, Util.getSelectedItem(mcbmPronunciation)));
		//
		testAndAccept(x -> pitchAccentImage != null,
				createByteArray(pitchAccentImage, getImageFormat(imageFormat, imageWriterSpiFormats), headless),
				x -> setPitchAccentImage(voice, x));
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
			ObjectMap.setObject(objectMap, VoiceMapper.class,
					ConfigurationUtil.getMapper(SqlSessionFactoryUtil.getConfiguration(sqlSessionFactory),
							VoiceMapper.class, sqlSession = SqlSessionFactoryUtil.openSession(sqlSessionFactory)));
			//
			ObjectMap.setObject(objectMap, VoiceManager.class, voiceManager);
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
				throw ObjectUtils.getIfNull(toRuntimeException(e), RuntimeException::new);
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

	private static void setJTextComponentText(final boolean headless, @Nullable final VoiceManager voiceManager,
			@Nullable final String value, @Nullable final String... names) {
		//
		if (voiceManager != null) {
			//
			for (int i = 0; i < length(names); i++) {
				//
				try {
					//
					Util.setText(Util.cast(JTextComponent.class,
							FieldUtils.readDeclaredField(voiceManager, ArrayUtils.get(names, i), true)), value);
					//
				} catch (final IllegalAccessException e) {
					//
					errorOrAssertOrShowException(headless, e);
					//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static String getMp3TagValue(@Nullable final File file, final Predicate<Object> predicate,
			final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		return getMp3TagValue(getMp3TagParirs(file, attributes), predicate);
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
			if (Util.test(predicate,
					string = Util.toString(Util.getValue(Util.cast(Pair.class, IterableUtils.get(pairs, i)))))
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

	@Nullable
	private static List<Pair<String, ?>> getMp3TagParirs(@Nullable final File file, final String... attributes)
			throws BaseException, IOException, IllegalAccessException, InvocationTargetException {
		//
		if (Objects.equals("mp3", getFileExtension(Util.cast(ContentInfo.class,
				testAndApply(Util::isFile, file, new ContentInfoUtil()::findMatch, null))))) {
			//
			final Mp3File mp3File = new Mp3File(file);
			//
			return getMp3TagParirs(
					ObjectUtils.defaultIfNull(Mp3FileUtil.getId3v2Tag(mp3File), Mp3FileUtil.getId3v1Tag(mp3File)),
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
		for (int i = 0; i < length(attributes); i++) {
			//
			final String attribute = ArrayUtils.get(attributes, i);
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
				if ((m = IterableUtils.get(methods, 0)) != null && m.getParameterCount() == 0) {
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
	private static File getAudioFile(final boolean headless, final Voice voice,
			final DefaultTableModel defaultTableModel) {
		//
		return getAudioFile(headless, new JFileChooser("."), voice, defaultTableModel);
		//
	}

	@Nullable
	private static File getAudioFile(final boolean headless, final JFileChooser jfc, final Voice voice,
			final DefaultTableModel defaultTableModel) {
		//
		setFileSelectionMode(jfc, JFileChooser.FILES_ONLY);
		//
		if (!headless && showOpenDialog(jfc, null) != JFileChooser.APPROVE_OPTION) {
			//
			clear(defaultTableModel);
			//
			ifElse(defaultTableModel != null,
					() -> Util.addRow(defaultTableModel,
							new Object[] { getText(voice), getRomaji(voice), NO_FILE_SELECTED }),
					() -> testAndRun(!GraphicsEnvironment.isHeadless() && !isTestMode(),
							() -> JOptionPane.showMessageDialog(null, NO_FILE_SELECTED)));
			//
			return null;
			//
		} // if
			//
		return getSelectedFile(jfc);
		//
	}

	@Nullable
	private static File getSelectedFile(@Nullable final JFileChooser instance) {
		return instance != null ? instance.getSelectedFile() : null;
	}

	private static <E extends Throwable> void ifElse(final boolean condition, final FailableRunnable<E> runnableTrue,
			final FailableRunnable<E> runnableFalse) throws E {
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

	private static void setFileSelectionMode(@Nullable final JFileChooser instance, final int mode) {
		if (instance != null) {
			instance.setFileSelectionMode(mode);
		}
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
					(a, b) -> Util.addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonHeadlessAndnonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} else if (!Util.exists(file)) {
			//
			testAndAccept((a, b) -> a != null, tmImportException,
					String.format("File \"%1$s\" does not exist", Util.getAbsolutePath(file)),
					//
					(a, b) -> Util.addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
					//
					(a, b) -> testAndRun(nonHeadlessAndnonTest, () -> JOptionPane.showMessageDialog(null, b))
			//
			);
			//
			return;
			//
		} else if (!Util.isFile(file)) {
			//
			testAndAccept((a, b) -> a != null, tmImportException, "Not A Regular File Selected",
					//
					(a, b) -> Util.addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
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
					(a, b) -> Util.addRow(a, new Object[] { getText(voice), getRomaji(voice), b }),
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
	private static Voice searchByTextAndRomaji(@Nullable final VoiceMapper instance, final String text,
			final String romaji) {
		return instance != null ? instance.searchByTextAndRomaji(text, romaji) : null;
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
					instance.insertVoiceListId(getId(ObjectUtils.defaultIfNull(voiceListOld, voiceList)), voiceId);
					//
				} // for
					//
			} // if
				//
		} // if
			//
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
	private static Integer getId(@Nullable final VoiceList instance) {
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

	@Nullable
	private static String getAlgorithm(@Nullable final MessageDigest instance) {
		return instance != null ? instance.getAlgorithm() : null;
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
	private static String getRomaji(@Nullable final Voice instance) {
		return instance != null ? instance.getRomaji() : null;
	}

	@Nullable
	private static String getText(@Nullable final Voice instance) {
		return instance != null ? instance.getText() : null;
	}

	@Nullable
	private File generateTtsAudioFile(final boolean headless, final boolean nonTest, final Voice voice)
			throws IllegalAccessException, InvocationTargetException {
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
			return null;
			//
		} // if
			//
		File file = null;
		//
		if (speechApi != null) {
			//
			try {
				//
				speechApi.writeVoiceToFile(Util.getText(tfTextImport), voiceId
				//
						, Util.intValue(getRate(), 0)// rate
						//
						, Math.min(Math.max(Util.intValue(getValue(jsSpeechVolume), 100), 0), 100)// volume
						, null, file = createTempFile(randomAlphabetic(TEMP_FILE_MINIMUM_PREFIX_LENGTH), null)
				//
				);
				//
				if (Objects.equals("wav", getFileExtension(Util.cast(ContentInfo.class,
						testAndApply(Objects::nonNull, file, new ContentInfoUtil()::findMatch, null))))) {
					//
					final ByteConverter byteConverter = getByteConverter(configurableListableBeanFactory, FORMAT,
							Util.getSelectedItem(cbmAudioFormatExecute));
					//
					if (byteConverter != null) {
						//
						FileUtils.writeByteArrayToFile(file,
								byteConverter.convert(Files.readAllBytes(Path.of(Util.toURI(file)))));
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
		setLanguage(voice, StringUtils.defaultIfBlank(getLanguage(voice), ObjIntFunctionUtil.apply(
				languageCodeToTextObjIntFunction, SpeechApi.getVoiceAttribute(speechApi, voiceId, LANGUAGE), 16)));
		//
		setSource(voice, StringUtils.defaultIfBlank(getSource(voice),
				Provider.getProviderName(Util.cast(Provider.class, speechApi))));
		//
		return file;
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

	private static File createTempFile(@Nullable final String prefix, @Nullable final String suffix)
			throws IllegalAccessException, InvocationTargetException {
		//
		final List<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(File.class), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), "createTempFile")
						&& Arrays.equals(new Class<?>[] { String.class, String.class }, Util.getParameterTypes(x))));
		//
		return Util.cast(File.class,
				prefix != null
						? invoke(testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null),
								null, prefix, suffix)
						: null);
		//
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
		for (int i = 0; i < length(values); i++) {
			//
			result |= Util.test(predicate, ArrayUtils.get(values, i));
			//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
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
			final Field f = IterableUtils.get(fs, 0);
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
				setRenderer(jcbVoiceIdLocal = new JComboBox<>(cbmVoiceIdLocal),
						ConverterUtil.convert(voiceIdListCellRendererConverter,
								Util.getRenderer(Util.cast(JComboBox.class, jcbVoiceIdLocal))));
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

	@Nullable
	private static Voice createVoice(@Nullable final ObjectMapper objectMapper,
			@Nullable final VoiceManagerImportSinglePanel instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Voice voice = new Voice();
		//
		voice.setLanguage(Util.getText(instance.tfLanguage));
		//
		voice.setText(Util.getText(instance.tfTextImport));
		//
		setSource(voice, Util.getText(instance.tfSource));
		//
		voice.setRomaji(Util.getText(instance.tfRomaji));
		//
		voice.setHiragana(Util.getText(instance.tfHiragana));
		//
		voice.setKatakana(Util.getText(instance.tfKatakana));
		//
		voice.setYomi(Util.cast(Yomi.class, Util.getSelectedItem(instance.cbmYomi)));
		//
		setListNames(voice,
				Util.toList(Util.map(Util.stream(getObjectList(objectMapper, Util.getText(instance.tfListNames))),
						x -> Util.toString(x))));
		//
		voice.setJlptLevel(Util.toString(Util.getSelectedItem(instance.cbmJlptLevel)));
		//
		voice.setIpaSymbol(Util.getText(instance.tfIpaSymbol));
		//
		voice.setIsKanji(Util.cast(Boolean.class, Util.getSelectedItem(instance.cbmIsKanji)));
		//
		voice.setJouYouKanji(Util.cast(Boolean.class, Util.getSelectedItem(instance.cbmJouYouKanJi)));
		//
		voice.setGaKuNenBeTsuKanJi(Util.toString(Util.getSelectedItem(instance.cbmGaKuNenBeTsuKanJi)));
		//
		voice.setPronunciationPageUrl(Util.getText(instance.tfPronunciationPageUrl));
		//
		return voice;
		//
	}

	private static void setListNames(@Nullable final Voice instance, final Iterable<String> listNames) {
		if (instance != null) {
			instance.setListNames(listNames);
		}
	}

	private static void setSource(@Nullable final Voice instance, final String source) {
		if (instance != null) {
			instance.setSource(source);
		}
	}

	private static void setPitchAccentImage(@Nullable final Voice instance, final ByteArray pitchAccentImage) {
		if (instance != null) {
			instance.setPitchAccentImage(pitchAccentImage);
		}
	}

	/**
	 * Handle the case if "Pronunciation" is selected and "Pronunciation Audio
	 * Format" is selected
	 */
	private static File getPronunciationAudioFileByAudioFormat(final Pronunciation pronunciation,
			@Nullable final Object pronounicationAudioFormat) {
		//
		URL url = null;
		//
		try {
			//
			url = testAndApply(StringUtils::isNotBlank,
					Util.get(getAudioUrls(pronunciation), pronounicationAudioFormat), x -> new URI(x).toURL(), null);
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final File file = testAndApply(Objects::nonNull, StringUtils.substringAfterLast(Util.getFile(url), '/'),
				File::new, null);
		//
		try (final InputStream is = Util.openStream(url)) {
			//
			if (and(Objects::nonNull, is, file)) {
				//
				FileUtils.copyInputStreamToFile(is, file);
				//
			} // if
				//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return file;
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
		for (int i = 0; i < length(values); i++) {
			//
			result &= Util.test(predicate, ArrayUtils.get(values, i));
			//
		} // for
			//
		return result;
		//
	}

	private static ByteArray createByteArray(@Nullable final RenderedImage image, @Nullable final String format,
			final boolean headless) {
		//
		byte[] bs = null;
		//
		ContentInfo ci = null;
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			if (image != null && format != null) {
				//
				ImageIO.write(image, format, baos);
				//
			} // if
				//
			ci = new ContentInfoUtil().findMatch(bs = toByteArray(baos));
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
		final ByteArray byteArray = new ByteArray();
		//
		byteArray.setContent(bs);
		//
		byteArray.setMimeType(getMimeType(ci));
		//
		return byteArray;
		//
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static byte[] toByteArray(@Nullable final ByteArrayOutputStream instance) {
		return instance != null ? instance.toByteArray() : null;
	}

	@Nullable
	private static String getImageFormat(@Nullable final IValue0<String> iValue0, final Iterable<String> imageFormats) {
		//
		String imageFormat = null;
		//
		if (iValue0 != null) {
			//
			imageFormat = IValue0Util.getValue0(iValue0);
			//
		} else {
			//
			if (!IterableUtils.isEmpty(imageFormats)) {
				//
				imageFormat = IterableUtils.get(imageFormats, 0);
				//
			} // if
				//
		} // if
			//
		return imageFormat;
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
	private static BufferedImage getPitchAccentImage(@Nullable final Pronunciation instance) {
		return instance != null ? instance.getPitchAccentImage() : null;
	}

	@Nullable
	private static String getSource(@Nullable final Voice instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; i < length(abs); i++) {
			//
			if ((ab = ArrayUtils.get(abs, i)) == null) {
				//
				continue;
				//
			} // if
				//
			Util.addActionListener(ab, actionListener);
			//
		} // for
			//
	}

	private static void addChangeListener(final ChangeListener changeListener, final JSlider instance,
			@Nullable final JSlider... vs) {
		//
		addChangeListener(instance, changeListener);
		//
		for (int i = 0; i < length(vs); i++) {
			//
			addChangeListener(ArrayUtils.get(vs, i), changeListener);
			//
		} // for
			//
	}

	private static void addChangeListener(@Nullable final JSlider instance, final ChangeListener changeListener) {
		if (instance != null) {
			instance.addChangeListener(changeListener);
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> a, final BiConsumer<T, U> b) {
		if (Util.test(instance, t, u)) {
			Util.accept(a, t, u);
		} else {
			Util.accept(b, t, u);
		} // if
	}

	@Nullable
	private static Integer getValue(@Nullable final JSlider instance) {
		return instance != null ? Integer.valueOf(instance.getValue()) : null;
	}

	private void keyReleasedForTextImport(final JTextComponent jTextComponent) {
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
			Util.setSelectedItem(cbmJouYouKanJi, null);
			//
		} else if (jouYouKanJiList != null) {
			//
			Util.setSelectedItem(cbmJouYouKanJi,
					StringUtils.length(text) <= orElse(max(mapToInt(Util.stream(list), StringUtils::length)), 0)
							? Util.contains(list, text)
							: null);
			//
		} // if
			//
	}

	private static int orElse(@Nullable final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	@Nullable
	private static OptionalInt max(@Nullable final IntStream instance) {
		return instance != null ? instance.max() : null;
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

	private static void keyReleasedForTextImport(final Multimap<String, String> multiMap,
			final JTextComponent jTextComponent, final ComboBoxModel<String> comboBoxModel) {
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
			Util.setSelectedItem(comboBoxModel, IterableUtils.get(list, 0));
			//
		} else if (size < 1) {
			//
			Util.setSelectedItem(comboBoxModel, null);
			//
		} else {
			//
			throw new IllegalStateException();
			//
		} // if
			//
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

	private static void addSpeedButtons(@Nullable final VoiceManagerImportSinglePanel instance,
			final Container container, final Range<Integer> range, final int width) {
		//
		if (!(RangeUtil.hasLowerBound(range) && RangeUtil.hasUpperBound(range) && RangeUtil.lowerEndpoint(range) != null
				&& RangeUtil.upperEndpoint(range) != null)) {
			//
			return;
			//
		} // if
			//
		add(container, new JLabel(SPEECH_RATE), String.format(ALIGN_FORMAT, "50%"));
		//
		final JSlider jsSpeechRate = instance != null
				? instance.jsSpeechRate = new JSlider(Util.intValue(RangeUtil.lowerEndpoint(range), 0),
						Util.intValue(RangeUtil.upperEndpoint(range), 0))
				: null;
		//
		add(container, jsSpeechRate, String.format(WMIN_ONLY_FORMAT, width));
		//
		setMajorTickSpacing(jsSpeechRate, 1);
		//
		setPaintTicks(jsSpeechRate, true);
		//
		setPaintLabels(jsSpeechRate, true);
		//
		final JTextComponent tfSpeechRate = instance != null ? instance.tfSpeechRate = new JTextField() : null;
		//
		add(container, tfSpeechRate, String.format("align %1$s %1$s,width %2$s", "50%", 20));
		//
		Util.setEditable(tfSpeechRate, false);
		//
		setValue(jsSpeechRate,
				PropertyResolverUtil.getProperty(instance != null ? instance.propertyResolver : null,
						"org.springframework.context.support.VoiceManager.speechRate"),
				a -> instance.stateChanged(new ChangeEvent(a)));
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
							&& StringUtils.startsWithIgnoreCase(Util.getName(x), "get" + string)));
			//
			final int size = CollectionUtils.size(ms);
			//
			if (size == 1) {
				//
				setValue(instance, IterableUtils.get(ms, 0), consumer, GraphicsEnvironment.isHeadless());
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

	private static void setValue(@Nullable final JSlider instance, final int n) {
		if (instance != null) {
			instance.setValue(n);
		}
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

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
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

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (Util.test(instance, t, u)) {
			FailableBiConsumerUtil.accept(consumer, t, u);
		} // if
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

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static void setPreferredWidth(final int width, @Nullable final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; i < length(cs); i++) {
			//
			if ((c = ArrayUtils.get(cs, i)) == null || (d = Util.getPreferredSize(c)) == null) {
				//
				continue;
				//
			} // if
				//
			setPreferredSize(c, new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	@Nullable
	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
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

	@Nullable
	private static Integer valueOf(@Nullable final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static <T> void accept(final Consumer<? super T> action, final T a, final T b,
			@Nullable final T... values) {
		//
		Util.accept(action, a);
		//
		Util.accept(action, b);
		//
		for (int i = 0; i < length(values); i++) {
			//
			Util.accept(action, ArrayUtils.get(values, i));
			//
		} // for
			//
	}

	@Nullable
	private static List<Object> getObjectList(@Nullable final ObjectMapper objectMapper, @Nullable final Object value) {
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

	private static void setBackground(@Nullable final Component instance, @Nullable final Color color) {
		if (instance != null) {
			instance.setBackground(color);
		}
	}

	private static ListCellRenderer<Pronunciation> createPronunciationListCellRenderer(
			@Nullable final ListCellRenderer<?> lcr) {
		//
		return new ListCellRenderer<>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends Pronunciation> list,
					final Pronunciation value, final int index, boolean isSelected, boolean cellHasFocus) {
				//
				final BufferedImage pitchAccentImage = getPitchAccentImage(value);
				//
				// If the "java.awt.image.BufferedImage" instance is instantiated by
				// "io.github.toolfactory.narcissus.Narcissus.allocateInstance(java.lang.Class)",
				// check if the "raster" field in the "java.awt.image.BufferedImage" instance is
				// not null
				//
				Object raster = null;
				//
				try {
					//
					raster = testAndApply(Objects::nonNull, pitchAccentImage,
							x -> Narcissus.getObjectField(x, Util.getDeclaredField(Util.getClass(x), "raster")), null);
					//
				} catch (final NoSuchFieldException e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
				if (pitchAccentImage != null && raster != null) {
					//
					return Util.getListCellRendererComponent(((ListCellRenderer) lcr), list,
							new ImageIcon(pitchAccentImage), index, isSelected, cellHasFocus);
					//
				} // if
					//
				return Util.getListCellRendererComponent(((ListCellRenderer) lcr), list, new ImageIcon(), index,
						isSelected, cellHasFocus);
				//
			}

			@Nullable
			private static BufferedImage getPitchAccentImage(@Nullable final Pronunciation instance) {
				return instance != null ? instance.getPitchAccentImage() : null;
			}

		};
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

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	@Nullable
	private static Map<String, String> createYomiNameMap() {
		//
		final Class<?> nameClass = Util.forName("domain.Voice$Name");
		//
		final Stream<Field> stream = testAndApply(Objects::nonNull, Util.getDeclaredFields(Yomi.class), Arrays::stream,
				null);
		//
		return createYomiNameMap(Util.toList(
				//
				Util.filter(Util.map(stream, f -> {
					//
					final List<?> objects = Util.toList(FailableStreamUtil.stream(new FailableStream<>(
							Util.filter(testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null),
									a -> Objects.equals(Util.annotationType(a), nameClass)))
							.map(a -> {
								//
								final List<Method> ms = Util.toList(Util.filter(testAndApply(Objects::nonNull,
										Util.getDeclaredMethods(Util.annotationType(a)), Arrays::stream, null),
										ma -> Objects.equals(Util.getName(ma), VALUE)));
								//
								if (CollectionUtils.isEmpty(ms)) {
									//
									return false;
									//
								} // if
									//
								final Method m = IterableUtils.size(ms) == 1 ? IterableUtils.get(ms, 0) : null;
								//
								if (m != null) {
									//
									return Narcissus.invokeMethod(a, m);
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
						return Pair.of(Util.getName(f), Util.toString(IterableUtils.get(objects, 0)));
						//
					} // if
						//
					throw new IllegalStateException();
					//
				}), Objects::nonNull)));
		//
	}

	@Nullable
	private static Annotation[] getDeclaredAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	@Nullable
	private static Map<String, String> createYomiNameMap(final Iterable<Pair<String, String>> pairs) {
		//
		Map<String, String> map = null;
		//
		if (Util.iterator(pairs) != null) {
			//
			for (final Pair<String, String> pair : pairs) {
				//
				if (pair == null) {
					//
					continue;
					//
				} // if
					//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Util.getKey(pair), Util.getValue(pair));
				//
			} // for
				//
		} // if
			//
		return map;
		//
	}

	private static void setRenderer(@Nullable final JComboBox<?> instance, final ListCellRenderer<?> aRenderer) {
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
			//
		final Method[] ms = Util.getDeclaredMethods(JComboBox.class);
		//
		Method m = null;
		//
		for (int i = 0; i < length(ms); i++) {
			//
			if (!(Boolean.logicalAnd(Objects.equals(Util.getName(m = ArrayUtils.get(ms, i)), "setRenderer"),
					Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { ListCellRenderer.class })))) {
				//
				continue;
				//
			} // if
				//
			testAndAccept((a, b) -> a != null && b != null, instance, m,
					(a, b) -> Narcissus.invokeMethod(a, b, aRenderer));
			//
		} // for
			//
	}

	@Nullable
	private static Integer valueOf(final String instance, final int base) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance, base) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static void setJlptVocabularyAndLevel(@Nullable final VoiceManagerImportSinglePanel instance) {
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
		for (int i = Util.getSize(mcbmJlptVocabulary) - 1; i >= 0; i--) {
			//
			removeElementAt(mcbmJlptVocabulary, i);
			//
		} // for
			//
		Util.setSelectedItem(instance.cbmJlptLevel, null);
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
			forEach(temp, x -> Util.addElement(mcbmJlptVocabulary, x));
			//
			if (IterableUtils.size(temp) > 1) {
				//
				Util.setSelectedItem(instance.cbmJlptLevel, null);
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						Util.toList(
								Util.distinct(Util.map(Util.stream(temp), VoiceManagerImportSinglePanel::getLevel))),
						x -> setSelectedItemByString(cbmJlptLevel, IterableUtils.get(x, 0)));
				//
				if (instance.jcbJlptVocabulary != null) {
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
			if (instance.jcbJlptVocabulary != null) {
				//
				instance.itemStateChanged(new ItemEvent(instance.jcbJlptVocabulary, 0, "", 0));
				//
			} // if
				//
		} // if
			//
	}

	private static void setSelectedItemByString(final ComboBoxModel<String> cbm, final String string) {
		//
		IntList intList = null;
		//
		for (int i = 0; i < Util.getSize(cbm); i++) {
			//
			if (StringUtils.equalsAnyIgnoreCase(Util.getElementAt(cbm, i), string)) {
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
			Util.setSelectedItem(cbm, Util.getElementAt(cbm, intList.get(0)));
			//
		} // if
			//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			@Nullable final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	@Nullable
	private static String getLevel(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getLevel() : null;
	}

	@Nullable
	private static String getKana(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKana() : null;
	}

	@Nullable
	private static String getKanji(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKanji() : null;
	}

	private static <T, E extends Throwable> void forEach(final Iterable<T> items,
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

	private static void removeElementAt(@Nullable final MutableComboBoxModel<?> instance, final int index) {
		if (instance != null) {
			instance.removeElementAt(index);
		}
	}

	@Nullable
	private static javax.swing.text.Document getDocument(@Nullable final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
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
			Util.forEach(reverseRange(0, Util.getSize(mcbmPronunciation)), i -> removeElementAt(mcbmPronunciation, i));
			//
		} // if
			//
	}

	/**
	 * @see <a href="https://stackoverflow.com/a/24011264">list - Java 8 stream
	 *      reverse order - Stack Overflow</a>
	 */
	@Nullable
	private static IntStream reverseRange(final int from, final int to) {
		return map(IntStream.range(from, to), i -> to - i + from - 1);
	}

	@Nullable
	private static IntStream map(@Nullable final IntStream instance, @Nullable final IntUnaryOperator mapper) {
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || mapper != null)
				? instance.map(mapper)
				: instance;
	}

	private static void addDocumentListener(@Nullable final javax.swing.text.Document instance,
			final DocumentListener listener) {
		if (instance != null) {
			instance.addDocumentListener(listener);
		}
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

	private static <E> void add(@Nullable final List<E> instance, final int index, @Nullable final E element) {
		if (instance != null) {
			instance.add(index, element);
		}
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}