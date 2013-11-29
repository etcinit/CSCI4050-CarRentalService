package edu.uga.csci4050.group3.template;

import java.io.Writer;

import javax.servlet.ServletContext;

public class ConfirmationDialog extends Template {
	
	private SimpleTemplate internalTemplate;
	private String description;
	private String url_yes;
	private String url_no;

	public ConfirmationDialog(ServletContext context, String description, String url_yes, String url_no) {
		super(context);
		this.description = description;
		this.url_yes = url_yes;
		this.url_no = url_no;
		this.internalTemplate = new SimpleTemplate(context, "ConfirmationDialog.mustache");
	}

	@Override
	public String render() {
		this.internalTemplate.setVariable("description", description);
		this.internalTemplate.setVariable("url_yes", url_yes);
		this.internalTemplate.setVariable("url_no", url_no);
		return internalTemplate.render();
	}

	@Override
	public void render(Writer pw) {
		this.internalTemplate.setVariable("description", description);
		this.internalTemplate.setVariable("url_yes", url_yes);
		this.internalTemplate.setVariable("url_no", url_no);
		internalTemplate.render(pw);
	}

}
