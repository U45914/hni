package org.hni.admin.service.dto;

public class NgoBasicDto {
	
	Long user_id;
	String name;
	String phone;
	String website;
	Long createdUsers;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long userId) {
		this.user_id = userId;
	}
	public String getPhone() {
		return phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name = name;
	}
	public void setPhone(String phone) {
		phone = phone;
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
