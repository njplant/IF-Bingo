package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Statement {
	
	private HashMap<Integer, Integer> value, count;
	private TreeMap<Integer,Integer> greater, equals, less;
	private ArrayList<Integer> total;
	private int tNum;
	
	public Statement(HashMap<Integer,Integer> gr, HashMap<Integer,Integer> eq, HashMap<Integer,Integer> ls, ArrayList<Integer> tot, int ticketNum){
		value = new HashMap<Integer, Integer>();
		count = new HashMap<Integer, Integer>();
		
		greater = new TreeMap<Integer, Integer>(gr);
		equals = new TreeMap<Integer, Integer>(eq);
		less = new TreeMap<Integer, Integer>(ls);
		less.descendingMap();
		total = tot;
		/*
		System.out.println("Greater Size: " +greater.size());
		System.out.println("Equals Size: " +equals.size());
		System.out.println("Less Size: " +less.size());
		*/
		tNum = ticketNum;
		
		Iterator i1 = greater.entrySet().iterator();
		Map.Entry<Integer, Integer> pairs = null;
		for(int j=0; j<greater.size();j++) {
			pairs = (Map.Entry)i1.next();
			value.put(pairs.getKey(),pairs.getValue());
			count.put(pairs.getKey(),0);
    	}
		
		Iterator i2 = equals.entrySet().iterator();
		for(int j=0; j<equals.size();j++) {
			pairs = (Map.Entry)i2.next();
        	value.put(pairs.getKey(),pairs.getValue());
        	count.put(pairs.getKey(),0);
    	}
		
		Iterator i3 = less.entrySet().iterator();
		for(int j=0; j<less.size();j++) {
			pairs = (Map.Entry)i3.next();
			value.put(pairs.getKey(),pairs.getValue());
			count.put(pairs.getKey(),0);
    	}
		
	}
	
	public int getRandom(){
		int ran = randInt(0, total.size()-1);
		int ranCount = count.get(total.get(ran));
		while(ranCount>0){
			ran = randInt(0, total.size()-1);
			ranCount = count.get(total.get(ran));
		}
		return total.get(ran);
	}
	
	public int getValue(int num){
		int totPos=0;
		int length = total.size()-1;
		
		if(num==total.get(0)){
			totPos = value.get(num);
		}else if(num==total.get(1)){
			int num1 = value.get(total.get(0));
			totPos = value.get(num) + num1;
		}else if(num==total.get(2)){
			int num1 = value.get(total.get(0));
			int num2 = value.get(total.get(1));
			totPos = value.get(num) + num1 + num2;
		}else if(num==total.get(length)){
			totPos = value.get(num);
		}else if(num==total.get(length-1)){
			int num1 = value.get(total.get(length));
			totPos = value.get(num) + num1;
		}else if(num==total.get(length-2)){
			int num1 = value.get(total.get(length));
			int num2 = value.get(total.get(length-1));
			totPos = value.get(num) + num1 + num2;
		}else{
			totPos = value.get(num);
		}
		return totPos;
	}
	
	public void addCount(int key){
		int c = count.get(key);
		//System.out.println(key+" has count "+(c+1));
		count.put(key, c+1);
	}
	
	public boolean searchLess(int key){
		return less.containsKey(key);
	}
	
	public boolean searchEquals(int key){
		return equals.containsKey(key);
	}
	
	public boolean searchGreat(int key){
		return greater.containsKey(key);
	}
	
	public int getLessVal(int key){
		int val=0;
		for(int i=0; i<total.size();i++){
			if(total.get(i)==key){
				if(i==0){
					val = randInt((key-10),key-1);
				}else{
					int min = total.get(i-1);
					val = randInt(min,key-1);
				}
			}
		}
		return val;
	}
	
	public int getGreatVal(int key){
		int val=0;
		for(int i=0; i<total.size();i++){
			if(total.get(i)==key){
				if(i==total.size()-1){
					val = randInt(key+1,key+10);
				}else{
					int max = total.get(i+1);
					val = randInt(key+1,max);
				}
			}
		}
		return val;
	}
	
	private static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

}
