package org.springframework.context.support;

import java.io.File;

import javax.annotation.Nullable;

public interface SpeechApi {

	public boolean isInstalled();

	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate, final int volume);

	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			final int volume, @Nullable final File file);

	@Nullable
	public String[] getVoiceIds();

	@Nullable
	public String getVoiceAttribute(@Nullable final String voiceId, final String attribute);

}