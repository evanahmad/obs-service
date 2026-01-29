package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.fault.InsufficientStockException;
import org.evan.project.fault.ObsFault;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Order;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.OrderRepository;
import org.evan.project.service.InventoryService;
import org.evan.project.service.ItemService;
import org.evan.project.service.OrderService;
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

        if (quantity <= 0) {
            throw new InsufficientStockException(ObsFault.INSUFFICIENT_STOCK);
        }

        var item = itemService.getById(itemId);

        int remainingStock = itemService.getRemainingStock(itemId);
        if (remainingStock < quantity) {
            throw new InsufficientStockException(ObsFault.INSUFFICIENT_STOCK);
        }

        inventoryService.create(itemId, quantity, InventoryType.W);

        Order order = Order.builder()
                .item(item)
                .qty(quantity)
                .price(item.getPrice())
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ObsFault.RESOURCE_NOT_FOUND));
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order updateOrder(Long id, int qty) {

        if (qty <= 0) {
            throw new InsufficientStockException();
        }

        Order existing = getById(id);
        existing.setQty(qty);

        return orderRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Order order = getById(id);
        orderRepository.delete(order);
    }
}
