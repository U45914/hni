package org.hni.user.service;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.user.dao.VolunteerDao;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("defaultVolunteerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultVolunteerService extends AbstractService<Volunteer> implements VolunteerService {
 
	private VolunteerDao volunteerDao;

	@Inject
	public DefaultVolunteerService(VolunteerDao volunteerDao) {
		super(volunteerDao);
		this.volunteerDao = volunteerDao;
	}
	
	@Override
	public Volunteer save(Volunteer volunteer) {
		return volunteerDao.save(volunteer);
	}

/*	
	@Override
	public Volunteer get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volunteer insert(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volunteer update(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public Volunteer delete(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Volunteer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public Volunteer registerVolunteer(Volunteer volunteer) {
		return volunteerDao.save(volunteer);
	}*/

}
