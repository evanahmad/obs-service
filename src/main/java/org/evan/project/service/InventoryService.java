package org.evan.project.service;

import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;

public interface InventoryService extends BaseService<Inventory, Long> {

    Inventory create(Long itemId, int quantity, InventoryType inventoryType);

    Inventory update(Long id, int quantity, InventoryType inventoryType);
}
