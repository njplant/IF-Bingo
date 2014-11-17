package View;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpWindowGame {
	private JPanel panel;
	private JLabel label;
	private ImageIcon icon;
	
	public HelpWindowGame(){
		JFrame frame = new JFrame();
		frame.setSize(565,380);
		frame.setLocation(300,30);
		
		panel = new JPanel();
		label = new JLabel();
		icon = new ImageIcon(getClass().getResource("HelpGame.png"));
		label.setIcon(icon);
		panel.add(label);
		frame.add(panel);
		
		frame.setVisible(true);
	}
}