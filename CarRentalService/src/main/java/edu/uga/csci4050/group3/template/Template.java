package edu.uga.csci4050.group3.template;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;

public abstract class Template {
	
	private ServletContext context;

	public Template(ServletContext context){
		this.context = context;
	}
	
	abstract public void render(PrintWriter pw);
	
	protected InputStreamReader locateTemplate(String name){
		return new InputStreamReader(this.context.getResourceAsStream("/WEB-INF/templates/" + name));
	}
}
