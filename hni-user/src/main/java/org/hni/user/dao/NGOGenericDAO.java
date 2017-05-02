package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.common.om.Persistable;
import org.hni.provider.om.Provider;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


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
		 Long userId = Long.valueOf(getValue(u[0]));
		
		 ngoBasicDto.setUserId(userId);
		 ngoBasicDto.setName(getValue(u[1])+" "+getValue(u[2]));
		 ngoBasicDto.setPhone(getValue(u[3]));
		 ngoBasicDto.setWebsite(u[4]!=null?(String) u[4]:"");
		 ngoBasicDto.setCreatedUsers((Long) em.createQuery("select count(id) from Client where createdBy=:userId").setParameter("userId", userId).getSingleResult());
		 ngos.add(ngoBasicDto);
	}
	return ngos;
	 
	
}

public List<Volunteer> getAllVolunteers(Long loggedInUserId) {

	List<Volunteer> volunteerList = new ArrayList<>();
	List<Long> orgIds = em.createQuery("select x.id.orgId from UserOrganizationRole x where x.id.userId=:userId ")
				  		  .setParameter("userId", loggedInUserId)
				  		  .getResultList();
	if(orgIds.size() > 0){
		Long orgId = orgIds.get(0);		
		List<Long> volunteerRoleIds = em.createQuery("select id from Role x where x.name='Volunteer'")
										.getResultList();
		
		Long volunteerRoleId = volunteerRoleIds.get(0);		
		List<Object[]> userDetails = em.createNativeQuery("SELECT u.id, u.first_name, u.last_name, u.gender_code, u.email FROM users u INNER JOIN user_organization_role uo WHERE uo.role_id=:roleId AND uo.organization_id=:orgId AND u.id = uo.user_id")
									   .setParameter("roleId", volunteerRoleId)
									   .setParameter("orgId", orgId)
									   .getResultList();
		
		for (Object[] user : userDetails) {			
			Volunteer volunteer = new Volunteer();		
			volunteer.setId(Long.valueOf(getValue(user[0])));
			volunteer.setFirstName(getValue(user[1]));
			volunteer.setLastName(getValue(user[2]));
			volunteer.setSex(getValue(user[3]));
			volunteer.setEmail(getValue(user[4]));
			volunteerList.add(volunteer);
		}		
	}
	
	return volunteerList;
}
public void updateStatus(Long userId) {
	Long id = (Long) em.createQuery("select x.id from UserPartialData x where x.userId =:userId").setParameter("userId", userId).getSingleResult();
	if(id!=null){
		UserPartialData user = get(UserPartialData.class,id);
		user.setStatus("Y");
		save(UserPartialData.class,user);
	}
}

public List<ObjectNode> getAllProviders(User user) {
	List<ObjectNode> providers= new ArrayList<>();
	Long userId=user.getId();
	List<Object[]> result=em.createNativeQuery("select p.name as provider_name,p.website_url,p.created,u.first_name,a.name from providers p INNER JOIN users u  ON p.created_by =u.id INNER JOIN addresses a ON p.address_id=a.id and p.created_by=:uId").setParameter("uId",userId).getResultList();
	for(Object[] prov:result){
		ObjectNode provider=new ObjectMapper().createObjectNode();
		provider.put("name", getValue(prov[0]));
		provider.put("website",getValue(prov[1]));
		provider.put("createdOn",getValue(prov[2]));
		provider.put("createdBy",getValue(prov[3]));
		provider.put("address",getValue(prov[4]));
		providers.add(provider);
		
	}
	return providers;
}
}
