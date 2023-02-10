package org.springframework.context.support;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Proxy;

import com.github.hal4j.uritemplate.URIBuilder;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
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

	@Note("Play Audio")
	private AbstractButton btnPlayAudio = null;

	@Note("Save Audio")
	private AbstractButton btnSaveAudio = null;

	@Note("Copy Pitch Accent Image")
	private AbstractButton btnCopyPitchAccentImage = null;

	private AbstractButton btnSavePitchAccentImage = null;

	private transient MutableComboBoxModel<Pronounication> mcbmPronounication = null;

	private transient MutableComboBoxModel<String> mcbmAudioFormat = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private JComboBox<Pronounication> jcbPronounication = null;

	private JLabel jlSavePitchAccentImage = null;

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
			// If "java.awt.Container.component" is null, return this method immediately
			//
			// The below check is for "-Djava.awt.headless=true"
			//
		final List<Field> fs = stream(FieldUtils.getAllFieldsList(getClass(this)))
				.filter(f -> f != null && Objects.equals(f.getName(), "component")).toList();
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null && Narcissus.getObjectField(this, f) == null) {
			//
			return;
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		final String growx = "growx";
		//
		add(tfText = new JTextField(), String.format("%1$s,wmin %2$s,span %3$s", growx, "100px", 2 + 1));
		//
		final String wrap = "wrap";
		//
		add(btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2));
		//
		btnExecute.addActionListener(this);
		//
		add(new JLabel(""));
		//
		(jcbPronounication = new JComboBox<>(mcbmPronounication = new DefaultComboBoxModel<>()))
				.addActionListener(this);
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
		add(jcbPronounication, String.format("%1$s,%2$s,span %3$s", wrap, growx, 2 + 1));
		//
		add(new JLabel("Audio"));
		//
		add(btnPlayAudio = new JButton("Play"));
		//
		add(new JComboBox<>(mcbmAudioFormat = new DefaultComboBoxModel<>()), String.format("%1$s,span %2$s", growx, 2));
		//
		add(btnSaveAudio = new JButton("Save"), wrap);
		//
		add(new JLabel("Image"));
		//
		add(btnCopyPitchAccentImage = new JButton("Copy Image"), String.format("span %1$s", 2));
		//
		// Image Format
		//
		final Map<?, ?> imageWriterSpis = cast(Map.class,
				testAndApply(
						Objects::nonNull, get(
								cast(Map.class,
										Narcissus.getObjectField(IIORegistry.getDefaultInstance(),
												getDeclaredField(ServiceRegistry.class, "categoryMap"))),
								ImageWriterSpi.class),
						x -> Narcissus.getField(x, getDeclaredField(getClass(x), "map")), null));
		//
		final List<String> classNames = testAndApply(
				Objects::nonNull, stream(imageWriterSpis.keySet())
						.map(x -> x instanceof Class<?> ? ((Class<?>) x).getName() : null).toList(),
				ArrayList::new, null);
		//
		final String commonPrefix = StringUtils.getCommonPrefix(classNames.toArray(new String[] {}));
		//
		for (int i = 0; classNames != null && i < classNames.size(); i++) {
			//
			classNames.set(i, StringUtils
					.substringBefore(StringUtils.replace(IterableUtils.get(classNames, i), commonPrefix, ""), '.'));
			//
		} // if
			//
		final MutableComboBoxModel<String> mcbm = new DefaultComboBoxModel<>();
		//
		add(new JComboBox<>(mcbm), growx);
		//
		if (classNames != null) {
			//
			classNames.forEach(x -> {
				//
				mcbm.addElement(x);
				//
			});
			//
		} // if
			//
		cbmImageFormat = mcbm;
		//
		add(btnSavePitchAccentImage = new JButton("Save Image"));
		//
		add(jlSavePitchAccentImage = new JLabel());
		//
		addActionListener(this, btnPlayAudio, btnSaveAudio, btnCopyPitchAccentImage, btnSavePitchAccentImage);
		//
		pack();
		//
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... bs) {
		//
		AbstractButton b = null;
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if ((b = bs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			b.addActionListener(actionListener);
			//
		} // for
			//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
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
			// Remove all element(s) in "mcbmPronounication"
			//
			forEach(reverseRange(0, getSize(mcbmPronounication)), i -> removeElementAt(mcbmPronounication, i));
			//
			// Remove all element(s) in "mcbmAudioFormat"
			//
			forEach(reverseRange(0, getSize(mcbmAudioFormat)), i -> removeElementAt(mcbmAudioFormat, i));
			//
			final URIBuilder uriBuilder = testAndApply(Objects::nonNull, url, URIBuilder::basedOn, null);
			//
			try {
				//
				final URL u = toURL(toURI(relative(uriBuilder, getText(tfText))));
				//
				final String protocolAndHost = testAndApply(Objects::nonNull, u,
						x -> String.join("://", getProtocol(x), getHost(u)), null);
				//
				final Document document = testAndApply(Objects::nonNull, u, x -> Jsoup.parse(x, 0), null);
				//
				final Elements elements = ElementUtil.select(document, "audio[title='発音図：']");
				//
				Pronounication pronounication = null;
				//
				Element element = null;
				//
				for (int i = 0; i < IterableUtils.size(elements); i++) {
					//
					if ((element = IterableUtils.get(elements, i)) == null) {
						//
						continue;
						//
					} // if
						//
					stream(entrySet(((pronounication = new Pronounication()).audioUrls = getSrcMap(element))))
							.forEach(x -> {
								setValue(x, String.join("", protocolAndHost, getValue(x)));
							});
					//
					pronounication.pitchAccentImage = createMergedBufferedImage(protocolAndHost, getImageSrcs(element));
					//
					addElement(mcbmPronounication, pronounication);
					//
				} // for
					//
				if (!GraphicsEnvironment.isHeadless()) {
					//
					pack();
					//
				} // if
					//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnPlayAudio)) {
			//
			playAudio(cast(Pronounication.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, btnSaveAudio)) {
			//
			final Pronounication pronounication = cast(Pronounication.class, getSelectedItem(mcbmPronounication));
			//
			final Map<String, String> audioUrls = testAndApply(Objects::nonNull,
					pronounication != null ? pronounication.audioUrls : null, LinkedHashMap::new, null);
			//
			final Object audioFormat = getSelectedItem(mcbmAudioFormat);
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			if (containsKey(audioUrls, audioFormat)) {
				//
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					//
					try {
						//
						saveFile(jfc.getSelectedFile(), get(audioUrls, audioFormat));
						//
					} catch (final IOException e) {
						//
						TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
						//
					} // try
						//
				} // if
					//
				return;
				//
			} // if
				//
			final Set<Entry<String, String>> entrySet = entrySet(audioUrls);
			//
			if (iterator(entrySet) != null) {
				//
				for (final String url : audioUrls.values()) {
					//
					if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						//
						try {
							//
							saveFile(jfc.getSelectedFile(), url);
							//
						} catch (final IOException e) {
							//
							TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
							//
						} // try
							//
					} // if
						//
					return;
					//
				} // for
					//
			} // if
				//
		} else if (Objects.equals(source, btnCopyPitchAccentImage)) {
			//
			setPitchAccentImageToSystemClipboardContents(
					cast(Pronounication.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, btnSavePitchAccentImage)) {
			//
			savePitchAccentImage(cast(Pronounication.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, jcbPronounication)) {
			//
			forEach(reverseRange(0, getSize(mcbmAudioFormat)), i -> removeElementAt(mcbmAudioFormat, i));
			//
			final Pronounication pronounication = cast(Pronounication.class, getSelectedItem(mcbmPronounication));
			//
			final Map<String, String> audioUrls = pronounication != null ? pronounication.audioUrls : null;
			//
			if (MapUtils.isNotEmpty(audioUrls)) {
				//
				addElement(mcbmAudioFormat, null);
				//
				audioUrls.forEach((k, v) -> addElement(mcbmAudioFormat, k));
				//
			} // if
				//
		} // if
			//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static void saveFile(final File file, final String url) throws IOException {
		//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, URL::new, null))) {
			//
			if (file != null) {
				//
				FileUtils.copyInputStreamToFile(is, file);
				//
			} // if
				//
		} // try
			//
	}

	private static void setText(final JLabel instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static void playAudio(final Pronounication pronounication) {
		//
		final Set<Entry<String, String>> entrySet = entrySet(pronounication != null ? pronounication.audioUrls : null);
		//
		if (iterator(entrySet) != null) {
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				try {
					//
					if (playAudio(getKey(entry), getValue(entry)) != null) {
						//
						break;
						//
					} // if
				} catch (final JavaLayerException | IOException e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	private static Object playAudio(final Object key, final String value) throws JavaLayerException, IOException {
		//
		if (Objects.equals("audio/wav", key)) {
			//
			return null;
			//
		} // if
			//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, value, URL::new, null))) {
			//
			play(testAndApply(Objects::nonNull, is, Player::new, null));
			//
			return "";
			//
		} // try
			//
	}

	private static InputStream openStream(final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	private static void play(final Player instance) throws JavaLayerException {
		if (instance != null) {
			instance.play();
		}
	}

	private static void setPitchAccentImageToSystemClipboardContents(final Pronounication pronounication) {
		//
		final BufferedImage pitchAccentImage = pronounication != null ? pronounication.pitchAccentImage : null;
		//
		Object raster = null;
		//
		try {
			//
			raster = testAndApply(Objects::nonNull, pitchAccentImage,
					x -> Narcissus.getObjectField(x, getDeclaredField(getClass(x), "raster")), null);
			//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		if (pitchAccentImage != null && raster != null) {
			//
			final IH ih = new IH();
			//
			ih.transferDataFlavors = new DataFlavor[] { DataFlavor.imageFlavor };
			//
			ih.transferData = pitchAccentImage;
			//
			if (forName("org.junit.jupiter.api.Test") == null) {
				//
				setContents(getSystemClipboard(Toolkit.getDefaultToolkit()),
						Reflection.newProxy(Transferable.class, ih), null);
				//
			} // if
				//
		} // if
			//
	}

	private void savePitchAccentImage(final Pronounication pronounication) {
		//
		final BufferedImage pitchAccentImage = pronounication != null ? pronounication.pitchAccentImage : null;
		//
		Object raster = null;
		//
		try {
			//
			raster = testAndApply(Objects::nonNull, pitchAccentImage,
					x -> Narcissus.getObjectField(x, getDeclaredField(getClass(x), "raster")), null);
			//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		if (pitchAccentImage != null && raster != null) {
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			if (!GraphicsEnvironment.isHeadless() && jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					setText(jlSavePitchAccentImage, ImageIO.write(pitchAccentImage,
							toString(getSelectedItem(cbmImageFormat)), jfc.getSelectedFile()) ? "Saved" : "Not Saved");
					//
					pack();
					//
				} catch (final IOException e) {
					//
					TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
					//
				} // try
					//
			} // if
				//
		} // if
			//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static void forEach(final IntStream instance, final IntConsumer action) {
		if (instance != null && (action != null || Proxy.isProxyClass(getClass(instance)))) {
			instance.forEach(action);
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	/**
	 * @see <a href="https://stackoverflow.com/a/24011264">list - Java 8 stream
	 *      reverse order - Stack Overflow</a>
	 */
	private static IntStream reverseRange(final int from, final int to) {
		return map(IntStream.range(from, to), i -> to - i + from - 1);
	}

	private static IntStream map(final IntStream instance, final IntUnaryOperator mapper) {
		return instance != null ? instance.map(mapper) : instance;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static String getHost(final URL instance) {
		return instance != null ? instance.getHost() : null;
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private static <E> Iterator<E> iterator(final Iterable<E> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static int getSize(final ListModel<?> instance) {
		return instance != null ? instance.getSize() : 0;
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static <E> void addElement(final MutableComboBoxModel<E> instance, final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	private static void removeElementAt(final MutableComboBoxModel<?> instance, final int index) {
		if (instance != null) {
			instance.removeElementAt(index);
		}
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> void setValue(final Entry<?, V> instance, final V value) {
		if (instance != null) {
			instance.setValue(value);
		}
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	private static URIBuilder relative(final URIBuilder instance, final Object... pathSegments) {
		return instance != null ? instance.relative(pathSegments) : null;
	}

	private static URI toURI(final URIBuilder instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
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
		final Elements children = ElementUtil.children(input);
		//
		Element element = null;
		//
		for (int i = 0; i < IterableUtils.size(children); i++) {
			//
			if ((element = IterableUtils.get(children, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if ((map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) != null) {
				//
				map.put(ElementUtil.attr(element, "type"), ElementUtil.attr(element, "src"));
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
		while ((nextElement = ElementUtil.nextElementSibling(temp)) != null
				&& !Objects.equals("audio", ElementUtil.tagName(nextElement))) {
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
		for (int i = 0; i < IterableUtils.size(bis); i++) {
			//
			if ((bi = IterableUtils.get(bis, i)) == null) {
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
						, BufferedImage.TYPE_INT_RGB);
				//
			} // if
				//
			if (graphics == null) {
				//
				graphics = getGraphics(result);
				//
			} // if
				//
			drawImage(graphics, bi, position != null ? position.getAndAdd(intValue(getWidth(bi), 0)) : 0, 0, null);
			//
		} // for
			//
		return result;
		//
	}

	private static Graphics getGraphics(final Image instance) {
		return instance != null ? instance.getGraphics() : null;
	}

	private static boolean drawImage(final Graphics instance, final Image image, final int x, final int y,
			final ImageObserver observer) {
		return instance != null && instance.drawImage(image, x, y, observer);
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