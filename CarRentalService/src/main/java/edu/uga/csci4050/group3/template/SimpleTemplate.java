package edu.uga.csci4050.group3.template;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class SimpleTemplate extends Template{
	
	String servlet_name;
	String template_file;
	ServletContext context;
	Map<String, String> variables;
	
	public SimpleTemplate(ServletContext context, String templateFile){
		super(context);
		servlet_name = context.getContextPath();
		template_file = templateFile;
		variables = new HashMap<String, String>();
		variables.put("base_url",context.getContextPath());
		this.context = context;
	}
	
	public void setVariable(String name, String value){
		variables.put(name, value);
	}
	
	public void clearVariables(){
		variables.clear();
	}
	
	public void setVariables(Map<String, String> variables){
		this.variables = null;
		this.variables = variables;
		variables.put("base_url",this.context.getContextPath());
	}
	
	public void render(PrintWriter pw){
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate(template_file),template_file);
		mustache.execute(pw, variables);
		// Test comment
		pw.flush();
	}
	
	public String render(){
		StringWriter sw = new StringWriter();
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate(template_file),template_file);
		mustache.execute(sw, variables);
		// Test comment
		sw.flush();
		return sw.toString();
	}
}
