package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
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
							wb != null && wb.getNumberOfSheets() == 1 ? WorkbookUtil.getSheetAt(wb, 0) : null);
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

	private static IValue0<List<JlptVocabulary>> getJlptVocabularies(final Sheet sheet) throws IllegalAccessException {
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
				if ((jv = getJlptVocabulary(fieldMap, row)) != null) {
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

	private static Map<Integer, Field> getFieldMap(final Row row) {
		//
		int size = 0;
		//
		List<Field> fs = null;
		//
		Map<Integer, Field> fieldMap = null;
		//
		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
			//
			if ((size = IterableUtils.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(),
					getStringCellValue(row.getCell(i))))) == 1) {
				//
				put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new), Integer.valueOf(i),
						IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return fieldMap;
		//
	}

	private static IValue0<JlptVocabulary> getJlptVocabulary(final Map<Integer, Field> fieldMap, final Row row)
			throws IllegalAccessException {
		//
		IValue0<JlptVocabulary> ivalue0 = null;
		//
		if (row != null && row.iterator() != null) {
			//
			Field f = null;
			//
			JlptVocabulary jv = null;
			//
			Cell cell = null;
			//
			CellType cellType = null;
			//
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				//
				if ((f = MapUtils.getObject(fieldMap, Integer.valueOf(i))) == null || (cell = row.getCell(i)) == null
						|| Objects.equals(CellType.BLANK, cellType = cell.getCellType())) {
					//
					continue;
					//
				} // if
					//
				f.setAccessible(true);
				//
				jv = ObjectUtils.getIfNull(
						IValue0Util.getValue0(
								ivalue0 = ObjectUtils.getIfNull(ivalue0, () -> Unit.with(new JlptVocabulary()))),
						JlptVocabulary::new);
				//
				if (Objects.equals(f.getType(), Integer.class)) {
					//
					if (Objects.equals(cellType, CellType.STRING)) {
						//
						f.set(jv, testAndApply(StringUtils::isNotBlank, getStringCellValue(cell), Integer::valueOf,
								null));
						//
					} else {
						//
						throw new UnsupportedOperationException();
						//
					} // if
						//
				} else {
					//
					f.set(jv, getStringCellValue(cell));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return ivalue0;
		//
	}

	private static List<JlptVocabulary> getObjectByUrls(final List<String> urls)
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static String getMessage(final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) {
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

	private static String getStringCellValue(final Cell instance) {
		return instance != null ? instance.getStringCellValue() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <E> void addAll(final Collection<E> a, final Collection<? extends E> b) {
		if (a != null && b != null) {
			a.addAll(b);
		}
	}

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
			while ((ss = readNext(csvReader)) != null) {
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
					f.setAccessible(true);
					//
					s = ss[i];
					//
					if (Objects.equals(f.getType(), Integer.class)) {
						//
						f.set(jv, testAndApply(StringUtils::isNotBlank, s, Integer::valueOf, null));
						//
					} else {
						//
						f.set(jv, s);
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

	private static Map<Integer, Field> getFieldMap(final String[] ss) {
		//
		int size = 0;
		//
		List<Field> fs = null;
		//
		Map<Integer, Field> fieldMap = null;
		//
		for (int i = 0; i < ss.length; i++) {
			//
			if ((size = IterableUtils
					.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(), ss[i]))) == 1) {
				//
				put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new), Integer.valueOf(i),
						IterableUtils.get(fs, 0));
				//
			} else if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return fieldMap;
		//
	}

	private static InputStream openStream(final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	private static String getFile(final URL instance) {
		return instance != null ? instance.getFile() : null;
	}

	private static String[] readNext(final CSVReader instance) throws CsvValidationException, IOException {
		return instance != null ? instance.readNext() : null;
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static List<Field> getFieldsByName(final Field[] fs, final String name) {
		//
		List<Field> list = null;
		//
		Field f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null || !Objects.equals(f.getName(), name)) {
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

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}