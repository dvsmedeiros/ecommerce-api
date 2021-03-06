package com.dvsmedeiros.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dvsmedeiros.bce.core.controller.IFacade;
import com.dvsmedeiros.bce.core.controller.INavigator;
import com.dvsmedeiros.bce.core.controller.impl.BusinessCase;
import com.dvsmedeiros.bce.core.controller.impl.BusinessCaseBuilder;
import com.dvsmedeiros.bce.domain.Filter;
import com.dvsmedeiros.bce.domain.Result;
import com.dvsmedeiros.commons.domain.Client;
import com.dvsmedeiros.commons.domain.User;
import com.dvsmedeiros.commons.service.CommonsController;
import com.dvsmedeiros.order.domain.Order;
import com.dvsmedeiros.payment.domain.PaymentType;
import com.dvsmedeiros.rest.domain.ResponseMessage;

@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("${bce.app.context}/order")
public class OrderController extends CommonsController<Order> {

	public OrderController() {
		super(Order.class);
	}

	@Autowired
	@Qualifier("applicationFacade")
	private IFacade<Order> appFacade;

	@Autowired
	@Qualifier("navigator")
	private INavigator navigator;
	
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity checkout(@RequestBody Order order) {

		try {
			
			if(order != null && order.getPayment() != null && order.getPayment().getPaymentType() != null && order.getPayment().getPaymentType().equals(PaymentType.CREDIT_CARD)) {
				order.getPayment().getCard().setId(0);
			}else {
				order.getPayment().setCard(null);
			}
			
			Client loggedUser = getLoggedClient();
			order.setCustumer(loggedUser);
			
			Result result = appFacade.save(order, new BusinessCaseBuilder().withName("CHECKOUT").build());

			if (result.hasError()) {
				return new ResponseEntity(new ResponseMessage(Boolean.TRUE, result.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			navigator.run(order, new BusinessCaseBuilder<>().withName("SEND_ORDER_TO_PROCESS").build());
			
			return new ResponseEntity(HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new ResponseMessage(Boolean.TRUE, "Erro ao cadastrar pedido."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "status", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity changeStatus(@RequestBody Order order) {

		try {
			
			User loggedUser = getLoggedUser();
			
			BusinessCase aCase = new BusinessCaseBuilder().withName("UPDATE_STATUS#".concat(order.getStatusOrder().getCode())).build();
			aCase.getContext().setAttribute("logged", loggedUser);
			
			Result result = appFacade.update(order, aCase);

			if (result.hasError()) {
				return new ResponseEntity(new ResponseMessage(Boolean.TRUE, result.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity(order, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new ResponseMessage(Boolean.TRUE, "Erro ao atualizar status pedido."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public @ResponseBody ResponseEntity findEntityByFilter(@RequestBody Filter<Order> filter, @RequestParam(name="logged", required = false) boolean logged) {
		
		if(logged) {
			Client loggedUser = getLoggedClient();
			filter.getEntity().setCustumer(loggedUser);				
		}
		return super.findEntityByFilter(filter, logged);
	}
	
}
