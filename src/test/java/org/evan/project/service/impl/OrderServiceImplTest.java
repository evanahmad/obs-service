package org.evan.project.service.impl;

import org.evan.project.model.entity.Item;
import org.evan.project.model.entity.Order;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.OrderRepository;
import org.evan.project.service.InventoryService;
import org.evan.project.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private ItemService itemService;
    @Mock private InventoryService inventoryService;
    @InjectMocks private OrderServiceImpl orderService;

    @Test
    void createOrder_Success() {
        Long itemId = 1L;
        Item item = Item.builder().id(itemId).price(BigDecimal.valueOf(100)).build();

        when(itemService.getById(itemId)).thenReturn(item);
        when(itemService.getRemainingStock(itemId)).thenReturn(20);
        when(orderRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Order result = orderService.createOrder(itemId, 5);

        assertNotNull(result);
        assertEquals(5, result.getQty());
        verify(inventoryService).create(itemId, 5, InventoryType.W);
        verify(orderRepository).save(any());
    }

    @Test
    void updateOrder_Success() {
        Long id = 1L;
        Order existing = Order.builder().id(id).qty(1).build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenReturn(existing);

        Order result = orderService.updateOrder(id, 10);
        assertEquals(10, result.getQty());
    }
}
