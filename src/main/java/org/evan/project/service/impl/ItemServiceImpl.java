package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.ObsFault;
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
    public Item updateItem(Long id, Item item) {
        Item existing = getById(id);

        existing.setName(item.getName());
        existing.setPrice(item.getPrice());

        return itemRepository.save(existing);
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ObsFault.RESOURCE_NOT_FOUND));
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        Item item = getById(id);
        itemRepository.delete(item);
    }

    @Override
    public int getRemainingStock(Long itemId) {
        Integer totalTopUp = inventoryRepository.sumQuantityByItemIdAndType(
                itemId, InventoryType.T
        );

        Integer totalWithdrawal = inventoryRepository.sumQuantityByItemIdAndType(
                itemId, InventoryType.W
        );

        return (totalTopUp == null ? 0 : totalTopUp)
                - (totalWithdrawal == null ? 0 : totalWithdrawal);
    }
}
