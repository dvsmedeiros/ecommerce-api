package com.dvsmedeiros.product.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dvsmedeiros.bce.core.controller.IFacade;
import com.dvsmedeiros.bce.core.controller.INavigator;
import com.dvsmedeiros.bce.core.controller.impl.BusinessCase;
import com.dvsmedeiros.bce.core.controller.impl.BusinessCaseBuilder;
import com.dvsmedeiros.freight.domain.Freight;
import com.dvsmedeiros.freight.domain.FreightFilter;
import com.dvsmedeiros.product.domain.Product;
import com.dvsmedeiros.rest.rest.controller.BaseController;
import com.dvsmedeiros.shopcart.domain.Cart;

@Controller
public class FreightController extends BaseController {

	@Autowired
	@Qualifier("applicationFacade")
	private IFacade<Freight> appFacade;

	@Autowired
	@Qualifier("navigator")
	private INavigator navigator;

	@Autowired
	private Cart cart;

	@RequestMapping(value = "freight/{postalCode}", method = RequestMethod.GET)
	public @ResponseBody List<Freight> calculteFreightAndDeadLine(@PathVariable String postalCode) {

		FreightFilter filter = new FreightFilter(Freight.class);
		filter.setCartItems(cart.getCartItems());
		filter.getEntity().setPostalCodeDestine(postalCode);

		BusinessCase<FreightFilter> aCase = new BusinessCaseBuilder<FreightFilter>().withName("CALCULATE_FREIGHT").build();
		navigator.run(filter, aCase);

		if (!aCase.isSuspendExecution()) {
			return aCase.getResult().getEntity("freights");
		}

		return Collections.emptyList();
	}

	@RequestMapping(value = "freight/{productId}/{postalCode}", method = RequestMethod.GET)
	public @ResponseBody List<Freight> calculteFreightAndDeadLine(@PathVariable Long productId, @PathVariable String postalCode) {

		FreightFilter filter = new FreightFilter(Freight.class);
		Product product = new Product();
		product.setId(productId);

		filter.getEntity().setProduct(product);
		filter.getEntity().setPostalCodeDestine(postalCode);

		BusinessCase<FreightFilter> aCase = new BusinessCaseBuilder<FreightFilter>().withName("CALCULATE_FREIGHT").build();
		navigator.run(filter, aCase);

		if (!aCase.isSuspendExecution()) {
			return aCase.getResult().getEntity("freights");
		}

		return Collections.emptyList();
	}
}
