package org.evan.project.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Item;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.ItemService;

import java.util.List;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.findByIdOptional(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Item> getAll(int page, int size) {
        return itemRepository.findAll()
                .page(page, size)
                .list();
    }

    @Override
    @Transactional
    public Item createItem(Item item) {
        itemRepository.persist(item);
        return item;
    }

    @Override
    public int getRemainingStock(Long itemId) {
        return getById(itemId).getStock();
    }

    @Override
    @Transactional
    public void topUpStock(Long itemId, int quantity) {
        Item item = getById(itemId);
        item.setStock(item.getStock() + quantity);
    }

    @Override
    @Transactional
    public void decreaseStock(Long itemId, int quantity) {
        Item item = getById(itemId);
        if (item.getStock() < quantity) {
            throw new org.evan.project.fault.InsufficientStockException();
        }
        item.setStock(item.getStock() - quantity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!itemRepository.deleteById(id)) {
            throw new ResourceNotFoundException();
        }
    }
}
