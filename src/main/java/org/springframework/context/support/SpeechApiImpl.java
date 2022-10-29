package org.springframework.context.support;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;

import com.sun.jna.platform.win32.VersionHelpers;

public class SpeechApiImpl implements SpeechApi, Provider, InitializingBean {

	private SpeechApi instance = null;

	private SpeechApi getInstance() {
		//
		if (instance == null) {
			//
			if (VersionHelpers.IsWindows10OrGreater()) {
				//
				instance = new SpeechApiSystemSpeechImpl();
				//
			} else {
				//
				instance = new SpeechApiSpeechServerImpl();
				//
			} // if
				//
		} // if
			//
		return instance;
		//
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		final InitializingBean initializingBean = cast(InitializingBean.class, getInstance());
		//
		if (initializingBean != null) {
			//
			initializingBean.afterPropertiesSet();
			//
		} // if
			//
	}

	@Override
	public boolean isInstalled() {
		//
		final SpeechApi instance = getInstance();
		//
		return instance != null && instance.isInstalled();
		//
	}

	@Override
	public void speak(final String text, final String voiceId, final int rate, final int volume) {
		//
		final SpeechApi instance = getInstance();
		//
		if (instance != null) {
			//
			instance.speak(text, voiceId, rate, volume);
			//
		} // if
			//
	}

	@Override
	public void writeVoiceToFile(final String text, final String voiceId, final int rate, final int volume,
			final File file) {
		//
		final SpeechApi instance = getInstance();
		//
		if (instance != null) {
			//
			instance.writeVoiceToFile(text, voiceId, 0, volume, file);
			//
		} // if
			//
	}

	@Override
	public String[] getVoiceIds() {
		//
		final SpeechApi instance = getInstance();
		//
		return instance != null ? instance.getVoiceIds() : null;
		//
	}

	@Override
	public String getVoiceAttribute(final String voiceId, final String attribute) {
		//
		return instance != null ? instance.getVoiceAttribute(voiceId, attribute) : null;
		//
	}

	@Override
	public String getProviderName() {
		//
		final Provider instance = cast(Provider.class, getInstance());
		//
		return instance != null ? instance.getProviderName() : null;
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Override
	public String getProviderVersion() {
		//
		final Provider instance = cast(Provider.class, getInstance());
		//
		return instance != null ? instance.getProviderVersion() : null;
		//
	}

	@Override
	public String getProviderPlatform() {
		//
		final Provider instance = cast(Provider.class, getInstance());
		//
		return instance != null ? instance.getProviderPlatform() : null;
		//
	}

}