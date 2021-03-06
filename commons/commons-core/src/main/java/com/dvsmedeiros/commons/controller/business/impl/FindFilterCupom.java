package com.dvsmedeiros.commons.controller.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.dvsmedeiros.bce.domain.Filter;
import com.dvsmedeiros.bce.domain.Result;
import com.dvsmedeiros.commons.controller.dao.impl.CupomDAO;
import com.dvsmedeiros.commons.domain.Cupom;

@Component
public class FindFilterCupom implements IStrategy<Filter<Cupom>> {
	
	@Autowired
	private CupomDAO dao;
	
	@Override
	public void process(Filter<Cupom> aFilter, INavigationCase<Filter<Cupom>> aCase) {
		
		List<Cupom> cupons = dao.filter(aFilter);
		aCase.getResult().addEntity(Result.RESULTS_KEY, cupons);
		
	}

}
