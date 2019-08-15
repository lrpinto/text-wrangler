package org.luisa.miniwrangler.java.parsing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.luisa.miniwrangler.CSVBaseListener;
import org.luisa.miniwrangler.CSVParser;
import org.luisa.miniwrangler.CSVParser.CsvFileContext;
import org.luisa.miniwrangler.CSVParser.EmptyContext;
import org.luisa.miniwrangler.CSVParser.HdrContext;
import org.luisa.miniwrangler.CSVParser.RowContext;
import org.luisa.miniwrangler.CSVParser.StringContext;
import org.luisa.miniwrangler.CSVParser.TextContext;
import org.luisa.miniwrangler.java.data.utils.StringUtils;
import org.luisa.miniwrangler.java.table.order.Order;
import org.luisa.miniwrangler.java.table.order.OrderFacade;
import org.luisa.miniwrangler.java.table.order.OrderQueryUtil;
import org.luisa.miniwrangler.java.transform.ITextTransform;
import org.luisa.miniwrangler.java.validation.JoinCombinator;
import org.luisa.miniwrangler.java.validation.Validated;

/*
 * TODO:
 * - Should refactor this class to decouple it from the responsibility of creating,
 *   saving orders and running transformations
 * - For this purpose, should give it the ability to register the CSVProcessor as a subscriber,
 *   who would be interested in topics such as exitHeader and exitRow
 * - Should also decouple the responsability f validating and saving orders
 * - Finally should lift responsability of treating and logging exceptions
 */

/**
 * A Custom CSV Listener creates and transforms orders according to given
 * transformations and target source fields
 *
 * @author Luisa Pinto
 *
 */
public class CSVCustomListener extends CSVBaseListener {

    private static final Logger LOGGER = Logger.getLogger(CSVCustomListener.class.getName());

    private final Collection<ITextTransform> transformations;
    private Set<String> srcFields = null;
    private Map<String, Integer> srcFieldIndexMap = null;
    private List<String> currentRow = null;

    private List<String> skippedSrcFields = null;
    private final String EMPTY_STR = "";

    List<Order> orders = null;

    private int bulkSaveSize;

    private boolean replaceFl;

    /**
     * Constructs an CSVCustomListener with the given transformation loader
     *
     * @param loader the given loader
     */
    public CSVCustomListener(TextTransformCustomListener loader) {
        skippedSrcFields = new ArrayList<>();
        srcFields = loader.getSrcFields();
        transformations = loader.getTransformations();
        srcFieldIndexMap = new HashMap<>();
        orders = new ArrayList<>();

    }

    /**
     * On entering an empty field add an empty String to the current row
     */
    @Override
    public void enterEmpty(EmptyContext ctx) {
        currentRow.add(EMPTY_STR);
    }

    /**
     * On entering the header creates a new row
     */
    @Override
    public void enterHdr(HdrContext ctx) {
        currentRow = new ArrayList<>();
    }

    /**
     * On entering a row creates a new row
     */
    @Override
    public void enterRow(RowContext ctx) {
        currentRow = new ArrayList<>();
    }

    /**
     * On entering a string remove any leadings quotes and trim
     */
    @Override
    public void enterString(StringContext ctx) {
        String value = ctx.getText();
        value = StringUtils.stripQuotes(value);
        value = value.trim();
        currentRow.add(value);
    }

    /**
     * On entering text remove any leadings quotes and trim
     */
    @Override
    public void enterText(TextContext ctx) {
        String value = ctx.getText();
        value = StringUtils.stripQuotes(value);
        value = value.trim();
        currentRow.add(value);
    }

    /**
     * On exiting the CSV file save any remaining order
     */
    @Override
    public void exitCsvFile(CsvFileContext ctx) {
        try {
            OrderQueryUtil.storeOrders(orders, replaceFl);
        } catch (final SQLException e) {
            LOGGER.severe("SQL Error " + e.getMessage());
        }
    }

    /**
     * On exiting the header save a map from target source field to row-wise index
     * and log skipped ones
     */
    @Override
    public void exitHdr(HdrContext ctx) {
        setSrcFieldsIndexes(currentRow);
        LOGGER.warning("Skipped source fields: " + skippedSrcFields);
    }

    /**
     * On exiting a row create a order from the current row, and may or not save any
     * accumulated orders depending on the bulkSaveSize property
     */
    @Override
    public void exitRow(RowContext ctx) {
        if (ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) {
            return;
        }

        // Create a new order and apply transformations to it
        final OrderFacade order = new OrderFacade();
        for (final ITextTransform transformation : transformations) {
            try {
                transformation.run(srcFieldIndexMap, currentRow, order);
            } catch (final Exception e) {
                LOGGER.warning("Tranformation failed " + e.getMessage());
            }
        }

        // Validate the order
        final Validated<String, String> validated = order.validate();

        final List<String> errors = validated.errors();
        if (errors.isEmpty()) {
            orders.add(order);
        } else {
            final String validationResult = validated.combined(new JoinCombinator<String, String>());
            LOGGER.warning("Order skipped: " + order.toString() + " due to " + validationResult);
        }

        // Save the order
        if (orders.size() == bulkSaveSize) {
            try {
                OrderQueryUtil.storeOrders(orders, replaceFl);
            } catch (final SQLException e) {
                LOGGER.severe("SQL Error: " + e.getMessage());
            }
        }

    }

    /**
     * Set bulk save size
     *
     * @param size the given bulk save size
     */
    public void setBulkSaveSize(int size) {
        bulkSaveSize = size;
    }

    /**
     * Set the replace flag
     *
     * @param replaceFl the given replace flag
     */
    public void setReplaceFl(boolean replaceFl) {
        this.replaceFl = replaceFl;
    }

    /*
     * Auxiliary method that saves the row-wise indexes of the target src field
     * values
     */
    private void setSrcFieldsIndexes(List<String> headers) {
        for (int i = 0; i < headers.size(); i++) {
            final String currentHeader = headers.get(i);
            if (srcFields.contains(currentHeader)) {
                srcFieldIndexMap.put(currentHeader, i);
            } else {
                skippedSrcFields.add(currentHeader);
            }
        }
    }

}
