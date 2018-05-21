package org.hni.user.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "participant_profile_config")
public class ParticipantProfileConfig implements Persistable, Serializable{
	
	private static final long serialVersionUID = 7553475738921092329L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "address_Line1")
	private String addressLine1;
	
	@Column(name = "address_Line2")
	private String addressLine2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zip_Code")
	private String zipCode;
	
	@Column(name = "ethni_City")
	private String ethniCity;
	
	@Column(name = "phone_Number")
	private String phoneNumber;
	
	@Column(name = "dob")
	private String dob;
	
	@Column(name = "everBeen_Arrested")
	private String everBeenArrested;
	
	@Column(name = "convicted_Felony")
	private String conivtedOfFelony;
	
	@Column(name = "smart_Phone")
	private String smartPhone;
	
	@Column(name = "monthly_Plan")
	private String monthlyPlan;
	
	@Column(name = "no_Siblings")
	private String noOfSiblings;
	
	@Column(name = "no_Kids")
	private String noOfKids;
	
	@Column(name = "live_Home")
	private String liveAtHome;
	
	@Column(name = "housing_Status")
	private String housingStatus;
	
	@Column(name = "education")
	private String education;
	
	@Column(name = "parental_Education")
	private String parentalEducation;
	
	@Column(name = "enrolled")
	private String enrolled;
	
	@Column(name = "resident_Status")
	private String residentStatus;
	
	@Column(name = "work_Status")
	private String workStatus;
	
	@Column(name = "monthly_Spendings")
	private String monthlySpendings;
	
	@Column(name = "no_Meals")
	private String noOfMeals;
	
	@Column(name = "food_Preferences")
	private String foodPreferences;
	
	@Column(name = "purchase_Location")
	private String purchaseLocation;
	
	@Column(name = "cooking_Options")
	private String cookingOptions;
	
	@Column(name = "distance_Travel")
	private String distanceOfTravel;
	
	@Column(name = "time_Travel")
	private String timeForTravel;
	
	@Column(name = "subsidised_Food_Programs")
	private String subsidisedFoodPrograms;
	
	@Column(name = "allergies")
	private String allergies;
	
	@Column(name = "history_Addiction")
	private String historyOfAddiction;
	
	@Column(name = "mental_Health")
	private String mentalHealth;
	
	@Column(name = "height")
	private String height;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "excercise_PerWeek")
	private String excercisePerWeek;
	
	@Column(name = "doctor_Appointment")
	private String doctorAppointment;
	
	@Column(name = "dentist_Appointment")
	private String dentistAppointment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEthniCity() {
		return ethniCity;
	}

	public void setEthniCity(String ethniCity) {
		this.ethniCity = ethniCity;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEverBeenArrested() {
		return everBeenArrested;
	}

	public void setEverBeenArrested(String everBeenArrested) {
		this.everBeenArrested = everBeenArrested;
	}

	public String getConivtedOfFelony() {
		return conivtedOfFelony;
	}

	public void setConivtedOfFelony(String conivtedOfFelony) {
		this.conivtedOfFelony = conivtedOfFelony;
	}

	public String getSmartPhone() {
		return smartPhone;
	}

	public void setSmartPhone(String smartPhone) {
		this.smartPhone = smartPhone;
	}

	public String getMonthlyPlan() {
		return monthlyPlan;
	}

	public void setMonthlyPlan(String monthlyPlan) {
		this.monthlyPlan = monthlyPlan;
	}

	public String getNoOfSiblings() {
		return noOfSiblings;
	}

	public void setNoOfSiblings(String noOfSiblings) {
		this.noOfSiblings = noOfSiblings;
	}

	public String getNoOfKids() {
		return noOfKids;
	}

	public void setNoOfKids(String noOfKids) {
		this.noOfKids = noOfKids;
	}

	public String getLiveAtHome() {
		return liveAtHome;
	}

	public void setLiveAtHome(String liveAtHome) {
		this.liveAtHome = liveAtHome;
	}
	
	public String getHousingStatus() {
		return housingStatus;
	}

	public void setHousingStatus(String housingStatus) {
		this.housingStatus = housingStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getParentalEducation() {
		return parentalEducation;
	}

	public void setParentalEducation(String parentalEducation) {
		this.parentalEducation = parentalEducation;
	}

	public String getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(String enrolled) {
		this.enrolled = enrolled;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getMonthlySpendings() {
		return monthlySpendings;
	}

	public void setMonthlySpendings(String monthlySpendings) {
		this.monthlySpendings = monthlySpendings;
	}

	public String getNoOfMeals() {
		return noOfMeals;
	}

	public void setNoOfMeals(String noOfMeals) {
		this.noOfMeals = noOfMeals;
	}

	public String getFoodPreferences() {
		return foodPreferences;
	}

	public void setFoodPreferences(String foodPreferences) {
		this.foodPreferences = foodPreferences;
	}

	public String getPurchaseLocation() {
		return purchaseLocation;
	}

	public void setPurchaseLocation(String purchaseLocation) {
		this.purchaseLocation = purchaseLocation;
	}

	public String getCookingOptions() {
		return cookingOptions;
	}

	public void setCookingOptions(String cookingOptions) {
		this.cookingOptions = cookingOptions;
	}

	public String getDistanceOfTravel() {
		return distanceOfTravel;
	}

	public void setDistanceOfTravel(String distanceOfTravel) {
		this.distanceOfTravel = distanceOfTravel;
	}

	public String getTimeForTravel() {
		return timeForTravel;
	}

	public void setTimeForTravel(String timeForTravel) {
		this.timeForTravel = timeForTravel;
	}

	public String getSubsidisedFoodPrograms() {
		return subsidisedFoodPrograms;
	}

	public void setSubsidisedFoodPrograms(String subsidisedFoodPrograms) {
		this.subsidisedFoodPrograms = subsidisedFoodPrograms;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getHistoryOfAddiction() {
		return historyOfAddiction;
	}

	public void setHistoryOfAddiction(String historyOfAddiction) {
		this.historyOfAddiction = historyOfAddiction;
	}

	public String getMentalHealth() {
		return mentalHealth;
	}

	public void setMentalHealth(String mentalHealth) {
		this.mentalHealth = mentalHealth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getExcercisePerWeek() {
		return excercisePerWeek;
	}

	public void setExcercisePerWeek(String excercisePerWeek) {
		this.excercisePerWeek = excercisePerWeek;
	}

	public String getDoctorAppointment() {
		return doctorAppointment;
	}

	public void setDoctorAppointment(String doctorAppointment) {
		this.doctorAppointment = doctorAppointment;
	}

	public String getDentistAppointment() {
		return dentistAppointment;
	}

	public void setDentistAppointment(String dentistAppointment) {
		this.dentistAppointment = dentistAppointment;
	}
	

}
