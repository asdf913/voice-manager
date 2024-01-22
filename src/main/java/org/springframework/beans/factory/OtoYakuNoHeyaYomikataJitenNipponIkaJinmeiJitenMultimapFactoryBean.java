package org.springframework.beans.factory;

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.XlsUtil;
import org.springframework.core.io.XlsxUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

/*
 * https://hiramatu-hifuka.com/onyak/mw3.html
 */
public class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private static final Logger LOG = LoggerFactory
			.getLogger(OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.class);

	private String url = null;

	private Iterable<Link> links = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private IValue0<String> text = null;

	@Note("description")
	private IValue0<String> description = null;

	private IValue0<String> sheetName = null;

	@Nullable
	private Multimap<String, String> toBeRemoved = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	public void setDescription(final String description) {
		this.description = Unit.with(description);
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setSheetName(final String sheetName) {
		this.sheetName = Unit.with(sheetName);
	}

	public void setToBeRemoved(final String string) {
		//
		Object object = null;
		//
		try {
			//
			object = testAndApply(StringUtils::isNotBlank, string, x -> new ObjectMapper().readValue(x, Object.class),
					null);
			//
		} catch (final JsonProcessingException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		if (object instanceof Map m) {
			//
			toBeRemoved = toMultimap(m);
			//
		} else if (object instanceof Iterable iterable) {
			//
			toBeRemoved = toMultimap(iterable);
			//
		} else if (object != null) {
			//
			throw new IllegalStateException(Util.toString(Util.getClass(object)));
			//
		} // if
			//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<?> iterable) {
		//
		if (Util.iterator(iterable) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		for (final Object obj : iterable) {
			//
			if (obj instanceof Map m) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(m));
				//
			} else if (obj != null) {
				//
				throw new IllegalStateException(Util.toString(Util.getClass(obj)));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Map<?, ?> m) {
		//
		if (Util.iterator(Util.entrySet(m)) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Object value = null;
		//
		for (final Entry<?, ?> entry : Util.entrySet(m)) {
			//
			if (entry == null) {
				//
				continue;
				//
			} // if
				//
			if ((value = Util.getValue(entry)) instanceof Iterable iterable) {
				//
				for (final Object o : iterable) {
					//
					if (Boolean.logicalOr(o instanceof Iterable, o instanceof Map)) {
						//
						throw new IllegalStateException(Util.toString(Util.getClass(o)));
						//
					} // if
						//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.toString(Util.getKey(entry)), Util.toString(o));
					//
				} // if
					//
			} else if (value instanceof Map) {
				//
				throw new IllegalStateException(Util.toString(Util.getClass(value)));
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.toString(Util.getKey(entry)), Util.toString(Util.getValue(entry)));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> multimap = toMultimap(resource, sheetName);
		//
		if (multimap != null) {
			//
			return IValue0Util.getValue0(multimap);
			//
		} // if
			//
		List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)), toBeRemoved);
			//
		} // if
			//
		if ((size = IterableUtils.size(ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> description != null && x != null
						&& Objects.equals(x.getDescription(), IValue0Util.getValue0(description)))))) > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)), toBeRemoved);
			//
		} // if
			//
		return createMultimap(url, toBeRemoved);
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap(final InputStreamSource resource,
			@Nullable final IValue0<String> sheetName) throws Exception {
		//
		if (XlsxUtil.isXlsx(resource) || XlsUtil.isXls(resource)) {
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
					final Workbook wb = WorkbookFactory.create(is)) {
				//
				final int numberOfSheets = wb != null ? wb.getNumberOfSheets() : 0;
				//
				if (numberOfSheets == 1) {
					//
					return Unit.with(toMultimap(WorkbookUtil.getSheetAt(wb, 0),
							CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
					//
				} else if (numberOfSheets > 1) {
					//
					if (sheetName == null) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					return Unit.with(toMultimap(WorkbookUtil.getSheet(wb, IValue0Util.getValue0(sheetName)),
							CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // try
				//
		} // if
			//
		return toMultimap2(resource, sheetName);
		//
	}

	private static IValue0<Multimap<String, String>> toMultimap2(final InputStreamSource resource,
			final IValue0<String> sheetName) throws Exception {
		//
		ContentInfo ci = null;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
			//
			ci = testAndApply(Objects::nonNull, is, x -> new ContentInfoUtil().findMatch(x), null);
			//
		} // try
			//
		if (Objects.equals(Util.getMessage(ci), "OpenDocument Spreadsheet")) {
			//
			SpreadsheetDocument ssd = null;
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				ssd = SpreadsheetDocument.loadDocument(is);
				//
			} // try
				//
			final int sheetCount = ssd != null ? ssd.getSheetCount() : 0;
			//
			if (sheetCount == 1) {
				//
				return Unit.with(toMultimap(ssd.getSheetByIndex(0)));
				//
			} else if (sheetCount > 1) {
				//
				if (sheetName == null) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				return Unit.with(toMultimap(getSheetByName(ssd, IValue0Util.getValue0(sheetName))));
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Table getSheetByName(@Nullable final SpreadsheetDocument instance, final String name) {
		return instance != null ? instance.getSheetByName(name) : null;
	}

	@Nullable
	private static Multimap<String, String> toMultimap(@Nullable final Table table) {
		//
		Multimap<String, String> multimap = null;
		//
		final Iterator<org.odftoolkit.simple.table.Row> rows = table != null ? table.getRowIterator() : null;
		//
		org.odftoolkit.simple.table.Row row = null;
		//
		while (rows != null && table.getOdfElement() != null && rows.hasNext()) {
			//
			if ((row = rows.next()) == null || row.getCellCount() < 2) {
				//
				continue;
				//
			} // if
				//
			if (multimap == null) {
				//
				multimap = LinkedHashMultimap.create();
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap, org.odftoolkit.simple.table.CellUtil.getStringValue(row.getCellByIndex(0)),
					org.odftoolkit.simple.table.CellUtil.getStringValue(row.getCellByIndex(1)));
			//
		} // while
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Sheet sheet, final FormulaEvaluator formulaEvaluator) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(sheet) != null) {
			//
			for (final Row row : sheet) {
				//
				if (Util.iterator(row) == null || IterableUtils.size(row) < 2) {
					//
					continue;
					//
				} // if
					//
				if (multimap == null) {
					//
					multimap = LinkedHashMultimap.create();
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap, CellUtil.getStringCellValue(row.getCell(0), formulaEvaluator),
						CellUtil.getStringCellValue(row.getCell(1), formulaEvaluator));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(@Nullable final String url,
			final Multimap<String, String> toBeRemoved) throws Exception {
		//
		return createMultimap(ElementUtil.select(testAndApply(
				Objects::nonNull, testAndApply(Util::isAbsolute,
						testAndApply(StringUtils::isNotBlank, url, URI::new, null), x -> toURL(x), null),
				x -> Jsoup.parse(x, 0), null), "tbody"), toBeRemoved);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Iterable<Element> es,
			final Multimap<String, String> toBeRemoved) {
		//
		if (Util.iterator(es) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		List<Element> children = null;
		//
		Element element = null;
		//
		for (final Element e : es) {
			//
			if (ElementUtil.childrenSize(e) < 1 || !Util.matches(Util.matcher(
					pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}{1,2}行")),
					ElementUtil.text(IterableUtils.get(children = ElementUtil.children(e), 0))))) {
				//
				continue;
				//
			} // if
				//
			for (int i = 0; i < IterableUtils.size(children); i++) {
				//
				if (Util.matches(pattern.matcher(ElementUtil.text(element = IterableUtils.get(children, i))))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						createMultimap2(element.children()));
				//
			} // for
				//
		} // for
			//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(toBeRemoved);
		//
		if (Util.iterator(entries) != null) {
			//
			for (final Entry<String, String> entry : entries) {
				//
				remove(multimap, Util.getKey(entry), Util.getValue(entry));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	private static void remove(@Nullable final Multimap<?, ?> instance, final Object key, final Object value) {
		if (instance != null) {
			instance.remove(key, value);
		}
	}

	@Nullable
	private static Multimap<String, String> createMultimap2(final Iterable<Element> es) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(es) != null) {
			//
			Pattern pattern = null;
			//
			String key = null;
			//
			Matcher matcher = null;
			//
			for (final Element e : es) {
				//
				if (key == null) {
					//
					key = StringUtils.deleteWhitespace(ElementUtil.text(e));
					//
				} else {
					//
					matcher = Util.matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}+")),
							StringUtils.deleteWhitespace(ElementUtil.text(e)));
					//
					while (Util.find(matcher)) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key,
								matcher.group());
						//
					} // while
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		//
		if (instance == null || !Util.isAbsolute(instance)) {
			//
			return null;
			//
		} // if
			//
		return instance.toURL();
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}