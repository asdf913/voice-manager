package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CellValueUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceContentInfoUtil;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;

import com.j256.simplemagic.ContentInfo;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

import domain.JlptVocabulary;

public class JlptVocabularyListFactoryBean implements FactoryBean<List<JlptVocabulary>> {

	private List<String> urls = null;

	private Resource resource = null;

	public void setUrls(final List<String> us) {
		//
		this.urls = testAndApply(x -> x != null && x.toArray() != null, us, ArrayList::new, null);
		//
		for (int i = 0; urls != null && i < urls.size(); i++) {
			//
			urls.set(i, StringUtils.trim(urls.get(i)));
			//
		} // for
			//
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public List<JlptVocabulary> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final ContentInfo ci = ResourceContentInfoUtil.getContentInfo(resource);
			//
			final String mimeType = getMimeType(ci);
			//
			if (or(Objects.equals("application/vnd.openxmlformats-officedocument", mimeType),
					Boolean.logicalAnd(Objects.equals("application/zip", mimeType), XlsxUtil.isXlsx(resource)),
					Objects.equals("OLE 2 Compound Document", getMessage(ci)))) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
					//
					final IValue0<List<JlptVocabulary>> list = getJlptVocabularies(
							wb != null && wb.getNumberOfSheets() == 1 ? WorkbookUtil.getSheetAt(wb, 0) : null,
							CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb)));
					//
					if (list != null) {
						//
						return IValue0Util.getValue0(list);
						//
					} // if
						//
				} // try
					//
			} // if
				//
		} // if
			//
		return getObjectByUrls(urls);
		//
	}

	@Nullable
	private static IValue0<List<JlptVocabulary>> getJlptVocabularies(@Nullable final Sheet sheet,
			final FormulaEvaluator formulaEvaluator) throws IllegalAccessException {
		//
		IValue0<List<JlptVocabulary>> list = null;
		//
		if (sheet != null && sheet.iterator() != null) {
			//
			final AtomicBoolean first = new AtomicBoolean(true);
			//
			Map<Integer, Field> fieldMap = null;
			//
			IValue0<JlptVocabulary> jv = null;
			//
			for (final Row row : sheet) {
				//
				if (row == null) {
					//
					continue;
					//
				} // if
					//
				if (first.getAndSet(false)) {
					//
					fieldMap = getFieldMap(row);
					//
					continue;
					//
				} // if
					//
				if ((jv = getJlptVocabulary(fieldMap, row, formulaEvaluator)) != null) {
					//
					add(IValue0Util.getValue0(list = ObjectUtils.getIfNull(list, () -> Unit.with(new ArrayList<>()))),
							IValue0Util.getValue0(jv));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	@Nullable
	private static Map<Integer, Field> getFieldMap(final Row row) {
		//
		int size = 0;
		//
		List<Field> fs = null;
		//
		Entry<Integer, Field> entry = null;
		//
		Map<Integer, Field> fieldMap = null;
		//
		String stringCellValue = null;
		//
		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
			//
			entry = null;
			//
			// name
			//
			if ((size = IterableUtils.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(),
					stringCellValue = CellUtil.getStringCellValue(RowUtil.getCell(row, i))))) == 1) {
				//
				entry = Pair.of(Integer.valueOf(i), IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
				// domain.JlptVocabulary$ColumnName
				//
			if (entry == null && (size = IterableUtils.size(
					fs = getFieldsByColumnName(JlptVocabulary.class.getDeclaredFields(), stringCellValue))) == 1) {
				//
				entry = Pair.of(Integer.valueOf(i), IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			if (entry != null) {
				//
				Util.put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new), entry.getKey(),
						entry.getValue());
				//
			} // if
				//
		} // for
			//
		return fieldMap;
		//
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static Class<? extends Annotation> annotationType(@Nullable final Annotation instance) {
		return instance != null ? instance.annotationType() : null;
	}

	@Nullable
	private static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static IValue0<JlptVocabulary> getJlptVocabulary(@Nullable final Map<Integer, Field> fieldMap,
			final Row row, final FormulaEvaluator formulaEvaluator) throws IllegalAccessException {
		//
		IValue0<JlptVocabulary> ivalue0 = null;
		//
		Field f = null;
		//
		JlptVocabulary jv = null;
		//
		Cell cell = null;
		//
		IValue0<?> value = null;
		//
		Class<?> type = null;
		//
		CellType cellType = null;
		//
		final Integer physicalNumberOfCells = getPhysicalNumberOfCells(row);
		//
		for (int i = 0; ObjectUtils.compare(i, physicalNumberOfCells) < 0; i++) {
			//
			if (or((f = MapUtils.getObject(fieldMap, Integer.valueOf(i))) == null,
					(cell = RowUtil.getCell(row, i)) == null,
					Objects.equals(CellType.BLANK, cellType = CellUtil.getCellType(cell)))) {
				//
				continue;
				//
			} // if
				//
			setAccessible(f, true);
			//
			jv = ObjectUtils.getIfNull(
					IValue0Util
							.getValue0(ivalue0 = ObjectUtils.getIfNull(ivalue0, () -> Unit.with(new JlptVocabulary()))),
					JlptVocabulary::new);
			//
			if (Objects.equals(type = getType(f), Integer.class)) {
				//
				if ((value = getIntegerValue(cell, formulaEvaluator)) != null) {
					//
					set(f, jv, IValue0Util.getValue0(value));
					//
				} else {
					//
					throw new UnsupportedOperationException(String.format("type=%1$s,cellType=%2$s", type, cellType));
					//
				} // if
					//
			} else if (isAssignableFrom(CharSequence.class, type)) {
				//
				if ((value = getStringValue(cell, formulaEvaluator)) != null) {
					//
					set(f, jv, IValue0Util.getValue0(value));
					//
				} else {
					//
					throw new UnsupportedOperationException(String.format("type=%1$s,cellType=%2$s", type, cellType));
					//
				} // if
					//
			} else {
				//
				throw new UnsupportedOperationException();
				//
			} // if
				//
		} // for
			//
		return ivalue0;
		//
	}

	@Nullable
	private static Integer getPhysicalNumberOfCells(@Nullable final Row instance) {
		return instance != null ? Integer.valueOf(instance.getPhysicalNumberOfCells()) : null;
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static void set(@Nullable final Field field, final Object instance, final Object value)
			throws IllegalAccessException {
		if (field != null) {
			field.set(instance, value);
		}
	}

	@Nullable
	private static Class<?> getType(@Nullable final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	private static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	@Nullable
	private static IValue0<Integer> getIntegerValue(final Cell cell, final FormulaEvaluator formulaEvaluator) {
		//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		IValue0<Integer> result = null;
		//
		if (Objects.equals(cellType, CellType.STRING)) {
			//
			result = Unit.with(
					testAndApply(StringUtils::isNotBlank, CellUtil.getStringCellValue(cell), Integer::valueOf, null));
			//
		} else if (Objects.equals(cellType, CellType.NUMERIC)) {
			//
			result = Unit.with(testAndApply(Objects::nonNull, Double.valueOf(cell.getNumericCellValue()),
					x -> x != null ? x.intValue() : null, null));
			//
		} else if (Objects.equals(cellType, CellType.FORMULA)) {
			//
			final CellValue cellValue = evaluate(formulaEvaluator, cell);
			//
			final CellType cellValueType = CellValueUtil.getCellType(cellValue);
			//
			if (Objects.equals(cellValueType, CellType.NUMERIC)) {
				//
				final Double d = getNumberValue(cellValue);
				//
				result = Unit.with(d != null ? Integer.valueOf(d.intValue()) : null);
				//
			} else if (Objects.equals(cellValueType, CellType.STRING)) {
				//
				result = Unit.with(testAndApply(StringUtils::isNotBlank, CellValueUtil.getStringValue(cellValue),
						Integer::valueOf, null));
				//
			} // if
				//
			if (result == null) {
				//
				throw new UnsupportedOperationException(
						String.format("cellType=%1$s,cellValueType=%2$s", cellType, cellValueType));
				//
			} // if
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static IValue0<String> getStringValue(final Cell cell, final FormulaEvaluator formulaEvaluator) {
		//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		IValue0<String> result = null;
		//
		if (Objects.equals(cellType, CellType.STRING)) {
			//
			result = Unit.with(CellUtil.getStringCellValue(cell));
			//
		} else if (Objects.equals(cellType, CellType.NUMERIC)) {
			//
			result = Unit.with(Util.toString(Double.valueOf(cell.getNumericCellValue())));
			//
		} else if (Objects.equals(cellType, CellType.FORMULA)) {
			//
			final CellValue cellValue = evaluate(formulaEvaluator, cell);
			//
			final CellType cellValueType = CellValueUtil.getCellType(cellValue);
			//
			if (Objects.equals(cellValueType, CellType.BOOLEAN)) {
				//
				result = Unit.with(cellValue != null ? Boolean.toString(cellValue.getBooleanValue()) : null);
				//
			} else if (Objects.equals(cellValueType, CellType.NUMERIC)) {
				//
				result = Unit.with(Util.toString(getNumberValue(cellValue)));
				//
			} else if (Objects.equals(cellValueType, CellType.STRING)) {
				//
				result = Unit.with(CellValueUtil.getStringValue(cellValue));
				//
			} // if
				//
			if (result == null) {
				//
				throw new UnsupportedOperationException(
						String.format("cellType=%1$s,cellValueType=%2$s", cellType, cellValueType));
				//
			} // if
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static Double getNumberValue(@Nullable final CellValue instance) {
		return instance != null ? Double.valueOf(instance.getNumberValue()) : null;
	}

	@Nullable
	private static CellValue evaluate(@Nullable final FormulaEvaluator instance, final Cell cell) {
		return instance != null ? instance.evaluate(cell) : null;
	}

	@Nullable
	private static List<JlptVocabulary> getObjectByUrls(@Nullable final List<String> urls)
			throws CsvValidationException, IllegalAccessException, IOException {
		//
		List<JlptVocabulary> list = null;
		//
		if (urls != null && urls.iterator() != null) {
			//
			List<JlptVocabulary> temp = null;
			//
			for (final String urlString : urls) {
				//
				if ((temp = getJlptVocabularies(urlString)) == null) {
					//
					continue;
					//
				} // if
					//
				addAll(list = ObjectUtils.getIfNull(list, ArrayList::new), temp);
				//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
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

	private static <E> void add(@Nullable final Collection<E> items, @Nullable final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <E> void addAll(@Nullable final Collection<E> a, @Nullable final Collection<? extends E> b) {
		if (a != null && (b != null || Proxy.isProxyClass(Util.getClass(a)))) {
			a.addAll(b);
		}
	}

	@Nullable
	private static List<JlptVocabulary> getJlptVocabularies(final String urlString)
			throws IOException, CsvValidationException, IllegalAccessException {
		//
		final URL url = testAndApply(StringUtils::isNotBlank, urlString, URL::new, null);
		//
		List<JlptVocabulary> list = null;
		//
		try (final InputStream is = openStream(url);
				final Reader r = testAndApply(Objects::nonNull, is, InputStreamReader::new, null);
				final CSVReader csvReader = testAndApply(Objects::nonNull, r, CSVReader::new, null)) {
			//
			final String level = StringUtils.substringAfterLast(StringUtils.substringBefore(getFile(url), '.'), '/');
			//
			String[] ss = null;
			//
			String s = null;
			//
			Map<Integer, Field> fieldMap = null;
			//
			JlptVocabulary jv = null;
			//
			Field f = null;
			//
			final AtomicBoolean first = new AtomicBoolean(true);
			//
			while ((ss = CSVReaderUtil.readNext(csvReader)) != null) {
				//
				if (first.getAndSet(false)) {
					//
					fieldMap = getFieldMap(ss);
					//
					continue;
					//
				} // if
					//
				(jv = new JlptVocabulary()).setLevel(level);
				//
				for (int i = 0; i < ss.length; i++) {
					//
					if ((f = MapUtils.getObject(fieldMap, Integer.valueOf(i))) == null) {
						//
						continue;
						//
					} // if
						//
					setAccessible(f, true);
					//
					s = ss[i];
					//
					if (Objects.equals(getType(f), Integer.class)) {
						//
						set(f, jv, testAndApply(StringUtils::isNotBlank, s, Integer::valueOf, null));
						//
					} else {
						//
						set(f, jv, s);
						//
					} // if
						//
				} // for
					//
				add(list = ObjectUtils.getIfNull(list, ArrayList::new), jv);
				//
			} // while
				//
		} // try
			//
		return list;
		//
	}

	@Nullable
	private static Map<Integer, Field> getFieldMap(final String[] ss) {
		//
		int size = 0;
		//
		List<Field> fs = null;
		//
		Entry<Integer, Field> entry = null;
		//
		Map<Integer, Field> fieldMap = null;
		//
		String s = null;
		//
		for (int i = 0; i < ss.length; i++) {
			//
			if ((size = IterableUtils
					.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(), s = ss[i]))) == 1) {
				//
				entry = Pair.of(Integer.valueOf(i), IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
				// domain.JlptVocabulary$ColumnName
				//
			if (entry == null && (size = IterableUtils
					.size(fs = getFieldsByColumnName(JlptVocabulary.class.getDeclaredFields(), s))) == 1) {
				//
				entry = Pair.of(Integer.valueOf(i), IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			if (entry != null) {
				//
				Util.put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new), entry.getKey(),
						entry.getValue());
				//
			} // if
				//
		} // for
			//
		return fieldMap;
		//
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	@Nullable
	private static String getFile(@Nullable final URL instance) {
		return instance != null ? instance.getFile() : null;
	}

	@Nullable
	private static List<Field> getFieldsByName(@Nullable final Field[] fs, @Nullable final String name) {
		//
		List<Field> list = null;
		//
		Field f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if (!Objects.equals(getName(f = fs[i]), name)) {
				//
				continue;
				//
			} // if
				//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), f);
			//
		} // for
			//
		return list;
		//
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static List<Field> getFieldsByColumnName(final Field[] fs, @Nullable final String columnName) {
		//
		return new FailableStream<Field>(testAndApply(Objects::nonNull, fs, Arrays::stream, null)).filter(f -> {
			//
			final List<Annotation> as = new FailableStream<>(
					testAndApply(Objects::nonNull, getDeclaredAnnotations(f), Arrays::stream, null)).filter(

							a -> {
								//
								final IValue0<Object> iValue0 = getColumnName(a);
								//
								if (iValue0 != null) {
									//
									return Objects.equals(IValue0Util.getValue0(iValue0), columnName);
									//
								} // if
									//
								return false;
								//
							})
					.collect(Collectors.toList());
			//
			final int sz = IterableUtils.size(as);
			//
			if (sz > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			return sz == 1;
			//
		}).collect(Collectors.toList());
		//
	}

	@Nullable
	private static IValue0<Object> getColumnName(final Annotation a)
			throws IllegalAccessException, InvocationTargetException {
		//
		if (Objects.equals("domain.JlptVocabulary$ColumnName", getName(annotationType(a)))) {
			//
			final Method[] ms = getDeclaredMethods(annotationType(a));
			//
			// Check if there is only one method defined in a.class which returns
			// "java.lang.String"
			//
			Method m = ms != null && ms.length == 1 ? ms[0] : null;
			//
			if (m != null && Objects.equals(String.class, m.getReturnType()) && m.getParameterCount() == 0) {
				//
				setAccessible(m, true);
				//
				return Unit.with(invoke(m, a));
				//
			} // if
				//
				// Check if there is a "value()" defined in a.class
				//
			final List<Method> methods = Util.toList(
					filter(testAndApply(Objects::nonNull, annotationType(a).getDeclaredMethods(), Arrays::stream, null),
							x -> x != null && Objects.equals("value", getName(x)) && x.getParameterCount() == 0));
			//
			final int sz = IterableUtils.size(methods);
			//
			if ((m = testAndApply(x -> IterableUtils.size(x) == 1, methods, x -> IterableUtils.get(x, 0),
					null)) != null) {
				//
				setAccessible(m, true);
				//
			} // if
				//
			if (sz > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			return Unit.with(invoke(m, a));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Method[] getDeclaredMethods(@Nullable final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Nullable
	private static Annotation[] getDeclaredAnnotations(@Nullable final AnnotatedElement instance) {
		return instance != null ? instance.getDeclaredAnnotations() : null;
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}