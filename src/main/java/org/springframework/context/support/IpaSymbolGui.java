package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class IpaSymbolGui extends JFrame implements EnvironmentAware, InitializingBean, ActionListener {

	private static final long serialVersionUID = 467011648930037863L;

	private static Logger LOG = LoggerFactory.getLogger(IpaSymbolGui.class);

	private transient PropertyResolver propertyResolver = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	private JTextComponent tfIpaSymbol = null;

	@Note("Get IPA Symobl")
	private AbstractButton btnGetIpaSymbol = null;

	private AbstractButton btnCheckIpaSymbolJson = null;

	private JLabel jlIpaJsonFile = null;

	private Unit<Multimap<String, String>> ipaSymbolMultimap = null;

	private transient Resource resource = null;

	private String url = null;

	private String messageDigestAlgorithm = null;

	private IpaSymbolGui() {
	}

	public void setIpaSymbolMultimap(final Multimap<String, String> ipaSymbolMultimap) {
		this.ipaSymbolMultimap = Unit.with(ipaSymbolMultimap);
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setMessageDigestAlgorithm(final String messageDigestAlgorithm) {
		this.messageDigestAlgorithm = messageDigestAlgorithm;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
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
		final List<Field> fs = FieldUtils.getAllFieldsList(getClass(this)).stream()
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
		final String wrap = "wrap";
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.text")), wrap);
		//
		add(new JLabel(""));
		//
		add(btnGetIpaSymbol = new JButton("Get IPA Symbol"), wrap);
		//
		add(new JLabel("IPA"));
		//
		add(tfIpaSymbol = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.ipaSymbol")), wrap);
		//
		add(new JLabel(""));
		//
		add(btnCheckIpaSymbolJson = new JButton("Check IPA Symbol Json File"));
		//
		add(jlIpaJsonFile = new JLabel());
		//
		final Dimension preferredSize = Collections.max(
				Arrays.asList(btnGetIpaSymbol.getPreferredSize(), btnCheckIpaSymbolJson.getPreferredSize()), (a, b) -> {
					return a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
				});
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), tfText, tfIpaSymbol, btnGetIpaSymbol,
					btnCheckIpaSymbolJson);
			//
		} // if
			//
		addActionListener(this, btnGetIpaSymbol, btnCheckIpaSymbolJson);
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
			//
			if ((ab = abs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			ab.addActionListener(actionListener);
			//
		} // for
			//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = getSource(evt);
		//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (Objects.equals(source, btnGetIpaSymbol)) {
			//
			setIpaSymbolTextField(headless);
			//
		} else if (Objects.equals(source, btnCheckIpaSymbolJson)) {
			//
			MessageDigest md = null;
			//
			byte[] bs = null;
			//
			Integer length1 = null;
			//
			String hex1 = null;
			//
			try (final InputStream is = testAndApply(ResourceUtil::exists, resource,
					InputStreamSourceUtil::getInputStream, null)) {
				//
				length1 = (bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null)) != null
						? Integer.valueOf(bs.length)
						: null;
				//
				hex1 = testAndApply(Objects::nonNull,
						digest(md = MessageDigest
								.getInstance(StringUtils.defaultIfBlank(messageDigestAlgorithm, "SHA-512")), bs),
						Hex::encodeHexString, null);
				//
			} catch (final IOException | NoSuchAlgorithmException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
			Integer length2 = null;
			//
			String hex2 = null;
			//
			try (final InputStream is = openStream(testAndApply(StringUtils::isNotBlank, url, URL::new, null))) {
				//
				length2 = (bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null)) != null
						? Integer.valueOf(bs.length)
						: null;
				//
				hex2 = testAndApply(Objects::nonNull, digest(md, bs), Hex::encodeHexString, null);
				//
			} catch (final IOException e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
			final boolean match = Boolean.logicalAnd(Objects.equals(length1, length2), Objects.equals(hex1, hex2));
			//
			setText(jlIpaJsonFile, iif(match, "Matched", "Not Matched"));
			//
			setForeground(jlIpaJsonFile, iif(match, Color.GREEN, Color.RED));
			//
			if (!GraphicsEnvironment.isHeadless()) {
				//
				pack();
				//
			} // if
				//
		} // if
			//
	}

	private void setIpaSymbolTextField(final boolean headless) {
		//
		final Collection<String> values = MultimapUtil.get(IValue0Util.getValue0(ipaSymbolMultimap), getText(tfText));
		//
		final int size = IterableUtils.size(values);
		//
		if (size == 0) {
			//
			setText(tfIpaSymbol, null);
			//
		} else if (size == 1) {
			//
			setText(tfIpaSymbol, toString(IterableUtils.get(values, 0)));
			//
		} else if (Boolean.logicalAnd(!headless, !isTestMode())) {
			//
			final JList<Object> list = testAndApply(Objects::nonNull, toArray(values), JList::new, x -> new JList<>());
			//
			JOptionPane.showMessageDialog(null, list, "IPA", JOptionPane.PLAIN_MESSAGE);
			//
			setText(tfIpaSymbol, toString(list.getSelectedValue()));
			//
		} // if
			//
	}

	private static void setText(final JLabel instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setForeground(final Component instance, final Color color) {
		if (instance != null) {
			instance.setForeground(color);
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static InputStream openStream(final URL instance) throws IOException {
		//
		// Check if "handler" field in "java.net.URL" class is null or not
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1,
				Arrays.stream(URL.class.getDeclaredFields())
						.filter(x -> x != null && Objects.equals(x.getName(), "handler")).toList(),
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

	private static byte[] digest(final MessageDigest instance, final byte[] input) {
		return instance != null && input != null ? instance.digest(input) : null;
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable) {
		//
		TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(headless, LOG, throwable);
		//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static boolean isTestMode() {
		return forName("org.junit.jupiter.api.Test") != null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static void setPreferredWidth(final int width, final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = getPreferredSize(c)) == null) {
				//
				continue;
				//
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

}