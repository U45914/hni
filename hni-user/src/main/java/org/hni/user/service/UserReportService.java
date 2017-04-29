package org.hni.user.service;

import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.provider.om.Provider;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserReportService {

	public List<NgoBasicDto> getAllNgo() ;
	public List<User> getAllCustomersByRole();
	public List<User> getAllCustomersUnderOrganisation(User user);
	public List<User> getAllCustomersEnrolledByNgo(User user);
	public List<Volunteer> getAllVolunteers(Long userId);
	public List<ObjectNode> getAllProviders(User user);
	
}
