package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Item;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item updated) {
        var existing = getById(id);
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        return itemRepository.save(existing);
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        itemRepository.delete(getById(id));
    }

    @Override
    public int getRemainingStock(Long itemId) {

        var totalIn = inventoryRepository
                .sumQuantityByItemIdAndType(itemId, InventoryType.T);

        var totalOut = inventoryRepository
                .sumQuantityByItemIdAndType(itemId, InventoryType.W);

        return (totalIn == null ? 0 : totalIn)
                - (totalOut == null ? 0 : totalOut);
    }
}
