package com.pactera.pds.u2.commerce.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.enterpriseinfo.CourtAnnouncementBean;
import com.pactera.pds.u2.commerce.repository.mybatis.BcCourtBulletinDao;
import com.pactera.pds.u2.commerce.service.BcCourtBulletinService;

@Transactional
@Service("bcCourtBulletinService")
public class BcCourtBulletinServiceImpl implements BcCourtBulletinService {

	@Autowired
	private BcCourtBulletinDao bcCourtBulletinDao;

	@Override
	public List<CourtAnnouncementBean> queryBcCourtBulletinsByBusinessName(
			String businessName) {
		// TODO Auto-generated method stub
		return bcCourtBulletinDao.queryBcCourtBulletinsByBusinessName(businessName);
	}

	@Override
	public void saveBcCourtBulletins(
			List<CourtAnnouncementBean> courtAnnouncementBeans,String businessName) {
		// TODO Auto-generated method stub
		if(courtAnnouncementBeans!=null&&courtAnnouncementBeans.size()>0){
			for (CourtAnnouncementBean courtAnnouncementBean : courtAnnouncementBeans) {
				if(courtAnnouncementBean!=null&&StringUtils.isNotBlank(courtAnnouncementBean.getAnnouncement())){
					courtAnnouncementBean.setBusinessName(businessName);
					bcCourtBulletinDao.saveBcCourtBulletin(courtAnnouncementBean);
				}
				
			}
		}
	}
	

}
