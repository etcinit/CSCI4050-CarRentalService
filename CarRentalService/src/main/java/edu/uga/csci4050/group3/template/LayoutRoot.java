package edu.uga.csci4050.group3.template;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletContext;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class LayoutRoot extends Template{
	
	String title;
	String servlet_name;
	String content;
	
	public LayoutRoot(ServletContext context){
		super(context);
		title = "Untitled Page";
		servlet_name = context.getContextPath();
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void render(PrintWriter pw){
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("LayoutRoot.mustache"),"LayoutRoot.mustache");
		mustache.execute(pw, this);
		// Test comment
		pw.flush();
	}
	
	public String render(){
		StringWriter sw = new StringWriter();
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("LayoutRoot.mustache"),"LayoutRoot.mustache");
		mustache.execute(sw, this);
		// Test comment
		sw.flush();
		return sw.toString();
	}

}
