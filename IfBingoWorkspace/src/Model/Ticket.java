package Model;

import java.io.Serializable;

public class Ticket implements Serializable {
	private Square[] squares;
	
	public Ticket(Variable x, Variable y, Variable z, int xNum, int yNum, int zNum, boolean diff, boolean lang){
		squares = new Square[9];
		if(!diff){
			//BASIC
			if(xNum==0){
				squares[0] = new Square(x,">",lang);
				squares[1] = new Square(x,"==",lang);
				squares[2] = new Square(x,"<",lang);
			}else if(xNum==1){
				squares[0] = new Square(x,">",lang);
				squares[1] = new Square(x,"==",lang);
				squares[2] = new Square(x,"==",squares[1].getPrevNum(),lang);
			}else if(xNum==2){
				squares[0] = new Square(x,"==",lang);
				squares[1] = new Square(x,"==",squares[0].getPrevNum(),lang);
				squares[2] = new Square(x,"<",lang);
			}
			
			if(yNum==0){
				squares[3] = new Square(y,">",lang);
				squares[4] = new Square(y,"==",lang);
				squares[5] = new Square(y,"<",lang);
			}else if(yNum==1){
				squares[3] = new Square(y,">",lang);
				squares[4] = new Square(y,"==",lang);
				squares[5] = new Square(y,"==",squares[4].getPrevNum(),lang);
			}else if(yNum==2){
				squares[3] = new Square(y,"==",lang);
				squares[4] = new Square(y,"==",squares[3].getPrevNum(),lang);
				squares[5] = new Square(y,"<",lang);
			}
			
			if(zNum==0){
				squares[6] = new Square(z,">",lang);
				squares[7] = new Square(z,"==",lang);
				squares[8] = new Square(z,"<",lang);
			}else if(zNum==1){
				squares[6] = new Square(z,">",lang);
				squares[7] = new Square(z,"==",lang);
				squares[8] = new Square(z,"==",squares[7].getPrevNum(),lang);
			}else if(zNum==2){
				squares[6] = new Square(z,"==",lang);
				squares[7] = new Square(z,"==",squares[6].getPrevNum(),lang);
				squares[8] = new Square(z,"<",lang);
			}
		}else{
			//ADVANCED
			if(xNum==0){
				squares[0] = new Square(x,">",lang);
				squares[1] = new Square(x,"!=",lang);
				squares[2] = new Square(x,"<=",lang);
			}else if(xNum==1){
				squares[0] = new Square(x,">else",lang);
				squares[1] = new Square(x,"<>",lang);
				squares[2] = new Square(x,"==",squares[1].getPrevNum(),lang);
			}else if(xNum==2){
				squares[0] = new Square(x,"=if",lang);
				squares[1] = new Square(x,"==",squares[0].getPrevNum(),lang);
				squares[2] = new Square(x,"<",lang);
			}
			
			if(yNum==0){
				squares[3] = new Square(y,">else",lang);
				squares[4] = new Square(y,"<>",lang);
				squares[5] = new Square(y,"<",lang);
			}else if(yNum==1){
				squares[3] = new Square(y,">",lang);
				squares[4] = new Square(y,"=if",lang);
				squares[5] = new Square(y,"==",squares[4].getPrevNum(),lang);
			}else if(yNum==2){
				squares[3] = new Square(y,"<>",lang);
				squares[4] = new Square(y,"==",squares[3].getPrevNum(),lang);
				squares[5] = new Square(y,"<=",lang);
			}
			
			if(zNum==0){
				squares[6] = new Square(z,">else",lang);
				squares[7] = new Square(z,"=if",lang);
				squares[8] = new Square(z,"<",lang);
			}else if(zNum==1){
				squares[6] = new Square(z,">",lang);
				squares[7] = new Square(z,"<>",lang);
				squares[8] = new Square(z,"==",squares[7].getPrevNum(),lang);
			}else if(zNum==2){
				squares[6] = new Square(z,"!=",lang);
				squares[7] = new Square(z,"==",squares[6].getPrevNum(),lang);
				squares[8] = new Square(z,"<=",lang);
			}
		}
		
	}
	
	public String[] printTicket(){
		String[] tickets = new String[9];
		for(int i=0; i<9;i++){
			tickets[i]=squares[i].toString();
			//System.out.println(squares[i].toString());
		}
		return tickets;
	}
	
	public boolean[] getElif(){
		boolean[] tickets = new boolean[9];
		for(int i=0; i<9;i++){
			tickets[i]=squares[i].getElif();
		}
		return tickets;
	}
	
	public boolean[] getNested(){
		boolean[] tickets = new boolean[9];
		for(int i=0; i<9;i++){
			tickets[i]=squares[i].getNested();
		}
		return tickets;
	}
	
	public void pTicket(){
		for(int i=0; i<9;i++){
			System.out.println(squares[i].toString());
		}
	}
	
	public Square[] getSquares(){
		return squares;
	}
	
	public String[] getSquareText(){
		String[] sq = new String[9];
		for(int i=0; i<9; i++){
			String var = null;
			if(i<3){
				var="x";
			}else if(i>=3 && i<6){
				var="y";
			}else if(i>=6 && i<9){
				var="z";
			}
			String type = squares[i].getType();
			if(type.equals("==") || type.equals("<>") || type.equals("=if") || type.equals("!=")){
				sq[i] = var + " == " + squares[i].getNum();
			}else if(type.equals("<") || type.equals("<else")){
				sq[i] = var + " < " + squares[i].getNum();
			}else if(type.equals("<=")){
				sq[i] = var + " <= " + squares[i].getNum();
			}else if(type.equals(">") || type.equals(">else")){
				sq[i] = var + " > " + squares[i].getNum();
			}else if(type.equals(">=")){
				sq[i] = var + " >= " + squares[i].getNum();
			}
		}
		return sq;
	}
	
}
