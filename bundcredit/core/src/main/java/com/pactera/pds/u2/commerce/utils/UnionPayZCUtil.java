package com.pactera.pds.u2.commerce.utils;

import net.sf.json.JSONObject;
import upa.client.UPAClient;

public class UnionPayZCUtil {

	public static String doQuery() {

		String result = "{\"error_code\":0,\"data\":[{\"RFM_12_var6\":\"16\",\"RFM_12_var50\":\"60\",\"RFM_12_var5\":\"940.5170588235295\",\"RFM_12_var4\":\"24.5\",\"RFM_12_var3\":\"46800.0\",\"RFM_12_var54\":\"1\",\"RFM_12_var2\":\"85\",\"RFM_12_var1\":\"79943.95000000001\",\"wlp_score\":\"5\",\"RFM_1_var2\":\"3280.6\",\"RFM_1_var1\":\"7\",\"dsi_score\":\"797\",\"MON_6_var1\":\"6\",\"crb_cluster\":\"3\",\"RFM_3_var5\":\"2129.26\",\"dsi_cluster\":\"8\",\"RFM_3_var2\":\"25\",\"RFM_3_var1\":\"53231.5\",\"cot_score\":\"889\",\"RFM_3_var4\":\"24.5\",\"RFM_3_var3\":\"46800.0\",\"RFM_12_var25\":\"2\",\"RFM_12_var26\":\"41\",\"RFM_12_var23\":\"0.0\",\"MCC_6_var1\":\"9\",\"RFM_12_var24\":\"2\",\"RFM_12_var29\":\"\\\"null\\\"\",\"RFM_12_var27\":\"\\\"null\\\"\",\"DAY_1_var1\":\"1\",\"cnt_score\":\"5\",\"FLAG_6_var8\":\"3\",\"RFM_12_var22\":\"4449.0\",\"FLAG_6_var6\":\"\\\"null\\\"\",\"RFM_12_var21\":\"87.0\",\"FLAG_6_var3\":\"3\",\"RFM_12_var20\":\"47800.0\",\"rsk_score\":\"786\",\"FLAG_6_var4\":\"\\\"null\\\"\",\"FLAG_6_var1\":\"4\",\"cst_score\":\"2\",\"LOC_6_var11\":\"上海\",\"cna_score\":\"5\",\"chv_score\":\"6\",\"summary_score\":\"586\",\"RFM_12_var30\":\"0.0\",\"rsk_cluster\":\"8\",\"account_no\":\"6225758304327058\",\"RFM_12_var39\":\"\\\"null\\\"\",\"cot_cluster\":\"9\",\"dc_flag\":\"credit\",\"MON_12_var1\":\"12\",\"crb_score\":\"0.032735039729\",\"cnp_score\":\"4\",\"MON_3_var1\":\"3\",\"RFM_12_var44\":\"0.0\",\"RFM_12_var40\":\"0.0\",\"RFM_12_var45\":\"\\\"null\\\"\",\"RFM_12_var48\":\"\\\"null\\\"\",\"RFM_12_var47\":\"0.0\",\"RFM_6_var3\":\"46800.0\",\"RFM_6_var2\":\"48\",\"RFM_6_var1\":\"57134.1\",\"MCC_3_var1\":\"7\",\"RFM_6_var6\":\"3.0\",\"RFM_6_var5\":\"1190.29375\",\"LOC_1_var1\":\"上海\",\"RFM_6_var4\":\"24.5\"}]}";
		return result.toLowerCase();

	}

	public static void main(String[] args) {
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
		upa.setApiLocation("http://192.168.8.60/repoweb/api/UPAHfCardInfoServer.do");
		// 设置银行卡卡号作参数，获取JSONObject类型的account score
		JSONObject perScore = upa.getBhUPAScoreByAccountNo("6225758304327058");
		
		System.out.println(perScore.toString());
	}

}
