package org.hni.user.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.admin.service.converter.HNIValidator;
import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.HNIUtils;
import org.hni.common.dao.BaseDAO;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.AddressDAO;
import org.hni.user.dao.ClientDAO;
import org.hni.user.dao.NGOGenericDAO;
import org.hni.user.dao.UserDAO;
import org.hni.user.dao.UserOnboardingDAO;
import org.hni.user.dao.VolunteerDao;
import org.hni.user.om.Address;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.Client;
import org.hni.user.om.Invitation;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserOnboardingService extends AbstractService<Invitation> implements UserOnboardingService{
	
	private static final String SUCCESS = "Success";

	ObjectMapper mapper = new ObjectMapper();
	@Inject
	private UserOnboardingDAO invitationDAO;

	
	@Inject
	private NGOGenericDAO ngoGenericDAO;
	
	@Inject
	private AddressDAO addressDAO;
	
	@Inject
	private VolunteerDao volunteerDao;
	
	@Inject
	private ClientDAO clientDAO;
	
	@Inject
	private UserDAO userDao;

	public DefaultUserOnboardingService(BaseDAO<Invitation> dao) {
		super(dao);
	}

	@Override
	public Collection<Invitation> validateInvitationCode(String invitationCode) {
		return invitationDAO.validateInvitationCode(invitationCode);
	}


	@Override
	public String buildInvitationAndSave(Long orgId, Long invitedBy, String email) {
		User user = userDao.byEmailAddress(email);
		if (user == null) {
			String UUID = HNIUtils.getUUID();
			Invitation invitation = new Invitation();
			invitation.setOrganizationId(orgId.toString());
			invitation.setInvitationCode(UUID);
			invitation.setInvitedBy(invitedBy);
			invitation.setEmail(email);
			invitation.setCreatedDate(new Date());
			invitation.setActivated(0);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 5);
			invitation.setExpirationDate(cal.getTime());
			invitationDAO.save(invitation);
			return UUID;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String, String> ngoSave(ObjectNode onboardData, User user) {
		Map<String, String> messages = new HashMap<>();
		validateNGO(onboardData, messages);
		if (messages != null && messages.isEmpty()) {
			saveNGOData(onboardData, user);
		} else {
			return messages;
		}
		return messages;
	}
	
	private void validateNGO(ObjectNode onboardData, Map<String, String> errors) {
		HNIValidator.validateNgo(HNIConverter.getNGOFromJson(onboardData),errors);
		HNIValidator.validateBoardMembers(HNIConverter.getBoardMembersFromJson(onboardData,null),errors);
		HNIValidator.validateBrandPartners(HNIConverter.getBrandPartnersFromJson(onboardData,null),errors);
		HNIValidator.validateLocalPartners(HNIConverter.getLocalPartnersFromJson(onboardData,null),errors);
		HNIValidator.validateFoodBank(HNIConverter.getFoodBankFromJson(onboardData,null),errors);
		HNIValidator.validateFoodServices(HNIConverter.getFoodServicesFromJson(onboardData,null),errors);
		HNIValidator.validateMealDonationSources( HNIConverter.getMealDonationSourceFromJson(onboardData,null),errors);
		HNIValidator.validateMealFundingSources(HNIConverter.getMealFundingSourcesFromJson(onboardData,null),errors);
		HNIValidator.validateNgoFundingSources(HNIConverter.getNgoFundingSourcesFromJson(onboardData,null),errors);
	}
	
	private String saveNGOData(ObjectNode onboardData, User user){
		
		Ngo ngo = ngoGenericDAO.save(Ngo.class ,HNIConverter.getNGOFromJson(onboardData));
		Invitation invitation = invitationDAO.getInvitedBy(user.getEmail());
		ngo.setCreatedBy(invitation.getInvitedBy());
		
		ngoGenericDAO.update(Ngo.class, ngo);
		
		user.setAddresses(HNIConverter.getAddressSet(onboardData));
		userDao.update(user);
		
		ngoGenericDAO.saveBatch(BoardMember.class ,(HNIConverter.getBoardMembersFromJson(onboardData,ngo.getId())));
		ngoGenericDAO.saveBatch(BrandPartner.class ,HNIConverter.getBrandPartnersFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(LocalPartner.class ,HNIConverter.getLocalPartnersFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(FoodBank.class ,HNIConverter.getFoodBankFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(FoodService.class ,HNIConverter.getFoodServicesFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(MealDonationSource.class ,HNIConverter.getMealDonationSourceFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch( MealFundingSource.class,HNIConverter.getMealFundingSourcesFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.saveBatch(NgoFundingSource.class ,HNIConverter.getNgoFundingSourcesFromJson(onboardData,ngo.getId()));
		ngoGenericDAO.updateStatus(ngo.getUserId());
		return SUCCESS;
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String,String> buildVolunteerAndSave(Volunteer volunteer, User user) {
		Map<String, String> error = new HashMap<>();
		
		HNIValidator.validateVolunteer(volunteer, error);
		
		if(error!=null && error.isEmpty()){
			Volunteer extVolunteer = volunteerDao.getByUserId(user.getId());
			Long volunteerId = extVolunteer != null ? extVolunteer.getId() : null;
			if (volunteerId != null) {
				
			}
			Long createdBy = getInvitedBy(volunteer);
			if(createdBy==null){
				createdBy = user.getId();
				volunteer.setCreated(new Date());
				volunteer.setCreatedBy(createdBy);
			}

			volunteer.setUserId(user.getId());
			
			volunteer = volunteerDao.save(volunteer);
			if (volunteer.getId() != null) {
				ngoGenericDAO.updateStatus(volunteer.getUserId());
			}
		}
		return error;
	}
	
	private Long getInvitedBy(Volunteer volunteer) {
		Invitation invitedBy = invitationDAO.getInvitedBy(volunteer.getEmail());
		return invitedBy.getInvitedBy();
	} 

	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public ObjectNode getNGODetail(Long ngoId, User user) {
		ObjectNode parentJSON = mapper.createObjectNode();
		ObjectNode overViewNode = mapper.createObjectNode();
		// set Address to overview
		// TODO if user is null try to get from user table using the user_id of NGO table
		if (user != null) {
			overViewNode.put("address", HNIConverter.getAddress(mapper.createObjectNode(), user.getAddresses()));
			overViewNode.put("name", user.getFirstName() + " " + user.getLastName());
			overViewNode.put("mobilePhone", user.getMobilePhone());
			overViewNode.put("genderCode", user.getGenderCode());
			 
			 
		}

		parentJSON.set("overview", overViewNode);
		parentJSON.set("stakeHolder", mapper.createObjectNode());
		parentJSON.set("service", mapper.createObjectNode());
		parentJSON.set("funding", mapper.createObjectNode());
		parentJSON.set("client", mapper.createObjectNode());
		
		HNIConverter.convertNGOToJSON((Ngo) ngoGenericDAO.get(Ngo.class, ngoId), parentJSON);
		HNIConverter.convertBoardMembersToJSON(ngoGenericDAO.find(BoardMember.class, "select x from BoardMember x where x.ngo_id=?1 ", ngoId), parentJSON);
		HNIConverter.convertBrandPartnersToJSON(ngoGenericDAO.find(BrandPartner.class, "select x from BrandPartner x where x.ngo_id=?1 ", ngoId), parentJSON);
		HNIConverter.convertLocalPartnerToJSON(ngoGenericDAO.find(LocalPartner.class, "select x from LocalPartner x where x.ngo_id=?1 ", ngoId), parentJSON);
		HNIConverter.convertFoodBanksToJSON(ngoGenericDAO.find(FoodBank.class, "select x from FoodBank x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertFoodServiceToJSON(ngoGenericDAO.find(FoodService.class, "select x from FoodService x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertMealDonationToJSON(ngoGenericDAO.find(MealDonationSource.class, "select x from MealDonationSource x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertMealFundingToJSON(ngoGenericDAO.find(MealFundingSource.class, "select x from MealFundingSource x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertFundingSourceToJSON(ngoGenericDAO.find(NgoFundingSource.class, "select x from NgoFundingSource x where x.ngoId=?1 ", ngoId), parentJSON);
		return parentJSON;
	}
	
	@Override
	public List<NgoBasicDto> getAllNgo() {
		return ngoGenericDAO.getAllNgo();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String,String> clientSave(Client client, User user) {
		Client extClient = clientDAO.getByUserId(user.getId());
		
		Map<String, String> error = new HashMap<>();
		
		if (extClient == null) {
			client.setUserId(user.getId());
			Invitation invitedBy = invitationDAO.getInvitedBy(user.getEmail());
			if (invitedBy != null) {
				client.setCreatedBy(invitedBy.getInvitedBy());
			}
		} else {
			client.setId(extClient.getId());
		}
		
		HNIValidator.validateClient(client, error);
		if(error!=null && error.isEmpty()) {
			clientDAO.save(client);
			if (client.getId() != null) {
				ngoGenericDAO.updateStatus(client.getUserId());
			}
			
		}
		return error;
	}

	@Override
	public Map<String, Object> getUserProfiles(String type, Long userId) {
		Long id = findIdByType(userId,type);
		User user = userDao.get(userId);
		
		Map<String,Object> response = new HashMap<>();
		
		if(type!=null && type.equalsIgnoreCase("ngo")){
			ObjectNode ngoInfo = this.getNGODetail(id, user);
			response.put("response", ngoInfo);
		} else if(type.equalsIgnoreCase("Volunteer")){
			Volunteer volunteer = volunteerDao.get(id);
			volunteer.setUser(user);
			volunteer.setAddress(getAddress(user.getAddresses()));
			response.put("response", volunteer);
		} else if(type.equalsIgnoreCase("Client")){
			Client client = ngoGenericDAO.get(Client.class,id);
			client.setAddress(getAddress(user.getAddresses()));
			client.setUser(user);
			response.put("response", ngoGenericDAO.get(Client.class,id));
		}
		
		return response;
	}
	 private  Long findIdByType(Long userId,String type) {
		 return userDao.findTypeIdByUser(userId, type);
	 }
	 
	 private Address getAddress(Set<Address> userAddresses) {
		 if (userAddresses != null && !userAddresses.isEmpty()) {
			 return userAddresses.iterator().next();
		 } else {
			 return new Address();
		 }
	 }

	@Override
	public Invitation finalizeRegistration(String activationCode) {
		return invitationDAO.updateInvitationStatus(activationCode);
	}
	 
	 
}
