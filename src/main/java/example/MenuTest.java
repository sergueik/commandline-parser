package example;

// http://www.java2s.com/example/java-src/no-pkg/798/messagedigesttest-4d065.html
/*
This program is a part of the companion code for Core Java 8th ed.
(http://horstmann.com/corejava)
 
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MenuTest extends JFrame {
	public static void main(String[] args) {
		try {
			// https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html#programmatic
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MenuTestFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
* This frame contains a menu for computing the message digest of a file or text area, radio buttons
* to toggle between SHA-1 and MD5, a text area, and a text field to show the messge digest.
*/
@SuppressWarnings("serial")
class MenuTestFrame extends JFrame {
	public MenuTestFrame() {
		setTitle("MenuTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		addRadioButton(panel, "SHA-1", group);
		addRadioButton(panel, "MD5", group);

		add(panel, BorderLayout.NORTH);
		add(new JScrollPane(message), BorderLayout.CENTER);
		add(digest, BorderLayout.SOUTH);
		digest.setFont(new Font("Monospaced", Font.PLAIN, 12));

		setAlgorithm("SHA-1");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new EmptyBorder(1, 1, 1, 1));
		JMenu menu = new JMenu("File");
		// menu.setBorderPainted(false);
		menu.setBorder(new EmptyBorder(1, 1, 1, 1));
		JMenuItem fileDigestItem = new JMenuItem("File digest");
		// fileDigestItem.getBorder().paintBorder(arg0, arg1, arg2, arg3, arg4,
		// arg5);
		// fileDigestItem.setBorderPainted(false);
		fileDigestItem.setBorder(new EmptyBorder(1, 1, 1, 1));
		fileDigestItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				loadFile();
			}
		});
		menu.add(fileDigestItem);
		JMenuItem textDigestItem = new JMenuItem("Text area digest");
		textDigestItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String m = message.getText();
				computeDigest(m.getBytes());
			}
		});
		menu.add(textDigestItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	/**
	* Adds a radio button to select an algorithm.
	* @param c the container into which to place the button
	* @param name the algorithm name
	* @param g the button group
	*/
	public void addRadioButton(Container c, final String name, ButtonGroup g) {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setAlgorithm(name);
			}
		};
		JRadioButton b = new JRadioButton(name, g.getButtonCount() == 0);
		c.add(b);
		g.add(b);
		b.addActionListener(listener);
	}

	/**
	* Sets the algorithm used for computing the digest.
	* @param alg the algorithm name
	*/
	public void setAlgorithm(String alg) {
		try {
			currentAlgorithm = MessageDigest.getInstance(alg);
			digest.setText("");
		} catch (NoSuchAlgorithmException e) {
			digest.setText("" + e);
		}
	}

	/**
	* Loads a file and computes its message digest.
	*/
	public void loadFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		int r = chooser.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			try {
				String name = chooser.getSelectedFile().getAbsolutePath();
				computeDigest(loadBytes(name));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	/**
	* Loads the bytes in a file.
	* @param name the file name
	* @return an array with the bytes in the file
	*/
	public byte[] loadBytes(String name) throws IOException {
		FileInputStream in = null;

		in = new FileInputStream(name);
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1)
				buffer.write(ch);
			return buffer.toByteArray();
		} finally {
			in.close();
		}
	}

	/**
	* Computes the message digest of an array of bytes and displays it in the text field.
	* @param b the bytes for which the message digest should be computed.
	*/
	public void computeDigest(byte[] b) {
		currentAlgorithm.reset();
		currentAlgorithm.update(b);
		byte[] hash = currentAlgorithm.digest();
		String d = "";
		for (int i = 0; i < hash.length; i++) {
			int v = hash[i] & 0xFF;
			if (v < 16)
				d += "0";
			d += Integer.toString(v, 16).toUpperCase() + " ";
		}
		digest.setText(d);
	}

	private JTextArea message = new JTextArea();
	private JTextField digest = new JTextField();
	private MessageDigest currentAlgorithm;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 300;
}
