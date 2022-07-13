package org.springframework.context.support;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Library;

public class SpechApiImpl implements SpechApi {

	private interface Jna extends Library {

		Jna INSTANCE = (Jna) com.sun.jna.Native.load("MicrosoftSpeechApi.dll", Jna.class);

		public void speak(final int[] text, final int length, final String voiceId, final int rate,
				@MinValue(0) @MaxValue(100) final int volume);

		public void writeVoiceToFile(final int[] text, final int textLength, final String voiceId, final int rate,
				@MinValue(0) @MaxValue(100) final int volume, final int[] fileName, final int fileNameLength);

		public String getVoiceIds(final String requiredAttributes, final String optionalAttributes);

	}

	@Override
	public void speak(final String text, final String voiceId, final int rate, final int volume) {
		//
		final int[] ints = toIntArray(text);
		//
		Jna.INSTANCE.speak(ints, length(ints), voiceId, 0, volume);
		//
	}

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

	private static int length(final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Override
	public void writeVoiceToFile(final String text, final String voiceId, final int rate, final int volume,
			final File file) {
		//
		int[] ints = toIntArray(text);
		//
		Jna.INSTANCE.writeVoiceToFile(ints, length(ints), voiceId, 0, volume,
				ints = toIntArray(file != null ? file.getAbsolutePath() : null), length(ints));
		//
	}

	@Override
	public String[] getVoiceIds() {
		//
		return StringUtils.split(Jna.INSTANCE.getVoiceIds("", ""), ',');
		//
	}

}