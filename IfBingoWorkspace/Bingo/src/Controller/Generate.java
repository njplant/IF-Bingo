package Controller;

import java.util.ArrayList;

import com.itextpdf.text.DocumentException;

import Model.Square;
import Model.Ticket;
import Model.Statement;
import Model.CallingCard;
import Model.TicketSet;
import Model.Variable;
import View.Pdf;

public class Generate {
	private static Ticket[] tickets;
	private static ArrayList<CallingCard> cards;
	private int ticketNum;
	private int tickBingo;
	private boolean diff;
	private boolean lang;
	private Variable x;
	private Variable y;
	private Variable z;
	private TicketSet set;
	private static ArrayList<boolean[]> bTickets;
	
	
	public Generate(int num, boolean b, boolean b2){
		ticketNum = num;
		diff = b;
		lang = b2;
		while(num % 5 > 0){
			num++;
		}
		
		x = new Variable('x',num);
		y = new Variable('y',num);
		z = new Variable('z',num);
	}
	
	public Generate(TicketSet s){
		set = s;
		tickets = set.getTickets();
		ticketNum = tickets.length;
		Variable v[]= set.getXYZ();
		x = v[0];
		y = v[1];
		z = v[2];
	}
	
	public boolean createTicketSet(){
		set = new TicketSet(ticketNum,x,y,z, diff,lang);
		boolean generated = set.addTickets();
		if(generated){
			tickets = set.getTickets();
			return true;
		}else{
			return false;
		}
	}
	
	public TicketSet getTSet(){
		return set;
	}
	
	public Ticket[] getTickets(){
		return tickets;
	}
	
	public void addTickets(Pdf pdf){	
		Ticket t;
		for(int i=0; i<tickets.length;i++){
			t = tickets[i];
			pdf.addTicket(t);
		}
	}
	
	public void addCards(Pdf pdf){
		CallingCard c;
		for(int i=0; i<cards.size();i++){
			c = cards.get(i);
			try {
				pdf.addCallingCard(c);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean createCards(){
		//System.out.println("Creating Calling Cards...");
		Statement statX = new Statement(x.getGreaterMap(), x.getEqualsMap(), x.getLessMap(), x.getTotalArr(),ticketNum);
		Statement statY = new Statement(y.getGreaterMap(), y.getEqualsMap(), y.getLessMap(), y.getTotalArr(),ticketNum);
		Statement statZ = new Statement(z.getGreaterMap(), z.getEqualsMap(), z.getLessMap(), z.getTotalArr(),ticketNum);
		
		bTickets = new ArrayList<boolean[]>();
		for(int i=0; i<ticketNum;i++){
			boolean [] bSquares = new boolean[9];
			for(int j=0;j<9;j++){
				bSquares[j] = false;
			}
			bTickets.add(bSquares);
		}
		boolean bingo = false;
		int count=0;
		cards = new ArrayList<CallingCard>();
		
		while(!bingo){
			CallingCard c = new CallingCard(statX,statY,statZ,ticketNum);
			boolean gen = c.getGen();
			if(!gen){
				return false;
			}
			checkTickets(c);
			//System.out.println("Calling Card "+count+" created");
			cards.add(c);
			
			for(int j=0; j<ticketNum;j++){
				boolean[] bSqr = bTickets.get(j);
				if(checkBingo(bSqr)){
					bingo = true;
					tickBingo = count;
					//System.out.println("Bingo Ticket: "+j);
				}
			}
			count++;
		}
		return true;
	}
	
	public int getTickBingo(){
		return tickBingo;
	}
	
	public ArrayList<CallingCard> getCards(){
		return cards;
	}
	
	public static void checkTickets(CallingCard c){
		int[] values = c.getNumVals();
		for(int i=0; i<tickets.length;i++){
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
			/*
			int bCount=0;
			for(int k=0; k<9;k++){
				if(bSqr[k]){
					bCount++;
				}
			}
			System.out.println("Ticket="+i+ " Count="+bCount);
			*/
		}
	}
	
	public static boolean checkBingo(boolean[] bSqr){
		for(int j=0; j<bSqr.length;j++){
			if (bSqr[j]==false){
				return false;
			}
		}
		return true;
	}
	
	public int getTicketNum(){
		return ticketNum;
	}
	
}
