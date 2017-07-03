package com.foxconn.plm.mailProxy.model;

public abstract class Mail {
	
	private String subject;
	private String mail_to;
	private String mail_from;
	private String[] mail_cc;
	
	
	public Mail(String subject,String mailTo, String mailFrom, String[] cc) {
		this.subject=subject;
		this.mail_to = mailTo;
		this.mail_from = mailFrom;
		this.mail_cc = cc;
	}
	
	
	
	public abstract String getContent();
	
	
	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mailTo) {
		mail_to = mailTo;
	}

	public String getMail_from() {
		return mail_from;
	}

	public void setMail_from(String mailFrom) {
		mail_from = mailFrom;
	}

	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String[] getMail_cc() {
		return mail_cc;
	}

	public void setMail_cc(String[] mailCc) {
		mail_cc = mailCc;
	}

	
	
}
