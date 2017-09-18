/**
 * 
 */
package org.hni.service.helpers;

import java.util.Calendar;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.service.ProviderLocationService;
import org.hni.provider.service.ProviderService;
import org.hni.user.om.Address;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

/**
 * @author U45914
 *
 */
@Service
public class ProviderResourceHelper extends AbstractServiceHelper {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ProviderResourceHelper.class);

	@Inject
	private ProviderService providerService;
	@Inject
	private ProviderLocationService providerLocationService;

	public String createProvider(Provider provider, User user) {
		_LOGGER.info("Request reached to create new provider");

		if (_LOGGER.isTraceEnabled()) {
			_LOGGER.trace("Request reached to create new provider : ", provider.toString());
		}

		provider.setCreatedById(user.getId());
		provider.setCreated(Calendar.getInstance().getTime());

		saveProvider(provider);

		_LOGGER.info("Completed creating new Provider : {0}", provider.getId());

		return serializeProviderToJson(provider);
	}
	
	@Transactional
	private Provider saveProvider(Provider provider) {
		return providerService.save(provider);
	}

	private String serializeProviderToJson(Provider provider) {
		try {
			String json = mapper.writeValueAsString(JsonView.with(provider)
					.onClass(Provider.class, Match.match().exclude("*").include("id", "name", "websiteUrl", "address"))
					.onClass(Address.class, Match.match().exclude("*").include("address1","address2","city","state", "zip", "longitude", "latitude")));

			return json;
		} catch (Exception e) {
			_LOGGER.error("Serializing Client object:" + e.getMessage(), e);
		}
		return "{}";
	}

	public ProviderLocation addProviderLocation(Long providerId, User user, ProviderLocation providerLocation) {
		_LOGGER.info("Request reached to add new provider location for provider {0}", providerId);

		if (_LOGGER.isTraceEnabled()) {
			_LOGGER.trace("Request reached to create new provider : ", providerLocation.toString());
		}
		
		providerLocation.setCreatedById(user.getId());
		providerLocation.setCreated(Calendar.getInstance().getTime());
		providerLocation.setIsActive(Boolean.TRUE);
		
		providerLocation.setProvider(new Provider(providerId));
		// Save provider location to database
		
		saveProviderLocation(providerLocation);
		
		return providerLocation;
	}
	
	@Transactional
	private ProviderLocation saveProviderLocation(ProviderLocation pLocation) {
		return providerLocationService.save(pLocation);
	}

	@Transactional
	public String updateProvider(Provider provider, User loggedInUser) {
		_LOGGER.info("Request reached to update provider : " + provider.getId());

		if (_LOGGER.isTraceEnabled()) {
			_LOGGER.trace("Request reached to update provider : ", provider.toString());
		}
		Provider extProvider = providerService.get(provider.getId());
		
		extProvider.setName(provider.getName());
		extProvider.setWebsiteUrl(provider.getWebsiteUrl());
		extProvider.setCreatedById(loggedInUser.getId());
		extProvider.getAddress().setAddress1(provider.getAddress().getAddress1());
		extProvider.getAddress().setAddress2(provider.getAddress().getAddress2());
		extProvider.getAddress().setCity(provider.getAddress().getCity());
		extProvider.getAddress().setState(provider.getAddress().getState());
		extProvider.getAddress().setZip(provider.getAddress().getZip());
		
		providerService.update(extProvider);
		
		return serializeProviderToJson(extProvider);
	}
}
