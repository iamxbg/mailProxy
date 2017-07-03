package com.foxconn.plm.mailProxy.util;

import java.io.IOException;
import java.util.Properties;

public class HostChecker {

		public static Properties hostProps;
		
		static{
			hostProps=new Properties();
				try {
					hostProps.load(HostChecker.class.getClassLoader().getResourceAsStream("host.properties"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		public static boolean isHostAllowed(String appName,String hostIp){
			boolean isAllowed=false;
			System.out.println("appName:"+appName+"hostIp:"+hostIp);
			if(hostProps.containsKey(appName)){
				String[] hostIps=hostProps.getProperty(appName).split(";");
				System.out.println("IP count:"+hostIps.length);
				
				for(int i=0;i<hostIps.length;i++){
					System.out.println("testIP:"+hostIps[i]);
					if(hostIps[i].equals(hostIp)){
						isAllowed=true;
						break;
					} 
				}
			}
				return isAllowed;
		}
		
		
}
