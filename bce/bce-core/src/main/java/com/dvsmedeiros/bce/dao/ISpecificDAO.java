package com.dvsmedeiros.bce.dao;

import java.util.List;

import com.dvsmedeiros.bce.domain.DomainEntity;
import com.dvsmedeiros.bce.domain.IEntity;

public interface ISpecificDAO<T extends DomainEntity> extends IDAO<T>, IEntity {
	
//	public T find(String code, Class<? extends T> clazz);
//	
//	public void delete(String code, Class<? extends T> clazz);
//	
//	public List<T> findAll(boolean active, Class<? extends T> clazz);
}