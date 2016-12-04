
package com.pactera.pds.u2.commerce.service.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.pactera.pds.u2.commerce.entity.Car;
import com.pactera.pds.u2.commerce.repository.jpa.CarDao;

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class CarService {

	
	private CarDao carDao;
	@Autowired
	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}

//	private TaskDao taskDao;

//	public Task getTask(Long id) {
//		return taskDao.findOne(id);
//	}
	
	public Car getCar(Long id) {
		return carDao.findOne(id);
	}

//	public void saveTask(Task entity) {
//		taskDao.save(entity);
//	}
	
	public void saveCar(Car entity) {
		carDao.save(entity);
	}

//	public void deleteTask(Long id) {
//		taskDao.delete(id);
//	}
	
	public void deleteCar(Long id) {
		carDao.delete(id);
	}

//	public List<Task> getAllTask() {
//		return (List<Task>) taskDao.findAll();
//	}
	
	public List<Car> getAllCar() {
		return (List<Car>) carDao.findAll();
	}

//	public Page<Task> getUserTask(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
//			String sortType) {
//		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
//		Specification<Task> spec = buildSpecification(userId, searchParams);
//
//		return taskDao.findAll(spec, pageRequest);
//	}
	
	public Page<Car> getUserCar(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Car> spec = buildSpecification(userId, searchParams);

		return carDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

//	/**
//	 * 创建动态查询条件组合.
//	 */
//	private Specification<Task> buildSpecificationBak(Long userId, Map<String, Object> searchParams) {
//		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
//		Specification<Task> spec = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
//		return spec;
//	}
//	
	private Specification<Car> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Car> spec = DynamicSpecifications.bySearchFilter(filters.values(), Car.class);
		return spec;
	}

//	@Autowired
//	public void setTaskDao(TaskDao taskDao) {
//		this.taskDao = taskDao;
//	}
	
//	@Autowired
//	public void setCarDao(CarDao carDao) {
//		this.carDao = carDao;
//	}
}
