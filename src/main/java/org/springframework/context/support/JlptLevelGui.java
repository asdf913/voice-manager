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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
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
import org.apache.commons.lang3.reflect.FieldUtils;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.github.hal4j.uritemplate.URIBuilder;

import domain.JlptVocabulary;
import io.github.toolfactory.narcissus.Narcissus;
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
			// If "java.awt.Container.component" is null, return this method immediately
			//
			// The below check is for "-Djava.awt.headless=true"
			//
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		final boolean isGui = f == null || Narcissus.getObjectField(this, f) != null;
		//
		final Predicate<Component> predicate = Predicates.always(isGui, null);
		//
		testAndAccept(predicate, new JLabel("Text"), this::add);
		//
		final String wrap = "wrap";
		//
		final BiPredicate<Component, Object> biPredicate = Predicates.biAlways(isGui, null);
		//
		testAndAccept(biPredicate, tfText = new JTextField(), wrap, this::add);
		//
		addDocumentListener(tfTextDocument = tfText.getDocument(), this);
		//
		testAndAccept(predicate, new JLabel(), this::add);
		//
		testAndAccept(biPredicate,
				jcbJlptVocabulary = new JComboBox<JlptVocabulary>(cbmJlptVocabulary = new DefaultComboBoxModel<>()),
				wrap, this::add);
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
		testAndAccept(predicate, new JLabel(), this::add);
		//
		testAndAccept(biPredicate, btnVisitJMdictDB = new JButton("Visit JMdict"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel("JLPT Level(s)"), this::add);
		//
		testAndAccept(biPredicate,
				jlJlptLevel = (cbmJlptLevel = testAndApply(Objects::nonNull, Util.toArray(jlptLevels, new String[] {}),
						DefaultComboBoxModel::new, null)) != null ? new JList<>(cbmJlptLevel) : new JList<>(),
				wrap, this::add);
		//
		testAndAccept(predicate, new JLabel(), this::add);
		//
		testAndAccept(biPredicate, btnExportJson = new JButton("Export JSON"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel("JSON"), this::add);
		//
		testAndAccept(predicate, tfJson = new JTextField(), this::add);
		//
		testAndAccept(biPredicate, btnCopy = new JButton("Copy"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel(), this::add);
		//
		testAndAccept(predicate, btnCompare = new JButton("Compare JLPT Level(s)"), this::add);
		//
		testAndAccept(predicate, jlCompare = new JLabel(), this::add);
		//
		addActionListener(this, btnExportJson, btnCopy, btnCompare, btnVisitJMdictDB);
		//
		final List<Component> cs = Arrays.asList(jlJlptLevel, btnExportJson, tfJson, btnCompare, tfText);
		//
		final Dimension preferredSize = Util.orElse(max(Util.map(Util.stream(cs), Util::getPreferredSize),
				(a, b) -> a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0), null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
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

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static void addDocumentListener(@Nullable final Document instance, final DocumentListener listener) {
		if (instance != null) {
			instance.addDocumentListener(listener);
		}
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

	private static void setPreferredWidth(final int width, @Nullable final Iterable<Component> cs) {
		//
		if (Util.iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = Util.getPreferredSize(c)) == null) {
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

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExportJson)) {
			//
			actionPerformedForBtnExportJson();
			//
		} else if (Objects.equals(source, btnCopy)) {
			//
			run(!isTestMode(), () -> setContents(getSystemClipboard(getToolkit()),
					new StringSelection(Util.getText(tfJson)), null));
			//
		} else if (Objects.equals(source, btnCompare)) {
			//
			Util.setText(jlCompare, null);
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull,
							Util.getDeclaredMethods(
									Util.forName("org.springframework.beans.factory.JlptLevelListFactoryBean")),
							Arrays::stream, null),
					m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "getObjectByUrl"), Arrays
							.equals(new Class<?>[] { String.class, Duration.class }, Util.getParameterTypes(m)))));
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
				final boolean matched = Objects
						.equals(Util.isStatic(m) ? Narcissus.invokeStaticMethod(m, url, null) : null, jlptLevels);
				//
				Util.setText(jlCompare, iif(matched, "Matched", "Not Matched"));
				//
				Util.setForeground(jlCompare, iif(matched, Color.GREEN, Color.RED));
				//
			} catch (final Exception e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(
						ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
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
			Util.setText(tfJson, ObjectMapperUtil.writeValueAsString(getObjectMapper(), jlptLevels));
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
		final Integer jmdictSeq = getJmdictSeq(Util.cast(JlptVocabulary.class, getSelectedItem(cbmJlptVocabulary)));
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
			} catch (final IOException | NoSuchFieldException e) {
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
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void browse(@Nullable final Desktop instance, @Nullable final URI uri)
			throws IOException, NoSuchFieldException {
		//
		if (instance != null && uri != null
				&& Narcissus.getObjectField(instance, Util.getDeclaredField(Desktop.class, "peer")) != null) {
			//
			instance.browse(uri);
			//
		} // if
			//
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
		final String text = Util.getText(instance.tfText);
		//
		final MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = instance.cbmJlptVocabulary;
		//
		for (int i = Util.getSize(cbmJlptVocabulary) - 1; i >= 0; i--) {
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
			final List<JlptVocabulary> temp = Util.toList(Util.filter(Util.stream(jlptVocabularies),
					x -> Boolean.logicalOr(Objects.equals(text, getKanji(x)), Objects.equals(text, getKana(x)))));
			//
			forEach(temp, x -> addElement(cbmJlptVocabulary, x));
			//
			if (IterableUtils.size(temp) > 1) {
				//
				setSelectedIndices(jlJlptLevel, new int[] {});
				//
				testAndAccept(x -> IterableUtils.size(x) == 1,
						Util.toList(Util.distinct(Util.map(Util.stream(temp), JlptLevelGui::getLevel))), x -> {
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
		if (Objects.equals(Util.getSource(evt), jcbJlptVocabulary)) {
			//
			setJlptLevel(getLevel(Util.cast(JlptVocabulary.class,
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
			if (StringUtils.equalsAnyIgnoreCase(Util.getElementAt(jlJlptLevel.getModel(), i), level)) {
				//
				IntCollectionUtil.addInt(intList = ObjectUtils.getIfNull(intList, IntList::create), i);
				//
			} // if
				//
		} // for
			//
		setSelectedIndices(jlJlptLevel, IntCollectionUtil.toIntArray(intList));
		//
	}

	@Nullable
	private static Document getDocument(@Nullable final DocumentEvent instance) {
		return instance != null ? instance.getDocument() : null;
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

	private static <T> void forEach(@Nullable final Iterable<T> items, @Nullable final Consumer<? super T> action) {
		//
		if (Util.iterator(items) != null) {
			//
			for (final T item : items) {
				//
				Util.accept(action, item);
				//
			} // for
				//
		} // if
			//
	}

	private static <E> void addElement(@Nullable final MutableComboBoxModel<E> instance, final E item) {
		if (instance != null) {
			instance.addElement(item);
		}
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			@Nullable final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
		}
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (Util.test(predicate, t, u) && consumer != null) {
			consumer.accept(t, u);
		}
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

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static void run(final boolean b, @Nullable final Runnable runnable) {
		//
		if (b) {
			//
			Util.run(runnable);
			//
		} // if
			//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}