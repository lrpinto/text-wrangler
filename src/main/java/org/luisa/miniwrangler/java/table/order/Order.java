/**
 * Package beans.
 */
package org.luisa.miniwrangler.java.table.order;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * A class that represents Order objects.
 *
 * @author Luisa Pinto
 */
public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer orderId;

	private Date orderDt;

	private String productId;

	private String productName;

	private BigDecimal quantity;

	private String unit;

	/**
	 * Return this order's date.
	 *
	 * @return the order date.
	 */
	public Date getOrderDt() {
		return orderDt;
	}

	/**
	 * Return this order's ID.
	 *
	 * @return the order ID.
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * Return this order's product ID.
	 *
	 * @return the product ID.
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Return this order's product name.
	 *
	 * @return the product name.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Return this order's quantity.
	 *
	 * @return the given quantity.
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * Return this order's units.
	 *
	 * @return unit the given unit.
	 */
	public String getUnit() {
		return unit;
	}

	// Setters

	/**
	 * Set this order's date.
	 *
	 * @param orderDt the given order date.
	 */
	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}

	/**
	 * Set this order's ID.
	 *
	 * @param orderId the given order ID.
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * Set this order's product ID.
	 *
	 * @param productId the given product ID.
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * Set this order's product name.
	 *
	 * @param productName the given product name.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Set this order's quantity.
	 *
	 * @param quantity the given quantity.
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * Set the this order's unit.
	 *
	 * @param unit the given unit.
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
