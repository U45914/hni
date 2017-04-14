package org.hni.user.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.admin.service.converter.HNIValidator;
import org.hni.common.HNIUtils;
import org.hni.common.dao.BaseDAO;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.FoodServiceAvailability;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.NGOGenericDAO;
import org.hni.user.dao.UserOnboardingDAO;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.Invitation;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserOnboardingService extends AbstractService<Invitation> implements UserOnboardingService{
	
	private static final String SUCCESS = "Success";


	@Inject
	private UserOnboardingDAO invitationDAO;

	
/*	@Inject
	private NGOGenericDAO ngoGenericDAO;
*/
	public DefaultUserOnboardingService(BaseDAO<Invitation> dao) {
		super(dao);
	}

	@Override
	public Collection<Invitation> validateInvitationCode(String invitationCode) {
		return invitationDAO.validateInvitationCode(invitationCode);
	}


	@Override
	public String buildInvitationAndSave(Long orgId) {
		String UUID = HNIUtils.getUUID();
		Invitation invitation = new Invitation();
		invitation.setOrganizationId(orgId.toString());
		invitation.setInvitationCode(UUID);
		invitation.setCreatedDate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		invitation.setExpirationDate(cal.getTime());
		invitationDAO.save(invitation);
		return UUID;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String, String> ngoSave(ObjectNode onboardData) {
		Map<String,String> messages = new HashMap<>();
		validateNGO(onboardData, messages);
		if(messages!=null && messages.isEmpty()){
		saveNGOData(onboardData);
		}
		else{
		return messages;
		}
		return messages;
	}

	private void validateNGO(ObjectNode onboardData, Map<String, String> errors) {
		
		HNIValidator.validateBoardMembers(HNIConverter.getBoardMembersFromJson(onboardData,null),errors);
		HNIValidator.validateBrandPartners(HNIConverter.getBrandPartnersFromJson(onboardData,null),errors);
		//HNIValidator.validateFoodBank(HNIConverter.getFoodBankFromJson(onboardData,null),errors);
		HNIValidator.validateFoodServicesAvailability(HNIConverter.getFoodServiceAvailabilityFromJson(onboardData,null),errors);
		HNIValidator.validateLocalPartners(HNIConverter.getLocalPartnersFromJson(onboardData,null),errors);
		HNIValidator.validateMealFundingSources(HNIConverter.getMealFundingSourcesFromJson(onboardData,null),errors);
		HNIValidator.validateMealDonationSources( HNIConverter.getMealDonationSourceFromJson(onboardData,null),errors);
		HNIValidator.validateNgoFoundingSources(HNIConverter.getNgoFundingSourcesFromJson(onboardData,null),errors);
		//HNIValidator.validateFoodBank(HNIConverter.getFoodBankFromJson(onboardData,null),errors);
	}
	
	private String saveNGOData(ObjectNode onboardData){
		/*Ngo ngo = ngoGenericDAO.save(Ngo.class ,HNIConverter.getNGOFromJson(onboardData));
		ngoGenericDAO.saveBatch(BoardMember.class ,(HNIConverter.getBoardMembersFromJson(onboardData,ngo.getId())));
		ngoGenericDAO.saveBatch(BrandPartner.class ,HNIConverter.getBrandPartnersFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(FoodBank.class ,HNIConverter.getFoodBankFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(FoodServiceAvailability.class ,HNIConverter.getFoodServiceAvailabilityFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(FoodService.class ,HNIConverter.getFoodServicesFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(LocalPartner.class ,HNIConverter.getLocalPartnersFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(MealDonationSource.class ,HNIConverter.getMealDonationSourceFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(MealFundingSource.class ,HNIConverter.getMealFundingSourcesFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(NgoFundingSource.class ,HNIConverter.getNgoFundingSourcesFromJson(onboardData,ngo.getId()));*/
		return SUCCESS;
		
	}
	 
}
