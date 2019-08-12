package org.luisa.miniwrangler.java.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.luisa.miniwrangler.java.Context;

public class OrderQuery {

	private static final Logger LOGGER = Logger.getLogger(OrderQuery.class.getName());

	private final String INSERT_STMT = "insert into order values(?,?,?,?,?,?)";
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private int count = 0;

	private final int ORDER_ID = 0;
	private final int ORDER_DATE = 1;
	private final int PRODUCT_ID = 2;
	private final int PRODUCT_NAME = 3;
	private final int QUANTITY = 4;
	private final int UNIT = 5;

	public void close() {
		try {
			commit();
			con.setAutoCommit(true);
			con.close();
		} catch (final SQLException e) {
			LOGGER.warning("Unable to close connection, reason=" + e.getMessage());
		}
	}

	private void commit() {
		try {
			con.commit();
			count = 0;
		} catch (final SQLException e) {
			LOGGER.severe("Unable to commit, reason=" + e.getMessage());
			try {
				con.rollback();
			} catch (final SQLException e1) {
				LOGGER.severe("Unable to rollback commit, reason=" + e.getMessage());
			}
		}
	}

	public void open() {
		try {
			con = Context.getDataSource(Context.MINI_WRANGLER).getConnection();
			con.setAutoCommit(false);
		} catch (final SQLException e) {
			LOGGER.warning("Unable to open connection, reason=" + e.getMessage());
		}
	}

	private void prepareCommit(OrderFacade order) {
		try {
			if (pstmt == null || pstmt.isClosed()) {
				if (con.isClosed()) {
					open();
				}
				pstmt = con.prepareStatement(INSERT_STMT);
			}

			if (order.isValidated() || order.validate()) {
				pstmt.setInt(ORDER_ID, order.getOrderId());
				pstmt.setDate(ORDER_DATE, order.getOrderDt());
				pstmt.setString(PRODUCT_ID, order.getProductId());
				pstmt.setString(PRODUCT_NAME, order.getProductName());
				pstmt.setBigDecimal(QUANTITY, order.getQuantity());
				pstmt.setString(UNIT, order.getUnit());
				pstmt.execute();
				count++;
			} else {
				LOGGER.warning("Invalid order has been ignored");
			}

		} catch (final SQLException e) {
			LOGGER.severe("Unable to prepare commit, reason=" + e.getMessage());
		}

	}

	public void store(OrderFacade order) {
		if (count >= 50) {
			commit();
		} else {
			prepareCommit(order);
		}
	}
}
