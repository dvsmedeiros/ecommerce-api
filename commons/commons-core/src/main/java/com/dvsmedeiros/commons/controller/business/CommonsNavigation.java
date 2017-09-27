package com.dvsmedeiros.commons.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dvsmedeiros.bce.core.controller.impl.Navigation;
import com.dvsmedeiros.bce.core.controller.impl.NavigationBuilder;
import com.dvsmedeiros.commons.controller.business.impl.ReasonCategoryValidator;
import com.dvsmedeiros.commons.controller.business.impl.ReasonCodeValidator;
import com.dvsmedeiros.commons.controller.business.impl.ReasonTypeValidator;
import com.dvsmedeiros.commons.domain.Reason;

@Configuration
public class CommonsNavigation {
	
	@Autowired private ReasonCodeValidator codeValidator;
	@Autowired private ReasonCategoryValidator reasonCategoryValidator;
	@Autowired private ReasonTypeValidator reasonTypeValidator;
	
	@Bean("SAVE_REASON")
	public Navigation<Reason> saveReason(){
		return new NavigationBuilder<Reason>()
				.next(codeValidator)
				.next(reasonCategoryValidator)
				.next(reasonTypeValidator)				
				.build();
	}
}
