package org.springframework.context.support;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ConfigurationUtil;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.tags.ContainerTagUtil;
import j2html.tags.TagUtil;
import j2html.tags.specialized.ATag;

public class VoiceManagerHelpPanel extends JScrollPane implements Titled, InitializingBean {

	private static final long serialVersionUID = 6912789824080220210L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerHelpPanel.class);

	private static final String HANDLER = "handler";

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Url("https://help.libreoffice.org/latest/en-US/text/shared/01/moviesound.html")
	private String mediaFormatPageUrl = null;

	@Url("https://poi.apache.org/encryption.html")
	private String poiEncryptionPageUrl = null;

	private transient freemarker.template.Configuration freeMarkerConfiguration = null;

	private Duration jSoupParseTimeout = null;

	@Override
	public String getTitle() {
		return "Help";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		JEditorPane jep = null;
		//
		try (final Writer writer = new StringWriter()) {
			//
			final Map<Object, Object> map = new LinkedHashMap<>(Collections.singletonMap("statics",
					new BeansWrapper(freemarker.template.Configuration.getVersion()).getStaticModels()));
			//
			Util.put(map, "mediaFormatLink", getMediaFormatLink(mediaFormatPageUrl));
			//
			Util.put(map, "encryptionTableHtml",
					getEncryptionTableHtml(
							testAndApply(StringUtils::isNotBlank, poiEncryptionPageUrl, x -> new URI(x).toURL(), null),
							jSoupParseTimeout));
			//
			TemplateUtil.process(ConfigurationUtil.getTemplate(freeMarkerConfiguration, "help.html.ftl"), map, writer);
			//
			final String html = Util.toString(writer);
			//
			setEditable(false, jep = new JEditorPane(StringUtils
					.defaultIfBlank(getMimeType(new ContentInfoUtil().findMatch(getBytes(html))), "text/html"), html));
			//
		} catch (final IOException | TemplateException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} catch (final Exception e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(
					ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		addHyperlinkListener(jep, x -> {
			//
			try {
				//
				if (Objects.equals(EventType.ACTIVATED, getEventType(x))) {
					//
					browse(Desktop.getDesktop(), toURI(getURL(x)));
					//
				} // if
					//
			} catch (final IOException | URISyntaxException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		});
		//
		final JScrollPane jsp = new JScrollPane(jep);
		//
		final Double preferredHeight = 0.0d;
		// getMaxPagePreferredHeight(jTabbedPane);//TODO
		//
		jsp.setPreferredSize(new Dimension(intValue(getPreferredWidth(jsp), 0),
				intValue(preferredHeight, intValue(getPreferredHeight(jsp), 0))));
		//
		add(jsp);
		//
	}

	@Nullable
	private static Double getPreferredHeight(final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getHeight()) : null;
		//
	}

	@Nullable
	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = Util.getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	private static void browse(@Nullable final Desktop instance, final URI uri) throws IOException {
		if (instance != null) {
			instance.browse(uri);
		}
	}

	@Nullable
	private static URI toURI(@Nullable final URL instance) throws URISyntaxException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, URL.class.getDeclaredField(HANDLER)) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.toURI();
		//
	}

	private static URL getURL(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getURL() : null;
	}

	private static EventType getEventType(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getEventType() : null;
	}

	private static void addHyperlinkListener(@Nullable final JEditorPane instance, final HyperlinkListener listener) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getObjectField(instance, JComponent.class.getDeclaredField("listenerList")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.addHyperlinkListener(listener);
		//
	}

	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static byte[] getBytes(@Nullable final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	private static void setEditable(final boolean editable, @Nullable final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
				continue;
			} // if
				//
			jtc.setEditable(editable);
			//
		} // for
			//
	}

	private static String getEncryptionTableHtml(final URL url, final Duration timeout) throws IOException {
		//
		org.jsoup.nodes.Document document = testAndApply(
				x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x), ProtocolUtil.getAllowProtocols()), url,
				x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null);
		//
		if (document == null) {
			//
			document = testAndApply(Objects::nonNull,
					testAndApply(Objects::nonNull, url, x -> IOUtils.toString(x, StandardCharsets.UTF_8), null),
					Jsoup::parse, null);
			//
		} // if
			//
		final Elements h2s = ElementUtil.selectXpath(document, "//h2[text()=\"Supported feature matrix\"]");
		//
		return html(ElementUtil.nextElementSibling(IterableUtils.size(h2s) == 1 ? IterableUtils.get(h2s, 0) : null));
		//
	}

	private static String html(@Nullable final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.html() : null;
	}

	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static ATag getMediaFormatLink(final String url) throws Exception {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.forName("com.sun.jna.Platform")),
							Arrays::stream, null),
					m -> m != null && Objects.equals(m.getReturnType(), Boolean.TYPE) && m.getParameterCount() == -0));
			//
			Method m = null;
			//
			List<String> methodNames = null;
			//
			for (int i = 0; i < IterableUtils.size(ms); i++) {
				//
				if ((m = IterableUtils.get(ms, i)) == null || !isStatic(m)) {
					//
					continue;
					//
				} // if
					//
				if (!Objects.equals(Boolean.TRUE, Narcissus.invokeStaticMethod(m))) {
					//
					continue;
					//
				} // if
					//
				Util.add(methodNames = ObjectUtils.getIfNull(methodNames, ArrayList::new), Util.getName(m));
				//
			} // for
				//
			final Elements elements = ElementUtil.select(testAndApply(Objects::nonNull,
					(is = openStream(testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null))) != null
							? IOUtils.toString(is, StandardCharsets.UTF_8)
							: null,
					Jsoup::parse, null), ".relatedtopics a[href]");
			//
			org.jsoup.nodes.Element element = null;
			//
			String textContent, methodName = null;
			//
			for (int i = 0; i < IterableUtils.size(elements); i++) {
				//
				for (int j = 0; j < IterableUtils.size(methodNames); j++) {
					//
					if (!StringUtils.startsWithIgnoreCase(methodName = IterableUtils.get(methodNames, j), "is")
							|| !StringUtils.containsIgnoreCase(
									textContent = ElementUtil.text(element = IterableUtils.get(elements, i)),
									StringUtils.substringAfter(methodName, "is"))
							|| aTag != null) {
						//
						continue;
						//
					} // if
						//
					TagUtil.attr(ContainerTagUtil.withText(aTag = new ATag(), textContent), "href",
							NodeUtil.attr(element, "href"));
					//
				} // for
					//
			} // for
				//
		} finally {
			//
			IOUtils.closeQuietly(is);
			//
		} // try
			//
		return aTag;
		//
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(Util.getClass(instance), HANDLER)) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.openStream();
	}

	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}
