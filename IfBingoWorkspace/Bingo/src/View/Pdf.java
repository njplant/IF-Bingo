package View;

import java.io.FileOutputStream;
import java.util.ArrayList;

import Model.CallingCard;
import Model.Ticket;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Pdf {
  private static String filename;
  private static Document document;
  private static Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14);
  private static Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 11);
  private static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  private static Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
  private static Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 7);
  private int cardNum=1;
  private String [] tickets;
  private boolean [] elif;
  private boolean [] nested;
  private int ticketNum =1;
  private boolean lang;

  public Pdf(String fname, boolean b) {
    try {
    	lang = b;
    	document = new Document();
    	filename = fname;
    	PdfWriter.getInstance(document, new FileOutputStream(filename));
    	document.open();
    	//addContent(document);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void addInstruction() throws DocumentException{
	  Paragraph paragraph = new Paragraph();
	  List unorderedList = new List(List.ORDERED);
      unorderedList.add(new ListItem("Each square on your ticket has a different variable (x, y or z)"));
      unorderedList.add(new ListItem("For each round:"));
      paragraph.add(unorderedList);
      List unorderedList2 = new List(List.UNORDERED,15);
      unorderedList2.add(new ListItem("The teacher will call out a statement for each variable"));
      //unorderedList2.add(new ListItem("You must check each square to see if any of the conditions are true"));
      unorderedList2.add(new ListItem("If you think the condition in a square is true, cross it off"));
      unorderedList2.add(new ListItem("More than one of the squares could be true for each round, so check thoroughly"));
      paragraph.add(unorderedList2);
      List unorderedList3 = new List(List.ORDERED);
      unorderedList3.setFirst(3);
      unorderedList3.add(new ListItem("The first player/team to cross off all their squares must shout bingo!"));
      paragraph.add(unorderedList3);
      
      
      addEmptyLine(paragraph, 1);
      document.add(paragraph);
  }

  public void addTicket(Ticket t){
	  try {
		  addInstruction();
	  } catch (DocumentException e1) {
		  e1.printStackTrace();
	  }  
	  PdfPTable table = new PdfPTable(3);
	 
	  tickets = t.printTicket();
	  elif = t.getElif();
	  nested = t.getNested();
	  PdfPCell cell;
	  for(int i=0; i<3;i++){
		  cell = formatCell(i);
		  table.addCell(cell);
	   	
		  cell = formatCell(i+3);
		  table.addCell(cell);
	   	
		  cell = formatCell(i+6);
		  table.addCell(cell);
	  }
	  try {
		  document.add(table);
	  } catch (DocumentException e) {
		  e.printStackTrace();
	  }
	  ticketNum++;
	  
	  Paragraph p = new Paragraph();
	  if(lang){
		  addEmptyLine(p, 2); 
	  }else{
		  addEmptyLine(p, 1); 
	  }
	  
	  try {
		  document.add(p);
	  } catch (DocumentException e) {
		  e.printStackTrace();
	  }
  }
  
  private PdfPCell formatCell(int num){
	PdfPCell cell = new PdfPCell();;
	String id = Integer.toString(num+1);
	Phrase content = null;
	Phrase cellID = null;
	boolean b1 = elif[num];
	boolean b2 = nested[num];
	if(!lang){
		if(b1){
			content = new Phrase(tickets[num]   +"\n"+ "   print (\"No Bingo!\")" + "\n"+ "else :" + "\n"+ "   print (\"Bingo!\")", font3);
			cellID = new Phrase(ticketNum + "." +id, font4);
		}else if(b2){
			content = new Phrase(tickets[num]   +"\n"+ "      print (\"Bingo\")" + "\n", font2);
			cellID = new Phrase(ticketNum + "." +id, font4);
		}else{
			content = new Phrase(tickets[num]   +"\n"+ "   print (\"Bingo\")", font1);
			cellID = new Phrase("\n"+ ticketNum + "." +id, font4);
		}
	}else{
		if(b1){
			content = new Phrase(tickets[num]   +"\n"+ "   System.out.println(\"No Bingo!\");" + "\n"+ "} else {" + "\n"+ "   System.out.println(\"Bingo\");" +"\n" +"}", font5);
			cellID = new Phrase(ticketNum + "." +id, font5);
		}else if(b2){
			content = new Phrase(tickets[num]   +"\n"+ "      System.out.println(\"Bingo\");" + "\n" +"   }" +"\n" +"}", font5);
			cellID = new Phrase(ticketNum + "." +id, font5);
		}else{
			content = new Phrase(tickets[num]   +"\n"+ "   System.out.println(\"Bingo\");" +"\n" +"}", font3);
			cellID = new Phrase(ticketNum + "." +id, font4);
		}
	}
	
  	cell.addElement(content);
  	cell.addElement(cellID);
  	cell.setPaddingLeft(5);
  	cell.setPaddingBottom(10);
	return cell;
  }
  
  public void addCallingCard(CallingCard c) throws DocumentException{
	  Paragraph p = new Paragraph();
	  p.add("Calling Card: "+cardNum);
	  addEmptyLine(p, 1); 
	  document.add(p);
	  
	  PdfPTable table = new PdfPTable(3);
	  String[] val = c.getValues();
	  for(int i=0; i<val.length;i++){
		  table.addCell(val[i]);
	  }
	  document.add(table);
	  
	  Paragraph p2 = new Paragraph();
	  addEmptyLine(p2, 1); 
	  document.add(p2);
	  cardNum++;
  }
  
  private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
  }
  
  public void printPDF() throws DocumentException{
      document.close();
      System.out.println("PDF closed");
  }
} 