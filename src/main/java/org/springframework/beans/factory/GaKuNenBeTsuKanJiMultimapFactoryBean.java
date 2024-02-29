package org.springframework.beans.factory;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.SpreadsheetDocumentUtil;
import org.odftoolkit.simple.table.CellUtil;
import org.odftoolkit.simple.table.Table;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceContentInfoUtil;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsUtil;
import org.springframework.core.io.XlsxUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;

public class GaKuNenBeTsuKanJiMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private Resource resource = null;

	private String url = null;

	private Duration timeout = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTimeout(@Nullable final Object timeout) {
		//
		Unit<Duration> value = null;
		//
		if (timeout == null) {
			//
			value = Unit.with(null);
			//
		} else if (timeout instanceof Duration duration) {
			//
			value = Unit.with(duration);
			//
		} else if (timeout instanceof Number number) {
			//
			value = Unit.with(Duration.ofMillis(Util.longValue(number, 0)));
			//
		} // if
			//
		if (value != null) {
			//
			this.timeout = IValue0Util.getValue0(value);
			//
			return;
			//
		} // if
			//
		final String string = Util.toString(timeout);
		//
		final Long l = valueOf(string);
		//
		if (l != null) {
			//
			setTimeout(l);
			//
		} else if (StringUtils.isNotBlank(string)) {
			//
			setTimeout(Duration.parse(string));
			//
		} // if
			//
	}

	@Nullable
	private static Long valueOf(@Nullable final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Long.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			Unit<Multimap<String, String>> mm = null;
			//
			final ContentInfo ci = ResourceContentInfoUtil.getContentInfo(resource);
			//
			final String mimeType = Util.getMimeType(ci);
			//
			if (Util.or(Objects.equals("application/vnd.openxmlformats-officedocument", mimeType),
					Boolean.logicalAnd(Objects.equals("application/zip", mimeType), XlsxUtil.isXlsx(resource)),
					XlsUtil.isXls(resource))) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
					//
					mm = createMulitmapUnit(WorkbookFactory.create(is));
					//
				} // try
					//
			} else if (Objects.equals("OpenDocument Spreadsheet", Util.getMessage(ci))) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
					//
					mm = createMulitmapUnit(SpreadsheetDocument.loadDocument(is));
					//
				} // try
					//
			} // if
				//
			if (mm != null) {
				//
				return IValue0Util.getValue0(mm);
				//
			} // if
				//
		} // if
			//
		return createMultimapByUrl(url, timeout);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimapByUrl(final String url, final Duration timeout)
			throws Exception {
		//
		final Elements elements = ElementUtil
				.selectXpath(
						testAndApply(
								x -> StringUtils.equalsAnyIgnoreCase(Util.getProtocol(x),
										ProtocolUtil.getAllowProtocols()),
								testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null),
								x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null),
						"//span[@class='mw-headline'][starts-with(.,'第')]");
		//
		Element element = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(elements); i++) {
			//
			if ((element = IterableUtils.get(elements, i)) == null || !Util.matches(matcher = Util.matcher(
					pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("(第(\\d+)学年)（\\d+字）")),
					ElementUtil.text(element))) || Util.groupCount(matcher) <= 0) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create),
					Util.group(matcher, 1),
					Util.toList(Util.map(
							Util.stream(ElementUtil.select(ElementUtil.nextElementSibling(element.parent()), "a")),
							ElementUtil::text)));
			//
		} // for
			//
		return multimap;
		//
	}

	private static int getSheetCount(@Nullable final SpreadsheetDocument instance) {
		return instance != null ? instance.getSheetCount() : 0;
	}

	private static int getRowCount(@Nullable final Table instance) {
		return instance != null ? instance.getRowCount() : 0;
	}

	@Nullable
	private static Unit<Multimap<String, String>> createMulitmapUnit(@Nullable final Workbook wb) {
		//
		Unit<Multimap<String, String>> mm = null;
		//
		final Sheet sheet = wb != null && wb.getNumberOfSheets() > 0 ? WorkbookUtil.getSheetAt(wb, 0) : null;
		//
		if (Util.iterator(sheet) != null) {
			//
			int rowNum = 0;
			//
			for (final Row row : sheet) {
				//
				if (row == null || rowNum++ == 0 || row.getLastCellNum() < 2) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(
						IValue0Util.getValue0(
								mm = ObjectUtils.getIfNull(mm, () -> Unit.with(LinkedHashMultimap.create()))),
						Util.toString(RowUtil.getCell(row, 0)), Util.toString(RowUtil.getCell(row, 1)));
				//
			} // for
				//
		} // if
			//
		return mm;
		//
	}

	@Nullable
	private static Unit<Multimap<String, String>> createMulitmapUnit(final SpreadsheetDocument ssd) {
		//
		Unit<Multimap<String, String>> mm = null;
		//
		final Table table = testAndApply(x -> getSheetCount(x) > 0, ssd,
				x -> SpreadsheetDocumentUtil.getSheetByIndex(x, 0), null);
		//
		int rowNum = 0;
		//
		org.odftoolkit.simple.table.Row row = null;
		//
		final int rowCount = getRowCount(table);
		//
		for (int i = 0; i < rowCount; i++) {
			//
			if ((row = table.getRowByIndex(i)) == null || rowNum++ < 1) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(
					IValue0Util.getValue0(mm = ObjectUtils.getIfNull(mm, () -> Unit.with(LinkedHashMultimap.create()))),
					CellUtil.getStringValue(row.getCellByIndex(0)), CellUtil.getStringValue(row.getCellByIndex(1)));
			//
		} // for
			//
		return mm;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}