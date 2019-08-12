package org.luisa.miniwrangler.java.transform;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.luisa.miniwrangler.java.table.OrderFacade;

public class TextTransformProperCase extends TextTransformRename {

	private static final Logger LOGGER = Logger.getLogger(TextTransformProperCase.class.getName());

	public TextTransformProperCase(Builder b) {
		super(b);
	}

	private boolean apply(OrderFacade order) {
		boolean success = true;
		final Class<OrderFacade> aClass = OrderFacade.class;
		Field field;
		try {
			field = aClass.getField(getOrderField());
			final String value = (String) field.get(order);
			field.set(order, toProperCase(value));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			success = false;
			LOGGER.warning("Transformation failed. reason= " + e.getMessage());
		}
		return success;
	}

	public String toProperCase(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		final StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}

		return converted.toString();
	}

	@Override
	public boolean transform(OrderFacade order) {
		boolean success = super.transform(order);

		if (!success) {
			return success;
		}

		final String orderField = getOrderField();
		switch (orderField) {
		case PRODUCT_ID:
		case PRODUCT_NAME:
			success = success && apply(order);
			break;
		default:
			success = false;
			LOGGER.warning("Not applicable to " + orderField);
			break;
		}

		return success;
	}

}
