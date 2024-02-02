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
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;
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
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.config.Title;
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

@Title("IPA Symbol")
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
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		final boolean isGui = f == null || Narcissus.getObjectField(this, f) != null;
		//
		final String wrap = "wrap";
		//
		final Predicate<Component> predicate = Predicates.always(isGui, null);
		//
		testAndAccept(predicate, new JLabel("Text"), this::add);
		//
		final BiPredicate<Component, Object> biPredicate = Predicates.biAlways(isGui, null);
		//
		testAndAccept(biPredicate, tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.text")), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel(""), this::add);
		//
		testAndAccept(biPredicate, btnGetIpaSymbol = new JButton("Get IPA Symbol"), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel("IPA"), this::add);
		//
		testAndAccept(biPredicate, tfIpaSymbol = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.ipaSymbol")), wrap, this::add);
		//
		testAndAccept(predicate, new JLabel(""), this::add);
		//
		testAndAccept(predicate, btnCheckIpaSymbolJson = new JButton("Check IPA Symbol Json File"), this::add);
		//
		testAndAccept(predicate, jlIpaJsonFile = new JLabel(), this::add);
		//
		final Dimension preferredSize = Collections.max(
				Arrays.asList(btnGetIpaSymbol.getPreferredSize(), btnCheckIpaSymbolJson.getPreferredSize()),
				(a, b) -> a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0);
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

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) throws E {
		if (Util.test(predicate, value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (predicate != null && predicate.test(t, u) && consumer != null) {
			consumer.accept(t, u);
		}
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
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
		final Object source = Util.getSource(evt);
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
			try (final InputStream is = openStream(
					testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null))) {
				//
				length2 = (bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null)) != null
						? Integer.valueOf(bs.length)
						: null;
				//
				hex2 = testAndApply(Objects::nonNull, digest(md, bs), Hex::encodeHexString, null);
				//
			} catch (final Exception e) {
				//
				errorOrAssertOrShowException(headless, e);
				//
			} // try
				//
			final boolean match = Boolean.logicalAnd(Objects.equals(length1, length2), Objects.equals(hex1, hex2));
			//
			Util.setText(jlIpaJsonFile, iif(match, "Matched", "Not Matched"));
			//
			Util.setForeground(jlIpaJsonFile, iif(match, Color.GREEN, Color.RED));
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
		final Collection<String> values = MultimapUtil.get(IValue0Util.getValue0(ipaSymbolMultimap),
				Util.getText(tfText));
		//
		final int size = IterableUtils.size(values);
		//
		if (size == 0) {
			//
			Util.setText(tfIpaSymbol, null);
			//
		} else if (size == 1) {
			//
			Util.setText(tfIpaSymbol, Util.toString(IterableUtils.get(values, 0)));
			//
		} else if (Boolean.logicalAnd(!headless, !isTestMode())) {
			//
			final JList<Object> list = testAndApply(Objects::nonNull, toArray(values), JList::new, x -> new JList<>());
			//
			JOptionPane.showMessageDialog(null, list, "IPA", JOptionPane.PLAIN_MESSAGE);
			//
			Util.setText(tfIpaSymbol, Util.toString(list.getSelectedValue()));
			//
		} // if
			//
	}

	@Nullable
	private static Object[] toArray(@Nullable final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		//
		// Check if "handler" field in "java.net.URL" class is null or not
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1,
				Util.toList(Util.filter(Arrays.stream(Util.getDeclaredFields(URL.class)),
						x -> Objects.equals(Util.getName(x), "handler"))),
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

	@Nullable
	private static byte[] digest(@Nullable final MessageDigest instance, @Nullable final byte[] input) {
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

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void setPreferredWidth(final int width, @Nullable final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = Util.getPreferredSize(c)) == null) {
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

}