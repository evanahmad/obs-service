package org.evan.project.service;

import org.evan.project.model.entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService extends BaseService<Item, Long> {
    Item createItem(Item item);

    Item updateItem(Long id, Item item);

    int getRemainingStock(Long itemId);
}
