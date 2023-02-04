package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import net.miginfocom.swing.MigLayout;

public class JlptLevelGui extends JFrame implements InitializingBean, ActionListener {

	private static final long serialVersionUID = 1466869508176258464L;

	private List<String> jlptLevels = null;

	private String url = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Export JSON")
	private AbstractButton btnExportJson = null;

	@Note("Copy")
	private AbstractButton btnCopy = null;

	private AbstractButton btnCompare = null;

	private JTextComponent tfJson = null;

	private JLabel jlCompare = null;

	private ObjectMapper objectMapper = null;

	private JlptLevelGui() {
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = jlptLevels;
	}

	public void setUrl(final String url) {
		this.url = url;
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
		add(new JLabel("JLPT Level(s)"));
		//
		final ListModel<String> listModel = testAndApply(Objects::nonNull, toArray(jlptLevels, new String[] {}),
				DefaultComboBoxModel::new, null);
		//
		final JList<String> jList = listModel != null ? new JList<>(listModel) : new JList<>();
		//
		final String wrap = "wrap";
		//
		add(jList, wrap);
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
		addActionListener(this, btnExportJson, btnCopy, btnCompare);
		//
		final List<Component> cs = Arrays.asList(jList, btnExportJson, tfJson, btnCompare);
		//
		final Dimension preferredSize = cs.stream().map(JlptLevelGui::getPreferredSize).max((a, b) -> {
			return a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
		}).orElse(null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
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

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) {
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

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
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
			try {
				//
				setText(tfJson, ObjectMapperUtil.writeValueAsString(getObjectMapper(), jlptLevels));
				//
			} catch (JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnCopy)) {
			//
			run(forName("org.junit.jupiter.api.Test") == null,
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
		} // if
			//
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?>[] getParameterTypes(final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
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

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
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

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}