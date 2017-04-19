package org.hni.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.service.BaseService;
import org.hni.user.om.Invitation;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserOnboardingService extends BaseService<Invitation>{
	String buildInvitationAndSave(Long orgId);
	Collection<Invitation> validateInvitationCode(String invitationCode);
	Map<String,String> ngoSave(ObjectNode onboardData);
	ObjectNode getNGODetail(Long ngoId);
	List<NgoBasicDto> getAllNgo();
}
