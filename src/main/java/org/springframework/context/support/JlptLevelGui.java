package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.miginfocom.swing.MigLayout;

public class JlptLevelGui extends JFrame implements InitializingBean, ActionListener {

	private static final long serialVersionUID = 1466869508176258464L;

	private List<String> jlptLevels = null;

	private AbstractButton btnExportJson = null;

	private JTextComponent tfJson = null;

	private ObjectMapper objectMapper = null;

	private JlptLevelGui() {
	}

	public void setJlptLevels(final List<String> jlptLevels) {
		this.jlptLevels = jlptLevels;
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
		add(new JLabel("JLPT Level(s)"));
		//
		final ListModel<String> listModel = testAndApply(Objects::nonNull, toArray(jlptLevels, new String[] {}),
				DefaultComboBoxModel::new, null);
		//
		final JList<String> jList = listModel != null ? new JList<>(listModel) : new JList<>();
		//
		final String wrap = "wrap";
		//
		add(jList, wrap);
		//
		add(new JLabel());
		//
		add(btnExportJson = new JButton("Export JSON"), wrap);
		//
		btnExportJson.addActionListener(this);
		//
		add(new JLabel("JSON"));
		//
		add(tfJson = new JTextField());
		//
		final List<Component> cs = Arrays.asList(jList, btnExportJson, tfJson);
		//
		final Dimension preferredSize = cs.stream().map(JlptLevelGui::getPreferredSize).max((a, b) -> {
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

	private static Dimension getPreferredSize(final Component instance) {
		return instance != null ? instance.getPreferredSize() : null;
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

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(evt != null ? evt.getSource() : null, btnExportJson)) {
			//
			final ObjectMapper objectMapper = getObjectMapper();
			//
			try {
				//
				setText(tfJson, objectMapper != null ? objectMapper.writeValueAsString(jlptLevels) : null);
				//
			} catch (JsonProcessingException e) {
				//
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
				//
			} // try
				//
		} // if
			//
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

}