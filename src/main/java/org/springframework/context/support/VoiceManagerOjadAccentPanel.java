package org.springframework.context.support;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOjadAccentPanel extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -3247760984161467171L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerOjadAccentPanel.class);

	private JTextComponent tfText = null;

	private AbstractButton btnExecute = null;

	private JLabel lblAccent = null;

	private Window window = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());// TODO
		//
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f == null || Narcissus.getField(this, f) != null) {
			//
			add(new JLabel("Text"));
			//
			final String wrap = "wrap";
			//
			add(tfText = new JTextField(), String.format("%1$s,%2$s", wrap, "growx"));
			//
			add(new JLabel());
			//
			add(btnExecute = new JButton("Execute"), wrap);
			//
			btnExecute.addActionListener(this);
			//
			add(new JLabel());
			//
			add(lblAccent = new JLabel());
			//
		} // if
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			setIcon(lblAccent, new ImageIcon());
			//
			Page page = null;
			//
			if (Util.forName("org.junit.jupiter.api.Test") == null) {
				//
				PageUtil.navigate(page = newPage(BrowserTypeUtil.launch(chromium(Playwright.create()))),
						StringUtils.join("https://www.gavo.t.u-tokyo.ac.jp/ojad/search/index/word:",
								testAndApply(Objects::nonNull, Util.getText(tfText),
										x -> URLEncoder.encode(x, Charset.forName("utf-8")), null)));
				//
			} // if
				//
				// TODO
				//
			try (final InputStream is = testAndApply(
					Objects::nonNull, screenshot(testAndApply(x -> IterableUtils.size(x) == 1,
							querySelectorAll(page, ".katsuyo_accent"), x -> IterableUtils.get(x, 0), null)),
					ByteArrayInputStream::new, null)) {
				//
				setIcon(lblAccent,
						testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, is, x -> ImageIO.read(x), null),
								ImageIcon::new, x -> new ImageIcon()));
				//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			pack(window);
			//
		} // if
			//
	}

	private static void setIcon(final JLabel instance, final Icon icon) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(
						Util.stream(testAndApply(Objects::nonNull, Util.getClass(instance),
								x -> FieldUtils.getAllFieldsList(x), null)),
						f -> Objects.equals(Util.getName(f), "objectLock")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f == null || Narcissus.getField(instance, f) != null) {
			//
			instance.setIcon(icon);
		} // if
			//
	}

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = new JFrame();
		//
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		final VoiceManagerOjadAccentPanel instance = new VoiceManagerOjadAccentPanel();
		//
		instance.afterPropertiesSet();
		//
		jFrame.add(instance);
		//
		pack(instance.window = jFrame);
		//
		jFrame.setVisible(true);
		//
	}

	private static void pack(final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(
						Util.stream(testAndApply(Objects::nonNull, Util.getClass(instance),
								x -> FieldUtils.getAllFieldsList(x), null)),
						f -> Objects.equals(Util.getName(f), "objectLock")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f == null || Narcissus.getField(instance, f) != null) {
			//
			instance.pack();
			//
		} // if
			//
	}

	@Nullable
	private static String[] getFileExtensions(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static ContentInfo findMatch(@Nullable final ContentInfoUtil instance, @Nullable final byte[] bytes) {
		return instance != null && bytes != null ? instance.findMatch(bytes) : null;
	}

	@Nullable
	private static BrowserType chromium(@Nullable final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	@Nullable
	private static Page newPage(@Nullable final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

	@Nullable
	private static List<ElementHandle> querySelectorAll(@Nullable final Page instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	@Nullable
	private static byte[] screenshot(@Nullable final ElementHandle instance) {
		return instance != null ? instance.screenshot() : null;
	}

}