package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("serial")
public class TicketSet implements Serializable{
	public final int setID;
	public String setName;
	public int ticketNum;
	public Ticket[] tickets;
	private static int xCount[]; 
	private static int yCount[];
	private static int zCount[];
	private Variable x;
	private Variable y;
	private Variable z;
	private boolean diff;
	private boolean lang;
	
	public TicketSet(int num, Variable vx, Variable vy, Variable vz, boolean b, boolean b2){
		ticketNum = num;
		setID=randInt(1,10000000);
		
		x = vx;
		y = vy;
		z = vz;
		diff = b;
		lang = b2;
	}
	
	public boolean addTickets(){
		xCount = new int[3];
		yCount = new int[3];
		zCount = new int[3];
		
		int tickReq = ticketNum;
		while(tickReq % 5 > 0){
			tickReq++;
		}
		int mult = tickReq/5;
		int limit =0;
		if(mult==1){
			limit =2;
		}else if(mult==2){
			limit =3;
		}else if(mult==3){
			limit =5;
		}else if(mult==4){
			limit =6;
		}else if(mult==5){
			limit =7;
		}else if(mult==6){
			limit =7;
		}
		// [0] >,==,< || [1] >,==,== || [2] ==,==,<
		for(int i=0;i<3;i++){
			if(i==0){
				xCount[i]=mult*1;
				yCount[i]=mult*1;
				zCount[i]=mult*1;
			}else{
				xCount[i]=mult*2;
				yCount[i]=mult*2;
				zCount[i]=mult*2;
			}
		}
		
		tickets = new Ticket[ticketNum];
		for(int i=0; i<ticketNum;i++){
			int xNum = selectNum("x");
			int yNum = selectNum("y");
			int zNum = selectNum("z");
			if(i==ticketNum-1){	//Last card
				HashMap<Integer, Integer> xMap = x.getEqualsMap();
				Iterator i1 = xMap.entrySet().iterator();
				Map.Entry<Integer, Integer> pairs = null;
				for(int j=0; j<xMap.size();j++) {
					pairs = (Map.Entry)i1.next();
		        	int val = pairs.getValue();
		        	if(val ==(limit-2)){
		        		//System.out.println("2 of the same expressions for last card X. New cards to be generated");
						return false;
		        	}
		    	}
				
				HashMap<Integer, Integer> zMap = z.getEqualsMap();
				Iterator i2 = zMap.entrySet().iterator();
				for(int j=0; j<xMap.size();j++) {
					pairs = (Map.Entry)i2.next();
		        	int val = pairs.getValue();
		        	if(val ==(limit-2)){
		        		//System.out.println("2 of the same expressions for last card Z. New cards to be generated");
						return false;
		        	}
		    	}
			}
			Ticket t = new Ticket(x,y,z, xNum,yNum,zNum,diff,lang);
			tickets[i]=t;
			//System.out.println("--- Ticket "+i+" ---");
			//t.pTicket();
		}
		return true;
	}
	
	public Ticket[] getTickets(){
		return tickets;
	}
	
	public void setName(String n){
		setName = n;
	}
	
	public Variable[] getXYZ(){
		Variable v[] = new Variable[3];
		v[0]=x;
		System.out.println(x.getGreaterMap() +" "+ x.getEqualsMap() +" "+ x.getLessMap() +" "+ x.getTotalArr());
		v[1]=y;
		v[2]=z;
		return v;
	}
	
	//Method which decides the format of the card for each variable
		private static int selectNum(String v){
			int num=0;
			if(v.equals("x")){
				if(xCount[0]>=1){
					num=0;
					xCount[0]--;
				}else if(xCount[1]>=1){
					num=1;
					xCount[1]--;
				}else{
					num=2;
					xCount[2]--;
				}
			}else if(v.equals("y")){
				if(yCount[1]>=1){
					num=1;
					yCount[1]--;
				}else if(yCount[2]>=1){
					num=2;
					yCount[2]--;
				}else{
					num=0;
					yCount[0]--;
				}
			}else if(v.equals("z")){
				if(zCount[2]>=1){
					num=2;
					zCount[2]--;
				}else if(zCount[0]>=1){
					num=0;
					zCount[0]--;
				}else{
					num=1;
					zCount[1]--;
				}
			}
			return num;
		}
		
		private static int randInt(int min, int max) {
		    Random rand = new Random();
		    int randomNum = rand.nextInt((max - min) + 1) + min;
		    return randomNum;
		}
}
