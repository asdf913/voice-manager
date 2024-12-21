package org.springframework.context.support;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.ss.usermodel.Workbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.google.common.base.Functions;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;
import com.healthmarketscience.jackcess.impl.DatabaseImpl.FileFormatDetails;
import com.healthmarketscience.jackcess.impl.JetFormat;
import com.j256.simplemagic.ContentType;

import io.github.toolfactory.narcissus.Narcissus;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerExportPanel extends JPanel
		implements Titled, InitializingBean, EnvironmentAware, ActionListener {

	private static final long serialVersionUID = -2806818680922579630L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	/**
	 * @see java.lang.String#format(java.lang.String,java.lang.Object...)
	 * 
	 * @see net.miginfocom.layout.ConstraintParser#parseComponentConstraint(java.lang.String)
	 * 
	 * @see <a href=
	 *      "https://github.com/mikaelgrev/miglayout/blob/master/core/src/main/java/net/miginfocom/layout/ConstraintParser.java#L534">net.miginfocom.layout.ConstraintParser.parseComponentConstraint(java.lang.String)&nbsp;Line&nbsp;534&nbsp;at&nbsp;master&nbsp;·&nbsp;mikaelgrev/miglayout</a>
	 */
	private static final String SPAN_ONLY_FORMAT = "span %1$s";

	private static final String WRAP = "wrap";

	private static final String GROWX = "growx";

	private static final String PASSWORD = "Password";

	private ComboBoxModel<Class> cbmWorkbookClass = null;

	private ComboBoxModel<EncryptionMode> cbmEncryptionMode = null;

	private ComboBoxModel<CompressionLevel> cbmCompressionLevel = null;

	private ComboBoxModel<FileFormat> cbmMicrosoftAccessFileFormat = null;

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> workbookClassFailableSupplierMap = null;

	private Class<?> workbookClass = null;

	private PropertyResolver propertyResolver = null;

	private Duration presentationSlideDuration = null;

	private FileFormat microsoftAccessFileFormat = null;

	private JTextComponent tfExportPassword, tfOrdinalPositionFileNamePrefix, tfJlptFolderNamePrefix,
			tfExportHtmlFileName, tfPresentationSlideDuration, tfPhraseCounter, tfPhraseTotal = null;

	private AbstractButton cbOverMp3Title, cbOrdinalPositionAsFileNamePrefix, cbJlptAsFolder, cbExportHtml,
			cbExportListHtml, cbExportWebSpeechSynthesisHtml, cbExportHtmlAsZip, cbExportHtmlRemoveAfterZip,
			cbExportListSheet, cbExportJlptSheet, cbExportPresentation, cbEmbedAudioInPresentation,
			cbHideAudioImageInPresentation, cbExportMicrosoftAccess, btnExport = null;

	private JProgressBar progressBarExport = null;

	@Override
	public String getTitle() {
		return "Export";
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	private static class MicrosoftAccessFileFormatListCellRenderer implements ListCellRenderer<Object> {

		private String commonPrefix = null;

		private ListCellRenderer<Object> listCellRenderer = null;

		@Override
		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			//
			final FileFormat fileFormat = Util.cast(FileFormat.class, value);
			//
			if (fileFormat != null) {
				//
				final String toString = Util.toString(value);
				//
				int idx = StringUtils.indexOf(toString, ' ');
				//
				StringBuilder sb = null;
				//
				if (idx >= 0) {
					//
					(sb = new StringBuilder(StringUtils.defaultString(toString))).insert(idx,
							String.format(" (%1$s)", fileFormat.getFileExtension()));
					//
				} // if
					//
				if ((idx = StringUtils.indexOf(sb, commonPrefix)) >= 0
						&& (sb = getIfNull(sb, () -> new StringBuilder(StringUtils.defaultString(toString)))) != null) {
					//
					sb.delete(idx, idx + StringUtils.length(commonPrefix));
					//
				} // if
					//
				if (sb != null) {
					//
					return VoiceManagerExportPanel.getListCellRendererComponent(this, list, sb, index, isSelected,
							cellHasFocus);
					//
				} // if
					//
			} // if
				//
			return VoiceManagerExportPanel.getListCellRendererComponent(listCellRenderer, list, value, index,
					isSelected, cellHasFocus);
			//
		}

	}

	/*
	 * Copy from the below URL
	 * 
	 * https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/
	 * commons/lang3/ObjectUtils.java#L597
	 */
	private static <T, E extends Throwable> T getIfNull(final T object, final FailableSupplier<T, E> defaultSupplier)
			throws E {
		return object != null ? object : get(defaultSupplier);
	}

	private static <T, E extends Throwable> T get(final FailableSupplier<T, E> instance) throws E {
		return instance != null ? instance.get() : null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		// Microsoft Excel Format
		//
		add(new JLabel("Workbook Implementation"), String.format(SPAN_ONLY_FORMAT, 5));
		//
		final List<Class<? extends Workbook>> classes = testAndApply(Objects::nonNull,
				new Reflections("org.apache.poi").getSubTypesOf(Workbook.class), ArrayList::new, null);
		//
		if (classes != null) {
			//
			classes.add(0, null);
			//
		} // if
			//
		final JComboBox<Class> jcbClass = new JComboBox<Class>(
				cbmWorkbookClass = new DefaultComboBoxModel<>((Class[]) toArray(classes, new Class[] {})));
		//
		testAndRun(
				Util.contains(Util.keySet(IValue0Util.getValue0(getWorkbookClassFailableSupplierMap())), workbookClass),
				() -> setSelectedItem(cbmWorkbookClass, workbookClass));
		//
		final ListCellRenderer<?> lcr = getRenderer(jcbClass);
		//
		setRenderer(jcbClass, new ListCellRenderer<>() {

			@Override
			public Component getListCellRendererComponent(final JList<? extends Class> list, final Class value,
					final int index, final boolean isSelected, final boolean cellHasFocus) {
				//
				return VoiceManagerExportPanel.getListCellRendererComponent((ListCellRenderer) lcr, list,
						Util.getName(value), index, isSelected, cellHasFocus);
				//
			}

		});
		//
		add(jcbClass, String.format("%1$s,span %2$s", WRAP, 7));
		//
		// Encryption Mode
		//
		add(new JLabel("Encryption Mode"), String.format(SPAN_ONLY_FORMAT, 5));
		//
		final EncryptionMode[] encryptionModes = EncryptionMode.values();
		//
		add(new JComboBox<>(cbmEncryptionMode = new DefaultComboBoxModel<>(
				ArrayUtils.insert(0, encryptionModes, (EncryptionMode) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(cbmEncryptionMode,
				Util.orElse(
						findFirst(Util.filter(Arrays.stream(encryptionModes),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.encryptionMode")))),
						null));
		//
		// ZIP Compression Level
		//
		add(new JLabel("ZIP Compression Level"), String.format(SPAN_ONLY_FORMAT, 5));
		//
		final CompressionLevel[] compressionLevels = CompressionLevel.values();
		//
		add(new JComboBox<>(cbmCompressionLevel = new DefaultComboBoxModel<>(
				ArrayUtils.insert(0, compressionLevels, (CompressionLevel) null))),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		setSelectedItem(cbmCompressionLevel,
				Util.orElse(
						findFirst(Util.filter(Arrays.stream(compressionLevels),
								x -> StringUtils.equalsIgnoreCase(name(x),
										PropertyResolverUtil.getProperty(propertyResolver,
												"org.springframework.context.support.VoiceManager.compressionLevel")))),
						null));
		//
		// Password
		//
		add(new JLabel(PASSWORD), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(tfExportPassword = new JPasswordField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportPassword")),
				String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		add(new JLabel("Option(s)"), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbOverMp3Title = new JCheckBox("Over Mp3 Title"), String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbOverMp3Title.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.overMp3Title")));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbOrdinalPositionAsFileNamePrefix = new JCheckBox("Ordinal Position As File Name Prefix"),
				String.format(SPAN_ONLY_FORMAT, 4));
		//
		cbOrdinalPositionAsFileNamePrefix
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.ordinalPositionAsFileNamePrefix")));
		//
		add(new JLabel("Prefix"));
		//
		add(tfOrdinalPositionFileNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.ordinalPositionFileNamePrefix")),
				String.format("%1$s,%2$s", GROWX, WRAP));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbJlptAsFolder = new JCheckBox("JLPT As Folder"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbJlptAsFolder.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptAsFolder")));
		//
		add(new JLabel("Folder Name Prefix"));
		//
		add(tfJlptFolderNamePrefix = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.jlptFolderNamePrefix")),
				String.format("%1$s,wmin %2$s,span %3$s", WRAP, 100, 2));
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportHtml = new JCheckBox("Export HTML"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbExportHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtml")));
		//
		// File Name
		//
		add(new JLabel("File Name"));
		//
		add(tfExportHtmlFileName = new JTextField(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtmlFileName")),
				String.format("wmin %1$s,span %2$s", 100, 2));
		//
		final String[] fileExtensions = getFileExtensions(ContentType.HTML);
		//
		setToolTipText(
				tfExportHtmlFileName, String
						.format("If the File Name does not ends with %1$s, file extension \".%2$s\" will be appended.",
								Util.collect(
										Util.map(
												testAndApply(Objects::nonNull, fileExtensions, Arrays::stream, null)
														.sorted(),
												x -> StringUtils.wrap(StringUtils.join('.', x), '"')),
										Collectors.joining(" or ")),
								StringUtils.defaultIfBlank(Util.orElse(max(
										fileExtensions != null ? Arrays.stream(fileExtensions) : null,
										(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b))), null),
										"")));
		//
		add(cbExportListHtml = new JCheckBox("Export List"));
		//
		cbExportListHtml.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtml")));
		//
		// cbExportWebSpeechSynthesisHtml
		//
		add(cbExportWebSpeechSynthesisHtml = new JCheckBox("Export Web Speech Synthesis HTML"));
		//
		cbExportWebSpeechSynthesisHtml
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.exportWebSpeechSynthesisHtml")));
		//
		// ZIP
		//
		add(cbExportHtmlAsZip = new JCheckBox("Zip"));
		//
		cbExportHtmlAsZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListHtmlAsZip")));
		//
		add(cbExportHtmlRemoveAfterZip = new JCheckBox("Remove Html After Zip"),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		cbExportHtmlRemoveAfterZip.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportHtmlRemoveAfterZip")));
		//
		// Export List Sheet
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportListSheet = new JCheckBox("Export List Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportListSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportListSheet")));
		//
		// Export JLPT Sheet
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportJlptSheet = new JCheckBox("Export JLPT Sheet"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		cbExportJlptSheet.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportJlptSheet")));
		//
		// Export Presentation
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportPresentation = new JCheckBox("Export Presentation"), String.format(",span %1$s", 3));
		//
		setToolTipText(cbExportPresentation, "Open Document Format (odp) format, Libre Office is recommended");
		//
		cbExportPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportPresentation")));
		//
		add(cbEmbedAudioInPresentation = new JCheckBox("Emded Audio In Presentation"),
				String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbEmbedAudioInPresentation.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.embedAudioInPresentation")));
		//
		add(cbHideAudioImageInPresentation = new JCheckBox("Hide Audio Image In Presentation"),
				String.format(SPAN_ONLY_FORMAT, 2));
		//
		cbHideAudioImageInPresentation
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.hideAudioImageInPresentation")));
		//
		add(new JLabel("Presentation Slide Duration"), String.format(SPAN_ONLY_FORMAT, 2));
		//
		add(tfPresentationSlideDuration = new JTextField(
				StringUtils.defaultString(Util.toString(presentationSlideDuration))),
				String.format("%1$s,wmin %2$spx", WRAP, 100));
		//
		// Export Microsoft Access
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(cbExportMicrosoftAccess = new JCheckBox("Export Microsoft Access"), String.format(SPAN_ONLY_FORMAT, 3));
		//
		cbExportMicrosoftAccess.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
				"org.springframework.context.support.VoiceManager.exportMicrosoftAccess")));
		//
		final Map<?, ?> fileFormatDetails = new LinkedHashMap<>();
		//
		try {
			//
			final FailableConsumer<Map<?, ?>, IllegalAccessException> consumer = x -> putAll((Map) fileFormatDetails,
					x);
			//
			testAndAccept(Objects::nonNull,
					Util.cast(Map.class,
							FieldUtils.readDeclaredStaticField(DatabaseImpl.class, "FILE_FORMAT_DETAILS", true)),
					consumer);
			//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		final FileFormat[] fileFormats = testAndApply(Objects::nonNull, toArray(Util
				.toList(Util.filter(testAndApply(Objects::nonNull, FileFormat.values(), Arrays::stream, null), x -> {
					//
					final FileFormatDetails ffds = Util.cast(FileFormatDetails.class, Util.get(fileFormatDetails, x));
					//
					final JetFormat format = getFormat(ffds);
					//
					return Boolean.logicalAnd(
							Objects.equals(Boolean.FALSE, format != null ? Boolean.valueOf(format.READ_ONLY) : null),
							getEmptyFilePath(ffds) != null);
					//
				})), new FileFormat[] {}), x -> ArrayUtils.addFirst(x, null), null);
		//
		final JComboBox<FileFormat> jcbFileFormat = new JComboBox<>(
				cbmMicrosoftAccessFileFormat = new DefaultComboBoxModel<>(fileFormats));
		//
		final MicrosoftAccessFileFormatListCellRenderer mafflcr = new MicrosoftAccessFileFormatListCellRenderer();
		//
		mafflcr.listCellRenderer = Util.cast(ListCellRenderer.class, getRenderer(jcbFileFormat));
		//
		final Stream<FileFormat> ffs = testAndApply(Objects::nonNull, fileFormats, Arrays::stream, null);
		//
		mafflcr.commonPrefix = Util
				.orElse(reduce(
						Util.filter(Util.map(Util.map(Util.map(ffs, DatabaseImpl::getFileFormatDetails),
								VoiceManagerExportPanel::getFormat), x -> Util.toString(x)), Objects::nonNull),
						StringUtils::getCommonPrefix), null);
		//
		setRenderer(jcbFileFormat, mafflcr);
		//
		add(jcbFileFormat, String.format("%1$s,span %2$s", WRAP, 5));
		//
		cbmMicrosoftAccessFileFormat.setSelectedItem(microsoftAccessFileFormat);
		//
		add(new JLabel(), String.format(SPAN_ONLY_FORMAT, 4));
		//
		add(btnExport = new JButton("Export"), WRAP);
		//
		// Progress
		//
		add(tfPhraseCounter = new JTextField("0"));
		//
		add(new JLabel("/"), "align center");
		//
		add(tfPhraseTotal = new JTextField("0"));
		//
		add(progressBarExport = new JProgressBar(), String.format("%1$s,span %2$s", GROWX, 7));
		//
		progressBarExport.setStringPainted(true);
		//
		addActionListener(this, btnExport);
		//
		setEditable(false, tfPhraseCounter, tfPhraseTotal);
		//
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		throw new UnsupportedOperationException();// TODO
		//
	}

	private static void setEditable(final boolean editable, final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			jtc.setEditable(editable);
			//
		} // for
			//
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
			//
			if ((ab = abs[i]) == null) {
				continue;
			} // if
				//
			ab.addActionListener(actionListener);
			//
		} // for
			//
	}

	private static <T> Optional<T> reduce(final Stream<T> instance, final BinaryOperator<T> accumulator) {
		//
		return instance != null && (accumulator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.reduce(accumulator)
				: null;
		//
	}

	private static String getEmptyFilePath(final FileFormatDetails instance) {
		return instance != null ? instance.getEmptyFilePath() : null;
	}

	private static JetFormat getFormat(final FileFormatDetails instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <K, V> void putAll(final Map<K, V> a, final Map<? extends K, ? extends V> b) {
		if (a != null && b != null) {
			a.putAll(b);
		}
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	private static void setToolTipText(final JComponent instance, final String toolTipText) {
		if (instance != null) {
			instance.setToolTipText(toolTipText);
		}
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(Util.getClass(instance)) || comparator != null)
				? instance.max(comparator)
				: null;
		//
	}

	private static String[] getFileExtensions(final ContentType instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		//
		return instance != null ? instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				: null;
		//
	}

	private static <E> void setRenderer(final JComboBox<E> instance, final ListCellRenderer<? super E> aRenderer) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		try {
			//
			if (Narcissus.invokeMethod(instance, Component.class.getDeclaredMethod("getObjectLock")) == null) {
				//
				return;
				//
			} // if
				//
		} catch (final NoSuchMethodException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		instance.setRenderer(aRenderer);
		//
	}

	private static <E> ListCellRenderer<? super E> getRenderer(final JComboBox<E> instance) {
		return instance != null ? instance.getRenderer() : null;
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) {
		if (instance != null) {
			instance.setSelectedItem(selectedItem);
		}
	}

	private IValue0<Map<Class<? extends Workbook>, FailableSupplier<Workbook, RuntimeException>>> getWorkbookClassFailableSupplierMap() {
		//
		if (workbookClassFailableSupplierMap == null) {
			//
			workbookClassFailableSupplierMap = Unit.with(Util.collect(
					Util.stream(new Reflections("org.apache.poi").getSubTypesOf(Workbook.class)),
					Collectors.toMap(Functions.identity(), x -> new FailableSupplier<Workbook, RuntimeException>() {

						@Override
						public Workbook get() throws RuntimeException {
							try {
								//
								return Util.cast(Workbook.class, newInstance(getDeclaredConstructor(x)));
								//
							} catch (final NoSuchMethodException | InstantiationException | IllegalAccessException
									| InvocationTargetException e) {
								//
								throw ObjectUtils.getIfNull(toRuntimeException(e), RuntimeException::new);
								//
							} // try
						}

					})));
			//
		} // if
			//
		return workbookClassFailableSupplierMap;
		//
	}

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> runnable) throws E {
		//
		if (b && runnable != null) {
			//
			runnable.run();
			//
		} // if
			//
	}

	private static RuntimeException toRuntimeException(final Throwable instance) {
		//
		if (instance instanceof RuntimeException re) {
			//
			return re;
			//
		} else if (instance instanceof Throwable) {
			//
			return new RuntimeException(instance);
			//
		} // if
			//
		return null;
		//
	}

	private static <T> T newInstance(final Constructor<T> constructor, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return constructor != null ? constructor.newInstance(initargs) : null;
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> clz, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) {
		//
		return instance != null && (array != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(array)
				: null;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}