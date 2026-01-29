package org.evan.project.service.impl;

import org.evan.project.fault.InsufficientStockException;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.entity.Item;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.evan.project.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock private InventoryRepository inventoryRepository;
    @Mock private ItemRepository itemRepository;
    @Mock private ItemService itemService;
    @InjectMocks private InventoryServiceImpl inventoryService;

    @Test
    void create_TopUp_Success() {
        Long itemId = 1L;
        Item item = Item.builder().id(itemId).build();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(inventoryRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Inventory result = inventoryService.create(itemId, 10, InventoryType.T);

        assertNotNull(result);
        assertEquals(InventoryType.T, result.getType());
        verify(inventoryRepository).save(any());
        verify(itemService, never()).getRemainingStock(any());
    }

    @Test
    void create_Withdrawal_Insufficient_ShouldThrowException() {
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));
        when(itemService.getRemainingStock(itemId)).thenReturn(5);

        assertThrows(InsufficientStockException.class, () ->
                inventoryService.create(itemId, 10, InventoryType.W)
        );
    }

    @Test
    void delete_Success() {
        Long id = 1L;
        Inventory inv = new Inventory();
        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inv));

        inventoryService.delete(id);
        verify(inventoryRepository).delete(inv);
    }
}
