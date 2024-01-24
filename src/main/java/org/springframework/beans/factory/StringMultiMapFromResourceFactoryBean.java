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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CellValueUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.FormulaEvaluatorUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.javatuples.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsUtil;
import org.springframework.core.io.XlsxUtil;
import org.xml.sax.SAXException;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfoUtil;

public class StringMultiMapFromResourceFactoryBean implements FactoryBean<Multimap<String, String>> {

	private IValue0<Multimap<String, String>> iValue0 = null;

	private Resource resource = null;

	private IValue0<String> sheetName, keyColumnName = null;

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
	public Multimap<String, String> getObject() throws Exception {
		//
		return IValue0Util.getValue0(getIvalue0());
		//
	}

	@Nullable
	IValue0<Multimap<String, String>> getIvalue0() {
		//
		if (iValue0 == null) {
			//
			try {
				//
				iValue0 = ResourceUtil.exists(resource)
						? createMultimap(resource, sheetName, keyColumnName, valueColumnNameAndIndex)
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
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ObjectIntMap) {
				//
				if (Objects.equals(methodName, "put") && args != null && args.length > 1) {
					//
					Util.put(getMap(), args[0], args[1]);
					//
					return null;
					//
				} else if (Objects.equals(methodName, "get") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!Util.containsKey(getMap(), key)) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					return Util.get(getMap(), key);
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
	private static IValue0<Multimap<String, String>> createMultimap(final Resource resource,
			final IValue0<String> sheetName, final IValue0<String> keyColumnName,
			final Pair<String, Integer> valueColumnNameAndIndex)
			throws IOException, SAXException, ParserConfigurationException {
		//
		final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
		//
		IValue0<Multimap<String, String>> result = null;
		//
		if (Objects.equals("application/vnd.openxmlformats-officedocument",
				Util.getMimeType(new ContentInfoUtil().findMatch(bs))) || XlsUtil.isXls(resource)
				|| XlsxUtil.isXlsx(resource)) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
				//
				Sheet sheet = null;
				//
				if (sheetName != null) {
					//
					if ((sheet = WorkbookUtil.getSheet(wb, IValue0Util.getValue0(sheetName))) == null) {
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
				result = createMultimap(sheet,
						CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb)), keyColumnName,
						valueColumnNameAndIndex);
				//
			} // try
				//
		} // if
			//
		return result;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(final Sheet sheet,
			final FormulaEvaluator formulaEvaluator, final IValue0<String> keyColumnName,
			final Pair<String, Integer> valueColumnNameAndIndex) {
		//
		if (Util.iterator(sheet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<Multimap<String, String>> result = null;
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
					|| (result = ObjectUtils.getIfNull(result, () -> Unit.with(LinkedHashMultimap.create()))) == null) {
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
					(a, b, c) -> MultimapUtil.put(a, getString(b, formulaEvaluator), getString(c, formulaEvaluator)));
			//
		} // for
			//
		return result;
		//
	}

	private static String getString(@Nullable final Cell cell, final FormulaEvaluator formulaEvaluator) {
		//
		if (cell == null) {
			//
			return null;
			//
		} // if
			//
		final CellType cellType = CellUtil.getCellType(cell);
		//
		IValue0<String> iv = null;
		//
		if (Objects.equals(cellType, CellType.STRING) || Objects.equals(cellType, CellType.BLANK)) {
			//
			iv = Unit.with(CellUtil.getStringCellValue(cell));
			//
		} else if (Objects.equals(cellType, CellType.NUMERIC)) {
			//
			iv = Unit.with(Double.toString(cell.getNumericCellValue()));
			//
		} else if (Objects.equals(cellType, CellType.BOOLEAN)) {
			//
			iv = Unit.with(Boolean.toString(cell.getBooleanCellValue()));
			//
		} else if (Objects.equals(cellType, CellType.FORMULA)) {
			//
			iv = getString(FormulaEvaluatorUtil.evaluate(formulaEvaluator, cell));
			//
		} // if
			//
		if (iv == null) {
			//
			throw new IllegalStateException(Util.toString(cellType));
			//
		} // if
			//
		return IValue0Util.getValue0(iv);
		//
	}

	private static IValue0<String> getString(@Nullable final CellValue cellValue) {
		//
		if (cellValue == null) {
			//
			return null;
			//
		} // if
			//
		final CellType cellType = CellValueUtil.getCellType(cellValue);
		//
		IValue0<String> iv = null;
		//
		if (Objects.equals(cellType, CellType.BOOLEAN)) {
			//
			iv = Unit.with(Boolean.toString(cellValue.getBooleanValue()));
			//
		} else if (Objects.equals(cellType, CellType.STRING)) {
			//
			iv = Unit.with(CellValueUtil.getStringValue(cellValue));
			//
		} else if (Objects.equals(cellType, CellType.ERROR)) {
			//
			final String string = getString(
					FormulaError.isValidCode(cellValue.getErrorValue()) ? FormulaError.forInt(cellValue.getErrorValue())
							: null);
			//
			iv = Unit.with(StringUtils.isNotBlank(string) ? string : Byte.toString(cellValue.getErrorValue()));
			//
		} // if
			//
		if (iv == null) {
			//
			throw new IllegalStateException(Util.toString(cellType));
			//
		} // if
			//
		return iv;
		//
	}

	private static String getString(@Nullable final FormulaError instance) {
		return instance != null ? instance.getString() : null;
	}

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

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T, U, R, E extends Throwable> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t,
			@Nullable final U u, final BiFunction<T, U, R> functionTrue,
			@Nullable final BiFunction<T, U, R> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? Util.apply(functionTrue, t, u)
				: Util.apply(functionFalse, t, u);
	}

	private static <A, B, C> void testAndAccept(final TriPredicate<A, B, C> predicate, final A a, final B b, final C c,
			@Nullable final TriConsumer<A, B, C> consumer) {
		if (predicate != null && predicate.test(a, b, c) && consumer != null) {
			consumer.accept(a, b, c);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}