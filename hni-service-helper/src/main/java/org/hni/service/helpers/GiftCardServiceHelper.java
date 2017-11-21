package org.hni.service.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.GiftCardService;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class GiftCardServiceHelper extends AbstractServiceHelper {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(GiftCardServiceHelper.class);
	
	@Inject
	private GiftCardService giftCardService;

	public Map<String, Object> getGiftCardsFor(Long providerId){
		_LOGGER.info("Invoking method to retrieve gift cards, provider: "+providerId);
		Map<String, Object> response = new HashMap();
		try{
			List<PaymentInstrumentDto> exisitingGiftCards = (List<PaymentInstrumentDto>) giftCardService.getGitCardsFor(providerId);
			_LOGGER.info("Successfully retrieved giftCards.");
			response.put("headers",HNIUtils.getReportHeaders(100, true));
			response.put("data", exisitingGiftCards);
			response.put(Constants.MESSAGE,Constants.SUCCESS);
		} catch(Exception e){
			_LOGGER.info("Failed to retrieve giftCards. "+e.getMessage());
			response.put(Constants.MESSAGE, "Error occured while retrieval");
		}
		
		return response;
	}
	
	public Map<String,Object> deactivateGiftCard(Long giftCardId){
		Map<String, Object> response = new HashMap();
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			if(paymentInstrument != null){
				paymentInstrument.setStatus("I");
				giftCardService.update(paymentInstrument);
				response.put(Constants.MESSAGE,"Gift card deactivated successfully.");
			} else {
				response.put(Constants.MESSAGE,"No information found.");
			}
		} catch(Exception e){
			response.put(Constants.MESSAGE, "Failed to deactivate gift card.");
		}
		
		return response;
	}
	
	public Map<String,Object> activateGiftCard(Long giftCardId){
		Map<String, Object> response = new HashMap();
		try{
			PaymentInstrument paymentInstrument = giftCardService.get(giftCardId);
			if(paymentInstrument != null){
				paymentInstrument.setStatus("A");
				giftCardService.update(paymentInstrument);
				response.put(Constants.MESSAGE,"Gift card Activated successfully.");
			} else {
				response.put(Constants.MESSAGE,"No information found.");
			}
		} catch(Exception e){
			response.put(Constants.MESSAGE, "Failed to Activate gift card.");
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
	
	public Map<String, Object> saveGiftCards(PaymentInstrument paymentInstrument, User user){
		Map<String, Object> response = new HashMap();
		try{
			Double balance = paymentInstrument.getOriginalBalance();
			paymentInstrument.setAllowTopup(true);
			paymentInstrument.setBalance(balance);
			paymentInstrument.setCardType("gift");
			paymentInstrument.setCreatedBy(user);
			paymentInstrument.setCreatedDate(new Date());
			paymentInstrument.setStatus("A");
			
			if(paymentInstrument.getCardSerialId() == null || paymentInstrument.getCardSerialId().equals("")){
				Long providerId = paymentInstrument.getProvider().getId();
				Long newSerialNumber = getNewSerialNumber(providerId);
				paymentInstrument.setCardSerialId(newSerialNumber.toString());
			}
			giftCardService.save(paymentInstrument);
			response.put("data", "Gift card added.");
			response.put(Constants.MESSAGE, Constants.SUCCESS);
			
		} catch(Exception e){
			response.put("data", "Failed to add new gift card.");
		}
		
		
		return response;
	}
	
	private Long getNewSerialNumber(Long providerId){
		List<String> existingCards = giftCardService.getSerialCardNumbers(providerId);
		Long newSerialNumber = new Long(0);
		for(String giftCard : existingCards){
			Long cardSerialId = Long.valueOf(giftCard);
			if(cardSerialId > newSerialNumber){
				newSerialNumber = cardSerialId;
			}
		}
		return newSerialNumber+1;
	}
}
