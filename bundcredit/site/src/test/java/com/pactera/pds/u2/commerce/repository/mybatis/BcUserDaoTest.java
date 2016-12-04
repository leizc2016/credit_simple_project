package com.pactera.pds.u2.commerce.repository.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.repository.mybatis.UserMybatisDao;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class BcUserDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private BCUserMybatisDao userDao;
	   @Autowired
	private BCAccountService bs;

	@Test
	public void findAll() throws Exception {
		BCUser u=new BCUser();
//		u.setInsCode("ora_bundcredit"); 
		u.setLoginName("user");
//		u.setLoginName("user");
		/*u.setPlainPassword("wosdfsdf");
		bs.updatePassword(u);*/
//		List<BCUser> result = userDao.findAllBcUser(u);
//		System.out.println(result.size());
//		assertThat(result).hasSize(2);
		//assertThat(result.get(0).getLoginName()).isEqualTo("admin");
	}
	
	


}
