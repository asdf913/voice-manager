package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstructionUtil;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageUtil;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDFontUtil;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
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
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerImageToPdfPanel extends JPanel
		implements InitializingBean, ActionListener, Titled, EnvironmentAware, ApplicationContextAware, ItemListener {

	private static final long serialVersionUID = 7360299976827392995L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerImageToPdfPanel.class);

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private transient SpeechApi speechApi = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	@Note("Image URL")
	private JTextComponent tfImageUrl = null;

	@Note("Image URL State Code")
	private JTextComponent tfImageUrlStateCode = null;

	@Note("Image File")
	private JTextComponent tfImageFile = null;

	@Note("Font Size")
	private JTextComponent tfFontSize = null;

	private JTextComponent tfOutputFile = null;

	@Note("Speech Language Code")
	private JTextComponent tfSpeechLanguageCode = null;

	@Note("Speech Language Name")
	private JTextComponent tfSpeechLanguageName = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Image File")
	private AbstractButton btnImageFile = null;

	private AbstractButton btnCopyOutputFilePath = null;

	private transient ComboBoxModel<String> cbmVoiceId = null;

	private JComboBox<Object> jcbVoiceId = null;

	private transient PropertyResolver propertyResolver = null;

	private transient ApplicationContext applicationContext = null;

	private transient ObjIntFunction<String, String> languageCodeToTextObjIntFunction = null;

	private transient ComboBoxModel<FontName> cbmFontName = null;

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static class VoiceIdListCellRenderer implements ListCellRenderer<Object> {

		private SpeechApi speechApi = null;

		private ListCellRenderer<Object> listCellRenderer = null;

		private String commonPrefix = null;

		private VoiceIdListCellRenderer(final SpeechApi speechApi) {
			this.speechApi = speechApi;
		}

		@Override
		@Nullable
		public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value,
				final int index, final boolean isSelected, final boolean cellHasFocus) {
			//
			final String s = Util.toString(value);
			//
			try {
				//
				final String name = SpeechApi.getVoiceAttribute(speechApi, s, "Name");
				//
				if (StringUtils.isNotBlank(name)) {
					//
					return Util.getListCellRendererComponent(listCellRenderer, list, name, index, isSelected,
							cellHasFocus);
					//
				} // if
					//
			} catch (final Error e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			return Util.getListCellRendererComponent(listCellRenderer, list,
					StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
					index, isSelected, cellHasFocus);
			//
		}

	}

	@Override
	public String getTitle() {
		return "Image To PDF";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		propertyResolver = environment;
	}

	public void setLanguageCodeToTextObjIntFunction(
			final ObjIntFunction<String, String> languageCodeToTextObjIntFunction) {
		this.languageCodeToTextObjIntFunction = languageCodeToTextObjIntFunction;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		// Font
		//
		add(new JLabel("Font"));
		//
		add(tfFontSize = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManagerImageToPdfPanel.fontSize")),
				String.format("wmin %1$s", 30));
		//
		final FontName[] fontNames = FontName.values();
		//
		add(new JComboBox<>(cbmFontName = new DefaultComboBoxModel<>(ArrayUtils.insert(0, fontNames, (FontName) null))),
				String.format("span %1$s,%2$s", 3, WRAP));
		//
		final Integer index = getIndex(cbmFontName,
				getFontName("org.springframework.context.support.VoiceManagerImageToPdfPanel.fontName",
						propertyResolver, System.getProperties()));
		//
		if (index != null) {
			//
			Util.setSelectedItem(cbmFontName, Util.getElementAt(cbmFontName, index.intValue()));
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), String.format("%1$s,span %2$s,%3$s", GROWX, 4, WRAP));
		//
		add(new JLabel("Voice"));
		//
		final String[] voiceIds = testAndApply(x -> SpeechApi.isInstalled(x),
				speechApi = ObjectUtils.getIfNull(speechApi, SpeechApiImpl::new), x -> SpeechApi.getVoiceIds(x), null);
		//
		if ((cbmVoiceId = testAndApply(Objects::nonNull, voiceIds,
				x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null)) != null) {
			//
			final VoiceIdListCellRenderer voiceIdListCellRenderer = new VoiceIdListCellRenderer(speechApi);
			//
			voiceIdListCellRenderer.listCellRenderer = Util.getRenderer(Util.cast(JComboBox.class,
					jcbVoiceId = new JComboBox<>(Util.cast(ComboBoxModel.class, cbmVoiceId))));
			//
			voiceIdListCellRenderer.commonPrefix = String.join("",
					StringUtils.substringBeforeLast(StringUtils.getCommonPrefix(voiceIds), "\\"), "\\");
			//
			jcbVoiceId.setRenderer(voiceIdListCellRenderer);
			//
			jcbVoiceId.addItemListener(this);
			//
			add(jcbVoiceId, String.format("span %1$s", 2));
			//
			testAndAccept(PropertyResolverUtil::containsProperty, propertyResolver,
					"org.springframework.context.support.VoiceManagerImageToPdfPanel.voiceId", (a, b) -> {
						//
						final String s = PropertyResolverUtil.getProperty(a, b);
						//
						Object element = null;
						//
						for (int i = 0; i < Util.getSize(cbmVoiceId); i++) {
							//
							if (s != null && Util.contains(
									Arrays.asList(element = Util.getElementAt(cbmVoiceId, i),
											SpeechApi.getVoiceAttribute(speechApi, Util.toString(element), "Name")),
									s)) {
								//
								Util.setSelectedItem(cbmVoiceId, element);
								//
							} // if
								//
						} // for
							//
					});
			//
		} // if
			//
		add(tfSpeechLanguageCode = new JTextField(), String.format("width %1$s", 30));
		//
		add(tfSpeechLanguageName = new JTextField(), String.format("%1$s,width %2$s,span %3$s", WRAP, 230, 2));
		//
		JPanel panel = new JPanel();
		//
		setLayout(panel,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Image"));
		//
		panel.add(new JLabel("URL"));
		//
		final int width = 356;
		//
		panel.add(tfImageUrl = new JTextField(), String.format("wmin %1$s,wmax %1$s", width - 34));
		//
		panel.add(tfImageUrlStateCode = new JTextField(), String.format("wmin %1$s,%2$s", 27, WRAP));
		//
		panel.add(new JLabel("File"));
		//
		panel.add(tfImageFile = new JTextField(), String.format("wmin %1$s,wmax %1$s,span %2$s", width, 2));
		//
		panel.add(btnImageFile = new JButton("Select"));
		//
		add(panel, String.format("%1$s,%2$s,span %3$s", WRAP, GROWX, 5));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), String.format("span %1$s,%2$s", 2, WRAP));
		//
		add(new JLabel("Output"));
		//
		setLayout(panel = new JPanel(),
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		if (panel.getLayout() instanceof MigLayout migLayout) {
			//
			migLayout.setLayoutConstraints("insets 0 0 0 0");
			//
		} // if
			//
		panel.add(tfOutputFile = new JTextField(), String.format("wmin %1$s", 378));
		//
		panel.add(btnCopyOutputFilePath = new JButton("Copy"));
		//
		add(panel, String.format("%1$s,span %2$s", GROWX, 4));
		//
		Util.forEach(Stream.of(btnExecute, btnImageFile, btnCopyOutputFilePath), x -> Util.addActionListener(x, this));
		//
		Util.setEnabled(btnExecute, Util.getSelectedItem(cbmVoiceId) != null);
		//
		Util.forEach(
				Stream.of(tfSpeechLanguageCode, tfSpeechLanguageName, tfImageFile, tfImageUrlStateCode, tfOutputFile),
				x -> Util.setEditable(x, false));
		//
	}

	@Nullable
	private static Integer getIndex(final ListModel<?> instance, @Nullable final Object object) {
		//
		Integer index = null;
		//
		for (int i = 0; i < Util.getSize(instance); i++) {
			//
			if (!Objects.equals(Util.getElementAt(instance, i), object)) {
				//
				continue;
				//
			} // if
				//
			if (index != null) {
				//
				throw new IllegalStateException();
				//
			} else {
				//
				index = Integer.valueOf(i);
				//
			} // if
				//
		} // for
			//
		return index;
		//
	}

	@Nullable
	private static FontName getFontName(final String key, final PropertyResolver propertyResolver,
			final Map<?, ?> map) {
		//
		final FontName[] fontNames = FontName.values();
		//
		IValue0<FontName> iValue0 = getFontName(fontNames, testAndApply(PropertyResolverUtil::containsProperty,
				propertyResolver, key, PropertyResolverUtil::getProperty, null));
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		if ((iValue0 = getFontName(fontNames,
				Util.toString(testAndApply(Util::containsKey, map, key, Util::get, null)))) != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<FontName> getFontName(final FontName[] fontNames, final String prefix) {
		//
		FontName fontName = null;
		//
		IValue0<FontName> iValue0 = null;
		//
		final int length = fontNames != null ? fontNames.length : 0;
		//
		for (int i = 0; i < length; i++) {
			//
			if ((fontName = ArrayUtils.get(fontNames, i)) == null
					|| (!StringUtils.equalsIgnoreCase(fontName.getName(), prefix)
							&& !StringUtils.equalsIgnoreCase(Util.name(fontName), prefix))) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(iValue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			iValue0 = Unit.with(fontName);
			//
		} // for
			//
		if (iValue0 == null) {
			//
			for (int i = 0; i < length; i++) {
				//
				if ((fontName = ArrayUtils.get(fontNames, i)) == null
						|| (!StringUtils.startsWithIgnoreCase(fontName.getName(), prefix)
								&& !StringUtils.startsWithIgnoreCase(Util.name(fontName), prefix))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(iValue0 != null, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				iValue0 = Unit.with(fontName);
				//
			} // for
				//
		} // if
			//
		return iValue0;
		//
	}

	private static void setLayout(@Nullable final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
	}

	@Nullable
	private static LayoutManager getLayoutManager(final AutowireCapableBeanFactory acbf,
			final Iterable<Entry<String, Object>> entrySet) throws Exception {
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<LayoutManager> iValue0 = null;
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
			fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(acbf))),
					x -> Objects.equals(Util.getName(x), "singletonObjects")));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if (FactoryBeanUtil
						.getObject(
								Util.cast(
										FactoryBean.class, MapUtils
												.getObject(
														Util.cast(Map.class,
																Narcissus.getObjectField(acbf,
																		IterableUtils.get(fs, i))),
														Util.getKey(entry)))) instanceof LayoutManager lm) {
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
		return IValue0Util.getValue0(iValue0);
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> map = null;

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof ObjectMap) {
				//
				if (Objects.equals(name, "getObject") && args != null && args.length > 0) {
					//
					final Object arg = args[0];
					//
					if (!Util.containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), arg)) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					return Util.get(map, arg);
					//
				} else if (Objects.equals(name, "setObject") && args != null && args.length > 1) {
					//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			try (final PDDocument pdDocument = new PDDocument()) {
				//
				Util.setText(tfOutputFile, null);
				//
				final PDRectangle pdRectangle = PDRectangle.A4;
				//
				final PDPage pdPage = new PDPage(pdRectangle);
				//
				pdDocument.addPage(pdPage);
				//
				PDComplexFileSpecification pdComplexFileSpecification = null;
				//
				PDAnnotationFileAttachment pdAnnotationFileAttachment = null;
				//
				final PDRectangle mediaBox = PDPageUtil.getMediaBox(pdPage);
				//
				float pageWidth = getWidth(mediaBox);
				//
				float pageHeight = getHeight(mediaBox);
				//
				final float size = pageWidth / 10;
				//
				File tempFile = null;
				//
				try {
					//
					testAndAccept(Objects::nonNull,
							tempFile = File.createTempFile(nextAlphabetic(RandomStringUtils.secureStrong(), 3), null),
							VoiceManagerImageToPdfPanel::deleteOnExit);
					//
				} catch (final IOException e) {
					//
					LoggerUtil.error(LOG, getMessage(e), e);
					//
				} // if
					//
				final String text = Util.getText(tfText);
				//
				final Object voiceId = Util.getSelectedItem(cbmVoiceId);
				//
				if (voiceId == null) {
					//
					testAndRunThrows(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
							() -> JOptionPane.showMessageDialog(null, "Please select a voice"));
					//
					return;
					//
				} // if
					//
				for (int i = 0; i < 10; i++) {
					//
					(pdComplexFileSpecification = new PDComplexFileSpecification()).setFile(i + ".wav");
					//
					if (text != null) {// TODO
						//
						writeVoiceToFile(speechApi = ObjectUtils.getIfNull(speechApi, SpeechApiImpl::new), text,
								Util.toString(voiceId)
								//
								, i * -1// TODO
								//
								, 100, null, tempFile);
						//
					} // if
						//
					pdComplexFileSpecification.setEmbeddedFile(createPDEmbeddedFile(pdDocument, Util.toPath(tempFile),
							e -> LoggerUtil.error(LOG, getMessage(e), e)));
					//
					(pdAnnotationFileAttachment = new PDAnnotationFileAttachment()).setFile(pdComplexFileSpecification);
					//
					pdAnnotationFileAttachment.setRectangle(new PDRectangle(i * size, pageHeight - size, size, size));
					//
					pdAnnotationFileAttachment.setContents("test " + i);// TODO
					//
					Util.add(getAnnotations(pdPage, e -> LoggerUtil.error(LOG, getMessage(e), e)),
							pdAnnotationFileAttachment);
					//
				} // for
					//
				testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)), tempFile,
						FileUtils::deleteQuietly);
				//
				final boolean isTestMode = isTestMode();
				//
				try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
					//
					final PDFont font = new PDType1Font(ObjectUtils.defaultIfNull(
							Util.cast(FontName.class, Util.getSelectedItem(cbmFontName)), FontName.HELVETICA));
					//
					final float fontSize = NumberUtils.toFloat(Util.getText(tfFontSize), 14);
					//
					addText(cs, font, fontSize, pdPage, size);
					//
					final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
					//
					ObjectMap.setObject(objectMap, PDDocument.class, pdDocument);
					//
					ObjectMap.setObject(objectMap, PDRectangle.class, pdRectangle);
					//
					ObjectMap.setObject(objectMap, PDPageContentStream.class, cs);
					//
					ObjectMap.setObject(objectMap, URL.class,
							testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x),
									Util.getText(tfImageUrl), URL::new, null));
					//
					ObjectMap.setObject(objectMap, File.class,
							testAndApply(Objects::nonNull, Util.getText(tfImageFile), File::new, null));
					//
					ObjectMap.setObject(objectMap, VoiceManagerImageToPdfPanel.class, this);
					//
					addImage(objectMap, pageWidth, size, getTextHeight(font, fontSize, size));
					//
				} catch (final IOException | NoSuchMethodException e) {
					//
					LoggerUtil.error(LOG, getMessage(e), e);
					//
				} // try
					//
				final File file = Util.toFile(
						Path.of(StringUtils.join(StringUtils.defaultIfBlank(Util.getText(tfText), "temp"), ".pdf")));
				//
				testAndRunThrows(!isTestMode,
						() -> save(pdDocument, file, e -> LoggerUtil.error(LOG, getMessage(e), e)));
				//
				Util.setText(tfOutputFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException ioe) {
				//
				LoggerUtil.error(LOG, getMessage(ioe), ioe);
				//
			} // try
				//
		} else if (Objects.equals(source, btnImageFile)) {
			//
			testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.exists(x)), getFile(Util.toFile(Path.of("."))),
					x -> Util.setText(tfImageFile, Util.getAbsolutePath(Util.getAbsoluteFile(x))));
			//
		} else if (Objects.equals(source, btnCopyOutputFilePath)) {
			//
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			//
			if (!isTestMode()) {
				//
				setContents(toolkit != null && !GraphicsEnvironment.isHeadless() ? toolkit.getSystemClipboard() : null,
						new StringSelection(Util.getText(tfOutputFile)), null);
				//
			} // if
				//
		} // if
			//
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	@Nullable
	private static File getFile(final File file) {
		//
		final JFileChooser jfc = new JFileChooser(file);
		//
		if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode())
				&& jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			//
			return jfc.getSelectedFile();
			//
		} // if
			//
		return null;
		//
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), jcbVoiceId)) {
			//
			final Object voiceId = Util.getSelectedItem(cbmVoiceId);
			//
			Util.setEnabled(btnExecute, voiceId != null);
			//
			try {
				//
				final String language = SpeechApi.getVoiceAttribute(speechApi, Util.toString(voiceId), "Language");
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

	private static int getTextHeight(final PDFont font, final float fontSize, final float size) throws IOException {
		//
		final PDDocument pdDocument = new PDDocument();
		//
		final PDRectangle pdRectangle = PDRectangle.A4;
		//
		final PDPage pdPage = new PDPage(pdRectangle);
		//
		pdDocument.addPage(pdPage);
		//
		try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
			//
			addText(cs, font, fontSize, pdPage, size);
			//
		} // try
			//
		final IntIntPair intIntPair = getMinimumAndMaximumY(new PDFRenderer(pdDocument).renderImage(0));
		//
		return intIntPair != null ? intIntPair.rightInt() - intIntPair.leftInt() + 1 : 0;
		//
	}

	@Nullable
	private static IntIntPair getMinimumAndMaximumY(@Nullable final BufferedImage bi) {
		//
		Color c = null;
		//
		IntIntMutablePair intIntPair = null;
		//
		for (int x = 0; bi != null && x < bi.getWidth(); x++) {
			//
			for (int y = 0; y < bi.getHeight(); y++) {
				//
				if (c == null) {
					//
					c = new Color(bi.getRGB(x, y));
					//
				} else if (!Objects.equals(c, new Color(bi.getRGB(x, y)))) {
					//
					if (intIntPair == null) {
						//
						intIntPair = IntIntMutablePair.of(y, y);
						//
					} else {
						//
						intIntPair.left(Math.min(intIntPair.leftInt(), y));
						//
						intIntPair.right(Math.max(intIntPair.rightInt(), y));
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return intIntPair;
		//
	}

	private static void addText(@Nullable final PDPageContentStream cs, @Nullable final PDFont font,
			final float fontSize, final PDPage pdPage, final float size) throws IOException {
		//
		PDFontDescriptor pdFontDescriptor = null;
		//
		String value = null;
		//
		Field f = null;
		//
		for (int i = 0; cs != null && i < 10; i++) {
			//
			cs.beginText();
			//
			testAndAccept(Objects::nonNull, font, x -> cs.setFont(x, fontSize));
			//
			cs.newLineAtOffset(i * size + (size - getTextWidth(
					//
					value = Integer.toString(100 - i * 10) + "%"
					//
					, font, fontSize)) / 2, (getHeight(PDPageUtil.getMediaBox(pdPage)) - size
			//
							- (getAscent(pdFontDescriptor = PDFontUtil.getFontDescriptor(font), 0) / 1000 * fontSize)
							+ (getDescent(pdFontDescriptor, 0) / 1000 * fontSize))
			//
			);
			//
			if (f == null) {
				//
				final Collection<Field> fs = Util
						.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(cs))),
								x -> Objects.equals(Util.getName(x), "fontStack")));
				//
				testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
				//
			} // if
				//
			if (f == null || !IterableUtils.isEmpty(Util.cast(Iterable.class, Narcissus.getField(cs, f)))) {
				//
				cs.showText(value);
				//
			} // if
				//
			cs.endText();
			//
		} // for
			//
	}

	private static interface ObjectMap {

		@Nullable
		<T> T getObject(final Class<?> key);

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

	private static void addImage(final ObjectMap objectMap, final float pageWidth, final float size,
			final int textHeight) throws IOException, NoSuchMethodException {
		//
		final PDDocument pdDocument = ObjectMap.getObject(objectMap, PDDocument.class);
		//
		final PDRectangle pdRectangle = ObjectMap.getObject(objectMap, PDRectangle.class);
		//
		final PDPageContentStream cs = ObjectMap.getObject(objectMap, PDPageContentStream.class);
		//
		final URLConnection urlConnection = Util.openConnection(ObjectMap.getObject(objectMap, URL.class));
		//
		final VoiceManagerImageToPdfPanel voiceManagerImageToPdfPanel = ObjectMap.getObject(objectMap,
				VoiceManagerImageToPdfPanel.class);
		//
		final JTextComponent tfImageUrlStateCode = voiceManagerImageToPdfPanel != null
				? voiceManagerImageToPdfPanel.tfImageUrlStateCode
				: null;
		//
		Util.setText(tfImageUrlStateCode, null);
		//
		Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(cs), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "resources")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Object object = testAndApply((a, b) -> a != null, cs,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
				Narcissus::getField, null);
		//
		testAndRunThrows(IterableUtils.size(fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(object), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "resources")))) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		final COSDictionary cosDictionary = Util.cast(COSDictionary.class,
				testAndApply((a, b) -> a != null, object, f, Narcissus::getField, null));
		//
		final int cosDictionarySize = size(cosDictionary);
		//
		try (final InputStream is = Util.getInputStream(urlConnection)) {
			//
			final byte[] bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
			if (Objects.equals(Boolean.TRUE, isPDImage(bs))) {
				//
				addPDImageXObject(PDImageXObject.createFromByteArray(pdDocument, bs, null), pdRectangle, cs, pageWidth,
						size, textHeight);
				//
			} // if
				//
		} finally {
			//
			if (urlConnection instanceof HttpURLConnection httpURLConnection) {
				//
				Util.setText(tfImageUrlStateCode, Integer.toString(httpURLConnection.getResponseCode()));
				//
			} // if
				//
		} // try
			//
		if (size(cosDictionary) != cosDictionarySize) {
			//
			return;
			//
		} // if
			//
		File file = ObjectMap.getObject(objectMap, File.class);
		//
		if (file == null || !Util.exists(file) || !Util.isFile(file)) {
			//
			file = getFile(Util.toFile(Path.of(".")));
			//
		} // if
			//
		if (Util.exists(file) && Util.isFile(file)) {
			//
			if (Objects.equals(Boolean.FALSE, isPDImage(Files.readAllBytes(Util.toPath(file))))) {
				//
				JOptionPane.showMessageDialog(null, "Please select an image file");
				//
				return;
				//
			} // if
				//
			addPDImageXObject(PDImageXObject.createFromFileByContent(file, pdDocument), pdRectangle, cs, pageWidth,
					size, textHeight);
			//
		} // if
			//
	}

	private static int size(@Nullable final COSDictionary instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "items")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return 0;
			//
		} // if
			//
		return instance.size();
		//
	}

	private static void addPDImageXObject(final PDImageXObject pdImageXObject, final PDRectangle pdRectangle,
			@Nullable final PDPageContentStream cs, final float pageWidth, final float size, final float textHeight)
			throws IOException {
		//
		final float imageWidth = getWidth(pdImageXObject);
		//
		final float imageHeight = getHeight(pdImageXObject);
		//
		final float ratioMin = Math.min(imageWidth == 0 ? 0 : pageWidth / imageWidth,
				imageHeight == 0 ? 0 : getHeight(pdRectangle) / imageHeight);
		//
		final float pageHeight = imageHeight * ratioMin;
		//
		if (cs != null) {
			//
			cs.drawImage(pdImageXObject, 0, (imageHeight - pageHeight) / 2, imageWidth * ratioMin,
					pageHeight - size - textHeight);
			//
		} // if
			//
	}

	@Nullable
	private static PDEmbeddedFile createPDEmbeddedFile(final PDDocument pdDocument, final Path path,
			final Consumer<IOException> consumer) {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, path, Files::newInputStream, null)) {
			//
			return testAndApply((a, b) -> a != null && b != null, pdDocument, is, PDEmbeddedFile::new, null);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return Util.test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static void save(@Nullable final PDDocument instance, final File file,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			if (instance != null) {
				//
				instance.save(file);
				//
			} // if
				//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
	}

	private static void writeVoiceToFile(@Nullable final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, @Nullable final Map<String, Object> map, @Nullable final File file) {
		if (instance != null) {
			instance.writeVoiceToFile(text, voiceId, rate, volume, map, file);
		}
	}

	@Nullable
	private static String getMessage(@Nullable final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(@Nullable final PDPage instance,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			return PDPageUtil.getAnnotations(instance);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Boolean isPDImage(@Nullable final byte[] bs) throws NoSuchMethodException, IOException {
		//
		if (bs == null || bs.length == 0) {
			//
			return Boolean.FALSE;
			//
		} // if
			//
		final Entry<Method, Collection<Object>> entry = getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes();
		//
		final Method method = Util.getKey(entry);
		//
		if (Arrays.equals(Util.getParameterTypes(method), new Class<?>[] { BufferedInputStream.class })) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final BufferedInputStream bis = new BufferedInputStream(is)) {
				//
				return Util.isStatic(method)
						? Boolean
								.valueOf(Util.contains(Util.getValue(entry), Narcissus.invokeStaticMethod(method, bis)))
						: null;
				//
			} // try
				//
		} // if
			//
		return null;
		//
	}

	private static int getWidth(@Nullable final PDImage instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static int getHeight(@Nullable final PDImage instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	private static void deleteOnExit(@Nullable final File instance) {
		if (instance != null) {
			instance.deleteOnExit();
		}
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static float getWidth(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String nextAlphabetic(@Nullable final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphabetic(count) : null;
	}

	private static Entry<Method, Collection<Object>> getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes()
			throws IOException, NoSuchMethodException {
		//
		Class<?> clz = PDImageXObject.class;
		//
		Collection<Object> allowedFileType = null;
		//
		MutablePair<Method, Collection<Object>> entry = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			// org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromFileByContent(java.io.File,org.apache.pdfbox.pdmodel.PDDocument)
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					Util.getDeclaredMethod(clz, "createFromFileByContent", File.class, PDDocument.class));
			//
			final Instruction[] ins = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			final ConstantPool cp = FieldOrMethodUtil.getConstantPool(m);
			//
			final ConstantPoolGen cpg = new ConstantPoolGen(cp);
			//
			Instruction in = null;
			//
			INVOKESTATIC invokestatic = null;
			//
			Object object = null;
			//
			Type[] argumentTypes = null;
			//
			List<Field> fs = null;
			//
			int size = 0;
			//
			for (int i = 0; i < length(ins); i++) {
				//
				if ((in = ArrayUtils.get(ins, i)) instanceof INVOKESTATIC temp && invokestatic == null) {
					//
					if (length(
							(argumentTypes = InvokeInstructionUtil.getArgumentTypes(invokestatic = temp, cpg))) == 1) {
						//
						MutablePairUtil.setLeft(entry = ObjectUtils.getIfNull(entry, MutablePair::new),
								Util.getDeclaredMethod(
										clz = Util.forName(InvokeInstructionUtil.getClassName(invokestatic, cpg)),
										InvokeInstructionUtil.getMethodName(invokestatic, cpg),
										Util.forName(TypeUtil.getClassName(ArrayUtils.get(argumentTypes, 0)))));
						//
					} // if
						//
				} else if (i > 0 && ArrayUtils.get(ins, i - 1) instanceof GETSTATIC getstatic
						&& Boolean.logicalOr(in instanceof IF_ACMPNE, in instanceof IF_ACMPEQ)) {
					//
					if ((size = IterableUtils.size(fs = Util.toList(Util.filter(
							Arrays.stream(Util.getDeclaredFields(
									Util.forName(TypeUtil.getClassName(getstatic.getFieldType(cpg))))),
							f -> Objects.equals(Util.getName(f),
									FieldInstructionUtil.getFieldName(getstatic, cpg)))))) > 1) {
						//
						throw new IllegalStateException();
						//
					} else if (size == 1
							&& !Util.contains(allowedFileType = ObjectUtils.getIfNull(allowedFileType, ArrayList::new),
									object = Narcissus.getStaticField(IterableUtils.get(fs, 0)))) {
						//
						Util.add(allowedFileType, object);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // try
			//
		Util.setValue(entry = ObjectUtils.getIfNull(entry, MutablePair::new), allowedFileType);
		//
		return entry;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static float getAscent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getAscent() : defaultValue;
	}

	private static float getDescent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getDescent() : defaultValue;
	}

	private static float getTextWidth(final String text, @Nullable final PDFont font, final float fontSize)
			throws IOException {
		float width = 0;
		for (int i = 0; i < StringUtils.length(text); i++) {
			// Get the width of each character and add it to the total width
			width += getWidth(font, text.charAt(i), 0) / 1000.0f;
		}
		return width * fontSize;
	}

	private static float getWidth(@Nullable final PDFont instance, final int code, final float defaultValue)
			throws IOException {
		//
		if (instance == null) {
			//
			return defaultValue;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance);
		//
		final String name = Util.getName(clz);
		//
		List<Field> fs = null;
		//
		Field f = null;
		//
		if (Util.contains(Arrays.asList("org.apache.pdfbox.pdmodel.font.PDMMType1Font",
				"org.apache.pdfbox.pdmodel.font.PDTrueTypeFont", "org.apache.pdfbox.pdmodel.font.PDType1CFont",
				"org.apache.pdfbox.pdmodel.font.PDType1Font"), name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "codeToWidthMap")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} else if (Objects.equals("org.apache.pdfbox.pdmodel.font.PDType0Font", name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "descendantFont")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} else if (Objects.equals("org.apache.pdfbox.pdmodel.font.PDType3Font", name)) {
			//
			testAndRunThrows(
					IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
							x -> Objects.equals(Util.getName(x), "dict")))) > 1,
					() -> {
						//
						throw new IllegalStateException();
						//
					});
			//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
					&& Narcissus.getField(instance, f) == null) {
				//
				return defaultValue;
				//
			} // if
				//
		} // if
			//
		return instance.getWidth(code);
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

}