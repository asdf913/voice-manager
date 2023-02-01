package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.PrintStream;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertyResolverUtil;

public class Main {

	private Main() {
	}

	public static void main(final String[] args) throws IllegalAccessException {
		//
		try (final ConfigurableApplicationContext beanFactory = new ClassPathXmlApplicationContext(
				"applicationContext.xml")) {
			//
			final Class<?> clz = forName(PropertyResolverUtil.getProperty(beanFactory.getEnvironment(),
					"org.springframework.context.support.Main.class"));
			//
			final PrintStream ps = cast(PrintStream.class, FieldUtils.readDeclaredStaticField(System.class, "out"));
			//
			if (clz == null) {
				//
				showMessageDialogOrPrintln(ps, "java.lang.Class is null");
				//
				return;
				//
			} // if
				//
			final Object instance = getInstance(beanFactory, clz, x -> showMessageDialogOrPrintln(ps, x));
			//
			if (instance instanceof Window) {
				//
				((Window) instance).pack();
				//
			} // if
				//
			if (instance instanceof Component) {
				//
				((Component) instance).setVisible(true);
				//
			} // if
				//
		} // try
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static void showMessageDialogOrPrintln(final PrintStream ps, final Object object) {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			JOptionPane.showMessageDialog(null, object);
			//
		} else if (ps != null) {
			//
			ps.println(object);
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

	private static Object getInstance(final ListableBeanFactory beanFactory, final Class<?> clz,
			final Consumer<String> consumer) {
		//
		if (clz == null) {
			//
			accept(consumer, "java.lang.Class is null");
			//
			return null;
			//
		} // if
			//
		final Map<?, ?> beans = ListableBeanFactoryUtil.getBeansOfType(beanFactory, clz);
		//
		if (beans == null) {
			//
			accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s) return null", clz));
			//
			return null;
			//
		} else if (beans.isEmpty()) {
			//
			accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s) return empty", clz));
			//
			return null;
			//
		} else if (beans.size() > 1) {
			//
			accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s).size()>1", clz));
			//
			return null;
			//
		} // if
			//
		return IterableUtils.first(beans.values());
		//
	}

	private static <T> void accept(final Consumer<T> instance, final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

}