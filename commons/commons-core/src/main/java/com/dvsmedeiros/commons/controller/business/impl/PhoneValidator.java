package com.dvsmedeiros.commons.controller.business.impl;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.dvsmedeiros.commons.domain.Individual;
import com.dvsmedeiros.commons.domain.Phone;
import com.google.common.base.Strings;

@Component
public class PhoneValidator implements IStrategy<Individual> {

	@Override
	public void process(Individual aEntity, INavigationCase<Individual> aCase) {

		if (aEntity != null && aEntity.getPhones() != null && !aEntity.getPhones().isEmpty()) {
			
			boolean first = true;
			for (Phone phone : aEntity.getPhones()) {
				if (Strings.isNullOrEmpty(phone.getPhone()) && first) {
					aCase.suspendExecution();
					aCase.getResult().setMessage("Telefone inexistente ou inválido");
					return;
				}
			}
			return;
		}

		aCase.suspendExecution();
		aCase.getResult().setMessage("Telefone inexistente ou inválido");

	}

}
