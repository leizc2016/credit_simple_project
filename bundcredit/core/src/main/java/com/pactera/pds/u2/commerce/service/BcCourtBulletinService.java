
package com.pactera.pds.u2.commerce.service;

import java.util.List;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;

/**
 * 法院公告service
 * @author baifan
 *
 */
public interface BcCourtBulletinService {

	/**
	 * 根据businessName查询法院公告
	 * @param businessName
	 * @return
	 */
	public List<CourtAnnouncementBean> queryBcCourtBulletinsByBusinessName(String businessName);
	
	
	/**
	 * 保存法院公告表
	 * @param courtAnnouncementBeans
	 * @param businessName
	 */
	public void saveBcCourtBulletins(List<CourtAnnouncementBean> courtAnnouncementBeans,String businessName);
}
