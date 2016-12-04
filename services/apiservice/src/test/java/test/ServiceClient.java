package test;  

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bundcredit.service.IUnionPayQueryService;  

public final class ServiceClient {  
    public static void main(String args[]) throws Exception { 
    	//法院公告
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});  
        /*IEnterpriseInfoQueryService client = (IEnterpriseInfoQueryService)context.getBean("client");
        String result = client.query("苏州市金诚担保有限公司","","");
        System.out.println("Response: " +result);
    	//法院文书
        IEnterpriseInfoQueryService courtDocClient = (IEnterpriseInfoQueryService)context.getBean("courtDocClient");
        String docresult = courtDocClient.query("（2015）丰民初字第","","");
        System.out.println("Response: " +docresult);*/
        
      //失信人和被执行人信息
        /*IEnterpriseInfoQueryService dishPersonAndBeExecutorClient = (IEnterpriseInfoQueryService)context.getBean("dishPersonAndBeExecutorClient");
        String deresult = dishPersonAndBeExecutorClient.query("江苏元通能源发展有限公司","","1");
        System.out.println("Response: " +deresult);*/
        
        //工商信息
      /*  IEnterpriseInfoQueryService businessInfoClient = (IEnterpriseInfoQueryService)context.getBean("businessInfoClient");
        String binforesult = businessInfoClient.query("上海地产","","");
        System.out.println("Response: " +binforesult);*/
        
        //银联查询
        IUnionPayQueryService unionPayZCInfoClient = (IUnionPayQueryService)context.getBean("unionPayZCInfoClient");
        String uinforesult = unionPayZCInfoClient.query("6225758304327058");
        System.out.println("Response: " +uinforesult);
    	/*String result1 = WSClient.invokeWSClient("http://localhost:8080/apiservice/dishPersonAndBeExecutorQueryService?wsdl",
    			"http://service.bundcredit.com/", "query", new String[]{"江苏元通能源发展有限公司","",""});
    	System.out.println(result1);*/
    }  
}  