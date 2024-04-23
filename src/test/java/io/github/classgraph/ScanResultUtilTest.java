package io.github.classgraph;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ScanResultUtilTest {

	@Test
	void testScan() throws IllegalAccessException {
		//
		Assertions.assertNull(ScanResultUtil.getAllClasses(null));
		//
		final ScanResult scanResult = cast(ScanResult.class, Narcissus.allocateInstance(ScanResult.class));
		//
		Assertions.assertNull(ScanResultUtil.getAllClasses(scanResult));
		//
		FieldUtils.writeDeclaredField(scanResult, "closed", new AtomicBoolean(false), true);
		//
		Assertions.assertNull(ScanResultUtil.getAllClasses(scanResult));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}