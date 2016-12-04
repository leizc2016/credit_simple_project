package com.pactera.pds.u2.commerce.entity;

import com.pactera.pds.u2.commerce.utils.ConstantUtil;

public class BcHisYLZC {
	
	private long id;
	private String idCardNum;
	private String bankCardNum;
	private String bankCardType;
	private String authCode;
	private String recentLocation;//最近消费地区
	private String commonLocation;//常用消费地区
	private String cstScore;
	private String chvScore;
	private String cotScore;
	private String cnpScore;
	private String wlpScore;
	private String rskScore;
	private String cntScore;
	private String cnaScore;
	private String dsiScore;
	private long	 summaryScore;
	private float depositMoney12MonthsDetail;
	private long	 depositCount12MonthsDetail;
	private float consumeMoney12MonthsDetail;
	private long	 consumeCount12MonthsDetail;
	private float drawMoney12MonthsDetail;
	private long	 drawCount12MonthsDetail;
	private float transIn12MonthsDetail;
	private long transInCount12MonthsDetail;
	private float transOut12MonthsDetail;
	private long	transOutCount12MonthsDetail;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getBankCardNum() {
		return bankCardNum;
	}
	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getRecentLocation() {
		return recentLocation;
	}
	public void setRecentLocation(String recentLocation) {
		this.recentLocation = recentLocation;
	}
	public String getCommonLocation() {
		return commonLocation;
	}
	public void setCommonLocation(String commonLocation) {
		this.commonLocation = commonLocation;
	}
	
	public String getCstScore() {
		return cstScore;
	}
	public String getCstScoreStr() {
		return ConstantUtil.YLZC_CST_SCORE.get(cstScore);
	}
	public void setCstScore(String cstScore) {
		this.cstScore = cstScore;
	}
	public String getChvScore() {
		return chvScore;
	}
	public String getChvScoreStr() {
		return ConstantUtil.YLZC_CHV_SCORE.get(chvScore);
	}
	public void setChvScore(String chvScore) {
		this.chvScore = chvScore;
	}
	public String getCotScore() {
		return cotScore;
	}
	public String getCotScoreInt() {
		int score = 0;
		int cotscore = Integer.parseInt(cotScore);
		if (cotscore == 9990){
			score = 0;
		} else if (cotscore == 9991){
			score = 6;
		} else if (cotscore < 300){
			score = 1;
		} else if(300 <= cotscore && cotscore < 500){
			score = 2;
		} else if(500 <= cotscore && cotscore < 700){
			score = 3;
		} else if(700 <= cotscore && cotscore < 900){
			score = 4;
		} else if(900 <= cotscore){
			score = 5;
		}
		return String.valueOf(score);
	}
	public void setCotScore(String cotScore) {
		this.cotScore = cotScore;
	}
	public String getCnpScore() {
		return cnpScore;
	}
	public String getCnpScoreStr() {
		return ConstantUtil.YLZC_CNP_SCORE.get(cnpScore);
	}
	public void setCnpScore(String cnpScore) {
		this.cnpScore = cnpScore;
	}
	public String getWlpScore() {
		return wlpScore;
	}
	public String getWlpScoreStr() {
		return ConstantUtil.YLZC_WLP_SCORE.get(wlpScore);
	}
	public void setWlpScore(String wlpScore) {
		this.wlpScore = wlpScore;
	}
	public String getRskScore() {
		return rskScore;
	}
	
	public String getRskScoreInt() {
		int score = 0;
		int rskscore = Integer.parseInt(rskScore);
		if (rskscore == 9990){
			score = 0;
		} else if (rskscore == 9991){
			score = 6;
		} else if (rskscore < 300){
			score = 1;
		} else if(300 <= rskscore && rskscore < 500){
			score = 2;
		} else if(500 <= rskscore && rskscore < 700){
			score = 3;
		} else if(700 <= rskscore && rskscore < 900){
			score = 4;
		} else if(900 <= rskscore){
			score = 5;
		}
		return String.valueOf(score);
	}
	
	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}
	public String getCntScore() {
		return cntScore;
	}
	public void setCntScore(String cntScore) {
		this.cntScore = cntScore;
	}
	public String getCnaScore() {
		return cnaScore;
	}
	public void setCnaScore(String cnaScore) {
		this.cnaScore = cnaScore;
	}
	public String getDsiScore() {
		return dsiScore;
	}
	public String getDsiScoreInt() {
		int score = 0;
		int dsiscore = Integer.parseInt(dsiScore);
		if (dsiscore == 9991){
			score = 6;
		} else if (dsiscore == 9992){
			score = 1;
		} else if (dsiscore == 9993){
			score = 2;
		}else if (dsiscore <= 300){
			score = 3;
		} else if(300 < dsiscore && dsiscore <= 700){
			score = 4;
		} else if(700 < dsiscore && dsiscore <= 1000){
			score = 5;
		}
		return String.valueOf(score);
	}
	public void setDsiScore(String dsiScore) {
		this.dsiScore = dsiScore;
	}
	public long getSummaryScore() {
		return summaryScore;
	}
	public void setSummaryScore(long summaryScore) {
		this.summaryScore = summaryScore;
	}
	public float getDepositMoney12MonthsDetail() {
		return depositMoney12MonthsDetail;
	}
	public void setDepositMoney12MonthsDetail(float depositMoney12MonthsDetail) {
		this.depositMoney12MonthsDetail = depositMoney12MonthsDetail;
	}
	public long getDepositCount12MonthsDetail() {
		return depositCount12MonthsDetail;
	}
	public void setDepositCount12MonthsDetail(long depositCount12MonthsDetail) {
		this.depositCount12MonthsDetail = depositCount12MonthsDetail;
	}
	public float getConsumeMoney12MonthsDetail() {
		return consumeMoney12MonthsDetail;
	}
	public void setConsumeMoney12MonthsDetail(float consumeMoney12MonthsDetail) {
		this.consumeMoney12MonthsDetail = consumeMoney12MonthsDetail;
	}
	public long getConsumeCount12MonthsDetail() {
		return consumeCount12MonthsDetail;
	}
	public void setConsumeCount12MonthsDetail(long consumeCount12MonthsDetail) {
		this.consumeCount12MonthsDetail = consumeCount12MonthsDetail;
	}
	public float getDrawMoney12MonthsDetail() {
		return drawMoney12MonthsDetail;
	}
	public void setDrawMoney12MonthsDetail(float drawMoney12MonthsDetail) {
		this.drawMoney12MonthsDetail = drawMoney12MonthsDetail;
	}
	public long getDrawCount12MonthsDetail() {
		return drawCount12MonthsDetail;
	}
	public void setDrawCount12MonthsDetail(long drawCount12MonthsDetail) {
		this.drawCount12MonthsDetail = drawCount12MonthsDetail;
	}
	public float getTransIn12MonthsDetail() {
		return transIn12MonthsDetail;
	}
	public void setTransIn12MonthsDetail(float transIn12MonthsDetail) {
		this.transIn12MonthsDetail = transIn12MonthsDetail;
	}
	public long getTransInCount12MonthsDetail() {
		return transInCount12MonthsDetail;
	}
	public void setTransInCount12MonthsDetail(long transInCount12MonthsDetail) {
		this.transInCount12MonthsDetail = transInCount12MonthsDetail;
	}
	public float getTransOut12MonthsDetail() {
		return transOut12MonthsDetail;
	}
	public void setTransOut12MonthsDetail(float transOut12MonthsDetail) {
		this.transOut12MonthsDetail = transOut12MonthsDetail;
	}
	public long getTransOutCount12MonthsDetail() {
		return transOutCount12MonthsDetail;
	}
	public void setTransOutCount12MonthsDetail(long transOutCount12MonthsDetail) {
		this.transOutCount12MonthsDetail = transOutCount12MonthsDetail;
	}
	
}
