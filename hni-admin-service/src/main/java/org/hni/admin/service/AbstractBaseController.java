package org.hni.admin.service;

import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.hni.common.Constants;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.utils.HNISecurityUtils;
import org.hni.type.HNIRoles;
import org.hni.user.om.Address;
import org.hni.user.om.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewSerializer;

public class AbstractBaseController {

	protected static final String SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN = "Something went wrong, please try again";
	protected static final String ERROR_MSG = "errorMsg";
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static final String RESPONSE = "response";
	protected static final String USER_NAME = "userName";
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	@Inject
	protected OrganizationUserService organizationUserService;

	protected ObjectMapper mapper = new ObjectMapper();
	protected SimpleModule module = new SimpleModule();

	@PostConstruct
	public void configureJackson() {
		module.addSerializer(JsonView.class, new JsonViewSerializer());
		mapper.registerModule(module);
	}

	protected User getLoggedInUser() {
		Long userId = (Long) ThreadContext.get(Constants.USERID);
		return organizationUserService.get(userId);
	}

	protected boolean isPermitted(String domain, String permission, Long id) {
		if (SecurityUtils.getSubject().isPermitted(createPermission(domain, permission, id))) {
			return true;
		}
		return false;

	}

	private String createPermission(String domain, String action, Long instance) {
		return String.format("%s:%s:%d", domain, action, instance);
	}

	protected Response createResponse(String message, Response.Status status) {
		return Response.status(status).entity(String.format("{\"message\":\"%s\"}", message.toString()))
				.type(MediaType.APPLICATION_JSON).build();
	}

	protected Response createResponse(String message) {
		return createResponse(message, Response.Status.OK);
	}

	protected User setPassword(User user) {
		if (user != null) {
			user.setCreated(new Date());
			user.setSalt(HNISecurityUtils.getSalt());
			user.setHashedSecret(HNISecurityUtils.getHash(user.getPassword(), user.getSalt()));
		}
		return user;
	}
	
	protected Long convertUserTypeToRole(String type) {
		if (type.equalsIgnoreCase("ngo")) {
			return HNIRoles.NGO.getRole();
		} else if (type.equalsIgnoreCase("volunteer")) {
			return HNIRoles.VOLUNTEERS.getRole();
		}
		return HNIRoles.USER.getRole();
	}

	protected Set<Address> getAddressSet(Address address) {
		Set<Address> addresses = new HashSet<>(1);
		addresses.add(address);
		
		return addresses;
	}
	
	protected String getRoleName(Long roleId) {
		if (roleId.equals(HNIRoles.SUPER_ADMIN.getRole())) {
			return "Super Admin";
		} else if (roleId.equals(HNIRoles.NGO_ADMIN.getRole())) {
			return "NGOAdmin";
		} else if (roleId.equals(HNIRoles.NGO.getRole())) {
			return "NGO";
		} else if (roleId.equals(HNIRoles.VOLUNTEERS.getRole())) {
			return "Volunteer";
		} else if (roleId.equals(HNIRoles.CLIENT.getRole())) {
			return "Client";
		} else {
			return "User";
		}
	}
}
