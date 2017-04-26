package com.dvsmedeiros.freight.business.impl;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.controller.INavigationCase;
import com.dvsmedeiros.bce.controller.business.IStrategy;
import com.dvsmedeiros.freight.domain.FreightFilter;

@Component
public class PackingEnvelopeDiameterValidator implements IStrategy<FreightFilter> {

	private final static Long DIAMETER_ENVELOPE = 0L;

	@Override
	public void process(FreightFilter aEntity, INavigationCase<FreightFilter> aCase) {

		if (aEntity.getEntity().getProduct().getPacking().getDiameter() != null
				&& aEntity.getEntity().getProduct().getPacking().getDiameter().longValueExact() != DIAMETER_ENVELOPE) {
			aCase.suspendExecution();
			aCase.getResult().setMessage("O Pacote do Tipo Envelope não deve conter valor para o diametro");
			return;
		}
	}

}
