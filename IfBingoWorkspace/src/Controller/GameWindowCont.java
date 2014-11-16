package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import Model.AppendingObjectOutputStream;
import Model.CallingCard;
import Model.Square;
import Model.Ticket;
import Model.TicketSet;
import View.CheckTicketWindow;
import View.GameWindow;
import View.HelpWindowGame;
import View.LoadWindow;
import View.NewGameWindow;
import View.Pdf;
import View.PrevTicketWindow;
import View.StartMenu;

public class GameWindowCont {
	
	private GameWindow view;
	private Generate gen;
	private Pdf pdf;
	private int round;
	private ArrayList<CallingCard> cards;
	private ArrayList<String[]> pCards;
	private Ticket[] tickets;
	private ArrayList<boolean[]> bTickets;
	private CheckTicketWindow view2;
	private int index;
	private int sButton;
	private HashMap<String, TicketSet> tSets;
	private LoadWindow viewL;
	private PrevTicketWindow viewPT;
	private NewGameWindow viewNew;
	
	public GameWindowCont(GameWindow v, Generate g, Pdf p){
		view = v;
		gen = g;
		pdf = p;
		tickets = gen.getTickets();
		round = 0;
		view.showPrevCard(false);
		
		boolean b = gen.createCards();
		while(!b){
			System.out.println("Generate Cards Failed.");
			b = gen.createCards();
		}
		cards = gen.getCards();
		
		//gen.addCards(pdf);
		bTickets = new ArrayList<boolean[]>();
		for(int i=0; i<tickets.length;i++){
			boolean [] bSquares = new boolean[9];
			for(int j=0;j<9;j++){
				bSquares[j] = false;
			}
			bTickets.add(bSquares);
		}
		pCards = new ArrayList<String[]>();
		view.addCardListener(new CardAl());
		view.addPrevCardListener(new PrevCardAl());
		view.addcTicketListener(new cTicketAl());
		view.addStartListener(new StartAl());
		view.addSaveListener(new SaveAl());
		view.addLoadListener(new LoadAl());
		view.addExitListener(new ExitAl());
		view.addHelpListener(new HelpAl());
		//Generate PDF
		try {
			pdf.printPDF();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public class StartAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			view.showWindow(false);
			viewNew = new NewGameWindow();
			viewNew.addBasicListener(new BasicAl());
			viewNew.addAdvancedListener(new AdvancedAl());
			viewNew.addPythonListener(new PythonAl());
			viewNew.addJavaListener(new JavaAl());
			viewNew.addSubmitListener(new SubmitNewAl());
			viewNew.addCancelListener(new CancelNewAl());
		}	
	}
	

	public class BasicAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewNew.radioBasic.setSelected(true);
			viewNew.radioAdvanced.setSelected(false);
		}	
	}
	
	public class AdvancedAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewNew.radioBasic.setSelected(false);
			viewNew.radioAdvanced.setSelected(true);
		}	
	}
	
	public class PythonAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewNew.radioPython.setSelected(true);
			viewNew.radioJava.setSelected(false);
		}	
	}
	
	public class JavaAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewNew.radioPython.setSelected(false);
			viewNew.radioJava.setSelected(true);
		}	
	}
	
	public class SubmitNewAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String PDFName = viewNew.getPDFName();
			int length = PDFName.length();
			String filetype = PDFName.substring(length-4, length);
			if(filetype.equalsIgnoreCase(".pdf")){
				viewNew.closeWindow();
				int tNum = viewNew.getTick();
				boolean advanced;
				if(viewNew.radioAdvanced.isSelected()){
					advanced=true;
				}else{
					advanced=false;
				}
				boolean java;
				if(viewNew.radioJava.isSelected()){
					java=true;
				}else{
					java=false;
				}
				pdf = new Pdf(PDFName,java);
				Generate g = new Generate(tNum,advanced,java);
				boolean generated = g.createTicketSet();
				while(!generated){
					g = new Generate(tNum,advanced,java);
					generated = g.createTicketSet();
				}
				g.addTickets(pdf);
				
				view = new GameWindow(tNum);
				GameWindowCont gwc = new GameWindowCont(view, g, pdf);
			}else{
				JOptionPane.showMessageDialog(null, "Incorrect file name. It must end with .pdf");
				System.out.println("Not PDF!");
			}
		}	
	}
	
	public class CancelNewAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			view.showWindow(true);
			viewNew.closeWindow();
		}	
	}
	
	public class SaveAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String sName = JOptionPane.showInputDialog("Please give a file name");
			File f = new File("TicketSets.ser");
			TicketSet set;
			try
		    {
				if(f.exists()){
					FileOutputStream fileOut =new FileOutputStream(f,true);
			        AppendingObjectOutputStream out = new AppendingObjectOutputStream(fileOut);
			        set = gen.getTSet();
			        set.setName(sName);
			        out.writeObject(set);
			        out.close();
			        fileOut.close();
				}else{
					FileOutputStream fileOut =new FileOutputStream(f,true);
			        ObjectOutputStream out = new ObjectOutputStream(fileOut);
			        set = gen.getTSet();
			        set.setName(sName);
			        out.writeObject(set);
			        out.close();
			        fileOut.close();
				}
		        //JOptionPane.showMessageDialog(null,"Game Saved with ID = "+set.setID);
		    }catch(IOException i)
		    {
		    	i.printStackTrace();
		    }
		}	
	}
	
	public class LoadAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			ArrayList<TicketSet> tickid = new ArrayList<TicketSet>();
			tSets = new HashMap<String, TicketSet>();
			FileInputStream fileStr = null;
			ObjectInputStream reader = null;
			try{ 
				fileStr = new FileInputStream("TicketSets.ser"); 
				reader = new ObjectInputStream(fileStr); 
				//System.out.println(fileStr.available());
				TicketSet tSet= null;
				while(fileStr.available()>0){
					Object next = reader.readObject();
					if (next instanceof TicketSet) {
						tSet = (TicketSet) next;
						System.out.println("Name: "+tSet.setName);
						tSets.put(tSet.setName, tSet);
						tickid.add(tSet);
					}else {
					    System.out.println("Unexpected object type:  " + next.getClass().getName());
					}
				}
				//System.out.println("Size: "+tSets.size());
				reader.close();
			    fileStr.close();
			}
			catch(IOException i){
				i.printStackTrace();
			}
			catch (ClassNotFoundException c) {
				System.out.println("TicketSet class not found");
				c.printStackTrace();
			}
			view.showWindow(false);
			
			viewL = new LoadWindow(tickid);
			viewL.addSubmitLoadListener(new SubmitLoadAl());
			viewL.addCancelLoadListener(new CancelLoadAl());
		}	
	}
	
	public class SubmitLoadAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String id = viewL.getLoadTick();
			//System.out.println("Selected: "+id+ "  "+tSets.containsKey(id));
			TicketSet s = tSets.get(id);
			pdf = new Pdf("load.pdf",false);
			gen = new Generate(s);
			gen.addTickets(pdf);
			view= new GameWindow(s.ticketNum);
			GameWindowCont gwc = new GameWindowCont(view, gen, pdf);
		}	
	}
	
	public class CancelLoadAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			viewL.closeLoad();
		}	
	}
	
	public class ExitAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}	
	}
	
	public class HelpAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			HelpWindowGame hwg = new HelpWindowGame();
		}	
	}
	
	public class CardAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			round++;
			if(round==1){
				view.changeBackground();
			}
			view.showPrevCard(true);
			view.setRound(round);
			CallingCard c;
			if(round<=cards.size()){
				c =cards.get(round-1);
				String[] vals = c.getValues();
				view.setXLabel(vals[0]);
				view.setYLabel(vals[1]);
				view.setZLabel(vals[2]);
				pCards.add(vals);
				
				// UPDATE
				int[] values = c.getNumVals();
				for(int i=0; i<bTickets.size();i++){
					boolean[] bSqr = bTickets.get(i);
					Square[] sq = tickets[i].getSquares();
					// X expressions
					for(int j=0; j<3;j++){
						int v = values[0];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("X Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
					// Y expressions
					for(int j=3; j<6;j++){
						int v = values[1];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("Y Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
					// Z expressions
					for(int j=6; j<9;j++){
						int v = values[2];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("Z Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
				}
			}else{
				System.out.println("Unique cards completed!");
				c= new CallingCard();
				String[] vals = c.getRandVals(tickets.length);
				view.setXLabel(vals[0]);
				view.setYLabel(vals[1]);
				view.setZLabel(vals[2]);
				pCards.add(vals);
				
				//UPDATE
				int[] values = c.getRanNumVals();
				for(int i=0; i<bTickets.size();i++){
					boolean[] bSqr = bTickets.get(i);
					Square[] sq = tickets[i].getSquares();
					// X expressions
					for(int j=0; j<3;j++){
						int v = values[0];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("X Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
					// Y expressions
					for(int j=3; j<6;j++){
						int v = values[1];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("Y Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
					// Z expressions
					for(int j=6; j<9;j++){
						int v = values[2];
						String type = sq[j].getType();
						int num = sq[j].getNum();
						//System.out.println("Z Val: "+v+ ". Type: "+type+". Num: "+num);
						if(type.equals(">")){
							if(v>num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals(">=")){
							if(v>=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
							if(v==num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<")){
							if(v<num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}else if(type.equals("<=")){
							if(v<=num){
								bSqr[j] = true;
								//System.out.println("BINGO! ticket: "+i);
							}
						}
					}
				}
			}
		}
	}
	
	public class PrevCardAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewPT = new PrevTicketWindow(pCards);
			viewPT.addSubmitListener(new SubmitPrevAl());
		}	
	}
	
	public class SubmitPrevAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewPT.close();
		}	
	}
	
	public class cTicketAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			view2 = new CheckTicketWindow();
			view2.addBackListener(new DoneAl());
			view2.addUpTicketsListener(new UpAl());
			view2.addBtn1Listener(new Btn1Al());
			view2.addBtn2Listener(new Btn2Al());
			view2.addBtn3Listener(new Btn3Al());
			view2.addBtn4Listener(new Btn4Al());
			view2.addBtn5Listener(new Btn5Al());
			view2.addDownTicketsListener(new DownAl());
			view.showWindow(false);
			index=0;
			//CheckTicketWindowCont cont = new CheckTicketWindowCont(v, gen, bTickets);
		}
	}
	
	public class SearchAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			System.out.println("Search tickets");
		}	
	}
	
	public class DoneAl implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			view.showWindow(true);
			view2.showWindow(false);
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}	
	}
	

	public class UpAl implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			if(index!=0){
				index--;
				view2.setSelected(++sButton);
				view2.updateBtnNums(index+1);
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}
	
	public class Btn1Al implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			view2.setMainTicket(bTickets.get(index), tickets[index]);
			sButton=1;
			view2.setSelected(sButton);
		}	
	}
	
	public class Btn2Al implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			view2.setMainTicket(bTickets.get(index+1), tickets[index+1]);
			sButton=2;
			view2.setSelected(sButton);
		}	
	}
	
	public class Btn3Al implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			view2.setMainTicket(bTickets.get(index+2), tickets[index+2]);
			sButton=3;
			view2.setSelected(sButton);
		}	
	}
	
	public class Btn4Al implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			view2.setMainTicket(bTickets.get(index+3), tickets[index+3]);
			sButton=4;
			view2.setSelected(sButton);
		}	
	}
	
	public class Btn5Al implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			view2.setMainTicket(bTickets.get(index+4), tickets[index+4]);
			sButton=5;
			view2.setSelected(sButton);
		}	
	}
	
	public class DownAl implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			if(index<tickets.length-5){
				index++;
				view2.setSelected(--sButton);
				view2.updateBtnNums(index+1);
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}


}

