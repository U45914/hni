/**
 * 
 */
package org.hni.sms.service.provider.twilio;

import java.util.List;

import org.hni.sms.service.model.SmsMessage;
import org.hni.sms.service.provider.Provider;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class TwilioSmsProvider implements Provider {

	@Override
	public SmsMessage receiveMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsMessage sendMessage(SmsMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmsMessage> sendBulkMessage(List<SmsMessage> messages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSendStatus(String messageId) {
		// TODO Auto-generated method stub
		return false;
	}

}
