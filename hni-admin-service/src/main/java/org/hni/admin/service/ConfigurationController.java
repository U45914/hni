/**
 * 
 */
package org.hni.admin.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hni.service.helper.onboarding.ConfigurationServiceHelper;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;

/**
 * @author Rahul
 *
 */
@Api(value = "/configure", description = "Operations to manage users and to manage configuration of system related function, all functions to micro-services")
@Component
@Path("/configure")
public class ConfigurationController extends AbstractBaseController {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ConfigurationController.class);

	@Inject
	private ConfigurationServiceHelper configurationServiceHelper;

	@POST
	@Path("/user/activate")
	@Produces("application/json")
	public Response activateUser(Long userId) {
		_LOGGER.debug("Request reached to activate user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<String, String> response = configurationServiceHelper.activateUser(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/users/activate")
	@Produces("application/json")
	public Response activateUsers(List<Long> userId) {
		_LOGGER.debug("Request reached to activate user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<Object, Object> response = configurationServiceHelper.activateUsers(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/user/de-activate")
	@Produces("application/json")
	public Response deActivateUser(Long userId) {
		_LOGGER.debug("Request reached to de-activate user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<String, String> response = configurationServiceHelper.deActivateUser(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/users/de-activate")
	@Produces("application/json")
	public Response deActivateUsers(List<Long> userId) {
		_LOGGER.debug("Request reached to de-activate user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<Object, Object> response = configurationServiceHelper.deActivateUsers(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/user/delete")
	@Produces("application/json")
	public Response deleteUser(Long userId) {
		_LOGGER.debug("Request reached to delete user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<String, String> response = configurationServiceHelper.deleteUser(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/users/delete")
	@Produces("application/json")
	public Response deleteUsers(List<Long> userIds) {
		_LOGGER.debug("Request reached to delete multiple user " + userIds);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<Object, Object> response = configurationServiceHelper.deleteUsers(userIds, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/user/sheltered")
	@Produces("application/json")
	public Response shelterUser(Long userId) {
		_LOGGER.debug("Request reached to shelter user " + userId);
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			Map<String, String> response = configurationServiceHelper.shelterUser(userId, loggedInUser);
			return Response.ok().entity(response).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
}
