package com.pactera.pds.u2.commerce.repository.jpa.base;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class GlobalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GlobalRepository<T, ID> {

	private EntityManager entityManager;

	public GlobalRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	public void sharedCustomMethod() {
		System.out.println("this is shared method invoke...");
	}
}