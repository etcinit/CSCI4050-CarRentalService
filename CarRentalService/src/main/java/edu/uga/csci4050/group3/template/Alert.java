package edu.uga.csci4050.group3.template;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class Alert extends Template{
	
	String type;
	String servlet_name;
	String content;
	
	public Alert(ServletContext context, String content){
		super(context);
		type = "danger";
		servlet_name = context.getContextPath();
		this.content = content;
	}
	
	public Alert(ServletContext context){
		this(context, "Unknown message");
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setType(AlertType type){
		switch(type){
			case ERROR:
				this.type = "danger";
				break;
			case SUCCESS:
				this.type = "success";
				break;
			case WARNING:
				this.type = "warning";
				break;
			case INFO:
				this.type = "info";
				break;
		}
	}
	
	public void render(Writer pw){
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("Alert.mustache"),"Alert.mustache");
		mustache.execute(pw, this);
		// Test comment
		try {
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String render(){
		StringWriter sw = new StringWriter();
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("Alert.mustache"),"Alert.mustache");
		mustache.execute(sw, this);
		// Test comment
		sw.flush();
		return sw.toString();
	}

}
