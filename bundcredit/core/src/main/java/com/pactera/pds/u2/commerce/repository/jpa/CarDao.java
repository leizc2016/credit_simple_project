
package com.pactera.pds.u2.commerce.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pactera.pds.u2.commerce.entity.Car;
import com.pactera.pds.u2.commerce.repository.jpa.base.GlobalRepository;

public interface CarDao extends GlobalRepository<Car, Long>, JpaSpecificationExecutor<Car> {

	Page<Car> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from Car car where car.user.id=?1")
	void deleteByUserId(Long id);
}
