package com.pactera.pds.u2.commerce.repository.jpa.base;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;


public class GlobalRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

		return new GlobalRepositoryFactory(entityManager);
	}

	private static class GlobalRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private EntityManager entityManager;

		public GlobalRepositoryFactory(EntityManager entityManager) {
			super(entityManager);

			this.entityManager = entityManager;
		}

		@SuppressWarnings("unchecked")
		protected Object getTargetRepository(RepositoryMetadata metadata) {

			return new GlobalRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

			// JpaRepositoryFactory
			// 此处一定要返回的是实现类，不能是接口
			return GlobalRepositoryImpl.class;
		}
	}
}