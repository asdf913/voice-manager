package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.BIPUSH;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEWARRAY;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableRunnableUtil;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.function.FailableSupplierUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.Range;
import com.google.common.collect.RangeUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import de.sciss.jump3r.lowlevel.LameEncoder;
import de.sciss.jump3r.mp3.Lame;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.ConfigurationUtil;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import j2html.tags.specialized.ATag;
import j2html.tags.specialized.ATagUtil;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.javaflacencoder.AudioStreamEncoder;
import net.sourceforge.javaflacencoder.FLACEncoder;
import net.sourceforge.javaflacencoder.FLACStreamOutputStream;
import net.sourceforge.javaflacencoder.StreamConfiguration;

@Title("Voice Manager")
public class VoiceManager extends JFrame implements ActionListener, EnvironmentAware, BeanFactoryPostProcessor,
		InitializingBean, ApplicationContextAware {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static final String HANDLER = "handler";

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_1 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_2 = Pattern
			.compile("^Audio file with ID3 version (\\d+(\\.\\d+)?), MP3 encoding$");

	private static final Pattern PATTERN_CONTENT_INFO_MESSAGE_MP3_3 = Pattern
			.compile("^Audio file with ID3 version \\d+(\\.\\d+)?$");

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String FORMAT = "format";

	private static final String WARNING = "Warning";

	private static final String COMPONENT = "component";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see java.lang.Class#getResourceAsStream(java.lang.String)
	 */
	private static final String CLASS_RESOURCE_FORMAT = "/%1$s.class";

	private transient ApplicationContext applicationContext = null;

	private transient PropertyResolver propertyResolver = null;

	static VoiceManager INSTANCE = null;

	@Note("Folder")
	private JTextComponent tfFolder = null;

	@Note("File Length")
	private JTextComponent tfFileLength = null;

	@Note("File Digest")
	private JTextComponent tfFileDigest = null;

	@Note("File")
	private JTextComponent tfFile = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Note("For Write Voice in TTS Panel")
	private transient ComboBoxModel<?> cbmAudioFormatWrite = null;

	@Note("For Import Panel")
	private transient ComboBoxModel<?> cbmAudioFormatExecute = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Import Result")
	private DefaultTableModel tmImportResult = null;

	private String voiceFolder = null;

	private transient SpeechApi speechApi = null;

	@Nullable
	private String[] tabOrders = null;

	private transient Toolkit toolkit = null;

	private ObjectMapper objectMapper = null;

	private String microsoftSpeechPlatformRuntimeDownloadPageUrl = null;

	@Url("https://support.microsoft.com/en-us/windows/make-older-apps-or-programs-compatible-with-windows-10-783d6dd7-b439-bdb0-0490-54eea0f45938")
	private String microsoftWindowsCompatibilitySettingsPageUrl = null;

	private transient LayoutManager layoutManager = null;

	private transient freemarker.template.Configuration freeMarkerConfiguration = null;

	private Version freeMarkerVersion = null;

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		//
		// If the
		// "org.springframework.context.support.VoiceManager.getDefaultCloseOperation(java.lang.Iterable)"
		// return a "java.lang.Number" instance, pass the value of
		// "java.lang.Number.intValue()" of
		// the "java.lang.Number" instance to the
		// "javax.swing.JFrame.setDefaultCloseOperation(int)" method
		//
		final Iterable<?> bpps = Util.values(
				ListableBeanFactoryUtil.getBeansOfType(configurableListableBeanFactory, BeanPostProcessor.class));
		//
		final Number defaultCloseOperation = getDefaultCloseOperation(bpps);
		//
		if (defaultCloseOperation != null) {
			//
			setDefaultCloseOperation(defaultCloseOperation.intValue());
			//
		} // if
			//
		if (Util.iterator(bpps) != null) {
			//
			Collection<Method> ms = null;
			//
			for (final Object obj : bpps) {
				//
				Util.addAll(ms = ObjectUtils.getIfNull(ms, ArrayList::new), Util.toList(Util.filter(
						testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(obj)), Arrays::stream,
								null),
						m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "setTitle"), Arrays.equals(
								Util.getParameterTypes(m), new Class<?>[] { Frame.class, PropertyResolver.class })))));
				//
			} // for
				//
			final int size = IterableUtils.size(ms);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Method m = testAndApply(x -> size == 1, ms, x -> IterableUtils.get(x, 0), null);
			//
			setAccessible(m, true);
			//
			if (m != null) {
				//
				final boolean headless = GraphicsEnvironment.isHeadless();
				//
				try {
					//
					m.invoke(null, this, propertyResolver);
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
		} // if
			//
		final Collection<?> formats = getByteConverterAttributeValues(configurableListableBeanFactory, FORMAT);
		//
		// cbmAudioFormatWrite
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatWrite = Util.cast(MutableComboBoxModel.class,
				cbmAudioFormatWrite);
		//
		addElement(mcbmAudioFormatWrite, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatWrite, x));
		//
		// cbmAudioFormatExecute
		//
		final MutableComboBoxModel<Object> mcbmAudioFormatExecute = Util.cast(MutableComboBoxModel.class,
				cbmAudioFormatExecute);
		//
		addElement(mcbmAudioFormatExecute, null);
		//
		forEach(formats, x -> addElement(mcbmAudioFormatExecute, x));
		//
		final String audioFormat = PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.audioFormat");
		//
		setSelectedItem(cbmAudioFormatWrite, audioFormat);
		//
		setSelectedItem(cbmAudioFormatExecute, audioFormat);
		//
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	/**
	 * If there is a object in "values" with "defaultCloseOperation" field and the
	 * field could be cast as a "java.lang.Number" instance, return the field value
	 */
	@Nullable
	private static Number getDefaultCloseOperation(@Nullable final Iterable<?> values) {
		//
		if (Util.iterator(values) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<Number> result = null;
		//
		for (final Object obj : values) {
			//
			if (result == null) {
				//
				result = getNumber(obj,
						Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(obj)),
										Arrays::stream, null),
								x -> Objects.equals(Util.getName(x), "defaultCloseOperation"))));
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return IValue0Util.getValue0(result);
		//
	}

	@Nullable
	private static IValue0<Number> getNumber(@Nullable final Object instance, @Nullable final Iterable<Field> fs) {
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		IValue0<Number> result = null;
		//
		final Field f = size == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null) {
			//
			final Number number = Util.cast(Number.class, testAndApply(Util::isStatic, f, Narcissus::getStaticField,
					a -> testAndApply(Objects::nonNull, instance, b -> Narcissus.getField(b, f), null)));
			//
			if (number != null || Util.isAssignableFrom(Number.class, Util.getType(f))) {
				//
				result = Unit.with(number);
				//
			} // if
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, @Nullable final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	@Override
	public void afterPropertiesSet() {
		//
		try {
			//
			init();
			//
		} catch (final NoSuchFieldException | NoSuchMethodException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	@Override
	public void setLayout(final LayoutManager layoutManager) {
		///
		super.setLayout(this.layoutManager = layoutManager);
		//
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance,
			@Nullable final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	public void setTabOrders(final Object value) {
		//
		this.tabOrders = Util.toArray(
				Util.toList(Util.map(Util.stream(getObjectList(getObjectMapper(), value)), x -> Util.toString(x))),
				new String[] {});
		//
	}

	public void setMicrosoftSpeechPlatformRuntimeDownloadPageUrl(
			final String microsoftSpeechPlatformRuntimeDownloadPageUrl) {
		this.microsoftSpeechPlatformRuntimeDownloadPageUrl = microsoftSpeechPlatformRuntimeDownloadPageUrl;
	}

	public void setMicrosoftWindowsCompatibilitySettingsPageUrl(
			final String microsoftWindowsCompatibilitySettingsPageUrl) {
		this.microsoftWindowsCompatibilitySettingsPageUrl = microsoftWindowsCompatibilitySettingsPageUrl;
	}

	public void setFreeMarkerVersion(final Object value) {
		//
		if (value instanceof Version version) {
			//
			this.freeMarkerVersion = version;
			//
		} else if (value instanceof CharSequence) {
			//
			this.freeMarkerVersion = new Version(Util.toString(value));
			//
		} else if (value instanceof Number number) {
			//
			this.freeMarkerVersion = new Version(Util.intValue(number, 0));
			//
		} // if
			//
		final int[] ints = Util.cast(int[].class, value);
		//
		if (ints != null) {
			//
			if (ints.length != 3) {
				//
				throw new IllegalArgumentException();
				//
			} else {
				//
				this.freeMarkerVersion = new Version(ints[0], ints[1], ints[2]);
				//
			} // if
				//
		} // if
			//
	}

	public void setFreeMarkerConfiguration(final freemarker.template.Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
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

	private static class JTabbedPaneChangeListener implements ChangeListener {

		private Component component = null;

		private String[] importTabNames = null;

		@Override
		public void stateChanged(final ChangeEvent evt) {
			//
			final JTabbedPane jtp = Util.cast(JTabbedPane.class, Util.getSource(evt));
			//
			if (jtp != null) {
				//
				setVisible(component, ArrayUtils.contains(importTabNames, jtp.getTitleAt(jtp.getSelectedIndex())));
				//
			} // if
				//
		}
	}

	private void init() throws NoSuchFieldException, NoSuchMethodException {
		//
		final Field[] fs = Util.getDeclaredFields(VoiceManager.class);
		//
		for (int i = 0; i < length(fs); i++) {
			//
			try {
				//
				testAndAccept(x -> and(x, y -> Objects.equals(Util.getDeclaringClass(y), Util.getType(y)),
						Util::isStatic, y -> get(y, null) == null), fs[i], x -> Narcissus.setStaticField(x, this));
				//
			} catch (final IllegalArgumentException | IllegalAccessException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // for
			//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		final String TAB_TITLE_IMPORT_SINGLE = "Import(Single)";
		//
		final String TAB_TITLE_IMPORT_BATCH = "Import(Batch)";
		//
		final JTabbedPaneChangeListener jTabbedPaneChangeListener = new JTabbedPaneChangeListener();
		//
		jTabbedPaneChangeListener.importTabNames = new String[] { TAB_TITLE_IMPORT_SINGLE, TAB_TITLE_IMPORT_BATCH };
		//
		jTabbedPane.addChangeListener(jTabbedPaneChangeListener);
		//
		JPanel jPanelWarning = null;
		//
		final boolean isInstalled = SpeechApi.isInstalled(speechApi);
		//
		if (isInstalled) {
			//
			final LayoutManager lm = cloneLayoutManager();
			//
			if (Boolean.logicalAnd(Objects.equals(Boolean.TRUE, IValue0Util.getValue0(IsWindows10OrGreater())),
					getInstance(speechApi) instanceof SpeechApiSpeechServerImpl) &&
			//
			// https://learn.microsoft.com/en-us/windows/win32/api/winnt/ns-winnt-osversioninfoexa
			//
			// dwMajorVersion
			//
					ObjectUtils.compare(valueOf(Util.toString(Util.get(getOsVersionInfoExMap(), "getMajor"))),
							10) >= 10) {
				//
				jPanelWarning = createMicrosoftWindowsCompatibilityWarningJPanel(lm,
						microsoftWindowsCompatibilitySettingsPageUrl);
				//
			} // if
				//
			testAndAccept(Objects::nonNull, jPanelWarning, x -> {
				//
				testAndAccept(MigLayout.class::isInstance, lm, y -> add(x, WRAP));
				//
				testAndAccept(y -> !(y instanceof MigLayout), lm, y -> add(x));
				//
			});
			//
		} // if
			//
			// If the instance is instantiated by
			// "io.github.toolfactory.narcissus.Narcissus.allocateInstance(java.lang.Class)",
			// the "component" field in "ava.awt.Container" class will be null.
			//
			// If the "component" field in "ava.awt.Container" class is null, call "return"
			// at once and stop the rest statement(s).
			//
		if (Narcissus.getObjectField(this, Util.getDeclaredField(Container.class, COMPONENT)) == null) {
			//
			return;
			//
		} // if
			//
		testAndRun(!isInstalled,
				() -> add(craeteSpeechApiInstallationWarningJPanel(microsoftSpeechPlatformRuntimeDownloadPageUrl),
						WRAP));
		//
		forEach(Util.stream(Util.entrySet(getTitledComponentMap(
				ListableBeanFactoryUtil.getBeansOfType(applicationContext, Component.class), tabOrders))), x -> {
					final Component component = Util.getValue(x);
					if (!(component instanceof Window)) {
						jTabbedPane.addTab(Util.getKey(x), component);
					}
				});
		//
		// maximum preferred height of all tab page(s)
		//
		final Double preferredHeight = getMaxPagePreferredHeight(jTabbedPane);
		//
		final freemarker.template.Configuration configuration = ObjectUtils.getIfNull(freeMarkerConfiguration,
				() -> new freemarker.template.Configuration(
						ObjectUtils.getIfNull(freeMarkerVersion, freemarker.template.Configuration::getVersion)));
		//
		testAndAccept(x -> getTemplateLoader(configuration) == null, configuration,
				x -> ConfigurationUtil.setTemplateLoader(x, new ClassTemplateLoader(VoiceManager.class, "/")));
		//
		// Help Panel
		//
		add(Util.entrySet(ListableBeanFactoryUtil.getBeansOfType(applicationContext, IntFunction.class)),
				preferredHeight, jTabbedPane);
		//
		final List<?> pages = Util.cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getField(x, Util.getDeclaredField(Util.getClass(x), "pages")), null));
		//
		setSelectedIndex(jTabbedPane, getTabIndexByTitle(pages, PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.tabTitle")));
		//
		if (layoutManager instanceof MigLayout) {
			//
			add(jTabbedPane, WRAP);
			//
			add(jTabbedPaneChangeListener.component = createImportResultPanel(cloneLayoutManager()), GROWX);
			//
			jTabbedPaneChangeListener.stateChanged(testAndApply(Objects::nonNull, jTabbedPane, ChangeEvent::new, null));
			//
		} // if
			//
		setPreferredWidth(jPanelWarning, () -> getPreferredWidth(jTabbedPane));
		//
		final Predicate<Component> predicate = createFocusableComponentPredicate(
				Arrays.asList(JLabel.class, JScrollPane.class, JProgressBar.class, JPanel.class));
		//
		forEach(Util.toList(Util.map(
				FailableStreamUtil.stream(FailableStreamUtil.map(new FailableStream<>(Util.stream(pages)),
						x -> Narcissus.getObjectField(x, Util.getDeclaredField(Util.getClass(x), COMPONENT)))),
				x -> Util.cast(Container.class, x))), c -> {
					//
					// https://stackoverflow.com/questions/35508128/setting-personalized-focustraversalpolicy-on-tab-in-jtabbedpane
					//
					setFocusCycleRoot(c, true);
					//
					setFocusTraversalPolicy(c,
							new TabFocusTraversalPolicy(Util.toList(
									Util.filter(testAndApply(Objects::nonNull, getComponents(c), Arrays::stream, null),
											predicate))));
					//
				});
		//
	}

	private static void add(final Iterable<Entry<String, IntFunction>> entrySet, @Nullable final Number preferredHeight,
			@Nullable final Container container) throws NoSuchMethodException {

		if (Util.iterator(entrySet) != null) {
			//
			IntFunction intFunction = null;
			//
			for (final Entry<String, IntFunction> entry : entrySet) {
				//
				if ((intFunction = Util.getValue(entry)) != null && preferredHeight != null
						&& intFunction.apply(preferredHeight.intValue()) instanceof Component c && container != null
						&& Narcissus.invokeMethod(c,
								Util.getDeclaredMethod(Component.class, "getObjectLock")) != null) {
					//
					container.add(c.getName(), c);
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static Map<String, Component> getTitledComponentMap(final Map<String, Component> cs,
			final String... orders) {
		//
		final Iterable<Entry<String, Component>> entrySet = Util.entrySet(cs);
		//
		Map<String, Component> m1 = null;
		//
		if (Util.iterator(entrySet) != null) {
			//
			Component c = null;
			//
			Titled titled = null;
			//
			for (final Entry<String, Component> entry : entrySet) {
				//
				c = Util.getValue(entry);
				//
				Util.put(m1 = ObjectUtils.getIfNull(m1, LinkedHashMap::new),
						(titled = Util.cast(Titled.class, c)) != null ? getTitle(titled)
								: Util.getName(Util.getClass(c)),
						c);
				//
			} // for
				//
		} // if
			//
		return Util.collect(
				sorted(Util.filter(Util.stream(Util.entrySet(m1)), x -> Objects.nonNull(Util.getValue(x))), (a, b) -> {
					//
					final int ia = ArrayUtils.indexOf(orders, Util.getKey(a));
					//
					final int ib = ArrayUtils.indexOf(orders, Util.getKey(b));
					//
					if (ia > ib) {
						//
						return 1;
						//
					} else if (ia < ib) {
						//
						return -1;
						//
					} // if
						//
					return 0;
					//
				}), Collectors.toMap(x -> Util.getKey(x), x -> Util.getValue(x), (a, b) -> a, LinkedHashMap::new));
		//
	}

	private static <T> Stream<T> sorted(final Stream<T> instance, final Comparator<? super T> comparator) {
		//
		return instance != null && (comparator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.sorted(comparator)
				: instance;
		//
	}

	private static <T, E extends Throwable> boolean and(final T value, final FailablePredicate<T, E> a,
			final FailablePredicate<T, E> b, final FailablePredicate<T, E>... ps) throws E {
		//
		boolean result = Util.test(a, value) && Util.test(b, value);
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; i < length(ps); i++) {
			//
			if (!(result &= Util.test(ps[i], value))) {
				//
				return result;
				//
			} // if
				//
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static String getTitle(@Nullable final Titled instance) {
		return instance != null ? instance.getTitle() : null;
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static void setSelectedIndex(@Nullable final JTabbedPane instance, @Nullable final Number index) {
		//
		if (instance != null && index != null && instance.getTabCount() > index.intValue()) {
			//
			instance.setSelectedIndex(index.intValue());
			//
		} // if
			//
	}

	private static void setPreferredWidth(@Nullable final Component component,
			@Nullable final Supplier<Double> supplier) {
		//
		if (component != null) {
			//
			final Double maxPreferredWidth = ObjectUtils.max(getPreferredWidth(component),
					supplier != null ? supplier.get() : null);
			//
			if (maxPreferredWidth != null) {
				//
				setPreferredWidth(maxPreferredWidth.intValue(), component);
				//
			} // if
				//
		} // if
			//
	}

	private static Predicate<Component> createFocusableComponentPredicate(
			final Collection<Class<?>> compontentClassNotFocus) {
		//
		return x -> {
			//
			final JTextComponent jtc = Util.cast(JTextComponent.class, x);
			//
			if (jtc != null) {
				//
				return jtc.isEditable();
				//
			} // if
				//
			return !Util.contains(compontentClassNotFocus, Util.getClass(x));
			//
		};
		//
	}

	private static void setFocusCycleRoot(@Nullable final Container instance, final boolean focusCycleRoot) {
		if (instance != null) {
			instance.setFocusCycleRoot(focusCycleRoot);
		}
	}

	private static void setFocusTraversalPolicy(@Nullable final Container instance, final FocusTraversalPolicy policy) {
		if (instance != null) {
			instance.setFocusTraversalPolicy(policy);
		}
	}

	@Nullable
	private static Component[] getComponents(@Nullable final Container instance) {
		return instance != null ? instance.getComponents() : null;
	}

	private static JPanel createMicrosoftWindowsCompatibilityWarningJPanel(final LayoutManager lm,
			final String microsoftWindowsCompatibilitySettingsPageUrl) {
		//
		final JPanel jPanelWarning = new JPanel(lm);
		//
		jPanelWarning.setBorder(BorderFactory.createTitledBorder(WARNING));
		//
		final JLabel jLabel = new JLabel("Please set Compatibility Mode to \"Windows 8\" or prior version");
		//
		if (lm instanceof MigLayout) {
			add(jPanelWarning, jLabel, WRAP);
		} else {
			add(jPanelWarning, jLabel);
		} // if
			//
		ATag aTag = null;
		//
		try {
			//
			aTag = ATagUtil.createByUrl(microsoftWindowsCompatibilitySettingsPageUrl,
					"Make older apps or programs compatible with the latest version of Windows - Microsoft Support");
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		add(jPanelWarning,
				pageTitle != null ? new JLabelLink(aTag)
						: new JLabel(StringUtils.defaultIfBlank(pageTitle,
								"Make older apps or programs compatible with Windows 10")));
		//
		return jPanelWarning;
		//
	}

	private static JPanel craeteSpeechApiInstallationWarningJPanel(
			final String microsoftSpeechPlatformRuntimeDownloadPageUrl) {
		//
		final JPanel jPanelWarning = new JPanel();
		//
		jPanelWarning.setBorder(BorderFactory.createTitledBorder(WARNING));
		//
		ATag aTag = null;
		//
		try {
			//
			aTag = ATagUtil.createByUrl(microsoftSpeechPlatformRuntimeDownloadPageUrl,
					"Download Microsoft Speech Platform - Runtime (Version 11) from Official Microsoft Download Center");
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final String pageTitle = JLabelLink.getChildrenAsString(aTag);
		//
		add(jPanelWarning, pageTitle != null ? new JLabelLink(aTag)
				: new JLabel(StringUtils.defaultIfBlank(pageTitle,
						"Download Microsoft Speech Platform - Runtime (Version 11) from Official Microsoft Download Center")));
		//
		return jPanelWarning;
		//
	}

	@Nullable
	private static Double getMaxPagePreferredHeight(final Object jTabbedPane) throws NoSuchFieldException {
		//
		Double preferredHeight = null;
		//
		final List<?> pages = Util.cast(List.class, testAndApply(Objects::nonNull, jTabbedPane,
				x -> Narcissus.getObjectField(x, Util.getDeclaredField(JTabbedPane.class, "pages")), null));
		//
		Object page = null;
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(pages); i++) {
			//
			page = get(pages, i);
			//
			if (f == null) {
				//
				f = Util.getDeclaredField(Util.getClass(page), COMPONENT);
				//
			} // if
				//
			preferredHeight = ObjectUtils.max(preferredHeight, getPreferredHeight(
					Util.cast(Component.class, page != null ? Narcissus.getObjectField(page, f) : null)));
			//
		} // for
			//
		return preferredHeight;
		//
	}

	@Nullable
	private static TemplateLoader getTemplateLoader(@Nullable final freemarker.template.Configuration instance) {
		return instance != null ? instance.getTemplateLoader() : null;
	}

	private static void setVisible(@Nullable final Component instance, final boolean b) {
		if (instance != null) {
			instance.setVisible(b);
		}
	}

	@Nullable
	private static IValue0<Boolean> IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull,
							Util.getDeclaredMethods(Util.forName("com.sun.jna.platform.win32.VersionHelpers")),
							Arrays::stream, null),
					m -> m != null && Objects.equals(Util.getName(m), "IsWindows10OrGreater")
							&& m.getParameterCount() == 0 && Util.isStatic(m)));
			//
			if (ms == null || ms.isEmpty()) {
				//
				return null;
				//
			} // if
				//
			final Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
			//
			return Unit.with(Util.cast(Boolean.class, invoke(m, null)));
			//
		} catch (final IllegalAccessException | InvocationTargetException e) {
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Map<String, Object> getOsVersionInfoExMap() {
		//
		try {
			//
			final Object osVersionInfoEx = getOsVersionInfoEx();
			//
			final List<Method> ms = osVersionInfoEx != null
					? Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(osVersionInfoEx)),
									Arrays::stream, null),
							x -> x != null && !Objects.equals(x.getReturnType(), Void.TYPE)))
					: null;
			//
			Method m = null;
			//
			if (osVersionInfoEx != null && ms != null) {
				//
				Map<String, Object> map = null;
				//
				for (int i = 0; i < IterableUtils.size(ms); i++) {
					//
					if ((m = get(ms, i)) == null || m.isSynthetic()) {
						//
						continue;
						//
					} // if
						//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Util.getName(m),
							invoke(m, osVersionInfoEx));
					//
				} // for
					//
				return map;
				//
			} // if
				//
		} catch (final IllegalAccessException | InvocationTargetException | InstantiationException
				| NoSuchMethodException e) {
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Object getOsVersionInfoEx()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//
		final Class<?> clz = Util.forName("com.sun.jna.platform.win32.Kernel32");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#INSTANCE
		//
		final List<Field> fs = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(clz), Arrays::stream, null),
						f -> Objects.equals(Util.getName(f), "INSTANCE") && Objects.equals(Util.getType(f), clz)
								&& Util.isStatic(f)));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
		//
		final Class<?> clzOsVersionInfoEx = Util.forName("com.sun.jna.platform.win32.WinNT$OSVERSIONINFOEX");
		//
		// https://java-native-access.github.io/jna/5.6.0/javadoc/com/sun/jna/platform/win32/Kernel32.html#GetVersionEx-com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX-
		//
		final List<Method> ms = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(clz), Arrays::stream, null),
						m -> Objects.equals(Util.getName(m), "GetVersionEx")
								&& Arrays.equals(new Class[] { clzOsVersionInfoEx }, Util.getParameterTypes(m))));
		//
		final Method m = IterableUtils.size(ms) == 1 ? get(ms, 0) : null;
		//
		final Object osVersionInfoEx = newInstance(getDeclaredConstructor(clzOsVersionInfoEx));
		//
		return Objects.equals(Boolean.TRUE, invoke(m, FieldUtils.readStaticField(f), osVersionInfoEx)) ? osVersionInfoEx
				: null;
		//
	}

	private static Object getInstance(final SpeechApi speechApi) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(speechApi)), Arrays::stream, null),
				f -> Objects.equals(Util.getName(f), "instance")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? get(fs, 0) : null;
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

	private static class JLabelLink extends JLabel {

		private static final long serialVersionUID = 8848505138795752227L;

		@Nullable
		private String url = null;

		{
			//
			super.setForeground(darker(Color.BLUE));
			//
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			//
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(final MouseEvent evt) {
					//
					try {
						//
						testAndAccept(Objects::nonNull, testAndApply(Objects::nonNull, url, URI::new, null),
								x -> browse(Desktop.getDesktop(), x));
						//
					} catch (final IOException | URISyntaxException e) {
						//
						TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
						//
					} // try
						//
				}

			});
			//
		}

		private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
			if (instance != null) {
				instance.browse(uri);
			}
		}

		private JLabelLink(@Nullable final ATag aTag) {
			//
			super(getChildrenAsString(aTag));
			//
			this.url = getValue(getAttributeByName(aTag, "href"));
			//
		}

		@Nullable
		private static String getValue(@Nullable final Attribute instance) {
			return instance != null ? instance.getValue() : null;
		}

		@Nullable
		@SuppressWarnings("java:S1612")
		private static String getChildrenAsString(@Nullable final ContainerTag<?> instance) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(Util.cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "children", true), null)));
				//
				final Stream<?> stream = testAndApply(Objects::nonNull, spliterator,
						x -> StreamSupport.stream(x, false), null);
				//
				return testAndApply(Objects::nonNull, Util.toList(Util.map(stream, x -> Util.toString(x))),
						x -> String.join("", x), null);
				//
			} catch (final IllegalAccessException e) {
				//
				return null;
				//
			} // try
				//
		}

		@Nullable
		private static Attribute getAttributeByName(@Nullable final Tag<?> instance, final String name) {
			//
			try {
				//
				final Spliterator<?> spliterator = spliterator(Util.cast(Iterable.class, testAndApply(Objects::nonNull,
						instance, x -> FieldUtils.readField(x, "attributes", true), null)));
				//
				final Stream<?> stream = testAndApply(Objects::nonNull, spliterator,
						x -> StreamSupport.stream(x, false), null);
				//
				final List<Attribute> as = Util.toList(Util.filter(Util.map(stream, x -> Util.cast(Attribute.class, x)),
						a -> Objects.equals(name, getName(a))));
				//
				if (as == null || as.isEmpty()) {
					//
					return null;
					//
				} else if (IterableUtils.size(as) != 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				return get(as, 0);
				//
			} catch (final IllegalAccessException e) {
				//
				return null;
				//
			} // try
				//
		}

		@Nullable
		private static String getName(@Nullable final Attribute instance) {
			return instance != null ? instance.getName() : null;
		}

		@Nullable
		private static Color darker(@Nullable final Color instance) {
			return instance != null ? instance.darker() : null;
		}

	}

	@Nullable
	private static Integer getTabIndexByTitle(@Nullable final List<?> pages, final Object title) {
		//
		Integer tabIndex = null;
		//
		Object page = null;
		//
		Field fieldTitle = null;
		//
		for (int i = 0; i < IterableUtils.size(pages); i++) {
			//
			if ((page = get(pages, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (fieldTitle == null) {
				//
				fieldTitle = getFieldByName(Util.getClass(page), "title");
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

	@Nullable
	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final List<Field> fs = Util.toList(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getName(f), name)));
		//
		Field field = null;
		//
		if (fs != null && !fs.isEmpty()) {
			//
			if (IterableUtils.size(fs) == 1) {
				//
				field = get(fs, 0);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return field;
		//
	}

	private LayoutManager cloneLayoutManager() {
		//
		final LayoutManager layoutManagerDefault = null;
		//
		LayoutManager lm = ObjectUtils.defaultIfNull(this.layoutManager, layoutManagerDefault);
		//
		if (lm instanceof MigLayout) {
			//
			final MigLayout migLayout = new MigLayout();
			//
			testAndAccept(PropertyResolverUtil::containsProperty, propertyResolver,
					"net.miginfocom.swing.MigLayout.layoutConstraints",
					(a, b) -> migLayout.setLayoutConstraints(PropertyResolverUtil.getProperty(a, b)));
			//
			lm = migLayout;
			//
		} else if (lm instanceof Serializable serializable) {
			//
			lm = Util.cast(LayoutManager.class, SerializationUtils.clone(serializable));
			//
		} // if
			//
		return ObjectUtils.defaultIfNull(lm, layoutManagerDefault);
		//
	}

	private static class TabFocusTraversalPolicy extends FocusTraversalPolicy {

		private List<Component> components = null;

		private TabFocusTraversalPolicy(final List<Component> components) {
			this.components = components;
		}

		@Nullable
		public Component getComponentAfter(final Container focusCycleRoot, final Component aComponent) {
			//
			final int size = IterableUtils.size(components);
			//
			return size != 0 ? get(components, (Util.intValue(indexOf(components, aComponent), -1) + 1) % size) : null;
			//
		}

		@Nullable
		public Component getComponentBefore(final Container focusCycleRoot, final Component aComponent) {
			//
			int idx = Util.intValue(indexOf(components, aComponent), -1) - 1;
			//
			if (idx < 0) {
				//
				idx = IterableUtils.size(components) - 1;
				//
			} // if
				//
			return get(components, idx);
			//
		}

		@Nullable
		public Component getDefaultComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		@Nullable
		public Component getLastComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, components.size() - 1) : null;
		}

		@Nullable
		public Component getFirstComponent(final Container focusCycleRoot) {
			return CollectionUtils.isNotEmpty(components) ? get(components, 0) : null;
		}

		@Override
		@Nullable
		public Component getInitialComponent(@Nullable final Window window) {
			return window != null ? super.getInitialComponent(window) : null;
		}

		@Nullable
		private static Integer indexOf(@Nullable final List<?> items, final Object item) {
			return items != null ? Integer.valueOf(items.indexOf(item)) : null;
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

	private static void add(@Nullable final Container instance, @Nullable final Component comp,
			@Nullable final Object constraints) {
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
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	private JPanel createImportResultPanel(final LayoutManager layoutManager) {
		//
		final JPanel panel = new JPanel();
		//
		panel.setLayout(layoutManager);
		//
		panel.setBorder(BorderFactory.createTitledBorder("Import Result"));
		//
		add(panel, new JLabel("Folder"));
		//
		add(panel,
				tfFolder = new JTextField(
						Util.getAbsolutePath(testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null))),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 3));
		//
		add(panel, new JLabel("File"));
		//
		add(panel, tfFile = new JTextField(), GROWX);
		//
		add(panel, new JLabel("Length"));
		//
		add(panel, tfFileLength = new JTextField(), String.format("%1$s,%2$s", GROWX, WRAP));
		//
		add(panel, new JLabel("File Digest"));
		//
		add(panel, tfFileDigest = new JTextField(), String.format("%1$s,span %2$s", GROWX, 3));
		//
		setEditable(false, tfFolder, tfFile, tfFileLength, tfFileDigest);
		//
		return panel;
		//
	}

	@Nullable
	private static URI toURI(@Nullable final URL instance) throws URISyntaxException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, Util.getDeclaredField(URL.class, HANDLER)) == null) {
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
		return instance.toURI();
		//
	}

	@Nullable
	private static Integer toInteger(@Nullable final Object object) {
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

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static void setEditable(final boolean editable, @Nullable final JTextComponent... jtcs) {
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		clear(tmImportResult);
		//
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

	@Nullable
	private static <T> Constructor<T> getDeclaredConstructor(@Nullable final Class<T> clz,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredConstructor(parameterTypes) : null;
	}

	@Nullable
	private static <T> T newInstance(@Nullable final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
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

	public static Pair<String, String> getMimeTypeAndBase64EncodedString(@Nullable final String folderPath,
			@Nullable final String filePath) throws IOException {
		//
		final File f = folderPath != null && filePath != null ? Util.toFile(Path.of(folderPath, filePath))
				: testAndApply(Objects::nonNull, filePath, File::new, null);
		//
		final ContentInfo ci = testAndApply(Util::isFile, f, new ContentInfoUtil()::findMatch, null);
		//
		String mimeType = getMimeType(ci);
		//
		if (StringUtils.isBlank(mimeType)
				&& or(x -> Util.matches(Util.matcher(x, getMessage(ci))), PATTERN_CONTENT_INFO_MESSAGE_MP3_1,
						PATTERN_CONTENT_INFO_MESSAGE_MP3_2, PATTERN_CONTENT_INFO_MESSAGE_MP3_3)) {
			//
			mimeType = "audio/mpeg";
			//
		} // if
			//
		return Pair.of(mimeType, testAndApply(Util::isFile, f,
				x -> encodeToString(Base64.getEncoder(), Files.readAllBytes(Path.of(Util.toURI(x)))), null));
		//
	}

	@Nullable
	private static String encodeToString(@Nullable final Encoder instance, @Nullable final byte[] src) {
		return instance != null && src != null ? instance.encodeToString(src) : null;
	}

	@Override
	public Toolkit getToolkit() {
		//
		if (toolkit == null) {
			//
			toolkit = Toolkit.getDefaultToolkit();
			//
		} // if
			//
		return getIfNull(toolkit, super::getToolkit);
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate,
			@Nullable final T value, @Nullable final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, @Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
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

	static interface ByteConverter {

		@Nullable
		byte[] convert(final byte[] source);

	}

	private static class AudioToFlacByteConverter implements ByteConverter {

		@Nullable
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
							Math.max(Util.intValue(audioStreamEncoderByteArrayLength, 0), 2), flac, false);
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

		@Nullable
		private static AudioFormat getFormat(@Nullable final AudioInputStream instance) {
			return instance != null ? instance.getFormat() : null;
		}

		@Nullable
		private static StreamConfiguration createStreamConfiguration(@Nullable final AudioFormat format) {
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

	@Nullable
	private static byte[] toByteArray(@Nullable final ByteArrayOutputStream instance) {
		return instance != null ? instance.toByteArray() : null;
	}

	private static class AudioToMp3ByteConverter implements ByteConverter, InitializingBean {

		@Note("Bitrate")
		@Nullable
		private Integer bitRate = null;

		@Note("Quality")
		@Nullable
		private Integer quality = null;

		@Nullable
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
				String string = Util.toString(quality);
				//
				if (Util.containsKey(map, string) || Util.containsKey(map, string = StringUtils.lowerCase(string))) {
					//
					setQuality(Util.get(map, string));
					//
				} // if
					//
			} // if
				//
		}

		@Nullable
		private Integer getQuality() throws IllegalAccessException {
			//
			if (quality != null) {
				return quality;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_QUALITY", true);
			//
			if (object instanceof Integer integer) {
				return integer;
			} // if
				//
			return null;
			//
		}

		public void setVbr(@Nullable final Object vbr) {
			//
			if (vbr == null) {
				//
				this.vbr = null;
				//
			} else if (vbr instanceof Boolean b) {
				//
				this.vbr = b;
				//
			} else {
				//
				final String string = Util.toString(vbr);
				//
				setVbr(StringUtils.isNotBlank(string) ? Boolean.valueOf(string) : null);
				//
			} // if
				//
		}

		@Nullable
		private Boolean getVbr() throws IllegalAccessException {
			//
			if (vbr != null) {
				return vbr;
			} // if
				//
			final Object object = FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_VBR", true);
			//
			if (object instanceof Boolean b) {
				return b;
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
				final Integer q = getQuality();
				//
				if (range != null && !range.contains(q)) {
					//
					throw new IllegalStateException(String.format("Under VBR,\"quality\" cound be with in %1$s to %2$s",
							RangeUtil.lowerEndpoint(range), RangeUtil.upperEndpoint(range)));
					//
				} // if
					//
			} // if
				//
		}

		/**
		 * @see <a href=
		 *      "https://github.com/Sciss/jump3r/blob/master/src/main/java/de/sciss/jump3r/mp3/Lame.java#L1067">de.sciss.jump3r.mp3.Lame.lame_init_params(de.sciss.jump3r.mp3.LameGlobalFlags)</a>
		 * 
		 * @see de.sciss.jump3r.mp3.Lame#lame_init_params(de.sciss.jump3r.mp3.LameGlobalFlags)
		 */
		@Nullable
		private static Range<Integer> createQualityRange() throws IOException {
			//
			final Class<?> clz = Lame.class;
			//
			try (final InputStream is = Util.getResourceAsStream(clz,
					String.format(CLASS_RESOURCE_FORMAT, StringUtils.replace(Util.getName(clz), ".", "/")))) {
				//
				final org.apache.bcel.classfile.Method[] ms = JavaClassUtil.getMethods(
						ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
				//
				Map<Integer, Integer> map = null;
				//
				for (int i = 0; ms != null && i < ms.length; i++) {
					//
					Util.put(map = getIfNull(map, LinkedHashMap::new), Integer.valueOf(i),
							createQuality(InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(
									testAndApply(Objects::nonNull, ms[i], x -> new MethodGen(x, null, null), null)))));
					//
				} // for
					//
				final List<Integer> counts = Util.toList(
						Util.filter(Util.map(Util.stream(Util.entrySet(map)), Util::getValue), Objects::nonNull));
				//
				final int size = IterableUtils.size(counts);
				//
				Integer count = null;
				//
				if (size == 1) {
					count = IterableUtils.get(counts, 0);
				} else if (size > 1) {
					throw new IllegalStateException();
				} // if
					//
				return count != null ? Range.closed(0, count.intValue() - 1) : null;
				//
			} // try
				//
		}

		@Nullable
		private static Integer createQuality(@Nullable final Instruction[] ins) {
			//
			Instruction in = null;
			//
			// index
			//
			Integer index = null;
			//
			// count
			//
			Integer count = null;
			//
			final int length = ins != null ? ins.length : 0;
			//
			for (int i = 0; ins != null && i < length; i++) {
				//
				if ((in = ins[i]) instanceof NEWARRAY newArray && equalsTypecode(newArray, Const.T_FLOAT)) {
					//
					index = Integer.valueOf(i);
					//
				} else if (Boolean.logicalAnd(in instanceof LDC, index != null)) {
					//
					count = Integer.valueOf(Util.intValue(count, 0) + 1);
					//
					if (i < ins.length - 2 && ins[i + 2] instanceof ASTORE) {
						//
						break;
						//
					} // if
						//
				} // if
					//
			} // for
				//
			return count != null ? Integer.valueOf(count.intValue()) : null;
			//
		}

		private static boolean equalsTypecode(@Nullable final NEWARRAY instance, final byte b) {
			return instance != null && instance.getTypecode() == b;
		}

		/**
		 * @see <a href=
		 *      "https://github.com/Sciss/jump3r/blob/master/src/main/java/de/sciss/jump3r/lowlevel/LameEncoder.java#L598">de.sciss.jump3r.lowlevel.LameEncoder.string2quality(java.lang.String,int)</a>
		 * 
		 * @see de.sciss.jump3r.lowlevel.LameEncoder#string2quality(java.lang.String,int)
		 */
		@Nullable
		private static Map<String, Integer> createQualityMap() throws IOException {
			//
			Map<String, Integer> map = null;
			//
			final Class<?> clz = LameEncoder.class;
			//
			try (final InputStream is = Util.getResourceAsStream(clz,
					String.format(CLASS_RESOURCE_FORMAT, StringUtils.replace(Util.getName(clz), ".", "/")))) {
				//
				final List<org.apache.bcel.classfile.Method> ms = Util
						.toList(Util.filter(
								testAndApply(Objects::nonNull,
										JavaClassUtil.getMethods(ClassParserUtil.parse(testAndApply(Objects::nonNull,
												is, x -> new ClassParser(x, null), null))),
										Arrays::stream, null),
								x -> Objects.equals(FieldOrMethodUtil.getName(x), "string2quality")));
				//
				org.apache.bcel.classfile.Method m = null;
				//
				IValue0<Map<String, Integer>> temp = null;
				//
				boolean found = false;
				//
				for (int i = 0; i < IterableUtils.size(ms); i++) {
					//
					if (found) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					if ((temp = createQualityMap(FieldOrMethodUtil.getConstantPool(m = IterableUtils.get(ms, i)),
							InstructionListUtil
									.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, m,
											x -> new MethodGen(x, null, null), null))))) != null) {
						//
						map = IValue0Util.getValue0(temp);
						//
						found = true;
						//
					} // if
						//
				} // for
					//
				return map;
				//
			} // try
				//
		}

		@Nullable
		private static IValue0<Map<String, Integer>> createQualityMap(final ConstantPool constantPool,
				@Nullable final Instruction[] instructions) {
			//
			IValue0<Map<String, Integer>> result = null;
			//
			String key = null;
			//
			Instruction instruction = null;
			//
			Number value = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = instructions[i]) instanceof LDC ldc) {
					//
					key = Util.toString(
							getValue(ldc, testAndApply(Objects::nonNull, constantPool, ConstantPoolGen::new, null)));
					//
				} else if (instruction instanceof BIPUSH biPush) {
					//
					Util.put(IValue0Util.getValue0(result = getIfNull(result, () -> Unit.with(new LinkedHashMap<>()))),
							key, (value = getValue(biPush)) != null ? Integer.valueOf(value.intValue()) : null);
					//
				} else if (instruction instanceof ICONST iConst) {
					//
					Util.put(IValue0Util.getValue0(result = getIfNull(result, () -> Unit.with(new LinkedHashMap<>()))),
							key, (value = getValue(iConst)) != null ? Integer.valueOf(value.intValue()) : null);
				} // if
					//
			} // for
				//
			return result;
			//
		}

		@Nullable
		private static Object getValue(@Nullable final LDC instance, @Nullable final ConstantPoolGen cpg) {
			return instance != null && cpg != null && cpg.getConstantPool() != null
					&& cpg.getConstantPool().getConstant(instance.getIndex()) != null ? instance.getValue(cpg) : null;
		}

		@Nullable
		private static Number getValue(@Nullable final ConstantPushInstruction instance) {
			return instance != null ? instance.getValue() : null;
		}

		private static int length(@Nullable final byte[] instance) {
			return instance != null ? instance.length : 0;
		}

		@Override
		@Nullable
		public byte[] convert(final byte[] source) {
			//
			LameEncoder encoder = null;
			//
			ByteArrayOutputStream baos = null;
			//
			try (final ByteArrayInputStream bais = testAndApply(x -> length(x) > 0, source, ByteArrayInputStream::new,
					null); final AudioInputStream ais = bais != null ? AudioSystem.getAudioInputStream(bais) : null) {
				//
				final byte[] inputBuffer = new byte[(encoder = ais != null ? new LameEncoder(ais.getFormat()
				//
				// bitRate
				//
						,
						ObjectUtils.defaultIfNull(bitRate,
								Util.cast(Integer.class,
										FieldUtils.readDeclaredStaticField(LameEncoder.class, "DEFAULT_BITRATE",
												true))),
						Util.cast(Integer.class,
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
					if ((baos = getIfNull(baos, ByteArrayOutputStream::new)) != null) {
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

	private static void clear(@Nullable final DefaultTableModel instance) {
		//
		Util.clear(instance != null ? instance.getDataVector() : null);
		//
	}

	@Nullable
	private static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	private static <T, U, E extends Throwable> void accept(@Nullable final FailableBiConsumer<T, U, E> instance,
			final T t, @Nullable final U u) throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static <T> void forEach(@Nullable final Stream<T> instance, @Nullable final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		} // if
			//
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
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
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
	private static Double getPreferredWidth(@Nullable final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	@Nullable
	private static Double getPreferredHeight(@Nullable final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getHeight()) : null;
		//
	}

	public static void main(final String[] args) {
		//
		final VoiceManager instance = new VoiceManager();
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		instance.setLayout(new MigLayout());
		//
		instance.setTitle("Voice Manager");
		//
		instance.pack();
		//
		setVisible(instance, true);
		//
	}

}