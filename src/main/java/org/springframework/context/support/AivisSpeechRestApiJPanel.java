package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.net.HostAndPort;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class AivisSpeechRestApiJPanel extends JPanel implements InitializingBean, ActionListener, ItemListener {

	private static final long serialVersionUID = -1383116618784980170L;

	private static Pattern PATTERN_JAVASSIST_CLASS_NAME = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

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

	private AbstractButton btnSynthesis = null;

	private JComboBox<Speaker> jcbSpeaker = null;

	private DefaultComboBoxModel<Speaker> dcbmSpeaker = null;

	private DefaultComboBoxModel<Style> dcbmStyle = null;

	private String audioQuery = null;

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
		add(tfPort = new JFormattedTextField(numberFormatter), String.format("%1$s,wmin %2$spx", wrap, 42));
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
		jcbSpeaker.addItemListener(this);
		//
		add(jcbSpeaker, wrap);
		//
		add(new JLabel("Style"));
		//
		final JComboBox<Style> jcbStyle = new JComboBox<>(dcbmStyle = new DefaultComboBoxModel<>());
		//
		final ListCellRenderer<? super Style> lcrStyle = jcbStyle.getRenderer();
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
		add(jcbStyle, wrap);
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), "growx");
		//
		add(btnAudioQuery = new JButton("Audio Query"), String.format("span %1$s", 2));
		//
		add(btnViewAudioQuery = new JButton("View"), wrap);
		//
		add(new JLabel());
		//
		add(btnSynthesis = new JButton("Synthesis"));
		//
		addActionListener(this, btnSpeakers, btnAudioQuery, btnViewAudioQuery, btnSynthesis);
		//
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
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
			final Iterable<Method> ms = Util.collect(Util
					.filter(testAndApply(Objects::nonNull, JavaClassUtil.getMethods(javaClass), Arrays::stream, null),
							m -> m != null && Objects.equals(FieldOrMethodUtil.getName(m), "isValidPort")
									&& CollectionUtils.isEqualCollection(
											Util.collect(Util.map(Arrays.stream(m.getArgumentTypes()),
													TypeUtil::getClassName), Collectors.toList()),
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
		final JFrame jFrame = Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(),
				Util.forName("org.junit.jupiter.api.Test") == null) ? new JFrame() : null;
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
		Iterable<Field> fs = Util.collect(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
				x -> Objects.equals(Util.getName(x), "objectLock")), Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
			// graphicsConfig
			//
		if (IterableUtils
				.size(fs = Util.collect(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						x -> Objects.equals(Util.getName(x), "graphicsConfig")), Collectors.toList())) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
				&& Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
		instance.pack();
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
		final Iterable<Field> fs = Util
				.collect(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						x -> Objects.equals(Util.getName(x), "component")), Collectors.toList());
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
		instance.add(component);
		//
	}

	private static void setDefaultCloseOperation(@Nullable final JFrame instance, final int defaultCloseOperation) {
		if (instance != null) {
			instance.setDefaultCloseOperation(defaultCloseOperation);
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	@Nullable
	private static String replace(@Nullable final Strings instance, final String text, final String searchString,
			final String replacement) {
		return instance != null ? instance.replace(text, searchString, replacement) : null;
	}

	private static class Speaker {

		private String name = null;

		private List<Style> styles = null;

	}

	private static class Style {

		@Note("id")
		private String id = null;

		private String name = null;

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
				Util.forEach(Util.stream(speakers(createHostAndPort())), x -> Util.addElement(dcbmSpeaker, x));
				//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnAudioQuery)) {
			//
			try {
				//
				final Style style = Util.cast(Style.class, Util.getSelectedItem(dcbmStyle));
				//
				audioQuery = audioQuery(createHostAndPort(), style != null ? style.id : null, Util.getText(tfText));
				//
			} catch (final IOException | URISyntaxException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnViewAudioQuery)) {
			//
			final JPanel jPanel = new JPanel();
			//
			jPanel.setLayout(new MigLayout());
			//
			final JTextArea jTextArea = new JTextArea(audioQuery);
			//
			jTextArea.setRows(2);
			//
			final Dimension screenSize = getScreenSize(Toolkit.getDefaultToolkit());
			//
			jPanel.add(new JScrollPane(jTextArea),
					String.format("wmax %1$s", screenSize != null ? screenSize.getWidth() - 30 : "100%"));
			//
			if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(),
					Util.forName("org.junit.jupiter.api.Test") == null)) {
				//
				JOptionPane.showMessageDialog(null, jPanel, "Audio Query", JOptionPane.PLAIN_MESSAGE);
				//
			} // if
				//
		} else if (Objects.equals(source, btnSynthesis)) {
			//
			final Style style = Util.cast(Style.class, Util.getSelectedItem(dcbmStyle));
			//
			try {
				//
				final byte[] bs = synthesis(createHostAndPort(), style != null ? style.id : null, audioQuery);
				//
				if (bs != null && bs.length > 0) {
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
		} // if
			//
	}

	@Nullable
	private static Dimension getScreenSize(@Nullable final Toolkit instance) {
		//
		return instance != null && Boolean.logicalOr(!GraphicsEnvironment.isHeadless(), isJavassistProxy(instance))
				? instance.getScreenSize()
				: null;
		//
	}

	private static byte[] synthesis(@Nullable final HostAndPort hostAndPort, @Nullable final String speaker,
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
		uriBuilder.addParameter("speaker", speaker);
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			@Nullable final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
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

	private static String audioQuery(@Nullable final HostAndPort hostAndPort, @Nullable final String speaker,
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
		uriBuilder.addParameter("speaker", speaker);
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
		return instance != null && StringUtils.isNotBlank(getHost(instance.getURL())) ? instance.getOutputStream()
				: null;
		//
	}

	@Nullable
	private static String getHost(@Nullable final URL instance) {
		return instance != null ? instance.getHost() : null;
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
		if (Objects.equals(Util.getSource(evt), jcbSpeaker)) {
			//
			removeAllElements(dcbmStyle);
			//
			final Speaker speaker = Util.cast(Speaker.class, Util.getSelectedItem(jcbSpeaker));
			//
			if (speaker != null) {
				//
				Util.forEach(speaker.styles, x -> Util.addElement(dcbmStyle, x));
				//
			} // if
				//
		} // if
			//
	}

}