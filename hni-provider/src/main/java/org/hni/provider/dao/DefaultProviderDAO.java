package org.hni.provider.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.order.om.Order;
import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderDAO extends AbstractDAO<Provider> implements ProviderDAO {

	protected DefaultProviderDAO() {
		super(Provider.class);
	}

	@Override
	public List<Order> getProviderOrders(Long providerId) {
		try {
			Query q = em.createQuery("SELECT x FROM Order x WHERE x.providerLocation.provider.id = :providerId")
					.setParameter("providerId", providerId);
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

}
