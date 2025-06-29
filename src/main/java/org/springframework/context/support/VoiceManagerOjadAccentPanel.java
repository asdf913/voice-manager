package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Strings;
import com.google.common.reflect.Reflection;
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

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Input Text")
	private JTextComponent tfTextInput = null;

	private JTextComponent tfTextOutput = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy Text")
	private AbstractButton btnCopyText = null;

	private AbstractButton btnCopyImage = null;

	private JLabel lblAccent = null;

	private Window window = null;

	private static class TextAndImage {

		private String text = null;

		private Image image = null;

	}

	private JComboBox<TextAndImage> jcbTextAndImage = null;

	private transient MutableComboBoxModel<TextAndImage> mcbmTextAndImage = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());// TODO
		//
		final Field f = getFieldByName(Util.getClass(this), "component");
		//
		if (f == null || Narcissus.getField(this, f) != null) {
			//
			add(new JLabel("Text"));
			//
			final String wrap = "wrap";
			//
			final String growx = "growx";
			//
			final int span = 2;
			//
			add(tfTextInput = new JTextField(), String.format("%1$s,%2$s,span %3$s", wrap, growx, span));
			//
			add(new JLabel());
			//
			add(btnExecute = new JButton("Execute"), wrap);
			//
			add(new JLabel("Text And Image"));
			//
			add(jcbTextAndImage = new JComboBox<>(
					mcbmTextAndImage = new DefaultComboBoxModel<>(new TextAndImage[] { null })),
					String.format("%1$s,%2$s,span %3$s", wrap, growx, span));
			//
			jcbTextAndImage.addActionListener(this);
			//
			jcbTextAndImage.setRenderer(new ListCellRenderer<>() {

				@Override
				public Component getListCellRendererComponent(@Nullable final JList<? extends TextAndImage> list,
						@Nullable final TextAndImage value, final int index, final boolean isSelected,
						final boolean cellHasFocus) {
					//
					final JPanel panel = new JPanel();
					//
					final Dimension2D preferredSize = Util.getPreferredSize(panel);
					//
					if (preferredSize != null) {
						//
						if (Util.getSize(Util.getModel(list)) == 1) {
							//
							panel.setPreferredSize(new Dimension((int) preferredSize.getWidth(),
									(int) getHeight(Util.getPreferredSize(tfTextInput))));
							//
						} else {
							//
							// TODO
							//
							if (value == null) {
								//
								panel.setPreferredSize(new Dimension((int) preferredSize.getWidth(),
										Math.max((int) getHeight(Util.getPreferredSize(tfTextInput)), 26)));
								//
							} // if
								//
						} // if
							//
					} // if
						//
					panel.add(new JLabel(getText(value)));
					//
					final JLabel label = new JLabel();
					//
					label.setIcon(
							testAndApply(Objects::nonNull, getImage(value), ImageIcon::new, x -> new ImageIcon()));
					//
					panel.add(label);
					//
					return panel;
					//
				}

			});
			//
			add(new JLabel("Text"));
			//
			add(tfTextOutput = new JTextField(), growx);
			//
			add(btnCopyText = new JButton("Copy"), wrap);
			//
			Util.setEditable(tfTextOutput, false);
			//
			add(new JLabel("Image"));
			//
			add(lblAccent = new JLabel(), String.format("%1$s,span %2$s", wrap, span));
			//
			add(new JLabel());
			//
			add(btnCopyImage = new JButton("Copy"));
			//
			Util.forEach(Stream.of(btnExecute, btnCopyText, btnCopyImage), x -> Util.addActionListener(x, this));
			//
			Util.forEach(Stream.of(btnCopyText, btnCopyImage), x -> Util.setEnabled(x, false));
			//
		} // if
			//
	}

	private static double getHeight(@Nullable final Dimension2D instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getText(@Nullable final TextAndImage instance) {
		return instance != null ? instance.text : null;
	}

	@Nullable
	private static Image getImage(@Nullable final TextAndImage instance) {
		return instance != null ? instance.image : null;
	}

	private static Field getFieldByName(final Class<?> clz, final String fieldName) {
		//
		final List<Field> fs = Util.toList(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getName(f), fieldName)));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static class IH implements InvocationHandler {

		private Image image = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Transferable) {
				//
				if (Objects.equals(methodName, "getTransferDataFlavors")) {
					//
					return new DataFlavor[] { DataFlavor.imageFlavor };
					//
				} else if (Objects.equals(methodName, "getTransferData")) {
					//
					return image;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedBtnExecute();
			//
		} else if (Objects.equals(source, jcbTextAndImage)) {
			//
			final TextAndImage textAndImage = Util.cast(TextAndImage.class, getSelectedItem(jcbTextAndImage));
			//
			// text
			//
			final String text = getText(textAndImage);
			//
			Util.setText(tfTextOutput, text);
			//
			Util.setEnabled(btnCopyText, StringUtils.isNotBlank(text));
			//
			// image
			//
			final Image image = getImage(textAndImage);
			//
			setIcon(lblAccent, testAndApply(Objects::nonNull, image, ImageIcon::new, x -> new ImageIcon()));
			//
			Util.setEnabled(btnCopyImage, image != null);
			//
			pack(window);
			//
		} else if (Objects.equals(source, btnCopyImage)) {
			//
			final IH ih = new IH();
			//
			ih.image = getImage(Util.cast(TextAndImage.class, getSelectedItem(jcbTextAndImage)));
			//
			setContents(
					testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
							() -> getSystemClipboard(Toolkit.getDefaultToolkit()),
							() -> Util.cast(Clipboard.class, Narcissus.allocateInstance(Clipboard.class))),
					Reflection.newProxy(Transferable.class, ih), null);
			//
		} else if (Objects.equals(source, btnCopyText)) {
			//
			setContents(
					testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
							() -> getSystemClipboard(Toolkit.getDefaultToolkit()),
							() -> Util.cast(Clipboard.class, Narcissus.allocateInstance(Clipboard.class))),
					new StringSelection(Util.getText(tfTextOutput)), null);
			//
		} // if
			//
	}

	private void actionPerformedBtnExecute() {
		//
		Util.setText(tfTextOutput, null);
		//
		setIcon(lblAccent, new ImageIcon());
		//
		Page page = null;
		//
		if (!isTestMode()) {
			//
			PageUtil.navigate(page = newPage(BrowserTypeUtil.launch(chromium(Playwright.create()))),
					StringUtils.join("https://www.gavo.t.u-tokyo.ac.jp/ojad/search/index/word:",
							testAndApply(Objects::nonNull, Util.getText(tfTextInput),
									x -> URLEncoder.encode(x, StandardCharsets.UTF_8), null)));
			//
		} // if
			//
		Util.forEach(Util.map(sorted(Util.map(IntStream.range(1, Util.getSize(mcbmTextAndImage)), i -> -i)), i -> -i),
				i -> Util.removeElementAt(mcbmTextAndImage, i));
		//
		// TODO
		//
		final List<ElementHandle> ehs = querySelectorAll(page, ".katsuyo_accent");
		//
		final List<ElementHandle> words = querySelectorAll(page, "tr[id^=\"word\"]");
		//
		final Collection<TextAndImage> textAndImages = ObjectUtils.getIfNull(toTextAndImages(ehs, words),
				() -> toTextAndImages(ehs, Util.getText(tfTextInput), words));
		//
		Util.forEach(Util.stream(textAndImages), x -> Util.addElement(mcbmTextAndImage, x));
		//
		if (IterableUtils.size(textAndImages) == 1
				|| (IterableUtils.size(words) == 1 && IterableUtils.size(textAndImages) == 1)) {
			//
			Util.setSelectedItem(mcbmTextAndImage, IterableUtils.get(textAndImages, 0));
			//
		} // if
			//
		pack(window);
		//
	}

	private static Collection<TextAndImage> toTextAndImages(@Nullable final Iterable<ElementHandle> ehs,
			final Iterable<ElementHandle> words) {
		//
		TextAndImage textAndImage = null;
		//
		if (IterableUtils.size(ehs) == 1) {
			//
			(textAndImage = new TextAndImage()).text = StringUtils.trim(textContent(querySelector(
					testAndApply(x -> IterableUtils.size(x) == 1, words, x -> IterableUtils.get(x, 0), null),
					".midashi")));
			//
			textAndImage.image = toImage(screenshot(IterableUtils.get(ehs, 0)),
					e -> LoggerUtil.error(LOG, e.getMessage(), e));
			//
			return Collections.singleton(textAndImage);
			//
		} // if
			//
		Collection<TextAndImage> textAndImages = null;
		//
		if (IterableUtils.size(words) == IterableUtils.size(ehs)) {
			//
			for (int i = 0; i < IterableUtils.size(words); i++) {
				//
				(textAndImage = new TextAndImage()).text = StringUtils
						.trim(textContent(querySelector(IterableUtils.get(words, i), ".midashi")));
				//
				textAndImage.image = toImage(screenshot(IterableUtils.get(ehs, i)),
						e -> LoggerUtil.error(LOG, e.getMessage(), e));
				//
				Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
				//
			} // for
				//
			return textAndImages;
			//
		} // if
			//
		return null;
		//
	}

	private static Collection<TextAndImage> toTextAndImages(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words) {
		//
		if (IterableUtils.size(words) == 1) {
			//
			ElementHandle eh = null;
			//
			String commonPrefix;
			//
			String[] ws = null;
			//
			TextAndImage textAndImage, tempTextAndImage = null;
			//
			Collection<TextAndImage> textAndImages = null;
			//
			TextStringBuilder tsb = null;
			//
			boolean found = false;
			//
			for (int i = 0; i < IterableUtils.size(ehs); i++) {
				//
				if (StringUtils.equals(textInput, StringUtils.trim(textContent(eh = IterableUtils.get(ehs, i))))) {
					//
					(textAndImage = new TextAndImage()).image = toImage(screenshot(eh),
							e -> LoggerUtil.error(LOG, e.getMessage(), e));
					//
					ws = StringUtils.split(
							StringUtils.trim(textContent(querySelector(IterableUtils.get(words, 0), ".midashi"))), '・');
					//
					if (StringUtils.isBlank(textAndImage.text) && length(ws) == 2 && IterableUtils.size(ehs) > 1) {
						//
						TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
						//
						if (StringUtils.startsWith(textInput,
								commonPrefix = Strings.commonPrefix(
										StringUtils.trim(textContent(IterableUtils.get(ehs, 0))),
										StringUtils.trim(textContent(IterableUtils.get(ehs, 1)))))
								&& tsb != null) {
							//
							tsb.append(Strings.commonPrefix(ArrayUtils.get(ws, 0), ArrayUtils.get(ws, 1)));
							//
							tsb.append(StringUtils.substringAfter(textInput, commonPrefix));
							//
						} // if
							//
						textAndImage.text = Util.toString(tsb);
						//
					} // if
						//
						// TODO
						//
					found = false;
					//
					for (int j = 0; j < IterableUtils.size(textAndImages); j++) {
						//
						if ((tempTextAndImage = IterableUtils.get(textAndImages, j)) == null) {
							//
							continue;
							//
						} // if
							//
						try {
							//
							if (Objects.equals(getText(textAndImage), getText(tempTextAndImage))
									&& Arrays.equals(toByteArray(Util.cast(RenderedImage.class, textAndImage), "PNG"),
											toByteArray(Util.cast(RenderedImage.class, tempTextAndImage), "PNG"))) {
								//
								found = true;
								//
							} // if
								//
						} catch (final IOException e) {
							//
							throw new RuntimeException(e);
							//
						} // try
							//
					} // for
						//
					if (!found) {
						//
						Util.add(textAndImages = ObjectUtils.getIfNull(textAndImages, ArrayList::new), textAndImage);
						//
					} // if
						//
				} // if
					//
			} // for
				//
			return textAndImages;
			//
		} // if
			//
		return null;
		//
	}

	private static byte[] toByteArray(final RenderedImage image, final String format) throws IOException {
		//
		if (image == null || format == null) {
			//
			return null;
			//
		} // if
			//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(image, format, baos);
			//
			return baos.toByteArray();
			//
		} // try
			//
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static Object getSelectedItem(final JComboBox<?> instance) {
		//
		final Field field = getFieldByName(Util.getClass(instance), "dataModel");
		//
		return field != null && Narcissus.getField(instance, field) != null ? instance.getSelectedItem() : null;
		//
	}

	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplierTrue,
			final Supplier<T> supplierFalse) {
		return condition ? Util.get(supplierTrue) : Util.get(supplierFalse);
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			@Nullable final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Nullable
	private static Image toImage(@Nullable final byte[] bs, final Consumer<IOException> consumer) {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			return testAndApply(Objects::nonNull, is, ImageIO::read, null);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static String textContent(@Nullable final ElementHandle instance) {
		return instance != null ? instance.textContent() : null;
	}

	@Nullable
	private static ElementHandle querySelector(@Nullable final ElementHandle instance, final String selector) {
		return instance != null ? instance.querySelector(selector) : null;
	}

	@Nullable
	private static IntStream sorted(@Nullable final IntStream instance) {
		return instance != null ? instance.sorted() : instance;
	}

	private static void setIcon(@Nullable final JLabel instance, final Icon icon) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Field f = getFieldByName(Util.getClass(instance), "objectLock");
		//
		if (f == null || Narcissus.getField(instance, f) != null) {
			//
			instance.setIcon(icon);
		} // if
			//
	}

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()) ? new JFrame()
				: Util.cast(JFrame.class, Narcissus.allocateInstance(JFrame.class));
		//
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		final Field f = getFieldByName(JFrame.class, "component");
		//
		final boolean gui = f == null || Narcissus.getField(jFrame, f) != null;
		//
		final VoiceManagerOjadAccentPanel instance = new VoiceManagerOjadAccentPanel();
		//
		instance.afterPropertiesSet();
		//
		if (gui) {
			//
			jFrame.add(instance);
			//
		} // if
			//
		pack(instance.window = jFrame);
		//
		if (gui) {
			//
			jFrame.setVisible(true);
			//
		} // if
			//
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Field f = getFieldByName(Util.getClass(instance), "objectLock");
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