package com.dvsmedeiros.bce.controller.impl;

import com.dvsmedeiros.bce.controller.INavigationCase;
import com.dvsmedeiros.bce.domain.DomainEntity;
import com.dvsmedeiros.bce.domain.IEntity;
import com.dvsmedeiros.bce.domain.Result;

public class BusinessCase<E extends IEntity> implements INavigationCase<E> {

	private Result<E> result;
	private String name;
	private Boolean suspend = false;
	private E entity;
	
	public BusinessCase() {
		this.result = new Result<>();
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Result<E> getResult() {
		return this.result;
	}

	@Override
	public void suspendExecution() {
		this.suspend = true;
	}

	@Override
	public Boolean isSuspendExecution() {
		return this.suspend;
	}

	public void setResult(Result<E> result) {
		this.result = result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}
	
}