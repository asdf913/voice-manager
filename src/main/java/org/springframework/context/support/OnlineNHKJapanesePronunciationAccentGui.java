package org.springframework.context.support;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.factory.InitializingBean;

import com.github.hal4j.uritemplate.URIBuilder;
import com.google.common.reflect.Reflection;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.miginfocom.swing.MigLayout;

/**
 * @see <a href=
 *      "https://sakura-paris.org/dict/NHK%E6%97%A5%E6%9C%AC%E8%AA%9E%E7%99%BA%E9%9F%B3%E3%82%A2%E3%82%AF%E3%82%BB%E3%83%B3%E3%83%88%E8%BE%9E%E5%85%B8/">広辞苑無料検索
 *      NHK日本語発音アクセント辞典</a>
 */
public class OnlineNHKJapanesePronunciationAccentGui extends JFrame implements InitializingBean, ActionListener {

	private static final long serialVersionUID = 6227192813388400801L;

	private String url = null;

	private JTextComponent tfText = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnPlayAudio, btnCopyPitchAccentImage = null;

	private transient MutableComboBoxModel<Pronounication> mcbmPronounication = null;

	private OnlineNHKJapanesePronunciationAccentGui() {
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		if (!(getLayout() instanceof MigLayout)) {
			//
			setLayout(new MigLayout());
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		final String growx = "growx";
		//
		add(tfText = new JTextField(), String.format("%1$s,wmin %2$s", growx, "100px"));
		//
		final String wrap = "wrap";
		//
		add(btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2));
		//
		btnExecute.addActionListener(this);
		//
		add(new JLabel(""));
		//
		final JComboBox<Pronounication> jcbPronounication = new JComboBox<>(
				mcbmPronounication = new DefaultComboBoxModel<>());
		//
		final ListCellRenderer<?> render = jcbPronounication.getRenderer();
		//
		jcbPronounication.setRenderer(new ListCellRenderer<>() {

			@Override
			public Component getListCellRendererComponent(final JList<? extends Pronounication> list,
					final Pronounication value, final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				final BufferedImage pitchAccentImage = value != null ? value.pitchAccentImage : null;
				//
				if (pitchAccentImage != null) {
					//
					return OnlineNHKJapanesePronunciationAccentGui.getListCellRendererComponent(
							((ListCellRenderer) render), list, new ImageIcon(pitchAccentImage), index, isSelected,
							cellHasFocus);
					//
				} // if
					//
				return OnlineNHKJapanesePronunciationAccentGui.getListCellRendererComponent(((ListCellRenderer) render),
						list, new ImageIcon(), index, isSelected, cellHasFocus);
				//
			}

		});
		//
		add(jcbPronounication, growx);
		//
		add(btnPlayAudio = new JButton("Play"));
		//
		btnPlayAudio.addActionListener(this);
		//
		add(btnCopyPitchAccentImage = new JButton("Copy Pitch Accent Image"));
		//
		btnCopyPitchAccentImage.addActionListener(this);
		//
		pack();
		//
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static class Pronounication {

		private Map<String, String> audioUrls = null;

		private BufferedImage pitchAccentImage = null;

	}

	private static class IH implements InvocationHandler {

		private DataFlavor[] transferDataFlavors = null;

		private Object transferData = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Transferable) {
				//
				if (Objects.equals(methodName, "getTransferDataFlavors")) {
					//
					return transferDataFlavors;
					//
				} else if (Objects.equals(methodName, "getTransferData")) {
					//
					return transferData;
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
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			for (int i = (mcbmPronounication != null ? mcbmPronounication.getSize() : 0) - 1; i >= 0; i--) {
				//
				mcbmPronounication.removeElementAt(i);
				//
			} // for
				//
			final URIBuilder uriBuilder = testAndApply(Objects::nonNull, url, URIBuilder::basedOn, null);
			//
			if (uriBuilder != null) {
				//
				uriBuilder.relative(tfText != null ? tfText.getText() : null);
				//
			} // if
				//
			final URI uri = uriBuilder != null ? uriBuilder.toURI() : null;
			//
			try {
				//
				final URL u = uri != null ? uri.toURL() : null;
				//
				final String protocolAndHost = u != null ? String.join("://", u.getProtocol(), u.getHost()) : null;
				//
				final Document document = testAndApply(Objects::nonNull, u, x -> Jsoup.parse(x, 0), null);
				//
				final Elements elements = ElementUtil.select(document, "audio[title='発音図：']");
				//
				Pronounication pronounication = null;
				//
				Element element = null;
				//
				for (int i = 0; elements != null && i < elements.size(); i++) {
					//
					if ((element = elements.get(i)) == null) {
						//
						continue;
						//
					} // if
						//
					stream(((pronounication = new Pronounication()).audioUrls = getSrcMap(element)).entrySet())
							.forEach(x -> {
								if (x != null) {
									x.setValue(String.join("", protocolAndHost, x.getValue()));
								}
							});
					//
					pronounication.pitchAccentImage = createMergedBufferedImage(protocolAndHost, getImageSrcs(element));
					//
					if (mcbmPronounication != null) {
						//
						mcbmPronounication.addElement(pronounication);
						//
					} // if
						//
				} // for
					//
				pack();
				//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnPlayAudio)) {
			//
			final Pronounication pronounication = cast(Pronounication.class,
					mcbmPronounication != null ? mcbmPronounication.getSelectedItem() : null);
			//
			final Map<String, String> audioUrls = pronounication != null ? pronounication.audioUrls : null;
			//
			final Set<Entry<String, String>> entrySet = audioUrls != null ? audioUrls.entrySet() : null;
			//
			if (entrySet != null && entrySet.iterator() != null) {
				//
				InputStream is = null;
				//
				for (final Entry<String, String> entry : entrySet) {
					//
					if (entry == null || !Objects.equals("audio/wav", entry.getKey())) {
						//
						continue;
						//
					} // if
						//
					try {
						//
						new Player(is = (new URL(entry.getValue()).openStream())).play();
						//
					} catch (final JavaLayerException | IOException e) {
						//
						TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
						//
					} finally {
						//
						IOUtils.closeQuietly(is);
						//
					} // try
						//
					break;
					//
				} // for
					//
			} // if
				//
		} else if (Objects.equals(source, btnCopyPitchAccentImage)) {
			//
			final Pronounication pronounication = cast(Pronounication.class,
					mcbmPronounication != null ? mcbmPronounication.getSelectedItem() : null);
			//
			final BufferedImage pitchAccentImage = pronounication != null ? pronounication.pitchAccentImage : null;
			//
			if (pitchAccentImage != null) {
				//
				final Toolkit toolkit = Toolkit.getDefaultToolkit();
				//
				final Clipboard clipboard = toolkit != null ? toolkit.getSystemClipboard() : null;
				//
				final IH ih = new IH();
				//
				ih.transferDataFlavors = new DataFlavor[] { DataFlavor.imageFlavor };
				//
				ih.transferData = pitchAccentImage;
				//
				if (clipboard != null) {
					//
					clipboard.setContents(Reflection.newProxy(Transferable.class, ih), null);
					//
				} // if
					//
			} // if
				//
		} // if
			//
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static Map<String, String> getSrcMap(final Element input) {
		//
		Map<String, String> map = null;
		//
		final Elements children = input != null ? input.children() : null;
		//
		Element element = null;
		//
		for (int i = 0; children != null && i < children.size(); i++) {
			//
			if ((element = children.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) != null) {
				//
				map.put(element.attr("type"), element.attr("src"));
				//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	private static List<String> getImageSrcs(final Element element) {
		//
		List<String> srcs = null;
		//
		Element temp = element, nextElement = null;
		//
		while ((nextElement = temp != null ? temp.nextElementSibling() : null) != null
				&& !Objects.equals("audio", nextElement.tagName())) {
			//
			if ((srcs = ObjectUtils.getIfNull(srcs, ArrayList::new)) != null) {
				//
				srcs.add(nextElement.attr("src"));
				//
			} // if
				//
			temp = nextElement;
			//
		} // while
			//
		return srcs;
		//
	}

	private static BufferedImage createMergedBufferedImage(final String urlString, final List<String> srcs) {
		//
		final FailableStream<String> fs = new FailableStream<>(stream(srcs));
		//
		final List<BufferedImage> bis = fs != null && fs.stream() != null
				? fs.map(x -> ImageIO.read(new URL(String.join("/", urlString, x)))).collect(Collectors.toList())
				: null;
		//
		BufferedImage result = null;
		//
		Graphics graphics = null;
		//
		final AtomicInteger position = new AtomicInteger(0);
		//
		BufferedImage bi = null;
		//
		for (int i = 0; bis != null && i < bis.size(); i++) {
			//
			if ((bi = bis.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (result == null) {
				//
				result = new BufferedImage(
						//
						// total width
						//
						stream(bis).mapToInt(x -> intValue(getWidth(x), 0)).reduce(Integer::sum).orElse(0)
						//
						// max height
						//
						, stream(bis).mapToInt(x -> intValue(getHeight(x), 0)).reduce(Integer::max).orElse(0)
						//
						, BufferedImage.TYPE_INT_ARGB);
				//
			} // if
				//
			if (graphics == null && (graphics = getGraphics(result)) == null) {
				//
				continue;
				//
			} // if
				//
			graphics.drawImage(bi, position != null ? position.getAndAdd(intValue(getWidth(bi), 0)) : 0, 0, null);
			//
		} // for
			//
		return result;
		//
	}

	private static Graphics getGraphics(final Image instance) {
		return instance != null ? instance.getGraphics() : null;
	}

	private static Integer getWidth(final RenderedImage instance) {
		return instance != null ? Integer.valueOf(instance.getWidth()) : null;
	}

	private static Integer getHeight(final RenderedImage instance) {
		return instance != null ? Integer.valueOf(instance.getHeight()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

}