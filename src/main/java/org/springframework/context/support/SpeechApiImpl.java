package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.reflect.Reflection;

public class SpeechApiImpl implements SpeechApi, Provider, InitializingBean {

	private SpeechApi instance = null;

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(methodName, "isInstalled")) {
					//
					if (Objects.equals(Util.getName(Util.getClass(FileSystems.getDefault())),
							"sun.nio.fs.LinuxFileSystem")) {
						//
						final Map<?, ?> properties = System.getProperties();
						//
						final String key = "org.springframework.context.support.SpeechApi.isInstalled";
						//
						if (Util.containsKey(properties, key)) {
							//
							return Boolean.parseBoolean(Util.toString(get(properties, key)));
							//
						} else {
							//
							LoggerUtil.info(LoggerFactory.getLogger(IH.class),
									String.format("Please specify the \"%1$s\" system property", key));
							//
						} // if
							//
					} // if
						//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static Object get(final Map<?, ?> instance, final Object key) {
			return instance != null ? instance.get(key) : null;
		}

	}

	@Nullable
	private SpeechApi getInstance() {
		//
		if (instance == null) {
			//
			if (Objects.equals(Util.getName(Util.getClass(FileSystems.getDefault())), "sun.nio.fs.WindowsFileSystem")) {
				//
				if (Objects.equals(Boolean.TRUE, IValue0Util.getValue0(IsWindows10OrGreater()))) {
					//
					instance = new SpeechApiSystemSpeechImpl();
					//
				} else {
					//
					instance = new SpeechApiSpeechServerImpl();
					//
				} // if
					//
			} else {
				//
				instance = Reflection.newProxy(SpeechApi.class, new IH());
				//
			} // if
			//
		} // if
			//
		return instance;
		//
	}

	@Nullable
	private static IValue0<Boolean> IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					Arrays.stream(Util.getDeclaredMethods(Util.forName("com.sun.jna.platform.win32.VersionHelpers"))),
					m -> and(Objects.equals(Util.getName(m), "IsWindows10OrGreater"), getParameterCount(m) == 0,
							Util.isStatic(m))));
			//
			if (ms == null || ms.isEmpty()) {
				//
				return null;
				//
			} // if
				//
			final Method m = ms.size() == 1 ? ms.get(0) : null;
			//
			return Unit.with(Util.cast(Boolean.class, invoke(m, null)));
			//
		} catch (final IllegalAccessException | InvocationTargetException e) {
			//
		} // try
			//
		return null;
		//
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, final Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static int getParameterCount(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterCount() : 0;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		final InitializingBean initializingBean = Util.cast(InitializingBean.class, getInstance());
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
		return SpeechApi.isInstalled(getInstance());
	}

	@Override
	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate, final int volume) {
		//
		final SpeechApi speechApi = getInstance();
		//
		if (speechApi != null) {
			//
			speechApi.speak(text, voiceId, rate, volume);
			//
		} // if
			//
	}

	@Override
	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			final int volume, @Nullable final File file) {
		//
		final SpeechApi speechApi = getInstance();
		//
		if (speechApi != null) {
			//
			speechApi.writeVoiceToFile(text, voiceId, rate, volume, file);
			//
		} // if
			//
	}

	@Override
	@Nullable
	public String[] getVoiceIds() {
		return SpeechApi.getVoiceIds(getInstance());
	}

	@Override
	@Nullable
	public String getVoiceAttribute(@Nullable final String voiceId, final String attribute) {
		return SpeechApi.getVoiceAttribute(instance, voiceId, attribute);
	}

	@Override
	@Nullable
	public String getProviderName() {
		return Provider.getProviderName(Util.cast(Provider.class, getInstance()));
	}

	@Override
	@Nullable
	public String getProviderVersion() {
		return Provider.getProviderVersion(Util.cast(Provider.class, getInstance()));
	}

	@Override
	@Nullable
	public String getProviderPlatform() {
		return Provider.getProviderPlatform(Util.cast(Provider.class, getInstance()));
	}

}