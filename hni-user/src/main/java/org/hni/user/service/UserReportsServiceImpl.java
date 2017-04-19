package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.user.dao.NGOGenericDAO;
import org.springframework.stereotype.Component;
@Component
public class UserReportsServiceImpl implements UserReportService{

	

	@Inject
	private NGOGenericDAO ngoGenericDAO;
	@Override
	public List<NgoBasicDto> getAllNgo() {
		
		return ngoGenericDAO.getAllNgo();
	}

}
