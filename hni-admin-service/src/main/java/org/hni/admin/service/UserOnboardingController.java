package org.hni.admin.service;

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

import org.hni.common.email.service.EmailComponent;
import org.hni.organization.om.Organization;
import org.hni.organization.service.OrganizationService;
import org.hni.user.om.Invitation;
import org.hni.user.service.UserOnboardingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/onboard", description = "Operations on NGO")
@Component
@Path("/onboard")
public class UserOnboardingController extends AbstractBaseController {
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
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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
	@Path("/ngo/activate/{invitationCode}")
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

}
