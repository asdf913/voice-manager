import java.awt.Component;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

public class VoiceManager extends JFrame {

	private static final long serialVersionUID = 6093437131552718994L;

	private static final String WRAP = "wrap";

	private JTextComponent tfFile, tfText, tfRomaji = null;

	private AbstractButton btnExecute = null;

	private VoiceManager() {
	}

	private void init() {
		//
		add(new JLabel("File"));
		//
		add(tfFile = new JTextField(), WRAP);
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(), WRAP);
		//
		add(new JLabel("Romaji"));
		//
		add(tfRomaji = new JTextField(), WRAP);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"));
		//
		setPreferredWidth(97, tfFile, tfText, tfRomaji);
		//
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