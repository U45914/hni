package org.hni.admin.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.hni.common.exception.HNIException;
import org.hni.events.service.EventRouter;
import org.hni.events.service.om.Event;
import org.hni.provider.om.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Api(value = "/usermessage", description = "endpoint to accept SMS messages")
@Component
@Path("/usermessage")
public class SMSUserMessageController extends AbstractBaseController {
    private static final Logger logger = LoggerFactory.getLogger(SMSUserMessageController.class);

    @Inject
    private EventRouter eventRouter;
    
    

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as HTML"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public String respondToMessageHTML(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, "Body");
    	String fromNum	 = getQueryParamValue(params, "fromNum");
        logger.info("HTML/Received a message, body={}, fromNum={}",body,fromNum);
        final Event event = Event.createEvent("text/html", fromNum, body);
        return String.format("<html><body>%s</body></html>", eventRouter.handleEvent(event));
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as text"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public String respondToMessage(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, "Body");
    	String fromNum	 = getQueryParamValue(params, "fromNum");
        logger.info("PLAIN/Received a message, body={}, fromNum={}",body,fromNum);
        final Event event = Event.createEvent("text/plain", fromNum, body);
        try {
            return eventRouter.handleEvent(event);
        } catch (Exception ex) {
            throw new HNIException("Something went wrong. Please try again later.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as JSON"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Map<String, Object> respondToMessageJson(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, "Body");
    	String fromNum	 = getQueryParamValue(params, "fromNum");
        logger.info("JSON/Received a message, body={}, fromNum={}",body,fromNum);
        final Event event = Event.createEvent("text/plain", fromNum, body);
        Map<String, Object> res = new HashMap();
        try {
            final String returnMessage = eventRouter.handleEvent(event);
            String[] output = {returnMessage};
            res.put("message", output);
            res.put("status", Response.Status.OK.getStatusCode());
        } catch (HNIException ex) {
            logger.error("Error handling request: {}", ex.getMessage());
            res.put("error", ex.getMessage());
            res.put("status", ex.getResponse().getStatus());
            throw new HNIException(Response.status(ex.getResponse().getStatus()).entity(res).build());
        } catch (Exception ex) {
            logger.error("Internal error handling request: {}", ex.getMessage());
            ex.printStackTrace();
            res.put("error", "Something went wrong. Please try again later.");
            res.put("status", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            throw new HNIException(Response.serverError().entity(res).build());
        }
        return res;
    }
    
    private String getQueryParamValue(MultivaluedMap<String, String> params, String key){
    	return params.get(key).get(0);
    }
}
