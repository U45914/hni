package org.hni.provider.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.provider.om.NearByProviderDto;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.Address;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderLocationDAO extends AbstractDAO<ProviderLocation> implements ProviderLocationDAO {

	protected DefaultProviderLocationDAO() {
		super(ProviderLocation.class);
	}

	@Override
	public Collection<ProviderLocation> with(Provider provider) {
		try {
			Query q = em.createQuery("SELECT x FROM ProviderLocation x WHERE x.provider.id = :providerId")
				.setParameter("providerId", provider.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<ProviderLocation> providersNearCustomer(Address addr, int itemsPerPage) {
		try {

			String queryString = new StringBuilder()
					.append("SELECT pl.* FROM provider_locations pl ")
					.append(" WHERE pl.address_id in ")
					.append(" ( select new_addr.id from ")
					.append(" ( SELECT adr.id, ")
					.append(" ( 6371 * acos( ")
					.append(" cos(radians(:latLkp)) * cos(radians(adr.latitude)) * cos(radians(adr.longitude) - radians(:longLkp)) + ")
					.append(" sin(radians(:latLkp)) * sin(radians(adr.latitude)) ")
					.append(" ) ) AS distance ")
					.append(" FROM addresses adr ")
					.append(" group by adr.id ")
					.append(" HAVING distance < 10 ")
					.append(" ORDER BY distance LIMIT :items ) as new_addr ) ")
					.toString();

			Query q = em.createNativeQuery(queryString, ProviderLocation.class)
					.setParameter("longLkp", addr.getLongitude())
					.setParameter("latLkp", addr.getLatitude())
					.setParameter("items", itemsPerPage);

			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<NearByProviderDto> getNearbyProviders(String customerState) {
		List<NearByProviderDto> providersNearBy = new ArrayList<>(); 
		Query q = em.createNativeQuery("SELECT p.name, plh.dow, plh.open_hour, plh.close_hour "
				+ "FROM provider_location_hours plh "
				+ "LEFT JOIN provider_locations pl ON plh.provider_location_id = pl.id "
				+ "LEFT JOIN providers p ON pl.provider_id = p.id "
				+ "LEFT JOIN addresses a ON pl.address_id = a.id "
				+ "WHERE a.state = :state group by p.id")
					.setParameter("state", customerState);
		List<Object[]> providers = q.getResultList();
		for(Object[] obj : providers){
			NearByProviderDto nearByProviderDto = new NearByProviderDto();
			nearByProviderDto.setProviderName(obj[0].toString());
			nearByProviderDto.setDow(obj[1].toString());
			nearByProviderDto.setOpenHour(Long.parseLong(obj[2].toString()));
			nearByProviderDto.setCloseHour(Long.parseLong(obj[3].toString()));
			providersNearBy.add(nearByProviderDto);
		}
		return providersNearBy;
	}

}
