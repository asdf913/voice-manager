package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.BrowserUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightUtil;
import com.microsoft.playwright.Request;

import io.github.toolfactory.narcissus.Narcissus;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerUtil;
import net.miginfocom.swing.MigLayout;

public class YukumoJapaneseTtsGui extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = 8233800467364858829L;

	private static final Logger LOG = LoggerFactory.getLogger(YukumoJapaneseTtsGui.class);

	private static final String URL = "https://www.yukumo.net";

	private JTextComponent tfText = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Play")
	private AbstractButton btnPlay = null;

	private AbstractButton btnDownload = null;

	private YukumoJapaneseTtsGui() {
		//
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		if (Narcissus.getField(this, Narcissus.findField(getClass(), "component")) == null) {
			//
			return;
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), String.format("wmin %1$s", 100));
		//
		add(btnPlay = new JButton("Play"));
		//
		add(btnDownload = new JButton("Download"), "wrap");
		//
		Util.forEach(
				Util.map(
						Util.filter(Util.stream(FieldUtils.getAllFieldsList(getClass())),
								f -> Util.isAssignableFrom(AbstractButton.class, Util.getType(f))),
						f -> Util.cast(AbstractButton.class, Narcissus.getField(this, f))),
				x -> Util.addActionListener(x, this));
		//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnPlay)) {
			//
			try (final Playwright playwright = Playwright.create();
					final Browser browser = BrowserTypeUtil.launch(PlaywrightUtil.chromium(playwright));
					final Page page = BrowserUtil.newPage(browser)) {
				//
				onRequest(page, x -> {
					//
					if (x != null && Objects.equals(x.resourceType(), "media")) {
						//
						byte[] bs = null;
						//
						try (final InputStream is = Util.openStream(new URL(x.url()))) {
							//
							bs = readAllBytes(is);
							//
						} catch (final IOException e) {
							//
							LoggerUtil.error(LOG, e.getMessage(), e);
							//
						} // try
							//
						final ContentInfo ci = testAndApply(Objects::nonNull, bs,
								y -> new ContentInfoUtil().findMatch(y), null);
						//
						if (ci != null
								&& Objects.equals(ci.getMessage(), "Audio file with ID3 version 2.4, MP3 encoding")) {
							//
							try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new,
									null)) {
								//
								PlayerUtil.play(testAndApply(Objects::nonNull, is, Player::new, null));
								//
							} catch (final IOException | JavaLayerException e) {
								//
								LoggerUtil.error(LOG, e.getMessage(), e);
								//
							} // try
								//
						} // if
							//
					} // if
						//
				});
				//
				clickPlayButton(URL, page, Util.getText(tfText));
				//
			} // try
				//
		} else if (Objects.equals(source, btnDownload)) {
			//
			try (final Playwright playwright = Playwright.create();
					final Browser browser = BrowserTypeUtil.launch(PlaywrightUtil.chromium(playwright));
					final Page page = BrowserUtil.newPage(browser)) {
				//
				final String text = Util.getText(tfText);
				//
				onRequest(page, x -> {
					//
					if (x != null && Objects.equals(x.resourceType(), "media")) {
						//
						byte[] bs = null;
						//
						try (final InputStream is = Util.openStream(new URL(x.url()))) {
							//
							bs = readAllBytes(is);
							//
						} catch (final IOException e) {
							//
							LoggerUtil.error(LOG, e.getMessage(), e);
							//
						} // try
							//
						final ContentInfo ci = testAndApply(Objects::nonNull, bs,
								y -> new ContentInfoUtil().findMatch(y), null);
						//
						if (ci != null
								&& Objects.equals(ci.getMessage(), "Audio file with ID3 version 2.4, MP3 encoding")) {
							//
							final JFileChooser jfc = new JFileChooser();
							//
							final String[] fileExtensions = ci.getFileExtensions();
							//
							jfc.setSelectedFile(Util.toFile(Path.of(".",
									String.join(".", text,
											fileExtensions != null && fileExtensions.length == 1
													? ArrayUtils.get(fileExtensions, 0)
													: "mp3"))));
							//
							if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
								//
								try {
									//
									FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), bs);
									//
								} catch (final IOException e) {
									//
									LoggerUtil.error(LOG, e.getMessage(), e);
									//
								} // try
									//
							} // if
								//
						} // if
							//
					} // if
						//
				});
				//
				clickPlayButton(URL, page, text);
				//
			} // try
				//
		} // if
			//
	}

	private static void onRequest(final Page instance, final Consumer<Request> handler) {
		if (instance != null) {
			instance.onRequest(handler);
		}
	}

	private static byte[] readAllBytes(@Nullable final InputStream instance) throws IOException {
		return instance != null ? instance.readAllBytes() : null;
	}

	private static void clickPlayButton(final String url, @Nullable final Page page, final String text) {
		//
		PageUtil.navigate(page, url);
		//
		ElementHandle element = testAndApply(x -> IterableUtils.size(x) == 1,
				PageUtil.querySelectorAll(page, "input[type=\"text\"]"), x -> IterableUtils.get(x, 0), null);
		//
		if (element != null && text != null) {
			//
			element.fill(text);
			//
		} // if
			//
		if ((element = testAndApply(x -> IterableUtils.size(x) == 1,
				PageUtil.querySelectorAll(page, "button:has(i.fa-play)"), x -> IterableUtils.get(x, 0),
				null)) != null) {
			//
			element.click();
			//
		} // if
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	public static void main(final String[] args) throws Exception {
		//
		final YukumoJapaneseTtsGui instance = new YukumoJapaneseTtsGui();
		//
		instance.afterPropertiesSet();
		//
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() ? new JFrame() : null;
		//
		if (jFrame != null) {
			//
			jFrame.add(instance);
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			jFrame.pack();
			//
			if (!isTestMode()) {
				//
				jFrame.setVisible(true);
				//
			} // if
				//
		} // if
			//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

}