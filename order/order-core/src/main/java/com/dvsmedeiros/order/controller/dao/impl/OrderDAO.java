package com.dvsmedeiros.order.controller.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dvsmedeiros.bce.core.dao.impl.GenericDAO;
import com.dvsmedeiros.bce.domain.Filter;
import com.dvsmedeiros.order.controller.dao.IOrderDAO;
import com.dvsmedeiros.order.domain.Order;

@Repository
public class OrderDAO extends GenericDAO<Order> implements IOrderDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Order> filter(Filter<Order> filter) {

		boolean validFilter = filter != null && filter.getEntity() != null;

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT o FROM ").append(Order.class.getName()).append(" o ");
		
		if (validFilter && filter.getEntity().getCustumer() != null) {
			jpql.append( " JOIN FETCH o.custumer c " );
		}
        
        jpql.append(" WHERE 1=1 ");
	
		if (validFilter && filter.getEntity().getCustumer() != null) {
			jpql.append(" AND c.id = :custumerId");
		}
		
		if (validFilter && filter.getEntity().getStatusOrder() != null && filter.getEntity().getStatusOrder().getId() > 0) {
			jpql.append(" AND o.statusOrder.id = :statusOrderId");
		}
		
		TypedQuery<Order> query = em.createQuery(jpql.toString(), Order.class);
		
		if (validFilter && filter.getEntity().getCustumer() != null) {
			query.setParameter("custumerId", filter.getEntity().getCustumer().getId());
		}
		
		if (validFilter && filter.getEntity().getStatusOrder() != null && filter.getEntity().getStatusOrder().getId() > 0) {
			query.setParameter("statusOrderId", filter.getEntity().getStatusOrder().getId());
		}
		return query.getResultList();
	}

	@Override
	public Order updateStatus(Order order) {

		StringBuilder jpql = new StringBuilder();
		jpql.append("UPDATE ").append(Order.class.getName()).append(" o ");
		jpql.append(" SET o.statusOrder.id = :statusOrderId ");
		jpql.append(" WHERE o.id = :id ");

		Query query = em.createQuery(jpql.toString());

		if (order != null && order.getStatusOrder() != null && order.getStatusOrder().getId() > 0) {
			query.setParameter("statusOrderId", order.getStatusOrder().getId());
		}

		if (order != null && order.getId() > 0) {
			query.setParameter("id", order.getId());
		}
		query.executeUpdate();
		return order;
	}
	
	public Long saleQuantityByProductId(Long productId) {
		return 0L;
	}
}
