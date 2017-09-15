package org.hni.provider.service;

import org.hni.common.service.AbstractService;
import org.hni.provider.dao.ProviderLocationDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.om.ProviderLocationHour;
import org.hni.user.om.Address;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DefaultProviderLocationService extends AbstractService<ProviderLocation> implements ProviderLocationService {

	private static final Logger _LOGGER = LoggerFactory.getLogger(DefaultProviderLocationService.class);
    private ProviderLocationDAO providerLocationDao;

    @Autowired
    private GeoCodingService geoCodingService;

    @Inject
    public DefaultProviderLocationService(ProviderLocationDAO providerLocationDao) {
        super(providerLocationDao);
        this.providerLocationDao = providerLocationDao;
    }

    @Override
    public Collection<ProviderLocation> with(Provider provider) {
        return this.providerLocationDao.with(provider);
    }

    @Override
    public Address searchCustomerAddress(String customerAddress) {
        return geoCodingService.resolveAddress(customerAddress).orElse(null);
    }

    @Override
    public Collection<ProviderLocation> providersNearCustomer(Address customerAddress, int itemsPerPage, double distance, double radius) {
        return providerLocationDao.providersNearCustomer(customerAddress, itemsPerPage);
    }

	@Override
	public List<ProviderLocation> locationsOf(Long providerId) {
		return providerLocationDao.locationsOf(providerId);
	}

	@Override
	public void updateProviderLocations(List<ProviderLocation> providerLocations, User loggedInUser) {
		_LOGGER.debug("Starting process for update provider locations");
		for(ProviderLocation providerLocation : providerLocations){
			ProviderLocation existingProviderLocation = get(providerLocation.getId());
			existingProviderLocation.setName(providerLocation.getName());
			Address oldAddress = existingProviderLocation.getAddress();
			Address newAddress = providerLocation.getAddress();
			oldAddress.setAddress1(newAddress.getAddress1());
			oldAddress.setAddress2(newAddress.getAddress2());
			oldAddress.setCity(newAddress.getCity());
			oldAddress.setLatitude(newAddress.getLatitude());
			oldAddress.setLongitude(newAddress.getLongitude());
			oldAddress.setState(newAddress.getState());
			existingProviderLocation.setAddress(providerLocation.getAddress());
			existingProviderLocation.setIsActive(providerLocation.getIsActive());
			existingProviderLocation.setProvider(providerLocation.getProvider());
			ProviderLocationHour provLocHourExt = existingProviderLocation.getProviderLocationHours().iterator().next();
			ProviderLocationHour provLocHourNew = providerLocation.getProviderLocationHours().iterator().next();
			provLocHourExt.setOpenHour(provLocHourNew.getOpenHour());
			provLocHourExt.setCloseHour(provLocHourNew.getCloseHour());
			existingProviderLocation.setLastUpdatedBy(loggedInUser);
			//existingProviderLocation.setLastUpdated(new Date());
			existingProviderLocation.setMenu(providerLocation.getMenu());
			providerLocationDao.update(existingProviderLocation);
		}
		
	}

}
