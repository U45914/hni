package org.hni.user.service;

import org.hni.common.service.BaseService;
import org.hni.user.om.Dependent;

public interface DependentService extends BaseService<Dependent> {

	Dependent getDependentById(Long id);
}
