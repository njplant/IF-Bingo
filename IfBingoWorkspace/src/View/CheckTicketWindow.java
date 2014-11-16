package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import Model.Square;
import Model.Ticket;

public class CheckTicketWindow extends JFrame{
	private JPanel panelBottom;
	private JLabel btnBack;
	private ImageIcon iBack;
	private JPanel panelPrev;
	private JLabel btnUp;
	private ImageIcon iUp;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JLabel btnDown;
	private ImageIcon iDown;
	private JPanel panelMid;
	private Font fSquare = new Font("Serif", Font.PLAIN, 36);
	private Font fSelected = new Font("Serif", Font.BOLD, 36);
	private Font large = new Font("Serif", Font.BOLD, 20);
	
	public CheckTicketWindow(){
		super.setSize(800,600);
        super.setResizable(false);
        super.setLocation(300,30);
        super.setTitle("If Bingo Game!");
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelBottom = new JPanel();
        panelBottom.setBackground(Color.WHITE);
        btnBack = new JLabel();
        iBack = new ImageIcon(getClass().getResource("btnBack.png"));
        btnBack.setIcon(iBack);
        panelBottom.add(btnBack);
        super.add(panelBottom, BorderLayout.PAGE_END);
        
		panelPrev = new JPanel(new GridBagLayout());
		panelPrev.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    btnUp = new JLabel();
	    iUp = new ImageIcon(getClass().getResource("ArrowUp.png"));
	    btnUp.setIcon(iUp);
		panelPrev.add(btnUp,gbc);
		
		gbc.gridy = 1;
		gbc.ipadx=25;
	    gbc.ipady=55;
		btn1 = new JButton("1");
		btn1.setFont(large);
		panelPrev.add(btn1,gbc);
		
		gbc.gridy = 2;
		gbc.ipadx=25;
	    gbc.ipady=55;
		btn2 = new JButton("2");
		btn2.setFont(large);
		panelPrev.add(btn2,gbc);
		
		gbc.gridy = 3;
		gbc.ipadx=25;
	    gbc.ipady=55;
		btn3 = new JButton("3");
		btn3.setFont(large);
		panelPrev.add(btn3,gbc);
		
		gbc.gridy = 4;
		gbc.ipadx=25;
	    gbc.ipady=55;
		btn4 = new JButton("4");
		btn4.setFont(large);
		panelPrev.add(btn4,gbc);
		
		gbc.gridy = 5;
		gbc.ipadx=25;
	    gbc.ipady=55;
		btn5 = new JButton("5");
		btn5.setFont(large);
		panelPrev.add(btn5,gbc);
		
		gbc.ipady=0;
		gbc.ipadx=0;
		gbc.gridy = 6;
		iDown = new ImageIcon(getClass().getResource("ArrowDown.png"));
		btnDown = new JLabel();
		btnDown.setIcon(iDown);
		panelPrev.add(btnDown,gbc);
		
		super.add(panelPrev, BorderLayout.LINE_START);
		
		panelMid = new JPanel();
		Border border1 = BorderFactory.createEmptyBorder(62,0,50,0);
		panelMid.setBorder(border1);
		super.add(panelMid,BorderLayout.CENTER);
		
		super.setVisible(true); 
	}
	
	public void updateBtnNums(int index){
		btn1.setText(Integer.toString(index));
		btn2.setText(Integer.toString(index+1));
		btn3.setText(Integer.toString(index+2));
		btn4.setText(Integer.toString(index+3));
		btn5.setText(Integer.toString(index+4));
	}
	
	public void setMainTicket(boolean[] b, Ticket tick){
		panelMid.removeAll();
		String[] sq = tick.getSquareText();
		JPanel panelX = new JPanel(new GridLayout(3,3));
		Border border2 = BorderFactory.createEmptyBorder(50,25,50,25);
		Border border =  BorderFactory.createLineBorder(Color.BLACK, 1);
		panelX.setBorder(border);
		for(int j=0; j<3;j++){
			JLabel labelX = new JLabel(sq[j]);
			labelX = updateSquare(labelX, j, b);
			labelX.setBorder(border2);
			panelX.add(labelX);
			JLabel labelY = new JLabel(sq[j+3]);
			labelY = updateSquare(labelY, j+3, b);
			labelY.setBorder(border2);
			panelX.add(labelY);
			JLabel labelZ = new JLabel(sq[j+6]);
			labelZ = updateSquare(labelZ, j+6, b);
			labelZ.setBorder(border2);
			panelX.add(labelZ);
		}
		panelMid.add(panelX);
		panelMid.revalidate();
	}
	
	public JLabel updateSquare(JLabel lab, int j, boolean[] boo){
		if(boo[j]){
			lab.setFont(fSelected);
			lab.setForeground (Color.red);
		}else{
			lab.setFont(fSquare);
		}
		lab.setHorizontalAlignment(JLabel.CENTER);
		return lab;
	}
	
	public void updateTickets(ArrayList<boolean[]> b, int start, int end){
		for(int i=start; i<end; i++){
			boolean boo[] = b.get(i);
			for(int j=0; j<boo.length;j++){
				
			}
		}
	}
	
	public void showWindow(boolean b){
		super.setVisible(b);
	}
	
	public void setSelected(int n){
		if(n==1){
			btn1.setForeground (Color.red);
			btn2.setForeground(Color.black);
			btn3.setForeground(Color.black);
			btn4.setForeground(Color.black);
			btn5.setForeground(Color.black);
		}else if(n==2){
			btn1.setForeground (Color.black);
			btn2.setForeground(Color.red);
			btn3.setForeground(Color.black);
			btn4.setForeground(Color.black);
			btn5.setForeground(Color.black);
		}else if(n==3){
			btn1.setForeground (Color.black);
			btn2.setForeground(Color.black);
			btn3.setForeground(Color.red);
			btn4.setForeground(Color.black);
			btn5.setForeground(Color.black);
		}else if(n==4){
			btn1.setForeground (Color.black);
			btn2.setForeground(Color.black);
			btn3.setForeground(Color.black);
			btn4.setForeground(Color.red);
			btn5.setForeground(Color.black);
		}else if(n==5){
			btn1.setForeground (Color.black);
			btn2.setForeground(Color.black);
			btn3.setForeground(Color.black);
			btn4.setForeground(Color.black);
			btn5.setForeground(Color.red);
		}else{
			btn1.setForeground (Color.black);
			btn2.setForeground(Color.black);
			btn3.setForeground(Color.black);
			btn4.setForeground(Color.black);
			btn5.setForeground(Color.black);
		}
	}
	
	public void addBackListener(MouseListener ml){
	    btnBack.addMouseListener(ml);
	}
	
	public void addUpTicketsListener(MouseListener ml){
	    btnUp.addMouseListener(ml);
	}
	
	public void addBtn1Listener(ActionListener al){
	    btn1.addActionListener(al);
	}
	
	public void addBtn2Listener(ActionListener al){
	    btn2.addActionListener(al);
	}
	
	public void addBtn3Listener(ActionListener al){
	    btn3.addActionListener(al);
	}
	
	public void addBtn4Listener(ActionListener al){
	    btn4.addActionListener(al);
	}
	
	public void addBtn5Listener(ActionListener al){
	    btn5.addActionListener(al);
	}
	
	public void addDownTicketsListener(MouseListener ml){
	    btnDown.addMouseListener(ml);
	}
}