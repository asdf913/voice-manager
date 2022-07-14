package org.springframework.context.support;

import java.io.File;

public interface SpeechApi {

	public void speak(final String text, final String voiceId, final int rate, final int volume);

	public void writeVoiceToFile(final String text, final String voiceId, final int rate, final int volume,
			final File file);

	public String[] getVoiceIds();

}