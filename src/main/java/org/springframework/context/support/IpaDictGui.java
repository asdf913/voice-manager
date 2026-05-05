package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class IpaDictGui extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -7787164352542815004L;

	private JTextComponent tfText = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	private AbstractButton btnCopy = null;

	private DefaultListModel<String> dlm = null;

	private transient ListSelectionModel lsm = null;

	private transient Multimap<String, String> multimap = null;

	private IpaDictGui() {
		//
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		if (Narcissus.getField(this, Narcissus.findField(getClass(), "component")) == null) {
			//
			return;
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), "growx,pushx");
		//
		add(btnExecute = new JButton("Execute"), "wrap");
		//
		add(new JLabel("IPA"));
		//
		final JList<String> jList = new JList<>(dlm = new DefaultListModel<>());
		//
		setSelectionMode(lsm = jList.getSelectionModel(), ListSelectionModel.SINGLE_SELECTION);
		//
		add(new JScrollPane(jList), String.format("growx,span %1$s,wrap", 2));
		//
		add(new JLabel());
		//
		add(btnCopy = new JButton("Copy"));
		//
		Util.setEnabled(btnCopy, false);
		//
		Util.forEach(
				Util.map(
						Util.filter(Util.stream(FieldUtils.getAllFieldsList(getClass())),
								f -> Util.isAssignableFrom(AbstractButton.class, Util.getType(f))),
						f -> Util.cast(AbstractButton.class, Narcissus.getField(this, f))),
				x -> Util.addActionListener(x, this));
		//
		try (final InputStream is = IpaDictGui.class.getResourceAsStream("/ja.txt")) {
			//
			final Iterable<String> lines = testAndApply(Objects::nonNull, is,
					x -> IOUtils.readLines(x, StandardCharsets.UTF_8), null);
			//
			if (Util.iterator(lines) != null) {
				//
				String key = null;
				//
				String[] ss = null;
				//
				for (final String line : lines) {
					//
					key = StringUtils.substringBefore(line, '\t');
					//
					ss = StringUtils.split(StringUtils.substringAfter(line, '\t'), ',');
					//
					for (int i = 0; ss != null && i < ss.length; i++) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key,
								StringUtils.trim(ArrayUtils.get(ss, i)));
						//
					} // for
						//
				} // for
					//
			} // if
				//
			Util.setText(tfText, Util.orElse(
					Util.max(Util.stream(MultimapUtil.keySet(multimap)), Comparator.comparing(StringUtils::length)),
					null));
			//
		} // try
			//
	}

	private static void setSelectionMode(final ListSelectionModel instance, final int selectionMode) {
		if (instance != null) {
			instance.setSelectionMode(selectionMode);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T t,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, t) ? FailableFunctionUtil.apply(functionTrue, t)
				: FailableFunctionUtil.apply(functionFalse, t);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			removeAllElements(dlm);
			//
			final Iterable<String> iterable = MultimapUtil.get(multimap, Util.getText(tfText));
			//
			if (Util.iterator(iterable) != null) {
				//
				for (final String string : iterable) {
					//
					addElement(dlm, string);
					//
				} // for
					//
			} // if
				//
			Util.setEnabled(btnCopy, !IterableUtils.isEmpty(iterable));
			//
		} else if (Objects.equals(source, btnCopy)) {
			//
			final int[] selectedIndices = Util.getSelectedIndices(lsm);
			//
			if (selectedIndices != null) {
				//
				if (selectedIndices.length > 1) {
					//
					throw new IllegalStateException();
					//
				} else if (selectedIndices.length == 1 && !isTestMode()) {
					//
					setContents(getSystemClipboard(getToolkit()),
							new StringSelection(Util.getElementAt(dlm, selectedIndices[0])), null);
					//
				} // if
					//
			} // if
				//
		} // if
			//
	}

	private static <T> void addElement(final DefaultListModel<T> instance, final T value) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "delegate")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (Narcissus.getField(instance,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return;
			//
		} // if
			//
		instance.addElement(value);
		//
	}

	private static void removeAllElements(final DefaultListModel<?> instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Iterable<Field> fs = Util
				.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(instance))),
						f -> Objects.equals(Util.getName(f), "delegate")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (Narcissus.getField(instance,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
			//
			return;
			//
		} // if
			//
		instance.removeAllElements();
		//
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
		//
		if (instance == null
				|| Boolean.logicalAnd(Objects.equals(Util.getName(Util.getClass(instance)), "sun.awt.HeadlessToolkit"),
						GraphicsEnvironment.isHeadless())) {
			//
			return null;
			//
		} // if
			//
		return instance.getSystemClipboard();
		//
	}

	public static void main(final String[] args) throws Exception {
		//
		final IpaDictGui instance = new IpaDictGui();
		//
		instance.afterPropertiesSet();
		//
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() ? new JFrame() : null;
		//
		if (jFrame != null) {
			//
			jFrame.add(instance);
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			jFrame.pack();
			//
			if (!isTestMode()) {
				//
				jFrame.setVisible(true);
				//
			} // if
				//
		} // if
			//
		Util.setText(instance.tfText, null);
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

}