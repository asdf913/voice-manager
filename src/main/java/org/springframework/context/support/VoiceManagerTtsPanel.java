package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.poi.util.LocaleID;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.VoiceManager.ByteConverter;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerTtsPanel extends JPanel
		implements Titled, InitializingBean, EnvironmentAware, ItemListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 8338161986346369694L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerTtsPanel.class);

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String FORMAT = "format";

	private static final String SPEECH_RATE = "Speech Rate";

	private static final String LANGUAGE = "Language";

	private static final String COMPONENT = "component";

	private transient ApplicationContext applicationContext = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private transient PropertyResolver propertyResolver = null;

	private transient SpeechApi speechApi = null;

	private JTextComponent tfTextTts, tfProviderName, tfProviderVersion, tfProviderPlatform, tfSpeechLanguageCode,
			tfSpeechLanguageName, tfSpeechRate, tfSpeechVolume, tfElapsed = null;

	private JComboBox<Object> jcbVoiceId = null;

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

	private AbstractButton btnSpeak, btnWriteVoice = null;

	private JSlider jsSpeechRate, jsSpeechVolume = null;

	private transient ComboBoxModel<String> cbmVoiceId = null;

	private transient ComboBoxModel<?> cbmAudioFormatWrite = null;

	private transient ComboBoxModel<Method> cbmSpeakMethod = null;

	@Override
	public String getTitle() {
		return "TTS";
	}

	public VoiceManagerTtsPanel() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	private static interface ObjectMap {

		<T> T getObject(final Class<T> key);

		boolean containsObject(final Class<?> key);

		<T> void setObject(final Class<T> key, final T value);

		@Nullable
		static <T> T getObject(@Nullable final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> void setObject(@Nullable final ObjectMap instance, final Class<T> key, final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
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
					if (!containsKey(os, key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found",
								testAndApply(IH::isArray, Util.cast(Class.class, key), IH::getSimpleName, x -> key)));
						//
					} // if
						//
					return Util.get(os, key);
					//
				}
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean containsKey(@Nullable final Map<?, ?> instance, final Object key) {
			return instance != null && instance.containsKey(key);
		}

		private static boolean isArray(@Nullable final OfField<?> instance) {
			return instance != null && instance.isArray();
		}

		@Nullable
		private static String getSimpleName(@Nullable final Class<?> instance) {
			return instance != null ? instance.getSimpleName() : null;
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		add(new JLabel("Text"));
		//
		add(tfTextTts = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.text")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		// Provider
		//
		add(new JLabel("Provider"));
		//
		final Provider provider = Util.cast(Provider.class, speechApi);
		//
		add(tfProviderName = new JTextField(getProviderName(provider)), String.format("%1$s,span %2$s", GROWX, 3));
		//
		final boolean isInstalled = isInstalled(speechApi);
		//
		// Provider Version
		//
		testAndAccept(Objects::nonNull, tfProviderVersion = createProviderVersionJTextComponent(isInstalled, provider),
				x -> add(x, String.format("span %1$s,width %2$s", 3, 64)));
		//
		// Provider Platform
		//
		testAndAccept(Objects::nonNull,
				tfProviderPlatform = createProviderPlatformJTextComponent(isInstalled, provider), x -> add(x, WRAP));
		//
		// Voice Id
		//
		add(new JLabel("Voice Id"));
		//
		final String[] voiceIds = getVoiceIds(speechApi);
		//
		if ((cbmVoiceId = testAndApply(Objects::nonNull, voiceIds,
				x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
			//
			final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer();
			//
			voiceIdListCellRenderer.listCellRenderer = getRenderer(
					Util.cast(JComboBox.class, jcbVoiceId = new JComboBox(cbmVoiceId)));
			//
			jcbVoiceId.addItemListener(this);
			//
			voiceIdListCellRenderer.commonPrefix = String.join("",
					StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
			//
			jcbVoiceId.setRenderer(voiceIdListCellRenderer);
			//
			add(jcbVoiceId, String.format("%1$s,span %2$s", GROWX, 3));
			//
			add(tfSpeechLanguageCode = new JTextField(), String.format("width %1$s,span %2$s", 30, 2));
			//
			add(tfSpeechLanguageName = new JTextField(), String.format("%1$s,span %2$s,width %3$s", WRAP, 2, 230));
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
		final Lookup lookup = Util.cast(Lookup.class, speechApiInstance);
		//
		final Predicate<String> predicate = a -> contains(lookup, "rate", a);
		//
		final FailableFunction<String, Object, RuntimeException> function = a -> get(lookup, "rate", a);
		//
		if (Boolean.logicalAnd(Util.test(predicate, "min"), Util.test(predicate, "max"))) {
			//
			addSpeedButtons(this, createRange(toInteger(testAndApply(predicate, "min", function, null)),
					toInteger(testAndApply(predicate, "max", function, null))));
			//
		} // if
			//
		if (jsSpeechRate == null && tfSpeechRate == null) {
			//
			add(new JLabel(SPEECH_RATE));
			//
			add(tfSpeechRate = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
					"org.springframework.context.support.VoiceManager.speechRate")),
					String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
			//
		} // if
			//
			// Speech Volume
			//
		add(new JLabel("Speech Volume"), "aligny top");
		//
		final Range<Integer> speechVolumeRange = createVolumeRange(speechApiInstance);
		//
		final Integer upperEnpoint = testAndApply(VoiceManagerTtsPanel::hasUpperBound, speechVolumeRange,
				VoiceManagerTtsPanel::upperEndpoint, null);
		//
		add(jsSpeechVolume = new JSlider(intValue(testAndApply(VoiceManagerTtsPanel::hasLowerBound, speechVolumeRange,
				VoiceManagerTtsPanel::lowerEndpoint, null), 0), intValue(upperEnpoint, 100)),
				String.format("%1$s,span %2$s", GROWX, 3));
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
		add(tfSpeechVolume = new JTextField(), String.format("%1$s,width %2$s", WRAP, 27));
		//
		// Button(s)
		//
		add(new JLabel());
		//
		final JComboBox<Method> jcbSpeakMethod = testAndApply(CollectionUtils::isNotEmpty,
				Util.toList(Util.filter(testAndApply(Objects::nonNull,
						Util.getDeclaredMethods(Util.getClass(speechApiInstance)), Arrays::stream, null),
						m -> isAnnotationPresent(m, SpeakMethod.class))),
				x -> new JComboBox<>(cbmSpeakMethod = testAndApply(Objects::nonNull, toArray(x, new Method[] {}),
						DefaultComboBoxModel::new, y -> new DefaultComboBoxModel<>())),
				null);
		//
		final ListCellRenderer<?> listCellRenderer = getRenderer(jcbSpeakMethod);
		//
		setRenderer(jcbSpeakMethod, new ListCellRenderer<Object>() {

			@Override
			public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				//
				return VoiceManagerTtsPanel.getListCellRendererComponent((ListCellRenderer) listCellRenderer, list,
						Util.getName(Util.cast(Member.class, value)), index, isSelected, cellHasFocus);
				//
			}

		});
		//
		testAndAccept(Objects::nonNull, jcbSpeakMethod, this::add);
		//
		add(btnSpeak = new JButton("Speak"), WRAP);
		//
		// Audio Format
		//
		add(new JLabel("Write To File"));
		//
		final JComboBox<Object> jcbAudioFormat = new JComboBox(
				cbmAudioFormatWrite = new DefaultComboBoxModel<Object>());
		//
		final Collection<?> formats = getByteConverterAttributeValues(configurableListableBeanFactory = ObjectUtils
				.defaultIfNull(Util.cast(ConfigurableListableBeanFactory.class, applicationContext), Util.cast(
						ConfigurableListableBeanFactory.class,
						applicationContext != null ? applicationContext.getAutowireCapableBeanFactory() : null)),
				FORMAT);
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatWrite = Util.cast(MutableComboBoxModel.class,
				cbmAudioFormatWrite);
		//
		addElement(mcbmAudioFormatWrite, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
		//
		add(jcbAudioFormat);
		//
		add(btnWriteVoice = new JButton("Write"), WRAP);
		//
		// elapsed
		//
		add(new JLabel("Elapsed"));
		//
		add(tfElapsed = new JTextField(), String.format("%1$s,span %2$s", GROWX, 2));
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
		setPreferredWidth(
				intValue(getPreferredWidth(testAndApply(CollectionUtils::isNotEmpty, cs,
						x -> Collections.max(x,
								(a, b) -> ObjectUtils.compare(getPreferredWidth(a), getPreferredWidth(b))),
						null)), 0),
				cs);
		//
	}

	private static <T, E extends Throwable> void forEach(final Iterable<T> items,
			@Nullable final FailableConsumer<? super T, E> action) throws E {
		//
		if (Util.iterator(items) != null && (action != null || Proxy.isProxyClass(Util.getClass(items)))) {
			//
			for (final T item : items) {
				//
				if (action != null) {
					//
					action.accept(item);
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static Collection<Object> getByteConverterAttributeValues(
			final ConfigurableListableBeanFactory configurableListableBeanFactory, final String attribute) {
		//
		List<Object> list = null;
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
				if (en == null || (bd = ConfigurableListableBeanFactoryUtil
						.getBeanDefinition(configurableListableBeanFactory, Util.getKey(en))) == null
						|| !bd.hasAttribute(attribute)) {
					continue;
				} // if
					//
				Util.add(list = getIfNull(list, ArrayList::new), bd.getAttribute(attribute));
				//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), jcbVoiceId)) {
			//
			try {
				//
				final String language = getVoiceAttribute(speechApi, Util.toString(getSelectedItem(cbmVoiceId)),
						LANGUAGE);
				//
				Util.setText(tfSpeechLanguageCode, language);
				//
				Util.setText(tfSpeechLanguageName,
						StringUtils.defaultIfBlank(convertLanguageCodeToText(language, 16), language));
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (Util.contains(getObjectsByGroupAnnotation(this, SPEECH_RATE), source)) {
			//
			actionPerformedForSpeechRate(source);
			//
		} else if (Objects.equals(source, btnSpeak)) {
			//
			final Stopwatch stopwatch = Stopwatch.createStarted();
			//
			final Method method = Util.cast(Method.class, getSelectedItem(cbmSpeakMethod));
			//
			final Object instance = getInstance(speechApi);
			//
			final String text = Util.getText(tfTextTts);
			//
			final String voiceId = Util.toString(getSelectedItem(cbmVoiceId));
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
			Util.setText(tfElapsed, Util.toString(elapsed(stop(stopwatch))));
			//
		} else if (Objects.equals(source, btnWriteVoice)) {
			//
			actionPerformedForWriteVoice(headless);
			//
		} // if
			//
	}

	private void actionPerformedForWriteVoice(final boolean headless) {
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		setFileSelectionMode(jfc, JFileChooser.FILES_ONLY);
		//
		if (showSaveDialog(jfc, null) != JFileChooser.APPROVE_OPTION) {
			//
			return;
			//
		} //
			//
		final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
		//
		final File file = getSelectedFile(jfc);
		//
		ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
		//
		ObjectMap.setObject(objectMap, File.class, file);
		//
		writeVoiceToFile(objectMap, Util.getText(tfTextTts), Util.toString(getSelectedItem(cbmVoiceId))
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
				FileUtils.writeByteArrayToFile(file, byteConverter.convert(Files.readAllBytes(Path.of(toURI(file)))));
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

	private static ByteConverter getByteConverter(final ConfigurableListableBeanFactory configurableListableBeanFactory,
			final String attribute, final Object value) {
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

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
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
			if (Narcissus.getField(instance, JComponent.class.getDeclaredField("ui")) == null) {
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

	private void actionPerformedForSpeechRate(final Object source) {
		//
		Integer integer = null;
		//
		if (Objects.equals(source, btnSpeechRateSlower)) {
			//
			integer = Integer.valueOf(intValue(getValue(jsSpeechRate), 0) - 1);
			//
		} else if (Objects.equals(source, btnSpeechRateNormal)) {
			//
			integer = Integer.valueOf(0);
			//
		} else if (Objects.equals(source, btnSpeechRateFaster)) {
			//
			integer = Integer.valueOf(intValue(getValue(jsSpeechRate), 0) + 1);
			//
		} // if
			//
		if (integer != null) {
			//
			setValue(jsSpeechRate, integer.intValue());
			//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	@Nullable
	private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	@Nullable
	private static Duration elapsed(@Nullable final Stopwatch instance) {
		return instance != null ? instance.elapsed() : null;
	}

	@Nullable
	private static Stopwatch stop(@Nullable final Stopwatch instance) {
		return instance != null ? instance.stop() : null;
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
			if (f != null && isStatic(f)) {
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

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
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
				setEnabled(btnSpeechRateSlower, intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMinimum());
				//
				setEnabled(btnSpeechRateFaster, intValue(getValue(jsSpeechRate), 0) != jsSpeechRate.getMaximum());
				//
			} // if
				//
			Util.setText(tfSpeechRate, Util.toString(getValue(jsSpeechRate)));
			//
		} else {
			//
			throw new UnsupportedOperationException();
			//
		} // if
			//
	}

	@Nullable
	private static Integer getValue(@Nullable final JSlider instance) {
		return instance != null ? Integer.valueOf(instance.getValue()) : null;
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

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) {
		//
		if (Util.iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = Util.getPreferredSize(c)) == null) {
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

	private static void setPreferredWidth(final int width, @Nullable final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = Util.getPreferredSize(c)) == null) {
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

	@Nullable
	private static String[] getVoiceIds(@Nullable final SpeechApi instance) {
		return instance != null ? instance.getVoiceIds() : null;
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

	private static <T> List<T> getObjectsByGroupAnnotation(final Object instance, final String group,
			final Class<T> clz) {
		//
		return Util.toList(Util.map(Util.stream(getObjectsByGroupAnnotation(instance, group)), x -> Util.cast(clz, x)));
		//
	}

	private static List<?> getObjectsByGroupAnnotation(final Object instance, final String group) {
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(testAndApply(Objects::nonNull,
				Util.getDeclaredFields(VoiceManagerTtsPanel.class), Arrays::stream, null), f -> {
					final Group g = isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringUtils.equals(g != null ? g.value() : null, group);
				}));
		//
		return Util.toList(FailableStreamUtil.stream(FailableStreamUtil.map(fs,
				f -> testAndApply(Objects::nonNull, instance, x -> FieldUtils.readField(f, x, true), null))));
		//
	}

	@Nullable
	private static Double getPreferredWidth(@Nullable final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
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

	private static boolean isAnnotationPresent(@Nullable final AnnotatedElement instance,
			@Nullable final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
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

	private void setSpeechVolume(@Nullable final Number speechVolume, @Nullable final Number upperEnpoint) {
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

	private static void setValue(@Nullable final JSlider instance, final int n) {
		if (instance != null) {
			instance.setValue(n);
		}
	}

	private static Range<Integer> createVolumeRange(final Object instance) {
		//
		final Lookup lookup = Util.cast(Lookup.class, instance);
		//
		final BiPredicate<String, String> biPredicate = (a, b) -> contains(lookup, a, b);
		//
		final FailableBiFunction<String, String, Object, RuntimeException> biFunction = (a, b) -> get(lookup, a, b);
		//
		return createRange(toInteger(testAndApply(biPredicate, "volume", "min", biFunction, null)),
				toInteger(testAndApply(biPredicate, "volume", "max", biFunction, null)));
		//
	}

	@Nullable
	private static Object get(@Nullable final Lookup instance, final Object row, final Object column) {
		return instance != null ? instance.get(row, column) : instance;
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static void addSpeedButtons(@Nullable final VoiceManagerTtsPanel instance, final Range<Integer> range) {
		//
		if (!(hasLowerBound(range) && hasUpperBound(range) && lowerEndpoint(range) != null
				&& upperEndpoint(range) != null)) {
			//
			return;
			//
		} // if
			//
		add(instance, new JLabel(SPEECH_RATE), "aligny top");
		//
		final JSlider jsSpeechRate = instance != null
				? instance.jsSpeechRate = new JSlider(intValue(lowerEndpoint(range), 0),
						intValue(upperEndpoint(range), 0))
				: null;
		//
		add(instance, jsSpeechRate, String.format("%1$s,span %2$s", GROWX, 7));
		//
		setMajorTickSpacing(jsSpeechRate, 1);
		//
		setPaintTicks(jsSpeechRate, true);
		//
		setPaintLabels(jsSpeechRate, true);
		//
		final JTextComponent tfSpeechRate = instance != null ? instance.tfSpeechRate = new JTextField() : null;
		//
		add(instance, tfSpeechRate, String.format("%1$s,width %2$s", WRAP, 24));
		//
		setEditable(false, tfSpeechRate);
		//
		setValue(jsSpeechRate,
				PropertyResolverUtil.getProperty(instance != null ? instance.propertyResolver : null,
						"org.springframework.context.support.VoiceManager.speechRate"),
				a -> instance.stateChanged(new ChangeEvent(a)));
		//
		add(instance, new JLabel(""));
		//
		final AbstractButton btnSpeechRateSlower = instance != null
				? instance.btnSpeechRateSlower = new JButton("Slower")
				: null;
		//
		add(instance, btnSpeechRateSlower);
		//
		final AbstractButton btnSpeechRateNormal = instance != null
				? instance.btnSpeechRateNormal = new JButton("Normal")
				: null;
		//
		add(instance, btnSpeechRateNormal);
		//
		final AbstractButton btnSpeechRateFaster = instance != null
				? instance.btnSpeechRateFaster = new JButton("Faster")
				: null;
		//
		add(instance, btnSpeechRateFaster, WRAP);
		//
		final Double maxWidth = ObjectUtils.max(getPreferredWidth(btnSpeechRateSlower),
				getPreferredWidth(btnSpeechRateNormal), getPreferredWidth(btnSpeechRateFaster));
		//
		if (maxWidth != null) {
			//
			setPreferredWidth(maxWidth.intValue(), btnSpeechRateSlower, btnSpeechRateNormal, btnSpeechRateFaster);
			//
		} // if
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
				accept(consumer, instance);
				//
			} // if
				//
		} else {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(instance)), Arrays::stream,
							null),
					x -> x != null && Objects.equals(x.getReturnType(), Integer.TYPE) && x.getParameterCount() == 0
							&& StringUtils.startsWithIgnoreCase(Util.getName(x), "get" + string)));
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

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
	}

	private static <T> void accept(@Nullable final Consumer<T> instance, final T value) {
		if (instance != null) {
			instance.accept(value);
		}
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
			if (Narcissus.getObjectField(instance, getDeclaredField(Container.class, COMPONENT)) == null) {
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
			if (Narcissus.getObjectField(instance, getDeclaredField(Container.class, COMPONENT)) == null) {
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
	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	@Nullable
	private static <C extends Comparable<C>> C lowerEndpoint(@Nullable final Range<C> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			final Field lowerBound = getDeclaredField(Range.class, "lowerBound");
			//
			setAccessible(lowerBound, true);
			//
			if (get(lowerBound, instance) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.lowerEndpoint();
		//
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	@Nullable
	private static <C extends Comparable<C>> C upperEndpoint(@Nullable final Range<C> instance) {
		return instance != null ? instance.upperEndpoint() : null;
	}

	private static boolean hasLowerBound(@Nullable final Range<?> instance) {
		return instance != null && instance.hasLowerBound();
	}

	private static boolean hasUpperBound(@Nullable final Range<?> instance) {
		return instance != null && instance.hasUpperBound();
	}

	private static Range<Integer> createRange(@Nullable final Integer minValue, @Nullable final Integer maxValue) {
		//
		if (minValue != null && maxValue != null) {
			return Range.open(minValue, maxValue);
		} else if (minValue != null) {
			return Range.atLeast(minValue);
		} else if (maxValue != null) {
			return Range.atMost(maxValue);
		} // if
			//
		return Range.all();
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
			integer = Integer.valueOf(intValue(number, 0));
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
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static boolean contains(@Nullable final Lookup instance, final Object row, final Object column) {
		return instance != null && instance.contains(row, column);
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
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
		List<?> temp = Util.toList(Util.filter(testAndApply(Objects::nonNull, voiceIds, Arrays::stream, null),
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
			if ((size = IterableUtils.size(temp = Util
					.toList(Util.filter(testAndApply(Objects::nonNull, voiceIds, Arrays::stream, null), x -> {
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
								Util.toList(Util.map(Util.stream(temp), x -> StringUtils.wrap(Util.toString(x), "\""))),
								voiceLanguage));
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	private static String convertLanguageCodeToText(final String instance, final int base) {
		return StringUtils.defaultIfBlank(convertLanguageCodeToText(LocaleID.values(), valueOf(instance, base)),
				instance);
	}

	@Nullable
	private static Integer valueOf(final String instance, final int base) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance, base) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Nullable
	private static String convertLanguageCodeToText(final LocaleID[] enums, @Nullable final Integer value) {
		//
		final List<LocaleID> localeIds = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, enums, Arrays::stream, null),
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

	@Nullable
	private static String getVoiceAttribute(@Nullable final SpeechApi instance, final String voiceId,
			final String attribute) {
		return instance != null ? instance.getVoiceAttribute(voiceId, attribute) : null;
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	@Nullable
	private static <E> ListCellRenderer<? super E> getRenderer(@Nullable final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
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
			final String s = Util.toString(value);
			//
			try {
				//
				final String name = getVoiceAttribute(speechApi, s, "Name");
				//
				if (StringUtils.isNotBlank(name)) {
					//
					return VoiceManagerTtsPanel.getListCellRendererComponent(listCellRenderer, list, name, index,
							isSelected, cellHasFocus);
					//
				} // if
					//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			return VoiceManagerTtsPanel.getListCellRendererComponent(listCellRenderer, list,
					StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
					index, isSelected, cellHasFocus);
			//
		}

	}

	private static void setEnabled(final boolean b, final Component instance, @Nullable final Component... cs) {
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

	private static void setEnabled(@Nullable final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	@Nullable
	private static JTextComponent createProviderPlatformJTextComponent(final boolean isInstalled,
			@Nullable final Provider provider) {
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

	@Nullable
	private static String getProviderPlatform(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderPlatform() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static JTextComponent createProviderVersionJTextComponent(final boolean isInstalled,
			@Nullable final Provider provider) {
		return isInstalled ? new JTextField(getProviderVersion(provider)) : new JTextField();
	}

	@Nullable
	private static String getProviderVersion(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderVersion() : null;
	}

	private static boolean isInstalled(@Nullable final SpeechApi instance) {
		return instance != null && instance.isInstalled();
	}

	@Nullable
	private static String getProviderName(@Nullable final Provider instance) {
		return instance != null ? instance.getProviderName() : null;
	}

}