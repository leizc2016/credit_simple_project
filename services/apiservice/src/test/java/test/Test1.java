package test;
import com.bundcredit.utils.WSClient;

public class Test1 {

    public static void main(String[] args) {
    	String str = WSClient.invokeWSClient("http://localhost:8080/apiservice/businessInfoQueryService?wsdl","http://service.bundcredit.com/","query",new String[]{"320000400000850","",""}) ;
    	System.out.println(str);
    }

}