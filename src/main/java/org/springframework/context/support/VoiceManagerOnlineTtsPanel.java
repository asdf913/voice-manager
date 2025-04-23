package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.TripleUtil;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOnlineTtsPanel extends JPanel
		implements InitializingBean, Titled, ApplicationContextAware, ActionListener {

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

	private JTextComponent tfErrorMessage = null;

	@Name("SPKR")
	private transient ComboBoxModel<?> cbmVoice = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnCopy = null;

	private Map<String, String> voices = null;

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
		add(new JScrollPane(taText = new JTextArea()), String.format("%1$s,growy,wmin %2$spx", wrap, width));
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
		final Function<Triple<?, ?, ?>, String> function = x -> x != null ? String.format("wmin %1$spx", width)
				: String.format("wmin %1$spx,%2$s", width, wrap);
		//
		add(tfQuality = new JTextField(StringUtils.defaultString(NodeUtil.attr(element, VALUE))),
				Util.apply(function, triple));
		//
		final Consumer<Triple<String, String, String>> consumer = x -> {
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
		add(tfPitch = new JTextField(StringUtils.defaultString(NodeUtil.attr(
				element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null),
				VALUE))), Util.apply(function, triple = getTriple(pattern, element)));
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
		add(tfDuration = new JTextField(StringUtils.defaultString(NodeUtil.attr(
				element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null),
				VALUE))), Util.apply(function, triple = getTriple(pattern, element)));
		//
		testAndAccept(Objects::nonNull, triple, consumer);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		add(new JLabel("Output"));
		//
		add(tfUrl = new JTextField(), String.format("wmin %1$spx", width));
		//
		add(btnCopy = new JButton("Copy"), String.format("%1$s,span %2$s", wrap, 3));
		//
		add(new JLabel("Error"));
		//
		add(tfErrorMessage = new JTextField(), String.format("%1$s,wmin %2$spx", wrap, width));
		//
		Util.forEach(Arrays.asList(tfUrl, tfErrorMessage), x -> setEditable(x, false));
		//
		Util.forEach(Arrays.asList(btnExecute, btnCopy), x -> addActionListener(x, this));
		//
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			Util.forEach(Arrays.asList(tfUrl, tfErrorMessage), x -> Util.setText(x, null));
			//
			try (final WebClient webClient = new WebClient()) {
				//
				final HtmlPage htmlPage = testAndApply(Objects::nonNull, url, webClient::getPage, null);
				//
				Util.forEach(Util.collect(
						Util.filter(
								testAndApply(Objects::nonNull, VoiceManagerOnlineTtsPanel.class.getDeclaredFields(),
										Arrays::stream, null),
								f -> Util.isAnnotationPresent(f, Name.class) && Narcissus.getField(this, f) != null),
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
							x -> Objects.equals(x != null ? x.getTextContent() : null, "合成結果")));
					//
					testAndRunThrows(IterableUtils.size(domNodes) > 1, () -> {
						//
						throw new IllegalStateException();
						//
					});
					//
					Util.setText(tfErrorMessage,
							StringUtils.trim(Util.toString(testAndApply(x -> Util.getLength(x) == 1,
									getChildNodes(getNextElementSibling(testAndApply(x -> IterableUtils.size(x) == 1,
											domNodes, x -> IterableUtils.get(x, 0), null))),
									x -> Util.item(x, 0), null))));
					//
				} // if
					//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnCopy)) {
			//
			testAndRunThrows(!isTestMode(), () -> setContents(getSystemClipboard(getToolkit()),
					new StringSelection(Util.getText(tfUrl)), null));
			//
		} // if
			//
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

	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	@Nullable
	private static NodeList getChildNodes(@Nullable final Node instance) {
		return instance != null ? instance.getChildNodes() : null;
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
			final Object selectedItem = cbm.getSelectedItem();
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

	private static <T> void testAndAccept(final Predicate<T> instance, @Nullable final T value,
			final Consumer<T> consumer) {
		if (Util.test(instance, value)) {
			Util.accept(consumer, value);
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

}