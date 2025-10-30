package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.MethodUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.IFLT;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LDCUtil;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.ElementUtil;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.net.HostAndPort;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class AivisSpeechRestApiJPanel extends JPanel implements InitializingBean, ActionListener, ItemListener {

	private static final long serialVersionUID = -1383116618784980170L;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Javassist Class Name Pattern")
	private static Pattern PATTERN_JAVASSIST_CLASS_NAME = null;

	private static Pattern PATTERN_FILE_EXTENSION = null;

	@Note("Host")
	private JTextComponent tfHost = null;

	@Note("Port")
	private JTextComponent tfPort = null;

	private JTextComponent tfText = null;

	@Note("Speakers")
	private AbstractButton btnSpeakers = null;

	@Note("Audio Query")
	private AbstractButton btnAudioQuery = null;

	@Note("View Audio Query")
	private AbstractButton btnViewAudioQuery = null;

	@Note("Synthesis")
	private AbstractButton btnSynthesis = null;

	@Note("View Portrait")
	private AbstractButton btnViewPortrait = null;

	@Note("View Icon")
	private AbstractButton btnViewIcon = null;

	@Note("Copy Voice Sample Transcript To Text")
	private AbstractButton btnCopyVoiceSampleTranscriptToText = null;

	@Note("Play Voice Sample Transcript")
	private AbstractButton btnPlayVoiceSampleTranscript = null;

	private AbstractButton btnPlay = null;

	private JComboBox<Speaker> jcbSpeaker = null;

	private DefaultComboBoxModel<Speaker> dcbmSpeaker = null;

	private JComboBox<Style> jcbStyle = null;

	private DefaultComboBoxModel<Style> dcbmStyle = null;

	private JComboBox<String> jcbVoiceSampleTranscript = null;

	private DefaultComboBoxModel<String> dcbmVoiceSampleTranscript = null;

	private String audioQuery = null;

	@Note("Portrait")
	private JLabel jLabelPortrait = null;

	private JLabel jLabelIcon = null;

	private Window window = null;

	private AivisSpeechRestApiJPanel() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		if (Narcissus.getField(this, Narcissus.findField(getClass(), "component")) == null) {
			//
			return;
			//
		} // if
			//
		add(new JLabel("Host"));
		//
		add(tfHost = new JTextField(), String.format("wmin %1$spx", 100));
		//
		add(new JLabel(":"));
		//
		final NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		//
		if (numberFormat != null) {
			//
			numberFormat.setGroupingUsed(false);
			//
		} // if
			//
		final NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
		//
		final int[] ints = getInts(HostAndPort.class);
		//
		if (ints != null && ints.length == 2) {
			//
			Arrays.sort(ints);
			//
			numberFormatter.setMinimum(ints[0]);
			//
			numberFormatter.setMaximum(ints[1]);
			//
		} // if
			//
		final String wrap = "wrap";
		//
		add(tfPort = new JFormattedTextField(numberFormatter),
				String.format("%1$s,wmin %2$spx,span %3$s", wrap, 42, 2));
		//
		add(new JLabel());
		//
		add(btnSpeakers = new JButton("Speakers"), wrap);
		//
		add(new JLabel("Speaker"));
		//
		final ListCellRenderer<? super Speaker> lcrSpeaker = (jcbSpeaker = new JComboBox<>(
				dcbmSpeaker = new DefaultComboBoxModel<>())).getRenderer();
		//
		jcbSpeaker.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			//
			if (value != null) {
				//
				return new JLabel(value.name);
				//
			} // if
				//
			return getListCellRendererComponent(lcrSpeaker, list, value, index, isSelected, cellHasFocus);
			//
		});
		//
		add(jcbSpeaker);
		//
		add(jLabelPortrait = new JLabel(), String.format("span %1$s", 2));
		//
		add(btnViewPortrait = new JButton("View"), wrap);
		//
		add(new JLabel("Style"));
		//
		final ListCellRenderer<? super Style> lcrStyle = (jcbStyle = new JComboBox<>(
				dcbmStyle = new DefaultComboBoxModel<>())).getRenderer();
		//
		jcbStyle.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			//
			if (value != null) {
				//
				return new JLabel(value.name);
				//
			} // if
				//
			return getListCellRendererComponent(lcrStyle, list, value, index, isSelected, cellHasFocus);
			//
		});
		//
		add(jcbStyle);
		//
		add(jLabelIcon = new JLabel(), String.format("span %1$s", 2));
		//
		add(btnViewIcon = new JButton("View"), wrap);
		//
		add(new JLabel("Voice Sample Transcript"), String.format("span %1$s", 3));
		//
		add(jcbVoiceSampleTranscript = new JComboBox<>(dcbmVoiceSampleTranscript = new DefaultComboBoxModel<>()),
				String.format("span %1$s", 3));
		//
		add(btnCopyVoiceSampleTranscriptToText = new JButton("Copy"));
		//
		add(btnPlayVoiceSampleTranscript = new JButton("Play"), String.format("%1$s,span %2$s", wrap, 3));
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), String.format("growx,span %1$s", 5));
		//
		add(btnAudioQuery = new JButton("Audio Query"), String.format("span %1$s", 3));
		//
		add(btnViewAudioQuery = new JButton("View"), wrap);
		//
		add(new JLabel());
		//
		add(btnSynthesis = new JButton("Synthesis"));
		//
		add(btnPlay = new JButton("Play"), String.format("span %1$s", 3));
		//
		addItemListener(this, jcbSpeaker, jcbStyle, jcbVoiceSampleTranscript);
		//
		addActionListener(this,
				Util.toList(Util.map(
						Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
								x -> Util.isAssignableFrom(AbstractButton.class, Util.getType(x))),
						x -> Util.cast(AbstractButton.class, Narcissus.getField(this, x)))));
		//
	}

	private static void addItemListener(final ItemListener itemListener, @Nullable final ItemSelectable... iss) {
		//
		ItemSelectable is = null;
		//
		for (int i = 0; iss != null && i < iss.length; i++) {
			//
			if ((is = ArrayUtils.get(iss, i)) == null) {
				//
				continue;
				//
			} // if
				//
			is.addItemListener(itemListener);
			//
		} // for
			//
	}

	private static void addActionListener(final ActionListener actionListener,
			@Nullable final Iterable<AbstractButton> iterable) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			if ((ab = IterableUtils.get(iterable, i)) == null) {
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

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<? super E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
	}

	@Nullable
	private static int[] getInts(final Class<?> clz) throws IOException {
		//
		int[] ints = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				StringUtils.join("/", replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Iterable<Method> ms = Util
					.collect(
							Util.filter(
									testAndApply(Objects::nonNull, JavaClassUtil.getMethods(javaClass), Arrays::stream,
											null),
									m -> m != null
											&& Objects.equals(FieldOrMethodUtil.getName(m),
													"isValidPort")
											&& CollectionUtils
													.isEqualCollection(
															Util.collect(
																	Util.map(
																			Arrays.stream(
																					MethodUtil.getArgumentTypes(m)),
																			TypeUtil::getClassName),
																	Collectors.toList()),
															Collections.singleton("int"))),
							Collectors.toList());
			//
			if (IterableUtils.size(ms) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Instruction[] instructions = InstructionListUtil.getInstructions(testAndApply(Objects::nonNull,
					getCode(getCode(IterableUtils.size(ms) == 1 ? IterableUtils.get(ms, 0) : null)),
					InstructionList::new, null));
			//
			Instruction instruction = null;
			//
			Number number = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = ArrayUtils.get(instructions, i)) instanceof IFLT) {
					//
					ints = ArrayUtils.add(ints, 0);
					//
				} else if (instruction instanceof LDC ldc && (number = Util.cast(Number.class,
						LDCUtil.getValue(ldc, new ConstantPoolGen(javaClass.getConstantPool())))) != null) {
					//
					ints = ArrayUtils.add(ints, number.intValue());
					//
				} // if
					//
			} // for
				//
		} // try
			//
		return ints;
		//
	}

	@Nullable
	private static byte[] getCode(@Nullable final Code instance) {
		return instance != null ? instance.getCode() : null;
	}

	@Nullable
	private static Code getCode(@Nullable final Method instance) {
		return instance != null ? instance.getCode() : null;
	}

	public static void main(final String[] args) throws Exception {
		//
		final AivisSpeechRestApiJPanel instance = new AivisSpeechRestApiJPanel();
		//
		instance.afterPropertiesSet();
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		testAndAccept(Util::containsKey, properties, "host",
				(a, b) -> Util.setText(instance.tfHost, Util.toString(Util.get(a, b))));
		//
		testAndAccept(Util::containsKey, properties, "port",
				(a, b) -> Util.setText(instance.tfPort, Util.toString(Util.get(a, b))));
		//
		testAndAccept(Util::containsKey, properties, "text",
				(a, b) -> Util.setText(instance.tfText, Util.toString(Util.get(a, b))));
		//
		final JFrame jFrame = Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), isTestMode()) ? new JFrame() : null;
		//
		instance.window = jFrame;
		//
		setDefaultCloseOperation(jFrame, WindowConstants.EXIT_ON_CLOSE);
		//
		add(jFrame, instance);
		//
		pack(jFrame);
		//
		setVisible(jFrame, true);
		//
	}

	private static boolean isTestMode() {
		//
		return Util.forName("org.junit.jupiter.api.Test") == null;
		//
	}

	private static void setVisible(@Nullable final Component instance, final boolean visible) {
		if (instance != null) {
			instance.setVisible(visible);
		}
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// objectLock
			//
		final Class<?> clz = Util.getClass(instance);
		//
		Field f = getFieldByName(clz, "objectLock");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
			// graphicsConfig
			//
		if ((f = getFieldByName(clz, "graphicsConfig")) != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
		instance.pack();
		//
	}

	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final Iterable<Field> fs = Util.collect(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						x -> Objects.equals(Util.getName(x), name)),
				Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static void add(@Nullable final Container instance, final Component component) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// component
			//
		final Field f = getFieldByName(Util.getClass(instance), "component");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
		instance.add(component);
		//
	}

	private static void setDefaultCloseOperation(@Nullable final JFrame instance, final int defaultCloseOperation) {
		if (instance != null) {
			instance.setDefaultCloseOperation(defaultCloseOperation);
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (Util.test(instance, t, u)) {
			FailableBiConsumerUtil.accept(consumer, t, u);
		} // if
	}

	@Nullable
	private static String replace(@Nullable final Strings instance, final String text, final String searchString,
			final String replacement) {
		return instance != null ? instance.replace(text, searchString, replacement) : null;
	}

	private static class Speaker {

		@Note("Name")
		private String name;

		private String speakerUuid;

		private List<Style> styles;

		private SpeakerInfo speakerInfo;

	}

	private static class Style {

		@Note("id")
		private String id;

		private String name;

		private StyleInfo styleInfo;

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnSpeakers)) {
			//
			removeAllElements(dcbmSpeaker);
			//
			try {
				//
				final HostAndPort hostAndPort = createHostAndPort();
				//
				FailableStreamUtil.forEach(new FailableStream<>(Util.stream(speakers(hostAndPort))),
						x -> setStyleInfo(x, hostAndPort, dcbmSpeaker));
				//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // if
			//
		final Iterable<ToBooleanBiFunction<AivisSpeechRestApiJPanel, Object>> functions = Arrays.asList(
				AivisSpeechRestApiJPanel::actionPerformed1, AivisSpeechRestApiJPanel::actionPerformed2,
				AivisSpeechRestApiJPanel::actionPerformed3);
		//
		ToBooleanBiFunction<AivisSpeechRestApiJPanel, Object> function = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((function = IterableUtils.get(functions, i)) != null && function.applyAsBoolean(this, source)) {
				//
				break;
				//
			} // if
				//
		} // for
			//
	}

	private static void setStyleInfo(@Nullable final Speaker speaker, final HostAndPort hostAndPort,
			final DefaultComboBoxModel<Speaker> dcbmSpeaker) throws IOException, URISyntaxException {
		//
		if (speaker == null) {
			//
			return;
			//
		} // if
			//
		final SpeakerInfo speakerInfo = speaker.speakerInfo = speakerInfo(hostAndPort, speaker.speakerUuid);
		//
		if (speakerInfo != null) {
			//
			Util.forEach(speaker.styles, y -> {
				//
				if (y != null) {
					//
					y.styleInfo = getStyleInfoById(speakerInfo.styleInfos, y.id);
					//
				} // if
					//
			});
			//
		} // if
			//
		Util.addElement(dcbmSpeaker, speaker);
		//
	}

	private static StyleInfo getStyleInfoById(final Iterable<StyleInfo> styleInfos, final String id) {
		//
		final Iterable<StyleInfo> iterable = Util.collect(Util.filter(
				testAndApply(Objects::nonNull, spliterator(styleInfos), x -> StreamSupport.stream(x, false), null),
				z -> z != null && Objects.equals(z.id, id)), Collectors.toList());
		//
		if (IterableUtils.size(iterable) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(iterable) == 1, iterable, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static class SpeakerInfo {

		private byte[] portrait;

		private List<StyleInfo> styleInfos = null;

	}

	private static class StyleInfo {

		private String id;

		private byte[] icon;

		private List<byte[]> voiceSamples;

		private List<String> voiceSampleTranscripts;

	}

	@Nullable
	private static SpeakerInfo speakerInfo(@Nullable final HostAndPort hostAndPort, final String speakerUuid)
			throws IOException, URISyntaxException {
		//
		SpeakerInfo speakerInfo = null;
		//
		final URIBuilder uriBuilder = new URIBuilder();
		//
		uriBuilder.setScheme("http");
		//
		uriBuilder.setHost(getHost(hostAndPort));
		//
		if (hostAndPort != null && hostAndPort.hasPort()) {
			//
			uriBuilder.setPort(hostAndPort.getPort());
			//
		} // if
			//
		uriBuilder.setPath("speaker_info");
		//
		uriBuilder.addParameter("speaker_uuid", speakerUuid);
		//
		try (final InputStream is = Util.openStream(Util.toURL(uriBuilder.build()))) {
			//
			speakerInfo = speakerInfo(
					Util.cast(Map.class, ObjectMapperUtil.readValue(new ObjectMapper(), is, Object.class)));
			//
		} // try
			//
		return speakerInfo;
		//
	}

	@Nullable
	private static SpeakerInfo speakerInfo(@Nullable final Map<?, ?> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final SpeakerInfo speakerInfo = new SpeakerInfo();
		//
		speakerInfo.portrait = testAndApply(Objects::nonNull, Util.toString(Util.get(instance, "portrait")),
				x -> decode(Base64.getDecoder(), x), null);
		//
		final Iterable<?> iterable = Util.cast(Iterable.class, Util.get(instance, "style_infos"));
		//
		Map<?, ?> map = null;
		//
		StyleInfo styleInfo = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			if ((map = Util.cast(Map.class, IterableUtils.get(iterable, i))) == null) {
				//
				continue;
				//
			} // if
				//
			(styleInfo = new StyleInfo()).id = Util.toString(Util.get(map, "id"));
			//
			styleInfo.icon = testAndApply(Objects::nonNull, Util.toString(Util.get(map, "icon")),
					x -> decode(Base64.getDecoder(), x), null);
			//
			styleInfo.voiceSamples = Util.collect(Util.map(
					Util.stream(toCollection(Util.cast(Iterable.class, Util.get(map, "voice_samples")))),
					x -> testAndApply(Objects::nonNull, Util.toString(x), y -> decode(Base64.getDecoder(), y), null)),
					Collectors.toList());
			//
			styleInfo.voiceSampleTranscripts = Util.collect(Util.map(
					Util.stream(toCollection(Util.cast(Iterable.class, Util.get(map, "voice_sample_transcripts")))),
					Util::toString), Collectors.toList());
			//
			Util.add(speakerInfo.styleInfos = ObjectUtils.getIfNull(speakerInfo.styleInfos, ArrayList::new), styleInfo);
			//
		} // for
			//
		return speakerInfo;
		//
	}

	@Nullable
	private static byte[] decode(@Nullable final Decoder instance, @Nullable final String string) {
		return instance != null && string != null ? instance.decode(string) : null;
	}

	private static Collection<?> toCollection(final Iterable<?> iterable) {
		//
		return Util.collect(
				testAndApply(Objects::nonNull, spliterator(iterable), x -> StreamSupport.stream(x, false), null),
				Collectors.toList());
		//
	}

	@Nullable
	private static <E> Spliterator<E> spliterator(@Nullable final Iterable<E> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	private static boolean actionPerformed1(@Nullable final AivisSpeechRestApiJPanel instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnAudioQuery)) {
			//
			try {
				//
				instance.audioQuery = audioQuery(instance.createHostAndPort(),
						Util.cast(Style.class, Util.getSelectedItem(instance.dcbmStyle)),
						Util.getText(instance.tfText));
				//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnSynthesis)) {
			//
			try {
				//
				final byte[] bs = synthesis(instance.createHostAndPort(),
						Util.cast(Style.class, Util.getSelectedItem(instance.dcbmStyle)), instance.audioQuery);
				//
				if (length(bs) > 0) {
					//
					final JFileChooser jfc = new JFileChooser(".");
					//
					if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						//
						FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), bs);
						//
					} // if
						//
				} // if
					//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnViewAudioQuery)) {
			//
			final JPanel jPanel = new JPanel();
			//
			jPanel.setLayout(new MigLayout());
			//
			final Gson gson = create(new GsonBuilder().setPrettyPrinting());
			//
			final String json = toJson(gson, fromJson(gson, instance.audioQuery, Object.class));
			//
			final JTextArea jTextArea = new JTextArea(json);
			//
			jTextArea.setRows((int) Math.min(count(lines(json)), 40));
			//
			final Dimension screenSize = getScreenSize(Toolkit.getDefaultToolkit());
			//
			jPanel.add(new JScrollPane(jTextArea),
					String.format("wmax %1$s", screenSize != null ? screenSize.getWidth() - 30 : "100%"));
			//
			testAndRun(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), isTestMode()),
					() -> JOptionPane.showMessageDialog(null, jPanel, "Audio Query", JOptionPane.PLAIN_MESSAGE));
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static long count(@Nullable final Stream<?> instance) {
		return instance != null ? instance.count() : 0;
	}

	@Nullable
	private static Stream<String> lines(@Nullable final String instance) {
		//
		final Field f = getFieldByName(Util.getClass(instance), "value");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.lines() : null;
		//
	}

	@Nullable
	private static String toJson(@Nullable final Gson instance, @Nullable final Object src) {
		//
		// formattingStyle
		//
		final Field f = getFieldByName(Util.getClass(instance), "formattingStyle");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.toJson(src) : null;
		//
	}

	@Nullable
	private static <T> T fromJson(@Nullable final Gson instance, final String json, @Nullable final Class<T> classOfT) {
		//
		if (instance == null || classOfT == null) {
			//
			return null;
			//
		} // if
			//
			// typeTokenCache
			//
		final Field f = getFieldByName(Util.getClass(instance), "typeTokenCache");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.fromJson(json, classOfT);
		//
	}

	@Nullable
	private static Gson create(@Nullable final GsonBuilder instance) {
		//
		// factories
		//
		final Field f = getFieldByName(Util.getClass(instance), "factories");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.create() : null;
		//
	}

	private static boolean actionPerformed2(@Nullable final AivisSpeechRestApiJPanel instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnViewPortrait)) {
			//
			final Speaker speaker = Util.cast(Speaker.class, Util.getSelectedItem(instance.jcbSpeaker));
			//
			if (Util.and(speaker != null && speaker.speakerInfo != null, !GraphicsEnvironment.isHeadless(),
					isTestMode())) {
				//
				JOptionPane.showMessageDialog(null,
						testAndApply(Objects::nonNull, speaker.speakerInfo.portrait, ImageIcon::new, null), null,
						JOptionPane.PLAIN_MESSAGE);
				//
			} // if
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnViewIcon)) {
			//
			final Style style = Util.cast(Style.class, Util.getSelectedItem(instance.jcbStyle));
			//
			if (Util.and(style != null && style.styleInfo != null, !GraphicsEnvironment.isHeadless(), isTestMode())) {
				//
				JOptionPane.showMessageDialog(null,
						testAndApply(Objects::nonNull, style.styleInfo.icon, ImageIcon::new, null), null,
						JOptionPane.PLAIN_MESSAGE);
				//
			} // if
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyVoiceSampleTranscriptToText)) {
			//
			Util.setText(instance.tfText, Util.toString(Util.getSelectedItem(instance.dcbmVoiceSampleTranscript)));
			//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed3(@Nullable final AivisSpeechRestApiJPanel instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnPlayVoiceSampleTranscript)) {
			//
			final Style style = Util.cast(Style.class, Util.getSelectedItem(instance.dcbmStyle));
			//
			try {
				//
				if (instance.jcbVoiceSampleTranscript != null) {
					//
					play(style != null ? style.styleInfo : null, instance.jcbVoiceSampleTranscript.getSelectedIndex());
					//
				} // if
					//
			} catch (final Exception e) {
				//
				if (e instanceof RuntimeException runtimeException) {
					//
					throw runtimeException;
					//
				} // if
					//
				throw new RuntimeException(e);
				//
			} // try
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnPlay)) {
			//
			try {
				//
				play(synthesis(instance.createHostAndPort(),
						Util.cast(Style.class, Util.getSelectedItem(instance.dcbmStyle)), instance.audioQuery));
				//
			} catch (final Exception e) {
				//
				throw ObjectUtils.getIfNull(Util.cast(RuntimeException.class, e), () -> new RuntimeException(e));
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

	private static void play(@Nullable final StyleInfo instance, final int index) throws Exception {
		//
		play(testAndApply(x -> IterableUtils.size(x) > index, instance != null ? instance.voiceSamples : null,
				x -> IterableUtils.get(x, index), null));
		//
	}

	private static void play(@Nullable final byte[] bs) throws Exception {
		//
		if (Boolean.logicalAnd(isSupportedAudioFormat(bs),
				length(AudioSystem.getSourceLineInfo(new Line.Info(SourceDataLine.class))) > 0)) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final AudioInputStream ais = AudioSystem.getAudioInputStream(is)) {
				//
				final AudioFormat audioFormat = getFormat(ais);
				//
				final SourceDataLine sourceDataLine = Util.cast(SourceDataLine.class,
						AudioSystem.getLine(new Info(SourceDataLine.class, audioFormat)));
				//
				open(sourceDataLine, audioFormat);
				//
				start(sourceDataLine);
				//
				int read = 0;
				//
				final byte[] buffer = new byte[1024];
				//
				while (ais != null && sourceDataLine != null && read != -1) {
					//
					if ((read = ais.read(buffer, 0, buffer.length)) >= 0) {
						//
						sourceDataLine.write(buffer, 0, read);
						//
					} // if
						//
				} // while
					//
				drain(sourceDataLine);
				//
				close(sourceDataLine);
				//
				return;
				//
			} // try
				//
		} // if
			//
		File file = null;
		//
		try {
			//
			final OperatingSystem operatingSystem = OperatingSystemUtil.getOperatingSystem();
			//
			if (Objects.equals(OperatingSystem.LINUX, operatingSystem)) {
				//
				testAndAccept((a, b) -> b != null,
						file = File.createTempFile(nextAlphanumeric(RandomStringUtils.secureStrong(), 3),
								StringUtils.join(".", Objects.toString(getFileExtension(bs), "m4a"))),
						bs, FileUtils::writeByteArrayToFile);
				//
				final Process process = exec(Runtime.getRuntime(),
						String.format("ffplay -autoexit -nodisp %1$s", Util.getAbsolutePath(file)));
				//
				if (process != null) {
					//
					process.waitFor();
					//
				} // if
					//
				return;
				//
			} else if (Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) {
				//
				final ContentInfo contentInfo = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch,
						null);
				//
				if (Objects.equals(ContentType.WAV, getContentType(contentInfo))) {
					//
					testAndAccept((a, b) -> b != null,
							file = File.createTempFile(nextAlphanumeric(RandomStringUtils.secureStrong(), 3),
									StringUtils.join(".", Objects.toString(getFileExtension(contentInfo)))),
							bs, FileUtils::writeByteArrayToFile);
					//
					final Process process = exec(Runtime.getRuntime(),
							String.format("powershell -c (New-Object Media.SoundPlayer '%1$s').PlaySync();",
									Util.getAbsolutePath(file)));
					//
					if (process != null) {
						//
						process.waitFor();
						//
					} // if
						//
					return;
					//
				} // if
					//
			} // if
				//
		} finally {
			//
			FileUtils.deleteQuietly(file);
			//
		} // try
			//
		throw new UnsupportedOperationException();
		//
	}

	private static void open(final SourceDataLine instance, @Nullable final AudioFormat audioFormat)
			throws LineUnavailableException {
		if (instance != null) {
			instance.open(audioFormat);
		}
	}

	private static void start(final DataLine instance) {
		if (instance != null) {
			instance.start();
		}
	}

	private static void close(@Nullable final AutoCloseable instance) throws Exception {
		if (instance != null) {
			instance.close();
		}
	}

	private static void drain(@Nullable final DataLine instance) {
		if (instance != null) {
			instance.drain();
		}
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static AudioFormat getFormat(@Nullable final AudioInputStream instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static boolean isSupportedAudioFormat(@Nullable final byte[] bs) {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null);
				final AudioInputStream ais = testAndApply(Objects::nonNull, is, AudioSystem::getAudioInputStream,
						null)) {
			//
			return ais != null;
			//
		} catch (final Exception e) {
			//
			return false;
			//
		} // try
			//
	}

	@Nullable
	private static ContentType getContentType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getContentType() : null;
	}

	@Nullable
	private static String getFileExtension(@Nullable final byte[] bs) throws IOException {
		//
		final ContentInfo contentInfo = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
		//
		return getFileExtension(contentInfo);
		//
	}

	@Nullable
	private static String getFileExtension(@Nullable final ContentInfo contentInfo) throws IOException {
		//
		final String[] fileExtensions = contentInfo != null ? contentInfo.getFileExtensions() : null;
		//
		if (length(fileExtensions) == 1) {
			//
			return ArrayUtils.get(fileExtensions, 0);
			//
		} // if
			//
		if (contentInfo != null && Objects.equals(getContentType(contentInfo), ContentType.OTHER)
				&& Objects.equals(contentInfo.getMimeType(), "audio/mp4")
				&& Objects.equals(contentInfo.getName(), "ISO")) {
			//
			final Document document = Jsoup.parse(new URL("https://www.ftyps.com"), 0);
			//
			final String messsage = contentInfo.getMessage();
			//
			final String longestCommonSubstring = Util.orElse(Util.max(
					Util.map(Util.stream(ElementUtil.getElementsByTag(document, "td")),
							x -> longestCommonSubstring(ElementUtil.text(x), messsage)),
					(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null);
			//
			final String string = Util.orElse(Util
					.map(Util.filter(Util.stream(ElementUtil.getElementsByTag(document, "td")),
							x -> equals(Strings.CI, longestCommonSubstring,
									longestCommonSubstring(ElementUtil.text(x), messsage))),
							ElementUtil::text)
					.min((a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null);
			//
			final Matcher matcher = Util.matcher(PATTERN_FILE_EXTENSION = ObjectUtils.getIfNull(PATTERN_FILE_EXTENSION,
					() -> Pattern.compile("\\(\\.([^\\.]+)\\)")), string);
			//
			if (find(matcher) && Util.groupCount(matcher) == 1) {
				//
				return matcher.group(1);
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static boolean find(@Nullable final Matcher instance) {
		return instance != null && instance.find();
	}

	private static boolean equals(@Nullable final Strings instance, final String str1, final String str2) {
		return instance != null && instance.equals(str1, str2);
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
	private static String nextAlphanumeric(@Nullable final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphanumeric(count) : null;
	}

	@Nullable
	private static Process exec(@Nullable final Runtime instance, final String command) throws IOException {
		return instance != null && StringUtils.isNotEmpty(command) ? instance.exec(command) : null;
	}

	private static void testAndRun(final boolean condition, final Runnable runnable) {
		if (condition) {
			Util.run(runnable);
		}
	}

	private static int length(@Nullable final byte[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static Dimension getScreenSize(@Nullable final Toolkit instance) {
		//
		return instance != null && Boolean.logicalOr(!GraphicsEnvironment.isHeadless(), isJavassistProxy(instance))
				? instance.getScreenSize()
				: null;
		//
	}

	private static byte[] synthesis(@Nullable final HostAndPort hostAndPort, @Nullable final Style style,
			final String audioQuery) throws IOException, URISyntaxException {
		//
		final URIBuilder uriBuilder = new URIBuilder();
		//
		uriBuilder.setScheme("http");
		//
		uriBuilder.setHost(getHost(hostAndPort));
		//
		if (hostAndPort != null && hostAndPort.hasPort()) {
			//
			uriBuilder.setPort(hostAndPort.getPort());
			//
		} // if
			//
		uriBuilder.setPath("synthesis");
		//
		uriBuilder.addParameter("speaker", style != null ? style.id : null);
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
				Util.openConnection(Util.toURL(uriBuilder.build())));
		//
		if (httpURLConnection != null) {
			//
			httpURLConnection.setRequestMethod("POST");
			//
			httpURLConnection.addRequestProperty("Content-Type", "application/json");
			//
			httpURLConnection.setDoOutput(true);
			//
			httpURLConnection.setDoInput(true);
			//
		} // if
			//
		try (final OutputStream os = getOutputStream(httpURLConnection)) {
			//
			write(os, getBytes(audioQuery));
			//
		} // try
			//
		try (final InputStream is = Util.getInputStream(httpURLConnection)) {
			//
			return testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
		} // try
			//
	}

	private HostAndPort createHostAndPort() {
		//
		return createHostAndPort(Util.getText(tfHost), Util.getText(tfPort));
		//
	}

	private static HostAndPort createHostAndPort(final String host, final String port) {
		//
		return testAndApply((a, b) -> Util.matches(b), host, Util.matcher(Pattern.compile("\\d{1,5}"), port),
				(a, b) -> testAndApply(Objects::nonNull, a,
						x -> HostAndPort.fromParts(x, NumberUtils.toInt(Util.group(b, 0))), null),
				(a, b) -> testAndApply(Objects::nonNull, a, HostAndPort::fromHost, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) {
		return test(predicate, t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <T, U, R> R apply(@Nullable final BiFunction<T, U, R> instance, final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static String audioQuery(@Nullable final HostAndPort hostAndPort, @Nullable final Style style,
			final String text) throws IOException, URISyntaxException {
		//
		final URIBuilder uriBuilder = new URIBuilder();
		//
		uriBuilder.setScheme("http");
		//
		uriBuilder.setHost(getHost(hostAndPort));
		//
		if (hostAndPort != null && hostAndPort.hasPort()) {
			//
			uriBuilder.setPort(hostAndPort.getPort());
			//
		} // if
			//
		uriBuilder.setPath("audio_query");
		//
		uriBuilder.addParameter("speaker", style != null ? style.id : null);
		//
		uriBuilder.addParameter("text", text);
		//
		final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class,
				Util.openConnection(Util.toURL(uriBuilder.build())));
		//
		if (httpURLConnection != null) {
			//
			httpURLConnection.setRequestMethod("POST");
			//
			httpURLConnection.addRequestProperty("accept", "application/json");
			//
			httpURLConnection.setDoOutput(true);
			//
			httpURLConnection.setDoInput(true);
			//
		} // if
			//
		try (final OutputStream os = getOutputStream(httpURLConnection)) {
			//
			write(os, getBytes(text));
			//
		} // try
			//
		try (final InputStream is = Util.getInputStream(httpURLConnection)) {
			//
			return testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null);
			//
		} // try
			//
	}

	private static void write(@Nullable final OutputStream instance, final byte[] bs) throws IOException {
		//
		if (instance != null && Boolean.logicalOr(bs != null, isJavassistProxy(instance))) {
			//
			instance.write(bs);
			//
		} // if
			//
	}

	private static boolean isJavassistProxy(final Object instance) {
		//
		return Util.matches(Util.matcher(
				PATTERN_JAVASSIST_CLASS_NAME = ObjectUtils.getIfNull(PATTERN_JAVASSIST_CLASS_NAME,
						() -> Pattern.compile("^javassist.util.proxy[.\\w]+\\$\\$[.\\w]+$")),
				Util.getName(Util.getClass(instance))));
		//
	}

	@Nullable
	private static OutputStream getOutputStream(@Nullable final URLConnection instance) throws IOException {
		//
		return instance != null && StringUtils.isNotBlank(Util.getHost(instance.getURL())) ? instance.getOutputStream()
				: null;
		//
	}

	@Nullable
	private static byte[] getBytes(@Nullable final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	private static void removeAllElements(@Nullable final DefaultComboBoxModel<?> instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.collect(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "objects")), Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
		instance.removeAllElements();
		//
	}

	@Nullable
	private static List<Speaker> speakers(@Nullable final HostAndPort hostAndPort)
			throws IOException, URISyntaxException {
		//
		List<Speaker> list = null;
		//
		final URIBuilder uriBuilder = new URIBuilder();
		//
		uriBuilder.setScheme("http");
		//
		uriBuilder.setHost(getHost(hostAndPort));
		//
		if (hostAndPort != null && hostAndPort.hasPort()) {
			//
			uriBuilder.setPort(hostAndPort.getPort());
			//
		} // if
			//
		uriBuilder.setPath("speakers");
		//
		try (final InputStream is = Util.openStream(Util.toURL(uriBuilder.build()))) {
			//
			list = speakers(
					Util.cast(Iterable.class, ObjectMapperUtil.readValue(new ObjectMapper(), is, Object.class)));
			//
		} // try
			//
		return list;
		//
	}

	@Nullable
	private static List<Speaker> speakers(final Iterable<?> iterable) {
		//
		List<Speaker> list = null;
		//
		Map<?, ?> map = null;
		//
		Speaker speaker = null;
		//
		Iterable<?> styles = null;
		//
		Style style = null;
		//
		for (int i = 0; i < IterableUtils.size(iterable); i++) {
			//
			if ((map = Util.cast(Map.class, IterableUtils.get(iterable, i))) == null) {
				//
				continue;
				//
			} // if
				//
			(speaker = new Speaker()).name = Util.toString(Util.get(map, "name"));
			//
			speaker.speakerUuid = Util.toString(Util.get(map, "speaker_uuid"));
			//
			styles = Util.cast(Iterable.class, Util.cast(Iterable.class, Util.get(map, "styles")));
			//
			for (int j = 0; j < IterableUtils.size(styles); j++) {
				//
				if ((map = Util.cast(Map.class, IterableUtils.get(styles, j))) == null) {
					//
					continue;
					//
				} // if
					//
				(style = new Style()).id = Util.toString(Util.get(map, "id"));
				//
				style.name = Util.toString(Util.get(map, "name"));
				//
				Util.add(speaker.styles = ObjectUtils.getIfNull(speaker.styles, ArrayList::new), style);
				//
			} // for
				//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), speaker);
			//
		} // for
			//
		return list;
		//
	}

	@Nullable
	private static String getHost(@Nullable final HostAndPort instance) {
		return instance != null ? instance.getHost() : null;
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, jcbSpeaker)) {
			//
			removeAllElements(dcbmStyle);
			//
			final Speaker speaker = Util.cast(Speaker.class, Util.getSelectedItem(jcbSpeaker));
			//
			if (speaker != null) {
				//
				if (speaker.speakerInfo != null) {
					//
					Image image = null;
					//
					final byte[] portrait = speaker.speakerInfo.portrait;
					//
					try (final InputStream is = testAndApply(Objects::nonNull, portrait, ByteArrayInputStream::new,
							null)) {
						//
						image = Util.getScaledInstance(testAndApply(Objects::nonNull, is, ImageIO::read, null), 25, 25,
								Image.SCALE_SMOOTH);
						//
					} catch (final IOException e) {
						//
						throw new RuntimeException(e);
						//
					} // try
						//
					setIcon(jLabelPortrait, testAndApply(Objects::nonNull, image, ImageIcon::new,
							x -> testAndApply(Objects::nonNull, portrait, ImageIcon::new, null)));
					//
				} // if
					//
				Util.forEach(speaker.styles, x -> Util.addElement(dcbmStyle, x));
				//
			} // if
				//
		} // if
			//
		itemStateChanged(this, source);
		//
	}

	private static void itemStateChanged(@Nullable final AivisSpeechRestApiJPanel instance, final Object source) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (Objects.equals(source, instance.jcbStyle)) {
			//
			final Style style = Util.cast(Style.class, Util.getSelectedItem(instance.jcbStyle));
			//
			if (style != null && style.styleInfo != null) {
				//
				Image image = null;
				//
				final byte[] icon = style.styleInfo.icon;
				//
				try (final InputStream is = testAndApply(Objects::nonNull, icon, ByteArrayInputStream::new, null)) {
					//
					image = Util.getScaledInstance(testAndApply(Objects::nonNull, is, ImageIO::read, null), 25, 25,
							Image.SCALE_SMOOTH);
					//
				} catch (final IOException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
				setIcon(instance.jLabelIcon, testAndApply(Objects::nonNull, image, ImageIcon::new,
						x -> testAndApply(Objects::nonNull, icon, ImageIcon::new, null)));
				//
				removeAllElements(instance.dcbmVoiceSampleTranscript);
				//
				Util.forEach(Util.stream(style.styleInfo.voiceSampleTranscripts),
						x -> Util.addElement(instance.dcbmVoiceSampleTranscript, x));
				//
				pack(instance.window);
				//
			} // if
				//
		} // if
			//
	}

	private static void setIcon(@Nullable final JLabel instance, final Icon icon) {
		if (instance != null) {
			instance.setIcon(icon);
		}
	}

}