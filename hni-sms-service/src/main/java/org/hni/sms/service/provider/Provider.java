/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.List;

import org.hni.sms.service.model.SmsMessage;

/**
 * @author Rahul
 *
 */
public interface Provider {

	SmsMessage receiveMessage();
	SmsMessage sendMessage(SmsMessage message);
	List<SmsMessage> sendBulkMessage(List<SmsMessage> messages);
	boolean getSendStatus(String messageId);
	
}
