package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ooxml.CustomPropertiesUtil;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentUtil;
import org.apache.poi.ooxml.POIXMLPropertiesUtil;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

@Title("学年別漢字")
public class GaKuNenBeTsuKanJiGui extends JFrame
		implements EnvironmentAware, InitializingBean, KeyListener, ActionListener {

	private static final long serialVersionUID = 3076804232578596080L;

	private static Logger LOG = LoggerFactory.getLogger(GaKuNenBeTsuKanJiGui.class);

	private transient PropertyResolver propertyResolver = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	private JTextComponent tfExportFile = null;

	private transient ComboBoxModel<String> cbmGaKuNenBeTsuKanJi = null;

	@Note("Expport")
	private AbstractButton btnExport = null;

	private AbstractButton btnCompare = null;

	private JLabel jlCompare = null;

	private Unit<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private String gaKuNenBeTsuKanJiListPageUrl = null;

	private ObjectMapper objectMapper = null;

	private GaKuNenBeTsuKanJiGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setGaKuNenBeTsuKanJiMultimap(final Multimap<String, String> gaKuNenBeTsuKanJiMultimap) {
		this.gaKuNenBeTsuKanJiMultimap = Unit.with(gaKuNenBeTsuKanJiMultimap);
	}

	public void setGaKuNenBeTsuKanJiListPageUrl(final String gaKuNenBeTsuKanJiListPageUrl) {
		this.gaKuNenBeTsuKanJiListPageUrl = gaKuNenBeTsuKanJiListPageUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		if (!(getLayout() instanceof MigLayout)) {
			//
			setLayout(new MigLayout());
			//
		} // if
			//
			//
			// If "java.awt.Container.component" is null, return this method immediately
			//
			// The below check is for "-Djava.awt.headless=true"
			//
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		final boolean isGui = f == null || Narcissus.getObjectField(this, f) != null;
		//
		final Predicate<Component> predicate = Predicates.always(isGui, null);
		//
		testAndAccept(predicate, new JLabel("Text"), this::add);
		//
		testAndAccept(predicate, tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.GaKuNenBeTsuKanJiGui.text")), this::add);
		//
		tfText.addKeyListener(this);
		//
		final JComboBox<String> jcbGaKuNenBeTsuKanJi = new JComboBox<>(
				cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
						ArrayUtils.insert(0,
								toArray(MultimapUtil.keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap)),
										new String[] {}),
								(String) null),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>()));
		//
		final String wrap = "wrap";
		//
		final BiPredicate<Component, Object> biPredicate = Predicates.biAlways(isGui, null);
		//
		testAndAccept(biPredicate, jcbGaKuNenBeTsuKanJi, wrap, this::add);
		//
		testAndAccept(predicate, new JLabel(""), this::add);
		//
		testAndAccept(biPredicate, btnExport = new JButton("Export 学年別漢字"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel("Exported File"), this::add);
		//
		testAndAccept(biPredicate, tfExportFile = new JTextField(), String.format("growx,%1$s,span %2$s", wrap, 2),
				this::add);
		//
		testAndAccept(predicate, new JLabel(""), this::add);
		//
		testAndAccept(predicate, btnCompare = new JButton("Compare 学年別漢字"), this::add);
		//
		testAndAccept(predicate, jlCompare = new JLabel(), this::add);
		//
		addActionListener(this, btnExport, btnCompare);
		//
		final Collection<Component> cs = Arrays.asList(tfText, jcbGaKuNenBeTsuKanJi, btnExport);
		//
		final Dimension preferredSize = Util
				.orElse(max(Util.map(Util.stream(cs), Util::getPreferredSize), createDimensionComparator()), null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
	}

	private static Comparator<Dimension> createDimensionComparator() {
		return (a, b) -> a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
	}

	@Nullable
	private static <T> Optional<T> max(@Nullable final Stream<T> instance,
			@Nullable final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || comparator != null)
				? instance.max(comparator)
				: null;
		//
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

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	@Override
	public void keyTyped(final KeyEvent evt) {
		//
	}

	@Override
	public void keyPressed(final KeyEvent evt) {
		//
	}

	@Override
	public void keyReleased(final KeyEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		final JTextComponent jtf = Util.cast(JTextComponent.class, source);
		//
		if (Objects.equals(source, tfText)) {
			//
			final Collection<Entry<String, String>> entries = MultimapUtil
					.entries(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap));
			//
			if (Util.iterator(entries) == null) {
				//
				return;
				//
			} // if
				//
			List<String> list = null;
			//
			String key = null;
			//
			for (final Entry<String, String> en : entries) {
				//
				if (en == null || !StringUtils.equals(Util.getValue(en), Util.getText(jtf))) {
					//
					continue;
					//
				} // if
					//
				if (!Util.contains(list = ObjectUtils.getIfNull(list, ArrayList::new), key = Util.getKey(en))) {
					//
					Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), key);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
			setSelectedItemByIterable(cbmGaKuNenBeTsuKanJi, list);
			//
		} // if
			//
	}

	private static void setSelectedItemByIterable(final ComboBoxModel<?> cbm, @Nullable final Iterable<?> iterable) {
		//
		final int size = IterableUtils.size(iterable);
		//
		if (size == 1) {
			//
			setSelectedItem(cbm, IterableUtils.get(iterable, 0));
			//
		} else if (size < 1) {
			//
			setSelectedItem(cbm, null);
			//
		} else {
			//
			throw new IllegalStateException();
			//
		} // if
			//
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExport)) {
			//
			File file = null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = new FileOutputStream(file = Paths
					.get(String.format("学年別漢字_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())).toFile())) {
				//
				CustomPropertiesUtil.addProperty(
						POIXMLPropertiesUtil
								.getCustomProperties(POIXMLDocumentUtil.getProperties(Util.cast(POIXMLDocument.class,
										workbook = createWorkbook(Pair.of("学年", "漢字"),
												IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap))))),
						"Source", gaKuNenBeTsuKanJiListPageUrl);
				//
				WorkbookUtil.write(workbook, os);
				//
				Util.setText(tfExportFile, Util.getAbsolutePath(file));
				//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(GraphicsEnvironment.isHeadless(), LOG, e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				testAndAccept(f -> and(exists(f), isFile(f), longValue(length(f), 0) == 0), file,
						FileUtils::deleteQuietly);
				//
			} // try
				//
		} else if (Objects.equals(source, btnCompare)) {
			//
			Util.setText(jlCompare, null);
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull,
							Util.getDeclaredMethods(Util
									.forName("org.springframework.beans.factory.GaKuNenBeTsuKanJiMultimapFactoryBean")),
							Arrays::stream, null),
					m -> and(Objects.equals(Util.getName(m), "createMultimapByUrl"),
							Arrays.equals(new Class<?>[] { String.class, Duration.class }, getParameterTypes(m)))));
			//
			final int size = IterableUtils.size(ms);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Method m = size > 0 ? IterableUtils.get(ms, 0) : null;
			//
			try {
				//
				final Multimap<?, ?> multimap = Util.cast(Multimap.class,
						m != null && Modifier.isStatic(m.getModifiers())
								? Narcissus.invokeStaticMethod(m, gaKuNenBeTsuKanJiListPageUrl, null)
								: null);
				//
				final ObjectMapper om = getObjectMapper();
				//
				final boolean matched = Arrays.equals(
						ObjectMapperUtil.writeValueAsBytes(om, MultimapUtil.entries(multimap)),
						ObjectMapperUtil.writeValueAsBytes(om,
								MultimapUtil.entries(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap))));
				//
				Util.setText(jlCompare, iif(matched, "Matched", "Not Matched"));
				//
				Util.setForeground(jlCompare, iif(matched, Color.GREEN, Color.RED));
				//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(GraphicsEnvironment.isHeadless(), LOG, e);
				//
			} catch (final Exception e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
				//
			} // try
				//
		} // if
			//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Nullable
	private static Workbook createWorkbook(final Pair<String, String> columnNames, final Multimap<?, ?> multimap) {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		if (Util.iterator(MultimapUtil.entries(multimap)) != null) {
			//
			for (final Entry<?, ?> en : MultimapUtil.entries(multimap)) {
				//
				if (en == null) {
					//
					continue;
					//
				} // if
					//
				if (sheet == null) {
					//
					sheet = WorkbookUtil.createSheet(workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
					//
				} // if
					//
					// header
					//
				addHeaderRow(sheet, columnNames);
				//
				// content
				//
				if ((row = SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1)) == null) {
					//
					continue;
					//
				} // if
					//
				CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)),
						Util.toString(Util.getKey(en)));
				//
				CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)),
						Util.toString(Util.getValue(en)));
				//
			} // for
				//
		} // if
			//
		if (sheet != null && row != null) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
		return workbook;
		//
	}

	private static void addHeaderRow(@Nullable final Sheet sheet, final Pair<String, String> columnNames) {
		//
		if (sheet != null && sheet.getLastRowNum() < 0) {
			//
			final Row row = SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1);
			//
			if (row == null) {
				//
				return;
				//
			} // if
				//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)), Util.getKey(columnNames));
			//
			CellUtil.setCellValue(RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)),
					Util.getValue(columnNames));
			//
		} // if
			//
	}

	private static boolean exists(@Nullable final File instance) {
		return instance != null && instance.exists();
	}

	private static boolean isFile(@Nullable final File instance) {
		return instance != null && instance.isFile();
	}

	@Nullable
	private static Long length(@Nullable final File instance) {
		return instance != null ? Long.valueOf(instance.length()) : null;
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			@Nullable final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (predicate != null && predicate.test(t, u) && consumer != null) {
			consumer.accept(t, u);
		}
	}

	private static void setSelectedItem(@Nullable final ComboBoxModel<?> instance,
			@Nullable final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static void setPreferredWidth(final int width, @Nullable final Iterable<Component> cs) {
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

}