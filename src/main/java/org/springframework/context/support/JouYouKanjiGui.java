package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ooxml.CustomPropertiesUtil;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentUtil;
import org.apache.poi.ooxml.POIXMLPropertiesUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.reflect.Reflection;
import com.helger.commons.version.IHasVersion;
import com.helger.commons.version.Version;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.reader.CSSReaderDeclarationList;

import net.miginfocom.swing.MigLayout;

public class JouYouKanjiGui extends JFrame implements EnvironmentAware, InitializingBean, KeyListener, ActionListener {

	private static final long serialVersionUID = 3076804232578596080L;

	private transient PropertyResolver propertyResolver = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	private JTextComponent tfExportFile = null;

	private transient ComboBoxModel<Boolean> cbmJouYouKanJi = null;

	private AbstractButton btnExportJouYouKanJi = null;

	private transient IValue0<List<String>> jouYouKanJiList = null;

	private String url = null;

	private ECSSVersion ecssVersion = null;

	private JouYouKanjiGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setJouYouKanJiList(final List<String> jouYouKanJiList) {
		this.jouYouKanJiList = Unit.with(jouYouKanJiList);
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setEcssVersion(final Object object) throws NoSuchFieldException, IllegalAccessException {
		//
		if (object == null) {
			//
			this.ecssVersion = null;
			//
		} else if (object instanceof ECSSVersion ev) {
			//
			this.ecssVersion = ev;
			//
		} else if (object instanceof String string) {
			//
			final List<ECSSVersion> list = toList(
					filter(testAndApply(Objects::nonNull, ECSSVersion.values(), Arrays::stream, null),
							x -> StringUtils.contains(name(x), string)));
			//
			final int size = IterableUtils.size(list);
			//
			if (size > 1) {
				//
				throw new IllegalArgumentException();
				//
			} else if (size == 1) {
				//
				this.ecssVersion = IterableUtils.get(list, 0);
				//
			} // if
				//
		} else if (object instanceof Number number) {
			//
			final IValue0<ECSSVersion> iValue0 = getECSSVersionByMajor(ECSSVersion.values(), number);
			//
			if (iValue0 != null) {
				//
				this.ecssVersion = IValue0Util.getValue0(iValue0);
				//
			} // if
				//
		} // if
			//
	}

	private static IValue0<ECSSVersion> getECSSVersionByMajor(final ECSSVersion[] evs, final Number number) {
		//
		IValue0<ECSSVersion> iValue0 = null;
		//
		if (evs != null) {
			//
			Version v = null;
			//
			for (final ECSSVersion ev : evs) {
				//
				if ((v = getVersion(ev)) == null || number.intValue() != v.getMajor()) {
					//
					continue;
					//
				} // if
					//
				if (iValue0 == null) {
					//
					iValue0 = Unit.with(ev);
					//
				} else {
					//
					throw new IllegalArgumentException();
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return iValue0;
		//
	}

	private static Version getVersion(final IHasVersion instance) {
		return instance != null ? instance.getVersion() : null;
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
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
				"org.springframework.context.support.JouYouKanjiGui.text")), "wmin 100px");
		//
		tfText.addKeyListener(this);
		//
		add(new JLabel("常用漢字"));
		//
		final JComboBox<?> jcbJouYouKanji = new JComboBox<>(
				cbmJouYouKanJi = new DefaultComboBoxModel<>(toArray(getBooleanValues(), new Boolean[] {})));
		//
		final String wrap = "wrap";
		//
		add(jcbJouYouKanji, wrap);
		//
		add(new JLabel());
		//
		add(btnExportJouYouKanJi = new JButton("Export 常用漢字"), String.format("%1$s,span %2$s", wrap, 3));
		//
		btnExportJouYouKanJi.addActionListener(this);
		//
		add(new JLabel("File"));
		//
		add(tfExportFile = new JTextField(), String.format("growx,span %1$s", 3));
		//
		final List<Component> cs = Arrays.asList(tfText, jcbJouYouKanji);
		//
		final Dimension preferredSize = cs.stream().map(JouYouKanjiGui::getPreferredSize).max((a, b) -> {
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

	private static List<Boolean> getBooleanValues() throws IllegalAccessException {
		//
		List<Boolean> list = null;
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(Boolean.class), Arrays::stream, null),
						f -> Objects.equals(getType(f), Boolean.class)));
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			if (!Objects.equals(Boolean.class, getType(f = IterableUtils.get(fs, i)))) {
				//
				continue;
				//
			} // if
				//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), cast(Boolean.class, f.get(null)));
			//
		} // for
			//
		return list;
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static Class<?> getType(final Field instance) {
		return instance != null ? instance.getType() : null;
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(getSource(evt), btnExportJouYouKanJi)) {
			//
			File file = null;
			//
			Workbook workbook = null;
			//
			try (final OutputStream os = new FileOutputStream(
					file = new File(String.format("常用漢字_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
				//
				CustomPropertiesUtil.addProperty(
						POIXMLPropertiesUtil
								.getCustomProperties(POIXMLDocumentUtil.getProperties(cast(POIXMLDocument.class,
										workbook = createJouYouKanJiWorkbook(url, Duration.ZERO, ecssVersion)))),
						"Source", url);
				//
				WorkbookUtil.write(workbook, os);
				//
				setText(tfExportFile, getAbsolutePath(file));
				//
				pack();
				//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} finally {
				//
				IOUtils.closeQuietly(workbook);
				//
				final IntStream intStream = mapToInt(testAndApply(Objects::nonNull, spliterator(workbook),
						x -> StreamSupport.stream(x, false), null), x -> intValue(getPhysicalNumberOfRows(x), 0));
				//
				final Integer totalPhysicalNumberOfRows = intStream != null ? Integer.valueOf(intStream.sum()) : null;
				//
				testAndAccept(x -> intValue(totalPhysicalNumberOfRows, 0) == 0, file, FileUtils::deleteQuietly);
				//
			} // try
				//
		} // if
			//
	}

	private static interface ObjectMap {

		<T> T getObject(final Class<T> key);

		<T> void setObject(final Class<T> key, final T value);

		static <T> T getObject(final ObjectMap instance, final Class<T> key) {
			return instance != null ? instance.getObject(key) : null;
		}

		static <T> void setObject(final ObjectMap instance, final Class<T> key, final T value) {
			if (instance != null) {
				instance.setObject(key, value);
			}
		}

	}

	private static class IH implements InvocationHandler {

		private static final String KEY_NOT_FOUND_MESSAGE = "Key [%1$s] Not Found";

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
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ObjectMap) {
				//
				if (Objects.equals(methodName, "getObject") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!containsKey(getObjects(), key)) {
						//
						throw new IllegalStateException(String.format(KEY_NOT_FOUND_MESSAGE,
								testAndApply(IH::isArray, cast(Class.class, key), IH::getSimpleName, x -> key)));
						//
					} // if
						//
					return MapUtils.getObject(getObjects(), key);
					//
				} else if (Objects.equals(methodName, "setObject") && args != null && args.length > 1) {
					//
					put(getObjects(), args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean containsKey(final Map<?, ?> instance, final Object key) {
			return instance != null && instance.containsKey(key);
		}

		private static boolean isArray(final OfField<?> instance) {
			return instance != null && instance.isArray();
		}

		private static String getSimpleName(final Class<?> instance) {
			return instance != null ? instance.getSimpleName() : null;
		}

		private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
			if (instance != null) {
				instance.put(key, value);
			}
		}

	}

	private static Workbook createJouYouKanJiWorkbook(final String url, final Duration timeout,
			final ECSSVersion ecssVersion) {
		//
		Workbook workbook = null;
		//
		try {
			//
			final Document document = testAndApply(Objects::nonNull,
					testAndApply(StringUtils::isNotBlank, url, URL::new, null),
					x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null);
			//
			final ObjectMap objectMap = Reflection.newProxy(ObjectMap.class, new IH());
			//
			ObjectMap.setObject(objectMap, Workbook.class,
					workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new));
			//
			ObjectMap.setObject(objectMap, ECSSVersion.class, ecssVersion);
			//
			final String xPathFormat = StringUtils.prependIfMissing(StringUtils.joinWith("/", "h3",
					"span[text()=\"%1$s\"]", "..", "following-sibling::table[1]", "tbody"), "//");
			//
			// h3
			//
			final List<Element> h3s = ElementUtil.select(document, "h3 .mw-headline");
			//
			String sheetName = null;
			//
			for (int i = 0; h3s != null && i < h3s.size(); i++) {
				//
				ObjectMap.setObject(objectMap, Elements.class,
						//
						testAndApply(x -> IterableUtils.size(x) == 1,
								ElementUtil.selectXpath(document,
										String.format(xPathFormat, sheetName = ElementUtil.text(h3s.get(i)))),
								x -> ElementUtil.children(IterableUtils.get(x, 0)), null)
				//
				);
				//
				addJouYouKanJiSheet(objectMap, sheetName);
				//
			} // for
				//
		} catch (final IOException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		return workbook;
		//
	}

	private static void addJouYouKanJiSheet(final ObjectMap objectMap, final String sheetName) {
		//
		final Workbook workbook = ObjectMap.getObject(objectMap, Workbook.class);
		//
		final IndexedColorMap indexedColorMap = getIndexedColors(getStylesSource(cast(XSSFWorkbook.class, workbook)));
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		Elements tds = null;
		//
		String textContent = null;
		//
		Matcher matcher = null;
		//
		Pattern pattern2 = null;
		//
		Element domNode = null;
		//
		String backGroundColorString = null;
		//
		Cell cell = null;
		//
		CellStyle cellStyle = null;
		//
		final Elements elements = ObjectMap.getObject(objectMap, Elements.class);
		//
		final ECSSVersion eccsEcssVersion = ObjectUtils.defaultIfNull(ObjectMap.getObject(objectMap, ECSSVersion.class),
				ECSSVersion.CSS30);
		//
		for (int i = 0; i < IterableUtils.size(elements); i++) {
			//
			if ((sheet = ObjectUtils.getIfNull(sheet, () -> WorkbookUtil.createSheet(workbook, sheetName))) != null
					&& (row = SheetUtil.createRow(sheet, sheet.getLastRowNum() + 1)) == null) {
				//
				continue;
				//
			} // if
				//
			tds = ElementUtil.children(domNode = IterableUtils.get(elements, i));
			//
			backGroundColorString = getExpressionAsCSSString(
					getCSSDeclarationByAttributeAndCssProperty(domNode, "style", eccsEcssVersion, "background-color"));
			//
			for (int j = 0; j < IterableUtils.size(tds); j++) {
				//
				if ((matcher = matcher(pattern2 = ObjectUtils.getIfNull(pattern2, () -> Pattern.compile("\\[\\d+]")),
						textContent = ElementUtil.text(IterableUtils.get(tds, j)))) != null) {
					//
					textContent = matcher.replaceAll("");
					//
				} // if
					//
				CellUtil.setCellValue(cell = RowUtil.createCell(row, Math.max(row.getLastCellNum(), 0)),
						StringUtils.trim(textContent));
				//
				// background-color
				//
				if (StringUtils.isNotBlank(backGroundColorString)) {
					//
					setFillBackgroundColor(cellStyle = WorkbookUtil.createCellStyle(workbook),
							new XSSFColor(
									new Color(Integer.parseInt(StringUtils.substring(backGroundColorString, 1), 16)),
									indexedColorMap));
					//
					setFillPattern(cellStyle, FillPatternType.SOLID_FOREGROUND);
					//
					CellUtil.setCellStyle(cell, cellStyle);
					//
				} // if
					//
			} // for
				//
		} // for
			//
		if (sheet != null && row != null && sheet.getFirstRowNum() < sheet.getLastRowNum()
				&& row.getFirstCellNum() >= 0) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		return pattern != null && input != null ? pattern.matcher(input) : null;
	}

	private static String getExpressionAsCSSString(final CSSDeclaration instance) {
		return instance != null ? instance.getExpressionAsCSSString() : null;
	}

	private static IndexedColorMap getIndexedColors(final StylesTable instance) {
		return instance != null ? instance.getIndexedColors() : null;
	}

	private static StylesTable getStylesSource(final XSSFWorkbook instance) {
		return instance != null ? instance.getStylesSource() : null;
	}

	private static CSSDeclaration getCSSDeclarationByAttributeAndCssProperty(final Element element,
			final String attribute, final ECSSVersion ecssVersion, final String cssProperty) {
		//
		final String style = ElementUtil.attr(element, attribute);
		//
		CSSDeclaration cssDeclaration = null;
		//
		if (StringUtils.isNotBlank(style)) {
			//
			final List<CSSDeclaration> cssDeclarations = toList(filter(
					stream(CSSReaderDeclarationList.readFromString(style,
							ObjectUtils.defaultIfNull(ecssVersion, ECSSVersion.CSS30))),
					x -> Objects.equals(getProperty(x), cssProperty)));
			//
			final int size = IterableUtils.size(cssDeclarations);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			cssDeclaration = size == 1 ? IterableUtils.get(cssDeclarations, 0) : null;
			//
		} // if
			//
		return cssDeclaration;
		//
	}

	private static String getProperty(final CSSDeclaration instance) {
		return instance != null ? instance.getProperty() : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static Long toMillis(final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static void setFillBackgroundColor(final CellStyle instance,
			final org.apache.poi.ss.usermodel.Color color) {
		if (instance != null) {
			instance.setFillBackgroundColor(color);
		}
	}

	private static void setFillPattern(final CellStyle instance, final FillPatternType fillPatternType) {
		if (instance != null) {
			instance.setFillPattern(fillPatternType);
		}
	}

	private static String getAbsolutePath(final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	private static <T> Spliterator<T> spliterator(final Iterable<T> instance) {
		return instance != null ? instance.spliterator() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static Integer getPhysicalNumberOfRows(final Sheet instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfRows()) : null;
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
		if (Objects.equals(source, tfText)) {
			//
			// 常用漢字
			//
			final String text = getText(tfText);
			//
			final List<String> list = IValue0Util.getValue0(jouYouKanJiList);
			//
			if (StringUtils.isEmpty(text) || CollectionUtils.isEmpty(list)) {
				//
				setSelectedItem(cbmJouYouKanJi, null);
				//
			} else if (jouYouKanJiList != null) {
				//
				setSelectedItem(cbmJouYouKanJi,
						StringUtils.length(text) <= orElse(max(mapToInt(stream(list), StringUtils::length)), 0)
								? contains(list, text)
								: null);
				//
			} // if
				//
		} // if
			//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	private static OptionalInt max(final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	private static int orElse(final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
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

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

}