package validation;

import org.junit.jupiter.api.Test;
import org.luisa.miniwrangler.java.table.Order;
import org.luisa.miniwrangler.java.validation.ValidatorInjector;
import org.luisa.miniwrangler.java.validation.ValidatorInjectorImpl;

class OrderValidatorTest {

	@Test
	void test() {

		final Order order = new Order();
		order.setOrderId(1);

		final ValidatorInjector<Order> orderValidatorInjector = new ValidatorInjectorImpl<>();

		orderValidatorInjector.getValidatorConsumer().validate(order).stream()
				.forEach(violation -> System.out
						.println(violation.getMessage()));

	}

}
