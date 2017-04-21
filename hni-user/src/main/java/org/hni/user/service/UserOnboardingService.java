package org.hni.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.service.BaseService;
import org.hni.user.om.Client;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserOnboardingService extends BaseService<Invitation>{
	String buildInvitationAndSave(Long orgId, Long invitedBy, String email);
	Collection<Invitation> validateInvitationCode(String invitationCode);
	Map<String,String> ngoSave(ObjectNode onboardData);
	ObjectNode getNGODetail(Long ngoId);
	List<NgoBasicDto> getAllNgo();
	Map<String,String> buildVolunteerAndSave(Volunteer volunteer,  User user);
	Map<String,String> clientSave(Client client, User user);
}
