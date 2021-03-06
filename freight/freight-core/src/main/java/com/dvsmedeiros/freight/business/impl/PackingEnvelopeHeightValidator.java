package com.dvsmedeiros.freight.business.impl;

import org.springframework.stereotype.Component;

import com.dvsmedeiros.bce.core.controller.INavigationCase;
import com.dvsmedeiros.bce.core.controller.business.IStrategy;
import com.dvsmedeiros.freight.domain.FreightFilter;

@Component
public class PackingEnvelopeHeightValidator implements IStrategy<FreightFilter> {

	private final static Long HEIGHT_ENVELOPE = 0L;
	
	@Override
	public void process(FreightFilter aEntity, INavigationCase<FreightFilter> aCase) {
		
		if (aEntity.getEntity().getProduct().getPacking().getHeight() != null
				&& aEntity.getEntity().getProduct().getPacking().getHeight().longValueExact() != HEIGHT_ENVELOPE) {
				aCase.suspendExecution();
				aCase.getResult().setMessage("O Pacote do Tipo Envelope não deve conter valor para a altura");
			return;
		}
	}

}
