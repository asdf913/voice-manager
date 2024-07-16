package org.springframework.beans.factory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

public class CentralJapanRailwayKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	@URL("https://railway.jr-central.co.jp/common/_api/time-schedule/list_station.json")
	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		final List<?> list = Util.cast(List.class,
				Util.get(ObjectMapperUtil.readValue(new ObjectMapper(),
						Util.openStream(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null)),
						Object.class) instanceof Map<?, ?> m ? m : null, "station"));
		//
		Map<String, String> map = null;
		//
		Map<?, ?> m = null;
		//
		for (int i = 0; list != null && i < list.size(); i++) {
			//
			if ((m = list.get(i) instanceof Map<?, ?> t ? t : null) == null
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			Util.put(map, Util.toString(Util.get(m, "name")), Util.toString(Util.get(m, "kana")));
			//
		} // for
			//
		return map;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}