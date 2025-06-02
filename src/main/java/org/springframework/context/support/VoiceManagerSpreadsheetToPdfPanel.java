package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentUtil;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageUtil;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDRectangleUtil;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageUtil;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.d2ab.function.LanguageCodeToTextObjIntFunction;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.springframework.beans.factory.InitializingBean;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerSpreadsheetToPdfPanel extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -7995853525217556061L;

	private transient ComboBoxModel<Entry<String, Object>> cbmPDRectangle = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnPreview = null;

	private DefaultTableModel tableModel = null;

	private JTextComponent tfFile = null;

	private JLabel lblThumbnail = null;

	private VoiceManagerSpreadsheetToPdfPanel() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this, new MigLayout());// TODO
		//
		if (isGui()) {
			//
			add(new JLabel("Size"));
			//
			final JComboBox<Entry<String, Object>> jcbPDRectangle = new JComboBox<>(
					cbmPDRectangle = new DefaultComboBoxModel<>());
			//
			final ListCellRenderer<?> lcr = jcbPDRectangle.getRenderer();
			//
			jcbPDRectangle.setRenderer(new ListCellRenderer() {

				@Override
				public Component getListCellRendererComponent(final JList list, final Object value, final int index,
						final boolean isSelected, final boolean cellHasFocus) {
					//
					return Util.getListCellRendererComponent((ListCellRenderer) lcr, list,
							Util.getKey(Util.cast(Entry.class, value)), index, isSelected, cellHasFocus);
					//
				}

			});
			//
			add(jcbPDRectangle, String.format("span %1$s", 2));
			//
			final MutableComboBoxModel mcbm = Util.cast(MutableComboBoxModel.class, cbmPDRectangle);
			//
			Util.forEach(
					Util.filter(Util.stream(FieldUtils.getAllFieldsList(PDRectangle.class)),
							x -> Boolean.logicalAnd(Util.isAssignableFrom(PDRectangle.class, Util.getType(x)),
									Util.isStatic(x))),
					x -> Util.addElement(mcbm, Pair.of(Util.getName(x), Narcissus.getStaticField(x))));
			//
			Integer index = null;
			//
			final String size = "A4";// TODO
			//
			for (int i = 0; i < cbmPDRectangle.getSize(); i++) {
				//
				if (!Objects.equals(size, Util.getKey(cbmPDRectangle.getElementAt(i)))) {
					//
					continue;
					//
				} // if
					//
				if (index != null) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				index = Integer.valueOf(i);
				//
			} // for
				//
			setSelectedIndex(jcbPDRectangle, index);
			//
			final String wrap = "wrap";
			//
			add(lblThumbnail = new JLabel(), String.format("%1$s,span 1 %2$s,wmin %3$s,hmin %4$s", wrap, 4, 102, 159));
			//
			// File
			//
			add(new JLabel("File"));
			//
			add(tfFile = new JTextField(), String.format("growx,span %1$s,%2$s", 2, wrap));
			//
			Util.setEditable(tfFile, false);
			//
			//
			// Table
			//
			add(new JLabel());
			//
			final JScrollPane jsp = new JScrollPane(new JTable(tableModel = new DefaultTableModel(
					new Object[] { "text", "voice", "contents", "width", "height", "x", "y" }, 0)));
			//
			add(jsp, String.format("%1$s,span %2$s", wrap, 2));// TODO
			//
			jsp.setPreferredSize(new Dimension((int) getWidth(jsp.getPreferredSize()), 39));
			//
			// Buttons
			//
			add(new JLabel());
			//
			final String top = "top";
			//
			add(btnPreview = new JButton("Preview"), top);
			//
			add(btnExecute = new JButton("Execute"), top);
			//
		} // if
			//
		Util.forEach(Arrays.asList(btnPreview, btnExecute), x -> Util.addActionListener(x, this));
		//
	}

	private boolean isGui() {
		//
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f == null || Narcissus.getField(this, f) != null;
		//
	}

	private static double getWidth(@Nullable final Dimension instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static void setSelectedIndex(@Nullable final JComboBox<?> instance, @Nullable final Number index) {
		if (instance != null && index != null) {
			instance.setSelectedIndex(index.intValue());
		}
	}

	private static void setLayout(@Nullable final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedForBtnExecute();
			//
		} else if (Objects.equals(source, btnPreview)) {
			//
			actionPerformedForBtnPreview();
			//
		} // if
			//
	}

	private void actionPerformedForBtnPreview() {
		//
		Util.setText(tfFile, null);
		//
		setIcon(lblThumbnail, new ImageIcon());
		//
		Util.forEach(IntStream.range(0, Util.getRowCount(tableModel)), i -> Util.removeRow(tableModel, i));
		//
		Entry<Method, Collection<Object>> entry = null;
		//
		try {
			//
			entry = getAllowedFileMagicMethodAndCollection();
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		final Collection<?> collection = Util.getValue(entry);
		//
		File file = getSelectedFile(Util.toFile(Path.of(".")));
		//
		if (!Util.contains(collection, file != null ? Narcissus.invokeStaticMethod(Util.getKey(entry), file) : null)) {
			//
			if (!GraphicsEnvironment.isHeadless()) {// TODO
				//
				JOptionPane.showMessageDialog(null, String.format("Allowed file type %1$s", collection));
				//
			} // if
				//
			return;
			//
		} // if
			//
		Iterable<Data> dataIterable = null;
		//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			final Sheet sheet = testAndApply(x -> WorkbookUtil.getNumberOfSheets(wb) == 1, wb,
					x -> WorkbookUtil.getSheetAt(x, 0), null);
			//
			dataIterable = getDataIterable(
					testAndApply(Objects::nonNull, Util.iterator(sheet), IteratorUtils::toList, null));
			//
			testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)), file,
					x -> Util.setText(tfFile, Util.getAbsolutePath(Util.getAbsoluteFile(x))));
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		try (final PDDocument pdDocument = createPDDocument(file)) {
			//
			final BufferedImage bufferedImage = new PDFRenderer(pdDocument).renderImage(0);
			//
			if (bufferedImage != null) {
				//
				final int width = bufferedImage.getWidth();
				//
				final int height = bufferedImage.getHeight();
				//
				final Dimension preferredSize = isGui() ? getPreferredSize() : null;
				//
				final float ratioMin = Math
						.max(height / (float) (preferredSize != null ? preferredSize.getHeight() : 1), 1);
				//
				setIcon(lblThumbnail,
						new ImageIcon(bufferedImage.getScaledInstance(Math.max((int) (width / ratioMin), 1),
								Math.max((int) (height / ratioMin), 1), Image.SCALE_DEFAULT)));
				//
				revalidate();
				//
			} // if
				//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		if (Util.iterator(dataIterable) != null) {
			//
			for (final Data data : dataIterable) {
				//
				if (data == null) {
					//
					continue;
					//
				} // if
					//
				Util.addRow(tableModel, new Object[] { data.text, data.voice, data.contents, toBigDecimal(data.width),
						toBigDecimal(data.height), toBigDecimal(data.x), toBigDecimal(data.y) });// TODO
				//
			} // for
				//
		} // if
			//
	}

	private static Entry<Method, Collection<Object>> getAllowedFileMagicMethodAndCollection() throws IOException {
		//
		List<Object> list = null;
		//
		Class<?> clz = WorkbookFactory.class;
		//
		IValue0<Method> method = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			// org.apache.poi.ss.usermodel.WorkbookFactory.create(java.io.File,java.lang.String,boolean)
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			List<java.lang.reflect.Method> ms = Util.toList(
					Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(clz), Arrays::stream, null),
							m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "create"),
									Arrays.equals(Util.getParameterTypes(m),
											new Class<?>[] { File.class, String.class, Boolean.TYPE }))));
			//
			if (IterableUtils.size(ms) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final org.apache.bcel.classfile.Method m = testAndApply(Objects::nonNull,
					testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null),
					x -> JavaClassUtil.getMethod(javaClass, x), null);
			//
			final Instruction[] ins = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			final ConstantPoolGen cpg = new ConstantPoolGen(javaClass.getConstantPool());
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			Type[] argumentTypes = null;
			//
			for (int i = 0; ins != null && i < ins.length; i++) {
				//
				if (ins[i] instanceof GETSTATIC gs && i < ins.length - 1 && ins[i + 1] instanceof IF_ACMPNE) {
					//
					if (IterableUtils.size(fs = Util.toList(Util.filter(testAndApply(Objects::nonNull,
							Util.getDeclaredFields(Util.forName(TypeUtil.getClassName(
									gs.getLoadClassType(new ConstantPoolGen(javaClass.getConstantPool()))))),
							Arrays::stream, null), x -> Objects.equals(Util.getName(x), gs.getFieldName(cpg))))) > 1) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					if (Util.isStatic(f = testAndApply(x -> IterableUtils.size(x) == 1, fs,
							x -> IterableUtils.get(x, 0), null))) {
						//
						Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Narcissus.getStaticField(f));
						//
					} // if
						//
				} else if (ins[i] instanceof INVOKESTATIC invokeStatic
						&& (argumentTypes = invokeStatic.getArgumentTypes(cpg)) != null && argumentTypes.length == 1
						&& Objects.equals(TypeUtil.getClassName(argumentTypes[0]), "java.io.File")) {
					//
					if (method != null) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					if (IterableUtils.size(ms = Util.toList(Util.filter(testAndApply(Objects::nonNull,
							Util.getMethods(Util.forName(invokeStatic.getClassName(cpg))), Arrays::stream, null),
							x -> Boolean.logicalAnd(Objects.equals(Util.getName(x), invokeStatic.getMethodName(cpg)),
									Arrays.equals(Util.getParameterTypes(x), new Class<?>[] { File.class }))))) > 1) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					method = Unit.with(
							testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null));
					//
				} // if
					//
			} // for
				//
		} // try
			//
		return Pair.of(IValue0Util.getValue0(method), list);
		//
	}

	private static void setIcon(final JLabel instance, final Icon icon) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), "objectLock")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) != null) {
			//
			instance.setIcon(icon);
			//
		} // if
			//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
		}
	}

	private void actionPerformedForBtnExecute() {
		//
		Entry<Method, Collection<Object>> entry = null;
		//
		try {
			//
			entry = getAllowedFileMagicMethodAndCollection();
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		final Collection<?> collection = Util.getValue(entry);
		//
		File file = Util.toFile(testAndApply(Objects::nonNull, Util.getText(tfFile), Path::of, null));
		//
		if (!Util.contains(collection, file != null ? Narcissus.invokeStaticMethod(Util.getKey(entry), file) : null)) {
			//
			if (!GraphicsEnvironment.isHeadless()) {// TODO
				//
				JOptionPane.showMessageDialog(null, String.format("Allowed file type %1$s", collection));
				//
			} // if
				//
			return;
			//
		} // if
			//
		if (or(file == null, !Util.exists(file), !Util.isFile(file))) {
			//
			file = getSelectedFile(Util.toFile(Path.of(".")));
			//
		} // if
			//
		try (final PDDocument pdDocument = createPDDocument(file)) {
			//
			System.out.println(Util.getAbsolutePath(file = (Util.toFile(Path.of("test.pdf")))));// TODO
			//
			if (!isTestMode()) {
				//
				if (!Util.exists(file)) {
					//
					FileUtils.touch(file);
					//
				} // if
					//
				PDDocumentUtil.save(pdDocument, file);
				//
			} // if
				//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	private PDDocument createPDDocument(@Nullable final File file) {
		//
		Drawing<?> drawingPatriarch = null;
		//
		Iterable<Data> dataList = null;
		//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			final Sheet sheet = testAndApply(x -> WorkbookUtil.getNumberOfSheets(wb) == 1, wb,
					x -> WorkbookUtil.getSheetAt(x, 0), null);
			//
			drawingPatriarch = getDrawingPatriarch(sheet);
			//
			dataList = getDataIterable(
					testAndApply(Objects::nonNull, Util.iterator(sheet), IteratorUtils::toList, null));
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		final PDDocument pdDocument = new PDDocument();
		//
		final PDPage pdPage = new PDPage(ObjectUtils.defaultIfNull(Util.cast(PDRectangle.class,
				testAndApply(Entry.class::isInstance, Util.getSelectedItem(cbmPDRectangle), x -> {
					//
					final Collection<Method> ms = Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Util.getMethods(Util.getClass(x)), Arrays::stream, null),
							y -> Boolean.logicalAnd(Objects.equals(Util.getName(y), "getValue"),
									Arrays.equals(Util.getParameterTypes(y), new Class<?>[] {}))));
					//
					return testAndApply(Objects::nonNull,
							testAndApply(y -> IterableUtils.size(y) == 1, ms, y -> IterableUtils.get(y, 0), null),
							y -> Narcissus.invokeMethod(x, y), null);
					//
				}, null)), PDRectangle.A4));
		//
		pdDocument.addPage(pdPage);
		//
		final PDRectangle mediaBox = PDPageUtil.getMediaBox(pdPage);
		//
		Data data = null;
		//
		float ratioMin = 0;
		//
		try {
			//
			ratioMin = drawImage(drawingPatriarch, pdDocument, pdPage);
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		PDComplexFileSpecification pdComplexFileSpecification = null;
		//
		PDAnnotationFileAttachment pdAnnotationFileAttachment = null;
		//
		final SpeechApi speechApi = new SpeechApiImpl();
		//
		File tempFile = null;
		//
		final int size = 10;// TODO
		//
		ObjIntFunction<String, String> objIntFunction = null;
		//
		for (int i = 0; i < IterableUtils.size(dataList); i++) {
			//
			if ((data = IterableUtils.get(dataList, i)) == null) {
				//
				continue;
				//
			} // if
				//
			(pdComplexFileSpecification = new PDComplexFileSpecification()).setFile(i + ".wav");
			//
			try {
				//
				Util.deleteOnExit(
						tempFile = File.createTempFile(RandomStringUtils.secureStrong().nextAlphabetic(3), null));
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			speechApi.writeVoiceToFile(data.text, getVoice(speechApi,
					objIntFunction = ObjectUtils.getIfNull(objIntFunction, LanguageCodeToTextObjIntFunction::new),
					data.voice)
			//
					, i * -1// TODO
					//
					, 100, null, tempFile);
			//
			try (final InputStream is = Files.newInputStream(Util.toPath(tempFile))) {
				//
				pdComplexFileSpecification.setEmbeddedFile(new PDEmbeddedFile(pdDocument, is));
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			(pdAnnotationFileAttachment = new PDAnnotationFileAttachment()).setFile(pdComplexFileSpecification);
			//
			pdAnnotationFileAttachment
					.setRectangle(new PDRectangle(floatValue(data.x, 0) * ratioMin, PDRectangleUtil.getHeight(mediaBox)
							- (floatValue(data.y, size) + floatValue(data.height, size)) * ratioMin
					//
							, floatValue(data.width, size) * ratioMin
							//
							, floatValue(data.height, size) * ratioMin)
					//
					);
			//
			pdAnnotationFileAttachment.setContents(data.contents);
			//
			try {
				//
				Util.add(PDPageUtil.getAnnotations(pdPage), pdAnnotationFileAttachment);
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
		} // for
			//
		return pdDocument;
		//
	}

	private static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a || b;
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static BigDecimal toBigDecimal(final float f) {
		//
		final Matcher m = Util.matcher(Pattern.compile("^(-?\\d+)\\.0+$"), Float.toString(f));
		//
		if (Util.matches(m) && Util.groupCount(m) > 0) {
			//
			return new BigDecimal(Util.group(m, 1));
			//
		} // if
			//
		return BigDecimal.valueOf(f);
		//
	}

	@Nullable
	private static File createTempFile(final String prefix, @Nullable final String suffix,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			return testAndApply(Objects::nonNull, prefix, x -> File.createTempFile(x, suffix), null);
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
	private static File getSelectedFile(final File folder) {
		//
		final JFileChooser jfc = new JFileChooser(folder);
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

	private static float drawImage(@Nullable final Drawing<?> drawing, final PDDocument pdDocument, final PDPage pdPage)
			throws IOException {
		//
		final List<?> list = testAndApply(Objects::nonNull, Util.iterator(drawing), IteratorUtils::toList, null);
		//
		PictureData pictureData = null;
		//
		final PDRectangle mediaBox = PDPageUtil.getMediaBox(pdPage);
		//
		float pageWidth = PDRectangleUtil.getWidth(mediaBox);
		//
		float imageWidth, imageHeight, pageHeight, ratioMin = 0;
		//
		PDImageXObject pdImageXObject = null;
		//
		for (int i = 0; i < IterableUtils.size(list); i++) {
			//
			if (IterableUtils.get(list, i) instanceof Picture picture
					&& (pictureData = getPictureData(picture)) != null) {
				//
				try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
					//
					imageWidth = PDImageUtil.getWidth(pdImageXObject = PDImageXObject.createFromByteArray(pdDocument,
							pictureData.getData(), null));
					//
					imageHeight = PDImageUtil.getHeight(pdImageXObject);
					//
					pageHeight = imageHeight * (ratioMin = Math.min(imageWidth == 0 ? 0 : pageWidth / imageWidth,
							imageHeight == 0 ? 0 : PDRectangleUtil.getHeight(mediaBox) / imageHeight));
					//
					cs.drawImage(pdImageXObject, 0, (imageHeight - pageHeight) / 2, imageWidth * ratioMin, pageHeight);
					//
				} // try
					//
			} // if
				//
		} // for
			//
		return ratioMin;
		//
	}

	private static class Data {

		@Note("Text")
		private String text = null;

		@Note("Voice")
		private String voice = null;

		private String contents = null;

		@Note("Width")
		private Float width = null;

		@Note("Height")
		private Float height = null;

		@Note("x")
		private Float x = null;

		private Float y = null;

	}

	public static void main(final String[] args) throws Exception {
		//
		final VoiceManagerSpreadsheetToPdfPanel instance = new VoiceManagerSpreadsheetToPdfPanel();
		//
		instance.afterPropertiesSet();
		//
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() ? new JFrame() : null;
		//
		if (jFrame != null) {
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			jFrame.add(instance);
			//
			jFrame.pack();
			//
			jFrame.setVisible(true);
			//
		} // if
			//
	}

	@Nullable
	private static Iterable<Data> getDataIterable(final Iterable<Row> rows) {
		//
		Row row = null;
		//
		Map<Integer, String> map = null;
		//
		Collection<Data> dataList = null;
		//
		Field f = null;
		//
		Data data = null;
		//
		Cell cell = null;
		//
		for (int i = 0; i < IterableUtils.size(rows); i++) {
			//
			if ((row = IterableUtils.get(rows, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (map == null) {
				//
				map = toMap(row);
				//
			} else {
				//
				Util.add(dataList = ObjectUtils.getIfNull(dataList, ArrayList::new), data = new Data());
				//
				for (int j = 0; j < row.getLastCellNum(); j++) {
					//
					if ((f = getFieldByName(FieldUtils.getAllFieldsList(Data.class),
							map.get(Integer.valueOf(j)))) == null || (cell = row.getCell(j)) == null) {
						//
						continue;
						//
					} // if
						//
					setField(data, f, testAndApply(x -> Objects.equals(CellUtil.getCellType(x), CellType.NUMERIC), cell,
							VoiceManagerSpreadsheetToPdfPanel::getNumericCellValue, CellUtil::getStringCellValue));
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return dataList;
		//
	}

	private static void setField(@Nullable final Object instance, @Nullable final Field field, final Object value) {
		//
		if (instance == null || field == null) {
			//
			return;
			//
		} // if
			//
		if (Objects.equals(Util.getType(field), Float.class)) {
			//
			Narcissus.setField(instance, field, value instanceof Number number ? floatValue(number, 0) : value);
			//
		} else {
			//
			Narcissus.setField(instance, field, value);
			//
		} // if
			//
	}

	@Nullable
	private static Map<Integer, String> toMap(@Nullable final Row row) {
		//
		Map<Integer, String> map = null;
		//
		for (int j = 0; row != null && j < row.getLastCellNum(); j++) {
			//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Integer.valueOf(j),
					CellUtil.getStringCellValue(row.getCell(j)));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Double getNumericCellValue(@Nullable final Cell instance) {
		return instance != null ? Double.valueOf(instance.getNumericCellValue()) : null;
	}

	@Nullable
	private static PictureData getPictureData(@Nullable final Picture instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Map<String, String> map = new LinkedHashMap<>(Map.of("org.apache.poi.xssf.streaming.SXSSFPicture",
				"_picture", "org.apache.poi.xssf.usermodel.XSSFPicture", "ctPicture"));
		//
		Util.putAll(map,
				Util.collect(
						Stream.of("org.apache.poi.hssf.usermodel.HSSFPicture",
								"org.apache.poi.hssf.usermodel.HSSFObjectData"),
						Collectors.toMap(Function.identity(), x -> "_optRecord")));
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(Util.entrySet(map)) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(clz);
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return null;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return instance.getPictureData();
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static String getVoice(final SpeechApi speechApi, final ObjIntFunction<String, String> objIntFunction,
			final String voice) {
		//
		IValue0<String> ivalue0 = null;
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(speechApi);
		//
		final int length = voiceIds != null ? voiceIds.length : 0;
		//
		for (int j = 0; j < length; j++) {
			//
			if (!StringUtils.equalsIgnoreCase(ArrayUtils.get(voiceIds, j), voice)) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		if (ivalue0 != null) {
			//
			return IValue0Util.getValue0(ivalue0);
			//
		} // if
			//
		for (int j = 0; j < length; j++) {
			//
			if (!StringUtils.containsIgnoreCase(ArrayUtils.get(voiceIds, j), voice)) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		if (ivalue0 != null) {
			//
			return IValue0Util.getValue0(ivalue0);
			//
		} // if
			//
		String voiceAttribute = null;
		//
		for (int j = 0; j < length; j++) {
			//
			if (!(StringUtils.equalsIgnoreCase(
					voiceAttribute = SpeechApi.getVoiceAttribute(speechApi, ArrayUtils.get(voiceIds, j), "Language"),
					voice) || Objects.equals(ObjIntFunctionUtil.apply(objIntFunction, voiceAttribute, 16), voice))) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		return IValue0Util.getValue0(ivalue0);
		//
	}

	@Nullable
	private static Drawing<?> getDrawingPatriarch(@Nullable final Sheet instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Map<String, String> map = new LinkedHashMap<>(
				Collections.singletonMap("org.apache.poi.hssf.usermodel.HSSFSheet", "_book"));
		//
		Util.putAll(map,
				Util.collect(
						Stream.of("org.apache.poi.xssf.streaming.SXSSFSheet",
								"org.apache.poi.xssf.streaming.DeferredSXSSFSheet"),
						Collectors.toMap(Function.identity(), x -> "_sh")));
		//
		Util.putAll(map,
				Util.collect(Stream.of("org.apache.poi.xssf.usermodel.XSSFDialogsheet",
						"org.apache.poi.xssf.usermodel.XSSFSheet", "org.apache.poi.xssf.usermodel.XSSFChartSheet"),
						Collectors.toMap(Function.identity(), x -> "worksheet")));
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(Util.entrySet(map)) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(Util.getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return null;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance.getDrawingPatriarch();
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static float floatValue(@Nullable final Number instance, final float defaultValue) {
		return instance != null ? instance.floatValue() : defaultValue;
	}

	@Nullable
	private static Field getFieldByName(final Collection<Field> collection, final String name) {
		//
		final List<Field> fs = Util
				.toList(Util.filter(Util.stream(collection), x -> Objects.equals(Util.getName(x), name)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

}