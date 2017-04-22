package org.hni.user.service;

import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.user.om.Volunteer;

public interface UserReportService {

	public List<NgoBasicDto> getAllNgo() ;
	public List<Volunteer> getAllVolunteers(Long userId);
	
}
