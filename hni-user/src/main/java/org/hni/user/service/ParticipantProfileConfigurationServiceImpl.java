package org.hni.user.service;

import org.hni.common.dao.BaseDAO;
import org.hni.common.service.AbstractService;
import org.hni.user.om.ParticipantProfileConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ParticipantProfileConfigurationServiceImpl extends AbstractService<ParticipantProfileConfig>implements ParticipantProfileConfigurationService{

	public ParticipantProfileConfigurationServiceImpl(BaseDAO<ParticipantProfileConfig> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	
	/*@Override
	public ParticipantProfileConfig get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantProfileConfig insert(ParticipantProfileConfig obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantProfileConfig update(ParticipantProfileConfig obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantProfileConfig save(ParticipantProfileConfig obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantProfileConfig delete(ParticipantProfileConfig obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParticipantProfileConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}*/

}
