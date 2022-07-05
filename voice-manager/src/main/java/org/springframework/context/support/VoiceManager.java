package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import fr.free.nrw.jakaroma.Jakaroma;
import net.miginfocom.swing.MigLayout;

public class VoiceManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static Pattern PATTERN_CONTENT_INFO_MP3 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final String WRAP = "wrap";

	private JTextComponent tfFile, tfText, tfRomaji = null;

	private AbstractButton btnConvertToRomaji, btnCopyRomaji, btnExecute = null;

	private VoiceManager() {
	}

	private void init() {
		//
		add(new JLabel("File"));
		//
		add(tfFile = new JTextField(), String.format("span %1$s,growx,%2$s", 2, WRAP));
		//
		tfFile.setEditable(false);
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField());
		//
		add(btnConvertToRomaji = new JButton("Convert"), WRAP);
		//
		add(new JLabel("Romaji"));
		//
		add(tfRomaji = new JTextField());
		//
		add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"));
		//
		addActionListener(this, btnExecute, btnConvertToRomaji, btnCopyRomaji);
		//
		setPreferredWidth(165 - intValue(getPreferredWidth(btnConvertToRomaji), 0), tfText, tfRomaji);
		//
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
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
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			final JFileChooser jfc = new JFileChooser();
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final File selectedFile = jfc.getSelectedFile();
				//
				tfFile.setText(StringUtils.defaultString(selectedFile != null ? selectedFile.getAbsolutePath() : null,
						getText(tfFile)));
				//
				if (selectedFile == null) {
					//
					JOptionPane.showMessageDialog(null, "No File Selected");
					//
				} else if (!selectedFile.exists()) {
					//
					JOptionPane.showMessageDialog(null,
							String.format("File \"%1$s\" does not exist", selectedFile.getAbsolutePath()));
					//
				} else if (!selectedFile.isFile()) {
					//
					JOptionPane.showMessageDialog(null, "Not A Regular File Selected");
					//
				} else if (selectedFile.length() == 0) {
					//
					JOptionPane.showMessageDialog(null, "Empty File Selected");
					//
				} else {
					//
					ContentInfo ci = null;
					//
					try {
						//
						if ((ci = new ContentInfoUtil().findMatch(selectedFile)) == null) {
							//
							JOptionPane.showMessageDialog(null, "com.j256.simplemagic.ContentInfo is null");
							//
							return;
							//
						} else {
							//
							if (!(matches(
									PATTERN_CONTENT_INFO_MP3 != null ? PATTERN_CONTENT_INFO_MP3.matcher(ci.getMessage())
											: null)// mp3
									|| Objects.equals(ci.getName(), "wav")// wav
									|| Objects.equals(ci.getMimeType(), "audio/x-hx-aac-adts")// aac
							)) {
								//
								JOptionPane.showMessageDialog(null, String.format("File \"%1$s\" is not an audio File",
										selectedFile.getAbsolutePath()));
								//
							} // if
								//
						} // if
							//
					} catch (IOException e) {
						//
						if (LOG != null) {
							LOG.error(e.getMessage(), e);
						} else {
							e.printStackTrace();
						} // if
							//
					} // try
						//
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, "No File Selected");
				//
			} // if
				//
		} else if (Objects.equals(source, btnConvertToRomaji)) {
			//
			if (tfRomaji != null) {
				//
				tfRomaji.setText(new Jakaroma().convert(getText(tfText), false, false));
				//
			} // if
				//
		} else if (Objects.equals(source, btnCopyRomaji)) {
			//
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			//
			final Clipboard clipboard = toolkit != null ? toolkit.getSystemClipboard() : null;
			//
			if (clipboard != null) {
				//
				clipboard.setContents(new StringSelection(getText(tfRomaji)), null);
				//
			} // if
				//
		} // if
			//
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void setPreferredWidth(final int width, final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = c.getPreferredSize()) == null) {
				continue;
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = c != null ? c.getPreferredSize() : null;
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	public static void main(final String[] args) {
		//
		final VoiceManager instance = new VoiceManager();
		//
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		instance.setLayout(new MigLayout());
		//
		instance.setTitle("Voice Manager");
		//
		instance.init();
		//
		instance.pack();
		//
		instance.setVisible(true);
		//
	}

}