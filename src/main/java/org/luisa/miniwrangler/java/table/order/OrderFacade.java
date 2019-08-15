package org.luisa.miniwrangler.java.table.order;

import java.math.BigDecimal;
import java.sql.Date;

import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;
import org.luisa.miniwrangler.java.table.Facade;
import org.luisa.miniwrangler.java.validation.Validated;
import org.luisa.miniwrangler.java.validation.ValidatedImpl;
import org.luisa.miniwrangler.java.validation.Validator;
import org.luisa.miniwrangler.java.validation.utils.BigDecimalUtils;
import org.luisa.miniwrangler.java.validation.utils.DateUtils;
import org.luisa.miniwrangler.java.validation.utils.IntegerUtils;
import org.luisa.miniwrangler.java.validation.utils.StringUtils;

/**
 * A class to support interaction with Order objects
 *
 * @author Luisa Pinto
 *
 */
public class OrderFacade extends Order implements Facade, Validator<String, String> {

    private static final long serialVersionUID = 1L;

    public static final String PRODUCT_ID = "ProductId";

    public static final String PRODUCT_NAME = "ProductName";

    public static final String QUANTITY = "Quantity";

    public static final String ORDER_ID = "OrderID";

    public static final String ORDER_DATE = "OrderDate";

    public static final String UNIT = "Unit";

    private static final int PID_MIN_LENGTH = 1;

    private static final int PID_MAX_LENGTH = 45;

    private static final int PN_MAX_LENGTH = 255;

    private static final int PN_MIN_LENGTH = 1;

    private static final int QTY_MIN_VALUE = 0;

    private static final int U_MIN_LENGTH = 1;

    private static final int U_MAX_LENGTH = 8;

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.table.order.OrderFacade.get(String field)
     */
    @Override
    public Object get(String field) throws UnknownFieldException {
        Object value = null;
        switch (field) {
        case ORDER_ID:
            value = getOrderId();
            break;
        case ORDER_DATE:
            value = getOrderDt();
            break;
        case PRODUCT_ID:
            value = getProductId();
            break;
        case PRODUCT_NAME:
            value = getProductName();
            break;
        case QUANTITY:
            value = getQuantity();
            break;
        case UNIT:
            value = getUnit();
            break;
        default:
            throw new UnknownFieldException("Unknown field '" + field + "'");
        }
        return value;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.table.order.OrderFacade.set(String field,
     * String value)
     */
    @Override
    public void set(String field, String value) throws UnknownFieldException, InvalidDataException {
        switch (field) {
        case ORDER_ID:
            int orderId;
            try {
                orderId = Integer.parseInt(value);
            } catch (final NumberFormatException e) {
                throw new InvalidDataException(field, value);
            }
            setOrderId(orderId);
            break;
        case ORDER_DATE:
            Date date = null;
            try {
                date = Date.valueOf(value);
            } catch (final IllegalArgumentException e) {
                throw new InvalidDataException(field, value);
            }
            setOrderDt(date);
            break;
        case PRODUCT_ID:
            setProductId(value);
            break;
        case PRODUCT_NAME:
            setProductName(value);
            break;
        case QUANTITY:
            value = value.replace(",", "");
            final BigDecimal bigDecimal;
            try {
                bigDecimal = new BigDecimal(value);
            } catch (final NumberFormatException e) {
                throw new InvalidDataException(field, value);
            }
            setQuantity(bigDecimal);
            break;
        case UNIT:
            setUnit(value);
            break;
        default:
            throw new UnknownFieldException("Unknown field '" + field + "'");
        }

    }

    private void updateValidated(Validated<String, String> validated, String field, String errorMsg) {
        if (errorMsg.isEmpty()) {
            validated.success(field);
        } else {
            validated.error(errorMsg);
        }
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validate()
     */
    @Override
    public Validated<String, String> validate() {
        final Validated<String, String> validated = new ValidatedImpl<>();

        String errorMsg;

        errorMsg = IntegerUtils.checkNotNull(ORDER_ID, getOrderId());
        updateValidated(validated, ORDER_ID, errorMsg);

        errorMsg = DateUtils.checkNotNull(ORDER_DATE, getOrderDt())
                + DateUtils.checkPastOrPresent(ORDER_DATE, getOrderDt());
        updateValidated(validated, ORDER_DATE, errorMsg);

        errorMsg = StringUtils.checkNotBlank(PRODUCT_ID, getProductId())
                + StringUtils.checkLength(PRODUCT_ID, getProductId(), PID_MIN_LENGTH, PID_MAX_LENGTH);
        updateValidated(validated, PRODUCT_ID, errorMsg);

        errorMsg = StringUtils.checkNotBlank(PRODUCT_NAME, getProductName())
                + StringUtils.checkLength(PRODUCT_NAME, getProductName(), PN_MIN_LENGTH, PN_MAX_LENGTH);
        updateValidated(validated, PRODUCT_NAME, errorMsg);

        errorMsg = BigDecimalUtils.checkMinValue(QUANTITY, getQuantity(), new BigDecimal(QTY_MIN_VALUE));
        updateValidated(validated, QUANTITY, errorMsg);

        errorMsg = StringUtils.checkNotBlank(UNIT, getUnit())
                + StringUtils.checkLength(UNIT, getUnit(), U_MIN_LENGTH, U_MAX_LENGTH);
        updateValidated(validated, UNIT, errorMsg);

        return validated;
    }

}
