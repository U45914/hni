package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.common.om.Persistable;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.user.om.Ngo;
import org.hni.user.om.User;
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

public List<NgoBasicDto> getAllNgo()
{
	List<NgoBasicDto> ngos = new ArrayList<>();
	List<Long> ngoRoleIds=  em.createQuery("select id from Role x where x.name='NGO'").getResultList();
	Long ngoRoleId = ngoRoleIds.get(0);
	List<UserOrganizationRole> userOrganizationRoles=em.createQuery("select x from UserOrganizationRole x where x.id.roleId=:roleId").setParameter("roleId", ngoRoleId).getResultList();
	 for(UserOrganizationRole userOrganizationRole:userOrganizationRoles)
	 {
		 Long userId= userOrganizationRole.getId().getUserId();
		 Ngo ngoDetail=get(Ngo.class, userId);
		 NgoBasicDto ngoBasicDto = new NgoBasicDto();
		 User ngo = get(User.class, userId);
		 ngoBasicDto.setUser_id(userId);
		 ngoBasicDto.setName(ngo.getFirstName()+" "+ngo.getLastName());
		 ngoBasicDto.setPhone(ngo.getMobilePhone());
		 ngoBasicDto.setWebsite(ngoDetail!=null?ngoDetail.getWebsite():"");
		 ngoBasicDto.setCreatedUsers((Long) em.createQuery("select count(id) from Ngo where createdBy=:userId").setParameter("userId", userId).getSingleResult());
		 //TO DO the changes when Customer TABLE is alive instd of Ngo
		 ngos.add(ngoBasicDto);
	 }
	return ngos;
	 
	
}
	
}
