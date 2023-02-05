package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import net.miginfocom.swing.MigLayout;

public class JouYouKanjiGui extends JFrame implements EnvironmentAware, InitializingBean, KeyListener {

	private static final long serialVersionUID = 3076804232578596080L;

	private transient PropertyResolver propertyResolver = null;

	private JTextComponent tfText = null;

	private transient ComboBoxModel<Boolean> cbmJouYouKanJi = null;

	private transient IValue0<List<String>> jouYouKanJiList = null;

	private JouYouKanjiGui() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setJouYouKanJiList(final List<String> jouYouKanJiList) {
		this.jouYouKanJiList = Unit.with(jouYouKanJiList);
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
				"org.springframework.context.support.JouYouKanjiGui.text")));
		//
		tfText.addKeyListener(this);
		//
		add(new JLabel("常用漢字"));
		//
		final JComboBox<?> jcbJouYouKanji = new JComboBox<>(
				cbmJouYouKanJi = new DefaultComboBoxModel<>(toArray(getBooleanValues(), new Boolean[] {})));
		//
		add(jcbJouYouKanji);
		//
		final List<Component> cs = Arrays.asList(tfText, jcbJouYouKanji);
		//
		final Dimension preferredSize = cs.stream().map(JouYouKanjiGui::getPreferredSize).max((a, b) -> {
			return a != null && b != null ? Double.compare(a.getWidth(), b.getWidth()) : 0;
		}).orElse(null);
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), cs);
			//
		} // if
			//
	}

	private static List<Boolean> getBooleanValues() throws IllegalAccessException {
		//
		List<Boolean> list = null;
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, getDeclaredFields(Boolean.class), Arrays::stream, null),
						f -> Objects.equals(getType(f), Boolean.class)));
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			if (!Objects.equals(Boolean.class, getType(f = IterableUtils.get(fs, i)))) {
				//
				continue;
				//
			} // if
				//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), cast(Boolean.class, f.get(null)));
			//
		} // for
			//
		return list;
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) {
		return instance != null ? instance.getDeclaredFields() : null;
	}

	private static Class<?> getType(final Field instance) {
		return instance != null ? instance.getType() : null;
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
		if (Objects.equals(source, tfText)) {
			//
			// 常用漢字
			//
			final String text = getText(tfText);
			//
			final List<String> list = IValue0Util.getValue0(jouYouKanJiList);
			//
			if (StringUtils.isEmpty(text) || CollectionUtils.isEmpty(list)) {
				//
				setSelectedItem(cbmJouYouKanJi, null);
				//
			} else if (jouYouKanJiList != null) {
				//
				setSelectedItem(cbmJouYouKanJi,
						StringUtils.length(text) <= orElse(max(mapToInt(stream(list), StringUtils::length)), 0)
								? contains(list, text)
								: null);
				//
			} // if
				//
		} // if
			//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null)
				? instance.mapToInt(mapper)
				: null;
		//
	}

	private static OptionalInt max(final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	private static int orElse(final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
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

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) {
		//
		if (iterator(cs) != null) {
			//
			Dimension d = null;
			//
			for (final Component c : cs) {
				//
				if (c == null || (d = getPreferredSize(c)) == null) {
					//
					continue;
					//
				} // if
					//
				c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
				//
			} // for
				//
		} // if
			//
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
	}

}