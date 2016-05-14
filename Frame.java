package obj2F;
import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

//////////////
// MY IMPORT

public class Frame implements ActionListener {
	private JFrame m_window;
	private JFileChooser m_chooser;
	private File m_file;
	private JButton m_openButton;
	private JButton m_convertButton;
	private JLabel m_filename;
	
	private Parser m_parser;
	
	// 아무것도 하지 않는다.
	public Frame() {
		
	}
	
	public void Init() {
		m_window = new JFrame("obj2F");
		m_openButton = new JButton("find");
		m_chooser = new JFileChooser();
		m_convertButton = new JButton("convert");
		m_filename = new JLabel("");
		
		m_parser =  new Parser();
		
		
		// 레이아웃 설정
		m_window.setLayout(new FlowLayout());
		
		// change size of component before add them to frame.
		m_openButton.setBounds(10, 10, 50, 50);
		m_convertButton.setBounds(70, 10, 50, 50);
		m_filename.setBounds(10, 70, 100, 50);
		
		// Add component to frame.
		m_window.add(m_filename);
		m_window.add(m_openButton);
		m_window.add(m_convertButton);
		
		// Set size of frame.
		m_window.setSize(500, 500);
		
		// Show frame.
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
			m_parser.convert(m_file.toString());
	
		}
	}
	
	public static void main(String args[]) {
		Frame f = new Frame();
		
		f.Init();
	}
}