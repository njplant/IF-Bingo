package Model;

import java.io.Serializable;

public class Square implements Serializable{
	private Variable var;
	private Expression exp;
	private int num;
	private String type;
	
	public Square(Variable variable, String type, boolean lang) {
		var = variable;
		this.type = type;
		
		char v = var.getVar();
		
		if(type.equals(">")){
			num = var.getGreater();
		}else if(type.equals("==")){
			num = var.getEquals(999);
		}else if(type.equals("<")){
			num = var.getLess();
		}
		
		if(type.equals(">=")){
			num = var.getGreater();
		}else if(type.equals("<=")){
			num = var.getLess();
		}else if(type.equals(">else")){
			num = var.getGreater();
		}else if(type.equals("<else")){
			num = var.getLess();
		}else if(type.equals("<>")){
			num = var.getEquals(999);
		}else if(type.equals("=if")){
			num = var.getEquals(999);
		}else if(type.equals("=!")){
			num = var.getEquals(999);
		}
		
		exp = new Expression(v, type, num, lang);
	}
	
	public Square(Variable variable, String type, int prevNum, boolean lang){
		var = variable;
		this.type = type;
		
		char v = var.getVar();
		
		if(type.equals("==")){
			num = var.getEquals(prevNum);
		}else{ //less
			System.out.println("Wrong constructor used for equals");
		}
		exp = new Expression(v, type, num, lang);
	}
	
	public int getPrevNum(){
		return num;
	}
	
	public String getType(){
		return type;
	}
	
	public int getNum(){
		return num;
	}
	
	public boolean getElif(){
		return exp.getElif();
	}
	
	public boolean getNested(){
		return exp.getNested();
	}
	
	@Override
	public String toString(){
		return exp.getExpression();
	}
}
