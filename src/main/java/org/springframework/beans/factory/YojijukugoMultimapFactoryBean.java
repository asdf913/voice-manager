package org.springframework.beans.factory;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class YojijukugoMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final String[] allowProtocols = ProtocolUtil.getAllowProtocols();
		//
		final URL u = url != null ? new URL(url) : null;
		//
		final Elements tables = ElementUtil.getElementsByTag(
				u != null && (allowProtocols == null || allowProtocols.length == 0
						|| StringUtils.equalsAnyIgnoreCase(u.getProtocol(), allowProtocols)) ? Jsoup.parse(u, 0) : null,
				"table");
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
						|| (a = as.get(0)) == null) {
					//
					continue;
					//
				} // if
					//
				if (pattern == null) {
					//
					pattern = Pattern.compile("[ぁ-ん]+");
				} // if
					//
					// hiragana
					//
				if ((nextSibling = a.nextSibling()) != null && pattern != null
						&& (matcher = pattern.matcher(nextSibling.outerHtml())) != null && matcher.find()) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create), a.text(),
							matcher.group());
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

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}