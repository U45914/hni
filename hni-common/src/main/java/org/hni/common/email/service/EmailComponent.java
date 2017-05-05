package org.hni.common.email.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class EmailComponent {
	@Value("#{hniProperties['mail.smtp.host']}")
	private String smtpHost;
	@Value("#{hniProperties['mail.smtp.socketFactory.port']}")
	private String smtpSocketFactoryPort;
	@Value("#{hniProperties['mail.smtp.socketFactory.class']}")
	private String smtpSocket;
	@Value("#{hniProperties['mail.smtp.auth']}")
	private String smtpAuth;
	@Value("#{hniProperties['mail.smtp.port']}")
	private String smtpPort;
	@Value("#{hniProperties['mail.auth.email']}")
	private String authEmail;
	@Value("#{hniProperties['mail.auth.password']}")
	private String authPassword;

	@Value("#{hniProperties['mail.from.address']}")
	private String fromAddress;
	@Value("#{hniProperties['mail.from.name']}")
	private String fromName;

	@Value("#{hniProperties['mail.activation.url']}")
	private String activateURL;
	@Value("#{hniProperties['mail.template.sub']}")
	private String emailSubTemplate;
	@Value("#{hniProperties['mail.template.body']}")
	private String emailBodyTemplate;
	@Value("#{hniProperties['mail.template.footer']}")
	private String emailFooterTemplate;
	
	public boolean sendEmail(String receiverEmail, String UUID, String userType, String invitationMessage, String activationCode,String data)
			throws AddressException, MessagingException, JsonParseException, JsonMappingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.socketFactory.port", smtpSocketFactoryPort);
		props.put("mail.smtp.socketFactory.class", smtpSocket);
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.port", smtpPort);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authEmail, authPassword);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress, fromName));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
		message.setSubject(capitalize(userType)+" "+emailSubTemplate);
		
		message.setText(getEmailText(userType, UUID, invitationMessage, activationCode,data));

		Transport.send(message);
		return true;
	}
	
	private String getEmailText(String userType, String code,String invitationMessage, String activationCode,String data) throws JsonParseException, JsonMappingException, IOException {
		StringBuilder emailTextBuilder = new StringBuilder(50);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> dataMap = null;
		if(data!=null){
		dataMap = (Map) mapper.readValue(data, Map.class);
		emailTextBuilder.append("Dear "+dataMap.get("name"));
		}
		else{
			emailTextBuilder.append(getInviteName(userType));
		}
		emailTextBuilder.append(emailBodyTemplate);
		if (invitationMessage != null && !invitationMessage.isEmpty()) {
			emailTextBuilder.append("\n\n"+invitationMessage);
		}
		if (activationCode != null && !activationCode.isEmpty()) {
			emailTextBuilder.append("\n\n Activation Code : "+ activationCode);
		}
		emailTextBuilder.append("\n\n");
		emailTextBuilder.append(emailFooterTemplate);
		return String.format(emailTextBuilder.toString(), activateURL + userType + "/" + code);
	}
	
	private String getInviteName(String type) {
		if ("ngo".equalsIgnoreCase(type)) {
			return "Dear NGO";
		} else if ("volunteer".equalsIgnoreCase("type")) {
			return "Dear Volunteer";
		} else {
			return "Dear User";
		}
		
	}
	private String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
		}
}
