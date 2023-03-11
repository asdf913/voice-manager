package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
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
					if ((bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(clbf,
							beanDefinitionNames[i])) == null) {
						//
						continue;
						//
					} // if
						//
					if ((ss = StringUtils.split(getBeanClassName(bd), ".")) != null && ss.length > 0
							&& StringUtils.equals(string, ss[ss.length - 1])) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), string,
								bd);
						//
					} // if
						//
				} // for
					//
				if (MultimapUtil.size(multimap) == 1) {
					//
					clz = forName(MultimapUtil.values(multimap).stream().map(Main::getBeanClassName)
							.reduce((first, second) -> first).orElse(null));
					//
				} // if
					//
			} // if
				//
			if (clz == null) {
				//
				final JList<Object> list = testAndApply(Objects::nonNull,
						getBeanNamesForType(beanFactory, Component.class), JList::new, x -> new JList<>());
				//
				JOptionPane.showMessageDialog(null, list, "Component", JOptionPane.PLAIN_MESSAGE);
				//
				clz = forName(getBeanClassName(testAndApply(Objects::nonNull, toString(getSelectedValue(list)),
						x -> ConfigurableListableBeanFactoryUtil.getBeanDefinition(clbf, x), null)));
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
			setVisible(cast(Component.class, instance), true);
			//
		} // try
			//
	}

	@Nullable
	private static <E> E getSelectedValue(@Nullable final JList<E> instance) {
		return instance != null ? instance.getSelectedValue() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static void pack(@Nullable final Window instance) {
		if (instance != null) {
			instance.pack();
		}
	}

	private static void setVisible(@Nullable final Component instance, boolean b) {
		if (instance != null) {
			instance.setVisible(b);
		}
	}

	@Nullable
	private static String[] getBeanNamesForType(@Nullable final ListableBeanFactory instance, final Class<?> type) {
		return instance != null ? instance.getBeanNamesForType(type) : null;
	}

	@Nullable
	private static String[] getBeanDefinitionNames(@Nullable final ListableBeanFactory instance) {
		return instance != null ? instance.getBeanDefinitionNames() : null;
	}

	@Nullable
	private static String getBeanClassName(@Nullable final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

	@Nullable
	private static <T> T cast(final Class<T> clz, @Nullable final Object instance) {
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

	@Nullable
	private static Class<?> forName(@Nullable final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	@Nullable
	private static Object getInstance(final ListableBeanFactory beanFactory, @Nullable final Class<?> clz,
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