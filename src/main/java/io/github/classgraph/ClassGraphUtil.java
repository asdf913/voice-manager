package io.github.classgraph;

public interface ClassGraphUtil {

	static ScanResult scan(final ClassGraph instance) {
		return instance != null ? instance.scan() : null;
	}

}