package org.hni.user.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.ParticipantProfileConfig;
import org.springframework.stereotype.Component;

@Component
public class ParticipantProfileConfigurationDAOImpl extends AbstractDAO<ParticipantProfileConfig> implements ParticipantProfileConfigurationDAO{

	protected ParticipantProfileConfigurationDAOImpl() {
		super(ParticipantProfileConfig.class);
	}

}
