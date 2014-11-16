package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class NewGameWindow {
	private JFrame tick;
	private JComboBox tickList;
	private JTextField nameField;
	public JRadioButton radioBasic;
	public JRadioButton radioAdvanced;
	public JRadioButton radioPython;
	public JRadioButton radioJava;
	private JButton cancel;
	private JButton submit;
	
	public NewGameWindow(){
		tick = new JFrame();
		tick.setSize(400,170);
		tick.setLocation(300,30);
		tick.setLayout(new GridLayout(5,1));
		
		//Select PDF Name
		JPanel namePanel = new JPanel(new GridLayout(1,2));
		JLabel nameLabel = new JLabel("Save PDF document as:");
		namePanel.add(nameLabel);
		nameField = new JTextField(".pdf");
		namePanel.add(nameField);
		tick.add(namePanel);
		
		//Select Number of Tickets
		JPanel numPanel = new JPanel(new GridLayout(1,2));
		JLabel numLabel = new JLabel("Select number of tickets:");
		numPanel.add(numLabel);
		String nums[] = new String[26];
		int count=0;
		for(int i=5; i<=30; i++){
			nums[count] = Integer.toString(i);
			count++;
		}
		tickList = new JComboBox(nums);
		tickList.setSelectedIndex(5);
		numPanel.add(tickList);
		tick.add(numPanel);
		
		//Select Difficulty
		JPanel diffPanel = new JPanel(new GridLayout(1,3));
		JLabel diffLabel = new JLabel("Select difficulty:");
		diffPanel.add(diffLabel);
		radioBasic = new JRadioButton("Basic", true);
		diffPanel.add(radioBasic);
		radioAdvanced = new JRadioButton("Advanced");
		diffPanel.add(radioAdvanced);
		tick.add(diffPanel);
		
		//Select Language
		JPanel langPanel = new JPanel(new GridLayout(1,3));
		JLabel langLabel = new JLabel("Select language:");
		langPanel.add(langLabel);
		radioPython = new JRadioButton("Python", true);
		langPanel.add(radioPython);
		radioJava = new JRadioButton("Java");
		langPanel.add(radioJava);
		tick.add(langPanel);
		
		JPanel bPanel = new JPanel(new GridLayout(1,2));
		JPanel cancelP = new JPanel();
		JPanel submitP = new JPanel();
		cancel = new JButton("Cancel");
		submit = new JButton("Start");
		cancelP.add(cancel);
		submitP.add(submit);
		bPanel.add(cancelP);
		bPanel.add(submitP);
		
		tick.add(bPanel);
		
		tick.setVisible(true);
	}
	
	public int getTick(){
		String n = (String)tickList.getSelectedItem();
		int num = Integer.parseInt(n);
		return num;
	}
	
	public String getPDFName(){
		return nameField.getText();
	}
	
	public void closeWindow(){
		tick.setVisible(false);
	}
	
	public void addPDFNameListener(ActionListener al){
    	nameField.addActionListener(al);
    }
	
	public void addBasicListener(ActionListener al){
		radioBasic.addActionListener(al);
	}
	
	public void addAdvancedListener(ActionListener al){
		radioAdvanced.addActionListener(al);
	}
	
	public void addPythonListener(ActionListener al){
		radioPython.addActionListener(al);
	}
	
	public void addJavaListener(ActionListener al){
		radioJava.addActionListener(al);
	}
	
	public void addSubmitListener(ActionListener al){
    	submit.addActionListener(al);
    }
	
	public void addCancelListener(ActionListener al){
    	cancel.addActionListener(al);
    }
}
