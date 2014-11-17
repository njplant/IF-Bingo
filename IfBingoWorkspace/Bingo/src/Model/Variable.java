package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Variable implements Serializable{
	private int[] greater, equals, less;
	private HashMap<Integer, Integer> gr,eq,ls;
	int tNum, mult;
	char var;
	
	public Variable(char v, int ticketNum){
		var = v;
		tNum = ticketNum;
		
		mult = tNum/5;
		greater = new int[3];
		less = new int[3];
		double mult2;
		if(mult>1){
			mult2= Math.floor(mult/2);
		}else{
			mult2=0;
		}
		
		equals = new int[(int)mult2+5];
		gr = new HashMap<Integer, Integer>();
		eq = new HashMap<Integer, Integer>();
		ls = new HashMap<Integer, Integer>();
		
		// fill arrays with random numbers
		for(int i=0; i<greater.length;i++){
			int min = (mult*6)+1;
			int max = min + (mult*10);
			int num = randInt(min,max);
			while(checkDuplicate(num,greater)){
				num = randInt(min,max);
			}
			greater[i] = num;
			gr.put(num, 0);
			//System.out.println("GREATER "+num+ " " +var);
		}
		
		for(int i=0; i<equals.length;i++){
			int num = randInt(0,mult*6);
			while(checkDuplicate(num,equals)){
				num = randInt(0,mult*6);
			}
			equals[i] = num;
			eq.put(num, 0);
			//System.out.println("EQUALS "+num+ " " +var);
		}
		
		for(int i=0; i<less.length;i++){
			int min = mult*-10;
			int num = randInt(min,-1);
			while(checkDuplicate(num,less)){
				num = randInt(min,-1);
			}
			less[i] = num;
			ls.put(num, 0);
			//System.out.println("LESS "+num+ " " +var);
		}
		
	}
	
	private static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public static boolean checkDuplicate(int num, int[] arr){
		for(int i=0; i<arr.length;i++){
			if(num==arr[i]){
				return true;
			}
		}
		return false;
	}
	
	public int getGreater(){
		int limit;
		if(mult==1){
			limit =1;
		}else{
			limit=mult;
		}
		int num = randInt(0,gr.size()-1);
		Iterator i = gr.entrySet().iterator();
		if(i.hasNext()) {
			Map.Entry<Integer, Integer> pairs = getPos(i, num);
		    int val = pairs.getValue();
		    while(val == limit){
		     	num = randInt(0,gr.size()-1);
		     	i = gr.entrySet().iterator();
		       	pairs = getPos(i, num);
		        val = pairs.getValue();
		    }
		    val++;
		    gr.put(pairs.getKey(), val);
		    return pairs.getKey();
		}
    	return 0;
	}
	
	
	
	public int getEquals(int prevNum){
		//ITERATE RANDOMLY USING COUNTER?
		int num = randInt(0,eq.size()-1);
		int limit=0;
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
			limit =8;
		}
		
		Iterator i = eq.entrySet().iterator();
		if(i.hasNext()) {
			Map.Entry<Integer, Integer> pairs = getPos(i, num);
			int key = pairs.getKey();
	        int val = pairs.getValue();
	        //System.out.println("Key== " +key+ " Count== "+val+ " VAR: "+var + " Counter: "+counter);
	        while(key == prevNum || val == limit){
	        	num = randInt(0,eq.size()-1);
	        	i = eq.entrySet().iterator();
	        	pairs = getPos(i, num);
				key = pairs.getKey();
		        val = pairs.getValue();
	        }
        	val++;
        	//System.out.println("Key== " +key+ " Count== "+val);
        	eq.put(pairs.getKey(), val);
        	return pairs.getKey();
        	//System.out.println("END EQ size: "+eq.size() + " VAR: "+var);
    	}
		
		return 0;
	}
	
	public int getLess(){
		int limit;
		if(mult==1){
			limit =1;
		}else{
			limit=mult;
		}
		
		int num = randInt(0,ls.size()-1);
		Iterator i = ls.entrySet().iterator();
		if(i.hasNext()) {
			Map.Entry<Integer, Integer> pairs = getPos(i, num);
		    int val = pairs.getValue();
		    //System.out.println("Key== " +key+ " Count== "+val+ " VAR: "+var + " Counter: "+counter);
		    while(val == limit){
		     	num = randInt(0,ls.size()-1);
		     	i = ls.entrySet().iterator();
		       	pairs = getPos(i, num);
		        val = pairs.getValue();
		    }
		    val++;
		    //System.out.println("Key== " +key+ " Count== "+val);
		    ls.put(pairs.getKey(), val);
		    return pairs.getKey();
		    //System.out.println("END EQ size: "+eq.size() + " VAR: "+var);
		}		
    	return 0; 
	}
	
	public static Map.Entry<Integer, Integer> getPos(Iterator i, int num){
		int counter = 0;
		Map.Entry<Integer, Integer> pairs = (Map.Entry)i.next();
		while(counter!=num && i.hasNext()){
			pairs = (Map.Entry)i.next();
			counter++;
		}
		return pairs;
	}
	
	public HashMap<Integer, Integer> getGreaterMap(){
		//System.out.println("Size: "+gr.size());
		return gr;
	}
	
	public HashMap<Integer, Integer> getEqualsMap(){
		//System.out.println("Size: "+eq.size());
		return eq;
	}
	
	public HashMap<Integer, Integer> getLessMap(){
		//System.out.println("Size: "+ls.size());
		return ls;
	}
	
	public ArrayList<Integer> getTotalArr(){
		int[] total = new int[greater.length+equals.length+less.length];
		int count=0;
		for(int i=0; i<greater.length;i++){
			total[count]=greater[i];
			count++;
		}
		for(int i=0; i<equals.length;i++){
			total[count]=equals[i];
			count++;
		}
		for(int i=0; i<less.length;i++){
			total[count]=less[i];
			count++;
		}
		ArrayList<Integer> t = sort(total);
		return t;
	}
	
	private static ArrayList<Integer> sort(int[] a)
	{
		ArrayList<Integer> b = new ArrayList<Integer>();
		for(int i=0; i<a.length; i++){
			insert(a[i],b);
		}
		return b;
	}

	private static void insert(Integer n,ArrayList<Integer> a)
	{
		int i;
		for(i=0; i<a.size()&&a.get(i).compareTo(n)<0; i++) {}
			a.add(i,n);
	}

	
	public char getVar(){
		return var;
	}
	/*
	public void printCount(){
		for(int i=0; i<6;i++){
			System.out.println(var + " position: " +i+ ": " + eCount[i]);
		}
	}
	*/

}
