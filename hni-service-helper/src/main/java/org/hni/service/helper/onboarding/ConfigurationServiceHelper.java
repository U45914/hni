package org.hni.service.helper.onboarding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.common.Constants;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationUserService;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.hni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationServiceHelper extends AbstractServiceHelper {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ConfigurationServiceHelper.class);

	@Inject
	protected OrganizationUserService organizationUserService;
	@Inject
	@Named("defaultUserService")
	private UserService userService;

	public Map<String, String> activateUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for activate user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			toUser.setIsActive(true);
			toUser.setDeleted(false);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been activated");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}

	public Map<String, String> deActivateUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for de-activate user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			toUser.setIsActive(false);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been de-activated");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}

	/**
	 * Check the given user has the permission to enable or disable such actions
	 * on the given user
	 * 
	 * @return
	 */
	public boolean isAllowed(User performer, User toUser) {
		boolean isAllowed = false;
		List<UserOrganizationRole> performerRoles = (List<UserOrganizationRole>) organizationUserService
				.getUserOrganizationRoles(performer);

		if (!performerRoles.isEmpty()) {
			UserOrganizationRole performerRole = performerRoles.get(0);
			// Now get the roles of user who need to be modified
			List<UserOrganizationRole> toUserRoles = (List<UserOrganizationRole>) organizationUserService
					.getUserOrganizationRoles(toUser);
			if (!toUserRoles.isEmpty()) {
				UserOrganizationRole toUserRole = toUserRoles.get(0);

				Long roleOfPerformer = performerRole.getId().getRoleId();
				Long roleOfToUser = toUserRole.getId().getRoleId();

				isAllowed = checkPermission(roleOfPerformer, roleOfToUser);
			}
		}
		return isAllowed;
	}

	private boolean checkPermission(Long roleOfPerformer, Long roleOfToUser) {

		if (roleOfPerformer.equals(HNIRoles.CLIENT.getRole())) {
			// No action allowed for a client/participant
			return false;
		} else if (roleOfPerformer.equals(HNIRoles.SUPER_ADMIN.getRole())) {
			return true;
		} else if ((roleOfPerformer.equals(HNIRoles.NGO_ADMIN.getRole())
				|| roleOfPerformer.equals(HNIRoles.NGO.getRole()))
				&& (roleOfToUser.equals(HNIRoles.CLIENT.getRole())
						|| roleOfToUser.equals(HNIRoles.VOLUNTEERS.getRole()))) {
			return true;
		}
		return false;
	}

	public boolean isAnyOrderPending(User user) {
		return false;
	}

	public Map<Object, Object> activateUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for activate multiple user");
		Map<Object, Object> response = new HashMap<>();

		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);

			if (isAllowed(loggedInUser, toUser)) {
				toUser.setIsActive(true);
				toUser.setDeleted(false);

				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);

				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});

		return response;
	}

	public Map<Object, Object> deActivateUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for de-activate multiple user");
		Map<Object, Object> response = new HashMap<>();

		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);

			if (isAllowed(loggedInUser, toUser)) {
				toUser.setIsActive(false);

				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);

				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});

		return response;
	}

	public Map<String, String> deleteUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for delete user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			toUser.setIsActive(false);
			toUser.setDeleted(true);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been deleted");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}
	
	public Map<Object, Object> deleteUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for delete user");
		Map<Object, Object> response = new HashMap<>();
		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);
	
			if (isAllowed(loggedInUser, toUser)) {
				toUser.setIsActive(false);
				toUser.setDeleted(true);
	
				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);
	
				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});
		return response;
	}
}
