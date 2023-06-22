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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	private Main() {
	}

	public static void main(final String[] args) throws IllegalAccessException {
		//
		try (final ConfigurableApplicationContext beanFactory = new ClassPathXmlApplicationContext(
				"applicationContext.xml")) {
			//
			final ConfigurableListableBeanFactory clbf = beanFactory.getBeanFactory();
			//
			Class<?> clz = getClass(clbf, beanFactory.getEnvironment(),
					"org.springframework.context.support.Main.class");
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
			pack(cast(Window.class, instance));
			//
			setVisible(cast(Component.class, instance), true);
			//
		} // try
			//
	}

	private static Class<?> getClass(final ConfigurableListableBeanFactory clbf,
			final PropertyResolver propertyResolver, final String key) {
		//
		final String string = PropertyResolverUtil.getProperty(propertyResolver, key);
		//
		Class<?> clz = forName(string);
		//
		if (clz == null) {
			//
			final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(clbf);
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
		return clz;
		//

	}

	@Nullable
	private static <E> E getSelectedValue(@Nullable final JList<E> instance) {
		return instance != null ? instance.getSelectedValue() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance != null) {
			//
			try {
				//
				if (Narcissus.getObjectField(instance, Component.class.getDeclaredField("peer")) == null && Objects
						.equals("sun.awt.HeadlessToolkit", getName(getClass(Narcissus.invokeObjectMethod(instance,
								Component.class.getDeclaredMethod("getComponentFactory")))))) {// TODO
					//
					return;
					//
				} // if
					//
			} catch (final NoSuchFieldException | NoSuchMethodException e) {
				//
				if (LOG != null) {
					//
					LOG.error(e.getMessage(), e);
					//
				} else if (e != null) {
					//
					e.printStackTrace();
					//
				} // if
					//
			} // try
				//
			instance.pack();
			//
		} // if
			//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
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
	private static String getBeanClassName(@Nullable final BeanDefinition instance) {
		return instance != null ? instance.getBeanClassName() : null;
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, @Nullable final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static void showMessageDialogOrPrintln(@Nullable final PrintStream ps, final Object object) {
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

	private static <T> void accept(@Nullable final Consumer<T> instance, final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

}