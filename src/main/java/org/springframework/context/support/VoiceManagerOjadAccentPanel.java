package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.WindowConstants;
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
			add(tfText = new JTextField(), String.format("%1$s,%2$s", wrap, growx));
			//
			add(new JLabel());
			//
			add(btnExecute = new JButton("Execute"), wrap);
			//
			btnExecute.addActionListener(this);
			//
			add(new JLabel("Text And Image"));
			//
			add(jcbTextAndImage = new JComboBox<>(
					mcbmTextAndImage = new DefaultComboBoxModel<>(new TextAndImage[] { null })),
					String.format("%1$s,%2$s", wrap, growx));
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
									(int) getHeight(Util.getPreferredSize(tfText))));
							//
						} else {
							//
							// TODO
							//
							if (value == null) {
								//
								panel.setPreferredSize(new Dimension((int) preferredSize.getWidth(),
										Math.max((int) getHeight(Util.getPreferredSize(tfText)), 26)));
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
			add(new JLabel());
			//
			add(lblAccent = new JLabel());
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
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
										x -> URLEncoder.encode(x, StandardCharsets.UTF_8), null)));
				//
			} // if
				//
			Util.forEach(
					Util.map(sorted(Util.map(IntStream.range(1, Util.getSize(mcbmTextAndImage)), i -> -i)), i -> -i),
					i -> Util.removeElementAt(mcbmTextAndImage, i));
			//
			// TODO
			//
			final List<ElementHandle> ehs = querySelectorAll(page, ".katsuyo_accent");
			//
			final List<ElementHandle> words = querySelectorAll(page, "tr[id^=\"word\"]");
			//
			TextAndImage textAndImage = null;
			//
			if (IterableUtils.size(ehs) == 1) {
				//
				mcbmTextAndImage.addElement(textAndImage = new TextAndImage());
				//
				textAndImage.text = StringUtils.trim(textContent(querySelector(
						testAndApply(x -> IterableUtils.size(x) == 1, words, x -> IterableUtils.get(x, 0), null),
						".midashi")));
				//
				textAndImage.image = toImage(screenshot(IterableUtils.get(ehs, 0)), e -> {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				});
				//
				mcbmTextAndImage.setSelectedItem(textAndImage);
				//
			} else if (IterableUtils.size(words) == IterableUtils.size(ehs)) {
				//
				for (int i = 0; i < IterableUtils.size(words); i++) {
					//
					mcbmTextAndImage.addElement(textAndImage = new TextAndImage());
					//
					textAndImage.text = StringUtils
							.trim(textContent(querySelector(IterableUtils.get(words, i), ".midashi")));
					//
					textAndImage.image = toImage(screenshot(IterableUtils.get(ehs, i)), e -> {
						//
						LoggerUtil.error(LOG, e.getMessage(), e);
						//
					});
					//
				} // for
					//
			} // if
				//
			pack(window);
			//
		} else if (Objects.equals(source, jcbTextAndImage)) {
			//
			final TextAndImage textAndImage = Util.cast(TextAndImage.class, jcbTextAndImage.getSelectedItem());
			//
			setIcon(lblAccent,
					testAndApply(Objects::nonNull, getImage(textAndImage), ImageIcon::new, x -> new ImageIcon()));
			//
			pack(window);
			//
		} // if
			//
	}

	private static Image toImage(final byte[] bs, final Consumer<IOException> consumer) {
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
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() ? new JFrame()
				: Util.cast(JFrame.class, Narcissus.allocateInstance(JFrame.class));
		//
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		final Field f = getFieldByName(Util.getClass(JFrame.class), "component");
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