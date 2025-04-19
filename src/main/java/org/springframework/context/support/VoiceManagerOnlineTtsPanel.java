package org.springframework.context.support;

import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlPage;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerOnlineTtsPanel extends JPanel
		implements InitializingBean, ApplicationContextAware, ActionListener {

	private static final long serialVersionUID = 1679789881293611910L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerOnlineTtsPanel.class);

	private transient ApplicationContext applicationContext = null;

	private String url = null;// TODO https://open-jtalk.sp.nitech.ac.jp/index.php

	private JTextComponent tfText, tfUrl = null;

	private AbstractButton btnExecute = null;

	public void setUrl(final String url) {
		this.url = url;
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
		add(new JLabel("Input"));
		//
		add(tfText = new JTextField(), String.format("wrap,wmin %1$spx", 100));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), "wrap");
		//
		btnExecute.addActionListener(this);
		//
		add(new JLabel("Output"));
		//
		add(tfUrl = new JTextField(), String.format("wrap,wmin %1$spx", 100));
		//
		tfUrl.setEditable(false);
		//
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
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
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
				HtmlPage htmlPage = testAndApply(Objects::nonNull, url, webClient::getPage, null);
				//
				NodeList nodeList = getElementsByTagName(htmlPage, "textarea");
				//
				Node node = null;
				//
				for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
					//
					if ((node = nodeList.item(i)) == null) {
						//
						continue;
						//
					} // if
						//
					node.setTextContent(Util.getText(tfText));
					//
				} // for
					//
				nodeList = getElementsByTagName(
						htmlPage = Util.cast(HtmlPage.class,
								click(Util.cast(DomElement.class, querySelector(htmlPage, "input[type=\"submit\"]")))),
						"source");
				//
				NamedNodeMap attributes = null;
				//
				Node namedItem = null;
				//
				String nodeValue = null;
				//
				IValue0<String> src = null;
				//
				for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
					//
					if ((node = nodeList.item(i)) == null || (attributes = node.getAttributes()) == null
							|| (namedItem = attributes.getNamedItem("src")) == null) {
						//
						continue;
						//
					} // if
						//
					if (StringUtils.startsWith(nodeValue = namedItem.getNodeValue(), "./temp/")) {
						//
						if (src == null) {
							//
							src = Unit.with(nodeValue);
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
				if (src != null) {
					//
					Util.setText(tfUrl, String.join("/", StringUtils.substringBeforeLast(url, "/"),
							StringUtils.substringAfter(IValue0Util.getValue0(src), '/')));
					//
				} // if
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

	private static <N extends DomNode> N querySelector(final DomNode instance, final String selectors) {
		return instance != null ? instance.querySelector(selectors) : null;
	}

	private static NodeList getElementsByTagName(final Document instance, final String tagname) {
		return instance != null ? instance.getElementsByTagName(tagname) : null;
	}

	private static <P extends Page> P click(final DomElement instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "page_")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getObjectField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.click();
		//
		//
	}

}