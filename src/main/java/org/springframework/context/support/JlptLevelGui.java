package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.util.IntList;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.github.hal4j.uritemplate.URIBuilder;

import domain.JlptVocabulary;
import net.miginfocom.swing.MigLayout;

@Title("JLPT Level")
public class JlptLevelGui extends JFrame implements InitializingBean, ActionListener, DocumentListener, ItemListener {

	private static final long serialVersionUID = 1466869508176258464L;

	private List<String> jlptLevels = null;

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	private JTextComponent tfText = null;

	@Note("Export JSON")
	private AbstractButton btnExportJson = null;

	@Note("Copy")
	private AbstractButton btnCopy = null;

	@Note("Compre")
	private AbstractButton btnCompare = null;

	private AbstractButton btnVisitJMdictDB = null;

	private JTextComponent tfJson = null;

	private JLabel jlCompare = null;

	private ObjectMapper objectMapper = null;

	private transient Document tfTextDocument = null;

	@Nullable
	private JComboBox<JlptVocabulary> jcbJlptVocabulary = null;

	private transient MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = null;

	private transient IValue0<List<JlptVocabulary>> jlptVocabularyList = null;

	private transient ComboBoxModel<String> cbmJlptLevel = null;

	private JList<String> jlJlptLevel = null;

	private String jmDictUrl = null;

	private JlptLevelGui() {
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = jlptLevels;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setJlptVocabularyList(final List<JlptVocabulary> jlptVocabularyList) {
		this.jlptVocabularyList = Unit.with(jlptVocabularyList);
	}

	public void setJmDictUrl(final String jmDictUrl) {
		this.jmDictUrl = jmDictUrl;
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
		final String wrap = "wrap";
		//
		add(tfText = new JTextField(), wrap);
		//
		addDocumentListener(tfTextDocument = tfText.getDocument(), this);
		//
		add(new JLabel());
		//
		add(jcbJlptVocabulary = new JComboBox<JlptVocabulary>(cbmJlptVocabulary = new DefaultComboBoxModel<>()), wrap);
		//
		jcbJlptVocabulary.addItemListener(this);
		//
		final ListCellRenderer<?> render = jcbJlptVocabulary.getRenderer();
		//
		jcbJlptVocabulary.setRenderer(new ListCellRenderer<JlptVocabulary>() {

			@Override
			@Nullable
			public Component getListCellRendererComponent(final JList<? extends JlptVocabulary> list,
					final JlptVocabulary value, final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return JlptLevelGui.getListCellRendererComponent(((ListCellRenderer) render), list,
						testAndApply(Objects::nonNull, value, x -> StringUtils.trim(
								String.join(" ", StringUtils.defaultString(getKanji(x)), getKana(x), getLevel(x))),
								null),
						index, isSelected, cellHasFocus);
				//
			}

		});
		//
		add(new JLabel());
		//
		add(btnVisitJMdictDB = new JButton("Visit JMdict"), wrap);
		//
		add(new JLabel("JLPT Level(s)"));
		//
		add(jlJlptLevel = (cbmJlptLevel = testAndApply(Objects::nonNull, toArray(jlptLevels, new String[] {}),
				DefaultComboBoxModel::new, null)) != null ? new JList<>(cbmJlptLevel) : new JList<>(), wrap);
		//
		add(new JLabel());
		//
		add(btnExportJson = new JButton("Export JSON"), wrap);
		//
		add(new JLabel("JSON"));
		//
		add(tfJson = new JTextField());
		//
		add(btnCopy = new JButton("Copy"), wrap);
		//
		add(new JLabel());
		//
		add(btnCompare = new JButton("Compare JLPT Level(s)"));
		//
		add(jlCompare = new JLabel());
		//
		addActionListener(this, btnExportJson, btnCopy, btnCompare, btnVisitJMdictDB);
		//
		final List<Component> cs = Arrays.asList(jlJlptLevel, btnExportJson, tfJson, btnCompare, tfText);
		//
		final Dimension preferredSize = map(stream(cs), JlptLevelGui::getPreferredSize)
				.max((a, b) -> a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0).orElse(null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
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

	private static void addDocumentListener(final Document instance, final DocumentListener listener) {
		if (instance != null) {
			instance.addDocumentListener(listener);
		}
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

	@Nullable
	private static Dimension getPreferredSize(@Nullable final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

	private static void setPreferredWidth(final int width, @Nullable final Iterable<Component> cs) {
		//
		if (iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = getPreferredSize(c)) == null) {
					//
					continue;
					//
				} // if
					//
				c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExportJson)) {
			//
			actionPerformedForBtnExportJson();
			//
		} else if (Objects.equals(source, btnCopy)) {
			//
			run(!isTestMode(),
					() -> setContents(getSystemClipboard(getToolkit()), new StringSelection(getText(tfJson)), null));
			//
		} else if (Objects.equals(source, btnCompare)) {
			//
			setText(jlCompare, null);
			//
			final List<Method> ms = toList(filter(
					testAndApply(Objects::nonNull,
							getDeclaredMethods(forName("org.springframework.beans.factory.JlptLevelListFactoryBean")),
							Arrays::stream, null),
					m -> Boolean.logicalAnd(Objects.equals(getName(m), "getObjectByUrl"),
							Arrays.equals(new Class<?>[] { String.class, Duration.class }, getParameterTypes(m)))));
			//
			final int size = IterableUtils.size(ms);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Method m = size > 0 ? IterableUtils.get(ms, 0) : null;
			//
			try {
				//
				if (m != null) {
					//
					m.setAccessible(true);
					//
				} // if
					//
				final boolean matched = Objects.equals(invoke(m, null, url, null), jlptLevels);
				//
				setText(jlCompare, iif(matched, "Matched", "Not Matched"));
				//
				setForeground(jlCompare, iif(matched, Color.GREEN, Color.RED));
				//
			} catch (final IllegalAccessException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
								ExceptionUtils.getRootCause(e), e));
				//
			} // try
				//
		} else if (Objects.equals(source, btnVisitJMdictDB)) {
			//
			actionPerformedForBtnVisitJMdictDB();
			//
		} // if
			//
	}

	private void actionPerformedForBtnExportJson() {
		//
		try {
			//
			setText(tfJson, ObjectMapperUtil.writeValueAsString(getObjectMapper(), jlptLevels));
			//
		} catch (final JsonProcessingException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	private void actionPerformedForBtnVisitJMdictDB() {
		//
		final Integer jmdictSeq = getJmdictSeq(cast(JlptVocabulary.class, getSelectedItem(cbmJlptVocabulary)));
		//
		run(Boolean.logicalAnd(jmdictSeq != null, !isTestMode()), () -> {
			//
			final URIBuilder uriBuilder = testAndApply(Objects::nonNull, jmDictUrl, URIBuilder::basedOn, null);
			//
			if (uriBuilder != null) {
				//
				uriBuilder.queryParam("q", jmdictSeq);
				//
			} // if
				//
			try {
				//
				browse(Desktop.getDesktop(), toURI(uriBuilder));
				//
			} catch (final IOException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		});
		//
	}

	@Nullable
	private static URI toURI(@Nullable final URIBuilder instance) {
		return instance != null ? instance.toURI() : null;
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final ComboBoxModel<?> instance) {
		return instance != null ? instance.getSelectedItem() : null;
	}

	private static boolean isTestMode() {
		return forName("org.junit.jupiter.api.Test") != null;
	}

	private static void browse(final Desktop instance, final URI uri) throws IOException {
		if (instance != null && uri != null) {
			instance.browse(uri);
		}
	}

	@Override
	public void changedUpdate(final DocumentEvent evt) {
		//
	}

	@Override
	public void insertUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), tfTextDocument)) {
			//
			setJlptVocabularyAndLevel(this);
			//
		} // if
			//
	}

	@Override
	public void removeUpdate(final DocumentEvent evt) {
		//
		if (Objects.equals(getDocument(evt), tfTextDocument)) {
			//
			setJlptVocabularyAndLevel(this);
			//
		} // if
			//
	}

	private static void setJlptVocabularyAndLevel(@Nullable final JlptLevelGui instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final String text = getText(instance.tfText);
		//
		final MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = instance.cbmJlptVocabulary;
		//
		for (int i = getSize(cbmJlptVocabulary) - 1; i >= 0; i--) {
			//
			removeElementAt(cbmJlptVocabulary, i);
			//
		} // for
			//
		final JList<String> jlJlptLevel = instance.jlJlptLevel;
		//
		setSelectedIndices(jlJlptLevel, new int[] {});
		//
		final IValue0<List<JlptVocabulary>> jlptVocabularyList = instance.jlptVocabularyList;
		//
		final List<JlptVocabulary> jlptVocabularies = IValue0Util.getValue0(jlptVocabularyList);
		//
		if (StringUtils.isNotEmpty(text) && CollectionUtils.isNotEmpty(jlptVocabularies)
				&& jlptVocabularyList != null) {
			//
			final List<JlptVocabulary> temp = toList(filter(stream(jlptVocabularies),
					x -> Boolean.logicalOr(Objects.equals(text, getKanji(x)), Objects.equals(text, getKana(x)))));
			//
			forEach(temp, x -> addElement(cbmJlptVocabulary, x));
			//
			if (IterableUtils.size(temp) > 1) {
				//
				setSelectedIndices(jlJlptLevel, new int[] {});
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						toList(map(stream(temp), JlptLevelGui::getLevel).distinct()), x -> {
							//
							if (instance != null) {
								//
								instance.setJlptLevel(IterableUtils.get(x, 0));
								//
							} // if
								//
						});
				//
				return;
				//
			} // if
				//
			testAndAccept(Objects::nonNull,
					testAndApply(x -> IterableUtils.size(x) == 1, temp, x -> IterableUtils.get(x, 0), null), x -> {
						//
						if (instance != null) {
							//
							instance.setJlptLevel(getLevel(x));
							//
						} // if
							//
					});
			//
		} // if
			//
	}

	private static void removeElementAt(@Nullable final MutableComboBoxModel<?> instnace, final int index) {
		if (instnace != null) {
			instnace.removeElementAt(index);
		}
	}

	private static void setSelectedIndices(@Nullable final JList<?> instance, @Nullable final int[] indices) {
		if (instance != null && indices != null) {
			instance.setSelectedIndices(indices);
		}
	}

	@Override
	public void itemStateChanged(final ItemEvent evt) {
		//
		if (Objects.equals(getSource(evt), jcbJlptVocabulary)) {
			//
			setJlptLevel(getLevel(cast(JlptVocabulary.class,
					jcbJlptVocabulary != null ? jcbJlptVocabulary.getSelectedItem() : null)));
			//
		} // if
			//
	}

	private void setJlptLevel(@Nullable final String level) {
		//
		IntList intList = null;
		//
		for (int i = 0; jlJlptLevel != null && i < jlJlptLevel.getVisibleRowCount(); i++) {
			//
			if (StringUtils.equalsAnyIgnoreCase(jlJlptLevel.getModel().getElementAt(i), level)
					&& (intList = ObjectUtils.getIfNull(intList, IntList::new)) != null) {
				//
				intList.add(i);
				//
			} // if
				//
		} // for
			//
		setSelectedIndices(jlJlptLevel, toArray(intList));
		//
	}

	@Nullable
	private static int[] toArray(@Nullable final IntList instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static <T> T cast(final Class<T> clz, @Nullable final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Nullable
	private static Document getDocument(@Nullable final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
	}

	private static int getSize(@Nullable final ListModel<?> instance) {
		return instance != null ? instance.getSize() : 0;
	}

	@Nullable
	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R> Stream<R> map(@Nullable final Stream<T> instance,
			final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	@Nullable
	private static String getKanji(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKanji() : null;
	}

	@Nullable
	private static String getKana(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getKana() : null;
	}

	@Nullable
	private static String getLevel(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getLevel() : null;
	}

	@Nullable
	private static Integer getJmdictSeq(@Nullable final JlptVocabulary instance) {
		return instance != null ? instance.getJmdictSeq() : null;
	}

	private static <T> void forEach(final Iterable<T> items, final Consumer<? super T> action) {
		//
		if (iterator(items) != null && (action != null || Proxy.isProxyClass(getClass(items)))) {
			//
			for (final T item : items) {
				//
				action.accept(item);
				//
			} // for
				//
		} // if
			//
	}

	private static <E> void addElement(final MutableComboBoxModel<E> instance, final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer) {
		if (test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static Object getSource(@Nullable final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	@Nullable
	private static Method[] getDeclaredMethods(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, @Nullable final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static void run(final boolean b, final Runnable runnable) {
		//
		if (b && runnable != null) {
			//
			runnable.run();
			//
		} // if
			//
	}

	@Nullable
	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	@Nullable
	private static String getText(@Nullable final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setText(final JLabel instance, @Nullable final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setForeground(final Component instance, final Color color) {
		if (instance != null) {
			instance.setForeground(color);
		}
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

}