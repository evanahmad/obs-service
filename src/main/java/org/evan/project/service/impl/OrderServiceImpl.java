package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.InsufficientStockException;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Order;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.OrderRepository;
import org.evan.project.service.InventoryService;
import org.evan.project.service.ItemService;
import org.evan.project.service.OrderService;
import org.evan.project.util.QuantityValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    @Override
    public Order createOrder(Long itemId, int quantity) {

        QuantityValidator.validatePositive(quantity);

        var item = itemService.getById(itemId);
        var remainingStock = itemService.getRemainingStock(itemId);

        if (remainingStock < quantity) {
            throw new InsufficientStockException();
        }

        inventoryService.create(itemId, quantity, InventoryType.W);

        var order = Order.builder()
                .item(item)
                .qty(quantity)
                .price(item.getPrice())
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, int quantity) {
        QuantityValidator.validatePositive(quantity);
        var existing = getById(id);
        existing.setQty(quantity);
        return orderRepository.save(existing);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(getById(id));
    }
}
