package org.jsoup.nodes;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;

public interface ElementUtil {

	static String text(final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, "childNodes", true) != null ? instance.text()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	static Elements getElementsByTag(final Element instance, final String tagName) {
		//
		try {
			//
			return instance != null && StringUtils.isNotEmpty(tagName)
					&& FieldUtils.readField(instance, "tag", true) != null ? instance.getElementsByTag(tagName) : null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	static Elements selectXpath(final Element instance, final String xpath) {
		return instance != null && xpath != null ? instance.selectXpath(xpath) : null;
	}

	static Elements select(final Element instance, final String cssQuery) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, "childNodes", true) != null
					? instance.select(cssQuery)
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	static Elements children(final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, "childNodes", true) != null ? instance.children()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	static String attr(final Element instance, final String attributeKey) {
		return instance != null && attributeKey != null ? instance.attr(attributeKey) : null;
	}

	static Element nextElementSibling(final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	static String tagName(final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, "tag", true) != null ? instance.tagName() : null;
			//
		} catch (IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

}