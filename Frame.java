import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//////////////
// MY IMPORT
import Parser;

public class MainFrame {
	private JFrame m_window;
	private JFileChooser m_chooser;
	private File m_file;
	private JButton m_openButton;
	private JButton m_convertButton;
	private JLable m_filename;
	
	private Parser m_parser;
	
	public void MainFrame() {
		m_window = 0;
		m_file = 0;
		m_chooser = 0;
		m_openButton = 0;
		m_convertButton = 0;
		
		m_parser = 0;
	}
	
	public void Init() {
		m_window = new JFrame("obj2F");
		m_openButton = new JButton("find");
		m_chooser = new JFileChooser();
		m_convertButton = new JButton("convert");
		m_filename = new JLabel("");
		
		m_parser =  new Parser();
		
		// Add component to frame.
		m_window.add(m_filename);
		m_window.add(m_openButton);
		m_window.add(m_convertButton);
		
		// Set size of frame.
		m_window.setSize(500, 500);
		
		// Show frae.
		m_window.setVisible(true);
		
		// Register event listener.
		m_openButton.addActionListener(this);
		m_convertButton.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// action listener redefine.
		if (e.getSource().equals(m_openButton)) {
			// if user selected file and press the button "ok".
			if (m_chooser.showOpenDialog(m_window) == JFileChooser.APPROVE_OPTION) {
				m_file = m_chooser.getSelectedFile();
				
				m_filename.setText(m_file.toString());
			}
		} else if (e.getSource().equals(m_convertButton)) {
			m_parser.extract(m_file.toString());
			
			m_parser.export();
		}
	}
	
	public static void main(String args[]) {
		MainFrame f = new MainFrame();
		
		f.Init();
	}
}