package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEW;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValuesUtil;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionUtil;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ConfigurableApplicationContextUtil;
import org.springframework.core.env.EnvironmentCapableUtil;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphUtil;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.classgraph.ModuleInfo;
import io.github.classgraph.ScanResultUtil;
import io.github.toolfactory.narcissus.Narcissus;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	private Main() {
	}

	private static class CustomClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

		private CustomClassPathXmlApplicationContext(final String configLocation) {
			//
			super(configLocation);
			//
		}

		@Override
		protected void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
			//
			final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(beanFactory);
			//
			BeanDefinition bd = null;
			//
			for (int i = 0; beanDefinitionNames != null && i < beanDefinitionNames.length; i++) {
				//
				if (GraphicsEnvironment.isHeadless()
						&& Util.isAssignableFrom(Window.class,
								Util.forName(BeanDefinitionUtil.getBeanClassName(
										bd = beanFactory.getBeanDefinition(ArrayUtils.get(beanDefinitionNames, i)))))
						&& bd instanceof AbstractBeanDefinition abd) {
					//
					Util.clear(getPropertyValueList(getPropertyValues(bd)));
					//
					abd.setBeanClass(Object.class);
					//
				} // if
					//
			} // for
				//
			final Class<?> classUrl = Util.forName("org.springframework.beans.factory.URL");
			//
			final List<ClassInfo> classInfos = getClassInfoList(classUrl);
			//
			Field[] fs = null;
			//
			for (int i = 0; i < IterableUtils.size(classInfos); i++) {
				//
				try {
					//
					if ((fs = testAndApply(Objects::nonNull,
							Util.forName(HasNameUtil.getName(IterableUtils.get(classInfos, i))),
							FieldUtils::getAllFields, null)) == null) {
						//
						continue;
						//
					} // if
						//
				} catch (final Throwable e) {
					//
				} // try
					//
				for (int j = 0; j < fs.length; j++) {
					//
					try {
						//
						FailableConsumerUtil.accept(x -> addMutablePropertyValues(Util.getAnnotations(x), classUrl,
								beanDefinitionNames, beanFactory, x), fs[j]);
						//
					} catch (final NoSuchMethodException e) {
						//
						LoggerUtil.error(LOG, e.getMessage(), e);
						//
					} // try
						//
				} // for
					//
			} // for
				//
		}

		@Nullable
		private static MutablePropertyValues getPropertyValues(@Nullable final BeanDefinition instance) {
			return instance != null ? instance.getPropertyValues() : null;
		}

		@Nullable
		private static List<PropertyValue> getPropertyValueList(@Nullable final MutablePropertyValues instance) {
			return instance != null ? instance.getPropertyValueList() : null;
		}

		private static void addMutablePropertyValues(@Nullable final Annotation[] as, final Class<?> clz,
				final String[] beanDefinitionNames, final ConfigurableListableBeanFactory beanFactory,
				@Nullable final Field f) throws NoSuchMethodException {
			//
			Annotation a;
			//
			List<Field> fields = null;
			//
			for (int k = 0; as != null && k < as.length; k++) {
				//
				if (isProxyClass(Util.getClass(a = as[k]))) {
					//
					if (IterableUtils.size(fields = Util.toList(Util.filter(
							Arrays.stream(FieldUtils.getAllFields(Util.getClass(Proxy.getInvocationHandler(a)))),
							x -> Objects.equals(Util.getName(x), "type")))) > 1) {
						//
						throw new RuntimeException();
						//
					} // if
						//
					if (!Objects.equals(Util.getName(Util.cast(Class.class,
							Narcissus.getField(Proxy.getInvocationHandler(a),
									testAndApply(x -> IterableUtils.size(x) == 1, fields, x -> IterableUtils.get(x, 0),
											null)))),
							Util.getName(clz))
							|| noneMatch(
									testAndApply(Objects::nonNull, Util.getMethods(Util.getDeclaringClass(f)),
											Arrays::stream, null),
									m -> Objects.equals("set" + StringUtils.capitalize(Util.getName(f)),
											Util.getName(m)))) {
						//
						continue;
						//
					} // if
						//
					addMutablePropertyValues(beanDefinitionNames, f, beanFactory, a);
					//
				} // if
					//
			} // for
				//
		}

		private static <T> boolean noneMatch(@Nullable final Stream<T> instance, final Predicate<? super T> predicate) {
			return instance == null || instance.noneMatch(predicate);
		}

		private static void addMutablePropertyValues(@Nullable final String[] beanDefinitionNames,
				@Nullable final Field f, final ConfigurableListableBeanFactory beanFactory, final Object instance)
				throws NoSuchMethodException {
			//
			BeanDefinition bd = null;
			//
			MutablePropertyValues pv = null;
			//
			for (int l = 0; beanDefinitionNames != null && l < beanDefinitionNames.length; l++) {
				//
				if (Boolean.logicalOr(
						!Objects.equals(Util.getName(Util.getDeclaringClass(f)),
								BeanDefinitionUtil.getBeanClassName(bd = ConfigurableListableBeanFactoryUtil
										.getBeanDefinition(beanFactory, beanDefinitionNames[l]))),
						PropertyValuesUtil.contains(pv = BeanDefinitionUtil.getPropertyValues(bd), Util.getName(f)))) {
					//
					continue;
					//
				} // if
					//
				add(pv, Util.getName(f),
						Narcissus.invokeMethod(instance, Util.getDeclaredMethod(Util.getClass(instance), "value")));
				//
			} // for
				//
		}

		@Nullable
		private static List<ClassInfo> getClassInfoList(@Nullable final Class<?> classUrl) {
			//
			List<ClassInfo> classInfos = null;
			//
			if (classUrl != null && classUrl.getModifiers() == 9728) {
				//
				if ((classInfos = ScanResultUtil.getAllClasses(ClassGraphUtil
						.scan(new ClassGraph().acceptPackages(Util.getName(classUrl.getPackage()))))) != null) {
					//
					classInfos.removeIf(x -> getModuleInfo(x) != null);
					//
				} // if
					//
			} else {
				//
				classInfos = ClassInfoUtil.getClassInfos();
				//
			} // if
				//
			return classInfos;
			//
		}

		private static void add(@Nullable final MutablePropertyValues instance, final String propertyName,
				final Object propertyValue) {
			if (instance != null) {
				instance.add(propertyName, propertyValue);
			}
		}

		private static boolean isProxyClass(@Nullable final Class<?> instance) {
			return instance != null && Proxy.isProxyClass(instance);
		}

		@Nullable
		private static ModuleInfo getModuleInfo(@Nullable final ClassInfo instance) {
			return instance != null ? instance.getModuleInfo() : null;
		}

	}

	public static void main(final String[] args) throws IllegalAccessException {
		//
		try (final ConfigurableApplicationContext beanFactory = new CustomClassPathXmlApplicationContext(
				"applicationContext.xml")) {
			//
			final ConfigurableListableBeanFactory clbf = ConfigurableApplicationContextUtil.getBeanFactory(beanFactory);
			//
			Class<?> clz = getClass(clbf, EnvironmentCapableUtil.getEnvironment(beanFactory),
					"org.springframework.context.support.Main.class");
			//
			if (clz == null) {
				//
				final JList<Object> list = testAndApply(Objects::nonNull,
						getBeanNamesForType(beanFactory, Component.class), JList::new, x -> new JList<>());
				//
				JOptionPane.showMessageDialog(null, list, "Component", JOptionPane.PLAIN_MESSAGE);
				//
				clz = Util.forName(BeanDefinitionUtil
						.getBeanClassName(testAndApply(Objects::nonNull, Util.toString(getSelectedValue(list)),
								x -> ConfigurableListableBeanFactoryUtil.getBeanDefinition(clbf, x), null)));
				//
			} // if
				//
			final PrintStream ps = Util.cast(PrintStream.class,
					FieldUtils.readDeclaredStaticField(System.class, "out"));
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
			pack(Util.cast(Window.class, instance));
			//
			setVisible(Util.cast(Component.class, instance), true);
			//
		} // try
			//
	}

	@Nullable
	private static Class<?> getClass(final ConfigurableListableBeanFactory clbf,
			final PropertyResolver propertyResolver, final String key) {
		//
		final String string = PropertyResolverUtil.getProperty(propertyResolver, key);
		//
		Class<?> clz = Util.forName(string);
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
				if ((ss = StringUtils.split(BeanDefinitionUtil.getBeanClassName(bd), ".")) != null && ss.length > 0
						&& StringsUtil.equals(Strings.CS, string, ss[ss.length - 1])) {
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
				clz = Util
						.forName(
								Util.orElse(
										Util.map(Util.stream(MultimapUtil.values(multimap)),
												BeanDefinitionUtil::getBeanClassName).reduce((first, second) -> first),
										null));
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
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static void pack(@Nullable final Window instance) {
		//
		if (instance != null) {
			//
			try {
				//
				if (Narcissus.getObjectField(instance, Util.getDeclaredField(Component.class, "peer")) == null) {
					//
					final List<Method> ms = Util.toList(Util.filter(
							testAndApply(Objects::nonNull, Narcissus.getDeclaredMethods(Component.class),
									Arrays::stream, null),
							x -> Objects.equals(Util.getName(x), "getComponentFactory")));
					//
					final int size = IterableUtils.size(ms);
					//
					if (size > 1) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					final Method method = getMethod(Util.getClass(
							Narcissus.invokeObjectMethod(instance, size == 1 ? IterableUtils.get(ms, 0) : null)),
							"createWindow", Window.class);
					//
					if (isRaiseThrowableOnly(Util.getDeclaringClass(method), method)) {
						//
						return;
						//
					} // if
						//
				} // if
					//
			} catch (final NoSuchFieldException | NoSuchMethodException e) {
				//
				errorOrPrintStackTrace(LOG, e);
			} // try
				//
			instance.pack();
			//
		} // if
			//
	}

	@Nullable
	private static Method getMethod(@Nullable final Class<?> instance, final String name,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return instance != null ? instance.getMethod(name, parameterTypes) : null;
	}

	private static void errorOrPrintStackTrace(@Nullable final Logger logger, @Nullable final Throwable throwable) {
		//
		if (throwable == null) {
			//
			return;
			//
		} // if
			//
		if (logger != null) {
			//
			logger.error(throwable.getMessage(), throwable);
			//
		} else {
			//
			throwable.printStackTrace();
			//
		} // if
			//
	}

	@SuppressWarnings("java:S1612")
	private static boolean isRaiseThrowableOnly(@Nullable final Class<?> clz, @Nullable final Method method) {
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					method);
			//
			final Instruction[] ins = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			final ConstantPool cp = FieldOrMethodUtil.getConstantPool(m);
			//
			ConstantPoolGen cpg = null;
			//
			final int length = ins != null ? ins.length : 0;
			//
			String className = null;
			//
			for (int i = 0; i < length; i++) {
				//
				if (ins[i] instanceof InvokeInstruction ii) {
					//
					className = InvokeInstructionUtil.getClassName(ii, cpg = ObjectUtils.getIfNull(cpg,
							() -> testAndApply(Objects::nonNull, cp, ConstantPoolGen::new, null)));
					//
				} // if
					//
			} // for
				//
				// The below method
				//
				// void methodA(){throw new RuntimeException();}
				//
				// generates
				//
				// new[187](3) 371
				// dup[89](1)
				// invokespecial[183](3) 373
				// athrow[191](1)
				//
				// instructions
				//
			final Stream<Instruction> stream = testAndApply(Objects::nonNull, ins, Arrays::stream, null);
			//
			if (Objects.equals(Arrays.asList(NEW.class, DUP.class, INVOKESPECIAL.class, ATHROW.class),
					Util.toList(Util.map(stream, x -> Util.getClass(x))))) {
				//
				final Class<?> c = Util.forName(className);
				//
				if (Util.isAssignableFrom(Throwable.class, c)) {
					//
					return true;
					//
				} // if
					//
			} // if
				//
		} catch (final IOException e) {
			//
			errorOrPrintStackTrace(LOG, e);
			//
		} // try
			//
		return false;
		//
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
	private static Object getInstance(final ListableBeanFactory beanFactory, @Nullable final Class<?> clz,
			final Consumer<String> consumer) {
		//
		if (clz == null) {
			//
			Util.accept(consumer, "java.lang.Class is null");
			//
			return null;
			//
		} // if
			//
		final Map<?, ?> beans = ListableBeanFactoryUtil.getBeansOfType(beanFactory, clz);
		//
		if (beans == null) {
			//
			Util.accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s) return null", clz));
			//
			return null;
			//
		} else if (beans.isEmpty()) {
			//
			Util.accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s) return empty", clz));
			//
			return null;
			//
		} else if (beans.size() > 1) {
			//
			Util.accept(consumer, String.format(
					"org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(%1$s).size()>1", clz));
			//
			return null;
			//
		} // if
			//
		return IterableUtils.first(Util.values(beans));
		//
	}

}