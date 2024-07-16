package org.springframework.beans.factory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutablePairUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

public class RinkaiSenKanjRomajiMapFactoryBean extends StringMapFromResourceFactoryBean {

	@URL("https://www.twr.co.jp/route/tabid/105/Default.aspx")
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
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null),
				".stationnav a");
		//
		Map<String, String> map = null;
		//
		Entry<String, String> entry = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((entry = createEntry(NodeUtil.absUrl(es.get(i), "href"))) == null
					|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
				//
				continue;
				//
			} // if
				//
			Util.put(map, Util.getKey(entry), Util.getValue(entry));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Entry<String, String> createEntry(final String url) throws Exception {
		//
		return createEntry(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null),
				"[class^=\"ttl\"]"));
		//
	}

	@Nullable
	private static Entry<String, String> createEntry(final List<Element> es) {
		//
		MutablePair<String, String> pair = null;
		//
		int size = IterableUtils.size(es);
		//
		if (size > 0) {
			//
			MutablePairUtil.setLeft(pair = ObjectUtils.getIfNull(pair, MutablePair::new),
					ElementUtil.text(IterableUtils.get(es, 0)));
			//
		} // if
			//
		if (size > 1) {
			//
			MutablePairUtil.setRight(pair = ObjectUtils.getIfNull(pair, MutablePair::new),
					ElementUtil.text(IterableUtils.get(es, 1)));
			//
		} // if
			//
		return pair;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}