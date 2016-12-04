
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHisYYSCS;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface BcHisYYSCSMybatisDao {

	List<BcHisYYSCS> findAll(Map<String, Object> params, PageBounds pageBounds);
	
	long saveBcHisYYSCS(BcHisYYSCS entity);
	
	long updateBcHisYYSCS(BcHisYYSCS bchisYYSCS);
	
	long deleteBcHisYYSCS(long id);
	
	BcHisYYSCS getBcHisYYSCSById(long id);
	
	BcHisYYSCS getBcHisYYSCSByAsynCode(String asynCode);
	
	String checkAuthCode(Map<String, Object> condition);
}
