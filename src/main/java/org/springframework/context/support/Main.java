package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.PrintStream;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class Main {

	private Main() {
	}

	public static void main(final String[] args) throws IllegalAccessException {
		//
		try (final ConfigurableApplicationContext beanFactory = new ClassPathXmlApplicationContext(
				"applicationContext.xml")) {
			//
			final String string = PropertyResolverUtil.getProperty(beanFactory.getEnvironment(),
					"org.springframework.context.support.Main.class");
			//
			Class<?> clz = forName(string);
			//
			final PrintStream ps = cast(PrintStream.class, FieldUtils.readDeclaredStaticField(System.class, "out"));
			//
			final ConfigurableListableBeanFactory clbf = beanFactory.getBeanFactory();
			//
			if (clz == null) {
				//
				final String[] beanDefinitionNames = getBeanDefinitionNames(clbf);
				//
				BeanDefinition bd = null;
				//
				String[] ss = null;
				//
				Multimap<String, BeanDefinition> multimap = null;
				//
				for (int i = 0; beanDefinitionNames != null && i < beanDefinitionNames.length; i++) {
					//
					if ((bd = clbf.getBeanDefinition(beanDefinitionNames[i])) == null) {
						//
						continue;
						//
					} // if
						//
					if ((ss = StringUtils.split(getBeanClassName(bd), ".")) != null && ss.length > 0
							&& StringUtils.equals(string, ss[ss.length - 1])
							&& (multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create)) != null) {
						//
						multimap.put(string, bd);
						//
					} // if
						//
				} // for
					//
				if (MultimapUtil.size(multimap) == 1) {
					//
					clz = forName(multimap.values().stream().map(Main::getBeanClassName)
							.reduce((first, second) -> first).orElse(null));
					//
				} // if
					//
			} // if
				//
			if (clz == null) {
				//
				final String[] beanNames = getBeanNamesForType(beanFactory, Component.class);
				//
				final JList<Object> list = beanNames != null ? new JList<>(beanNames) : new JList<>();
				//
				JOptionPane.showMessageDialog(null, list, "Component", JOptionPane.PLAIN_MESSAGE);
				//
				final String toString = toString(list.getSelectedValue());
				//
				clz = forName(getBeanClassName(toString != null ? getBeanDefinition(clbf, toString) : null));
				//
			} // if
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
			pack(cast(Window.class, instance));
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

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static void pack(final Window instance) {
		if (instance != null) {
			instance.pack();
		}
	}

	private static String[] getBeanNamesForType(final ListableBeanFactory instance, final Class<?> type) {
		return instance != null ? instance.getBeanNamesForType(type) : null;
	}

	private static String[] getBeanDefinitionNames(final ListableBeanFactory instance) {
		return instance != null ? instance.getBeanDefinitionNames() : null;
	}

	private static String getBeanClassName(final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

	private static BeanDefinition getBeanDefinition(final ConfigurableListableBeanFactory instance,
			final String beanName) {
		return instance != null ? instance.getBeanDefinition(beanName) : null;
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