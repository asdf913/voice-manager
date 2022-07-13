package org.springframework.context.support;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface SpeechApi {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE_USE)
	@interface MinValue {
		int value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE_USE)
	@interface MaxValue {
		int value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE_USE)
	@interface Name {
		String value();
	}

	public void speak(final String text, final String voiceId, final int rate,
			@MinValue(0) @MaxValue(100) @Name("volume") final int volume);

	public void writeVoiceToFile(final String text, final String voiceId, final int rate,
			@MinValue(0) @MaxValue(100) @Name("volume") final int volume, final File file);

	public String[] getVoiceIds();

}