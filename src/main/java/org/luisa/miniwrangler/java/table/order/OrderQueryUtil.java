package org.luisa.miniwrangler.java.table.order;

import java.sql.Connection;

/*
 * TODO: Should abstract and make it generic
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.luisa.miniwrangler.java.Configuration;

/**
 * Utility class to save orders
 *
 * @author Luisa Pinto
 *
 */
public class OrderQueryUtil {

    private static final Logger LOGGER = Logger.getLogger(OrderQueryUtil.class.getName());
    private static int ORDER_ID = 1;
    private static int ORDER_DATE = 2;
    private static int PRODUCT_ID = 3;
    private static int PRODUCT_NAME = 4;
    private static int QUANTITY = 5;
    private static int UNIT = 6;

    private final static String REPLACE_STMT = "REPLACE INTO mini_wrangler.order VALUES (?,?,?,?,?,?)";
    private final static String INSERT_STMT = "INSERT INTO mini_wrangler.order VALUES (?,?,?,?,?,?)";

    /**
     * Performs batch save of a list of orders
     *
     * @param orders    the list of orders
     * @param replaceFl determines whether this until runs a 'INSERT' or 'REPLACE'
     *                  statement
     * @throws SQLException if a database access error occurs
     */
    public static void storeOrders(List<Order> orders, boolean replaceFl) throws SQLException {
        Connection con = null;
        PreparedStatement storeStmt = null;

        try {
            con = Configuration.getDataSource(Configuration.MINI_WRANGLER).getConnection();
            con.setAutoCommit(false);

            if (replaceFl) {
                storeStmt = con.prepareStatement(REPLACE_STMT);
            } else {
                storeStmt = con.prepareStatement(INSERT_STMT);
            }

            for (final Order order : orders) {
                storeStmt.setInt(ORDER_ID, order.getOrderId());
                storeStmt.setDate(ORDER_DATE, order.getOrderDt());
                storeStmt.setString(PRODUCT_ID, order.getProductId());
                storeStmt.setString(PRODUCT_NAME, order.getProductName());
                storeStmt.setBigDecimal(QUANTITY, order.getQuantity());
                storeStmt.setString(UNIT, order.getUnit());
                storeStmt.addBatch();
                storeStmt.executeBatch();
            }

            con.commit();
        } catch (final SQLException e) {
            LOGGER.severe("Unable to create orders " + e.getMessage());
            if (con != null) {
                try {
                    LOGGER.severe("Transaction is being rolled back");
                    con.rollback();
                } catch (final SQLException excep) {
                    LOGGER.severe("Unable to rollback " + e.getMessage());
                }
            }
        } finally {
            if (storeStmt != null) {
                storeStmt.close();
            }
            con.setAutoCommit(true);
            con.close();
        }
    }

}
