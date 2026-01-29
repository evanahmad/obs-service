package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public Inventory create(Long itemId, int quantity, InventoryType inventoryType) {
        var item = itemRepository.findById(itemId)
                .orElseThrow();

        var inventory = Inventory.builder()
                .item(item)
                .quantity(quantity)
                .type(inventoryType)
                .build();

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Long id, int quantity, InventoryType inventoryType) {

        var item = inventoryRepository.findById(id).orElseThrow();
        item.setQuantity(quantity);
        item.setType(inventoryType);

        return inventoryRepository.save(item);
    }

    @Override
    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    @SuppressWarnings("java:S112")
    public void delete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException();
        }

        inventoryRepository.deleteById(id);

    }
}
