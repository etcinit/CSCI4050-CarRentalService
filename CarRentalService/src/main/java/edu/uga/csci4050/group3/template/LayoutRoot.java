package edu.uga.csci4050.group3.template;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import edu.uga.csci4050.group3.core.AuthenticationException;
import edu.uga.csci4050.group3.db.SessionManagement;

public class LayoutRoot extends Template{
	
	String title;
	String servlet_name;
	String content;
	String username;
	String side_menu;
	HttpServletRequest request;
	HttpServletResponse response;
	
	public LayoutRoot(ServletContext context, HttpServletRequest request, HttpServletResponse response){
		super(context);
		title = "Untitled Page";
		servlet_name = context.getContextPath();
		this.request = request;
		this.response = response;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void render(Writer pw){
		
		SessionManagement sessMan = new SessionManagement(request, response);
		try {
			username = sessMan.getLoggedinUsername();
		} catch (AuthenticationException e) {
			// Just ignore
		}
		
		if(username == null){
			SimpleTemplate compactLogin = new SimpleTemplate(context, "CompactLoginForm.mustache");
			side_menu = compactLogin.render();
		}else{
			SimpleTemplate badge = new SimpleTemplate(context, "UserBadge.mustache");
			badge.setVariable("username", username);
			side_menu = badge.render();
		}
		DefaultMustacheFactory dmf = new DefaultMustacheFactory();
		Mustache mustache = dmf.compile(locateTemplate("LayoutRoot.mustache"),"LayoutRoot.mustache");
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
		render(sw);
		return sw.toString();
	}

}
