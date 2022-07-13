package org.springframework.context.support;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface SpeechApi {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE_USE)
	public @interface MinValue {
		int value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE_USE)
	public @interface MaxValue {
		int value();
	}

	public void speak(final String text, final String voiceId, final int rate,
			@MinValue(0) @MaxValue(100) final int volume);

	public void writeVoiceToFile(final String text, final String voiceId, final int rate,
			@MinValue(0) @MaxValue(100) final int volume, final File file);

	public String[] getVoiceIds();

}