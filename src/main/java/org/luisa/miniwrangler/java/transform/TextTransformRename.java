package org.luisa.miniwrangler.java.transform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.luisa.miniwrangler.java.table.OrderFacade;

public class TextTransformRename implements ITextTransformRename {

	public static class Builder implements ITextTransformRenameBuilder {
		private String orderField;
		private String srcField;
		private Pattern pattern;

		protected final Map<String, String> patternMap = new HashMap<>();

		public Builder() {
			initPatternMap();
		}

		@Override
		public ITextTransformBuilder as(String orderField) {
			this.orderField = orderField;
			return this;
		}

		@Override
		public ITextTransform build() {
			return new TextTransformRename(this);
		}

		protected void initPatternMap() {
			patternMap.put("d+", "\\d+");

			final String pattern = "([1-9],)?[1-9]{0,2}[0-9]\\.[0-9]([1-9])?";
			final String mappedPattern = "#,##0.0#";

			patternMap.put(pattern, mappedPattern);
		}

		@Override
		public ITextTransformBuilder match(String pattern) {
			String regex = patternMap.get(pattern);
			if (regex == null) {
				regex = pattern;
			}
			this.pattern = Pattern.compile(regex);
			return this;
		}

		@Override
		public ITextTransformBuilder rename(String srcField) {
			this.srcField = srcField;
			return this;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TextTransformRename.class.getName());

	private final String orderField;
	private final String srcField;
	private final Pattern pattern;

	protected final String ORDER_ID = "OrderID";

	private String srcFieldValue;

	protected final String PRODUCT_ID = "ProductId";

	protected final String PRODUCT_NAME = "ProductName";

	protected final String QUANTITY = "Quantity";

	public TextTransformRename(Builder b) {
		orderField = b.orderField;
		srcField = b.srcField;
		pattern = b.pattern;
	}

	private boolean apply(String value, OrderFacade order) {
		boolean success = true;
		switch (orderField) {
		case ORDER_ID:
			order.setOrderId(value);
			break;
		case PRODUCT_ID:
			order.setProductId(value);
			break;
		case PRODUCT_NAME:
			order.setProductName(value);
			break;
		case QUANTITY:
			order.setQuantity(value);
		default:
			success = false;
			LOGGER.warning("Not applicable to " + orderField);
			break;
		}
		return success;
	}

	@Override
	public String getOrderField() {
		return orderField;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public String getSrcField() {
		return srcField;
	}

	@Override
	public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, OrderFacade order) {
		final String srcField = getSrcField();
		final Integer index = srcFieldIndexMap.get(srcField);
		if (index != null) {
			final String value = currentRow.get(index);
			setSrcFieldValue(value);
		} else {
			LOGGER.warning("Failed trasnformation for " + srcField + ". reason= index missing ");
			return;
		}

		final boolean transformed = transform(order);
		if (!transformed) {
			LOGGER.warning("Failed " + toString());
		}

	}

	@Override
	public void setSrcFieldValue(String value) {
		srcFieldValue = value;
	}

	@Override
	public String toString() {
		return "TextTransform [orderField=" + orderField + ", srcField=" + srcField + ", pattern=" + pattern + "]";
	}

	@Override
	public boolean transform(OrderFacade order) {

		if (pattern != null) {
			final Matcher m = pattern.matcher(srcFieldValue);
			if (!m.matches()) {
				final String mismatch = m.replaceAll("");
				LOGGER.warning(
						"Skipped " + toString() + ": reason= pattern mismatches "
								+ Arrays.toString(mismatch.toCharArray())
								+ "; expected= " + pattern
								+ "; srcFieldValue= " + srcFieldValue);
				return false;
			}
		}

		return apply(srcFieldValue, order);
	}

}
