package org.hni.events.service;

import org.hni.common.HNIUtils;
import org.hni.events.service.dao.RegistrationStateDAO;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.RegistrationStep;
import org.hni.organization.service.OrganizationUserService;
import org.hni.events.service.om.RegistrationState;
import org.hni.security.service.ActivationCodeService;
import org.hni.security.utils.HNISecurityUtils;
import org.hni.type.HNIRoles;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.service.UserOnboardingService;
import org.hni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class RegisterService extends AbstractRegistrationService<User> {

	private static final String I_ACCEPT = "i accept";

	private static final String THANK_YOU_FOR_REGISTERING_WITH_HUNGER_NOT_IMPOSSIBLE = "Thank you for registering with HungerNotImpossible. ";

	private static final String DEPENDANTS = "dependants";

	private static final String REPLY_OK = "OK, ";

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);

	public static String MSG_PRIVACY = "PRIVACY";
	public static String MSG_NONE = "NONE";

	public static String REPLY_WELCOME = "Welcome to Hunger Not Impossible! Msg and data rates may apply. "
			+ "Information you provide will be kept private. "
			+ "Reply with PRIVACY to learn more. Let's get you registered. What's your first name?";
	public static String REPLY_PRIVACY = "HNI respects your privacy and protects your data. "
			+ "For more details on our privacy please visit http://hungernotimpossible.com/#/agreement-policy "
			+ "In order to continue the registration. ";

	public static String REPLY_NO_UNDERSTAND = "I didn't understand your last message. ";
	public static String REPLY_REQUEST_FIRST_NAME = "What's your first name?";
	public static String REPLY_REQUEST_LAST_NAME = " Thanks %s. What's your last name?";
	public static String REPLY_REQUEST_EMAIL = "I'd like to get your email address "
			+ "to verify your account in case you text me from a new number.";

	public static String REPLY_EMAIL_NONE = "You don't have an email address. Is that correct? Reply 1 for yes and 2 for no.";
	public static String REPLY_EMAIL_CONFIRM = "I have %s as your email address. Is that correct? Reply 1 for yes and 2 for no.";
	public static String REPLY_EMAIL_INVALID = "I'm sorry that is not a valid email. Try again or type '%s' if don't have an email.";
	public static String REPLY_EMAIL_REQUEST = "Enter your email address.";
	public static String REPLY_EMAIL_REGISTERED = "E-mail: %s has already been registered. Please re-enter e-mail.";

	public static String REPLY_AUTHCODE_REQUEST = "Please enter the 6 digit authorization code provided to you for this program.";
	public static String REPLY_AUTHCODE_INVALID = "The authorization code you entered (%s) is invalid. Please resend a valid unused authorization code.";
	public static String REPLY_AUTHCODE_ADDED = "I've added the authorization code to your family account.";
	public static String REPLY_AUTHCODE_ADDITIONAL = " If you have additional family members to register, enter the authorization codes now, one at a time.";

	public static String REPLY_REGISTRATION_COMPLETE = "Ok. You're all set up for yourself. When you need a meal just text HUNGRY back to this number. ";
	public static String REPLY_EXCEPTION_START_OVER = "Oops, an error occured. Please start over again.";

	public static String REPLY_PASSWORD_REQUEST = "What would you like your password to be ?, Eg: Abc@1234";
	public static String REPLY_PASSWORD_CONFIRM = "I have %s as your password. Is that correct? Reply 1 for yes and 2 for no.";
	public static String REPLY_PASSWORD_INVALID = "Please reply a valid password";
	public static String REPLY_HOW_MANY_CHILDREN_DO_YOU_HAVE = "Lastly, How many children do you have ?";

	public static String REPLY_OK_YOUR_PASSWORD_HAS_BEEEN_SET = "OK, Your password has beeen set. ";
	public static String REPLY_INVALID_INPUT_FOR_CHILDREN = "Invalid input %s , Reply NONE if you have no children.";

	public static String REPLY_ACCEPT_POLICY = "Please follow this link http://hungernotimpossible.com/#/agreement-policy to review the terms and conditions, and reply I ACCEPT to complete your enrollment";
	public static String REPLY_ACCEPT_POLICY_ERROR = "Invalid entry, You must accept our terms and conditions to enroll with HNI, reply I ACCEPT to complete your enrollment";
	@Inject
	private UserService customerService;
	
	@Inject
	private OrganizationUserService orgUserService;

	@Inject
	private ActivationCodeService activationCodeService;

	@Inject
	private RegistrationStateDAO registrationStateDAO;
	
	@Inject
	private UserOnboardingService userOnboardingService;

	@PostConstruct
	void init() {
		setRegistrationStateDAO(registrationStateDAO);
	}

	@Override
	protected WorkFlowStepResult performWorkFlowStep(final Event event, final RegistrationState registrationState) {
		final String returnString;
		RegistrationStep nextStateCode = registrationState.getRegistrationStep();
		final User user = registrationState.getPayload() != null
				? deserialize(registrationState.getPayload(), User.class) : new User();
		final String textMessage = event.getTextMessage();
		switch (RegistrationStep.fromStateCode(registrationState.getRegistrationStep().getStateCode())) {
		case STATE_REGISTER_START:
			user.setMobilePhone(event.getPhoneNumber());
			nextStateCode = RegistrationStep.STATE_REGISTER_GET_FIRST_NAME;
			returnString = REPLY_WELCOME;
			break;
		case STATE_REGISTER_GET_FIRST_NAME:
			if (!textMessage.equalsIgnoreCase(MSG_PRIVACY)) {
				user.setFirstName(textMessage);
				// validate the first name
				if (customerService.validate(user)) {
					nextStateCode = RegistrationStep.STATE_REGISTER_GET_LAST_NAME;
					returnString = String.format(REPLY_REQUEST_LAST_NAME, textMessage);
				} else {
					returnString = REPLY_NO_UNDERSTAND + REPLY_REQUEST_FIRST_NAME;
				}
			} else {
				returnString = REPLY_PRIVACY + REPLY_REQUEST_FIRST_NAME;
			}
			break;
		case STATE_REGISTER_GET_LAST_NAME:
			user.setLastName(textMessage);
			// validate the last name
			if (customerService.validate(user)) {
				nextStateCode = RegistrationStep.STATE_REGISTER_GET_EMAIL;
				returnString = String.format(REPLY_REQUEST_EMAIL);
			} else {
				returnString = REPLY_NO_UNDERSTAND + REPLY_REQUEST_LAST_NAME;
			}
			break;
		case STATE_REGISTER_GET_EMAIL:
			user.setEmail(textMessage);
			// validate the email
			if (customerService.validate(user)) {
				if(customerService.byEmailAddress(user.getEmail()) == null){
					nextStateCode = RegistrationStep.STATE_REGISTER_CONFIRM_EMAIL;
					if ("none".equalsIgnoreCase(textMessage)) {
						returnString = REPLY_EMAIL_NONE;
					} else {
						returnString = String.format(REPLY_EMAIL_CONFIRM, user.getEmail());
					}
				} else {
					returnString = String.format(REPLY_EMAIL_REGISTERED, user.getEmail());
				}
			} else {
				returnString = String.format(REPLY_EMAIL_INVALID, MSG_NONE);
			}
			break;
		case STATE_REGISTER_CONFIRM_EMAIL:
			switch (textMessage) {
			case "2":
				user.setEmail(null);
				nextStateCode = RegistrationStep.STATE_REGISTER_GET_EMAIL;
				returnString = REPLY_EMAIL_REQUEST;
				break;
			case "1":
				nextStateCode = RegistrationStep.STATE_REGISTER_GET_PASSWORD;
				returnString = REPLY_PASSWORD_REQUEST;
				break;
			default:
				nextStateCode = RegistrationStep.STATE_REGISTER_CONFIRM_EMAIL;
				returnString = String.format(REPLY_EMAIL_CONFIRM, user.getEmail());
				break;
			}

			break;
		case STATE_REGISTER_GET_PASSWORD:
			if (textMessage == null || textMessage.isEmpty()) {
				nextStateCode = RegistrationStep.STATE_REGISTER_GET_PASSWORD;
				returnString = REPLY_PASSWORD_INVALID;
				break;
			} else {
				user.setPassword(textMessage);
				nextStateCode = RegistrationStep.STATE_REGISTER_CONFIRM_PASSWORD;
				returnString = String.format(REPLY_PASSWORD_CONFIRM, user.getPassword());
			}
			break;
		case STATE_REGISTER_CONFIRM_PASSWORD:
			switch (textMessage) {
			case "2":
				nextStateCode = RegistrationStep.STATE_REGISTER_GET_PASSWORD;
				returnString = REPLY_OK + REPLY_PASSWORD_REQUEST;
				break;
			case "1":
				/*nextStateCode = RegistrationStep.STATE_REGISTER_NUMBER_OF_CHILDREN;
				returnString = REPLY_OK_YOUR_PASSWORD_HAS_BEEEN_SET + REPLY_HOW_MANY_CHILDREN_DO_YOU_HAVE;*/
				nextStateCode = RegistrationStep.STATE_REGISTER_ACCEPT_POLICY;
				returnString = REPLY_ACCEPT_POLICY;
				break;
			default:
				nextStateCode = RegistrationStep.STATE_REGISTER_CONFIRM_PASSWORD;
				returnString = String.format(REPLY_PASSWORD_CONFIRM, user.getPassword());
				break;
			}
			break;
		case STATE_REGISTER_NUMBER_OF_CHILDREN:
			Integer childrens = 0;
			if (textMessage.equalsIgnoreCase("none")) {
				returnString = REPLY_REGISTRATION_COMPLETE;
				nextStateCode = RegistrationStep.STATE_REGISTER_ACCEPT_POLICY;
				user.getAdditionalInfo().put(DEPENDANTS, childrens);
			} else {
				childrens = HNIUtils.getNumber(textMessage);

				if (HNIUtils.isPositiveNumeric(childrens)) {
					user.getAdditionalInfo().put(DEPENDANTS, childrens);
					nextStateCode = RegistrationStep.STATE_REGISTER_ACCEPT_POLICY;
					returnString = REPLY_ACCEPT_POLICY;
				} else {
					nextStateCode = RegistrationStep.STATE_REGISTER_NUMBER_OF_CHILDREN;
					returnString = String.format(REPLY_INVALID_INPUT_FOR_CHILDREN, textMessage);
					break;
				}
			}
			break;
		case STATE_REGISTER_ACCEPT_POLICY:
			if (textMessage.equalsIgnoreCase(I_ACCEPT)) {
				returnString = THANK_YOU_FOR_REGISTERING_WITH_HUNGER_NOT_IMPOSSIBLE + REPLY_REGISTRATION_COMPLETE;
				registerUserAndSetActivationCodes(user);
			} else {
				returnString = REPLY_ACCEPT_POLICY_ERROR;
				nextStateCode = RegistrationStep.STATE_REGISTER_ACCEPT_POLICY;
			}
			break;
			
		default:
			LOGGER.error("Unknown state {}", registrationState.getRegistrationStep());
			returnString = REPLY_EXCEPTION_START_OVER;
			break;
		}
		return new WorkFlowStepResult(returnString, nextStateCode, serialize(user));
	}
	
	private User registerUserAndSetActivationCodes(User user) {
		user.setOrganizationId(1L);
		HNISecurityUtils.setHashSecret(user, user.getPassword());
		user.setPassword("");
		Invitation invitation = userOnboardingService.getInvitationByPhoneNumber(user.getMobilePhone());
		if (invitation != null) {
			orgUserService.register(user, HNIRoles.CLIENT.getRole());
			int dependentCount = invitation.getDependantsCount() != null ? invitation.getDependantsCount() : 0;
			activationCodeService.saveActivationCodes(user, dependentCount);
		} else {
			LOGGER.debug("Unable to find a valid invitation record for the user");
		}
		return user;
	}
}
