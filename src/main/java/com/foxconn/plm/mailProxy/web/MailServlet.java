package com.foxconn.plm.mailProxy.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxconn.plm.mailProxy.model.Mail;
import com.foxconn.plm.mailProxy.model.PoseidonMail;
import com.foxconn.plm.mailProxy.util.HostChecker;
import com.foxconn.plm.mailProxy.util.MailUtil;

public class MailServlet extends HttpServlet{
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Send_mail");
		
		String host=req.getRemoteHost();
		String project=req.getParameter("project");
		

		Map<String, String> params=new  HashMap<String, String>();
		Map<String, String[]> paramMap=req.getParameterMap();
		for(String key:paramMap.keySet()){
			params.put(key, paramMap.get(key)[0]);
		}
		
		for(String key : params.keySet()){
			System.out.println("key:"+key+" value:"+params.get(key));
		}
		
		resp.setContentType("application/json;charset=utf-8");
		
		//if(HostChecker.isHostAllowed(project, host)){
					try {

						if("poseidon".equals(project)){

							Mail mail=new PoseidonMail(params);
							MailUtil.getMailUtil().sendMail(mail);
							resp.setStatus(200);
							
						}else{
							throw new MessagingException("* PROJECT NOT FOUND *");
						}
						
						
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						resp.setStatus(500);
						System.out.println("catch exception");
		
					} catch(Exception e){
						e.printStackTrace();
					}
					
					
		//}

		
		
	}

	
	

}
