package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class WiktionaryGui extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -1375311207524968996L;

	private JTextComponent tfText = null;

	private AbstractButton btnExecute = null;

	private DefaultTableModel dtmWiktionaryEntry = null;

	private JScrollPane jsp = null;

	private Window window = null;

	private WiktionaryGui() {
		//
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		if (Narcissus.getField(this, Narcissus.findField(getClass(), "component")) == null) {
			//
			return;
			//
		} // if
			//
		add(new JLabel("Text"));
		//
		final String wrap = "wrap";
		//
		add(tfText = new JTextField(), String.format("growx,%1$s", wrap));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), wrap);
		//
		btnExecute.addActionListener(this);
		//
		add(new JLabel("Entry"));
		//
		final JTable jTable = new JTable(dtmWiktionaryEntry = new DefaultTableModel(
				new Object[] { "Language", "IPA", "Hiragana", "Pitch Accent", "Pitch Accent Pattern" }, 0) {

			@Override
			public boolean isCellEditable(final int row, final int column) {
				//
				return false;
				//
			}

		});
		//
		setMinWidth(getColumn(jTable.getColumnModel(), 4), 105);
		//
		final Dimension pd = Util.getPreferredSize(jsp = new JScrollPane(jTable));
		//
		jsp.setPreferredSize(new Dimension((int) getWidth(pd), 0));
		//
		add(jsp);
		//
		final TableCellRenderer tcr = jTable.getDefaultRenderer(Object.class);
		//
		jTable.setDefaultRenderer(Object.class, new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value,
					final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				//
				final Component c = WiktionaryGui.getTableCellRendererComponent(tcr, table, value, isSelected, hasFocus,
						row, column);
				//
				final WiktionaryEntry we = Util.cast(WiktionaryEntry.class, getValueAt(getModel(table), row, 0));
				//
				if (we != null) {
					//
					final JLabel jLabel = Util.cast(JLabel.class, c);
					//
					if (column == 0) {
						//
						Util.setText(jLabel, we.language);
						//
					} else if (column == 1) {
						//
						Util.setText(jLabel, we.ipa);
						//
					} else if (column == 2) {
						//
						Util.setText(jLabel, we.hiragana);
						//
					} else if (column == 3) {
						//
						Util.setText(jLabel, we.pitchAccent);
						//
					} else if (column == 4) {
						//
						Util.setText(jLabel, we.pitchAccentPattern);
						//
					} // if
						//
				} // if
					//
				return c;
				//
			}
		});
		//
	}

	private static double getWidth(final Dimension instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static void setMinWidth(final TableColumn instance, final int minWidth) {
		if (instance != null) {
			instance.setMinWidth(minWidth);
		}
	}

	private static TableColumn getColumn(final TableColumnModel instance, final int columnIndex) {
		return instance != null ? instance.getColumn(columnIndex) : null;
	}

	private static Object getValueAt(final TableModel instance, final int row, final int column) {
		return instance != null ? instance.getValueAt(row, column) : null;
	}

	private static TableModel getModel(final JTable instance) {
		return instance != null ? instance.getModel() : null;
	}

	private static Component getTableCellRendererComponent(final TableCellRenderer instance, final JTable table,
			final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		return instance != null
				? instance.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
				: null;
	}

	public static void main(final String[] args) throws Exception {
		//
		final JFrame jFrame = testAndGet(!GraphicsEnvironment.isHeadless(), JFrame::new);
		//
		if (jFrame != null) {
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			final WiktionaryGui instance = new WiktionaryGui();
			//
			instance.setLayout(new MigLayout());
			//
			instance.afterPropertiesSet();
			//
			testAndAccept(Util::containsKey, System.getProperties(),
					"org.springframework.context.support.WiktionaryGui.text",
					(a, b) -> Util.setText(instance.tfText, MapUtils.getString(a, b)));
			//
			jFrame.add(instance);
			//
			pack(instance.window = jFrame);
			//
		} // if
			//
		if (!isTestMode()) {
			//
			Util.setVisible(jFrame, true);
			//
		} // if
			//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static void pack(final Window instance) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// objectLock
			//
		final Class<?> clz = Util.getClass(instance);
		//
		Field f = getFieldByName(clz, "objectLock");
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
			// graphicsConfig
			//
		if ((f = getFieldByName(clz, "graphicsConfig")) != null && Narcissus.getField(instance, f) == null) {
			//
			return;
			//
		} // if
			//
		instance.pack();
		//
	}

	private static Field getFieldByName(final Class<?> clz, final String name) {
		//
		final Iterable<Field> fs = Util.collect(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						x -> Objects.equals(Util.getName(x), name)),
				Collectors.toList());
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (instance != null && instance.test(t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	@Nullable
	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplier) {
		return condition ? Util.get(supplier) : null;
	}

	private static class WiktionaryEntry {

		private String language, ipa, hiragana, pitchAccent, pitchAccentPattern;

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			for (int i = Util.getRowCount(dtmWiktionaryEntry) - 1; i >= 0; i--) {
				//
				Util.removeRow(dtmWiktionaryEntry, i);
				//
			} // for
				//
			String html = null;
			//
			final String encoding = "utf-8";
			//
			InputStream is = null;
			//
			try {
				//
				final URLConnection urlConnection = Util.openConnection(new URL(Util
						.toString(new StringBuilder("https://www.wiktionary.org/wiki/").append(Util.getText(tfText)))));
				//
				final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class, urlConnection);
				//
				if (httpURLConnection != null) {
					//
					httpURLConnection.setRequestProperty("User-Agent",
							"Mozilla/5.0 (X11; Linux x86_64) AppleW;ebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
					//
				} // if
					//
				html = IOUtils.toString(is = Util.getInputStream(urlConnection), encoding);
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} finally {
				//
				IOUtils.closeQuietly(is);
				//
			} // try
				//
			final Document document = testAndApply(Objects::nonNull, html, x -> Jsoup.parse(x, ""), null);
			//
			final Iterable<Element> es1 = ElementUtil.select(document, ".mw-heading.mw-heading2");
			//
			String language = null;
			//
			Element e1, e2 = null;
			//
			WiktionaryEntry we = null;
			//
			Iterable<String> ss = null;
			//
			Collection<WiktionaryEntry> wes = null;
			//
			Iterable<Element> es2 = null;
			//
			ObjectMapper objectMapper = null;
			//
			for (int i = 0; i < IterableUtils.size(es1); i++) {
				//
				language = ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
						ElementUtil.select(e1 = IterableUtils.get(es1, i), "h2"), x -> IterableUtils.get(x, 0), null));
				//
				while ((e1 = ElementUtil.nextElementSibling(e1)) != null) {
					//
					if (CollectionUtils.isEqualCollection(e1.classNames(), Arrays.asList("mw-heading", "mw-heading4"))
							&& Objects.equals(
									ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
											ElementUtil.select(e1, "h4"), x -> IterableUtils.get(x, 0), null)),
									"Pronunciation")) {
						//
						if (IterableUtils
								.size(ss = Util
										.toList(Util.filter(
												Util.map(Util.filter(
														stream(ElementUtil.nextElementSibling(e1)),
														x -> StringsUtil.startsWith(Strings.CI, ElementUtil.html(x),
																"IPA")),
														x -> ElementUtil.text(testAndApply(
																y -> IterableUtils.size(y) == 1,
																Util.toList(Util.filter(stream(ElementUtil.parent(x)),
																		y -> Util.contains(
																				y != null ? y.classNames() : null,
																				"IPA"))),
																y -> IterableUtils.get(y, 0), null))),
												Objects::nonNull))) == 1) {
							//
							(we = new WiktionaryEntry()).language = language;
							//
							we.ipa = IterableUtils.get(ss, 0);
							//
							es2 = Util.toList(
									Util.filter(Util.stream(ElementUtil.children(ElementUtil.nextElementSibling(e1))),
											x -> Util.anyMatch(stream(x),
													y -> Objects.equals(y.className(), "usage-label-accent"))));
							//
							for (int j = 0; j < IterableUtils.size(es2); j++) {
								//
								if ((e2 = IterableUtils.get(es2, j)) == null) {
									//
									continue;
									//
								} // if
									//
								if (objectMapper == null) {
									//
									(objectMapper = new ObjectMapper()).setVisibility(PropertyAccessor.ALL,
											Visibility.ANY);
									//
								} // if
									//
								try {
									//
									we = ObjectUtils.getIfNull(objectMapper != null ? objectMapper.readValue(
											ObjectMapperUtil.writeValueAsBytes(objectMapper, we), WiktionaryEntry.class)
											: null, we);
									//
								} catch (final IOException e) {
									//
									throw new RuntimeException(e);
									//
								} // try
									//
								we.hiragana = ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
										Util.toList(Util.filter(stream(e2),
												x -> Objects.equals(NodeUtil.attr(x, "lang"), "ja"))),
										x -> IterableUtils.get(x, 0), null));
								//
								we.pitchAccent = ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
										Util.toList(Util.filter(stream(e2),
												x -> Objects.equals(NodeUtil.attr(x, "class"), "Latn"))),
										x -> IterableUtils.get(x, 0), null));
								//
								we.pitchAccentPattern = testAndApply(CollectionUtils::isNotEmpty, Util.toList(Util.map(
										Util.filter(stream(e2),
												x -> x != null && StringsUtil.equals(Strings.CI, x.tagName(), "a")
														&& NodeUtil.hasAttr(x, "title")),
										x -> NodeUtil.attr(x, "title"))),
										x -> IterableUtils.get(x, IterableUtils.size(x) - 1), null);
								//
								Util.add(wes = ObjectUtils.getIfNull(wes, ArrayList::new), we);
								//
							} // for
								//
						} // if
							//
					} else if (CollectionUtils.isEqualCollection(e1.classNames(),
							Arrays.asList("mw-heading", "mw-heading2"))) {
						//
						break;
						//
					} // if
						//
				} // while
					//
			} // for
				//
			Util.forEach(wes, x -> Util.addRow(dtmWiktionaryEntry, new Object[] { x }));
			//
			final Dimension pd = Util.getPreferredSize(jsp);
			//
			if (jsp != null) {
				//
				jsp.setPreferredSize(
						new Dimension((int) getWidth(pd), IterableUtils.size(wes) * (new JTable().getRowHeight() + 3)));
				//
			} // if
				//
			pack(window);
			//
		} // if
			//
	}

	private static Stream<Element> stream(final Element instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return Util.test(predicate, value) ? Util.apply(functionTrue, value) : Util.apply(functionFalse, value);
	}

}