
package org.oxbow.swingbits.util;

import java.nio.file.FileSystems;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;

public interface OperatingSystemUtil {

	static OperatingSystem getOperatingSystem() {
		//
		final String name = getName(getClass(FileSystems.getDefault()));
		//
		final OperatingSystem[] oss = OperatingSystem.values();
		//
		OperatingSystem os = null;
		//
		IValue0<OperatingSystem> iValue0 = null;
		//
		for (int i = 0; i < length(oss); i++) {
			//
			if (!StringUtils.containsIgnoreCase(name, name(os = ArrayUtils.get(oss, i)))) {
				//
				continue;
				//
			} // if
				//
			if (iValue0 == null) {
				//
				iValue0 = Unit.with(os);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // for
			//
		return IValue0Util.getValue0(iValue0);
		//
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	private static int length(final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

}