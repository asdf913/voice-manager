package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;
import com.kichik.pecoff4j.ImageData;
import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceEntry;
import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ResourceParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class SpeechApiSystemSpeechImpl implements SpeechApi, Provider, Lookup, InitializingBean {

	private interface Jna extends Library {

		Jna INSTANCE = cast(Jna.class, Native.load("MicrosoftSpeechApi10.dll", Jna.class));

		private static <T> T cast(final Class<T> clz, final Object value) {
			return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
		}

		public void speak(final int[] text, final int length, final String voiceId, final int rate, final int volume);

		public void writeVoiceToFile(final int[] text, final int textLength, final String voiceId, final int rate,
				final int volume, final int[] fileName, final int fileNameLength);

		public String getVoiceIds(final String requiredAttributes, final String optionalAttributes);

		public String getVoiceAttribute(final String voiceId, final String attribute);

		public String getProviderPlatform();

		public String getDllPath();

	}

	private Table<Object, Object, Object> table = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		final Table<Object, Object, Object> table = getTable();
		//
		if (table != null) {
			//
			table.put("volume", "min", 0);
			//
			table.put("volume", "max", 100);
			//
			table.put("rate", "min", -10);
			//
			table.put("rate", "max", 10);
			//
		} // if
			//
	}

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
	public void speak(final String text, final String voiceId, final int rate, final int volume) {
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

	private static int[] toIntArray(final String text) {
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

	private static int length(final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Override
	public void writeVoiceToFile(final String text, final String voiceId, final int rate, final int volume,
			final File file) {
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
		return StringUtils.split(Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceIds("", "") : null, ',');
		//
	}

	@Override
	public String getVoiceAttribute(final String voiceId, final String attribute) {
		//
		return Jna.INSTANCE != null ? Jna.INSTANCE.getVoiceAttribute(voiceId, attribute) : null;
		//
	}

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

	private static Map<String, String> getVersionInfoMap(final PE pe) throws IOException {
		//
		final ImageData id = pe != null ? pe.getImageData() : null;
		//
		return getVersionInfoMap2(
				ResourceHelper.findResources(id != null ? id.getResourceTable() : null, ResourceType.VERSION_INFO));
		//
	}

	private static Map<String, String> getVersionInfoMap2(final ResourceEntry[] res) throws IOException {
		//
		ResourceEntry re = null;
		//
		VersionInfo vi = null;
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
			st = (sfi = (vi = testAndApply(Objects::nonNull, re.getData(), ResourceParser::readVersionInfo,
					null)) != null ? vi.getStringFileInfo() : null) != null && sfi.getCount() > 0 ? sfi.getTable(0)
							: null;
			//
			for (int j = 0; st != null && j < st.getCount(); j++) {
				//
				if ((sp = st.getString(j)) == null) {
					continue;
				} // if
					//
				if (map == null) {
					map = new LinkedHashMap<>();
				} // if
					//
				map.put(sp.getKey(), sp.getValue());
				//
			} // for
				//
		} // for
			//
		return map;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
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
			throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
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
			throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
			//
		} // try
			//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	@Override
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
		final Table<?, ?, ?> table = getTable();
		//
		if (table != null && !table.contains(row, column)) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return TableUtil.get(table, row, column);
		//
	}

}