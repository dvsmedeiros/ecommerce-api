package com.dvsmedeiros.order.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dvsmedeiros.bce.core.controller.business.impl.CodeGenerator;
import com.dvsmedeiros.bce.core.controller.impl.Navigation;
import com.dvsmedeiros.bce.core.controller.impl.NavigationBuilder;
import com.dvsmedeiros.bce.domain.Filter;
import com.dvsmedeiros.order.controller.business.impl.AddOrderStatus;
import com.dvsmedeiros.order.controller.business.impl.CleanShopCart;
import com.dvsmedeiros.order.controller.business.impl.FindFilterOrder;
import com.dvsmedeiros.order.controller.business.impl.OrderCreateCupomExchange;
import com.dvsmedeiros.order.controller.business.impl.OrderHasStockValidator;
import com.dvsmedeiros.order.controller.business.impl.SendOrderToProcess;
import com.dvsmedeiros.order.controller.business.impl.SimulatesPayment;
import com.dvsmedeiros.order.controller.business.impl.UpdateStatusOrder;
import com.dvsmedeiros.order.controller.business.impl.UpdateStockByOrder;
import com.dvsmedeiros.order.domain.Order;

@Configuration
public class OrderNavigation {

	@Autowired private AddOrderStatus addOrderStatus;
	@Autowired private CleanShopCart cleanShopCart;
	@Autowired private FindFilterOrder findFilterOrder;
	@Autowired private OrderHasStockValidator orderHasStockValidator;
	@Autowired private CodeGenerator codeGenerator;
	@Autowired private UpdateStatusOrder updateStatusOrder;
	@Autowired private UpdateStockByOrder updateStockByOrder;
	@Autowired private OrderCreateCupomExchange orderCreateCupomExchange;
	@Autowired private SendOrderToProcess sendOrderToProcess;
	@Autowired private SimulatesPayment simulatesPayment;
	
	@Bean(name="CHECKOUT")
	public Navigation<Order> getSaveOrderNavigation(){
		
		return new NavigationBuilder<Order>()
				.next(orderHasStockValidator)		
				.next(codeGenerator)
				.next(cleanShopCart)
				.next(addOrderStatus)
		.build();
	}
	
	@Bean(name = "SEND_ORDER_TO_PROCESS")
	public Navigation<Order> sendOrderToProcess() {

		return new NavigationBuilder<Order>()
				.next(sendOrderToProcess)
				.build();
	}
	
	@Bean(name = "FILTER_ORDER")
	public Navigation<Filter<Order>> findFilterOrder() {

		return new NavigationBuilder<Filter<Order>>()
				.next(findFilterOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#EXCHANGED")
	public Navigation<Order> updateOrderExchanged() {

		return new NavigationBuilder<Order>()
				.next(orderCreateCupomExchange)
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#EXCHANGE")
	public Navigation<Order> updateOrderExchange() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#CANCELED")
	public Navigation<Order> updateOrderCanceled() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#DELIVERY")
	public Navigation<Order> updateOrderDelivery() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#TRANSPORTATION")
	public Navigation<Order> updateOrderTransportation() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#SEPARATION")
	public Navigation<Order> updateOrderSeparation() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#PROCESSING")
	public Navigation<Order> updateOrderProcessing() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#PAYMENT")
	public Navigation<Order> updateOrderPayment() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.next(simulatesPayment)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#DISAPPROVED")
	public Navigation<Order> updateOrderDisapproved() {

		return new NavigationBuilder<Order>()
				.next(updateStatusOrder)
				.build();
	}
	
	@Bean(name = "UPDATE_STATUS#APPROVED")
	public Navigation<Order> updateOrderApproved() {

		return new NavigationBuilder<Order>()
				.next(updateStockByOrder)
				.next(updateStatusOrder)
				.build();
	}
	
}
