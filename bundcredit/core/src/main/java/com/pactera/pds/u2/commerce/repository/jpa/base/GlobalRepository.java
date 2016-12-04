package com.pactera.pds.u2.commerce.repository.jpa.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 该接口要有@NoRepositoryBean注解
 * 
 * 全局dao控制，可让子dao共享方法
 */
@NoRepositoryBean
public interface GlobalRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	void sharedCustomMethod();
}
