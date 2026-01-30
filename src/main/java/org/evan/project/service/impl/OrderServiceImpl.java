package org.evan.project.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Item;
import org.evan.project.model.entity.Order;
import org.evan.project.repository.OrderRepository;
import org.evan.project.service.ItemService;
import org.evan.project.service.OrderService;

import java.util.List;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public OrderServiceImpl(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public Order createOrder(Long itemId, int quantity) {
        itemService.decreaseStock(itemId, quantity);

        Item item = itemService.getById(itemId);

        Order order = Order.builder()
                .item(item)
                .qty(quantity)
                .price(item.getPrice())
                .build();

        orderRepository.persist(order);

        return order;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findByIdOptional(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Order> getAll(int page, int size) {
        return orderRepository.findAll()
                .page(page, size)
                .list();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.deleteById(id)) {
            throw new ResourceNotFoundException();
        }
    }
}
