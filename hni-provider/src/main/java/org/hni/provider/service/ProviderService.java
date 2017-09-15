package org.hni.provider.service;

import java.util.List;

import org.hni.common.service.BaseService;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;

public interface ProviderService extends BaseService<Provider> {

	Provider getProviderDetails(Long providerId, User loggedInUser);
	List<ProviderLocation> getProviderLocations(Long providerId, User loggedInUser);
	
}
