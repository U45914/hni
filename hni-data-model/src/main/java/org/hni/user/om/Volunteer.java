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
@Table(name = "volunteer")
public class Volunteer implements Persistable, Serializable  {
	private static final long serialVersionUID = 7553475738921092329L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "race")
	private String race;
	
	@Column(name = "highest_Level_of_education_completed")
	private String highestLLevelOfEducationCompleted;
	
	@Column(name = "marital_status")
	private String maritalStatus;
	
	@Column(name = "income")
	private String income;
	
	@Column(name = "kids")
	private int kids;
	
	@Column(name = "employer")
	private String employer;
	
	@Column(name = "non_profit")
	private String nonProfit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getHighestLLevelOfEducationCompleted() {
		return highestLLevelOfEducationCompleted;
	}

	public void setHighestLLevelOfEducationCompleted(
			String highestLLevelOfEducationCompleted) {
		this.highestLLevelOfEducationCompleted = highestLLevelOfEducationCompleted;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getNonProfit() {
		return nonProfit;
	}

	public void setNonProfit(String nonProfit) {
		this.nonProfit = nonProfit;
	}
	
}
