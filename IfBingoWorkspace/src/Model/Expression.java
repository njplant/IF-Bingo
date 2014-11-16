package Model;

import java.io.Serializable;

public class Expression implements Serializable{
	private String exp;
	private boolean elif;
	private boolean nest;
			
	public Expression(char var, String type, int num, boolean lang){
		if(!lang){
			//PYTHON
			if(type.equalsIgnoreCase("<") || type.equalsIgnoreCase("<=") || type.equalsIgnoreCase("==") || type.equalsIgnoreCase(">") || type.equalsIgnoreCase(">=")){
				exp = "if " + var + " " + type + " " + num + " :"; 
				elif=false;
				nest= false;
			}else if(type.equals(">else")){
				exp = "if " + var + " < " + num + " :"; 
				elif=true;
				nest= false;
			}else if(type.equals("<else")){
				exp = "if " + var + " > " + num + " :"; 
				elif=true;
				nest= false;
			}else if(type.equals("<>")){
				exp = "if " + var + " > " + (num-1) + " and " + var + " < " + (num+1) + " :"; 
				elif=false;
				nest= false;
			}else if(type.equals("=if")){
				exp = "if " + var + " < " + (num+8) + " :" + "\n" + "   " + "if " + var + " == " + num + " :"; 
				elif=false;
				nest= true;
			}else if(type.equals("!=")){
				exp = "if " + var + " " + type + " " + num + " :"; 
				elif=true;
				nest= false;
			}
		}else{
			//JAVA
			if(type.equalsIgnoreCase("<") || type.equalsIgnoreCase("<=") || type.equalsIgnoreCase("==") || type.equalsIgnoreCase(">") || type.equalsIgnoreCase(">=")){
				exp = "if (" + var + " " + type + " " + num + "){"; 
				elif=false;
				nest= false;
			}else if(type.equals(">else")){
				exp = "if (" + var + " < " + num + "){"; 
				elif=true;
				nest= false;
			}else if(type.equals("<else")){
				exp = "if (" + var + " > " + num + "){"; 
				elif=true;
				nest= false;
			}else if(type.equals("<>")){
				exp = "if (" + var + " > " + (num-1) + " && " + var + " < " + (num+1) + "){"; 
				elif=false;
				nest= false;
			}else if(type.equals("=if")){
				exp = "if (" + var + " < " + (num+8) + "){" + "\n" + "   " + "if (" + var + " == " + num + "){"; 
				elif=false;
				nest= true;
			}else if(type.equals("!=")){
				exp = "if (" + var + " " + type + " " + num + "){"; 
				elif=true;
				nest= false;
			}
		}
		
	}
	
	public boolean getElif(){
		return elif;
	}
	
	public boolean getNested(){
		return nest;
	}
	public String getExpression(){
		return exp;
	}
}
