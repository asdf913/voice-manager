package org.springframework.core.convert.converter;

import java.awt.Component;

import javax.annotation.Nullable;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.commons.lang3.StringUtils;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.context.support.SpeechApi;

public class VoiceIdListCellRendererConverter implements Converter<ListCellRenderer<Object>, ListCellRenderer<Object>> {

	private SpeechApi speechApi = null;

	public void setSpeechApi(final SpeechApi speechApi) {
		this.speechApi = speechApi;
	}

	@Override
	public ListCellRenderer<Object> convert(final ListCellRenderer<Object> lcr) {
		//
		return new ListCellRenderer<>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value,
					final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				final String s = toString(value);
				//
				try {
					//
					final String name = SpeechApi.getVoiceAttribute(speechApi, s, "Name");
					//
					if (StringUtils.isNotBlank(name)) {
						//
						return getListCellRendererComponent(lcr, list, name, index, isSelected, cellHasFocus);
						//
					} // if
						//
				} catch (final Error e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
				final String commonPrefix = String.join("", StringUtils.substringBeforeLast(
						StringUtils.getCommonPrefix(SpeechApi.getVoiceIds(speechApi)), "\\"), "\\");
				//
				return getListCellRendererComponent(lcr, list,
						StringUtils.startsWith(s, commonPrefix) ? StringUtils.substringAfter(s, commonPrefix) : value,
						index, isSelected, cellHasFocus);
				//
			}

			@Nullable
			private static String toString(@Nullable final Object instance) {
				return instance != null ? instance.toString() : null;
			}

			@Nullable
			private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
					final JList<? extends E> list, final E value, final int index, final boolean isSelected,
					final boolean cellHasFocus) {
				//
				return instance != null
						? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
						: null;
				//
			}

		};

	}

}