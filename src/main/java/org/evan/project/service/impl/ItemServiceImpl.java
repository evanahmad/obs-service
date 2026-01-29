package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Item;
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
        return null;
    }

    @Override
    public Item updateItem(Long id, Item item) {
        return null;
    }

    @Override
    public int getRemainingStock(Long itemId) {
        return 0;
    }

    @Override
    public Item getById(Long aLong) {
        return null;
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
