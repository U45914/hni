/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.order.om.Order;
import org.hni.order.om.OrderItem;
import org.hni.sms.service.model.SmsMessage;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.dao.UserDAO;
import org.hni.user.service.VolunteerService;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("#{hniProperties['hni.homepage']}")
	private String hniHomepage;

	@Inject
	private UserDAO userDAO;

	public void createPushMessageAndSend(Order order) {

		String state = order.getProviderLocation().getAddress().getState();

		List<VolunteerDto> volunteersList = volunteerService.getVolunteerByState(state, true);

		SmsProvider smsProvider = SmsServiceLoader.getProviders().get(state.toUpperCase());
		if(volunteersList != null){
			if (!volunteersList.isEmpty()) {
				for (VolunteerDto volunteer : volunteersList) {
					provider.get(smsProvider.getProviderName()).sendMessage(prepareMessage(volunteer, order, smsProvider));
				}
			}
		}
	}

	private SmsMessage prepareMessage(VolunteerDto voulunteer, Order order, SmsProvider smsProvider) {
		SmsMessage message = new SmsMessage();

		message.setToNumber("+1" + voulunteer.getPhoneNumber());
		// TODO : Set volunteer name in message
		message.setFromNumber("+1" + smsProvider.getLongCode());
		message.setText(getMessageText(order));

		return message;
	}

	private SmsMessage createMessageObject(String message, String from, String to) {
		SmsMessage messageObject = new SmsMessage();
		messageObject.setToNumber("+1" + to);
		messageObject.setFromNumber("+1" + from);
		messageObject.setText(message);
		
		return messageObject;
	}
	private String getMessageText(Order order) {
		StringBuilder builder = new StringBuilder();
		builder.append("Please place the order for ");
		String userName = order.getUser().getFirstName() + " " + order.getUser().getLastName().substring(0, 1).toUpperCase() + ".";
		builder.append(userName);		
		builder.append(" Order Item : ");
		OrderItem orderItem = order.getOrderItems().iterator().next();
		builder.append(orderItem.getMenuItem().getName());
		builder.append(" Total Price : $");
		builder.append(order.getSubTotal());
		// Below items to be checked before final changes
		builder.append(" www.hungernotimpossible.com");
		
		return builder.toString();

	}
	
	public boolean sendMessage(String message, String from, String to) {
		
		provider.get(ServiceProvider.TWILIO).sendMessage(createMessageObject(message, from, to));
		
		return false;
	}
}
