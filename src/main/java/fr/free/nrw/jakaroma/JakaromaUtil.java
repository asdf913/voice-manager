package fr.free.nrw.jakaroma;

public interface JakaromaUtil {

	static String convert(final Jakaroma instance, final String input, final boolean trailingSpace,
			final boolean capitalizeWords) {
		return instance != null ? instance.convert(input, trailingSpace, capitalizeWords) : null;
	}

}