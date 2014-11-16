package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PrevTicketWindow {
	private JFrame prevTick;
	private JButton submit;

	public PrevTicketWindow(ArrayList<String[]> n){
		int len = n.size();
		prevTick = new JFrame();
		prevTick.setSize(300,(50*len)+20);
		prevTick.setLocation(300,30);
		prevTick.setLayout(new GridLayout(len+1,1));
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		for(int i=0; i<len; i++){
			JPanel bPanel = new JPanel(new GridLayout(1,4));
			String vals[] = n.get(i);
			JLabel labR = new JLabel(Integer.toString(i+1),SwingConstants.CENTER);
			labR.setBorder(border);
			JLabel labX = new JLabel(vals[0],SwingConstants.CENTER);
			labX.setBorder(border);
			JLabel labY = new JLabel(vals[1],SwingConstants.CENTER);
			labY.setBorder(border);
			JLabel labZ = new JLabel(vals[2],SwingConstants.CENTER);
			labZ.setBorder(border);
			bPanel.add(labR);
			bPanel.add(labX);
			bPanel.add(labY);
			bPanel.add(labZ);
			prevTick.add(bPanel);
		}
		JPanel submitP = new JPanel();
		submit = new JButton("Back");
		submitP.add(submit);
		prevTick.add(submitP);
		
		prevTick.setVisible(true);
	}
	
	public void close(){
		prevTick.setVisible(false);
	}
	
	public void addSubmitListener(ActionListener al){
    	submit.addActionListener(al);
    }
}
