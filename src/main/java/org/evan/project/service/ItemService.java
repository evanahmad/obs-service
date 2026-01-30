package org.evan.project.service;

import org.evan.project.model.entity.Item;

public interface ItemService extends BaseService<Item, Long> {
    Item createItem(Item item);
    int getRemainingStock(Long itemId);
    void topUpStock(Long itemId, int quantity);
    void decreaseStock(Long itemId, int quantity);
}
