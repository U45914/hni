package org.hni.admin.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.converter.HNIConverter;
import org.hni.admin.service.dto.HniServicesDto;
import org.hni.common.Constants;
import org.hni.common.exception.HNIException;
import org.hni.common.om.Role;
import org.hni.organization.om.HniServices;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationUserService;
import org.hni.passwordvalidater.CheckPassword;
import org.hni.security.dao.RoleDAO;
import org.hni.security.om.ActivationCode;
import org.hni.security.service.ActivationCodeService;
import org.hni.type.HNIRoles;
import org.hni.user.om.Client;
import org.hni.user.om.Ngo;
import org.hni.user.om.Report;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.hni.user.om.Volunteer;
import org.hni.user.service.ReportServices;
import org.hni.user.service.UserOnboardingService;
import org.hni.user.service.UserPartialCreateService;
import org.hni.user.service.UserService;
import org.hni.user.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;

@Api(value = "/users", description = "Operations on Users and to manage Users relationships to organiations")
@Component
@Path("/users")
public class UserServiceController extends AbstractBaseController {
	private static final Logger _LOGGER = LoggerFactory.getLogger(UserServiceController.class);

	@Inject
	private OrganizationUserService orgUserService;
	@Inject
	private RoleDAO roleDao;

	@Inject
	@Named("defaultUserService")
	private UserService userService;

	@Inject
	@Named("defaultVolunteerService")
	private VolunteerService volunteerService;

	@Context
	private HttpServletRequest servletRequest;
	
	@Inject
	private UserOnboardingService userOnBoardingService;
	
	@Inject
	private UserPartialCreateService userPartialCreateService;

	@Inject
	private ReportServices reportServices;
	
	@Inject
	private ActivationCodeService activationCodeService;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the user with the given id", notes = "", response = User.class, responseContainer = "")
	public Response getUser(@PathParam("id") Long id) {
		// return orgUserService.get(id);
		return Response.ok(orgUserService.get(id), MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Creates a new user or updates an existing one and returns it", notes = "An update occurs if the ID field is specified", response = User.class, responseContainer = "")
	public User addOrSaveUser(User user) {
		if (isPermitted(Constants.ORGANIZATION, Constants.CREATE, 0L)) {
			return orgUserService.save(user);
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@DELETE
	@Path("/{id}/organizations/{orgId}/roles/{roleId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Removes a user's role from the given organization", notes = "", response = User.class, responseContainer = "")
	public String deleteUser(@PathParam("id") Long id, @PathParam("orgId") Long orgId,
			@PathParam("roleId") Long roleId) {
		if (isPermitted(Constants.ORGANIZATION, Constants.DELETE, id)) {
			User user = new User(id);
			Organization org = new Organization(orgId);
			orgUserService.delete(user, org, Role.get(roleId));
			return "OK";
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@PUT
	@Path("/{id}/organizations/{orgId}/roles/{roleId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Adds the user to an organization with a specific role", notes = "", response = UserOrganizationRole.class, responseContainer = "")
	public UserOrganizationRole addUserToOrg(@PathParam("id") Long id, @PathParam("orgId") Long orgId,
			@PathParam("roleId") Long roleId) {
		if (isPermitted(Constants.ORGANIZATION, Constants.UPDATE, id)) {
			User user = new User(id);
			Organization org = new Organization(orgId);
			Role role = Role.get(roleId);
			if (null != user && null != org && null != role) {
				return orgUserService.associate(user, org, Role.get(roleId));
			}
			throw new HNIException("One of the ID's you sent was invalid...");
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@GET
	@Path("/organizations/{orgId}/roles/{roleId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns a collection of users for the given organization with the given roleId.", notes = "", response = User.class, responseContainer = "")
	public Collection<User> getOrgUsers(@PathParam("orgId") Long orgId, @QueryParam("roleId") Long roleId) {
		Organization org = new Organization(orgId);
		return orgUserService.getByRole(org, Role.get(roleId));
	}

	@GET
	@Path("/roles")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns a collection of all potential roles for users in the system.", notes = "", response = User.class, responseContainer = "")
	public Collection<Role> getUserRoles() {

		return roleDao.getAll();
	}

	@DELETE
	@Path("/{id}/organizations/{orgId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Removes a user from the given organization", notes = "", response = User.class, responseContainer = "")
	public String deleteUserFromOrg(@PathParam("id") Long id, @PathParam("orgId") Long orgId) {
		if (isPermitted(Constants.ORGANIZATION, Constants.DELETE, id)) {
			User user = new User(id);
			Organization org = new Organization(orgId);
			orgUserService.delete(user, org);
			return "OK";
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@GET
	@Path("/userinfo")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns info about the user in the current thread context", notes = "", response = User.class, responseContainer = "")
	public User getUser() {
		Long userId = (Long) ThreadContext.get(Constants.USERID); 
		return orgUserService.get(userId);
	}

	@GET
	@Path("/organizations")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "All users for all organizations..yikes!", notes = "", response = User.class, responseContainer = "")
	public Collection<User> getUsersByRole(@QueryParam("roleId") Long roleId) {
		if (SecurityUtils.getSubject().hasRole(Constants.SUPER_USER.toString())) {
			return orgUserService.byRole(Role.get(roleId));
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@GET
	@Path("/services")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the various user services/functionalities", notes = "", response = HniServicesDto.class, responseContainer = "")
	public Response getUserunctionalities() {
		_LOGGER.info("Invoked method to retrieve hni services...");
		Map<String, Object> userResponse = new HashMap<>();
		if (isPermitted(Constants.USERID, Constants.PERMISSIONS, 0L)) {
			User user = getLoggedInUser();
			if (null != user) {
				_LOGGER.info("User details fetch successfull");
				List<UserOrganizationRole> userOrganisationRoles = (List<UserOrganizationRole>) orgUserService.getUserOrganizationRoles(user);
				Collection<HniServices> hniServices = orgUserService.getHniServices(userOrganisationRoles);
				userResponse.put("data", HNIConverter.convertToServiceDtos(hniServices));
				userResponse.put("profileStatus", orgUserService.getProfileStatus(user));
				userResponse.put("role", getRoleName(userOrganisationRoles.get(0).getId().getRoleId()));
				
				return Response.ok(userResponse).build();
			}
		}
		_LOGGER.info("Not enough permissions...");
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@POST
	@Path("/ngoOnboarding")
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Accepts NGO Onboarding as parameter", notes = "", response = String.class, responseContainer = "")
	public String onBoardNGO(Ngo ngo) {
		System.out.println("Status OK");
		return "Status OK";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/register")
	@ApiOperation(value = "register a customer", notes = "An update occurs if the ID field is specified", response = User.class, responseContainer = "")
	public Response registerUser(User user, @HeaderParam("user-type") String type, @HeaderParam("invite-code") String invitaionCode, 
			@HeaderParam("act-code") String activationCode) {
		Map<String, String> userResponse = new HashMap<>();
		boolean validPassword = false;
		validPassword = CheckPassword.passwordCheck(user);
		if (validPassword == true) {
			User u = orgUserService.register(setPassword(user), convertUserTypeToRole(type));
			if (u != null) {
				UserPartialData userProfileTempInfo = new UserPartialData();
				userProfileTempInfo.setUserId(u.getId());
				userProfileTempInfo.setLastUpdated(new Date());
				userProfileTempInfo.setCreated(new Date());
				userProfileTempInfo.setStatus(Constants.N);
				userProfileTempInfo.setType(type);
				userProfileTempInfo.setData("{}");
				// Saving user data to userProfileTable for user profile redirection
				userPartialCreateService.save(userProfileTempInfo);
				if(type.equalsIgnoreCase("client") && StringUtils.isNotEmpty(activationCode) && activationCodeService.validate(activationCode)) {
					ActivationCode activationCodeEnity = activationCodeService.getByActivationCode(activationCode);
					activationCodeEnity.setUser(u);
					activationCodeService.update(activationCodeEnity);
				}
				userOnBoardingService.finalizeRegistration(invitaionCode);
				userResponse.put(SUCCESS, "Account has been created successfully");
			} else {
				userResponse.put(SUCCESS, SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN);
			}

			return Response.ok(userResponse).build();
		} else {
			userResponse.put(ERROR, "Wrong password format");
			return Response.ok(userResponse).build();
		}
	}
	
	@GET
	@Path("/volunteer")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the volunteer details of specified id.", notes = "", response = Volunteer.class, responseContainer = "")
	public Volunteer getVolunteerById(@QueryParam("id") Long volunteerId) {
		//TODO: Get user object and set Address to Volunteer
		return volunteerService.getVolunteerDetails(volunteerId);
	}

	@POST
	@Path("/client/save")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Service for saving details of Client", notes = "", response = Map.class, responseContainer = "")
	public Response clientOnBoarding(Client client){
		Map<String,String> response = new HashMap<>();
		try{
			User user = getLoggedInUser();
			user.setAddresses(getAddressSet(client.getAddress()));
			userService.update(user);
			Map<String,String> errors = userOnBoardingService.clientSave(client, user);
			if(errors!=null && errors.isEmpty()){
				response.put(RESPONSE, SUCCESS);
			}
			else{
				if(errors!=null){
					response.putAll(errors);
				}
			}
		}catch(Exception e){
			_LOGGER.error("Client save failed!");
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/volunteer/save")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Service for saving details of Volunteer", notes = "", response = Map.class, responseContainer = "")
	public Response volunteerOnBoarding(Volunteer volunteer){
		User user = getLoggedInUser();
		Map<String,String> response = new HashMap<>();
		try{
			Map<String,String> errors =   userOnBoardingService.buildVolunteerAndSave(volunteer, user);
			if(errors!=null && errors.isEmpty()){
				response.put(RESPONSE, SUCCESS);
			}
			else{
				if(errors!=null){
					response.putAll(errors);
				}
			}
		}catch(Exception e){
			_LOGGER.error("Volunteer save failed!");
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/volunteer/availability/save")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Service for saving details of Volunteer", notes = "", response = Map.class, responseContainer = "")
	public Response saveVolunteerAvailability(String availabilityJSON){
		Map<String,String> response = new HashMap<>();
		try {
			ObjectNode objectNode = new ObjectMapper().readValue(availabilityJSON, ObjectNode.class);
			response = userOnBoardingService.saveVolunteerAvailability(objectNode);
		} catch (Exception e) {
			_LOGGER.error("Save VolunteerAvailability Failed");
			response.put(ERROR, "Save VolunteerAvailability Failed");
		}
		return Response.ok(response).build();
	}
	
	@GET
	@Path("/volunteer/availability")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Service for saving details of Volunteer", notes = "", response = Map.class, responseContainer = "")
	public Response saveVolunteerAvailability(){
		Map<String,String> response = new HashMap<>();
		try {
			
		} catch (Exception e) {
			_LOGGER.error("Save VolunteerAvailability Failed");
			response.put(ERROR, "Save VolunteerAvailability Failed");
		}
		return Response.ok(response).build();
	}
	

	@GET
	@Path("/{type}/profile")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns Profile data of logged in user", notes = "", response = Map.class, responseContainer = "")
	public Response getUserProfiles(@PathParam("type") String type) {
		Map<String, Object> response = null;
		try {
			User user = getLoggedInUser();
			Long userId = null;
			if (user != null) {
				userId = user.getId();
			} else {
				return Response.serverError().build();
			}
			if(!orgUserService.getProfileStatus(user)){
				response= new HashMap<>();
				String profileData = userPartialCreateService.getUserPartialDataByUserId(user.getId()).getData();
				
				ObjectNode profile = mapper.readValue(profileData, ObjectNode.class);
				response.put(Constants.RESPONSE, profile);
				return Response.ok(response).build();
			}
			response = userOnBoardingService.getUserProfiles(type, userId);
			if (response != null && !response.isEmpty()) {
				return Response.ok(response).build();
			}

		} catch (Exception e) {
			_LOGGER.error("User Profile fetching failed!");
			return Response.serverError().build();
		}
		return Response.ok(response).build();
	}
	@GET
	@Path("/reports")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Service for getting the report headings", notes = "", response = List.class, responseContainer = "")
	public Response getReportHeadings(){
		Long role;
		Map<String,Object> response = new HashMap<>();
		try {
			User user = getLoggedInUser();
			if (null != user) {
				_LOGGER.info("User details fetch successfull");
				List<UserOrganizationRole> userOrganisationRoles = (List<UserOrganizationRole>) orgUserService.getUserOrganizationRoles(user);
				 role=userOrganisationRoles.get(0).getId().getRoleId();
				 List<Report> headings = reportServices.getReportHeadings(role);
				 //response.put("headers", HNIUtils.getHeader(Constants.USER_TYPES.get("customer")));
				 response.put("data", headings);
			}
			
			
		} catch (Exception e) {
			_LOGGER.error("Error in get Customers under an organization Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		response.put(Constants.RESPONSE, Constants.SUCCESS);
		return Response.ok(response).build();

	}
}
