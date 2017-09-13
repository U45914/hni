/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.order.om.Order;
import org.hni.order.om.OrderItem;
import org.hni.sms.service.model.SmsMessage;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.dao.UserDAO;
import org.hni.user.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(PushMessageService.class);

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
		builder.append(" Order ID : HNI");
		builder.append(order.getId());
		builder.append(String.valueOf(order.getProviderLocation().getAddress().getState()).toUpperCase());
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
	
	public String getProviderNumberForState(String stateCode) {
		SmsProvider smsProvider = SmsServiceLoader.getProviders().get(stateCode.toUpperCase());
		if (smsProvider == null) {
			return SmsServiceLoader.getProviders().get("MO").getLongCode();
		} else {
			return smsProvider.getLongCode();
		}
	}
	
	public Map<String, Object> sendMessageToAllActiveParticipants(String messageContent) throws InterruptedException {
		Map<String, Object> messageSids = new HashMap<>();
		_LOGGER.info("**********Started Broadcasting messages to Active participants*************");
		String[] userFromMO = "9496079136,3144793437,3142957975,3145650056,3142977524,3143761514,3142102818,6365418476,3146624484,3142957944,2134785766,3463074253,3147019887,3143662130,3143930913,3145322307,3143269582,3144584573,3144580983,6785988940,3146883619,3145658136,3146006363,3143955968,3146430565,2022971543,3144377618,3148107899,3143059249,3143091262,3142033225,3147938274,3143054345,3143129491,3145854124,3144222747,6182101959,3143272432,3147571659,7708710108,3148859101,3143052571,3142763112,6185787832,3145003654,3149336706,3144068490,3143931707,3145045926,3146650184,6189606360,3147368234,3149109358,3146883975,3146084119,3147577813,3142802861,3146887803,3146590817,3145563763,3142431219,3146350192,3143878198,3145833432,3148993298,6362816206,3143054390,3143544832,3147174232,3147663621,3143490576,3143056836,3143490285,3142522584,3142404191,3144972289,3143245390,3142104986,3149224985,3145856394,3143913854,3146032853,3146291995,4143231190,3145563931,3146370849,3149200655,3145039097,3145365386,3142033161,3142879728,3143271226,2403447516,3142838080,6185319026,3142033976,3147280207,3147040571,6189756951,3142584967,3146885117,3143088745,3143059712,6306664826,8478458874,8183714597,3147836333,3147096756,3147498238,3147096757,3147096365,3144435270,3142408599,3144972988,6187224858,3143054390,3142741787,3142854110,3145187282,3146807582,6182109434,3142672068,3147820490,3144826834,3147550589,3145624339,3147450224,3142295669,6186702026,4433602218,3146659403,3142407830,3143749642,3144826409,3142241344,3142264498,3145914443,6369001567,3144824627,2022971543,3142673144,3146296169,3143978922,3144940694,3148858424,3143191506,3142997634,3143196579,9018642941,3147619746,3145740533,3146512459,3054174965,3108552415,3144979608,3146294031,3143380413,3145409355,3147613024,3143766892,3144573878,3142409703,6188822288,3144787496,3144947405,3146882070,3168477413,3144746484,3142302091,3144779620,3107760200,3144057024,3148850973,3147929330,3142492067,3145688132,9176552491,3147013273,3146624872,3142036660,3145660308,3147371130,3144802650,3144857392,3145043047,3142769214,3147456095,3145181902,3144564442,3144568760,3146650312,6186705596,6188554585,3142212892,3142658430,3148770831,3142407014,3142821344,3144897845,3143544492,3146886208,3146011601,3147020378,3148852157,3142821331,3144892152,3142888923,3144969661,3146658148".split(",");
		List<String> participantsFromMO = Arrays.asList(userFromMO);
		
		String[] usersFromOR = "5044950873,5418108645,5413314222,9702756881,5415910099,5413632211,5418916343,5418918782,5418915730,5415398537,5418511049,5419748703,5038413551,5038413593,5412384788,5418547673,5415399170,5413639777,5413311657,5415913074,5418922836,5418109902,5415913968,5415390502,5418911252,5413313190,5418103419,5415912861,5412384216,5418100712,5418910807,5418921684,5415911622,5418923619,5418918357,5413315708,5418915796,3142406388,5415917188,5418919465,5418912666,4807211289,5412741761,5412741769,5415915621,5418411682,5418102100,5413311418".split(",");
		
		List<String> participantsFromOR = Arrays.asList(usersFromOR);
		_LOGGER.info("************************************************************");
		_LOGGER.info("Total Participants in MO : " + participantsFromMO.size());
		_LOGGER.info("MO Participant PhoneNumbers\n" + participantsFromMO.toString());
		_LOGGER.info("************************************************************");		
		_LOGGER.info("************************************************************");

		SmsProvider providerForMO = SmsServiceLoader.getProviders().get("MO");
		if( participantsFromMO != null && !participantsFromMO.isEmpty()){
			for(String participant : participantsFromMO){
				try {
					messageSids.put(participant, "Not Sent");
					_LOGGER.info("Staretd Sending message to Participant "+ participant);
					String sid = provider.get(providerForMO.getProviderName())
							.sendMessage(createMessageObject(messageContent, providerForMO.getLongCode(), participant));
					messageSids.put(participant, sid);
					_LOGGER.info("Completed Sending message to Participant "+ participant);
				} catch (Exception e) {
					_LOGGER.error("Exception while sending messages", e);
				}
				Thread.sleep(3000L);
			}
		}
		_LOGGER.info("************************************************************");		
		_LOGGER.info("************************************************************");
		_LOGGER.info("Total Participants in OR : " + participantsFromOR.size());
		_LOGGER.info("OR Participant PhoneNumbers\n" + participantsFromOR.toString());
		_LOGGER.info("************************************************************");
		_LOGGER.info("************************************************************");
		
		
		SmsProvider providerForOR = SmsServiceLoader.getProviders().get("OR");
		if( participantsFromOR != null && !participantsFromOR.isEmpty()){
			for(String participant : participantsFromOR){
				messageSids.put(participant, "Not Sent");
				try {
					_LOGGER.info("Staretd Sending message to Participant "+ participant);
					String sid = provider.get(providerForOR.getProviderName())
							.sendMessage(createMessageObject(messageContent, providerForOR.getLongCode(), participant));
					messageSids.put(participant, sid);
					_LOGGER.info("Completed Sending message to Participant "+ participant);
				} catch (Exception e) {
					_LOGGER.error("Exception while sending messages", e);
				}
				Thread.sleep(3000L);
			}
		}
		return messageSids;
	}
	
}
