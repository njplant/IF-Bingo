package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameWindow extends JFrame{
	private int tNum;
	private JMenuItem itemStart;
	private JMenuItem itemSave;
	private JMenuItem itemLoad;
	private JMenuItem itemExit;
	private JMenuItem itemHelp;
	private JPanel panelMain;
	private JPanel panel1;
	private JButton nextCard;
	private JButton prevCard;
	private JButton checkT;
	private JPanel panel2;
	private JLabel labelX;
	private JLabel labelY;
	private JLabel labelZ;
	private JLabel labelInfo;
	private Font large = new Font("Serif", Font.BOLD, 36);
	
	public GameWindow(int ticketNum){
		tNum = ticketNum;
		super.setSize(560,300);
        super.setResizable(false);
        super.setLocation(300,30);
        super.setTitle("If Bingo Game!");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar mb = new JMenuBar();
        
        JMenu mFile = new JMenu("File");
        itemStart = new JMenuItem("New Game");
        itemSave = new JMenuItem("Save Game");
        itemLoad = new JMenuItem("Load Game");
        itemExit = new JMenuItem("Exit");
        mFile.add(itemStart);
        mFile.add(itemSave);
        mFile.add(itemLoad);
        mFile.add(itemExit);
        mb.add(mFile);
        
        JMenu mHelp = new JMenu("Help");
        itemHelp = new JMenuItem("What should I do now?");
        mHelp.add(itemHelp);
        mb.add(mHelp);
        
        super.setJMenuBar(mb);
        
        panelMain = new JPanel(new BorderLayout());
        
        labelInfo = new JLabel("Round #" + "  ||  Number of Tickets: " +tNum);
        panelMain.add(labelInfo, BorderLayout.PAGE_START);
        
		panel2 = new ImagePanel(new ImageIcon(getClass().getResource("GWHelp.png")).getImage());
		panel2.setLayout(new GridLayout(1,3));
		
		labelX = new JLabel();
		labelX.setFont(large);
		labelX.setHorizontalAlignment(JLabel.CENTER);
		labelY = new JLabel();
		labelY.setFont(large);
		labelY.setHorizontalAlignment(JLabel.CENTER);
		labelZ = new JLabel();
		labelZ.setFont(large);
		labelZ.setHorizontalAlignment(JLabel.CENTER);
		panel2.add(labelX);
		panel2.add(labelY);
		panel2.add(labelZ);
		panelMain.add(panel2, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(new GridLayout(1,3));
		nextCard = new JButton("Next Card");
		prevCard = new JButton("Previous Cards");
        checkT = new JButton("Check Tickets");
		panel1.add(checkT);
		panel1.add(prevCard);
		panel1.add(nextCard);
		panelMain.add(panel1, BorderLayout.PAGE_END);
		
		System.out.println("View Game Window");
		
		super.add(panelMain);
		super.setVisible(true); 
	}
	
	public void showWindow(boolean b){
		super.setVisible(b);
	}
	
	public void setXLabel(String text){
		labelX.setText(text);
	}
	
	public void setYLabel(String text){
		labelY.setText(text);
	}
	
	public void setZLabel(String text){
		labelZ.setText(text);
	}
	
	public void setRound(int round){
		labelInfo.setText("Round #"+round + "  ||  Number of Tickets: " + tNum);
	}
	
	public void changeBackground(){
		((ImagePanel) panel2).setImage(new ImageIcon(getClass().getResource("GW.png")).getImage());
	}
	
	public void showPrevCard(boolean b){
		prevCard.setEnabled(b);
	}
	
	public void addCardListener(ActionListener al){
    	nextCard.addActionListener(al);
    }
	
	public void addPrevCardListener(ActionListener al){
    	prevCard.addActionListener(al);
    }
	
	public void addcTicketListener(ActionListener al){
	    checkT.addActionListener(al);
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
	
	
	
	class ImagePanel extends JPanel {
		private Image i;

		public ImagePanel(String img) {
		    this(new ImageIcon(img).getImage());
		}

		public ImagePanel(Image img) {
		    i = img;
		    Dimension size = new Dimension(i.getWidth(null), i.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		}
		
		public void setImage(Image img){
			i = img;
		    Dimension size = new Dimension(i.getWidth(null), i.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		}

		public void paintComponent(Graphics g) {
		    g.drawImage(i, 0, 0, null);
		}
	}
}