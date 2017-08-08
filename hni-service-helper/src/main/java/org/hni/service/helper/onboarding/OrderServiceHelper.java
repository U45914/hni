/**
 * 
 */
package org.hni.service.helper.onboarding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.hni.common.Constants;
import org.hni.order.om.Order;
import org.hni.order.service.OrderService;
import org.hni.payment.om.OrderPayment;
import org.hni.payment.service.OrderPaymentService;
import org.hni.template.om.HniTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author U45914
 *
 */
@Component
public class OrderServiceHelper extends AbstractServiceHelper {

	private static final String SUCCESSFULLY_CANCELLED_ORDER = "Successfully cancelled order";

	private static final String SOMETHING_WENT_WRONG_PLEASE_RETRY_AFTER_SOMETIME = "Something went wrong please retry after sometime";

	private static final String CANCELLED_ORDER = "CN";

	private static final String NO_ORDER_FOUND_WITH_THE_GIVEN_DETAILS = "No order found with the given details";

	private final static Logger _LOGGER = LoggerFactory.getLogger(OrderServiceHelper.class);
	
	@Inject
	private OrderPaymentService orderPaymentService;
	@Inject
	private OrderService orderService;

	public Map<String, String> cancelOrder(Map<String, Object> cancelledOrder, Long orderId) {
		Map<String, String> response = new HashMap<>();
		try {
			Order order = orderService.get(orderId);
			if (order != null) {
				// Cancel this order and release the lock of this order from
				// Reddis
				_LOGGER.info("Releasing lock for order "+ orderId);
				cancelOrderAndReleaseLock(order);
				// Now release any payment cards which is associated with this
				// and remove payment transcripts
				_LOGGER.info("Cancelling order payments "+ orderId);
				cancelPaymentFor(order);
				// Now sent intimation to client
				sendNotification(order);
				
				response.put(Constants.RESPONSE, Constants.SUCCESS);
				response.put(Constants.MESSAGE, SUCCESSFULLY_CANCELLED_ORDER);
			} else {
				response.put(Constants.RESPONSE, Constants.ERROR);
				response.put(Constants.MESSAGE, NO_ORDER_FOUND_WITH_THE_GIVEN_DETAILS);
			}
		} catch (Exception e) {
			response.put(Constants.RESPONSE, Constants.ERROR);
			response.put(Constants.MESSAGE, SOMETHING_WENT_WRONG_PLEASE_RETRY_AFTER_SOMETIME);
		}
		return response;
	}

	private void sendNotification(Order order) {
		_LOGGER.info("Trying to send cancel notification to user "+ order.getUser().getEmail());
		smsMessageService.sendMessage(buildCancelOrderNotificationMessage(order),
				getFromNumber(order.getProviderLocation().getAddress().getState().toUpperCase()),
				order.getUser().getMobilePhone());
		_LOGGER.info("Completed order cancellation notification");
	}

	private String buildCancelOrderNotificationMessage(Order order) {
		HniTemplate template = hniTemplateService.getByType(Constants.ORDER_CANCELLATION_NOTIFICATION);
		String message = String.format(template.getTemplate(), Constants.HNI_CAP + order.getId());

		return message;
	}

	private void cancelOrderAndReleaseLock(Order order) {
		orderService.cancelOrder(order);
	}

	private void cancelPaymentFor(Order order) {
		for (OrderPayment op : orderPaymentService.paymentsFor(order)) {
			op.setStatus(CANCELLED_ORDER);
			orderPaymentService.update(op);
		}
	}
}
