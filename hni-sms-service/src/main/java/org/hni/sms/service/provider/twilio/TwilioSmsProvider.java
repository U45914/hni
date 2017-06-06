/**
 * 
 */
package org.hni.sms.service.provider.twilio;

import java.util.List;

import javax.inject.Inject;

import org.hni.sms.service.model.SmsMessage;
import org.hni.sms.service.provider.Provider;
import org.hni.sms.service.rs.client.SMSRestClient;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class TwilioSmsProvider implements Provider {
	
	@Inject
	private SMSRestClient client;

	@Override
	public SmsMessage receiveMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsMessage sendMessage(SmsMessage message) {
		//Response messageResponse = client.pushMessage(formatMessageForPush(message));
		//System.out.println(messageResponse);
		client.sendMessageWithTwilio(message);
		return null;
	}

	@Override
	public List<SmsMessage> sendBulkMessage(List<SmsMessage> messages) {
		for (SmsMessage message : messages) {
			sendMessage(message);
		}
		
		return null;
	}

	@Override
	public boolean getSendStatus(String messageId) {
		// TODO Auto-generated method stub
		return false;
	}

	protected String formatMessageForPush(SmsMessage message) {
		StringBuilder queryBuilder = new StringBuilder();
		// Set to Number
		queryBuilder.append("To");
		queryBuilder.append("=");
		queryBuilder.append(message.getToNumber());
		queryBuilder.append("&");
		// Set from number
		queryBuilder.append("From");
		queryBuilder.append("=");
		queryBuilder.append(message.getFromNumber());
		queryBuilder.append("&");
		// Set body
		queryBuilder.append("Body");
		queryBuilder.append("=");
		queryBuilder.append(message.getText());
		queryBuilder.append("&");
    	
    	return queryBuilder.toString();
	}
}
