package edu.uga.csci4050.group3.template;

import java.io.PrintWriter;

import javax.servlet.ServletContext;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class LayoutRoot extends Template{
	
	String title;
	String servlet_name;
	
	public LayoutRoot(ServletContext context){
		super(context);
		title = "Untitled Page";
		servlet_name = context.getContextPath();
	}
	
	public void render(PrintWriter pw){
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("LayoutRoot.mustache"),"LayoutRoot.mustache");
		mustache.execute(pw, this);
		// Test comment
		pw.flush();
	}

}
