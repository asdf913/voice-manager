package org.springframework.context.support;

import java.io.File;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class SpeechApiSpeechServerImpl implements SpeechApi, Provider, Lookup, InitializingBean {

	private interface Jna extends Library {

		Jna INSTANCE = cast(Jna.class, Native.load("MicrosoftSpeechApi.dll", Jna.class));

		@Nullable
		private static <T> T cast(final Class<T> clz, final Object value) {
			return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
		}

		public boolean isInstalled();

		public void speak(@Nullable final int[] text, final int length, final String voiceId, final int rate,
				final int volume);

		public void writeVoiceToFile(@Nullable final int[] text, final int textLength, final String voiceId,
				final int rate, final int volume, final int[] fileName, final int fileNameLength);

		public String getVoiceIds(final String requiredAttributes, final String optionalAttributes);

		public String getVoiceAttribute(final String voiceId, final String attribute);

		public String getProviderName();

		public String getProviderVersion();

		public String getProviderPlatform();

	}

	private Table<Object, Object, Object> table = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		final Table<Object, Object, Object> t = getTable();
		//
		TableUtil.put(t, "volume", "min", 0);
		//
		TableUtil.put(t, "volume", "max", 100);
		//
	}

	private Table<Object, Object, Object> getTable() {
		//
		return table = ObjectUtils.getIfNull(table, HashBasedTable::create);
		//
	}

	@Override
	public boolean isInstalled() {
		//
		return Jna.INSTANCE != null && Jna.INSTANCE.isInstalled();
		//
	}

	@Override
	public void speak(final String text, final String voiceId, final int rate, final int volume) {
		//
		if (Jna.INSTANCE != null) {
			//
			final int[] ints = toIntArray(text);
			//
			Jna.INSTANCE.speak(ints, length(ints), voiceId, rate, volume);
			//
		} // if
			//
	}

	@Nullable
	private static int[] toIntArray(final String text) {
		//
		final char[] cs = text != null ? text.toCharArray() : null;
		//
		final int[] ints = cs != null ? new int[cs.length] : null;
		//
		for (int i = 0; cs != null && ints != null && i < cs.length; i++) {
			//
			ints[i] = cs[i];
			//
		} // for
			//
		return ints;
		//
	}

	private static int length(@Nullable final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Override
	public void writeVoiceToFile(final String text, final String voiceId, final int rate, final int volume,
			final File file) {
		//
		if (Jna.INSTANCE != null) {
			//
			int[] ints = toIntArray(text);
			//
			Jna.INSTANCE.writeVoiceToFile(ints, length(ints), voiceId, 0, volume,
					ints = toIntArray(file != null ? file.getAbsolutePath() : null), length(ints));
			//
		} // if
			//
	}

	@Override
	public String[] getVoiceIds() {
		//
		return StringUtils.split(Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceIds("", "") : null, ',');
		//
	}

	@Override
	@Nullable
	public String getVoiceAttribute(final String voiceId, final String attribute) {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceAttribute(voiceId, attribute) : null;
		//
	}

	@Override
	@Nullable
	public String getProviderName() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderName() : null;
		//
	}

	@Override
	@Nullable
	public String getProviderVersion() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderVersion() : null;
		//
	}

	@Override
	@Nullable
	public String getProviderPlatform() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderPlatform() : null;
		//
	}

	@Override
	public boolean contains(final Object row, final Object column) {
		//
		return TableUtil.contains(getTable(), row, column);
		//
	}

	@Override
	public Object get(final Object row, final Object column) {
		//
		final Table<?, ?, ?> t = getTable();
		//
		if (t != null && !TableUtil.contains(t, row, column)) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return TableUtil.get(t, row, column);
		//
	}

}