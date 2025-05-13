package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerImageToPdfPanel extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = 7360299976827392995L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerImageToPdfPanel.class);

	private transient SpeechApi speechApi = null;

	private JTextComponent tfText = null;

	private AbstractButton btnExecute = null;

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() && !isTestMode() ? new JFrame() : null;
		//
		final VoiceManagerImageToPdfPanel instance = new VoiceManagerImageToPdfPanel();
		//
		instance.afterPropertiesSet();
		//
		if (jFrame != null) {
			//
			jFrame.add(instance);
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			jFrame.pack();
			//
			jFrame.setVisible(true);
			//
		} // if
			//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), "growx,wrap");
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
			final PDDocument pdDocument = new PDDocument();
			//
			final PDRectangle pdRectangle = PDRectangle.A4;
			//
			final PDPage pdPage = new PDPage(pdRectangle);
			//
			pdDocument.addPage(pdPage);
			//
			PDEmbeddedFile pdEmbeddedFile = null;
			//
			PDComplexFileSpecification pdComplexFileSpecification = null;
			//
			PDAnnotationFileAttachment pdAnnotationFileAttachment = null;
			//
			final PDRectangle mediaBox = pdPage.getMediaBox();
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
			for (int i = 0; i < 10; i++) {
				//
				(pdComplexFileSpecification = new PDComplexFileSpecification()).setFile(i + ".wav");
				//
				final String text = Util.getText(tfText);
				//
				if (text != null) {// TODO
					//
					writeVoiceToFile(speechApi = ObjectUtils.getIfNull(speechApi, SpeechApiImpl::new), text,
							"TTS_MS_JA-JP_HARUKA_11.0"// TODO
							, i * -1// TODO
							, 100, null, tempFile);
					//
				} // if
					//
				try (final InputStream is = Files.newInputStream(Util.toPath(tempFile))) {
					//
					pdEmbeddedFile = new PDEmbeddedFile(pdDocument, is);
					//
				} catch (final IOException e) {
					//
					LoggerUtil.error(LOG, getMessage(e), e);
					//
				} // try
					//
				pdComplexFileSpecification.setEmbeddedFile(pdEmbeddedFile);
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
			testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)), tempFile, FileUtils::deleteQuietly);
			//
			final boolean isTestMode = isTestMode();
			//
			try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
				//
				final PDFont font = new PDType1Font(FontName.HELVETICA);// TODO
				//
				final float fontSize = 14;// TODO
				//
				String value = null;
				//
				PDFontDescriptor pdFontDescriptor = null;
				//
				for (int i = 0; i < 10; i++) {
					//
					cs.beginText();
					//
					cs.setFont(font, fontSize);
					//
					cs.newLineAtOffset(i * size + (size - getTextWidth(
							//
							value = Integer.toString(100 - i * 10) + "%"
							//
							, font, fontSize)) / 2, (getHeight(pdPage.getMediaBox()) - size
					//
									- (getAscent(pdFontDescriptor = getFontDescriptor(font), 0) / 1000 * fontSize)
									+ (getDescent(pdFontDescriptor, 0) / 1000 * fontSize))
					//
					);
					//
					cs.showText(value);
					//
					cs.endText();
					//
				} // for
					//
				final JFileChooser jfc = new JFileChooser(Util.toFile(Path.of(".")));
				//
				File file = null;
				//
				if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode)
						&& jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
						&& Util.exists(file = jfc.getSelectedFile()) && Util.isFile(file)) {// TODO
					//
					if (Objects.equals(Boolean.FALSE, isPDImage(Files.readAllBytes(Util.toPath(file))))) {
						//
						JOptionPane.showMessageDialog(null, "Please select an image file");
						//
						return;
						//
					} // if
						//
					final PDImageXObject pdImageXObject = PDImageXObject.createFromFileByContent(file, pdDocument);
					//
					final float imageWidth = getWidth(pdImageXObject);
					//
					final float imageHeight = getHeight(pdImageXObject);
					//
					final float ratioMin = Math.min(pageWidth / (imageWidth),
							((getHeight(pdRectangle)) / (imageHeight)));
					//
					cs.drawImage(pdImageXObject, 0, ((imageHeight) - (pageHeight = (imageHeight) * ratioMin)) / 2,
							(imageWidth) * ratioMin, pageHeight - size -
							//
									10// TODO
										//
					);
					//
				} // if
					//
			} catch (final IOException | NoSuchMethodException e) {
				//
				LoggerUtil.error(LOG, getMessage(e), e);
				//
			} // try
				//
			try {
				//
				testAndRunThrows(!isTestMode, () ->
				//
				pdDocument.save(Util.toFile(Path.of("temp.pdf")))// TODO
				//
				);
				//
			} catch (IOException e) {
				//
				LoggerUtil.error(LOG, getMessage(e), e);
				//
			} // try
				//
		} // if
			//
	}

	private static void writeVoiceToFile(@Nullable final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, @Nullable final Map<String, Object> map, final File file) {
		if (instance != null) {
			instance.writeVoiceToFile(text, voiceId, rate, volume, map, file);
		}
	}

	private static String getMessage(@Nullable final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(@Nullable final PDPage instance,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			return instance != null ? instance.getAnnotations() : null;
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

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
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
		setValue(entry = ObjectUtils.getIfNull(entry, MutablePair::new), allowedFileType);
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

	private static <V> void setValue(@Nullable final Entry<?, V> instance, @Nullable final V value) {
		if (instance != null) {
			instance.setValue(value);
		}
	}

	private static float getAscent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getAscent() : defaultValue;
	}

	private static float getDescent(@Nullable final PDFontDescriptor instance, final float defaultValue) {
		return instance != null ? instance.getDescent() : defaultValue;
	}

	@Nullable
	private static PDFontDescriptor getFontDescriptor(@Nullable final PDFont instance) {
		return instance != null ? instance.getFontDescriptor() : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
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