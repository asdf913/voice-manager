package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.BrowserUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.ElementHandleUtil;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightUtil;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class WiktionaryGui extends JPanel implements InitializingBean, ActionListener, ListSelectionListener {

	private static final long serialVersionUID = -1375311207524968996L;

	private static final String MW_HEADING = "mw-heading";

	private JTextComponent tfText = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Execute")
	private AbstractButton btnExecute = null;

	@Note("Copy")
	private AbstractButton btnCopy = null;

	@Note("Copy Hiragana Image")
	private AbstractButton btnCopyHiraganaImage = null;

	private AbstractButton btnSaveHiraganaImage = null;

	private DefaultTableModel dtmWiktionaryEntry = null;

	private JScrollPane jsp = null;

	private Window window = null;

	private JTable jTable = null;

	private transient TableModel tm = null;

	private transient ListSelectionModel lsm = null;

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
		add(tfText = new JTextField(), String.format("growx,%1$s,span %2$s", wrap, 2));
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), String.format("%1$s,span %2$s", wrap, 2));
		//
		add(new JLabel("Entry"));
		//
		final TableColumnModel tcm = (jTable = new JTable(dtmWiktionaryEntry = new DefaultTableModel(new Object[] {
				"Language", "IPA", "Hiragana", "Hiragana Image", "Pitch Accent", "Pitch Accent Pattern" }, 0) {

			@Override
			public boolean isCellEditable(final int row, final int column) {
				//
				return false;
				//
			}

		})).getColumnModel();
		//
		setMinWidth(getColumn(tcm, 0), 40);
		//
		setMinWidth(getColumn(tcm, 2), 50);
		//
		setMinWidth(getColumn(tcm, 3), 100);
		//
		setMinWidth(getColumn(tcm, 4), 70);
		//
		setMinWidth(getColumn(tcm, 5), 115);
		//
		if ((lsm = jTable.getSelectionModel()) != null) {
			//
			lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//
			lsm.addListSelectionListener(this);
			//
		} // if
			//
		tm = jTable.getModel();
		//
		final Dimension pd = Util.getPreferredSize(jsp = new JScrollPane(jTable));
		//
		setPreferredSize(jsp, new Dimension((int) getWidth(pd), 0));
		//
		add(jsp, String.format("%1$s,span %2$s", wrap, 3));
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
				JLabel jLabel = Util.cast(JLabel.class, c);
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
					final ImageIcon imageIcon = testAndApply(Objects::nonNull, WiktionaryEntry.getHiraganaImage(we),
							ImageIcon::new, null);
					//
					(jLabel = new JLabel(imageIcon)).setHorizontalAlignment(SwingConstants.LEFT);
					//
					return jLabel;
					//
				} else if (column == 4) {
					//
					Util.setText(jLabel, we.pitchAccent);
					//
				} else if (column == 5) {
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
		add(new JLabel());
		//
		add(btnCopy = new JButton("Copy"));
		//
		add(btnCopyHiraganaImage = new JButton("Copy Hiragana Image"));
		//
		add(btnSaveHiraganaImage = new JButton("Save Hiragana Image"));
		//
		final Stream<Field> stream = testAndApply(Objects::nonNull, Util.getDeclaredFields(getClass()), Arrays::stream,
				null);
		//
		Util.forEach(Util.map(stream, f -> Util.cast(AbstractButton.class,
				testAndGet(Util.isStatic(f), () -> Narcissus.getStaticField(f), () -> Narcissus.getField(this, f)))),
				x -> {
					//
					Util.addActionListener(x, this);
					//
					if (!Objects.equals(x, btnExecute)) {
						//
						Util.setEnabled(x, false);
						//
					} // if
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

		@Note("Language")
		private String language;

		@Note("IPA")
		private String ipa;

		@Note("Hiragana")
		private String hiragana;

		@Note("Hiragana CSS Selector")
		private String hiraganaCssSelector;

		@Note("Pitch Accent")
		private String pitchAccent;

		private String pitchAccentPattern;

		private byte[] hiraganaImage;

		private String getHiraganaCssSelector() {
			return hiraganaCssSelector;
		}

		@Nullable
		private static String getHiraganaCssSelector(@Nullable final WiktionaryEntry instance) {
			return instance != null ? instance.getHiraganaCssSelector() : null;
		}

		private byte[] getHiraganaImage() {
			return hiraganaImage;
		}

		@Nullable
		private static byte[] getHiraganaImage(@Nullable final WiktionaryEntry instance) {
			return instance != null ? instance.getHiraganaImage() : null;
		}

		private void setHiraganaImage(final byte[] hiraganaImage) {
			this.hiraganaImage = hiraganaImage;
		}

		private static void setHiraganaImage(@Nullable final WiktionaryEntry instance, final byte[] hiraganaImage) {
			if (instance != null) {
				instance.setHiraganaImage(hiraganaImage);
			}
		}

	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = Util.getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
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
			final String url = Util
					.toString(new StringBuilder("https://www.wiktionary.org/wiki/").append(Util.getText(tfText)));
			//
			try {
				//
				final URLConnection urlConnection = Util.openConnection(new URL(url));
				//
				setRequestProperty(urlConnection, "User-Agent",
						"Mozilla/5.0 (X11; Linux x86_64) AppleW;ebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
				//
				html = testAndApply(Objects::nonNull, is = testAndApply(x -> {
					//
					final HttpURLConnection httpURLConnection = Util.cast(HttpURLConnection.class, urlConnection);
					//
					return httpURLConnection != null && httpURLConnection.getResponseCode() == HttpStatus.SC_OK;
					//
				}, urlConnection, Util::getInputStream, null), x -> IOUtils.toString(x, encoding), null);
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
			WiktionaryEntry we = null;
			//
			String hiraganaCssSelector = null;
			//
			try (final Playwright playwright = Playwright.create();
					final Browser browser = BrowserTypeUtil.launch(PlaywrightUtil.chromium(playwright));
					final Page page = BrowserUtil.newPage(browser)) {
				//
				PageUtil.navigate(page, url);
				//
				for (int i = 0; i < IterableUtils.size(wes); i++) {
					//
					if (StringUtils.isBlank(hiraganaCssSelector = WiktionaryEntry
							.getHiraganaCssSelector(we = IterableUtils.get(wes, i)))) {
						//
						continue;
						//
					} // if
						//
					WiktionaryEntry.setHiraganaImage(we,
							ElementHandleUtil.screenshot(querySelector(page, hiraganaCssSelector)));
					//
				} // for
					//
			} // try
				//
			Util.forEach(wes, x -> Util.addRow(dtmWiktionaryEntry, new Object[] { x }));
			//
			final int maxImageHeight = Util
					.orElse(Util.max(Util.mapToInt(FailableStreamUtil.stream(FailableStreamUtil.map(
							testAndApply(Objects::nonNull,
									testAndApply(Objects::nonNull, Util.spliterator(wes),
											x -> StreamSupport.stream(x, false), null),
									FailableStream::new, null),
							x -> {
								//
								final byte[] bs = WiktionaryEntry.getHiraganaImage(x);
								//
								return getHeight(toImage(bs), null);
								//
							})), x -> Util.intValue(x, 0))), 0);
			//
			setRowHeight(jTable, maxImageHeight);
			//
			final int rowHeight = new JTable().getRowHeight();
			//
			final int size = IterableUtils.size(wes);
			//
			setPreferredSize(jsp,
					new Dimension((int) getWidth(Util.getPreferredSize(jsp)),
							size == 1 ? Math.max(rowHeight, maxImageHeight) * 2 - 8
									: size * (Math.max(rowHeight, maxImageHeight) + 3)));
			//
			testAndRun(size == 1, () -> setRowSelectionInterval(jTable, 0, 0));
			//
			pack(window);
			//
			final Stream<Field> stream = testAndApply(Objects::nonNull, Util.getDeclaredFields(getClass()),
					Arrays::stream, null);
			//
			Util.forEach(
					Util.filter(
							Util.map(stream,
									f -> Util.cast(AbstractButton.class,
											testAndGet(Util.isStatic(f), () -> Narcissus.getStaticField(f),
													() -> Narcissus.getField(this, f)))),
							x -> !Objects.equals(x, btnExecute)),
					x -> Util.setEnabled(x, false));
			//
			return;
			//
		} // if
			//
		final Iterable<BiPredicate<WiktionaryGui, Object>> predicates = Arrays.asList(WiktionaryGui::actionPerformed1,
				WiktionaryGui::actionPerformed2);
		//
		BiPredicate<WiktionaryGui, Object> predicate = null;
		//
		for (int i = 0; i < IterableUtils.size(predicates); i++) {
			//
			if ((predicate = IterableUtils.get(predicates, i)) != null && Util.test(predicate, this, source)) {
				//
				return;
				//
			} // if
				//
		} // for
			//
	}

	private static void setRowSelectionInterval(@Nullable final JTable instance, final int row, final int column) {
		if (instance != null && getModel(instance) != null && instance.getRowCount() > row) {
			instance.setRowSelectionInterval(row, column);
		}
	}

	private static class IH implements InvocationHandler {

		private Image image = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Transferable) {
				//
				if (Objects.equals(name, "getTransferDataFlavors")) {
					//
					return new DataFlavor[] { DataFlavor.imageFlavor };
					//
				} else if (Objects.equals(name, "getTransferData")) {
					//
					return image;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static boolean actionPerformed1(@Nullable final WiktionaryGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnCopy)) {
			//
			final int[] selectedIndices = getSelectedIndices(instance.lsm);
			//
			testAndRun(length(selectedIndices) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (length(selectedIndices) == 1) {
				//
				try {
					//
					setContents(
							testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
									() -> getSystemClipboard(Toolkit.getDefaultToolkit()), null),
							new StringSelection(ObjectMapperUtil.writeValueAsString(
									new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY),
									getValueAt(instance.tm, get(selectedIndices, 0, 0), 0))),
							null);
					//
				} catch (final JsonProcessingException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
			} // if
				//
			return true;
			//
		} else if (Objects.equals(source, instance.btnCopyHiraganaImage)) {
			//
			final int[] selectedIndices = getSelectedIndices(instance.lsm);
			//
			testAndRun(length(selectedIndices) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (length(selectedIndices) == 1) {
				//
				final IH ih = new IH();
				//
				try {
					//
					ih.image = toImage(WiktionaryEntry.getHiraganaImage(
							Util.cast(WiktionaryEntry.class, getValueAt(instance.tm, get(selectedIndices, 0, 0), 0))));
					//
				} catch (final IOException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
				setContents(
						testAndGet(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
								() -> getSystemClipboard(Toolkit.getDefaultToolkit()), null),
						Reflection.newProxy(Transferable.class, ih), null);
				//
			} // if
				//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean actionPerformed2(@Nullable final WiktionaryGui instance, final Object source) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		if (Objects.equals(source, instance.btnSaveHiraganaImage)) {
			//
			final int[] selectedIndices = getSelectedIndices(instance.lsm);
			//
			testAndRun(length(selectedIndices) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (length(selectedIndices) == 1) {
				//
				try {
					//
					final JFileChooser jfc = new JFileChooser();
					//
					if (testAndGetAsBoolean(Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode()),
							() -> jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
						//
						FileUtils.writeByteArrayToFile(jfc.getSelectedFile(), WiktionaryEntry.getHiraganaImage(Util
								.cast(WiktionaryEntry.class, getValueAt(instance.tm, get(selectedIndices, 0, 0), 0))));
						//
					} // if
						//
				} catch (final IOException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
			} // if
				//
			return true;
			//
		} // if
			//
		return false;
		//
	}

	private static boolean testAndGetAsBoolean(final boolean condition, final BooleanSupplier booleanSupplier) {
		return condition && booleanSupplier != null && booleanSupplier.getAsBoolean();
	}

	private static int get(@Nullable final int[] instance, final int index, final int defaultValue) {
		return instance != null && instance.length > index ? instance[index] : defaultValue;
	}

	@Nullable
	private static Image toImage(@Nullable final byte[] bs) throws IOException {
		//
		if (!StringsUtil.startsWith(Strings.CI,
				getMimeType(testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null)), "image/")) {
			//
			return null;
			//
		} // if
			//
		try (final InputStream bais = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new, null)) {
			//
			return testAndApply(Objects::nonNull, bais, ImageIO::read, null);
			//
		} // try
			//
	}

	private static int length(@Nullable final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static void testAndRun(final boolean condition, final Runnable runnable) {
		//
		if (condition) {
			//
			Util.run(runnable);
			//
		} // if
			//
	}

	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplierTrue,
			@Nullable final Supplier<T> supplierFalse) {
		return condition ? Util.get(supplierTrue) : Util.get(supplierFalse);
	}

	@Nullable
	private static int[] getSelectedIndices(@Nullable final ListSelectionModel instance) {
		return instance != null ? instance.getSelectedIndices() : null;
	}

	@Nullable
	private static Clipboard getSystemClipboard(@Nullable final Toolkit instance) {
		return instance != null && !GraphicsEnvironment.isHeadless() ? instance.getSystemClipboard() : null;
	}

	private static void setContents(@Nullable final Clipboard instance, final Transferable contents,
			@Nullable final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	@Nullable
	private static ElementHandle querySelector(@Nullable final Page instance, final String selector) {
		return instance != null ? instance.querySelector(selector) : null;
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static int getHeight(@Nullable final Image instance, final ImageObserver imageObserver) {
		return instance != null ? instance.getHeight(imageObserver) : 0;
	}

	private static void setRowHeight(@Nullable final JTable instance, final int rowHeight) {
		if (instance != null && rowHeight > 0) {
			instance.setRowHeight(rowHeight);
		}
	}

	@Nullable
	private static Iterable<WiktionaryEntry> getWiktionaryEntries(@Nullable final String html) {
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
		Element e1 = null;
		//
		Collection<WiktionaryEntry> collection = null;
		//
		ObjectMapper objectMapper = null;
		//
		for (int i = 0; i < IterableUtils.size(es1); i++) {
			//
			Util.addAll(collection = ObjectUtils.getIfNull(collection, ArrayList::new),
					IterableUtils.toList(getWiktionaryEntries(
							ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
									ElementUtil.select(e1 = IterableUtils.get(es1, i), "h2"),
									x -> IterableUtils.get(x, 0), null)),
							e1, objectMapper = ObjectUtils.getIfNull(objectMapper,
									() -> new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY)))));
			//
		} // for
			//
		return collection;
		//
	}

	@Nullable
	private static Iterable<WiktionaryEntry> getWiktionaryEntries(final String language, final Element element,
			final ObjectMapper objectMapper) {
		//
		WiktionaryEntry we = null;
		//
		Iterable<String> ss = null;
		//
		Collection<WiktionaryEntry> collection = null;
		//
		Element e = element;
		//
		Iterable<WiktionaryEntry> wes = null;
		//
		while ((e = ElementUtil.nextElementSibling(e)) != null) {
			//
			if (Boolean
					.logicalAnd(
							CollectionUtils.isEqualCollection(classNames(e), Arrays.asList(MW_HEADING, "mw-heading4")),
							Objects.equals(
									ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
											ElementUtil.select(e, "h4"), x -> IterableUtils.get(x, 0), null)),
									"Pronunciation"))) {
				//
				if (IterableUtils
						.size(ss = Util
								.toList(Util.filter(
										Util.map(
												Util.filter(stream(ElementUtil.nextElementSibling(e)),
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
					wes = getWiktionaryEntries(we, objectMapper, Util.toList(Util.filter(
							Util.stream(ElementUtil.children(ElementUtil.nextElementSibling(e))),
							x -> Util.anyMatch(stream(x), y -> Objects.equals(y.className(), "usage-label-accent")))));
					//
					for (int j = 0; j < IterableUtils.size(wes); j++) {
						//
						Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new),
								IterableUtils.get(wes, j));
						//
					} // for
						//
				} // if
					//
			} else if (CollectionUtils.isEqualCollection(classNames(e), Arrays.asList(MW_HEADING, "mw-heading2"))) {
				//
				break;
				//
			} // if
				//
		} // while
			//
		if (IterableUtils.isEmpty(collection)) {
			//
			wes = getWiktionaryEntries2(language, element, objectMapper);
			//
			for (int i = 0; i < IterableUtils.size(wes); i++) {
				//
				Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new), IterableUtils.get(wes, i));
				//
			} // for
				//
		} // if
			//
		return collection;
		//
	}

	@Nullable
	private static Iterable<WiktionaryEntry> getWiktionaryEntries2(final String language, final Element element,
			final ObjectMapper objectMapper) {
		//
		WiktionaryEntry we = null;
		//
		Iterable<String> ss = null;
		//
		Collection<WiktionaryEntry> collection = null;
		//
		Element e = element;
		//
		Iterable<WiktionaryEntry> wes = null;
		//
		while ((e = ElementUtil.nextElementSibling(e)) != null) {
			//
			if (Boolean
					.logicalAnd(
							CollectionUtils.isEqualCollection(classNames(e), Arrays.asList(MW_HEADING, "mw-heading3")),
							Objects.equals(
									ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
											ElementUtil.select(e, "h3"), x -> IterableUtils.get(x, 0), null)),
									"Pronunciation"))) {
				//
				if (IterableUtils
						.size(ss = Util
								.toList(Util.filter(
										Util.map(
												Util.filter(stream(ElementUtil.nextElementSibling(e)),
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
					wes = getWiktionaryEntries(we, objectMapper, Util.toList(Util.filter(
							Util.stream(ElementUtil.children(ElementUtil.nextElementSibling(e))),
							x -> Util.anyMatch(stream(x), y -> Objects.equals(y.className(), "usage-label-accent")))));
					//
					for (int j = 0; j < IterableUtils.size(wes); j++) {
						//
						Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new),
								IterableUtils.get(wes, j));
						//
					} // for
						//
				} // if
					//
			} else if (CollectionUtils.isEqualCollection(classNames(e), Arrays.asList(MW_HEADING, "mw-heading2"))) {
				//
				break;
				//
			} // if
				//
		} // while
			//
		return collection;
		//
	}

	@Nullable
	private static Iterable<WiktionaryEntry> getWiktionaryEntries(final WiktionaryEntry instance,
			final ObjectMapper objectMapper, final Iterable<Element> es) {
		//
		Element e1, e2 = null;
		//
		WiktionaryEntry we = null;
		//
		Collection<WiktionaryEntry> collection = null;
		//
		for (int j = 0; j < IterableUtils.size(es); j++) {
			//
			try {
				//
				if ((e1 = IterableUtils.get(es, j)) == null || (we = ObjectUtils.getIfNull(
						ObjectMapperUtil.readValue(objectMapper,
								ObjectMapperUtil.writeValueAsBytes(objectMapper, instance), WiktionaryEntry.class),
						we)) == null) {
					//
					continue;
					//
				} // if
					//
			} catch (final IOException ex) {
				//
				throw new RuntimeException(ex);
				//
			} // try
				//
			we.hiragana = ElementUtil.text(e2 = testAndApply(x -> IterableUtils.size(x) == 1,
					Util.toList(Util.filter(stream(e1), x -> Objects.equals(NodeUtil.attr(x, "lang"), "ja"))),
					x -> IterableUtils.get(x, 0), null));
			//
			we.hiraganaCssSelector = cssSelector(e2);
			//
			we.pitchAccent = ElementUtil.text(testAndApply(x -> IterableUtils.size(x) == 1,
					Util.toList(Util.filter(stream(e1), x -> Objects.equals(NodeUtil.attr(x, "class"), "Latn"))),
					x -> IterableUtils.get(x, 0), null));
			//
			we.pitchAccentPattern = testAndApply(CollectionUtils::isNotEmpty,
					Util.toList(Util.map(Util.filter(stream(e1),
							x -> Boolean.logicalAnd(StringsUtil.equals(Strings.CI, ElementUtil.tagName(x), "a"),
									NodeUtil.hasAttr(x, "title"))),
							x -> NodeUtil.attr(x, "title"))),
					x -> IterableUtils.get(x, IterableUtils.size(x) - 1), null);
			//
			Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new), we);
			//
		} // for
			//
		return collection;
		//
	}

	@Nullable
	private static String cssSelector(@Nullable final Element instance) {
		//
		if (instance == null || Narcissus.getField(instance, getFieldByName(Util.getClass(instance), "tag")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.cssSelector();
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
	private static Stream<Element> stream(@Nullable final Element instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final FailablePredicate<T, E> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public void valueChanged(final ListSelectionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), lsm)) {
			//
			final int[] selectedIndices = getSelectedIndices(lsm);
			//
			testAndRun(length(selectedIndices) > 1, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			if (length(selectedIndices) == 1) {
				//
				final Stream<Field> stream = testAndApply(Objects::nonNull, Util.getDeclaredFields(getClass()),
						Arrays::stream, null);
				//
				Util.forEach(
						Util.filter(
								Util.map(stream,
										f -> Util.cast(AbstractButton.class,
												Util.isStatic(f) ? Narcissus.getStaticField(f)
														: Narcissus.getField(this, f))),
								x -> !Objects.equals(x, btnExecute)),
						x -> Util.setEnabled(x, true));
				//
			} // if
				//
		} // if
			//
	}

}