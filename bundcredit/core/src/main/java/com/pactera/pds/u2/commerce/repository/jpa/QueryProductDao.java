
package com.pactera.pds.u2.commerce.repository.jpa;

import com.pactera.pds.u2.commerce.entity.QueryProduct;
import com.pactera.pds.u2.commerce.repository.jpa.base.GlobalRepository;

public interface QueryProductDao extends GlobalRepository<QueryProduct, Long> {
	QueryProduct findByName(String Name);
	QueryProduct getByProductCode(String productCode);
}
