package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.luisa.miniwrangler.java.table.OrderFacade;

public class TextTransformFixedValue implements ITextTransformFixedValue {

	public static class Builder implements ITextTransformFixedValueBuilder {
		private String orderField;
		private String fixedValue;

		public Builder() {
		}

		@Override
		public ITextTransformBuilder as(String orderField) {
			this.orderField = orderField;
			return this;
		}

		@Override
		public ITextTransform build() {
			return new TextTransformFixedValue(this);
		}

		@Override
		public ITextTransformBuilder fixedValue(String value) {
			fixedValue = value;
			return null;
		}

		@Override
		public ITextTransformBuilder match(String pattern) {
			throw new UnsupportedOperationException("Match not supported");
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TextTransformFixedValue.class.getName());

	private static final String UNIT = "Unit";

	private final String orderField;

	private final String fixedValue;

	public TextTransformFixedValue(Builder b) {
		orderField = b.orderField;
		fixedValue = b.fixedValue;
	}

	private boolean apply(OrderFacade order) {
		boolean success = true;
		switch (orderField) {
		case UNIT:
			order.setUnit(fixedValue);
			break;
		default:
			success = false;
			LOGGER.warning("Not applicable to " + orderField);
			break;
		}
		return success;
	}

	@Override
	public String getFixedValue() {
		return fixedValue;
	}

	@Override
	public String getOrderField() {
		return orderField;
	}

	@Override
	public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, OrderFacade order) {
		final boolean transformed = transform(order);
		if (!transformed) {
			LOGGER.warning("Failed " + toString());
		}

	}

	@Override
	public String toString() {
		return "TextTransformFixedValue [orderField=" + orderField + ", fixedValue=" + fixedValue + "]";
	}

	@Override
	public boolean transform(OrderFacade order) {
		return apply(order);
	}

}
