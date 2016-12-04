
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHisYLZC;
/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface BcHisYLZCMybatisDao {

	List<BcHisYLZC> findAll(Map<String, Object> params, PageBounds pageBounds);
	
	long saveBcHisYLZC(BcHisYLZC entity);
	
	long updateBcHisYLZC(BcHisYLZC bcHisYLZC);
	
	long deleteBcHisYLZC(long id);
	
	BcHisYLZC getBcHisYLZCById(long id);
	
	String checkAuthCode(Map<String, Object> condition);
}
