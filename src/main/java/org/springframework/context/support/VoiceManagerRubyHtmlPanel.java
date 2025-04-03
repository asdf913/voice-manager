package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.routines.UrlValidatorUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.PropertyValueUtil;
import org.springframework.beans.PropertyValuesUtil;
import org.springframework.beans.factory.BeanFactoryUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionUtil;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ConfigurableApplicationContextUtil;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.StringTemplateLoaderUtil;
import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.Template;
import freemarker.template.TemplateUtil;
import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerRubyHtmlPanel extends JPanel
		implements Titled, InitializingBean, ApplicationContextAware, ActionListener, ItemListener {

	private static final long serialVersionUID = 8508661990476987623L;

	private static final Logger LOG = LoggerFactory.getLogger(VoiceManagerRubyHtmlPanel.class);

	private static Pattern PATTERN_JAVASSIST_CLASS_NAME = null;

	private transient ApplicationContext applicationContext = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Text")
	private JTextComponent tfText = null;

	private JTextComponent taHtml = null;

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnCopy = null;

	private transient FailableFunction<String, String, IOException> furiganaFailableFunction = null;

	private transient Table<String, String, Object> table = null;

	private JComboBox<Object> jcbImplementation = null;

	@Override
	public String getTitle() {
		return "HTML";
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this,
				ObjectUtils.getIfNull(
						getLayoutManager(ApplicationContextUtil.getAutowireCapableBeanFactory(applicationContext),
								Util.entrySet(
										ListableBeanFactoryUtil.getBeansOfType(applicationContext, Object.class))),
						MigLayout::new));
		//
		JLabel jLabel = new JLabel("Implementation");
		//
		double maxJLabelWidth = getWidth(Util.getPreferredSize(jLabel));
		//
		add(jLabel);
		//
		final MutableComboBoxModel<Object> cbm = new DefaultComboBoxModel<>();
		//
		final ListCellRenderer<?> listCellRenderer = (jcbImplementation = new JComboBox<>(cbm)).getRenderer();
		//
		jcbImplementation.setRenderer((list, value, index, isSelected, cellHasFocus) -> VoiceManagerRubyHtmlPanel
				.getListCellRendererComponent(((ListCellRenderer) listCellRenderer), list,
						TableUtil.get(table, value, "label"), index, isSelected, cellHasFocus));
		//
		jcbImplementation.addItemListener(this);
		//
		add(jcbImplementation, "wrap");
		//
		maxJLabelWidth = Math.max(maxJLabelWidth, getWidth(Util.getPreferredSize(jLabel = new JLabel("Text"))));
		//
		add(jLabel);
		//
		add(tfText = new JTextField(), "growx,wrap");
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), "wrap");
		//
		add(jLabel = new JLabel("Ruby HTML"));
		//
		final JScrollPane jsp = new JScrollPane(taHtml = new JTextArea());
		//
		add(jsp, "growx,wrap");
		//
		add(new JLabel());
		//
		add(btnCopy = new JButton("Copy"));
		//
		addActionListener(this, btnExecute, btnCopy);
		//
		final double width = iif(!GraphicsEnvironment.isHeadless(), 0,
				() -> getWidth(getScreenSize(Toolkit.getDefaultToolkit())), () -> 0)
				- Math.max(maxJLabelWidth, getWidth(Util.getPreferredSize(jLabel)));
		//
		Dimension preferredSize = Util.getPreferredSize(tfText);
		//
		setSize(preferredSize, width, getHeight(preferredSize));
		//
		setPreferredSize(tfText, preferredSize);
		//
		setSize(preferredSize = Util.getPreferredSize(jsp), width, getHeight(preferredSize));
		//
		setPreferredSize(jsp, preferredSize);
		//
		final DefaultListableBeanFactory dlbf = Util.cast(DefaultListableBeanFactory.class,
				ConfigurableApplicationContextUtil
						.getBeanFactory(Util.cast(ConfigurableApplicationContext.class, applicationContext)));
		//
		forEach(testAndApply(Objects::nonNull, ListableBeanFactoryUtil.getBeanDefinitionNames(dlbf), Arrays::stream,
				null), x -> setFailableFunctionFields(applicationContext, dlbf, x, this));
		//
		final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(dlbf);
		//
		String beanDefinitionName, beanClassName = null;
		//
		IValue0<Object> description = null;
		//
		Object object, instance = null;
		//
		Configuration configuration = null;
		//
		StringTemplateLoader stringTemplateLoader = null;
		//
		Template template = null;
		//
		Object[] os = null;
		//
		List<Field> fs = null;
		//
		Field f = null;
		//
		int size;
		//
		Map<String, Object> map = null;
		//
		for (int i = 0; i < length(beanDefinitionNames); i++) {
			//
			if (!Util.isAssignableFrom(FailableFunction.class,
					Util.forName(beanClassName = BeanDefinitionUtil.getBeanClassName(ConfigurableListableBeanFactoryUtil
							.getBeanDefinition(dlbf, beanDefinitionName = ArrayUtils.get(beanDefinitionNames, i)))))
					|| !Objects.equals(
							testAndApply(x -> length(x) > 0, getActualTypeArguments(Util.cast(ParameterizedType.class,
									testAndApply(x -> length(x) > 0, getGenericInterfaces(Util.forName(beanClassName)),
											x -> ArrayUtils.get(x, 0), null))),
									x -> ArrayUtils.get(x, 0), null),
							String.class)) {
				//
				continue;
				//
			} // if
				//
			cbm.addElement(beanDefinitionName);
			//
			if ((description = getDescription(beanClassName)) == null) {
				//
				description = Unit.with(beanClassName);
				//
			} // if
				//
			instance = BeanFactoryUtil.getBean(dlbf, beanDefinitionName);
			//
			if (configuration == null) {
				//
				(configuration = new Configuration(Configuration.getVersion()))
						.setTemplateLoader(stringTemplateLoader = new StringTemplateLoader());
				//
			} // if
				//
			StringTemplateLoaderUtil.putTemplate(stringTemplateLoader, beanDefinitionName,
					Util.toString(IValue0Util.getValue0(description)));
			//
			try (final Writer writer = new StringWriter()) {
				//
				if ((os = Util.cast(Object[].class,
						FieldUtils.readField(FieldUtils.readDeclaredField(
								template = ConfigurationUtil.getTemplate(configuration, beanDefinitionName),
								"rootElement", true), "childBuffer", true))) != null) {
					//
					for (int j = 0; j < length(os); j++) {
						//
						if ((size = IterableUtils
								.size(fs = Util
										.toList(Util.filter(
												testAndApply(Objects::nonNull,
														Util.getDeclaredFields(
																Util.getClass(object = ArrayUtils.get(os, j))),
														Arrays::stream, null),
												x -> Objects.equals(Util.getName(x), "expression"))))) == 1
								&& (f = IterableUtils.get(fs,
										0)) != null
								&& (size = IterableUtils
										.size(fs = Util
												.toList(Util.filter(
														testAndApply(Objects::nonNull,
																Util.getDeclaredFields(Util.getClass(
																		object = Narcissus.getField(object, f))),
																Arrays::stream, null),
														x -> Objects.equals(Util.getName(x), "lho"))))) == 1
								&& Objects.equals("freemarker.core.Identifier", Util.getName(Util
										.getClass(object = Narcissus.getField(object, IterableUtils.get(fs, 0)))))) {
							//
							Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Util.toString(object), null);
							//
						} else if (size > 1) {
							//
							throw new IllegalStateException();
							//
						} // if
							//
					} // for
						//
				} // if
					//
				setFieldValues(instance, map);
				//
				TemplateUtil.process(template, map, writer);
				//
				TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create), beanDefinitionName, "label",
						Util.toString(writer));
				//
			} // try
				//
			TableUtil.put(table, beanDefinitionName, "instance", instance);
			//
		} // for
			//
	}

	private static <K, V> void setFieldValues(final Object instance, @Nullable final Map<K, V> map) {
		//
		final Iterable<Entry<K, V>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			int size;
			//
			Iterable<Field> fs = null;
			//
			Field f = null;
			//
			Method setValue = null;
			//
			List<Method> ms = null;
			//
			for (final Entry<K, V> entry : entrySet) {
				//
				if ((size = IterableUtils
						.size(fs = Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(instance)),
										Arrays::stream, null),
								x -> Objects.equals(Util.getName(x), Util.getKey(entry)))))) == 1
						&& (f = IterableUtils.get(fs, 0)) != null) {
					//
					if (setValue == null) {
						//
						if ((size = IterableUtils.size(ms = Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.getDeclaredMethods(Entry.class), Arrays::stream,
										null),
								x -> Objects.equals(Util.getName(x), "setValue")
										&& Arrays.equals(x != null ? x.getParameterTypes() : null,
												new Class<?>[] { Object.class }))))) == 1) {
							//
							setValue = IterableUtils.get(ms, 0);
							//
						} else if (size > 1) {
							//
							throw new IllegalStateException();
							//
						} // if
							//
					} // if
						//
					if (entry != null && setValue != null) {
						//
						if (Util.isStatic(f)) {
							//
							Narcissus.invokeMethod(entry, setValue, Narcissus.getStaticField(f));
							//
						} else if (entry != null) {
							//
							Narcissus.invokeMethod(entry, setValue, Narcissus.getField(instance, f));
							//
						} // if
							//
					} // if
						//
				} else if (size > 1) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
		} // if
			//

	}

	@Nullable
	private static IValue0<Object> getDescription(final String beanClassName) {
		//
		final Annotation[] as = Util.getAnnotations(Util.forName(beanClassName));
		//
		IValue0<Object> description = null, temp = null;
		//
		for (int i = 0; i < length(as); i++) {
			//
			if ((temp = getDescription(ArrayUtils.get(as, i))) != null) {
				//
				if (description == null) {
					//
					description = temp;
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return description;
		//
	}

	@Nullable
	private static IValue0<Object> getDescription(final Annotation annotation) {
		//
		if (Boolean.logicalAnd(
				Objects.equals(Util.annotationType(annotation),
						Util.forName("org.springframework.context.annotation.Description")),
				and(Util.getClass(annotation), Objects::nonNull, Proxy::isProxyClass))) {
			//
			final Object ih = Proxy.getInvocationHandler(annotation);
			//
			final Field[] fs = Util.getDeclaredFields(Util.getClass(ih));
			//
			Map<?, ?> map = null;
			//
			for (int j = 0; j < length(fs); j++) {
				//
				if (and(map = Util.cast(Map.class,
						testAndApply((a, b) -> !Util.isStatic(b), ih, ArrayUtils.get(fs, j), Narcissus::getObjectField,
								null)),
						Objects::nonNull,
						x -> CollectionUtils.isEqualCollection(Util.keySet(x), Collections.singleton("value")))) {
					//
					return Unit.with(Util.get(map, "value"));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return null;
		//
	}

	private static <T> boolean and(final T value, final Predicate<T> a, final Predicate<T> b) {
		return Util.test(a, value) && Util.test(b, value);
	}

	@Nullable
	private static <E> Component getListCellRendererComponent(@Nullable final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... bs) {
		//
		AbstractButton b = null;
		//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if ((b = bs[i]) == null) {
				//
				continue;
				//
			} // skip null
				//
			b.addActionListener(actionListener);
			//
		} // for
			//
	}

	private static <T> void forEach(@Nullable final Stream<T> instance, @Nullable final Consumer<? super T> action) {
		if (instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || action != null)) {
			instance.forEach(action);
		}
	}

	@Nullable
	private static Dimension getScreenSize(@Nullable final Toolkit instance) {
		return instance != null && Boolean.logicalOr(!GraphicsEnvironment.isHeadless(), isJavassistProxy(instance))
				? instance.getScreenSize()
				: null;
	}

	private static boolean isJavassistProxy(final Object instance) {
		//
		return Util.matches(Util.matcher(
				PATTERN_JAVASSIST_CLASS_NAME = ObjectUtils.getIfNull(PATTERN_JAVASSIST_CLASS_NAME,
						() -> Pattern.compile("^javassist.util.proxy[.\\w]+\\$\\$[.\\w]+$")),
				Util.getName(Util.getClass(instance))));
		//
	}

	private static double iif(final boolean condition, final double defaultValue, final DoubleSupplier supplierTrue,
			final DoubleSupplier supplierFalse) {
		return condition ? getAsDouble(supplierTrue, defaultValue) : getAsDouble(supplierFalse, defaultValue);
	}

	private static double getAsDouble(@Nullable final DoubleSupplier instance, final double defaultValue) {
		return instance != null ? instance.getAsDouble() : defaultValue;
	}

	private static void setPreferredSize(@Nullable final Component instance, @Nullable final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
		}
	}

	private static void setSize(@Nullable final Dimension2D instance, final double width, final double height) {
		if (instance != null) {
			instance.setSize(width, height);
		}
	}

	private static double getHeight(@Nullable final Dimension2D instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	private static double getWidth(@Nullable final Dimension2D instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static void setFailableFunctionFields(final ApplicationContext applicationContext,
			final DefaultListableBeanFactory dlbf, final String beanDefinitionName, final Object instance) {
		//
		final BeanDefinition bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(dlbf, beanDefinitionName);
		//
		final Class<?> clz = Util.forName(BeanDefinitionUtil.getBeanClassName(bd));
		//
		final java.lang.reflect.Type[] genericInterfaces = getGenericInterfaces(clz);
		//
		if (!Boolean.logicalAnd(Util.isAssignableFrom(FailableFunction.class, clz), genericInterfaces != null)
				|| (PropertyValueUtil
						.getValue(testAndApply(PropertyValuesUtil::contains, BeanDefinitionUtil.getPropertyValues(bd),
								"url", PropertyValuesUtil::getPropertyValue, null)) instanceof TypedStringValue tsv
						&& UrlValidatorUtil.isValid(UrlValidator.getInstance(), tsv.getValue()))) {
			//
			return;
			//
		} // if
			//
		Field[] fs = null;
		//
		Field f = null;
		//
		ParameterizedType pt2 = null;
		//
		for (final java.lang.reflect.Type genericInterface : genericInterfaces) {
			//
			if (fs == null) {
				//
				fs = Util.getDeclaredFields(Util.getClass(instance));
				//
			} // if
				//
			for (int j = 0; j < length(fs) && genericInterface instanceof ParameterizedType pt1; j++) {
				//
				if (Boolean
						.logicalOr(
								!Objects.equals(getRawType(pt1),
										getRawType((pt2 = Util.cast(ParameterizedType.class,
												getGenericType(f = ArrayUtils.get(fs, j)))))),
								!Arrays.equals(getActualTypeArguments(pt1), getActualTypeArguments(pt2)))) {
					//
					continue;
					//
				} // if
					//
				Narcissus.setField(instance, f, BeanFactoryUtil.getBean(applicationContext, beanDefinitionName));
				//
			} // for
				//
		} // for
			//
	}

	@Nullable
	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return test(predicate, t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	@Nullable
	private static <T, U, R> R apply(@Nullable final BiFunction<T, U, R> instance, final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static Type[] getActualTypeArguments(@Nullable final ParameterizedType instance) {
		return instance != null ? instance.getActualTypeArguments() : null;
	}

	@Nullable
	private static Type getGenericType(@Nullable final Field instance) {
		return instance != null ? instance.getGenericType() : null;
	}

	@Nullable
	private static Type getRawType(@Nullable final ParameterizedType instance) {
		return instance != null ? instance.getRawType() : null;
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static Type[] getGenericInterfaces(@Nullable final Class<?> instance) {
		return instance != null ? instance.getGenericInterfaces() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			try {
				//
				Util.setText(taHtml, FailableFunctionUtil.apply(furiganaFailableFunction, Util.getText(tfText)));
				//
			} catch (final IOException e) {
				//
				LoggerUtil.error(LOG, e.getMessage(), e);
				//
			} // try
				//
		} else if (Objects.equals(source, btnCopy)) {
			//
			setContents(Boolean.logicalAnd(!isTestMode(), !GraphicsEnvironment.isHeadless())
					? getSystemClipboard(Toolkit.getDefaultToolkit())
					: null, new StringSelection(Util.getText(taHtml)), null);
			//
		} // if
			//
	}

	@Override
	public void itemStateChanged(@Nullable final ItemEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), jcbImplementation) && evt != null
				&& evt.getStateChange() == ItemEvent.SELECTED) {
			//
			final Object instance = TableUtil.get(table, getSelectedItem(jcbImplementation), "instance");
			//
			final Field[] fs = Util.getDeclaredFields(getClass());
			//
			Field f = null;
			//
			for (int i = 0; i < length(fs); i++) {
				//
				if ((f = ArrayUtils.get(fs, i)) == null
						|| !Util.isAssignableFrom(Util.getType(f), Util.getClass(instance))) {
					//
					continue;
					//
				} // if
					//
				Narcissus.setField(this, f, instance);
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static Object getSelectedItem(@Nullable final JComboBox<?> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Class<?> clz = Util.getClass(instance);
		//
		final Stream<Field> stream = testAndApply(Objects::nonNull, Util.getDeclaredFields(clz), Arrays::stream, null);
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1,
				Util.toList(Util.filter(stream, x -> Objects.equals(Util.getName(x), "dataModel"))),
				x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getSelectedItem();
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			@Nullable final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null && Boolean.logicalOr(!GraphicsEnvironment.isHeadless(), isJavassistProxy(instance))
				? instance.getSystemClipboard()
				: null;
	}

	private static void setLayout(@Nullable final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
	}

	@Nullable
	private static LayoutManager getLayoutManager(final Object acbf, final Iterable<Entry<String, Object>> entrySet)
			throws Exception {
		//
		if (Util.iterator(entrySet) == null) {
			//
			return null;
			//
		} // if
			//
		IValue0<LayoutManager> iValue0 = null;
		//
		List<Field> fs = null;
		//
		for (final Entry<String, Object> entry : entrySet) {
			//
			if (!(Util.getValue(entry) instanceof LayoutManager)) {
				//
				continue;
				//
			} // if
				//
			fs = Util.toList(Util.filter(
					Util.stream(
							testAndApply(Objects::nonNull, Util.getClass(acbf), FieldUtils::getAllFieldsList, null)),
					x -> Objects.equals(Util.getName(x), "singletonObjects")));
			//
			for (int i = 0; i < IterableUtils.size(fs); i++) {
				//
				if (FactoryBeanUtil
						.getObject(
								Util.cast(
										FactoryBean.class, MapUtils
												.getObject(
														Util.cast(Map.class,
																Narcissus.getObjectField(acbf,
																		IterableUtils.get(fs, i))),
														Util.getKey(entry)))) instanceof LayoutManager lm) {
					//
					if (iValue0 == null) {
						//
						iValue0 = Unit.with(lm);
						//
					} else {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return IValue0Util.getValue0(iValue0);
		//
	}

}