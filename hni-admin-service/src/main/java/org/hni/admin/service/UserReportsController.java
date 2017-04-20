package org.hni.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.user.service.UserReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*'Super User'
'Administrator'
'Volunteer'
'Client'
'User'
'NGOAdmin'
'NGO'*/

@Api(value = "/reports/view", description = "Reports for all user type")
@Component
@Path("/reports/view")
public class UserReportsController {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(UserOnboardingController.class);

	@Inject
	private UserReportService userReportService;
	@GET
	@Path("/NGO")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting basic detail of NGO", notes = "", response = List.class, responseContainer = "")
	public Response getAllNgo() {
		
		Map<String,Object> response = new HashMap<>();
		try
		{
		List<NgoBasicDto> ngo =  userReportService.getAllNgo();
		response.put("headers", HNIUtils.getHeader(Constants.USER_TYPES.get("ngo")));
		response.put("data", ngo);
		}
		catch(Exception e){
			_LOGGER.error("Error in get Ngo Service:"+e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		response.put(Constants.RESPONSE, Constants.SUCCESS);
		return Response.ok(response).build();
	 
	}
	
	
	
}
