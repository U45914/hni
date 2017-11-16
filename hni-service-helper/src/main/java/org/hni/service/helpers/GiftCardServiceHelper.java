package org.hni.service.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.GiftCardService;
import org.springframework.stereotype.Component;

import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

@Component
public class GiftCardServiceHelper extends AbstractServiceHelper {
	
	@Inject
	private GiftCardService giftCardService;

	public Map<String, Object> getGiftCardsFor(Long providerId){
		Map<String, Object> response = new HashMap();
		try{
			List<PaymentInstrumentDto> exisitingGiftCards = (List<PaymentInstrumentDto>) giftCardService.getGitCardsFor(providerId);
			response.put("headers",HNIUtils.getReportHeaders(100, true));
			response.put("data", exisitingGiftCards);
			response.put(Constants.MESSAGE,Constants.SUCCESS);
		} catch(Exception e){
			response.put(Constants.MESSAGE, "Error occured while retrieval");
		}
		
		return response;
	}
	
	public Map<String,Object> deleteGiftCard(Long giftCardId){
		Map<String, Object> response = new HashMap();
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			if(paymentInstrument != null){
				giftCardService.delete(paymentInstrument);
				response.put(Constants.MESSAGE,"Gift card deleted successfully.");
			} else {
				response.put(Constants.MESSAGE,"No information found.");
			}
		} catch(Exception e){
			response.put(Constants.MESSAGE, "Failed to delete gift card.");
		}
		
		return response;
	}
	
	public Map<String, Object> updateGiftCards(List<PaymentInstrumentDto> newCards){
		Map<String, Object> response = new HashMap();
		try{
			for(PaymentInstrumentDto newCard : newCards){
				PaymentInstrument paymentInstrument = giftCardService.get(newCard.getId());
				paymentInstrument.setCardNumber(newCard.getCardNumber());
				paymentInstrument.setCardSerialId(newCard.getCardSerialId());
				paymentInstrument.setStatus(newCard.getStatus());
				paymentInstrument.setOriginalBalance(newCard.getOriginalBalance());
				paymentInstrument.setBalance(newCard.getBalance());
				paymentInstrument.setStateCode(newCard.getStateCode());
				giftCardService.update(paymentInstrument);
			}
			response.put("data", "Gift card(s) updated.");
			response.put(Constants.MESSAGE, Constants.SUCCESS);
			
		} catch(Exception e){
			response.put("data", "Failed to update gift card(s).");
		}
		
		return response;
	}
}
