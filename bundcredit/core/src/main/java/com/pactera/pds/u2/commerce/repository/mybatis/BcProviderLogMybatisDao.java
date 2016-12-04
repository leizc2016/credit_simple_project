
package com.pactera.pds.u2.commerce.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BcHis;
import com.pactera.pds.u2.commerce.entity.BcProviderLog;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 */
@MyBatisRepository
public interface BcProviderLogMybatisDao {

	List<BcProviderLog> findAll(Map<String, Object> params, PageBounds pageBounds);
	
	long saveBcProviderLog(BcProviderLog bchis);
	
	long updateBcProviderLog(BcProviderLog bchis);
	
	long deleteBcProviderLog(long id);
	
	BcHis getBcProviderLogById(long id);
	
}
