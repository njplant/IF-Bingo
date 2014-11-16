package View;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutWindow {
	private JPanel panel;
	private JLabel label;
	private ImageIcon icon;
	
	public AboutWindow(){
		JFrame frame = new JFrame();
		frame.setSize(225,170);
		frame.setLocation(400,120);
		
		panel = new JPanel();
		label = new JLabel();
		icon = new ImageIcon(getClass().getResource("AboutBingo.png"));
		label.setIcon(icon);
		panel.add(label);
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
