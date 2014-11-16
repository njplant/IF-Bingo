package Controller;

import java.util.ArrayList;

public class Test {
	public static void main(String[] Args){
		int[] sums = new int[15];
		for(int i=0; i<1000; i++){
			int tNum = 30;
			boolean advanced = false;
			boolean java =false;
			Generate g = new Generate(tNum,advanced,java);
			boolean generated = g.createTicketSet();
			while(!generated){
				g = new Generate(tNum,advanced,java);
				generated = g.createTicketSet();
			}
			
			boolean b = g.createCards();
			if(!b){
				System.out.println("Fail");
			}else{
				int tick = g.getTickBingo();
				sums[tick]++;
			}
			
		}
		for(int i=0; i<15;i++){
			System.out.println(sums[i]);
		}
		
	}
}
