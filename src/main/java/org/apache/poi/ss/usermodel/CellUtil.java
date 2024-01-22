package org.apache.poi.ss.usermodel;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;

import io.github.toolfactory.narcissus.Narcissus;

public interface CellUtil {

	static void setCellValue(final Cell instance, final String value) {
		if (instance != null) {
			instance.setCellValue(value);
		}
	}

	static void setCellStyle(final Cell instance, final CellStyle cellStyle) {
		if (instance != null) {
			instance.setCellStyle(cellStyle);
		}
	}

	static String getStringCellValue(final Cell instance) {
		return instance != null ? instance.getStringCellValue() : null;
	}

	static CellType getCellType(final Cell instance) {
		return instance != null ? instance.getCellType() : null;
	}

	static String getStringCellValue(final Cell instance, final FormulaEvaluator formulaEvaluator) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		final String name = getName(clz);
		//
		if (Objects.equals("org.apache.poi.xssf.usermodel.XSSFCell", name)) {
			//
			final List<Field> fs = toList(
					filter(Arrays.stream(getDeclaredFields(clz)), f -> Objects.equals(getName(f), "_cell")));
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException(Integer.toString(size));
				//
			} // if
				//
			if (Narcissus.getField(instance,
					testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return null;
				//
			} // if
				//
			final CellType cellType = org.apache.poi.ss.usermodel.CellUtil.getCellType(instance);
			//
			if (contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
				//
				return instance.getStringCellValue();
				//
			} else if (Objects.equals(CellType.NUMERIC, cellType)) {
				//
				return Double.toString(instance.getNumericCellValue());
				//
			} else if (Objects.equals(CellType.BOOLEAN, cellType)) {
				//
				return Boolean.toString(instance.getBooleanCellValue());
				//
			} else if (Objects.equals(CellType.FORMULA, cellType)) {
				//
				return toString(instance, formulaEvaluator);
				//
			} // if
				//
			throw new IllegalStateException(toString(cellType));
			//
		} else if (Objects.equals("org.apache.poi.hssf.usermodel.HSSFCell", name)) {
			//
			return handleHSSFCell(instance, formulaEvaluator);
			//
		} else if (Objects.equals("org.apache.poi.xssf.streaming.SXSSFCell", name)) {
			//
			return handleSXSSFCell(instance, formulaEvaluator);
			//
		} // if
			//
		return instance.getStringCellValue();
		//
	}

	private static String handleSXSSFCell(final Cell instance, final FormulaEvaluator formulaEvaluator) {
		//
		final List<Field> fs = toList(filter(Arrays.stream(getDeclaredFields(getClass(instance))),
				f -> Objects.equals(getName(f), "_value")));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException(Integer.toString(size));
			//
		} // if
			//
		if (Narcissus.getField(instance,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return null;
			//
		} // if
			//
		final CellType cellType = getCellType(instance);
		//
		if (contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
			//
			return instance != null ? instance.getStringCellValue() : null;
			//
		} else if (Objects.equals(CellType.BOOLEAN, cellType)) {
			//
			return instance != null ? Boolean.toString(instance.getBooleanCellValue()) : null;
			//
		} else if (Objects.equals(CellType.NUMERIC, cellType)) {
			//
			return instance != null ? Double.toString(instance.getNumericCellValue()) : null;
			//
		} else if (Objects.equals(CellType.FORMULA, cellType)) {
			//
			return toString(instance, formulaEvaluator);
			//
		} // if
			//
		throw new IllegalStateException(toString(cellType));
		//
	}

	private static String handleHSSFCell(final Cell instance, final FormulaEvaluator formulaEvaluator) {
		//
		final CellType cellType = getCellType(instance);
		//
		if (cellType == null) {
			//
			return null;
			//
		} // if
			//
		if (contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
			//
			return getStringCellValue(instance);
			//
		} else if (Objects.equals(CellType.BOOLEAN, cellType)) {
			//
			return Boolean.toString(instance.getBooleanCellValue());
			//
		} else if (Objects.equals(CellType.NUMERIC, cellType)) {
			//
			return Double.toString(instance.getNumericCellValue());
			//
		} else if (Objects.equals(CellType.FORMULA, cellType)) {
			//
			return toString(instance, formulaEvaluator);
			//
		} // if
			//
		throw new IllegalStateException(toString(cellType));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static String toString(final Cell instance, final FormulaEvaluator formulaEvaluator) {
		//
		final CellValue cellValue = FormulaEvaluatorUtil.evaluate(formulaEvaluator, instance);
		//
		final CellType cellValueType = CellValueUtil.getCellType(cellValue);
		//
		if (Objects.equals(CellType.ERROR, cellValueType)) {
			//
			return Byte.toString(cellValue.getErrorValue());
			//
		} else if (Objects.equals(CellType.BOOLEAN, cellValueType)) {
			//
			return Boolean.toString(cellValue.getBooleanValue());
			//
		} else if (Objects.equals(CellType.NUMERIC, cellValueType)) {
			//
			return Double.toString(cellValue.getNumberValue());
			//
		} else if (Objects.equals(CellType.STRING, cellValueType)) {
			//
			return cellValue.getStringValue();
			//
		} // if
			//
		if (cellValue != null) {
			//
			throw new IllegalStateException(toString(cellValueType));
			//
		} // if
			//
		return instance != null ? instance.getCellFormula() : null;
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

}