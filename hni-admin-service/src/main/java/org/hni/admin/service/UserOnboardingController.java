package org.hni.admin.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.common.Constants;
import org.hni.common.email.service.EmailComponent;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.sms.service.provider.SmsServiceLoader;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.hni.user.service.UserOnboardingService;
import org.hni.user.service.UserPartialCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;

@Api(value = "/onboard", description = "Operations on NGO")
@Component
@Path("/onboard")
public class UserOnboardingController extends AbstractBaseController {

	private static final String ORG_ID = "orgId";

	private static final Logger _LOGGER = LoggerFactory.getLogger(UserOnboardingController.class);

	private static final String FIRST_NAME = "firstName";

	@Inject
	private UserOnboardingService userOnBoardingService;

	@Inject
	private OrganizationService organizationService;

	@Inject
	private UserDAO userDao;

	@Inject
	private EmailComponent emailComponent;

	@Inject
	private UserPartialCreateService userPartialCreateService;

	@Inject
	private SmsServiceLoader smsServiceLoader;

	@Inject
	private PushMessageService smsMessageService;

	@POST
	@Path("/ngo/invite")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> sendNGOActivationLink(Organization org) {
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		try {
			org.setCreatedById(getLoggedInUser().getId());
			org.setCreated(new Date());
			boolean ors = organizationService.isAlreadyExists(org);
			if (!ors) {
				Map<String, String> additional = new HashMap<>();
				additional.put("phone", org.getPhone());
				Organization organization = organizationService.save(org);
				String UUID = userOnBoardingService.buildInvitationAndSave(organization.getId(),
						getLoggedInUser().getId(), organization.getEmail(), mapper.writeValueAsString(additional));
				if (UUID == null) {
					map.put(ERROR_MSG, "A user with same email address already exist");
					return map;
				}
				emailComponent.sendEmail(organization.getEmail(), UUID, "ngo", null, null, null);
				map.put(RESPONSE, SUCCESS);
				return map;
			} else {
				map.put(ERROR_MSG, "An organization with same email address already exist");
			}
		} catch (Exception e) {
			_LOGGER.error("Serializing User object:" + e.getMessage(), e);
		}
		return map;
	}

	@POST
	@Path("/{userType}/user/invite")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> sendNGOActivationLinkToUser(@PathParam("userType") String userType,
			Map<String, String> userInfo) {
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		try {
			String message = userInfo.get("invitationMessage");
			String activationCode = userInfo.get("activationCode");
			Long organizationId;

			List<UserOrganizationRole> userOrganizationRoles = (List<UserOrganizationRole>) organizationUserService
					.getUserOrganizationRoles(getLoggedInUser());
			if (!userOrganizationRoles.isEmpty()) {
				organizationId = userOrganizationRoles.get(0).getId().getOrgId();
			} else {
				// Sets org to super user - hack
				organizationId = 1L;
			}

			String hniBaseNumber = null;

			if (userType.equalsIgnoreCase("client")) {
				List<SmsProvider> smsProviders = smsServiceLoader.getSmsProvidersByState(userInfo.get("state"));
				hniBaseNumber = getHniPhoneNumber(smsProviders);
				message = formatInvitationMessageWithPhoneNumber(message, smsProviders);
			}

			String UUID = userOnBoardingService.buildInvitationAndSave(organizationId, getLoggedInUser().getId(),
					userInfo.get("email"), mapper.writeValueAsString(userInfo));
			if (UUID != null) {
				emailComponent.sendEmail(userInfo.get("email"), UUID, userType, message, activationCode,
						mapper.writeValueAsString(userInfo));
				sendSmsInvitation(userInfo, userType, hniBaseNumber);

			} else {
				map.put(RESPONSE, ERROR);
				map.put("message", "A user with " + userInfo.get("email") + " already exists");
			}
			map.put(RESPONSE, SUCCESS);
			return map;
		} catch (Exception e) {
			_LOGGER.error("Serializing User object:" + e.getMessage(), e);
		}
		return map;
	}

	private void sendSmsInvitation(Map<String, String> userInfo, String userType, String hniBaseNumber) {
		try {
			if (userType.equalsIgnoreCase("client")) {
				String phone = userInfo.get("phone");
				if (phone != null) {
					phone = HNIConverter.convertPhoneNumberFromUiFormat(phone);
				}
				StringBuilder messageBuilder = new StringBuilder("Hi ");
				messageBuilder.append(userInfo.get("name"));
				messageBuilder.append("\n");
				messageBuilder.append("Thank you for your interest in becoming a Hunger Not Impossible Participant! "
						+ "To register with us, please reply ENROLL to this number");

				smsMessageService.sendMessage(messageBuilder.toString(), hniBaseNumber, phone);
			}
		} catch (Exception e) {
			_LOGGER.error("Failed to send SMS Invitation to user , cause : ", e);
		}

	}

	private String getHniPhoneNumber(List<SmsProvider> smsProviders) {
		return smsProviders != null && !smsProviders.isEmpty() ? smsProviders.get(0).getLongCode() : null;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/activate/{userType}/{invitationCode}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> activateNGO(@PathParam("userType") String userType,
			@PathParam("invitationCode") String invitationCode)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		List<Invitation> invitations = (List<Invitation>) userOnBoardingService.validateInvitationCode(invitationCode);

		if (!invitations.isEmpty()) {
			map.put(RESPONSE, SUCCESS);
			map.put(ORG_ID, invitations.get(0).getOrganizationId());
			map.put(USER_NAME, invitations.get(0).getEmail());
			map.put(DATA, invitations.get(0).getData());
			Map<String, String> data = new HashMap<>();

			if (invitations.get(0).getData() != null) {
				data = mapper.readValue(invitations.get(0).getData(), Map.class);
				map.put(FIRST_NAME, data.get("name"));
			}
			if (userType != null && userType.equalsIgnoreCase("client") && !data.isEmpty()) {
				map.put("dependants", data.get("dependants"));
			}

			return map;
		}
		return map;
	}

	@POST
	@Path("/{userType}/save")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> savePartialData(JSONObject json, @PathParam("userType") String userType) {
		Map<String, String> response = new HashMap<>();
		response.put(RESPONSE, ERROR);
		if (userType != null && json != null) {
			UserPartialData userPartialDataUpdate = userPartialCreateService
					.getUserPartialDataByUserId(getLoggedInUser().getId());
			if (userPartialDataUpdate == null) {
				UserPartialData userPartialData = new UserPartialData();
				userPartialData.setType(userType);
				userPartialData.setUserId(getLoggedInUser().getId());
				userPartialData.setData(json.toString());
				userPartialData.setCreated(new Date());
				userPartialData.setLastUpdated(new Date());
				userPartialData.setStatus(Constants.N);
				userPartialCreateService.save(userPartialData);
				response.put(RESPONSE, SUCCESS);
			} else {
				userPartialDataUpdate.setData(json.toString());
				userPartialDataUpdate.setLastUpdated(new Date());
				userPartialCreateService.save(userPartialDataUpdate);
				response.put(RESPONSE, SUCCESS);
			}
		}
		return response;
	}

	@POST
	@Path("/ngo/ngoSave")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response ngoSave(String onboardDataJson) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode objectNode = mapper.readTree(onboardDataJson);
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		try {

			Map<String, String> errors = userOnBoardingService.ngoSave((ObjectNode) objectNode, getLoggedInUser());
			if (errors != null && errors.isEmpty()) {
				map.put(RESPONSE, SUCCESS);
			} else {
				if (map != null)
					map.putAll(errors);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(map).build();
	}

	@POST
	@Path("/validate/username")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response isUserNameAvailable(Map<String, String> userNameInfo) {
		Map<String, String> response = new HashMap<>();
		response.put("available", "false");
		try {
			User userInfo = userDao.byEmailAddress(userNameInfo.get("username"));
			if (userInfo == null) {
				response.put("available", "true");
			}
		} catch (Exception e) {
			_LOGGER.error("Exception while processing request @ isUserNameValid", e);
			response.put(ERROR, SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN);
		}
		return Response.ok(response).build();
	}

	@GET
	@Path("/ngo/get/{ngoId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response getNGO(@PathParam("ngoId") Long ngoId) {
		Map<String, String> response = new HashMap<>();
		response.put(RESPONSE, ERROR);
		try {
			ObjectNode ngoDetailJSON = userOnBoardingService.getNGODetail(ngoId, null);
			return Response.ok(ngoDetailJSON).build();
		} catch (Exception e) {
			e.printStackTrace();
			response.put(ERROR, SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN);
		}
		return Response.ok(response).build();
	}

}
