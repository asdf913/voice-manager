package org.springframework.context.support;

import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomElementUtil;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlOption;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSelect;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;
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

	private transient ApplicationContext applicationContext = null;

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Name {
		String value();
	}

	@Name("SYNTEXT")
	private JTextComponent taText = null;

	private JTextComponent tfUrl = null;

	@Name("SPKR")
	private ComboBoxModel<?> cbmVoice = null;

	private AbstractButton btnExecute = null;

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
		if (IterableUtils.size(elements) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		Element element = testAndApply(x -> IterableUtils.size(x) == 1, elements, x -> IterableUtils.get(x, 0), null);
		//
		add(new JLabel(StringUtils.defaultIfBlank(Util.collect(
				Util.map(Util.stream(NodeUtil.childNodes(previousElementSibling(ElementUtil.parent(element)))), x -> {
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
		add(new JScrollPane(taText = new JTextArea()), String.format("wrap,growy,wmin %1$spx", width));
		//
		// 話者
		//
		if (IterableUtils.size(elements = ElementUtil.select(document, "select")) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		add(new JLabel(StringUtils.defaultIfBlank(ElementUtil
				.text(previousElementSibling(ElementUtil.parent(element = testAndApply(x -> IterableUtils.size(x) == 1,
						elements, x -> IterableUtils.get(x, 0), null)))),
				"Voice")));
		//
		add(testAndApply(Objects::nonNull,
				cbmVoice = testAndApply(Objects::nonNull,
						Util.toArray(
								Util.values(voices = Util.collect(Util.stream(ElementUtil.children(element)),
										Collectors.toMap(x -> NodeUtil.attr(x, "value"), x -> ElementUtil.text(x)))),
								new String[] {}),
						DefaultComboBoxModel::new, null),
				JComboBox::new, x -> new JComboBox<>()), "wrap");
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), "wrap");
		//
		btnExecute.addActionListener(this);
		//
		add(new JLabel("Output"));
		//
		add(tfUrl = new JTextField(), String.format("wrap,wmin %1$spx", width));
		//
		tfUrl.setEditable(false);
		//
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			try (final WebClient webClient = new WebClient()) {
				//
				final HtmlPage htmlPage = testAndApply(Objects::nonNull, url, webClient::getPage, null);
				//
				forEach(Util.collect(
						Util.filter(
								testAndApply(Objects::nonNull, VoiceManagerOnlineTtsPanel.class.getDeclaredFields(),
										Arrays::stream, null),
								f -> isAnnotationPresent(f, Name.class) && Narcissus.getField(this, f) != null),
						Collectors.toMap(f -> value(getAnnotation(f, Name.class)), f -> Narcissus.getField(this, f))),
						(a, b) -> {
							//
							final List<DomElement> domElements = getElementsByName(htmlPage, a);
							//
							if (IterableUtils.size(domElements) > 1) {
								//
								throw new IllegalStateException();
								//
							} // if
								//
							final DomElement domElement = testAndApply(x -> IterableUtils.size(x) == 1, domElements,
									x -> IterableUtils.get(x, 0), null);
							//
							if (domElement != null) {
								//
								if (b instanceof JTextComponent jtc) {
									//
									domElement.setTextContent(Util.getText(jtc));
									//
								} else if (b instanceof ComboBoxModel cbm) {
									//
									final Object selectedItem = cbm.getSelectedItem();
									//
									final List<String> keys = Util.toList(Util.map(
											Util.filter(Util.stream(Util.entrySet(voices)),
													x -> Objects.equals(Util.getValue(x), selectedItem)),
											x -> Util.getKey(x)));
									//
									final int size = IterableUtils.size(keys);
									//
									if (size > 1) {
										//
										throw new IllegalStateException();
										//
									} else if (size == 1 && domElement instanceof HtmlSelect htmlSelect) {
										//
										final Iterable<HtmlOption> options = Util.toList(
												Util.filter(Util.stream(getOptions(htmlSelect)), x -> StringUtils
														.equals(getValueAttribute(x), IterableUtils.get(keys, 0))));
										//
										if (IterableUtils.size(options) > 1) {
											//
											throw new IllegalStateException();
											//
										} // if
											//
										final HtmlOption htmlOption = testAndApply(x -> IterableUtils.size(x) == 1,
												options, x -> IterableUtils.get(x, 0), null);
										//
										if (htmlOption != null) {
											//
											htmlSelect.setSelectedIndex(htmlOption.getIndex());
											//
										} // if
											//
									} // if
										//
								} // if
									//
							} // if
								//
						});
				//
				testAndAccept(Objects::nonNull,
						getAttribute(
								getElementsByTagName(Util.cast(HtmlPage.class,
										DomElementUtil.click(Util.cast(DomElement.class,
												querySelector(htmlPage, "input[type=\"submit\"]")))),
										"source"),
								"src", x -> StringUtils.startsWith(x, "./temp/")),
						x -> Util.setText(tfUrl, String.join("/", StringUtils.substringBeforeLast(url, "/"),
								StringUtils.substringAfter(IValue0Util.getValue0(x), '/'))));
				//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} // if
			//
	}

	private static String value(final Name instance) {
		return instance != null ? instance.value() : null;
	}

	private static <T extends Annotation> T getAnnotation(final AnnotatedElement instance,
			final Class<T> annotationClass) {
		return instance != null ? instance.getAnnotation(annotationClass) : null;
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) {
		return instance != null && instance.isAnnotationPresent(annotationClass);
	}

	private static final String getValueAttribute(final HtmlOption instance) {
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
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
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

	private static List<HtmlOption> getOptions(final HtmlSelect instance) {
		return instance != null ? instance.getOptions() : null;
	}

	private static <K, V> void forEach(final Map<K, V> instance, final BiConsumer<? super K, ? super V> action) {
		if (instance != null) {
			instance.forEach(action);
		}
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