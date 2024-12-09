package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import net.miginfocom.swing.MigLayout;

public class VoiceManagerImportBatchPanel extends JPanel
		implements Titled, InitializingBean, EnvironmentAware, ActionListener {

	private static final long serialVersionUID = 8152096292066653831L;

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

	private static final String ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER = "Romaji";

	private AbstractButton btnImport, btnImportWithinFolder, cbImportFileTemplateGenerateBlankRow,
			btnImportFileTemplate, btnExecute = null;

	private JProgressBar progressBarImport = null;

	private JTextComponent tfCurrentProcessingFile, tfCurrentProcessingSheetName, tfCurrentProcessingVoice = null;

	private DefaultTableModel tmImportResult, tmImportException = null;

	private PropertyResolver propertyResolver = null;

	private String voiceFolder = null;

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	@Override
	public String getTitle() {
		return "Import(Batch)";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(new MigLayout());
		//
		add(new JLabel("Import"));
		//
		add(btnImport = new JButton("Import a Single Spreadsheet"), String.format(SPAN_ONLY_FORMAT, 2));
		//
		add(btnImportWithinFolder = new JButton("Import SpreadSheet(s) Within a Folder"),
				String.format("%1$s,span %2$s", WRAP, 2));
		//
		add(new JLabel("Import Template"));
		//
		add(cbImportFileTemplateGenerateBlankRow = new JCheckBox("Generate a Blank Row"));
		//
		cbImportFileTemplateGenerateBlankRow
				.setSelected(Boolean.parseBoolean(PropertyResolverUtil.getProperty(propertyResolver,
						"org.springframework.context.support.VoiceManager.importFileTemplateGenerateBlankRow")));
		//
		add(btnImportFileTemplate = new JButton("Generate"), String.format("%1$s,span %2$s", WRAP, 3));
		//
		// Progress
		//
		add(progressBarImport = new JProgressBar(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5));
		//
		progressBarImport.setStringPainted(true);
		//
		add(new JLabel("Current Processing File"));
		//
		final String wrap = String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 5);
		//
		add(tfCurrentProcessingFile = new JTextField(), wrap);
		//
		add(new JLabel("Current Processing Sheet"));
		//
		add(tfCurrentProcessingSheetName = new JTextField(), String.format("%1$s,wmin %2$s,span %3$s", GROWX, 300, 2));
		//
		add(new JLabel("Voice"));
		//
		add(tfCurrentProcessingVoice = new JTextField(), String.format("%1$s,%2$s,span %3$s", GROWX, WRAP, 2));
		//
		add(new JLabel("Import Result"));
		//
		JScrollPane scp = new JScrollPane(new JTable(tmImportResult = new DefaultTableModel(
				new Object[] { "Number Of Sheet Processed", "Number of Voice Processed" }, 0)));
		//
		Dimension d = Util.getPreferredSize(scp);
		//
		if (d != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 40));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		add(scp, wrap);
		//
		add(new JLabel("Import Exception"));
		//
		add(scp = new JScrollPane(new JTable(tmImportException = new DefaultTableModel(
				new Object[] { "Text", ROMAJI_WITH_FIRST_CAPTICALIZED_LETTER, "Exception" }, 0))), wrap);
		//
		if ((d = Util.getPreferredSize(scp)) != null) {
			//
			scp.setMinimumSize(d = new Dimension((int) d.getWidth(), 55));
			//
			scp.setPreferredSize(d);
			//
		} // if
			//
		addActionListener(this, btnImport, btnImportWithinFolder, btnImportFileTemplate);
		//
		setEditable(false, tfCurrentProcessingFile, tfCurrentProcessingSheetName, tfCurrentProcessingVoice);
		//
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		final boolean b = folder != null && folder.exists() && folder.isDirectory();
		//
		setEnabled(btnExecute, b);
		//
		if (!b) {
			//
			setToolTipText(btnExecute, String.format("Please create \"%1$s\" folder.", Util.getAbsolutePath(folder)));
			//
		} // if
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static void setToolTipText(final JComponent instance, final String toolTipText) {
		if (instance != null) {
			instance.setToolTipText(toolTipText);
		}
	}

	private static void setEnabled(final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	private static void setEditable(final boolean editable, final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
				continue;
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

	@Override
	public void actionPerformed(final ActionEvent evt) {
		// TODO
	}

}