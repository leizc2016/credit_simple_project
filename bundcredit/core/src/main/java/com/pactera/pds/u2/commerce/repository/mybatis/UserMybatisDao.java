
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.User;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface UserMybatisDao {

	User get(Long id);

	List<User> search(Map<String, Object> parameters);

	void save(User user);

	void delete(Long id);
	
	List<User> findAll(Map<String, Object> params, PageBounds pageBounds);
	
	List<Map<String, Object>> findAll2(Map<String, Object> params ,PageBounds pageBounds);
}
