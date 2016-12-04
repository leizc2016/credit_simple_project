package test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {

    public static void main(String[] args) {
         String str = "<table class='tablestyle' cellspacing='1' cellpadding='0' align='center'>" +
							"<tbody>" +
							"<tr>"+
							"<tr class='tdbgs_odd'>" +
							"<td align='center'>1</td>" +
							"<td align='left'>北京市丰台区人民法院</td>" +
							"<td align='left'>" +
							"<a target='_blank' title='黄×与渠×离婚纠纷一审民事判决书' href='http://www.court.gov.cn/zgcpwsw/bj/bjsdezjrmfy/bjsftqrmfy/ms/201508/t20150827_10524736.htm'> 黄×与渠×离婚纠纷一审民事判决书 </a></td>"+
							"<td align='left'>（2015）丰民初字第06110号</td>" +
							"<td align='center'>2015-06-11</td>"+
							"</tr>"+
							"</tbody>" +
							"</table>";

        Document doc = Jsoup.parse(str);
        Elements trs = doc.select("table.tablestyle").select("tr.tdbgs_odd");
        for(int i = 0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            for(int j = 0;j<tds.size();j++){
                String text = tds.get(j).text();
                System.out.println(text);
            }
        }
        
        Elements as = doc.select("a[title=黄×与渠×离婚纠纷一审民事判决书]");
        for(int i = 0;i<as.size();i++){
        	 System.out.println(as.get(i).attr("href"));
        }
    }

}