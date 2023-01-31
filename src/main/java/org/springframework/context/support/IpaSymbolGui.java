package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.collect.Multimap;

import net.miginfocom.swing.MigLayout;

public class IpaSymbolGui extends JFrame implements EnvironmentAware, InitializingBean, ActionListener {

	private static final long serialVersionUID = 467011648930037863L;

	private transient PropertyResolver propertyResolver = null;

	private JTextComponent tfText, tfIpaSymbol = null;

	private AbstractButton btnGetIpaSymbol = null;

	private Unit<Multimap<String, String>> ipaSymbolMultimap = null;

	private IpaSymbolGui() {
	}

	public void setIpaSymbolMultimap(final Multimap<String, String> ipaSymbolMultimap) {
		this.ipaSymbolMultimap = Unit.with(ipaSymbolMultimap);
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
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
		final String wrap = "wrap";
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.text")), wrap);
		//
		add(new JLabel(""));
		//
		add(btnGetIpaSymbol = new JButton("Get IPA Symbol"), wrap);
		//
		btnGetIpaSymbol.addActionListener(this);
		//
		add(new JLabel("IPA"));
		//
		add(tfIpaSymbol = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.IpaSymbolGui.ipaSymbol")));
		//
		final Dimension preferredSize = btnGetIpaSymbol.getPreferredSize();
		//
		if (preferredSize != null) {
			//
			setPreferredWidth((int) preferredSize.getWidth(), tfText, tfIpaSymbol);
			//
		} // if
			//
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		//
		if (Objects.equals(e != null ? e.getSource() : null, btnGetIpaSymbol)) {
			//
			final Multimap<String, String> multimap = IValue0Util.getValue0(ipaSymbolMultimap);
			//
			final Collection<String> values = multimap != null ? multimap.get(getText(tfText)) : null;
			//
			final int size = IterableUtils.size(values);
			//
			if (size == 0) {
				//
				setText(tfIpaSymbol, null);
				//
			} else if (size == 1) {
				//
				setText(tfIpaSymbol, toString(IterableUtils.get(values, 0)));
				//
			} else if (!GraphicsEnvironment.isHeadless() && !isTestMode()) {
				//
				final Object[] array = values != null ? values.toArray() : null;
				//
				final JList<Object> list = array != null ? new JList<>(array) : new JList<>();
				//
				JOptionPane.showMessageDialog(null, list, "IPA", JOptionPane.PLAIN_MESSAGE);
				//
				setText(tfIpaSymbol, toString(list.getSelectedValue()));
				//
			} // if
				//
		} // if
			//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static boolean isTestMode() {
		return forName("org.junit.jupiter.api.Test") != null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
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