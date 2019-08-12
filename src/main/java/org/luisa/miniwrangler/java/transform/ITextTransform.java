package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;

import org.luisa.miniwrangler.java.table.OrderFacade;

public interface ITextTransform {

	String getOrderField();

	void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, OrderFacade order);

	boolean transform(OrderFacade order);

}
