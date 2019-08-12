package org.luisa.miniwrangler.java.table;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import org.luisa.miniwrangler.java.validation.ValidatorInjector;
import org.luisa.miniwrangler.java.validation.ValidatorInjectorImpl;

public class OrderFacade extends Order {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(OrderFacade.class.getName());
	private boolean isValidated;

	boolean isValid() {
		return isValidated;
	}

	public boolean isValidated() {
		return isValidated;
	}

	@Override
	public void setOrderDt(Date orderDt) {
		super.setOrderDt(orderDt);
		isValidated = false;
	}

	public void setOrderId(String orderId) {
		final int id = Integer.parseInt(orderId);
		super.setOrderId(id);
		isValidated = false;

	}

	@Override
	public void setProductId(String productId) {
		super.setProductId(productId);
		isValidated = false;
	}

	@Override
	public void setProductName(String productName) {
		super.setProductName(productName);
		isValidated = false;
	}

	@Override
	public void setQuantity(BigDecimal quantity) {
		super.setQuantity(quantity);
		isValidated = false;
	}

	public void setQuantity(String value) {
		BigDecimal bigDecimal = new BigDecimal(value);
		super.setQuantity(bigDecimal);
		isValidated = false;
	}

	@Override
	public void setUnit(String unit) {
		super.setUnit(unit);
		isValidated = false;
	}

	public boolean validate() {
		final ValidatorInjector<Order> orderValidatorInjector = new ValidatorInjectorImpl<>();
		final Set<ConstraintViolation<Order>> violations = orderValidatorInjector.getValidatorConsumer().validate(this);
		violations.stream().forEach(violation -> {
			LOGGER.warning("Order validation violation. reason=" + violation.getMessage());
		});
		isValidated = violations.isEmpty();
		return isValidated;
	}

}
