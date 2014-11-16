package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StartMenu extends JFrame{

	private JMenuItem itemStart;
	private JMenuItem itemSave;
	private JMenuItem itemLoad;
	private JMenuItem itemExit;
	private JMenuItem itemHelp;
	private JMenuItem itemAbout;
	private ImageIcon cover;
	private JFrame tick;
	private JTextField nameField;
	private JButton cancel;
	private JButton submit;
	
	public StartMenu(){
		super.setSize(540,420);
        super.setResizable(false);
        super.setLocation(300,30);
        super.setTitle("If Bingo!");

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar mb = new JMenuBar();
        
        JMenu menu = new JMenu("File");
        itemStart = new JMenuItem("New Game");
        itemSave = new JMenuItem("Save Game");
        itemSave.setEnabled(false);
        itemLoad = new JMenuItem("Load Game");
        itemExit = new JMenuItem("Exit");
        menu.add(itemStart);
        menu.add(itemSave);
        menu.add(itemLoad);
        menu.add(itemExit);
        mb.add(menu);
        
        JMenu mHelp = new JMenu("Help");
        itemHelp = new JMenuItem("How to start playing?");
        mHelp.add(itemHelp);
        mb.add(mHelp);
        super.setJMenuBar(mb);
        
        JMenu mAbout = new JMenu("About");
        itemAbout = new JMenuItem("About If Bingo");
        mAbout.add(itemAbout);
        mb.add(mAbout);
        
        super.setJMenuBar(mb);
        
        cover = new ImageIcon(getClass().getResource("cover2.png"));
        JLabel jCover = new JLabel();
        jCover.setIcon(cover);
        
        super.add(jCover);
				
		super.setVisible(true); 
	}
	
	public void closeTick(boolean b){
		tick.setVisible(false);
		super.setVisible(b);
	}
	
	public void showWindow(boolean b){
		super.setVisible(b);
	}
	
	public void setLoad(boolean b){
		itemLoad.setEnabled(b);
	}
	
	public void addStartListener(ActionListener al){
    	itemStart.addActionListener(al);
    }
	
	public void addSaveListener(ActionListener al){
    	itemSave.addActionListener(al);
    }
	
	public void addLoadListener(ActionListener al){
    	itemLoad.addActionListener(al);
    }
	
	public void addExitListener(ActionListener al){
    	itemExit.addActionListener(al);
    }
	
	public void addHelpListener(ActionListener al){
    	itemHelp.addActionListener(al);
    }
	
	public void addAboutListener(ActionListener al){
    	itemAbout.addActionListener(al);
    }
	
	public void addPDFNameListener(ActionListener al){
    	nameField.addActionListener(al);
    }
	
	public void addSubmitListener(ActionListener al){
    	submit.addActionListener(al);
    }
	
	public void addCancelListener(ActionListener al){
    	cancel.addActionListener(al);
    }
}