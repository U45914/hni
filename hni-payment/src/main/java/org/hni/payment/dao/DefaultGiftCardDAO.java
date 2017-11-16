package org.hni.payment.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.dao.AbstractDAO;
import org.hni.payment.om.PaymentInstrument;
import org.springframework.stereotype.Component;

@Component
public class DefaultGiftCardDAO extends AbstractDAO<PaymentInstrument>
		implements GiftCardDAO {

	protected DefaultGiftCardDAO() {
		super(PaymentInstrument.class);
		// TODO Auto-generated constructor stub
	}


	@Override
	public List<PaymentInstrumentDto> getGitCardsFor(Long providerId) {
		try {
			Query q = em.createQuery("SELECT x.balance, x.cardNumber, x.cardSerialId, x.status, x.originalBalance, x.stateCode, x.id FROM PaymentInstrument x WHERE x.provider.id = :providerId")
				.setParameter("providerId", providerId);
			List<Object[]> existingGiftCardList = q.getResultList();
			List<PaymentInstrumentDto> newGiftCards = new ArrayList<>();
			if(existingGiftCardList.isEmpty()){
				return null;
			} else{
				for(Object[] u: existingGiftCardList){
					PaymentInstrumentDto paymentInstrumentDto = new PaymentInstrumentDto();
					paymentInstrumentDto.setBalance(Double.valueOf(u[0].toString()));
					paymentInstrumentDto.setCardNumber(u[1].toString());
					paymentInstrumentDto.setCardSerialId(u[2].toString());
					paymentInstrumentDto.setStatus(u[3].toString());
					paymentInstrumentDto.setOriginalBalance(Double.valueOf(u[4].toString()));
					paymentInstrumentDto.setStateCode(u[5].toString());
					paymentInstrumentDto.setId(Long.valueOf(u[6].toString()));
					newGiftCards.add(paymentInstrumentDto);
				}
			}
			return newGiftCards;
		} catch(NoResultException e) {
			return Collections.emptyList();
		}

	}

}
