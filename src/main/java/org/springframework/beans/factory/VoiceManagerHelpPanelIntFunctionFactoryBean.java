package org.springframework.beans.factory;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
import java.util.function.IntFunction;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.helper.ProtocolUtil;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.select.Elements;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.TemplateException;
import freemarker.template.TemplateUtil;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.tags.ContainerTagUtil;
import j2html.tags.TagUtil;
import j2html.tags.specialized.ATag;

public class VoiceManagerHelpPanelIntFunctionFactoryBean implements FactoryBean<IntFunction<JScrollPane>> {

	private static Logger LOG = LoggerFactory.getLogger(VoiceManagerHelpPanelIntFunctionFactoryBean.class);

	private static final String HANDLER = "handler";

	private static final String COMPONENT = "component";

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Url {
		String value();
	}

	@Url("https://help.libreoffice.org/latest/en-US/text/shared/01/moviesound.html")
	private String mediaFormatPageUrl = null;

	@Url("https://poi.apache.org/encryption.html")
	private String poiEncryptionPageUrl = null;

	@Url("https://www.microsoft.com/en-us/download/details.aspx?id=27225")
	private IValue0<String> mediaFormatPageHref = null;

	private IValue0<String> mediaFormatPageText = null;

	private Configuration freeMarkerConfiguration = null;

	private Resource encryptionTableHtmlResource = null;

	@Nullable
	private Duration jSoupParseTimeout = null;

	public void setMediaFormatPageUrl(final String mediaFormatPageUrl) {
		this.mediaFormatPageUrl = mediaFormatPageUrl;
	}

	public void setMediaFormatPageHref(final String mediaFormatPageHref) {
		this.mediaFormatPageHref = Unit.with(mediaFormatPageHref);
	}

	public void setMediaFormatPageText(final String mediaFormatPageText) {
		this.mediaFormatPageText = Unit.with(mediaFormatPageText);
	}

	public void setPoiEncryptionPageUrl(final String poiEncryptionPageUrl) {
		this.poiEncryptionPageUrl = poiEncryptionPageUrl;
	}

	public void setFreeMarkerConfiguration(final Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	public void setEncryptionTableHtmlResource(final Resource encryptionTableHtmlResource) {
		this.encryptionTableHtmlResource = encryptionTableHtmlResource;
	}

	public void setjSoupParseTimeout(@Nullable final Object object) {
		//
		if (object == null) {
			//
			this.jSoupParseTimeout = null;
			//
			return;
			//
		} else if (object instanceof Duration duration) {
			//
			this.jSoupParseTimeout = duration;
			//
			return;
			//
		} else if (object instanceof Number number) {
			//
			this.jSoupParseTimeout = Duration.ofMillis(longValue(number, 0));
			//
			return;
			//
		} // if
			//
		final String string = Util.toString(object);
		//
		final Integer integer = valueOf(string);
		//
		if (integer != null) {
			//
			setjSoupParseTimeout(integer);
			//
			return;
			//
		} // if
			//
		this.jSoupParseTimeout = testAndApply(StringUtils::isNotBlank, string, Duration::parse, null);
		//
	}

	@Nullable
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static long longValue(@Nullable final Number instance, final long defaultValue) {
		return instance != null ? instance.longValue() : defaultValue;
	}

	@Override
	public IntFunction<JScrollPane> getObject() throws Exception {
		//
		return x -> {
			//
			JEditorPane jep = null;
			//
			try (final Writer writer = new StringWriter()) {
				//
				final Map<Object, Object> map = new LinkedHashMap<>(Collections.singletonMap("statics",
						new BeansWrapper(Configuration.getVersion()).getStaticModels()));
				//
				ATag aTag = null;
				//
				if (Boolean.logicalAnd(mediaFormatPageHref != null, mediaFormatPageText != null)) {
					//
					aTag = ContainerTagUtil.withText(
							TagUtil.attr(new ATag(), "href", IValue0Util.getValue0(mediaFormatPageHref)),
							IValue0Util.getValue0(mediaFormatPageText));
					//
				} else {
					//
					aTag = getMediaFormatLink(mediaFormatPageUrl);
					//
				} // if
					//
				Util.put(map, "mediaFormatLink", aTag);
				//
				String encryptionTableHtml = null;
				//
				byte[] bs = null;
				//
				if (ResourceUtil.exists(encryptionTableHtmlResource) && ResourceUtil.isFile(encryptionTableHtmlResource)
						&& ResourceUtil.isReadable(encryptionTableHtmlResource)
						&& (bs = ResourceUtil.getContentAsByteArray(encryptionTableHtmlResource)) != null) {
					//
					encryptionTableHtml = IOUtils.toString(bs, Util.toString(StandardCharsets.UTF_8));
					//
				} else {
					//
					encryptionTableHtml = getEncryptionTableHtml(
							testAndApply(StringUtils::isNotBlank, poiEncryptionPageUrl, y -> new URI(y).toURL(), null),
							jSoupParseTimeout);
					//
				} // if
					//
				Util.put(map, "encryptionTableHtml", encryptionTableHtml);
				//
				TemplateUtil.process(ConfigurationUtil.getTemplate(freeMarkerConfiguration, "help.html.ftl"), map,
						writer);
				//
				final String html = Util.toString(writer);
				//
				setEditable(false, jep = new JEditorPane(StringUtils.defaultIfBlank(
						getMimeType(new ContentInfoUtil().findMatch(getBytes(html))), "text/html"), html));
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
			addHyperlinkListener(jep, createHyperlinkListener());
			//
			final JScrollPane jsp = new JScrollPane(jep);
			//
			jsp.setPreferredSize(new Dimension(intValue(getPreferredWidth(jsp), 0),
					intValue(x, intValue(getPreferredHeight(jsp), 0))));
			//
			final JPanel jPanel = new JPanel();
			//
			add(jPanel, jsp);
			//
			jsp.setName("Help");
			//
			return jsp;
			//
		};
	}

	private static HyperlinkListener createHyperlinkListener() {
		//
		return event -> {
			//
			try {
				//
				if (Objects.equals(EventType.ACTIVATED, getEventType(event)) && !GraphicsEnvironment.isHeadless()) {
					//
					browse(Desktop.getDesktop(), toURI(getURL(event)));
					//
				} // if
					//
			} catch (final IOException | URISyntaxException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		};
		//
	}

	@Override
	public Class<?> getObjectType() {
		return IntFunction.class;
	}

	private static void add(@Nullable final Container instance, @Nullable final Component comp) {
		//
		if (instance == null) {
			//
			return;
			//
		} //
			//
		try {
			//
			if (Narcissus.getObjectField(instance, Util.getDeclaredField(Container.class, COMPONENT)) == null) {
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
		if (comp != null) {
			//
			instance.add(comp);
			//
		} // if
			//
	}

	private static void browse(@Nullable final Desktop instance, @Nullable final URI uri) throws IOException {
		if (instance != null && uri != null) {
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
			if (Narcissus.getField(instance, Util.getDeclaredField(URL.class, HANDLER)) == null) {
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

	@Nullable
	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	@Nullable
	private static Double getPreferredHeight(final Component c) {
		//
		final Dimension d = getPreferredSize(c);
		//
		return d != null ? Double.valueOf(d.getHeight()) : null;
		//
	}

	@Nullable
	public static Dimension getPreferredSize(@Nullable final Component instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getStaticField(Narcissus.findField(Util.getClass(instance), "LOCK")) == null) {
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
		return instance.getPreferredSize();
		//
	}

	@Nullable
	private static URL getURL(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getURL() : null;
	}

	@Nullable
	private static EventType getEventType(@Nullable final HyperlinkEvent instance) {
		return instance != null ? instance.getEventType() : null;
	}

	@Nullable
	private static byte[] getBytes(@Nullable final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
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
			if (Narcissus.getObjectField(instance, Util.getDeclaredField(JComponent.class, "listenerList")) == null) {
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

	@Nullable
	private static String getEncryptionTableHtml(final URL url, final Duration timeout) throws IOException {
		//
		org.jsoup.nodes.Document document = testAndApply(
				x -> StringsUtil.equalsAny(Strings.CI, getProtocol(x), ProtocolUtil.getAllowProtocols()), url,
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
		return ElementUtil
				.html(ElementUtil.nextElementSibling(IterableUtils.size(h2s) == 1 ? IterableUtils.get(h2s, 0) : null));
		//
	}

	@Nullable
	private static String getProtocol(@Nullable final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	@Nullable
	private static ATag getMediaFormatLink(final String url) throws Exception {
		//
		InputStream is = null;
		//
		ATag aTag = null;
		//
		try {
			//
			final List<Method> ms = Util.toList(Util.filter(
					testAndApply(Objects::nonNull, getDeclaredMethods(forName("com.sun.jna.Platform")), Arrays::stream,
							null),
					m -> m != null && Objects.equals(m.getReturnType(), Boolean.TYPE) && m.getParameterCount() == -0));
			//
			Method m = null;
			//
			List<String> methodNames = null;
			//
			for (int i = 0; i < IterableUtils.size(ms); i++) {
				//
				if ((m = IterableUtils.get(ms, i)) == null || !isStatic(m)
						|| !Objects.equals(Boolean.TRUE, Narcissus.invokeStaticMethod(m))) {
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
			final Strings strings = Strings.CI;
			//
			for (int i = 0; i < IterableUtils.size(elements); i++) {
				//
				for (int j = 0; j < IterableUtils.size(methodNames); j++) {
					//
					if (!StringsUtil.startsWith(strings, methodName = IterableUtils.get(methodNames, j), "is")
							|| !StringsUtil.contains(strings,
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

	@Nullable
	private static Method[] getDeclaredMethods(@Nullable final Class<?> instance) throws SecurityException {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Nullable
	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
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
			if (Narcissus.getField(instance, Util.getDeclaredField(Util.getClass(instance), HANDLER)) == null) {
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}