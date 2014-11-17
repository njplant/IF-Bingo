package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import Model.Ticket;
import Model.TicketSet;
import Model.Variable;
import View.AboutWindow;
import View.GameWindow;
import View.HelpWindow;
import View.LoadWindow;
import View.NewGameWindow;
import View.Pdf;
import View.StartMenu;

public class StartMenuCont {
	
	private StartMenu view;
	private NewGameWindow viewNew;
	private LoadWindow viewL;
	private HashMap<String, TicketSet> tSets;
	private int prevTick;
	private Pdf pdf;
	private File f;
	
	public StartMenuCont(StartMenu v){
		view = v;
		view.addStartListener(new StartAl());
		view.addLoadListener(new LoadAl());
		view.addExitListener(new ExitAl());
		view.addHelpListener(new HelpAl());
		view.addAboutListener(new AboutAl());
		f = new File("TicketSets.ser");
		if(!f.exists()){
			view.setLoad(false);
		}
		
	}
	
	public class StartAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			viewNew = new NewGameWindow();
			viewNew.addBasicListener(new BasicAl());
			viewNew.addAdvancedListener(new AdvancedAl());
			viewNew.addPythonListener(new PythonAl());
			viewNew.addJavaListener(new JavaAl());
			viewNew.addSubmitListener(new SubmitAl());
			viewNew.addCancelListener(new CancelAl());
			/*
			view.setTicketNum();
			view.addSubmitListener(new SubmitAl());
			view.addCancelListener(new CancelAl());
			*/
			//StartMenuVariables v = new StartMenuVariables();
			//StartMenuContVariables cont = new StartMenuContVariables(v);
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
			//Load new frame
			viewL = new LoadWindow(tickid);
			viewL.addSubmitLoadListener(new SubmitLoadAl());
			viewL.addCancelLoadListener(new CancelLoadAl());
		}	
	}
	
	public class ExitAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}	
	}
	
	public class HelpAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			HelpWindow hw = new HelpWindow();
		}	
	}
	
	public class AboutAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {	
			AboutWindow aw = new AboutWindow();
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
	
	public class SubmitAl implements ActionListener{
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
				view.showWindow(false);
				
				GameWindow gw = new GameWindow(tNum);
				GameWindowCont gwc = new GameWindowCont(gw, g, pdf);
			}else{
				JOptionPane.showMessageDialog(null, "Incorrect file name. It must end with .pdf");
				System.out.println("Not PDF!");
			}
		}	
	}
	
	public class CancelAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			viewNew.closeWindow();
			view.showWindow(true);
		}	
	}
	
	public class SubmitLoadAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String id = viewL.getLoadTick();
			//System.out.println("Selected: "+id+ "  "+tSets.containsKey(id));
			TicketSet s = tSets.get(id);
			pdf = new Pdf("load.pdf",false);
			Generate g = new Generate(s);
			g.addTickets(pdf);
			GameWindow gw = new GameWindow(s.ticketNum);
			GameWindowCont gwc = new GameWindowCont(gw, g, pdf);
		}	
	}
	
	public class CancelLoadAl implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			viewL.closeLoad();
		}	
	}

}

