package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
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
import org.apache.commons.lang3.function.OnlineNHKJapanesePronunciationsAccentFailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;

import domain.Pronunciation;
import io.github.toolfactory.narcissus.Narcissus;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.miginfocom.swing.MigLayout;

/**
 * @see <a href=
 *      "https://sakura-paris.org/dict/NHK%E6%97%A5%E6%9C%AC%E8%AA%9E%E7%99%BA%E9%9F%B3%E3%82%A2%E3%82%AF%E3%82%BB%E3%83%B3%E3%83%88%E8%BE%9E%E5%85%B8/">広辞苑無料検索
 *      NHK日本語発音アクセント辞典</a>
 */
@Title("NHK日本語発音アクセント辞典")
public class OnlineNHKJapanesePronunciationAccentGui extends JFrame
		implements InitializingBean, ActionListener, EnvironmentAware {

	private static final long serialVersionUID = 6227192813388400801L;

	private Integer bufferedImageType = null;

	private transient PropertyResolver propertyResolver = null;

	private JTextComponent tfText = null;

	private transient OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Group {
		String value();
	}

	@Note("Execute")
	@Group("LastComponentInRow")
	private AbstractButton btnExecute = null;

	@Note("Play Audio")
	private AbstractButton btnPlayAudio = null;

	@Note("Save Audio")
	@Group("LastComponentInRow")
	private AbstractButton btnSaveAudio = null;

	@Note("Copy Pitch Accent Image")
	private AbstractButton btnCopyPitchAccentImage = null;

	@Group("LastComponentInRow")
	private AbstractButton btnSavePitchAccentImage = null;

	private transient MutableComboBoxModel<Pronunciation> mcbmPronounication = null;

	private transient MutableComboBoxModel<String> mcbmAudioFormat = null;

	private transient ComboBoxModel<String> cbmImageFormat = null;

	private JComboBox<Pronunciation> jcbPronounication = null;

	private JLabel jlSavePitchAccentImage = null;

	private List<String> imageFormatOrders = null;

	private OnlineNHKJapanesePronunciationAccentGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setBufferedImageType(final Integer bufferedImageType) {
		this.bufferedImageType = bufferedImageType;
	}

	public void setOnlineNHKJapanesePronunciationsAccentFailableFunction(
			final OnlineNHKJapanesePronunciationsAccentFailableFunction onlineNHKJapanesePronunciationsAccentFailableFunction) {
		this.onlineNHKJapanesePronunciationsAccentFailableFunction = onlineNHKJapanesePronunciationsAccentFailableFunction;
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@SuppressWarnings("java:S1612")
	public void setImageFormatOrders(@Nullable final Object object) {
		//
		IValue0<List<String>> value = null;
		//
		final Class<?> clz = Util.getClass(object);
		//
		if (object == null) {
			//
			value = Unit.with(null);
			//
		} else if (object instanceof List) {
			//
			value = Unit.with(toList(Util.map(Util.stream(((List<?>) object)), x -> Util.toString(x))));
			//
		} else if (object instanceof Iterable) {
			//
			value = Unit.with(toList(Util.map(StreamSupport.stream(((Iterable<?>) object).spliterator(), false),
					x -> Util.toString(x))));
			//
		} else if (clz != null && clz.isArray()) {
			//
			if (Objects.equals(clz.getComponentType(), Character.TYPE)) {
				//
				setImageFormatOrders(new String((char[]) object));
				//
				return;
				//
			} // if
				//
			value = Unit.with(
					toList(Util.map(IntStream.range(0, Array.getLength(object)).mapToObj(i -> Array.get(object, i)),
							x -> Util.toString(x))));
			//
		} else if (object instanceof String string) {
			//
			try {
				//
				final Object obj = ObjectMapperUtil.readValue(new ObjectMapper(), string, Object.class);
				//
				if (obj != null) {
					//
					setImageFormatOrders(obj);
					//
					return;
					//
				} // if
					//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
			value = Unit.with(Collections.singletonList(string));
			//
		} else if (Boolean.logicalOr(object instanceof Number, object instanceof Boolean)) {
			//
			value = Unit.with(Collections.singletonList(Util.toString(object)));
			//
		} // if
			//
		if (value != null) {
			//
			this.imageFormatOrders = IValue0Util.getValue0(value);
			//
		} else {
			//
			throw new UnsupportedOperationException(Util.toString(clz));
			//
		} // if
			//
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
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1,
				toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
						x -> Objects.equals(Util.getName(x), "component"))),
				x -> IterableUtils.get(x, 0), null);
		//
		final boolean isGui = f == null || Narcissus.getObjectField(this, f) != null;
		//
		final Predicate<Component> predicate = Predicates.always(isGui, null);
		//
		testAndAccept(predicate, new JLabel("Text"), this::add);
		//
		final String growx = "growx";
		//
		final BiPredicate<Component, Object> biPredicate = Predicates.biAlways(isGui, null);
		//
		testAndAccept(biPredicate,
				tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui.text")),
				String.format("%1$s,wmin %2$s,span %3$s", growx, "100px", 3), this::add);
		//
		final String wrap = "wrap";
		//
		testAndAccept(biPredicate, btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2),
				this::add);
		//
		btnExecute.addActionListener(this);
		//
		testAndAccept(predicate, new JLabel(""), this::add);
		//
		(jcbPronounication = new JComboBox<>(mcbmPronounication = new DefaultComboBoxModel<>()))
				.addActionListener(this);
		//
		final ListCellRenderer<?> render = jcbPronounication.getRenderer();
		//
		jcbPronounication.setRenderer(new ListCellRenderer<>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends Pronunciation> list,
					final Pronunciation value, final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				final BufferedImage pitchAccentImage = getPitchAccentImage(value);
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
		testAndAccept(biPredicate, jcbPronounication, String.format("%1$s,%2$s,span %3$s", wrap, growx, 3), this::add);
		//
		testAndAccept(predicate, new JLabel("Audio"), this::add);
		//
		testAndAccept(predicate, btnPlayAudio = new JButton("Play"), this::add);
		//
		testAndAccept(biPredicate, new JComboBox<>(mcbmAudioFormat = new DefaultComboBoxModel<>()),
				String.format("%1$s,span %2$s", growx, 2), this::add);
		//
		testAndAccept(biPredicate, btnSaveAudio = new JButton("Save"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel("Image"), this::add);
		//
		testAndAccept(biPredicate, btnCopyPitchAccentImage = new JButton("Copy Image"), String.format("span %1$s", 2),
				this::add);
		//
		// Image Format
		//
		final List<String> classNames = getImageFormats(imageFormatOrders);
		//
		// Filter out unsupported image format in "Image Format" drop down list (i.e.
		// "javax.imageio.ImageIO.write(java.awt.image.RenderedImage,java.lang.String,java.io.OutputStream)"
		//
		if (bufferedImageType != null) {
			//
			final BufferedImage bi = new BufferedImage(1, 1, bufferedImageType.intValue());
			//
			String className = null;
			//
			for (int i = IterableUtils.size(classNames) - 1; i >= 0; i--) {
				//
				try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					//
					if ((className = IterableUtils.get(classNames, i)) != null && !ImageIO.write(bi, className, baos)) {
						//
						remove(classNames, i);
						//
					} // if
						//
				} // try
					//
			} // for
				//
		} // if
			//
		final MutableComboBoxModel<String> mcbm = new DefaultComboBoxModel<>();
		//
		testAndAccept(biPredicate, new JComboBox<>(mcbm), growx, this::add);
		//
		forEach(classNames, mcbm::addElement);
		//
		cbmImageFormat = mcbm;
		//
		testAndAccept(predicate, btnSavePitchAccentImage = new JButton("Save Image"), this::add);
		//
		testAndAccept(predicate, jlSavePitchAccentImage = new JLabel(), this::add);
		//
		addActionListener(this, btnPlayAudio, btnSaveAudio, btnCopyPitchAccentImage, btnSavePitchAccentImage);
		//
		// Get the max "width" of "java.awt.Component.getPreferredSize()" of
		// "java.awt.Component" from field(s) annotated by
		// "org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Group"
		//
		final FailableStream<Field> fs = new FailableStream<>(
				Util.filter(testAndApply(Objects::nonNull, getClass().getDeclaredFields(), Arrays::stream, null),
						x -> Objects.equals(value(testAndApply(y -> isAnnotationPresent(y, Group.class), x,
								y -> getAnnotation(y, Group.class), null)), "LastComponentInRow")));
		//
		final Collection<Component> cs = FailableStreamUtil.map(fs, x -> cast(Component.class, get(x, this)))
				.collect(Collectors.toList());
		//
		final Double maxPreferredSizeWidth = Util
				.orElse(max(Util.map(Util.stream(cs), x -> getWidth(getPreferredSize(x))), ObjectUtils::compare), null);
		//
		forEach(cs, c -> {
			//
			final Dimension pd = getPreferredSize(c);
			//
			if (pd != null && maxPreferredSizeWidth != null) {
				//
				setPreferredSize(c, new Dimension(maxPreferredSizeWidth.intValue(), (int) pd.getHeight()));
				//
			} // if
				//
		});
		//
		testAndRun(isGui, this::pack);
		//
	}

	private static void remove(@Nullable final List<?> instance, final int index) {
		if (instance != null) {
			instance.remove(index);
		}

	}

	private static void testAndRun(final boolean b, @Nullable final Runnable runnable) {
		if (b && runnable != null) {
			runnable.run();
		}
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (predicate != null && predicate.test(t, u) && consumer != null) {
			consumer.accept(t, u);
		}
	}

	@Nullable
	private static <T> Optional<T> max(@Nullable final Stream<T> instance,
			@Nullable final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || comparator != null)
				? instance.max(comparator)
				: null;
		//
	}

	private static void setPreferredSize(@Nullable final Component instance, final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
		}
	}

	@Nullable
	private static BufferedImage getPitchAccentImage(@Nullable final Pronunciation instnace) {
		return instnace != null ? instnace.getPitchAccentImage() : null;
	}

	@Nullable
	private static List<String> getImageFormats(final List<?> imageFormatOrders) throws NoSuchFieldException {
		//
		final Map<?, ?> imageWriterSpis = cast(Map.class,
				testAndApply(
						Objects::nonNull, get(
								cast(Map.class,
										Narcissus.getObjectField(IIORegistry.getDefaultInstance(),
												getDeclaredField(ServiceRegistry.class, "categoryMap"))),
								ImageWriterSpi.class),
						x -> Narcissus.getField(x, getDeclaredField(Util.getClass(x), "map")), null));
		//
		final List<String> classNames = testAndApply(Objects::nonNull,
				toList(Util.map(Util.stream(imageWriterSpis != null ? imageWriterSpis.keySet() : null),
						x -> getName(cast(Class.class, x)))),
				ArrayList::new, null);
		//
		final String commonPrefix = StringUtils.getCommonPrefix(toArray(classNames, new String[] {}));
		//
		for (int i = 0; classNames != null && i < classNames.size(); i++) {
			//
			classNames.set(i, StringUtils
					.substringBefore(StringUtils.replace(IterableUtils.get(classNames, i), commonPrefix, ""), '.'));
			//
		} // if
			//
		sort(classNames, createImageFormatComparator(imageFormatOrders));
		//
		return classNames;
		//
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	@Nullable
	private static Dimension getPreferredSize(@Nullable final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

	@Nullable
	private static Double getWidth(@Nullable final Dimension2D instance) {
		return instance != null ? Double.valueOf(instance.getWidth()) : null;
	}

	@Nullable
	private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Nullable
	private static String value(@Nullable final Group instance) {
		return instance != null ? instance.value() : null;
	}

	private static boolean isAnnotationPresent(@Nullable final AnnotatedElement instance,
			@Nullable final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
	}

	@Nullable
	private static <T extends Annotation> T getAnnotation(@Nullable final AnnotatedElement instance,
			@Nullable final Class<T> annotationClass) {
		return instance != null && annotationClass != null ? instance.getAnnotation(annotationClass) : null;
	}

	private static Comparator<String> createImageFormatComparator(final List<?> imageFormatOrders) {
		//
		return (a, b) -> {
			//
			final int ia = imageFormatOrders != null ? imageFormatOrders.indexOf(a) : -1;
			//
			final int ib = imageFormatOrders != null ? imageFormatOrders.indexOf(b) : -1;
			//
			if (ia >= 0 && ib >= 0) {
				//
				return Integer.compare(ia, ib);
				//
			} else if (ia >= 0) {
				//
				return -1;
				//
			} else if (ib >= 0) {
				//
				return 1;
				//
			} // if
				//
			return ObjectUtils.compare(a, b);
			//
		};
		//
	}

	@Nullable
	private static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <E> void sort(@Nullable final List<E> instance, @Nullable final Comparator<? super E> comparator) {
		//
		if (instance != null
				&& (Proxy.isProxyClass(Util.getClass(instance)) || (instance.size() > 1 && comparator != null))) {
			//
			instance.sort(comparator);
			//
		} // if
			//
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... bs) {
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

	@Nullable
	private static <V> V get(@Nullable final Map<?, V> instance, @Nullable final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static class IH implements InvocationHandler {

		private DataFlavor[] transferDataFlavors = null;

		private Object transferData = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
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
		final boolean headless = GraphicsEnvironment.isHeadless();
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
			try {
				//
				final List<Pronunciation> pronounications = FailableFunctionUtil
						.apply(onlineNHKJapanesePronunciationsAccentFailableFunction, getText(tfText));
				//
				forEach(pronounications, x -> addElement(mcbmPronounication, x));
				//
				if (!headless) {
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
			playAudio(cast(Pronunciation.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, btnSaveAudio)) {
			//
			saveAudio(headless, cast(Pronunciation.class, getSelectedItem(mcbmPronounication)),
					getSelectedItem(mcbmAudioFormat));
			//
		} else if (Objects.equals(source, btnCopyPitchAccentImage)) {
			//
			setPitchAccentImageToSystemClipboardContents(
					cast(Pronunciation.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, btnSavePitchAccentImage)) {
			//
			savePitchAccentImage(cast(Pronunciation.class, getSelectedItem(mcbmPronounication)));
			//
		} else if (Objects.equals(source, jcbPronounication)) {
			//
			pronounicationChanged(cast(Pronunciation.class, getSelectedItem(mcbmPronounication)), mcbmAudioFormat);
			//
		} // if
			//
	}

	private static boolean containsKey(@Nullable final Map<?, ?> instance, @Nullable final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static void saveFile(@Nullable final File file, final String url) throws Exception {
		//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null))) {
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

	private static void setText(@Nullable final JLabel instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setForeground(@Nullable final Component instance, final Color color) {
		if (instance != null) {
			instance.setForeground(color);
		}
	}

	private static void playAudio(@Nullable final Pronunciation pronunciation) {
		//
		final Set<Entry<String, String>> entrySet = entrySet(
				pronunciation != null ? pronunciation.getAudioUrls() : null);
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
				} catch (final Exception e) {
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

	@Nullable
	private static Object playAudio(@Nullable final Object key, @Nullable final String value) throws Exception {
		//
		if (Objects.equals("audio/wav", key)) {
			//
			return null;
			//
		} // if
			//
		try (final InputStream is = openStream(testAndApply(Objects::nonNull, value, x -> new URI(x).toURL(), null))) {
			//
			play(testAndApply(Objects::nonNull, is, Player::new, null));
			//
			return "";
			//
		} // try
			//
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		//
		// Check if "handler" field in "java.net.URL" class is null or not
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, toList(Util
				.filter(Arrays.stream(URL.class.getDeclaredFields()), x -> Objects.equals(Util.getName(x), "handler"))),
				x -> IterableUtils.get(x, 0), null);
		//
		if (instance != null && f != null && Narcissus.getObjectField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.openStream() : null;
		//
	}

	private static void play(@Nullable final Player instance) throws JavaLayerException {
		if (instance != null) {
			instance.play();
		}
	}

	private static void saveAudio(final boolean headless, @Nullable final Pronunciation pronunciation,
			@Nullable final Object audioFormat) {
		//
		final Map<String, String> audioUrls = testAndApply(Objects::nonNull,
				pronunciation != null ? pronunciation.getAudioUrls() : null, LinkedHashMap::new, null);
		//
		final JFileChooser jfc = new JFileChooser(".");
		//
		if (containsKey(audioUrls, audioFormat)) {
			//
			if (!headless && jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				try {
					//
					saveFile(jfc.getSelectedFile(), get(audioUrls, audioFormat));
					//
				} catch (final Exception e) {
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
		saveAudio(headless, jfc, audioUrls != null ? audioUrls.values() : null);
		//
	}

	private static void saveAudio(final boolean headless, @Nullable final JFileChooser jfc,
			@Nullable final Iterable<String> audioUrls) {
		//
		if (iterator(audioUrls) != null) {
			//
			for (final String u : audioUrls) {
				//
				if (!headless && jfc != null && jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					//
					try {
						//
						saveFile(jfc.getSelectedFile(), u);
						//
					} catch (final Exception e) {
						//
						TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
						//
					} // try
						//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static void setPitchAccentImageToSystemClipboardContents(@Nullable final Pronunciation pronunciation) {
		//
		final BufferedImage pitchAccentImage = getPitchAccentImage(pronunciation);
		//
		Object raster = null;
		//
		try {
			//
			raster = testAndApply(Objects::nonNull, pitchAccentImage,
					x -> Narcissus.getObjectField(x, getDeclaredField(Util.getClass(x), "raster")), null);
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

	private void savePitchAccentImage(@Nullable final Pronunciation pronunciation) {
		//
		final BufferedImage pitchAccentImage = getPitchAccentImage(pronunciation);
		//
		Object raster = null;
		//
		try {
			//
			raster = testAndApply(Objects::nonNull, pitchAccentImage,
					x -> Narcissus.getObjectField(x, getDeclaredField(Util.getClass(x), "raster")), null);
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
					final boolean result = ImageIO.write(pitchAccentImage,
							Util.toString(getSelectedItem(cbmImageFormat)), jfc.getSelectedFile());
					//
					setText(jlSavePitchAccentImage, iif(result, "Saved", "Not Saved"));
					//
					setForeground(jlSavePitchAccentImage, iif(result, Color.GREEN, Color.RED));
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

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static void pronounicationChanged(@Nullable final Pronunciation pronunciation,
			final MutableComboBoxModel<String> mcbmAudioFormat) {
		//
		forEach(reverseRange(0, getSize(mcbmAudioFormat)), i -> removeElementAt(mcbmAudioFormat, i));
		//
		final Map<String, String> audioUrls = pronunciation != null ? pronunciation.getAudioUrls() : null;
		//
		if (MapUtils.isNotEmpty(audioUrls)) {
			//
			addElement(mcbmAudioFormat, null);
			//
			audioUrls.forEach((k, v) -> addElement(mcbmAudioFormat, k));
			//
		} // if
			//
	}

	@Nullable
	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	@Nullable
	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static <T> void forEach(@Nullable final Iterable<T> instance, @Nullable final Consumer<? super T> action) {
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		}
	}

	private static void forEach(@Nullable final IntStream instance, @Nullable final IntConsumer action) {
		if (instance != null && (action != null || Proxy.isProxyClass(Util.getClass(instance)))) {
			instance.forEach(action);
		}
	}

	/**
	 * @see <a href="https://stackoverflow.com/a/24011264">list - Java 8 stream
	 *      reverse order - Stack Overflow</a>
	 */
	@Nullable
	private static IntStream reverseRange(final int from, final int to) {
		return map(IntStream.range(from, to), i -> to - i + from - 1);
	}

	@Nullable
	private static IntStream map(@Nullable final IntStream instance, final IntUnaryOperator mapper) {
		return instance != null ? instance.map(mapper) : instance;
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

	@Nullable
	private static <K, V> Set<Entry<K, V>> entrySet(@Nullable final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	@Nullable
	private static <E> Iterator<E> iterator(@Nullable final Iterable<E> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static int getSize(@Nullable final ListModel<?> instance) {
		return instance != null ? instance.getSize() : 0;
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, @Nullable final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	private static void removeElementAt(@Nullable final MutableComboBoxModel<?> instance, final int index) {
		if (instance != null) {
			instance.removeElementAt(index);
		}
	}

	@Nullable
	private static <K> K getKey(@Nullable final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	@Nullable
	private static <V> V getValue(@Nullable final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Nullable
	private static String getText(@Nullable final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	@Nullable
	private static Object getSource(@Nullable final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}