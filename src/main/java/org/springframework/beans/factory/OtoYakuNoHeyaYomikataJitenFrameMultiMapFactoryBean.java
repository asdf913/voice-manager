package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

interface Frame {

	String getSrc();

	String getName();

}

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean implements FactoryBean<Multimap<String, Frame>> {

	private Multimap<String, String> links = null;

	private Resource resource = null;

	public void setLinks(final Multimap<String, String> links) {
		this.links = links;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	private static class IH implements InvocationHandler {

		@Note("name")
		private String name = null;

		@Note("src")
		private String src = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Frame) {
				//
				if (Objects.equals(methodName, "getName")) {
					//
					return name;
					//
				} else if (Objects.equals(methodName, "getSrc")) {
					//
					return src;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Override
	public Multimap<String, Frame> getObject() throws Exception {
		//
		Multimap<String, Frame> multimap = null;
		//
		if (ResourceUtil.exists(resource) && ResourceUtil.isFile(resource) && ResourceUtil.isReadable(resource)) {
			//
			try (final InputStream is = testAndApply(Objects::nonNull, ResourceUtil.getContentAsByteArray(resource),
					ByteArrayInputStream::new, null);
					final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
				//
				final Sheet sheet = wb != null && wb.getNumberOfSheets() > 0 ? WorkbookUtil.getSheetAt(wb, 0) : null;
				//
				Row row = null;
				//
				IH ih = null;
				//
				for (int i = 1; sheet != null && i < sheet.getPhysicalNumberOfRows()
						&& (row = sheet.getRow(i)) != null; i++) {
					//
					(ih = new IH()).name = CellUtil.getStringCellValue(row.getCell(1));
					//
					ih.src = CellUtil.getStringCellValue(row.getCell(2));
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, ArrayListMultimap::create),
							CellUtil.getStringCellValue(row.getCell(0)), Reflection.newProxy(Frame.class, ih));
					//
				} // for
					//
			} // try
				//
		} else {
			//
			final Set<String> ls = testAndApply(Objects::nonNull, MultimapUtil.values(links), LinkedHashSet::new, null);
			//
			if (ls != null) {
				//
				for (final String l : ls) {
					//
					MultimapUtil
							.putAll(multimap = ObjectUtils.getIfNull(multimap, ArrayListMultimap::create),
									Util.collect(
											Util.stream(ElementUtil.select(testAndApply(Objects::nonNull,
													testAndApply(StringUtils::isNotBlank, l, x -> new URI(x).toURL(),
															null),
													x -> Jsoup.parse(x, 0), null), "frame")),
											ArrayListMultimap::create, (m, v) -> MultimapUtil.put(m, l, createFrame(v)),
											Multimap::putAll));
					//
				} // for
					//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	private static Frame createFrame(final Node node) {
		//
		final IH ih = new IH();
		//
		ih.src = NodeUtil.absUrl(node, "src");
		//
		ih.name = NodeUtil.attr(node, "name");
		//
		return Reflection.newProxy(Frame.class, ih);
		//
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}