package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;

public class SpeechApiImpl implements SpeechApi, Provider, InitializingBean {

	private SpeechApi instance = null;

	private SpeechApi getInstance() {
		//
		if (instance == null) {
			//
			if (Objects.equals(Boolean.TRUE, IsWindows10OrGreater())) {
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

	private static Boolean IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = Arrays
					.stream(Class.forName("com.sun.jna.platform.win32.VersionHelpers").getDeclaredMethods())
					.filter(m -> m != null && Objects.equals(m.getName(), "IsWindows10OrGreater")
							&& m.getParameterCount() == 0 && Modifier.isStatic(m.getModifiers()))
					.toList();
			//
			if (ms == null || ms.isEmpty()) {
				//
				return null;
				//
			} // if
				//
			final Method m = ms.size() == 1 ? ms.get(0) : null;
			//
			return cast(Boolean.class, m != null ? m.invoke(null) : null);
			//
		} catch (final ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
			//
		} // try
			//
		return null;
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
		final SpeechApi speechApi = getInstance();
		//
		return speechApi != null && speechApi.isInstalled();
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
		final SpeechApi speechApi = getInstance();
		//
		if (speechApi != null) {
			//
			speechApi.writeVoiceToFile(text, voiceId, 0, volume, file);
			//
		} // if
			//
	}

	@Override
	public String[] getVoiceIds() {
		//
		final SpeechApi speechApi = getInstance();
		//
		return speechApi != null ? speechApi.getVoiceIds() : null;
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
		final Provider provider = cast(Provider.class, getInstance());
		//
		return provider != null ? provider.getProviderName() : null;
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Override
	public String getProviderVersion() {
		//
		final Provider provider = cast(Provider.class, getInstance());
		//
		return provider != null ? provider.getProviderVersion() : null;
		//
	}

	@Override
	public String getProviderPlatform() {
		//
		final Provider provider = cast(Provider.class, getInstance());
		//
		return provider != null ? provider.getProviderPlatform() : null;
		//
	}

}