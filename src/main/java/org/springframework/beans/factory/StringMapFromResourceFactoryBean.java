package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.xml.sax.SAXException;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class StringMapFromResourceFactoryBean implements MapFromResourceFactoryBean<String, String> {

	private IValue0<Map<String, String>> iValue0 = null;

	private Resource resource = null;

	private IValue0<String> sheetName, keyColumnName, valueColumnName = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setSheetName(final String sheetName) {
		this.sheetName = Unit.with(sheetName);
	}

	public void setKeyColumnName(final String keyColumnName) {
		this.keyColumnName = Unit.with(keyColumnName);
	}

	public void setValueColumnName(final String valueColumnName) {
		this.valueColumnName = Unit.with(valueColumnName);
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		return IValue0Util.getValue0(getIvalue0());
		//
	}

	@Override
	public IValue0<Map<String, String>> getIvalue0() {
		//
		if (iValue0 == null) {
			//
			try {
				//
				iValue0 = ResourceUtil.exists(resource) ? createMap(resource, sheetName, keyColumnName, valueColumnName)
						: null;
				//
			} catch (final IOException | SAXException | ParserConfigurationException e) {
				//
				throw ObjectUtils.getIfNull(e instanceof RuntimeException re ? re : null,
						() -> new RuntimeException(e));
				//
			} // try
				//
		} // if
			//
		return iValue0;
		//
	}

	private static interface ObjectIntMap<K> {

		void put(final K key, final int value);

		int get(final K key);

		boolean containsKey(final K key);

	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> map = null;

		private Map<Object, Object> getMap() {
			if (map == null) {
				map = new LinkedHashMap<>();
			}
			return map;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ObjectIntMap) {
				//
				if (Objects.equals(methodName, "put") && args != null && args.length > 1) {
					//
					put(getMap(), args[0], args[1]);
					//
					return null;
					//
				} else if (Objects.equals(methodName, "get") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!getMap().containsKey(key)) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					return getMap().get(key);
					//
				} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
					//
					return getMap().containsKey(args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static IValue0<Map<String, String>> createMap(final Resource resource, final IValue0<String> sheetName,
			final IValue0<String> keyColumnName, final IValue0<String> valueColumnName)
			throws IOException, SAXException, ParserConfigurationException {
		//
		final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
		//
		IValue0<Map<String, String>> result = null;
		//
		final ContentInfo ci = new ContentInfoUtil().findMatch(bs);
		//
		final String mimeType = getMimeType(ci);
		//
		if (Objects.equals("application/vnd.openxmlformats-officedocument", mimeType)
				|| Objects.equals("OLE 2 Compound Document", getMessage(ci))) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
				//
				Sheet sheet = null;
				//
				if (sheetName != null) {
					//
					if ((sheet = wb.getSheet(IValue0Util.getValue0(sheetName))) == null) {
						//
						throw new IllegalArgumentException(
								String.format("Sheet [%1$s] not found", IValue0Util.getValue0(sheetName)));
						//
					} // if
						//
				} else {
					//
					final int numberOfSheets = wb.getNumberOfSheets();
					//
					if (numberOfSheets == 0) {
						//
						throw new IllegalArgumentException("There is no sheet in the workbook");
						//
					} else if (numberOfSheets > 1) {
						//
						throw new IllegalArgumentException("There are more than one sheet in the workbook");
						//
					} // if
						//
					sheet = wb.getSheetAt(0);
					//
				} // if
					//
				result = createMap(sheet, keyColumnName, valueColumnName);
				//
			} // try
				//
		} // if
			//
		return result;
		//
	}

	private static IValue0<Map<String, String>> createMap(final Sheet sheet, final IValue0<String> keyColumnName,
			final IValue0<String> valueColumnName) {
		//
		IValue0<Map<String, String>> result = null;
		//
		ObjectIntMap<String> objectIntMap = null;
		//
		if (sheet != null && sheet.iterator() != null) {
			//
			final AtomicBoolean first = new AtomicBoolean(true);
			//
			Cell cellKey, cellValue = null;
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
					objectIntMap = createObjectIntMap(row);
					//
					continue;
					//
				} // if
					//
				if (objectIntMap == null
						|| (result = ObjectUtils.getIfNull(result, () -> Unit.with(new LinkedHashMap<>()))) == null) {
					//
					continue;
					//
				} // if
					//
					// key
					//
				if ((cellKey = testAndApply((a, b) -> a != null && b != null && a.containsKey(IValue0Util.getValue0(b)),
						objectIntMap, keyColumnName, (a, b) -> row.getCell(a.get(IValue0Util.getValue0(b))),
						null)) == null) {
					//
					cellKey = testAndApply(x -> row != null && row.getPhysicalNumberOfCells() > 0, row,
							x -> row != null ? row.getCell(0) : null, null);
					//
				} // if
					//
					// value
					//
				if ((cellValue = testAndApply(
						(a, b) -> a != null && b != null && a.containsKey(IValue0Util.getValue0(b)), objectIntMap,
						valueColumnName, (a, b) -> row.getCell(a.get(IValue0Util.getValue0(b))), null)) == null) {
					//
					cellValue = testAndApply(x -> row != null && row.getPhysicalNumberOfCells() > 1, row,
							x -> row != null ? row.getCell(1) : null, null);
					//
				} // if
					//
				if (cellKey != null && cellValue != null) {
					//
					put(IValue0Util.getValue0(result), CellUtil.getStringCellValue(cellKey),
							CellUtil.getStringCellValue(cellValue));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return result;
		//
	}

	private static ObjectIntMap<String> createObjectIntMap(final Row row) {
		//
		final ObjectIntMap<String> objectIntMap = Reflection.newProxy(ObjectIntMap.class, new IH());
		//
		for (int i = 0; objectIntMap != null && i < IterableUtils.size(row); i++) {
			//
			objectIntMap.put(CellUtil.getStringCellValue(row.getCell(i)), i);
			//
		} // for
			//
		return objectIntMap;
	}

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static String getMessage(final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse)
			throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	private static <R, T, U> R apply(BiFunction<T, U, R> instance, final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

}