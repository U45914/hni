package org.hni.admin.service;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.exception.HNIException;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.GiftCardService;
import org.hni.service.helpers.GiftCardServiceHelper;
import org.hni.user.om.User;
import org.springframework.stereotype.Component;

@Api(value = "/giftCards", description = "Operations on gift cards and to manage gift card operations")
@Component
@Path("/giftCards")
public class GiftCardController extends AbstractBaseController {
	
	@Inject
	private GiftCardServiceHelper giftCardServiceHelper;

	@GET
	@Path("/{providerId}/provider")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the giftcard associated with the provider id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response getGiftCards(@PathParam("providerId") Long providerId) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.getGiftCardsFor(providerId), MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Path("/{giftCardId}/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deletes the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response deleteGiftCard(@PathParam("giftCardId") Long giftCardId) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.deleteGiftCard(giftCardId), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/cards/update")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deletes the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response updateGiftCard(List<PaymentInstrumentDto> newCards) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.updateGiftCards(newCards), MediaType.APPLICATION_JSON).build();
	}
}
