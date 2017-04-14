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

import org.hni.common.Constants;
import org.hni.common.email.service.EmailComponent;
import org.hni.organization.om.Organization;
import org.hni.organization.service.OrganizationService;
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
				Organization organization = organizationService.save(org);
				String UUID = userOnBoardingService.buildInvitationAndSave(organization.getId());
				emailComponent.sendEmail(organization.getEmail(), UUID);
				map.put(RESPONSE, SUCCESS);
				return map;
			} else {
				map.put(ERROR_MSG, "An organization with same email address already exist");
			}
		} catch (Exception e) {
			_LOGGER.error("Serializing User object:"+e.getMessage(), e);
		}
		return map;
	}

	@GET
	@Path("/activate/ngo/{invitationCode}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> activateNGO(@PathParam("invitationCode") String invitationCode) {
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		List<Invitation> invitations = (List<Invitation>) userOnBoardingService.validateInvitationCode(invitationCode);
		if (!invitations.isEmpty()) {
			map.put(RESPONSE, SUCCESS);
			map.put(ORG_ID, invitations.get(0).getOrganizationId());
			return map;
		}
		return map;
	}


	@POST
	@Path("/{userType}/save")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String,String> savePartialData(JSONObject json , @PathParam("userType") String userType){
		Map<String,String> response = new HashMap<>();
		response.put(RESPONSE, ERROR);
		if(userType!=null && json != null){
			UserPartialData userPartialDataUpdate = userPartialCreateService.getUserPartialDataByUserId(getLoggedInUser().getId().intValue());
			if(userPartialDataUpdate==null){
				UserPartialData userPartialData = new UserPartialData();
				userPartialData.setType(Constants.USER_TYPES.get(userType).intValue());
				userPartialData.setUserId(getLoggedInUser().getId().intValue());
				userPartialData.setData(json.toString());
				userPartialData.setCreated(new Date());
				userPartialData.setLastUpdated(new Date());
				userPartialData.setStatus(Constants.N);
				userPartialCreateService.save(userPartialData);
				response.put(RESPONSE, SUCCESS);
			}
			else{
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
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = ""
	, notes = ""
	, response = Map.class
	, responseContainer = "")
	public Map<String, String> ngoSave(String onboardDataJson) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode objectNode = mapper.readTree(onboardDataJson);
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		try {
			 
			Map<String, String> errors = userOnBoardingService.ngoSave((ObjectNode) objectNode);
			 if(errors!=null && errors.isEmpty()){
			map.put(RESPONSE, SUCCESS);
			 }
			 else{
				 if(map!=null)
					 map.putAll(errors);
			 }
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@POST
	@Path("/validate/username")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = ""
	, notes = ""
	, response = Map.class
	, responseContainer = "")
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

}
