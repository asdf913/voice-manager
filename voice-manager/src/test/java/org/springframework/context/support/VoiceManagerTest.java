package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.j256.simplemagic.ContentInfo;

import net.miginfocom.swing.MigLayout;

class VoiceManagerTest {

	private static Method METHOD_INIT, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS,
			METHOD_GET_FILE_EXTENSION = null;

	@BeforeAll
	private static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManager.class;
		//
		(METHOD_INIT = clz.getDeclaredMethod("init")).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION = clz.getDeclaredMethod("getFileExtension", ContentInfo.class)).setAccessible(true);
		//
	}

	private VoiceManager instance = null;

	@BeforeEach
	private void beforeEach() throws ReflectiveOperationException {
		//
		final Constructor<VoiceManager> constructor = VoiceManager.class.getDeclaredConstructor();
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		instance = constructor != null && !GraphicsEnvironment.isHeadless() ? constructor.newInstance() : null;
		//
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent("", 0, null)));
		//
		final AbstractButton btnConvertToRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToRomaji", btnConvertToRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnConvertToRomaji, 0, null)));
		//
		final AbstractButton btnCopyRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyRomaji", btnCopyRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyRomaji, 0, null)));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent actionEvent) {
		if (instance != null) {
			instance.actionPerformed(actionEvent);
		} // if
	}

	@Test
	void testInit() {
		//
		if (instance != null) {
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> init());
			//
			instance.setLayout(new MigLayout());
			//
			Assertions.assertDoesNotThrow(() -> init());
			//
		} // if
			//
	}

	private void init() throws Throwable {
		try {
			METHOD_INIT.invoke(instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner)
			throws Throwable {
		try {
			METHOD_SET_CONTENTS.invoke(null, instance, contents, owner);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFileExtension() throws Throwable {
		//
		Assertions.assertNull(getFileExtension(null));
		//
		Assertions.assertNull(getFileExtension(new ContentInfo(null, null, null, false)));
		//
		Assertions.assertNull(getFileExtension(new ContentInfo(null, null, "", false)));
		//
		Assertions.assertEquals("mp3", getFileExtension(new ContentInfo(null, null, "MPEG ADTS, layer III.", false)));
		//
		final String name = "wav";
		//
		Assertions.assertEquals(name, getFileExtension(new ContentInfo(name, null, null, false)));
		//
		Assertions.assertEquals("aac", getFileExtension(new ContentInfo(null, "audio/x-hx-aac-adts", null, false)));
		//
	}

	private static String getFileExtension(final ContentInfo ci) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION.invoke(null, ci);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}