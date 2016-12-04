package com.bundcredit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import com.bundcredit.entity.UnionPayZCBean;
import com.bundcredit.service.IUnionPayQueryService;
import com.bundcredit.utils.JsonBinder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import upa.client.UPAClient;

@WebService
public class UnionPayZCQueryService implements IUnionPayQueryService {
	
	private static final String URL = "http://192.168.8.60/repoweb/api/UPAHfCardInfoServer.do";
	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder(); 
	
	public String query(String param) {
		UPAClient upa = new UPAClient();
		// 设置开发者的ID
		upa.setDevelopmentId("PSJPSJG616LJXXK6ADX6LAWXQ61AGMPK");
		// 设置私钥
		upa.setPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMWoY/3UTq83xJC5e6LlQqmmvl1J94PPZMLT1Td/NajDD88PpouIOzU8rZx/b7gtBaryCLBzYApdoSEmFoewDu/G6BuzZFouwjUNrGFUyyXwCqeuIjyNx/jwxyQGNjfPRHTpIe1LvWJfmm4kPKLZeu6Uj17v73+gLowzGt3bn6XVAgMBAAECgYBFd4YU+p3g1O+/kYDTYlHIgDLdZJaZ+7TFCGAiThIUyuFue2ikF2//qOC3ZM5l42TfjIrjQNlbK0bq+JDohUbp/lZjoihs8ahxSun9lBEL58I6SyBTC9ajXz9NoefJml75SYa1AU8QcPM4Pvdr5oFQLKi17mIvc5EJFvNK72dvrQJBAOJ4wvZQoAFKynNdH5T5/CIVeaciJ3yb+7dwmBDvlHB/EzTORHQ/mZM/CJq1Vxx+c+ImFZ2rboYsv7FyLx9Xs6cCQQDfbd5lNIwVu5UF1tCUuB9nTYc9SaiFa/kRhLqPD/uKJ9OT8HIaTL0pRyTTnDnVZwPk+8TQOZ77FMOU6oPTZ/ojAkAKO2yLlm70P/3Pooh3waerno7Fh1yDW8TOr+6e7UQmpQcoHp2nJux4Y6Ou4byS0//GWvQlEIbI1yK+EKuiXeMHAkAywZrMEuvf5AUulmY74rf/l7UpTxphdO3X9PcMmOzjrbh62xyQrO4r+mV7TPo2aHiSBD/pra1YDJeBtOTNmclRAkByOK41Cc3d2bX4VaJTYbgOVGRfC89oX4ThqcBWopMIwfrFK6rOP7FnwWi7XB60A2O2AGLwvrPvFaWpAShi0S73");
		// 设置公钥
		upa.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCX+ZtuDBsEHtUHB/bwtQC5+6h0pNDlkS6XNuJBa1VCjNyrM8OmP9y5161/PPFNgqKt2raDVIjMIz6rE5+FX2Y5yRv3WolznJX/y9YN/8gw5gWe30W+IECRQaf2m9PVkUncAS8miV5622Q3pLY8tb+aJIjV+dj51obNu71wzN/YNwIDAQAB");
		// 设置是否调试模式，true：调试，默认是false
		upa.setDebugMode(false);
		// 设置访问的url地址,URL为我司给出的String类型字符串
		upa.setApiLocation(UnionPayZCQueryService.URL);
		// 设置银行卡卡号作参数，获取JSONObject类型的account score
		JSONObject perScore = upa.getBhUPAScoreByAccountNo(param);
		Map<String,Object> datamap = new HashMap<String,Object>();
		String errorCode = perScore.get("error_code").toString();
		datamap.put("error_code", errorCode);
		if("0".equals(errorCode)){
			ArrayList<UnionPayZCBean> datalist = new ArrayList<UnionPayZCBean>();
			JSONArray  jSONArray=  (JSONArray) perScore.get("data");
			for(Object object : jSONArray){
				UnionPayZCBean unionPayZCBean = new UnionPayZCBean();
				 JSONObject jsonObject = JSONObject.fromObject(object);
				 unionPayZCBean.setPri_acct_no_conv( jsonObject.get("account_no") == null ? "" :jsonObject.get("account_no").toString());
				 unionPayZCBean.setDc_flag(jsonObject.get("dc_flag") == null ? "" : jsonObject.get("dc_flag").toString());
				 unionPayZCBean.setCst_score(jsonObject.get("cst_score") == null ? "" : jsonObject.get("cst_score").toString());
				 unionPayZCBean.setCot_score(jsonObject.get("cot_score") == null ? "" : jsonObject.get("cot_score").toString());
				 unionPayZCBean.setCot_cluster(jsonObject.get("cot_cluster") == null ? "" : jsonObject.get("cot_cluster").toString());
				 unionPayZCBean.setCnt_score(jsonObject.get("cnt_score") == null ? "" :jsonObject.get("cnt_score").toString());
				 unionPayZCBean.setCna_score(jsonObject.get("cna_score") == null ? "" :jsonObject.get("cna_score").toString());
				 unionPayZCBean.setChv_score(jsonObject.get("chv_score") == null ? "" : jsonObject.get("chv_score").toString());
				 unionPayZCBean.setDsi_score(jsonObject.get("dsi_score") == null ? "" : jsonObject.get("dsi_score").toString());
				 unionPayZCBean.setDsi_cluster(jsonObject.get("dsi_cluster") == null ? "" : jsonObject.get("dsi_cluster").toString());
				 unionPayZCBean.setRsk_score (jsonObject.get("rsk_score") == null ? "" : jsonObject.get("rsk_score").toString());
				 unionPayZCBean.setRsk_cluster(jsonObject.get("rsk_cluster") == null ? "" : jsonObject.get("rsk_cluster").toString());
				 unionPayZCBean.setWlp_score(jsonObject.get("wlp_score") == null ? "" : jsonObject.get("wlp_score").toString());
				 unionPayZCBean.setCrb_score(jsonObject.get("crb_score") == null ? "" : jsonObject.get("crb_score").toString());
				 unionPayZCBean.setCrb_cluster(jsonObject.get("crb_cluster") == null ? "" : jsonObject.get("crb_cluster").toString());
				 unionPayZCBean.setSummary_score(jsonObject.get("summary_score") == null ? "" : jsonObject.get("summary_score").toString());
				 unionPayZCBean.setCnp_score(jsonObject.get("cnp_score") == null ? "" : jsonObject.get("cnp_score").toString());
				 unionPayZCBean.setRfm_1_var1(jsonObject.get("account_no") == null ? "" :jsonObject.get("RFM_1_var1").toString());
				 unionPayZCBean.setRfm_1_var2(jsonObject.get("RFM_1_var2") == null ? "" : jsonObject.get("RFM_1_var2").toString());
				 unionPayZCBean.setDay_1_var1(jsonObject.get("DAY_1_var1") == null ? "" : jsonObject.get("DAY_1_var1").toString());
				 unionPayZCBean.setLoc_1_var1(jsonObject.get("LOC_1_var1") == null ? "" : jsonObject.get("LOC_1_var1").toString());
				 unionPayZCBean.setMon_3_var1(jsonObject.get("MON_3_var1") == null ? "" : jsonObject.get("MON_3_var1").toString());
				 unionPayZCBean.setRfm_3_var1(jsonObject.get("RFM_3_var1") == null ? "" :jsonObject.get("RFM_3_var1").toString());
				 unionPayZCBean.setRfm_3_var2(jsonObject.get("RFM_3_var2") == null ? "" :jsonObject.get("RFM_3_var2").toString());
				 unionPayZCBean.setRfm_3_var3(jsonObject.get("RFM_3_var3") == null ? "" : jsonObject.get("RFM_3_var3").toString());
				 unionPayZCBean.setRfm_3_var4(jsonObject.get("RFM_3_var4") == null ? "" : jsonObject.get("RFM_3_var4").toString());
				 unionPayZCBean.setRfm_3_var5(jsonObject.get("RFM_3_var5") == null ? "" : jsonObject.get("RFM_3_var5").toString());
				 unionPayZCBean.setMcc_3_var1(jsonObject.get("MCC_3_var1") == null ? "" :jsonObject.get("MCC_3_var1").toString());
				 unionPayZCBean.setMon_6_var1(jsonObject.get("MON_6_var1") == null ? "" : jsonObject.get("MON_6_var1").toString());
				 unionPayZCBean.setRfm_6_var1(jsonObject.get("RFM_6_var1") == null ? "" : jsonObject.get("RFM_6_var1").toString());
				 unionPayZCBean.setRfm_6_var2(jsonObject.get("RFM_6_var2") == null ? "" : jsonObject.get("RFM_6_var2").toString());
				 unionPayZCBean.setRfm_6_var3(jsonObject.get("RFM_6_var3") == null ? "" : jsonObject.get("RFM_6_var3").toString());
				 unionPayZCBean.setRfm_6_var4(jsonObject.get("RFM_6_var4") == null ? "" : jsonObject.get("RFM_6_var4").toString());
				 unionPayZCBean.setRfm_6_var5(jsonObject.get("RFM_6_var5") == null ? "" : jsonObject.get("RFM_6_var5").toString());
				 unionPayZCBean.setMcc_6_var1(jsonObject.get("MCC_6_var1") == null ? "" : jsonObject.get("MCC_6_var1").toString());
				 unionPayZCBean.setFlag_6_var1(jsonObject.get("FLAG_6_var1") == null ? "" : jsonObject.get("FLAG_6_var1").toString());
				 unionPayZCBean.setFlag_6_var3(jsonObject.get("FLAG_6_var3") == null ? "" : jsonObject.get("FLAG_6_var3").toString());
				 unionPayZCBean.setLoc_6_var11(jsonObject.get("LOC_6_var11") == null ? "" : jsonObject.get("LOC_6_var11").toString());
				 unionPayZCBean.setFlag_6_var4(jsonObject.get("FLAG_6_var4") == null ? "" : jsonObject.get("FLAG_6_var4").toString());
				 unionPayZCBean.setFlag_6_var6(jsonObject.get("FLAG_6_var6") == null ? "" : jsonObject.get("FLAG_6_var6").toString());
				 unionPayZCBean.setRfm_6_var6(jsonObject.get("RFM_6_var6") == null ? "" : jsonObject.get("RFM_6_var6").toString());
				 unionPayZCBean.setFlag_6_var8(jsonObject.get("FLAG_6_var8") == null ? "" : jsonObject.get("FLAG_6_var8").toString());
				 unionPayZCBean.setMon_12_var1(jsonObject.get("MON_12_var1") == null ? "" : jsonObject.get("MON_12_var1").toString());
				 unionPayZCBean.setRfm_12_var1(jsonObject.get("RFM_12_var1") == null ? "" : jsonObject.get("RFM_12_var1").toString());
				 unionPayZCBean.setRfm_12_var2(jsonObject.get("RFM_12_var2") == null ? "" : jsonObject.get("RFM_12_var2").toString());
				 unionPayZCBean.setRfm_12_var3(jsonObject.get("RFM_12_var3") == null ? "" : jsonObject.get("RFM_12_var3").toString());
				 unionPayZCBean.setRfm_12_var4(jsonObject.get("RFM_12_var4") == null ? "" : jsonObject.get("RFM_12_var4").toString());
				 unionPayZCBean.setRfm_12_var5(jsonObject.get("RFM_12_var5") == null ? "" : jsonObject.get("RFM_12_var5").toString());
				 unionPayZCBean.setRfm_12_var6(jsonObject.get("RFM_12_var6") == null ? "" : jsonObject.get("RFM_12_var6").toString());
				 unionPayZCBean.setRfm_12_var20(jsonObject.get("RFM_12_var20") == null ? "" : jsonObject.get("RFM_12_var20").toString());
				 unionPayZCBean.setRfm_12_var21(jsonObject.get("RFM_12_var21") == null ? "" : jsonObject.get("RFM_12_var21").toString());
				 unionPayZCBean.setRfm_12_var22(jsonObject.get("RFM_12_var22") == null ? "" : jsonObject.get("RFM_12_var22").toString());
				 unionPayZCBean.setRfm_12_var23(jsonObject.get("RFM_12_var23") == null ? "" : jsonObject.get("RFM_12_var23").toString());
				 unionPayZCBean.setRfm_12_var24(jsonObject.get("RFM_12_var24") == null ? "" : jsonObject.get("RFM_12_var24").toString());
				 unionPayZCBean.setRfm_12_var25(jsonObject.get("RFM_12_var25") == null ? "" : jsonObject.get("RFM_12_var25").toString());
				 unionPayZCBean.setRfm_12_var26(jsonObject.get("RFM_12_var26") == null ? "" : jsonObject.get("RFM_12_var26").toString());
				 unionPayZCBean.setRfm_12_var27(jsonObject.get("RFM_12_var27") == null ? "" : jsonObject.get("RFM_12_var27").toString());
				 unionPayZCBean.setRfm_12_var29(jsonObject.get("RFM_12_var29") == null ? "" : jsonObject.get("RFM_12_var29").toString());
				 unionPayZCBean.setRfm_12_var30(jsonObject.get("RFM_12_var30") == null ? "" : jsonObject.get("RFM_12_var30").toString());
				 unionPayZCBean.setRfm_12_var39(jsonObject.get("RFM_12_var39") == null ? "" : jsonObject.get("RFM_12_var39").toString());
				 unionPayZCBean.setRfm_12_var40(jsonObject.get("RFM_12_var40") == null ? "" : jsonObject.get("RFM_12_var40").toString());
				 unionPayZCBean.setRfm_12_var44(jsonObject.get("RFM_12_var44") == null ? "" : jsonObject.get("RFM_12_var44").toString());
				 unionPayZCBean.setRfm_12_var45(jsonObject.get("RFM_12_var45") == null ? "" : jsonObject.get("RFM_12_var45").toString());
				 unionPayZCBean.setRfm_12_var47(jsonObject.get("RFM_12_var47") == null ? "" : jsonObject.get("RFM_12_var47").toString());
				 unionPayZCBean.setRfm_12_var48(jsonObject.get("RFM_12_var48") == null ? "" : jsonObject.get("RFM_12_var48").toString());
				 unionPayZCBean.setRfm_12_var50(jsonObject.get("RFM_12_var50") == null ? "" : jsonObject.get("RFM_12_var50").toString());
				 unionPayZCBean.setRfm_12_var54(jsonObject.get("RFM_12_var54") == null ? "" : jsonObject.get("RFM_12_var54").toString());
				 datalist.add(unionPayZCBean);
			}
			datamap.put("data", datalist);
		}
		return binder.toJson(datamap);
	}

}
