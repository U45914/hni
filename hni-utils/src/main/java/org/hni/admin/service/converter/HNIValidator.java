package org.hni.admin.service.converter;

import java.util.List;
import java.util.Map;

import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.FoodServiceAvailability;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;

public class HNIValidator {

	public static Map validateNgo(Ngo ngo, Map errors) {
		
			
			if (ngo.getName() == null) {
				errors.put("Ngo -> name", "cannot be null");
			}
			if (ngo.getAddressId() == null) {
				errors.put("Ngo -> addressId", "cannot be null");
			}
			if (ngo.getPhone() == null) {
				errors.put("Ngo -> phone", "cannot be null");
			}
			if (ngo.getWebsite() == null) {
				errors.put("Ngo -> website", "cannot be null");
			}
			if (ngo.getContactFirstName()== null) {
				errors.put("Ngo -> contactFirstName", "cannot be null");
			}
			if (ngo.getContactLastName()== null) {
				errors.put("Ngo -> contactLastName", "cannot be null");
			}
			/*if (b.getFte()== null) {
				errors.put("Ngo -> fte", "cannot be null");
			}*/
			if (ngo.getOverview()== null) {
				errors.put("Ngo -> overview", "cannot be null");
			}
			if (ngo.getMission()== null) {
				errors.put("Ngo -> mission", "cannot be null");
			}
			if (ngo.getMonthlyBudget()== null) {
				errors.put("Ngo -> monthlyBudget", "cannot be null");
			}
			if (ngo.getFoodStampAssist()== null) {
				errors.put("Ngo -> foodStampAssist", "cannot be null");
			}
			if (ngo.getFoodBank()== null) {
				errors.put("Ngo -> foodBank", "cannot be null");
			}
			if (ngo.getResourcesToClients()== null) {
				errors.put("Ngo -> resourceToClient", "cannot be null");
			}
			if (ngo.getIndServDaily()== null) {
				errors.put("Ngo -> indServDaily", "cannot be null");
			}
			if (ngo.getIndServMonthly()== null) {
				errors.put("Ngo -> indServMonthly", "cannot be null");
			}
			if (ngo.getIndServAnnual()== null) {
				errors.put("Ngo -> indServAnnual", "cannot be null");
			}
			if (ngo.getClientInfo()== null) {
				errors.put("Ngo -> clientInfo", "cannot be null");
			}
			if (ngo.getClientsUnsheltered()== null) {
				errors.put("Ngo -> clientsUnsheltered", "cannot be null");
			}
			if (ngo.getClientsEmployed()== null) {
				errors.put("Ngo -> clientsEmployed", "cannot be null");
			}
			
		

		return errors;

	}
	public static Map validateBoardMembers(List<BoardMember> boardMembers, Map errors) {
		boardMembers.forEach(b -> {
			
			if (b.getFirstName() == null) {
				errors.put("boardMember -> firstName", "cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("boardMember -> company", "cannot be null");
			}
		});

		return errors;

	}

	public static Map validateBrandPartners(List<BrandPartner> brandPartners,
			Map errors) {

		brandPartners.forEach(b -> {
			
			if (b.getPhone() == null) {
				errors.put("brandPartner -> phone", "Cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("brandPartner -> company", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateLocalPartners(List<LocalPartner> localPartners,
			Map errors) {

		localPartners.forEach(b -> {
			if (b.getPhone() == null) {
				errors.put("localPartner -> phone", "Cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("localPartner -> company", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateNgoFoundingSources(
			List<NgoFundingSource> NgoFundingSources, Map errors) {

		NgoFundingSources.forEach(b -> {
			if (b.getAmount() == null) {
				errors.put("ngoFundingSource -> amount", "Cannot be null");
			}
			if (b.getSource() == null) {
				errors.put("ngoFundingSource -> source", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateMealFundingSources(
			List<MealFundingSource> MealFundingSources, Map errors) {

		MealFundingSources.forEach(b -> {
			if (b.getAmount() == null) {
				errors.put("mealFundingSource -> amount", "Cannot be null");
			}
			if (b.getSource() == null) {
				errors.put("mealFundingSource -> source", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateFoodServices(List<FoodService> FoodServices, Map errors) {

		FoodServices.forEach(b -> {
			if (b.getServiceType() == null) {
				errors.put("foodService -> serviceType", "Cannot be null");
			}
			if (b.getTotalCount() == null) {
				errors.put("foodService -> totalCount", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateFoodServicesAvailability(
			List<FoodServiceAvailability> FoodServiceAvailability, Map errors) {

		FoodServiceAvailability.forEach(b -> {
			if (b.getFoodServicesId() == null) {
				errors.put("foodServiceAvailability -> foodServicesId", "Cannot be null");
			}
			if (b.getWeekDay() == null) {
				errors.put("foodServiceAvailability -> weakDay", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateFoodBank(List<FoodBank> FoodBank, Map errors) {

		FoodBank.forEach(b -> {
			if (b.getFoodBankName() == null) {
				errors.put("foodBank -> foodBankName", "Cannot be null");
			}

		});
		return errors;
	}

	public static Map validateMealDonationSources(
			List<MealDonationSource> MealDonationSources, Map errors) {

		MealDonationSources.forEach(b -> {
			if (b.getSource() == null) {
				errors.put("mealDonationSource -> source", "Cannot be null");
			}
			if (b.getFrequency() == null) {
				errors.put("mealDonationSource -> frequency", "Cannot be null");
			}

		});
		return errors;
	}

}
