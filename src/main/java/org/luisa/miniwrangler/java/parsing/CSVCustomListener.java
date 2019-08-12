package org.luisa.miniwrangler.java.parsing;

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
import org.luisa.miniwrangler.java.table.OrderFacade;
import org.luisa.miniwrangler.java.table.OrderQuery;
import org.luisa.miniwrangler.java.transform.ITextTransform;

public class CSVCustomListener extends CSVBaseListener {

	private static final Logger LOGGER = Logger.getLogger(CSVCustomListener.class.getName());

	private final Collection<ITextTransform> transformations;
	private Set<String> srcFields = null;
	private Map<String, Integer> srcFieldIndexMap = null;
	private List<String> currentRow = null;
	private final OrderQuery query;

	private List<String> skippedSrcFields = null;
	private final String EMPTY_STR = "";

	public CSVCustomListener(TextTransformCustomListener loader) {
		skippedSrcFields = new ArrayList<>();
		srcFields = loader.getSrcFields();
		transformations = loader.getTransformations();
		srcFieldIndexMap = new HashMap<>();
		query = new OrderQuery();
	}

	@Override
	public void enterCsvFile(CsvFileContext ctx) {
		super.enterCsvFile(ctx);
		query.open();
	}

	@Override
	public void enterEmpty(EmptyContext ctx) {
		super.enterEmpty(ctx);
		currentRow.add(EMPTY_STR);
	}

	@Override
	public void enterHdr(HdrContext ctx) {
		super.enterHdr(ctx);
		currentRow = new ArrayList<>();
	}

	@Override
	public void enterRow(RowContext ctx) {
		super.enterRow(ctx);
		currentRow = new ArrayList<>();
	}

	@Override
	public void enterString(StringContext ctx) {
		super.enterString(ctx);
		final String value = ctx.getText();
		currentRow.add(value);
	}

	@Override
	public void enterText(TextContext ctx) {
		super.enterText(ctx);
		final String value = ctx.getText();
		currentRow.add(value);
	}

	@Override
	public void exitCsvFile(CsvFileContext ctx) {
		super.exitCsvFile(ctx);
		query.close();
	}

	@Override
	public void exitHdr(HdrContext ctx) {
		super.exitHdr(ctx);
		setSrcFieldsIndexes(currentRow);
		LOGGER.warning("Skipped source fields: " + skippedSrcFields);
	}

	@Override
	public void exitRow(RowContext ctx) {
		super.exitRow(ctx);
		if (ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) {
			return;
		}
		final OrderFacade order = new OrderFacade();
		for (final ITextTransform transformation : transformations) {
			transformation.run(srcFieldIndexMap, currentRow, order);
		}
		query.store(order);
	}

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
