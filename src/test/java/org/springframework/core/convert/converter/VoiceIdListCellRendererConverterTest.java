package org.springframework.core.convert.converter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.SpeechApi;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceIdListCellRendererConverterTest {

	private static class IH implements InvocationHandler {

		private String voiceAttribute = null;

		private String[] voiceIds = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = method != null ? method.getName() : null;
			//
			if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(name, "getVoiceAttribute")) {
					//
					return voiceAttribute;
					//
				} else if (Objects.equals(name, "getVoiceIds")) {
					//
					return voiceIds;
					//
				} // if
					//
			} else if (proxy instanceof ListCellRenderer && Objects.equals(name, "getListCellRendererComponent")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	@Test
	void testConvert() throws NoSuchMethodException {
		//
		final VoiceIdListCellRendererConverter instance = new VoiceIdListCellRendererConverter();
		//
		ListCellRenderer<?> lcr = instance.convert(null);
		//
		Assertions.assertNotNull(lcr);
		//
		final IH ih = new IH();
		//
		if (lcr != null) {
			//
			Assertions.assertNull(lcr.getListCellRendererComponent(null, null, 0, false, false));
			//
			final Class<?> clz = lcr.getClass();
			//
			final Method method = clz != null ? clz.getDeclaredMethod("getListCellRendererComponent", JList.class,
					Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE) : null;
			//
			Assertions.assertNull(Narcissus.invokeMethod(lcr, method, null, "", 0, false, false));
			//
			ih.voiceIds = new String[] { ih.voiceAttribute = "voiceAttribute" };
			//
			instance.setSpeechApi(Reflection.newProxy(SpeechApi.class, ih));
			//
			Assertions.assertNull(Narcissus.invokeMethod(lcr, method, null, "", 0, false, false));
			//
		} // if
			//
		if ((lcr = cast(ListCellRenderer.class,
				Narcissus.invokeMethod(instance,
						VoiceIdListCellRendererConverter.class.getDeclaredMethod("convert", ListCellRenderer.class),
						Reflection.newProxy(ListCellRenderer.class, ih)))) != null) {
			//
			Assertions.assertNull(lcr.getListCellRendererComponent(null, null, 0, false, false));
			//
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}