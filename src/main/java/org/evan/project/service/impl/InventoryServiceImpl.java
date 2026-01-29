package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.InsufficientStockException;
import org.evan.project.fault.ObsFault;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public Inventory create(Long itemId, int quantity, InventoryType type) {

        if (quantity <= 0) {
            throw new InsufficientStockException(ObsFault.INSUFFICIENT_STOCK);
        }

        var item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", itemId));

        Inventory inventory = Inventory.builder()
                .item(item)
                .quantity(quantity)
                .type(type)
                .build();

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Long id, int quantity, InventoryType type) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ObsFault.RESOURCE_NOT_FOUND));

        inventory.setQuantity(quantity);
        inventory.setType(type);

        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ObsFault.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(ObsFault.RESOURCE_NOT_FOUND);
        }
        inventoryRepository.deleteById(id);
    }
}
