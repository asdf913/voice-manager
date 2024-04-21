package io.github.classgraph;

public interface HasNameUtil {

	static String getName(final HasName instance) {
		return instance != null ? instance.getName() : null;
	}

}