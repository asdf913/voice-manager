package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;
import org.xml.sax.SAXException;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class StringMapFromResourceFactoryBean implements MapFromResourceFactoryBean<String, String> {

	@Nullable
	private IValue0<Map<String, String>> iValue0 = null;

	private Resource resource = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Sheet Name")
	private IValue0<String> sheetName = null;

	@Note("Key Column Name")
	private IValue0<String> keyColumnName = null;

	@Note("Value Column Name")
	private Pair<String, Integer> valueColumnNameAndIndex = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setSheetName(final String sheetName) {
		this.sheetName = Unit.with(sheetName);
	}

	public void setKeyColumnName(final String keyColumnName) {
		this.keyColumnName = Unit.with(keyColumnName);
	}

	public void setValueColumnName(final String name) {
		//
		if ((valueColumnNameAndIndex = ObjectUtils.getIfNull(valueColumnNameAndIndex,
				() -> Pair.with(name, null))) != null) {
			//
			valueColumnNameAndIndex = valueColumnNameAndIndex.setAt0(name);
			//
		} // if
			//
	}

	public void setValueColumnIndex(final Integer index) {
		//
		if ((valueColumnNameAndIndex = ObjectUtils.getIfNull(valueColumnNameAndIndex,
				() -> Pair.with(null, index))) != null) {
			//
			valueColumnNameAndIndex = valueColumnNameAndIndex.setAt1(index);
			//
		} // if
			//
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
				iValue0 = ResourceUtil.exists(resource)
						? createMap(resource, sheetName, keyColumnName, valueColumnNameAndIndex)
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

		boolean containsKey(final Object key);

		static boolean containsKey(@Nullable final ObjectIntMap<?> instance, final Object key) {
			return instance != null && instance.containsKey(key);
		}

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
		@Nullable
		public Object invoke(final Object proxy, @Nullable final Method method, @Nullable final Object[] args)
				throws Throwable {
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

	@Nullable
	private static IValue0<Map<String, String>> createMap(final Resource resource,
			@Nullable final IValue0<String> sheetName, final IValue0<String> keyColumnName,
			final Pair<String, Integer> valueColumnNameAndIndex)
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
				|| Objects.equals("OLE 2 Compound Document", getMessage(ci)) || XlsxUtil.isXlsx(resource)) {
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
				result = createMap(sheet, keyColumnName, valueColumnNameAndIndex);
				//
			} // try
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static IValue0<Map<String, String>> createMap(@Nullable final Sheet sheet,
			final IValue0<String> keyColumnName, final Pair<String, Integer> valueColumnNameAndIndex) {
		//
		if (sheet == null || sheet.iterator() == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<Map<String, String>> result = null;
		//
		ObjectIntMap<String> objectIntMap = null;
		//
		final AtomicBoolean first = new AtomicBoolean(true);
		//
		Cell cellKey = null;
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
			if ((cellKey = testAndApply((a, b) -> b != null && ObjectIntMap.containsKey(a, IValue0Util.getValue0(b)),
					objectIntMap, keyColumnName, (a, b) -> RowUtil.getCell(row, a.get(IValue0Util.getValue0(b))),
					null)) == null) {
				//
				cellKey = testAndApply(x -> getPhysicalNumberOfCells(row, 0) > 0, row, x -> RowUtil.getCell(row, 0),
						null);
				//
			} // if
				//
			testAndAccept((a, b, c) -> and(Objects::nonNull, b, c), IValue0Util.getValue0(result), cellKey,
					getValueCell(row, objectIntMap, valueColumnNameAndIndex),
					(a, b, c) -> put(a, CellUtil.getStringCellValue(b), CellUtil.getStringCellValue(c)));
			//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Cell getValueCell(final Row row, final ObjectIntMap<String> objectIntMap,
			@Nullable final Pair<String, Integer> valueColumnNameAndIndex) {
		//
		Cell cell = null;
		//
		final Integer index = valueColumnNameAndIndex != null ? valueColumnNameAndIndex.getValue1() : null;
		//
		if ((cell = testAndApply((a, b) -> b != null && ObjectIntMap.containsKey(a, IValue0Util.getValue0(b)),
				objectIntMap, valueColumnNameAndIndex, (a, b) -> RowUtil.getCell(row, a.get(IValue0Util.getValue0(b))),
				null)) == null && index != null) {
			//
			cell = testAndApply(x -> getPhysicalNumberOfCells(row, 0) >= index.intValue(), row,
					x -> RowUtil.getCell(row, index.intValue()), null);
			//
		} // if
			//
		return cell;
		//
	}

	private static <T> boolean and(final Predicate<T> predicate, final T a, final T b) {
		return test(predicate, a) && test(predicate, b);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static int getPhysicalNumberOfCells(@Nullable final Row instance, final int defaultValue) {
		return instance != null ? instance.getPhysicalNumberOfCells() : defaultValue;
	}

	@Nullable
	private static ObjectIntMap<String> createObjectIntMap(final Row row) {
		//
		final ObjectIntMap<String> objectIntMap = Reflection.newProxy(ObjectIntMap.class, new IH());
		//
		for (int i = 0; objectIntMap != null && i < IterableUtils.size(row); i++) {
			//
			objectIntMap.put(CellUtil.getStringCellValue(RowUtil.getCell(row, i)), i);
			//
		} // for
			//
		return objectIntMap;
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			final U u, final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse)
			throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <R, T, U> R apply(@Nullable BiFunction<T, U, R> instance, final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static <A, B, C> void testAndAccept(@Nullable final TriPredicate<A, B, C> predicate, final A a, final B b,
			final C c, @Nullable final TriConsumer<A, B, C> consumer) {
		if (predicate != null && predicate.test(a, b, c) && consumer != null) {
			consumer.accept(a, b, c);
		}
	}

}