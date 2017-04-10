package org.hni.user.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "ngo")
public class Ngo implements Persistable, Serializable {

	private static final long serialVersionUID = 7553475738921092329L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address_id")
	private Long addressId;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "website")
	private String website;

	@Column(name = "contact_first_name")
	private String contactFirstName;
	
	@Column(name = "contact_last_name")
	private String contactLastName;
	
	@Column(name = "fte")
	private int fte;
	
	@Column(name = "overview")
	private String overview;
	
	@Column(name = "mission")
	private String mission;
	
	@Column(name = "endorsement_id")
	private Long endorsementId;
	
	@Column(name = "monthly_budget")
	private Long monthlyBudget;
	
	@Column(name = "operating_cost")
	private Long operatingCost;
	
	@Column(name = "personal_cost")
	private Long personalCost;
	
	@Column(name = "kitchen_volunteers")
	private Long kitchenVolunteers;
	
	@Column(name = "food_stamp_assist")
	private Boolean foodStampAssist;
	
	@Column(name = "food_bank")
	private Boolean foodBank;
	
	@Column(name = "resources_to_clients")
	private Long resourcesToClients;
	
	@Column(name = "ind_serv_daily")
	private Long indServDaily;
	
	@Column(name = "ind_serv_monthly")
	private Long indServMonthly;
	
	@Column(name = "ind_serv_annual")
	private Long indServAnnual;
	
	@Column(name = "client_info")
	private Boolean clientInfo;
	
	@Column(name = "store_client_info")
	private String storeClientInfo;
	
	@Column(name = "clients_unsheltered")
	private Long clientsUnsheltered;
	
	@Column(name = "clients_employed")
	private Long clientsEmployed;
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "created_by")
	private Long createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public int getFte() {
		return fte;
	}

	public void setFte(int fte) {
		this.fte = fte;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public Long getEndorsementId() {
		return endorsementId;
	}

	public void setEndorsementId(Long endorsementId) {
		this.endorsementId = endorsementId;
	}

	public Long getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(Long monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public Long getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(Long operatingCost) {
		this.operatingCost = operatingCost;
	}

	public Long getPersonalCost() {
		return personalCost;
	}

	public void setPersonalCost(Long personalCost) {
		this.personalCost = personalCost;
	}

	public Long getKitchenVolunteers() {
		return kitchenVolunteers;
	}

	public void setKitchenVolunteers(Long kitchenVolunteers) {
		this.kitchenVolunteers = kitchenVolunteers;
	}

	public Boolean getFoodStampAssist() {
		return foodStampAssist;
	}

	public void setFoodStampAssist(Boolean foodStampAssist) {
		this.foodStampAssist = foodStampAssist;
	}

	public Boolean getFoodBank() {
		return foodBank;
	}

	public void setFoodBank(Boolean foodBank) {
		this.foodBank = foodBank;
	}

	public Long getResourcesToClients() {
		return resourcesToClients;
	}

	public void setResourcesToClients(Long resourcesToClients) {
		this.resourcesToClients = resourcesToClients;
	}

	public Long getIndServDaily() {
		return indServDaily;
	}

	public void setIndServDaily(Long indServDaily) {
		this.indServDaily = indServDaily;
	}

	public Long getIndServMonthly() {
		return indServMonthly;
	}

	public void setIndServMonthly(Long indServMonthly) {
		this.indServMonthly = indServMonthly;
	}

	public Long getIndServAnnual() {
		return indServAnnual;
	}

	public void setIndServAnnual(Long indServAnnual) {
		this.indServAnnual = indServAnnual;
	}

	public Boolean getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Boolean clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getStoreClientInfo() {
		return storeClientInfo;
	}

	public void setStoreClientInfo(String storeClientInfo) {
		this.storeClientInfo = storeClientInfo;
	}

	public Long getClientsUnsheltered() {
		return clientsUnsheltered;
	}

	public void setClientsUnsheltered(Long clientsUnsheltered) {
		this.clientsUnsheltered = clientsUnsheltered;
	}

	public Long getClientsEmployed() {
		return clientsEmployed;
	}

	public void setClientsEmployed(Long clientsEmployed) {
		this.clientsEmployed = clientsEmployed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
}
