package org.hni.admin.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hni.common.Constants;
import org.hni.common.email.service.EmailComponent;
import org.hni.organization.om.Organization;
import org.hni.organization.service.OrganizationService;
import org.hni.user.om.Invitation;
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
	
	private static final Logger logger = LoggerFactory.getLogger(UserOnboardingController.class);
	
	private static final String ERROR_MSG = "errorMsg";

	private static final String SUCCESS = "success";

	private static final String ERROR = "error";

	private static final String RESPONSE = "response";

	@Inject
	private UserOnboardingService userOnBoardingService;

	@Inject
	private OrganizationService organizationService;

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
			logger.error("Serializing User object:"+e.getMessage(), e);
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
		Collection<Invitation> invitations = userOnBoardingService.validateInvitationCode(invitationCode);
		if (!invitations.isEmpty()) {
			map.put(RESPONSE, SUCCESS);
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

}
