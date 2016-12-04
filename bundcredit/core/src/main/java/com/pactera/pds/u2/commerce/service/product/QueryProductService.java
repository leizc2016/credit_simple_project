
package com.pactera.pds.u2.commerce.service.product;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.pactera.pds.u2.commerce.entity.QueryProduct;
import com.pactera.pds.u2.commerce.repository.jpa.QueryProductDao;

/**
 * 产品管理类.
 * 
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class QueryProductService {

	private static Logger logger = LoggerFactory.getLogger(QueryProductService.class);

	private QueryProductDao queryProductDao;

	public List<QueryProduct> getQueryProduct() {
		return (List<QueryProduct>) queryProductDao.findAll();
	}
	
	public Page<QueryProduct> getQueryProduct(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		return queryProductDao.findAll(pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}	
	
	public QueryProduct getQueryProduct(Long id) {
		return queryProductDao.findOne(id);
	}

	public QueryProduct findByProductCode(String productCode) {
		return queryProductDao.getByProductCode(productCode);
	}
	
	public QueryProduct findQueryProductByName(String name) {
		return queryProductDao.findByName(name);
	}
	
	public void saveQueryProduct(QueryProduct queryProduct) {
		queryProductDao.save(queryProduct);
	}
	
	public void deleteQueryProduct(Long id) {
		queryProductDao.delete(id);
	}
	
	@Autowired
	public void setQueryProductDao(QueryProductDao queryProductDao) {
		this.queryProductDao = queryProductDao;
	}

}
