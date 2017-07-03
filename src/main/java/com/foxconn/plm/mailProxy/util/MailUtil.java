package com.foxconn.plm.mailProxy.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.foxconn.plm.mailProxy.model.Mail;


public class MailUtil {

	private static MailUtil mailUtil;
	
	private static Properties props=new Properties();
	{
		try {
			props.load(MailUtil.class.getClassLoader().getResourceAsStream("mail.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized MailUtil getMailUtil(){
		if(mailUtil==null) mailUtil=new MailUtil();
			return mailUtil;
	}

	public void sendMail(Mail mail) throws MessagingException{
		
		Session session=Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				String username=props.getProperty("mail.username");
				String password=props.getProperty("mail.password");
				
				return new PasswordAuthentication(username, password);
			}
		});
		
		Message message=createMail(session, mail);
				message.setSentDate(new Date());

		Transport.send(message);
		System.out.println("# Send Out Mail #");
	}
	
	private Message createMail(Session session,
			Mail mail) throws MessagingException{
		
		MimeMessage message=new MimeMessage(session);
			message.setSubject(mail.getSubject(), "utf-8");
			message.setContent(mail.getContent(), "text/plain;charset=utf-8");
			
			
			message.setRecipient(RecipientType.TO, new InternetAddress(mail.getMail_to()));
			message.setFrom(new InternetAddress(mail.getMail_from()));
			message.setReplyTo(new Address[]{ new InternetAddress(mail.getMail_from())});
	
			List<Address> ccList=new ArrayList<Address>();
			String[] mail_cc=mail.getMail_cc();
			if(mail_cc!=null &&  mail_cc.length>0){
				for(int i=0;i<mail_cc.length;i++)
				ccList.add(new InternetAddress(mail_cc[i]));
				message.setRecipients(RecipientType.CC, (Address[]) ccList.toArray());
			}
			
			return message;
	}

}
