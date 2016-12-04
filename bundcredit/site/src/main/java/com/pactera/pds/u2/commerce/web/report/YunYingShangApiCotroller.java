package com.pactera.pds.u2.commerce.web.report;

import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.service.car.YunYingShangService;

@RestController
@RequestMapping(value = "/creditreportApi")
public class YunYingShangApiCotroller {

	@Autowired
    private YunYingShangService yunYingShangService;
	@Autowired
	private CreditSearchService creditSearchService;

	@RequestMapping(value = "IPS_Search", method=RequestMethod.POST)
	public String searchPhone(ServletRequest request)  {
		
		JSONObject result = new JSONObject();
		Map<String, String[]> paramsMap = request.getParameterMap();
		if (!yunYingShangService.validateParameters(paramsMap, result)) {
			return result.toString();
		}
		String[] cellNum = paramsMap.get("mobile");
		String queryProvider = creditSearchService.selectISPProvider(cellNum[0]);
		if(queryProvider.equals("GEO")){
			result = yunYingShangService.doQueryGEO(paramsMap);
		}else if(queryProvider.equals("CS")){
			result = yunYingShangService.doQueryCS(paramsMap);
		}
		result.put("query_provider", queryProvider);
		return result.toString();
	}
	
	@RequestMapping(value = "IPS_Search_test", method=RequestMethod.GET)
	public String searchPhoneTest(ServletRequest request){
		return searchPhone(request);
	}
	
}
