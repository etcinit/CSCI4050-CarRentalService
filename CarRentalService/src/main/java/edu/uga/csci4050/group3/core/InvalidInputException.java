package edu.uga.csci4050.group3.core;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import edu.uga.csci4050.group3.template.Alert;
import edu.uga.csci4050.group3.template.AlertType;

public class InvalidInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> messages;
	
	public InvalidInputException(){
		messages = new ArrayList<String>();
	}
	
	public void addMessage(String message){
		messages.add(message);
	}
	
	public ArrayList<String> getMessages(){
		return messages;
	}
	
	public int countMessages(){
		return messages.size();
	}
	
	public String getMessagesHtml(ServletContext context){
		String html = "";
		for(String message : messages){
			Alert alert = new Alert(context);
			alert.setType(AlertType.ERROR);
			alert.setContent(message);
			html += alert.render();
		}
		return html;
	}
}
