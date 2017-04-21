package org.hni.user.om;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "client")
public class Client implements Persistable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Long userId;
	private Long createdBy;
	private Long race;
	private Long addressId;
	private Integer bday;
	private Character beenArrested;
	private Character beenConvicted;
	private Character hasSmartPhone;
	private String serviceProvider;
	private String model;
	private Character haveMonthlyPlan;
	private String monthlyPlanMinute;
	private String monthlyPlanData;
	private String monthlyPlanCost;
	private Integer altMonthlyPlan;
	private String altMonthlyPlanTogether;
	private Integer sliblings;
	private Integer kids;
	private Character liveAtHome;
	private Integer sheltered;
	private Integer parentEducation;
	private Integer education;
	private Integer enrollmentStatus;
	private String enrollmentLocation;
	private Integer workStatus;
	private Integer timeToWorkplace;
	private Integer noOfJob;
	private String employer;
	private String jobTitle;
	private Integer durationOfEmployement;
	private Character unemploymentBenfits;
	private String reasonUnemploymentBenefits;
	private Double totalIncome;
	private Integer rateAmount;
	private Integer rateType;
	private String avgHoursPerWeek;
	private Integer residentStatus;
	private Integer dollarSpendFood;
	private Integer dollarSpendClothes;
	private Integer dollarSpendEntertainment;
	private Integer dollarSpendTransport;
	private Integer dollarSpendSavings;
	private Integer mealsPerDay;
	private Integer foodPreference;
	private String foodSource;
	private Character cook;
	private Integer travelForFoodDistance;
	private Integer travalForFoodTime;
	private Character subFoodProgram;
	private String subFoodProgramEntity;
	private Integer subFoodProgramDuration;
	private Integer subFoodProgramRenew;
	private String subFoodProgramExp;
	private String allergies;
	private Character addiction;
	private String addictionType;
	private Character mentalHealthIssue;
	private String mentalHealthIssueHistory;
	private String height;
	private String weight;
	private Integer exercisePerWeek;
	private Integer lastVisitDoctor;
	private Integer lastVisitDentist;
	
	@Transient
	private Address address;

	public Client() {
	}

	public Client(Long userId, Long createdBy, Long race, Long addressId) {
		this.userId = userId;
		this.createdBy = createdBy;
		this.race = race;
		this.addressId = addressId;
	}

	public Client(Long userId, Long createdBy, Long race, Long addressId,
			Integer bday, Character beenArrested, Character beenConvicted,
			Character hasSmartPhone, String serviceProvider, String model,
			Character haveMonthlyPlan, String monthlyPlanMinute,
			String monthlyPlanData, String monthlyPlanCost,
			Integer altMonthlyPlan, String altMonthlyPlanTogether,
			Integer sliblings, Integer kids, Character liveAtHome,
			Integer sheltered, Integer parentEducation, Integer education,
			Integer enrollmentStatus, String enrollmentLocation,
			Integer workStatus, Integer timeToWorkplace, Integer noOfJob,
			String employer, String jobTitle, Integer durationOfEmployement,
			Character unemploymentBenfits, String reasonUnemploymentBenefits,
			Double totalIncome, Integer rateAmount, Integer rateType,
			String avgHoursPerWeek, Integer residentStatus,
			Integer dollarSpendFood, Integer dollarSpendClothes,
			Integer dollarSpendEntertainment, Integer dollarSpendTransport,
			Integer dollarSpendSavings, Integer mealsPerDay,
			Integer foodPreference, String foodSource, Character cook,
			Integer travelForFoodDistance, Integer travalForFoodTime,
			Character subFoodProgram, String subFoodProgramEntity,
			Integer subFoodProgramDuration, Integer subFoodProgramRenew,
			String subFoodProgramExp, String allergies, Character addiction,
			String addictionType, Character mentalHealthIssue,
			String mentalHealthIssueHistory, String height, String weight,
			Integer exercisePerWeek, Integer lastVisitDoctor,
			Integer lastVisitDentist) {
		this.userId = userId;
		this.createdBy = createdBy;
		this.race = race;
		this.addressId = addressId;
		this.bday = bday;
		this.beenArrested = beenArrested;
		this.beenConvicted = beenConvicted;
		this.hasSmartPhone = hasSmartPhone;
		this.serviceProvider = serviceProvider;
		this.model = model;
		this.haveMonthlyPlan = haveMonthlyPlan;
		this.monthlyPlanMinute = monthlyPlanMinute;
		this.monthlyPlanData = monthlyPlanData;
		this.monthlyPlanCost = monthlyPlanCost;
		this.altMonthlyPlan = altMonthlyPlan;
		this.altMonthlyPlanTogether = altMonthlyPlanTogether;
		this.sliblings = sliblings;
		this.kids = kids;
		this.liveAtHome = liveAtHome;
		this.sheltered = sheltered;
		this.parentEducation = parentEducation;
		this.education = education;
		this.enrollmentStatus = enrollmentStatus;
		this.enrollmentLocation = enrollmentLocation;
		this.workStatus = workStatus;
		this.timeToWorkplace = timeToWorkplace;
		this.noOfJob = noOfJob;
		this.employer = employer;
		this.jobTitle = jobTitle;
		this.durationOfEmployement = durationOfEmployement;
		this.unemploymentBenfits = unemploymentBenfits;
		this.reasonUnemploymentBenefits = reasonUnemploymentBenefits;
		this.totalIncome = totalIncome;
		this.rateAmount = rateAmount;
		this.rateType = rateType;
		this.avgHoursPerWeek = avgHoursPerWeek;
		this.residentStatus = residentStatus;
		this.dollarSpendFood = dollarSpendFood;
		this.dollarSpendClothes = dollarSpendClothes;
		this.dollarSpendEntertainment = dollarSpendEntertainment;
		this.dollarSpendTransport = dollarSpendTransport;
		this.dollarSpendSavings = dollarSpendSavings;
		this.mealsPerDay = mealsPerDay;
		this.foodPreference = foodPreference;
		this.foodSource = foodSource;
		this.cook = cook;
		this.travelForFoodDistance = travelForFoodDistance;
		this.travalForFoodTime = travalForFoodTime;
		this.subFoodProgram = subFoodProgram;
		this.subFoodProgramEntity = subFoodProgramEntity;
		this.subFoodProgramDuration = subFoodProgramDuration;
		this.subFoodProgramRenew = subFoodProgramRenew;
		this.subFoodProgramExp = subFoodProgramExp;
		this.allergies = allergies;
		this.addiction = addiction;
		this.addictionType = addictionType;
		this.mentalHealthIssue = mentalHealthIssue;
		this.mentalHealthIssueHistory = mentalHealthIssueHistory;
		this.height = height;
		this.weight = weight;
		this.exercisePerWeek = exercisePerWeek;
		this.lastVisitDoctor = lastVisitDoctor;
		this.lastVisitDentist = lastVisitDentist;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "created_by", nullable = false)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "race", nullable = false)
	public Long getRace() {
		return this.race;
	}

	public void setRace(Long race) {
		this.race = race;
	}

	@Column(name = "address_id", nullable = false)
	public Long getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	@Column(name = "bday")
	public Integer getBday() {
		return this.bday;
	}

	public void setBday(Integer bday) {
		this.bday = bday;
	}

	@Column(name = "been_arrested", length = 1)
	public Character getBeenArrested() {
		return this.beenArrested;
	}

	public void setBeenArrested(Character beenArrested) {
		this.beenArrested = beenArrested;
	}

	@Column(name = "been_convicted", length = 1)
	public Character getBeenConvicted() {
		return this.beenConvicted;
	}

	public void setBeenConvicted(Character beenConvicted) {
		this.beenConvicted = beenConvicted;
	}

	@Column(name = "has_smart_phone", length = 1)
	public Character getHasSmartPhone() {
		return this.hasSmartPhone;
	}

	public void setHasSmartPhone(Character hasSmartPhone) {
		this.hasSmartPhone = hasSmartPhone;
	}

	@Column(name = "service_provider", length = 50)
	public String getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Column(name = "model", length = 50)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "have_monthly_plan", length = 1)
	public Character getHaveMonthlyPlan() {
		return this.haveMonthlyPlan;
	}

	public void setHaveMonthlyPlan(Character haveMonthlyPlan) {
		this.haveMonthlyPlan = haveMonthlyPlan;
	}

	@Column(name = "monthly_plan_minute", length = 50)
	public String getMonthlyPlanMinute() {
		return this.monthlyPlanMinute;
	}

	public void setMonthlyPlanMinute(String monthlyPlanMinute) {
		this.monthlyPlanMinute = monthlyPlanMinute;
	}

	@Column(name = "monthly_plan_data", length = 50)
	public String getMonthlyPlanData() {
		return this.monthlyPlanData;
	}

	public void setMonthlyPlanData(String monthlyPlanData) {
		this.monthlyPlanData = monthlyPlanData;
	}

	@Column(name = "monthly_plan_cost", length = 50)
	public String getMonthlyPlanCost() {
		return this.monthlyPlanCost;
	}

	public void setMonthlyPlanCost(String monthlyPlanCost) {
		this.monthlyPlanCost = monthlyPlanCost;
	}

	@Column(name = "alt_monthly_plan")
	public Integer getAltMonthlyPlan() {
		return this.altMonthlyPlan;
	}

	public void setAltMonthlyPlan(Integer altMonthlyPlan) {
		this.altMonthlyPlan = altMonthlyPlan;
	}

	@Column(name = "alt_monthly_plan_together", length = 50)
	public String getAltMonthlyPlanTogether() {
		return this.altMonthlyPlanTogether;
	}

	public void setAltMonthlyPlanTogether(String altMonthlyPlanTogether) {
		this.altMonthlyPlanTogether = altMonthlyPlanTogether;
	}

	@Column(name = "sliblings")
	public Integer getSliblings() {
		return this.sliblings;
	}

	public void setSliblings(Integer sliblings) {
		this.sliblings = sliblings;
	}

	@Column(name = "kids")
	public Integer getKids() {
		return this.kids;
	}

	public void setKids(Integer kids) {
		this.kids = kids;
	}

	@Column(name = "live_at_home", length = 1)
	public Character getLiveAtHome() {
		return this.liveAtHome;
	}

	public void setLiveAtHome(Character liveAtHome) {
		this.liveAtHome = liveAtHome;
	}

	@Column(name = "sheltered")
	public Integer getSheltered() {
		return this.sheltered;
	}

	public void setSheltered(Integer sheltered) {
		this.sheltered = sheltered;
	}

	@Column(name = "parent_education")
	public Integer getParentEducation() {
		return this.parentEducation;
	}

	public void setParentEducation(Integer parentEducation) {
		this.parentEducation = parentEducation;
	}

	@Column(name = "education")
	public Integer getEducation() {
		return this.education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	@Column(name = "enrollment_status")
	public Integer getEnrollmentStatus() {
		return this.enrollmentStatus;
	}

	public void setEnrollmentStatus(Integer enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	@Column(name = "enrollment_location", length = 50)
	public String getEnrollmentLocation() {
		return this.enrollmentLocation;
	}

	public void setEnrollmentLocation(String enrollmentLocation) {
		this.enrollmentLocation = enrollmentLocation;
	}

	@Column(name = "work_status")
	public Integer getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	@Column(name = "time_to_workplace")
	public Integer getTimeToWorkplace() {
		return this.timeToWorkplace;
	}

	public void setTimeToWorkplace(Integer timeToWorkplace) {
		this.timeToWorkplace = timeToWorkplace;
	}

	@Column(name = "no_of_job")
	public Integer getNoOfJob() {
		return this.noOfJob;
	}

	public void setNoOfJob(Integer noOfJob) {
		this.noOfJob = noOfJob;
	}

	@Column(name = "employer", length = 50)
	public String getEmployer() {
		return this.employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	@Column(name = "job_title", length = 50)
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "duration_of_employement")
	public Integer getDurationOfEmployement() {
		return this.durationOfEmployement;
	}

	public void setDurationOfEmployement(Integer durationOfEmployement) {
		this.durationOfEmployement = durationOfEmployement;
	}

	@Column(name = "unemployment_benfits", length = 1)
	public Character getUnemploymentBenfits() {
		return this.unemploymentBenfits;
	}

	public void setUnemploymentBenfits(Character unemploymentBenfits) {
		this.unemploymentBenfits = unemploymentBenfits;
	}

	@Column(name = "reason_unemployment_benefits", length = 100)
	public String getReasonUnemploymentBenefits() {
		return this.reasonUnemploymentBenefits;
	}

	public void setReasonUnemploymentBenefits(String reasonUnemploymentBenefits) {
		this.reasonUnemploymentBenefits = reasonUnemploymentBenefits;
	}

	@Column(name = "total_income", precision = 22, scale = 0)
	public Double getTotalIncome() {
		return this.totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}

	@Column(name = "rate_amount")
	public Integer getRateAmount() {
		return this.rateAmount;
	}

	public void setRateAmount(Integer rateAmount) {
		this.rateAmount = rateAmount;
	}

	@Column(name = "rate_type")
	public Integer getRateType() {
		return this.rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	@Column(name = "avg_hours_per_week")
	public String getAvgHoursPerWeek() {
		return this.avgHoursPerWeek;
	}

	public void setAvgHoursPerWeek(String avgHoursPerWeek) {
		this.avgHoursPerWeek = avgHoursPerWeek;
	}

	@Column(name = "resident_status")
	public Integer getResidentStatus() {
		return this.residentStatus;
	}

	public void setResidentStatus(Integer residentStatus) {
		this.residentStatus = residentStatus;
	}

	@Column(name = "dollar_spend_food")
	public Integer getDollarSpendFood() {
		return this.dollarSpendFood;
	}

	public void setDollarSpendFood(Integer dollarSpendFood) {
		this.dollarSpendFood = dollarSpendFood;
	}

	@Column(name = "dollar_spend_clothes")
	public Integer getDollarSpendClothes() {
		return this.dollarSpendClothes;
	}

	public void setDollarSpendClothes(Integer dollarSpendClothes) {
		this.dollarSpendClothes = dollarSpendClothes;
	}

	@Column(name = "dollar_spend_entertainment")
	public Integer getDollarSpendEntertainment() {
		return this.dollarSpendEntertainment;
	}

	public void setDollarSpendEntertainment(Integer dollarSpendEntertainment) {
		this.dollarSpendEntertainment = dollarSpendEntertainment;
	}

	@Column(name = "dollar_spend_transport")
	public Integer getDollarSpendTransport() {
		return this.dollarSpendTransport;
	}

	public void setDollarSpendTransport(Integer dollarSpendTransport) {
		this.dollarSpendTransport = dollarSpendTransport;
	}

	@Column(name = "dollar_spend_savings")
	public Integer getDollarSpendSavings() {
		return this.dollarSpendSavings;
	}

	public void setDollarSpendSavings(Integer dollarSpendSavings) {
		this.dollarSpendSavings = dollarSpendSavings;
	}

	@Column(name = "meals_per_day")
	public Integer getMealsPerDay() {
		return this.mealsPerDay;
	}

	public void setMealsPerDay(Integer mealsPerDay) {
		this.mealsPerDay = mealsPerDay;
	}

	@Column(name = "food_preference")
	public Integer getFoodPreference() {
		return this.foodPreference;
	}

	public void setFoodPreference(Integer foodPreference) {
		this.foodPreference = foodPreference;
	}

	@Column(name = "food_source", length = 50)
	public String getFoodSource() {
		return this.foodSource;
	}

	public void setFoodSource(String foodSource) {
		this.foodSource = foodSource;
	}

	@Column(name = "cook", length = 1)
	public Character getCook() {
		return this.cook;
	}

	public void setCook(Character cook) {
		this.cook = cook;
	}

	@Column(name = "travel_for_food_distance")
	public Integer getTravelForFoodDistance() {
		return this.travelForFoodDistance;
	}

	public void setTravelForFoodDistance(Integer travelForFoodDistance) {
		this.travelForFoodDistance = travelForFoodDistance;
	}

	@Column(name = "traval_for_food_time")
	public Integer getTravalForFoodTime() {
		return this.travalForFoodTime;
	}

	public void setTravalForFoodTime(Integer travalForFoodTime) {
		this.travalForFoodTime = travalForFoodTime;
	}

	@Column(name = "sub_food_program", length = 1)
	public Character getSubFoodProgram() {
		return this.subFoodProgram;
	}

	public void setSubFoodProgram(Character subFoodProgram) {
		this.subFoodProgram = subFoodProgram;
	}

	@Column(name = "sub_food_program_entity", length = 50)
	public String getSubFoodProgramEntity() {
		return this.subFoodProgramEntity;
	}

	public void setSubFoodProgramEntity(String subFoodProgramEntity) {
		this.subFoodProgramEntity = subFoodProgramEntity;
	}

	@Column(name = "sub_food_program_duration")
	public Integer getSubFoodProgramDuration() {
		return this.subFoodProgramDuration;
	}

	public void setSubFoodProgramDuration(Integer subFoodProgramDuration) {
		this.subFoodProgramDuration = subFoodProgramDuration;
	}

	@Column(name = "sub_food_program_renew")
	public Integer getSubFoodProgramRenew() {
		return this.subFoodProgramRenew;
	}

	public void setSubFoodProgramRenew(Integer subFoodProgramRenew) {
		this.subFoodProgramRenew = subFoodProgramRenew;
	}

	@Column(name = "sub_food_program_exp", length = 256)
	public String getSubFoodProgramExp() {
		return this.subFoodProgramExp;
	}

	public void setSubFoodProgramExp(String subFoodProgramExp) {
		this.subFoodProgramExp = subFoodProgramExp;
	}

	@Column(name = "allergies", length = 256)
	public String getAllergies() {
		return this.allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	@Column(name = "addiction", length = 1)
	public Character getAddiction() {
		return this.addiction;
	}

	public void setAddiction(Character addiction) {
		this.addiction = addiction;
	}

	@Column(name = "addiction_type", length = 50)
	public String getAddictionType() {
		return this.addictionType;
	}

	public void setAddictionType(String addictionType) {
		this.addictionType = addictionType;
	}

	@Column(name = "mental_health_issue", length = 1)
	public Character getMentalHealthIssue() {
		return this.mentalHealthIssue;
	}

	public void setMentalHealthIssue(Character mentalHealthIssue) {
		this.mentalHealthIssue = mentalHealthIssue;
	}

	@Column(name = "mental_health_issue_history", length = 256)
	public String getMentalHealthIssueHistory() {
		return this.mentalHealthIssueHistory;
	}

	public void setMentalHealthIssueHistory(String mentalHealthIssueHistory) {
		this.mentalHealthIssueHistory = mentalHealthIssueHistory;
	}

	@Column(name = "height", length = 50)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "weight", length = 50)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "exercise_per_week")
	public Integer getExercisePerWeek() {
		return this.exercisePerWeek;
	}

	public void setExercisePerWeek(Integer exercisePerWeek) {
		this.exercisePerWeek = exercisePerWeek;
	}

	@Column(name = "last_visit_doctor")
	public Integer getLastVisitDoctor() {
		return this.lastVisitDoctor;
	}

	public void setLastVisitDoctor(Integer lastVisitDoctor) {
		this.lastVisitDoctor = lastVisitDoctor;
	}

	@Column(name = "last_visit_dentist")
	public Integer getLastVisitDentist() {
		return this.lastVisitDentist;
	}
	
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setLastVisitDentist(Integer lastVisitDentist) {
		this.lastVisitDentist = lastVisitDentist;
	}

}
