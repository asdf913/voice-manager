package org.jsoup.nodes;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;

public final class ElementUtil {

	private static final String CHILD_NODES = "childNodes";

	private ElementUtil() {
	}

	public static String text(final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.text()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	@Nullable
	public static Elements getElementsByTag(final Element instance, final String tagName) {
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

	public static Elements selectXpath(final Element instance, final String xpath) {
		return instance != null && xpath != null ? instance.selectXpath(xpath) : null;
	}

	@Nullable
	public static Elements select(final Element instance, final String cssQuery) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null
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

	@Nullable
	public static Elements children(final Element instance) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.children()
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

	public static Element nextElementSibling(final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	public static String tagName(final Element instance) {
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

	public static Element child(final Element instance, final int index) {
		//
		try {
			//
			return instance != null && FieldUtils.readField(instance, CHILD_NODES, true) != null ? instance.child(index)
					: null;
			//
		} catch (final IllegalAccessException e) {
			//
			return null;
			//
		} // try
			//
	}

}