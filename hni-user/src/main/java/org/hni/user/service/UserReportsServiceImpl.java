package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.provider.om.Provider;
import org.hni.user.dao.CustomerDao;
import org.hni.user.dao.NGOGenericDAO;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class UserReportsServiceImpl implements UserReportService {

	@Inject
	private NGOGenericDAO ngoGenericDAO;

	@Override
	public List<NgoBasicDto> getAllNgo() {
		return ngoGenericDAO.getAllNgo();
	}

	@Override
	public List<Volunteer> getAllVolunteers(Long userId){
		return ngoGenericDAO.getAllVolunteers(userId);
	}

	@Inject
	private CustomerDao customerDao;

	@Override
	public List<User> getAllCustomersByRole() {

		return customerDao.getAllCustomersByRole();
	}

	@Override
	public List<User> getAllCustomersUnderOrganisation(User user) {
		return customerDao.getAllCustomersUnderOrganisation(user);
	}

	@Override
	public List<User> getAllCustomersEnrolledByNgo(User user) {
		return customerDao.getAllCustomersEnrolledByNgo(user);
	}

	@Override
	public List<ObjectNode> getAllProviders(User user) {
		return ngoGenericDAO.getAllProviders(user);
	}

}
