package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.InsufficientStockException;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.InventoryService;
import org.evan.project.service.ItemService;
import org.evan.project.util.QuantityValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    private final ItemService itemService;

    @Override
    public Inventory create(Long itemId, int quantity, InventoryType type) {
        QuantityValidator.validatePositive(quantity);
        var item = itemRepository.findById(itemId).orElseThrow(ResourceNotFoundException::new);

        if (type == InventoryType.W) {
            int currentStock = itemService.getRemainingStock(itemId);
            if (currentStock < quantity) {
                throw new InsufficientStockException();
            }
        }

        var inventory = Inventory.builder()
                .item(item)
                .quantity(quantity)
                .type(type)
                .build();

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Long id, int quantity, InventoryType type) {

        QuantityValidator.validatePositive(quantity);

        var inventory = inventoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        inventory.setQuantity(quantity);
        inventory.setType(type);

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        inventoryRepository.delete(getById(id));
    }
}
