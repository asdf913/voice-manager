package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellUtil;
import org.odftoolkit.simple.table.Table;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class YojijukugoMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
			//
			final String message = ci != null ? ci.getMessage() : null;
			//
			IValue0<Multimap<String, String>> value = null;
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument", getMimeType(ci))
					|| Objects.equals("OLE 2 Compound Document", message) || XlsxUtil.isXlsx(resource)) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = WorkbookFactory.create(is)) {
					//
					if ((value = createMultimap(wb)) != null) {
						//
						return IValue0Util.getValue0(value);
						//
					} // if
						//
				} // try
					//
			} else if (Objects.equals("OpenDocument Spreadsheet", message)) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final SpreadsheetDocument ssd = SpreadsheetDocument.loadDocument(is)) {
					//
					if ((value = createMultimap(ssd)) != null) {
						//
						return IValue0Util.getValue0(value);
						//
					} // if
						//
				} // try
					//
			} // if
				//
		} // if
			//
		return createMultimapByUrl(url, ProtocolUtil.getAllowProtocols());
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(@Nullable final Workbook wb) {
		//
		final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
		//
		final AtomicBoolean first = new AtomicBoolean(true);
		//
		if (iterator(sheet) != null) {
			//
			IValue0<Multimap<String, String>> multimap = null;
			//
			for (final Row row : sheet) {
				//
				if (row == null || row.getPhysicalNumberOfCells() < 2 || first.getAndSet(false)) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedListMultimap.create()))),
						Util.toString(RowUtil.getCell(row, 0)), Util.toString(RowUtil.getCell(row, 1)));
				//
			} // if
				//
			return multimap;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(@Nullable final SpreadsheetDocument ssd) {
		//
		final Table table = ssd != null && ssd.getSheetCount() == 1 ? ssd.getSheetByIndex(0) : null;
		//
		final AtomicBoolean first = new AtomicBoolean(true);
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		org.odftoolkit.simple.table.Row row = null;
		//
		// text
		//
		String text = null;
		//
		// hiragana
		//
		String hiragana = null;
		//
		for (int i = 0; table != null && i < table.getRowCount(); i++) {
			//
			if ((row = table.getRowByIndex(i)) == null
					//
					|| StringUtils.isEmpty(text = CellUtil.getStringValue(row.getCellByIndex(0)))
					|| StringUtils.isEmpty(hiragana = CellUtil.getStringValue(row.getCellByIndex(1)))
					//
					|| first.getAndSet(false)) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(
					IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedListMultimap.create()))),
					text, hiragana);
			//
		} // for
			//
		return multimap;

		//
	}

	@Nullable
	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols)
			throws Exception {
		//
		final Elements tables = ElementUtil.getElementsByTag(testAndApply(
				x -> x != null && (allowProtocols == null || allowProtocols.length == 0
						|| StringUtils.equalsAnyIgnoreCase(getProtocol(x), allowProtocols)),
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "table");
		//
		Element tr, a = null;
		//
		Elements trs, as = null;
		//
		Node nextSibling = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(tables); i++) {
			//
			if ((trs = ElementUtil.getElementsByTag(IterableUtils.get(tables, i), "tr")) == null) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < IterableUtils.size(trs); j++) {
				//
				if ((tr = IterableUtils.get(trs, j)) == null || tr.childNodeSize() < 2
						|| (as = ElementUtil.getElementsByTag(tr, "a")) == null || as == null || as.isEmpty()
						|| (a = IterableUtils.get(as, 0)) == null) {
					//
					continue;
					//
				} // if
					//
					// hiragana
					//
				if ((nextSibling = a.nextSibling()) != null
						&& (pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("[ぁ-ん]+"))) != null
						&& Util.find(matcher = Util.matcher(pattern, nextSibling.outerHtml()))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create),
							ElementUtil.text(a), matcher.group());
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}