package org.evan.project.model.response;

import org.evan.project.model.entity.Item;

public record ItemWithStockResponse(
        Item item,
        Integer remainingStock
) {
}
