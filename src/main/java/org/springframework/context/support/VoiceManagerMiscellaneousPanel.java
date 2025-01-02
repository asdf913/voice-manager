package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CommentUtil;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.DrawingUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.LocaleID;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.ContainerTag;
import j2html.tags.ContainerTagUtil;
import j2html.tags.Tag;
import j2html.tags.specialized.ATag;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerMiscellaneousPanel extends JPanel
		implements Titled, InitializingBean, ApplicationContextAware, ActionListener, EnvironmentAware {

	private static final long serialVersionUID = 5735480312975758059L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerMiscellaneousPanel.class);

	private static final String WRAP = "wrap";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L780">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String WMIN_ONLY_FORMAT = "wmin %1$s";

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L534">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String SPAN_ONLY_FORMAT = "span %1$s";

	private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

	private static final String LANGUAGE = "Language";

	private static final FailablePredicate<File, RuntimeException> EMPTY_FILE_PREDICATE = f -> f != null && f.exists()
			&& isFile(f) && longValue(length(f), 0) == 0;

	private transient ApplicationContext applicationContext = null;

	private transient SpeechApi speechApi = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Url("https://www.microsoft.com/en-us/download/details.aspx?id=27224")
	private String microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl = null;

	@Nullable
	private transient IValue0<String> microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("DLL Path")
	private JTextComponent tfDllPath = null;

	private JTextComponent tfExportFile = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface SystemClipboard {
	}

	@SystemClipboard
	private AbstractButton btnDllPathCopy = null;

	@SystemClipboard
	private AbstractButton btnExportCopy = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ExportButton {
	}

	@ExportButton
	private AbstractButton btnExportMicrosoftSpeechObjectLibraryInformation = null;

	private AbstractButton btnExportBrowse = null;

	private String[] voiceIds = null;

	private transient LayoutManager layoutManager = null;

	private transient PropertyResolver propertyResolver = null;

	private Duration jSoupParseTimeout = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private String[] microsoftSpeechObjectLibraryAttributeNames = null;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Override
	public String getTitle() {
		return "Misc";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static boolean isFile(@Nullable final File instance) {
		return instance != null && instance.isFile();
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
			if (Narcissus.getField(instance, getDeclaredField(File.class, "path")) == null) {
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
	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
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

		private JLabelLink(final ATag aTag) {
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

		@SuppressWarnings("java:S1612")
		@Nullable
		private static String getChildrenAsString(final ContainerTag<?> instance) {
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
		private static <T> Spliterator<T> spliterator(@Nullable final Iterable<T> instance) {
			return instance != null ? instance.spliterator() : null;
		}

		@Nullable
		private static Attribute getAttributeByName(final Tag<?> instance, final String name) {
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

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		final Iterable<Entry<String, Object>> entrySet = Util
				.entrySet(ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class));
		//
		if (Util.iterator(entrySet) != null) {
			//
			AutowireCapableBeanFactory acbf = null;
			//
			List<Field> fs = null;
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
					testAndAccept(
							Objects::nonNull, Util
									.cast(LayoutManager.class,
											FactoryBeanUtil
													.getObject(
															Util.cast(
																	FactoryBean.class, MapUtils
																			.getObject(
																					Util.cast(Map.class,
																							Narcissus.getObjectField(
																									acbf,
																									IterableUtils.get(
																											fs, i))),
																					Util.getKey(entry))))),
							this::setLayout);
					//
				} // for
					//
			} // for
				//
		} // if
			//
		final Object speechApiInstance = getInstance(speechApi);
		//
		if (speechApiInstance instanceof SpeechApiSpeechServerImpl) {
			//
			add(new JLabelLink(ContainerTagUtil.withText(
					new ATag().withHref(microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl),
					IValue0Util.getValue0(getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle()))), WRAP);
			//
		} // if
			//
		final IValue0<Object> dllPath = getDllPath(speechApiInstance);
		//
		JPanel panelDllPath = null;
		//
		if (dllPath != null) {
			//
			(panelDllPath = new JPanel()).setLayout(cloneLayoutManager());
			//
			panelDllPath.setBorder(BorderFactory.createTitledBorder("Dll Path"));
			//
			panelDllPath.add(tfDllPath = new JTextField(Util.toString(IValue0Util.getValue0(dllPath))));
			//
			panelDllPath.add(btnDllPathCopy = new JButton("Copy"));
			//
			add(panelDllPath, String.format("%1$s,span %2$s", WRAP, 2));
			//
		} // if
			//
			// Find the maximum width of the "java.awt.Component" instance from the field
			// with "org.springframework.context.support.VoiceManager.Group" annotation with
			// same value (i.e. "Short Export Button"),
			// then set the maximum width to each "java.awt.Component" in the list.
			//
		final List<Component> cs = getObjectsByGroupAnnotation(this, "Short Export Button", Component.class);
		//
		if (CollectionUtils.isNotEmpty(cs)) {
			//
			setPreferredWidth(intValue(getPreferredWidth(
					Collections.max(cs, (a, b) -> ObjectUtils.compare(getPreferredWidth(a), getPreferredWidth(b)))), 0),
					cs);
			//
		} // if
			//
			// btnExportMicrosoftSpeechObjectLibraryInformation
			//
		final StringBuilder btnExportMicrosoftSpeechObjectLibraryInformationName = new StringBuilder("Export ");
		//
		if (StringUtils.isNotBlank(append(btnExportMicrosoftSpeechObjectLibraryInformationName,
				StringUtils.defaultIfBlank(Provider.getProviderName(Util.cast(Provider.class, speechApi)),
						"Microsoft Speech Object Library")))) {
			//
			append(btnExportMicrosoftSpeechObjectLibraryInformationName, ' ');
			//
		} // if
			//
		add(btnExportMicrosoftSpeechObjectLibraryInformation = new JButton(
				Util.toString(append(btnExportMicrosoftSpeechObjectLibraryInformationName, "Information"))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		final JPanel panelFile = new JPanel();
		//
		panelFile.setLayout(cloneLayoutManager());
		//
		panelFile.setBorder(BorderFactory.createTitledBorder("File"));
		//
		panelFile.add(tfExportFile = new JTextField(), String.format(WMIN_ONLY_FORMAT, 300));
		//
		panelFile.add(btnExportCopy = new JButton("Copy"));
		//
		panelFile.add(btnExportBrowse = new JButton("Browse"));
		//
		add(panelFile, String.format(SPAN_ONLY_FORMAT, 2));
		//
		setEditable(false, tfDllPath, tfExportFile);
		//
		addActionListener(this, btnExportMicrosoftSpeechObjectLibraryInformation, btnExportCopy, btnExportBrowse,
				btnDllPathCopy);
		//
		setEnabled(SpeechApi.isInstalled(speechApi) && voiceIds != null,
				btnExportMicrosoftSpeechObjectLibraryInformation);
		//
		if (panelDllPath != null) {
			//
			setPreferredWidth((int) Math.max(getPreferredWidth(panelDllPath), getPreferredWidth(panelFile)),
					panelDllPath, panelFile);
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
		if (anyMatch(Util.stream(findFieldsByValue(Util.getDeclaredFields(getClass()), this, source)),
				f -> isAnnotationPresent(f, ExportButton.class))) {
			//
			Util.setText(tfExportFile, null);
			//
			actionPerformedForExportButtons(source, headless);
			//
		} // if
			//
		final boolean nonTest = !isTestMode();
		//
		// if the "source" is one of the value of the field annotated with
		// "@SystemClipboard", pass the "source" to
		// "actionPerformedForSystemClipboardAnnotated(java.lang.Object)" method
		//
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null),
				f -> isAnnotationPresent(f, SystemClipboard.class)));
		//
		testAndRun(Util.contains(Util.toList(Util.filter(
				FailableStreamUtil.stream(FailableStreamUtil.map(fs, f -> FieldUtils.readField(f, this, true))),
				Objects::nonNull)), source), () -> actionPerformedForSystemClipboardAnnotated(nonTest, source));
		//
		if (Objects.equals(source, btnExportBrowse)) {
			//
			actionPerformedForExportBrowse(headless);
			//
		} // if
			//
	}

	private void actionPerformedForExportBrowse(final boolean headless) {
		//
		try {
			//
			final File file = testAndApply(Objects::nonNull, Util.getText(tfExportFile), File::new, null);
			//
			testAndAccept(Objects::nonNull, toURI(file), x -> browse(Desktop.getDesktop(), x));
			//
		} catch (final IOException e) {
			//
			errorOrAssertOrShowException(headless, e);
			//
		} // try
			//
	}

	private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
		if (instance != null) {
			instance.browse(uri);
		}
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private void actionPerformedForSystemClipboardAnnotated(final boolean nonTest, final Object source) {
		//
		final Clipboard clipboard = getSystemClipboard(getToolkit());
		//
		IValue0<String> stringValue = null;
		//
		if (Objects.equals(source, btnExportCopy)) {
			//
			stringValue = Unit.with(Util.getText(tfExportFile));
			//
		} else if (Objects.equals(source, btnDllPathCopy)) {
			//
			stringValue = Unit.with(Util.getText(tfDllPath));
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

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private void actionPerformedForExportButtons(final Object source, final boolean headless) {
		//
		if (Objects.equals(source, btnExportMicrosoftSpeechObjectLibraryInformation)) {
			//
			final Path path = Path
					.of(String.format("MicrosoftSpeechObjectLibrary_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date()));
			//
			final File file = path != null ? path.toFile() : null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = Files.newOutputStream(path)) {
				//
				WorkbookUtil.write(workbook = createMicrosoftSpeechObjectLibraryWorkbook(speechApi,
						languageCodeToTextObjIntFunction, microsoftSpeechObjectLibraryAttributeNames), os);
				//
				Util.setText(tfExportFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				testAndAccept(EMPTY_FILE_PREDICATE, file, FileUtils::deleteQuietly);
				// ini
			} // try
				//
			return;
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private static interface ObjectMap {

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
					return Unit.with(VoiceManagerMiscellaneousPanel.invoke(method, runnable, args));
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
				testAndAccept(m -> throwable != null || isStatic(m), method,
						m -> VoiceManagerMiscellaneousPanel.invoke(m, throwable));
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
			if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
				//
				final Object key = args[0];
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
			} else if (Objects.equals(methodName, "containsObject") && args != null && args.length > 0) {
				//
				return Unit.with(Boolean.valueOf(Util.containsKey(map, args[0])));
				//
			} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
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

	@Nullable
	private static Workbook createMicrosoftSpeechObjectLibraryWorkbook(final SpeechApi speechApi,
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction, final String... attributes) {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(speechApi);
		//
		final String commonPrefix = String.join("",
				StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
		//
		String voiceId = null;
		//
		final String[] as = toArray(Util.toList(
				Util.filter(testAndApply(Objects::nonNull, attributes, Arrays::stream, null), StringUtils::isNotEmpty)),
				new String[] {});
		//
		ObjectMap objectMap = null;
		//
		for (int i = 0; voiceIds != null && as != null && i < voiceIds.length; i++) {
			//
			if (sheet == null) {
				//
				setMicrosoftSpeechObjectLibrarySheetFirstRow(
						sheet = WorkbookUtil.createSheet(workbook = getIfNull(workbook, XSSFWorkbook::new)), as);
				//
			} // if
				//
			if (sheet != null && (row = SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1)) == null) {
				//
				continue;
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : null, 0)),
					commonPrefix);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : 0, 0)),
					StringUtils.defaultIfBlank(testAndApply(StringUtils::contains, commonPrefix, voiceId = voiceIds[i],
							StringUtils::substringAfter, null), voiceId));
			//
			if (objectMap == null && (objectMap = Reflection.newProxy(ObjectMap.class, new IH())) != null) {
				//
				ObjectMap.setObject(objectMap, Workbook.class, workbook);
				//
				ObjectMap.setObject(objectMap, SpeechApi.class, speechApi);
				//
			} // if
				//
			ObjectMap.setObject(objectMap, Sheet.class, sheet);
			//
			ObjectMap.setObject(objectMap, Row.class, row);
			//
			setMicrosoftSpeechObjectLibrarySheet(objectMap, voiceId, as, languageCodeToTextObjIntFunction);
			//
		} // for
			//
		ObjectMap.setObject(objectMap, Sheet.class, WorkbookUtil.createSheet(workbook, "Locale ID"));
		//
		ObjectMap.setObject(objectMap, LocaleID[].class, LocaleID.values());
		//
		setLocaleIdSheet(objectMap);
		//
		setAutoFilter(sheet);
		//
		return workbook;
		//
	}

	private static void setMicrosoftSpeechObjectLibrarySheetFirstRow(@Nullable final Sheet sheet,
			@Nullable final String[] columnNames) {
		//
		final Row row = sheet != null ? SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1) : null;
		//
		if (row != null) {
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), "Common Prefix");
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), "ID");
			//
			for (int j = 0; columnNames != null && j < columnNames.length; j++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), columnNames[j]);
				//
			} // for
				//
		} // if
			//
	}

	private static void setAutoFilter(@Nullable final Sheet sheet) {
		//
		final Row row = sheet != null ? sheet.getRow(sheet.getLastRowNum()) : null;
		//
		if (sheet != null && row != null && sheet.getFirstRowNum() < sheet.getLastRowNum()) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
	}

	private static void setLocaleIdSheet(@Nullable final ObjectMap objectMap) {
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		final LocaleID[] localeIds = ObjectMap.getObject(objectMap, LocaleID[].class);
		//
		LocaleID localeId = null;
		//
		List<Field> fs = null;
		//
		Row row = null;
		//
		Method methodIsAccessible = null;
		//
		for (int i = 0; localeIds != null && i < localeIds.length; i++) {
			//
			if ((localeId = localeIds[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if (fs == null) {
				//
				fs = Util.toList(sorted(Util.filter(
						testAndApply(Objects::nonNull, FieldUtils.getAllFields(LocaleID.class), Arrays::stream, null),
						x -> x != null && !Objects.equals(Util.getType(x), Util.getDeclaringClass(x))
								&& !x.isSynthetic() && !isStatic(x)),
						(a, b) -> StringUtils.compare(Util.getName(getPackage(Util.getDeclaringClass(a))),
								Util.getName(getPackage(Util.getDeclaringClass(b))))));
				//
			} // if
				//
				// Header Row
				//
			addLocaleIdSheetHeaderRow(sheet, fs);
			//
			ObjectMap.setObject(objectMap, Method.class, methodIsAccessible = getIfNull(methodIsAccessible,
					VoiceManagerMiscellaneousPanel::getAccessibleObjectIsAccessibleMethod));
			//
			row = addLocaleIdRow(objectMap, fs, localeId);
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
	}

	@Nullable
	private static Package getPackage(@Nullable final Class<?> instance) {
		return instance != null ? instance.getPackage() : null;
	}

	@Nullable
	private static Row addLocaleIdRow(@Nullable final ObjectMap objectMap, @Nullable final List<Field> fs,
			final Object instance) {
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		Field f = null;
		//
		Object value = null;
		//
		Row row = null;
		//
		for (int j = 0; fs != null && j < fs.size(); j++) {
			//
			if ((f = fs.get(j)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(Util.getType(f), Integer.TYPE)) {
				//
				value = Integer.valueOf(Narcissus.getIntField(instance, f));
				//
			} else {
				//
				value = Narcissus.getField(instance, f);
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(
					row = getIfNull(row, () -> SheetUtil.createRow(sheet, intValue(getPhysicalNumberOfRows(sheet), 0))),
					intValue(getPhysicalNumberOfCells(row), 0)), Util.toString(value));
			//
		} // for
			//
		return row;
		//
	}

	private static Method getAccessibleObjectIsAccessibleMethod() {
		//
		final List<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(AccessibleObject.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(Util.getName(m), "isAccessible") && m.getParameterCount() == 0));
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> get(x, 0), null);
		//
	}

	private static void addLocaleIdSheetHeaderRow(final Sheet sheet, @Nullable final List<Field> fs) {
		//
		final int physicalNumberOfRows = intValue(getPhysicalNumberOfRows(sheet), 0);
		//
		if (physicalNumberOfRows == 0) {
			//
			Row row = null;
			//
			for (int j = 0; fs != null && j < fs.size(); j++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(
						row = getIfNull(row, () -> SheetUtil.createRow(sheet, intValue(physicalNumberOfRows, 0))),
						intValue(getPhysicalNumberOfCells(row), 0)), Util.getName(fs.get(j)));
				//
			} // for
				//
		} // if
			//
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
		return object != null ? object : get(defaultSupplier);
	}

	@Nullable
	private static <T, E extends Throwable> T get(@Nullable final FailableSupplier<T, E> instance) throws E {
		return instance != null ? instance.get() : null;
	}

	@Nullable
	private static Integer getPhysicalNumberOfCells(@Nullable final Row instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfCells()) : null;
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

	private static void setMicrosoftSpeechObjectLibrarySheet(@Nullable final ObjectMap objectMap, final String voiceId,
			final String[] attributes, final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		//
		final Workbook workbook = ObjectMap.getObject(objectMap, Workbook.class);
		//
		final Sheet sheet = ObjectMap.getObject(objectMap, Sheet.class);
		//
		final Row row = ObjectMap.getObject(objectMap, Row.class);
		//
		Cell cell = null;
		//
		Drawing<?> drawing = null;
		//
		CreationHelper creationHelper = null;
		//
		Comment comment = null;
		//
		// attribute
		//
		String attribute = null;
		//
		// value
		//
		String value = null;
		//
		for (int j = 0; attributes != null && j < attributes.length; j++) {
			//
			if (drawing == null) {
				//
				drawing = SheetUtil.createDrawingPatriarch(sheet);
				//
			} // if
				//
			if (creationHelper == null) {
				//
				creationHelper = WorkbookUtil.getCreationHelper(workbook);
				//
			} // if
				//
			try {
				//
				CellUtil.setCellValue(
						cell = RowUtil.createCell(row, Math.max(row != null ? row.getLastCellNum() : 0, 0)),
						value = SpeechApi.getVoiceAttribute(ObjectMap.getObject(objectMap, SpeechApi.class), voiceId,
								attribute = attributes[j]));
				//
				if (Objects.equals(LANGUAGE, attribute)) {
					//
					CommentUtil.setString(
							comment = DrawingUtil.createCellComment(drawing,
									CreationHelperUtil.createClientAnchor(creationHelper)),
							CreationHelperUtil.createRichTextString(creationHelper,
									ObjIntFunctionUtil.apply(languageCodeToTextObjIntFunction, value, 16)));
					//
					CellUtil.setCellComment(cell, comment);
					//
				} // if
					//
			} catch (final Error e) {
				//
				CommentUtil.setString(
						comment = DrawingUtil.createCellComment(drawing,
								CreationHelperUtil.createClientAnchor(creationHelper)),
						CreationHelperUtil.createRichTextString(creationHelper, e.getMessage()));
				//
				CommentUtil.setAuthor(comment, Util.getName(Util.getClass(e)));
				//
				CellUtil.setCellComment(cell, comment);
				//
			} // try
				//
		} // for
			//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	private static <T> boolean anyMatch(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(Util.getClass(instance)))
				&& instance.anyMatch(predicate);
		//
	}

	@Nullable
	private static List<Field> findFieldsByValue(@Nullable final Field[] fs, final Object instance,
			@Nullable final Object value) {
		//
		Field f = null;
		//
		Object fieldValue = null;
		//
		List<Field> list = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if ((fieldValue = testAndApply(VoiceManagerMiscellaneousPanel::isStatic, f, Narcissus::getStaticField,
					a -> testAndApply(Objects::nonNull, instance, b -> Narcissus.getField(b, a), null))) != value
					|| !Objects.equals(fieldValue, value)) {
				//
				continue;
				//
			} // if
				//
			testAndAccept((a, b) -> !Util.contains(a, b), list = ObjectUtils.getIfNull(list, ArrayList::new), f,
					Util::add, null);
			//
		} // for
			//
		return list;
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> a, @Nullable final BiConsumer<T, U> b) {
		if (test(instance, t, u)) {
			accept(a, t, u);
		} else {
			accept(b, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
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
	private static StringBuilder append(@Nullable final StringBuilder instance, final char c) {
		return instance != null ? instance.append(c) : null;
	}

	@Nullable
	private static StringBuilder append(@Nullable final StringBuilder instance, final String string) {
		return instance != null ? instance.append(string) : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Nullable
	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
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
		final FailableStream<Field> fs = new FailableStream<>(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(VoiceManager.class), Arrays::stream, null), f -> {
					final Group g = isAnnotationPresent(f, Group.class) ? f.getAnnotation(Group.class) : null;
					return StringUtils.equals(g != null ? g.value() : null, group);
				}));
		//
		return Util.toList(FailableStreamUtil.stream(
				FailableStreamUtil.map(fs, f -> instance != null ? FieldUtils.readField(f, instance, true) : null)));
		//
	}

	private static boolean isAnnotationPresent(@Nullable final AnnotatedElement instance,
			@Nullable final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
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

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws E {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static <T, U, E extends Throwable> void accept(@Nullable final FailableBiConsumer<T, U, E> instance,
			final T t, final U u) throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Nullable
	private static IValue0<Object> getDllPath(final Object instance) {
		//
		final Class<?>[] declaredClasses = getDeclaredClasses(Util.getClass(instance));
		//
		List<Field> fs = null;
		//
		Field f = null;
		//
		List<Method> ms = null;
		//
		Method m = null;
		//
		IValue0<Object> dllPath = null;
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		for (int i = 0; declaredClasses != null && i < declaredClasses.length; i++) {
			//
			final Class<?> declaredClass = declaredClasses[i];
			//
			if (declaredClass == null
					//
					|| (fs = Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredFields(declaredClass), Arrays::stream, null),
							x -> Objects.equals(Util.getType(x), declaredClass)))) == null
					|| IterableUtils.size(fs) != 1 || (f = get(fs, 0)) == null
					//
					|| (ms = Util
							.toList(Util.filter(
									testAndApply(Objects::nonNull, Util.getDeclaredMethods(declaredClass),
											Arrays::stream, null),
									x -> Objects.equals(Util.getName(x), "getDllPath")))) == null
					|| IterableUtils.size(ms) != 1 || (m = get(ms, 0)) == null) {
				continue;
			} // if
				//
			try {
				//
				dllPath = Unit.with(invoke(m, get(f, null)));
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
		} // for
			//
		return dllPath;
		//
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	@Nullable
	private static Class<?>[] getDeclaredClasses(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredClasses() : null;
	}

	private IValue0<String> getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle() {
		//
		if (microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle == null) {
			//
			microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle = getPageTitle(
					microsoftSpeechPlatformRuntimeLanguagesDownloadPageUrl, jSoupParseTimeout);
			//
		} // if
			//
		return microsoftSpeechPlatformRuntimeLanguagesDownloadPageTitle;
		//
	}

	@Nullable
	private static IValue0<String> getPageTitle(final String url, final Duration timeout) {
		//
		try {
			//
			return Unit
					.with(ElementUtil
							.text(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil
											.getElementsByTag(
													testAndApply(Objects::nonNull,
															testAndApply(StringUtils::isNotBlank, url,
																	x -> new URI(x).toURL(), null),
															x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null),
													"title"),
									x -> get(x, 0), null)));
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
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

	@Nullable
	private static <E> E get(@Nullable final List<E> instance, final int index) {
		return instance != null ? instance.get(index) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate,
			@Nullable final T value, final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

}