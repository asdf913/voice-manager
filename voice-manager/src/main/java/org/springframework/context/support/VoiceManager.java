package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import domain.Voice;
import fr.free.nrw.jakaroma.Jakaroma;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

public class VoiceManager extends JFrame implements ActionListener, EnvironmentAware {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static Pattern PATTERN_CONTENT_INFO_MP3 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final String WRAP = "wrap";

	private PropertyResolver propertyResolver = null;

	private JTextComponent tfFile, tfFileLength, tfFileDigest, tfText, tfRomaji = null;

	private AbstractButton btnConvertToRomaji, btnCopyRomaji, btnExecute = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setSqlSessionFactory(final SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	private void init() {
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.text")));
		//
		add(btnConvertToRomaji = new JButton("Convert"), WRAP);
		//
		add(new JLabel("Romaji"));
		//
		add(tfRomaji = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.romaji")));
		//
		add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"), WRAP);
		//
		final String wrap = String.format("span %1$s,growx,%2$s", 2, WRAP);
		//
		add(new JLabel("File"));
		//
		add(tfFile = new JTextField(), wrap);
		//
		add(new JLabel("File Length"));
		//
		add(tfFileLength = new JTextField(), wrap);
		//
		add(new JLabel("File Digest"));
		//
		add(tfFileDigest = new JTextField(), wrap);
		//
		setEditable(false, tfFile, tfFileLength, tfFileDigest);
		//
		addActionListener(this, btnExecute, btnConvertToRomaji, btnCopyRomaji);
		//
		setPreferredWidth(165 - intValue(getPreferredWidth(btnConvertToRomaji), 0), tfText, tfRomaji);
		//
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

	private static String getProperty(final PropertyResolver instance, final String key) {
		return instance != null ? instance.getProperty(key) : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			setText(null, tfFile, tfFileLength, tfFileDigest);
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final File selectedFile = jfc.getSelectedFile();
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
					SqlSession sqlSession = null;
					//
					try {
						//
						final String fileExtension = getFileExtension(new ContentInfoUtil().findMatch(selectedFile));
						//
						if (fileExtension == null) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is null");
							//
							return;
							//
						} else if (StringUtils.isEmpty(fileExtension)) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is Empty");
							//
							return;
							//
						} else if (StringUtils.isBlank(fileExtension)) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is Blank");
							//
							return;
							//
						} // if
							//
						final String filePath = selectedFile != null ? selectedFile.getAbsolutePath() : null;
						//
						setText(tfFile, StringUtils.defaultString(filePath, getText(tfFile)));
						//
						final Long length = selectedFile != null ? Long.valueOf(selectedFile.length()) : null;
						//
						setText(tfFileLength, length != null ? length.toString() : null);
						//
						final MessageDigest md = MessageDigest.getInstance("SHA-512");
						//
						String fileDigest = null;
						//
						if (md != null) {
							//
							setText(tfFileDigest, fileDigest = Hex
									.encodeHexString(md.digest(FileUtils.readFileToByteArray(selectedFile))));
							//
						} // if
							//
						final Voice voice = new Voice();
						//
						final String text = getText(tfText);
						//
						voice.setText(text);
						//
						final String romaji = getText(tfRomaji);
						//
						voice.setRomaji(romaji);
						//
						voice.setFilePath(filePath);
						//
						voice.setFileDigestAlgorithm(md != null ? md.getAlgorithm() : null);
						//
						voice.setFileDigest(fileDigest);
						//
						voice.setFileExtension(fileExtension);
						//
						voice.setFileLength(length);
						//
						final Configuration configuration = sqlSessionFactory != null
								? sqlSessionFactory.getConfiguration()
								: null;
						//
						final VoiceMapper voiceMapper = configuration != null
								? configuration.getMapper(VoiceMapper.class,
										sqlSession = sqlSessionFactory != null ? sqlSessionFactory.openSession() : null)
								: null;
						//
						if (voiceMapper != null) {
							//
							if (voiceMapper.exists(text, romaji)) {
								//
								voice.setUpdateTs(new Date());
								//
								voiceMapper.update(voice);
								//
							} else {
								//
								voice.setCreateTs(new Date());
								//
								voiceMapper.insert(voice);
								//
							} // if
								//
						} // if
							//
					} catch (IOException | NoSuchAlgorithmException e) {
						//
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null) {
								LOG.error(e.getMessage(), e);
							} else {
								e.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, e.getMessage());
							//
						} // if
							//
					} finally {
						//
						IOUtils.closeQuietly(sqlSession);
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
			setText(tfRomaji, new Jakaroma().convert(getText(tfText), false, false));
			//
		} else if (Objects.equals(source, btnCopyRomaji)) {
			//
			setContents(getSystemClipboard(Toolkit.getDefaultToolkit()), new StringSelection(getText(tfRomaji)), null);
			//
		} // if
			//
	}

	private static String getFileExtension(final ContentInfo ci) {
		//
		if (ci == null) {
			//
			return null;
			//
		} // if
			//
		final String messgae = ci.getMessage();
		//
		if (matches(PATTERN_CONTENT_INFO_MP3 != null && messgae != null ? PATTERN_CONTENT_INFO_MP3.matcher(messgae)
				: null)) {
			//
			return "mp3";
			//
		} // if
			//
		final String name = ci.getName();
		//
		if (Objects.equals(name, "wav")) {
			//
			return name;
			//
		} else if (Objects.equals(ci.getMimeType(), "audio/x-hx-aac-adts")) {
			//
			return "aac";
			//
		} // if
			//
		return null;
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static void setText(final String text, final JTextComponent... jtcs) {
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			setText(jtcs[i], text);
			//
		} // for
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