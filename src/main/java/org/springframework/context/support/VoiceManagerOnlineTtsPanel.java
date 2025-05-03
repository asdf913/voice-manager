package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.text.JTextComponent;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.TripleUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomElementUtil;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlOption;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSelect;
import org.htmlunit.html.HtmlTextArea;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.meeuw.functional.TriPredicate;
import org.meeuw.functional.TriPredicateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Stopwatch;
import com.google.common.base.StopwatchUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOnlineTtsPanel extends JPanel
		implements InitializingBean, Titled, ApplicationContextAware, ActionListener, ListDataListener {

	private static final long serialVersionUID = 1679789881293611910L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerOnlineTtsPanel.class);

	private static final String VALUE = "value";

	private transient ApplicationContext applicationContext = null;

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Name {
		String value();
	}

	@Name("SYNTEXT")
	private JTextComponent taText = null;

	@Name("SYNALPHA")
	private JTextComponent tfQuality = null;

	@Name("F0SHIFT")
	private JTextComponent tfPitch = null;

	@Name("DURATION")
	private JTextComponent tfDuration = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("URL")
	private JTextComponent tfUrl = null;

	@Note("Error Message")
	private JTextComponent tfErrorMessage = null;

	private JTextComponent tfElapsed = null;

	@Name("SPKR")
	private transient ComboBoxModel<?> cbmVoice = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy")
	private AbstractButton btnCopy = null;

	@Note("Download")
	private AbstractButton btnDownload = null;

	private AbstractButton btnPlayAudio = null;

	private Map<String, String> voices = null;

	private transient SpeechApi speechApi = null;

	private String key = null;

	private ObjectMapper objectMapper = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String getTitle() {
		return "Online TTS";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		final org.jsoup.nodes.Document document = testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null);
		//
		// 合成テキスト(最大200字)
		//
		Iterable<Element> elements = ElementUtil.select(document, "textarea");
		//
		testAndRunThrows(IterableUtils.size(elements) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		Element element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null);
		//
		final UnaryOperator<Element> parentPreviousElementSibling = x -> previousElementSibling(ElementUtil.parent(x));
		//
		add(new JLabel(StringUtils.defaultIfBlank(Util.collect(
				Util.map(Util.stream(NodeUtil.childNodes(Util.apply(parentPreviousElementSibling, element))), x -> {
					//
					if (x instanceof TextNode textNode) {
						//
						return TextNodeUtil.text(textNode);
						//
					} else if (x instanceof Element e && StringUtils.equalsIgnoreCase(ElementUtil.tagName(e), "br")) {
						//
						return "<br/>";
						//
					} // if
						//
					return Util.toString(x);
					//
				}), Collectors.joining("", "<html>", "</html>")), "Text")));
		//
		final int width = 375;
		//
		final String wrap = "wrap";
		//
		final PropertyResolver propertyResolver = getEnvironment(applicationContext);
		//
		add(new JScrollPane(taText = new JTextArea(Util.toString(testAndApply(PropertyResolverUtil::containsProperty,
				propertyResolver, String.join(".", Util.getName(Util.getClass(this)), "SYNTEXT"),
				PropertyResolverUtil::getProperty, null)))), String.format("%1$s,growy,wmin %2$spx", wrap, width));
		//
		// 話者
		//
		testAndRunThrows(IterableUtils.size(elements = ElementUtil.select(document, "select")) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		add(new JLabel(
				StringUtils
						.defaultIfBlank(
								ElementUtil
										.text(Util
												.apply(parentPreviousElementSibling,
														element = testAndApply(x -> IterableUtils.size(x) == 1,
																elements, x -> IterableUtils.get(x, 0), null))),
								"Voice")));
		//
		add(Util.cast(Component.class, testAndApply(Objects::nonNull, cbmVoice = testAndApply(Objects::nonNull,
				Util.toArray(Util.values(voices = Util.collect(Util.stream(ElementUtil.children(element)),
						Collectors.toMap(x -> NodeUtil.attr(x, VALUE), ElementUtil::text))), new String[] {}),
				DefaultComboBoxModel::new, null), JComboBox::new, x -> new JComboBox<>())));
		//
		testAndAccept(
				Objects::nonNull, getVoice(propertyResolver,
						String.join(".", Util.getName(Util.getClass(this)), "SPKR"), cbmVoice, voices),
				x -> Util.setSelectedItem(cbmVoice, IValue0Util.getValue0(x)));
		//
		add(new JLabel("最小"));
		//
		add(new JLabel("標準"));
		//
		add(new JLabel("最大"), wrap);
		//
		// 声質
		//
		String label = "声質";
		//
		testAndRunThrows(IterableUtils.size(elements = getParentPreviousElementSiblingByLabel(document, label)) > 1,
				() -> {
					//
					throw new IllegalStateException();
					//
				});
		//
		add(new JLabel(label));
		//
		final Pattern pattern = Pattern.compile("^\\((-?\\d+(\\.\\d+)?)〜(\\d+(\\.\\d+)?), 標準: (\\d+(\\.\\d+)?)\\)$");
		//
		Triple<String, String, String> triple = getTriple(pattern,
				element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null));
		//
		final Function<Triple<?, ?, ?>, String> function = x -> iif(x != null, String.format("wmin %1$spx", width),
				String.format("wmin %1$spx,%2$s", width, wrap));
		//
		add(tfQuality = new JTextField(
				StringUtils.defaultString(testAndApply((a, b, c) -> PropertyResolverUtil.containsProperty(a, b),
						propertyResolver, String.join(".", Util.getName(Util.getClass(this)), "SYNALPHA"), element,
						(a, b, c) -> PropertyResolverUtil.getProperty(a, b), (a, b, c) -> NodeUtil.attr(c, VALUE)))),
				Util.apply(function, triple));
		//
		final FailableConsumer<Triple<String, String, String>, RuntimeException> consumer = x -> {
			//
			add(new JLabel(TripleUtil.getLeft(x)));
			//
			add(new JLabel(TripleUtil.getMiddle(x)));
			//
			add(new JLabel(TripleUtil.getRight(x)), wrap);
			//
		};
		//
		testAndAccept(Objects::nonNull, triple, consumer);
		//
		// ピッチシフト
		//
		testAndRunThrows(
				IterableUtils.size(elements = getParentPreviousElementSiblingByLabel(document, label = "ピッチシフト")) > 1,
				() -> {
					//
					throw new IllegalStateException();
					//
				});
		//
		add(new JLabel(label));
		//
		add(tfPitch = new JTextField(StringUtils.defaultString(testAndApply(
				(a, b, c) -> PropertyResolverUtil.containsProperty(a, b), propertyResolver,
				String.join(".", Util.getName(Util.getClass(this)), "F0SHIFT"),
				element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null),
				(a, b, c) -> PropertyResolverUtil.getProperty(a, b), (a, b, c) -> NodeUtil.attr(c, VALUE)))),
				Util.apply(function, triple = getTriple(pattern, element)));
		//
		testAndAccept(Objects::nonNull, triple, consumer);
		//
		// 話速
		//
		testAndRunThrows(
				IterableUtils.size(elements = getParentPreviousElementSiblingByLabel(document, label = "話速")) > 1,
				() -> {
					//
					throw new IllegalStateException();
					//
				});
		//
		add(new JLabel(label));
		//
		add(tfDuration = new JTextField(StringUtils.defaultString(testAndApply(
				(a, b, c) -> PropertyResolverUtil.containsProperty(a, b), propertyResolver,
				String.join(".", Util.getName(Util.getClass(this)), "DURATION"),
				element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null),
				(a, b, c) -> PropertyResolverUtil.getProperty(a, b), (a, b, c) -> NodeUtil.attr(c, VALUE)))),
				Util.apply(function, triple = getTriple(pattern, element)));
		//
		testAndAccept(Objects::nonNull, triple, consumer);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		add(new JLabel("Elpased"));
		//
		add(tfElapsed = new JTextField(), String.format("%1$s,wmin %2$spx", wrap, width));
		//
		add(new JLabel("Output"));
		//
		add(tfUrl = new JTextField(), String.format("wmin %1$spx", width));
		//
		add(btnCopy = new JButton("Copy"), String.format("%1$s,span %2$s", wrap, 2));
		//
		add(new JLabel());
		//
		final JPanel panel = new JPanel();
		//
		final LayoutManager layoutManager = panel.getLayout();
		//
		if (layoutManager instanceof FlowLayout flowLayout) {
			//
			flowLayout.setVgap(0);
			//
		} // if
			//
		panel.add(btnDownload = new JButton("Download"));
		//
		panel.add(btnPlayAudio = new JButton("Play Audio"));
		//
		add(panel, wrap);
		//
		add(new JLabel("Error"));
		//
		add(tfErrorMessage = new JTextField(), String.format("%1$s,wmin %2$spx", wrap, width));
		//
		Util.forEach(Arrays.asList(tfElapsed, tfUrl, tfErrorMessage), x -> setEditable(x, false));
		//
		Util.forEach(Arrays.asList(btnCopy, btnDownload), x -> setEnabled(x, false));
		//
		Util.forEach(
				Util.filter(Util.map(
						Util.filter(Util.stream(FieldUtils.getAllFieldsList(getClass())), f -> !Util.isStatic(f)),
						f -> Util.cast(AbstractButton.class, Narcissus.getField(this, f))), Objects::nonNull),
				x -> addActionListener(x, this));
		//
		Util.forEach(
				Util.filter(Util
						.stream(testAndApply(
								Objects::nonNull, Util
										.getClass(
												speechApi = testAndApply(x -> IterableUtils.size(x) == 1, Util.collect(
														Util.filter(
																Util.stream(Util
																		.values(ListableBeanFactoryUtil.getBeansOfType(
																				applicationContext, SpeechApi.class))),
																x -> x instanceof SpeechApi
																		&& IterableUtils.size(Util.collect(
																				Util.filter(Util.stream(testAndApply(
																						Objects::nonNull,
																						Util.getClass(x),
																						FieldUtils::getAllFieldsList,
																						null)),
																						f -> UrlValidatorUtil.isValid(
																								UrlValidator
																										.getInstance(),
																								Util.toString(
																										Util.isStatic(
																												f) ? Narcissus.getStaticField(f) : Narcissus.getField(x, f)))),
																				Collectors.toList())) == 1),
														Collectors.toList()), x -> IterableUtils.get(x, 0), null)),
								FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getType(f), DefaultListModel.class)),
				f -> {
					//
					DefaultListModel<?> dlm = null;
					//
					if ((dlm = Util.cast(DefaultListModel.class, testAndApply((a, b) -> a != null && b != null,
							speechApi, f, Narcissus::getField, null))) == null && speechApi != null && f != null) {
						//
						(dlm = new DefaultListModel<>()).addListDataListener(this);
						//
						Narcissus.setField(speechApi, f, dlm);
						//
					} else if (dlm != null) {
						//
						dlm.addListDataListener(this);
						//
					} // if
						//
				});
		//
	}

	private static void setEnabled(@Nullable final AbstractButton instance, final boolean b) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Collection<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "model")));
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
			instance.setEnabled(b);
			//
		} // if
			//
	}

	@Nullable
	private static IValue0<Object> getVoice(@Nullable final PropertyResolver propertyResolver, final String propertyKey,
			final ListModel<?> listModel, final Map<?, ?> map) {
		//
		IValue0<Object> iValue0 = null;
		//
		if (PropertyResolverUtil.containsProperty(propertyResolver, propertyKey)) {
			//
			final String propertyValue = PropertyResolverUtil.getProperty(propertyResolver, propertyKey);
			//
			Object elementAt = null;
			//
			for (int i = 0; i < Util.getSize(listModel); i++) {
				//
				if (!Objects.equals(Util.toString(elementAt = Util.getElementAt(listModel, i)),
						Util.get(map, propertyValue))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(iValue0 != null, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				iValue0 = Unit.with(elementAt);
				//
			} // for
				//
			if (iValue0 == null) {
				//
				for (int i = 0; i < Util.getSize(listModel); i++) {
					//
					if (!StringUtils.containsIgnoreCase(Util.toString(elementAt = Util.getElementAt(listModel, i)),
							propertyValue)) {
						//
						continue;
						//
					} // if
						//
					testAndRunThrows(iValue0 != null, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					iValue0 = Unit.with(elementAt);
					//
				} // for
					//
			} // if
				//
		} // if
			//
		return iValue0;
		//
	}

	private static <T> T iif(final boolean condition, final T valueTrue, final T valueFalse) {
		return condition ? valueTrue : valueFalse;
	}

	@Nullable
	private static Environment getEnvironment(@Nullable final EnvironmentCapable instance) {
		return instance != null ? instance.getEnvironment() : null;
	}

	private static void addActionListener(@Nullable final AbstractButton instance,
			final ActionListener actionListener) {
		if (instance != null) {
			instance.addActionListener(actionListener);
		}
	}

	private static void setEditable(@Nullable final JTextComponent instance, final boolean b) {
		if (instance != null) {
			instance.setEditable(b);
		}
	}

	@Nullable
	private static Triple<String, String, String> getTriple(final Pattern pattern, final Element element) {
		//
		final Matcher matcher = Util.matcher(pattern, Util.toString(NodeUtil.nextSibling(element)));
		//
		return Util.matches(matcher) && Util.groupCount(matcher) > 5
				? Triple.of(Util.group(matcher, 1), Util.group(matcher, 5), Util.group(matcher, 3))
				: null;
		//
	}

	private static Iterable<Element> getParentPreviousElementSiblingByLabel(final Element element, final String label) {
		//
		return Util.toList(Util.filter(selectStream(element, "input[type=\"text\"]"),
				x -> StringUtils.equals(ElementUtil.text(previousElementSibling(ElementUtil.parent(x))), label)));
		//
	}

	@Nullable
	private static Stream<Element> selectStream(@Nullable final Element instance, final String cssQuery) {
		return instance != null && StringUtils.isNotBlank(cssQuery) ? instance.selectStream(cssQuery) : null;
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	@Nullable
	private static Element previousElementSibling(@Nullable final Element instance) {
		return instance != null ? instance.previousElementSibling() : null;
	}

	private static void setLayout(@Nullable final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
	}

	@Nullable
	private static LayoutManager getLayoutManager(final Object acbf, final Iterable<Entry<String, Object>> entrySet)
			throws Exception {
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<LayoutManager> iValue0 = null;
		//
		List<Field> fs = null;
		//
		for (final Entry<String, Object> entry : entrySet) {
			//
			if (!(Util.getValue(entry) instanceof LayoutManager)) {
				//
				continue;
				//
			} // if
				//
			fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(acbf), FieldUtils::getAllFieldsList, null)),
					x -> Objects.equals(Util.getName(x), "singletonObjects")));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if (FactoryBeanUtil
						.getObject(
								Util.cast(
										FactoryBean.class, MapUtils
												.getObject(
														Util.cast(Map.class,
																Narcissus.getObjectField(acbf,
																		IterableUtils.get(fs, i))),
														Util.getKey(entry)))) instanceof LayoutManager lm) {
					//
					if (iValue0 == null) {
						//
						iValue0 = Unit.with(lm);
						//
					} else {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return IValue0Util.getValue0(iValue0);
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <T, U, R> R apply(@Nullable final BiFunction<T, U, R> instance, @Nullable final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static <T, U, V, R> R testAndApply(final TriPredicate<T, U, V> predicate, @Nullable final T t, final U u,
			final V v, final TriFunction<T, U, V, R> functionTrue, final TriFunction<T, U, V, R> functionFalse) {
		return TriPredicateUtil.test(predicate, t, u, v) ? TriFunctionUtil.apply(functionTrue, t, u, v)
				: TriFunctionUtil.apply(functionFalse, t, u, v);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			Util.forEach(Arrays.asList(tfElapsed, tfUrl, tfErrorMessage), x -> Util.setText(x, null));
			//
			final Stopwatch stopwatch = Stopwatch.createStarted();
			//
			try (final WebClient webClient = new WebClient()) {
				//
				final HtmlPage htmlPage = testAndApply(Objects::nonNull, url, webClient::getPage, null);
				//
				Util.forEach(
						Util.collect(
								Util.filter(testAndApply(Objects::nonNull,
										VoiceManagerOnlineTtsPanel.class.getDeclaredFields(), Arrays::stream, null),
										f -> and(f, x -> Util.isAnnotationPresent(x, Name.class),
												x -> Narcissus.getField(this, x) != null)),
								Collectors.toMap(f -> value(Util.getAnnotation(f, Name.class)),
										f -> Narcissus.getField(this, f))),
						(a, b) -> setValues(htmlPage, voices, a, b));
				//
				final HtmlPage hm = Util.cast(HtmlPage.class, DomElementUtil
						.click(Util.cast(DomElement.class, querySelector(htmlPage, "input[type=\"submit\"]"))));
				//
				final IValue0<String> attribute = getAttribute(getElementsByTagName(hm, "source"), "src",
						x -> StringUtils.startsWith(x, "./temp/"));
				//
				if (attribute != null) {
					//
					Util.setText(tfUrl, String.join("/", StringUtils.substringBeforeLast(url, "/"),
							StringUtils.substringAfter(IValue0Util.getValue0(attribute), '/')));
					//
				} else if (hm != null) {
					//
					final Iterable<DomNode> domNodes = Util.toList(Util.filter(Util.stream(hm.querySelectorAll("b")),
							x -> Objects.equals(Util.getTextContent(x), "合成結果")));
					//
					testAndRunThrows(IterableUtils.size(domNodes) > 1, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					Util.setText(tfErrorMessage,
							StringUtils.trim(Util.toString(testAndApply(x -> Util.getLength(x) == 1,
									Util.getChildNodes(
											getNextElementSibling(testAndApply(x -> IterableUtils.size(x) == 1,
													domNodes, x -> IterableUtils.get(x, 0), null))),
									x -> Util.item(x, 0), null))));
					//
				} // if
					//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} finally {
				//
				Util.forEach(Arrays.asList(btnCopy, btnDownload),
						x -> setEnabled(x, UrlValidatorUtil.isValid(UrlValidator.getInstance(), Util.getText(tfUrl))));
				//
				final String string = Util.toString(StopwatchUtil.elapsed(stopwatch));
				//
				final Matcher matcher = Util.matcher(Pattern.compile("^PT(((\\d+)(.\\d+)?)S)$"), string);
				//
				testAndRun(and(matcher, Util::matches, x -> Util.groupCount(x) > 0),
						() -> Util.setText(tfElapsed, Util.group(matcher, 1)), () -> Util.setText(tfElapsed, string));
				//
			} // try
				//
		} else if (Objects.equals(source, btnDownload)) {
			//
			URL u = null;
			//
			try {
				//
				u = testAndApply(StringUtils::isNotBlank, Util.getText(tfUrl), URL::new, null);
				//
			} catch (final MalformedURLException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
			final JFileChooser jfc = new JFileChooser(".");
			//
			setFileName(Util.cast(BasicFileChooserUI.class, jfc.getUI()),
					StringUtils.substringAfterLast(Util.getFile(u), '/'));
			//
			if (and(jfc, x -> Boolean.logicalAnd(!isTestMode(), !GraphicsEnvironment.isHeadless()),
					x -> equals(showSaveDialog(x, null), JFileChooser.APPROVE_OPTION))) {
				//
				try (final InputStream is = Util.openStream(u)) {
					//
					testAndAccept(Objects::nonNull, testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null),
							x -> FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), x));
					//
				} catch (final IOException e) {
					//
					LoggerUtil.error(LOG, e.getMessage(), e);
					//
				} // try
					//
			} // if
				//
		} else if (actionPerformed(source)) {
			//
			return;
			//
		} // if
			//
	}

	private boolean actionPerformed(final Object source) {
		//
		if (Objects.equals(source, btnCopy)) {
			//
			testAndRunThrows(!isTestMode(), () -> setContents(getSystemClipboard(getToolkit()),
					new StringSelection(Util.getText(tfUrl)), null));
			//
			return true;
			//
		} else if (Objects.equals(source, btnPlayAudio)) {
			//
			String keyTemp = null;
			//
			try {
				//
				if (!Objects.equals(keyTemp = testAndApply(Objects::nonNull,
						ObjectMapperUtil.writeValueAsString(
								objectMapper = ObjectUtils.getIfNull(objectMapper, ObjectMapper::new),
								new Object[] { Util.getText(taText), Util.getSelectedItem(cbmVoice),
										Util.getText(tfQuality), Util.getText(tfPitch), Util.getText(tfDuration) }),
						DigestUtils::sha512Hex, null), key)) {
					//
					Util.setText(tfUrl, null);
					//
				} // if
					//
			} catch (final JsonProcessingException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			Util.setText(tfErrorMessage, null);
			//
			final String urlString = Util.getText(tfUrl);
			//
			URL u = null;
			//
			try {
				//
				u = testAndApply(x -> UrlValidatorUtil.isValid(UrlValidator.getInstance(), x), urlString, URL::new,
						null);
				//
			} catch (final MalformedURLException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			final Iterable<Method> ms = Util.collect(
					Util.filter(
							testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(speechApi)),
									Arrays::stream, null),
							m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "speak"),
									Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { URL.class }))),
					Collectors.toList());
			//
			testAndRunThrows(IterableUtils.size(ms) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			final Method m = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
			//
			if (Boolean.logicalAnd(Util.isStatic(m), u != null)) {
				//
				Narcissus.invokeStaticMethod(m, u);
				//
				return true;
				//
			} // if
				//
			final List<String> keys = Util.toList(Util.map(Util.filter(Util.stream(Util.entrySet(voices)),
					x -> Objects.equals(Util.getValue(x), Util.getSelectedItem(cbmVoice))), Util::getKey));
			//
			testAndRunThrows(IterableUtils.size(keys) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			try {
				//
				final Map<String, Object> map = new LinkedHashMap<>(
						Collections.singletonMap("SYNALPHA", Util.getText(tfQuality)));
				//
				Util.put(map, "F0SHIFT", Util.getText(tfPitch));
				//
				speak(speechApi, Util.getText(taText),
						testAndApply(x -> IterableUtils.size(x) == 1, keys, x -> IterableUtils.get(x, 0), null),
						NumberUtils.toInt(Util.getText(tfDuration), 0)// TODO
						, 0// TODO volume
						, map);
				//
				key = keyTemp;
				//
			} catch (final RuntimeException e) {
				//
				Util.setText(tfErrorMessage, e.getMessage());
				//
			} // try
				//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static void speak(@Nullable final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, final Map<String, Object> map) {
		if (instance != null) {
			instance.speak(text, voiceId, rate, volume, map);
		}
	}

	private static boolean equals(@Nullable final Number a, final int b) {
		return a != null && a.intValue() == b;
	}

	@Nullable
	private static Integer showSaveDialog(@Nullable final JFileChooser instance, final Component parent) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Collection<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "ui")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f != null && Narcissus.getField(instance, f) == null ? null
				: Integer.valueOf(instance.showSaveDialog(parent));
		//
	}

	private static void testAndRun(final boolean condition, final Runnable runnableTrue, final Runnable runnableFalse) {
		if (condition) {
			Util.run(runnableTrue);
		} else {
			Util.run(runnableFalse);
		} // if
	}

	private static <T> boolean and(final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	private static void setFileName(@Nullable final BasicFileChooserUI instance, final String filename) {
		if (instance != null) {
			instance.setFileName(filename);
		}
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	@Nullable
	private static DomElement getNextElementSibling(@Nullable final DomNode instance) {
		return instance != null ? instance.getNextElementSibling() : null;
	}

	private static void setValues(final HtmlPage htmlPage, final Map<String, String> voices, final String a,
			final Object b) {
		//
		final List<DomElement> domElements = getElementsByName(htmlPage, a);
		//
		testAndRunThrows(IterableUtils.size(domElements) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final DomElement domElement = testAndApply(x -> IterableUtils.size(x) == 1, domElements,
				x -> IterableUtils.get(x, 0), null);
		//
		if (b instanceof JTextComponent jtc) {
			//
			final String text = Util.getText(jtc);
			//
			if (domElement instanceof HtmlTextArea) {
				//
				Util.setTextContent(domElement, text);
				//
			} else if (domElement instanceof HtmlInput htmlInput) {
				//
				htmlInput.setValue(text);
				//
			} // if
				//
		} else if (b instanceof ComboBoxModel cbm) {
			//
			final Object selectedItem = Util.getSelectedItem(cbm);
			//
			final List<String> keys = Util.toList(Util.map(Util.filter(Util.stream(Util.entrySet(voices)),
					x -> Objects.equals(Util.getValue(x), selectedItem)), Util::getKey));
			//
			final int size = IterableUtils.size(keys);
			//
			testAndRunThrows(size > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (size == 1 && domElement instanceof HtmlSelect htmlSelect) {
				//
				final Iterable<HtmlOption> options = Util.toList(Util.filter(Util.stream(getOptions(htmlSelect)),
						x -> StringUtils.equals(getValueAttribute(x), IterableUtils.get(keys, 0))));
				//
				testAndRunThrows(IterableUtils.size(options) > 1, () -> {
					//
					throw new IllegalStateException();
					//
				});
				//
				setSelectedIndex(htmlSelect,
						testAndApply(x -> IterableUtils.size(x) == 1, options, x -> IterableUtils.get(x, 0), null));
				//
			} // if
				//
		} // if
			//
	}

	private static void setSelectedIndex(@Nullable final HtmlSelect htmlSelect, @Nullable final HtmlOption htmlOption) {
		//
		if (htmlSelect == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, FieldUtils.getAllFields(Util.getClass(htmlSelect)),
						Arrays::stream, null), f -> Objects.equals(Util.getName(f), "attributes_")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(htmlSelect, f) == null) {
			//
			return;
			//
		} // if
			//
		if (htmlOption != null) {
			//
			htmlSelect.setSelectedIndex(htmlOption.getIndex());
			//
		} // if
			//
	}

	@Nullable
	private static String value(@Nullable final Name instance) {
		return instance != null ? instance.value() : null;
	}

	@Nullable
	private static final String getValueAttribute(@Nullable final HtmlOption instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, FieldUtils.getAllFields(Util.getClass(instance)), Arrays::stream, null),
				f -> Objects.equals(Util.getName(f), "attributes_")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getValueAttribute();
		//
	}

	@Nullable
	private static List<HtmlOption> getOptions(@Nullable final HtmlSelect instance) {
		return instance != null ? instance.getOptions() : null;
	}

	@Nullable
	private static List<DomElement> getElementsByName(@Nullable final HtmlPage instance, final String name) {
		return instance != null ? instance.getElementsByName(name) : null;
	}

	@Nullable
	private static IValue0<String> getAttribute(@Nullable final NodeList nodeList, final String attrbiuteName,
			final Predicate<String> predicate) {
		//
		Node namedItem = null;
		//
		String nodeValue = null;
		//
		IValue0<String> result = null;
		//
		for (int i = 0; i < Util.getLength(nodeList); i++) {
			//
			if ((namedItem = Util.getNamedItem(Util.getAttributes(Util.item(nodeList, i)), attrbiuteName)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Util.test(predicate, nodeValue = namedItem.getNodeValue())) {
				//
				if (result == null) {
					//
					result = Unit.with(nodeValue);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> instance, @Nullable final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(instance, value)) {
			FailableConsumerUtil.accept(consumer, value);
		} // if
	}

	@Nullable
	private static <N extends DomNode> N querySelector(@Nullable final DomNode instance, final String selectors) {
		return instance != null ? instance.querySelector(selectors) : null;
	}

	@Nullable
	private static NodeList getElementsByTagName(@Nullable final Document instance, final String tagname) {
		return instance != null ? instance.getElementsByTagName(tagname) : null;
	}

	@Override
	public void intervalAdded(final ListDataEvent evt) {
		//
		final DefaultListModel<?> dlm = Util.cast(DefaultListModel.class, Util.getSource(evt));
		//
		Util.setText(tfUrl, Util.toString(dlm != null && dlm.size() == 1 ? dlm.get(0) : null));
		//
	}

	@Override
	public void intervalRemoved(final ListDataEvent evt) {
	}

	@Override
	public void contentsChanged(final ListDataEvent evt) {
	}

}