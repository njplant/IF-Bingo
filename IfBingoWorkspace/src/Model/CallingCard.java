package Model;

import java.util.Random;

public class CallingCard {
	private Statement statX;
	private Statement statY;
	private Statement statZ;
	private int keyX;
	private int keyY;
	private int keyZ;
	private int strX;
	private int strY;
	private int strZ;
	private boolean gen;
	private int tNum;
	
	public CallingCard(Statement sx, Statement sy, Statement sz, int ticketNum){
		tNum = ticketNum;
		int min = (int) (tNum*0.8);
		int max = (int) (tNum*1.2);
		
		//System.out.println(min +" -- "+max);
		
		statX = sx;
		statY = sy;
		statZ = sz;
		
		keyX = statX.getRandom();
		keyY = statY.getRandom();
		keyZ = statZ.getRandom();
		int totVal = statX.getValue(keyX) + statY.getValue(keyY) + statZ.getValue(keyZ);
		int attempts=0;
		gen = true;
		while(totVal < min || totVal > max){
			if(attempts>1000){
				gen = false;
				System.out.println("Too many attempts");
				break;
			}else{
				keyX = statX.getRandom();
				keyY = statY.getRandom();
				keyZ = statZ.getRandom();
				totVal = statX.getValue(keyX) + statY.getValue(keyY) + statZ.getValue(keyZ);
				attempts++;
			}
		}
		//System.out.println("Tot val: "+totVal);
		if(gen){
			//System.out.println(keyX +" -- "+ keyY +" -- "+ keyZ);
			addCount(keyX, keyY, keyZ);
		}
	}
	
	public CallingCard(){
		//For Random Values
	}
	
	public boolean getGen(){
		return gen;
	}
	
	public void addCount(int kX, int kY, int kZ){
		statX.addCount(kX);
		statY.addCount(kY);
		statZ.addCount(kZ);
	}
	
	public String[] getRandVals(int t){
		tNum = t;
		int mult = tNum/5;
		int min = (-((mult*6)+1) + (mult*10));
		int max = ((mult*6)+1) + (mult*10);
		
		String[] values = new String[3];
		
		strX = randInt(min,max);
		strY = randInt(min,max);
		strZ = randInt(min,max);
		
		values[0] = "x == "+strX+"; ";
		values[1] = "y == "+strY+"; ";
		values[2] = "z == "+strZ+"; ";
		
		return values;
	}
	
	private static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public String[] getValues(){
		String[] values = new String[3];
		
		values[0] = "x == "+strX+"; ";
		values[1] = "y == "+strY+"; ";
		values[2] = "z == "+strZ+"; ";
		
		/*
		for(int i=0; i<3;i++){
			System.out.println(values[i]);
		}
		*/
		return values;
	}
	
	public int[] getNumVals(){
		if(statX.searchLess(keyX)){
			strX = statX.getLessVal(keyX);
		}else if (statX.searchGreat(keyX)){
			strX = statX.getGreatVal(keyX);
		}else{
			strX = keyX;
		}
		
		if(statY.searchLess(keyY)){
			strY = statY.getLessVal(keyY);
		}else if (statY.searchGreat(keyY)){
			strY = statY.getGreatVal(keyY);
		}else{
			strY = keyY;
		}
		
		if(statZ.searchLess(keyZ)){
			strZ = statZ.getLessVal(keyZ);
		}else if (statZ.searchGreat(keyZ)){
			strZ = statZ.getGreatVal(keyZ);
		}else{
			strZ = keyZ;
		}
		
		int[] vals = new int[3];
		vals[0]=strX;
		vals[1]=strY;
		vals[2]=strZ;
		return vals;
	}
	
	public int[] getRanNumVals(){
		int[] vals = new int[3];
		vals[0]=strX;
		vals[1]=strY;
		vals[2]=strZ;
		return vals;
	}
	
	
}
