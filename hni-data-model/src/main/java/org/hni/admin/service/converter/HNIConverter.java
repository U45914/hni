package org.hni.admin.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.dto.HniServicesDto;
//import org.hni.common.Constants;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.FoodServiceAvailability;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.organization.om.HniServices;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HNIConverter {
	private static final Logger logger = LoggerFactory
			.getLogger(HNIConverter.class);

	public static Collection<HniServicesDto> convertToServiceDtos(
			Collection<HniServices> hniServices) {

		Collection<HniServicesDto> hniServicesDtoList = new ArrayList<>();
		for (HniServices hniService : hniServices) {
			HniServicesDto hniServiceDto = new HniServicesDto();
			hniServiceDto.setServiceName(hniService.getServiceName());
			hniServiceDto.setServicePath(hniService.getServicePath());
			hniServiceDto.setServiceImg(hniService.getServiceImg());
			hniServiceDto.setActive(hniService.getActive());

			hniServicesDtoList.add(hniServiceDto);
		}
		return hniServicesDtoList;
	}

	public List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("brandPartners");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode brand = jsonNode.iterator().next();
				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setCompany(brand.get("company").textValue());
				brandPartner.setCreated(new Date());
				brandPartner.setCreatedBy((Long) ThreadContext.get("userId"));
				// brandPartner.setId(brand.get("id").asLong());
				brandPartner.setPhone(brand.get("phoneNumber").textValue());

				brandPartners.add(brandPartner);
			}
		}
		return brandPartners;
	}

	public Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode jsonNode = objectNode.get("overview");
		JsonNode serviceNode = objectNode.get("service");
		JsonNode clientNode = objectNode.get("client");

		Ngo ngo = new Ngo();

		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get("userId"));
		ngo.setMission(jsonNode.get("mission").textValue());
		ngo.setName(jsonNode.get("name").textValue());
		ngo.setOverview(jsonNode.get("overview").textValue());
		ngo.setPhone(jsonNode.get("phone").textValue());
		ngo.setWebsite(jsonNode.get("website").textValue());
		ngo.setFte(jsonNode.get("employees").asInt());

		ngo.setFoodBank(serviceNode.get("foodBankSelect").asBoolean());
		ngo.setKitchenVolunteers(serviceNode.get("volunteerNbr").asLong());
		ngo.setMonthlyBudget(serviceNode.get("monthlyBudget").asLong());
		ngo.setOperatingCost(serviceNode.get("operatingCost").asLong());
		ngo.setPersonalCost(serviceNode.get("personnelCost").asLong());

		ngo.setIndServAnnual(clientNode.get("individualsServedAnnually")
				.asLong());
		ngo.setIndServDaily(clientNode.get("individualsServedDaily").asLong());
		ngo.setIndServMonthly(clientNode.get("individualsServedMonthly")
				.asLong());
		ngo.setClientInfo(clientNode.get("individualClientInfoCollected")
				.asBoolean());
		ngo.setClientsUnsheltered(clientNode.get("unshelteredClientPercentage")
				.asLong());
		ngo.setClientsEmployed(clientNode.get("employeedClientPercentage")
				.asLong());

		// Data type mismatch with database
		/*
		 * ngo.setEndorsementId(jsonNode.get("endorsementId").asLong());
		 * ngo.setFoodStampAssist(jsonNode.get("foodStampAssist").asBoolean());
		 * ngo
		 * .setResourcesToClients(jsonNode.get("resourcesToClients").asLong());
		 * ngo.setStoreClientInfo(jsonNode.get("storeClientInfo").textValue());
		 */

		return ngo;
	}

	public List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode) {
		List<BoardMember> boardMembers = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("boardMembers");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode board = jsonNode.iterator().next();
				BoardMember boardMember = new BoardMember();
				boardMember.setFirstName(board.get("name").textValue());
				// boardMember.setLastName(board.get("lastName").textValue());
				boardMember.setCompany(board.get("company").textValue());
				boardMember.setCreated(new Date());
				boardMember.setCreatedBy((Long) ThreadContext.get("userId"));
				// boardMember.setId(board.get("id").asLong());

				boardMembers.add(boardMember);
			}
		}
		return boardMembers;
	}

	public List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode) {
		List<LocalPartner> localPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("localPartners");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode local = jsonNode.iterator().next();
				LocalPartner localPartner = new LocalPartner();
				localPartner.setCompany(local.get("company").textValue());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get("userId"));
				// localPartner.setId(local.get("id").asLong());
				localPartner.setPhone(local.get("phoneNumber").textValue());

				localPartners.add(localPartner);
			}
		}
		return localPartners;
	}

	public List<NgoFundingSource> getNgoFundingSourcesFromJson(
			ObjectNode objectNode) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("fundingSource");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode fundSource = jsonNode.iterator().next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setCreated(new Date());
				ngoFundingSource.setCreatedBy((Long) ThreadContext
						.get("userId"));
				// ngoFundingSource.setId(fundSource.get("id").asLong());
				ngoFundingSource.setAmount(fundSource.get("amount").asDouble());
				ngoFundingSource
						.setSource(fundSource.get("source").textValue());

				ngoFundingSources.add(ngoFundingSource);
			}
		}
		return ngoFundingSources;
	}

	public List<MealFundingSource> getMealFundingSourcesFromJson(
			ObjectNode objectNode) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("mealFunding");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setCreated(new Date());
				mealFundingSource.setCreatedBy((Long) ThreadContext
						.get("userId"));
				// mealFundingSource.setId(mealSource.get("id").asLong());
				mealFundingSource
						.setAmount(mealSource.get("amount").asDouble());
				mealFundingSource.setSource(mealSource.get("source")
						.textValue());

				mealFundingSources.add(mealFundingSource);
			}
		}
		return mealFundingSources;
	}

	public List<MealDonationSource> getMealDonationSourceFromJson(
			ObjectNode objectNode) {
		List<MealDonationSource> MealDonationSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("mealDonation");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setCreated(new Date());
				mealDonationSource.setCreatedBy((Long) ThreadContext
						.get("userId"));
				// mealDonationSource.setId(mealSource.get("id").asLong());
				mealDonationSource.setSource(mealSource.get("source")
						.textValue());
				// mealQty
				mealDonationSource.setFrequency(mealSource.get("frequency")
						.textValue());

				MealDonationSources.add(mealDonationSource);
			}
		}
		return MealDonationSources;
	}

	public List<FoodService> getFoodServicesFromJson(ObjectNode objectNode) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("service");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodServiz = jsonNode.iterator().next();
				FoodService foodService = new FoodService();
				foodService.setCreated(new Date());
				foodService.setCreatedBy((Long) ThreadContext.get("userId"));
				//foodService.setId(foodServiz.get("id").asLong());
				foodService.setOther(foodServiz.get("other").textValue());
				foodService
						.setTotalCount(foodServiz.get("totalCount").asLong());
				foodService.setServiceType(foodServiz.get("serviceType")
						.asLong());

				foodServices.add(foodService);
			}
		}
		return foodServices;
	}

	public List<FoodServiceAvailability> getFoodServiceAvailabilityFromJson(
			ObjectNode objectNode) {
		List<FoodServiceAvailability> foodServiceAvailability = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodServiceAvailability");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodAvail = jsonNode.iterator().next();
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext
						.get("userId"));
				foodAvailability.setId(foodAvail.get("id").asLong());
				foodAvailability.setFoodServicesId(foodAvail.get(
						"foodServiceId").asLong());
				foodAvailability.setWeekDay(getFoodServiceWeekDay(objectNode,
						foodAvail));
				foodAvail.get(0);

				foodServiceAvailability.add(foodAvailability);
			}
		}
		return foodServiceAvailability;
	}

	private String getFoodServiceWeekDay(ObjectNode objectNode,
			JsonNode foodAvail) {
		final Long BREAKFAST_ID = 1L;
		final Long LUNCH_ID = 2L;
		final Long DINNER_ID = 3L;
		StringBuilder weekDays = new StringBuilder();
		JsonNode jsonNode = objectNode.get("foodServices");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodServiz = jsonNode.iterator().next();
				Long serviceType = foodServiz.get("serviceType").asLong();
				if (serviceType.equals(BREAKFAST_ID)) {
					for (int index = 0; index < 7; index++) {
						JsonNode weekDay = foodAvail.get(index);
						if (weekDay.has("Breakast")) {
							weekDays.append(weekDay + ",");
						}
					}
				}
				if (serviceType.equals(LUNCH_ID)) {
					for (int index = 0; index < 7; index++) {
						JsonNode weekDay = foodAvail.get(index);
						if (weekDay.has("Lunch")) {
							weekDays.append(weekDay + ",");
						}
					}
				}
				if (serviceType.equals(DINNER_ID)) {
					for (int index = 0; index < 7; index++) {
						JsonNode weekDay = foodAvail.get(index);
						if (weekDay.has("Dinner")) {
							weekDays.append(weekDay + ",");
						}
					}
				}

			}
		}
		return weekDays.toString();
	}

	public List<FoodBank> getFoodBankFromJson(ObjectNode objectNode) {
		List<FoodBank> listOfFoodBank = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("service").get("foodBankValue");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodBankName = jsonNode.iterator().next();
				FoodBank foodBank = new FoodBank();
				foodBank.setCreated(new Date());
				foodBank.setCreatedBy((Long) ThreadContext.get("userId"));
				foodBank.setFoodBankName(foodBankName.textValue());
				listOfFoodBank.add(foodBank);
			}
		}
		return listOfFoodBank;
	}

}
