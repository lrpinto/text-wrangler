package org.luisa.miniwrangler.java.transform;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.luisa.miniwrangler.java.table.OrderFacade;

public class TextTransformConcat implements ITextTransformConcat {

	public static class Builder implements ITextTransformConcatBuilder {
		private String orderField;
		private final List<String> srcFields;
		private final List<Pattern> patterns;
		private final List<String> srcFieldsOriginalPatterns;

		private final Map<String, String> patternMap = new HashMap<>();

		public Builder() {
			initPatternMap();
			srcFields = new ArrayList<>();
			patterns = new ArrayList<>();
			srcFieldsOriginalPatterns = new ArrayList<>();
		}

		@Override
		public ITextTransformBuilder as(String orderField) {
			this.orderField = orderField;
			return this;
		}

		@Override
		public ITextTransform build() {
			return new TextTransformConcat(this);
		}

		@Override
		public ITextTransformBuilder concat(String... srcFields) {
			this.srcFields.addAll(Arrays.asList(srcFields));
			return this;
		}

		private void initPatternMap() {
			patternMap.put("YYYY", "^\\d{4}$");
			patternMap.put("MM", "^0*([1-9]|1[0-2])$");
			patternMap.put("dd", "^0*([1-9]|[12][0-9]|3[01])$");
		}

		@Override
		public ITextTransformBuilder match(String pattern) {
			srcFieldsOriginalPatterns.add(pattern);
			pattern = patternMap.get(pattern);
			patterns.add(Pattern.compile(pattern));
			return this;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TextTransformConcat.class.getName());

	private static final String ORDER_DATE = "OrderDate";

	private final String orderField;
	private final List<String> srcFields;
	private final List<Pattern> patterns;
	private final Map<String, String> srcFieldValueMap;
	private final List<String> srcFieldsOriginalPatterns;

	public TextTransformConcat(Builder b) {
		orderField = b.orderField;
		srcFields = b.srcFields;
		patterns = b.patterns;
		srcFieldsOriginalPatterns = b.srcFieldsOriginalPatterns;
		srcFieldValueMap = new HashMap<>();
	}

	@Override
	public void addSrcFieldValueMap(Map<String, String> valueMap) {
		srcFieldValueMap.putAll(valueMap);
	}

	private boolean apply(OrderFacade order) {
		boolean success = true;
		switch (orderField) {
		case ORDER_DATE:
			final int positionYear = srcFieldsOriginalPatterns.indexOf("YYYY");
			final int positionMonth = srcFieldsOriginalPatterns.indexOf("MM");
			final int positionDay = srcFieldsOriginalPatterns.indexOf("dd");
			if (positionYear > -1 && positionMonth > -1 && positionDay > -1) {
				String year = "", month = "", day = "";
				for (int i = 0; i < srcFields.size(); i++) {
					if (i == positionYear) {
						year = srcFieldValueMap.get(srcFields.get(i));
					} else if (i == positionMonth) {
						month = srcFieldValueMap.get(srcFields.get(i));
					} else if (i == positionDay) {
						day = srcFieldValueMap.get(srcFields.get(i));
					}
				}
				final Date date = Date.valueOf(year + "-" + month + "-" + day);
				order.setOrderDt(date);
			} else {
				LOGGER.warning("Date concatenation failed. reason= missing" + (positionYear < 0 ? " YYYY" : "")
						+ (positionMonth < 0 ? " MM" : "") + (positionDay < 0 ? "dd" : ""));
			}
			break;
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
	public List<Pattern> getPatterns() {
		return patterns;
	}

	@Override
	public List<String> getSrcFields() {
		return srcFields;
	}

	@Override
	public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, OrderFacade order) {
		final List<String> srcFields = getSrcFields();
		final Map<String, String> srcFieldValueMap = new HashMap<>();
		srcFields.forEach(srcField -> {
			final Integer index = srcFieldIndexMap.get(srcField);
			if (index != null) {
				final String value = currentRow.get(index);
				srcFieldValueMap.put(srcField, value);
				addSrcFieldValueMap(srcFieldValueMap);
			} else {
				LOGGER.warning("Failed trasnformation for " + srcField + ". reason= index missing ");
				return;
			}
		});
		final boolean transformed = transform(order);
		if (!transformed) {
			LOGGER.warning("Failed " + toString());
		}

	}

	@Override
	public String toString() {
		return "TextTransform [orderField=" + orderField + ", srcField=" + srcFields + ", patterns=" + patterns + "]";
	}

	@Override
	public boolean transform(OrderFacade order) {
		boolean success = true;
		Pattern pattern;
		String srcField;
		String value;
		for (int i = 0; i < srcFields.size(); i++) {
			srcField = srcFields.get(i);
			pattern = patterns.get(i);
			value = srcFieldValueMap.get(srcField);
			final Matcher m = pattern.matcher(value);
			success &= m.matches();
		}
		if (!success) {
			LOGGER.warning("Skipped " + toString() + ": reason= pattern mismatch; expected= " + patterns
					+ "; srcFieldValueMap= " + srcFieldValueMap);
		} else {
			success = apply(order);
		}
		return success;
	}

}
