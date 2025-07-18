package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.reflect.Reflection;

public class SpeechApiImpl implements SpeechApi, Provider, InitializingBean, ApplicationContextAware {

	private SpeechApi instance = null;

	private ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (Util.and(proxy instanceof SpeechApi, Objects.equals(methodName, "isInstalled"),
					Objects.equals(OperatingSystem.LINUX, OperatingSystemUtil.getOperatingSystem()))) {
				//
				final Map<?, ?> properties = System.getProperties();
				//
				final String key = "org.springframework.context.support.SpeechApi.isInstalled";
				//
				if (Util.containsKey(properties, key)) {
					//
					return Boolean.parseBoolean(Util.toString(Util.get(properties, key)));
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
			throw new Throwable(methodName);
			//
		}

	}

	@Nullable
	private SpeechApi getInstance() {
		//
		if (instance == null) {
			//
			if (Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
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
				final SpeechApi speechApi = Reflection.newProxy(SpeechApi.class, new IH());
				//
				instance = testAndApply(x -> IterableUtils.size(x) == 1,
						Util.toList(Util.filter(
								Util.stream(Util.values(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, SpeechApi.class))),
								x -> x != this)),
						x -> ObjectUtils.getIfNull(IterableUtils.get(x, 0), speechApi), x -> speechApi);
				//
			} // if
				//
		} // if
			//
		return instance;
		//
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return Util.test(predicate, value) ? Util.apply(functionTrue, value) : Util.apply(functionFalse, value);
	}

	@Nullable
	private static IValue0<Boolean> IsWindows10OrGreater() {
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					Arrays.stream(Util.getDeclaredMethods(Util.forName("com.sun.jna.platform.win32.VersionHelpers"))),
					m -> Util.and(Objects.equals(Util.getName(m), "IsWindows10OrGreater"), getParameterCount(m) == 0,
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
	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate, final int volume,
			final Map<String, Object> map) {
		//
		final SpeechApi speechApi = getInstance();
		//
		if (speechApi != null) {
			//
			speechApi.speak(text, voiceId, rate, volume, map);
			//
		} // if
			//
	}

	@Override
	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			final int volume, final Map<String, Object> map, @Nullable final File file) {
		//
		final SpeechApi speechApi = getInstance();
		//
		if (speechApi != null) {
			//
			speechApi.writeVoiceToFile(text, voiceId, rate, volume, map, file);
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