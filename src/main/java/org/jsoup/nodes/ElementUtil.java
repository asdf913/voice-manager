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

	@Nullable
	public static String text(@Nullable final Element instance) {
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
	public static Elements getElementsByTag(@Nullable final Element instance, final String tagName) {
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

	@Nullable
	public static Elements selectXpath(@Nullable final Element instance, @Nullable final String xpath) {
		return instance != null && xpath != null ? instance.selectXpath(xpath) : null;
	}

	@Nullable
	public static Elements select(@Nullable final Element instance, final String cssQuery) {
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
	public static Elements children(@Nullable final Element instance) {
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

	@Nullable
	public static Element nextElementSibling(@Nullable final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	@Nullable
	public static String tagName(@Nullable final Element instance) {
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

	@Nullable
	public static Element child(@Nullable final Element instance, final int index) {
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

	public static int childrenSize(@Nullable final Element instance) {
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

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	public static List<Element> parents(@Nullable final Element instance) {
		return instance != null ? instance.parents() : null;
	}

	@Nullable
	public static final Element parent(@Nullable final Element instnace) {
		return instnace != null ? instnace.parent() : null;
	}

	@Nullable
	public static Elements nextElementSiblings(@Nullable final Element instnace) {
		return instnace != null ? instnace.nextElementSiblings() : null;
	}

}