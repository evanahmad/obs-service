package org.evan.project.service.impl;

import org.evan.project.fault.ResourceNotFoundException;
import org.evan.project.model.entity.Item;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.repository.InventoryRepository;
import org.evan.project.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock private ItemRepository itemRepository;
    @Mock private InventoryRepository inventoryRepository;
    @InjectMocks private ItemServiceImpl itemService;

    @Test
    void createItem_Success() {
        Item item = Item.builder().name("Item A").price(BigDecimal.TEN).build();
        when(itemRepository.save(any())).thenReturn(item);

        Item result = itemService.createItem(item);
        assertEquals("Item A", result.getName());
        verify(itemRepository).save(item);
    }

    @Test
    void updateItem_Success() {
        Long id = 1L;
        Item existing = Item.builder().id(id).name("Old").build();
        Item updated = Item.builder().name("New").price(BigDecimal.ONE).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(existing));
        when(itemRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Item result = itemService.updateItem(id, updated);
        assertEquals("New", result.getName());
    }

    @Test
    void getById_NotFound_ShouldThrowException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> itemService.getById(1L));
    }

    @Test
    void getRemainingStock_CalculationCorrect() {
        Long itemId = 1L;
        when(inventoryRepository.sumQuantityByItemIdAndType(itemId, InventoryType.T)).thenReturn(100);
        when(inventoryRepository.sumQuantityByItemIdAndType(itemId, InventoryType.W)).thenReturn(40);

        int stock = itemService.getRemainingStock(itemId);
        assertEquals(60, stock);
    }

    @Test
    void getRemainingStock_NullReturns_ShouldReturnZero() {
        when(inventoryRepository.sumQuantityByItemIdAndType(any(), any())).thenReturn(null);
        assertEquals(0, itemService.getRemainingStock(1L));
    }
}
