package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;
import com.kichik.pecoff4j.ImageData;
import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceEntry;
import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.ByteArrayDataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface SpeakMethod {
}

public class SpeechApiSystemSpeechImpl implements SpeechApi, Provider, Lookup, InitializingBean {

	private interface Jna extends Library {

		Jna INSTANCE = Util.cast(Jna.class, Native.load("MicrosoftSpeechApi10.dll", Jna.class));

		public void speak(@Nullable final int[] text, final int length, @Nullable final String voiceId, final int rate,
				final int volume);

		public void speakSsml(@Nullable final int[] text, final int length, final String voiceId, final int rate,
				final int volume);

		public void writeVoiceToFile(@Nullable final int[] text, final int textLength, @Nullable final String voiceId,
				final int rate, final int volume, @Nullable final int[] fileName, final int fileNameLength);

		public Pointer getVoiceIds(final IntByReference length);

		public String getVoiceAttribute(@Nullable final String voiceId, final String attribute);

		public String getProviderPlatform();

		public String getDllPath();

	}

	private Table<Object, Object, Object> table = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		final Table<Object, Object, Object> t = getTable();
		//
		TableUtil.put(t, "volume", "min", 0);
		//
		TableUtil.put(t, "volume", "max", 100);
		//
		TableUtil.put(t, "rate", "min", -10);
		//
		TableUtil.put(t, "rate", "max", 10);
		//
	}

	@Nullable
	private Table<Object, Object, Object> getTable() {
		//
		return table = ObjectUtils.getIfNull(table, HashBasedTable::create);
		//
	}

	@Override
	public boolean isInstalled() {
		//
		return true;
		//
	}

	@Override
	@SpeakMethod
	public void speak(@Nullable final String text, @Nullable final String voiceId, final int rate, final int volume) {
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

	@SpeakMethod
	public void speakSsml(final String text, final String voiceId, final int rate, final int volume) {
		//
		if (Jna.INSTANCE != null) {
			//
			final int[] ints = toIntArray(text);
			//
			Jna.INSTANCE.speakSsml(ints, length(ints), voiceId, rate, volume);
			//
		} // if
			//
	}

	@Nullable
	private static int[] toIntArray(@Nullable final String text) {
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

	private static int length(@Nullable final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Override
	public void writeVoiceToFile(@Nullable final String text, @Nullable final String voiceId, final int rate,
			final int volume, @Nullable final File file) {
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
		final IntByReference lengthIbr = new IntByReference();
		//
		final Jna instance = Jna.INSTANCE;
		//
		final Pointer pointer = instance != null ? instance.getVoiceIds(lengthIbr) : null;
		//
		final int length = lengthIbr.getValue();
		//
		final Pointer[] pointers = pointer != null ? pointer.getPointerArray(0, length) : null;
		//
		List<String> list = null;
		//
		for (int i = 0; pointers != null && i < Math.min(pointers.length, length); i++) {
			//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), pointers[i].getWideString(0));
			//
		} // for
			//
		return list != null ? list.toArray(new String[] {}) : null;
		//
	}

	@Override
	@Nullable
	public String getVoiceAttribute(@Nullable final String voiceId, final String attribute) {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceAttribute(voiceId, attribute) : null;
		//
	}

	@Nullable
	private Map<String, String> versionInfoMap = null;

	private Map<String, String> getVersionInfoMap() throws IOException {
		//
		if (versionInfoMap == null) {
			//
			versionInfoMap = getVersionInfoMap(testAndApply(StringUtils::isNotBlank,
					Jna.INSTANCE != null ? Jna.INSTANCE.getDllPath() : null, PEParser::parse, null));
			//
		} // if
			//
		return versionInfoMap;
		//
	}

	@Nullable
	private static Map<String, String> getVersionInfoMap(@Nullable final PE pe) throws IOException {
		//
		final ImageData id = pe != null ? pe.getImageData() : null;
		//
		return getVersionInfoMap2(
				ResourceHelper.findResources(id != null ? id.getResourceTable() : null, ResourceType.VERSION_INFO));
		//
	}

	@Nullable
	private static Map<String, String> getVersionInfoMap2(@Nullable final ResourceEntry[] res) throws IOException {
		//
		ResourceEntry re = null;
		//
		StringFileInfo sfi = null;
		//
		StringTable st = null;
		//
		StringPair sp = null;
		//
		Map<String, String> map = null;
		//
		for (int i = 0; res != null && i < res.length; i++) {
			//
			if ((re = res[i]) == null) {
				continue;
			} // if
				//
			st = (sfi = (getStringFileInfo(testAndApply(Objects::nonNull, re.getData(),
					x -> VersionInfo.read(new ByteArrayDataReader(x)), null)))) != null && sfi.getCount() > 0
							? sfi.getTable(0)
							: null;
			//
			for (int j = 0; st != null && j < st.getCount(); j++) {
				//
				if ((sp = st.getString(j)) == null) {
					continue;
				} // if
					//
				put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), sp.getKey(), sp.getValue());
				//
			} // for
				//
		} // for
			//
		return map;
		//
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	@Nullable
	private static StringFileInfo getStringFileInfo(@Nullable final VersionInfo instance) {
		return instance != null ? instance.getStringFileInfo() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public String getProviderName() {
		//
		try {
			//
			return get(getVersionInfoMap(), "FileDescription");
			//
		} catch (final Exception e) {
			//
			throw e instanceof RuntimeException re ? re : new RuntimeException(e);
			//
		} // try
			//
	}

	@Override
	public String getProviderVersion() {
		//
		try {
			//
			return get(getVersionInfoMap(), "ProductVersion");
			//
		} catch (final Exception e) {
			//
			throw e instanceof RuntimeException re ? re : new RuntimeException(e);
			//
		} // try
			//
	}

	@Nullable
	private static <V> V get(@Nullable final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	@Override
	@Nullable
	public String getProviderPlatform() {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getProviderPlatform() : null;
		//
	}

	@Override
	public boolean contains(final Object row, final Object column) {
		//
		return TableUtil.contains(getTable(), row, column);
		//
	}

	@Override
	public Object get(final Object row, final Object column) {
		//
		final Table<?, ?, ?> t = getTable();
		//
		if (t != null && !TableUtil.contains(t, row, column)) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return TableUtil.get(t, row, column);
		//
	}

}