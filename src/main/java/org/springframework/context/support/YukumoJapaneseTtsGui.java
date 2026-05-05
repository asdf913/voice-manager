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
import java.lang.reflect.Field;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
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
					if (!Objects.equals(resourceType(x), "media")) {
						//
						return;
						//
					} // if
						//
					acceptAndAccept(YukumoJapaneseTtsGui::play,
							applyAndAccept(YukumoJapaneseTtsGui::readAllBytes, url(x),
									e -> LoggerUtil.error(LOG, getMessage(e), e)),
							e -> LoggerUtil.error(LOG, getMessage(e), e));
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
					if (!Objects.equals(resourceType(x), "media")) {
						//
						return;
						//
					} // if
						//
					final byte[] bs = applyAndAccept(YukumoJapaneseTtsGui::readAllBytes, url(x),
							e -> LoggerUtil.error(LOG, getMessage(e), e));
					//
					final ContentInfo ci = testAndApply(Objects::nonNull, bs, y -> new ContentInfoUtil().findMatch(y),
							null);
					//
					if (StringsUtil.startsWith(Strings.CI, getMessage(ci), "Audio file with ID3 version 2.4")) {
						//
						final JFileChooser jfc = new JFileChooser();
						//
						jfc.setSelectedFile(Util.toFile(getPath(".", text, getFileExtensions(ci), "mp3")));
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
				});
				//
				clickPlayButton(URL, page, text);
				//
			} // try
				//
		} // if
			//
	}

	private static <T, E extends Exception> void acceptAndAccept(final FailableConsumer<T, E> consumer, final T value,
			final Consumer<Throwable> throwableConsumer) {
		//
		try {
			//
			FailableConsumerUtil.accept(consumer, value);
			//
		} catch (final Throwable throwable) {
			//
			Util.accept(throwableConsumer, throwable);
			//
		} // try
			//
	}

	private static void play(final byte[] bs) throws IOException, JavaLayerException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			PlayerUtil.play(testAndApply(Objects::nonNull, is, Player::new, null));
			//
		} // try
			//
	}

	private static String getMessage(@Nullable final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static byte[] readAllBytes(final String url) throws IOException {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(url), FieldUtils::getAllFieldsList, null)),
				f -> Objects.equals(Util.getName(f), "value")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		try (final InputStream is = Util
				.openStream(
						testAndApply(
								s -> s != null
										&& Narcissus.getField(s,
												testAndApply(f -> IterableUtils.size(f) == 1, fs,
														f -> IterableUtils.get(f, 0), null)) != null,
								url, URL::new, null))) {
			//
			return readAllBytes(is);
			//
		} // try
			//
	}

	private static <T, R, E extends Exception> R applyAndAccept(final FailableFunction<T, R, E> function, final T value,
			final Consumer<Throwable> throwableConsumer) {
		//
		try {
			//
			return FailableFunctionUtil.apply(function, value);
			//
		} catch (final Throwable throwable) {
			//
			Util.accept(throwableConsumer, throwable);
			//
		} // try
			//
		return null;
		//
	}

	private static Path getPath(final String first, final String text, @Nullable final String[] fileExtensions,
			final String fileExtension) {
		//
		final Iterable<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(String.class)),
				f -> Objects.equals(Util.getName(f), "value")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		final Predicate<String> predicate = x -> x == null || Narcissus.getField(x, f) == null;
		//
		final FailableFunction<String, String, RuntimeException> function = x -> "";
		//
		final FailableFunction<String, String, RuntimeException> identity = FailableFunction.identity();
		//
		return Path
				.of(testAndApply(predicate, first, function, identity),
						String.join(".", testAndApply(predicate, text, function, identity),
								StringUtils.defaultString(fileExtensions != null && fileExtensions.length == 1
										? ArrayUtils.get(fileExtensions, 0)
										: testAndApply(predicate, fileExtension, function, identity))));
		//
	}

	@Nullable
	private static String[] getFileExtensions(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	@Nullable
	private static String url(@Nullable final Request instance) {
		return instance != null ? instance.url() : null;
	}

	@Nullable
	private static String resourceType(@Nullable final Request instance) {
		return instance != null ? instance.resourceType() : null;
	}

	private static void onRequest(@Nullable final Page instance, final Consumer<Request> handler) {
		if (instance != null) {
			instance.onRequest(handler);
		}
	}

	@Nullable
	private static byte[] readAllBytes(@Nullable final InputStream instance) throws IOException {
		return instance != null ? instance.readAllBytes() : null;
	}

	private static void clickPlayButton(final String url, @Nullable final Page page, @Nullable final String text) {
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