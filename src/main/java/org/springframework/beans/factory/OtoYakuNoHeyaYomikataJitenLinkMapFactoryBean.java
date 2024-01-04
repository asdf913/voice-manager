package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CellValueUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.FormulaEvaluatorUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import io.github.toolfactory.narcissus.Narcissus;

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean implements FactoryBean<Object>, InitializingBean {

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("title")
	private String title = null;

	@Note("URL Transition Sheet Name")
	private String urlTransitionSheetName = null;

	private Map<Object, Object> urlMap = null;

	private Resource resource = null;

	private String linkSheetName = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrlTransitionSheetName(final String urlTransitionSheetName) {
		this.urlTransitionSheetName = urlTransitionSheetName;
	}

	public void setLinkSheetName(final String linkSheetName) {
		this.linkSheetName = linkSheetName;
	}

	public void setUrlMap(final Map<Object, Object> urlMap) {
		this.urlMap = urlMap;
	}

	static interface Link {

		String getCategory();

		String getUrl();

		void setUrl(final String url);

		String getText();

		String getDescription();

		Integer getNumber();

		String getImgSrc();

		@Nullable
		static String getUrl(@Nullable final Link instance) {
			return instance != null ? instance.getUrl() : null;
		}

		static void setUrl(@Nullable final Link instance, final String url) {
			//
			if (instance != null) {
				//
				instance.setUrl(url);
				//
			} // if
				//
		}

	}

	private static class IH implements InvocationHandler {

		@Note("description")
		private String description = null;

		@Note("url")
		private String url = null;

		@Note("category")
		private String category = null;

		@Note("text")
		private String text = null;

		private String imgSrc = null;

		private Integer number = null;

		private Map<Object, Object> objects = null;

		private Map<Object, Integer> integers = null;

		private Map<Object, Object> intStrings = null;

		private Map<Object, Object> getObjects() {
			if (objects == null) {
				objects = new LinkedHashMap<>();
			}
			return objects;
		}

		private Map<Object, Integer> getIntegers() {
			if (integers == null) {
				integers = new LinkedHashMap<>();
			}
			return integers;
		}

		private Map<Object, Object> getIntStrings() {
			if (intStrings == null) {
				intStrings = new LinkedHashMap<>();
			}
			return intStrings;
		}

		@Override
		@Nullable
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link) {
				//
				final IValue0<?> iValue0 = handleLink(methodName, args);
				//
				if (iValue0 != null) {
					//
					return iValue0.getValue0();
					//
				} // if
					//
			} else if (proxy instanceof ObjectMap && Objects.equals(methodName, "getObject") && args != null
					&& args.length > 0) {
				//
				final Object obj = args[0];
				//
				final Map<?, ?> map = getObjects();
				//
				if (!Util.containsKey(map, obj)) {
					//
					throw new IllegalStateException(Util.toString(obj));
					//
				} // if
					//
				return Util.get(map, obj);
				//
			} else if (Boolean.logicalAnd(proxy instanceof IntMap, Objects.equals(methodName, "getInt")) && args != null
					&& args.length > 1) {
				//
				final Object object = args[0];
				//
				final Map<?, ?> map = getIntegers();
				//
				if (!Util.containsKey(map, object)) {
					//
					throw new IllegalStateException(Util.toString(object));
					//
				} // if
					//
				return ObjectUtils.getIfNull(Util.get(map, object), () -> Util.cast(Integer.class, args[1]));
				//
			} else if (proxy instanceof IntStringMap) {
				//
				final IValue0<?> iValue0 = handleIntStringMap(methodName, args);
				//
				if (iValue0 != null) {
					//
					return iValue0.getValue0();
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		@Nullable
		private IValue0<?> handleIntStringMap(final String methodName, @Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "setString") && args != null && args.length > 1) {
				//
				Util.put(getIntStrings(), args[0], args[1]);
				//
				return Unit.with(null);
				//
			} else if (Objects.equals(methodName, "getString") && args != null && args.length > 0) {
				//
				final Object object = args[0];
				//
				final Map<?, ?> map = getIntStrings();
				//
				if (!Util.containsKey(map, object)) {
					//
					throw new IllegalStateException(Util.toString(object));
					//
				} // if
					//
				return Unit.with(ObjectUtils.getIfNull(Util.get(map, object), () -> Util.cast(Integer.class, args[1])));
				//
			} // if
				//
			return null;
			//
		}

		@Nullable
		private IValue0<?> handleLink(final String methodName, @Nullable final Object[] args) {
			//
			if (Objects.equals(methodName, "getText")) {
				//
				return Unit.with(text);
				//
			} else if (Objects.equals(methodName, "getDescription")) {
				//
				return Unit.with(description);
				//
			} else if (Objects.equals(methodName, "getUrl")) {
				//
				return Unit.with(url);
				//
			} else if (Objects.equals(methodName, "getCategory")) {
				//
				return Unit.with(category);
				//
			} else if (Objects.equals(methodName, "getNumber")) {
				//
				return Unit.with(number);
				//
			} else if (Objects.equals(methodName, "getImgSrc")) {
				//
				return Unit.with(imgSrc);
				//
			} else if (Objects.equals(methodName, "setUrl") && args != null && args.length > 0) {
				//
				this.url = Util.toString(args[0]);
				//
				return Unit.with(null);
				//
			} // if
				//
			return null;
			//
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			ContentInfo ci = null;
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				ci = new ContentInfoUtil().findMatch(is);
				//
			} // try
				//
			if (XlsxUtil.isXlsx(resource) || Objects.equals(Util.getMessage(ci), "OLE 2 Compound Document")) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					Util.putAll(urlMap = ObjectUtils.getIfNull(urlMap, LinkedHashMap::new),
							toMap(WorkbookUtil.getSheet(wb, urlTransitionSheetName),
									CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
					//
				} // try
					//
			} // if
				//
		} // if
			//
	}

	@Nullable
	private static Map<Object, Object> toMap(@Nullable final Sheet sheet, final FormulaEvaluator formulaEvaluator) {
		//
		Map<Object, Object> map = null;
		//
		if (iterator(sheet) != null) {
			//
			for (final Row row : sheet) {
				//
				if (row == null || row.getPhysicalNumberOfCells() < 2 || IterableUtils.size(row) < 2) {
					//
					continue;
					//
				} // if
					//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
						getStringCellValue(IterableUtils.get(row, 0), formulaEvaluator),
						getStringCellValue(IterableUtils.get(row, 1), formulaEvaluator));
				//
			} // for
				//
		} // if
			//
		return map;
		//
	}

	@Nullable
	private static String getStringCellValue(@Nullable final Cell instance,
			@Nullable final FormulaEvaluator formulaEvaluator) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance);
		//
		final String name = Util.getName(clz);
		//
		if (Objects.equals("org.apache.poi.xssf.usermodel.XSSFCell", name)) {
			//
			final List<Field> fs = Util.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(clz)),
					f -> Objects.equals(Util.getName(f), "_cell")));
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
			if (Util.contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
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
			throw new IllegalStateException(Util.toString(cellType));
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

	@Nullable
	private static String handleHSSFCell(@Nullable final Cell instance,
			@Nullable final FormulaEvaluator formulaEvaluator) {
		//
		final CellType cellType = org.apache.poi.ss.usermodel.CellUtil.getCellType(instance);
		//
		if (cellType == null) {
			//
			return null;
			//
		} // if
			//
		if (Util.contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
			//
			return org.apache.poi.ss.usermodel.CellUtil.getStringCellValue(instance);
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
		throw new IllegalStateException(Util.toString(cellType));
		//
	}

	@Nullable
	private static String handleSXSSFCell(final Cell instance, @Nullable final FormulaEvaluator formulaEvaluator) {
		//
		final List<Field> fs = Util.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(Util.getClass(instance))),
				f -> Objects.equals(Util.getName(f), "_value")));
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
		if (Util.contains(Arrays.asList(CellType.BLANK, CellType.STRING), cellType)) {
			//
			return instance.getStringCellValue();
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
		throw new IllegalStateException(Util.toString(cellType));
		//
	}

	@Nullable
	private static String toString(@Nullable final Cell instance, @Nullable final FormulaEvaluator formulaEvaluator) {
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
			throw new IllegalStateException(Util.toString(cellValueType));
			//
		} // if
			//
		return instance != null ? instance.getCellFormula() : null;
		//
	}

	private static interface IntStringMap {

		String getString(final int key);

		void setString(final int key, @Nullable final String value);

		@Nullable
		static String getString(@Nullable final IntStringMap instance, final int key) {
			return instance != null ? instance.getString(key) : null;
		}
	}

	@Override
	public List<Link> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			ContentInfo ci = null;
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				ci = new ContentInfoUtil().findMatch(is);
				//
			} // try
				//
			if (XlsxUtil.isXlsx(resource) || Objects.equals(Util.getMessage(ci), "OLE 2 Compound Document")) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					final IValue0<List<Link>> iValue0 = toLinks(wb, linkSheetName);
					//
					if (iValue0 != null) {
						//
						return IValue0Util.getValue0(iValue0);
						//
					} // if
						//
				} // try
					//
			} // if
				//
		} // if
			//
		final List<Link> links = getLinks(Util.toList(Util.filter(Util.stream(ElementUtil.select(
				getElement(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), title), "tr")),
				x -> ElementUtil.childrenSize(x) >= 3)));
		//
		forEach(links, x -> {
			//
			final String u = Link.getUrl(x);
			//
			if (Util.containsKey(urlMap, u)) {
				//
				Link.setUrl(x, Util.toString(Util.get(urlMap, u)));
				//
			} // if
				//
		});
		//
		return links;
		//
	}

	@Nullable
	private static IValue0<List<Link>> toLinks(final Workbook wb, final String sheetName)
			throws IllegalAccessException {
		//
		final Sheet sheet = WorkbookUtil.getSheet(wb, sheetName);
		//
		if (iterator(sheet) != null) {
			//
			List<Link> links = null;
			//
			IntStringMap intStringMap = null;
			//
			FormulaEvaluator formulaEvaluator = null;
			//
			DataFormatter df = null;
			//
			for (final Row row : sheet) {
				//
				if (iterator(row) == null) {
					//
					continue;
					//
				} // if
					//
				if (intStringMap == null) {
					//
					intStringMap = toIntStringMap(row, formulaEvaluator = ObjectUtils.getIfNull(formulaEvaluator,
							() -> CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
					//
					continue;
					//
				} // if
					//
				Util.add(links = ObjectUtils.getIfNull(links, ArrayList::new),
						toLink(row, intStringMap, formulaEvaluator = ObjectUtils.getIfNull(formulaEvaluator,
								() -> CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))),
								df = ObjectUtils.getIfNull(df, DataFormatter::new)));
				//
			} // for
				//
			return Unit.with(links);
			//
		} // if
			//
		return null;
		//
	}

	private static Link toLink(final Iterable<Cell> cells, final IntStringMap intStringMap,
			final FormulaEvaluator formulaEvaluator, final DataFormatter df) throws IllegalAccessException {
		//
		if (iterator(cells) == null) {
			//
			return null;
			//
		} // if
			//
		IH ih = null;
		//
		Field[] fs = null;
		//
		List<Field> fields = null;
		//
		Field f = null;
		//
		int size = 0;
		//
		Class<?> type = null;
		//
		for (final Cell cell : cells) {
			//
			if (cell == null) {
				//
				continue;
				//
			} // if
				//
			if (fs == null) {
				//
				fs = FieldUtils.getAllFields(Util.getClass(ih = ObjectUtils.getIfNull(ih, IH::new)));
				//
			} // if
				//
			clear(fields = ObjectUtils.getIfNull(fields, ArrayList::new));
			//
			for (int i = 0; fs != null && i < fs.length; i++) {
				//
				if ((f = fs[i]) == null || !StringUtils.equalsIgnoreCase(Util.getName(f),
						IntStringMap.getString(intStringMap, cell.getColumnIndex()))) {
					//
					continue;
					//
				} // if
					//
				Util.add(fields, f);
				//
			} // for
				//
			if ((size = IterableUtils.size(fields)) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0),
					null)) != null) {
				//
				if (Objects.equals(type = f.getType(), String.class)) {
					//
					f.set(ih, getStringCellValue(cell, formulaEvaluator));
					//
				} else if (Objects.equals(type, Integer.class)) {
					//
					f.set(ih, valueOf(formatCellValue(df, cell)));
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return testAndApply(Objects::nonNull, ih, x -> Reflection.newProxy(Link.class, x), null);
		//
	}

	private static void clear(@Nullable final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	@Nullable
	private static IntStringMap toIntStringMap(final Iterable<Cell> cells, final FormulaEvaluator formulaEvaluator) {
		//
		if (iterator(cells) == null) {
			//
			return null;
			//
		} // if
			//
		IntStringMap intStringMap = null;
		//
		for (final Cell cell : cells) {
			//
			if (cell == null || (intStringMap = ObjectUtils.getIfNull(intStringMap,
					() -> Reflection.newProxy(IntStringMap.class, new IH()))) == null) {
				//
				continue;
				//
			} // if
				//
			intStringMap.setString(cell.getColumnIndex(), getStringCellValue(cell, formulaEvaluator));
			//
		} // for
			//
		return intStringMap;
		//
	}

	@Nullable
	private static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	@Nullable
	private static String formatCellValue(@Nullable final DataFormatter instance, final Cell cell) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(cell);
		//
		final String name = Util.getName(clz);
		//
		if (Objects.equals(name, "org.apache.poi.hssf.usermodel.HSSFCell")) {
			//
			if (org.apache.poi.ss.usermodel.CellUtil.getCellType(cell) == null) {
				//
				return null;
				//
			} // if
				//
		} else if (Objects.equals(name, "org.apache.poi.xssf.streaming.SXSSFCell")) {
			//
			final List<Field> fs = Util.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(clz)),
					f -> Objects.equals(Util.getName(f), "_value")));
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException(Integer.toString(size));
				//
			} // if
				//
			if (Narcissus.getField(cell,
					testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return null;
				//
			} // if
				//
		} else if (Objects.equals(name, "org.apache.poi.xssf.usermodel.XSSFCell")) {
			//
			final List<Field> fs = Util.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(clz)),
					f -> Objects.equals(Util.getName(f), "_cell")));
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException(Integer.toString(size));
				//
			} // if
				//
			if (Narcissus.getField(cell,
					testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return null;
				//
			} // if
				//
		} // if
			//
		return org.apache.poi.ss.usermodel.CellUtil.getCellType(cell) != null ? instance.formatCellValue(cell) : null;
		//
	}

	private static <T> void forEach(@Nullable final Iterable<T> instance, @Nullable final Consumer<? super T> action) {
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		}
	}

	@Nullable
	private static List<Link> getLinks(final List<Element> es) {
		//
		Element e = null;
		//
		String category = null;
		//
		int childrenSize = 0;
		//
		Element child = null;
		//
		List<Link> links = null;
		//
		Integer number = null;
		//
		int offset, index = 0;
		//
		boolean hasAttrRowSpan = false;
		//
		String imgSrc = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((childrenSize = ElementUtil.childrenSize(e)) > 0
					&& (hasAttrRowSpan = hasAttr(child = ElementUtil.child(e, 0), "rowspan"))) {
				//
				category = ElementUtil.text(child);
				//
			} // if
				//
				// number
				//
			imgSrc = null;
			//
			if (childrenSize > (index = (0 + (offset = iif(hasAttrRowSpan, 1, 0))))
					&& (number = valueOf(ElementUtil.text(child = ElementUtil.child(e, index)))) == null) {
				//
				imgSrc = NodeUtil.absUrl(getImg(child), "src");
				//
			} // if
				//
			Util.addAll(links = ObjectUtils.getIfNull(links, ArrayList::new),
					createLinks(childrenSize, offset, e,
							childrenSize > (index = 2 + offset) && (child = ElementUtil.child(e, index)) != null
									? ElementUtil.select(child, "a")
									: null,
							category, number, imgSrc));
			//
		} // for
			//
		return links;
		//
	}

	@Nullable
	private static Element getImg(final Element instance) {
		//
		final Collection<Element> imgs = ElementUtil.select(instance, "img");
		//
		return IterableUtils.size(imgs) == 1 ? IterableUtils.get(imgs, 0) : null;
		//
	}

	private static int iif(final boolean condition, final int trueValue, final int falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static boolean hasAttr(@Nullable final Element instance, @Nullable final String attributeKey) {
		return instance != null && attributeKey != null && instance.hasAttr(attributeKey);
	}

	@Nullable
	private static Collection<Link> createLinks(final int childrenSize, final int offset, final Element e,
			@Nullable final Collection<Element> as2, @Nullable final String category, @Nullable final Integer number,
			@Nullable final String imgSrc) {
		//
		Collection<Element> as1 = null;
		//
		int index = 0;
		//
		Element child = null;
		//
		Collection<Link> links = null;
		//
		IH ih = null;
		//
		Map<Object, Object> objects = null;
		//
		Map<Object, Integer> integers = null;
		//
		for (int j = 0; j < IterableUtils
				.size(as1 = childrenSize > (index = 1 + offset) && (child = ElementUtil.child(e, index)) != null
						? ElementUtil.select(child, "a")
						: null); j++) {
			//
			Util.put(objects = (ih = new IH()).getObjects(), String.class, category);
			//
			Util.put(objects, Integer.class, number);
			//
			Util.put(objects, IntMap.class, Reflection.newProxy(IntMap.class, ih));
			//
			Util.put(integers = ih.getIntegers(), "childrenSize", childrenSize);
			//
			Util.put(integers, "offset", offset);
			//
			addLinks(links = ObjectUtils.getIfNull(links, ArrayList::new), IterableUtils.get(as1, j), as2, e,
					Reflection.newProxy(ObjectMap.class, ih), imgSrc);
			//
		} // for
			//
		return links;
		//
	}

	private static interface ObjectMap {

		<T> T getObject(final Class<?> clz);

		@Nullable
		static <T> T getObject(@Nullable final ObjectMap instance, final Class<?> clz) {
			return instance != null ? instance.getObject(clz) : null;
		}

	}

	private static interface IntMap {

		int getInt(final String key, final int defaultValue);

		static int getInt(@Nullable final IntMap instance, final String key, final int defaultValue) {
			return instance != null ? instance.getInt(key, defaultValue) : defaultValue;
		}

	}

	private static void addLinks(final Collection<Link> links, final Element a1,
			@Nullable final Collection<Element> as2, final Element e, final ObjectMap objectMap,
			@Nullable final String imgSrc) {
		//
		final int size = IterableUtils.size(as2);
		//
		IH ih = null;
		//
		final String category = ObjectMap.getObject(objectMap, String.class);
		//
		final Integer number = ObjectMap.getObject(objectMap, Integer.class);
		//
		if (size > 0) {
			//
			Element a2 = null;
			//
			for (int k = 0; k < size; k++) {
				//
				if ((a2 = IterableUtils.get(as2, k)) == null) {
					//
					continue;
					//
				} // if
					//
				(ih = new IH()).category = category;
				//
				ih.number = number;
				//
				ih.imgSrc = imgSrc;
				//
				setDescriptionAndTextAndUrl(a1, ih, a2);
				//
				Util.add(links, Reflection.newProxy(Link.class, ih));
				//
			} // for
				//
		} else {
			//
			(ih = new IH()).category = category;
			//
			ih.number = number;
			//
			ih.imgSrc = imgSrc;
			//
			int index = 0;
			//
			Element child = null;
			//
			final IntMap intMap = ObjectMap.getObject(objectMap, IntMap.class);
			//
			if (IntMap.getInt(intMap, "childrenSize", 0) > (index = 2 + IntMap.getInt(intMap, "offset", 0))
					&& (child = ElementUtil.child(e, index)) != null) {
				//
				ih.description = ElementUtil.text(child);
				//
			} // if
				//
			ih.url = NodeUtil.absUrl(a1, "href");
			//
			ih.text = ElementUtil.text(a1);
			//
			Util.add(links, Reflection.newProxy(Link.class, ih));
			//
		} // if
			//
	}

	private static void setDescriptionAndTextAndUrl(final Element a1, @Nullable final IH ih, final Element a2) {
		//
		if (ih == null) {
			//
			return;
			//
		} // if
			//
		if (isAbsolute(apply(URI::new, NodeUtil.attr(a1, "href"), null))) {
			//
			ih.description = ElementUtil.text(a1);
			//
			ih.url = NodeUtil.absUrl(a1, "href");
			//
			ih.text = ElementUtil.text(a2);
			//
		} else {
			//
			ih.description = StringUtils.joinWith(" ", ElementUtil.text(a1), ElementUtil.text(a2));
			//
			ih.url = NodeUtil.absUrl(a2, "href");
			//
			ih.text = StringUtils.joinWith(" ", ElementUtil.text(a1), ElementUtil.text(a2));
			//
		} // if
			//
	}

	private static boolean isAbsolute(@Nullable final URI instance) {
		return instance != null && instance.isAbsolute();
	}

	@Nullable
	private static <T, R, E extends Throwable> R apply(@Nullable final FailableFunction<T, R, E> function,
			final T value, @Nullable final R defaultValue) {
		try {
			return function != null ? function.apply(value) : defaultValue;
		} catch (final Throwable e) {
			return defaultValue;
		}
	}

	@Nullable
	private static Integer valueOf(@Nullable final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Nullable
	private static Element getParentByNodeName(@Nullable final Element element, @Nullable final String nodeName) {
		//
		return Util.orElse(Util.findFirst(Util.filter(Util.stream(ElementUtil.parents(element)),
				x -> Objects.equals(nodeName, NodeUtil.nodeName(x)))), null);
		//
	}

	@Nullable
	private static Element getElement(final URL url, final String title) throws IOException {
		//
		final List<Element> bs = ElementUtil.select(testAndApply(Objects::nonNull, url, x -> Jsoup.parse(x, 0), null),
				"b");
		//
		final List<Element> es = Util.collect(Util.filter(Util.stream(bs),
				x -> StringUtils.equals(StringUtils.defaultIfBlank(title, "音訳の部屋読み方辞典"), trim(ElementUtil.text(x)))),
				Collectors.toList());
		//
		final int size = IterableUtils.size(es);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getParentByNodeName(size == 1 ? IterableUtils.get(es, 0) : null, "table");
		//
	}

	private static String trim(final String string) {
		//
		if (StringUtils.isEmpty(string)) {
			//
			return string;
			//
		} // if
			//
		final char[] cs = Util.toCharArray(string);
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if (Character.isWhitespace(c = cs[i])) {
				//
				continue;
				//
			} // if
				//
			append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
			//
		} // for
			//
		return StringUtils.defaultString(Util.toString(sb));
		//
	}

	private static void append(@Nullable final StringBuilder instance, final char c) {
		if (instance != null) {
			instance.append(c);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}