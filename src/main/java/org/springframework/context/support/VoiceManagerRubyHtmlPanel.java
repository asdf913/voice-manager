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
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
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
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
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
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ExpressionParserUtil;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.ast.MethodReference;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;

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

	private JTextArea taHtml = null;

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
		final ListCellRenderer<?> listCellRenderer = Util.getRenderer(jcbImplementation = new JComboBox<>(cbm));
		//
		jcbImplementation.setRenderer((list, value, index, isSelected, cellHasFocus) -> Util
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
		taHtml.setRows(2);
		//
		add(jsp, "growx,wrap");
		//
		add(new JLabel());
		//
		add(btnCopy = new JButton("Copy"));
		//
		Util.forEach(Stream.of(btnExecute, btnCopy), x -> Util.addActionListener(x, this));
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
		final Stream<String> stream = testAndApply(Objects::nonNull,
				ListableBeanFactoryUtil.getBeanDefinitionNames(dlbf), Arrays::stream, null);
		//
		Util.forEach(stream, x -> setFailableFunctionFields(applicationContext, dlbf, x, this));
		//
		final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(dlbf);
		//
		String beanDefinitionName, beanClassName = null;
		//
		Object instance, ast = null;
		//
		Map<String, Object> map = null;
		//
		ExpressionParser ep = null;
		//
		StandardEvaluationContext sec = null;
		//
		Expression expression = null;
		//
		for (int i = 0; i < length(beanDefinitionNames); i++) {
			//
			if (!Util.isAssignableFrom(FailableFunction.class,
					Util.forName(beanClassName = BeanDefinitionUtil.getBeanClassName(ConfigurableListableBeanFactoryUtil
							.getBeanDefinition(dlbf, beanDefinitionName = ArrayUtils.get(beanDefinitionNames, i)))))
					|| !Objects.equals(
							testAndApply(x -> length(x) > 0, Util.getActualTypeArguments(Util.cast(
									ParameterizedType.class,
									testAndApply(x -> length(x) > 0, getGenericInterfaces(Util.forName(beanClassName)),
											x -> ArrayUtils.get(x, 0), null))),
									x -> ArrayUtils.get(x, 0), null),
							String.class)) {
				//
				continue;
				//
			} // if
				//
			Util.addElement(cbm, beanDefinitionName);
			//
			TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create), beanDefinitionName, "instance",
					instance = BeanFactoryUtil.getBean(dlbf, beanDefinitionName));
			//
			clear(map = ObjectUtils.getIfNull(map, LinkedHashMap::new));
			//
			if ((ast = getAST(expression = testAndApply((a, b) -> b != null,
					ep = ObjectUtils.getIfNull(ep, SpelExpressionParser::new),
					Util.toString(IValue0Util.getValue0(getDescription(beanClassName))),
					ExpressionParserUtil::parseExpression, null))) instanceof PropertyOrFieldReference pofr
					&& getChildCount(pofr) == 0) {
				//
				Util.putAll(map, createMap(instance, pofr));
				//
			} else if (ast instanceof CompoundExpression ce && getChildCount(ce) == 2
					&& getChild(ce, 1) instanceof MethodReference mr && getChildCount(mr) > 1
					&& getChild(mr, 1) instanceof PropertyOrFieldReference pofr) {
				//
				Util.putAll(map, createMap(instance, pofr));
				//
			} // if
				//
			TableUtil.put(table, beanDefinitionName, "label",
					expression != null ? getValue(expression, sec = ObjectUtils.getIfNull(sec, () -> {
						//
						final StandardEvaluationContext x = new StandardEvaluationContext();
						//
						x.addPropertyAccessor(new MapAccessor());
						//
						return x;
						//
					}), map) : beanClassName);
			//
		} // for
			//
	}

	@Nullable
	private static SpelNode getChild(@Nullable final SpelNode instance, final int index) {
		return instance != null ? instance.getChild(index) : null;
	}

	private static int getChildCount(@Nullable final SpelNode instance) {
		return instance != null ? instance.getChildCount() : 0;
	}

	private static void clear(@Nullable final Map<?, ?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	@Nullable
	private static Object getValue(@Nullable final Expression instance, final EvaluationContext context,
			final Object rootObject) throws EvaluationException {
		return instance != null ? instance.getValue(context, rootObject) : null;
	}

	private static Map<String, Object> createMap(final Object instance, @Nullable final PropertyOrFieldReference pofr) {
		//
		final String name = pofr != null ? pofr.getName() : null;
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Objects.equals(Util.getName(x), name)));
		//
		Field f = null;
		//
		if (IterableUtils.size(fs) == 1 && (f = IterableUtils.get(fs, 0)) != null) {
			//
			if (Util.isStatic(f)) {
				//
				return Collections.singletonMap(name, Narcissus.getStaticField(f));
				//
			} else {
				//
				return Collections.singletonMap(name, Narcissus.getField(instance, f));
				//
			} // if
				//
		} // if
			//
		return Collections.singletonMap(name, name);
		//
	}

	private static Object getAST(@Nullable final Object instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(instance)), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), "ast")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			throw new IllegalStateException();
		});
		//
		return testAndApply(Objects::nonNull,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
				x -> Narcissus.getField(instance, x), null);
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			@Nullable final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
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
				if (Boolean.logicalOr(
						!Objects.equals(Util.getRawType(pt1),
								Util.getRawType((pt2 = Util.cast(ParameterizedType.class,
										Util.getGenericType(f = ArrayUtils.get(fs, j)))))),
						!Arrays.equals(Util.getActualTypeArguments(pt1), Util.getActualTypeArguments(pt2)))) {
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
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
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
			final Object instance = TableUtil.get(table, Util.getSelectedItem(jcbImplementation), "instance");
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