package org.hni.user.service;

import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.user.om.User;

public interface UserReportService {

	public List<NgoBasicDto> getAllNgo() ;
	public List<User> getAllCustomersByRole();
	public List<User> getAllCustomersUnderOrganisation(User user);
	
}
