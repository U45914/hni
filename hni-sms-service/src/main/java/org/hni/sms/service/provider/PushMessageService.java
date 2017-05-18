/**
 * 
 */
package org.hni.sms.service.provider;

import javax.inject.Inject;

import org.hni.order.om.Order;
import org.hni.sms.service.provider.om.SmsProvider;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class PushMessageService {
	

	@Inject
	private SMSProvider provider;
	
	public void createPushMessageAndSend(Order order) {
		//TODO : we have offer here, prepare message and send it to the volunteers
		SmsProvider smsProvider = SmsServiceLoader.providers.get("NY");
		
		provider.get(smsProvider.getProviderName()).sendMessage(null);
		
	}
}
