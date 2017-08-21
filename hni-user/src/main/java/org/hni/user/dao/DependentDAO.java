package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Dependent;

public interface DependentDAO extends BaseDAO<Dependent>{

	List<Dependent> getDependentById(Long id);
}
