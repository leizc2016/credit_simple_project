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
import com.pactera.pds.u2.commerce.repository.mybatis.UserMybatisDao;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserMybatisDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private UserMybatisDao userDao;

	@Test
	public void findAll() throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		
		int page = 1; //页号
		int pageSize = 2; //每页数据条数
		String sortString = "name.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString));
		List<User> result = userDao.findAll(params, pageBounds);
		assertThat(result).hasSize(2);
		//assertThat(result.get(0).getLoginName()).isEqualTo("admin");
	}
	
	@Test
	public void findAll2() throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "admin");
		int page = 2; //页号
		int pageSize = 3; //每页数据条数
		String sortString = "name.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString));
		List<Map<String, Object>> result = userDao.findAll2(params, pageBounds);
		assertThat(result).hasSize(0);
		//assertThat(result.get(0).getLoginName()).isEqualTo("admin");
	}


}
