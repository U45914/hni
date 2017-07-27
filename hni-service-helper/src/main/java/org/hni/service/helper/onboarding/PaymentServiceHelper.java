/**
 * 
 */
package org.hni.service.helper.onboarding;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import org.hni.common.Constants;
import org.hni.common.exception.HNIException;
import org.hni.common.service.HniTemplateService;
import org.hni.order.om.Order;
import org.hni.order.service.OrderService;
import org.hni.payment.om.OrderPayment;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.OrderPaymentService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.template.om.HniTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class PaymentServiceHelper extends AbstractServiceHelper {

	private static final String OK = "OK";
	
	private final static Logger _LOGGER = LoggerFactory.getLogger(PaymentServiceHelper.class);

	private static final String ORDER_COMPLETED = "C";
	
	private static final String NO_ORDER_FOUND_FOR_ORDER = "Order does not exist. Order # %d";
	
	@Inject
	private OrderService orderService;
	@Inject
	private OrderPaymentService orderPaymentService;
	@Inject
	private PushMessageService smsMessageService;
	@Inject 
	private HniTemplateService hniTemplateService;
	

	public String completeOrder(Long orderId, String confirmationId, Double orderAmount) throws HNIException {
		String uniqIdForOrderTrace = Constants.HNI_CAP + orderId + confirmationId;
		_LOGGER.info("Starting the process of order completion : " + uniqIdForOrderTrace);
		Optional<Order> order = Optional.ofNullable(orderService.get(orderId));
		String message = "";
		if (order.isPresent()) {
			_LOGGER.info(String.format("Marking order %d complete", order.get().getId()));
			Collection<OrderPayment> paymentsForOrder = orderPaymentService.paymentsFor(order.get());
			
			double subTotal = updateOrderPaymentStatusAndGetSubTotal(paymentsForOrder, uniqIdForOrderTrace).doubleValue();	
			if (subTotal <= 0.0) {
				subTotal = orderAmount;
			}
			order.get().setSubTotal(subTotal);
			order.get().setOrderConfirmationId(confirmationId);
			
			orderService.complete(order.get());
			
			sendOrderConfirmation(order.get(), confirmationId, uniqIdForOrderTrace);
			_LOGGER.info("Completed the process of order completion : " + uniqIdForOrderTrace);
			return OK;
		} else {
			message = String.format(NO_ORDER_FOUND_FOR_ORDER, orderId);
			_LOGGER.error(message + uniqIdForOrderTrace);
			throw new HNIException(message);
		}
	}

	private void sendOrderConfirmation(Order order, String confirmationId, String traceKey) {
		try {
			smsMessageService.sendMessage(buildConfirmationMessage(order, confirmationId), getFromNumber(order.getProviderLocation().getAddress().getState().toUpperCase()), order.getUser().getMobilePhone());
		} catch (Exception e) {
			_LOGGER.error("Exception while sending confirmation message to user "+ traceKey, e);
			throw new HNIException("Exception while sending confimation message to user : " + traceKey);
		}
	}

	private String getFromNumber(String stateCode) {
		return smsMessageService.getProviderNumberForState(stateCode);
	}

	private BigDecimal updateOrderPaymentStatusAndGetSubTotal(Collection<OrderPayment> paymentsForOrder, String traceKey) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		try {
			for(OrderPayment op : paymentsForOrder) { 
				totalAmount = totalAmount.add(BigDecimal.valueOf(op.getAmount()));
				op.setStatus(ORDER_COMPLETED);
				
				Optional<PaymentInstrument> paymentInstrument = Optional.ofNullable(op.getId().getPaymentInstrument());
				if (paymentInstrument.isPresent()) {
					double balance = paymentInstrument.get().getBalance() - op.getAmount();
					paymentInstrument.get().setBalance(balance);
					orderPaymentService.updatePaymentInstrument(paymentInstrument.get());
				}
				orderPaymentService.update(op);
			}
		} catch (Exception e) {
			_LOGGER.error("Exception while updating the Orderpayment and Instrument" + traceKey, e);
			throw new HNIException("Exception while updating the Orderpayment and Instrument : " + traceKey);
		}
		return totalAmount;
	}
	
	private String buildConfirmationMessage(Order order, String confirmationId) {
		HniTemplate template = hniTemplateService.getByType(Constants.PARTICIPANT_ORDER_CONFIRMATION_NOTIFICATION);
		String message = String.format(template.getTemplate(), Constants.HNI_CAP + order.getId(), getAddressString(order), confirmationId);
		
		return message;
	}

	private Object getAddressString(Order order) {
		StringBuilder sb = new StringBuilder();
		sb.append(order.getProviderLocation().getProvider().getName());
		sb.append(", ");
		sb.append(order.getProviderLocation().getAddress().getAddress1());
		sb.append(", ");
		sb.append(order.getProviderLocation().getAddress().getCity());
		
		return sb.toString();
	}
}
