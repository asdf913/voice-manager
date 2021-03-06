package org.springframework.context.support;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Library;
import com.sun.jna.Native;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MinValue {

	String name();

	int value();

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MaxValue {

	String name();

	int value();

}

@MinValue(name = "volume", value = 0)
@MaxValue(name = "volume", value = 100)
public class SpeechApiImpl implements SpeechApi, Provider {

	private interface Jna extends Library {

		Jna INSTANCE = cast(Jna.class, Native.load("MicrosoftSpeechApi.dll", Jna.class));

		private static <T> T cast(final Class<T> clz, final Object value) {
			return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
		}

		public void speak(final int[] text, final int length, final String voiceId, final int rate, final int volume);

		public void writeVoiceToFile(final int[] text, final int textLength, final String voiceId, final int rate,
				final int volume, final int[] fileName, final int fileNameLength);

		public String getVoiceIds(final String requiredAttributes, final String optionalAttributes);

		public String getVoiceAttribute(final String voiceId, final String attribute);

		public String getProviderName();

		public String getProviderVersion();

		public String getProviderPlatform();

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
	public String getVoiceAttribute(final String voiceId, final String attribute) {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceAttribute(voiceId, attribute) : null;
		//
	}

	@Override
	public String getProviderName() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderName() : null;
		//
	}

	@Override
	public String getProviderVersion() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderVersion() : null;
		//
	}

	@Override
	public String getProviderPlatform() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderPlatform() : null;
		//
	}

}