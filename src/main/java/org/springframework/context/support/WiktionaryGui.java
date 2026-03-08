package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		setPreferredSize(jsp, new Dimension((int) getWidth(pd), 0));
		//
		add(jsp);
		//
		final TableCellRenderer tcr = jTable.getDefaultRenderer(Object.class);
		//
		jTable.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
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
		});
		//
	}

	private static void setPreferredSize(@Nullable final Component instance, final Dimension preferredSize) {
		if (instance != null) {
			instance.setPreferredSize(preferredSize);
		}
	}

	private static double getWidth(@Nullable final Dimension instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static void setMinWidth(@Nullable final TableColumn instance, final int minWidth) {
		if (instance != null) {
			instance.setMinWidth(minWidth);
		}
	}

	@Nullable
	private static TableColumn getColumn(@Nullable final TableColumnModel instance, final int columnIndex) {
		return instance != null ? instance.getColumn(columnIndex) : null;
	}

	@Nullable
	private static Object getValueAt(@Nullable final TableModel instance, final int row, final int column) {
		return instance != null ? instance.getValueAt(row, column) : null;
	}

	@Nullable
	private static TableModel getModel(@Nullable final JTable instance) {
		return instance != null ? instance.getModel() : null;
	}

	@Nullable
	private static Component getTableCellRendererComponent(@Nullable final TableCellRenderer instance,
			final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row,
			final int column) {
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

	private static void pack(@Nullable final Window instance) {
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

	private static <T, U> void testAndAccept(@Nullable final BiPredicate<T, U> instance, final T t, final U u,
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

		@Target(ElementType.FIELD)
		@Retention(RetentionPolicy.RUNTIME)
		private @interface Note {
			String value();
		}

		@Note("Language")
		private String language;

		@Note("IPA")
		private String ipa;

		@Note("Hiragana")
		private String hiragana;

		@Note("Pitch Accent")
		private String pitchAccent;

		private String pitchAccentPattern;

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			Util.forEach(IntStream.iterate(Util.getRowCount(dtmWiktionaryEntry) - 1, i -> i >= 0, i -> i - 1),
					i -> Util.removeRow(dtmWiktionaryEntry, i));
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
				setRequestProperty(Util.cast(HttpURLConnection.class, urlConnection), "User-Agent",
						"Mozilla/5.0 (X11; Linux x86_64) AppleW;ebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
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
			final Iterable<WiktionaryEntry> wes = getWiktionaryEntries(html);
			//
			Util.forEach(wes, x -> Util.addRow(dtmWiktionaryEntry, new Object[] { x }));
			//
			setPreferredSize(jsp, new Dimension((int) getWidth(Util.getPreferredSize(jsp)),
					IterableUtils.size(wes) * (new JTable().getRowHeight() + 3)));
			//
			pack(window);
			//
		} // if
			//
	}

	private static Iterable<WiktionaryEntry> getWiktionaryEntries(final String html) {
		//
		if (html == null || Narcissus.getField(html, getFieldByName(Util.getClass(html), "value")) == null) {
			//
			return null;
			//
		} // if
			//
		final Document document = Jsoup.parse(html, "");
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
				if (Boolean.logicalAnd(
						CollectionUtils.isEqualCollection(classNames(e1), Arrays.asList("mw-heading", "mw-heading4")),
						Objects.equals(
								ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
										ElementUtil.select(e1, "h4"), x -> IterableUtils.get(x, 0), null)),
								"Pronunciation"))) {
					//
					if (IterableUtils
							.size(ss = Util
									.toList(Util.filter(
											Util.map(
													Util.filter(stream(ElementUtil.nextElementSibling(e1)),
															x -> StringsUtil.startsWith(Strings.CI, ElementUtil.html(x),
																	"IPA")),
													x -> ElementUtil.text(testAndApply(y -> IterableUtils.size(y) == 1,
															Util.toList(Util.filter(stream(ElementUtil.parent(x)),
																	y -> Util.contains(classNames(y), "IPA"))),
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
							try {
								//
								we = ObjectUtils
										.getIfNull(
												readValue(
														objectMapper = ObjectUtils.getIfNull(objectMapper,
																() -> new ObjectMapper().setVisibility(
																		PropertyAccessor.ALL, Visibility.ANY)),
														ObjectMapperUtil.writeValueAsBytes(objectMapper, we),
														WiktionaryEntry.class),
												we);
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
							we.pitchAccentPattern = testAndApply(CollectionUtils::isNotEmpty,
									Util.toList(
											Util.map(
													Util.filter(stream(e2),
															x -> Boolean.logicalAnd(
																	StringsUtil.equals(Strings.CI,
																			ElementUtil.tagName(x), "a"),
																	NodeUtil.hasAttr(x, "title"))),
													x -> NodeUtil.attr(x, "title"))),
									x -> IterableUtils.get(x, IterableUtils.size(x) - 1), null);
							//
							Util.add(wes = ObjectUtils.getIfNull(wes, ArrayList::new), we);
							//
						} // for
							//
					} // if
						//
				} else if (CollectionUtils.isEqualCollection(classNames(e1),
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
		return wes;
		//
	}

	private static void setRequestProperty(@Nullable final URLConnection instance, final String key,
			final String value) {
		if (instance != null) {
			instance.setRequestProperty(key, value);
		}
	}

	@Nullable
	private static Set<String> classNames(@Nullable final Element instance) {
		return instance != null ? instance.classNames() : null;
	}

	@Nullable
	private static <T> T readValue(@Nullable final ObjectMapper instance, final byte[] src, final Class<T> valueType)
			throws IOException {
		//
		if (instance == null
				|| Narcissus.getField(instance, getFieldByName(Util.getClass(instance), "_jsonFactory")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.readValue(src, valueType);
		//
	}

	@Nullable
	private static Stream<Element> stream(@Nullable final Element instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			@Nullable final Function<T, R> functionFalse) {
		return Util.test(predicate, value) ? Util.apply(functionTrue, value) : Util.apply(functionFalse, value);
	}

}