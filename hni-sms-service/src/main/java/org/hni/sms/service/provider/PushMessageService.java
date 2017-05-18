/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.order.om.Order;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.dao.UserDAO;
import org.hni.user.service.VolunteerService;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class PushMessageService {
	

	@Inject
	private SMSProvider provider;
	
	@Inject
	@Named("defaultVolunteerService")
	private VolunteerService volunteerService;
	
	@Inject
	private UserDAO userDAO;
	
	public void createPushMessageAndSend(Order order) {
		//TODO : we have offer here, prepare message and send it to the volunteers
		//TODO : get the list of volunteers based on availability.
		String state = userDAO.findUserState(order.getUser().getId()); 
		List<VolunteerDto> availableVolunteersByState= volunteerService.getVolunteerByState(state);
		SmsProvider smsProvider = SmsServiceLoader.providers.get(state);
		
		if(!availableVolunteersByState.isEmpty()){
			provider.get(smsProvider.getProviderName()).sendBulkMessage(null); //TODO
		}
		
	}
}
