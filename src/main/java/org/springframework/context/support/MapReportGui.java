package org.springframework.context.support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.config.Title;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

@Title("Map Report")
public class MapReportGui extends JFrame
		implements EnvironmentAware, InitializingBean, ActionListener, BeanFactoryPostProcessor {

	private static final long serialVersionUID = 467011648930037863L;

	private transient PropertyResolver propertyResolver = null;

	private transient ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	private JTextComponent tfAttributeJson = null;

	private AbstractButton btnExecute = null;

	private DefaultTableModel dtm = null;

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
		final List<Field> fs = FieldUtils.getAllFieldsList(getClass(this)).stream()
				.filter(f -> f != null && Objects.equals(f.getName(), "component")).toList();
		//
		final Field f = IterableUtils.size(fs) == 1 ? IterableUtils.get(fs, 0) : null;
		//
		if (f != null && Narcissus.getObjectField(this, f) == null) {
			//
			return;
			//
		} // if
			//
		final String wrap = "wrap";
		//
		add(new JLabel("Attribute JSON"));
		//
		add(tfAttributeJson = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.MapReportGui.attributeJson")),
				String.format("%1$s,wmin %2$s", wrap, 100));
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		add(new JScrollPane(new JTable(dtm = new DefaultTableModel(new Object[] { "Key", "Old", "New" }, 0))),
				String.format("span %1$s", 2));
		//
		btnExecute.addActionListener(this);
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(getSource(evt), btnExecute)) {
			//
			Object object = null;
			//
			try {
				//
				if ((object = ObjectMapperUtil.readValue(new ObjectMapper(), getText(tfAttributeJson),
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
			for (int j = intValue(getRowCount(dtm), 0) - 1; j >= 0; j--) {
				//
				dtm.removeRow(j);
				//
			} // for
				//
			final List<String> beanNames = getBeanDefinitionNamesByClassAndAttributes(configurableListableBeanFactory,
					Map.class, cast(Map.class, object));
			//
			Map<Object, Object> map = null;
			//
			Map<?, ?> m = null;
			//
			Object key, valueOld, valueNew = null;
			//
			for (int i = 0; i < IterableUtils.size(beanNames); i++) {
				//
				if ((m = cast(Map.class,
						configurableListableBeanFactory.getBean(IterableUtils.get(beanNames, i)))) == null) {
					//
					continue;
					//
				} // if
					//
				for (final Entry<?, ?> entry : m.entrySet()) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					if (!containsKey(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), key = entry.getKey())) {
						//
						put(map, entry.getKey(), entry.getValue());
						//
					} else if (!Objects.equals(valueOld = MapUtils.getObject(map, key), valueNew = entry.getValue())) {
						//
						dtm.addRow(new Object[] { key, valueOld, valueNew });
						//
					} // if
						//
				} // for
					//
			} // for
				//
		} // if
			//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static Integer getRowCount(final TableModel instance) {
		return instance != null ? Integer.valueOf(instance.getRowCount()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
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
			final Map<?, ?> attributes) {
		//
		List<String> multimapBeanDefinitionNames = null;
		//
		final String[] beanDefinitionNames = instnace != null ? instnace.getBeanDefinitionNames() : null;
		//
		BeanDefinition bd = null;
		//
		Class<?> clz = null;
		//
		FactoryBean<?> fb = null;
		//
		String beanDefinitionName = null;
		//
		for (int i = 0; beanDefinitionNames != null && i < beanDefinitionNames.length; i++) {
			//
			if ((bd = instnace.getBeanDefinition(beanDefinitionName = beanDefinitionNames[i])) == null) {
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
		if (attributes != null && iterator(attributes.entrySet()) != null) {
			//
			for (final Entry<?, ?> entry : attributes.entrySet()) {
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