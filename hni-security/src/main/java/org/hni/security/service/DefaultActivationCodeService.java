package org.hni.security.service;

import org.apache.commons.codec.binary.Base64;
import org.hni.common.service.AbstractService;
import org.hni.security.dao.ActivationCodeDAO;
import org.hni.security.om.ActivationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultActivationCodeService extends AbstractService<ActivationCode> implements ActivationCodeService {
	private static final Logger logger = LoggerFactory.getLogger(ActivationCodeService.class);
	private ActivationCodeDAO activationCodeDao;

	private static final int LARGE_BASE = 100000;
	private static final int LARGE_PRIME = 999983;
	private static final int OFFSET = 151647;
	
	@Inject
	public DefaultActivationCodeService(ActivationCodeDAO activationCodeDao) {
		super(activationCodeDao);
		this.activationCodeDao = activationCodeDao;
	}

	@Override
	public <T> boolean validate(T authCode) {
        Long authCodeLong;
        try {
			// wmenez1: why is there a need for a generic here?
			if (authCode instanceof String) {
				authCodeLong = Long.valueOf((String) authCode);
			} else {
				authCodeLong = Long.valueOf((Long) authCode);
			}
        } catch (ClassCastException e) {
            return false;
        }

		ActivationCode code = getByActivationCode(authCodeLong);
		return code != null
            && code.getOrganizationId() != null
			&& !code.isActivated()
			&& code.getMealsRemaining() > 0
			&& code.getMealsAuthorized() > 0;
	}

	@Override
    public ActivationCode getByActivationCode(Long authCode) {
        return activationCodeDao.getByActivationCode(authCode);
    }

    /**
     * WARNING This implementation WILL NOT work with JPA generated Ids. This method was implemented
     * with the assumption that authCodes are already available, and decoded values of authCodes will be
     * inserted to the database manually
     * @param authCodeId
     * @return
     */
	public Long encode(String authCodeId) {
        byte[] authCodeIdBytes = authCodeId.getBytes();
        String authCodeStr = new String(Base64.decodeBase64(authCodeIdBytes));
        return Long.valueOf(authCodeStr);
	}

	public String decode(Long authCode) {
        byte[] authCodeStr = String.valueOf(authCode).getBytes();
        return new String(Base64.encodeBase64(authCodeStr));
	}

}