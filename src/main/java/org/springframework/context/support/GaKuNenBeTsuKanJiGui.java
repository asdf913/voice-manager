package org.springframework.context.support;

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
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ooxml.POIXMLProperties.CustomProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.Multimap;

import net.miginfocom.swing.MigLayout;

public class GaKuNenBeTsuKanJiGui extends JFrame
		implements EnvironmentAware, InitializingBean, KeyListener, ActionListener {

	private static final long serialVersionUID = 3076804232578596080L;

	private static Logger LOG = LoggerFactory.getLogger(GaKuNenBeTsuKanJiGui.class);

	private transient PropertyResolver propertyResolver = null;

	private JTextComponent tfText, tfExportFile = null;

	private ComboBoxModel<String> cbmGaKuNenBeTsuKanJi = null;

	private AbstractButton btnExport = null;

	private Unit<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private String gaKuNenBeTsuKanJiListPageUrl = null;

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
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.GaKuNenBeTsuKanJiGui.text")));
		//
		tfText.addKeyListener(this);
		//
		final JComboBox<String> jcbGaKuNenBeTsuKanJi = new JComboBox<>(
				cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
						ArrayUtils.insert(0,
								toArray(keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap)), new String[] {}),
								(String) null),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>()));
		//
		final String wrap = "wrap";
		//
		add(jcbGaKuNenBeTsuKanJi, wrap);
		//
		add(new JLabel(""));
		//
		add(btnExport = new JButton("Export 学年別漢字"), wrap);
		//
		btnExport.addActionListener(this);
		//
		add(new JLabel("Exported File"));
		//
		add(tfExportFile = new JTextField(), String.format("growx,span %1$s", 2));
		//
		final List<Component> cs = Arrays.asList(tfText, jcbGaKuNenBeTsuKanJi, btnExport);
		//
		final Dimension preferredSize = cs.stream().map(GaKuNenBeTsuKanJiGui::getPreferredSize).max((a, b) -> {
			return a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
		}).orElse(null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
	}

	private static <K> Set<K> keySet(final Multimap<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
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
		final Object source = getSource(evt);
		//
		final JTextComponent jtf = cast(JTextComponent.class, source);
		//
		if (Objects.equals(source, tfText)) {
			//
			final Collection<Entry<String, String>> entries = entries(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap));
			//
			if (iterator(entries) == null) {
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
				if (en == null || !StringUtils.equals(getValue(en), getText(jtf))) {
					//
					continue;
					//
				} // if
					//
				if (!contains(list = ObjectUtils.getIfNull(list, ArrayList::new), key = getKey(en))) {
					//
					add(list = ObjectUtils.getIfNull(list, ArrayList::new), key);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
			final int size = CollectionUtils.size(list);
			//
			if (size == 1) {
				//
				setSelectedItem(cbmGaKuNenBeTsuKanJi, IterableUtils.get(list, 0));
				//
			} else if (size < 1) {
				//
				setSelectedItem(cbmGaKuNenBeTsuKanJi, null);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(getSource(evt), btnExport)) {
			//
			File file = null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = new FileOutputStream(
					file = new File(String.format("学年別漢字_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
				//
				addProperty(
						getCustomProperties(getProperties(cast(POIXMLDocument.class,
								workbook = createWorkbook(Pair.of("学年", "漢字"),
										IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap))))),
						"Source", gaKuNenBeTsuKanJiListPageUrl);
				//
				write(workbook, os);
				//
				setText(tfExportFile, getAbsolutePath(file));
				//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(GraphicsEnvironment.isHeadless(), LOG, e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				testAndAccept(f -> f != null && f.exists() && isFile(f) && longValue(length(f), 0) == 0, file,
						FileUtils::deleteQuietly);
				//
			} // try
				//
			return;
			//
		} // if
			//
	}

	private static Workbook createWorkbook(final Pair<String, String> columnNames, final Multimap<?, ?> multimap) {
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		if (multimap != null && iterator(multimap.entries()) != null) {
			//
			for (final Entry<?, ?> en : multimap.entries()) {
				//
				if (en == null) {
					//
					continue;
					//
				} // if
					//
				if (sheet == null) {
					//
					sheet = createSheet(workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
					//
				} // if
					//
					// header
					//
				addHeaderRow(sheet, columnNames);
				//
				// content
				//
				if ((row = createRow(sheet, sheet.getLastRowNum() + 1)) == null) {
					//
					continue;
					//
				} // if
					//
				setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), toString(getKey(en)));
				//
				setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), toString(getValue(en)));
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

	private static void addHeaderRow(final Sheet sheet, final Pair<String, String> columnNames) {
		//
		if (sheet != null && sheet.getLastRowNum() < 0) {
			//
			final Row row = createRow(sheet, sheet.getLastRowNum() + 1);
			//
			if (row == null) {
				//
				return;
				//
			} // if
				//
			setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), getKey(columnNames));
			//
			setCellValue(createCell(row, Math.max(row.getLastCellNum(), 0)), getValue(columnNames));
			//
		} // if
			//
	}

	private static POIXMLProperties getProperties(final POIXMLDocument instance) {
		return instance != null ? instance.getProperties() : null;
	}

	private static CustomProperties getCustomProperties(final POIXMLProperties instance) {
		return instance != null ? instance.getCustomProperties() : null;
	}

	private static void addProperty(final CustomProperties instance, final String name, final String value) {
		if (instance != null) {
			instance.addProperty(name, value);
		}
	}

	private static Sheet createSheet(final Workbook instance) {
		return instance != null ? instance.createSheet() : null;
	}

	private static Row createRow(final Sheet instance, final int rownum) {
		return instance != null ? instance.createRow(rownum) : null;
	}

	private static Cell createCell(final Row instance, final int column) {
		return instance != null ? instance.createCell(column) : null;
	}

	private static void setCellValue(final Cell instance, final String value) {
		if (instance != null) {
			instance.setCellValue(value);
		}
	}

	private static void write(final Workbook instance, final OutputStream stream) throws IOException {
		//
		if (instance != null && (stream != null || Proxy.isProxyClass(getClass(instance)))) {
			//
			instance.write(stream);
			//
		} // if
			//
	}

	private static String getAbsolutePath(final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	private static boolean isFile(final File instance) {
		return instance != null && instance.isFile();
	}

	private static Long length(final File instance) {
		return instance != null ? Long.valueOf(instance.length()) : null;
	}

	private static long longValue(final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static <K, V> Collection<Entry<K, V>> entries(final Multimap<K, V> instance) {
		return instance != null ? instance.entries() : null;
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) {
		//
		if (iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = getPreferredSize(c)) == null) {
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

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

}