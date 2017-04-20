package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.common.om.Persistable;
import org.hni.type.HNIRoles;
import org.springframework.stereotype.Component;
@Component
public class NGOGenericDAO extends DefaultGenericDAO {
public NGOGenericDAO()
{
	super();
	}

public <T extends Persistable>List<T> saveBatch(Class<T> clazz, List<T> objList) {
	for(T obj:objList){
	if ( null == obj ) {
		return null;
	}
	if ( obj.getId() != null ) {
		obj = update(clazz, obj);
	} else {
		obj = insert(clazz, obj);
	}
	}
	return objList;
}

public List<NgoBasicDto> getAllNgo()
{
	List<NgoBasicDto> ngos = new ArrayList<>();
	Long ngoRoleId =  HNIRoles.NGO.getRole();
	List<Object[]> userOrganizationRoles=em.createNativeQuery("select u.id,u.first_name,u.last_name,u.mobile_phone,n.website from user_organization_role x INNER JOIN users u ON u.id = x.user_id LEFT OUTER JOIN ngo n ON n.id=u.id where x.role_id=:roleId").setParameter("roleId", ngoRoleId).getResultList();
	for(Object[] u:userOrganizationRoles)
	{
		 NgoBasicDto ngoBasicDto = new NgoBasicDto();
		 Long userId = Long.valueOf(u[0].toString());
		
		 ngoBasicDto.setUserId(userId);
		 ngoBasicDto.setName(u[1]+" "+u[2]);
		 ngoBasicDto.setPhone((String) u[3]);
		 ngoBasicDto.setWebsite(u[4]!=null?(String) u[4]:"");
		 ngoBasicDto.setCreatedUsers((Long) em.createQuery("select count(id) from Ngo where createdBy=:userId").setParameter("userId", userId).getSingleResult());
		 //TO DO the changes when Client TABLE is alive instd of Ngo
		 ngos.add(ngoBasicDto);
	}
	return ngos;
	 
	
}
	
}
