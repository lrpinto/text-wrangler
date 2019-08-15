package org.luisa.miniwrangler.java.table;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.luisa.miniwrangler.java.Configuration;
import org.luisa.miniwrangler.java.table.order.Order;
import org.luisa.miniwrangler.java.table.order.OrderQueryUtil;

public class OrderQueryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configuration.init();
	}

	List<Order> orders;
	private Order order;

	@Before
	public void setUp() throws Exception {
		order = new Order();

		final Date orderDt = new Date(0);
		order.setOrderDt(orderDt);

		final Integer orderId = 1000;
		order.setOrderId(orderId);

		final String productId = "P-1000";
		order.setProductId(productId);

		final String productName = "Tarantula";
		order.setProductName(productName);

		final BigDecimal quantity = new BigDecimal("5000.50");
		order.setQuantity(quantity);

		final String unit = "kg";
		order.setUnit(unit);

		orders = new ArrayList<>();

		orders.add(order);

	}

	@Test
	public void test() throws SQLException {
		final boolean replaceFl = false;
		OrderQueryUtil.storeOrders(orders, replaceFl);

	}

}
