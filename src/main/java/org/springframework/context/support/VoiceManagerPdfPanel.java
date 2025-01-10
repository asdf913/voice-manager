package org.springframework.context.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
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
import com.helger.css.ECSSUnit;
import com.helger.css.propertyvalue.CSSSimpleValueWithUnit;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerPdfPanel extends JPanel
		implements Titled, InitializingBean, ActionListener, ApplicationContextAware, EnvironmentAware {

	private static final long serialVersionUID = 284477348908531649L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerPdfPanel.class);

	private transient SpeechApi speechApi = null;

	private AbstractButton btnExecute = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("HTML")
	private JTextComponent taHtml = null;

	private JTextComponent tfText = null;

	private transient ApplicationContext applicationContext = null;

	private transient PropertyResolver propertyResolver = null;

	@Override
	public String getTitle() {
		return "PDF";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
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
		add(new JLabel("HTML"));
		//
		final JScrollPane jsp = new JScrollPane(taHtml = new JTextArea(PropertyResolverUtil
				.getProperty(propertyResolver, "org.springframework.context.support.VoiceManagerPdfPanel.html")));
		//
		final Dimension preferredSize = jsp.getPreferredSize();
		//
		if (preferredSize != null) {
			//
			jsp.setPreferredSize(new Dimension((int) preferredSize.getWidth(), (int) preferredSize.getHeight() * 2));
			//
		} // if
			//
		add(jsp, "growx,wrap");
		//
		final JComponent jLabel = new JLabel("Text");
		//
		jLabel.setToolTipText("Voice");
		//
		add(jLabel);
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerPdfPanel.text")), "growx,wrap");
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"));
		//
		btnExecute.addActionListener(this);
		//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			final Path pathHtml = Path.of("test.html");
			//
			final int fontSize = 80;
			//
			final Map<String, String> style = new LinkedHashMap<>(
					Map.of("text-align", "center", "display", "block", "margin-left", "auto", "margin-right", "auto"));
			//
			style.put("font-size", new CSSSimpleValueWithUnit(new BigDecimal(fontSize), ECSSUnit.PX).getFormatted());
			//
			final File file = toFile(Path.of("test.pdf"));
			//
			System.out.println(getAbsolutePath(file));
			//
			PDDocument document = null;
			//
			try {
				//
				final StringBuilder sb = new StringBuilder(Util.getText(taHtml));
				//
				FileUtils
						.writeStringToFile(
								toFile(pathHtml), Util
										.toString(
												append(sb.insert(0,
														String.format("<div style=\"%1$s\">", Util.collect(
																Util.map(Util.stream(Util.entrySet(style)),
																		x -> StringUtils.joinWith(":", Util.getKey(x),
																				Util.getValue(x))),
																Collectors.joining(";")))),
														"</div>")),
								StandardCharsets.UTF_8, false);
				//
				document = Loader.loadPDF(pdf(pathHtml));
				//
				final Map<Integer, String> map = new LinkedHashMap<>(Collections.singletonMap(0, "100% Speed"));
				//
				map.put(-1, "90% Speed");
				//
				map.put(-2, "80% Speed");
				//
				map.put(-3, "70% Speed");
				//
				map.put(-4, "60% Speed");
				//
				map.put(-5, "50% Speed");
				//
				map.put(-6, "40% Speed");
				//
				map.put(-7, "30% Speed");
				//
				map.put(-8, "20% Speed");
				//
				map.put(-9, "10% Speed");
				//
				final IH ih = new IH();
				//
				final StringMap stringMap = Reflection.newProxy(StringMap.class, ih);
				//
				if (stringMap != null) {
					//
					stringMap.setString("text", Util.getText(tfText));
					//
					stringMap.setString("voiceId", "TTS_MS_JA-JP_HARUKA_11.0");
					//
				} // if
					//
				final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, ih);
				//
				if (objectMap != null) {
					//
					objectMap.setObject(PDDocument.class, document);
					//
					objectMap.setObject(SpeechApi.class, speechApi);
					//
					objectMap.setObject(PDFont.class, new PDType1Font(FontName.HELVETICA));
					//
					objectMap.setObject(File.class, file);
					//
					objectMap.setObject(StringMap.class, stringMap);
					//
				} // if
					//
				addTextAndVoice(objectMap, 14, map, 100, 61);
				//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} // if
			//
	}

	private static StringBuilder append(final StringBuilder instance, final String str) {
		return instance != null ? instance.append(str) : instance;
	}

	private static interface ObjectMap {

		<T> T getObject(final Class<?> clz);

		<T> void setObject(final Class<T> key, @Nullable final T value);

		@Nullable
		static <T> T getObject(@Nullable final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, final String value);

		@Nullable
		static String getString(@Nullable final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}
	}

	private static class IH implements InvocationHandler {

		private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

		private Map<Object, Object> objects = null;

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
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!Util.containsKey(getObjects(), key)) {
						//
						throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
								testAndApply(IH::isArray, Util.cast(Class.class, key), Util::getSimpleName, x -> key)));
						//
					} // if
						//
					return MapUtils.getObject(getObjects(), key);
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					Util.put(getObjects(), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} // if
				//
			final IValue0<?> iValue0 = handleStringMap(methodName, getStrings(), args);
			//
			if (iValue0 != null) {
				//
				return IValue0Util.getValue0(iValue0);
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

	private static void addTextAndVoice(@Nullable final ObjectMap objectMap, final int fontSize,
			final Map<Integer, String> map, final int volume, final int size) throws IOException {
		//
		final PDDocument document = ObjectMap.getObject(objectMap, PDDocument.class);
		//
		if (document != null && document.getNumberOfPages() > 0) {
			//
			final PDPage pd = document.getPage(0);
			//
			final PDFRenderer pdfRenderer = new PDFRenderer(document);
			//
			final Path page1Path = Path.of("page1.png");
			//
			System.out.println(getAbsolutePath(toFile(page1Path)));
			//
			final BufferedImage bi = pdfRenderer.renderImage(0);
			//
			ImageIO.write(bi, "png", toFile(page1Path));
			//
			final Integer largestY = getLargestY(bi);
			//
			Files.delete(page1Path);
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd, AppendMode.PREPEND, true);
			//
			final Path pathAudio = Path.of("test.wav");
			//
			System.out.println(getAbsolutePath(toFile(pathAudio)));
			//
			int index = 0;
			//
			Integer key = null;
			//
			String value = null;
			//
			Duration duration = null;
			//
			final StringMap stringMap = ObjectMap.getObject(objectMap, StringMap.class);
			//
			final String text = StringMap.getString(stringMap, "text");
			//
			setSubject(document.getDocumentInformation(), text);
			//
			PDEmbeddedFile pdfEmbeddedFile = null;
			//
			PDComplexFileSpecification fileSpec = null;
			//
			PDAnnotationFileAttachment attachment = null;
			//
			for (final Entry<Integer, String> entry : Util.entrySet(map)) {
				//
				if ((key = Util.getKey(entry)) == null) {
					//
					continue;
					//
				} // if
					//
				writeVoiceToFile(ObjectMap.getObject(objectMap, SpeechApi.class), text,
						StringMap.getString(stringMap, "voiceId"), Util.intValue(key, 0), volume, toFile(pathAudio));
				//
//				try (final InputStream is = PdfTest.class.getResourceAsStream("\\NotoSansCJKjp-Regular.otf")) {
				//
//					font = PDType0Font.load(document, new OTFParser().parseEmbedded(is), false);
				//
//				} // try
				//
				Pattern pattern = null;
				//
				float lastHeight = 0;
				//
				final PDRectangle md = getMediaBox(pd);
				//
				try (final InputStream is = Files.newInputStream(pathAudio)) {
					//
					(pdfEmbeddedFile = new PDEmbeddedFile(document, is)).setSubtype(getMimeType(pathAudio));
					//
					testAndAccept((a, b) -> b != null, pdfEmbeddedFile, toFile(pathAudio),
							(a, b) -> setSize(a, Util.intValue(length(b), 0)));
					//
					(fileSpec = new PDComplexFileSpecification()).setFile(getName(toFile(pathAudio)));
					//
					fileSpec.setEmbeddedFile(pdfEmbeddedFile);
					//
					(attachment = new PDAnnotationFileAttachment()).setFile(fileSpec);
					//
					// Position on the page
					//
					attachment.setRectangle(new PDRectangle(index++ * size,
							getHeight(md) - Util.intValue(largestY, 0) - size, size, size));
					//
					attachment.setContents(value = Util.getValue(entry));
					//
					Util.add(getAnnotations(pd), attachment);
					//
					// Label (Speed)
					//
					cs.beginText();
					//
					final PDFont font = ObjectMap.getObject(objectMap, PDFont.class);
					//
					cs.setFont(font, fontSize);
					//
					final String value_ = value;
					//
					cs.newLineAtOffset((index - 1) * size + getTextWidth(
							//
							value = testAndApply(x -> and(x, y -> matches(y), y -> groupCount(y) > 0),
									matcher(pattern = ObjectUtils.getIfNull(pattern,
											() -> Pattern.compile("^(\\d+%).+$")), value),
									x -> group(x, 1), x -> value_)
							//
							, font, fontSize) / 2, lastHeight = (getHeight(md) - Util.intValue(largestY, 0) - size
					//
									- (font.getFontDescriptor().getAscent() / 1000 * fontSize)
									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize))
					//
					);
					//
//					cs.newLineAtOffset((pd.getMediaBox().getWidth() - getTextWidth(text, font, fontSize)) / 2,
//							pd.getMediaBox().getHeight() - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
//									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize));
					//
					cs.showText(value);
					//
					cs.endText();
					//
					// Label (Duration)
					//
					if ((duration = getAudioDuration(toFile(pathAudio))) != null) {
						//
						cs.beginText();
						//
						cs.setFont(font, fontSize);
						//
						cs.newLineAtOffset((index - 1) * size + getTextWidth(value, font, fontSize) / 2,
								lastHeight - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
										+ (font.getFontDescriptor().getDescent() / 1000 * fontSize)
						//
						);
						cs.showText(String.format("%1$s s", duration.toMillis() / 1000d));
						//
						cs.endText();
						//
					} // if
						//
				} catch (final Exception e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} finally {
					//
					Files.delete(pathAudio);
					//
				} // try
					//
			} // for
				//
			IOUtils.close(cs);
			//
			final File file = ObjectMap.getObject(objectMap, File.class);
			//
			document.save(file);
			//
		} // if
			//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, @Nullable final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, @Nullable final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	private static void setSize(@Nullable final PDEmbeddedFile instance, final int size) {
		if (instance != null) {
			instance.setSize(size);
		}
	}

	@Nullable
	private static Long length(@Nullable final File instance) {
		return instance != null ? Long.valueOf(instance.length()) : null;
	}

	@Nullable
	private static String group(@Nullable final MatchResult instance, final int group) {
		return instance != null ? instance.group(group) : null;
	}

	private static <T> boolean and(@Nullable final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	private static void writeVoiceToFile(@Nullable final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, @Nullable final File file) {
		if (instance != null) {
			instance.writeVoiceToFile(text, voiceId, rate, volume, file);
		}
	}

	public static void main(final String[] args) throws Exception {
		//
		final VoiceManagerPdfPanel instance = new VoiceManagerPdfPanel();
		//
		instance.afterPropertiesSet();
		//
		instance.actionPerformed(new ActionEvent(instance.btnExecute, 0, null));
		//
	}

	private static String getMimeType(@Nullable final Path path) throws IOException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, path, Files::newInputStream, null)) {
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, is, new ContentInfoUtil()::findMatch, null);
			//
			return ci != null ? ci.getMimeType() : null;
			//
		} // try
			//
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(@Nullable final PDPage instance) throws IOException {
		return instance != null ? instance.getAnnotations() : null;
	}

	@Nullable
	private static PDRectangle getMediaBox(@Nullable final PDPage instance) {
		return instance != null ? instance.getMediaBox() : null;
	}

	@Nullable
	private static Integer getLargestY(@Nullable final BufferedImage bi) {
		//
		Color color = null;
		//
		IntList ily = null;
		//
		for (int y = 0; bi != null && y < bi.getHeight(); y++) {
			//
			for (int x = 0; x < bi.getWidth(); x++) {
				//
				if (color == null) {
					//
					color = new Color(bi.getRGB(x, y));
					//
				} else {
					//
					if (!Objects.equals(color, new Color(bi.getRGB(x, y)))
							&& !Util.contains(ily = ObjectUtils.getIfNull(ily, IntList::create), y)) {
						//
						IntCollectionUtil.addInt(ily, y);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		sortInts(ily);
		//
		return ily != null && !ily.isEmpty() ? ily.getInt(ily.size() - 1) : null;
		//
	}

	private static void setSubject(@Nullable final PDDocumentInformation instance, final String keywords) {
		if (instance != null) {
			instance.setSubject(keywords);
		}
	}

	private static Duration getAudioDuration(@Nullable final File file) throws Exception {
		//
		final AudioFileFormat fileFormat = testAndApply(Objects::nonNull, file, AudioSystem::getAudioFileFormat, null);
		//
		return testAndApply((a, b) -> a != null && b != null, fileFormat, getFormat(fileFormat),
				(a, b) -> Duration.parse(String.format("PT%1$sS", a.getFrameLength() / b.getFrameRate())), null);
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, @Nullable final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static AudioFormat getFormat(@Nullable final AudioFileFormat instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static int groupCount(@Nullable final Matcher instance) {
		return instance != null ? instance.groupCount() : 0;
	}

	private static boolean matches(@Nullable final Matcher instance) {
		return instance != null && instance.matches();
	}

	@Nullable
	private static Matcher matcher(@Nullable final Pattern instance, final CharSequence input) {
		return instance != null ? instance.matcher(input) : null;
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getName(@Nullable final File instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static byte[] pdf(@Nullable final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(toURI(toFile(pathHtml)))), page::navigate);
				//
				return page.pdf();
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Browser launch(@Nullable final BrowserType instance) {
		return instance != null ? instance.launch() : null;
	}

	@Nullable
	private static BrowserContext newContext(@Nullable final Browser instance) {
		return instance != null ? instance.newContext() : null;
	}

	@Nullable
	private static Page newPage(@Nullable final BrowserContext instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static void sortInts(@Nullable final IntList instance) {
		if (instance != null) {
			instance.sortInts();
		}
	}

	@Nullable
	private static String getAbsolutePath(@Nullable final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	@Nullable
	private static File toFile(@Nullable final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		float width = 0;
		for (int i = 0; i < StringUtils.length(text); i++) {
			// Get the width of each character and add it to the total width
			width += font.getWidth(text.charAt(i)) / 1000.0f;
		}
		return width * fontSize;
	}

}