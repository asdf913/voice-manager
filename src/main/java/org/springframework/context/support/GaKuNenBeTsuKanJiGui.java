package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.Multimap;

import net.miginfocom.swing.MigLayout;

public class GaKuNenBeTsuKanJiGui extends JFrame implements EnvironmentAware, InitializingBean, KeyListener {

	private static final long serialVersionUID = 467011648930037863L;

	private transient PropertyResolver propertyResolver = null;

	private JTextComponent tfText = null;

	private ComboBoxModel<String> cbmGaKuNenBeTsuKanJi = null;

	private Unit<Multimap<String, String>> gaKuNenBeTsuKanJiMultimap = null;

	private GaKuNenBeTsuKanJiGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setGaKuNenBeTsuKanJiMultimap(final Multimap<String, String> gaKuNenBeTsuKanJiMultimap) {
		this.gaKuNenBeTsuKanJiMultimap = Unit.with(gaKuNenBeTsuKanJiMultimap);
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
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.GaKuNenBeTsuKanJiGui.text")));
		//
		tfText.addKeyListener(this);
		//
		final JComboBox<String> jcbGaKuNenBeTsuKanJi = new JComboBox<>(
				cbmGaKuNenBeTsuKanJi = testAndApply(Objects::nonNull,
						ArrayUtils.insert(0,
								toArray(keySet(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap)), new String[] {}),
								(String) null),
						DefaultComboBoxModel::new, x -> new DefaultComboBoxModel<>()));
		//
		add(jcbGaKuNenBeTsuKanJi);
		//
		final Dimension preferredSize = Collections
				.max(Arrays.asList(tfText.getPreferredSize(), jcbGaKuNenBeTsuKanJi.getPreferredSize()), (a, b) -> {
					return a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
				});
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), tfText, jcbGaKuNenBeTsuKanJi);
			//
		} // if
			//
	}

	private static <K> Set<K> keySet(final Multimap<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(getClass(instance))) ? instance.toArray(array)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Override
	public void keyTyped(final KeyEvent evt) {
		//
	}

	@Override
	public void keyPressed(final KeyEvent evt) {
		//
	}

	@Override
	public void keyReleased(final KeyEvent evt) {
		//
		final Object source = getSource(evt);
		//
		final JTextComponent jtf = cast(JTextComponent.class, source);
		//
		if (Objects.equals(source, tfText)) {
			//
			final Collection<Entry<String, String>> entries = entries(IValue0Util.getValue0(gaKuNenBeTsuKanJiMultimap));
			//
			if (iterator(entries) == null) {
				//
				return;
				//
			} // if
				//
			List<String> list = null;
			//
			String key = null;
			//
			for (final Entry<String, String> en : entries) {
				//
				if (en == null || !StringUtils.equals(getValue(en), getText(jtf))) {
					//
					continue;
					//
				} // if
					//
				if (!contains(list = ObjectUtils.getIfNull(list, ArrayList::new), key = getKey(en))) {
					//
					add(list = ObjectUtils.getIfNull(list, ArrayList::new), key);
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
			final int size = CollectionUtils.size(list);
			//
			if (size == 1) {
				//
				setSelectedItem(cbmGaKuNenBeTsuKanJi, IterableUtils.get(list, 0));
				//
			} else if (size < 1) {
				//
				setSelectedItem(cbmGaKuNenBeTsuKanJi, null);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//

			//
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private static <K, V> Collection<Entry<K, V>> entries(final Multimap<K, V> instance) {
		return instance != null ? instance.entries() : null;
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setPreferredWidth(final int width, final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = getPreferredSize(c)) == null) {
				//
				continue;
				//
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

}