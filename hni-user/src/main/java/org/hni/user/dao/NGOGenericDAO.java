package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.DefaultGenericDAO;
import org.hni.common.om.Persistable;
import org.springframework.stereotype.Component;
@Component
public class NGOGenericDAO extends DefaultGenericDAO {
public NGOGenericDAO()
{
	super();
	}

public <T extends Persistable> T saveBatch(Class<T> clazz, List<T> objList) {
	for(T obj:objList){
	if ( null == obj ) {
		return null;
	}
	if ( obj.getId() != null ) {
		return update(clazz, obj);
	} else {
		return insert(clazz, obj);
	}
	}
	return null;
}
	
}
