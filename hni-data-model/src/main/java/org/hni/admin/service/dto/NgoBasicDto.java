package org.hni.admin.service.dto;

public class NgoBasicDto {
	
	Long userId;
	String name;
	String phone;
	String website;
	Long createdUsers;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getCreatedUsers() {
		return createdUsers;
	}
	public void setCreatedUsers(Long createdUsers) {
		this.createdUsers = createdUsers;
	}

}
