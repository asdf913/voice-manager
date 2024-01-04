package org.springframework.beans.factory;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class IpaMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private static Logger LOG = LoggerFactory.getLogger(IpaMultimapFactoryBean.class);

	@Nullable
	private Unit<Multimap<String, String>> multimap = null;

	private ObjectMapper objectMapper = null;

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
		Multimap<String, String> mm = IValue0Util.getValue0(this.multimap);
		//
		final ObjectMapper om = getObjectMapper();
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (mm == null) {
			//
			try {
				//
				mm = IValue0Util.getValue0(this.multimap = getMultimapUnitFromJson(om,
						testAndApply(ResourceUtil::exists, resource, InputStreamSourceUtil::getInputStream, null)));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
		if (mm == null) {
			//
			try (final InputStream is = Util
					.openStream(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null))) {
				//
				mm = IValue0Util.getValue0(this.multimap = getMultimapUnitFromJson(om,
						Util.openStream(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null))));
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
		} // if
			//
		return mm;
		//
	}

	@Nullable
	private static Unit<Multimap<String, String>> getMultimapUnitFromJson(final ObjectMapper objectMapper,
			@Nullable final InputStream is) throws IOException {
		//
		final Object obj = is != null ? ObjectMapperUtil.readValue(objectMapper, is, Object.class) : null;
		//
		if (is == null) {
			//
			return null;
			//
		} else if (obj == null) {
			//
			return Unit.with(null);
			//
		} else if (obj instanceof Map map) {
			//
			final Set<Entry<String, String>> entrySet = Util.entrySet(map);
			//
			Multimap<String, String> multimap = null;
			//
			if (Util.iterator(entrySet) != null) {
				//
				List<String> list = null;
				//
				for (final Entry<?, ?> en : entrySet) {
					//
					list = testAndApply(Objects::nonNull, StringUtils.split(Util.toString(Util.getValue(en)), ","),
							Arrays::asList, null);
					//
					for (int i = 0; list != null && i < list.size(); i++) {
						//
						list.set(i, StringUtils.trim(list.get(i)));
						//
					} // for
						//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.toString(Util.getKey(en)), list);
					//
				} // for
					//
			} // if
				//
			return Unit.with(multimap);
			//
		} // if
			//
		throw new IllegalArgumentException(Util.toString(Util.getClass(obj)));
		//
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}