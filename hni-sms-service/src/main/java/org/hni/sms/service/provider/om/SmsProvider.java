/**
 * 
 */
package org.hni.sms.service.provider.om;

import org.hni.sms.service.provider.ServiceProvider;

/**
 * @author Rahul
 *
 */
public class SmsProvider {

	private Integer id;
	private ServiceProvider providerName;
	private String longCode;
	private String shortCode;
	private String stateCode;
	private String description;
	private String created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServiceProvider getProviderName() {
		return providerName;
	}

	public void setProviderName(ServiceProvider providerName) {
		this.providerName = providerName;
	}

	public String getLongCode() {
		return longCode;
	}

	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
