package org.evan.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Inventory;
import org.evan.project.model.response.ApiResponse;
import org.evan.project.model.request.InventoryRequest;
import org.evan.project.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ApiResponse<Inventory> getById(
            @PathVariable Long id
    ) {
        return ApiResponse.of(
                inventoryService.getById(id)
        );
    }

    @GetMapping
    public ApiResponse<Page<Inventory>> getAll(
            Pageable pageable
    ) {
        return ApiResponse.of(
                inventoryService.getAll(pageable)
        );
    }

    @PostMapping
    public ApiResponse<Inventory> create(
            @Valid @RequestBody InventoryRequest request
    ) {
        return ApiResponse.of(
                inventoryService.create(request.itemId(), request.quantity(), request.type())
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Inventory> update(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request
    ) {
        return ApiResponse.of(
                inventoryService.update(id, request.quantity(), request.type())
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {
        inventoryService.delete(id);
        return ApiResponse.of(null);
    }
}
