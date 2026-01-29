package org.evan.project.service;

import org.evan.project.model.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends BaseService<Order, Long> {
    Order createOrder(Long itemId, int quantity);

    Order updateOrder(Long id, int quantity);
}
