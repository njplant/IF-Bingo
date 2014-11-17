package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.TicketSet;



public class LoadWindow {
	private JFrame loadGame;
	private JComboBox loadGameList;
	private ArrayList<String> names;
	private JButton cancel;
	private JButton submit;

	public LoadWindow(ArrayList<TicketSet> n){
		loadGame = new JFrame();
		loadGame.setSize(300,150);
		loadGame.setLocation(300,30);
		loadGame.setLayout(new GridLayout(2,1));
		
		names = new ArrayList<String>(n.size());
		
		String nums[] = new String[n.size()];
		for(int i=0; i<nums.length; i++){
			nums[i] = n.get(i).setName + " || " + n.get(i).ticketNum + " tickets";
			names.add(n.get(i).setName);
		}
		loadGameList = new JComboBox(nums);
		loadGame.add(loadGameList);
		
		JPanel bPanel = new JPanel(new GridLayout(1,2));
		JPanel cancelP = new JPanel();
		JPanel submitP = new JPanel();
		cancel = new JButton("Cancel");
		submit = new JButton("Load");
		cancelP.add(cancel);
		submitP.add(submit);
		bPanel.add(cancelP);
		bPanel.add(submitP);
		
		loadGame.add(bPanel);
		
		loadGame.setVisible(true);
	}
	
	public String getLoadTick(){
		//String n = (String)loadGameList.getSelectedItem();
		int index = loadGameList.getSelectedIndex();
		return names.get(index);
	}
	
	public void closeLoad(){
		loadGame.setVisible(false);
	}
	
	public void addSubmitLoadListener(ActionListener al){
    	submit.addActionListener(al);
    }
	
	public void addCancelLoadListener(ActionListener al){
    	cancel.addActionListener(al);
    }
}
