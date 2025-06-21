package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
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
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
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
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CellValueUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.FormulaEvaluatorUtil;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterUtil;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerSpreadsheetToPdfPanel extends JPanel
		implements InitializingBean, ActionListener, MouseListener, Titled, EnvironmentAware, ApplicationContextAware {

	private static final long serialVersionUID = -7995853525217556061L;

	private static final String RASTER = "raster";

	private transient ComboBoxModel<Entry<String, Object>> cbmPDRectangle = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnPreview = null;

	private JScrollPane jsp = null;

	private DefaultTableModel tableModel = null;

	@Note("File")
	private JTextComponent tfFile = null;

	private JTextComponent tfException = null;

	private JLabel lblThumbnail = null;

	private transient BufferedImage bufferedImage = null;

	private transient PropertyResolver propertyResolver = null;

	private transient ApplicationContext applicationContext = null;

	private transient SpeechApi speechApi = null;

	private transient Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter = null;

	private transient ComboBoxModel<Object> cbmVoiceId = null;

	private transient MutableComboBoxModel<String> cbmSheet = null;

	private JComboBox<String> jcbSheet = null;

	@Nullable
	private Double lastImageHeight = null;

	private VoiceManagerSpreadsheetToPdfPanel() {
	}

	@Override
	public String getTitle() {
		return "Spreadsheet to PDF";
	}

	@Override
	public void setEnvironment(final Environment environment) {
		propertyResolver = environment;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	public void setVoiceIdListCellRendererConverter(
			final Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> voiceIdListCellRendererConverter) {
		this.voiceIdListCellRendererConverter = voiceIdListCellRendererConverter;
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
		if (isGui()) {
			//
			// Voice Id
			//
			add(new JLabel("Voice Id"));
			//
			final String[] voiceIds = testAndApply(x -> SpeechApi.isInstalled(x), speechApi,
					x -> SpeechApi.getVoiceIds(x), null);
			//
			final JComboBox<Object> jcbVoiceId = new JComboBox<Object>(cbmVoiceId = testAndApply(Objects::nonNull,
					voiceIds, x -> new DefaultComboBoxModel<>(ArrayUtils.insert(0, x, (String) null)), null));
			//
			add(jcbVoiceId, String.format("span %1$s", 2));
			//
			testAndAccept((a, b) -> a != null && b != null, jcbVoiceId,
					ConverterUtil.convert(voiceIdListCellRendererConverter,
							Util.getRenderer(Util.cast(JComboBox.class, jcbVoiceId))),
					(a, b) -> setRenderer(a, b));
			//
			final String wrap = "wrap";
			//
			add(lblThumbnail = new JLabel(),
					String.format("%1$s,span 1 %2$s,wmin %3$s,hmin %4$s", wrap, 7, 102, 159 + 29));
			//
			lblThumbnail.addMouseListener(this);
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
			add(jcbPDRectangle, String.format("span %1$s,%2$s", 2, wrap));
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
			final String size = testAndApply(PropertyResolverUtil::containsProperty, propertyResolver,
					"org.springframework.context.support.VoiceManagerSpreadsheetToPdfPanel.PDRectangle",
					PropertyResolverUtil::getProperty, (a, b) -> "A4");
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
			// File
			//
			add(new JLabel("File"));
			//
			add(tfFile = new JTextField(), String.format("growx,span %1$s,%2$s", 2, wrap));
			//
			// Sheet
			//
			add(new JLabel("Sheet"));
			//
			add(jcbSheet = new JComboBox<>(cbmSheet = new DefaultComboBoxModel<>(new String[] { null })),
					String.format("growx,span %1$s,%2$s", 2, wrap));
			//
			jcbSheet.addActionListener(this);
			//
			// Table
			//
			add(new JLabel());
			//
			add(jsp = new JScrollPane(new JTable(tableModel = new DefaultTableModel(
					new Object[] { "text", "voice", "contents", "width", "height", "x", "y" }, 0))),
					String.format("%1$s,span %2$s", wrap, 2));// TODO
			//
			setPreferredSize(jsp, new Dimension((int) getWidth(Util.getPreferredSize(jsp)), 39));
			//
			// Buttons
			//
			add(new JLabel());
			//
			add(btnPreview = new JButton("Preview"));
			//
			add(btnExecute = new JButton("Execute"), wrap);
			//
			// Exception
			//
			final String top = "top";
			//
			add(new JLabel("Execption"), top);
			//
			add(tfException = new JTextField(), String.format("growx,span %1$s,%2$s,%3$s", 2, wrap, top));
			//
		} // if
			//
		Util.forEach(Stream.of(tfFile, tfException), x -> Util.setEditable(x, false));
		//
		Util.forEach(Stream.of(btnPreview, btnExecute), x -> Util.addActionListener(x, this));
		//
	}

	private static void setRenderer(final JComboBox<?> jcb, final ListCellRenderer<?> lcr) {
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
			testAndAccept((a, b) -> a != null && b != null, jcb, m, (a, b) -> Narcissus.invokeMethod(a, b, lcr));
			//
		} // for
			//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(predicate, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	private static int length(@Nullable final Object[] instnce) {
		return instnce != null ? instnce.length : 0;
	}

	private static void setPreferredSize(@Nullable final Component instance, final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
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
			fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(acbf), FieldUtils::getAllFieldsList, null)),
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
		} else if (Objects.equals(source, jcbSheet)) {
			//
			actionPerformedJcbSheet();
			//
		} // if
			//
	}

	private void actionPerformedJcbSheet() {
		//
		setIcon(lblThumbnail, new ImageIcon());
		//
		for (int i = Util.getRowCount(tableModel); i >= 0; i--) {
			//
			Util.removeRow(tableModel, i);
			//
		} // for
			//
		final File file = testAndApply(Objects::nonNull, Util.getText(tfFile), x -> Util.toFile(Path.of(x)), null);
		//
		Iterable<Data> dataIterable = null;
		//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			final Sheet sheet = WorkbookUtil.getSheet(wb, Util.toString(Util.getSelectedItem(cbmSheet)));
			//
			dataIterable = getDataIterable(
					testAndApply(Objects::nonNull, Util.iterator(sheet), IteratorUtils::toList, null),
					CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb)));
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
		final int selectedIndex = getSelectedIndex(jcbSheet);
		//
		try (final PDDocument pdDocument = jcbSheet != null && selectedIndex > 0
				? createPDDocument(file, selectedIndex - 1, false)
				: null) {
			//
			if (Boolean.logicalAnd(
					(bufferedImage = (renderImage(
							testAndApply(Objects::nonNull, pdDocument, x -> new PDFRenderer(x), null), 0))) != null,
					IterableUtils.size(dataIterable) > 0)) {
				//
				final int imageHeight = getHeight(bufferedImage);
				//
				if (lastImageHeight == null) {
					//
					lastImageHeight = Double.valueOf(getHeight(testAndGet(isGui(), () -> getPreferredSize(), null), 1));
					//
				} // if
					//
				final float ratioMin = Math.max(imageHeight
						/ (float) (Util.doubleValue(lastImageHeight, 0) != 0 ? Util.doubleValue(lastImageHeight, 0)
								: 1),
						1);
				//
				if (ratioMin != 0) {
					//
					setIcon(lblThumbnail, testAndApply(Objects::nonNull,
							getScaledInstance(bufferedImage, Math.max((int) (getWidth(bufferedImage) / ratioMin), 1),
									Math.max((int) (imageHeight / ratioMin), 1), Image.SCALE_DEFAULT),
							ImageIcon::new, null));
					//
				} // if
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
				Util.addRow(tableModel, toArray(data));
				//
			} // for
				//
		} // if
			//
		setPreferredSize(jsp, new Dimension((int) getWidth(Util.getPreferredSize(jsp)),
				Math.max(IterableUtils.size(dataIterable), 1) * 17 + 22));
		//
	}

	private static BufferedImage renderImage(final PDFRenderer instance, final int pageIndex) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				f -> Objects.equals(Util.getName(f), "pageTree")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field field = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (field != null && Narcissus.getField(instance, field) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.renderImage(pageIndex);
		//
	}

	private static int getSelectedIndex(@Nullable final JComboBox<?> instance) {
		//
		if (instance == null) {
			//
			return -1;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				f -> Objects.equals(Util.getName(f), "dataModel")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field field = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (field != null && Narcissus.getField(instance, field) == null) {
			//
			return -1;
			//
		} // if
			//
		return instance.getSelectedIndex();
		//
	}

	private void actionPerformedForBtnPreview() {
		//
		Util.forEach(Stream.of(tfFile, tfException), x -> Util.setText(x, null));
		//
		setIcon(lblThumbnail, new ImageIcon());
		//
		lastImageHeight = null;
		//
		Util.forEach(Util.map(sorted(Util.map(IntStream.range(1, Util.getSize(cbmSheet)), i -> -i)), i -> -i),
				i -> Util.removeElementAt(cbmSheet, i));
		//
		try {
			//
			FieldUtils.writeDeclaredField(this, "bufferedImage", null, true);
			//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		Util.clear(getDataVector(tableModel));
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
		if (!Util.contains(collection,
				testAndApply((a, b) -> b != null, Util.getKey(entry), file, Narcissus::invokeStaticMethod, null))) {
			//
			testAndRunThrows(file != null,
					() -> Util.setText(tfException, String.format("Allowed file type %1$s", collection)));
			//
			return;
			//
		} // if
			//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			forEachRemaining(Util.iterator(wb), x -> cbmSheet.addElement(SheetUtil.getSheetName(x)));
			//
			testAndAccept(x -> Boolean.logicalAnd(Util.exists(x), Util.isFile(x)), file,
					x -> Util.setText(tfFile, Util.getAbsolutePath(Util.getAbsoluteFile(x))));
			//
			testAndRunThrows(IterableUtils.size(wb) == 1, () -> jcbSheet.setSelectedIndex(1));
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	@Nullable
	private static IntStream sorted(@Nullable final IntStream instance) {
		return instance != null ? instance.sorted() : instance;
	}

	@Nullable
	private static Image getScaledInstance(@Nullable final Image instance, final int width, final int height,
			final int hints) {
		return instance != null ? instance.getScaledInstance(width, height, hints) : instance;
	}

	private static double getHeight(@Nullable final Dimension2D instance, final double defaultValue) {
		return instance != null ? instance.getHeight() : defaultValue;
	}

	private static <E> void forEachRemaining(@Nullable final Iterator<E> instance,
			@Nullable final Consumer<? super E> action) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (Proxy.isProxyClass(Util.getClass(instance)) || action != null) {
			//
			instance.forEachRemaining(action);
			//
		} // if
			//
	}

	@Nullable
	private static Collection<?> getDataVector(@Nullable final DefaultTableModel instance) {
		return instance != null ? instance.getDataVector() : null;
	}

	@Nullable
	private static Object[] toArray(@Nullable final Data data) {
		//
		if (data == null) {
			//
			return null;
			//
		} // if
			//
		return new Object[] { data.text, data.voice, data.contents,
				data.width != null ? toBigDecimal(data.width.floatValue()) : null,
				data.height != null ? toBigDecimal(data.height.floatValue()) : null,
				data.x != null ? toBigDecimal(data.x.floatValue()) : null,
				data.y != null ? toBigDecimal(data.y.floatValue()) : null };
		//
	}

	private static int getWidth(@Nullable final RenderedImage instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), RASTER)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return (f == null || Narcissus.getField(instance, f) != null) && instance != null ? instance.getWidth() : 0;
		//
	}

	private static int getHeight(@Nullable final RenderedImage instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), RASTER)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return (f == null || Narcissus.getField(instance, f) != null) && instance != null ? instance.getHeight() : 0;
		//
	}

	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplierTrue,
			@Nullable final Supplier<T> supplierFalse) {
		return condition ? Util.get(supplierTrue) : Util.get(supplierFalse);
	}

	@Nullable
	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, @Nullable final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
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
			for (int i = 0; i < length(ins); i++) {
				//
				if (ArrayUtils.get(ins, i) instanceof GETSTATIC gs && i < length(ins) - 1
						&& ArrayUtils.get(ins, i + 1) instanceof IF_ACMPNE) {
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
						&& (argumentTypes = invokeStatic.getArgumentTypes(cpg)) != null && length(argumentTypes) == 1
						&& Objects.equals(TypeUtil.getClassName(ArrayUtils.get(argumentTypes, 0)), "java.io.File")) {
					//
					testAndRunThrows(method != null, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					testAndRunThrows(
							IterableUtils.size(ms = Util.toList(Util.filter(testAndApply(Objects::nonNull,
									Util.getMethods(Util.forName(invokeStatic.getClassName(cpg))), Arrays::stream,
									null),
									x -> Boolean.logicalAnd(
											Objects.equals(Util.getName(x), invokeStatic.getMethodName(cpg)),
											Arrays.equals(Util.getParameterTypes(x),
													new Class<?>[] { File.class }))))) > 1,
							() -> {
								//
								throw new IllegalStateException();
								//
							});
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
		Util.setText(tfException, null);
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
		if (or(file == null, !Util.exists(file), !Util.isFile(file))) {
			//
			file = getSelectedFile(Util.toFile(Path.of(".")));
			//
		} // if
			//
		if (!Util.contains(collection,
				testAndApply((a, b) -> b != null, Util.getKey(entry), file, Narcissus::invokeStaticMethod, null))) {
			//
			testAndRunThrows(file != null,
					() -> Util.setText(tfException, String.format("Allowed file type %1$s", collection)));
			//
			return;
			//
		} // if
			//
		try (final PDDocument pdDocument = createPDDocument(file, true)) {
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

	@Nullable
	private PDDocument createPDDocument(@Nullable final File file, final boolean generateAudio) {
		//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			if (WorkbookUtil.getNumberOfSheets(wb) == 1) {
				//
				return createPDDocument(file, 0, generateAudio);
				//
			} // if
				//
			return null;
			//
		} catch (final IOException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
	}

	private PDDocument createPDDocument(@Nullable final File file, final int index, final boolean generateAudio) {
		//
		Drawing<?> drawingPatriarch = null;
		//
		Iterable<Data> dataList = null;
		//
		try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
			//
			final Sheet sheet = WorkbookUtil.getSheetAt(wb, index);
			//
			drawingPatriarch = getDrawingPatriarch(sheet);
			//
			dataList = getDataIterable(
					testAndApply(Objects::nonNull, Util.iterator(sheet), IteratorUtils::toList, null),
					CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb)));
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
		File tempFile = null;
		//
		final int defaultPdAnnotationFileAttachmentRectangleSize = 10;
		//
		final int pdAnnotationFileAttachmentRectangleSize = NumberUtils.toInt(testAndApply(
				PropertyResolverUtil::containsProperty, propertyResolver,
				"org.springframework.context.support.VoiceManagerSpreadsheetToPdfPanel.pdAnnotationFileAttachment.rectangle.size",
				PropertyResolverUtil::getProperty,
				(a, b) -> Integer.toString(defaultPdAnnotationFileAttachmentRectangleSize)),
				defaultPdAnnotationFileAttachmentRectangleSize);
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
			pdComplexFileSpecification = new PDComplexFileSpecification();
			//
			if (generateAudio) {
				//
				pdComplexFileSpecification.setFile(i + ".wav");// TODO
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
				writeVoiceToFile(speechApi, data.text,
						//
						StringUtils.defaultIfBlank(
								getVoice(speechApi,
										objIntFunction = ObjectUtils.getIfNull(objIntFunction,
												LanguageCodeToTextObjIntFunction::new),
										data.voice),
								Util.toString(Util.getSelectedItem(cbmVoiceId)))
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
			} // if
				//
			(pdAnnotationFileAttachment = new PDAnnotationFileAttachment()).setFile(pdComplexFileSpecification);
			//
			pdAnnotationFileAttachment.setRectangle(new PDRectangle(floatValue(data.x, 0) * ratioMin,
					PDRectangleUtil.getHeight(mediaBox) - (floatValue(data.y, pdAnnotationFileAttachmentRectangleSize)
							+ floatValue(data.height, pdAnnotationFileAttachmentRectangleSize)) * ratioMin
					//
					, floatValue(data.width, pdAnnotationFileAttachmentRectangleSize) * ratioMin
					//
					, floatValue(data.height, pdAnnotationFileAttachmentRectangleSize) * ratioMin)
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

	private static void writeVoiceToFile(@Nullable final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, @Nullable final Map<String, Object> map, final File file) {
		if (instance != null) {
			instance.writeVoiceToFile(text, voiceId, rate, volume, map, file);
		}
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

	@Nullable
	private static Iterable<Data> getDataIterable(final Iterable<Row> rows, final FormulaEvaluator formulaEvaluator) {
		//
		Row row = null;
		//
		Map<Integer, String> map = null;
		//
		Collection<Data> dataList = null;
		//
		Data data = null;
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
				map = toMap(row, formulaEvaluator);
				//
			} else if ((data = toData(map, row, formulaEvaluator)) != null) {
				//
				Util.add(dataList = ObjectUtils.getIfNull(dataList, ArrayList::new), data);
				//
			} // if
				//
		} // for
			//
		return dataList;
		//
	}

	@Nullable
	private static Data toData(final Map<?, String> map, @Nullable final Row row,
			final FormulaEvaluator formulaEvaluator) {
		//
		Data data = null;
		//
		Field f = null;
		//
		Cell cell = null;
		//
		Object value = null;
		//
		Double d = null;
		//
		for (int i = 0; row != null && i < row.getLastCellNum(); i++) {
			//
			if ((f = getFieldByName(FieldUtils.getAllFieldsList(Data.class), Util.get(map, Integer.valueOf(i)))) == null
					|| (cell = RowUtil.getCell(row, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Boolean.logicalAnd(
					Objects.equals(Boolean.class, Util.getClass(value = getValue(cell, formulaEvaluator))),
					Objects.equals(Util.getType(f), String.class))) {
				//
				value = Util.toString(value);
				//
			} else if (Boolean.logicalAnd((d = Util.cast(Double.class, value)) != null,
					Objects.equals(Util.getType(f), String.class))) {
				//
				value = Util.toString(toBigDecimal(d.floatValue()));
				//
			} // if
				//
			setField(data = ObjectUtils.getIfNull(data, Data::new), f, value);
			//
		} // for
			//
		return data;
		//
	}

	private static Object getValue(final Cell cell, final FormulaEvaluator formulaEvaluator) {
		//
		CellType cellType = CellUtil.getCellType(cell);
		//
		Object value;
		//
		CellValue cellValue = null;
		//
		if (Objects.equals(cellType, CellType.FORMULA)) {
			//
			if (Objects.equals(
					cellType = CellValueUtil
							.getCellType(cellValue = FormulaEvaluatorUtil.evaluate(formulaEvaluator, cell)),
					CellType.NUMERIC)) {
				//
				value = cellValue.getNumberValue();
				//
			} else if (Objects.equals(cellType, CellType.BOOLEAN)) {
				//
				value = cellValue.getBooleanValue();
				//
			} else {
				//
				value = CellValueUtil.getStringValue(cellValue);
				//
			} // if
				//
		} else if (Objects.equals(cellType, CellType.NUMERIC)) {
			//
			value = getNumericCellValue(cell);
			//
		} else {
			//
			value = CellUtil.getStringCellValue(cell);
			//
		} // if
			//
		return value;
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
	private static Map<Integer, String> toMap(@Nullable final Row row, final FormulaEvaluator formulaEvaluator) {
		//
		Map<Integer, String> map = null;
		//
		Cell cell = null;
		//
		Object value = null;
		//
		for (int i = 0; row != null && i < row.getLastCellNum(); i++) {
			//
			if (Objects.equals(CellUtil.getCellType(cell = RowUtil.getCell(row, i)), CellType.FORMULA)) {
				//
				value = CellValueUtil.getStringValue(FormulaEvaluatorUtil.evaluate(formulaEvaluator, cell));
				//
			} else {
				//
				value = CellUtil.getStringCellValue(cell);
				//
			} // if
				//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Integer.valueOf(i), Util.toString(value));
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
		final int length = length(voiceIds);
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

	@Override
	public void mouseClicked(final MouseEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), lblThumbnail)) {
			//
			final int width = getWidth(bufferedImage);
			//
			final int imageHeight = getHeight(bufferedImage);
			//
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			//
			final boolean gui = !GraphicsEnvironment.isHeadless();
			//
			final double screenHeight = getHeight(toolkit != null && gui ? toolkit.getScreenSize() : null, 0) -
			//
					129// TODO
			;
			//
			testAndRunThrows(gui && !isTestMode(), () -> {
				//
				final float ratioMin = Math.max(imageHeight / (float) (screenHeight != 0 ? screenHeight : 1), 1);
				//
				final List<Field> fs = Util
						.toList(Util.filter(
								Util.stream(testAndApply(Objects::nonNull, Util.getClass(bufferedImage),
										FieldUtils::getAllFieldsList, null)),
								x -> Objects.equals(Util.getName(x), RASTER)));
				//
				testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
				//
				JOptionPane.showMessageDialog(null,
						testAndApply(Objects::nonNull,
								f == null || Narcissus.getField(bufferedImage, f) != null
										? getScaledInstance(bufferedImage, Math.max((int) (width / ratioMin), 1),
												Math.max((int) (imageHeight / ratioMin), 1), Image.SCALE_DEFAULT)
										: null,
								ImageIcon::new, null),
						"Image", JOptionPane.PLAIN_MESSAGE);
				//
			});
			//
		} // if
			//
	}

	@Override
	public void mousePressed(final MouseEvent evt) {
	}

	@Override
	public void mouseReleased(final MouseEvent evt) {
	}

	@Override
	public void mouseEntered(final MouseEvent evt) {
	}

	@Override
	public void mouseExited(final MouseEvent evt) {
	}

}