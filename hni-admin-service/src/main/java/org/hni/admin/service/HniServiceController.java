/**
 * 
 */
package org.hni.admin.service;

import java.io.File;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

/**
 * @author Rahul
 *
 */
@Api(value = "/help")
@SwaggerDefinition(info = @Info(description = "provides methods hni documents and associated resources", version = "v1", title = "Help API"))
@Component
@Path("/help")
public class HniServiceController extends AbstractBaseController {

	private static final String FILE_PATH_VOLUNTEER_INSTRUCTION = "instructions/HNI _Volunteer_Guide_Meal_Orders.pdf";
	private static final String VOLUNTEER_INSTRUCTION_PDF = "volunteer-guide.pdf";

	@Path("/volunteer/instruction/pdf")
	@Produces("application/pdf")
	public Response getVolunteerInstructionPdf() {
		try {
		File file = new File(FILE_PATH_VOLUNTEER_INSTRUCTION);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=\" " + VOLUNTEER_INSTRUCTION_PDF + "\"");
			return response.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}
}
