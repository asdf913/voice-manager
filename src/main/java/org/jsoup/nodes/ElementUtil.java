package org.jsoup.nodes;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;

public final class ElementUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ElementUtil.class);

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

	public static int childrenSize(final Element instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		try {
			//
			final Field field = clz != null ? clz.getDeclaredField(CHILD_NODES) : null;
			//
			if (Narcissus.getField(instance, field) == null) {
				//
				return 0;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.childrenSize();
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	public static List<Element> parents(final Element instance) {
		return instance != null ? instance.parents() : null;
	}

}