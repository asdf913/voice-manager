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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionUtil;
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
import com.fasterxml.jackson.databind.ObjectWriter;
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

	@Note("Copy")
	private AbstractButton btnCopy = null;

	private AbstractButton cbPrettyJson = null;

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
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		final boolean isGui = f == null || Narcissus.getObjectField(this, f) != null;
		//
		final JLabel label = new JLabel("Attribute JSON");
		//
		final Predicate<Component> predicate = Predicates.always(isGui, null);
		//
		testAndAccept(predicate, label, this::add);
		//
		final BiPredicate<Component, Object> biPredicate = Predicates.biAlways(isGui, null);
		//
		testAndAccept(biPredicate, tfAttributeJson = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.MapReportGui.attributeJson")), "growx", this::add);
		//
		final String wrap = "wrap";
		//
		testAndAccept(biPredicate, btnExecute = new JButton("Execute"), wrap, this::add);
		//
		final JScrollPane jsp = new JScrollPane(
				jTable = new JTable(dtm = new DefaultTableModel(new Object[] { "Key", "Old", "New" }, 0)));
		//
		testAndAccept(biPredicate, jsp, String.format("%1$s,span %2$s", wrap, 3), this::add);
		//
		testAndAccept(predicate, new JLabel(), this::add);
		//
		testAndAccept(biPredicate, cbPrettyJson = new JCheckBox("Pretty JSON"), "al right", this::add);
		//
		testAndAccept(PropertyResolverUtil::containsProperty, propertyResolver,
				"org.springframework.context.support.MapReportGui.prettyJson", (a, b) -> {
					//
					final String string = PropertyResolverUtil.getProperty(a, b);
					//
					if (StringUtils.isNotBlank(string)) {
						//
						cbPrettyJson.setSelected(Boolean.valueOf(string));
						//
					} // if
						//
				});
		//
		testAndAccept(biPredicate, btnCopy = new JButton("Copy"), "growx", this::add);
		//
		addActionListener(this, btnExecute, btnCopy);
		//
		final Dimension pd = tfAttributeJson.getPreferredSize();
		//
		if (pd != null) {
			//
			pd.setSize(Util.doubleValue(getPreferredWidth(jsp), 0) - Util.doubleValue(getPreferredWidth(label), 0)
					- Util.doubleValue(getPreferredWidth(btnExecute), 0) - 14, pd.getHeight());
			//
		} // if
			//
		tfAttributeJson.setPreferredSize(pd);
		//
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (Util.test(predicate, value)) {
			Util.accept(consumer, value);
		}
	}

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			@Nullable final BiConsumer<T, U> consumer) {
		if (Util.test(predicate, t, u)) {
			Util.accept(consumer, t, u);
		}
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
			Util.addActionListener(ab, actionListener);
			//
		} // for
			//
	}

	@Nullable
	private static Double getPreferredWidth(@Nullable final Component instance) {
		//
		final Dimension2D pd = Util.getPreferredSize(instance);
		//
		return pd != null ? Double.valueOf(pd.getWidth()) : null;
		//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			actionPerformedForBtnExecute();
			//
		} else if (Objects.equals(source, btnCopy)) {
			//
			actionPerformedForBtnCopy();
			//
		} // if
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws E {
		return Util.test(predicate, value) ? Util.apply(functionTrue, value) : Util.apply(functionFalse, value);
	}

	@Nullable
	private static Vector<Vector> getDataVector(@Nullable final DefaultTableModel instance) {
		return instance != null ? instance.getDataVector() : null;
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
		if (instance != null && Util.forName("org.junit.jupiter.api.Assertions") == null) {
			instance.setContents(contents, owner);
		}
	}

	private void actionPerformedForBtnExecute() {
		//
		Object object = null;
		//
		try {
			//
			if ((object = ObjectMapperUtil.readValue(getObjectMapper(), Util.getText(tfAttributeJson),
					Object.class)) != null && !(object instanceof Map)) {
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
		for (int j = Util.intValue(getRowCount(dtm), 0) - 1; j >= 0; j--) {
			//
			removeRow(dtm, j);
			//
		} // for
			//
		final Multimap<?, ?> mm = createMultimap(
				createMultimap((Collection) getValues(configurableListableBeanFactory, Map.class,
						getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory, Map.class,
								Util.cast(Map.class, object)))),
				(a, b) -> IterableUtils.size(MultimapUtil.get((Multimap) a, b)) > 1);
		//
		final List<String> columns = new ArrayList<>(Collections.singleton("Key"));
		//
		Util.addAll(columns,
				Util.toList(IntStream
						.range(0,
								orElse(max(mapToInt(Util.stream(Util.entrySet(asMap(mm))),
										x -> IterableUtils.size(Util.getValue(x)))), 0))
						.mapToObj(x -> String.format("Value %1$s", x + 1))));
		//
		dtm = new DefaultTableModel(Util.toArray(columns), 0);
		//
		final Set<?> keySet = MultimapUtil.keySet(mm);
		//
		if (keySet != null && Util.iterator(keySet) != null) {
			//
			Object[] os = null;
			//
			Util.clear(jTableRowColumnCount = ObjectUtils.getIfNull(jTableRowColumnCount, IntList::create));
			//
			for (final Object key : keySet) {
				//
				addRow(dtm, os = ArrayUtils.addAll(new Object[] { key },
						Util.toArray(MultimapUtil.get((Multimap) mm, key))));
				//
				IntCollectionUtil.addInt(jTableRowColumnCount, length(os));
				//
			} // for
				//
		} // if
			//
		setModel(jTable, dtm);
		//
	}

	private void actionPerformedForBtnCopy() {
		//
		try {
			//
			final List<List<Object>> lists = Util.toList(Util.map(Util.stream(getDataVector(dtm)),
					x -> testAndApply(Objects::nonNull, x, ArrayList::new, null)));
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
			final ObjectMapper om = ObjectUtils.getIfNull(getObjectMapper(), ObjectMapper::new);
			//
			setContents(!GraphicsEnvironment.isHeadless() ? getSystemClipboard(Toolkit.getDefaultToolkit()) : null,
					new StringSelection(writeValueAsString(
							Util.isSelected(cbPrettyJson) ? writerWithDefaultPrettyPrinter(om) : writer(om), lists)),
					null);
			//
		} catch (final JsonProcessingException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	@Nullable
	private static String writeValueAsString(@Nullable final ObjectWriter instance, @Nullable final Object value)
			throws JsonProcessingException {
		return instance != null ? instance.writeValueAsString(value) : null;
	}

	@Nullable
	private static ObjectWriter writerWithDefaultPrettyPrinter(@Nullable final ObjectMapper instance) {
		return instance != null ? instance.writerWithDefaultPrettyPrinter() : null;
	}

	@Nullable
	private static ObjectWriter writer(@Nullable final ObjectMapper instance) {
		return instance != null ? instance.writer() : null;
	}

	@Nullable
	private static Multimap<?, ?> createMultimap(@Nullable final Multimap<?, ?> mm,
			@Nullable final BiPredicate<Multimap<?, ?>, Object> biPredicate) {
		//
		Multimap<?, ?> mm2 = null;
		//
		final Set<?> keySet = MultimapUtil.keySet(mm);
		//
		if (Util.iterator(keySet) != null) {
			//
			for (final Object key : keySet) {
				//
				if ((biPredicate != null && !Util.test(biPredicate, mm, key))
						|| (mm2 = ObjectUtils.getIfNull(mm2, LinkedHashMultimap::create)) == null) {
					//
					continue;
					//
				} // if
					//
				((Multimap) mm2).putAll(key, MultimapUtil.get((Multimap) mm, key));
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
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	@Nullable
	private static Multimap<?, ?> createMultimap(@Nullable final Iterable<Map<?, ?>> maps) {
		//
		Multimap<?, ?> mm = null;
		//
		if (maps != null && Util.iterator(maps) != null) {
			//
			for (final Map<?, ?> t : maps) {
				//
				if (t == null || Util.iterator(Util.entrySet(t)) == null) {
					//
					continue;
					//
				} // if
					//
				for (final Entry<?, ?> entry : Util.entrySet(t)) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(((Multimap) (mm = ObjectUtils.getIfNull(mm, LinkedHashMultimap::create))),
							Util.getKey(entry), Util.getValue(entry));
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
			if ((v = Util.cast(clz, BeanFactoryUtil.getBean(beanFactory, IterableUtils.get(beanNames, i)))) == null) {
				//
				continue;
				//
			} // if
				//
			Util.add(vs = ObjectUtils.getIfNull(vs, ArrayList::new), v);
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
			if (((Util.isAssignableFrom(FactoryBean.class, clz = Util.forName(BeanDefinitionUtil.getBeanClassName(bd)))
					&& (fb = Util.cast(FactoryBean.class, Narcissus.allocateInstance(clz))) != null
					&& Util.isAssignableFrom(classToBeFound, FactoryBeanUtil.getObjectType(fb)))
					|| Util.isAssignableFrom(classToBeFound, clz)) && isAllAttributesMatched(attributes, bd)) {
				//
				Util.add(multimapBeanDefinitionNames = ObjectUtils.getIfNull(multimapBeanDefinitionNames,
						ArrayList::new), beanDefinitionName);
				//
			} // if
				//
		} // for
			//
		return multimapBeanDefinitionNames;
		//
	}

	private static boolean isAllAttributesMatched(@Nullable final Map<?, ?> attributes,
			@Nullable final AttributeAccessor aa) {
		//
		if (Util.iterator(Util.entrySet(attributes)) != null) {
			//
			for (final Entry<?, ?> entry : Util.entrySet(attributes)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				if (aa != null && (!aa.hasAttribute(Util.toString(Util.getKey(entry)))
						|| !Objects.equals(aa.getAttribute(Util.toString(Util.getKey(entry))), Util.getValue(entry)))) {
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

}