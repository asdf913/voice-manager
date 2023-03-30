package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.util.IntList;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactoryUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

@Title("Map Report")
public class MapReportGui extends JFrame
		implements EnvironmentAware, InitializingBean, ActionListener, BeanFactoryPostProcessor {

	private static final long serialVersionUID = 467011648930037863L;

	private transient PropertyResolver propertyResolver = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private ObjectMapper objectMapper = null;

	private JTextComponent tfAttributeJson = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnCopy = null;

	private JTable jTable = null;

	private DefaultTableModel dtm = null;

	private transient IntList jTableRowColumnCount = null;

	private MapReportGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
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
		final List<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(getClass(this))),
				f -> f != null && Objects.equals(f.getName(), "component")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null && Narcissus.getObjectField(this, f) == null) {
			//
			return;
			//
		} // if
			//
		final JLabel label = new JLabel("Attribute JSON");
		//
		add(label);
		//
		add(tfAttributeJson = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.MapReportGui.attributeJson")), "growx");
		//
		final String wrap = "wrap";
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		final JScrollPane jsp = new JScrollPane(
				jTable = new JTable(dtm = new DefaultTableModel(new Object[] { "Key", "Old", "New" }, 0)));
		//
		add(jsp, String.format("%1$s,span %2$s", wrap, 3));
		//
		add(new JLabel(), String.format("span %1$s", 2));
		//
		add(btnCopy = new JButton("Copy"), "growx");
		//
		addActionListener(this, btnExecute, btnCopy);
		//
		final Dimension pd = tfAttributeJson.getPreferredSize();
		//
		if (pd != null) {
			//
			pd.setSize(doubleValue(getPreferredWidth(jsp), 0) - doubleValue(getPreferredWidth(label), 0)
					- doubleValue(getPreferredWidth(btnExecute), 0) - 14, pd.getHeight());
			//
		} // if
			//
		tfAttributeJson.setPreferredSize(pd);
		//
	}

	private static void addActionListener(final ActionListener actionListener, @Nullable final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < length(abs); i++) {
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

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || predicate != null)
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static Double getPreferredWidth(@Nullable final Component instance) {
		//
		final Dimension2D pd = instance != null ? instance.getPreferredSize() : null;
		//
		return pd != null ? Double.valueOf(pd.getWidth()) : null;
		//
	}

	private static double doubleValue(@Nullable final Number instance, final double defaultValue) {
		return instance != null ? instance.doubleValue() : defaultValue;
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedForBtnExecute();
			//
		} else if (Objects.equals(source, btnCopy)) {
			//
			try {
				//
				final List<List<Object>> lists = toList(
						map(stream(getDataVector(dtm)), x -> testAndApply(Objects::nonNull, x, ArrayList::new, null)));
				//
				List<?> list = null;
				//
				for (int i = 0; i < IterableUtils.size(lists) && jTableRowColumnCount != null; i++) {
					//
					if ((list = IterableUtils.get(lists, i)) == null || i >= jTableRowColumnCount.size()) {
						//
						continue;
						//
					} // if
						//
					for (int j = IterableUtils.size(list) - 1; j >= jTableRowColumnCount.get(i); j--) {
						//
						list.remove(j);
						//
					} // for
						//
				} // for
					//
				setContents(!GraphicsEnvironment.isHeadless() ? getSystemClipboard(Toolkit.getDefaultToolkit()) : null,
						new StringSelection(ObjectMapperUtil.writeValueAsString(getObjectMapper(), lists)), null);
				//
			} catch (final JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // if
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(@Nullable final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	@Nullable
	private static Vector<Vector> getDataVector(@Nullable final DefaultTableModel instance) {
		return instance != null ? instance.getDataVector() : null;
	}

	@Nullable
	private static <T, R> Stream<R> map(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			@Nullable final ClipboardOwner owner) {
		if (instance != null && forName("org.junit.jupiter.api.Assertions") == null) {
			instance.setContents(contents, owner);
		}
	}

	private void actionPerformedForBtnExecute() {
		//
		Object object = null;
		//
		try {
			//
			if ((object = ObjectMapperUtil.readValue(getObjectMapper(), getText(tfAttributeJson), Object.class)) != null
					&& !(object instanceof Map)) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} catch (final JsonProcessingException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
			// Remove all row(s)
			//
		for (int j = intValue(getRowCount(dtm), 0) - 1; j >= 0; j--) {
			//
			removeRow(dtm, j);
			//
		} // for
			//
		final Multimap<?, ?> mm2 = createMultimapWithMultipleValues(
				createMultimap((Collection) getValues(configurableListableBeanFactory, Map.class,
						getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Map.class,
								cast(Map.class, object)))));
		//
		final List<String> columns = new ArrayList<>(Collections.singleton("Key"));
		//
		columns.addAll(toList(IntStream
				.range(0, orElse(max(mapToInt(stream(entrySet(asMap(mm2))), x -> IterableUtils.size(getValue(x)))), 0))
				.mapToObj(x -> String.format("Value %1$s", x + 1))));
		//
		dtm = new DefaultTableModel(columns.toArray(), 0);
		//
		final Set<?> keySet = MultimapUtil.keySet(mm2);
		//
		if (keySet != null && iterator(keySet) != null) {
			//
			Object[] os = null;
			//
			if (jTableRowColumnCount == null) {
				//
				jTableRowColumnCount = new IntList();
				//
			} // if
				//
			jTableRowColumnCount.clear();
			//
			for (final Object key : keySet) {
				//
				addRow(dtm,
						os = ArrayUtils.addAll(new Object[] { key }, MultimapUtil.get((Multimap) mm2, key).toArray()));
				//
				jTableRowColumnCount.add(length(os));
				//
			} // for
				//
		} // if
			//
		setModel(jTable, dtm);
		//
	}

	private static Multimap<?, ?> createMultimapWithMultipleValues(final Multimap<?, ?> mm) {
		//
		Multimap<?, ?> mm2 = null;
		//
		final Set<?> keySet = MultimapUtil.keySet(mm);
		//
		if (iterator(keySet) != null) {
			//
			Collection<?> values = null;
			//
			for (final Object key : keySet) {
				//
				if (IterableUtils.size(values = MultimapUtil.get((Multimap) mm, key)) <= 1
						|| (mm2 = ObjectUtils.getIfNull(mm2, LinkedHashMultimap::create)) == null) {
					//
					continue;
					//
				} // if
					//
				((Multimap) mm2).putAll(key, values);
				//
			} // for
				//
		} // if
			//
		return mm2;
		//
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static void setModel(@Nullable final JTable instnace, final TableModel dataModel) {
		if (instnace != null) {
			instnace.setModel(dataModel);
		}
	}

	private static int orElse(@Nullable final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	@Nullable
	private static OptionalInt max(@Nullable final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	@Nullable
	private static <T> IntStream mapToInt(@Nullable final Stream<T> instance,
			@Nullable final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	@Nullable
	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	@Nullable
	private static <K, V> Set<Map.Entry<K, V>> entrySet(@Nullable final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	@Nullable
	private static Multimap<?, ?> createMultimap(@Nullable final Iterable<Map<?, ?>> maps) {
		//
		Multimap<?, ?> mm = null;
		//
		if (maps != null && iterator(maps) != null) {
			//
			for (final Map<?, ?> t : maps) {
				//
				if (t == null || iterator(entrySet(t)) == null) {
					//
					continue;
					//
				} // if
					//
				for (final Entry<?, ?> entry : entrySet(t)) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(((Multimap) (mm = ObjectUtils.getIfNull(mm, LinkedHashMultimap::create))),
							getKey(entry), getValue(entry));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return mm;
		//
	}

	@Nullable
	private static <V> Collection<V> getValues(final BeanFactory beanFactory, final Class<V> clz,
			final Iterable<String> beanNames) {
		//
		Collection<V> vs = null;
		//
		V v = null;
		//
		for (int i = 0; i < IterableUtils.size(beanNames); i++) {
			//
			if ((v = cast(clz, BeanFactoryUtil.getBean(beanFactory, IterableUtils.get(beanNames, i)))) == null) {
				//
				continue;
				//
			} // if
				//
			add(vs = ObjectUtils.getIfNull(vs, ArrayList::new), v);
			//
		} // for
			//
		return vs;
		//
	}

	@Nullable
	private static <K, V> Map<K, Collection<V>> asMap(@Nullable final Multimap<K, V> instance) {
		return instance != null ? instance.asMap() : null;
	}

	private static void removeRow(@Nullable final DefaultTableModel instance, final int row) {
		if (instance != null) {
			instance.removeRow(row);
		}
	}

	private static void addRow(@Nullable final DefaultTableModel instance, final Object[] rowData) {
		if (instance != null) {
			instance.addRow(rowData);
		}
	}

	@Nullable
	private static Integer getRowCount(@Nullable final TableModel instance) {
		return instance != null ? Integer.valueOf(instance.getRowCount()) : null;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Nullable
	private static Object getSource(@Nullable final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	@Nullable
	private static String getText(@Nullable final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	@Nullable
	private static List<String> getBeanDefinitionNamesByClassAndAttributes(
			@Nullable final ConfigurableListableBeanFactory instnace, final Class<?> classToBeFound,
			@Nullable final Map<?, ?> attributes) {
		//
		List<String> multimapBeanDefinitionNames = null;
		//
		final String[] beanDefinitionNames = ListableBeanFactoryUtil.getBeanDefinitionNames(instnace);
		//
		BeanDefinition bd = null;
		//
		Class<?> clz = null;
		//
		FactoryBean<?> fb = null;
		//
		String beanDefinitionName = null;
		//
		for (int i = 0; beanDefinitionNames != null && i < length(beanDefinitionNames); i++) {
			//
			if ((bd = ConfigurableListableBeanFactoryUtil.getBeanDefinition(instnace,
					beanDefinitionName = beanDefinitionNames[i])) == null) {
				//
				continue;
				//
			} // if
				//
			if (((isAssignableFrom(FactoryBean.class, clz = forName(bd.getBeanClassName()))
					&& (fb = cast(FactoryBean.class, Narcissus.allocateInstance(clz))) != null
					&& isAssignableFrom(classToBeFound, fb.getObjectType())) || isAssignableFrom(classToBeFound, clz))
					&& isAllAttributesMatched(attributes, bd)) {
				//
				add(multimapBeanDefinitionNames = ObjectUtils.getIfNull(multimapBeanDefinitionNames, ArrayList::new),
						beanDefinitionName);
				//
			} // if
				//
		} // for
			//
		return multimapBeanDefinitionNames;
		//
	}

	private static <E> void add(@Nullable final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
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
	private static <T> T cast(@Nullable final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static boolean isAllAttributesMatched(@Nullable final Map<?, ?> attributes,
			@Nullable final AttributeAccessor aa) {
		//
		if (iterator(entrySet(attributes)) != null) {
			//
			for (final Entry<?, ?> entry : entrySet(attributes)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				if (aa != null && (!aa.hasAttribute(toString(getKey(entry)))
						|| !Objects.equals(aa.getAttribute(toString(getKey(entry))), getValue(entry)))) {
					//
					return false;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return true;
		//
	}

	@Nullable
	private static <K> K getKey(@Nullable final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	@Nullable
	private static <V> V getValue(@Nullable final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	private static <T> Iterator<T> iterator(@Nullable final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

}