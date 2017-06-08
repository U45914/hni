package org.hni.provider.om;

public class NearByProviderDto {
	
	private String providerName;
	private String dow;
	private Long openHour;
	private Long closeHour;
	
	
	public NearByProviderDto() {
		super();
	}
	public NearByProviderDto(String providerName, String dow, Long openHour,
			Long closeHour) {
		super();
		this.providerName = providerName;
		this.dow = dow;
		this.openHour = openHour;
		this.closeHour = closeHour;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getDow() {
		return dow;
	}
	public void setDow(String dow) {
		this.dow = dow;
	}
	public Long getOpenHour() {
		return openHour;
	}
	public void setOpenHour(Long openHour) {
		this.openHour = openHour;
	}
	public Long getCloseHour() {
		return closeHour;
	}
	public void setCloseHour(Long closeHour) {
		this.closeHour = closeHour;
	}
	

}
