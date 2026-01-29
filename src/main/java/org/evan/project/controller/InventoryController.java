package org.evan.project.controller;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;
import org.evan.project.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public Inventory getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @GetMapping
    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryService.getAll(pageable);
    }

    @PostMapping
    public Inventory create(@RequestParam Long itemId,
                            @RequestParam int quantity,
                            @RequestParam InventoryType type) {
        return inventoryService.create(itemId, quantity, type);
    }

    @PutMapping("/{id}")
    public Inventory update(@PathVariable Long id,
                            @RequestParam int quantity,
                            @RequestParam InventoryType type) {

        return inventoryService.update(id, quantity, type);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }
}
