package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public interface ResourceContentInfoUtil {

	static ContentInfo getContentInfo(final InputStreamSource instance) throws IOException {
		//
		ContentInfo ci = null;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(instance)) {
			//
			ci = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null),
					new ContentInfoUtil()::findMatch, null);
			//
		} // try
			//
		return ci;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}