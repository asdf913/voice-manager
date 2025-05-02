package org.springframework.context.support;

import java.io.File;
import java.util.Map;

import javax.annotation.Nullable;

public interface SpeechApi {

	public boolean isInstalled();

	static boolean isInstalled(@Nullable final SpeechApi instance) {
		return instance != null && instance.isInstalled();
	}

	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate, final int volume,
			final Map<String, Object> map);

	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			final int volume, final Map<String, Object> map, @Nullable final File file);

	@Nullable
	public String[] getVoiceIds();

	@Nullable
	static String[] getVoiceIds(@Nullable final SpeechApi instance) {
		return instance != null ? instance.getVoiceIds() : null;
	}

	@Nullable
	public String getVoiceAttribute(@Nullable final String voiceId, final String attribute);

	@Nullable
	static String getVoiceAttribute(@Nullable final SpeechApi instance, @Nullable final String voiceId,
			final String attribute) {
		return instance != null ? instance.getVoiceAttribute(voiceId, attribute) : null;
	}

}