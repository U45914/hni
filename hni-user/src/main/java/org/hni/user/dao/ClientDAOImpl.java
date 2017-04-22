package org.hni.user.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Address;
import org.hni.user.om.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDAOImpl extends AbstractDAO<Client> implements ClientDAO  {

	protected ClientDAOImpl() {
		super(Client.class);
	}

}
