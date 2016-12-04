package com.pactera.pds.u2.commerce.utils;

import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
/**
 * webservice客户端调用类
 * @author libolin
 */
public class WSClient {
	
	public static String invokeWSClient(String url,String namespace,String method,String... params){
		JaxWsDynamicClientFactory  factory =JaxWsDynamicClientFactory.newInstance();
	    Client client =factory.createClient(url);
	    HTTPConduit http = (HTTPConduit) client.getConduit();   
    	HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();   
    	httpClientPolicy.setConnectionTimeout(30000);   
    	httpClientPolicy.setAllowChunking(false);   
    	httpClientPolicy.setReceiveTimeout(300000);   
    	http.setClient(httpClientPolicy);
	    try{
	    	 QName opName = new QName(namespace, method);  
	     	 Object[] obj =client.invoke(opName,params);
	     	 if(obj[0] != null){
	     		 return obj[0].toString();
	     	 }else{
	     		 return null;
	     	 }
	    }catch (Exception e) {
			 e.printStackTrace();
		 }
	    return null;
	}
	
}
